package com.minjinsuo.zhongchou.activity;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import com.minjinsuo.zhongchou.R;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

/**
 * 协议阅读类
 * 
 * @author zp
 *
 *         2016年9月27日
 */
public class Activity_CommonRead extends Activity_Base {
	@ViewInject(R.id.wv_opencharge)
	private WebView wv_opencharge;

	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	private String content = "";
	private String title = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.activity_thirdweb);
		x.view().inject(this);

		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleText("协议");
		setTitleBack();
	}

	@Override
	protected void initData() {

		if (getIntent() != null) {
			content = getIntent().getStringExtra(CONTENT) == null ? ""
					: getIntent().getStringExtra(CONTENT);

			title = getIntent().getStringExtra(TITLE) == null ? "协议"
					: getIntent().getStringExtra(TITLE);
			setTitleText(title);
		}

		WebView wView = (WebView) findViewById(R.id.wv_opencharge);

		wView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(ResponseSupport response) {
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
