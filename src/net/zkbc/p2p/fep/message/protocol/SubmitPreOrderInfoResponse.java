package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交预约的信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitPreOrderInfoResponse extends ResponseSupport {

	private String message;
	private String orderId;
	private String result;
	private String prjId;
	private String orderNo;

	public SubmitPreOrderInfoResponse() {
		super();
		setMessageId("submitPreOrderInfo");
	}

	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return 反馈信息
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return 预约订单id
	 */
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return success-成功 fail 失败
	 */
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}