package net.zkbc.p2p.fep.message.protocol;

/**
 * 注册获取验证码(不使用，请使用getVfcodeForRegister).服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetRegAuthCodeByPhoneResponse extends ResponseSupport {

	private String returnCode;

	public GetRegAuthCodeByPhoneResponse() {
		super();
		setMessageId("getRegAuthCodeByPhone");
	}


	/**
	 * @return 执行结果（success-成功fail-失败）不使用请使用getVfcodeForRegister
	 */
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
}