package net.zkbc.p2p.fep.message.protocol;

/**
 * 购买债权.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class BuyDebtRequest extends RequestSupport {

	private Integer debtid;

	public BuyDebtRequest() {
		super();
		setMessageId("buyDebt");
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