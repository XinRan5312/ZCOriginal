package net.zkbc.p2p.fep.message.protocol;

/**
 * 计算债权价值及手续费.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CalculateDebtRequest extends RequestSupport {

	private Integer debtid;
	private Double diccountrate;

	public CalculateDebtRequest() {
		super();
		setMessageId("calculateDebt");
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

	/**
	 * @return 折价率
	 */
	public Double getDiccountrate() {
		return diccountrate;
	}

	public void setDiccountrate(Double diccountrate) {
		this.diccountrate = diccountrate;
	}
}