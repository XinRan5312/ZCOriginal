package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交订单.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitPrjOrderRequest extends RequestSupport {

	private String buyCnt;
	private String redMoneyId;
	private String remark;
	private String rwdId;
	private String seqNoAddr;
	private String subId;

	public SubmitPrjOrderRequest() {
		super();
		setMessageId("submitPrjOrder");
	}	

	/**
	 * @return 购买数量
	 */
	public String getBuyCnt() {
		return buyCnt;
	}

	public void setBuyCnt(String buyCnt) {
		this.buyCnt = buyCnt;
	}

	/**
	 * @return 红包id
	 */
	public String getRedMoneyId() {
		return redMoneyId;
	}

	public void setRedMoneyId(String redMoneyId) {
		this.redMoneyId = redMoneyId;
	}

	/**
	 * @return 客户备注
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return 项目回报id
	 */
	public String getRwdId() {
		return rwdId;
	}

	public void setRwdId(String rwdId) {
		this.rwdId = rwdId;
	}

	/**
	 * @return 地址序列号
	 */
	public String getSeqNoAddr() {
		return seqNoAddr;
	}

	public void setSeqNoAddr(String seqNoAddr) {
		this.seqNoAddr = seqNoAddr;
	}

	/**
	 * @return 提交id(获取数据时反馈的避免重复提交的参数)
	 */
	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}
}