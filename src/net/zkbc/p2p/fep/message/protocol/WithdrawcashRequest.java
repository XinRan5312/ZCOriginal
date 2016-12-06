package net.zkbc.p2p.fep.message.protocol;

/**
 * 提现.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class WithdrawcashRequest extends RequestSupport {

	private String amount;
	private String paypassword;
	private String vcode;

	public WithdrawcashRequest() {
		super();
		setMessageId("withdrawcash");
	}	

	/**
	 * @return 提现金额
	 */
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return 提现密码
	 */
	public String getPaypassword() {
		return paypassword;
	}

	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}

	/**
	 * @return 提现验证码
	 */
	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}
}