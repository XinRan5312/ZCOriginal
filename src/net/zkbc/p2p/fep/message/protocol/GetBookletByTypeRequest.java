package net.zkbc.p2p.fep.message.protocol;

/**
 * 根据类型获取字典表信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetBookletByTypeRequest extends RequestSupport {

	private String type;

	public GetBookletByTypeRequest() {
		super();
		setMessageId("getBookletByType");
	}	

	/**
	 * @return 字典类型（prjTrade-项目行业，prjBStatus-项目状态）
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type(prjTrade-项目行业，prjBStatus-项目状态，authRightTyp-认证资格，selfPayTyp-无私奉献的投资档位展示)
	 */
	public void setType(String type) {
		this.type = type;
	}
}