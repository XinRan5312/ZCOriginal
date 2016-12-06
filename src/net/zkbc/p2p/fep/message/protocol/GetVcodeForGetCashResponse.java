package net.zkbc.p2p.fep.message.protocol;

/**
 * 提现获取验证码.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetVcodeForGetCashResponse extends ResponseSupport {

	private String vcode;

	public GetVcodeForGetCashResponse() {
		super();
		setMessageId("getVcodeForGetCash");
	}


	/**
	 * @return 验证码
	 */
	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
}