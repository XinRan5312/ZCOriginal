package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询资金流水.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchMoneyRecordListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchMoneyRecordListResponse#getMoneyRecordList
	 * 
	 */
	public static class ElementMoneyRecordList {

		private String accountBalance;
		private String amount;
		private String amtUserId;
		private String amtUserNickname;
		private String freezeBalance;
		private String fromUserId;
		private String fromUserNickname;
		private String loanId;
		private String loanPortraitPath;
		private String loanTitle;
		private String toUserId;
		private String toUserNickname;
		private String tradeChannel;
		private String tradeComment;
		private String tradeId;
		private String tradeTime;
		private String tradeType;

		/**
		 * @return 此交易完成后的账户余额
		 */
		public String getAccountBalance() {
			return accountBalance;
		}

		public void setAccountBalance(String accountBalance) {
			this.accountBalance = accountBalance;
		}

		/**
		 * @return 交易金额
		 */
		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		/**
		 * @return 资金账户(操作的资金主体)
		 */
		public String getAmtUserId() {
			return amtUserId;
		}

		public void setAmtUserId(String amtUserId) {
			this.amtUserId = amtUserId;
		}

		/**
		 * @return 资金账户
		 */
		public String getAmtUserNickname() {
			return amtUserNickname;
		}

		public void setAmtUserNickname(String amtUserNickname) {
			this.amtUserNickname = amtUserNickname;
		}

		/**
		 * @return 此交易完成后的账户投标冻结余额
		 */
		public String getFreezeBalance() {
			return freezeBalance;
		}

		public void setFreezeBalance(String freezeBalance) {
			this.freezeBalance = freezeBalance;
		}

		/**
		 * @return 资金汇出账户的userid
		 */
		public String getFromUserId() {
			return fromUserId;
		}

		public void setFromUserId(String fromUserId) {
			this.fromUserId = fromUserId;
		}

		/**
		 * @return 资金汇出账户的昵称
		 */
		public String getFromUserNickname() {
			return fromUserNickname;
		}

		public void setFromUserNickname(String fromUserNickname) {
			this.fromUserNickname = fromUserNickname;
		}

		/**
		 * @return 关联的loanId
		 */
		public String getLoanId() {
			return loanId;
		}

		public void setLoanId(String loanId) {
			this.loanId = loanId;
		}

		/**
		 * @return 标头像图片的路径
		 */
		public String getLoanPortraitPath() {
			return loanPortraitPath;
		}

		public void setLoanPortraitPath(String loanPortraitPath) {
			this.loanPortraitPath = loanPortraitPath;
		}

		/**
		 * @return 项目名称
		 */
		public String getLoanTitle() {
			return loanTitle;
		}

		public void setLoanTitle(String loanTitle) {
			this.loanTitle = loanTitle;
		}

		/**
		 * @return 资金汇入账户的userid
		 */
		public String getToUserId() {
			return toUserId;
		}

		public void setToUserId(String toUserId) {
			this.toUserId = toUserId;
		}

		/**
		 * @return 资金汇入账户
		 */
		public String getToUserNickname() {
			return toUserNickname;
		}

		public void setToUserNickname(String toUserNickname) {
			this.toUserNickname = toUserNickname;
		}

		/**
		 * @return 交易渠道
		 */
		public String getTradeChannel() {
			return tradeChannel;
		}

		public void setTradeChannel(String tradeChannel) {
			this.tradeChannel = tradeChannel;
		}

		/**
		 * @return 交易备注
		 */
		public String getTradeComment() {
			return tradeComment;
		}

		public void setTradeComment(String tradeComment) {
			this.tradeComment = tradeComment;
		}

		/**
		 * @return 交易表Id
		 */
		public String getTradeId() {
			return tradeId;
		}

		public void setTradeId(String tradeId) {
			this.tradeId = tradeId;
		}

		/**
		 * @return 交易时间
		 */
		public String getTradeTime() {
			return tradeTime;
		}

		public void setTradeTime(String tradeTime) {
			this.tradeTime = tradeTime;
		}

		/**
		 * @return 流水类型
		 */
		public String getTradeType() {
			return tradeType;
		}

		public void setTradeType(String tradeType) {
			this.tradeType = tradeType;
		}
	}

	private List<ElementMoneyRecordList> moneyRecordList;

	public SearchMoneyRecordListResponse() {
		super();
		setMessageId("searchMoneyRecordList");
	}


	/**
	 * @return 资金流水列表
	 */
	public List<ElementMoneyRecordList> getMoneyRecordList() {
		return moneyRecordList;
	}

	public void setMoneyRecordList(List<ElementMoneyRecordList> moneyRecordList) {
		this.moneyRecordList = moneyRecordList;
	}
}