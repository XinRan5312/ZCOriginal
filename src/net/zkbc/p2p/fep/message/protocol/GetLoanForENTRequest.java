package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取企业借款详情.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetLoanForENTRequest extends RequestSupport {

	private Integer loanid;

	public GetLoanForENTRequest() {
		super();
		setMessageId("getLoanForENT");
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