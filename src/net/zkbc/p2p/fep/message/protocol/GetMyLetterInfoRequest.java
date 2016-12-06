package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取信件信件信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyLetterInfoRequest extends RequestSupport {

	private String letterTyp;
	private String status;
	private Integer pageNo;
	private Integer pageSize;

	public GetMyLetterInfoRequest() {
		super();
		setMessageId("getMyLetterInfo");
	}	

	/**
	 * @return 请求类型    10-发件箱   20-收件箱
	 */
	public String getLetterTyp() {
		return letterTyp;
	}

	public void setLetterTyp(String letterTyp) {
		this.letterTyp = letterTyp;
	}

	/**
	 * @return 状态  10-未阅读   20-已阅读
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