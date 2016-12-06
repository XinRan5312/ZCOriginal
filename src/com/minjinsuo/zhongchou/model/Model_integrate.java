package com.minjinsuo.zhongchou.model;

public class Model_integrate {
     private String type;
     private String time;
     private String amount;
     private String status;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Model_integrate() {
		super();
	}
	public Model_integrate(String type, String time, String amount,
			String status) {
		super();
		this.type = type;
		this.time = time;
		this.amount = amount;
		this.status = status;
	}
     
}
