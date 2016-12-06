package net.zkbc.p2p.fep.message.protocol;

/**
 * 忘记密码.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ForgetPasswordRequest extends RequestSupport {

	private String mobile;

	public ForgetPasswordRequest() {
		super();
		setMessageId("forgetPassword");
	}	

	/**
	 * @return 手机号码
	 */
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}