package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取用户个人身份信息(2016-04-29已改可用).服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPersonInfoResponse extends ResponseSupport {

	private String email;
	private String idCardNo;
	private String mobile;
	private String nickName;
	private String portrait;
	private String realName;
	private String registerTime;

	public GetPersonInfoResponse() {
		super();
		setMessageId("getPersonInfo");
	}


	/**
	 * @return 邮箱
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 身份证号
	 */
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	/**
	 * @return 手机号码
	 */
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return 昵称
	 */
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return 头像
	 */
	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
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
	 * @return 注册时间
	 */
	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
}