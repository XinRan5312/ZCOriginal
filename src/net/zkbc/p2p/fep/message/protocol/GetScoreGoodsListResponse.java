package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取积分商品列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetScoreGoodsListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetScoreGoodsListResponse#getScoreGoodsList
	 * 
	 */
	public static class ElementScoreGoodsList {

		private String goodsname;
		private String id;
		private String picpath;
		private String points;
		private String price;
		private String time;

		/**
		 * @return 商品名称
		 */
		public String getGoodsname() {
			return goodsname;
		}

		public void setGoodsname(String goodsname) {
			this.goodsname = goodsname;
		}

		/**
		 * @return 商品ID
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 图片路径
		 */
		public String getPicpath() {
			return picpath;
		}

		public void setPicpath(String picpath) {
			this.picpath = picpath;
		}

		/**
		 * @return 商品需积分额
		 */
		public String getPoints() {
			return points;
		}

		public void setPoints(String points) {
			this.points = points;
		}

		/**
		 * @return 商品价格
		 */
		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		/**
		 * @return 商品上架时间
		 */
		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
	}

	private Integer totalrows;
	private List<ElementScoreGoodsList> scoreGoodsList;

	public GetScoreGoodsListResponse() {
		super();
		setMessageId("getScoreGoodsList");
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
	 * @return 商品列表
	 */
	public List<ElementScoreGoodsList> getScoreGoodsList() {
		return scoreGoodsList;
	}

	public void setScoreGoodsList(List<ElementScoreGoodsList> scoreGoodsList) {
		this.scoreGoodsList = scoreGoodsList;
	}
}