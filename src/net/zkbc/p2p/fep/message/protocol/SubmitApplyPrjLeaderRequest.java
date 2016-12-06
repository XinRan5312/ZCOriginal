package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交项目领投人申请.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitApplyPrjLeaderRequest extends RequestSupport {

	private String prjId;

	public SubmitApplyPrjLeaderRequest() {
		super();
		setMessageId("submitApplyPrjLeader");
	}	

	/**
	 * @return 项目id
	 */
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
}