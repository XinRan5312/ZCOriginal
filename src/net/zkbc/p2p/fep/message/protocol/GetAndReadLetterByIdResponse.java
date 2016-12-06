package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取信件详细信息并设置未读为已读.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetAndReadLetterByIdResponse extends ResponseSupport {

	private String id;
	private String letter;
	private String letterTime;
	private String ownerCustId;
	private String ownerLoginNam;
	private String relateCustId;
	private String relateLoginNam;
	private String title;

	public GetAndReadLetterByIdResponse() {
		super();
		setMessageId("getAndReadLetterById");
	}


	/**
	 * @return 信件主键id
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	 * @return 信件产生时间
	 */
	public String getLetterTime() {
		return letterTime;
	}

	public void setLetterTime(String letterTime) {
		this.letterTime = letterTime;
	}

	/**
	 * @return 拥有着id
	 */
	public String getOwnerCustId() {
		return ownerCustId;
	}

	public void setOwnerCustId(String ownerCustId) {
		this.ownerCustId = ownerCustId;
	}

	/**
	 * @return 信件拥有者登录名
	 */
	public String getOwnerLoginNam() {
		return ownerLoginNam;
	}

	public void setOwnerLoginNam(String ownerLoginNam) {
		this.ownerLoginNam = ownerLoginNam;
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
	 * @return 关联登录名
	 */
	public String getRelateLoginNam() {
		return relateLoginNam;
	}

	public void setRelateLoginNam(String relateLoginNam) {
		this.relateLoginNam = relateLoginNam;
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