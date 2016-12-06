package net.zkbc.p2p.fep.message.protocol;

/**
 * 还款.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class RepayRequest extends RequestSupport {

	private String phasenumber;
	private Integer loanid;

	public RepayRequest() {
		super();
		setMessageId("repay");
	}	

	/**
	 * @return 期数
	 */
	public String getPhasenumber() {
		return phasenumber;
	}

	public void setPhasenumber(String phasenumber) {
		this.phasenumber = phasenumber;
	}

	/**
	 * @return 借款标识
	 */
	public Integer getLoanid() {
		return loanid;
	}

	public void setLoanid(Integer loanid) {
		this.loanid = loanid;
	}
}