package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 根据项目id获取项目公告.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjNoticeListResponse extends ResponseSupport implements
		Serializable {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjNoticeListResponse#getPrjNoticeList
	 * 
	 */
	public static class ElementPrjNoticeList implements Serializable {

		private String createTime;
		private String fileAddrs;
		private String fileIdMemo;
		private String fileNames;
		private String title;

		/**
		 * @return 发布时间
		 */
		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		/**
		 * @return 附件下载地址（多个地址用英文逗号隔开，顺序与附件名顺序一致）
		 */
		public String getFileAddrs() {
			return fileAddrs;
		}

		public void setFileAddrs(String fileAddrs) {
			this.fileAddrs = fileAddrs;
		}

		/**
		 * @return 公告内容
		 */
		public String getFileIdMemo() {
			return fileIdMemo;
		}

		public void setFileIdMemo(String fileIdMemo) {
			this.fileIdMemo = fileIdMemo;
		}

		/**
		 * @return 附件名称（多个附件用英文逗号隔开）
		 */
		public String getFileNames() {
			return fileNames;
		}

		public void setFileNames(String fileNames) {
			this.fileNames = fileNames;
		}

		/**
		 * @return 公告标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	private List<ElementPrjNoticeList> prjNoticeList;

	public GetPrjNoticeListResponse() {
		super();
		setMessageId("getPrjNoticeList");
	}

	/**
	 * @return 公告列表
	 */
	public List<ElementPrjNoticeList> getPrjNoticeList() {
		return prjNoticeList;
	}

	public void setPrjNoticeList(List<ElementPrjNoticeList> prjNoticeList) {
		this.prjNoticeList = prjNoticeList;
	}
}