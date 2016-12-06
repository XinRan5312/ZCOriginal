package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取登录用户普通投资认证信息（申请中则显示申请信息）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetFollowerInfoResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetFollowerInfoResponse#getFollowerFileList
	 * 
	 */
	public static class ElementFollowerFileList {

		private String followerFileAddr;

		/**
		 * @return 认证资料地址
		 */
		public String getFollowerFileAddr() {
			return followerFileAddr;
		}

		public void setFollowerFileAddr(String followerFileAddr) {
			this.followerFileAddr = followerFileAddr;
		}
	}

	private String authRight;
	private String headPhotoAddr;
	private String idcardNo;
	private String infoType;
	private String prjTrade;
	private String realName;
	private String selfIntro;
	private String status;
	private List<ElementFollowerFileList> followerFileList;

	public GetFollowerInfoResponse() {
		super();
		setMessageId("getFollowerInfo");
	}


	/**
	 * @return 认证资格
	 */
	public String getAuthRight() {
		return authRight;
	}

	public void setAuthRight(String authRight) {
		this.authRight = authRight;
	}

	/**
	 * @return 头像地址
	 */
	public String getHeadPhotoAddr() {
		return headPhotoAddr;
	}

	public void setHeadPhotoAddr(String headPhotoAddr) {
		this.headPhotoAddr = headPhotoAddr;
	}

	/**
	 * @return 身份证号
	 */
	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
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
	 * @return 投资领域
	 */
	public String getPrjTrade() {
		return prjTrade;
	}

	public void setPrjTrade(String prjTrade) {
		this.prjTrade = prjTrade;
	}

	/**
	 * @return 真实姓名
	 */
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return 自我介绍
	 */
	public String getSelfIntro() {
		return selfIntro;
	}

	public void setSelfIntro(String selfIntro) {
		this.selfIntro = selfIntro;
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
	 * @return 认证资料列表
	 */
	public List<ElementFollowerFileList> getFollowerFileList() {
		return followerFileList;
	}

	public void setFollowerFileList(List<ElementFollowerFileList> followerFileList) {
		this.followerFileList = followerFileList;
	}
}