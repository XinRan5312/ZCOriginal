package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据订单id获取股权订单详情.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyStockOrderByIdResponse extends ResponseSupport {

	private String custId;
	private String custMobile;
	private String custName;
	private String custPortraitAddr;
	private String investor;
	private String investorMobile;
	private String orderId;
	private String orderNo;
	private String orderStatus;
	private String prjBStatus;
	private String prjId;
	private String prjNam;
	private String realPayAmt;
	private String stockPct;
	private String totalPrice;
	private String recvMobile;
	private String recvNam;
	private String recvAddress;
	private String isCancelOrder;

	public GetMyStockOrderByIdResponse() {
		super();
		setMessageId("getMyStockOrderById");
	}

	public String getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(String isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	public String getRecvMobile() {
		return recvMobile;
	}

	public void setRecvMobile(String recvMobile) {
		this.recvMobile = recvMobile;
	}

	public String getRecvNam() {
		return recvNam;
	}

	public void setRecvNam(String recvNam) {
		this.recvNam = recvNam;
	}

	public String getRecvAddress() {
		return recvAddress;
	}

	public void setRecvAddress(String recvAddress) {
		this.recvAddress = recvAddress;
	}

	/**
	 * @return 项目发起者id
	 */
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	/**
	 * @return 项目发起者电话
	 */
	public String getCustMobile() {
		return custMobile;
	}

	public void setCustMobile(String custMobile) {
		this.custMobile = custMobile;
	}

	/**
	 * @return 项目发起者昵称
	 */
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	/**
	 * @return 项目发起者头像地址
	 */
	public String getCustPortraitAddr() {
		return custPortraitAddr;
	}

	public void setCustPortraitAddr(String custPortraitAddr) {
		this.custPortraitAddr = custPortraitAddr;
	}

	/**
	 * @return 投资人
	 */
	public String getInvestor() {
		return investor;
	}

	public void setInvestor(String investor) {
		this.investor = investor;
	}

	/**
	 * @return 投资人电话
	 */
	public String getInvestorMobile() {
		return investorMobile;
	}

	public void setInvestorMobile(String investorMobile) {
		this.investorMobile = investorMobile;
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
	 * @return 订单编号
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
	 * @return 项目状态
	 *         待预热-10,预热中-20,待筹款-30,预热流标-35,筹款中-40,筹款结束-45,筹款满标-50,筹款流标-55,项目成功
	 *         -60,成功结项-70,失败结项-80
	 */
	public String getPrjBStatus() {
		return prjBStatus;
	}

	public void setPrjBStatus(String prjBStatus) {
		this.prjBStatus = prjBStatus;
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
	 * @return 项目名称
	 */
	public String getPrjNam() {
		return prjNam;
	}

	public void setPrjNam(String prjNam) {
		this.prjNam = prjNam;
	}

	/**
	 * @return 实际应支付总额
	 */
	public String getRealPayAmt() {
		return realPayAmt;
	}

	public void setRealPayAmt(String realPayAmt) {
		this.realPayAmt = realPayAmt;
	}

	/**
	 * @return 预计所占股比
	 */
	public String getStockPct() {
		return stockPct;
	}

	public void setStockPct(String stockPct) {
		this.stockPct = stockPct;
	}

	/**
	 * @return 订单总额
	 */
	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
}