package net.zkbc.p2p.fep.message.protocol;

/**
 * 绑定银行卡.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class BindingBankcardRequest extends RequestSupport {

	private String bankcardno;
	private String bankcode;
	private String bankname;

	public BindingBankcardRequest() {
		super();
		setMessageId("bindingBankcard");
	}	

	/**
	 * @return 银行卡号
	 */
	public String getBankcardno() {
		return bankcardno;
	}

	public void setBankcardno(String bankcardno) {
		this.bankcardno = bankcardno;
	}

	/**
	 * @return 开户行编码
	 */
	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	/**
	 * @return 开户行名称
	 */
	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
}