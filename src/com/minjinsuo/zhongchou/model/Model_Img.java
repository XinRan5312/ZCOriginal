package com.minjinsuo.zhongchou.model;

import android.graphics.Bitmap;

public class Model_Img {
	private String id;
	private String type;
	private String url;
	private String sdUrl;
	private String fileName;
	private Bitmap pic_bitmap;

	public Bitmap getPic_bitmap() {
		return pic_bitmap;
	}

	public void setPic_bitmap(Bitmap pic_bitmap) {
		this.pic_bitmap = pic_bitmap;
	}

	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            文件名称
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSdUrl() {
		return sdUrl;
	}

	public void setSdUrl(String sdUrl) {
		this.sdUrl = sdUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            :图片网络加载地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public Model_Img() {
		super();
	}

	public Model_Img(String id, String type, String url) {
		super();
		this.id = id;
		this.type = type;
		this.url = url;
	}

	public Model_Img(String id, String type, String url, String sdUrl,
			String name) {
		super();
		this.id = id;
		this.type = type;
		this.url = url;
		this.sdUrl = sdUrl;
		this.fileName = name;
	}

}
