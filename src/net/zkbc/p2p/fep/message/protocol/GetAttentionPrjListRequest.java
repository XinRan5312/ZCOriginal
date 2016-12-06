package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取关注信息列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetAttentionPrjListRequest extends RequestSupport {

	private Integer pageNo;
	private Integer pageSize;

	public GetAttentionPrjListRequest() {
		super();
		setMessageId("getAttentionPrjList");
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
	 * @return 每页条数
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}