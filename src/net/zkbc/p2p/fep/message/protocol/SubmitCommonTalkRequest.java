package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交发起话题.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitCommonTalkRequest extends RequestSupport {

	private String content;
	private Integer ctId;
	private Integer prId;

	public SubmitCommonTalkRequest() {
		super();
		setMessageId("submitCommonTalk");
	}	

	/**
	 * @return 输入的内容
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return 话题id，如果为空，则此话题不是回复记录
	 */
	public Integer getCtId() {
		return ctId;
	}

	public void setCtId(Integer ctId) {
		this.ctId = ctId;
	}

	/**
	 * @return 项目id
	 */
	public Integer getPrId() {
		return prId;
	}

	public void setPrId(Integer prId) {
		this.prId = prId;
	}
}