package com.minjinsuo.zhongchou.service;

import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.minjinsuo.zhongchou.model.QueryMoney;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 易宝支付（注意支付接口前有个 yeepay）
 * 
 * @author zp
 *
 *         2016年8月24日
 */
public class Service_ThirdPay {

	/**
	 * public static final String TOPUP =
	 * CommonUtils.getValue("websiteUrl")+"/chinapnr/start-recharge?"; 易宝充值
	 */
	public static final String TOPUP = CommonUtils.getValue("websiteUrl")
			+ "/yeepay/start-recharge?";

	/**
	 * 易宝提现
	 */
	public static final String TIXIAN = CommonUtils.getValue("websiteUrl")
			+ "/yeepay/start-cash?";
	/**
	 * 易宝绑定银行卡
	 */
	public static final String BINDBANKCARD = CommonUtils
			.getValue("websiteUrl") + "/yeepay/start-bindCard?";
	/**
	 * 易宝开通第三方支付账户。
	 */
	public static final String OPENCHARGE = CommonUtils.getValue("websiteUrl")
			+ "/yeepay/start-userRegister?";
	/**
	 * 易宝立即投资。
	 */
	public static final String INVESTNOW = CommonUtils.getValue("websiteUrl")
			+ "/yeepay/start-initiativeTender?";
	/**
	 * 易宝还款。 /chinapnr/start-repayment？sessionId=&loanId=
	 */
	public static final String PAYMONEY = CommonUtils.getValue("websiteUrl")
			+ "/yeepay/start-repayment?";
	/**
	 * 易宝债权转让。
	 */
	public static final String TRANFERNOW = CommonUtils.getValue("websiteUrl")
			+ "/yeepay/start-creditAssign?";
	/**
	 * 自动投标开启授权
	 */
	public static final String WARRANT_OPEN = CommonUtils
			.getValue("websiteUrl") + "/yeepay/account/start-autoInvest?";
	/**
	 * 自动投标关闭授权
	 */
	public static final String WARRANT_CLOSE = CommonUtils
			.getValue("websiteUrl") + "/yeepay/account/cancelAutoInvestAuth?";

	/**
	 * 订单支付
	 */
	public static final String PAY_ORDER = CommonUtils.getValue("websiteUrl")
			+ "/yeepay/start-orderPay?";

}
