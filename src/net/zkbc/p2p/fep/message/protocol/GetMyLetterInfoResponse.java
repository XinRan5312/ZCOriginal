package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 获取信件信件信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyLetterInfoResponse extends ResponseSupport implements Serializable{

	/**
	 * @see net.zkbc.p2p.message.protocol.GetMyLetterInfoResponse#getLetterList
	 * 
	 */
	public static class ElementLetterList implements Serializable{

		private String headerAddress;
		private String id;
		private String letter;
		private String letterTime;
		private String letterTyp;
		private String ownerCustId;
		private String ownerLoginNam;
		private String relateCustId;
		private String relateLoginNam;
		private String status;
		private String title;

		/**
		 * @return 相关人头像
		 */
		public String getHeaderAddress() {
			return headerAddress;
		}

		public void setHeaderAddress(String headerAddress) {
			this.headerAddress = headerAddress;
		}

		/**
		 * @return 
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
		 * @return 信件类型  10 发送信  15 群发  20 接收信
		 */
		public String getLetterTyp() {
			return letterTyp;
		}

		public void setLetterTyp(String letterTyp) {
			this.letterTyp = letterTyp;
		}

		/**
		 * @return 信件拥有者的客户Id
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
		 * @return 状态  10 未阅读  20 已阅读
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 信件标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	private List<ElementLetterList> letterList;

	public GetMyLetterInfoResponse() {
		super();
		setMessageId("getMyLetterInfo");
	}


	/**
	 * @return 信件信息
	 */
	public List<ElementLetterList> getLetterList() {
		return letterList;
	}

	public void setLetterList(List<ElementLetterList> letterList) {
		this.letterList = letterList;
	}
}