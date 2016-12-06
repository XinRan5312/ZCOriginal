package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询借款列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchBorrowListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchBorrowListResponse#getBorrowList
	 * 
	 */
	public static class ElementBorrowList {

		private String amount;
		private String id;
		private String intrest;
		private String loanid;
		private String monthlyamount;
		private String nextrepaydate;
		private String plannedtermamount;
		private String progress;
		private String status;
		private String subjectamount;
		private String term;
		private String title;

		/**
		 * @return 借款金额
		 */
		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		/**
		 * @return 借款标识
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 年利率
		 */
		public String getIntrest() {
			return intrest;
		}

		public void setIntrest(String intrest) {
			this.intrest = intrest;
		}

		/**
		 * @return 借款标识
		 */
		public String getLoanid() {
			return loanid;
		}

		public void setLoanid(String loanid) {
			this.loanid = loanid;
		}

		/**
		 * @return 月还本息
		 */
		public String getMonthlyamount() {
			return monthlyamount;
		}

		public void setMonthlyamount(String monthlyamount) {
			this.monthlyamount = monthlyamount;
		}

		/**
		 * @return 下一还款日
		 */
		public String getNextrepaydate() {
			return nextrepaydate;
		}

		public void setNextrepaydate(String nextrepaydate) {
			this.nextrepaydate = nextrepaydate;
		}

		/**
		 * @return 应还金额
		 */
		public String getPlannedtermamount() {
			return plannedtermamount;
		}

		public void setPlannedtermamount(String plannedtermamount) {
			this.plannedtermamount = plannedtermamount;
		}

		/**
		 * @return 投标进度
		 */
		public String getProgress() {
			return progress;
		}

		public void setProgress(String progress) {
			this.progress = progress;
		}

		/**
		 * @return 状态
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 可投金额
		 */
		public String getSubjectamount() {
			return subjectamount;
		}

		public void setSubjectamount(String subjectamount) {
			this.subjectamount = subjectamount;
		}

		/**
		 * @return 期限
		 */
		public String getTerm() {
			return term;
		}

		public void setTerm(String term) {
			this.term = term;
		}

		/**
		 * @return 借款标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	private Integer totalrows;
	private List<ElementBorrowList> borrowList;

	public SearchBorrowListResponse() {
		super();
		setMessageId("searchBorrowList");
	}


	/**
	 * @return 总记录数
	 */
	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	/**
	 * @return 借款列表
	 */
	public List<ElementBorrowList> getBorrowList() {
		return borrowList;
	}

	public void setBorrowList(List<ElementBorrowList> borrowList) {
		this.borrowList = borrowList;
	}
}