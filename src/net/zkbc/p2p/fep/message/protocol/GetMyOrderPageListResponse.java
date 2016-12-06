package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取用户订单.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyOrderPageListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetMyOrderPageListResponse#getMyOrderList
	 * 
	 */
	public static class ElementMyOrderList {

		private String autoConfRecvGoodsTime;
		private String buyDepositOrderNo;
		private String buyedDepositAmt;
		private String commissionAmt;
		private String commodityCnt;
		private String commodityID;
		private String commodityNam;
		private String createTime;
		private String custId;
		private String custRemark;
		private String discountPct;
		private String endStatus;
		private String endTime;
		private String feeDeposit;
		private String feeYfMoney;
		private String fileaddr;
		private String id;
		private String isReturnMoney;
		private String loginNam;
		private String orderNo;
		private String orderTyp;
		private String orderValidateTime;
		private String payTime;
		private String perSuppAmt;
		private String prebookValidateTime;
		private String prjCustRemark;
		private String prjEndTime;
		private String prjId;
		private String prjTrade;
		private String prodId;
		private String realPayAmt;
		private String realPayedAmt;
		private String recvNam;
		private String regretEndTime;
		private String regretSuppDayCnt;
		private String riskPreventAmt;
		private String rwdContent;
		private String rwdTyp;
		private String status;
		private String totalPrice;
		private String totOrderNo;
		private String transId;
		private String unregretDayCnt;
		private String usePassword;
		private String version_;
		private String prjBStatus;
		private String isCancelOrder;
		private String servicePhone;

		/**
		 * @return 联系电话
		 */
		public String getServicePhone() {
			return servicePhone;
		}

		public void setServicePhone(String servicePhone) {
			this.servicePhone = servicePhone;
		}

		/**
		 * @return 是否可以取消订单 1-是 0-否
		 */
		public String getIsCancelOrder() {
			return isCancelOrder;
		}

		public void setIsCancelOrder(String isCancelOrder) {
			this.isCancelOrder = isCancelOrder;
		}


		public String getPrjBStatus() {
			return prjBStatus;
		}

		public void setPrjBStatus(String prjBStatus) {
			this.prjBStatus = prjBStatus;
		}

		/**
		 * @return 自动确认收货时间。
		 */
		public String getAutoConfRecvGoodsTime() {
			return autoConfRecvGoodsTime;
		}

		public void setAutoConfRecvGoodsTime(String autoConfRecvGoodsTime) {
			this.autoConfRecvGoodsTime = autoConfRecvGoodsTime;
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
		 * @return
		 */
		public String getBuyedDepositAmt() {
			return buyedDepositAmt;
		}

		public void setBuyedDepositAmt(String buyedDepositAmt) {
			this.buyedDepositAmt = buyedDepositAmt;
		}

		/**
		 * @return 佣金
		 */
		public String getCommissionAmt() {
			return commissionAmt;
		}

		public void setCommissionAmt(String commissionAmt) {
			this.commissionAmt = commissionAmt;
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
		 * @return 商品ID
		 */
		public String getCommodityID() {
			return commodityID;
		}

		public void setCommodityID(String commodityID) {
			this.commodityID = commodityID;
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
		 * @return 创建时间
		 */
		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		/**
		 * @return 用户ID
		 */
		public String getCustId() {
			return custId;
		}

		public void setCustId(String custId) {
			this.custId = custId;
		}

		/**
		 * @return 订单客户备注
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
		 * @return 完成状态10 初始状态20 已完成状态
		 */
		public String getEndStatus() {
			return endStatus;
		}

		public void setEndStatus(String endStatus) {
			this.endStatus = endStatus;
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
		 * @return 订单保证金
		 */
		public String getFeeDeposit() {
			return feeDeposit;
		}

		public void setFeeDeposit(String feeDeposit) {
			this.feeDeposit = feeDeposit;
		}

		/**
		 * @return 运费
		 */
		public String getFeeYfMoney() {
			return feeYfMoney;
		}

		public void setFeeYfMoney(String feeYfMoney) {
			this.feeYfMoney = feeYfMoney;
		}

		/**
		 * @return 订单相关图片地址
		 */
		public String getFileaddr() {
			return fileaddr;
		}

		public void setFileaddr(String fileaddr) {
			this.fileaddr = fileaddr;
		}

		/**
		 * @return 订单标识
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 是否可以退款
		 */
		public String getIsReturnMoney() {
			return isReturnMoney;
		}

		public void setIsReturnMoney(String isReturnMoney) {
			this.isReturnMoney = isReturnMoney;
		}

		/**
		 * @return 用户名
		 */
		public String getLoginNam() {
			return loginNam;
		}

		public void setLoginNam(String loginNam) {
			this.loginNam = loginNam;
		}

		/**
		 * @return 订单编号，需要发送到第三方支付。
		 */
		public String getOrderNo() {
			return orderNo;
		}

		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}

		/**
		 * @return 订单类型 10 子订单 无效的订单 20 总订单 有效订单 30 合并总订单
		 */
		public String getOrderTyp() {
			return orderTyp;
		}

		public void setOrderTyp(String orderTyp) {
			this.orderTyp = orderTyp;
		}

		/**
		 * @return 订单有效日期
		 */
		public String getOrderValidateTime() {
			return orderValidateTime;
		}

		public void setOrderValidateTime(String orderValidateTime) {
			this.orderValidateTime = orderValidateTime;
		}

		/**
		 * @return 支付时间
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
		 * @return 预约有效日期
		 */
		public String getPrebookValidateTime() {
			return prebookValidateTime;
		}

		public void setPrebookValidateTime(String prebookValidateTime) {
			this.prebookValidateTime = prebookValidateTime;
		}

		/**
		 * @return 订单客户备注
		 */
		public String getPrjCustRemark() {
			return prjCustRemark;
		}

		public void setPrjCustRemark(String prjCustRemark) {
			this.prjCustRemark = prjCustRemark;
		}

		/**
		 * @return 项目结束时间
		 */
		public String getPrjEndTime() {
			return prjEndTime;
		}

		public void setPrjEndTime(String prjEndTime) {
			this.prjEndTime = prjEndTime;
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
		 * @return 项目行业
		 */
		public String getPrjTrade() {
			return prjTrade;
		}

		public void setPrjTrade(String prjTrade) {
			this.prjTrade = prjTrade;
		}

		/**
		 * @return 产品表示 0 股权众筹 1 产品众筹
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
		 * @return 实际已付金额，定金不作为计算已付金额
		 */
		public String getRealPayedAmt() {
			return realPayedAmt;
		}

		public void setRealPayedAmt(String realPayedAmt) {
			this.realPayedAmt = realPayedAmt;
		}

		/**
		 * @return 收货姓名
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
		 * @return 后悔天数
		 */
		public String getRegretSuppDayCnt() {
			return regretSuppDayCnt;
		}

		public void setRegretSuppDayCnt(String regretSuppDayCnt) {
			this.regretSuppDayCnt = regretSuppDayCnt;
		}

		/**
		 * @return
		 */
		public String getRiskPreventAmt() {
			return riskPreventAmt;
		}

		public void setRiskPreventAmt(String riskPreventAmt) {
			this.riskPreventAmt = riskPreventAmt;
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
		 * @return 回报类型0 实物回报 1 虚拟回报
		 */
		public String getRwdTyp() {
			return rwdTyp;
		}

		public void setRwdTyp(String rwdTyp) {
			this.rwdTyp = rwdTyp;
		}

		/**
		 * @return 订单的状态10 未支付 20 已撤销 30
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
		 * @return 总订单号，如果没有子订单，填orderNo
		 */
		public String getTotOrderNo() {
			return totOrderNo;
		}

		public void setTotOrderNo(String totOrderNo) {
			this.totOrderNo = totOrderNo;
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

		/**
		 * @return 禁止后悔天数
		 */
		public String getUnregretDayCnt() {
			return unregretDayCnt;
		}

		public void setUnregretDayCnt(String unregretDayCnt) {
			this.unregretDayCnt = unregretDayCnt;
		}

		/**
		 * @return 使用密码。通过这个密码，进行现场消费。
		 */
		public String getUsePassword() {
			return usePassword;
		}

		public void setUsePassword(String usePassword) {
			this.usePassword = usePassword;
		}

		/**
		 * @return 版本号
		 */
		public String getVersion_() {
			return version_;
		}

		public void setVersion_(String version_) {
			this.version_ = version_;
		}
	}

	private List<ElementMyOrderList> myOrderList;

	public GetMyOrderPageListResponse() {
		super();
		setMessageId("getMyOrderPageList");
	}

	/**
	 * @return 订单列表
	 */
	public List<ElementMyOrderList> getMyOrderList() {
		return myOrderList;
	}

	public void setMyOrderList(List<ElementMyOrderList> myOrderList) {
		this.myOrderList = myOrderList;
	}
}