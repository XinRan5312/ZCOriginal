package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 获取项目回报.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjRwdByPrjIdResponse extends ResponseSupport implements Serializable{

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjRwdByPrjIdResponse#getPrjRwdList
	 * 
	 */
	public static class ElementPrjRwdList implements Serializable{

		private String bookCnt;
		private String commodityId;
		private String commodityNam;
		private String commodityPerSupp;
		private String feeFreight;
		private String id;
		private String isFillMemo;
		private String isWantInvoice;
		private String lmtOneSuppCnt;
		private String perSuppAmt;
		private String prjId;
		private String rwdContent;
		private String rwdNam;
		private String rwdPct;
		private String rwdPicAddress;
		private String rwdPicFileID;
		private String rwdStartTime;
		private String rwdTyp;
		private String seqNo;
		private String status;
		private String subCommodityCnt;
		private String subRwdTyp;
		private String suppAmt;
		private String suppCnt;
		private String suppedAmt;
		private String suppedCnt;
		private String version_;

		/**
		 * @return 已预定数量
		 */
		public String getBookCnt() {
			return bookCnt;
		}

		public void setBookCnt(String bookCnt) {
			this.bookCnt = bookCnt;
		}

		/**
		 * @return 商品标识
		 */
		public String getCommodityId() {
			return commodityId;
		}

		public void setCommodityId(String commodityId) {
			this.commodityId = commodityId;
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
		 * @return 每支持获得的商品数量
		 */
		public String getCommodityPerSupp() {
			return commodityPerSupp;
		}

		public void setCommodityPerSupp(String commodityPerSupp) {
			this.commodityPerSupp = commodityPerSupp;
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
		 * @return 主键id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 支持是是否填写备注
		 */
		public String getIsFillMemo() {
			return isFillMemo;
		}

		public void setIsFillMemo(String isFillMemo) {
			this.isFillMemo = isFillMemo;
		}

		/**
		 * @return 是否要发票 0 不要 1 要
		 */
		public String getIsWantInvoice() {
			return isWantInvoice;
		}

		public void setIsWantInvoice(String isWantInvoice) {
			this.isWantInvoice = isWantInvoice;
		}

		/**
		 * @return 每人支持最大限制
		 */
		public String getLmtOneSuppCnt() {
			return lmtOneSuppCnt;
		}

		public void setLmtOneSuppCnt(String lmtOneSuppCnt) {
			this.lmtOneSuppCnt = lmtOneSuppCnt;
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
		 * @return 项目id
		 */
		public String getPrjId() {
			return prjId;
		}

		public void setPrjId(String prjId) {
			this.prjId = prjId;
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
		 * @return 支持名称
		 */
		public String getRwdNam() {
			return rwdNam;
		}

		public void setRwdNam(String rwdNam) {
			this.rwdNam = rwdNam;
		}

		/**
		 * @return 回报比例百分比
		 */
		public String getRwdPct() {
			return rwdPct;
		}

		public void setRwdPct(String rwdPct) {
			this.rwdPct = rwdPct;
		}

		/**
		 * @return 回报图片
		 */
		public String getRwdPicAddress() {
			return rwdPicAddress;
		}

		public void setRwdPicAddress(String rwdPicAddress) {
			this.rwdPicAddress = rwdPicAddress;
		}

		/**
		 * @return 
		 */
		public String getRwdPicFileID() {
			return rwdPicFileID;
		}

		public void setRwdPicFileID(String rwdPicFileID) {
			this.rwdPicFileID = rwdPicFileID;
		}

		/**
		 * @return 回报开始日期
		 */
		public String getRwdStartTime() {
			return rwdStartTime;
		}

		public void setRwdStartTime(String rwdStartTime) {
			this.rwdStartTime = rwdStartTime;
		}

		/**
		 * @return 回报类型  0 实物回报 1 虚拟回报 3无私奉献
		 */
		public String getRwdTyp() {
			return rwdTyp;
		}

		public void setRwdTyp(String rwdTyp) {
			this.rwdTyp = rwdTyp;
		}

		/**
		 * @return 
		 */
		public String getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(String seqNo) {
			this.seqNo = seqNo;
		}

		/**
		 * @return 回报状态 10-初始状态20-回报开始发放30-回报完成
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 子产品数量
		 */
		public String getSubCommodityCnt() {
			return subCommodityCnt;
		}

		public void setSubCommodityCnt(String subCommodityCnt) {
			this.subCommodityCnt = subCommodityCnt;
		}

		/**
		 * @return 子回报类型
		 */
		public String getSubRwdTyp() {
			return subRwdTyp;
		}

		public void setSubRwdTyp(String subRwdTyp) {
			this.subRwdTyp = subRwdTyp;
		}

		/**
		 * @return 应支持金额
		 */
		public String getSuppAmt() {
			return suppAmt;
		}

		public void setSuppAmt(String suppAmt) {
			this.suppAmt = suppAmt;
		}

		/**
		 * @return 支持份数
		 */
		public String getSuppCnt() {
			return suppCnt;
		}

		public void setSuppCnt(String suppCnt) {
			this.suppCnt = suppCnt;
		}

		/**
		 * @return 已支持金额 如果输入为0 ，表示不限制份数
		 */
		public String getSuppedAmt() {
			return suppedAmt;
		}

		public void setSuppedAmt(String suppedAmt) {
			this.suppedAmt = suppedAmt;
		}

		/**
		 * @return 已支持数量
		 */
		public String getSuppedCnt() {
			return suppedCnt;
		}

		public void setSuppedCnt(String suppedCnt) {
			this.suppedCnt = suppedCnt;
		}

		/**
		 * @return 版本
		 */
		public String getVersion_() {
			return version_;
		}

		public void setVersion_(String version_) {
			this.version_ = version_;
		}
	}

	private List<ElementPrjRwdList> prjRwdList;

	public GetPrjRwdByPrjIdResponse() {
		super();
		setMessageId("getPrjRwdByPrjId");
	}


	/**
	 * @return 回报列表
	 */
	public List<ElementPrjRwdList> getPrjRwdList() {
		return prjRwdList;
	}

	public void setPrjRwdList(List<ElementPrjRwdList> prjRwdList) {
		this.prjRwdList = prjRwdList;
	}
}