package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取用户站内信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMessageInfoListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetMessageInfoListResponse#getMessageList
	 * 
	 */
	public static class ElementMessageList {

		private String content;
		private String id;
		private String isread;
		private String messagetype;
		private String sendtime;

		/**
		 * @return 站内信内容
		 */
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		/**
		 * @return 站内信id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 是否已读：0-未读，1-已读
		 */
		public String getIsread() {
			return isread;
		}

		public void setIsread(String isread) {
			this.isread = isread;
		}

		/**
		 * @return 站内信类型
		 */
		public String getMessagetype() {
			return messagetype;
		}

		public void setMessagetype(String messagetype) {
			this.messagetype = messagetype;
		}

		/**
		 * @return 发送时间
		 */
		public String getSendtime() {
			return sendtime;
		}

		public void setSendtime(String sendtime) {
			this.sendtime = sendtime;
		}
	}

	private Integer totalrows;
	private List<ElementMessageList> messageList;

	public GetMessageInfoListResponse() {
		super();
		setMessageId("getMessageInfoList");
	}


	/**
	 * @return 总条数
	 */
	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	/**
	 * @return 消息列表
	 */
	public List<ElementMessageList> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<ElementMessageList> messageList) {
		this.messageList = messageList;
	}
}