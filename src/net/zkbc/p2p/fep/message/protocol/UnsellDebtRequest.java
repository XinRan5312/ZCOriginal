package net.zkbc.p2p.fep.message.protocol;

/**
 * 债权转让撤回.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class UnsellDebtRequest extends RequestSupport {

	private Integer debtid;

	public UnsellDebtRequest() {
		super();
		setMessageId("unsellDebt");
	}	

	/**
	 * @return 债权唯一标识
	 */
	public Integer getDebtid() {
		return debtid;
	}

	public void setDebtid(Integer debtid) {
		this.debtid = debtid;
	}
}