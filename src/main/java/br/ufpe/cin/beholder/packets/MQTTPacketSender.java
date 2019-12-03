package br.ufpe.cin.beholder.packets;

public class MQTTPacketSender {

	private int connect;
	private int connack;
	private int publish;
	private int pubrel;
	private int pubrec;
	private String evilHost;
	private String srcAddr;
	/*
	 * private int PUBLISH; private int PUBACK; private int PUBREC; private int
	 * PUBREL; private int PUBCOMP; private int SUBSCRIBE; private int SUBACK;
	 * private int UNSUBSCRIBE; private int UNSUBACK; private int PINGREQ; private
	 * int PINGRESP; private int DISCONNECT;
	 */

	private int messageType;
	private int dup;
	private int qos;
	private int retain;

	public MQTTPacketSender(int connect, int connack, int publish, int pubrel, int pubrec, int messageType, int qos, String evilHost, String srcAddr) {
		this.connect = connect;
		this.connack = connack;
		this.publish = publish;
		this.pubrel = pubrel;
		this.pubrec = pubrec;
		this.messageType = messageType;
		this.qos = qos;
		this.evilHost = evilHost;
		this.srcAddr = srcAddr;

	}

	public int getConnect() {
		return connect;
	}

	public int getConnack() {
		return connack;
	}

	public int getPubrec() {
		return pubrec;
	}

	public int getPublish() {
		return publish;
	}

	public int getPubrel() {
		return pubrel;
	}

	/*
	 * public int getPublish() { return PUBLISH; }
	 * 
	 * public int getPuback() { return PUBACK; }
	 * 
	 * public int getPubrec() { return PUBREC; }
	 * 
	 * public int getPubrel() { return PUBREL; }
	 * 
	 * public int getPubcomp() { return PUBCOMP; }
	 * 
	 * public int getSubscribe() { return SUBSCRIBE; }
	 * 
	 * public int getSuback() { return SUBACK; }
	 * 
	 * public int getUnsubscribe() { return UNSUBSCRIBE; }
	 * 
	 * public int getUnsuback() { return UNSUBACK; }
	 * 
	 * public int getPingreq() { return PINGREQ; }
	 * 
	 * public int getPingresp() { return PINGRESP; }
	 * 
	 * public int getDisconnect() { return DISCONNECT; }
	 */
	public int getMessageType() {
		return messageType;
	}

	public int getDup() {
		return dup;
	}

	public int getQos() {
		return qos;
	}

	public int getRetain() {
		return retain;
	}

	public String getEvilHost() {
		return evilHost;
	}

	public String getSrcAddr() {
		return srcAddr;
	}

}
