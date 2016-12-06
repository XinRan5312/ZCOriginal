package net.zkbc.p2p.fep.message.protocol;

/**
 * 查询我的债权列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchMyDebtListRequest extends RequestSupport {

	private String searchtype;
	private Integer pageno;
	private Integer pagesize;

	public SearchMyDebtListRequest() {
		super();
		setMessageId("searchMyDebtList");
	}	

	/**
	 * @return 查询类型：0-可转让,1-转让中,20-已转让,21-已购买
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