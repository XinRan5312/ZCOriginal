package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取修改交易密码的验证码.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetVfcodeForTradepassResponse extends ResponseSupport {

	private String vcode;

	public GetVfcodeForTradepassResponse() {
		super();
		setMessageId("getVfcodeForTradepass");
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