package com.minjinsuo.zhongchou.model;

import java.io.Serializable;

public class Model_inviteFriend implements Serializable {
	private String name;
	// private int rewardCount;
	private String time;
	private String rewardCount;
	private String way;

	/**
	 * @return 红包的类型 1代表加息券，需要添加%
	 */
	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public String getRewardCount() {
		return rewardCount;
	}

	public void setRewardCount(String rewardCount) {
		this.rewardCount = rewardCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public int getRewardCount() {
	// return rewardCount;
	// }
	// public void setRewardCount(int rewardCount) {
	// this.rewardCount = rewardCount;
	// }
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Model_inviteFriend() {
		super();
	}

	public Model_inviteFriend(String name, String rewardCount, String time, String way) {
		super();
		this.name = name;
		this.rewardCount = rewardCount;
		this.time = time;
		this.way = way;
	}

}
