package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据项目回报id获取产品项目确认订单相关信息（产品订单确认）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetProdOrderInfoByRwdIdRequest extends RequestSupport {

	private String rwdId;

	public GetProdOrderInfoByRwdIdRequest() {
		super();
		setMessageId("getProdOrderInfoByRwdId");
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