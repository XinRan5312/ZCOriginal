package net.zkbc.p2p.fep.message.protocol;

/**
 * 查询我的散标投资列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchMyInvestListRequest extends RequestSupport {

	private String searchtype;
	private Integer pageno;
	private Integer pagesize;

	public SearchMyInvestListRequest() {
		super();
		setMessageId("searchMyInvestList");
	}	

	/**
	 * @return 查询类型:0-全部,1-筹款中,2-回款中,3-已完成
	 */
	public String getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}

	/**
	 * @return 当前页号
	 */
	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	/**
	 * @return 每页记录数
	 */
	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
}