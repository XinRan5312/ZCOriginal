package net.zkbc.p2p.fep.message.protocol;

public class ResponseSupport {

	protected static final int SC_TIMEOUT = -200;
	protected static final int SC_FAIL = -1;

	private String messageId;
	private String sessionId;
	protected int statusCode;
	protected String statusMessage;
	// 有的接口返回statusCode==0但是还要判断result是否为success；有的接口没有result字段
	protected String result;
	protected String message;

	public ResponseSupport() {
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

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public boolean hasError() {
		return statusCode < 0;
	}

	public void fail(String msg) {
		statusCode = SC_FAIL;
		statusMessage = msg;
	}

	public void timeout() {
		statusCode = SC_TIMEOUT;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
