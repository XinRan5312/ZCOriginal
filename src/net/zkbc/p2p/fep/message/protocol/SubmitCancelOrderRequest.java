package net.zkbc.p2p.fep.message.protocol;

/**
 * 取消订单.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitCancelOrderRequest extends RequestSupport {

	private String orderId;

	public SubmitCancelOrderRequest() {
		super();
		setMessageId("submitCancelOrder");
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