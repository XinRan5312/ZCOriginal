package com.minjinsuo.zhongchou.model;

import java.io.Serializable;

/**
 * 标详情
 */
public class Model_Tender implements Serializable {
	// "increaseamount":"100","endtime":"2016-06-02
	// 18:10:26","progress":0,"status":"投标中","loanpic":"",
	// "use":"19","type":"1","subjectamount":"1000.00元","monthlyamount":"0.00元","beginamount":"100",
	// "id":"527907","amount":"1000.00元","classify":"0","title":"xiaoshudianceshi","term":"1个月",
	// "repayedamount":"0.00元","loanid":"527907","limitmoney":"1000.00","repaytype":"等额本息",
	// "interest":"12.36","safetype":"担保公司担保","nextamount":"0.00元","prepayment":"0",
	// "producttype":"等额本息","borrowid":"527101"

	private static final long serialVersionUID = 1L;
	private int progress;
	private String endtime;
	private String increaseamount;
	private String status;
	private String loanpic;
	private String use;
	private String type;
	private String subjectamount;
	private String monthlyamount;
	private String beginamount;
	private String id;
	private String amount;
	private String classify;
	private String title;
	private String term;
	private String repayedamount;
	private String loanid;
	private String limitmoney;
	private String repaytype;
	private String interest;
	private String safetype;
	private String nextamount;
	private String prepayment;
	private String producttype;
	private String borrowid;
	private String isWay;
	private String isexperience;

	// 债权
	private String annualinterestrate;
	private String lefttermcount;
	private String soldprice;
	private String tobetotalcollection;

	public String getTobetotalcollection() {
		return tobetotalcollection;
	}

	public void setTobetotalcollection(String tobetotalcollection) {
		this.tobetotalcollection = tobetotalcollection;
	}

	public String getSoldprice() {
		return soldprice;
	}

	public void setSoldprice(String soldprice) {
		this.soldprice = soldprice;
	}

	public String getLefttermcount() {
		return lefttermcount;
	}

	public void setLefttermcount(String lefttermcount) {
		this.lefttermcount = lefttermcount;
	}

	public String getAnnualinterestrate() {
		return annualinterestrate;
	}

	public void setAnnualinterestrate(String annualinterestrate) {
		this.annualinterestrate = annualinterestrate;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getIncreaseamount() {
		return increaseamount;
	}

	public void setIncreaseamount(String increaseamount) {
		this.increaseamount = increaseamount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoanpic() {
		return loanpic;
	}

	public void setLoanpic(String loanpic) {
		this.loanpic = loanpic;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubjectamount() {
		return subjectamount;
	}

	public void setSubjectamount(String subjectamount) {
		this.subjectamount = subjectamount;
	}

	public String getMonthlyamount() {
		return monthlyamount;
	}

	public void setMonthlyamount(String monthlyamount) {
		this.monthlyamount = monthlyamount;
	}

	public String getBeginamount() {
		return beginamount;
	}

	public void setBeginamount(String beginamount) {
		this.beginamount = beginamount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getRepayedamount() {
		return repayedamount;
	}

	public void setRepayedamount(String repayedamount) {
		this.repayedamount = repayedamount;
	}

	public String getLoanid() {
		return loanid;
	}

	public void setLoanid(String loanid) {
		this.loanid = loanid;
	}

	public String getLimitmoney() {
		return limitmoney;
	}

	public void setLimitmoney(String limitmoney) {
		this.limitmoney = limitmoney;
	}

	public String getRepaytype() {
		return repaytype;
	}

	public void setRepaytype(String repaytype) {
		this.repaytype = repaytype;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getSafetype() {
		return safetype;
	}

	public void setSafetype(String safetype) {
		this.safetype = safetype;
	}

	public String getNextamount() {
		return nextamount;
	}

	public void setNextamount(String nextamount) {
		this.nextamount = nextamount;
	}

	public String getPrepayment() {
		return prepayment;
	}

	public void setPrepayment(String prepayment) {
		this.prepayment = prepayment;
	}

	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}

	public String getBorrowid() {
		return borrowid;
	}

	public void setBorrowid(String borrowid) {
		this.borrowid = borrowid;
	}

	public String getIsWay() {
		return isWay;
	}

	public void setIsWay(String isWay) {
		this.isWay = isWay;
	}

	public String getIsexperience() {
		return isexperience;
	}

	public void setIsexperience(String isexperience) {
		this.isexperience = isexperience;
	}

}
