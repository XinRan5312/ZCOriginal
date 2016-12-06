package net.zkbc.p2p.fep.message.protocol;

/**
 * 查询资金流水.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SearchMoneyRecordListRequest extends RequestSupport {

	private String timetype;
	private String tradeType;
	private Integer pageno;
	private Integer pagesize;

	public SearchMoneyRecordListRequest() {
		super();
		setMessageId("searchMoneyRecordList");
	}	

	/**
	 * @return 时间类型：0-全部,1-1个月,2-2个月,3-3个月,4-3个月以上
	 */
	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	/**
	 * @return 流水类型
	 */
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
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
	 * @return 每页记录数
	 */
	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
}