package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取借款人信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetBorrowerResponse extends ResponseSupport {

	private String age;
	private String bmaterial;
	private String carstatus;
	private String carvalue;
	private String childstatus;
	private String cmaterial;
	private String corpcity;
	private String corpsize;
	private String description;
	private String dmaterial;
	private String education;
	private String ematerial;
	private String fmaterial;
	private String gender;
	private String hometown;
	private String hometowncity;
	private String housestatus;
	private String id;
	private String maritalstatus;
	private String monthlycarloan;
	private String monthlyhouseloan;
	private String monthlyincome;
	private String name;
	private String nowcity;
	private String username;

	public GetBorrowerResponse() {
		super();
		setMessageId("getBorrower");
	}


	/**
	 * @return 年龄
	 */
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return 身份证明
	 */
	public String getBmaterial() {
		return bmaterial;
	}

	public void setBmaterial(String bmaterial) {
		this.bmaterial = bmaterial;
	}

	/**
	 * @return 购车情况
	 */
	public String getCarstatus() {
		return carstatus;
	}

	public void setCarstatus(String carstatus) {
		this.carstatus = carstatus;
	}

	/**
	 * @return 汽车价值
	 */
	public String getCarvalue() {
		return carvalue;
	}

	public void setCarvalue(String carvalue) {
		this.carvalue = carvalue;
	}

	/**
	 * @return 子女状况
	 */
	public String getChildstatus() {
		return childstatus;
	}

	public void setChildstatus(String childstatus) {
		this.childstatus = childstatus;
	}

	/**
	 * @return 信用报告
	 */
	public String getCmaterial() {
		return cmaterial;
	}

	public void setCmaterial(String cmaterial) {
		this.cmaterial = cmaterial;
	}

	/**
	 * @return 工作城市
	 */
	public String getCorpcity() {
		return corpcity;
	}

	public void setCorpcity(String corpcity) {
		this.corpcity = corpcity;
	}

	/**
	 * @return 公司规模
	 */
	public String getCorpsize() {
		return corpsize;
	}

	public void setCorpsize(String corpsize) {
		this.corpsize = corpsize;
	}

	/**
	 * @return 项目描述
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return 收入证明
	 */
	public String getDmaterial() {
		return dmaterial;
	}

	public void setDmaterial(String dmaterial) {
		this.dmaterial = dmaterial;
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
	 * @return 工作及职务证明
	 */
	public String getEmaterial() {
		return ematerial;
	}

	public void setEmaterial(String ematerial) {
		this.ematerial = ematerial;
	}

	/**
	 * @return 居住地证明
	 */
	public String getFmaterial() {
		return fmaterial;
	}

	public void setFmaterial(String fmaterial) {
		this.fmaterial = fmaterial;
	}

	/**
	 * @return 性别
	 */
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return 籍贯
	 */
	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	/**
	 * @return 户籍城市
	 */
	public String getHometowncity() {
		return hometowncity;
	}

	public void setHometowncity(String hometowncity) {
		this.hometowncity = hometowncity;
	}

	/**
	 * @return 住房情况
	 */
	public String getHousestatus() {
		return housestatus;
	}

	public void setHousestatus(String housestatus) {
		this.housestatus = housestatus;
	}

	/**
	 * @return 借款人标识
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	 * @return 汽车月供
	 */
	public String getMonthlycarloan() {
		return monthlycarloan;
	}

	public void setMonthlycarloan(String monthlycarloan) {
		this.monthlycarloan = monthlycarloan;
	}

	/**
	 * @return 房租/月供
	 */
	public String getMonthlyhouseloan() {
		return monthlyhouseloan;
	}

	public void setMonthlyhouseloan(String monthlyhouseloan) {
		this.monthlyhouseloan = monthlyhouseloan;
	}

	/**
	 * @return 月收入水平
	 */
	public String getMonthlyincome() {
		return monthlyincome;
	}

	public void setMonthlyincome(String monthlyincome) {
		this.monthlyincome = monthlyincome;
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
	 * @return 居住地城市
	 */
	public String getNowcity() {
		return nowcity;
	}

	public void setNowcity(String nowcity) {
		this.nowcity = nowcity;
	}

	/**
	 * @return 借款人用户名
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}