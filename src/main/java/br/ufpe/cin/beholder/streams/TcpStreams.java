package br.ufpe.cin.beholder.streams;

import java.net.Inet4Address;
//import java.net.Inet6Address;
import java.net.InetAddress;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
//import org.pcap4j.packet.UdpPacket;
//import org.pcap4j.util.ByteArrays;
import org.pcap4j.util.ByteArrays;

import com.espertech.esper.client.EPRuntime;

import br.ufpe.cin.beholder.packets.TcpPacketSender;

public class TcpStreams extends Thread {

	private EPRuntime cepLocal;
	private String evilHost;

	public TcpStreams(EPRuntime cepLocal) {
		this.cepLocal = cepLocal;
	}

	public void run() {
		int synCount = 0;

		PcapNetworkInterface nif = null;

		try {
			InetAddress addr = InetAddress.getByName("172.16.1.1");
			nif = Pcaps.getDevByAddress(addr);

		} catch (Exception e) {
			e.printStackTrace();
		}

		int snapLen = 65536;
		PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
		int timeout = 1;

		PcapHandle handle = null;
		try {
			handle = nif.openLive(snapLen, mode, timeout);
		} catch (PcapNativeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Long previousPacketTime = (long) 0;

		while (true) {
			try {

				Packet packet = handle.getNextPacketEx();
				
				//PcapPacket mqtt = handle.getNextPacket();

				IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
				// IpV6Packet ipV6Packet = packet.get(IpV6Packet.class);
				TcpPacket tcpPacket = packet.get(TcpPacket.class);
				// System.out.println(tcpPacket);
				
				//Mqtt mqtt = packet.get

				try {

					if (tcpPacket == null) {
						continue;
					}

					Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();
					Inet4Address dstAddr = ipV4Packet.getHeader().getDstAddr();
					
					//MqttPacket mqtt = packet.get(MqttPacket.class);
					

					// short length = ipV4Packet.getHeader().getTotalLength();
					// int tcpLength = tcpPacket.getHeader()length();

					boolean syn = tcpPacket.getHeader().getSyn();
					boolean ack = tcpPacket.getHeader().getAck();
					boolean fin = tcpPacket.getHeader().getFin();
					boolean urg = tcpPacket.getHeader().getRst();
					boolean psh = tcpPacket.getHeader().getPsh();
					boolean rst = tcpPacket.getHeader().getRst();
					String data = ByteArrays.toHexString(tcpPacket.getRawData(), " ");

					evilHost = srcAddr.getHostAddress();
					//int packetLenght = tcpPacket.length();
					int seq = tcpPacket.getHeader().getSequenceNumber();
					
					int packetLenght = ipV4Packet.getHeader().getTotalLengthAsInt();

					// Thread.sleep(5);
					this.cepLocal.sendEvent(new TcpPacketSender(srcAddr.toString(), dstAddr.toString(), syn, ack, fin,
							psh, urg, rst, synCount, evilHost, packetLenght, seq, data));

					//System.out.println(mqtt);
					System.out.println(srcAddr + " --> " + dstAddr);
					//System.out.println(packet);

				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					// if(handle != null)
					// handle.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// System.out.println("DIF = " + (System.currentTimeMillis() -
			// previousPacketTime));
			// previousPacketTime = System.currentTimeMillis();
		} // End while
	}

	public static void main(String[] args) {
		new TcpStreams(null).start();
	}
}
