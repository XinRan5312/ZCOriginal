package net.zkbc.p2p.fep.message.protocol;

/**
 * 查询还款记录.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchLoanphaseListRequest extends RequestSupport {

	private String loanid;

	public SearchLoanphaseListRequest() {
		super();
		setMessageId("searchLoanphaseList");
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