package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据预约订单id取消预约订单.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitCancelPreOrderResponse extends ResponseSupport {

	private String message;
	private String result;

	public SubmitCancelPreOrderResponse() {
		super();
		setMessageId("submitCancelPreOrder");
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