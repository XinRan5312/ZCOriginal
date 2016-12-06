package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取邀请码.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetInviteCodeResponse extends ResponseSupport {

	private String code;
	private String desc;

	public GetInviteCodeResponse() {
		super();
		setMessageId("getInviteCode");
	}


	/**
	 * @return 邀请链接
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 邀请描述内容
	 */
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}