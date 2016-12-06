package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 根据订单id获取股权预约订单详情.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyPreProdOrderByIdResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetMyPreProdOrderByIdResponse#getPrjIntroList
	 * 
	 */
	public static class ElementPrjIntroList {

		private String fileIdMemo;
		private String introImgAddr;
		private String title;

		/**
		 * @return 项目介绍内容
		 */
		public String getFileIdMemo() {
			return fileIdMemo;
		}

		public void setFileIdMemo(String fileIdMemo) {
			this.fileIdMemo = fileIdMemo;
		}

		/**
		 * @return 项目介绍图片
		 */
		public String getIntroImgAddr() {
			return introImgAddr;
		}

		public void setIntroImgAddr(String introImgAddr) {
			this.introImgAddr = introImgAddr;
		}

		/**
		 * @return 项目介绍标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	private String earnest;
	private String homePicAddress;
	private String orderId;
	private String orderNo;
	private String orderStatus;
	private String perSuppAmt;
	private String preEndDate;
	private String preStartDate;
	private String purposeAmount;
	private String stockPctPerSupp;
	private String suppedCnt;
	private String depositStatus;
	private String isCanFirstBuy;
	private String recvNam;
	private String recvMobile;
	private String recvAddress;

	private List<ElementPrjIntroList> prjIntroList;

	public GetMyPreProdOrderByIdResponse() {
		super();
		setMessageId("getMyPreProdOrderById");
	}

	public String getRecvNam() {
		return recvNam;
	}

	public void setRecvNam(String recvNam) {
		this.recvNam = recvNam;
	}

	public String getRecvMobile() {
		return recvMobile;
	}

	public void setRecvMobile(String recvMobile) {
		this.recvMobile = recvMobile;
	}

	public String getRecvAddress() {
		return recvAddress;
	}

	public void setRecvAddress(String recvAddress) {
		this.recvAddress = recvAddress;
	}

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
	 * @return 预约金比例
	 */
	public String getEarnest() {
		return earnest;
	}

	public void setEarnest(String earnest) {
		this.earnest = earnest;
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
	 * @return 订单编号
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
	 * @return 支持金额(单笔)
	 */
	public String getPerSuppAmt() {
		return perSuppAmt;
	}

	public void setPerSuppAmt(String perSuppAmt) {
		this.perSuppAmt = perSuppAmt;
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
	 * @return 优先购买开始时间
	 */
	public String getPreStartDate() {
		return preStartDate;
	}

	public void setPreStartDate(String preStartDate) {
		this.preStartDate = preStartDate;
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

	/**
	 * @return 每份占股比例
	 */
	public String getStockPctPerSupp() {
		return stockPctPerSupp;
	}

	public void setStockPctPerSupp(String stockPctPerSupp) {
		this.stockPctPerSupp = stockPctPerSupp;
	}

	/**
	 * @return 数量
	 */
	public String getSuppedCnt() {
		return suppedCnt;
	}

	public void setSuppedCnt(String suppedCnt) {
		this.suppedCnt = suppedCnt;
	}

	/**
	 * @return 介绍列表
	 */
	public List<ElementPrjIntroList> getPrjIntroList() {
		return prjIntroList;
	}

	public void setPrjIntroList(List<ElementPrjIntroList> prjIntroList) {
		this.prjIntroList = prjIntroList;
	}
}