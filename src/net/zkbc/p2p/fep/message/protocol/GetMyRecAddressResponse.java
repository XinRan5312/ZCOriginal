package net.zkbc.p2p.fep.message.protocol;

import java.io.Serializable;
import java.util.List;

/**
 * 获取个人收货地址.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyRecAddressResponse extends ResponseSupport implements Serializable{

	/**
	 * @see net.zkbc.p2p.message.protocol.GetMyRecAddressResponse#getReceAddressList
	 * 
	 */
	public static class ElementReceAddressList implements Serializable {

		private String addr;
		private String codCity;
		private String codProv;
		private String email;
		private String inUseAdd;
		private String loginname;
		private String recvMobile;
		private String recvNam;
		private String seqNo;

		/**
		 * @return 地址
		 */
		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		/**
		 * @return 收货城市
		 */
		public String getCodCity() {
			return codCity;
		}

		public void setCodCity(String codCity) {
			this.codCity = codCity;
		}

		/**
		 * @return 收货省份
		 */
		public String getCodProv() {
			return codProv;
		}

		public void setCodProv(String codProv) {
			this.codProv = codProv;
		}

		/**
		 * @return 邮箱
		 */
		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * @return 是否默认地址 1-默认  0-不默认
		 */
		public String getInUseAdd() {
			return inUseAdd;
		}

		public void setInUseAdd(String inUseAdd) {
			this.inUseAdd = inUseAdd;
		}

		/**
		 * @return 用户登录名称
		 */
		public String getLoginname() {
			return loginname;
		}

		public void setLoginname(String loginname) {
			this.loginname = loginname;
		}

		/**
		 * @return 电话
		 */
		public String getRecvMobile() {
			return recvMobile;
		}

		public void setRecvMobile(String recvMobile) {
			this.recvMobile = recvMobile;
		}

		/**
		 * @return 收货姓名
		 */
		public String getRecvNam() {
			return recvNam;
		}

		public void setRecvNam(String recvNam) {
			this.recvNam = recvNam;
		}

		/**
		 * @return 序列号
		 */
		public String getSeqNo() {
			return seqNo;
		}

		public void setSeqNo(String seqNo) {
			this.seqNo = seqNo;
		}
	}

	private List<ElementReceAddressList> receAddressList;

	public GetMyRecAddressResponse() {
		super();
		setMessageId("getMyRecAddress");
	}


	/**
	 * @return 收货地址列表
	 */
	public List<ElementReceAddressList> getReceAddressList() {
		return receAddressList;
	}

	public void setReceAddressList(List<ElementReceAddressList> receAddressList) {
		this.receAddressList = receAddressList;
	}
}