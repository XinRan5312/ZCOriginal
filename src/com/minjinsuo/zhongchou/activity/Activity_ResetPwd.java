package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ChangePasswordRequest;
import net.zkbc.p2p.fep.message.protocol.ChangePasswordResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.EncryptionAndDecryptionUtil;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 修改登录密码
 * 
 * @author zp
 *
 *         2016年9月11日
 */
public class Activity_ResetPwd extends Activity_Base {

	private Button bt_complete;
	private EditText et_password_before;
	private EditText et_password_new;
	private EditText et_password_new2;
	private ImageView ivEye, ivEye2, ivEye3;
	private LinearLayout ivEye_ly, ivEye_ly2, ivEye_ly3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty__reset_pwd);

		setTitleText("修改登录密码");
		setTitleBack();
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		bt_complete = (Button) findViewById(R.id.bt_complete);

		et_password_before = (EditText) findViewById(R.id.et_password_before);
		et_password_new = (EditText) findViewById(R.id.et_password_new);
		et_password_new2 = (EditText) findViewById(R.id.et_password_new2);

		ivEye = (ImageView) findViewById(R.id.ivEye);
		ivEye_ly = (LinearLayout) findViewById(R.id.ivEye_ly);
		ivEye_ly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ivEye.getTag() == null || ((Integer) ivEye.getTag()) == 0) {
					et_password_before
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
					ivEye.setImageResource(R.drawable.eye_y);
					ivEye.setTag(1);
					if (!StringUtils.isEmpty(et_password_before.getText()
							.toString())) {
						et_password_before.setSelection(et_password_before
								.getText().toString().length());// 光标置后
					} else {
						et_password_before.setSelection(0);// 光标置后
					}
				} else {
					et_password_before
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
					ivEye.setImageResource(R.drawable.eye_n);
					ivEye.setTag(0);
					if (!StringUtils.isEmpty(et_password_before.getText()
							.toString())) {
						et_password_before.setSelection(et_password_before
								.getText().toString().length());// 光标置后
					} else {
						et_password_before.setSelection(0);// 光标置后
					}
				}
			}
		});
		ivEye2 = (ImageView) findViewById(R.id.ivEye2);
		ivEye_ly2 = (LinearLayout) findViewById(R.id.ivEye_ly2);
		ivEye_ly2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ivEye2.getTag() == null || ((Integer) ivEye2.getTag()) == 0) {
					et_password_new
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
					ivEye2.setImageResource(R.drawable.eye_y);
					ivEye2.setTag(1);
					if (!StringUtils.isEmpty(et_password_new.getText()
							.toString())) {
						et_password_new.setSelection(et_password_new.getText()
								.toString().length());// 光标置后
					} else {
						et_password_new.setSelection(0);
					}
				} else {
					et_password_new
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
					ivEye2.setImageResource(R.drawable.eye_n);
					ivEye2.setTag(0);
					if (!StringUtils.isEmpty(et_password_new.getText()
							.toString())) {
						et_password_new.setSelection(et_password_new.getText()
								.toString().length());// 光标置后
					} else {
						et_password_new.setSelection(0);
					}
				}
			}
		});
		ivEye3 = (ImageView) findViewById(R.id.ivEye3);
		ivEye_ly3 = (LinearLayout) findViewById(R.id.ivEye_ly3);
		ivEye_ly3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ivEye3.getTag() == null || ((Integer) ivEye3.getTag()) == 0) {
					et_password_new2
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
					ivEye3.setImageResource(R.drawable.eye_y);
					ivEye3.setTag(1);
					if (!StringUtils.isEmpty(et_password_new2.getText()
							.toString())) {
						et_password_new2.setSelection(et_password_new2
								.getText().toString().length());// 光标置后
					} else {
						et_password_new2.setSelection(0);
					}
				} else {
					et_password_new2
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
					ivEye3.setImageResource(R.drawable.eye_n);
					ivEye3.setTag(0);
					if (!StringUtils.isEmpty(et_password_new2.getText()
							.toString())) {
						et_password_new2.setSelection(et_password_new2
								.getText().toString().length());// 光标置后
					} else {
						et_password_new2.setSelection(0);
					}
				}
			}
		});

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {
		bt_complete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (StringUtils
						.isEmpty(et_password_before.getText().toString())) {
					ToastUtil.showToast(Activity_ResetPwd.this, "请输入原密码");
					return;
				}
				if (et_password_before.getText().toString().length() < 6
						|| et_password_before.getText().toString().length() > 16
						|| et_password_before.getText().toString()
								.contains(" ")) {
					ToastUtil.showToast(Activity_ResetPwd.this,
							"请输入6到16位不含空格的原密码");
					return;
				}
				if (StringUtils.isEmpty(et_password_new.getText().toString())) {
					ToastUtil.showToast(Activity_ResetPwd.this, "请输入新密码");
					return;
				}
				if (StringUtils.isEmpty(et_password_new2.getText().toString())) {
					ToastUtil.showToast(Activity_ResetPwd.this, "请输入确认密码");
					return;
				}

				if (et_password_before.getText().toString()
						.equals(et_password_new.getText().toString())) {
					ToastUtil.showToast(Activity_ResetPwd.this, "新密码和旧密码不能相同");
					return;
				}
				if (et_password_new.getText().toString().length() < 6
						|| et_password_new.getText().toString().length() > 16
						|| et_password_new2.getText().toString().length() < 6
						|| et_password_new2.getText().toString().length() > 16) {
					ToastUtil.showToast(Activity_ResetPwd.this,
							"请输入6-16位数字、字母等字符的组合");
					return;
				}
				if (!(et_password_new.getText().toString()
						.equals(et_password_new2.getText().toString()))) {
					ToastUtil.showToast(Activity_ResetPwd.this, "两次新密码输入不一致");
					return;
				}
				startRequestResetPassword();
			}
		});
	}

	/**
	 * 修改密码
	 */
	protected void startRequestResetPassword() {
		ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
		changePasswordRequest.setSessionId(ZCApplication.getInstance()
				.getUserInfo().getSessionId());
		changePasswordRequest.setLoginname(ZCApplication.getInstance()
				.getUserInfo().getLoginname());
		try {
			changePasswordRequest.setOrigpassword(EncryptionAndDecryptionUtil
					.encode(et_password_before.getText().toString()));
			changePasswordRequest.setNewpassword(EncryptionAndDecryptionUtil
					.encode(et_password_new2.getText().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		NetWorkRequestManager.sendRequestPost(this, true,
				changePasswordRequest, ChangePasswordResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {

		DialogUtils.dismisLoading();
		ToastUtil.showToast(getContext(), "密码修改成功");
		// 存储新密码
		try {
			LockPatternService.saveUserPassword(getContext(),
					EncryptionAndDecryptionUtil.encode(et_password_new2
							.getText().toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 修改成功后需要重新登录
		ZCApplication.getInstance().TAG = Activity_Main.TAB_Account;
		startActivity(new Intent(Activity_ResetPwd.this, Activity_Main.class));
		LockPatternService.LoginOut(Activity_ResetPwd.this);
		ZCApplication.getInstance().exit();
		finish();
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
	}

}
