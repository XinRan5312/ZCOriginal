package com.minjinsuo.zhongchou.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Test;

import org.xutils.common.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;

/**
 * 通用工具类,如检查网络，toast弹框等 如果需要增加新的工具类，请在最后面添加
 */
public class CommonUtils {

	// 公用接口请求服务地址
	public static final String URL = getValue("postUrl");

	/**
	 * 判断手机是否联网
	 */
	public static boolean isNetLink(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager != null) {
				NetworkInfo info = connectivityManager.getActiveNetworkInfo();
				if (info != null && info.isConnected()
						&& info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static Toast toast;

	/**
	 * Toast提示
	 */
	public static void showToast(Context context, String string) {
		if (toast == null) {
			toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
		} else {
			toast.setText(string);
		}
		toast.show();
	}

	/**
	 * 读写assets目录下configuration.properties中的特定字符串
	 */
	public static String getValue(String key) {
		String value = "";
		AssetManager am = ZCApplication.getInstance().getAssets();
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = am.open("configuration.properties");
			props.load(in);
			// 输出属性文件中的信息
			value = props.getProperty(key);
			LogUtil.d("获取的值" + value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					Logger.getLogger(Test.class.getName()).log(Level.SEVERE,
							null, ex);
				}
			}
		}

		return value;
	}

