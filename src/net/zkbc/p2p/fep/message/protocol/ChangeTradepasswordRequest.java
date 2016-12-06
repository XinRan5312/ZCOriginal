package net.zkbc.p2p.fep.message.protocol;

/**
 * 修改交易密码.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ChangeTradepasswordRequest extends RequestSupport {

	private String password;
	private String tradepassword;
	private String vfcode;

	public ChangeTradepasswordRequest() {
		super();
		setMessageId("changeTradepassword");
	}	

	/**
	 * @return 登录密码
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return 交易密码
	 */
	public String getTradepassword() {
		return tradepassword;
	}

	public void setTradepassword(String tradepassword) {
		this.tradepassword = tradepassword;
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