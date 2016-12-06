package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交用户个人财务信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitFinancialInfoRequest extends RequestSupport {

	private String bondstatus;
	private String bondvalue;
	private String carbrand;
	private String carstatus;
	private String carvalue;
	private String housestatus;
	private String housingarea;
	private String monthlycarloan;
	private String monthlyhouseloan;
	private String monthlyincome;
	private String payday;
	private String paytype;

	public SubmitFinancialInfoRequest() {
		super();
		setMessageId("submitFinancialInfo");
	}	

	/**
	 * @return 证券情况
	 */
	public String getBondstatus() {
		return bondstatus;
	}

	public void setBondstatus(String bondstatus) {
		this.bondstatus = bondstatus;
	}

	/**
	 * @return 证券价值
	 */
	public String getBondvalue() {
		return bondvalue;
	}

	public void setBondvalue(String bondvalue) {
		this.bondvalue = bondvalue;
	}

	/**
	 * @return 车辆品牌
	 */
	public String getCarbrand() {
		return carbrand;
	}

	public void setCarbrand(String carbrand) {
		this.carbrand = carbrand;
	}

	/**
	 * @return 购车情况
	 */
	public String getCarstatus() {
		return carstatus;
	}

	public void setCarstatus(String carstatus) {
		this.carstatus = carstatus;
	}

	/**
	 * @return 汽车价值
	 */
	public String getCarvalue() {
		return carvalue;
	}

	public void setCarvalue(String carvalue) {
		this.carvalue = carvalue;
	}

	/**
	 * @return 住房情况
	 */
	public String getHousestatus() {
		return housestatus;
	}

	public void setHousestatus(String housestatus) {
		this.housestatus = housestatus;
	}

	/**
	 * @return 住房面积
	 */
	public String getHousingarea() {
		return housingarea;
	}

	public void setHousingarea(String housingarea) {
		this.housingarea = housingarea;
	}

	/**
	 * @return 车贷月供
	 */
	public String getMonthlycarloan() {
		return monthlycarloan;
	}

	public void setMonthlycarloan(String monthlycarloan) {
		this.monthlycarloan = monthlycarloan;
	}

	/**
	 * @return 房贷月供
	 */
	public String getMonthlyhouseloan() {
		return monthlyhouseloan;
	}

	public void setMonthlyhouseloan(String monthlyhouseloan) {
		this.monthlyhouseloan = monthlyhouseloan;
	}

	/**
	 * @return 月收入
	 */
	public String getMonthlyincome() {
		return monthlyincome;
	}

	public void setMonthlyincome(String monthlyincome) {
		this.monthlyincome = monthlyincome;
	}

	/**
	 * @return 发薪日期
	 */
	public String getPayday() {
		return payday;
	}

	public void setPayday(String payday) {
		this.payday = payday;
	}

	/**
	 * @return 发薪方式
	 */
	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
}