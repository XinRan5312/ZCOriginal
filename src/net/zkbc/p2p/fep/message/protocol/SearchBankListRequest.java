package net.zkbc.p2p.fep.message.protocol;

/**
 * 模糊查询分支行.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchBankListRequest extends RequestSupport {

	private String bankcode;
	private String key;

	public SearchBankListRequest() {
		super();
		setMessageId("searchBankList");
	}	

	/**
	 * @return 行号
	 */
	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	/**
	 * @return 关键字
	 */
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}