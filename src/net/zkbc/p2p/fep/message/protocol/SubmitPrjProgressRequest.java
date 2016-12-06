package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交项目进展.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitPrjProgressRequest extends RequestSupport {

	private String fileIdMemo;
	private String prjId;
	private String proFileIds;
	private String proFileNames;
	private String title;

	public SubmitPrjProgressRequest() {
		super();
		setMessageId("submitPrjProgress");
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
	 * @return 项目id
	 */
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * @return 进展附件（多个文件id用英文逗号隔开）
	 */
	public String getProFileIds() {
		return proFileIds;
	}

	public void setProFileIds(String proFileIds) {
		this.proFileIds = proFileIds;
	}

	/**
	 * @return 进展附件（多个文件源名称用英文逗号隔开，顺序与proFileIds一致）
	 */
	public String getProFileNames() {
		return proFileNames;
	}

	public void setProFileNames(String proFileNames) {
		this.proFileNames = proFileNames;
	}

	/**
	 * @return 进展标题
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}