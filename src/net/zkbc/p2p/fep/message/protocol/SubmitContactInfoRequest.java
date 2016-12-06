package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交用户联系人信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitContactInfoRequest extends RequestSupport {

	private String contactname1;
	private String contactname2;
	private String contactname3;
	private String contactrelation1;
	private String contactrelation2;
	private String contactrelation3;
	private String contacttel1;
	private String contacttel2;
	private String contacttel3;

	public SubmitContactInfoRequest() {
		super();
		setMessageId("submitContactInfo");
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
}