package net.zkbc.p2p.fep.message.protocol;

/**
 * 更新用户地址.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class UpdateUserAddressRequest extends RequestSupport {

	private String addr;
	private String codCity;
	private String codProv;
	private String email;
	private String inUseAdd;
	private String recvMobile;
	private String recvNam;
	private String seqNo;
	private String subType;

	public UpdateUserAddressRequest() {
		super();
		setMessageId("updateUserAddress");
	}	

	/**
	 * @return 详细地址
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
	 * @return 电子邮件
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return 是否默认地址（是-1 否-0）
	 */
	public String getInUseAdd() {
		return inUseAdd;
	}

	public void setInUseAdd(String inUseAdd) {
		this.inUseAdd = inUseAdd;
	}

	/**
	 * @return 收货人手机号
	 */
	public String getRecvMobile() {
		return recvMobile;
	}

	public void setRecvMobile(String recvMobile) {
		this.recvMobile = recvMobile;
	}

	/**
	 * @return 收货人姓名
	 */
	public String getRecvNam() {
		return recvNam;
	}

	public void setRecvNam(String recvNam) {
		this.recvNam = recvNam;
	}

	/**
	 * @return subType为修改时必填请求参数
	 */
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * @return 提交类型  新增add    更新 update
	 */
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
}