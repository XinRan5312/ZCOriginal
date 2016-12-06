package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.view.CircleImageView;
import com.minjinsuo.zhongchou.view.GestureLockView;
import com.minjinsuo.zhongchou.view.GestureLockView.OnGestureFinishListener;

/**
 * 手势密码认证
 */
public class Activity_CheckoutGestureLock extends Activity_Base implements
		OnClickListener {
	@ViewInject(R.id.title_bar)
	private RelativeLayout title_bar;// 标题栏
	/** 手势密码解锁成功 */
	public static final int SUCCESS = 101;
	/** 手势密码输入过多失败重置 */
	public static final int FAILURE = 100;
	/** 其他情况（忘记密码登录、切换账号） */
	public static final int OTHER = 104;
	/** 取消 */
	public static final int CANCLE = 105;

	/** 忘记密码 */
	public static final int WJMM = 101;
	/** 切换账号 */
	public static final int QHZH = 102;
	/** 密码输错次数过多 */
	public static final int SCZH = 103;

	private GestureLockView gestureLockView;

	private TextView textview;
	private TextView tvWjmm;// 忘记密码
	private TextView tvQhzh;// 切换账号
	private CircleImageView iv_userIcon;

	private Animation animation;

	private int errorNum;// 错误数量
	private int limitErrorNum = 5;// 限制错误数次
	private ZCApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_checkout_gesturelock);
		x.view().inject(this);
		setSwipeBackEnable(false);
		
		ZCApplication.getInstance().addActivity(this);
		initView();
		initData();
		initUserPhoto();

		// 下面针对修改手势、打开手势开关前校验手势进行处理——如果不需要校验可删除下面代码即可
		if (LockPatternService.isFromCheckGesture(this)) {
			tvQhzh.setVisibility(View.GONE);
			title_bar.setVisibility(View.VISIBLE);
			setTitleText("校验原手势密码");
			setTitleBack(new OnClickListener() {

				@Override
				public void onClick(View v) {
					LockPatternService.setFromCheckGesture(
							Activity_CheckoutGestureLock.this, false);
					Activity_CheckoutGestureLock.this.finish();
					ZCApplication.getInstance().deleteActivity(
							Activity_CheckoutGestureLock.this);
				}
			});
		}

	}

	public void initView() {
		app = (ZCApplication) this.getApplicationContext();
		gestureLockView = (GestureLockView) findViewById(R.id.gestureLockView);
		textview = (TextView) findViewById(R.id.textview);
		tvWjmm = (TextView) findViewById(R.id.tv_wjmm);
		tvQhzh = (TextView) findViewById(R.id.tv_qhzh);
		iv_userIcon = (CircleImageView) findViewById(R.id.iv_userIcon);
		tvWjmm.setOnClickListener(this);
		tvQhzh.setOnClickListener(this);
	}

	public void initUserPhoto() {
		ImageOptions imageOptions = new ImageOptions.Builder()
				.setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
				.setCrop(false)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.head)
				.setFailureDrawableId(R.drawable.head).build();

		if (!StringUtils.isEmpty(app.getInstance().getUserInfo().getPortrait())) {
			String pic_head = app.getInstance().getUserInfo().getPortrait();
			if (!StringUtils.isEmpty(pic_head)) {
				x.image().bind(iv_userIcon, pic_head, imageOptions);
			}
		} else if (!StringUtils.isEmpty(LockPatternService.getUserPhoto(this))) {
			String pic_head = LockPatternService.getUserPhoto(this);
			if (!StringUtils.isEmpty(pic_head)) {
				x.image().bind(iv_userIcon, pic_head, imageOptions);
			}
		}
	}

	public void initData() {

		animation = new TranslateAnimation(-20, 20, 0, 0);
		animation.setDuration(50);
		animation.setRepeatCount(2);
		animation.setRepeatMode(Animation.REVERSE);
		// 设置密码
		String key = LockPatternService.getLockPattern(this);
		System.out.println("key:" + key);
		if (StringUtils.isEmpty(key)) {
			finish();
		} else {
			gestureLockView.setKey(key);
		}
		errorNum = LockPatternService.getSturePasswordNum(this);

		if (errorNum != 0) {
			textview.setTextColor(Color.parseColor("#FF2525"));
			int num = limitErrorNum - errorNum;
			textview.setText("还可以输入" + num + "次");
		} else {
			textview.setTextColor(Color.parseColor("#666666"));
			String phoneNumber = LockPatternService.getPhoneNumber(this);
			if (!StringUtils.isEmpty(phoneNumber)) {
				phoneNumber = StringUtils.blurNumForNM(3, 4, phoneNumber);
				textview.setText(phoneNumber);
			} else {
				if (!StringUtils.isEmpty(LockPatternService.getUserName(this))) {
					textview.setText(LockPatternService.getUserName(this));
				} else {
					textview.setText("");
				}
			}
		}
		// 手势完成后回
		gestureLockView
				.setOnGestureFinishListener(new OnGestureFinishListener() {
					@Override
					public void OnGestureFinish(boolean success, String key) {
						System.out.println("keyback" + key);
						if (success) {
							textview.setTextColor(Color.parseColor("#666666"));
							textview.setVisibility(View.VISIBLE);
							textview.setText("密码正确");
							textview.startAnimation(animation);

							LockPatternService.saveSturePasswordNum(
									Activity_CheckoutGestureLock.this, 0);
							setResult(SUCCESS);
							finish();

						} else {
							errorNum++;
							LockPatternService
									.saveSturePasswordNum(
											Activity_CheckoutGestureLock.this,
											errorNum);
							int num = limitErrorNum - errorNum;
							if (errorNum >= limitErrorNum) {
								Toast.makeText(getApplicationContext(),
										"密码错误次数超过" + limitErrorNum + "次，请重新登录",
										Toast.LENGTH_SHORT).show();

								LockPatternService
										.LoginOut(Activity_CheckoutGestureLock.this);

								startActivityForResult(new Intent(
										Activity_CheckoutGestureLock.this,
										Activity_Login.class), SCZH);
							}
							textview.setTextColor(Color.parseColor("#FF2525"));
							textview.setVisibility(View.VISIBLE);
							textview.setText("密码错误，还可以输入" + num + "次");
							textview.startAnimation(animation);

						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_wjmm:// 忘记密码
			Intent intent = new Intent(this, Activity_Login.class);
			startActivityForResult(intent, WJMM);
			break;
		case R.id.tv_qhzh:// 切换登陆方式
			startActivityForResult(new Intent(this, Activity_Login.class), QHZH);
			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case WJMM:// 忘记密码
			// 忘记密码
			if (resultCode == RESULT_OK) {// 登陆成功
				startActivity(new Intent(Activity_CheckoutGestureLock.this,
						Activity_Main.class));
				LockPatternService
						.clearLockPattern(Activity_CheckoutGestureLock.this);
				LockPatternService.isOpenLockPattern(
						Activity_CheckoutGestureLock.this, false);// 手势密码关闭
				setResult(OTHER);// Activity_Splash 中回调
				Activity_CheckoutGestureLock.this.finish();
				ZCApplication.getInstance().exit();
			}
			break;

		case QHZH:// 切换账号
			if (resultCode == RESULT_OK) {// 登陆成功
				startActivity(new Intent(Activity_CheckoutGestureLock.this,
						Activity_Main.class));
				setResult(OTHER);// Activity_Splash 中回调
				Activity_CheckoutGestureLock.this.finish();
				ZCApplication.getInstance().exit();
			}
			break;
		case SCZH:// 密码输错次数过多
			if (resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
				startActivity(new Intent(Activity_CheckoutGestureLock.this,
						Activity_Main.class));
				setResult(OTHER);// Activity_Splash 中回调关闭页面
				Activity_CheckoutGestureLock.this.finish();
				ZCApplication.getInstance().exit();
			}
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (LockPatternService.getLockPattern(this) != null) {// 设置了手势密码

			LockPatternService.NotSeenStartTime = System.currentTimeMillis();
			LockPatternService.NotSeenActivivityName = getClass()
					.getSimpleName();
		}
	}

	/**
	 * 双击返回键退出程序
	 */
	private boolean isExit = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (LockPatternService.isFromCheckGesture(this)) {
				LockPatternService.setFromCheckGesture(
						Activity_CheckoutGestureLock.this, false);
				finish();
				ZCApplication.getInstance().deleteActivity(
						Activity_CheckoutGestureLock.this);
			} else {
				exit();
			}
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			// Intent intent = new Intent(Intent.ACTION_MAIN);
			// intent.addCategory(Intent.CATEGORY_HOME);
			// startActivity(intent);
			// System.exit(0);
			// finish();
			getApplicationContext().sendBroadcast(new Intent("finish"));
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			isExit = false;
		};
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		app.deleteActivity(this);
	}

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

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}
}
