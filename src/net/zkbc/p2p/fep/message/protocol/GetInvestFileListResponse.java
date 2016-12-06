package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 根据项目id获取投资文件列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetInvestFileListResponse extends ResponseSupport implements Serializable{

	/**
	 * @see net.zkbc.p2p.message.protocol.GetInvestFileListResponse#getInvestFileList
	 * 
	 */
	public static class ElementInvestFileList implements Serializable{

		private String fileAddr;
		private String fileNam;
		private String fileSize;

		/**
		 * @return 文件下载地址
		 */
		public String getFileAddr() {
			return fileAddr;
		}

		public void setFileAddr(String fileAddr) {
			this.fileAddr = fileAddr;
		}

		/**
		 * @return 文件名
		 */
		public String getFileNam() {
			return fileNam;
		}

		public void setFileNam(String fileNam) {
			this.fileNam = fileNam;
		}

		/**
		 * @return 文件大小
		 */
		public String getFileSize() {
			return fileSize;
		}

		public void setFileSize(String fileSize) {
			this.fileSize = fileSize;
		}
	}

	private List<ElementInvestFileList> investFileList;

	public GetInvestFileListResponse() {
		super();
		setMessageId("getInvestFileList");
	}


	/**
	 * @return 投资文件列表
	 */
	public List<ElementInvestFileList> getInvestFileList() {
		return investFileList;
	}

	public void setInvestFileList(List<ElementInvestFileList> investFileList) {
		this.investFileList = investFileList;
	}
}