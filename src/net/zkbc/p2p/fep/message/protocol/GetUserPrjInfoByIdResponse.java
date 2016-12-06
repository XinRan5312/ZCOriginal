package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据用户的id及项目id获取身份信息（跟投人、领投人、发起人）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUserPrjInfoByIdResponse extends ResponseSupport {

	private String id;
	private String identity;
	private String loginNam;
	private String portraitAddr;
	private String selfIntro;

	public GetUserPrjInfoByIdResponse() {
		super();
		setMessageId("getUserPrjInfoById");
	}


	/**
	 * @return 用户id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return 10-项目发起人 20--项目领投人（股权) 多个身份以英文逗号隔开
	 */
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	/**
	 * @return 用户登录昵称
	 */
	public String getLoginNam() {
		return loginNam;
	}

	public void setLoginNam(String loginNam) {
		this.loginNam = loginNam;
	}

	/**
	 * @return 头像地址
	 */
	public String getPortraitAddr() {
		return portraitAddr;
	}

	public void setPortraitAddr(String portraitAddr) {
		this.portraitAddr = portraitAddr;
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