package net.zkbc.p2p.fep.message.protocol;

/**
 * 当前登录用户获取项目分页信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjPageListForUserRequest extends RequestSupport {

	private String custId;
	private String orderBy;
	private String prodId;
	private String searchtype;
	private String sort;
	private String status;
	private Integer pageNo;
	private Integer pageSize;

	public GetPrjPageListForUserRequest() {
		super();
		setMessageId("getPrjPageListForUser");
	}	

	/**
	 * @return 用户id（不传默认当前登录用户）
	 */
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return 排序字段(不传默认id)
	 */
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return 产品表示     0 股权众筹
            1 产品众筹
	 */
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return 00-发起  10-跟投  20-关注 30-领投 40-预约
	 */
	public String getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}

	/**
	 * @return 排序 除头像地址 其余字段键名与数据库字段名一致(不传默认desc)
	 */
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return 项目状态 待预热-10,预热中-20,待筹款-30,预热流标-35,筹款中-40,筹款结束-45,筹款满标-50,筹款流标-55,项目成功-60,成功结项-70,失败结项-80
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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