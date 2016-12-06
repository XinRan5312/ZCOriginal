package com.minjinsuo.zhongchou.activity;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.utils.AppSetUp;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.EncryptionAndDecryptionUtil;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.widget.TimeButton;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import net.zkbc.p2p.fep.message.protocol.GetVfcodeForResetPasswordRequest;
import net.zkbc.p2p.fep.message.protocol.GetVfcodeForResetPasswordResponse;
import net.zkbc.p2p.fep.message.protocol.ResetPasswordRequest;
import net.zkbc.p2p.fep.message.protocol.ResetPasswordResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

/**
 * 忘记密码 确认信息界面 填写验证码 新密码
 * 
 * @author Jzc
 *
 */
public class Activity_findPassWordCertify extends Activity_Base implements TextWatcher {
	private String TAG = Activity_findPassWordCertify.class.getSimpleName();
	@ViewInject(R.id.et_certify_code)
	private EditText et_certify_code;

	@ViewInject(R.id.btn_vfcode)
	private TimeButton btn_vfcode;

	@ViewInject(R.id.et_newpw)
	private EditText et_pwd;

	@ViewInject(R.id.et_newpw_confirm)
	private EditText et_pwd_config;

	@ViewInject(R.id.bt_next_certify)
	private Button bt_next;

	private String tel_num = "";

