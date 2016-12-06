package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取借款人信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetBorrowerRequest extends RequestSupport {

	private String loanid;
	private Integer borrowid;

	public GetBorrowerRequest() {
		super();
		setMessageId("getBorrower");
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
	 * @return 借款人标识
	 */
	public Integer getBorrowid() {
		return borrowid;
	}

	public void setBorrowid(Integer borrowid) {
		this.borrowid = borrowid;
	}
}