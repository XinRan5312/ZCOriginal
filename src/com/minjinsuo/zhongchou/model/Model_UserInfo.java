package com.minjinsuo.zhongchou.model;

/**
 * 用户信息
 */
public class Model_UserInfo {

	// {"messageId":"getMyAccount","sessionId":"e63e06b1-20df-4450-8138-18aa3bd149c4","statusCode":0,
	// "balamount":528,"borrowamount":0,"investamount":0,"roles":0,"accountbankname":"ICBC",
	// "actualloanamount":"0.00","authstat":"已认证","bankid":"ICBC","cardno":"622262*********7654",
	// "frozenamount":"0.00","idcardnumber":"211223197710313421","interest":"0.00","investCount":"0",
	// "isblackname":"1","isrealname":"1","loginname":"z001","loginUserId":"527016","maxCash":"10000",
	// "minCash":"1","moldId":"1","payaccountname":"易宝支付","payaccountstat":"已注册",
	// "phonenumber":"13520890001","principal":"0.00","realname":"平梦桃","repayamount":"0.00",
	// "tocollectredmoneyinterest":"0.00","totalassertnew":"0.00","totaltointerest":"0.00",
	// "usableCashCount":"2","websitename":"http:\/\/124.205.230.50:9105"}

	private int statusCode;
	private double borrowamount;
	private double investamount;
	private double balamount;
	private String totaltointerest;
	private String loginUserId;
	private String investCount;
	private String frozenamount;
	private String maxCash;
	private String loginname;
	private String principal;
	private String interest;
	private String authstat;
	private String usableCashCount;
	private String moldId;
	private String phonenumber;
	private String payaccountname;
	private String isrealname;
	private String totalassertnew;
	private String isblackname;
	private String actualloanamount;
	private String sessionId;
	private String minCash;
	private int roles;
	private String tocollectredmoneyinterest;
	private String repayamount;
	private String websitename;
	private String payaccountstat;
	private String accountbankname;
	private String bankid;
	private String idcardnumber;
	private String realname;
	private String email;
	private String loginPwd;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountbankname() {
		return accountbankname;
	}

	public void setAccountbankname(String accountbankname) {
		this.accountbankname = accountbankname;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getIdcardnumber() {
		return idcardnumber;
	}

	public void setIdcardnumber(String idcardnumber) {
		this.idcardnumber = idcardnumber;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String cardno;// 卡号

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public double getBorrowamount() {
		return borrowamount;
	}

	public void setBorrowamount(double borrowamount) {
		this.borrowamount = borrowamount;
	}

	public double getInvestamount() {
		return investamount;
	}

	public void setInvestamount(double investamount) {
		this.investamount = investamount;
	}

	public double getBalamount() {
		return balamount;
	}

	public void setBalamount(double balamount) {
		this.balamount = balamount;
	}

	public String getTotaltointerest() {
		return totaltointerest;
	}

	public void setTotaltointerest(String totaltointerest) {
		this.totaltointerest = totaltointerest;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public String getInvestCount() {
		return investCount;
	}

	public void setInvestCount(String investCount) {
		this.investCount = investCount;
	}

	public String getFrozenamount() {
		return frozenamount;
	}

	public void setFrozenamount(String frozenamount) {
		this.frozenamount = frozenamount;
	}

	public String getMaxCash() {
		return maxCash;
	}

	public void setMaxCash(String maxCash) {
		this.maxCash = maxCash;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getAuthstat() {
		return authstat;
	}

	public void setAuthstat(String authstat) {
		this.authstat = authstat;
	}

	public String getUsableCashCount() {
		return usableCashCount;
	}

	public void setUsableCashCount(String usableCashCount) {
		this.usableCashCount = usableCashCount;
	}

	public String getMoldId() {
		return moldId;
	}

	public void setMoldId(String moldId) {
		this.moldId = moldId;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPayaccountname() {
		return payaccountname;
	}

	public void setPayaccountname(String payaccountname) {
		this.payaccountname = payaccountname;
	}

	public String getIsrealname() {
		return isrealname;
	}

	public void setIsrealname(String isrealname) {
		this.isrealname = isrealname;
	}

	public String getTotalassertnew() {
		return totalassertnew;
	}

	public void setTotalassertnew(String totalassertnew) {
		this.totalassertnew = totalassertnew;
	}

	public String getIsblackname() {
		return isblackname;
	}

	public void setIsblackname(String isblackname) {
		this.isblackname = isblackname;
	}

	/**
	 * @return 实到借款资金
	 */
	public String getActualloanamount() {
		return actualloanamount;
	}

	public void setActualloanamount(String actualloanamount) {
		this.actualloanamount = actualloanamount;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getMinCash() {
		return minCash;
	}

	public void setMinCash(String minCash) {
		this.minCash = minCash;
	}

	/**
	 * 角色:1-投资者,2-借款者,3-企业用户
	 * 
	 * @return
	 */
	public int getRoles() {
		return roles;
	}

	public void setRoles(int roles) {
		this.roles = roles;
	}

	public String getTocollectredmoneyinterest() {
		return tocollectredmoneyinterest;
	}

	public void setTocollectredmoneyinterest(String tocollectredmoneyinterest) {
		this.tocollectredmoneyinterest = tocollectredmoneyinterest;
	}

	public String getRepayamount() {
		return repayamount;
	}

	public void setRepayamount(String repayamount) {
		this.repayamount = repayamount;
	}

	public String getWebsitename() {
		return websitename;
	}

	public void setWebsitename(String websitename) {
		this.websitename = websitename;
	}

	public String getPayaccountstat() {
		return payaccountstat;
	}

	public void setPayaccountstat(String payaccountstat) {
		this.payaccountstat = payaccountstat;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

}
