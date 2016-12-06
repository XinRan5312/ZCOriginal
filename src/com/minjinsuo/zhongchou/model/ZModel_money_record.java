package com.minjinsuo.zhongchou.model;

import java.util.List;

/**
 * 资金明细
 * 
 * 存放按年月分组后的数据模型
 * 
 * @author zp
 *
 *         2015年11月5日
 */
public class ZModel_money_record {

	private String GroupTime;
	private List<MoneyRecordList> moneyRecordList;

	public String getGroupTime() {
		return GroupTime;
	}

	public void setGroupTime(String groupTime) {
		GroupTime = groupTime;
	}

	public List<MoneyRecordList> getMoneyRecordList() {
		return moneyRecordList;
	}

	public void setMoneyRecordList(List<MoneyRecordList> moneyRecordList) {
		this.moneyRecordList = moneyRecordList;
	}

	public static class MoneyRecordList {
		public String amount;
		public String createtime;
		public String recordtype;
		public String recordtypename;
		public String loantitle;
	}

}
