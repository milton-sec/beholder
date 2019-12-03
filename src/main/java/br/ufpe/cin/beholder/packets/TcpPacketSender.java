package br.ufpe.cin.beholder.packets;

public class TcpPacketSender {

	private String srcAddr;
	private String dstAddr;
	// private int packetLength;
	private boolean syn;
	private boolean ack;
	private boolean fin;
	private boolean psh;
	private boolean urg;
	private boolean rst;
	private int synCount;
	private String evilHost;
	private int packetLenght;
	private int seq;
	// private int CONNECT;
	private String data;

	// TcpPacket tcpPacket;

	public TcpPacketSender(String srcAddr, String dstAddr, boolean syn, boolean ack, boolean fin, boolean psh,
			boolean urg, boolean rst, int synCount, String evilHost, int packetLenght, int seq, String data) {
		this.srcAddr = srcAddr;
		this.dstAddr = dstAddr;
		this.syn = syn;
		this.ack = ack;
		this.synCount = synCount;
		this.evilHost = evilHost;
		this.packetLenght = packetLenght;
		this.syn = syn;
		this.ack = ack;
		this.seq = seq;
		this.data = data;
	}

	public TcpPacketSender(String evilHost) {
		this.evilHost = evilHost;
	}

	public TcpPacketSender() {

	}

	public String getSrcAddr() {
		return srcAddr;
	}

	public String getDstAddr() {
		return dstAddr;
	}

	public void setSrcAddr(String srcAddr) {
		this.srcAddr = srcAddr;
	}

	public void setDstAddr(String dstAddr) {
		this.dstAddr = dstAddr;
	}

	public boolean isSyn() {
		return syn;
	}

	public boolean isAck() {
		return ack;
	}

	public int getSynCount() {
		return synCount;
	}

	public String getEvilHost() {
		return evilHost;
	}

	public void setEvilHost(String evilHost) {
		this.evilHost = evilHost;
	}

	public int getPacketLenght() {
		return packetLenght;
	}

	public void setPacketLenght(int packetLenght) {
		this.packetLenght = packetLenght;
	}

	public boolean isFin() {
		return fin;
	}

	public boolean isPsh() {
		return psh;
	}

	public boolean isUrg() {
		return urg;
	}

	public boolean isRst() {
		return rst;
	}

	public int getSeq() {
		return seq;
	}

	public String getData() {
		return data;
	}

}
