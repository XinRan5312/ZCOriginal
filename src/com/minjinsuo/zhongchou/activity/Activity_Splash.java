package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.common.util.LogUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.igexin.sdk.PushManager;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;

/**
 * 启动页
 */
public class Activity_Splash extends Activity_Base {
	private boolean mIsFirstStart;
	private static final int REQUEST_GESTURE = 001;// 手势密码
	private ZCApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		setSwipeBackEnable(false);

		app = (ZCApplication) this.getApplicationContext();
		app.addActivity(this);

		// 推送初始化
		PushManager.getInstance().initialize(this.getApplicationContext());
		mIsFirstStart = SharedPreferUtils.isFirstStart(this);
		if (!this.isTaskRoot()) {
			System.out.println("Splash_Activity Not isTaskRoot");
			finish();
			return;
		}

		new PrepareTask().execute();
	}

	/**
	 * 准备
	 */
	private class PrepareTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (mIsFirstStart) {
				startActivity(new Intent(Activity_Splash.this,
						Activity_Navigation.class));
				finish();
			} else {
				LogUtil.i("是否登录--" + app.isLogin());

				if (ZCApplication.getInstance().isLogin()) {

					// 登录状态判断下是否开启收拾功能
					if (LockPatternService
							.getIsOpenLockPatten(Activity_Splash.this)) {
						Intent intent = new Intent(Activity_Splash.this,
								Activity_CheckoutGestureLock.class);
						startActivityForResult(intent, REQUEST_GESTURE);
					} else {// 手势开关关闭
						startActivity(new Intent(Activity_Splash.this,
								Activity_Main.class));
						finish();
					}

				} else {
					startActivity(new Intent(Activity_Splash.this,
							Activity_Main.class));
					finish();
				}

			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_GESTURE:
			if (resultCode == Activity_CheckoutGestureLock.SUCCESS
					|| resultCode == Activity_CheckoutGestureLock.FAILURE) {
				if (resultCode == Activity_CheckoutGestureLock.SUCCESS) {
					LogUtil.i("SUCCESS~~main——》finish");
					startActivity(new Intent(Activity_Splash.this,
							Activity_Main.class));
					finish();
				}
			} else {// resultCode == Activity_CheckoutGestureLock.OTHER
				LogUtil.i("other~~finish");
				finish();
			}
			break;

		default:
			finish();
			break;
		}
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
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

}
