package net.zkbc.p2p.fep.message.protocol;

/**
 * 查询债权列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchDebtListRequest extends RequestSupport {

	private String searchname;
	private String searchtype;
	private Integer pageno;
	private Integer pagesize;

	public SearchDebtListRequest() {
		super();
		setMessageId("searchDebtList");
	}	

	/**
	 * @return 排序类型：0-loanId 20-金额 30-期限 40-利率
	 */
	public String getSearchname() {
		return searchname;
	}

	public void setSearchname(String searchname) {
		this.searchname = searchname;
	}

	/**
	 * @return 查询类型：0-降序,1-升序
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