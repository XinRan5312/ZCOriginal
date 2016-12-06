package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SendLetterRequest;
import net.zkbc.p2p.fep.message.protocol.SendLetterResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.widget.ClearableEditText;

/**
 * 发送私信
 * 
 * @author zp
 *
 *         2016年9月11日
 */
public class Activity_MsgSend extends Activity_Base {

	@ViewInject(R.id.et_sender)
	private EditText et_sender;// 收件人
	@ViewInject(R.id.et_zhuti)
	private EditText et_zhuti;
	@ViewInject(R.id.et_content)
	private ClearableEditText et_content;

	private String custId = "";// 发起人id
	private String title = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_msg_send);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleBack();
		setTitleText("写私信");
		setTitleRight("发送", new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (StringUtils.isEmpty(et_zhuti.getText().toString())) {
					CommonUtils.showToast(Activity_MsgSend.this, "请输入主题");
					return;
				}
				if (StringUtils.isEmpty(et_content.getText().toString())) {
					CommonUtils.showToast(Activity_MsgSend.this, "请输入内容");
					return;
				}

				startRequestSendMsg();
			}
		});

	}

	@Override
	protected void initData() {
		// 收件人
		if (getIntent() != null
				&& !StringUtils.isEmpty(getIntent().getStringExtra("name"))) {
			et_sender.setText(getIntent().getStringExtra("name"));

			custId = getIntent().getStringExtra("custId");
			LogUtil.i("发件人id==" + custId);

			if (!StringUtils.isEmpty(getIntent().getStringExtra("title"))) {
				title = getIntent().getStringExtra("title");
				et_zhuti.setText("回复：" + title);
			}
		}
	}

	@Override
	protected void initListener() {

	}

	// 发送私信
	public void startRequestSendMsg() {
		SendLetterRequest request = new SendLetterRequest();
		if (ZCApplication.getInstance().getUserInfo() == null
				|| StringUtils.isEmpty(ZCApplication.getInstance()
						.getUserInfo().getSessionId())) {
			return;
		}
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setRelateCustId(custId);
		request.setTitle(et_zhuti.getText().toString());
		request.setLetter(et_content.getText().toString());

		NetWorkRequestManager.sendRequestPost(this, true, request,
				SendLetterResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		DialogUtils.dismisLoading();
		new AlertDialog(Activity_MsgSend.this).builder().setMsg("发送成功")
				.setPositiveButton("返回", new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				}).show();
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
