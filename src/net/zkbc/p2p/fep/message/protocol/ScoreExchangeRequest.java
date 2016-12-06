package net.zkbc.p2p.fep.message.protocol;

/**
 * 商品兑换.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ScoreExchangeRequest extends RequestSupport {

	private String id;

	public ScoreExchangeRequest() {
		super();
		setMessageId("scoreExchange");
	}	

	/**
	 * @return 商品id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}