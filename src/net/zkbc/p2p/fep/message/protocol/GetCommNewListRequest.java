package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取相应信息（常见问题）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetCommNewListRequest extends RequestSupport {

	private String newType;
	private int pageSize;
	private int pageNo;

	public GetCommNewListRequest() {
		super();
		setMessageId("getCommNewList");
	}

	/**
	 * @return 所属信息类别 HelpCenter-帮助中心
	 */
	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
}