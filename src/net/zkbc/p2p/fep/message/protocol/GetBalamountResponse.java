package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取可用余额.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetBalamountResponse extends ResponseSupport {

	private String balamount;

	public GetBalamountResponse() {
		super();
		setMessageId("getBalamount");
	}


	/**
	 * @return 账户可用余额
	 */
	public String getBalamount() {
		return balamount;
	}

	public void setBalamount(String balamount) {
		this.balamount = balamount;
	}
}