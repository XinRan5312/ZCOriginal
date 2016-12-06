package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取话题回复列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetCommonTalkRepayListRequest extends RequestSupport {

	private Integer prjId;

	public GetCommonTalkRepayListRequest() {
		super();
		setMessageId("getCommonTalkRepayList");
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