package net.zkbc.p2p.fep.message.protocol;

public class RequestSupport {

	private String messageId;
	private String sessionId;

	public RequestSupport() {
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
