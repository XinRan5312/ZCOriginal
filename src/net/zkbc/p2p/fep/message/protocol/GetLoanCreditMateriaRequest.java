package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取用户上传资料.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetLoanCreditMateriaRequest extends RequestSupport {

	private String type;

	public GetLoanCreditMateriaRequest() {
		super();
		setMessageId("getLoanCreditMateria");
	}	

	/**
	 * @return 图片类型
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}