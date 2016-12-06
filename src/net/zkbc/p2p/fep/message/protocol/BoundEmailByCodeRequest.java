package net.zkbc.p2p.fep.message.protocol;

/**
 * 绑定邮箱操作.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class BoundEmailByCodeRequest extends RequestSupport {

	private String email;
	private String verifycode;

	public BoundEmailByCodeRequest() {
		super();
		setMessageId("boundEmailByCode");
	}	

	/**
	 * @return 绑定的邮箱
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 验证码
	 */
	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
}