package net.zkbc.p2p.fep.message.protocol;

/**
 * 绑定手机号.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class BindingPhoneRequest extends RequestSupport {

	private String phonenumber;
	private String vfcode;

	public BindingPhoneRequest() {
		super();
		setMessageId("bindingPhone");
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
	 * @return 验证码
	 */
	public String getVfcode() {
		return vfcode;
	}

	public void setVfcode(String vfcode) {
		this.vfcode = vfcode;
	}
}