package com.minjinsuo.zhongchou.activity;

import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.widget.ActionSheet;
import com.minjinsuo.zhongchou.widget.ActionSheet.ActionSheetBuilder;
import com.minjinsuo.zhongchou.widget.ActionSheet.ActionSheetListener;
import com.minjinsuo.zhongchou.widget.swipebacklayout.SwipeBackActivity;

/**
 * Activity 基础类
 */
public abstract class Activity_Base extends SwipeBackActivity implements
		MyRequestCallBack {
	public static int pageSize = 10;
	public int pageNo = 1;
	public boolean isPullDown = true;
	public ImageOptions imageOptions;// xUtils图片配置
	// 输入键盘判定
	private InputMethodManager im;

	private ActionSheetBuilder actionSheet = null;

	/**
	 * 获取当前Activity的Context
	 * 
	 * @return
	 */
	public Context getContext() {
		return Activity_Base.this;
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTheme(R.style.ActionSheetStyleIOS7);
		actionSheet = ActionSheet.createBuilder(this,
				getSupportFragmentManager()).setCancelableOnTouchOutside(true);

		//用于关闭Activity的广播
		IntentFilter filter = new IntentFilter();
		filter.addAction("finish");
		registerReceiver(mFinishReceiver, filter);

		// 填充顶部图片
		imageOptions = new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(0)).setIgnoreGif(false)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
	}

	/**
	 * 初始化视图
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.i("onPause~~~");
		if (LockPatternService.getLockPattern(this) != null) {// 设置了手势密码

			AppContants.NotSeenStartTime = System.currentTimeMillis();
			AppContants.NotSeenActivivityName = getClass().getSimpleName();
		}
	}

	/**
	 * Volley请求与Activity生命周期联动 可以在Activity关闭时取消请求队列中的请求。
	 */
	@Override
	protected void onStop() {
		super.onStop();
		ZCApplication.getQueue().cancelAll(getContext());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// overridePendingTransition(R.anim.default_anim_in,
		// R.anim.default_anim_out);
		unregisterReceiver(mFinishReceiver);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		LogUtil.i("onRestart~~~~");
		if (LockPatternService.getLockPattern(this) != null
				&& LockPatternService.getIsOpenLockPatten(this)
				&& !AppContants.isNotShowGesPwd) {
			if ((System.currentTimeMillis() - AppContants.NotSeenStartTime) > AppContants.MaxGesPwdTime * 1000) {
				String notSeenActivivityName = AppContants.NotSeenActivivityName;
				if (!StringUtils.isEmpty(notSeenActivivityName)
						&& notSeenActivivityName.equals(getClass()
								.getSimpleName())) {// 上一次不可见界面存在并且和当前activity相同，显示手势密码
					AppContants.NotSeenStartTime = 0;
					// 注意一定将全局变量的值置空，否则会导致很多个手势界面打开
					AppContants.NotSeenActivivityName = "";
					startActivity(new Intent(this,
							Activity_CheckoutGestureLock.class));
				}
			}
		}

	}

	/**
	 * 初始化监听
	 */
	protected abstract void initListener();

	public void setTitleText(String title) {
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		if (StringUtils.isEmpty(title)) {
			return;
		}
		tv_title.setText(title);
	}

	public void setTitleBack(OnClickListener listener) {
		ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_back.setVisibility(View.VISIBLE);
		if (null == listener) {
			btn_back.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					if (im != null && getCurrentFocus() != null) {
						im.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
					}
					finish();
				}
			});
		} else {
			btn_back.setOnClickListener(listener);
		}
	}

	public void setTitleBack() {
		setTitleBack(new OnClickListener() {
			@Override
			public void onClick(View v) {
				im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				if (im != null && getCurrentFocus() != null) {
					im.hideSoftInputFromWindow(getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
				finish();
			}
		});

	}

	/**
	 * 标题栏有按钮
	 * 
	 * @param text
	 * @param listener
	 */
	public void setTitleRight(String text, OnClickListener listener) {
		TextView tv_right = (TextView) findViewById(R.id.tv_right);
		if (tv_right != null) {
			if (tv_right.getVisibility() != View.VISIBLE) {
				tv_right.setVisibility(View.VISIBLE);
			}
			tv_right.setText(text);
			tv_right.setOnClickListener(listener);
		}

	}

	/**
	 * 右边按钮带图片
	 * 
	 * @param view
	 * @param rid
	 * @param onClickListener
	 */
	public void setTitleRightDrawable(int rid, OnClickListener onClickListener) {
		ImageView iv_menu = (ImageView) findViewById(R.id.iv_menu);
		if (iv_menu != null) {
			if (iv_menu.getVisibility() != View.VISIBLE) {
				iv_menu.setVisibility(View.VISIBLE);
			}
			iv_menu.setImageResource(rid);
			iv_menu.setOnClickListener(onClickListener);
		}
	}

	/**
	 * 右边按钮带图片2
	 * 
	 * @param view
	 * @param rid
	 * @param onClickListener
	 */
	public void setTitleRightDrawable2(int rid, OnClickListener onClickListener) {
		ImageView iv_menu = (ImageView) findViewById(R.id.iv_menu_2);
		if (iv_menu != null) {
			if (iv_menu.getVisibility() != View.VISIBLE) {
				iv_menu.setVisibility(View.VISIBLE);
			}
			iv_menu.setImageResource(rid);
			iv_menu.setOnClickListener(onClickListener);
		}
	}

	/**
	 * 显示选项
	 */
	public void showOption(String cancel, ActionSheetListener listener,
			String[] actionsheetData) {
		// TODO Auto-generated method stub
		actionSheet.setCancelButtonTitle(cancel);
		actionSheet.setListener(listener);
		actionSheet.setOtherButtonTitles(actionsheetData);
		actionSheet.show();
	}

	private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if ("finish".equals(intent.getAction())) {

				Log.e("#########", "I am " + getLocalClassName()

				+ ",now finishing myself...");

				finish();

			}
		}
	};

	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		Configuration config = new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config, res.getDisplayMetrics());
		return res;
	}

}
