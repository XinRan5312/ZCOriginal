package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取项目动态信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjDynListByPrjIdResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjDynListByPrjIdResponse#getPrjDynList
	 * 
	 */
	public static class ElementPrjDynList {

		private String createlTime;
		private String custId;
		private String eventContent;
		private String eventTime;
		private String eventTitle;
		private String id;
		private String isPublish;
		private String loginNam;
		private String opLoginNam;
		private String opUserId;
		private String prjId;
		private String prjNam;
		private String prjRunStatus;
		private String prjStatus;
		private String toPrjRunStatus;
		private String toPrjStatus;
		private String trendTyp;

		/**
		 * @return 
		 */
		public String getCreatelTime() {
			return createlTime;
		}

		public void setCreatelTime(String createlTime) {
			this.createlTime = createlTime;
		}

		/**
		 * @return 项目关联客户
		 */
		public String getCustId() {
			return custId;
		}

		public void setCustId(String custId) {
			this.custId = custId;
		}

		/**
		 * @return 事件内容
		 */
		public String getEventContent() {
			return eventContent;
		}

		public void setEventContent(String eventContent) {
			this.eventContent = eventContent;
		}

		/**
		 * @return 事件日期
		 */
		public String getEventTime() {
			return eventTime;
		}

		public void setEventTime(String eventTime) {
			this.eventTime = eventTime;
		}

		/**
		 * @return 事件标题
		 */
		public String getEventTitle() {
			return eventTitle;
		}

		public void setEventTitle(String eventTitle) {
			this.eventTitle = eventTitle;
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
		 * @return 是否发布到平台
		 */
		public String getIsPublish() {
			return isPublish;
		}

		public void setIsPublish(String isPublish) {
			this.isPublish = isPublish;
		}

		/**
		 * @return 
		 */
		public String getLoginNam() {
			return loginNam;
		}

		public void setLoginNam(String loginNam) {
			this.loginNam = loginNam;
		}

		/**
		 * @return 
		 */
		public String getOpLoginNam() {
			return opLoginNam;
		}

		public void setOpLoginNam(String opLoginNam) {
			this.opLoginNam = opLoginNam;
		}

		/**
		 * @return 
		 */
		public String getOpUserId() {
			return opUserId;
		}

		public void setOpUserId(String opUserId) {
			this.opUserId = opUserId;
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
		 * @return 项目名称
		 */
		public String getPrjNam() {
			return prjNam;
		}

		public void setPrjNam(String prjNam) {
			this.prjNam = prjNam;
		}

		/**
		 * @return 项目运行状态
		 */
		public String getPrjRunStatus() {
			return prjRunStatus;
		}

		public void setPrjRunStatus(String prjRunStatus) {
			this.prjRunStatus = prjRunStatus;
		}

		/**
		 * @return 项目状态
		 */
		public String getPrjStatus() {
			return prjStatus;
		}

		public void setPrjStatus(String prjStatus) {
			this.prjStatus = prjStatus;
		}

		/**
		 * @return 目标项目运行状态
		 */
		public String getToPrjRunStatus() {
			return toPrjRunStatus;
		}

		public void setToPrjRunStatus(String toPrjRunStatus) {
			this.toPrjRunStatus = toPrjRunStatus;
		}

		/**
		 * @return 目标项目状态
		 */
		public String getToPrjStatus() {
			return toPrjStatus;
		}

		public void setToPrjStatus(String toPrjStatus) {
			this.toPrjStatus = toPrjStatus;
		}

		/**
		 * @return 动态类型 10 系统自动          20 手动发布
		 */
		public String getTrendTyp() {
			return trendTyp;
		}

		public void setTrendTyp(String trendTyp) {
			this.trendTyp = trendTyp;
		}
	}

	private List<ElementPrjDynList> prjDynList;

	public GetPrjDynListByPrjIdResponse() {
		super();
		setMessageId("getPrjDynListByPrjId");
	}


	/**
	 * @return 项目动态信息列表
	 */
	public List<ElementPrjDynList> getPrjDynList() {
		return prjDynList;
	}

	public void setPrjDynList(List<ElementPrjDynList> prjDynList) {
		this.prjDynList = prjDynList;
	}
}