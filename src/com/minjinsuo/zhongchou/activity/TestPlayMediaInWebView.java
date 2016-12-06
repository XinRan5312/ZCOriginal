package com.minjinsuo.zhongchou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.minjinsuo.zhongchou.R;

/**
 * 添加权限<br/>
 * android.permission.INTERNET<br/>
 * 在manefest里面加上<br/>
 * android:hardwareAccelerated="true" <br/>
 * <br/>
 * 给webview添加两个<br/>
 * webView.setWebChromeClient(new WebChromeClient());<br/>
 * webView.setWebViewClient(new WebViewClient());<br/>
 * <br/>
 * 设置setting的属性 <br/>
 * 重点是setting.setJavaScriptEnabled(true);
 * 
 * @author
 * 
 * <br/>
 * 
 */
public class TestPlayMediaInWebView extends Activity {
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thirdweb);
		webView = (WebView) findViewById(R.id.wv_opencharge);
		WebSettings setting = webView.getSettings();
		setSettings(setting);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("http://v.qq.com/iframe/player.html?vid=e01264pnkg9&tiny=0&auto=0");
		// webView.loadUrl("http://182.92.76.202:8380/stock/detail/2150");
	}

	private void setSettings(WebSettings setting) {
		setting.setJavaScriptEnabled(true);
		setting.setBuiltInZoomControls(true);
		// setting.setDisplayZoomControls(false);
		setting.setSupportZoom(true);// 缩放

		setting.setDomStorageEnabled(true);
		setting.setDatabaseEnabled(true);
		// 全屏显示
		setting.setLoadWithOverviewMode(true);
		setting.setUseWideViewPort(true);
	}

	@Override
	protected void onPause() {
		webView.reload();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		webView.destroy();
	}
}
