package net.zkbc.p2p.fep.message.protocol;

/**
 * 注册获取验证码(不使用，请使用getVfcodeForRegister).客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetRegAuthCodeByPhoneRequest extends RequestSupport {

	private String phoneNum;

	public GetRegAuthCodeByPhoneRequest() {
		super();
		setMessageId("getRegAuthCodeByPhone");
	}	

	/**
	 * @return 手机号码（不使用） 请使用getVfcodeForRegister
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}