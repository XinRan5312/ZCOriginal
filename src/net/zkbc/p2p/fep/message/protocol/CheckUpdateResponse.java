package net.zkbc.p2p.fep.message.protocol;

/**
 * 检测更新.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckUpdateResponse extends ResponseSupport {

	private String pkgurl;

	public CheckUpdateResponse() {
		super();
		setMessageId("checkUpdate");
	}


	/**
	 * @return 更新链接
	 */
	public String getPkgurl() {
		return pkgurl;
	}

	public void setPkgurl(String pkgurl) {
		this.pkgurl = pkgurl;
	}
}