package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取用户的提现状态.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetCashApplyStatusResponse extends ResponseSupport {

	private String amount;
	private String wdAppStatus;

	public GetCashApplyStatusResponse() {
		super();
		setMessageId("getCashApplyStatus");
	}


	/**
	 * @return 金额
	 */
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return 00-初始化 10-申请中 20-批准 30-否决
	 */
	public String getWdAppStatus() {
		return wdAppStatus;
	}

	public void setWdAppStatus(String wdAppStatus) {
		this.wdAppStatus = wdAppStatus;
	}
}