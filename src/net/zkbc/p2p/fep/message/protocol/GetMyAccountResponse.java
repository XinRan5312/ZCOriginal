package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取我的账户信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyAccountResponse extends ResponseSupport {

	private String sessionId;
	private String accountbankname;
	private String actualloanamount;
	private String authstat;
	private String balamount;
	private String availableAmt;
	private String bankid;
	private String bankstat;
	private String cardno;
	private String email;
	private String frozenamount;
	private String frozenBiddingCash;
	private String frozenWithDrawCash;
	private String idcardnumber;
	private String interest;
	private String ishaveborrow;
	private String isLeadOrFollow;
	private String isrealname;
	private String loginname;
	private String minCash;
	private String orderCount;
	private String payaccountname;
	private String payaccountstat;
	private String phonenumber;
	private String portrait;
	private String principal;
	private String qqUId;
	private String realname;
	private String recCount;
	private String repayamount;
	private String websitename;
	private Integer idVerifyLimit;
	private Integer loginUserId;
	private Integer roles;
	private Double borrowamount;
	private Double investamount;
	private String maxCash;
	private String usableCashCount;
	private String identity;
	private String userType;

	public GetMyAccountResponse() {
		super();
		setMessageId("getMyAccount");
	}

	/**
	 * @return:可用余额
	 */
	public String getAvailableAmt() {
		return availableAmt;
	}

	/**
	 * @param availableAmt
	 *            :可用余额
	 */
	public void setAvailableAmt(String availableAmt) {
		this.availableAmt = availableAmt;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 用户类型 1个人客户 2 企业客户
	 * 
	 * @return
	 */
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return 开户行名称
	 */
	public String getAccountbankname() {
		return accountbankname;
	}

	public void setAccountbankname(String accountbankname) {
		this.accountbankname = accountbankname;
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

	/**
	 * @return 实名认证状态：未认证,审核中,已认证
	 */
	public String getAuthstat() {
		return authstat;
	}

	public void setAuthstat(String authstat) {
		this.authstat = authstat;
	}

	/**
	 * @return 账户余额(总额)
	 */
	public String getBalamount() {
		return balamount;
	}

	/**
	 * @param balamount：总额
	 */
	public void setBalamount(String balamount) {
		this.balamount = balamount;
	}

	/**
	 * @return 开户行id
	 */
	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	/**
	 * @return 卡号
	 */
	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	/**
	 * @return 邮件
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 冻结金额
	 */
	public String getFrozenamount() {
		return frozenamount;
	}

	public void setFrozenamount(String frozenamount) {
		this.frozenamount = frozenamount;
	}

	/**
	 * @return 已冻结的投标中现金
	 */
	public String getFrozenBiddingCash() {
		return frozenBiddingCash;
	}

	public void setFrozenBiddingCash(String frozenBiddingCash) {
		this.frozenBiddingCash = frozenBiddingCash;
	}

	/**
	 * @return 已冻结的提现中现金
	 */
	public String getFrozenWithDrawCash() {
		return frozenWithDrawCash;
	}

	public void setFrozenWithDrawCash(String frozenWithDrawCash) {
		this.frozenWithDrawCash = frozenWithDrawCash;
	}

	/**
	 * @return 身份证号
	 */
	public String getIdcardnumber() {
		return idcardnumber;
	}

	public void setIdcardnumber(String idcardnumber) {
		this.idcardnumber = idcardnumber;
	}

	/**
	 * @return 待收利息
	 */
	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	/**
	 * @return 是否存在借款项没处理0-不存在可以借款1-存在不可以借款
	 */
	public String getIshaveborrow() {
		return ishaveborrow;
	}

	public void setIshaveborrow(String ishaveborrow) {
		this.ishaveborrow = ishaveborrow;
	}

	/**
	 * @return 是否平台跟投人或平台领投人
	 */
	public String getIsLeadOrFollow() {
		return isLeadOrFollow;
	}

	public void setIsLeadOrFollow(String isLeadOrFollow) {
		this.isLeadOrFollow = isLeadOrFollow;
	}

	/**
	 * @return 是否实名认证：0-未认证，1-已认证，2-审核中
	 */
	public String getIsrealname() {
		return isrealname;
	}

	public void setIsrealname(String isrealname) {
		this.isrealname = isrealname;
	}

	/**
	 * @return 用户名
	 */
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	/**
	 * @return 最小提现金额
	 */
	public String getMinCash() {
		return minCash;
	}

	public void setMinCash(String minCash) {
		this.minCash = minCash;
	}

	/**
	 * @return 订单总数
	 */
	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	/**
	 * @return 第三方支付名称
	 */
	public String getPayaccountname() {
		return payaccountname;
	}

	public void setPayaccountname(String payaccountname) {
		this.payaccountname = payaccountname;
	}

	/**
	 * @return 第三方支付账号状态：已注册,未注册
	 */
	public String getPayaccountstat() {
		return payaccountstat;
	}

	public void setPayaccountstat(String payaccountstat) {
		this.payaccountstat = payaccountstat;
	}

	/**
	 * @return 手机号码
	 */
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * @return 头像地址
	 */
	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	/**
	 * @return 待收本金
	 */
	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/**
	 * @return QQ
	 */
	public String getQqUId() {
		return qqUId;
	}

	public void setQqUId(String qqUId) {
		this.qqUId = qqUId;
	}

	/**
	 * @return 真实姓名
	 */
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	/**
	 * @return 已收货个数
	 */
	public String getRecCount() {
		return recCount;
	}

	public void setRecCount(String recCount) {
		this.recCount = recCount;
	}

	/**
	 * @return 待还金额
	 */
	public String getRepayamount() {
		return repayamount;
	}

	public void setRepayamount(String repayamount) {
		this.repayamount = repayamount;
	}

	/**
	 * @return 网站地址
	 */
	public String getWebsitename() {
		return websitename;
	}

	public void setWebsitename(String websitename) {
		this.websitename = websitename;
	}

	/**
	 * @return 国政通验证次数，每次验证减1，默认每人最多验证3次
	 */
	public Integer getIdVerifyLimit() {
		return idVerifyLimit;
	}

	public void setIdVerifyLimit(Integer idVerifyLimit) {
		this.idVerifyLimit = idVerifyLimit;
	}

	/**
	 * @return 登录用户id
	 */
	public Integer getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(Integer loginUserId) {
		this.loginUserId = loginUserId;
	}

	/**
	 * @return 角色 1 投资者 2 借款者
	 */
	public Integer getRoles() {
		return roles;
	}

	public void setRoles(Integer roles) {
		this.roles = roles;
	}

	/**
	 * @return 借款总额
	 */
	public Double getBorrowamount() {
		return borrowamount;
	}

	public void setBorrowamount(Double borrowamount) {
		this.borrowamount = borrowamount;
	}

	/**
	 * @return 投资总额
	 */
	public Double getInvestamount() {
		return investamount;
	}

	public void setInvestamount(Double investamount) {
		this.investamount = investamount;
	}

	public String getBankstat() {
		return bankstat;
	}

	public void setBankstat(String bankstat) {
		this.bankstat = bankstat;
	}

	public String getMaxCash() {
		return maxCash;
	}

	public void setMaxCash(String maxCash) {
		this.maxCash = maxCash;
	}

	public String getUsableCashCount() {
		return usableCashCount;
	}

	public void setUsableCashCount(String usableCashCount) {
		this.usableCashCount = usableCashCount;
	}

	/**
	 * @return 10-项目发起人，20-项目领投人，30-跟投人，40-领投人 多个身份以英文逗号隔开 默认为空
	 */
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

}