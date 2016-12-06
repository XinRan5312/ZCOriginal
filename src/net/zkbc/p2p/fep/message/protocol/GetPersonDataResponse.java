package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取用户填写资料状态.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPersonDataResponse extends ResponseSupport {

	private String ishaveconectinfo;
	private String ishavemoneyinfo;
	private String ishaveperinfo;
	private String ishaveworkinfo;
	private String isuploadinfo;

	public GetPersonDataResponse() {
		super();
		setMessageId("getPersonData");
	}


	/**
	 * @return 是否录入联系人信息：0-未录入、2-已录入
	 */
	public String getIshaveconectinfo() {
		return ishaveconectinfo;
	}

	public void setIshaveconectinfo(String ishaveconectinfo) {
		this.ishaveconectinfo = ishaveconectinfo;
	}

	/**
	 * @return 是否录入财务信息：0-未录入、1-已录入
	 */
	public String getIshavemoneyinfo() {
		return ishavemoneyinfo;
	}

	public void setIshavemoneyinfo(String ishavemoneyinfo) {
		this.ishavemoneyinfo = ishavemoneyinfo;
	}

	/**
	 * @return 是否录入个人信息：0-未录入、1-已录入
	 */
	public String getIshaveperinfo() {
		return ishaveperinfo;
	}

	public void setIshaveperinfo(String ishaveperinfo) {
		this.ishaveperinfo = ishaveperinfo;
	}

	/**
	 * @return 是否录入工作信息：0-未录入、1-已录入
	 */
	public String getIshaveworkinfo() {
		return ishaveworkinfo;
	}

	public void setIshaveworkinfo(String ishaveworkinfo) {
		this.ishaveworkinfo = ishaveworkinfo;
	}

	/**
	 * @return 是否上传资料信息：0-未上传、3-已上传
	 */
	public String getIsuploadinfo() {
		return isuploadinfo;
	}

	public void setIsuploadinfo(String isuploadinfo) {
		this.isuploadinfo = isuploadinfo;
	}
}