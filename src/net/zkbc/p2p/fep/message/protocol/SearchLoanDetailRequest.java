package net.zkbc.p2p.fep.message.protocol;

/**
 * 查询标详情.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchLoanDetailRequest extends RequestSupport {

	private Integer loanid;

	public SearchLoanDetailRequest() {
		super();
		setMessageId("searchLoanDetail");
	}	

	/**
	 * @return 标id
	 */
	public Integer getLoanid() {
		return loanid;
	}

	public void setLoanid(Integer loanid) {
		this.loanid = loanid;
	}
}