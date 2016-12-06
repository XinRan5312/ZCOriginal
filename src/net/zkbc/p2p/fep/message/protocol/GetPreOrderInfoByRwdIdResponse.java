package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 根据项目回报id获取项目相关信息（预约订单确认）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPreOrderInfoByRwdIdResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPreOrderInfoByRwdIdResponse#getPrjIntroList
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
	private String perSuppAmt;
	private String preEndDate;
	private String preStartDate;
	private String prjId;
	private String rwdId;
	private String rwdPicFileAddr;
	private String stockPctPerSupp;
	private String subId;
	private String isFillMemo;
	private String lmtOneSuppCnt;
	private String custBuyedCnt;
	private List<ElementPrjIntroList> prjIntroList;

	public GetPreOrderInfoByRwdIdResponse() {
		super();
		setMessageId("getPreOrderInfoByRwdId");
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
	 * @return 当前用户已经购买份数，如果支持不限制份数，则不反馈这个参数）
	 */
	public String getCustBuyedCnt() {
		return custBuyedCnt;
	}

	public void setCustBuyedCnt(String custBuyedCnt) {
		this.custBuyedCnt = custBuyedCnt;
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
	 * @return 预约金比例
	 */
	public String getEarnest() {
		return earnest;
	}

	public void setEarnest(String earnest) {
		this.earnest = earnest;
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
	 * @return 预计优先购买期结束时间
	 */
	public String getPreEndDate() {
		return preEndDate;
	}

	public void setPreEndDate(String preEndDate) {
		this.preEndDate = preEndDate;
	}

	/**
	 * @return 预计优先购买期开始时间
	 */
	public String getPreStartDate() {
		return preStartDate;
	}

	public void setPreStartDate(String preStartDate) {
		this.preStartDate = preStartDate;
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
	 * @return 项目回报id
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
	public String getRwdPicFileAddr() {
		return rwdPicFileAddr;
	}

	public void setRwdPicFileAddr(String rwdPicFileAddr) {
		this.rwdPicFileAddr = rwdPicFileAddr;
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
	 * @return 提交id(避免重复提交)
	 */
	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
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