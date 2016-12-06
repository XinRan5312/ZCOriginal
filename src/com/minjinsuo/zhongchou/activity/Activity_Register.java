package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetAgreementContentResponse;
import net.zkbc.p2p.fep.message.protocol.GetAgreementContentResponse.ElementAgreemenList;
import net.zkbc.p2p.fep.message.protocol.GetVfcodeForRegisterRequest;
import net.zkbc.p2p.fep.message.protocol.GetVfcodeForRegisterResponse;
import net.zkbc.p2p.fep.message.protocol.RegisterRequest;
import net.zkbc.p2p.fep.message.protocol.RegisterResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.Service_Login;
import com.minjinsuo.zhongchou.service.Service_Login.UserCallBack;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AppSetUp;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.EncryptionAndDecryptionUtil;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.widget.TimeButton;
import com.minjinsuo.zhongchou.widget.TimeButton.onBtnTimingClickListener;

public class Activity_Register extends Activity_Base {
	/** 控件声明开始 **/
	@ViewInject(R.id.btn_vfcode)
	// 时间倒计时控件
	private TimeButton btn_vfcode;
	@ViewInject(R.id.et_phone)
	// 手机号码输入框
	private EditText et_phone;
	@ViewInject(R.id.et_vfcode)
	// 手机验证码输入框
	private EditText et_vfcode;
	@ViewInject(R.id.et_pass)
	// 密码输入框
	private EditText et_pass;
	@ViewInject(R.id.et_pass_confirm)
	// 密码确认输入框
	private EditText et_pass_confirm;
	@ViewInject(R.id.check_agreement)
	// 服务协议的勾选框
	private CheckBox check_agreement;
	@ViewInject(R.id.tv_protocol_one)
	// 服务协议
	private TextView tv_protocol_one;
	@ViewInject(R.id.tv_protocol_two)
	// 隐私条款
	private TextView tv_protocol_two;
	@ViewInject(R.id.tv_and)
	private TextView tv_and;
	@ViewInject(R.id.btn_next_register)
	// 立即注册
	private Button btn_next_register;
	@ViewInject(R.id.sign_back)
	// 立即登录
	private TextView sign_back;

	/** 控件声明结束 **/
	/** 本类中所使用到的变量声明开始 **/
	// private String vfVode = "";

