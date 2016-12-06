package com.minjinsuo.zhongchou.model;

public class QueryMoney {
	private String xmlStr;

	private boolean valid;

	private String notify;

	private String sign;

	private String platformNo;

	private String code;

	private String service;

	private String description;

	private String requestNo;

	private String memberType;

	private String activeStatus;

	private String balance;

	private String availableAmount;

	private String freezeAmount;

	private String cardNo;

	private String cardStatus;

	private String bank;

	private String autoTender;

	private String errorMsg;

	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}

	public String getXmlStr() {
		return this.xmlStr;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean getValid() {
		return this.valid;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public String getNotify() {
		return this.notify;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign() {
		return this.sign;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getPlatformNo() {
		return this.platformNo;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getService() {
		return this.service;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getRequestNo() {
		return this.requestNo;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getMemberType() {
		return this.memberType;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getActiveStatus() {
		return this.activeStatus;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @return 总额
	 */
	public String getBalance() {
		return this.balance;
	}

	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}

	/**
	 * @return 可用余额
	 */
	public String getAvailableAmount() {
		return this.availableAmount;
	}

	public void setFreezeAmount(String freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	public String getFreezeAmount() {
		return this.freezeAmount;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCardStatus() {
		return this.cardStatus;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBank() {
		return this.bank;
	}

	public void setAutoTender(String autoTender) {
		this.autoTender = autoTender;
	}

	public String getAutoTender() {
		return this.autoTender;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
