package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取项目动态信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjTrendsLListRequest extends RequestSupport {

	private Integer prjId;

	public GetPrjTrendsLListRequest() {
		super();
		setMessageId("getPrjTrendsLList");
	}	

	/**
	 * @return 项目id
	 */
	public Integer getPrjId() {
		return prjId;
	}

	public void setPrjId(Integer prjId) {
		this.prjId = prjId;
	}
}