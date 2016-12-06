package net.zkbc.p2p.fep.message.protocol;

import java.util.List;

/**
 * 获取团队信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetPrjTeamByPrjIdResponse extends ResponseSupport {

	/**
	 * @see net.zkbc.p2p.message.protocol.GetPrjTeamByPrjIdResponse#getPrjTeamList
	 * 
	 */
	public static class ElementPrjTeamList {

		private String capitalContr;
		private String createTime;
		private String dispOrder;
		private String duty;
		private String fileIdPhoto;
		private String id;
		private String isShareHolder;
		private String nam;
		private String photoImgAddresss;
		private String prjId;
		private String selfIntro;
		private String seqNo;
		private String stockPct;

		/**
		 * @return 
		 */
		public String getCapitalContr() {
			return capitalContr;
		}

		public void setCapitalContr(String capitalContr) {
			this.capitalContr = capitalContr;
		}

		/**
		 * @return 
		 */
		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		/**
		 * @return 
		 */
		public String getDispOrder() {
			return dispOrder;
		}

		public void setDispOrder(String dispOrder) {
			this.dispOrder = dispOrder;
		}

		/**
		 * @return 职务
		 */
		public String getDuty() {
			return duty;
		}

		public void setDuty(String duty) {
			this.duty = duty;
		}

		/**
		 * @return 头文件id
		 */
		public String getFileIdPhoto() {
			return fileIdPhoto;
		}

		public void setFileIdPhoto(String fileIdPhoto) {
			this.fileIdPhoto = fileIdPhoto;
		}

		/**
		 * @return 主键id
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 是否股东
		 */
		public String getIsShareHolder() {
			return isShareHolder;
		}

		public void setIsShareHolder(String isShareHolder) {
			this.isShareHolder = isShareHolder;
		}

		/**
		 * @return 姓名
		 */
		public String getNam() {
			return nam;
		}

		public void setNam(String nam) {
			this.nam = nam;
		}

		/**
		 * @return 头像地址
		 */
		public String getPhotoImgAddresss() {
			return photoImgAddresss;
		}

		public void setPhotoImgAddresss(String photoImgAddresss) {
			this.photoImgAddresss = photoImgAddresss;
		}

		/**
		 * @return 项目id
		 */
		public String getPrjId() {
			return prjId;
		}

		public void setPrjId(String prjId) {
			this.prjId = prjId;
		}

		/**
		 * @return 自我介绍
		 */
		public String getSelfIntro() {
			return selfIntro;
		}

		public void setSelfIntro(String selfIntro) {
			this.selfIntro = selfIntro;
		}

		/**
		 * @return 
		 */
		public String getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(String seqNo) {
			this.seqNo = seqNo;
		}

		/**
		 * @return 股份比例
		 */
		public String getStockPct() {
			return stockPct;
		}

		public void setStockPct(String stockPct) {
			this.stockPct = stockPct;
		}
	}

	private List<ElementPrjTeamList> prjTeamList;

	public GetPrjTeamByPrjIdResponse() {
		super();
		setMessageId("getPrjTeamByPrjId");
	}


	/**
	 * @return 团队列表
	 */
	public List<ElementPrjTeamList> getPrjTeamList() {
		return prjTeamList;
	}

	public void setPrjTeamList(List<ElementPrjTeamList> prjTeamList) {
		this.prjTeamList = prjTeamList;
	}
}