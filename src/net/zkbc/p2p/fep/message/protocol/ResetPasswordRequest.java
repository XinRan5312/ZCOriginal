package net.zkbc.p2p.fep.message.protocol;

/**
 * 重置密码.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ResetPasswordRequest extends RequestSupport {

	private String password;
	private String phonenumber;
	private String vfcode;

	public ResetPasswordRequest() {
		super();
		setMessageId("resetPassword");
	}	

	/**
	 * @return 新密码
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return 手机号码
	 */
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * @return 验证码
	 */
	public String getVfcode() {
		return vfcode;
	}

	public void setVfcode(String vfcode) {
		this.vfcode = vfcode;
	}
}