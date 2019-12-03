package br.ufpe.cin.beholder.streams;

//import java.io.File;

import java.util.ArrayList;

import java.util.List;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapBpfProgram;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;

import br.ufpe.cin.beholder.packets.MQTTPacket;
import br.ufpe.cin.beholder.packets.MQTTPacketSender;

import com.espertech.esper.client.EPRuntime;

public class MQTTStreams extends Thread {

	private EPRuntime cepLocal;
	private String evilHost;

	public MQTTStreams(EPRuntime cepLocal) {
		this.cepLocal = cepLocal;
	}

	@SuppressWarnings("deprecation")
	public void run() {

		List<PcapIf> alldevs = new ArrayList<PcapIf>();
		StringBuilder errbuf = new StringBuilder();
		int r = Pcap.findAllDevs(alldevs, errbuf);
		if (r == Pcap.NOT_OK || alldevs.isEmpty()) {
			System.err.printf("Can't read list of devices, error is %s", errbuf.toString());
			return;
		}

		System.out.println("Network devices found:");
		int i = 0;

		for (PcapIf device : alldevs) {
			String description = (device.getDescription() != null) ? device.getDescription()
					: "No description available";
			System.out.printf("#%d: %s [%s]\n", i++, device.getName(), description);
		}

		PcapIf device = alldevs.get(11);
		System.out.printf("\nChoosing '%s' on your behalf:\n",
				(device.getDescription() != null) ? device.getDescription() : device.getName());

		int snaplen = 64 * 1024;
		int flags = Pcap.MODE_PROMISCUOUS;
		int timeout = 10 * 1000;

		PcapBpfProgram filter = new PcapBpfProgram();
		// String expression = "(tcp dst port 1883 and dst host 172.22.79.44) or (tcp
		// src port 1883 and src host 172.22.79.44)";
		String expression = "tcp port 1883";
		int optimize = 0; // 0 = false
		int netmask = 0xFFFFFF00; // 255.255.255.0

		Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);

		if (pcap == null) {
			System.err.printf("Error while opening device for capture: " + errbuf.toString());
			return;
		}

		if (pcap.compile(filter, expression, optimize, netmask) != Pcap.OK) {
			System.err.println(pcap.getErr());
			return;
		}

		if (pcap.setFilter(filter) != Pcap.OK) {
			System.err.println(pcap.getErr());
			return;
		}

		PcapPacketHandler<String> jpacketHandler = new PcapPacketHandler<String>() {

			public void nextPacket(PcapPacket packet, String user) {

				Tcp tcp = new Tcp();

				if (packet.hasHeader(tcp) == false) {
					return; // Not IP packet
				}

				if (0 == tcp.getPayloadLength())
					return;

				packet.getHeader(tcp);
				System.out.print("payload length = " + tcp.getPayloadLength());

				Ip4 ip = new Ip4();
				packet.getHeader(ip);

				String srcAddr = org.jnetpcap.packet.format.FormatUtils.ip(ip.source());
				String destinationIP = org.jnetpcap.packet.format.FormatUtils.ip(ip.destination());
				evilHost = srcAddr;
				int qos = MQTTPacket.getQos();
				int connect = MQTTPacket.getConnect();
				int connack = MQTTPacket.getConnack();
				int pubrec = MQTTPacket.getPubrec();
				int publish = MQTTPacket.getPublish();
				int pubrel = MQTTPacket.getPubrel();
				int messageType = MQTTPacket.getMessageType();

				cepLocal.sendEvent(new MQTTPacketSender(connect, connack, publish, pubrel, pubrec, messageType, qos,
						evilHost, srcAddr));

				System.out.println("----------------------------------");
				System.out.println("source ip = " + srcAddr + " ---> " + " destination ip = " + destinationIP);

				byte[] payload = tcp.getPayload();
				System.out.println(new MQTTPacket(payload).toString());

			}
		};
		pcap.loop(Pcap.LOOP_INFINITE, jpacketHandler, "Beholder!");
		// pcap.close();
	}
}
