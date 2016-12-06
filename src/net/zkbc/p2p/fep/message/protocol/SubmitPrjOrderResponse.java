package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交订单.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitPrjOrderResponse extends ResponseSupport {

	private String message;
	private String orderId;
	private String orderNo;
	private String payAmt;
	private String prjId;
	private String result;
	private String type;

	public SubmitPrjOrderResponse() {
		super();
		setMessageId("submitPrjOrder");
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
	 * @return 订单id
	 */
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return 付款金额
	 */
	public String getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}

	/**
	 * @return 项目id
	 */
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
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

	/**
	 * @return 支付类型
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}