package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取站内信.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyInnerMailResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetMyInnerMailResponse#getInnerMailList
	 * 
	 */
	public static class ElementInnerMailList {

		private String content;
		private String headerAddress;
		private String id;
		private String isOutstanding;
		private String isread;
		private String mailContext;
		private String messageType;
		private String readTime;
		private String receiver;
		private String sender;
		private String sendtime;

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
		 * @return 发件人头像
		 */
		public String getHeaderAddress() {
			return headerAddress;
		}

		public void setHeaderAddress(String headerAddress) {
			this.headerAddress = headerAddress;
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
		 * @return 是否红旗，默认否
		 */
		public String getIsOutstanding() {
			return isOutstanding;
		}

		public void setIsOutstanding(String isOutstanding) {
			this.isOutstanding = isOutstanding;
		}

		/**
		 * @return 是否已读，默认否
		 */
		public String getIsread() {
			return isread;
		}

		public void setIsread(String isread) {
			this.isread = isread;
		}

		/**
		 * @return 引用的内容
		 */
		public String getMailContext() {
			return mailContext;
		}

		public void setMailContext(String mailContext) {
			this.mailContext = mailContext;
		}

		/**
		 * @return 站内信类型
		 */
		public String getMessageType() {
			return messageType;
		}

		public void setMessageType(String messageType) {
			this.messageType = messageType;
		}

		/**
		 * @return 信件阅读时间
		 */
		public String getReadTime() {
			return readTime;
		}

		public void setReadTime(String readTime) {
			this.readTime = readTime;
		}

		/**
		 * @return 收件人
		 */
		public String getReceiver() {
			return receiver;
		}

		public void setReceiver(String receiver) {
			this.receiver = receiver;
		}

		/**
		 * @return 发件人
		 */
		public String getSender() {
			return sender;
		}

		public void setSender(String sender) {
			this.sender = sender;
		}

		/**
		 * @return 推送时间
		 */
		public String getSendtime() {
			return sendtime;
		}

		public void setSendtime(String sendtime) {
			this.sendtime = sendtime;
		}
	}

	private String unreadCount;
	private List<ElementInnerMailList> innerMailList;

	public GetMyInnerMailResponse() {
		super();
		setMessageId("getMyInnerMail");
	}


	/**
	 * @return 未读条数
	 */
	public String getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(String unreadCount) {
		this.unreadCount = unreadCount;
	}

	/**
	 * @return 站内信集合
	 */
	public List<ElementInnerMailList> getInnerMailList() {
		return innerMailList;
	}

	public void setInnerMailList(List<ElementInnerMailList> innerMailList) {
		this.innerMailList = innerMailList;
	}
}