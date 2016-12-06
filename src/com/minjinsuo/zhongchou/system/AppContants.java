package com.minjinsuo.zhongchou.system;

public class AppContants {

	/**
	 * 项目标示。产品还是众筹项目
	 */
	public static String ISPRODUCT = "isproduct";
	/**
	 * 项目类型标示
	 */
	public static String TYPE_PRJ = "type_prj";
	/**
	 * 跳转来源
	 */
	public static String FROM = "from";
	/**
	 * 保存登录用户的的标示
	 */
	public static String USERINFO = "userinfo";
	/** act 默认请求码 **/
	public static final int ACT_REQUEST_CODE = 1001;
	/** act 默认返回码 **/
	public static final int ACT_RESULT_CODE = 1002;

	/**
	 * 记录请求接口的时间
	 */
	public static long lastTime = System.currentTimeMillis();
	/**
	 * 判断是否暗登录的间隔(防止sessionId过期),分钟
	 */
	public static long fresh_Max = 3 * 60;

	/**
	 * 界面不可见初始时间
	 */
	public static long NotSeenStartTime = 0;
	/**
	 * 上次不可见界面名称
	 */
	public static String NotSeenActivivityName = null;
	/**
	 * 手势密码显示的时间限制，单位秒
	 */
	public static final int MaxGesPwdTime = 0;

	/**
	 * 是否暂时屏蔽手势密码页面——比如：打电话、去相册选择图片时。默认false 要显示
	 */
	public static boolean isNotShowGesPwd = false;
	/**
	 * 是否是无私奉献的标
	 */
	public static final String ISWSFX = "isWuSiFengXian";
}
