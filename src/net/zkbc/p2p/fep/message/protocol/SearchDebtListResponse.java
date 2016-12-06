package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询债权列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchDebtListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchDebtListResponse#getDebtList
	 * 
	 */
	public static class ElementDebtList {

		private String annualinterestrate;
		private String fee;
		private String lefttermcount;
		private String nextamount;
		private String nextrepaydate;
		private String repaytype;
		private String soldprice;
		private String status;
		private String title;
		private String tobetotalcollection;
		private String type;
		private String value;
		private Integer borrowid;
		private Integer debtid;
		private Integer loanid;

		/**
		 * @return 年化利率
		 */
		public String getAnnualinterestrate() {
			return annualinterestrate;
		}

		public void setAnnualinterestrate(String annualinterestrate) {
			this.annualinterestrate = annualinterestrate;
		}

		/**
		 * @return 手续费
		 */
		public String getFee() {
			return fee;
		}

		public void setFee(String fee) {
			this.fee = fee;
		}

		/**
		 * @return 剩余期限
		 */
		public String getLefttermcount() {
			return lefttermcount;
		}

		public void setLefttermcount(String lefttermcount) {
			this.lefttermcount = lefttermcount;
		}

		/**
		 * @return 下期还款金额
		 */
		public String getNextamount() {
			return nextamount;
		}

		public void setNextamount(String nextamount) {
			this.nextamount = nextamount;
		}

		/**
		 * @return 下期还款日
		 */
		public String getNextrepaydate() {
			return nextrepaydate;
		}

		public void setNextrepaydate(String nextrepaydate) {
			this.nextrepaydate = nextrepaydate;
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
		 * @return 转让价格
		 */
		public String getSoldprice() {
			return soldprice;
		}

		public void setSoldprice(String soldprice) {
			this.soldprice = soldprice;
		}

		/**
		 * @return 转让状态：1-转让中，2-已结束
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
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

		/**
		 * @return 待收本息
		 */
		public String getTobetotalcollection() {
			return tobetotalcollection;
		}

		public void setTobetotalcollection(String tobetotalcollection) {
			this.tobetotalcollection = tobetotalcollection;
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
		 * @return 债权价值
		 */
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return 借款人唯一标识
		 */
		public Integer getBorrowid() {
			return borrowid;
		}

		public void setBorrowid(Integer borrowid) {
			this.borrowid = borrowid;
		}

		/**
		 * @return 债权唯一标识
		 */
		public Integer getDebtid() {
			return debtid;
		}

		public void setDebtid(Integer debtid) {
			this.debtid = debtid;
		}

		/**
		 * @return 借款唯一标识
		 */
		public Integer getLoanid() {
			return loanid;
		}

		public void setLoanid(Integer loanid) {
			this.loanid = loanid;
		}
	}

	private Integer totalrows;
	private List<ElementDebtList> debtList;

	public SearchDebtListResponse() {
		super();
		setMessageId("searchDebtList");
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
	 * @return 债权列表
	 */
	public List<ElementDebtList> getDebtList() {
		return debtList;
	}

	public void setDebtList(List<ElementDebtList> debtList) {
		this.debtList = debtList;
	}
}