package com.minjinsuo.zhongchou.model;

public class Model_Login {

	// {"statusCode":0,"messageId":"login","sessionId":"ee76c474-8759-4169-bd63-c7d50527e8e8"}
	private int statusCode;
	private String statusMessage;
	private String sessionId;

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

}
