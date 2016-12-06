package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取用户上传资料.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetLoanCreditMateriaResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetLoanCreditMateriaResponse#getLoanCreditMateriaList
	 * 
	 */
	public static class ElementLoanCreditMateriaList {

		private String id;
		private String type;
		private String url;

		/**
		 * @return 图片 id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 图片类型
		 */
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		/**
		 * @return 图片url
		 */
		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	private List<ElementLoanCreditMateriaList> loanCreditMateriaList;

	public GetLoanCreditMateriaResponse() {
		super();
		setMessageId("getLoanCreditMateria");
	}


	/**
	 * @return 图片列表
	 */
	public List<ElementLoanCreditMateriaList> getLoanCreditMateriaList() {
		return loanCreditMateriaList;
	}

	public void setLoanCreditMateriaList(List<ElementLoanCreditMateriaList> loanCreditMateriaList) {
		this.loanCreditMateriaList = loanCreditMateriaList;
	}
}