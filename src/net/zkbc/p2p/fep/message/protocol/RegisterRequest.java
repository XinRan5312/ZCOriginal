package net.zkbc.p2p.fep.message.protocol;

/**
 * 用户注册.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class RegisterRequest extends RequestSupport {

	private String loginname;
	private String mail;
	private String password;
	private String phonenumber;
	private String tokentype;
	private String type;
	private String unionid;
	private String vfcode;

	public RegisterRequest() {
		super();
		setMessageId("register");
	}	

	/**
	 * @return 用户名称
	 */
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	/**
	 * @return 邮箱
	 */
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return 手机号码
	 */
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	/**
	 * @return 判断是qq还是微博的token 0-qq 1-微博
	 */
	public String getTokentype() {
		return tokentype;
	}

	public void setTokentype(String tokentype) {
		this.tokentype = tokentype;
	}

	/**
	 * @return 用户类别：1-投资人，2-借款人
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 微博qq 联合登陆id
	 */
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/**
	 * @return 验证码
	 */
	public String getVfcode() {
		return vfcode;
	}

	public void setVfcode(String vfcode) {
		this.vfcode = vfcode;
	}
}