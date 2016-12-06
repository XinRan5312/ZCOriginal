package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 根据协议类型查询协议内容.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetAgreementContentResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetAgreementContentResponse#getAgreemenList
	 * 
	 */
	public static class ElementAgreemenList {

		private String content;
		private String createTime;
		private String title;

		/**
		 * @return 协议内容
		 */
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

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
		 * @return 协议标题
		 */
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}

	private List<ElementAgreemenList> agreemenList;

	public GetAgreementContentResponse() {
		super();
		setMessageId("getAgreementContent");
	}

	/**
	 * @return 协议列表
	 */
	public List<ElementAgreemenList> getAgreemenList() {
		return agreemenList;
	}

	public void setAgreemenList(List<ElementAgreemenList> agreemenList) {
		this.agreemenList = agreemenList;
	}
}