package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取订单确认数据(准备下订单).客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetConfirmOrderDataRequest extends RequestSupport {

	private String prjRwdId;

	public GetConfirmOrderDataRequest() {
		super();
		setMessageId("getConfirmOrderData");
	}	

	/**
	 * @return 项目回报id
	 */
	public String getPrjRwdId() {
		return prjRwdId;
	}

	public void setPrjRwdId(String prjRwdId) {
		this.prjRwdId = prjRwdId;
	}
}