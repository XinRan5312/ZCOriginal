package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取用户订单.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMyOrderPageListRequest extends RequestSupport {

	private String pageNo;
	private String pageSize;
	private String prodId;
	private String status;

	public GetMyOrderPageListRequest() {
		super();
		setMessageId("getMyOrderPageList");
	}	

	/**
	 * @return 页数
	 */
	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return 每页条数
	 */
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 产品表示     0 股权众筹
            1 产品众筹
	 */
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	/**
	 * @return 订单的状态：10  未支付 20 已撤销 30  定金已付 35  预约作废（已完成）40 已支付  41 支付异常  45 已认筹  50 已成功（众筹融满 60 已流标（退款申请中）
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}