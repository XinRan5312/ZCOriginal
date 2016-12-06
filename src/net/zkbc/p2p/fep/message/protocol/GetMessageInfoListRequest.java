package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取用户站内信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMessageInfoListRequest extends RequestSupport {

	private Integer pageno;
	private Integer pagesize;

	public GetMessageInfoListRequest() {
		super();
		setMessageId("getMessageInfoList");
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