package br.ufpe.cin.beholder.packets;

import org.pcap4j.packet.Packet;

import com.sun.xml.internal.ws.commons.xmlutil.Converter;

public class MQTTPacket {

	private static final int CONNECT = 1;
	private static final int CONNACK = 2;
	private static final int PUBLISH = 3;
	private static final int PUBACK = 4;
	private static final int PUBREC = 5;
	private static final int PUBREL = 6;
	private static final int PUBCOMP = 7;
	private static final int SUBSCRIBE = 8;
	private static final int SUBACK = 9;
	private static final int UNSUBSCRIBE = 10;
	private static final int UNSUBACK = 11;
	private static final int PINGREQ = 12;
	private static final int PINGRESP = 13;
	private static final int DISCONNECT = 14;

	static int messageType;
	int dup;
	static int qos;
	int retain;

	@SuppressWarnings("unused")
	public MQTTPacket() {
	}

	public MQTTPacket(byte[] payload) {
		if (payload.length < 1)
			return;

		messageType = (0x000000FF) & (((0x000000F0) & payload[0]) >> 4);
		dup = (0x00000001) & (payload[0] >> 3);
		qos = (0x00000003) & (payload[0] >> 1);
		retain = (0x00000001) & (payload[0]);
	}

	public static int getConnect() {
		return CONNECT;
	}

	public static int getConnack() {
		return CONNACK;
	}

	public static int getPublish() {
		return PUBLISH;
	}

	public static int getPuback() {
		return PUBACK;
	}

	public static int getPubrec() {
		return PUBREC;
	}

	public static int getPubrel() {
		return PUBREL;
	}

	public static int getPubcomp() {
		return PUBCOMP;
	}

	public static int getSubscribe() {
		return SUBSCRIBE;
	}

	public static int getSuback() {
		return SUBACK;
	}

	public static int getUnsubscribe() {
		return UNSUBSCRIBE;
	}

	public static int getUnsuback() {
		return UNSUBACK;
	}

	public static int getPingreq() {
		return PINGREQ;
	}

	public static int getPingresp() {
		return PINGRESP;
	}

	public static int getDisconnect() {
		return DISCONNECT;
	}

	public static int getMessageType() {
		return messageType;
	}

	public int getDup() {
		return dup;
	}

	public static int getQos() {
		return qos;
	}

	public int getRetain() {
		return retain;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public void setDup(int dup) {
		this.dup = dup;
	}

	public void setQos(int qos) {
		MQTTPacket.qos = qos;
	}

	public void setRetain(int retain) {
		this.retain = retain;
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("MQTT Header:\n");
		sb.append("messageType: ");

		switch (messageType) {

		case CONNECT:
			sb.append("CONNECT").append("\n");
			break;

		case CONNACK:
			sb.append("CONNACK").append("\n");
			break;

		case PUBLISH:
			sb.append("PUBLISH").append("\n");
			break;

		case PUBACK:
			sb.append("PUBACK").append("\n");
			break;

		case PUBREC:
			sb.append("PUBREC").append("\n");
			break;

		case PUBREL:
			sb.append("PUBREL").append("\n");
			break;

		case PUBCOMP:
			sb.append("PUBCOMP").append("\n");
			break;

		case SUBSCRIBE:
			sb.append("SUBSCRIBE").append("\n");
			break;

		case SUBACK:
			sb.append("SUBACK").append("\n");
			break;

		case UNSUBSCRIBE:
			sb.append("UNSUBSCRIBE").append("\n");
			break;

		case UNSUBACK:
			sb.append("UNSUBACK").append("\n");
			break;

		case PINGREQ:
			sb.append("PINGREQ").append("\n");
			break;

		case PINGRESP:
			sb.append("PINGRESP").append("\n");
			break;

		case DISCONNECT:
			sb.append("DISCONNECT").append("\n");
			break;
		}

		sb.append("dup: " + dup).append("\n");
		sb.append("qos: " + qos).append("\n");
		sb.append("retain: " + retain).append("\n");
		return sb.toString();
	}

}
