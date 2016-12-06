package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取用户未读信息条数.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUnReadMessageCountResponse extends ResponseSupport {

	private Integer count;

	public GetUnReadMessageCountResponse() {
		super();
		setMessageId("getUnReadMessageCount");
	}


	/**
	 * @return 未读信息条数
	 */
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}