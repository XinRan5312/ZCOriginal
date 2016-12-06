package net.zkbc.p2p.fep.message.protocol;

/**
 * 删除订单.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitDeleteOrderRequest extends RequestSupport {

	private String orderId;

	public SubmitDeleteOrderRequest() {
		super();
		setMessageId("submitDeleteOrder");
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