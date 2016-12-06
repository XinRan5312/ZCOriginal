package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据项目回报id获取项目相关信息（预约订单确认）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPreOrderInfoByRwdIdRequest extends RequestSupport {

	private String rwdId;

	public GetPreOrderInfoByRwdIdRequest() {
		super();
		setMessageId("getPreOrderInfoByRwdId");
	}	

	/**
	 * @return 项目回报id
	 */
	public String getRwdId() {
		return rwdId;
	}

	public void setRwdId(String rwdId) {
		this.rwdId = rwdId;
	}
}