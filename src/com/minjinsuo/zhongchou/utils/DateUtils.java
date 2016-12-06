package com.minjinsuo.zhongchou.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.xutils.common.util.LogUtil;

public class DateUtils {
	public final static String FULL_DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前系统时间
	 * 
	 * @return string
	 */
	public static String getCurrentTime() {
		SimpleDateFormat df = new SimpleDateFormat(FULL_DATE_TIME_FORMAT_1);// 设置日期格式
		// LogUtil.d("系统：" + df.format(new Date()));// new Date()为获取当前系统时间
		return df.format(new Date());
	}

	/**
	 * 把已经格式化显示的时间转化为自己想要的指定格式
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			return new Date();
		}
	}
}
