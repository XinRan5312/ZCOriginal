package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取借款用途列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetLoanUseListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetLoanUseListResponse#getLoanUseList
	 * 
	 */
	public static class ElementLoanUseList {

		private String id;
		private String name;
		private String valstring;

		/**
		 * @return 借款用途id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 借款用途名称
		 */
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return 借款用途显示与否1-显示2-不显示
		 */
		public String getValstring() {
			return valstring;
		}

		public void setValstring(String valstring) {
			this.valstring = valstring;
		}
	}

	private List<ElementLoanUseList> loanUseList;

	public GetLoanUseListResponse() {
		super();
		setMessageId("getLoanUseList");
	}


	/**
	 * @return 借款用途列表
	 */
	public List<ElementLoanUseList> getLoanUseList() {
		return loanUseList;
	}

	public void setLoanUseList(List<ElementLoanUseList> loanUseList) {
		this.loanUseList = loanUseList;
	}
}