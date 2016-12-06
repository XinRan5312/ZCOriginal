package com.minjinsuo.zhongchou.widget;

import com.minjinsuo.zhongchou.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * @author admin 带进度条的WebView
 */
public class ProgressWebView extends WebView {

	private Context context;
	private ProgressBar progressbar;
	private OnWebCallBack onWebCallBack; // 回调

	public ProgressWebView(Context context) {
		this(context, null);
	}

	public ProgressWebView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.webTextViewStyle);
	}

	public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
		setWebViewClient(new MyWebViewClient());
		setWebChromeClient(new MyWebChromeClient());
		getSettings().setJavaScriptEnabled(true);
	}

	/**
	 * 设置ProgressBar
	 */
	void init() {
		progressbar = new ProgressBar(context, null,
				android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				8, 0, 0));
		progressbar.setProgressDrawable(getResources().getDrawable(
				R.drawable.progressbar));
		addView(progressbar);
	}

	public class MyWebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			if (onWebCallBack != null) { // 获取标题
				onWebCallBack.getTitle(title);
			}
		}

	}

	/**
	 * 不重写的话，会跳到手机浏览器中
	 * 
	 * @author admin
	 */
	public class MyWebViewClient extends WebViewClient {
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) { // Handle
			goBack();
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (onWebCallBack != null) { // 获得WebView的地址
				onWebCallBack.getUrl(url);
			}
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	/**
	 * 设置WebView的回掉器
	 * 
	 * @param onWebCallBack
	 */
	public void setOnWebCallBack(OnWebCallBack onWebCallBack) {
		this.onWebCallBack = onWebCallBack;
	}

	public interface OnWebCallBack {
		/**
		 * 获取标题
		 * 
		 * @param title
		 */
		void getTitle(String title);

		/**
		 * 获得WebView的地址
		 * 
		 * @param url
		 */
		void getUrl(String url);
	}
	
}