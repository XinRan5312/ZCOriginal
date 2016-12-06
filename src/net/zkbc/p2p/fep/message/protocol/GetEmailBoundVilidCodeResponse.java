package net.zkbc.p2p.fep.message.protocol;

/**
 * 向邮箱发送验证码.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetEmailBoundVilidCodeResponse extends ResponseSupport {

	private String vocode;

	public GetEmailBoundVilidCodeResponse() {
		super();
		setMessageId("getEmailBoundVilidCode");
	}


	/**
	 * @return 验证码
	 */
	public String getVocode() {
		return vocode;
	}

	public void setVocode(String vocode) {
		this.vocode = vocode;
	}
}