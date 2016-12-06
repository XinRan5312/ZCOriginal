package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取已邀请的好友列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetInviteFriendListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetInviteFriendListResponse#getInviteFriendList
	 * 
	 */
	public static class ElementInviteFriendList {

		private String inviteeregdate;
		private String moneystatus;
		private String nickname;
		private String redmoneyamount;
		private String statu;
		private String way;

		public String getWay() {
			return way;
		}

		public void setWay(String way) {
			this.way = way;
		}

		/**
		 * @return 被邀请者注册时间
		 */
		public String getInviteeregdate() {
			return inviteeregdate;
		}

		public void setInviteeregdate(String inviteeregdate) {
			this.inviteeregdate = inviteeregdate;
		}

		/**
		 * @return 是否生效
		 */
		public String getMoneystatus() {
			return moneystatus;
		}

		public void setMoneystatus(String moneystatus) {
			this.moneystatus = moneystatus;
		}

		/**
		 * @return 昵称
		 */
		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		/**
		 * @return 邀请奖励
		 */
		public String getRedmoneyamount() {
			return redmoneyamount;
		}

		public void setRedmoneyamount(String redmoneyamount) {
			this.redmoneyamount = redmoneyamount;
		}

		/**
		 * @return 用户状态
		 */
		public String getStatu() {
			return statu;
		}

		public void setStatu(String statu) {
			this.statu = statu;
		}
	}

	private String invitemoney;
	private String invitenum;
	private List<ElementInviteFriendList> inviteFriendList;

	public GetInviteFriendListResponse() {
		super();
		setMessageId("getInviteFriendList");
	}


	/**
	 * @return 邀请获取的奖励
	 */
	public String getInvitemoney() {
		return invitemoney;
	}

	public void setInvitemoney(String invitemoney) {
		this.invitemoney = invitemoney;
	}

	/**
	 * @return 邀请人数
	 */
	public String getInvitenum() {
		return invitenum;
	}

	public void setInvitenum(String invitenum) {
		this.invitenum = invitenum;
	}

	/**
	 * @return 好友列表
	 */
	public List<ElementInviteFriendList> getInviteFriendList() {
		return inviteFriendList;
	}

	public void setInviteFriendList(List<ElementInviteFriendList> inviteFriendList) {
		this.inviteFriendList = inviteFriendList;
	}
}