package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取红包列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetRedMoneyListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetRedMoneyListResponse#getRedMoneyList
	 * 
	 */
	public static class ElementRedMoneyList {

		private String amount;
		private String enddate;
		private String name;
		private String recievedate;
		private String requirement;
		private String startdate;
		private String status;
		private String userange;
		private String way;

		/**
		 * @return 红包类型
		 */
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

	private Integer totalrows;
	private List<ElementRedMoneyList> redMoneyList;

	public GetRedMoneyListResponse() {
		super();
		setMessageId("getRedMoneyList");
	}

	/**
	 * @return 总条数
	 */
	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	/**
	 * @return 红包列表
	 */
	public List<ElementRedMoneyList> getRedMoneyList() {
		return redMoneyList;
	}

	public void setRedMoneyList(List<ElementRedMoneyList> redMoneyList) {
		this.redMoneyList = redMoneyList;
	}
}