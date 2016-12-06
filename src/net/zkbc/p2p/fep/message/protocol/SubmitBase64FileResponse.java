package net.zkbc.p2p.fep.message.protocol;

/**
 * 上传base64加密类型文件.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitBase64FileResponse extends ResponseSupport {

	private String fileId;
	private String fileLoadAddr;
	private String message;
	private String result;

	public SubmitBase64FileResponse() {
		super();
		setMessageId("submitBase64File");
	}


	/**
	 * @return 文件id
	 */
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return 文件下载地址
	 */
	public String getFileLoadAddr() {
		return fileLoadAddr;
	}

	public void setFileLoadAddr(String fileLoadAddr) {
		this.fileLoadAddr = fileLoadAddr;
	}

	/**
	 * @return 反馈信息
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return success-成功 fail 失败
	 */
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}