package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取项目介绍信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjIntroByPrjIdResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjIntroByPrjIdResponse#getPrjIntroList
	 * 
	 */
	public static class ElementPrjIntroList {

		private String dispOrd;
		private String fileIdMemo;
		private String fileIdPic;
		private String introImgAddress;
		private String prjId;
		private String seqNo;
		private String title;

		/**
		 * @return 显示顺序
		 */
		public String getDispOrd() {
			return dispOrd;
		}

		public void setDispOrd(String dispOrd) {
			this.dispOrd = dispOrd;
		}

		/**
		 * @return 备注文件标识
		 */
		public String getFileIdMemo() {
			return fileIdMemo;
		}

		public void setFileIdMemo(String fileIdMemo) {
			this.fileIdMemo = fileIdMemo;
		}

		/**
		 * @return 图片
		 */
		public String getFileIdPic() {
			return fileIdPic;
		}

		public void setFileIdPic(String fileIdPic) {
			this.fileIdPic = fileIdPic;
		}

		/**
		 * @return 图片地址
		 */
		public String getIntroImgAddress() {
			return introImgAddress;
		}

		public void setIntroImgAddress(String introImgAddress) {
			this.introImgAddress = introImgAddress;
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

		/**
		 * @return 子流水
		 */
		public String getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(String seqNo) {
			this.seqNo = seqNo;
		}

		/**
		 * @return 说明标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	private List<ElementPrjIntroList> prjIntroList;

	public GetPrjIntroByPrjIdResponse() {
		super();
		setMessageId("getPrjIntroByPrjId");
	}


	/**
	 * @return 介绍列表
	 */
	public List<ElementPrjIntroList> getPrjIntroList() {
		return prjIntroList;
	}

	public void setPrjIntroList(List<ElementPrjIntroList> prjIntroList) {
		this.prjIntroList = prjIntroList;
	}
}