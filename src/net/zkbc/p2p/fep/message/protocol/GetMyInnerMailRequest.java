package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取站内信.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyInnerMailRequest extends RequestSupport {

	private String status;
	private Integer pageNo;
	private Integer pageSize;

	public GetMyInnerMailRequest() {
		super();
		setMessageId("getMyInnerMail");
	}	

	/**
	 * @return 状态  0-未阅读   1-已阅读
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
	 * @return 每页显示条数
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}