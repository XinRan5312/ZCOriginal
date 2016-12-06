package net.zkbc.p2p.fep.message.protocol;

/**
 * 立即付款按钮（订单付款）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ButtonPayMentResponse extends ResponseSupport {

	private String eflag;
	private String feeAmtSum;
	private String orderId;
	private String orderNo;
	private String payedRedMoney;
	private String prjId;
	private String prodId;
	private String realPayAmt;
	private String realPayAmtSum;
	private String resultCode;
	private String resultMessage;

	public ButtonPayMentResponse() {
		super();
		setMessageId("buttonPayMent");
	}


	/**
	 * @return 为false 及余额不足
	 */
	public String getEflag() {
		return eflag;
	}

	public void setEflag(String eflag) {
		this.eflag = eflag;
	}

	/**
	 * @return 运费
	 */
	public String getFeeAmtSum() {
		return feeAmtSum;
	}

	public void setFeeAmtSum(String feeAmtSum) {
		this.feeAmtSum = feeAmtSum;
	}

	/**
	 * @return 订单ID
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
	 * @return 实际已付红包金额
	 */
	public String getPayedRedMoney() {
		return payedRedMoney;
	}

	public void setPayedRedMoney(String payedRedMoney) {
		this.payedRedMoney = payedRedMoney;
	}

	/**
	 * @return 项目ID
	 */
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * @return 产品表示     0 股权众筹       1 产品众筹
	 */
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return 实际应付金额
	 */
	public String getRealPayAmt() {
		return realPayAmt;
	}

	public void setRealPayAmt(String realPayAmt) {
		this.realPayAmt = realPayAmt;
	}

	/**
	 * @return 实际应付金额总和
	 */
	public String getRealPayAmtSum() {
		return realPayAmtSum;
	}

	public void setRealPayAmtSum(String realPayAmtSum) {
		this.realPayAmtSum = realPayAmtSum;
	}

	/**
	 * @return true或者false 
	 */
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return 错误信息
	 */
	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}