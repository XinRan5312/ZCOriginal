package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取项目动态信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjTrendsLListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjTrendsLListResponse#getTrendsLList
	 * 
	 */
	public static class ElementTrendsLList {

		private String eventContent;
		private String eventTime;
		private String eventTitle;
		private String id;
		private String opLoginNam;
		private String prjId;
		private String trendTyp;

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
		 * @return 动态id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 操作用户
		 */
		public String getOpLoginNam() {
			return opLoginNam;
		}

		public void setOpLoginNam(String opLoginNam) {
			this.opLoginNam = opLoginNam;
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
		 * @return 动态类型 10 系统自动 20 手动发布
		 */
		public String getTrendTyp() {
			return trendTyp;
		}

		public void setTrendTyp(String trendTyp) {
			this.trendTyp = trendTyp;
		}
	}

	private List<ElementTrendsLList> trendsLList;

	public GetPrjTrendsLListResponse() {
		super();
		setMessageId("getPrjTrendsLList");
	}


	/**
	 * @return 项目动态列表
	 */
	public List<ElementTrendsLList> getTrendsLList() {
		return trendsLList;
	}

	public void setTrendsLList(List<ElementTrendsLList> trendsLList) {
		this.trendsLList = trendsLList;
	}
}