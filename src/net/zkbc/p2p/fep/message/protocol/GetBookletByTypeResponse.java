package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 根据类型获取字典表信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetBookletByTypeResponse extends ResponseSupport implements
		Serializable {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetBookletByTypeResponse#getBookletList
	 * 
	 */
	public static class ElementBookletList implements Serializable {

		private String code;
		private String display;
		private String enabled;
		private String iconAddr;
		private String value;

		/**
		 * @return 编码
		 */
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		/**
		 * @return 字典显示名称
		 */
		public String getDisplay() {
			return display;
		}

		public void setDisplay(String display) {
			this.display = display;
		}

		/**
		 * @return 是否可用（1-可用 0-不可用）
		 */
		public String getEnabled() {
			return enabled;
		}

		public void setEnabled(String enabled) {
			this.enabled = enabled;
		}

		/**
		 * @return 图标地址
		 */
		public String getIconAddr() {
			return iconAddr;
		}

		public void setIconAddr(String iconAddr) {
			this.iconAddr = iconAddr;
		}

		/**
		 * @return 字典值
		 */
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	private List<ElementBookletList> bookletList;

	public GetBookletByTypeResponse() {
		super();
		setMessageId("getBookletByType");
	}

	/**
	 * @return 字典项信息列表
	 */
	public List<ElementBookletList> getBookletList() {
		return bookletList;
	}

	public void setBookletList(List<ElementBookletList> bookletList) {
		this.bookletList = bookletList;
	}
}