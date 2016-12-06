package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取相应信息（常见问题）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetCommNewListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetCommNewListResponse#getInfoList
	 * 
	 */
	public static class ElementInfoList {

		private String article_source;
		private String author;
		private String content;
		private String content_brief;
		private String id;
		private String issue_time;
		private String state;
		private String title;

		/**
		 * @return 来源
		 */
		public String getArticle_source() {
			return article_source;
		}

		public void setArticle_source(String article_source) {
			this.article_source = article_source;
		}

		/**
		 * @return 作者
		 */
		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		/**
		 * @return 内容
		 */
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		/**
		 * @return 内容简介
		 */
		public String getContent_brief() {
			return content_brief;
		}

		public void setContent_brief(String content_brief) {
			this.content_brief = content_brief;
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
		 * @return 发布时间
		 */
		public String getIssue_time() {
			return issue_time;
		}

		public void setIssue_time(String issue_time) {
			this.issue_time = issue_time;
		}

		/**
		 * @return 状态
		 */
		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		/**
		 * @return 标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	private List<ElementInfoList> infoList;

	public GetCommNewListResponse() {
		super();
		setMessageId("getCommNewList");
	}


	/**
	 * @return 
	 */
	public List<ElementInfoList> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<ElementInfoList> infoList) {
		this.infoList = infoList;
	}
}