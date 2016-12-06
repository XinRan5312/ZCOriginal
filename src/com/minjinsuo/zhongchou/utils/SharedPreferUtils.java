package com.minjinsuo.zhongchou.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import org.xutils.common.util.LogUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

/**
 * SharedPreference工具类
 * 
 * @author zp
 *
 *         2016年8月3日
 */
public class SharedPreferUtils {
	// 是否第一次启动程序
	private static final String ISFIRSTSTART = "IsFristStart";
	private static final String CLIENTID = "clientId";
	private static final String GETUI_CID = "clientCid"; // 个推的clientid
	private static final String EYESTATE = "eyestate"; // 睁眼 闭眼的状态
	private static String FILENAME = "sp_data";

	/**
	 * 向SharedPreferences中写入int类型数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putValue(Context context, String name, String key,
			int value) {
		Editor sp = getEditor(context, name);
		sp.putInt(key, value);
		sp.commit();
	}

	/**
	 * 向SharedPreferences中写入boolean类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putValue(Context context, String name, String key,
			boolean value) {
		Editor sp = getEditor(context, name);
		sp.putBoolean(key, value);
		sp.commit();
	}

	/**
	 * 向SharedPreferences中写入String类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putValue(Context context, String name, String key,
			String value) {
		Editor sp = getEditor(context, name);
		sp.putString(key, value);
		sp.commit();
		LogUtil.i(value);
	}

	/**
	 * 向SharedPreferences中写入float类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putValue(Context context, String name, String key,
			float value) {
		Editor sp = getEditor(context, name);
		sp.putFloat(key, value);
		sp.commit();
	}

	/**
	 * 向SharedPreferences中写入long类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public static void putValue(Context context, String name, String key,
			long value) {
		Editor sp = getEditor(context, name);
		sp.putLong(key, value);
		sp.commit();
	}

	/**
	 * 从SharedPreferences中读取int类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param defValue
	 *            如果读取不成功则使用默认值
	 * @return 返回读取的值
	 */
	public static int getValue(Context context, String name, String key,
			int defValue) {
		SharedPreferences sp = getSharedPreferences(context, name);
		int value = sp.getInt(key, defValue);
		return value;
	}

	/**
	 * 从SharedPreferences中读取boolean类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param defValue
	 *            如果读取不成功则使用默认值
	 * @return 返回读取的值
	 */
	public static boolean getValue(Context context, String name, String key,
			boolean defValue) {
		SharedPreferences sp = getSharedPreferences(context, name);
		boolean value = sp.getBoolean(key, defValue);
		return value;
	}

	/**
	 * 从SharedPreferences中读取String类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param defValue
	 *            如果读取不成功则使用默认值
	 * @return 返回读取的值
	 */
	public static String getValue(Context context, String name, String key,
			String defValue) {
		SharedPreferences sp = getSharedPreferences(context, name);
		String value = sp.getString(key, defValue);
		LogUtil.i(key + ":" + value);
		return value;
	}

	/**
	 * 从SharedPreferences中读取float类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param defValue
	 *            如果读取不成功则使用默认值
	 * @return 返回读取的值
	 */
	public static float getValue(Context context, String name, String key,
			float defValue) {
		SharedPreferences sp = getSharedPreferences(context, name);
		float value = sp.getFloat(key, defValue);
		return value;
	}

	/**
	 * 从SharedPreferences中读取long类型的数据
	 * 
	 * @param context
	 *            上下文环境
	 * @param name
	 *            对应的xml文件名称
	 * @param key
	 *            键
	 * @param defValue
	 *            如果读取不成功则使用默认值
	 * @return 返回读取的值
	 */
	public static long getValue(Context context, String name, String key,
			long defValue) {
		SharedPreferences sp = getSharedPreferences(context, name);
		long value = sp.getLong(key, defValue);
		return value;
	}

	// 获取Editor实例
	private static Editor getEditor(Context context, String name) {
		return getSharedPreferences(context, name).edit();
	}

	// 获取SharedPreferences实例
	private static SharedPreferences getSharedPreferences(Context context,
			String name) {
		return context.getSharedPreferences(name, Context.MODE_PRIVATE);
	}

	/**
	 * @author zp
	 * 
	 *         desc:保存对象
	 * 
	 * @param context
	 * @param key
	 * @param obj
	 *            要保存的对象，只能保存实现了serializable的对象 modified:
	 */
	public static void saveObject(Context context, String key, Object obj) {
		try {
			// 保存对象
			SharedPreferences.Editor sharedata = context.getSharedPreferences(
					FILENAME, 0).edit();
			// 先将序列化结果写到byte缓存中，其实就分配一个内存空间
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			// 将对象序列化写入byte缓存
			os.writeObject(obj);
			// 将序列化的数据转为16进制保存
			String bytesToHexString = bytesToHexString(bos.toByteArray());
			// 保存该16进制数组
			sharedata.putString(key, bytesToHexString);
			sharedata.commit();
			Log.e("", "保存obj成功");
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("", "保存obj失败");
		}
	}

