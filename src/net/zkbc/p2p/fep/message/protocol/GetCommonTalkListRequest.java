package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取话题列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetCommonTalkListRequest extends RequestSupport {

	private Integer pageNo;
	private Integer prjId;
	private Integer pageSize;

	public GetCommonTalkListRequest() {
		super();
		setMessageId("getCommonTalkList");
	}	

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 页数
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
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