package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取物流信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetOrderLogiInfoResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetOrderLogiInfoResponse#getLogiInfoList
	 * 
	 */
	public static class ElementLogiInfoList {

		private String createTime;
		private String logisticsCorpNam;
		private String logisticsNo;
		private String logisticsTime;
		private String memo;

		/**
		 * @return 创建时间
		 */
		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		/**
		 * @return 快递公司
		 */
		public String getLogisticsCorpNam() {
			return logisticsCorpNam;
		}

		public void setLogisticsCorpNam(String logisticsCorpNam) {
			this.logisticsCorpNam = logisticsCorpNam;
		}

		/**
		 * @return 快递单号
		 */
		public String getLogisticsNo() {
			return logisticsNo;
		}

		public void setLogisticsNo(String logisticsNo) {
			this.logisticsNo = logisticsNo;
		}

		/**
		 * @return 快递时间
		 */
		public String getLogisticsTime() {
			return logisticsTime;
		}

		public void setLogisticsTime(String logisticsTime) {
			this.logisticsTime = logisticsTime;
		}

		/**
		 * @return 备注
		 */
		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}
	}

	private List<ElementLogiInfoList> logiInfoList;

	public GetOrderLogiInfoResponse() {
		super();
		setMessageId("getOrderLogiInfo");
	}


	/**
	 * @return 
	 */
	public List<ElementLogiInfoList> getLogiInfoList() {
		return logiInfoList;
	}

	public void setLogiInfoList(List<ElementLogiInfoList> logiInfoList) {
		this.logiInfoList = logiInfoList;
	}
}