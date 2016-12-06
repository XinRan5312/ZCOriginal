package net.zkbc.p2p.fep.message.protocol;

/**
 * 设置默认地址.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SetDefaultAddressRequest extends RequestSupport {

	private String seqNo;

	public SetDefaultAddressRequest() {
		super();
		setMessageId("setDefaultAddress");
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