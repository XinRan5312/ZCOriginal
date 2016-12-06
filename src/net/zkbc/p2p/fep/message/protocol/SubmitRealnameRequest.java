package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交实名认证.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitRealnameRequest extends RequestSupport {

	private String idpica;
	private String idpicb;

	public SubmitRealnameRequest() {
		super();
		setMessageId("submitRealname");
	}	

	/**
	 * @return 身份证正面
	 */
	public String getIdpica() {
		return idpica;
	}

	public void setIdpica(String idpica) {
		this.idpica = idpica;
	}

	/**
	 * @return 身份证反面
	 */
	public String getIdpicb() {
		return idpicb;
	}

	public void setIdpicb(String idpicb) {
		this.idpicb = idpicb;
	}
}