package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取当前登录用户我的预约列表信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyPreOrderPageListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetMyPreOrderPageListResponse#getPreOrderList
	 * 
	 */
	public static class ElementPreOrderList {

		private String frostAmount;
		private String homePicAddress;
		private String orderId;
		private String orderNo;
		private String orderStatus;
		private String preEndDate;
		private String preOrderCount;
		private String preStartDate;
		private String prjBStatus;
		private String prjId;
		private String prjNam;
		private String prodId;
		private String purposeAmount;
		private String depositStatus;
		private String isCanFirstBuy;

		/**
		 * 当前是否可以优先购买（0-否,1-是，2-正常购买）
		 * 
		 * @return
		 */
		public String getIsCanFirstBuy() {
			return isCanFirstBuy;
		}

		public void setIsCanFirstBuy(String isCanFirstBuy) {
			this.isCanFirstBuy = isCanFirstBuy;
		}

		/**
		 * @return 预约状态
		 */
		public String getDepositStatus() {
			return depositStatus;
		}

		public void setDepositStatus(String depositStatus) {
			this.depositStatus = depositStatus;
		}

		/**
		 * @return 冻结金额
		 */
		public String getFrostAmount() {
			return frostAmount;
		}

		public void setFrostAmount(String frostAmount) {
			this.frostAmount = frostAmount;
		}

		/**
		 * @return 项目展示图片地址
		 */
		public String getHomePicAddress() {
			return homePicAddress;
		}

		public void setHomePicAddress(String homePicAddress) {
			this.homePicAddress = homePicAddress;
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
		 * @return 订单状态
		 */
		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		/**
		 * @return 优先购买结束时间
		 */
		public String getPreEndDate() {
			return preEndDate;
		}

		public void setPreEndDate(String preEndDate) {
			this.preEndDate = preEndDate;
		}

		/**
		 * @return 预约人数
		 */
		public String getPreOrderCount() {
			return preOrderCount;
		}

		public void setPreOrderCount(String preOrderCount) {
			this.preOrderCount = preOrderCount;
		}

		/**
		 * @return 优先购买开始时间
		 */
		public String getPreStartDate() {
			return preStartDate;
		}

		public void setPreStartDate(String preStartDate) {
			this.preStartDate = preStartDate;
		}

		/**
		 * @return 项目状态
		 *         待预热-10,预热中-20,待筹款-30,预热流标-35,筹款中-40,筹款结束-45,筹款满标-50,筹款流标-
		 *         55,项目成功-60,成功结项-70,失败结项-80
		 */
		public String getPrjBStatus() {
			return prjBStatus;
		}

		public void setPrjBStatus(String prjBStatus) {
			this.prjBStatus = prjBStatus;
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
		 * @return 项目名称
		 */
		public String getPrjNam() {
			return prjNam;
		}

		public void setPrjNam(String prjNam) {
			this.prjNam = prjNam;
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
		 * @return 意向投资金额
		 */
		public String getPurposeAmount() {
			return purposeAmount;
		}

		public void setPurposeAmount(String purposeAmount) {
			this.purposeAmount = purposeAmount;
		}
	}

	private List<ElementPreOrderList> preOrderList;

	public GetMyPreOrderPageListResponse() {
		super();
		setMessageId("getMyPreOrderPageList");
	}

	/**
	 * @return 预约订单列表
	 */
	public List<ElementPreOrderList> getPreOrderList() {
		return preOrderList;
	}

	public void setPreOrderList(List<ElementPreOrderList> preOrderList) {
		this.preOrderList = preOrderList;
	}
}