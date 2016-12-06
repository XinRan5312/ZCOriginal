package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取注册验证码.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetVfcodeForRegisterResponse extends ResponseSupport {

	private String vfcode;

	public GetVfcodeForRegisterResponse() {
		super();
		setMessageId("getVfcodeForRegister");
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