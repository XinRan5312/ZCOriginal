package net.zkbc.p2p.fep.message.protocol;

/**
 * 检测更新.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckUpdateRequest extends RequestSupport {

	private String deviceheight;
	private String devicetype;
	private String devicewidth;
	private String version;

	public CheckUpdateRequest() {
		super();
		setMessageId("checkUpdate");
	}	

	/**
	 * @return 设备高度
	 */
	public String getDeviceheight() {
		return deviceheight;
	}

	public void setDeviceheight(String deviceheight) {
		this.deviceheight = deviceheight;
	}

	/**
	 * @return 设备类型：iphone,ipad,aphone,apad
	 */
	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	/**
	 * @return 设备宽度
	 */
	public String getDevicewidth() {
		return devicewidth;
	}

	public void setDevicewidth(String devicewidth) {
		this.devicewidth = devicewidth;
	}

	/**
	 * @return 当前版本
	 */
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}