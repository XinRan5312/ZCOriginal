package net.zkbc.p2p.fep.message.protocol;

/**
 * 计算债权价值及手续费.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CalculateDebtResponse extends ResponseSupport {

	private Double debtamount;
	private Double fee;

	public CalculateDebtResponse() {
		super();
		setMessageId("calculateDebt");
	}


	/**
	 * @return 债权价值
	 */
	public Double getDebtamount() {
		return debtamount;
	}

	public void setDebtamount(Double debtamount) {
		this.debtamount = debtamount;
	}

	/**
	 * @return 手续费
	 */
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
}