package com.minjinsuo.zhongchou.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.common.util.LogUtil;

import android.app.Activity;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.widget.TextView;

/**
 * 监听短信并截取出6位验证码
 * 
 * @author zp
 *
 *         2016年10月24日
 */
public class SmsContentObserver extends ContentObserver {
	private Activity activity;
	private String address;
	private TextView text;
	static SmsContentObserver content;

	/**
	 * <uses-permission android:name="android.permission.RECEIVE_SMS" /> 供其它组件调用
	 * 注册短信变化监听
	 * 
	 * @param context
	 */
	public static void registerSmsContentObserver(Activity context,
			String number, TextView text) {
		if (content != null) {
			return;
		}
		content = new SmsContentObserver(new Handler(), context, number, text);
		// 注册短信变化监听
		context.getContentResolver().registerContentObserver(
				Uri.parse("content://sms"), true, content);
	}

	/**
	 * 供其它组件调用 关闭数据库监听
	 * 
	 * @param context
	 */
	public static void unregisterSmsContentObserver(Activity context) {
		if (content != null) {
			// 关闭数据库监听
			context.getContentResolver().unregisterContentObserver(content);
		}
	}

	public SmsContentObserver(Handler handler, Activity activity,
			String number, TextView text) {
		super(handler);
		this.activity = activity;
		this.address = number;
		this.text = text;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);

		// 读取收件箱中指定号码的短信
		// content://sms/inbox 收件箱
		// content://sms/sent 已发送
		// content://sms/draft 草稿
		// content://sms/outbox 发件箱
		// content://sms/failed 发送失败
		// content://sms/queued 待发送列表
		Cursor cursor = activity.managedQuery(Uri.parse("content://sms/inbox"),
				new String[] { "_id", "address", "body", "read" },
				" address=? and read=?", new String[] { address, "0" },
				"_id desc");

		// 按id排序，如果按date排序的话，修改手机时间后，读取的短信就不准了
		if (cursor != null && cursor.getCount() > 0) {
			ContentValues values = new ContentValues();
			if (Build.VERSION.SDK_INT < 21) {
				values.put("type", 1);// 修改短信为已读短信 5.0后以不能修改
			}
			cursor.moveToNext();
			int smsbodyColumn = cursor.getColumnIndex("body");
			String smsbody = cursor.getString(smsbodyColumn);
			LogUtil.i("读取短信内容==" + smsbody);
			ToastUtil.showToast(activity, smsbody);
			text.setText(getDynamicPassword(smsbody)); // 调用下面的截取短信中六位数字验证码的方法
		}

		// 在用managedQuery的时候，不能主动调用close()方法， 否则在Android 4.0+的系统上， 会发生崩溃
		if (Build.VERSION.SDK_INT < 14) {
			cursor.close();
		}
	}

	/**
	 * 从字符串中截取连续6位数字组合 ([0-9]{" + 6 + "})截取六位数字 进行前后断言不能出现数字 用于从短信中获取动态密码
	 *
	 * @param st
	 *            短信内容
	 * @return 截取得到的6位动态密码
	 */
	public String getDynamicPassword(String st) {
		// 6是验证码的位数一般为六位 如果验证码的位数变化只要将6修改为想要的位数，
		// 过验证如果不止为数字，直接修改正则为想要的内容即可

		// Pattern是java.util.regex（一个用正则表达式所订制的模式来对字符串进行匹配工作的类库包）中的一个类。
		// 一个Pattern是一个正则表达式经编译后的表现模式
		Pattern pattern = Pattern.compile("(?<![0-9])([0-9]{" + 6
				+ "})(?![0-9])");
		Matcher matcher = pattern.matcher(st);
		String dynamicPassword = null;
		while (matcher.find()) {
			System.out.println(matcher.group());
			dynamicPassword = matcher.group();
		}
		return dynamicPassword;
	}
}
