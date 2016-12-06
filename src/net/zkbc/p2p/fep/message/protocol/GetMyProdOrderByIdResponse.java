package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据订单id获取产品订单详情.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyProdOrderByIdResponse extends ResponseSupport {

	private String addr;
	private String commodityNam;
	private String custId;
	private String custMobile;
	private String custName;
	private String custPortraitAddr;
	private String custRemark;
	private String feeFreight;
	private String orderId;
	private String orderNo;
	private String orderStatus;
	private String prjBStatus;
	private String prjId;
	private String prjNam;
	private String realPayAmt;
	private String recvMobile;
	private String recvNam;
	private String rwdContent;
	private String rwdPicFileAddr;
	private String totalPrice;
	private String isCancelOrder;

	public GetMyProdOrderByIdResponse() {
		super();
		setMessageId("getMyProdOrderById");
	}

	/**
	 * @return 是否可以取消订单  1-是 0-否
	 */
	public String getIsCancelOrder() {
		return isCancelOrder;
	}

	public void setIsCancelOrder(String isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}

	/**
	 * @return 收货人地址
	 */
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return 回报商品名称
	 */
	public String getCommodityNam() {
		return commodityNam;
	}

	public void setCommodityNam(String commodityNam) {
		this.commodityNam = commodityNam;
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
	 * @return 客户备注
	 */
	public String getCustRemark() {
		return custRemark;
	}

	public void setCustRemark(String custRemark) {
		this.custRemark = custRemark;
	}

	/**
	 * @return 运费
	 */
	public String getFeeFreight() {
		return feeFreight;
	}

	public void setFeeFreight(String feeFreight) {
		this.feeFreight = feeFreight;
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
	 * @return 收货人电话
	 */
	public String getRecvMobile() {
		return recvMobile;
	}

	public void setRecvMobile(String recvMobile) {
		this.recvMobile = recvMobile;
	}

	/**
	 * @return 收货人
	 */
	public String getRecvNam() {
		return recvNam;
	}

	public void setRecvNam(String recvNam) {
		this.recvNam = recvNam;
	}

	/**
	 * @return 回报内容
	 */
	public String getRwdContent() {
		return rwdContent;
	}

	public void setRwdContent(String rwdContent) {
		this.rwdContent = rwdContent;
	}

	/**
	 * @return 回报展示地址
	 */
	public String getRwdPicFileAddr() {
		return rwdPicFileAddr;
	}

	public void setRwdPicFileAddr(String rwdPicFileAddr) {
		this.rwdPicFileAddr = rwdPicFileAddr;
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