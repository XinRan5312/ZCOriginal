package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询还款记录.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchLoanphaseListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchLoanphaseListResponse#getPhaseList
	 * 
	 */
	public static class ElementPhaseList {

		private String loanid;
		private String phasenumber;
		private String plannedtermamount;
		private String repaydate;
		private String stattext;
		private Double remanentamount;
		private Double repayedamount;

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
		 * @return 期数
		 */
		public String getPhasenumber() {
			return phasenumber;
		}

		public void setPhasenumber(String phasenumber) {
			this.phasenumber = phasenumber;
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
		 * @return 还款日期
		 */
		public String getRepaydate() {
			return repaydate;
		}

		public void setRepaydate(String repaydate) {
			this.repaydate = repaydate;
		}

		/**
		 * @return 还款状态：已还、未还
		 */
		public String getStattext() {
			return stattext;
		}

		public void setStattext(String stattext) {
			this.stattext = stattext;
		}

		/**
		 * @return 剩余本息
		 */
		public Double getRemanentamount() {
			return remanentamount;
		}

		public void setRemanentamount(Double remanentamount) {
			this.remanentamount = remanentamount;
		}

		/**
		 * @return 已还本息
		 */
		public Double getRepayedamount() {
			return repayedamount;
		}

		public void setRepayedamount(Double repayedamount) {
			this.repayedamount = repayedamount;
		}
	}

	private List<ElementPhaseList> phaseList;

	public SearchLoanphaseListResponse() {
		super();
		setMessageId("searchLoanphaseList");
	}


	/**
	 * @return 还款列表
	 */
	public List<ElementPhaseList> getPhaseList() {
		return phaseList;
	}

	public void setPhaseList(List<ElementPhaseList> phaseList) {
		this.phaseList = phaseList;
	}
}