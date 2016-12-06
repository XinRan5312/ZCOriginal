package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.CheckUserRightRequest;
import net.zkbc.p2p.fep.message.protocol.CheckUserRightResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 企业用户认证状态
 * 
 * @author zp
 *
 *         2016年10月12日
 */
public class Activity_InvestCerciticyComFirst extends Activity_Base {
	@ViewInject(R.id.btn_certicify_top)
	private Button btn_certicify_top;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_invest_cirtify_fcom_irst);
		x.view().inject(this);
		initView();
		initData();
		initListener();

		checkUserRight();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ZCApplication.getInstance().isNeedRefresh) {
			ZCApplication.getInstance().isNeedRefresh = false;
			// 重新检查状态
			checkUserRight();
		}
	}

	@Override
	protected void initView() {
		setTitleText("企业认证");
		setTitleBack();
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {

	}

	@Event({ R.id.btn_certicify_top })
	private void OnClick(View view) {
		if (!ZCApplication.getInstance().isLogin()) {
			new AlertDialog(Activity_InvestCerciticyComFirst.this).builder()
					.setMsg("您尚未登录，请登录后操作")
					.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(View v) {
							startActivity(new Intent(
									Activity_InvestCerciticyComFirst.this,
									Activity_Login.class));
						}
					}).setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(View v) {

						}
					}).show();
			return;
		}

		switch (view.getId()) {
		case R.id.btn_certicify_top:
			startActivity(new Intent(Activity_InvestCerciticyComFirst.this,
					Activity_InvestCerticifyCompany.class));
			break;

		default:
			break;
		}

	}

	/**
	 * 1、检测用户当前角色状态
	 */
	public void checkUserRight() {
		CheckUserRightRequest request = new CheckUserRightRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				CheckUserRightResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "checkUserRight":
			CheckUserRightResponse model = (CheckUserRightResponse) response;
			if (model != null && !StringUtils.isEmpty(model.getIsFollower())) {
				// isFollower 是否普通投资资质认证 否-0 是-1 禁用-2 申请中-3
				// isUserLeader 是否平台领投人 否-0 是-1 禁用-2 申请中-3

				if (model.getIsFollower().equals("0")) {// 是否普通投资资质认证 否-0 是-1
														// 禁用-2 申请中-3
					btn_certicify_top.setEnabled(true);
				} else if (model.getIsFollower().equals("1")) {// 已通过认证第一步，可以进行认证第二部
					btn_certicify_top.setText("认证成功");
					btn_certicify_top.setEnabled(false);
				} else if (model.getIsFollower().equals("2")) {
					btn_certicify_top.setText("禁用");
					btn_certicify_top
							.setBackgroundResource(R.drawable.shap_btn_gray);
					btn_certicify_top.setEnabled(false);
				} else if (model.getIsFollower().equals("3")) {
					btn_certicify_top.setText("申请中…");
					btn_certicify_top
							.setBackgroundResource(R.drawable.shap_btn_gray);
					btn_certicify_top.setEnabled(false);
				}
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void failure() {
	}

	@Override
	public void onFailure(ResponseSupport response) {
	}
}
