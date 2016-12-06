package net.zkbc.p2p.fep.message.protocol;

/**
 * 充值.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class RechargeRequest extends RequestSupport {

	private String amount;

	public RechargeRequest() {
		super();
		setMessageId("recharge");
	}	

	/**
	 * @return 充值金额
	 */
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}