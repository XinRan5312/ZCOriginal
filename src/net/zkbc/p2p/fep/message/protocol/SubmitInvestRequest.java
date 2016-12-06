package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交投资信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitInvestRequest extends RequestSupport {

	private String loanid;
	private String redmoneyid;
	private Double investamount;

	public SubmitInvestRequest() {
		super();
		setMessageId("submitInvest");
	}	

	/**
	 * @return 借款标识
	 */
	public String getLoanid() {
		return loanid;
	}

	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}

	/**
	 * @return 红包id
	 */
	public String getRedmoneyid() {
		return redmoneyid;
	}

	public void setRedmoneyid(String redmoneyid) {
		this.redmoneyid = redmoneyid;
	}

	/**
	 * @return 投资金额
	 */
	public Double getInvestamount() {
		return investamount;
	}

	public void setInvestamount(Double investamount) {
		this.investamount = investamount;
	}
}