package com.minjinsuo.zhongchou.utils;

import java.util.HashMap;

public class StatusUtils {
	public static HashMap<String, String> map = new HashMap<String, String>();

	/**
	 * 1、标的——通过状态码获取对应的状态
	 * 
	 * @param code
	 *            :对应的状态码
	 * @return：状态文字
	 */
	public static String getStatusByCode(String code) {
		String status = "未知";
		if (!StringUtils.isEmpty(code)) {
			setDataLoan();
			status = map.get(code);
		}
		return status;
	}

	/**
	 * 2、订单——通过状态码获取对应的状态
	 * 
	 * @param code
	 *            :对应的状态码
	 * @return：状态文字
	 */
	public static String getOrderStatusByCode(String code) {
		String status = "未知";
		if (!StringUtils.isEmpty(code)) {
			setDataOrder();
			status = map.get(code);
		}
		return status;
	}

	/**
	 * 3、预约——通过状态码获取对应的状态
	 * 
	 * @param code
	 *            :对应的状态码
	 * @return：状态文字
	 */
	public static String getYuyueStatusByCode(String code) {
		String status = "处理中";
		if (!StringUtils.isEmpty(code)) {
			setDataYuyue();
			status = map.get(code);
		}
		return status;
	}

	/**
	 * 4、获取用户身份（是否是领投人等）
	 * 
	 * @param code
	 *            :对应的状态码
	 * @return：状态文字
	 */
	public static String getIdentityByCode(String code) {
		String status = "未知";
		if (!StringUtils.isEmpty(code)) {
			setDataIdentity();
			status = map.get(code);
		}
		return status;
	}

	/**
	 * 标的状态对应表
	 */
	private static void setDataLoan() {
		map.put("10", "待预热");
		map.put("20", "预热中");
		map.put("30", "待筹款");
		map.put("35", "预热流标");
		map.put("40", "众筹中");
		map.put("45", "筹款结束");
		map.put("50", "众筹成功");// 筹款满标
		map.put("55", "筹款流标");
		map.put("60", "项目成功");
		map.put("70", "成功结项");
		map.put("80", "失败结项");
	}

	/**
	 * 原始（已弃用20160927）——订单的状态对应表订单的状态及代码 "未支付" "10" "已取消" "20" "定金已付" "30"
	 * "预约作废" "35" "满标作废" "36" "已支付" "40" "支付异常" "41" "已删除" "45" "已成功" "50"
	 * "已流标" "60" "超额退款中" "65" "已退款" "70" "已剔除" "75" "发货中" "80" "未中奖" "85"
	 * "已确认收货" "90" "申请退款中" "100" "已失效" "110" "退款处理中" "120" "定金已退" "130" "付款处理中"
	 * "140" "中奖" "150"
	 */
	private static void setDataOrder() {
		map.put("10", "未支付");
		map.put("20", "订单取消");
		map.put("40", "支付成功");
		map.put("41", "支付失败");
		map.put("50", "未发货");
		map.put("70", "退款成功");
		map.put("80", "已发货");
		map.put("90", "已收货");
		map.put("110", "订单超时");
		map.put("120", "退款中");
		map.put("140", "付款中");
	}

	/**
	 * 预约状态对应表
	 */
	private static void setDataYuyue() {
		map.put("10", "预约成功");
		map.put("20", "预约失败");
		map.put("30", "预约取消");
		map.put("40", "优先购买中");
		map.put("50", "投资成功");
	}

	/**
	 * 身份标示 10-项目发起人，20-项目领投人，30-跟投人，40-领投人
	 */
	private static void setDataIdentity() {
		map.put("10", "项目发起人");
		map.put("20", "项目领投人");
		map.put("30", "跟投人");
		map.put("40", "领投人");
	}
}
