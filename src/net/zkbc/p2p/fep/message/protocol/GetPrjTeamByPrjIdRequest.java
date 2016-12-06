package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取团队信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjTeamByPrjIdRequest extends RequestSupport {

	private String prjId;

	public GetPrjTeamByPrjIdRequest() {
		super();
		setMessageId("getPrjTeamByPrjId");
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