package br.ufpe.cin.beholder.application;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

//import br.ufpe.cin.beholder.messages.LandListener;
import br.ufpe.cin.beholder.messages.QosFloodListener;
import br.ufpe.cin.beholder.packets.MQTTPacketSender;
//import br.ufpe.cin.beholder.messages.LandListener;
//import br.ufpe.cin.beholder.messages.PortScanListener;
//import br.ufpe.cin.beholder.messages.PortScanListener;
//import br.ufpe.cin.beholder.messages.SynFloodListener;
//import br.ufpe.cin.beholder.packets.TcpPacketSender;
import br.ufpe.cin.beholder.streams.MQTTStreams;
//import br.ufpe.cin.beholder.streams.TcpStreams;

public class MainCEP {

	public static void main(String[] args) {
		try {
			Configuration config = new Configuration();

			//config.addEventType("TCP_Stream", TcpPacketSender.class);
			//EPServiceProvider tcpCep = EPServiceProviderManager.getProvider("esper-tcp", config);
			config.addEventType("MQTT_Stream", MQTTPacketSender.class);
			EPServiceProvider mqttCep = EPServiceProviderManager.getProvider("esper-mqtt", config);
			
			//******************QoS Flood Attack!********************* 
			EPStatement qosFloodStatement = mqttCep.getEPAdministrator().createEPL("INSERT INTO evilhost "
					+ "SELECT srcAddr FROM MQTT_Stream.win:time(1 sec) "
					+ "HAVING COUNT (messageType = publish) > 100 AND qos = 2");
			qosFloodStatement.addListener(new QosFloodListener());

			/*
			 * ******************SYN Flood Attacks!********************* 
			 * EPStatement
			 * synFloodStatement = tcpCep.getEPAdministrator().createEPL( "INSERT INTO
			 * evilhost SELECT srcAddr FROM TCP_Stream.win:time(1 sec) HAVING COUNT (syn) >
			 * 100 AND NOT (ack)"); synFloodStatement.addListener(new SynFloodListener());
			 */

			// ******************Land Attack!***************************
			//EPStatement landAttackStatement = tcpCep.getEPAdministrator()
			//		.createEPL("INSERT INTO evilhost SELECT srcAddr FROM TCP_Stream WHERE data like '%58 58 58 58 58 58 58 58 58 58 58%'");
			//landAttackStatement.addListener(new LandListener());

			// ******************Portscan NMAP FIN Scan********************************
			 //EPStatement portScanStatement = tcpCep.getEPAdministrator().createEPL("INSERT INTO evilhost SELECT srcAddr FROM "
			 	//	+ "TCP_Stream WHERE NOT(syn) AND NOT (rst) AND NOT (ack) AND NOT (psh) AND NOT (urg)"); //--> Flags FPU
			 //portScanStatement.addListener(new PortScanListener());

			new MQTTStreams(mqttCep.getEPRuntime()).start();
			System.out.println("*******************************Starting IDSoT**********************************");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
