package net.zkbc.p2p.fep.message.protocol;

/**
 * 查询投资记录.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchInvestListByLoanidRequest extends RequestSupport {

	private Integer loanid;

	public SearchInvestListByLoanidRequest() {
		super();
		setMessageId("searchInvestListByLoanid");
	}	

	/**
	 * @return 借款唯一标识
	 */
	public Integer getLoanid() {
		return loanid;
	}

	public void setLoanid(Integer loanid) {
		this.loanid = loanid;
	}
}