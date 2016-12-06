package com.minjinsuo.zhongchou.activity;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.service.Service_Login;
import com.minjinsuo.zhongchou.service.Service_Login.UserCallBack;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.EncryptionAndDecryptionUtil;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

public class Activity_Login extends Activity_Base {

	// 登录前控件初始化
	@ViewInject(R.id.et_username)
	private EditText et_username;
	@ViewInject(R.id.et_pass)
	private EditText et_pass;
	// 登录
	@ViewInject(R.id.btn_login)
	private Button btn_login;
	// 注册
	@ViewInject(R.id.btn_register)
	private TextView btn_register;
	@ViewInject(R.id.ivEye)
	private ImageView ivEye;
	// 忘记密码
	@ViewInject(R.id.tv_forgetpwd_login)
	private TextView tv_forgetpwd;

	private String nameStr, passStr;

	/* 从验证手势密码传来值，忘记手势密码时需要清除之前的密码 */
	private boolean isClear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_login);
		x.view().inject(this);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		setTitleText("登录");
		// 返回按钮
		ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_back.setVisibility(View.VISIBLE);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initListener() {

	}

	/**
	 * 用于手势密码验证页回调用
	 */
	public void success() {
		setResult(RESULT_OK);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		et_pass.setText("");
		et_username.setText("");
	}

	@Event({ R.id.btn_login, R.id.btn_register, R.id.tv_forgetpwd_login,
			R.id.ivEye })
	private void OnClick(View view) {
		switch (view.getId()) {
		case R.id.btn_login:
			String password = et_pass.getText().toString();
			if (StringUtils.isEmpty(et_username.getText().toString())) {
				ToastUtil.showToast(Activity_Login.this, "请输入手机号");
				return;
			}
			if (StringUtils.isEmpty(password)) {
				ToastUtil.showToast(Activity_Login.this, "请输入密码");
				return;
			}
			try {
				password = EncryptionAndDecryptionUtil.encode(password);
			} catch (Exception e) {
				LogUtil.d("password encode Error：" + e.getMessage());
				e.printStackTrace();
			}
			Service_Login.goLogin(Activity_Login.this, et_username.getText()
					.toString(), password, true, true, userCallback);
			break;
		case R.id.btn_register:
			startActivityForResult(new Intent(Activity_Login.this,
					Activity_Register.class), CommonUtils.CODE.GOREGISTER);
			break;
		case R.id.tv_forgetpwd_login:
			startActivity(new Intent(getContext(), Activity_findPassWord.class));
			break;
		case R.id.ivEye:
			if (ivEye.getTag() == null || ((Integer) ivEye.getTag()) == 0) {
				et_pass.setTransformationMethod(HideReturnsTransformationMethod
						.getInstance());
				ivEye.setImageResource(R.drawable.eye_y);
				ivEye.setTag(1);
				if (!StringUtils.isEmpty(et_pass.getText().toString())) {
					et_pass.setSelection(et_pass.getText().toString().length());// 光标置后
				} else {
					et_pass.setSelection(0);// 光标置后
				}
			} else {
				et_pass.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
				ivEye.setImageResource(R.drawable.eye_n);
				ivEye.setTag(0);
				if (!StringUtils.isEmpty(et_pass.getText().toString())) {
					et_pass.setSelection(et_pass.getText().toString().length());// 光标置后
				} else {
					et_pass.setSelection(0);// 光标置后
				}
			}
			break;
		default:
			break;
		}
	}

	UserCallBack userCallback = new UserCallBack() {

		@Override
		public void onSuccess(ResponseSupport sucResponse) {
			// 登录成功如果没有手势密码设置手势
			if (StringUtils.isEmpty(LockPatternService
					.getLockPattern(Activity_Login.this))) {
				Intent intent = new Intent(Activity_Login.this,
						Activity_SetGestureLock.class);
				intent.putExtra(Activity_SetGestureLock.FIRSTSET, true);
				startActivityForResult(intent, 001);
			} else {
				ToastUtil.showToast(Activity_Login.this, "登录成功");
				setResult(RESULT_OK);// 主要传递给Activity_CheckoutGestureLock.class
				Activity_Login.this.finish();
			}

			DialogUtils.dismisLoading();
		}

		@Override
		public void onFailure(ResponseSupport failResponse) {
			DialogUtils.dismisLoading();
			if (null != failResponse) {
				ToastUtil.showToast(getContext(),
						failResponse.getStatusMessage());
			}
		}

		@Override
		public void failure() {
			DialogUtils.dismisLoading();
		}
	};

	@Override
	public void onSuccess(ResponseSupport response) {

	}

	@Override
	public void failure() {

	}

	@Override
	public void onFailure(ResponseSupport response) {

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (RESULT_OK == arg1) {
			switch (arg0) {
			case CommonUtils.CODE.GOREGISTER:// 注册并登录成功后的逻辑处理
				// 登录成功如果没有手势密码设置手势
				if (StringUtils.isEmpty(LockPatternService
						.getLockPattern(Activity_Login.this))) {
					Intent intent = new Intent(Activity_Login.this,
							Activity_SetGestureLock.class);
					intent.putExtra(Activity_SetGestureLock.FIRSTSET, true);
					startActivityForResult(intent, 001);
				} else {
					setResult(RESULT_OK);// 主要传递给Activity_CheckoutGestureLock.class
					Activity_Login.this.finish();
				}
				break;
			case 001:// 手势密码设置
				LockPatternService.isOpenLockPattern(Activity_Login.this, true);
				LogUtil.i("手势设置成功，开关:"
						+ LockPatternService
								.getIsOpenLockPatten(Activity_Login.this));
				// 登录成功跳转
				setResult(RESULT_OK);
				Activity_Login.this.finish();
				break;
			default:
				break;
			}
		} else {
			if (arg0 == 001 && arg1 == RESULT_CANCELED) {// 选择提过手势密码设置
				LockPatternService
						.isOpenLockPattern(Activity_Login.this, false);
				setResult(RESULT_OK);
				Activity_Login.this.finish();
			}
		}

	}
}
