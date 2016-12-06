package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交平台领投人认证.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitApplyUserLeaderResponse extends ResponseSupport {

	private String message;
	private String result;

	public SubmitApplyUserLeaderResponse() {
		super();
		setMessageId("submitApplyUserLeader");
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