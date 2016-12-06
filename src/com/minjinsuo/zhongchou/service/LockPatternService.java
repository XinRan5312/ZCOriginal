package com.minjinsuo.zhongchou.service;

import net.zkbc.p2p.fep.message.protocol.GetMyAccountResponse;

import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 手势密码设置
 * 
 * @author Tracy
 * 
 */
public class LockPatternService {
	// 手势密码配置文件
	private static final String CONFIG_FILE_NAME = "lockinfo";
	public static String GESTURE_KEY = "gesture_key";
	// 手势密码还能输入的次数
	private static final String PASSWORDNUM = "passwordNum";
	// 用户名
	private static final String USER_NAME = "username";
	// 用户名
	private static final String PHONENUMBER = "phonenumber";
	// 用户登陆密码
	private static final String USER_PASSWORD = "password";
	// 用户头像
	private static final String USER_PHOTO = "userphoto";
	// 登录状态
	private static final String LOGINSTATE = "loginstate";
	/**
	 * 是否从修改手势密码登录
	 */
	private static final String ISFROMRESETGESTURE = "isfromresetgesture";
	/**
	 * 是否是校验手势密码（修改、打开手势开关前都要校验）
	 */
	private static final String ISFROMCHECKGESTURE = "isfromcheckgesture";
	// 登陆密码输入错误次数限制
	public static final int MaxPwdInputCount = 4;
	// 界面不可见初始时间
	public static long NotSeenStartTime = 0;
	// 上次不可见界面名称
	public static String NotSeenActivivityName = null;
	// 手势密码显示的时间限制，单位秒
	public static final int MaxGesPwdTime = 300;
	// 是否第一次启动程序
	private static final String ISFIRSTLOGIN = "IsFristStart";

	// // 手势密码开关
	public static final String ISOPENGESTURE = "isopengesture";

