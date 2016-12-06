package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取登录用户平台领投人认证信息（申请中则显示申请信息）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUserLeaderInfoResponse extends ResponseSupport {

	private String codCity;
	private String codCompany;
	private String codProv;
	private String companyNam;
	private String education;
	private String infoType;
	private String investCnt;
	private String investNam;
	private String investTime;
	private String jobTitle;
	private String likeTrades;
	private String prjInNext;
	private String prjsucc;
	private String prjSuccIntro;
	private String prjSuccNam;
	private String status;
	private String workHis;

	public GetUserLeaderInfoResponse() {
		super();
		setMessageId("getUserLeaderInfo");
	}


	/**
	 * @return 城市代码
	 */
	public String getCodCity() {
		return codCity;
	}

	public void setCodCity(String codCity) {
		this.codCity = codCity;
	}

	/**
	 * @return 公司类型
	 */
	public String getCodCompany() {
		return codCompany;
	}

	public void setCodCompany(String codCompany) {
		this.codCompany = codCompany;
	}

	/**
	 * @return 省份代码
	 */
	public String getCodProv() {
		return codProv;
	}

	public void setCodProv(String codProv) {
		this.codProv = codProv;
	}

	/**
	 * @return 公司名称
	 */
	public String getCompanyNam() {
		return companyNam;
	}

	public void setCompanyNam(String companyNam) {
		this.companyNam = companyNam;
	}

	/**
	 * @return 教育背景
	 */
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return 资料类型（10-申请中资料 20-正式资料）
	 */
	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	/**
	 * @return 已投项目数 1有 0无
	 */
	public String getInvestCnt() {
		return investCnt;
	}

	public void setInvestCnt(String investCnt) {
		this.investCnt = investCnt;
	}

	/**
	 * @return 已投项目名称
	 */
	public String getInvestNam() {
		return investNam;
	}

	public void setInvestNam(String investNam) {
		this.investNam = investNam;
	}

	/**
	 * @return 从事投资实业时间
	 */
	public String getInvestTime() {
		return investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}

	/**
	 * @return 职位/头衔
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return 阶段偏好 stockPhase 编码用逗号分开
	 */
	public String getLikeTrades() {
		return likeTrades;
	}

	public void setLikeTrades(String likeTrades) {
		this.likeTrades = likeTrades;
	}

	/**
	 * @return 项目已到下轮 1有 0无
	 */
	public String getPrjInNext() {
		return prjInNext;
	}

	public void setPrjInNext(String prjInNext) {
		this.prjInNext = prjInNext;
	}

	/**
	 * @return 成功退出项目 1有 0无
	 */
	public String getPrjsucc() {
		return prjsucc;
	}

	public void setPrjsucc(String prjsucc) {
		this.prjsucc = prjsucc;
	}

	/**
	 * @return 成功案例介绍
	 */
	public String getPrjSuccIntro() {
		return prjSuccIntro;
	}

	public void setPrjSuccIntro(String prjSuccIntro) {
		this.prjSuccIntro = prjSuccIntro;
	}

	/**
	 * @return 成功案例名称
	 */
	public String getPrjSuccNam() {
		return prjSuccNam;
	}

	public void setPrjSuccNam(String prjSuccNam) {
		this.prjSuccNam = prjSuccNam;
	}

	/**
	 * @return 状态（正式资料 10-激活 20-禁用 申请中 20-待审批）
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 工作经历
	 */
	public String getWorkHis() {
		return workHis;
	}

	public void setWorkHis(String workHis) {
		this.workHis = workHis;
	}
}