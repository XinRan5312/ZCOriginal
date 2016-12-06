package net.zkbc.p2p.fep.message.protocol;

public class GetAgreementContentRequest extends RequestSupport {
	private String type;

	public GetAgreementContentRequest() {
		super();
		setMessageId("getAgreementContent");
	}

	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            协议类型 1产品和公益发起众筹协议 2股权发起众筹协议 3产品订单下单协议 4股权订单下单协议 5产品订单付款协议
	 *            6股权订单付款协议
	 */
	public void setType(String type) {
		this.type = type;
	}

}
