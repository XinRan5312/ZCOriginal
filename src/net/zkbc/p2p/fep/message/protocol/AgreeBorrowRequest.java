package net.zkbc.p2p.fep.message.protocol;

/**
 * 待签约同意.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class AgreeBorrowRequest extends RequestSupport {

	private String loanid;

	public AgreeBorrowRequest() {
		super();
		setMessageId("agreeBorrow");
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
}