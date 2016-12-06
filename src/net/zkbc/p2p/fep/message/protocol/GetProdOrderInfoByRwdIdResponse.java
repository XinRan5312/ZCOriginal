package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据项目回报id获取产品项目确认订单相关信息（产品订单确认）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetProdOrderInfoByRwdIdResponse extends ResponseSupport {

	private String commodityNam;
	private String feeFreight;
	private String perSuppAmt;
	private String rwdContent;
	private String rwdId;
	private String rwdPicAddress;
	private String subId;
	private String isFillMemo;
	private String lmtOneSuppCnt;
	private String custBuyedCnt;

	public GetProdOrderInfoByRwdIdResponse() {
		super();
		setMessageId("getProdOrderInfoByRwdId");
	}

	/**
	 * @return 0-非必填 支持是否填写备注
	 */
	public String getIsFillMemo() {
		return isFillMemo;
	}

	public void setIsFillMemo(String isFillMemo) {
		this.isFillMemo = isFillMemo;
	}

	/**
	 * @return 每人支持最大限制（如果为0 表示不限制）
	 */
	public String getLmtOneSuppCnt() {
		return lmtOneSuppCnt;
	}

	public void setLmtOneSuppCnt(String lmtOneSuppCnt) {
		this.lmtOneSuppCnt = lmtOneSuppCnt;
	}

	/**
	 * @return 当前用户已经购买份数，如果支持不限制份数，则不反馈这个参数
	 */
	public String getCustBuyedCnt() {
		return custBuyedCnt;
	}

	public void setCustBuyedCnt(String custBuyedCnt) {
		this.custBuyedCnt = custBuyedCnt;
	}

	/**
	 * @return 商品名称
	 */
	public String getCommodityNam() {
		return commodityNam;
	}

	public void setCommodityNam(String commodityNam) {
		this.commodityNam = commodityNam;
	}

	/**
	 * @return 运费
	 */
	public String getFeeFreight() {
		return feeFreight;
	}

	public void setFeeFreight(String feeFreight) {
		this.feeFreight = feeFreight;
	}

	/**
	 * @return 支持金额(单笔)
	 */
	public String getPerSuppAmt() {
		return perSuppAmt;
	}

	public void setPerSuppAmt(String perSuppAmt) {
		this.perSuppAmt = perSuppAmt;
	}

	/**
	 * @return 回报内容
	 */
	public String getRwdContent() {
		return rwdContent;
	}

	public void setRwdContent(String rwdContent) {
		this.rwdContent = rwdContent;
	}

	/**
	 * @return 回报id
	 */
	public String getRwdId() {
		return rwdId;
	}

	public void setRwdId(String rwdId) {
		this.rwdId = rwdId;
	}

	/**
	 * @return 回报图片展示地址
	 */
	public String getRwdPicAddress() {
		return rwdPicAddress;
	}

	public void setRwdPicAddress(String rwdPicAddress) {
		this.rwdPicAddress = rwdPicAddress;
	}

	/**
	 * @return 提交id(避免重复提交)
	 */
	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}
}