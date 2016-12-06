package net.zkbc.p2p.fep.message.protocol;

/**
 * 验证身份证（调用第三方接口 每次￥2.00）.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class VerifyIDCardResponse extends ResponseSupport {

	private String result;

	public VerifyIDCardResponse() {
		super();
		setMessageId("verifyIDCard");
	}


	/**
	 * @return 提示信息（通过验证过后 后台自动将其身份证信息修改入库）
	 */
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}