package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据订单id获取股权订单详情.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyStockOrderByIdRequest extends RequestSupport {

	private String orderId;

	public GetMyStockOrderByIdRequest() {
		super();
		setMessageId("getMyStockOrderById");
	}	

	/**
	 * @return 订单id
	 */
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}