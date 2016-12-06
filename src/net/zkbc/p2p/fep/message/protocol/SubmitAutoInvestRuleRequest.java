package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交自动投标规则.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitAutoInvestRuleRequest extends RequestSupport {

	private String amount;
	private String interestratemax;
	private String interestratemin;
	private String reserveamount;
	private String termmax;
	private String termmin;

	public SubmitAutoInvestRuleRequest() {
		super();
		setMessageId("submitAutoInvestRule");
	}	

	/**
	 * @return 每次投标金额
	 */
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return 利率最大值
	 */
	public String getInterestratemax() {
		return interestratemax;
	}

	public void setInterestratemax(String interestratemax) {
		this.interestratemax = interestratemax;
	}

	/**
	 * @return 利率最小值
	 */
	public String getInterestratemin() {
		return interestratemin;
	}

	public void setInterestratemin(String interestratemin) {
		this.interestratemin = interestratemin;
	}

	/**
	 * @return 账户保留金额
	 */
	public String getReserveamount() {
		return reserveamount;
	}

	public void setReserveamount(String reserveamount) {
		this.reserveamount = reserveamount;
	}

	/**
	 * @return 期限最大值
	 */
	public String getTermmax() {
		return termmax;
	}

	public void setTermmax(String termmax) {
		this.termmax = termmax;
	}

	/**
	 * @return 期限最小值
	 */
	public String getTermmin() {
		return termmin;
	}

	public void setTermmin(String termmin) {
		this.termmin = termmin;
	}
}