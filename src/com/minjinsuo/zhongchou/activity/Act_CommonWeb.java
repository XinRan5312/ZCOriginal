package com.minjinsuo.zhongchou.activity;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.widget.ProgressWebView;

import android.os.Bundle;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

/**
 * 应用中公用的Webview
 * 
 * @author Jzc
 *
 */
public class Act_CommonWeb extends Activity_Base {

	@ViewInject(R.id.commonWeb)
	private ProgressWebView commonWeb;

	private String url, title = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_commonweb);
		x.view().inject(this);
		initView();
	}

	@Override
	protected void initView() {
		// ~~~ 获取参数
		url = getIntent().getStringExtra("url") == null ? "" : getIntent()
				.getStringExtra("url");
		LogUtil.i(url);
		title = getIntent().getStringExtra("title") == null ? "详情"
				: getIntent().getStringExtra("title");
		setTitleBack();
		setTitleText(title);
		
		// 设置webview的回掉函数,获得Url
		commonWeb.setOnWebCallBack(new ProgressWebView.OnWebCallBack() {
			// 获取标题
			@Override
			public void getTitle(String title) {
				// setTitleText(title);
			}

			// 获取当前web的URL地址
			@Override
			public void getUrl(String url) {
				// url_tv.setText(url);
			}
		});

		commonWeb.loadUrl(url);
		// 测试播放视频
		// commonWeb.loadUrl("http://182.92.76.202:8080/stock/prjIntroAPP/2224");
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initListener() {
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

	@Override
	protected void onPause() {
		commonWeb.reload();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		commonWeb.destroy();
	}
}
