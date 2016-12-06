package net.zkbc.p2p.fep.message.protocol;

/**
 * 得到服务器时间.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetServerTimeResponse extends ResponseSupport {

	private String serverTime;

	public GetServerTimeResponse() {
		super();
		setMessageId("getServerTime");
	}


	/**
	 * @return 服务器时间
	 */
	public String getServerTime() {
		return serverTime;
	}

	public void setServerTime(String serverTime) {
		this.serverTime = serverTime;
	}
}