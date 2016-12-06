package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据预约订单id取消预约订单.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitCancelPreOrderRequest extends RequestSupport {

	private String orderId;

	public SubmitCancelPreOrderRequest() {
		super();
		setMessageId("submitCancelPreOrder");
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