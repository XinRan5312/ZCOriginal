package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取可用余额.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetBalamountRequest extends RequestSupport {

	private String type;

	public GetBalamountRequest() {
		super();
		setMessageId("getBalamount");
	}	

	/**
	 * @return 余额类型：0-网关1-托管
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}