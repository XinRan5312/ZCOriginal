package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 提交用户上传资料信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitUploadPicRequest extends RequestSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.SubmitUploadPicRequest#getPicList
	 * 
	 */
	public static class ElementPicList {

		private String picname;
		private String pictype;

		/**
		 * @return 关键字
		 */
		public String getPicname() {
			return picname;
		}

		public void setPicname(String picname) {
			this.picname = picname;
		}

		/**
		 * @return 行号
		 */
		public String getPictype() {
			return pictype;
		}

		public void setPictype(String pictype) {
			this.pictype = pictype;
		}
	}

	private List<ElementPicList> picList;

	public SubmitUploadPicRequest() {
		super();
		setMessageId("submitUploadPic");
	}	

	/**
	 * @return 图片列表
	 */
	public List<ElementPicList> getPicList() {
		return picList;
	}

	public void setPicList(List<ElementPicList> picList) {
		this.picList = picList;
	}
}