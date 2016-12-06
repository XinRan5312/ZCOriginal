package net.zkbc.p2p.fep.message.protocol;

/**
 * 上传base64加密类型文件.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitBase64FileRequest extends RequestSupport {

	private String fileName;
	private String fileStr;

	public SubmitBase64FileRequest() {
		super();
		setMessageId("submitBase64File");
	}	

	/**
	 * @return 文件原名
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return 加密文件
	 */
	public String getFileStr() {
		return fileStr;
	}

	public void setFileStr(String fileStr) {
		this.fileStr = fileStr;
	}
}