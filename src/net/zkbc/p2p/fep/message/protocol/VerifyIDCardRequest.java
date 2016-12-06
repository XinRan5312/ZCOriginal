package net.zkbc.p2p.fep.message.protocol;

/**
 * 验证身份证（调用第三方接口 每次￥2.00）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class VerifyIDCardRequest extends RequestSupport {

	private String cardNo;
	private String name;

	public VerifyIDCardRequest() {
		super();
		setMessageId("verifyIDCard");
	}	

	/**
	 * @return 身份证号码
	 */
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return 身份证姓名
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}