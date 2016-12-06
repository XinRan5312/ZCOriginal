package com.minjinsuo.zhongchou.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zkbc.p2p.fep.message.protocol.GetMyAccountResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;

import android.app.Activity;
import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.minjinsuo.zhongchou.BuildConfig;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * @author zp
 *
 *         2016年11月1日
 */
public class ZCApplication extends Application {
	public static ZCApplication instance = null;
	public List<Activity> activityList = new ArrayList<Activity>();
	public GetMyAccountResponse userInfo = new GetMyAccountResponse();
	/**
	 * 网络请求队列 Volley
	 */
	private static RequestQueue requestQueue;
	/**
	 * 用于存放验证码倒计时时间
	 */
	public static Map<String, Object> timeBtn_map;
	/**
	 * 用于判断在Activity_Main中显示哪个Fragment
	 */
	public String TAG = "tab_home";
	/**
	 * 判断某个页面是否需要重新加载数据
	 */
	public boolean isNeedRefresh;
	/**
	 * 个推推送sdk获取的clientId
	 */
	private String clientId;

	public static ZCApplication getInstance() {
		return instance;
	}

	public ZCApplication() {
		super();
		instance = this;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 1、LogUtil日志打印初始化
		x.Ext.init(this);
		x.Ext.setDebug(BuildConfig.DEBUG);
		// 2、 volley网络接口请求初始化
		requestQueue = Volley.newRequestQueue(getApplicationContext());
		// 3、 启动的时候获取保存的用户账户信息
		try {
			userInfo = JSON.parseObject(SharedPreferUtils.getValue(this,
					AppContants.USERINFO, AppContants.USERINFO, ""),
					GetMyAccountResponse.class);
			if (userInfo != null) {
				LogUtil.i("用户名:" + userInfo.getLoginname());
			}
		} catch (Exception e) {
		}
	}

	public boolean isLogin() {
		return userInfo != null
				&& !StringUtils.isEmpty(userInfo.getLoginname());
	}

	/**
	 * add Activity[栈管理]
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	/**
	 * delete Activity
	 * 
	 * @param activity
	 */
	public void deleteActivity(Activity activity) {
		activityList.remove(activity);
	}

	/**
	 * 销毁activity
	 */
	public void exit() {
		for (Activity activity : activityList) {
			if (activity != null)
				activity.finish();
		}
	}

	/** 返回RequestQueue单例 **/
	public static RequestQueue getQueue() {
		return requestQueue;
	}

	public GetMyAccountResponse getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(GetMyAccountResponse userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return：个推客户端clientId
	 */
	public String getClientId() {
		if (StringUtils.isEmpty(clientId)) {
			clientId = SharedPreferUtils.getClientId(getApplicationContext());
		}
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
