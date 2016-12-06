package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.GetMyAccountResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.IDCardUtil;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 
 * 身份认证，用户名+身份证号
 */
public class OpenChargeFirstActivity extends Activity_Base {

	TextView tvRealName;
	TextView tvIdCard;
	Button btnNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_open_charge_first);
		setTitleBack();
		setTitleText("身份认证");
		initView();
		initListener();
	}

	@Override
	protected void initView() {
		tvRealName = (TextView) findViewById(R.id.et_realName);
		tvIdCard = (TextView) findViewById(R.id.et_IdCard);
		btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String realName = tvRealName.getText().toString().trim();
				if (StringUtils.isEmpty(realName)) {
					DialogUtils.createOneBtnDiolog(
							OpenChargeFirstActivity.this, "真实姓名不能为空");
					return;
				}
				if (!StringUtils.isLegalName(realName)) {
					DialogUtils.createOneBtnDiolog(
							OpenChargeFirstActivity.this, "真实姓名格式有误");
					return;
				}
				String idCard = tvIdCard.getText().toString().trim();
				if (StringUtils.isEmpty(idCard)) {
					DialogUtils.createOneBtnDiolog(
							OpenChargeFirstActivity.this, "身份证号不能为空");
					return;
				}
				if (!IDCardUtil.verify(idCard)) {
					ToastUtil
							.showToast(OpenChargeFirstActivity.this, "无效的身份证号");
					return;
				}

				/* 把小写的x自动转变为X再传递参数 */
				if (idCard.contains("x")) {
					idCard = idCard.replace("x", "X");
				}

				GetMyAccountResponse p2pUser = ZCApplication.getInstance()
						.getUserInfo();
				String mobile = p2pUser.getPhonenumber();
				if (StringUtils.isEmpty(mobile)) {
					Toast.makeText(getApplicationContext(), "您尚未绑定手机号",
							Toast.LENGTH_LONG).show();
					return;
				} else {
					String sessionId = p2pUser.getSessionId();
					String url = Service_ThirdPay.OPENCHARGE + "sessionId="
							+ sessionId + "&realName=" + realName + "&idNo="
							+ idCard;
					Intent intent = new Intent();
					intent.setClass(getContext(), Activity_ThirdWeb.class);
					intent.putExtra(Activity_ThirdWeb.URL, url);
					intent.putExtra(Activity_ThirdWeb.TITLENAME, "开通支付");
					startActivityForResult(intent, 3);
				}
			}
		});
	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 3) {
			switch (resultCode) {
			case RESULT_OK:
				Toast.makeText(getContext(), "实名认证成功", Toast.LENGTH_SHORT)
						.show();
				ZCApplication.getInstance().getUserInfo().setIsrealname("1");
				ZCApplication.getInstance().getUserInfo()
						.setIdcardnumber(tvIdCard.getText().toString().trim());
				ZCApplication.getInstance().getUserInfo()
						.setRealname(tvRealName.getText().toString().trim());
				ZCApplication.getInstance().isNeedRefresh = true;
				setResult(RESULT_OK);
				finish();
				break;
			case 200:
				break;
			}
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
	protected void initData() {
		// TODO Auto-generated method stub

	}
}
