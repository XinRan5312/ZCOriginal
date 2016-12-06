package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 模糊查询分支行.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchBankListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchBankListResponse#getBankList
	 * 
	 */
	public static class ElementBankList {

		private String bankcode;
		private String bankname;

		/**
		 * @return 行号
		 */
		public String getBankcode() {
			return bankcode;
		}

		public void setBankcode(String bankcode) {
			this.bankcode = bankcode;
		}

		/**
		 * @return 行名
		 */
		public String getBankname() {
			return bankname;
		}

		public void setBankname(String bankname) {
			this.bankname = bankname;
		}
	}

	private List<ElementBankList> bankList;

	public SearchBankListResponse() {
		super();
		setMessageId("searchBankList");
	}


	/**
	 * @return 银行列表
	 */
	public List<ElementBankList> getBankList() {
		return bankList;
	}

	public void setBankList(List<ElementBankList> bankList) {
		this.bankList = bankList;
	}
}