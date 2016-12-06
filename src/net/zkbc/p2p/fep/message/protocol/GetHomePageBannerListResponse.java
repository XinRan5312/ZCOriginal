package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取首页轮播图.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetHomePageBannerListResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetHomePageBannerListResponse#getHomePageBannerList
	 * 
	 */
	public static class ElementHomePageBannerList {

		private String clickLink;
		private String link;
		private String picture;
		private String titile;
		private String uuid;
		private Integer is_show;
		private Integer weight;
		private String prjId;
		private String prodId;

		/**
		 * @return:标唯一标示
		 */
		public String getPrjId() {
			return prjId;
		}

		public void setPrjId(String prjId) {
			this.prjId = prjId;
		}

		/**
		 * @return：标的类型（0-股权；1-产品）
		 */
		public String getProdId() {
			return prodId;
		}

		public void setProdId(String prodId) {
			this.prodId = prodId;
		}

		/**
		 * @return 超链接
		 */
		public String getClickLink() {
			return clickLink;
		}

		public void setClickLink(String clickLink) {
			this.clickLink = clickLink;
		}

		/**
		 * @return 文件夹（以前这里放的超链接）
		 */
		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		/**
		 * @return 图片
		 */
		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}

		/**
		 * @return 标题
		 */
		public String getTitile() {
			return titile;
		}

		public void setTitile(String titile) {
			this.titile = titile;
		}

		/**
		 * @return id
		 */
		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		/**
		 * @return 是否首页显示
		 */
		public Integer getIs_show() {
			return is_show;
		}

		public void setIs_show(Integer is_show) {
			this.is_show = is_show;
		}

		/**
		 * @return 权重
		 */
		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}
	}

	private List<ElementHomePageBannerList> homePageBannerList;

	public GetHomePageBannerListResponse() {
		super();
		setMessageId("getHomePageBannerList");
	}


	/**
	 * @return 首页轮播图
	 */
	public List<ElementHomePageBannerList> getHomePageBannerList() {
		return homePageBannerList;
	}

	public void setHomePageBannerList(List<ElementHomePageBannerList> homePageBannerList) {
		this.homePageBannerList = homePageBannerList;
	}
}