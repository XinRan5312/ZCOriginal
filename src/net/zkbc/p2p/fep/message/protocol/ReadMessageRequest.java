package net.zkbc.p2p.fep.message.protocol;

/**
 * 标记信息已经读取.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ReadMessageRequest extends RequestSupport {

	private Integer id;

	public ReadMessageRequest() {
		super();
		setMessageId("readMessage");
	}	

	/**
	 * @return 信息ID
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}