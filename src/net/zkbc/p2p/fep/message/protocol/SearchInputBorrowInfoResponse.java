package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 查询用户提交信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchInputBorrowInfoResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SearchInputBorrowInfoResponse#getPicList
	 * 
	 */
	public static class ElementPicList {

		private String picname;
		private String pictype;

		/**
		 * @return 图片名称
		 */
		public String getPicname() {
			return picname;
		}

		public void setPicname(String picname) {
			this.picname = picname;
		}

		/**
		 * @return 图片类型：0-身份证明,1-信用报告,2-收入证明,3-居住地址证明,4-工作及职务证明
		 */
		public String getPictype() {
			return pictype;
		}

		public void setPictype(String pictype) {
			this.pictype = pictype;
		}
	}

	private String age;
	private String carstatus;
	private String childstatus;
	private String contactname1;
	private String contactname2;
	private String contactname3;
	private String contactrelation1;
	private String contactrelation2;
	private String contactrelation3;
	private String contacttel1;
	private String contacttel2;
	private String contacttel3;
	private String corpaddress;
	private String corpcity;
	private String corpcounty;
	private String corpkind;
	private String corpname;
	private String corpprovice;
	private String corpsize;
	private String corptel;
	private String creditpic1;
	private String creditpic2;
	private String creditpic3;
	private String creditpic4;
	private String department;
	private String description;
	private String education;
	private String gender;
	private String hometownaddress;
	private String hometowncity;
	private String hometowncounty;
	private String hometownprovice;
	private String housestatus;
	private String idcardno;
	private String loantitle;
	private String maritalstatus;
	private String monthlycarloan;
	private String monthlyhouseloan;
	private String monthlyincome;
	private String name;
	private String nowaddress;
	private String nowcity;
	private String nowcountry;
	private String nowprovice;
	private String officejoindate;
	private String phonenumber;
	private String producttype;
	private String qqno;
	private String repaytype;
	private String salarypic1;
	private String salarypic2;
	private String salarypic3;
	private String salarypic4;
	private String term;
	private String title;
	private String use;
	private Double amount;
	private Double intrest;
	private List<ElementPicList> picList;

	public SearchInputBorrowInfoResponse() {
		super();
		setMessageId("searchInputBorrowInfo");
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
	 * @return 购车情况
	 */
	public String getCarstatus() {
		return carstatus;
	}

	public void setCarstatus(String carstatus) {
		this.carstatus = carstatus;
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
	 * @return 家庭联系人姓名
	 */
	public String getContactname1() {
		return contactname1;
	}

	public void setContactname1(String contactname1) {
		this.contactname1 = contactname1;
	}

	/**
	 * @return 工作联系人姓名
	 */
	public String getContactname2() {
		return contactname2;
	}

	public void setContactname2(String contactname2) {
		this.contactname2 = contactname2;
	}

	/**
	 * @return 其他联系人姓名
	 */
	public String getContactname3() {
		return contactname3;
	}

	public void setContactname3(String contactname3) {
		this.contactname3 = contactname3;
	}

	/**
	 * @return 家庭联系人关系
	 */
	public String getContactrelation1() {
		return contactrelation1;
	}

	public void setContactrelation1(String contactrelation1) {
		this.contactrelation1 = contactrelation1;
	}

	/**
	 * @return 工作联系人关系
	 */
	public String getContactrelation2() {
		return contactrelation2;
	}

	public void setContactrelation2(String contactrelation2) {
		this.contactrelation2 = contactrelation2;
	}

	/**
	 * @return 其他联系人关系
	 */
	public String getContactrelation3() {
		return contactrelation3;
	}

	public void setContactrelation3(String contactrelation3) {
		this.contactrelation3 = contactrelation3;
	}

	/**
	 * @return 家庭联系人电话
	 */
	public String getContacttel1() {
		return contacttel1;
	}

	public void setContacttel1(String contacttel1) {
		this.contacttel1 = contacttel1;
	}

	/**
	 * @return 工作联系人电话
	 */
	public String getContacttel2() {
		return contacttel2;
	}

	public void setContacttel2(String contacttel2) {
		this.contacttel2 = contacttel2;
	}

	/**
	 * @return 其他联系人电话
	 */
	public String getContacttel3() {
		return contacttel3;
	}

	public void setContacttel3(String contacttel3) {
		this.contacttel3 = contacttel3;
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
	 * @return 信用报告图片1
	 */
	public String getCreditpic1() {
		return creditpic1;
	}

	public void setCreditpic1(String creditpic1) {
		this.creditpic1 = creditpic1;
	}

	/**
	 * @return 信用报告图片2
	 */
	public String getCreditpic2() {
		return creditpic2;
	}

	public void setCreditpic2(String creditpic2) {
		this.creditpic2 = creditpic2;
	}

	/**
	 * @return 信用报告图片3
	 */
	public String getCreditpic3() {
		return creditpic3;
	}

	public void setCreditpic3(String creditpic3) {
		this.creditpic3 = creditpic3;
	}

	/**
	 * @return 信用报告图片4
	 */
	public String getCreditpic4() {
		return creditpic4;
	}

	public void setCreditpic4(String creditpic4) {
		this.creditpic4 = creditpic4;
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
	 * @return 借款描述
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	 * @return 性别
	 */
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
	 * @return 住房情况
	 */
	public String getHousestatus() {
		return housestatus;
	}

	public void setHousestatus(String housestatus) {
		this.housestatus = housestatus;
	}

	/**
	 * @return 身份证号
	 */
	public String getIdcardno() {
		return idcardno;
	}

	public void setIdcardno(String idcardno) {
		this.idcardno = idcardno;
	}

	/**
	 * @return 借款标题
	 */
	public String getLoantitle() {
		return loantitle;
	}

	public void setLoantitle(String loantitle) {
		this.loantitle = loantitle;
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
	 * @return 车贷月供
	 */
	public String getMonthlycarloan() {
		return monthlycarloan;
	}

	public void setMonthlycarloan(String monthlycarloan) {
		this.monthlycarloan = monthlycarloan;
	}

	/**
	 * @return 房贷月供
	 */
	public String getMonthlyhouseloan() {
		return monthlyhouseloan;
	}

	public void setMonthlyhouseloan(String monthlyhouseloan) {
		this.monthlyhouseloan = monthlyhouseloan;
	}

	/**
	 * @return 月收入
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
	 * @return 进入公司时间
	 */
	public String getOfficejoindate() {
		return officejoindate;
	}

	public void setOfficejoindate(String officejoindate) {
		this.officejoindate = officejoindate;
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
	 * @return 产品类型
	 */
	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
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

	/**
	 * @return 还款方式
	 */
	public String getRepaytype() {
		return repaytype;
	}

	public void setRepaytype(String repaytype) {
		this.repaytype = repaytype;
	}

	/**
	 * @return 工资流水图片1
	 */
	public String getSalarypic1() {
		return salarypic1;
	}

	public void setSalarypic1(String salarypic1) {
		this.salarypic1 = salarypic1;
	}

	/**
	 * @return 工资流水图片2
	 */
	public String getSalarypic2() {
		return salarypic2;
	}

	public void setSalarypic2(String salarypic2) {
		this.salarypic2 = salarypic2;
	}

	/**
	 * @return 工资流水图片3
	 */
	public String getSalarypic3() {
		return salarypic3;
	}

	public void setSalarypic3(String salarypic3) {
		this.salarypic3 = salarypic3;
	}

	/**
	 * @return 工资流水图片4
	 */
	public String getSalarypic4() {
		return salarypic4;
	}

	public void setSalarypic4(String salarypic4) {
		this.salarypic4 = salarypic4;
	}

	/**
	 * @return 期限
	 */
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
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

	/**
	 * @return 用途
	 */
	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	/**
	 * @return 借款金额
	 */
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return 借款利率
	 */
	public Double getIntrest() {
		return intrest;
	}

	public void setIntrest(Double intrest) {
		this.intrest = intrest;
	}

	/**
	 * @return 图片列表
	 */
	public List<ElementPicList> getPicList() {
		return picList;
	}

	public void setPicList(List<ElementPicList> picList) {
		this.picList = picList;
	}
}