package com.minjinsuo.zhongchou.http;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.common.util.LogUtil;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.minjinsuo.zhongchou.model.QueryMoney;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.service.Service_Login;
import com.minjinsuo.zhongchou.service.Service_Login.UserCallBack;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 从第三方获取时时余额，添加session过期判断
 * 
 * @author zp
 *
 *         2016年11月3日
 */
public class GetThirdAmountManager {

	public interface GetThirdAmountManagerCallBack {
		public void onSuccess(QueryMoney response);

		public void onFailure(String errorMsg);

		public void onFailure();
	}

	/**
	 * 获取易宝时时余额（get请求）
	 *
	 * @param context
	 */

	public static void getThirdAmtRequest(Context context,
			boolean isShowLoading, final GetThirdAmountManagerCallBack callBack) {
		if (isShowLoading) {
			DialogUtils.showLoading(context);
		}
		String URL_HOSTAMOUNT = CommonUtils.getValue("postUrlAmount");

		// 为防session过期，限时重新暗登录获取新的session
		if ((System.currentTimeMillis() - AppContants.lastTime) > AppContants.fresh_Max * 1000) {
			loginBackground(context, URL_HOSTAMOUNT, callBack);
		} else {
			AppContants.lastTime = System.currentTimeMillis();
			sendRealGetRequest(context, URL_HOSTAMOUNT, callBack);
		}
	}

	/**
	 * 实质发送请求
	 * 
	 * @param context
	 * @param url
	 * @param callBack
	 */
	private static void sendRealGetRequest(Context context,
			String URL_HOSTAMOUNT, final GetThirdAmountManagerCallBack callBack) {
		String mSessionId = ZCApplication.getInstance().userInfo.getSessionId();
		String url = URL_HOSTAMOUNT + "?sessionId=" + mSessionId;
		LogUtil.d("时时余额请求参数==" + url);

		ZCApplication.getQueue().cancelAll(context);
		StringRequest request = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						String result = response.toString().trim();
						LogUtil.i("volley获取三方余额==" + result);
						QueryMoney qm = null;
						if (!StringUtils.isEmpty(result)) {
							qm = new QueryMoney();
							qm = JSON.parseObject(result, QueryMoney.class);
							if (qm != null) {
								LogUtil.i(qm.getDescription());
								if (qm.getDescription().equals("操作成功")) {
									callBack.onSuccess(qm);
								} else if (!StringUtils.isEmpty(qm
										.getErrorMsg())) {
									callBack.onFailure(qm.getErrorMsg());
								} else {
									callBack.onFailure();
								}
							}
						} else {
							callBack.onFailure();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						callBack.onFailure();
					}
				});

		request.setTag(context);
		ZCApplication.getQueue().add(request);
		ZCApplication.getQueue().start();
	}

	/**
	 * 暗登录
	 */
	public static void loginBackground(final Context context, final String url,
			final GetThirdAmountManagerCallBack callBack) {
		String loginPwd = LockPatternService.getUserPassword(context);// 存储的时候就已经是加密后的密码
		Service_Login.goLogin(context, LockPatternService.getUserName(context),
				loginPwd, false, false, new UserCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						LogUtil.i("时时余额——重新登录成功，获取新的sessionId=="
								+ response.getSessionId());

						ZCApplication.getInstance().userInfo
								.setSessionId(response.getSessionId());
						sendRealGetRequest(context, url, callBack);
					}

					@Override
					public void onFailure(ResponseSupport response) {
					}

					@Override
					public void failure() {
					}
				});
	}
}
