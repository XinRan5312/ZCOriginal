package com.minjinsuo.zhongchou.model;

import java.io.Serializable;

/**
 * List<String>多选时使用（带记录选中状态）
 * 
 * @author zp
 *
 *         2016年9月22日
 */
public class Model_SelectMore implements Serializable {
	private String id;
	private String content;
	private boolean isChecked;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return 是否被选中
	 */
	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

}
