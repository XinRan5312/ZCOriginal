package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取统计信息（个人信息编辑展示）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPersonStatisticsResponse extends ResponseSupport {

	private String accountBalance;
	private String frostMoney;
	private String orderCount;
	private String recCount;

	public GetPersonStatisticsResponse() {
		super();
		setMessageId("getPersonStatistics");
	}


	/**
	 * @return 账户余额
	 */
	public String getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}

	/**
	 * @return 投资冻结金额
	 */
	public String getFrostMoney() {
		return frostMoney;
	}

	public void setFrostMoney(String frostMoney) {
		this.frostMoney = frostMoney;
	}

	/**
	 * @return 订单总数
	 */
	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	/**
	 * @return 已收货个数
	 */
	public String getRecCount() {
		return recCount;
	}

	public void setRecCount(String recCount) {
		this.recCount = recCount;
	}
}