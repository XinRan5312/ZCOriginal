package com.minjinsuo.zhongchou.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类 不允许相同的显示信息在同一个窗体中频繁多次重复显示
 * 
 * @author Jzc
 *
 */
public class ToastUtil {
	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	public static void showToast(Context context, String s) {
		if (StringUtils.isEmpty(s)) {
			return;
		}
		if (toast == null) {
			toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > AppSetUp.TOASTINTERVAL) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
		}
		oldMsg = s;
		oneTime = twoTime;
	}

	public static void cancelToast() {
		if (toast != null) {
			toast.cancel();
		}
	}

	public static void showToast(Context context, int resId) {
		showToast(context, context.getString(resId));
	}
}