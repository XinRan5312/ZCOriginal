package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 获取话题列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetCommonTalkListResponse extends ResponseSupport implements Serializable{

	/**
	 * @see net.zkbc.p2p.message.protocol.GetCommonTalkListResponse#getCommonTalkList
	 * 
	 */
	public static class ElementCommonTalkList implements Serializable{

		private String byUserNickName;
		private String commentTime;
		private String content;
		private String id;
		private Integer praiseCnt;
		private Integer repayCnt;
		private String portraitAddr;


		public String getPortraitAddr() {
			return portraitAddr;
		}

		public void setPortraitAddr(String portraitAddr) {
			this.portraitAddr = portraitAddr;
		}

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

		/**
		 * @return 回复条数
		 */
		public Integer getRepayCnt() {
			return repayCnt;
		}

		public void setRepayCnt(Integer repayCnt) {
			this.repayCnt = repayCnt;
		}
	}

	private Integer pageSize;
	private Integer totalCount;
	private List<ElementCommonTalkList> commonTalkList;

	public GetCommonTalkListResponse() {
		super();
		setMessageId("getCommonTalkList");
	}


	/**
	 * @return 每页条数
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 总条数
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return 留言记录列表
	 */
	public List<ElementCommonTalkList> getCommonTalkList() {
		return commonTalkList;
	}

	public void setCommonTalkList(List<ElementCommonTalkList> commonTalkList) {
		this.commonTalkList = commonTalkList;
	}
}