	/** 本类中所使用到的变量声明结束 **/
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_register);
		x.view().inject(this);
		btn_vfcode.onCreate(arg0);

		initView();
		initData();

		getAgreement(true);
	}

	@Override
	protected void initView() {
		btn_vfcode.setTextAfter("秒后重发").setTextBefore("获取验证码")
				.setLenght(AppSetUp.VFCODETIME);
		setTitleText("注册");
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

	@Event({ R.id.btn_vfcode, R.id.btn_next_register, R.id.sign_back,
			R.id.tv_protocol_one, R.id.tv_protocol_two })
	private void OnClick(View view) {
		switch (view.getId()) {
		case R.id.btn_vfcode:
			if (StringUtils.isEmpty(et_phone.getText().toString())) {
				ToastUtil.showToast(getContext(), "请输入手机号");
				return;
			} else if (et_phone.getText().toString().length() != 11
					|| !StringUtils.isPhoneNumDetail(et_phone.getText()
							.toString().trim())) {
				ToastUtil.showToast(getContext(), "请输入正确的手机号");
				return;
			} else {
				btn_vfcode
						.addOnBtnTimingListener(new onBtnTimingClickListener() {
							@Override
							public void onButtonTimingClick() {
								getVFCode(et_phone.getText().toString());
							}
						});
			}
			break;
		case R.id.btn_next_register:
			String password = et_pass.getText().toString();
			String phoneNub = et_phone.getText().toString().trim();
			if (StringUtils.isEmpty(phoneNub)) {
				ToastUtil.showToast(getContext(), "手机号不能为空");
			} else if (!StringUtils.isPhoneNumDetail(phoneNub)) {
				ToastUtil.showToast(getContext(), "手机号格式有误");
			} else if (StringUtils
					.isEmpty(et_phone.getText().toString().trim())) {
				ToastUtil.showToast(getContext(), "验证码不能为空");
			} else if (StringUtils.isEmpty(password) || password.length() < 6
					|| password.length() > 16 || password.contains(" ")) {
				ToastUtil.showToast(getContext(), "请输入6-16位数字、字母等字符的组合");
			} else if (StringUtils
					.isEmpty(et_pass_confirm.getText().toString())) {
				ToastUtil.showToast(getContext(), "两次密码必须一致");
			} else if (!password.trim().equals(
					et_pass_confirm.getText().toString().trim())) {
				ToastUtil.showToast(getContext(), "两次密码必须一致");
			} else if (!check_agreement.isChecked()) {
				ToastUtil.showToast(getContext(), "请选中我们的协议");
			} else {
				try {
					password = EncryptionAndDecryptionUtil.encode(password);
				} catch (Exception e) {
					e.printStackTrace();
					LogUtil.d("password encode Error:" + e.getMessage());
				}
				goRegister(et_phone.getText().toString().trim(), password,
						et_vfcode.getText().toString().trim());// 校验全部通过，进行注册
			}
			break;
		case R.id.sign_back:
			Activity_Register.this.finish();// 已有账户，立即登录
			break;
		case R.id.tv_protocol_one:
			if (list_protocal != null && list_protocal.size() > 0
					&& !StringUtils.isEmpty(list_protocal.get(0).getContent())) {
				Intent intent = new Intent(Activity_Register.this,
						Activity_CommonRead.class);
				intent.putExtra(Activity_CommonRead.CONTENT,
						list_protocal.get(0).getContent());
				intent.putExtra(Activity_CommonRead.TITLE, list_protocal.get(0)
						.getTitle());
				startActivity(intent);
			} else {
				ToastUtil.showToast(Activity_Register.this, "协议为空");
			}
			break;
		case R.id.tv_protocol_two:
			if (list_protocal != null && list_protocal.size() > 1
					&& !StringUtils.isEmpty(list_protocal.get(1).getContent())) {
				Intent intent = new Intent(Activity_Register.this,
						Activity_CommonRead.class);
				intent.putExtra(Activity_CommonRead.CONTENT,
						list_protocal.get(1).getContent());
				intent.putExtra(Activity_CommonRead.TITLE, list_protocal.get(1)
						.getTitle());
				startActivity(intent);
			} else {
				ToastUtil.showToast(Activity_Register.this, "协议为空");
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		btn_vfcode.onDestroy();
	}

	/**
	 * @Description: 获取验证码
	 * @author zichen
	 */
	private void getVFCode(String phonenumber) {
		// 做获取验证码的逻辑操作
		GetVfcodeForRegisterRequest request = new GetVfcodeForRegisterRequest();
		request.setPhonenumber(phonenumber);
		NetWorkRequestManager.sendRequestPost(getContext(), true, request,
				GetVfcodeForRegisterResponse.class, this);
	}

	/**
	 * @Description: 请求注册
	 */
	private void goRegister(String phonenub, String password, String vfcode) {
		RegisterRequest request = new RegisterRequest();
		request.setPhonenumber(phonenub);
		request.setPassword(password);
		request.setVfcode(vfcode);
		NetWorkRequestManager.sendRequestPost(getContext(), true, request,
				RegisterResponse.class, this);
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub
		switch (response.getMessageId()) {
		case "getVfcodeForRegister":
			GetVfcodeForRegisterResponse getVfcode = (GetVfcodeForRegisterResponse) response;
			if (!StringUtils.isEmpty(getVfcode.getVfcode())
					&& !getVfcode.getVfcode().equals("0")) {
				btn_vfcode.start();// 如果服务器返回验证码成功，启动倒计时控件
				ZCApplication.timeBtn_map.put("vfVode", getVfcode.getVfcode());// 将验证码保存在ZCApplication中保存倒计时时间的map中
			} else {
				LogUtil.d("getVfcode error Vfcode is NUll");
			}
			break;
		case "register":
			ToastUtil.showToast(getContext(), "注册成功");
			String pasd = et_pass.getText().toString();
			try {
				pasd = EncryptionAndDecryptionUtil.encode(pasd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Service_Login.goLogin(getContext(), et_phone.getText().toString()
					.trim(), pasd, false, true, new UserCallBack() {
				@Override
				public void onSuccess(ResponseSupport response) {
					// 登录成功跳转
					setResult(RESULT_OK);// 注册并登录成功
					Activity_Register.this.finish();
				}

				@Override
				public void onFailure(ResponseSupport response) {
					if (null != response) {
						ToastUtil.showToast(getContext(),
								response.getStatusMessage());
					}
				}

				@Override
				public void failure() {
					// TODO Auto-generated method stub
				}
			});
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

	/**
	 * 获取协议
	 * 
	 * @param isShow
	 */
	private List<ElementAgreemenList> list_protocal = new ArrayList<ElementAgreemenList>();

	public void getAgreement(boolean isShow) {
		Service_Login.getAgreementContent(this, "9", isShow,
				new UserCallBack() {

					@Override
					public void onSuccess(ResponseSupport sucResponse) {
						DialogUtils.dismisLoading();
						GetAgreementContentResponse model = (GetAgreementContentResponse) sucResponse;
						if (model.getAgreemenList() != null
								&& model.getAgreemenList().size() > 0) {
							list_protocal = model.getAgreemenList();
							if (list_protocal.size() > 1) {
								tv_and.setVisibility(View.VISIBLE);
								tv_protocol_two.setVisibility(View.VISIBLE);

								tv_protocol_one.setText(list_protocal.get(0)
										.getTitle());
								tv_protocol_two.setText(list_protocal.get(1)
										.getTitle());
							} else {
								tv_and.setVisibility(View.GONE);
								tv_protocol_two.setVisibility(View.GONE);

								tv_protocol_one.setText(list_protocal.get(0)
										.getTitle());
							}

						}

					}

					@Override
					public void onFailure(ResponseSupport failResponse) {
						DialogUtils.dismisLoading();
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
					}
				});
	}
}
