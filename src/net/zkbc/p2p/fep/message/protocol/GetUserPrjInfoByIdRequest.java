package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据用户的id及项目id获取身份信息（跟投人、领投人、发起人）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUserPrjInfoByIdRequest extends RequestSupport {

	private String prjId;
	private String userId;

	public GetUserPrjInfoByIdRequest() {
		super();
		setMessageId("getUserPrjInfoById");
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
	 * @return 用户ID
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}