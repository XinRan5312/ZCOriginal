package net.zkbc.p2p.fep.message.protocol;

/**
 * 按钮立即下单（确认订单 下一步）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ButtonPlaceOrderRequest extends RequestSupport {

	private String appId;
	private String buyCnt;
	private String perSuppAmt;
	private String prjId;
	private String prjRwdDtlBBuyCnts;
	private String prjRwdDtlBIds;
	private String prjRwdDtlBPerSuppAmts;
	private String prjRwdId;
	private String prodId;
	private String seqNoAddr;

	public ButtonPlaceOrderRequest() {
		super();
		setMessageId("buttonPlaceOrder");
	}	

	/**
	 * @return 交易流水号(用于防止重复下订单 该值由getConfirmOrderData接口提供 )
	 */
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return 项目回报支持数量
	 */
	public String getBuyCnt() {
		return buyCnt;
	}

	public void setBuyCnt(String buyCnt) {
		this.buyCnt = buyCnt;
	}

	/**
	 * @return 项目回报单笔支持金额
	 */
	public String getPerSuppAmt() {
		return perSuppAmt;
	}

	public void setPerSuppAmt(String perSuppAmt) {
		this.perSuppAmt = perSuppAmt;
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
	 * @return 项目子回报支持数量  有多个的情况下用“，”隔开
	 */
	public String getPrjRwdDtlBBuyCnts() {
		return prjRwdDtlBBuyCnts;
	}

	public void setPrjRwdDtlBBuyCnts(String prjRwdDtlBBuyCnts) {
		this.prjRwdDtlBBuyCnts = prjRwdDtlBBuyCnts;
	}

	/**
	 * @return 项目子回报Id 有多个的情况下用“，”隔开
	 */
	public String getPrjRwdDtlBIds() {
		return prjRwdDtlBIds;
	}

	public void setPrjRwdDtlBIds(String prjRwdDtlBIds) {
		this.prjRwdDtlBIds = prjRwdDtlBIds;
	}

	/**
	 * @return 项目子回报单笔支持金额 有多个的情况下用“，”隔开
	 */
	public String getPrjRwdDtlBPerSuppAmts() {
		return prjRwdDtlBPerSuppAmts;
	}

	public void setPrjRwdDtlBPerSuppAmts(String prjRwdDtlBPerSuppAmts) {
		this.prjRwdDtlBPerSuppAmts = prjRwdDtlBPerSuppAmts;
	}

	/**
	 * @return 项目回报id
	 */
	public String getPrjRwdId() {
		return prjRwdId;
	}

	public void setPrjRwdId(String prjRwdId) {
		this.prjRwdId = prjRwdId;
	}

	/**
	 * @return 产品表示     0 股权众筹
            1 产品众筹
	 */
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return 地址序列号
	 */
	public String getSeqNoAddr() {
		return seqNoAddr;
	}

	public void setSeqNoAddr(String seqNoAddr) {
		this.seqNoAddr = seqNoAddr;
	}
}