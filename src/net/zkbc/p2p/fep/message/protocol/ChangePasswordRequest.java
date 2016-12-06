package net.zkbc.p2p.fep.message.protocol;

/**
 * 修改密码.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ChangePasswordRequest extends RequestSupport {

	private String loginname;
	private String newpassword;
	private String origpassword;

	public ChangePasswordRequest() {
		super();
		setMessageId("changePassword");
	}	

	/**
	 * @return 登录名称
	 */
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	/**
	 * @return 新密码
	 */
	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	/**
	 * @return 原密码
	 */
	public String getOrigpassword() {
		return origpassword;
	}

	public void setOrigpassword(String origpassword) {
		this.origpassword = origpassword;
	}
}