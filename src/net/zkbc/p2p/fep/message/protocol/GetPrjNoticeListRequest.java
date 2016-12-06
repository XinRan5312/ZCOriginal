package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据项目id获取项目公告.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjNoticeListRequest extends RequestSupport {

	private String prjId;

	public GetPrjNoticeListRequest() {
		super();
		setMessageId("getPrjNoticeList");
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