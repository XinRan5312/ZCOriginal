package net.zkbc.p2p.fep.message.protocol;

/**
 * 检测用户申请情况（是否实名、是否跟投人、是否平台领投人）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckUserRightResponse extends ResponseSupport {

	private String isFollower;
	private String isIDcard;
	private String isUserLeader;

	public CheckUserRightResponse() {
		super();
		setMessageId("checkUserRight");
	}


	/**
	 * @return 是否普通投资资质认证 是-1 否-0 禁用-2 申请中-3
	 */
	public String getIsFollower() {
		return isFollower;
	}

	public void setIsFollower(String isFollower) {
		this.isFollower = isFollower;
	}

	/**
	 * @return 是否实名 是-1 否-0
	 */
	public String getIsIDcard() {
		return isIDcard;
	}

	public void setIsIDcard(String isIDcard) {
		this.isIDcard = isIDcard;
	}

	/**
	 * @return 是否平台领投人 是-1 否-0 禁用-2 申请中-3
	 */
	public String getIsUserLeader() {
		return isUserLeader;
	}

	public void setIsUserLeader(String isUserLeader) {
		this.isUserLeader = isUserLeader;
	}
}