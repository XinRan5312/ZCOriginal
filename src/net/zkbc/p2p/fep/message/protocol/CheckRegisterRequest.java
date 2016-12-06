package net.zkbc.p2p.fep.message.protocol;

/**
 * 检查用户名和邮箱是否存在.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckRegisterRequest extends RequestSupport {

	private String email;
	private String nickname;
	private String phoneNumber;

	public CheckRegisterRequest() {
		super();
		setMessageId("checkRegister");
	}	

	/**
	 * @return 邮箱
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	 * @return 手机号码
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}