	private static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(CONFIG_FILE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 清空SharedPreferences数据
	 * 
	 * @param context
	 */
	public static void clearGesturePassword(Context context) {
		SharedPreferences preference = getSharedPreferences(context);
		Editor editor = preference.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 保存设置的手势密码
	 * 
	 * @param locks
	 */
	public static void saveLockPattern(Context context, String locks) {
		SharedPreferences prefs = getSharedPreferences(context);
		Editor edit = prefs.edit();
		String userName = getUserName(context);
		edit.putString(userName, locks);
		edit.commit();
	}

	/**
	 * 设置当前登录用户是否首次登录该账号（用于“我的账户”判断是否显示实名认证弹框）
	 * 
	 * @param context
	 * @param flag
	 */
	public static void setIfFirstLogin(Context context, boolean flag) {
		SharedPreferences prefs = getSharedPreferences(context);
		Editor edit = prefs.edit();
		String userName = getUserName(context);
		edit.putBoolean(ISFIRSTLOGIN + userName, flag);
		edit.commit();
	}

	/**
	 * 获取是否首次登录该账号（用于“我的账户”判断是否显示实名认证弹框）
	 *
	 * @return true:是首次登陆该账户
	 */
	public static boolean getIsFirstLogin(Context context) {
		SharedPreferences prefs = getSharedPreferences(context);
		String userName = getUserName(context);
		boolean isFirst = prefs.getBoolean(ISFIRSTLOGIN + userName, true);// 默认首次
		return isFirst;
	}

	/**
	 * 清空到当前用户的收拾密码
	 * 
	 * @param context
	 */
	public static void clearLockPattern(Context context) {
		saveSturePasswordNum(context, 0);
		SharedPreferences prefs = getSharedPreferences(context);
		Editor edit = prefs.edit();
		String userName = getUserName(context);
		edit.putString(userName, null);
		edit.commit();
	}

	/**
	 * 读取手势密码
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getLockPattern(Context context) {
		SharedPreferences prefs = getSharedPreferences(context);
		String userName = getUserName(context);
		return prefs.getString(userName, null);
	}

	/**
	 * 获取手势密码错误的次数
	 * 
	 * @param context
	 * @return
	 */
	public static int getSturePasswordNum(Context context) {
		SharedPreferences preferences = getSharedPreferences(context);
		String userName = getUserName(context);
		return preferences.getInt(PASSWORDNUM, 0);
	}

	/**
	 * 设置手势密码错误的次数
	 * 
	 * @param context
	 * @param num
	 */
	public static void saveSturePasswordNum(Context context, int num) {
		SharedPreferences preferences = getSharedPreferences(context);
		Editor editor = preferences.edit();
		String userName = getUserName(context);
		editor.putInt(PASSWORDNUM, num);
		editor.commit();
	}

	/**
	 * 获取用户名
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context) {
		SharedPreferences preference = getSharedPreferences(context);
		String userName = preference.getString(USER_NAME, "");
		return userName;
	}

	/**
	 * 保存用户名
	 * 
	 * @param context
	 * @param userName
	 */
	public static void saveUserName(Context context, String userName) {
		SharedPreferences preference = getSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putString(USER_NAME, userName);
		editor.commit();
		saveSturePasswordNum(context, 0);// 登录成功后把手势密码错误的次数清0
	}

	/**
	 * 获取手机号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		SharedPreferences preference = getSharedPreferences(context);
		String userName = preference.getString(PHONENUMBER, "");
		return userName;
	}

	/**
	 * 保存手机号
	 * 
	 * @param context
	 * @param phoneNumber
	 */
	public static void savePhoneNumber(Context context, String phoneNumber) {
		SharedPreferences preference = getSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putString(PHONENUMBER, phoneNumber);
		editor.commit();
	}

	/**
	 * 获取用户登陆密码
	 * 
	 * @param context
	 * @return
	 */
	public static String getUserPassword(Context context) {
		SharedPreferences preference = getSharedPreferences(context);
		String userName = preference.getString(USER_PASSWORD, "");
		return userName;
	}

	/**
	 * 保存用户登陆密码
	 *
	 * @param context
	 * @param userName
	 */
	public static void saveUserPassword(Context context, String password) {
		SharedPreferences preference = getSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putString(USER_PASSWORD, password);
		editor.commit();
	}

	/**
	 * 保存头像
	 * 
	 * @param context
	 * @param picPath
	 */
	public static void saveUerPhoto(Context context, String picPath) {
		SharedPreferences prefs = getSharedPreferences(context);
		Editor edit = prefs.edit();
		String userName = getUserName(context);
		edit.putString(userName + USER_PHOTO, picPath);
		edit.commit();
	}

	public static String getUserPhoto(Context context) {
		SharedPreferences prefs = getSharedPreferences(context);
		String userName = getUserName(context);
		return prefs.getString(userName + USER_PHOTO, "");
	}

	/**
	 * 保存用户登陆的状态
	 * 
	 * @param context
	 * @param state
	 */
	public static void saveLoginState(Context context, boolean state) {
		SharedPreferences prefs = getSharedPreferences(context);
		Editor edit = prefs.edit();
		edit.putBoolean(LOGINSTATE, state);
		edit.commit();
	}

	/**
	 * 获取用户登陆状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getLonginState(Context context) {
		SharedPreferences prefs = getSharedPreferences(context);
		return prefs.getBoolean(LOGINSTATE, false);
	}

	/**
	 * 退出登录（用户名，密码,该用户手势，保存的账户信息制空）
	 * 
	 * @param context
	 */
	public static void LoginOut(Context context) {
		// clearLockPattern(context);
		ZCApplication.getQueue().cancelAll(context);
		saveUserName(context, "");
		saveUserPassword(context, "");
		saveLoginState(context, false);
		isOpenLockPattern(context, false);// 手势密码关闭
		SharedPreferUtils.putValue(context, AppContants.USERINFO,
				AppContants.USERINFO, "");
		ZCApplication.getInstance().userInfo = null;
	}

	/**
	 * 登陆成功后保存用户的数据，用户名，手机号(P2P6.0现用)
	 */
	public static void saveGestureData(Context context,
			GetMyAccountResponse model) {
		// 保存用户名、手机号码
		LockPatternService.saveUserName(context, model.getLoginname());
		// LockPatternService.saveUserPassword(context, model.getLoginPwd());
		LockPatternService.savePhoneNumber(context, model.getPhonenumber());
		LockPatternService.saveLoginState(context, true);
	}

	/**
	 * 获取是否从修改手势密码跳入
	 * 
	 * @return
	 */
	public static boolean isFromReSetGesture(Context context) {
		SharedPreferences preference = getSharedPreferences(context);
		boolean isFirst = preference.getBoolean(ISFROMRESETGESTURE, false);
		return isFirst;
	}

	/**
	 * 表示点击了修改手势密码
	 * 
	 */
	public static void setFromReSetGesture(Context context, boolean flag) {
		SharedPreferences preference = getSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putBoolean(ISFROMRESETGESTURE, flag);
		editor.commit();
	}

	/**
	 * 获取是否从校验原手势密码进入
	 * 
	 * @return
	 */
	public static boolean isFromCheckGesture(Context context) {
		SharedPreferences preference = getSharedPreferences(context);
		boolean isFirst = preference.getBoolean(ISFROMCHECKGESTURE, false);
		return isFirst;
	}

	/**
	 * 表示点击了校验原手势密码进入
	 * 
	 */
	public static void setFromCheckGesture(Context context, boolean flag) {
		SharedPreferences preference = getSharedPreferences(context);
		Editor editor = preference.edit();
		editor.putBoolean(ISFROMCHECKGESTURE, flag);
		editor.commit();
	}

	/**
	 * 为每个用户存储手势密码开关状态
	 * 
	 * @param locks
	 */
	public static boolean isOpenLockPattern(Context context, boolean flag) {
		SharedPreferences prefs = getSharedPreferences(context);
		Editor edit = prefs.edit();
		String userName = getUserName(context);
		edit.putBoolean(ISOPENGESTURE + userName, flag);

		edit.commit();
		return false;
	}

	/**
	 * 读取手势开关状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getIsOpenLockPatten(Context context) {
		SharedPreferences prefs = getSharedPreferences(context);
		String userName = getUserName(context);
		return prefs.getBoolean(ISOPENGESTURE + userName, false);
	}

}
