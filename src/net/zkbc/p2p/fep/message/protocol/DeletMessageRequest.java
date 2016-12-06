package net.zkbc.p2p.fep.message.protocol;

/**
 * 请求删除用户消息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class DeletMessageRequest extends RequestSupport {

	private String id;

	public DeletMessageRequest() {
		super();
		setMessageId("deletMessage");
	}	

	/**
	 * @return 消息id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}