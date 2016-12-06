package com.minjinsuo.zhongchou.activity;

import org.xutils.x;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import net.zkbc.p2p.fep.message.protocol.GetVfcodeForResetPasswordRequest;
import net.zkbc.p2p.fep.message.protocol.GetVfcodeForResetPasswordResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

public class Activity_findPassWord extends Activity_Base implements TextWatcher {
	private String TAG = Activity_findPassWord.class.getSimpleName();
	private TextView bt_next;
	private TextView et_tel_num;
	private TextView tv_findpwd_tip;
	private TextView tv_toptip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_find_password);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleText("找回密码");
		setTitleBack();
		et_tel_num = (TextView) findViewById(R.id.et_tel_num);
		bt_next = (TextView) findViewById(R.id.bt_next_findpsd);
		// bt_next.setBackgroundResource(R.drawable.dengdai);
		bt_next.setBackgroundResource(R.drawable.btn_red_disable);
		bt_next.setEnabled(false);
		et_tel_num.addTextChangedListener(this);

		tv_findpwd_tip = (TextView) findViewById(R.id.tv_findpwd_tip);
		tv_toptip = (TextView) findViewById(R.id.tv_toptip);
		// 从配置文件中读取网站名称和网站访问地址写到tv_findpwd_tip中
		initTvTipInfo();
	}

	private void initTvTipInfo() {
		new Thread() {
			@Override
			public void run() {
				final String website = CommonUtils.getValue("website");
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tv_findpwd_tip.setText("若您尚未进行手机认证，请访问" + website
								+ "重置您的登录密码");
						tv_toptip.setText("输入您在该平台认证的手机号码");
					}
				});
			};
		}.start();
	}

	@Override
	protected void initListener() {
		bt_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (StringUtils.isEmpty(et_tel_num.getText().toString())) {
					ToastUtil.showToast(Activity_findPassWord.this, "手机号不能为空");
					return;
				} else if (!StringUtils.isPhoneNumDetail(et_tel_num.getText()
						.toString())) {
					ToastUtil.showToast(Activity_findPassWord.this, "手机号格式不正确");
					return;
				}
				startRequest();
			}
		});
	}

	protected void startRequest() {
		String tel_num = et_tel_num.getText().toString();
		GetVfcodeForResetPasswordRequest request = new GetVfcodeForResetPasswordRequest();
		request.setPhonenumber(tel_num);
		NetWorkRequestManager.sendRequestPost(getContext(), true, request,
				GetVfcodeForResetPasswordResponse.class, this);

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// bt_next.setBackgroundResource(R.drawable.dengdai);
		bt_next.setBackgroundResource(R.drawable.btn_red_disable);
		bt_next.setEnabled(false);
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
		if (!StringUtils.isEmpty(et_tel_num.getText().toString())) {
			// bt_next.setBackgroundResource(R.drawable.btn_bottom_selector);
			bt_next.setBackgroundResource(R.drawable.home_btn_red_selector);
			bt_next.setEnabled(true);
		}
	}

	private void Enabledfalse() {
		if (StringUtils.isEmpty(et_tel_num.getText().toString())) {
			// bt_next.setBackgroundResource(R.drawable.dengdai);
			bt_next.setBackgroundResource(R.drawable.btn_red_disable);
			ToastUtil.showToast(this, "请输入手机号");
			bt_next.setEnabled(false);
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub
		switch (response.getMessageId()) {
		case "getVfcodeForResetPassword":
			if (0 == response.getStatusCode()) {
				String statusMsg = response.getStatusMessage();
				Intent intent = new Intent(getApplicationContext(),
						Activity_findPassWordCertify.class);
				intent.putExtra("tel_num", et_tel_num.getText().toString());
				intent.putExtra("vfcode", statusMsg);
				startActivity(intent);
				finish();
			} else {
				ToastUtil.showToast(getContext(), "获取验证码失败,请稍后重试");
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

	}
}
