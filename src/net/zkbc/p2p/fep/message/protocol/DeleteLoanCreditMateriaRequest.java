package net.zkbc.p2p.fep.message.protocol;

/**
 * 删除用户上传资料.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class DeleteLoanCreditMateriaRequest extends RequestSupport {

	private String id;

	public DeleteLoanCreditMateriaRequest() {
		super();
		setMessageId("deleteLoanCreditMateria");
	}	

	/**
	 * @return 图片id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}