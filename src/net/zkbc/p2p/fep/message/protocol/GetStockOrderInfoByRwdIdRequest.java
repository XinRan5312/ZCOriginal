package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据项目回报id获取股权项目确认订单相关信息（股权订单确认）.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetStockOrderInfoByRwdIdRequest extends RequestSupport {

	private String rwdId;

	public GetStockOrderInfoByRwdIdRequest() {
		super();
		setMessageId("getStockOrderInfoByRwdId");
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