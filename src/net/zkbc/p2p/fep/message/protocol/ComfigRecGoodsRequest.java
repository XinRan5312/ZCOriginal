package net.zkbc.p2p.fep.message.protocol;

/**
 * 确认收货.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ComfigRecGoodsRequest extends RequestSupport {

	private String orderId;

	public ComfigRecGoodsRequest() {
		super();
		setMessageId("comfigRecGoods");
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