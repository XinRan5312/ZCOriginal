package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交普通投资人认证.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitApplyFollowerRequest extends RequestSupport {

	private String authRight;
	private String headPhotoId;
	private String infoFileIds;
	private String prjTrades;
	private String selfIntro;

	public SubmitApplyFollowerRequest() {
		super();
		setMessageId("submitApplyFollower");
	}	

	/**
	 * @return 认证资格(多个认证资格以英文逗号隔开)
	 */
	public String getAuthRight() {
		return authRight;
	}

	public void setAuthRight(String authRight) {
		this.authRight = authRight;
	}

	/**
	 * @return 头像文件Id
	 */
	public String getHeadPhotoId() {
		return headPhotoId;
	}

	public void setHeadPhotoId(String headPhotoId) {
		this.headPhotoId = headPhotoId;
	}

	/**
	 * @return 认证资料文件Id(多个文件以英文逗号隔开)
	 */
	public String getInfoFileIds() {
		return infoFileIds;
	}

	public void setInfoFileIds(String infoFileIds) {
		this.infoFileIds = infoFileIds;
	}

	/**
	 * @return 项目子行业(多个行业以英文逗号隔开)
	 */
	public String getPrjTrades() {
		return prjTrades;
	}

	public void setPrjTrades(String prjTrades) {
		this.prjTrades = prjTrades;
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
}