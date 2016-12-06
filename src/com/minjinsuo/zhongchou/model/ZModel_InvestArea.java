package com.minjinsuo.zhongchou.model;

import java.io.Serializable;
import java.util.List;

/**
 * 投资领域
 * 
 * @author zp
 *
 *         2016年9月2日
 */
public class ZModel_InvestArea {

	private String GroupName;
	private String GroupCode;
	private List<ElementBookletList> sonList;

	public String getGroupName() {
		return GroupName;
	}

	public void setGroupName(String groupName) {
		GroupName = groupName;
	}

	public String getGroupCode() {
		return GroupCode;
	}

	public void setGroupCode(String groupCode) {
		GroupCode = groupCode;
	}

	public List<ElementBookletList> getSonList() {
		return sonList;
	}

	public void setSonList(List<ElementBookletList> sonList) {
		this.sonList = sonList;
	}

	public static class ElementBookletList implements Serializable {

		private String code;
		private String display;
		private String enabled;
		private String iconAddr;
		private String value;
		private boolean isChecked;

		/**
		 * @return 是否被选中
		 */
		public boolean isChecked() {
			return isChecked;
		}

		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}

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

}
