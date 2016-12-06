package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取系统信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetSystemInfoResponse extends ResponseSupport {

	private String aboutmeurl;
	private String csphonenumber;

	public GetSystemInfoResponse() {
		super();
		setMessageId("getSystemInfo");
	}


	/**
	 * @return 关于我们网页
	 */
	public String getAboutmeurl() {
		return aboutmeurl;
	}

	public void setAboutmeurl(String aboutmeurl) {
		this.aboutmeurl = aboutmeurl;
	}

	/**
	 * @return 客服电话
	 */
	public String getCsphonenumber() {
		return csphonenumber;
	}

	public void setCsphonenumber(String csphonenumber) {
		this.csphonenumber = csphonenumber;
	}
}