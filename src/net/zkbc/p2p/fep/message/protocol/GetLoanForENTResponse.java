package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取企业借款详情.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetLoanForENTResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetLoanForENTResponse#getAssurepicList
	 * 
	 */
	public static class ElementAssurepicList {

		private String assurepic;

		/**
		 * @return 图片地址
		 */
		public String getAssurepic() {
			return assurepic;
		}

		public void setAssurepic(String assurepic) {
			this.assurepic = assurepic;
		}
	}

	/**
	 * @see net.zkbc.p2p.message.protocol.GetLoanForENTResponse#getEntpicList
	 * 
	 */
	public static class ElementEntpicList {

		private String entpic;

		/**
		 * @return 图片地址
		 */
		public String getEntpic() {
			return entpic;
		}

		public void setEntpic(String entpic) {
			this.entpic = entpic;
		}
	}

	private String assuredescr;
	private String assureloan;
	private String assurename;
	private String assureopinion;
	private String entdescr;
	private String entscope;
	private String entstatus;
	private String loandescr;
	private String pawndescr;
	private String repayfrom;
	private String riskmeasures;
	private String use;
	private List<ElementAssurepicList> assurepicList;
	private List<ElementEntpicList> entpicList;

	public GetLoanForENTResponse() {
		super();
		setMessageId("getLoanForENT");
	}


	/**
	 * @return 担保机构简介
	 */
	public String getAssuredescr() {
		return assuredescr;
	}

	public void setAssuredescr(String assuredescr) {
		this.assuredescr = assuredescr;
	}

	/**
	 * @return 担保描述
	 */
	public String getAssureloan() {
		return assureloan;
	}

	public void setAssureloan(String assureloan) {
		this.assureloan = assureloan;
	}

	/**
	 * @return 担保机构
	 */
	public String getAssurename() {
		return assurename;
	}

	public void setAssurename(String assurename) {
		this.assurename = assurename;
	}

	/**
	 * @return 担保意见
	 */
	public String getAssureopinion() {
		return assureopinion;
	}

	public void setAssureopinion(String assureopinion) {
		this.assureopinion = assureopinion;
	}

	/**
	 * @return 企业背景
	 */
	public String getEntdescr() {
		return entdescr;
	}

	public void setEntdescr(String entdescr) {
		this.entdescr = entdescr;
	}

	/**
	 * @return 经营范围
	 */
	public String getEntscope() {
		return entscope;
	}

	public void setEntscope(String entscope) {
		this.entscope = entscope;
	}

	/**
	 * @return 经营状况
	 */
	public String getEntstatus() {
		return entstatus;
	}

	public void setEntstatus(String entstatus) {
		this.entstatus = entstatus;
	}

	/**
	 * @return 项目描述
	 */
	public String getLoandescr() {
		return loandescr;
	}

	public void setLoandescr(String loandescr) {
		this.loandescr = loandescr;
	}

	/**
	 * @return 抵押物简介
	 */
	public String getPawndescr() {
		return pawndescr;
	}

	public void setPawndescr(String pawndescr) {
		this.pawndescr = pawndescr;
	}

	/**
	 * @return 还款来源
	 */
	public String getRepayfrom() {
		return repayfrom;
	}

	public void setRepayfrom(String repayfrom) {
		this.repayfrom = repayfrom;
	}

	/**
	 * @return 风控措施
	 */
	public String getRiskmeasures() {
		return riskmeasures;
	}

	public void setRiskmeasures(String riskmeasures) {
		this.riskmeasures = riskmeasures;
	}

	/**
	 * @return 资金用途
	 */
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	/**
	 * @return 担保公司图片列表
	 */
	public List<ElementAssurepicList> getAssurepicList() {
		return assurepicList;
	}

	public void setAssurepicList(List<ElementAssurepicList> assurepicList) {
		this.assurepicList = assurepicList;
	}

	/**
	 * @return 企业图片列表
	 */
	public List<ElementEntpicList> getEntpicList() {
		return entpicList;
	}

	public void setEntpicList(List<ElementEntpicList> entpicList) {
		this.entpicList = entpicList;
	}
}