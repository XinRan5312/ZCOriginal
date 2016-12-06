package net.zkbc.p2p.fep.message.protocol;

/**
 * 发送私信.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SendLetterRequest extends RequestSupport {

	private String letter;
	private String letterTyp;
	private String parentId;
	private String prjId;
	private String relateCustId;
	private String title;

	public SendLetterRequest() {
		super();
		setMessageId("sendLetter");
	}	

	/**
	 * @return 信件内容
	 */
	public String getLetter() {
		return letter;
	}

	public void setLetter(String letter) {
		this.letter = letter;
	}

	/**
	 * @return 发送类型  10-发送信   15-群发  20-接收信
	 */
	public String getLetterTyp() {
		return letterTyp;
	}

	public void setLetterTyp(String letterTyp) {
		this.letterTyp = letterTyp;
	}

	/**
	 * @return 父信件Id   对于发送者填0   对于收到信件填发送者信件ID
	 */
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return 项目Id，可以给项目发送
	 */
	public String getPrjId() {
		return prjId;
	}

	public void setPrjId(String prjId) {
		this.prjId = prjId;
	}

	/**
	 * @return 关联客户Id
	 */
	public String getRelateCustId() {
		return relateCustId;
	}

	public void setRelateCustId(String relateCustId) {
		this.relateCustId = relateCustId;
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