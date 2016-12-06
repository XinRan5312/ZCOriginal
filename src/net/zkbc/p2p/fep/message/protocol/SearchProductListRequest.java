package net.zkbc.p2p.fep.message.protocol;

/**
 * 查询借款产品列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchProductListRequest extends RequestSupport {

	private String type;

	public SearchProductListRequest() {
		super();
		setMessageId("searchProductList");
	}	

	/**
	 * @return 判断是企业的借款产品还是个人的借款产品
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}