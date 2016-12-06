package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取物流信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetOrderLogiInfoRequest extends RequestSupport {

	private String orderId;

	public GetOrderLogiInfoRequest() {
		super();
		setMessageId("getOrderLogiInfo");
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