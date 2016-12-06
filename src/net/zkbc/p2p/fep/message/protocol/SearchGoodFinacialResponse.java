package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询优选理财列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchGoodFinacialResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchGoodFinacialResponse#getPlanList
	 * 
	 */
	public static class ElementPlanList {

		private String amount;
		private String annualinterestrate;
		private String biddingamount;
		private String createtime;
		private String description;
		private String exannualinterestrate;
		private String fulltime;
		private String mininvestamount;
		private String openendtime;
		private String opentime;
		private String recordnum;
		private String status;
		private String termcount;
		private String title;
		private String type;
		private Integer preferredplanid;

		/**
		 * @return 总额
		 */
		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		/**
		 * @return 实际利率
		 */
		public String getAnnualinterestrate() {
			return annualinterestrate;
		}

		public void setAnnualinterestrate(String annualinterestrate) {
			this.annualinterestrate = annualinterestrate;
		}

		/**
		 * @return 已投标的金额
		 */
		public String getBiddingamount() {
			return biddingamount;
		}

		public void setBiddingamount(String biddingamount) {
			this.biddingamount = biddingamount;
		}

		/**
		 * @return 标建立的时间
		 */
		public String getCreatetime() {
			return createtime;
		}

		public void setCreatetime(String createtime) {
			this.createtime = createtime;
		}

		/**
		 * @return 借款描述
		 */
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return 利率
		 */
		public String getExannualinterestrate() {
			return exannualinterestrate;
		}

		public void setExannualinterestrate(String exannualinterestrate) {
			this.exannualinterestrate = exannualinterestrate;
		}

		/**
		 * @return 满标时间
		 */
		public String getFulltime() {
			return fulltime;
		}

		public void setFulltime(String fulltime) {
			this.fulltime = fulltime;
		}

		/**
		 * @return 起投金额
		 */
		public String getMininvestamount() {
			return mininvestamount;
		}

		public void setMininvestamount(String mininvestamount) {
			this.mininvestamount = mininvestamount;
		}

		/**
		 * @return 招标结束时间
		 */
		public String getOpenendtime() {
			return openendtime;
		}

		public void setOpenendtime(String openendtime) {
			this.openendtime = openendtime;
		}

		/**
		 * @return 开标时间
		 */
		public String getOpentime() {
			return opentime;
		}

		public void setOpentime(String opentime) {
			this.opentime = opentime;
		}

		/**
		 * @return 记录条数
		 */
		public String getRecordnum() {
			return recordnum;
		}

		public void setRecordnum(String recordnum) {
			this.recordnum = recordnum;
		}

		/**
		 * @return 状态码
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 还款周期数量
		 */
		public String getTermcount() {
			return termcount;
		}

		public void setTermcount(String termcount) {
			this.termcount = termcount;
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
		 * @return 形式
		 */
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		/**
		 * @return 标编号
		 */
		public Integer getPreferredplanid() {
			return preferredplanid;
		}

		public void setPreferredplanid(Integer preferredplanid) {
			this.preferredplanid = preferredplanid;
		}
	}

	private Integer totalrows;
	private List<ElementPlanList> planList;

	public SearchGoodFinacialResponse() {
		super();
		setMessageId("searchGoodFinacial");
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
	 * @return 优选列表
	 */
	public List<ElementPlanList> getPlanList() {
		return planList;
	}

	public void setPlanList(List<ElementPlanList> planList) {
		this.planList = planList;
	}
}