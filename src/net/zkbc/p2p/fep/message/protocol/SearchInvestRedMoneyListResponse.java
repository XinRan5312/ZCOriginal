package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取投资可用红包列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchInvestRedMoneyListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchInvestRedMoneyListResponse#getInvestRedMoneyList
	 * 
	 */
	public static class ElementInvestRedMoneyList {

		private String amount;
		private String enddate;
		private String id;
		private String name;
		private String recievedate;
		private String requirement;
		private String startdate;
		private String status;
		private String userange;
		private String way;
		private boolean isChecked;

		/**
		 * @return 是否被选中
		 */
		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}

		public String getWay() {
			return way;
		}

		public void setWay(String way) {
			this.way = way;
		}

		/**
		 * @return 红包金额
		 */
		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		/**
		 * @return 过期时间
		 */
		public String getEnddate() {
			return enddate;
		}

		public void setEnddate(String enddate) {
			this.enddate = enddate;
		}

		/**
		 * @return 红包id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 红包名称
		 */
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return 获得时间
		 */
		public String getRecievedate() {
			return recievedate;
		}

		public void setRecievedate(String recievedate) {
			this.recievedate = recievedate;
		}

		/**
		 * @return 最小投资额
		 */
		public String getRequirement() {
			return requirement;
		}

		public void setRequirement(String requirement) {
			this.requirement = requirement;
		}

		/**
		 * @return 生效时间
		 */
		public String getStartdate() {
			return startdate;
		}

		public void setStartdate(String startdate) {
			this.startdate = startdate;
		}

		/**
		 * @return 红包状态
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 使用范围
		 */
		public String getUserange() {
			return userange;
		}

		public void setUserange(String userange) {
			this.userange = userange;
		}
	}

	private List<ElementInvestRedMoneyList> investRedMoneyList;

	public SearchInvestRedMoneyListResponse() {
		super();
		setMessageId("searchInvestRedMoneyList");
	}


	/**
	 * @return 投资红包列表
	 */
	public List<ElementInvestRedMoneyList> getInvestRedMoneyList() {
		return investRedMoneyList;
	}

	public void setInvestRedMoneyList(List<ElementInvestRedMoneyList> investRedMoneyList) {
		this.investRedMoneyList = investRedMoneyList;
	}
}