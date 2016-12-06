package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取企业信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjCompanyByPrjIdRequest extends RequestSupport {

	private String prjId;

	public GetPrjCompanyByPrjIdRequest() {
		super();
		setMessageId("getPrjCompanyByPrjId");
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