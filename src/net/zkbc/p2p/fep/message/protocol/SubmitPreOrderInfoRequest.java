package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交预约的信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitPreOrderInfoRequest extends RequestSupport {

	private String buyCnt;
	private String id;
	private String subId;
	private String seqNoAddr;

	public SubmitPreOrderInfoRequest() {
		super();
		setMessageId("submitPreOrderInfo");
	}

	public String getSeqNoAddr() {
		return seqNoAddr;
	}

	/**
	 * @param seqNoAddr
	 *            ：收货地址序列号
	 */
	public void setSeqNoAddr(String seqNoAddr) {
		this.seqNoAddr = seqNoAddr;
	}

	/**
	 * @return 购买份数
	 */
	public String getBuyCnt() {
		return buyCnt;
	}

	public void setBuyCnt(String buyCnt) {
		this.buyCnt = buyCnt;
	}

	/**
	 * @return 回报id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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