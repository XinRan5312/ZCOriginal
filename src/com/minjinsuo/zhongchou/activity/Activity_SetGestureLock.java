package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_Lock;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.GestureLockView;
import com.minjinsuo.zhongchou.view.GestureLockView.OnGestureFinishListener;

/**
 * 设置手势密码
 * 
 */
public class Activity_SetGestureLock extends Activity_Base {

	private GridView gv_lock;// 提示手势图片

	private TextView gv_textview;// 提示语

	private GestureLockView gestureLockView;// 手势密码锁

	private Adapter_Lock lockAdapter;

	private boolean isSetting;// 是否绘制
	private boolean isFirstSet;// 是否初次登录
	public static final String FIRSTSET = "firstSetPwd";

	private TranslateAnimation animation;
	private RelativeLayout rl_title;
	private ImageView iv_back;
	/** 手势密码连接的数量值 */
	private int limitNum = 4;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			setResult(RESULT_OK);
			Activity_SetGestureLock.this.finish();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_gesturelock);
		setSwipeBackEnable(false);
		
		setTitleText("设置手势密码");

		initView();
		initListener();

		// 是否从修改手势密码进入
		if (LockPatternService.isFromReSetGesture(Activity_SetGestureLock.this)) {
			setTitleBack();
			iv_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					LockPatternService.setFromReSetGesture(
							Activity_SetGestureLock.this, false);
					Activity_SetGestureLock.this.finish();
				}
			});
		}
	}

	@Override
	protected void initView() {
		rl_title = (RelativeLayout) findViewById(R.id.title_bar);
		iv_back = (ImageView) findViewById(R.id.btn_back);
		gv_lock = (GridView) findViewById(R.id.gv_lock);
		lockAdapter = new Adapter_Lock(this);
		gv_lock.setAdapter(lockAdapter);
		gv_textview = (TextView) findViewById(R.id.gv_textview);
		gv_textview.setText("请绘制解锁图案");
		gv_textview.setVisibility(View.VISIBLE);
		gv_textview.setTextColor(Color.parseColor("#666666"));
		gestureLockView = (GestureLockView) findViewById(R.id.gestureLockView);
		gestureLockView.setLimitNum(limitNum);

		isFirstSet = getIntent().getBooleanExtra(FIRSTSET, false);
		if (isFirstSet) {// 只有初次设置时提醒是否跳过手势
			// 跳过按钮
			setTitleRight("跳过", new OnClickListener() {

				@Override
				public void onClick(View v) {
					setResult(RESULT_CANCELED);
					Activity_SetGestureLock.this.finish();
				}
			});
		}
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {
		animation = new TranslateAnimation(-20, 20, 0, 0);
		animation.setDuration(50);
		animation.setRepeatCount(2);
		animation.setRepeatMode(Animation.REVERSE);
		gestureLockView
				.setOnGestureFinishListener(new OnGestureFinishListener() {
					@Override
					public void OnGestureFinish(boolean success, String key) {
						if (success) {
							lockAdapter.setKey(key);
							if (!isSetting) {
								gv_textview.setTextColor(Color
										.parseColor("#333333"));
								gv_textview.setText("请再绘制一次");
								gestureLockView.setKey(key);
								isSetting = true;
							} else {
								// gv_textview.setTextColor(Color
								// .parseColor("#009900"));//浅绿色
								gv_textview.setTextColor(getResources()
										.getColor(R.color.main_color));// 主题色
								gv_textview.setText("手势密码绘制成功");
								// 下面是提示绘制成功时显示的对号icon，可以不显示
								// Drawable drawable =
								// getResources().getDrawable(
								// R.drawable.skyblue_platform_checked);
								// drawable.setBounds(0, 0,
								// drawable.getMinimumWidth(),
								// drawable.getMinimumHeight());
								// gv_textview.setCompoundDrawables(drawable,
								// null, null, null);
								LockPatternService.saveLockPattern(
										getApplicationContext(), key);
								LockPatternService.saveSturePasswordNum(
										getApplicationContext(), 0);
								new Thread() {
									@Override
									public void run() {
										try {
											sleep(200);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										mHandler.sendEmptyMessage(1);
									};
								}.start();

								// finish();
							}

						} else {
							if (key.length() >= limitNum)
								gv_textview.setText("绘制的密码与上次不一致！");
							else
								gv_textview.setText("绘制的个数不能低于" + limitNum
										+ "个");
							gv_textview.startAnimation(animation);
							gv_textview.setTextColor(getResources().getColor(
									R.color.red));
						}

					}
				});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (LockPatternService.isFromReSetGesture(Activity_SetGestureLock.this)) {
			LockPatternService.setFromReSetGesture(this, false);
			finish();
			return true;
		} else {// 登录后必须设置手势
			// CommonUtils.showToast(this, "你还未设置手势密码,不能返回");
			exit();
			return false;
		}
	}

	/**
	 * 双击返回键退出程序
	 */
	private boolean isExit = false;

	private void exit() {
		if (!isExit) {
			isExit = true;
			ToastUtil.showToast(getApplicationContext(), "再按一次退出程序");
			handler.sendEmptyMessageDelayed(0, 2000);
		} else {
			LockPatternService.LoginOut(this);
//			Intent intent = new Intent(Intent.ACTION_MAIN);
//			intent.addCategory(Intent.CATEGORY_HOME);
//			startActivity(intent);
//			System.exit(0);
			getApplicationContext().sendBroadcast(new Intent("finish"));
			ZCApplication.getInstance().exit();
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			isExit = false;
		};
	};

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}
}
