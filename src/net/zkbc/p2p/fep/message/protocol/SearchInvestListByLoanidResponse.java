package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询投资记录.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchInvestListByLoanidResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchInvestListByLoanidResponse#getInvestList
	 * 
	 */
	public static class ElementInvestList {

		private String amount;
		private String createtime;
		private String investor;

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
		 * @return 投资时间
		 */
		public String getCreatetime() {
			return createtime;
		}

		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}

		/**
		 * @return 投资人
		 */
		public String getInvestor() {
			return investor;
		}

		public void setInvestor(String investor) {
			this.investor = investor;
		}
	}

	private List<ElementInvestList> investList;

	public SearchInvestListByLoanidResponse() {
		super();
		setMessageId("searchInvestListByLoanid");
	}


	/**
	 * @return 投资列表
	 */
	public List<ElementInvestList> getInvestList() {
		return investList;
	}

	public void setInvestList(List<ElementInvestList> investList) {
		this.investList = investList;
	}
}