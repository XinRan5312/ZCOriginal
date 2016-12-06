package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取修改交易密码的验证码.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetVfcodeForTradepassRequest extends RequestSupport {

	private String phonenumber;

	public GetVfcodeForTradepassRequest() {
		super();
		setMessageId("getVfcodeForTradepass");
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