package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取用户积分信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUserScoreResponse extends ResponseSupport {

	private String expendpoints;
	private String freezepoints;
	private String totalpoints;
	private String usedpoints;

	public GetUserScoreResponse() {
		super();
		setMessageId("getUserScore");
	}


	/**
	 * @return 已消耗积分
	 */
	public String getExpendpoints() {
		return expendpoints;
	}

	public void setExpendpoints(String expendpoints) {
		this.expendpoints = expendpoints;
	}

	/**
	 * @return 冻结积分
	 */
	public String getFreezepoints() {
		return freezepoints;
	}

	public void setFreezepoints(String freezepoints) {
		this.freezepoints = freezepoints;
	}

	/**
	 * @return 累计积分
	 */
	public String getTotalpoints() {
		return totalpoints;
	}

	public void setTotalpoints(String totalpoints) {
		this.totalpoints = totalpoints;
	}

	/**
	 * @return 可用积分
	 */
	public String getUsedpoints() {
		return usedpoints;
	}

	public void setUsedpoints(String usedpoints) {
		this.usedpoints = usedpoints;
	}
}