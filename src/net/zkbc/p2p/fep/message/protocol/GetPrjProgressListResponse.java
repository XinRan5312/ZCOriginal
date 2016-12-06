package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 根据项目id获取项目进展.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjProgressListResponse extends ResponseSupport implements Serializable{

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjProgressListResponse#getPrjProgressList
	 * 
	 */
	public static class ElementPrjProgressList implements Serializable{

		private String createTime;
		private String fileAddrs;
		private String fileIdMemo;
		private String title;

		/**
		 * @return 创建时间
		 */
		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		/**
		 * @return 图片地址（多张图片用英文逗号隔开）
		 */
		public String getFileAddrs() {
			return fileAddrs;
		}

		public void setFileAddrs(String fileAddrs) {
			this.fileAddrs = fileAddrs;
		}

		/**
		 * @return 进展内容
		 */
		public String getFileIdMemo() {
			return fileIdMemo;
		}

		public void setFileIdMemo(String fileIdMemo) {
			this.fileIdMemo = fileIdMemo;
		}

		/**
		 * @return 标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	private List<ElementPrjProgressList> prjProgressList;

	public GetPrjProgressListResponse() {
		super();
		setMessageId("getPrjProgressList");
	}


	/**
	 * @return 进展列表
	 */
	public List<ElementPrjProgressList> getPrjProgressList() {
		return prjProgressList;
	}

	public void setPrjProgressList(List<ElementPrjProgressList> prjProgressList) {
		this.prjProgressList = prjProgressList;
	}
}