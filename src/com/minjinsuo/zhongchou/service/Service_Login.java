package com.minjinsuo.zhongchou.service;

import net.zkbc.p2p.fep.message.protocol.GetAgreementContentRequest;
import net.zkbc.p2p.fep.message.protocol.GetAgreementContentResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyAccountRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyAccountResponse;
import net.zkbc.p2p.fep.message.protocol.LoginRequest;
import net.zkbc.p2p.fep.message.protocol.LoginResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;

public class Service_Login {

	public interface UserCallBack {
		public void onSuccess(ResponseSupport sucResponse);

		public void onFailure(ResponseSupport failResponse);

		public void failure();
	}

	/**
	 * flag == true时显示加载框 登录
	 */
	public static void goLogin(final Context context, final String loginName,
			final String password, final boolean isShowLoading,
			final boolean isGetMyAccount, final UserCallBack callBack) {
		LoginRequest request = new LoginRequest();
		request.setLoginname(loginName);
		request.setPassword(password);

		request.setImei(CommonUtils.getImei(context));
		request.setOstype("2");
		request.setDeviceid(CommonUtils.getImei(context));
		request.setCid(ZCApplication.getInstance().getClientId());

		NetWorkRequestManager.sendRequestPost(context, isShowLoading, request,
				LoginResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						AppContants.lastTime = System.currentTimeMillis();
						// 将用户名和密码暂存于手势管理类中，方便显示手势验证时显示用户，也方便登录超时时用于重登陆
						LockPatternService.saveUserName(context, loginName);
						LockPatternService.saveUserPassword(context, password);

						LoginResponse model = (LoginResponse) response;
						if (isGetMyAccount) {
							if (model.getStatusCode() == 0) {
								getAccountMessage(context,
										model.getSessionId(), false, callBack);
							}
						} else {// 如果不需要获取用户信息
							callBack.onSuccess(response);// 返回登录成功的报文实体
						}
					}

					@Override
					public void onFailure(ResponseSupport response) {
						// DialogUtils.dismisLoading();
						callBack.onFailure(response);
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
						callBack.failure();
					}
				});
	}

	/**
	 * 获取账户信息
	 */
	public static void getAccountMessage(final Context context,
			String sessionId, boolean flag, final UserCallBack callBack) {

		GetMyAccountRequest request = new GetMyAccountRequest();
		request.setSessionId(sessionId);
		NetWorkRequestManager.sendRequestPost(context, flag, request,
				GetMyAccountResponse.class, new MyRequestCallBack() {
					@Override
					public void onSuccess(ResponseSupport response) {
						GetMyAccountResponse user = (GetMyAccountResponse) response;
						ZCApplication.getInstance().setUserInfo(user);
						// // 企业用户不能登陆
						// if (null != user.getRoles() && user.getRoles() == 3)
						// {
						// DialogUtils.dismisLoading();
						// ToastUtil.showToast(context, "暂不支持企业用户登录");
						// return;
						// }

						// 保存用户信息
						SharedPreferUtils.putValue(context,
								AppContants.USERINFO, AppContants.USERINFO,
								JSON.toJSONString(user));
						callBack.onSuccess(user);
					}

					@Override
					public void onFailure(ResponseSupport response) {
						callBack.onFailure(response);
					}

					@Override
					public void failure() {
						callBack.failure();
					}
				});
	}

	/**
	 * 获取协议
	 * 
	 * @param context
	 * @param typ
	 *            : 协议类型 1产品和公益发起众筹协议 2股权发起众筹协议 3产品订单下单协议 4股权订单下单协议 5产品订单付款协议
	 *            6股权订单付款协议 7公益下单协议 8公益付款协议 9注册协议
	 * @param isShow
	 * @param callBack
	 */
	public static void getAgreementContent(final Context context, String type,
			boolean isShow, final UserCallBack callBack) {
		GetAgreementContentRequest request = new GetAgreementContentRequest();
		request.setType(type);
		NetWorkRequestManager.sendRequestPost(context, isShow, request,
				GetAgreementContentResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						callBack.onSuccess(response);
					}

					@Override
					public void onFailure(ResponseSupport response) {
						callBack.onFailure(response);
					}

					@Override
					public void failure() {
						callBack.failure();
					}
				});
	}
}
