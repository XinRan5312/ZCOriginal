package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取红包列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetRedMoneyListRequest extends RequestSupport {

	private String redtype;
	private Integer pageno;
	private Integer pagesize;

	public GetRedMoneyListRequest() {
		super();
		setMessageId("getRedMoneyList");
	}	

	/**
	 * @return 红包类型：0》全部，20》未使用，30》已使用，40》已过期，50》历史（包含30，40）
	 */
	public String getRedtype() {
		return redtype;
	}

	public void setRedtype(String redtype) {
		this.redtype = redtype;
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