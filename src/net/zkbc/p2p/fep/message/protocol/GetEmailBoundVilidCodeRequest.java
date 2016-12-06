package net.zkbc.p2p.fep.message.protocol;

/**
 * 向邮箱发送验证码.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetEmailBoundVilidCodeRequest extends RequestSupport {

	private String email;

	public GetEmailBoundVilidCodeRequest() {
		super();
		setMessageId("getEmailBoundVilidCode");
	}	

	/**
	 * @return 接受验证码的邮箱
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}