package net.zkbc.p2p.fep.message.protocol;

/**
 * 删除用户收货地址.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class DelUserAddressRequest extends RequestSupport {

	private String seqNo;

	public DelUserAddressRequest() {
		super();
		setMessageId("delUserAddress");
	}	

	/**
	 * @return 地址序列号
	 */
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
}