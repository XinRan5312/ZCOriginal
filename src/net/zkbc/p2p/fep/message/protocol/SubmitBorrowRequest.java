package net.zkbc.p2p.fep.message.protocol;

/**
 * 提交借款信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SubmitBorrowRequest extends RequestSupport {

	private String borrowtype;
	private String description;
	private String producttype;
	private String termcount;
	private String termunit;
	private String title;
	private Double amount;
	private Double annualinterestrate;

	public SubmitBorrowRequest() {
		super();
		setMessageId("submitBorrow");
	}	

	/**
	 * @return 用途
	 */
	public String getBorrowtype() {
		return borrowtype;
	}

	public void setBorrowtype(String borrowtype) {
		this.borrowtype = borrowtype;
	}

	/**
	 * @return 借款描述
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return 产品类型
	 */
	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	/**
	 * @return 期限
	 */
	public String getTermcount() {
		return termcount;
	}

	public void setTermcount(String termcount) {
		this.termcount = termcount;
	}

	/**
	 * @return 期限：1-天3-月
	 */
	public String getTermunit() {
		return termunit;
	}

	public void setTermunit(String termunit) {
		this.termunit = termunit;
	}

	/**
	 * @return 借款标题
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return 借款金额
	 */
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return 借款利率
	 */
	public Double getAnnualinterestrate() {
		return annualinterestrate;
	}

	public void setAnnualinterestrate(Double annualinterestrate) {
		this.annualinterestrate = annualinterestrate;
	}
}