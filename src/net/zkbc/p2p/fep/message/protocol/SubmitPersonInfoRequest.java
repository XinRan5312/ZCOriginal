package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交用户个人身份信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitPersonInfoRequest extends RequestSupport {

	private String education;
	private String hometownaddress;
	private String hometowncity;
	private String hometowncounty;
	private String hometownprovice;
	private String maritalstatus;
	private String name;
	private String nowaddress;
	private String nowcity;
	private String nowcountry;
	private String nowprovice;
	private String phonenumber;
	private String qqno;

	public SubmitPersonInfoRequest() {
		super();
		setMessageId("submitPersonInfo");
	}	

	/**
	 * @return 学历
	 */
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return 户籍地址
	 */
	public String getHometownaddress() {
		return hometownaddress;
	}

	public void setHometownaddress(String hometownaddress) {
		this.hometownaddress = hometownaddress;
	}

	/**
	 * @return 户籍市
	 */
	public String getHometowncity() {
		return hometowncity;
	}

	public void setHometowncity(String hometowncity) {
		this.hometowncity = hometowncity;
	}

	/**
	 * @return 户籍县
	 */
	public String getHometowncounty() {
		return hometowncounty;
	}

	public void setHometowncounty(String hometowncounty) {
		this.hometowncounty = hometowncounty;
	}

	/**
	 * @return 户籍省
	 */
	public String getHometownprovice() {
		return hometownprovice;
	}

	public void setHometownprovice(String hometownprovice) {
		this.hometownprovice = hometownprovice;
	}

	/**
	 * @return 婚姻状况
	 */
	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	/**
	 * @return 姓名
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 现居住地址
	 */
	public String getNowaddress() {
		return nowaddress;
	}

	public void setNowaddress(String nowaddress) {
		this.nowaddress = nowaddress;
	}

	/**
	 * @return 现居住城市
	 */
	public String getNowcity() {
		return nowcity;
	}

	public void setNowcity(String nowcity) {
		this.nowcity = nowcity;
	}

	/**
	 * @return 现居住县
	 */
	public String getNowcountry() {
		return nowcountry;
	}

	public void setNowcountry(String nowcountry) {
		this.nowcountry = nowcountry;
	}

	/**
	 * @return 现居住省份
	 */
	public String getNowprovice() {
		return nowprovice;
	}

	public void setNowprovice(String nowprovice) {
		this.nowprovice = nowprovice;
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
	 * @return QQ号码
	 */
	public String getQqno() {
		return qqno;
	}

	public void setQqno(String qqno) {
		this.qqno = qqno;
	}
}