	/**
	 * desc:将数组转为16进制
	 * 
	 * @param bArray
	 * @return modified:
	 */
	public static String bytesToHexString(byte[] bArray) {
		if (bArray == null) {
			return null;
		}
		if (bArray.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * desc:获取保存的Object对象
	 * 
	 * @param context
	 * @param key
	 * @return modified:
	 */
	public static Object readObject(Context context, String key) {
		try {
			SharedPreferences sharedata = context.getSharedPreferences(
					FILENAME, 0);
			if (sharedata.contains(key)) {
				String string = sharedata.getString(key, "");
				if (TextUtils.isEmpty(string)) {
					return null;
				} else {
					// 将16进制的数据转为数组，准备反序列化
					byte[] stringToBytes = StringToBytes(string);
					ByteArrayInputStream bis = new ByteArrayInputStream(
							stringToBytes);
					ObjectInputStream is = new ObjectInputStream(bis);
					// 返回反序列化得到的对象
					Object readObject = is.readObject();
					return readObject;
				}
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 所有异常返回null
		return null;

	}

	/**
	 * desc:将16进制的数据转为数组
	 * <p>
	 * 创建人：聂旭阳 , 2014-5-25 上午11:08:33
	 * </p>
	 * 
	 * @param data
	 * @return modified:
	 */
	public static byte[] StringToBytes(String data) {
		String hexString = data.toUpperCase().trim();
		if (hexString.length() % 2 != 0) {
			return null;
		}
		byte[] retData = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i++) {
			int int_ch; // 两位16进制数转化后的10进制数
			char hex_char1 = hexString.charAt(i); // //两位16进制数中的第一位(高位*16)
			int int_ch1;
			if (hex_char1 >= '0' && hex_char1 <= '9')
				int_ch1 = (hex_char1 - 48) * 16; // // 0 的Ascll - 48
			else if (hex_char1 >= 'A' && hex_char1 <= 'F')
				int_ch1 = (hex_char1 - 55) * 16; // // A 的Ascll - 65
			else
				return null;
			i++;
			char hex_char2 = hexString.charAt(i); // /两位16进制数中的第二位(低位)
			int int_ch2;
			if (hex_char2 >= '0' && hex_char2 <= '9')
				int_ch2 = (hex_char2 - 48); // // 0 的Ascll - 48
			else if (hex_char2 >= 'A' && hex_char2 <= 'F')
				int_ch2 = hex_char2 - 55; // // A 的Ascll - 65
			else
				return null;
			int_ch = int_ch1 + int_ch2;
			retData[i / 2] = (byte) int_ch;// 将转化后的数放入Byte里
		}
		return retData;
	}

	/**
	 * 获取是否为第一次启动程序
	 *
	 * @return
	 */
	public static boolean isFirstStart(Context context) {
		SharedPreferences preference = getSharedPreferences(context,
				ISFIRSTSTART);
		boolean isFirst = preference.getBoolean(ISFIRSTSTART, true);
		return isFirst;
	}

	/**
	 * 设置用户第一次启动程序
	 *
	 */
	public static void setNotFirstStart(Context context) {
		SharedPreferences preference = getSharedPreferences(context,
				ISFIRSTSTART);
		Editor editor = preference.edit();
		editor.putBoolean(ISFIRSTSTART, false);
		editor.commit();
	}

	/**
	 * 设置个推的ClientId
	 * 
	 * @param context
	 * @param state
	 *            状态 true:睁眼 false：闭眼
	 */
	public static void setClientId(Context context, String clientId) {
		SharedPreferences preference = getSharedPreferences(context, CLIENTID);
		Editor editor = preference.edit();
		editor.putString(GETUI_CID, clientId);
		editor.commit();
	}

	/**
	 * 获取个推的ClientId
	 * 
	 * @param context
	 * @return
	 */
	public static String getClientId(Context context) {
		SharedPreferences preference = getSharedPreferences(context, CLIENTID);
		return preference.getString(GETUI_CID, "");
	}

	/**
	 * 设置睁眼闭眼的状态
	 * 
	 * @param context
	 * @param state
	 *            状态 true:睁眼 false：闭眼
	 */
	public static void setEyeState(Context context, boolean state) {
		SharedPreferences preference = getSharedPreferences(context, EYESTATE);
		Editor editor = preference.edit();
		editor.putBoolean(EYESTATE, state);
		editor.commit();
	}

	/**
	 * 获取睁眼闭眼的状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getEyeSate(Context context) {
		SharedPreferences preference = getSharedPreferences(context, EYESTATE);
		return preference.getBoolean(EYESTATE, true);
	}

}
