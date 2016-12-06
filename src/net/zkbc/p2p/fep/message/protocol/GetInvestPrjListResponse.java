package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取投资信息列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetInvestPrjListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetInvestPrjListResponse#getInvestPrjList
	 * 
	 */
	public static class ElementInvestPrjList {

		private String amount;
		private String auditTaskId;
		private String bookCnt;
		private String cautionMoneyAmt;
		private String cityName;
		private String codCity;
		private String codFeature;
		private String codPrjTrade;
		private String codProv;
		private String commissionAmt;
		private String commissionPct;
		private String createTime;
		private String custId;
		private String custLoginNam;
		private String custManagerId;
		private String custManagerLoginNam;
		private String desp;
		private String fromPrjId;
		private String fullTime;
		private String headerAddress;
		private String homePicAddress;
		private String id;
		private String isExtendSupp;
		private String nam;
		private String pct1thUseMoney;
		private String prjAmount;
		private String prjCustNam;
		private String prjDayCnt;
		private String prjEndTime;
		private String prjNo;
		private String prjPreHotTime;
		private String prjStartTime;
		private String prjSuccIf;
		private String prodId;
		private String regretSuppDayCnt;
		private String riskCautionAmt;
		private String riskCautionPct;
		private String runStatus;
		private String status;
		private String subProdTyp;
		private String succAmount;
		private String suppCnt;
		private String suppedAmt;
		private String suppedAmt4Succ;
		private String suppedCnt;
		private String telephone;
		private String unregretDayCnt;
		private String userId;
		private String userLoginNam;
		private String version_;

		/**
		 * @return 投标金额
		 */
		public String getAmount() {
			return amount;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		/**
		 * @return 审批任务Id
		 */
		public String getAuditTaskId() {
			return auditTaskId;
		}

		public void setAuditTaskId(String auditTaskId) {
			this.auditTaskId = auditTaskId;
		}

		/**
		 * @return 已预约数量
		 */
		public String getBookCnt() {
			return bookCnt;
		}

		public void setBookCnt(String bookCnt) {
			this.bookCnt = bookCnt;
		}

		/**
		 * @return 项目保证金
		 */
		public String getCautionMoneyAmt() {
			return cautionMoneyAmt;
		}

		public void setCautionMoneyAmt(String cautionMoneyAmt) {
			this.cautionMoneyAmt = cautionMoneyAmt;
		}

		/**
		 * @return 城市名称
		 */
		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
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
		 * @return 10 老店，20 新店，30 品牌
		 */
		public String getCodFeature() {
			return codFeature;
		}

		public void setCodFeature(String codFeature) {
			this.codFeature = codFeature;
		}

		/**
		 * @return 项目行业
		 */
		public String getCodPrjTrade() {
			return codPrjTrade;
		}

		public void setCodPrjTrade(String codPrjTrade) {
			this.codPrjTrade = codPrjTrade;
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
		 * @return 待付佣金金额
		 */
		public String getCommissionAmt() {
			return commissionAmt;
		}

		public void setCommissionAmt(String commissionAmt) {
			this.commissionAmt = commissionAmt;
		}

		/**
		 * @return 佣金比例
		 */
		public String getCommissionPct() {
			return commissionPct;
		}

		public void setCommissionPct(String commissionPct) {
			this.commissionPct = commissionPct;
		}

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
		 * @return 项目发起人用户id
		 */
		public String getCustId() {
			return custId;
		}

		public void setCustId(String custId) {
			this.custId = custId;
		}

		/**
		 * @return 项目发起人登录名
		 */
		public String getCustLoginNam() {
			return custLoginNam;
		}

		public void setCustLoginNam(String custLoginNam) {
			this.custLoginNam = custLoginNam;
		}

		/**
		 * @return 客户经理标识
		 */
		public String getCustManagerId() {
			return custManagerId;
		}

		public void setCustManagerId(String custManagerId) {
			this.custManagerId = custManagerId;
		}

		/**
		 * @return 客户经理后台登录名
		 */
		public String getCustManagerLoginNam() {
			return custManagerLoginNam;
		}

		public void setCustManagerLoginNam(String custManagerLoginNam) {
			this.custManagerLoginNam = custManagerLoginNam;
		}

		/**
		 * @return 描述
		 */
		public String getDesp() {
			return desp;
		}

		public void setDesp(String desp) {
			this.desp = desp;
		}

		/**
		 * @return 饕客约主项目ID
		 */
		public String getFromPrjId() {
			return fromPrjId;
		}

		public void setFromPrjId(String fromPrjId) {
			this.fromPrjId = fromPrjId;
		}

		/**
		 * @return 满标时间
		 */
		public String getFullTime() {
			return fullTime;
		}

		public void setFullTime(String fullTime) {
			this.fullTime = fullTime;
		}

		/**
		 * @return 项目方头像
		 */
		public String getHeaderAddress() {
			return headerAddress;
		}

		public void setHeaderAddress(String headerAddress) {
			this.headerAddress = headerAddress;
		}

		/**
		 * @return 首页图片
		 */
		public String getHomePicAddress() {
			return homePicAddress;
		}

		public void setHomePicAddress(String homePicAddress) {
			this.homePicAddress = homePicAddress;
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
		 * @return 是否允许超募
		 */
		public String getIsExtendSupp() {
			return isExtendSupp;
		}

		public void setIsExtendSupp(String isExtendSupp) {
			this.isExtendSupp = isExtendSupp;
		}

		/**
		 * @return 项目名称
		 */
		public String getNam() {
			return nam;
		}

		public void setNam(String nam) {
			this.nam = nam;
		}

		/**
		 * @return 首次放款比例
		 */
		public String getPct1thUseMoney() {
			return pct1thUseMoney;
		}

		public void setPct1thUseMoney(String pct1thUseMoney) {
			this.pct1thUseMoney = pct1thUseMoney;
		}

		/**
		 * @return 融资金额
		 */
		public String getPrjAmount() {
			return prjAmount;
		}

		public void setPrjAmount(String prjAmount) {
			this.prjAmount = prjAmount;
		}

		/**
		 * @return 项目方名称
		 */
		public String getPrjCustNam() {
			return prjCustNam;
		}

		public void setPrjCustNam(String prjCustNam) {
			this.prjCustNam = prjCustNam;
		}

		/**
		 * @return 项目天数
		 */
		public String getPrjDayCnt() {
			return prjDayCnt;
		}

		public void setPrjDayCnt(String prjDayCnt) {
			this.prjDayCnt = prjDayCnt;
		}

		/**
		 * @return 项目截止时间
		 */
		public String getPrjEndTime() {
			return prjEndTime;
		}

		public void setPrjEndTime(String prjEndTime) {
			this.prjEndTime = prjEndTime;
		}

		/**
		 * @return 项目编号
		 */
		public String getPrjNo() {
			return prjNo;
		}

		public void setPrjNo(String prjNo) {
			this.prjNo = prjNo;
		}

		/**
		 * @return 预热开始时间
		 */
		public String getPrjPreHotTime() {
			return prjPreHotTime;
		}

		public void setPrjPreHotTime(String prjPreHotTime) {
			this.prjPreHotTime = prjPreHotTime;
		}

		/**
		 * @return 项目开始时间
		 */
		public String getPrjStartTime() {
			return prjStartTime;
		}

		public void setPrjStartTime(String prjStartTime) {
			this.prjStartTime = prjStartTime;
		}

		/**
		 * @return 众筹成功条件
		 */
		public String getPrjSuccIf() {
			return prjSuccIf;
		}

		public void setPrjSuccIf(String prjSuccIf) {
			this.prjSuccIf = prjSuccIf;
		}

		/**
		 * @return 产品表示
		 */
		public String getProdId() {
			return prodId;
		}

		public void setProdId(String prodId) {
			this.prodId = prodId;
		}

		/**
		 * @return 后悔天数
		 */
		public String getRegretSuppDayCnt() {
			return regretSuppDayCnt;
		}

		public void setRegretSuppDayCnt(String regretSuppDayCnt) {
			this.regretSuppDayCnt = regretSuppDayCnt;
		}

		/**
		 * @return 待付风险保证金金额
		 */
		public String getRiskCautionAmt() {
			return riskCautionAmt;
		}

		public void setRiskCautionAmt(String riskCautionAmt) {
			this.riskCautionAmt = riskCautionAmt;
		}

		/**
		 * @return 风险保证金比例
		 */
		public String getRiskCautionPct() {
			return riskCautionPct;
		}

		public void setRiskCautionPct(String riskCautionPct) {
			this.riskCautionPct = riskCautionPct;
		}

		/**
		 * @return 经营状态
		 */
		public String getRunStatus() {
			return runStatus;
		}

		public void setRunStatus(String runStatus) {
			this.runStatus = runStatus;
		}

		/**
		 * @return 项目状态  待预热-10,预热中-20,待筹款-30,预热流标-35,筹款中-40,筹款结束-45,筹款满标-50,筹款流标-55,项目成功-60,成功结项-70,失败结项-80
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 产品子类 10 普通 20 饕客约
		 */
		public String getSubProdTyp() {
			return subProdTyp;
		}

		public void setSubProdTyp(String subProdTyp) {
			this.subProdTyp = subProdTyp;
		}

		/**
		 * @return 判定为融资金额
		 */
		public String getSuccAmount() {
			return succAmount;
		}

		public void setSuccAmount(String succAmount) {
			this.succAmount = succAmount;
		}

		/**
		 * @return 用于判断标的成功的已支持金额
		 */
		public String getSuppCnt() {
			return suppCnt;
		}

		public void setSuppCnt(String suppCnt) {
			this.suppCnt = suppCnt;
		}

		/**
		 * @return 已支持金额
		 */
		public String getSuppedAmt() {
			return suppedAmt;
		}

		public void setSuppedAmt(String suppedAmt) {
			this.suppedAmt = suppedAmt;
		}

		/**
		 * @return 判断成功的标志金额
		 */
		public String getSuppedAmt4Succ() {
			return suppedAmt4Succ;
		}

		public void setSuppedAmt4Succ(String suppedAmt4Succ) {
			this.suppedAmt4Succ = suppedAmt4Succ;
		}

		/**
		 * @return 已支持数量
		 */
		public String getSuppedCnt() {
			return suppedCnt;
		}

		public void setSuppedCnt(String suppedCnt) {
			this.suppedCnt = suppedCnt;
		}

		/**
		 * @return 联系电话
		 */
		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		/**
		 * @return 禁止后悔天数
		 */
		public String getUnregretDayCnt() {
			return unregretDayCnt;
		}

		public void setUnregretDayCnt(String unregretDayCnt) {
			this.unregretDayCnt = unregretDayCnt;
		}

		/**
		 * @return 后台操作用户
		 */
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		/**
		 * @return 后台操作用户名
		 */
		public String getUserLoginNam() {
			return userLoginNam;
		}

		public void setUserLoginNam(String userLoginNam) {
			this.userLoginNam = userLoginNam;
		}

		/**
		 * @return 
		 */
		public String getVersion_() {
			return version_;
		}

		public void setVersion_(String version_) {
			this.version_ = version_;
		}
	}

	private List<ElementInvestPrjList> investPrjList;

	public GetInvestPrjListResponse() {
		super();
		setMessageId("getInvestPrjList");
	}


	/**
	 * @return 投资项目列表
	 */
	public List<ElementInvestPrjList> getInvestPrjList() {
		return investPrjList;
	}

	public void setInvestPrjList(List<ElementInvestPrjList> investPrjList) {
		this.investPrjList = investPrjList;
	}
}