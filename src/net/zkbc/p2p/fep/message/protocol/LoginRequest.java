package net.zkbc.p2p.fep.message.protocol;

/**
 * 登录.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class LoginRequest extends RequestSupport {

	private String accesstoken;
	private String deviceid;
	private String devicetoken;
	private String devicetype;
	private String isjailbreak;
	private String loginname;
	private String password;
	private String tokentype;
	private String unionid;
	private String vfcode;
	private String imei;
	private String ostype;
	private String cid;

	public LoginRequest() {
		super();
		setMessageId("login");
	}

	public String getCid() {
		return cid;
	}

	/**
	 * 个推id
	 * 
	 * @param cid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	/**
	 * @return 手机系统类型 1 为ios 2为安卓
	 */
	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	/**
	 * @return 登录识别
	 */
	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	/**
	 * @return 设备号
	 */
	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	/**
	 * @return 推送标识
	 */
	public String getDevicetoken() {
		return devicetoken;
	}

	public void setDevicetoken(String devicetoken) {
		this.devicetoken = devicetoken;
	}

	/**
	 * @return 设备类型
	 */
	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	/**
	 * @return 是否越狱：0-未越狱,1-已越狱
	 */
	public String getIsjailbreak() {
		return isjailbreak;
	}

	public void setIsjailbreak(String isjailbreak) {
		this.isjailbreak = isjailbreak;
	}

	/**
	 * @return 登录名称
	 */
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	/**
	 * @return 登录密码
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return 登录类型：0-qq，1-微博
	 */
	public String getTokentype() {
		return tokentype;
	}

	public void setTokentype(String tokentype) {
		this.tokentype = tokentype;
	}

	/**
	 * @return 联合登录第三方id
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