package net.zkbc.p2p.fep.message.protocol;

/**
 * 获取积分商品列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetScoreGoodsListRequest extends RequestSupport {

	private Integer pageno;
	private Integer pagesize;

	public GetScoreGoodsListRequest() {
		super();
		setMessageId("getScoreGoodsList");
	}	

	/**
	 * @return 当前页号
	 */
	public Integer getPageno() {
		return pageno;
	}

	public void setPageno(Integer pageno) {
		this.pageno = pageno;
	}

	/**
	 * @return 每页页记录数
	 */
	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
}