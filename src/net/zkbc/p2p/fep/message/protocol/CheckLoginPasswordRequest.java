package net.zkbc.p2p.fep.message.protocol;

/**
 * 判断登录密码是否正确.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckLoginPasswordRequest extends RequestSupport {

	private String loginpassword;

	public CheckLoginPasswordRequest() {
		super();
		setMessageId("checkLoginPassword");
	}	

	/**
	 * @return 登录密码
	 */
	public String getLoginpassword() {
		return loginpassword;
	}

	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}
}