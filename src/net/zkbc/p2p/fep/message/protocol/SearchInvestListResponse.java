package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询散标投资列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchInvestListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchInvestListResponse#getInvestList
	 * 
	 */
	public static class ElementInvestList {

		private String amount;
		private String beginamount;
		private String borrowid;
		private String endtime;
		private String id;
		private String increaseamount;
		private String interest;
		private String loanid;
		private String loanpic;
		private String monthlyamount;
		private String nextamount;
		private String prepayment;
		private String producttype;
		private String repayedamount;
		private String repaytype;
		private String safetype;
		private String status;
		private String subjectamount;
		private String term;
		private String title;
		private String type;
		private String use;
		private Double progress;

		/**
		 * @return 标的总额
		 */
		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		/**
		 * @return 起投金额
		 */
		public String getBeginamount() {
			return beginamount;
		}

		public void setBeginamount(String beginamount) {
			this.beginamount = beginamount;
		}

		/**
		 * @return 借款人id
		 */
		public String getBorrowid() {
			return borrowid;
		}

		public void setBorrowid(String borrowid) {
			this.borrowid = borrowid;
		}

		/**
		 * @return 结束时间
		 */
		public String getEndtime() {
			return endtime;
		}

		public void setEndtime(String endtime) {
			this.endtime = endtime;
		}

		/**
		 * @return 投标标识
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 递增金额
		 */
		public String getIncreaseamount() {
			return increaseamount;
		}

		public void setIncreaseamount(String increaseamount) {
			this.increaseamount = increaseamount;
		}

		/**
		 * @return 年利率
		 */
		public String getInterest() {
			return interest;
		}

		public void setInterest(String interest) {
			this.interest = interest;
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
		 * @return 图片 id
		 */
		public String getLoanpic() {
			return loanpic;
		}

		public void setLoanpic(String loanpic) {
			this.loanpic = loanpic;
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
		 * @return 待还本息
		 */
		public String getNextamount() {
			return nextamount;
		}

		public void setNextamount(String nextamount) {
			this.nextamount = nextamount;
		}

		/**
		 * @return 提前还款费率
		 */
		public String getPrepayment() {
			return prepayment;
		}

		public void setPrepayment(String prepayment) {
			this.prepayment = prepayment;
		}

		/**
		 * @return 产品类型
		 */
		public String getProducttype() {
			return producttype;
		}

		public void setProducttype(String producttype) {
			this.producttype = producttype;
		}

		/**
		 * @return 已还本息
		 */
		public String getRepayedamount() {
			return repayedamount;
		}

		public void setRepayedamount(String repayedamount) {
			this.repayedamount = repayedamount;
		}

		/**
		 * @return 还款方式
		 */
		public String getRepaytype() {
			return repaytype;
		}

		public void setRepaytype(String repaytype) {
			this.repaytype = repaytype;
		}

		/**
		 * @return 保障方式
		 */
		public String getSafetype() {
			return safetype;
		}

		public void setSafetype(String safetype) {
			this.safetype = safetype;
		}

		/**
		 * @return 投标状态
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
		 * @return 还款期限
		 */
		public String getTerm() {
			return term;
		}

		public void setTerm(String term) {
			this.term = term;
		}

		/**
		 * @return 投资标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * @return 标的类型：0-个人,1-企业
		 */
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		/**
		 * @return 借款用途
		 */
		public String getUse() {
			return use;
		}

		public void setUse(String use) {
			this.use = use;
		}

		/**
		 * @return 投标进度
		 */
		public Double getProgress() {
			return progress;
		}

		public void setProgress(Double progress) {
			this.progress = progress;
		}
	}

	private Integer totalrows;
	private List<ElementInvestList> investList;

	public SearchInvestListResponse() {
		super();
		setMessageId("searchInvestList");
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
	 * @return 散标列表
	 */
	public List<ElementInvestList> getInvestList() {
		return investList;
	}

	public void setInvestList(List<ElementInvestList> investList) {
		this.investList = investList;
	}
}