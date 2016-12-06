package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 获取领投人页面的字典数据.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUserLeaderBookletResponse extends ResponseSupport implements
		Serializable {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetUserLeaderBookletResponse#getCompanyTypList
	 * 
	 */
	public static class ElementCompanyTypList implements Serializable {

		private String ccode;
		private String cdisplay;
		private String cenabled;
		private String ciconAddr;
		private String cvalue;

		/**
		 * @return 编码
		 */
		public String getCcode() {
			return ccode;
		}

		public void setCcode(String ccode) {
			this.ccode = ccode;
		}

		/**
		 * @return 字典显示名称
		 */
		public String getCdisplay() {
			return cdisplay;
		}

		public void setCdisplay(String cdisplay) {
			this.cdisplay = cdisplay;
		}

		/**
		 * @return 是否可用（1-可用 0-不可用）
		 */
		public String getCenabled() {
			return cenabled;
		}

		public void setCenabled(String cenabled) {
			this.cenabled = cenabled;
		}

		/**
		 * @return 图标地址
		 */
		public String getCiconAddr() {
			return ciconAddr;
		}

		public void setCiconAddr(String ciconAddr) {
			this.ciconAddr = ciconAddr;
		}

		/**
		 * @return 字典值
		 */
		public String getCvalue() {
			return cvalue;
		}

		public void setCvalue(String cvalue) {
			this.cvalue = cvalue;
		}
	}

	/**
	 * @see net.zkbc.p2p.message.protocol.GetUserLeaderBookletResponse#getInvestTimeList
	 * 
	 */
	public static class ElementInvestTimeList implements Serializable{

		private String code;
		private String display;
		private String enabled;
		private String iconAddr;
		private String value;

		/**
		 * @return 编码
		 */
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		/**
		 * @return 字典显示名称
		 */
		public String getDisplay() {
			return display;
		}

		public void setDisplay(String display) {
			this.display = display;
		}

		/**
		 * @return 是否可用（1-可用 0-不可用）
		 */
		public String getEnabled() {
			return enabled;
		}

		public void setEnabled(String enabled) {
			this.enabled = enabled;
		}

		/**
		 * @return 图标地址
		 */
		public String getIconAddr() {
			return iconAddr;
		}

		public void setIconAddr(String iconAddr) {
			this.iconAddr = iconAddr;
		}

		/**
		 * @return 字典值
		 */
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * @see net.zkbc.p2p.message.protocol.GetUserLeaderBookletResponse#getEducationList
	 * 
	 */
	public static class ElementEducationList implements Serializable{

		private String ecode;
		private String edisplay;
		private String eenabled;
		private String eiconAddr;
		private String evalue;

		/**
		 * @return 编码
		 */
		public String getEcode() {
			return ecode;
		}

		public void setEcode(String ecode) {
			this.ecode = ecode;
		}

		/**
		 * @return 字典显示名称
		 */
		public String getEdisplay() {
			return edisplay;
		}

		public void setEdisplay(String edisplay) {
			this.edisplay = edisplay;
		}

		/**
		 * @return 是否可用（1-可用 0-不可用）
		 */
		public String getEenabled() {
			return eenabled;
		}

		public void setEenabled(String eenabled) {
			this.eenabled = eenabled;
		}

		/**
		 * @return 图标地址
		 */
		public String getEiconAddr() {
			return eiconAddr;
		}

		public void setEiconAddr(String eiconAddr) {
			this.eiconAddr = eiconAddr;
		}

		/**
		 * @return 字典值
		 */
		public String getEvalue() {
			return evalue;
		}

		public void setEvalue(String evalue) {
			this.evalue = evalue;
		}
	}

	/**
	 * @see net.zkbc.p2p.message.protocol.GetUserLeaderBookletResponse#getStockPhaseList
	 * 
	 */
	public static class ElementStockPhaseList implements Serializable{

		private String scode;
		private String sdisplay;
		private String senabled;
		private String siconAddr;
		private String svalue;

		/**
		 * @return 编码
		 */
		public String getScode() {
			return scode;
		}

		public void setScode(String scode) {
			this.scode = scode;
		}

		/**
		 * @return 字典显示名称
		 */
		public String getSdisplay() {
			return sdisplay;
		}

		public void setSdisplay(String sdisplay) {
			this.sdisplay = sdisplay;
		}

		/**
		 * @return 是否可用（1-可用 0-不可用）
		 */
		public String getSenabled() {
			return senabled;
		}

		public void setSenabled(String senabled) {
			this.senabled = senabled;
		}

		/**
		 * @return 图标地址
		 */
		public String getSiconAddr() {
			return siconAddr;
		}

		public void setSiconAddr(String siconAddr) {
			this.siconAddr = siconAddr;
		}

		/**
		 * @return 字典值
		 */
		public String getSvalue() {
			return svalue;
		}

		public void setSvalue(String svalue) {
			this.svalue = svalue;
		}
	}

	private List<ElementCompanyTypList> companyTypList;
	private List<ElementInvestTimeList> investTimeList;
	private List<ElementEducationList> educationList;
	private List<ElementStockPhaseList> stockPhaseList;

	public GetUserLeaderBookletResponse() {
		super();
		setMessageId("getUserLeaderBooklet");
	}

	/**
	 * @return 公司类型选项列表
	 */
	public List<ElementCompanyTypList> getCompanyTypList() {
		return companyTypList;
	}

	public void setCompanyTypList(List<ElementCompanyTypList> companyTypList) {
		this.companyTypList = companyTypList;
	}

	/**
	 * @return 从事投资实业时间列表
	 */
	public List<ElementInvestTimeList> getInvestTimeList() {
		return investTimeList;
	}

	public void setInvestTimeList(List<ElementInvestTimeList> investTimeList) {
		this.investTimeList = investTimeList;
	}

	/**
	 * @return 教育背景选项列表
	 */
	public List<ElementEducationList> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<ElementEducationList> educationList) {
		this.educationList = educationList;
	}

	/**
	 * @return 极端偏好选项列表
	 */
	public List<ElementStockPhaseList> getStockPhaseList() {
		return stockPhaseList;
	}

	public void setStockPhaseList(List<ElementStockPhaseList> stockPhaseList) {
		this.stockPhaseList = stockPhaseList;
	}
}