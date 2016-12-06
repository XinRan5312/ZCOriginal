package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交用户个人工作信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitWorkInfoRequest extends RequestSupport {

	private String corpaddress;
	private String corpcity;
	private String corpcounty;
	private String corpkind;
	private String corpname;
	private String corpprovice;
	private String corpsize;
	private String corptel;
	private String department;
	private String officejoindate;
	private String title;

	public SubmitWorkInfoRequest() {
		super();
		setMessageId("submitWorkInfo");
	}	

	/**
	 * @return 公司地址
	 */
	public String getCorpaddress() {
		return corpaddress;
	}

	public void setCorpaddress(String corpaddress) {
		this.corpaddress = corpaddress;
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
	 * @return 公司县
	 */
	public String getCorpcounty() {
		return corpcounty;
	}

	public void setCorpcounty(String corpcounty) {
		this.corpcounty = corpcounty;
	}

	/**
	 * @return 公司性质
	 */
	public String getCorpkind() {
		return corpkind;
	}

	public void setCorpkind(String corpkind) {
		this.corpkind = corpkind;
	}

	/**
	 * @return 公司名称
	 */
	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	/**
	 * @return 公司省
	 */
	public String getCorpprovice() {
		return corpprovice;
	}

	public void setCorpprovice(String corpprovice) {
		this.corpprovice = corpprovice;
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
	 * @return 公司电话
	 */
	public String getCorptel() {
		return corptel;
	}

	public void setCorptel(String corptel) {
		this.corptel = corptel;
	}

	/**
	 * @return 所在部门
	 */
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return 进入公司时间
	 */
	public String getOfficejoindate() {
		return officejoindate;
	}

	public void setOfficejoindate(String officejoindate) {
		this.officejoindate = officejoindate;
	}

	/**
	 * @return 担任职务
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}