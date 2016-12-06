package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 密码管理（登录密码、手势密码）
 * 
 * @author zp
 *
 *         2016年9月11日
 */
public class Activity_Pwd_Manage extends Activity_Base {
	@ViewInject(R.id.tv_reset_loginPwd)
	private TextView tv_reset_loginPwd;
	@ViewInject(R.id.tv_gesture_set)
	private TextView tv_gesture_set;
	@ViewInject(R.id.clock_onoff)
	private CheckBox clock_onoff;
	private boolean isCheck;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_pwd_manage);

		x.view().inject(this);
		ZCApplication.getInstance().addActivity(Activity_Pwd_Manage.this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void onResume() {
		super.onResume();

		clock_onoff.setChecked(LockPatternService.getIsOpenLockPatten(this));
		if (LockPatternService.getIsOpenLockPatten(this)) {
			tv_gesture_set.setVisibility(View.VISIBLE);
		} else {
			tv_gesture_set.setVisibility(View.GONE);
		}
	}

	@Override
	protected void initView() {
		setTitleText("密码管理");
		setTitleBack();

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {
		clock_onoff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 打开开关要校验是否设置过手势
				if (!LockPatternService
						.getIsOpenLockPatten(Activity_Pwd_Manage.this)) {
					if (StringUtils.isEmpty(LockPatternService
							.getLockPattern(Activity_Pwd_Manage.this))) {
						LockPatternService.setFromReSetGesture(
								Activity_Pwd_Manage.this, true);
						startActivityForResult(new Intent(
								Activity_Pwd_Manage.this,
								Activity_SetGestureLock.class), 001);
					} else {
						LockPatternService.setFromCheckGesture(
								Activity_Pwd_Manage.this, true);
						startActivityForResult(new Intent(
								Activity_Pwd_Manage.this,
								Activity_CheckoutGestureLock.class), 004);
					}

				} else {
					// 关闭开关
					LockPatternService.setFromCheckGesture(
							Activity_Pwd_Manage.this, true);
					startActivityForResult(new Intent(Activity_Pwd_Manage.this,
							Activity_CheckoutGestureLock.class), 003);
				}
			}
		});

	}

	@Override
	public void onSuccess(ResponseSupport response) {

	}

	@Override
	public void failure() {

	}

	@Override
	public void onFailure(ResponseSupport response) {

	}

	@Event({ R.id.tv_reset_loginPwd, R.id.tv_gesture_set })
	private void OnClick(View view) {
		switch (view.getId()) {
		case R.id.tv_reset_loginPwd:
			startActivity(new Intent(Activity_Pwd_Manage.this,
					Activity_ResetPwd.class));
			break;
		case R.id.tv_gesture_set:
			LockPatternService.setFromCheckGesture(Activity_Pwd_Manage.this,
					true);
			startActivityForResult(new Intent(Activity_Pwd_Manage.this,
					Activity_CheckoutGestureLock.class), 002);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 001) {// 初次设置手势成功
			if (arg1 == RESULT_OK) {// 设置手势成功才将开关打开,否则关闭
				LockPatternService.isOpenLockPattern(Activity_Pwd_Manage.this,
						true);
			} else {
				LockPatternService.isOpenLockPattern(Activity_Pwd_Manage.this,
						false);
			}
		}

		if (arg0 == 002) {// 修改手势前先验证手势密码
			if (arg1 == Activity_CheckoutGestureLock.SUCCESS) {
				LockPatternService.setFromCheckGesture(
						Activity_Pwd_Manage.this, false);
				Intent intent = new Intent(Activity_Pwd_Manage.this,
						Activity_SetGestureLock.class);
				LockPatternService.setFromReSetGesture(
						Activity_Pwd_Manage.this, true);
				startActivity(intent);
			} else {
				LogUtil.i("手势验证失败，应该退出登录  或 点击了返回键~~");
			}
		}

		if (arg0 == 003) {// 关闭手势前校验成功
			if (arg1 == Activity_CheckoutGestureLock.SUCCESS) {
				LockPatternService.setFromCheckGesture(
						Activity_Pwd_Manage.this, false);

				LockPatternService.isOpenLockPattern(Activity_Pwd_Manage.this,
						false);
			} else {
				LogUtil.i("手势验证失败，应该退出登录  或 点击了返回键~~");
				LockPatternService.isOpenLockPattern(Activity_Pwd_Manage.this,
						true);
			}
		}
		if (arg0 == 004) {// 打开手势开关前校验成功
			if (arg1 == Activity_CheckoutGestureLock.SUCCESS) {
				LockPatternService.setFromCheckGesture(
						Activity_Pwd_Manage.this, false);
				LockPatternService.isOpenLockPattern(Activity_Pwd_Manage.this,
						true);
			} else {
				LogUtil.i("手势验证失败，应该退出登录  或 点击了返回键~~");
				LockPatternService.isOpenLockPattern(Activity_Pwd_Manage.this,
						false);
			}
		}

	}
}