	@ViewInject(R.id.ivEye)
	private ImageView ivEye;
	@ViewInject(R.id.ivEye2)
	private ImageView ivEye2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_pwd_certify);
		x.view().inject(this);
		btn_vfcode.onCreate(savedInstanceState);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		btn_vfcode.setTextAfter(" S").setTextBefore("重新获取").setLenght(AppSetUp.VFCODETIME);
		btn_vfcode.start();
		setTitleText("验证信息");
		setTitleBack();
		bt_next = (Button) findViewById(R.id.bt_next_certify);
		et_pwd = (EditText) findViewById(R.id.et_newpw);
		et_pwd_config = (EditText) findViewById(R.id.et_newpw_confirm);
		et_pwd.setBackgroundResource(0);
		et_pwd_config.setBackgroundResource(0);
	}

	@Override
	protected void initListener() {
	}

	/**
	 * 再次请求验证码
	 */
	protected void startRequestForCode() {
		GetVfcodeForResetPasswordRequest request = new GetVfcodeForResetPasswordRequest();
		request.setPhonenumber(tel_num);
		NetWorkRequestManager.sendRequestPost(getContext(), true, request, GetVfcodeForResetPasswordResponse.class,
				this);
	}

	/**
	 * 请求修改密码
	 */
	protected void changePwd() {
		String password = "";
		try {
			password = EncryptionAndDecryptionUtil.encode(et_pwd.getText().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResetPasswordRequest request = new ResetPasswordRequest();
		request.setVfcode(et_certify_code.getText().toString());
		request.setPhonenumber(tel_num);
		request.setPassword(password);
		NetWorkRequestManager.sendRequestPost(getContext(), true, request, ResetPasswordResponse.class, this);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		// bt_next.setBackgroundResource(R.drawable.dengdai);
		// bt_next.setEnabled(false);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		Enabledfalse();
		EnabledTrue();
	}

	@Override
	public void afterTextChanged(Editable s) {
		EnabledTrue();
	}

	private void EnabledTrue() {
		if (!StringUtils.isEmpty(et_certify_code.getText().toString())
				&& !StringUtils.isEmpty(et_pwd.getText().toString())
				&& !StringUtils.isEmpty(et_pwd_config.getText().toString())) {
			bt_next.setBackgroundResource(R.drawable.home_btn_red_selector);
			bt_next.setEnabled(true);
		}
	}

	private void Enabledfalse() {
		if (StringUtils.isEmpty(et_certify_code.getText().toString())
				|| StringUtils.isEmpty(et_pwd.getText().toString())
				|| StringUtils.isEmpty(et_pwd_config.getText().toString())) {
			bt_next.setBackgroundResource(R.drawable.home_btn_red_selector);
			bt_next.setEnabled(false);
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		tel_num = getIntent().getExtras().getString("tel_num");
	}

	@Event({ R.id.ivEye, R.id.ivEye2, R.id.bt_next_certify, R.id.btn_vfcode })
	private void OnClick(View view) {
		switch (view.getId()) {
		case R.id.ivEye:
			if (ivEye.getTag() == null || ((Integer) ivEye.getTag()) == 0) {
				et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				ivEye.setImageResource(R.drawable.eye_y);
				ivEye.setTag(1);
				if (!StringUtils.isEmpty(et_pwd.getText().toString())) {
					et_pwd.setSelection(et_pwd.getText().toString().length());// 光标置后
				} else {
					et_pwd.setSelection(0);// 光标置后
				}
			} else {
				et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
				ivEye.setImageResource(R.drawable.eye_n);
				ivEye.setTag(0);
				if (!StringUtils.isEmpty(et_pwd.getText().toString())) {
					et_pwd.setSelection(et_pwd.getText().toString().length());// 光标置后
				} else {
					et_pwd.setSelection(0);// 光标置后
				}
			}
			break;
		case R.id.ivEye2:
			if (ivEye2.getTag() == null || ((Integer) ivEye2.getTag()) == 0) {
				et_pwd_config.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				ivEye2.setImageResource(R.drawable.eye_y);
				ivEye2.setTag(1);
				if (!StringUtils.isEmpty(et_pwd_config.getText().toString())) {
					et_pwd_config.setSelection(et_pwd_config.getText().toString().length());// 光标置后
				} else {
					et_pwd_config.setSelection(0);
				}
			} else {
				et_pwd_config.setTransformationMethod(PasswordTransformationMethod.getInstance());
				ivEye2.setImageResource(R.drawable.eye_n);
				ivEye2.setTag(0);
				if (!StringUtils.isEmpty(et_pwd_config.getText().toString())) {
					et_pwd_config.setSelection(et_pwd_config.getText().toString().length());// 光标置后
				} else {
					et_pwd_config.setSelection(0);
				}
			}
			break;
		case R.id.bt_next_certify:

			if (StringUtils.isEmpty(et_certify_code.getText().toString())) {

				DialogUtils.createOneBtnDiolog(this, "验证码不能为空");
				return;
			}
			String pwdStr = et_pwd.getText().toString();
			if (StringUtils.isEmpty(pwdStr)) {
				ToastUtil.showToast(getContext(), "请输入密码");
				return;
			}
			if (StringUtils.isEmpty(et_pwd_config.getText().toString())) {
				ToastUtil.showToast(getContext(), "请输入确认密码");
				return;
			}
			if (pwdStr.length() < 6 || pwdStr.length() > 16 || !StringUtils.isENG_NUM_MUST(pwdStr)) {
				ToastUtil.showToast(getContext(), "请输入6-16位数字、字母等字符的组合");
				return;
			}

			if (!et_pwd.getText().toString().equals(et_pwd_config.getText().toString())) {

				ToastUtil.showToast(getContext(), "新密码和确认密码必须一致");
				return;
			}
			changePwd();
			// Intent intent = new Intent(getApplicationContext(),
			// ResetPasswordActivity.class);
			// startActivity(intent);

			break;
		case R.id.btn_vfcode:
			startRequestForCode();
			break;
		default:
			break;
		}
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub
		switch (response.getMessageId()) {
		case "getVfcodeForResetPassword":
			if (0 == response.getStatusCode()) {
				btn_vfcode.start();
				ToastUtil.showToast(getContext(), "验证码发送成功");
			} else {
				ToastUtil.showToast(getContext(), "验证码发送失败,请稍后再试");
			}
			break;
		case "resetPassword":
			if (0 == response.getStatusCode()) {
				ToastUtil.showToast(getContext(), "重置密码成功");
				finish();
			} else {
				ToastUtil.showToast(getContext(), "修改密码失败");
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void failure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub
		if (null != response) {
			ToastUtil.showToast(getContext(), response.getStatusMessage());
		}
	}

}
