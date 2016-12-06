package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 获取话题回复列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetCommonTalkRepayListResponse extends ResponseSupport implements
		Serializable {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetCommonTalkRepayListResponse#getCommonTalkRepayList
	 * 
	 */
	public static class ElementCommonTalkRepayList implements Serializable {

		private String byUserNickName;
		private String commentIdreplyTo;
		private String commentTime;
		private String content;
		private String id;
		private Integer praiseCnt;

		/**
		 * @return 留言人昵称
		 */
		public String getByUserNickName() {
			return byUserNickName;
		}

		public void setByUserNickName(String byUserNickName) {
			this.byUserNickName = byUserNickName;
		}

		/**
		 * @return 发起留言的目标id
		 */
		public String getCommentIdreplyTo() {
			return commentIdreplyTo;
		}

		public void setCommentIdreplyTo(String commentIdreplyTo) {
			this.commentIdreplyTo = commentIdreplyTo;
		}

		/**
		 * @return 发起留言时间
		 */
		public String getCommentTime() {
			return commentTime;
		}

		public void setCommentTime(String commentTime) {
			this.commentTime = commentTime;
		}

		/**
		 * @return 留言内容
		 */
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		/**
		 * @return 留言id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 被赞次数
		 */
		public Integer getPraiseCnt() {
			return praiseCnt;
		}

		public void setPraiseCnt(Integer praiseCnt) {
			this.praiseCnt = praiseCnt;
		}
	}

	private List<ElementCommonTalkRepayList> commonTalkRepayList;

	public GetCommonTalkRepayListResponse() {
		super();
		setMessageId("getCommonTalkRepayList");
	}

	/**
	 * @return 留言回复记录列表
	 */
	public List<ElementCommonTalkRepayList> getCommonTalkRepayList() {
		return commonTalkRepayList;
	}

	public void setCommonTalkRepayList(
			List<ElementCommonTalkRepayList> commonTalkRepayList) {
		this.commonTalkRepayList = commonTalkRepayList;
	}
}