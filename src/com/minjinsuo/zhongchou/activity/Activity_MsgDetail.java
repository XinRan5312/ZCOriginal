package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ReadMessageRequest;
import net.zkbc.p2p.fep.message.protocol.ReadMessageResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.os.Bundle;
import android.webkit.WebView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 消息详情展示页（帮助中心、平台公告、站内信使用）
 * 
 * @author zp
 *
 *         2016年9月14日
 */
public class Activity_MsgDetail extends Activity_Base {

	private String msgId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thirdweb);
		msgId = getIntent().getStringExtra("id");
		setTitleBack();
		setTitleText("消息详情");

		if (getIntent() != null) {
			if (!StringUtils.isEmpty(getIntent().getStringExtra(
					AppContants.FROM))
					&& getIntent().getStringExtra(AppContants.FROM).equals(
							"innerMail")) {

				startReadMessageRequest();// 帮助中心和公告没有消息已读和未读状态区分；消息中心的站内信有区分 }
			}
		}

		String stringExtra = getIntent().getStringExtra("content");
		WebView wView = (WebView) findViewById(R.id.wv_opencharge);

		wView.loadDataWithBaseURL(null, stringExtra, "text/html", "utf-8", null);
	}

	/**
	 * 已读消息接口
	 */
	private void startReadMessageRequest() {
		ReadMessageRequest request = new ReadMessageRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setId(Integer.valueOf(msgId));
		NetWorkRequestManager.sendRequestPost(this, true, request,
				ReadMessageResponse.class, this);
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onSuccess(ResponseSupport response) {
		DialogUtils.dismisLoading();
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

}
