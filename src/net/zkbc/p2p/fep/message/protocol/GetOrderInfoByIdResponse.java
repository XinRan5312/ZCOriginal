package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据订单id查询订单信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetOrderInfoByIdResponse extends ResponseSupport {

	private String addr;
	private String buyDepositOrderNo;
	private String codCity;
	private String codProv;
	private String commodityCnt;
	private String commodityID;
	private String commodityNam;
	private String custRemark;
	private String discountPct;
	private String endTime;
	private String feeYfMoney;
	private String id;
	private String isReturnMoney;
	private String orderNo;
	private String payTime;
	private String perSuppAmt;
	private String prjCustId;
	private String prjCustNam;
	private String prjCustTel;
	private String prjId;
	private String prjRwdId;
	private String prjStatus;
	private String prodId;
	private String realPayAmt;
	private String realPayedAmt;
	private String recvMobile;
	private String recvNam;
	private String regretEndTime;
	private String rwdContent;
	private String rwdPicAddr;
	private String status;
	private String totalPrice;
	private String transId;

	public GetOrderInfoByIdResponse() {
		super();
		setMessageId("getOrderInfoById");
	}


	/**
	 * @return 详细地址
	 */
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return 定金订单号
	 */
	public String getBuyDepositOrderNo() {
		return buyDepositOrderNo;
	}

	public void setBuyDepositOrderNo(String buyDepositOrderNo) {
		this.buyDepositOrderNo = buyDepositOrderNo;
	}

	/**
	 * @return 收货城市
	 */
	public String getCodCity() {
		return codCity;
	}

	public void setCodCity(String codCity) {
		this.codCity = codCity;
	}

	/**
	 * @return 收货省份
	 */
	public String getCodProv() {
		return codProv;
	}

	public void setCodProv(String codProv) {
		this.codProv = codProv;
	}

	/**
	 * @return 产品数量
	 */
	public String getCommodityCnt() {
		return commodityCnt;
	}

	public void setCommodityCnt(String commodityCnt) {
		this.commodityCnt = commodityCnt;
	}

	/**
	 * @return 商品ID            对于项目来说，就是prjId
	 */
	public String getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(String commodityID) {
		this.commodityID = commodityID;
	}

	/**
	 * @return 回报商品名称
	 */
	public String getCommodityNam() {
		return commodityNam;
	}

	public void setCommodityNam(String commodityNam) {
		this.commodityNam = commodityNam;
	}

	/**
	 * @return 订单用户备注
	 */
	public String getCustRemark() {
		return custRemark;
	}

	public void setCustRemark(String custRemark) {
		this.custRemark = custRemark;
	}

	/**
	 * @return 折扣比例
	 */
	public String getDiscountPct() {
		return discountPct;
	}

	public void setDiscountPct(String discountPct) {
		this.discountPct = discountPct;
	}

	/**
	 * @return 订单完成时间
	 */
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return 回报运费
	 */
	public String getFeeYfMoney() {
		return feeYfMoney;
	}

	public void setFeeYfMoney(String feeYfMoney) {
		this.feeYfMoney = feeYfMoney;
	}

	/**
	 * @return 订单主键id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 是否可以退款(0-否  1-是)
	 */
	public String getIsReturnMoney() {
		return isReturnMoney;
	}

	public void setIsReturnMoney(String isReturnMoney) {
		this.isReturnMoney = isReturnMoney;
	}

	/**
	 * @return 订单编号，需要发送到第三方支付
	 */
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return 付款时间
	 */
	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	/**
	 * @return 回报单价
	 */
	public String getPerSuppAmt() {
		return perSuppAmt;
	}

	public void setPerSuppAmt(String perSuppAmt) {
		this.perSuppAmt = perSuppAmt;
	}

	/**
	 * @return 项目发起人id
	 */
	public String getPrjCustId() {
		return prjCustId;
	}

	public void setPrjCustId(String prjCustId) {
		this.prjCustId = prjCustId;
	}

	/**
	 * @return 项目发起人登录名
	 */
	public String getPrjCustNam() {
		return prjCustNam;
	}

	public void setPrjCustNam(String prjCustNam) {
		this.prjCustNam = prjCustNam;
	}

	/**
	 * @return 项目发起人电话
	 */
	public String getPrjCustTel() {
		return prjCustTel;
	}

	public void setPrjCustTel(String prjCustTel) {
		this.prjCustTel = prjCustTel;
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
	 * @return 回报Id
	 */
	public String getPrjRwdId() {
		return prjRwdId;
	}

	public void setPrjRwdId(String prjRwdId) {
		this.prjRwdId = prjRwdId;
	}

	/**
	 * @return 项目状态  待预热-10,预热中-20,待筹款-30,预热流标-35,筹款中-40,筹款结束-45,筹款满标-50,筹款流标-55,项目成功-60,成功结项-70,失败结项-80
	 */
	public String getPrjStatus() {
		return prjStatus;
	}

	public void setPrjStatus(String prjStatus) {
		this.prjStatus = prjStatus;
	}

	/**
	 * @return 产品号
	 */
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return 实际应该支付金额
	 */
	public String getRealPayAmt() {
		return realPayAmt;
	}

	public void setRealPayAmt(String realPayAmt) {
		this.realPayAmt = realPayAmt;
	}

	/**
	 * @return 实际已付金额,定金不作为计算已付金额。
	 */
	public String getRealPayedAmt() {
		return realPayedAmt;
	}

	public void setRealPayedAmt(String realPayedAmt) {
		this.realPayedAmt = realPayedAmt;
	}

	/**
	 * @return 收货人手机
	 */
	public String getRecvMobile() {
		return recvMobile;
	}

	public void setRecvMobile(String recvMobile) {
		this.recvMobile = recvMobile;
	}

	/**
	 * @return 收货人姓名
	 */
	public String getRecvNam() {
		return recvNam;
	}

	public void setRecvNam(String recvNam) {
		this.recvNam = recvNam;
	}

	/**
	 * @return 后悔截至时间
	 */
	public String getRegretEndTime() {
		return regretEndTime;
	}

	public void setRegretEndTime(String regretEndTime) {
		this.regretEndTime = regretEndTime;
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
	 * @return 回报展示图片地址
	 */
	public String getRwdPicAddr() {
		return rwdPicAddr;
	}

	public void setRwdPicAddr(String rwdPicAddr) {
		this.rwdPicAddr = rwdPicAddr;
	}

	/**
	 * @return 订单状态
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 总价
	 */
	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return 交易流水
	 */
	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}
}