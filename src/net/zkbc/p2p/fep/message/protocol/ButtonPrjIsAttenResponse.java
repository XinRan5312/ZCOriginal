package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 项目关注功能.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ButtonPrjIsAttenResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.ButtonPrjIsAttenResponse#getPrjAttent
	 * 
	 */
	public static class ElementPrjAttent {

		private String result;

		/**
		 * @return 成功或者失败
		 */
		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
	}

	private List<ElementPrjAttent> prjAttent;

	public ButtonPrjIsAttenResponse() {
		super();
		setMessageId("buttonPrjIsAtten");
	}


	/**
	 * @return 关注按钮功能
	 */
	public List<ElementPrjAttent> getPrjAttent() {
		return prjAttent;
	}

	public void setPrjAttent(List<ElementPrjAttent> prjAttent) {
		this.prjAttent = prjAttent;
	}
}