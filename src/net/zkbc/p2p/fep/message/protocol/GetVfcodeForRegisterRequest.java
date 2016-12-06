package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取注册验证码.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetVfcodeForRegisterRequest extends RequestSupport {

	private String phonenumber;

	public GetVfcodeForRegisterRequest() {
		super();
		setMessageId("getVfcodeForRegister");
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
}