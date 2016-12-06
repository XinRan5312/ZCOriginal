package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取详细信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjDetailByPrjIdResponse extends ResponseSupport {

	private String abortOldStatus;
	private String abortRemark;
	private String abortTime;
	private String cautionMoneyStatus;
	private String fileIdCommPlan;
	private String fileIdPrjDesp;
	private String homePicAddress;
	private String id;
	private String picListAddress;
	private String preHotPicAddress;
	private String prjId;
	private String version_;
	private String vidAddr;
	private String weiBoAddr;

	public GetPrjDetailByPrjIdResponse() {
		super();
		setMessageId("getPrjDetailByPrjId");
	}


	/**
	 * @return 流标时候的原状态
	 */
	public String getAbortOldStatus() {
		return abortOldStatus;
	}

	public void setAbortOldStatus(String abortOldStatus) {
		this.abortOldStatus = abortOldStatus;
	}

	/**
	 * @return 流标备注
	 */
	public String getAbortRemark() {
		return abortRemark;
	}

	public void setAbortRemark(String abortRemark) {
		this.abortRemark = abortRemark;
	}

	/**
	 * @return 流标时间
	 */
	public String getAbortTime() {
		return abortTime;
	}

	public void setAbortTime(String abortTime) {
		this.abortTime = abortTime;
	}

	/**
	 * @return 保证金状态  10 已缴纳20 已扣除30 已退还
	 */
	public String getCautionMoneyStatus() {
		return cautionMoneyStatus;
	}

	public void setCautionMoneyStatus(String cautionMoneyStatus) {
		this.cautionMoneyStatus = cautionMoneyStatus;
	}

	/**
	 * @return 
	 */
	public String getFileIdCommPlan() {
		return fileIdCommPlan;
	}

	public void setFileIdCommPlan(String fileIdCommPlan) {
		this.fileIdCommPlan = fileIdCommPlan;
	}

	/**
	 * @return 
	 */
	public String getFileIdPrjDesp() {
		return fileIdPrjDesp;
	}

	public void setFileIdPrjDesp(String fileIdPrjDesp) {
		this.fileIdPrjDesp = fileIdPrjDesp;
	}

	/**
	 * @return 首页文件
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
	 * @return 列表页图片
	 */
	public String getPicListAddress() {
		return picListAddress;
	}

	public void setPicListAddress(String picListAddress) {
		this.picListAddress = picListAddress;
	}

	/**
	 * @return 预热图片
	 */
	public String getPreHotPicAddress() {
		return preHotPicAddress;
	}

	public void setPreHotPicAddress(String preHotPicAddress) {
		this.preHotPicAddress = preHotPicAddress;
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
	public String getVersion_() {
		return version_;
	}

	public void setVersion_(String version_) {
		this.version_ = version_;
	}

	/**
	 * @return 视频地址
	 */
	public String getVidAddr() {
		return vidAddr;
	}

	public void setVidAddr(String vidAddr) {
		this.vidAddr = vidAddr;
	}

	/**
	 * @return 微博地址
	 */
	public String getWeiBoAddr() {
		return weiBoAddr;
	}

	public void setWeiBoAddr(String weiBoAddr) {
		this.weiBoAddr = weiBoAddr;
	}
}