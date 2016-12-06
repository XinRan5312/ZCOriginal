package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取企业信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjCompanyByPrjIdResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjCompanyByPrjIdResponse#getCompanyFileList
	 * 
	 */
	public static class ElementCompanyFileList {

		private String codFileTyp;
		private String imagePath;

		/**
		 * @return 文件种类名称
		 */
		public String getCodFileTyp() {
			return codFileTyp;
		}

		public void setCodFileTyp(String codFileTyp) {
			this.codFileTyp = codFileTyp;
		}

		/**
		 * @return 文件路径
		 */
		public String getImagePath() {
			return imagePath;
		}

		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}
	}

	private String address;
	private String codCity;
	private String codCompany;
	private String codProv;
	private String codTrade;
	private String corpOrgNo;
	private String createTime;
	private String foundTime;
	private String id;
	private String lawManNam;
	private String mainProductService;
	private String memberCnt;
	private String nam;
	private String pctAssetReturn;
	private String prjId;
	private String runLisNo;
	private String runScale;
	private List<ElementCompanyFileList> companyFileList;

	public GetPrjCompanyByPrjIdResponse() {
		super();
		setMessageId("getPrjCompanyByPrjId");
	}


	/**
	 * @return 
	 */
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return 
	 */
	public String getCodCity() {
		return codCity;
	}

	public void setCodCity(String codCity) {
		this.codCity = codCity;
	}

	/**
	 * @return 企业类型
	 */
	public String getCodCompany() {
		return codCompany;
	}

	public void setCodCompany(String codCompany) {
		this.codCompany = codCompany;
	}

	/**
	 * @return 
	 */
	public String getCodProv() {
		return codProv;
	}

	public void setCodProv(String codProv) {
		this.codProv = codProv;
	}

	/**
	 * @return 企业行业
	 */
	public String getCodTrade() {
		return codTrade;
	}

	public void setCodTrade(String codTrade) {
		this.codTrade = codTrade;
	}

	/**
	 * @return 
	 */
	public String getCorpOrgNo() {
		return corpOrgNo;
	}

	public void setCorpOrgNo(String corpOrgNo) {
		this.corpOrgNo = corpOrgNo;
	}

	/**
	 * @return 
	 */
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return 成立时间
	 */
	public String getFoundTime() {
		return foundTime;
	}

	public void setFoundTime(String foundTime) {
		this.foundTime = foundTime;
	}

	/**
	 * @return 主键id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 
	 */
	public String getLawManNam() {
		return lawManNam;
	}

	public void setLawManNam(String lawManNam) {
		this.lawManNam = lawManNam;
	}

	/**
	 * @return 主要产品服务
	 */
	public String getMainProductService() {
		return mainProductService;
	}

	public void setMainProductService(String mainProductService) {
		this.mainProductService = mainProductService;
	}

	/**
	 * @return 成员数量
	 */
	public String getMemberCnt() {
		return memberCnt;
	}

	public void setMemberCnt(String memberCnt) {
		this.memberCnt = memberCnt;
	}

	/**
	 * @return 企业名称
	 */
	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	/**
	 * @return 
	 */
	public String getPctAssetReturn() {
		return pctAssetReturn;
	}

	public void setPctAssetReturn(String pctAssetReturn) {
		this.pctAssetReturn = pctAssetReturn;
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
	 * @return 
	 */
	public String getRunLisNo() {
		return runLisNo;
	}

	public void setRunLisNo(String runLisNo) {
		this.runLisNo = runLisNo;
	}

	/**
	 * @return 经营规模
	 */
	public String getRunScale() {
		return runScale;
	}

	public void setRunScale(String runScale) {
		this.runScale = runScale;
	}

	/**
	 * @return 公司文件集合
	 */
	public List<ElementCompanyFileList> getCompanyFileList() {
		return companyFileList;
	}

	public void setCompanyFileList(List<ElementCompanyFileList> companyFileList) {
		this.companyFileList = companyFileList;
	}
}