package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取站内信详细信息并设置未读为已读.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetAndReadInnerMailByIdRequest extends RequestSupport {

	private String id;

	public GetAndReadInnerMailByIdRequest() {
		super();
		setMessageId("getAndReadInnerMailById");
	}	

	/**
	 * @return 站内信id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}