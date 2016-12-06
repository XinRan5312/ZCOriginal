package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据项目id获取项目进展.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjProgressListRequest extends RequestSupport {

	private String prjId;

	public GetPrjProgressListRequest() {
		super();
		setMessageId("getPrjProgressList");
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