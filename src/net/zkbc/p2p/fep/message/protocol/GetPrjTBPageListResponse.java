package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取当前登录用户所有发起的项目信息（我的发起）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjTBPageListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjTBPageListResponse#getPrjTBList
	 * 
	 */
	public static class ElementPrjTBList {

		private String attentionCount;
		private String homePicAddress;
		private String id;
		private String nam;
		private String prjAmount;
		private String prjBStatus;
		private String prjTrade;
		private String status;
		private String succAmount;
		private String suppedPeopleCnt;
		private String surplusDays;

		/**
		 * @return 关注人数
		 */
		public String getAttentionCount() {
			return attentionCount;
		}

		public void setAttentionCount(String attentionCount) {
			this.attentionCount = attentionCount;
		}

		/**
		 * @return 展示图片地址
		 */
		public String getHomePicAddress() {
			return homePicAddress;
		}

		public void setHomePicAddress(String homePicAddress) {
			this.homePicAddress = homePicAddress;
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
		 * @return 项目状态  待预热-10,预热中-20,待筹款-30,预热流标-35,筹款中-40,筹款结束-45,筹款满标-50,筹款流标-55,项目成功-60,成功结项-70,失败结项-80
		 */
		public String getPrjBStatus() {
			return prjBStatus;
		}

		public void setPrjBStatus(String prjBStatus) {
			this.prjBStatus = prjBStatus;
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
		 * @return 项目审批状态（待初审-20，待复审-30，待审批-35，待终审-40，已批准-45，已否决-50）
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
		public String getSuccAmount() {
			return succAmount;
		}

		public void setSuccAmount(String succAmount) {
			this.succAmount = succAmount;
		}

		/**
		 * @return 已支持人数
		 */
		public String getSuppedPeopleCnt() {
			return suppedPeopleCnt;
		}

		public void setSuppedPeopleCnt(String suppedPeopleCnt) {
			this.suppedPeopleCnt = suppedPeopleCnt;
		}

		/**
		 * @return 剩余天数
		 */
		public String getSurplusDays() {
			return surplusDays;
		}

		public void setSurplusDays(String surplusDays) {
			this.surplusDays = surplusDays;
		}
	}

	private List<ElementPrjTBList> prjTBList;

	public GetPrjTBPageListResponse() {
		super();
		setMessageId("getPrjTBPageList");
	}


	/**
	 * @return 项目列表
	 */
	public List<ElementPrjTBList> getPrjTBList() {
		return prjTBList;
	}

	public void setPrjTBList(List<ElementPrjTBList> prjTBList) {
		this.prjTBList = prjTBList;
	}
}