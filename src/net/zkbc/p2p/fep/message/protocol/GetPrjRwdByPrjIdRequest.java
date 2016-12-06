package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取项目回报.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjRwdByPrjIdRequest extends RequestSupport {

	private String prjId;

	public GetPrjRwdByPrjIdRequest() {
		super();
		setMessageId("getPrjRwdByPrjId");
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