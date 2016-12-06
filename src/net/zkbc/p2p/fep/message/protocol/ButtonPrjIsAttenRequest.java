package net.zkbc.p2p.fep.message.protocol;

/**
 * 项目关注功能.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ButtonPrjIsAttenRequest extends RequestSupport {

	private String isAttentFlag;
	private String prjId;

	public ButtonPrjIsAttenRequest() {
		super();
		setMessageId("buttonPrjIsAtten");
	}	

	/**
	 * @return 是否关注：1-关注 0-取消关注
	 */
	public String getIsAttentFlag() {
		return isAttentFlag;
	}

	public void setIsAttentFlag(String isAttentFlag) {
		this.isAttentFlag = isAttentFlag;
	}

	/**
	 * @return 项目id
	 */
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}
}