package net.zkbc.p2p.fep.message.protocol;

/**
 * 立即付款按钮（订单付款）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ButtonPayMentRequest extends RequestSupport {

	private String feeFlag;
	private String orderId;
	private String prodId;
	private String redMoneyId;

	public ButtonPayMentRequest() {
		super();
		setMessageId("buttonPayMent");
	}	

	/**
	 * @return 运费
	 */
	public String getFeeFlag() {
		return feeFlag;
	}

	public void setFeeFlag(String feeFlag) {
		this.feeFlag = feeFlag;
	}

	/**
	 * @return 订单Id
	 */
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 产品表示     0 股权众筹           1 产品众筹
	 */
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return 红包ID 可能有多个 可以拼接成字符串用“，”隔开
	 */
	public String getRedMoneyId() {
		return redMoneyId;
	}

	public void setRedMoneyId(String redMoneyId) {
		this.redMoneyId = redMoneyId;
	}
}