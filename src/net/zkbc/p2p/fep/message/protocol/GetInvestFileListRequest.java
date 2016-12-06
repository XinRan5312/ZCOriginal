package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据项目id获取投资文件列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetInvestFileListRequest extends RequestSupport {

	private String prjId;

	public GetInvestFileListRequest() {
		super();
		setMessageId("getInvestFileList");
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
}