package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取热门推荐项目.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetHotChoosePrjListRequest extends RequestSupport {

	private Integer pageNo;
	private Integer pageSize;

	public GetHotChoosePrjListRequest() {
		super();
		setMessageId("getHotChoosePrjList");
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
	 * @return 每页显示条数
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}