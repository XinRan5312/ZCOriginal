package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交企业借款申请.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitLoanForENTRequest extends RequestSupport {

	private String amount;
	private String city;
	private String idcardno;
	private String interestrate;
	private String name;
	private String phonenumber;
	private String productid;
	private String regcode;
	private String repayfrom;
	private String representative;
	private String term;
	private String termutil;
	private String use;

	public SubmitLoanForENTRequest() {
		super();
		setMessageId("submitLoanForENT");
	}	

	/**
	 * @return 借款金额
	 */
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return 所在城市
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return 身份证号
	 */
	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	/**
	 * @return 借款利率
	 */
	public String getInterestrate() {
		return interestrate;
	}

	public void setInterestrate(String interestrate) {
		this.interestrate = interestrate;
	}

	/**
	 * @return 企业名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 电话
	 */
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * @return 产品类型id
	 */
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	/**
	 * @return 注册号
	 */
	public String getRegcode() {
		return regcode;
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}

	/**
	 * @return 还款来源
	 */
	public String getRepayfrom() {
		return repayfrom;
	}

	public void setRepayfrom(String repayfrom) {
		this.repayfrom = repayfrom;
	}

	/**
	 * @return 法人
	 */
	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	/**
	 * @return 借款期限
	 */
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	/**
	 * @return 期限单位1-天3-月
	 */
	public String getTermutil() {
		return termutil;
	}

	public void setTermutil(String termutil) {
		this.termutil = termutil;
	}

	/**
	 * @return 借款用途
	 */
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}
}