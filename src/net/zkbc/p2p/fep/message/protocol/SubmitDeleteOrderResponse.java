package net.zkbc.p2p.fep.message.protocol;

/**
 * 删除订单.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitDeleteOrderResponse extends ResponseSupport {

	private String message;
	private String result;

	public SubmitDeleteOrderResponse() {
		super();
		setMessageId("submitDeleteOrder");
	}


	/**
	 * @return 反馈信息
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return success-成功 fail 失败
	 */
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}