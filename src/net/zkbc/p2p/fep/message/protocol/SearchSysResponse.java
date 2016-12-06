package net.zkbc.p2p.fep.message.protocol;

/**
 * 请求系统参数.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchSysResponse extends ResponseSupport {

	private Integer dzje;
	private Integer gmsxhbdyts;
	private Integer hkjsbdyts;
	private Integer qtje;
	private Double diccountratemax;
	private Double diccountratemin;
	private Double fee;
	private Double feemax;
	private Double feemin;

	public SearchSysResponse() {
		super();
		setMessageId("searchSys");
	}


	/**
	 * @return 个人标递增金额
	 */
	public Integer getDzje() {
		return dzje;
	}

	public void setDzje(Integer dzje) {
		this.dzje = dzje;
	}

	/**
	 * @return 购买生效后不低于天数
	 */
	public Integer getGmsxhbdyts() {
		return gmsxhbdyts;
	}

	public void setGmsxhbdyts(Integer gmsxhbdyts) {
		this.gmsxhbdyts = gmsxhbdyts;
	}

	/**
	 * @return 距离还款结束不低于
	 */
	public Integer getHkjsbdyts() {
		return hkjsbdyts;
	}

	public void setHkjsbdyts(Integer hkjsbdyts) {
		this.hkjsbdyts = hkjsbdyts;
	}

	/**
	 * @return 个人标起投金额
	 */
	public Integer getQtje() {
		return qtje;
	}

	public void setQtje(Integer qtje) {
		this.qtje = qtje;
	}

	/**
	 * @return 折让率最大值
	 */
	public Double getDiccountratemax() {
		return diccountratemax;
	}

	public void setDiccountratemax(Double diccountratemax) {
		this.diccountratemax = diccountratemax;
	}

	/**
	 * @return 折让率最小值
	 */
	public Double getDiccountratemin() {
		return diccountratemin;
	}

	public void setDiccountratemin(Double diccountratemin) {
		this.diccountratemin = diccountratemin;
	}

	/**
	 * @return 转让手续费
	 */
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	/**
	 * @return 手续费最大值
	 */
	public Double getFeemax() {
		return feemax;
	}

	public void setFeemax(Double feemax) {
		this.feemax = feemax;
	}

	/**
	 * @return 手续费最小值
	 */
	public Double getFeemin() {
		return feemin;
	}

	public void setFeemin(Double feemin) {
		this.feemin = feemin;
	}
}