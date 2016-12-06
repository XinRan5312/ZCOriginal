package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 获取投资信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjCustBuyByPrjIdResponse extends ResponseSupport implements Serializable{

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjCustBuyByPrjIdResponse#getPrjCustBuyList
	 * 
	 */
	public static class ElementPrjCustBuyList implements Serializable{

		private String buyDepositAmt;
		private String buyedDepositAmt;
		private String buyTime;
		private String custId;
		private String custLoginNam;
		private String custPortraitAddr;
		private String feeDepositEndTime;
		private String isPrjLeader;
		private String prjId;
		private String realPayAmt;
		private String realPayedAmt;
		private String rwdContent;
		private String rwdTyp;
		private String status;
		private String suppCnt;
		private String totalPrice;
		private String transId;
		private String version_;

		/**
		 * @return 定金金额
		 */
		public String getBuyDepositAmt() {
			return buyDepositAmt;
		}

		public void setBuyDepositAmt(String buyDepositAmt) {
			this.buyDepositAmt = buyDepositAmt;
		}

		/**
		 * @return 已付定金
		 */
		public String getBuyedDepositAmt() {
			return buyedDepositAmt;
		}

		public void setBuyedDepositAmt(String buyedDepositAmt) {
			this.buyedDepositAmt = buyedDepositAmt;
		}

		/**
		 * @return 购买时间
		 */
		public String getBuyTime() {
			return buyTime;
		}

		public void setBuyTime(String buyTime) {
			this.buyTime = buyTime;
		}

		/**
		 * @return 客户Id
		 */
		public String getCustId() {
			return custId;
		}

		public void setCustId(String custId) {
			this.custId = custId;
		}

		/**
		 * @return 客户登录名
		 */
		public String getCustLoginNam() {
			return custLoginNam;
		}

		public void setCustLoginNam(String custLoginNam) {
			this.custLoginNam = custLoginNam;
		}

		/**
		 * @return 用户头像地址
		 */
		public String getCustPortraitAddr() {
			return custPortraitAddr;
		}

		public void setCustPortraitAddr(String custPortraitAddr) {
			this.custPortraitAddr = custPortraitAddr;
		}

		/**
		 * @return 定金结束时间
		 */
		public String getFeeDepositEndTime() {
			return feeDepositEndTime;
		}

		public void setFeeDepositEndTime(String feeDepositEndTime) {
			this.feeDepositEndTime = feeDepositEndTime;
		}

		/**
		 * @return 是否该项目领投人（true-是）
		 */
		public String getIsPrjLeader() {
			return isPrjLeader;
		}

		public void setIsPrjLeader(String isPrjLeader) {
			this.isPrjLeader = isPrjLeader;
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
		 * @return 应支持金额
		 */
		public String getRealPayAmt() {
			return realPayAmt;
		}

		public void setRealPayAmt(String realPayAmt) {
			this.realPayAmt = realPayAmt;
		}

		/**
		 * @return 实际付款金额
		 */
		public String getRealPayedAmt() {
			return realPayedAmt;
		}

		public void setRealPayedAmt(String realPayedAmt) {
			this.realPayedAmt = realPayedAmt;
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
		 * @return 回报类型
		 */
		public String getRwdTyp() {
			return rwdTyp;
		}

		public void setRwdTyp(String rwdTyp) {
			this.rwdTyp = rwdTyp;
		}

		/**
		 * @return 认购状态
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 购买份数
		 */
		public String getSuppCnt() {
			return suppCnt;
		}

		public void setSuppCnt(String suppCnt) {
			this.suppCnt = suppCnt;
		}

		/**
		 * @return 
		 */
		public String getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(String totalPrice) {
			this.totalPrice = totalPrice;
		}

		/**
		 * @return 
		 */
		public String getTransId() {
			return transId;
		}

		public void setTransId(String transId) {
			this.transId = transId;
		}

		/**
		 * @return 
		 */
		public String getVersion_() {
			return version_;
		}

		public void setVersion_(String version_) {
			this.version_ = version_;
		}
	}

	private List<ElementPrjCustBuyList> prjCustBuyList;

	public GetPrjCustBuyByPrjIdResponse() {
		super();
		setMessageId("getPrjCustBuyByPrjId");
	}


	/**
	 * @return 投资列表
	 */
	public List<ElementPrjCustBuyList> getPrjCustBuyList() {
		return prjCustBuyList;
	}

	public void setPrjCustBuyList(List<ElementPrjCustBuyList> prjCustBuyList) {
		this.prjCustBuyList = prjCustBuyList;
	}
}