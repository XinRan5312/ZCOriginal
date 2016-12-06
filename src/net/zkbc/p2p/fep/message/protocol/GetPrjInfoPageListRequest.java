package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取项目分页信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjInfoPageListRequest extends RequestSupport {

	private String codPrjTrade;
	private String keywords;
	private String orderBy;
	private String prodId;
	private String sort;
	private String status;
	private Integer pageNo;
	private Integer pageSize;

	public GetPrjInfoPageListRequest() {
		super();
		setMessageId("getPrjInfoPageList");
	}	

	/**
	 * @return 项目行业   字典信息接口中value字段值
	 */
	public String getCodPrjTrade() {
		return codPrjTrade;
	}

	public void setCodPrjTrade(String codPrjTrade) {
		this.codPrjTrade = codPrjTrade;
	}

	/**
	 * @return 项目关键字搜索
	 */
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return 排序字段
	 */
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return 产品表示     0 股权众筹  1 产品众筹 2 公益众筹
	 */
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return 排序 除头像地址 其余字段键名与数据库字段名一致
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