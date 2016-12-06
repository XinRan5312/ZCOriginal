package com.minjinsuo.zhongchou.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.common.util.LogUtil;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * 专门处理字符串的工具类，各种校验和字符处理
 */
public class StringUtils {
	public static final String STR_ENG_NUM_MUST = "(?!^\\d+$)(?!^[a-zA-Z]+$)(?!^[#$%^&.*~@]+$).{6,16}";

	/*
	 * String：SharedPreferences中取数据
	 */
	public static String getSharedPreferencesString(Context context,
			String fileName, String keyName, String defaultValue) {
		SharedPreferences settings = context.getSharedPreferences(fileName, 0);
		String value = settings.getString(keyName, defaultValue);
		return value;
	}

	/*
	 * String：SharedPreferences中存数据
	 */
	public static void setSharedPreferencesString(Context context,
			String fileName, String keyName, String value) {
		SharedPreferences settings = context.getSharedPreferences(fileName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(keyName, value);
		editor.commit();
	}

	/**
	 * 判断字符串是否为空
	 */
	public static boolean isEmpty(String str) {
		return (null == str || str.trim().length() == 0);
	}

	/**
	 * 校验手机号码(宽松校验) created at 2016/6/11 19:07
	 */
	public static boolean isPhoneNum(String phoneNum) {
		if (isEmpty(phoneNum)) {
			return false;
		} else
			return phoneNum.matches("^1\\d{10}$");
	}

	/**
	 * 校验手机号码（严格校验）
	 */
	public static boolean isPhoneNumDetail(String mobiles) {
		if (isEmpty(mobiles)) {
			return false;
		} else {
			Pattern p = Pattern
					.compile("^((14[0-9])|(17[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
			Matcher m = p.matcher(mobiles);
			return m.matches();
		}
	}

	/* 校验邮箱 */
	public static boolean isLegalMailAddress(String mail) {
		if (StringUtils.isEmpty(mail)) {
			return false;
		} else
			return mail
					.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	}

	/**
	 * 检验用户名(默认只能是字母和数字，不能包含汉字)
	 */
	public static boolean isUsername(String username) {
		if (isEmpty(username)) {
			return false;
		} else {
			String str = "^[0-9a-zA-Z]{4,18}$";// 此时只能包含英文和数字
			// String str = "^[0 -9a-zA-Z_\\u4e00 -\\u9fa5]{4,18}$";//
			// 只能包含中文，数字和英文
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(username);
			return m.matches();
		}
	}

	/**
	 * 实名认证时 判断用户名是否合法
	 * 
	 * @param homeName
	 * @return
	 */
	public static boolean isLegalName(String homeName) {
		if (isEmpty(homeName)) {
			return false;
		}
		return homeName.matches("^[\u4e00-\u9fa5A-Za-z0-9]{2,50}$");
	}

	/**
	 * 判断输入密码是否符合规范
	 */
	public static boolean isAvailablePwd(String str) {
		if (isEmpty(str)) {
			return false;
		} else {
			Pattern pattern = Pattern
					.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$");
			return pattern.matcher(str).matches();
		}
	}

	/**
	 * 过滤掉字符(允许输入以下字符)
	 *
	 * @param str
	 */
	public static String StringFilter(String str) {
		String reg = "[^a-zA-Z0-9_\u4E00-\u9FA5\\.\\_\\-@]+";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll("").trim();
	}

	/**
	 * 输入金额合法化限制
	 *
	 * @param str
	 */
	public static String moneyFilter(String str) {
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(str);

		return match.replaceAll("").trim();
	}

	/**
	 * 检验身份证号
	 */
	public static boolean isIDCard(String IDCard) {
		if (isEmpty(IDCard)) {
			return false;
		} else {
			String str = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(IDCard);
			return m.matches();
		}
	}

	/**
	 * 检验银行卡号（只校验位数--可以是15 -20位）
	 */
	public static boolean isBankCard(String bankCard) {
		if (isEmpty(bankCard)) {
			return false;
		} else {
			String str = "^\\d{15,20}$";
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(bankCard);
			return m.matches();
		}
	}

	/**
	 * 检测String是否都是数字
	 */
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 将字符串转换为数字double
	 */
	public static double changeToDouble(String value) {
		if (value.contains("元")) {
			value = value.replace("元", "");
		}
		if (value == null || value.trim().length() == 0) {
			return 0;
		} else {
			return Double.parseDouble(value);
		}
	}

	/**
	 * 取字符串中的数字
	 *
	 * @param value
	 * @return
	 */
	public static String getNumFromStr(String value) {
		String regEx = "[^0-9.]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(value);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断是否是合法金额（不能输入负数，小数点后最多两位）
	 */
	public static boolean isMoneyVerify(String str) {
		Pattern pattern = Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 返回两位小数(不进行四舍五入)
	 */
	@SuppressLint("NewApi")
	public static String getTwoPoint(String value) {
		if (!isEmpty(value)) {
			if ("0.00".equals(value) || "0.00".equals(value)
					|| "0.0".equals(value) || "0".equals(value)) {
				return "0.00";
			}
			if ("-0.00".equals(value)) {
				return "0.00";
			}

			DecimalFormat df = new DecimalFormat("#,###.00");
			// 设置不进行四舍五入
			df.setRoundingMode(RoundingMode.FLOOR);
			Double valueD = Double.valueOf(value);
			if (valueD < 1 && valueD > 0) {
				return "0" + df.format(valueD);
			} else {
				return df.format(valueD);
			}

		}
		return "0";
	}

	/**
	 * 应用默认金额数字都保留到小数点后两位，要截取出来 长字符串的转化中文表示处理，规则为：超过一百万，如有必要标示为小数点后一位，超过一亿， 示例
	 * 101000.00 1000000 10000 11111 1000 ==> 10.1万
	 */
	public static String changeValuePattern(String str) {
		if (str.contains(".")) {
			str = str.substring(0, str.indexOf("."));
		}
		if (str.length() > 4 && str.length() < 9) {
			// 超过万不超过亿
			char c = str.charAt(str.length() - 4);
			if (Integer.valueOf(c + "") > 0) {
				str = str.substring(0, str.length() - 4) + "." + c + "万";
			} else {
				str = str.substring(0, str.length() - 4) + "万";
			}
		} else if (str.length() > 8) {
			// 超过亿
			boolean flag = false;
			StringBuffer buffer = new StringBuffer();
			for (int i = 4; i < 9; i++) {
				char c = str.charAt(str.length() - i);
				if (flag) {
					buffer.insert(0, c);
				} else if (Integer.valueOf(c + "") > 0) {
					flag = true;
					buffer.append(c);
				}
			}
			// 开始拼装
			if (buffer.toString().length() != 0) {
				str = str.substring(0, str.length() - 8) + "."
						+ buffer.toString() + "亿";
			} else {
				str = str.substring(0, str.length() - 8) + "亿";
			}
		} else {
			// 不超过万的情况，现实小数点后两位.00
			str += ".00";
		}
		return str;
	}

	/**
	 * 时间格式化
	 *
	 * @param dateStr
	 * @param flag
	 *            =1:只截取到日；2=截取到时分秒
	 * @return
	 */
	public static String getDate(String dateStr, int flag) {
		String date = "";
		if (isEmpty(dateStr)) {
			return date;
		}
		if (flag == 1) {
			if (dateStr.length() > 10) {
				date = dateStr.substring(0, 10);
			} else {
				date = dateStr;
			}
		} else if (flag == 2) {
			if (dateStr.length() > 11 && dateStr.contains(".")) {
				date = dateStr.substring(0, dateStr.indexOf("."));
			} else {
				date = dateStr;
			}
		}
		return date;
	}

	/**
	 * 混淆任意字符串， 保留前n个后m个字符 中间添加*格式
	 *
	 * @param n
	 *            保留前n个字符
	 * @param m
	 *            保留后m个字符
	 * @return 如前三后四位：133***********8236
	 * @author zp
	 */
	public static String blurNumForNM(int n, int m, String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		} else if (str.length() > (n + m)) {
			return str.substring(0, n) + "****"
					+ str.substring(str.length() - m, str.length());
		}
		return null;
	}

	/**
	 * 判断字符串必须包含英文字母+数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isENG_NUM_MUST(String str) {
		return regular(str, STR_ENG_NUM_MUST);
	}

	/**
	 * 匹配是否符合正则表达式pattern 匹配返回true
	 * 
	 * @param str
	 *            匹配的字符串
	 * @param pattern
	 *            匹配模式
	 * @return boolean
	 */
	private static boolean regular(String str, String pattern) {
		if (null == str || str.trim().length() <= 0)
			return false;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * list<String> 转 String
	 * 
	 * @param join
	 * @param strAry
	 * @return
	 */
	public static String join(String join, String[] strAry) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strAry.length; i++) {
			if (i == (strAry.length - 1)) {
				sb.append(strAry[i]);
			} else {
				sb.append(strAry[i]).append(join);
			}
		}

		return new String(sb);
	}

	// 应用默认金额数字都保留到小数点后两位，要截取出来
	// 长字符串的转化中文表示处理，规则为：超过一百万，如有必要标示为小数点后一位，超过一亿，
	/**
	 * @param str
	 *            示例 101000.00 1000000 10000 11111 1000
	 * @return 10.1万
	 */
	public static String rawIntStr2IntStr(String str) {
		String firstsym = "";
		String lastnum = ".00";
		if (isEmpty(str)) {
			str = "0.00";
		}
		if (str.contains("-")) {
			firstsym = "-";
			str = str.replace("-", "");
		}
		if (str.contains("+")) {
			firstsym = "+";
			str = str.replace("+", "");
		}
		if (str.contains("元")) {
			str = str.replace("元", "");
		}
		if (str.contains(".")) {
			lastnum = str.substring(str.indexOf("."));
			str = str.substring(0, str.indexOf("."));
		}
		if (str.length() > 4 && str.length() < 9) {
			// 超过万不超过亿
			// char c = str.charAt(str.length() - 4);
			String c = str.substring(str.length() - 4, str.length() - 2);

			if (Integer.valueOf(c + "") >= 0) {
				str = str.substring(0, str.length() - 4) + "." + c + "万";
			} else {
				str = str.substring(0, str.length() - 4) + "万";
			}
		} else if (str.length() > 8) {
			// 超过亿
			boolean flag = false;
			StringBuffer buffer = new StringBuffer();
			for (int i = 4; i < 9; i++) {
				char c = str.charAt(str.length() - i);
				if (flag) {
					buffer.insert(0, c);
				} else if (Integer.valueOf(c + "") > 0) {
					flag = true;
					buffer.append(c);
				}
			}
			// 开始拼装
			if (buffer.toString().length() != 0) {
				str = str.substring(0, str.length() - 8) + "."
						+ buffer.toString() + "亿";
			} else {
				str = str.substring(0, str.length() - 8) + "亿";
			}
		} else {
			// 不超过万的情况，现实小数点后两位.00
			str += lastnum;
		}
		return firstsym + str;
	}

	/**
	 * 给textView动态添加图片到右边
	 * 
	 * @param context
	 * @param tv
	 */
	public static void setTextViewDrawableRight(Context context, TextView tv,
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
	 * inputStream转String
	 *
	 * @author zp created at 2016/8/4 17:27
	 */
	public static String inputStream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	/**
	 * 复制文本到剪切板
	 *
	 * @param context
	 * @param tv_result
	 */
	public static void copyText(Context context, String str_result) {
		// 获取剪贴板管理服务
		ClipboardManager cm = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		if (!isEmpty(str_result)) {
			// 将文本数据复制到剪贴板
			cm.setText(str_result.trim());
			ToastUtil.showToast(context, "已复制到剪切板");
		} else {
			ToastUtil.showToast(context, "内容为空");
		}
	}

	/**
	 * 显示或隐藏文本内容（明文、密文）
	 * 
	 * @param isVisible
	 *            是否显示
	 */
	public static void hiddenOrVisible(TextView textView, boolean isVisible) {
		if (!isVisible) {
			StringBuilder hiddenTxt = new StringBuilder("****");
			textView.setTag(textView.getText().toString());
			textView.setText(hiddenTxt.toString());
		} else {
			if (null != textView.getTag()
					&& textView.getTag() instanceof String) {
				textView.setText(textView.getTag().toString());
			}
		}
	}

	/**
	 * 四舍五入保留n位小数
	 * 
	 * @param v
	 * @param scale
	 *            :要保留的位数（scale==0代表保留整数）
	 * @return
	 */
	public static String round(double v, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		String value = String.valueOf(v);
		int roundingMode = BigDecimal.ROUND_HALF_EVEN;
		BigDecimal decimal = new BigDecimal(value);
		decimal = decimal.setScale(scale, roundingMode);
		return decimal.toString();
	}

	/**
	 * 金额处理 满万进位
	 * 
	 * 当大于等于10000时，要以X万来显示,如果为10000的非整数倍，取两位小数，并不四舍五入，不足1万的不处理
	 * 例：13873.87——>1.38万
	 * 
	 * @param money
	 * @return
	 */
	public static String exceedW(String money) {
		if (StringUtils.isEmpty(money)) {
			LogUtil.i("error:金额满万进位无值");
			return "0";
		} else {
			money = money.trim();
			if (money.contains("元")) {
				money = money.replace("元", "");
			}
			String moneyStr = money;
			if (money.contains("."))
				money = money.substring(0, money.lastIndexOf("."));
			if (money.length() > 4) {// 判断整数部分是否大于万
				String c = money.substring(money.length() - 4,
						money.length() - 2);
				if (Integer.valueOf(c) > 0) {
					money = money.substring(0, money.length() - 4) + "." + c
							+ "万";
				} else {
					money = money.substring(0, money.length() - 4) + "万";
				}
			} else {
				money = round(Double.parseDouble(moneyStr), 2);
			}
		}
		return money;
	}
}
