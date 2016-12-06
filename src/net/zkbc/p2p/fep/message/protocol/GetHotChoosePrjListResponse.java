package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取热门推荐项目.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetHotChoosePrjListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetHotChoosePrjListResponse#getHotPrjList
	 * 
	 */
	public static class ElementHotPrjList {

		private String id;
		private String imgAddr;
		private String nam;
		private String prjAmount;
		private String prjEndTime;
		private String prjNo;
		private String prjStartTime;
		private String status;
		private String suppedAmt4Succ;
		private String suppedCnt;
		private String prjEndSurplusDays;
		

		public String getPrjEndSurplusDays() {
			return prjEndSurplusDays;
		}

		public void setPrjEndSurplusDays(String prjEndSurplusDays) {
			this.prjEndSurplusDays = prjEndSurplusDays;
		}

		/**
		 * @return 项目id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 展示图片地址
		 */
		public String getImgAddr() {
			return imgAddr;
		}

		public void setImgAddr(String imgAddr) {
			this.imgAddr = imgAddr;
		}

		/**
		 * @return 项目名称
		 */
		public String getNam() {
			return nam;
		}

		public void setNam(String nam) {
			this.nam = nam;
		}

		/**
		 * @return 项目融资金额
		 */
		public String getPrjAmount() {
			return prjAmount;
		}

		public void setPrjAmount(String prjAmount) {
			this.prjAmount = prjAmount;
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
		 * @return 项目编号
		 */
		public String getPrjNo() {
			return prjNo;
		}

		public void setPrjNo(String prjNo) {
			this.prjNo = prjNo;
		}

		/**
		 * @return 项目开始时间
		 */
		public String getPrjStartTime() {
			return prjStartTime;
		}

		public void setPrjStartTime(String prjStartTime) {
			this.prjStartTime = prjStartTime;
		}

		/**
		 * @return 项目状态  待预热-10,预热中-20,待筹款-30,预热流标-35,筹款中-40,筹款结束-45,筹款满标-50,筹款流标-55,项目成功-60,成功结项-70,失败结项-80
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 已支持金额
		 */
		public String getSuppedAmt4Succ() {
			return suppedAmt4Succ;
		}

		public void setSuppedAmt4Succ(String suppedAmt4Succ) {
			this.suppedAmt4Succ = suppedAmt4Succ;
		}

		/**
		 * @return 支持人数
		 */
		public String getSuppedCnt() {
			return suppedCnt;
		}

		public void setSuppedCnt(String suppedCnt) {
			this.suppedCnt = suppedCnt;
		}
	}

	private List<ElementHotPrjList> hotPrjList;

	public GetHotChoosePrjListResponse() {
		super();
		setMessageId("getHotChoosePrjList");
	}


	/**
	 * @return 热门推荐项目
	 */
	public List<ElementHotPrjList> getHotPrjList() {
		return hotPrjList;
	}

	public void setHotPrjList(List<ElementHotPrjList> hotPrjList) {
		this.hotPrjList = hotPrjList;
	}
}