	/**
	 * 读写assets目录下文件内容
	 * 
	 * @param context
	 * @param fileUrl
	 *            ：文件名
	 * @return byte字节
	 */
	public static byte[] getData(Context context, String fileUrl) {
		byte[] data = null;
		AssetManager am = context.getAssets();

		try {
			InputStream is = am.open(fileUrl);
			byte[] buffer = new byte[256];
			int length = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((length = is.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			data = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 读取assets里的文件
	 * 
	 * @param context
	 * @param fileName
	 *            ：文件名
	 * @return String类型的文件内容
	 */
	public static String getAssetsData(Context context, String fileName) {
		byte[] data = null;
		AssetManager am = context.getAssets();

		try {
			InputStream is = am.open(fileName);
			byte[] buffer = new byte[256];
			int length = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((length = is.read(buffer)) > 0) {
				baos.write(buffer, 0, length);
			}
			data = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String str = "";
		try {
			str = new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 散标计算器-专为散标详情接口数据设计
	 *
	 * @param amount
	 *            金额
	 * @param dealine
	 *            周期
	 * @param rate
	 *            利率(9.99)
	 * @param term
	 *            利率类型（6个月）
	 * @param repaytype
	 *            贷款类型（等额本息）
	 * @return String dRe 利息
	 */

	@SuppressLint("NewApi")
	public static String countWithInvestmentDetail(String amount,
			String dealine, String rate, String term, String repaytype) {
		// 将传入的String参数转化为double类型
		double dAmount = Double.valueOf(amount);// 本金
		double dRate = Double.valueOf(rate) / 100.0f;// 利率
		double dDealine = 0;// 期限
		int iRateType = 1; // 利率类型

		/**
		 * 计算还款期限
		 */
		if (!StringUtils.isEmpty(term)) {
			if (term.contains("年")) {
				iRateType = 1;
				term = term.substring(0, term.indexOf("年"));
			} else if (term.contains("月")) {
				iRateType = 2;
				term = term.substring(0, term.indexOf("个月"));
			} else if (term.contains("日")) {
				iRateType = 3;
				term = term.substring(0, term.indexOf("日"));
			} else if (term.contains("天")) {
				iRateType = 3;
				term = term.substring(0, term.indexOf("天"));
			}

			if (dealine == null || dealine.length() == 0) {
				dDealine = Double.valueOf(term);
			} else {
				dDealine = Double.valueOf(dealine);// 期限
			}
		}

		// 判断还款类型
		int iRepaymentType = 0;
		if (repaytype.equals("等额本息")) {
			iRepaymentType = 1;
		} else if (repaytype.equals("按月付息，到期还本")
				|| repaytype.equals("按期付息，到期还本")) {
			iRepaymentType = 2;
		} else if (repaytype.equals("一次性还本付息")) {
			iRepaymentType = 3;
		} else {
			String strMsg = "没有该类型：" + repaytype;
			LogUtil.d(strMsg);
		}

		// 计算收益
		double dRe = countInvestmentWithAmount(dAmount, dRate, iRateType,
				dDealine, iRepaymentType);
		LogUtil.d("计算的收益double类型=" + dRe);
		DecimalFormat df = new DecimalFormat("############0.00");
		return df.format(dRe);
	}

	/**
	 * 散标计算器
	 *
	 * @param amount
	 *            金额
	 * @param rate
	 *            利率
	 * @param rateType
	 *            利率类别:"1-年"，"2-月"，"3-日"
	 * @param repaymentType
	 *            还款方式:"1-等额本息"，"2-按月付息,到期还本"，"3-一次性还本付息"
	 * @return 利息(收益)
	 */
	public static double countInvestmentWithAmount(double amount, double rate,
			int rateType, double deadline, int repaymentType) {
		if (deadline == 0) {
			return 0.0f;
		}
		switch (rateType) {
		case 1:

			break;
		case 2:
			rate = rate / 12;
			break;
		case 3:
			rate = rate / 360;
			break;
		default:
			break;
		}

		double dResult = 0.00f;
		if (repaymentType == 1) {
			// 等额本息
			dResult = (amount * deadline * rate * Math.pow(1 + rate, deadline))
					/ (Math.pow(1 + rate, deadline) - 1) - amount;
		} else if (repaymentType == 2) {
			// 按月付息,到期还本
			dResult = amount * rate * deadline;
		} else if (repaymentType == 3) {
			// 一次性还本付息
			dResult = amount * rate * deadline;
		}
		// dResult = Math.round(dResult * 100) / 100;
		return dResult;
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变
	 * 
	 * @param pxValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param dipValue
	 * @param scale
	 *            （DisplayMetrics类中属性density）
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 
	 * 2016年7月4日
	 * 
	 * @param values
	 *            key value 键值对 传入
	 * @return 服务器请求的参数
	 */
	public static HashMap<String, Object> getParams(String... values) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < values.length; i += 2) {
			params.put(values[i], values[i + 1]);
			LogUtil.d("parmams:" + values[i] + "_" + values[i + 1]);
		}
		return params;
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static class CODE {
		/** 注册 **/
		public static final int GOREGISTER = 100;
	}

	/**
	 * 给textView动态添加图片到左边
	 * 
	 * @param context
	 * @param tv
	 */
	public static void setTextViewDrawableLeft(Activity context, TextView tv,
			int pic) {
		if (pic == 0) {
			tv.setCompoundDrawables(null, null, null, null);
			return;
		}
		Drawable drawable = context.getResources().getDrawable(pic);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		tv.setCompoundDrawables(drawable, null, null, null);// 画在顶部
	}

	/**
	 * 给textView动态添加图片到顶部
	 * 
	 * @param context
	 * @param tv
	 */
	public static void setTextViewDrawableTop(Activity context, TextView tv,
			int pic) {
		if (pic == 0) {
			tv.setCompoundDrawables(null, null, null, null);
			return;
		}
		Drawable drawable = context.getResources().getDrawable(pic);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		tv.setCompoundDrawables(null, drawable, null, null);// 画在顶部
	}

	/**
	 * 给textView动态添加图片到顶部
	 * 
	 * @param context
	 * @param tv
	 */
	public static void setTextViewDrawableRight(Activity context, TextView tv,
			int pic) {
		if (pic == 0) {
			tv.setCompoundDrawables(null, null, null, null);
			return;
		}
		Drawable drawable = context.getResources().getDrawable(pic);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		tv.setCompoundDrawables(null, null, drawable, null);// 画在顶部
	}

	/**
	 * 给textView动态添加图片到文字下边
	 * 
	 * @param context
	 * @param tv
	 */
	public static void setTextViewDrawableBottom(Activity context, TextView tv,
			int pic) {
		if (pic == 0) {
			tv.setCompoundDrawables(null, null, null, null);
			return;
		}
		Drawable drawable = context.getResources().getDrawable(pic);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		tv.setCompoundDrawables(null, null, null, drawable);// 画在顶部
	}

	/**
	 * 拨打电话：调用拨号界面
	 * 
	 * @param phone
	 *            电话号码
	 */
	public static void callPhone(Context context, String phone) {
		Intent intent = new Intent(Intent.ACTION_DIAL,
				Uri.parse("tel:" + phone));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		AppContants.isNotShowGesPwd = true;
		context.startActivity(intent);
	}

	/**
	 * 获取屏幕宽度或高度
	 * 
	 * @param flag
	 *            :flag=0 获取屏幕宽度；否则获取高度
	 * @return int类型结果
	 */
	public static int getWindowSize(int flag) {
		WindowManager wm = (WindowManager) ZCApplication.getInstance()
				.getSystemService(Context.WINDOW_SERVICE);

		int rusult = 0;
		if (flag == 0) {
			rusult = wm.getDefaultDisplay().getWidth();
		} else {
			rusult = wm.getDefaultDisplay().getHeight();
		}
		return rusult;
	}

	private static String mImei;

	public static String getImei(Context context) {
		if (!StringUtils.isEmpty(mImei)) {
			return mImei;
		}
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		mImei = tm.getDeviceId();

		// 濡傛灉涓嶆槸imei鍙凤紝鍒欒繑鍥炵┖
		if (!isImei(mImei)) {
			mImei = null;
			return null;
		}
		LogUtil.d("my mImei is ==" + mImei);
		return mImei;
	}

	private static boolean isImei(String imei) {
		if (TextUtils.isEmpty(imei)) {
			return false;
		}

		// 妫�鏌ユ槸鍚﹂兘鏄暟瀛椾笖鍦�15涓強浠ヤ笂
		// 浣跨敤姝ｅ垯琛ㄨ揪寮�, 鏇寸洿瑙�
		Pattern pattern = Pattern.compile("[\\d]{15,}");
		Matcher matcher = pattern.matcher(imei);
		if (matcher.matches()) {
			return true;
		}

		return false;
	}

	/**
	 * 动态获取状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * 5.0+系统（API21+）设置状态栏透明
	 * 
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void setStatusBarTransparent(Context context) {
		((Activity) context).getWindow()
				.requestFeature(Window.FEATURE_NO_TITLE);
		if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {// 5.0+
			Window window = ((Activity) context).getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		} else
			if (VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4+
			((Activity) context).getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}

	}

	/**
	 * 获取目标时间距今几天前
	 * 
	 * @param early
	 *            :目标时间 如：2016-4-07 23:59:59
	 * @param late
	 *            :最后时间，一般传当前时间
	 * @return 今天；几天前（小于10天）；月/日（大于10天）
	 */
	public static final String daysBetween(String early, String late) {
		Date earlydate = new Date();
		Date latedate = new Date();
		DateFormat df = DateFormat.getDateInstance();
		// try {
		// earlydate = df.parse(early);
		// latedate = df.parse(late);
		earlydate = DateUtils.parseDate(early,
				DateUtils.FULL_DATE_TIME_FORMAT_1);
		latedate = DateUtils.parseDate(DateUtils.getCurrentTime(),
				DateUtils.FULL_DATE_TIME_FORMAT_1);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }

		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(earlydate);
		caled.setTime(latedate);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;

		String result;
		if (days == 0) {
			result = "今天";
		} else if (days < 10) {
			result = days + "天前";
		} else {
			result = calst.get(Calendar.MONTH) + 1 + "/"
					+ calst.get(Calendar.DAY_OF_MONTH);
		}
		return result;
	}

}
