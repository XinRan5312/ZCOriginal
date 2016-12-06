package com.minjinsuo.zhongchou.activity;

import java.net.URLDecoder;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

public class Activity_ThirdWeb extends Activity_Base {
	public static final String TAG = "Activity_ThirdWeb";
	public static final String TITLENAME = "titlename";
	public static final String URL = "url";
	public static final String FLAG = "flag";// 0:此时操作成功后不关闭之前的页面
	@ViewInject(R.id.wv_opencharge)
	WebView wv_opencharge;

	private String url, titleName, type, flag;

	private ZCApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thirdweb);
		app = (ZCApplication) getApplicationContext();
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleBack();
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void initData() {

		app.addActivity(Activity_ThirdWeb.this);

		url = getIntent().getStringExtra(URL);
		titleName = getIntent().getStringExtra(TITLENAME);
		type = getIntent().getStringExtra("type");
		flag = getIntent().getStringExtra(FLAG) == null ? "-1" : getIntent()
				.getStringExtra(FLAG);

		if (StringUtils.isEmpty(titleName)) {
			titleName = "最新动态";// banner图点击显示，避免没有标题
		}
		setTitleText(titleName);

		// 设置webView属性，能够执行JavaScript脚本
		wv_opencharge.getSettings().setJavaScriptEnabled(true);
		wv_opencharge.setWebViewClient(new MyWebViewClient());
		wv_opencharge.loadUrl(url);
		LogUtil.i("调用第三方页面上传参数==" + url);
	}

	@Override
	protected void initListener() {

	}

	class MyWebViewClient extends WebViewClient {
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			LogUtil.i("第三方页面返回值==" + url);
			if (url.startsWith("p2p:")) {
				String url_code = URLDecoder.decode(url);
				String status = url_code.substring(
						url_code.indexOf("status=") + 1, url_code.indexOf("&"));

				LogUtil.i("返回码--status值为：" + status);
				if (status.equals("tatus=0") && "1".equals(type)) {
					app.isNeedRefresh = true;
					setResult(RESULT_OK);
					finish();
				}

				if ("tatus=1".equals(status) || status.equals("1")) {
					if ("1".equals(type)) {// 投资、联动优势开户
						app.isNeedRefresh = true;
					}
					Intent intent = new Intent();
					intent.putExtra("status", status);
					String message = url.substring(url.indexOf("message=") + 8,
							url.length());
					intent.putExtra("message", message);
					setResult(RESULT_OK, intent);
					if (!flag.equals("0")) {
						app.exit();
					} else {
						finish();
					}
				} else if ("tatus=0".equals(status) || status.equals("0")) {
					// 开通失败，跳回详情页面，让其重新投资，并弹框提示
					String string = url_code
							.substring(url_code.indexOf("message=") + 8,
									url_code.length());
					if (!StringUtils.isEmpty(string)) {
						final Dialog dialog = new DialogUtils()
								.createOneBtnDiolog(Activity_ThirdWeb.this,
										string);
						dialog.findViewById(R.id.btn_confirm)
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										ZCApplication.getInstance().isNeedRefresh = true;
										dialog.dismiss();
										finish();
									}
								});
					}
				}
			} else {
				view.loadUrl(url);
			}
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {

			super.onPageFinished(view, url);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv_opencharge.canGoBack()) {
			wv_opencharge.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		app.deleteActivity(Activity_ThirdWeb.this);
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

}
