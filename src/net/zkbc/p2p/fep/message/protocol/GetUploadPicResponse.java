package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取用户上传资料信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUploadPicResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetUploadPicResponse#getPicList
	 * 
	 */
	public static class ElementPicList {

		private String picname;
		private String pictype;

		/**
		 * @return 图片名称
		 */
		public String getPicname() {
			return picname;
		}

		public void setPicname(String picname) {
			this.picname = picname;
		}

		/**
		 * @return 图片类型：0-身份证明,1-信用报告,2-收入证明,3-居住地址证明,4-工作及职务证明
		 */
		public String getPictype() {
			return pictype;
		}

		public void setPictype(String pictype) {
			this.pictype = pictype;
		}
	}

	private List<ElementPicList> picList;

	public GetUploadPicResponse() {
		super();
		setMessageId("getUploadPic");
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