package com.minjinsuo.zhongchou.http;

import net.zkbc.p2p.fep.message.protocol.RequestSupport;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_Main;
import com.minjinsuo.zhongchou.model.QueryMoney;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.service.Service_Login;
import com.minjinsuo.zhongchou.service.Service_Login.UserCallBack;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 接口请求工具类
 *
 * 2016年6月24日
 */
public class NetWorkRequestManager {

	/**
	 * 普通报文请求前判断——注意防止session过期的处理方法（注：图片等请求url不一样，需要单独处理）
	 * 
	 * @param context
	 * @param isShowLoadingDialog
	 *            :是否显示加载框
	 * @param params
	 *            ：请求参数实体对象
	 * @param reslovedType
	 *            ：要解析的数据结构目标实体类
	 * @param callBack
	 *            ：回调
	 */
	public static void sendRequestPost(final Context context,
			boolean isShowLoadingDialog, RequestSupport params,
			final Class reslovedType, final MyRequestCallBack callBack) {
		// 未联网
		if (!CommonUtils.isNetLink(context)) {
			ToastUtil.showToast(context, context.getString(R.string.net_error));
			callBack.failure();
			ToastUtil.showToast(context, "网络未连接");
		} else {
			JSONObject JsonParams = null;
			try {
				JsonParams = new JSONObject(JSON.toJSONString(params));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// 超时判断，避免session过期
			if (!StringUtils.isEmpty(params.getSessionId())) {
				if ((System.currentTimeMillis() - AppContants.lastTime) > AppContants.fresh_Max * 1000) {
					LogUtil.i("为防止session过期，下面暗登录~~");
					goLogin(JsonParams, context, isShowLoadingDialog, params,
							reslovedType, callBack);
				} else {
					AppContants.lastTime = System.currentTimeMillis();
					goRequestRealy(JsonParams, context, isShowLoadingDialog,
							params, reslovedType, callBack);
				}
			} else {
				goRequestRealy(JsonParams, context, isShowLoadingDialog,
						params, reslovedType, callBack);
			}

		}
	}

	/**
	 * 实际请求处理类
	 * 
	 * @param JsonParams
	 * @param context
	 * @param isShowLoadingDialog
	 * @param params
	 * @param reslovedType
	 * @param callBack
	 */
	public static void goRequestRealy(final JSONObject JsonParams,
			final Context context, final boolean isShowLoadingDialog,
			final RequestSupport params, final Class reslovedType,
			final MyRequestCallBack callBack) {
		LogUtil.i("请求接口：" + CommonUtils.URL + params.getMessageId()
				+ "\n 上传参数: " + JsonParams.toString());
		// 请求之前，先取消之前的请求（取消还没有进行完的请求）
		ZCApplication.getQueue().cancelAll(context);
		if (isShowLoadingDialog) {
			DialogUtils.showLoading(context);
		}
		// 普通报文发起请求(请求图片时url传递不一样，单独处理)
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
				CommonUtils.URL + params.getMessageId(), JsonParams,
				new Listener<JSONObject>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onResponse(JSONObject response) {
						DialogUtils.dismisLoading();
						LogUtil.i("返回结果 : ==\n" + response.toString());
						ResponseSupport model = JSON.parseObject(
								String.valueOf(response), reslovedType);
						if (model.getStatusCode() == 0) {
							// 有的后期添加的接口需要通过result进行二次判断是否成功
							if (model.getResult() != null) {
								if (model.getResult().equals("success")) {
									callBack.onSuccess(model);
								} else {
									if (model.getMessage() != null) {
										if (!StringUtils.isEmpty(model
												.getMessage())) {
											ToastUtil.showToast(context,
													model.getMessage());
										}
										callBack.onFailure(model);
									} else {
										callBack.failure();
										ToastUtil.showToast(context, "操作失败");
									}
								}
							} else {
								callBack.onSuccess(model);
							}
						} else if (model.getStatusCode() == -100) {
							callBack.failure();
							LogUtil.i("statusCode==-100,登录过期");
							goLogin(JsonParams, context, isShowLoadingDialog,
									params, reslovedType, callBack);
						} else if (model.getStatusCode() == -1
								&& null != model.getStatusMessage()
								&& model.getStatusMessage().equals(
										"您已在别处登录，此处已下线.")) {
							callBack.failure();
							loginOutSel(context);
						} else {
							callBack.onFailure(model);// UI层可以拿到错误码进行其他操作
							if (!StringUtils.isEmpty(model.getStatusMessage())) {
								if (!model.getStatusMessage().contains("请求异常")) {
									ToastUtil.showToast(context,
											model.getStatusMessage());
								}
							}
						}
					}

				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError response) {
						DialogUtils.dismisLoading();
						callBack.failure();
						ToastUtil.showToast(context, "服务请求异常");
					}
				});
		// 请求加上Tag,用于取消请求
		request.setTag(context);
		ZCApplication.getQueue().add(request);
		ZCApplication.getQueue().start();
	}

	/**
	 * 登录。重新登录获取新的sessionId
	 * 
	 */
	public static void goLogin(final JSONObject JsonParams,
			final Context context, final boolean isShowLoadingDialog,
			final RequestSupport params, final Class reslovedType,
			final MyRequestCallBack callBack) {
		String loginPwd = LockPatternService.getUserPassword(context);// 存储的时候就已经是加密后的密码
		Service_Login.goLogin(context, LockPatternService.getUserName(context),
				loginPwd, false, false, new UserCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						LogUtil.i("重新登录成功，获取新的sessionId=="
								+ response.getSessionId());

						ZCApplication.getInstance().userInfo
								.setSessionId(response.getSessionId());

						params.setSessionId(response.getSessionId());
						JSONObject NewJsonParams = null;
						try {
							NewJsonParams = new JSONObject(JSON
									.toJSONString(params));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						goRequestRealy(NewJsonParams, context,
								isShowLoadingDialog, params, reslovedType,
								callBack);
					}

					@Override
					public void onFailure(ResponseSupport response) {
					}

					@Override
					public void failure() {

					}
				});
	}

	/**
	 * 退出。被踢下线后需重新登录
	 * 
	 * @param context
	 */
	public static void loginOutSel(final Context context) {
		new AlertDialog(context).builder().setMsg("您已在别处登录")
				.setCancelable(false)
				.setPositiveButton("重新登录", new OnClickListener() {

					@Override
					public void onClick(View v) {
						LockPatternService.LoginOut(context);
						ZCApplication.getInstance().TAG = Activity_Main.TAB_Account;
						context.startActivity(new Intent(context,
								Activity_Main.class));
						ZCApplication.getInstance().exit();
					}
				}).show();
	}
}
