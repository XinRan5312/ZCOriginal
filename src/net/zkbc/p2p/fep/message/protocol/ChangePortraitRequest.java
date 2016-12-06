package net.zkbc.p2p.fep.message.protocol;

/**
 * 更换头像.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ChangePortraitRequest extends RequestSupport {

	private String headPic;
	private String name;

	public ChangePortraitRequest() {
		super();
		setMessageId("changePortrait");
	}	

	/**
	 * @return 头像 base64转化的图片文件
	 */
	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	/**
	 * @return 名称  包含文件后缀
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}