package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取积分列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetScoreListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetScoreListResponse#getScoreList
	 * 
	 */
	public static class ElementScoreList {

		private String points;
		private String pointsname;
		private String status;
		private String time;

		/**
		 * @return 积分额
		 */
		public String getPoints() {
			return points;
		}

		public void setPoints(String points) {
			this.points = points;
		}

		/**
		 * @return 获取积分名称
		 */
		public String getPointsname() {
			return pointsname;
		}

		public void setPointsname(String pointsname) {
			this.pointsname = pointsname;
		}

		/**
		 * @return 积分状态
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 获取积分时间
		 */
		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
	}

	private Integer totalrows;
	private List<ElementScoreList> scoreList;

	public GetScoreListResponse() {
		super();
		setMessageId("getScoreList");
	}


	/**
	 * @return 总条数
	 */
	public Integer getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(Integer totalrows) {
		this.totalrows = totalrows;
	}

	/**
	 * @return 积分列表
	 */
	public List<ElementScoreList> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<ElementScoreList> scoreList) {
		this.scoreList = scoreList;
	}
}