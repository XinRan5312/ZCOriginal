package net.zkbc.p2p.fep.message.protocol;

/**
 * 挂牌转让债权.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SellDebtRequest extends RequestSupport {

	private String discount;
	private Integer debtid;

	public SellDebtRequest() {
		super();
		setMessageId("sellDebt");
	}	

	/**
	 * @return 折让率
	 */
	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
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