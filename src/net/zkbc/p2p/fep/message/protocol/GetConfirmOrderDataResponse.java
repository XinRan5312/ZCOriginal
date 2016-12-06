package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取订单确认数据(准备下订单).服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetConfirmOrderDataResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetConfirmOrderDataResponse#getUserAddressList
	 * 
	 */
	public static class ElementUserAddressList {

		private String addr;
		private String inUseAdd;
		private String recvMobile;
		private String recvNam;
		private String seqNo;

		/**
		 * @return 收货地址
		 */
		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		/**
		 * @return 是否默认地址 1-默认  0-不默认
		 */
		public String getInUseAdd() {
			return inUseAdd;
		}

		public void setInUseAdd(String inUseAdd) {
			this.inUseAdd = inUseAdd;
		}

		/**
		 * @return 收货人手机
		 */
		public String getRecvMobile() {
			return recvMobile;
		}

		public void setRecvMobile(String recvMobile) {
			this.recvMobile = recvMobile;
		}

		/**
		 * @return 收货人姓名
		 */
		public String getRecvNam() {
			return recvNam;
		}

		public void setRecvNam(String recvNam) {
			this.recvNam = recvNam;
		}

		/**
		 * @return 收货地址序号
		 */
		public String getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(String seqNo) {
			this.seqNo = seqNo;
		}
	}

	private String appId;
	private String commodityNam;
	private String id;
	private String isPrehotBuy;
	private String perSuppAmt;
	private String priPostage;
	private String prjId;
	private String rwdContent;
	private String rwdPicAddr;
	private List<ElementUserAddressList> userAddressList;

	public GetConfirmOrderDataResponse() {
		super();
		setMessageId("getConfirmOrderData");
	}


	/**
	 * @return 交易标识号
	 */
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return 商品名称
	 */
	public String getCommodityNam() {
		return commodityNam;
	}

	public void setCommodityNam(String commodityNam) {
		this.commodityNam = commodityNam;
	}

	/**
	 * @return 回报id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 预热阶段能否购买 1-可以购买 0-不允许购买
	 */
	public String getIsPrehotBuy() {
		return isPrehotBuy;
	}

	public void setIsPrehotBuy(String isPrehotBuy) {
		this.isPrehotBuy = isPrehotBuy;
	}

	/**
	 * @return 单数量支付金额
	 */
	public String getPerSuppAmt() {
		return perSuppAmt;
	}

	public void setPerSuppAmt(String perSuppAmt) {
		this.perSuppAmt = perSuppAmt;
	}

	/**
	 * @return 邮费
	 */
	public String getPriPostage() {
		return priPostage;
	}

	public void setPriPostage(String priPostage) {
		this.priPostage = priPostage;
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
	 * @return 回报内容
	 */
	public String getRwdContent() {
		return rwdContent;
	}

	public void setRwdContent(String rwdContent) {
		this.rwdContent = rwdContent;
	}

	/**
	 * @return 回报展示图片地址
	 */
	public String getRwdPicAddr() {
		return rwdPicAddr;
	}

	public void setRwdPicAddr(String rwdPicAddr) {
		this.rwdPicAddr = rwdPicAddr;
	}

	/**
	 * @return 收货地址列表
	 */
	public List<ElementUserAddressList> getUserAddressList() {
		return userAddressList;
	}

	public void setUserAddressList(List<ElementUserAddressList> userAddressList) {
		this.userAddressList = userAddressList;
	}
}