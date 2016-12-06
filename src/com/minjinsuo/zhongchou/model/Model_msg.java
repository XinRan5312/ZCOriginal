package com.minjinsuo.zhongchou.model;

public class Model_msg {
	private String type;
	private String id;
	private String time;
	private String tatus;
	// 详情
	private String detail;
	private String sender;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTatus() {
		return tatus;
	}

	public void setTatus(String tatus) {
		this.tatus = tatus;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Model_msg(String id, String type, String time, String tatus,
			String detail,String sender) {
		super();
		this.id = id;
		this.type = type;
		this.time = time;
		this.tatus = tatus;
		this.detail = detail;
		this.sender = sender;
	}

	public Model_msg() {
		super();
	}

}
