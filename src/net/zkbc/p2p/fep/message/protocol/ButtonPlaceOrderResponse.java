package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 按钮立即下单（确认订单 下一步）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ButtonPlaceOrderResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.ButtonPlaceOrderResponse#getRedMoneyList
	 * 
	 */
	public static class ElementRedMoneyList {

		private String amount;
		private String end_date;
		private String id;
		private String requirement;
		private String start_date;
		private String usedAmount;
		private String _name;

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
		public String getEnd_date() {
			return end_date;
		}

		public void setEnd_date(String end_date) {
			this.end_date = end_date;
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
		 * @return 要求最低投资金额
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
		public String getStart_date() {
			return start_date;
		}

		public void setStart_date(String start_date) {
			this.start_date = start_date;
		}

		/**
		 * @return 红包已用金额
		 */
		public String getUsedAmount() {
			return usedAmount;
		}

		public void setUsedAmount(String usedAmount) {
			this.usedAmount = usedAmount;
		}

		/**
		 * @return 红包名称
		 */
		public String get_name() {
			return _name;
		}

		public void set_name(String _name) {
			this._name = _name;
		}
	}

	private String commissionAmt;
	private String createTime;
	private String orderId;
	private String orderNo;
	private String orderStatus;
	private String prjId;
	private String realPayAmt;
	private String resultCode;
	private String resultMessage;
	private List<ElementRedMoneyList> redMoneyList;

	public ButtonPlaceOrderResponse() {
		super();
		setMessageId("buttonPlaceOrder");
	}


	/**
	 * @return 佣金
	 */
	public String getCommissionAmt() {
		return commissionAmt;
	}

	public void setCommissionAmt(String commissionAmt) {
		this.commissionAmt = commissionAmt;
	}

	/**
	 * @return 订单创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return 订单id
	 */
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return 订单状态
	 */
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return 项目id
	 */
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * @return 实际付款金额
	 */
	public String getRealPayAmt() {
		return realPayAmt;
	}

	public void setRealPayAmt(String realPayAmt) {
		this.realPayAmt = realPayAmt;
	}

	/**
	 * @return 返回ture或者false
	 */
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return 错误信息
	 */
	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	/**
	 * @return 红包集合
	 */
	public List<ElementRedMoneyList> getRedMoneyList() {
		return redMoneyList;
	}

	public void setRedMoneyList(List<ElementRedMoneyList> redMoneyList) {
		this.redMoneyList = redMoneyList;
	}
}