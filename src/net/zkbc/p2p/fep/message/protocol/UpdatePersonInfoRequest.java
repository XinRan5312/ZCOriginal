package net.zkbc.p2p.fep.message.protocol;

/**
 * 更新个人信息（QQ号）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class UpdatePersonInfoRequest extends RequestSupport {

	private String qqUId;

	public UpdatePersonInfoRequest() {
		super();
		setMessageId("updatePersonInfo");
	}	

	/**
	 * @return QQ号码
	 */
	public String getQqUId() {
		return qqUId;
	}

	public void setQqUId(String qqUId) {
		this.qqUId = qqUId;
	}
}