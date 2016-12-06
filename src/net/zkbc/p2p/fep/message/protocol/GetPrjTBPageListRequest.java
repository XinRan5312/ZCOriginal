package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取当前登录用户所有发起的项目信息（我的发起）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjTBPageListRequest extends RequestSupport {

	private Integer pageNo;
	private Integer pageSize;
	private Integer prodId;

	public GetPrjTBPageListRequest() {
		super();
		setMessageId("getPrjTBPageList");
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

	/**
	 * @return 产品表示     0 股权众筹           1 产品众筹
	 */
	public Integer getProdId() {
		return prodId;
	}

	public void setProdId(Integer prodId) {
		this.prodId = prodId;
	}
}