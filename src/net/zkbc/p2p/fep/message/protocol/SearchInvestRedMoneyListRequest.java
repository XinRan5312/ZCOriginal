package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取投资可用红包列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchInvestRedMoneyListRequest extends RequestSupport {

	private String amount;
	private String searchtype;

	public SearchInvestRedMoneyListRequest() {
		super();
		setMessageId("searchInvestRedMoneyList");
	}	

	/**
	 * @return 投资金额
	 */
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return 红包类型：默认全部   10-普通红包 20-定时红包
	 */
	public String getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(String searchtype) {
		this.searchtype = searchtype;
	}
}