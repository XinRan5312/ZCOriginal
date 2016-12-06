package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据订单id查询订单信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetOrderInfoByIdRequest extends RequestSupport {

	private String id;

	public GetOrderInfoByIdRequest() {
		super();
		setMessageId("getOrderInfoById");
	}	

	/**
	 * @return 订单Id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}