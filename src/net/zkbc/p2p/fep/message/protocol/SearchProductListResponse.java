package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询借款产品列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchProductListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchProductListResponse#getProductList
	 * 
	 */
	public static class ElementProductList {

		private String amountlimitb;
		private String amountlimite;
		private String appfile;
		private String appreq;
		private String id;
		private String name;
		private String productmerit;
		private String repaytype;
		private String termunit;
		private Integer borrowtermb;
		private Integer borrowterme;
		private Double deposit;
		private Double fees;
		private Double interestrateb;
		private Double interestratee;

		/**
		 * @return 金额范围上限
		 */
		public String getAmountlimitb() {
			return amountlimitb;
		}

		public void setAmountlimitb(String amountlimitb) {
			this.amountlimitb = amountlimitb;
		}

		/**
		 * @return 金额范围下限
		 */
		public String getAmountlimite() {
			return amountlimite;
		}

		public void setAmountlimite(String amountlimite) {
			this.amountlimite = amountlimite;
		}

		/**
		 * @return 申请资料
		 */
		public String getAppfile() {
			return appfile;
		}

		public void setAppfile(String appfile) {
			this.appfile = appfile;
		}

		/**
		 * @return 申请条件
		 */
		public String getAppreq() {
			return appreq;
		}

		public void setAppreq(String appreq) {
			this.appreq = appreq;
		}

		/**
		 * @return 产品标识
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 产品名称
		 */
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return 产品特点
		 */
		public String getProductmerit() {
			return productmerit;
		}

		public void setProductmerit(String productmerit) {
			this.productmerit = productmerit;
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
		 * @return 借款期限单位
		 */
		public String getTermunit() {
			return termunit;
		}

		public void setTermunit(String termunit) {
			this.termunit = termunit;
		}

		/**
		 * @return 借款期限上限
		 */
		public Integer getBorrowtermb() {
			return borrowtermb;
		}

		public void setBorrowtermb(Integer borrowtermb) {
			this.borrowtermb = borrowtermb;
		}

		/**
		 * @return 借款期限下限
		 */
		public Integer getBorrowterme() {
			return borrowterme;
		}

		public void setBorrowterme(Integer borrowterme) {
			this.borrowterme = borrowterme;
		}

		/**
		 * @return 风险保证金
		 */
		public Double getDeposit() {
			return deposit;
		}

		public void setDeposit(Double deposit) {
			this.deposit = deposit;
		}

		/**
		 * @return 平台手续费
		 */
		public Double getFees() {
			return fees;
		}

		public void setFees(Double fees) {
			this.fees = fees;
		}

		/**
		 * @return 年化利率上限
		 */
		public Double getInterestrateb() {
			return interestrateb;
		}

		public void setInterestrateb(Double interestrateb) {
			this.interestrateb = interestrateb;
		}

		/**
		 * @return 年化利率下限
		 */
		public Double getInterestratee() {
			return interestratee;
		}

		public void setInterestratee(Double interestratee) {
			this.interestratee = interestratee;
		}
	}

	private List<ElementProductList> productList;

	public SearchProductListResponse() {
		super();
		setMessageId("searchProductList");
	}


	/**
	 * @return 产品列表
	 */
	public List<ElementProductList> getProductList() {
		return productList;
	}

	public void setProductList(List<ElementProductList> productList) {
		this.productList = productList;
	}
}