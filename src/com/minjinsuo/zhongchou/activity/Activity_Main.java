package com.minjinsuo.zhongchou.activity;

import org.xutils.x;
import org.xutils.common.util.LogUtil;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.fragment.Fragment_Account;
import com.minjinsuo.zhongchou.fragment.Fragment_Stock;
import com.minjinsuo.zhongchou.fragment.Fragment_Home;
import com.minjinsuo.zhongchou.fragment.Fragment_More;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.TabIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.widget.TabHost;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

/**
 * 底部tab控制器
 * 
 */
public class Activity_Main extends Activity_Base implements
		TabHost.OnTabChangeListener {
	private FragmentTabHost mTabHost;

	private TabIndicator home;// 首页
	private TabIndicator crowd;// 众筹
	private TabIndicator account;// 账户
	private TabIndicator more;// 更多

	public static final String TAB_Home = "tab_home";
	public static final String TAB_Crowd = "tab_crowd";
	public static final String TAB_Account = "tab_account";
	public static final String TAB_More = "tab_more";

	private boolean isExit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		x.view().inject(this);
		setSwipeBackEnable(false);

		initTabHost();
		initIndicator();
		initTab();

		LogUtil.i(ZCApplication.getInstance().TAG);
		setCurrentTabByTag(ZCApplication.getInstance().TAG);
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
	}

	public void initTabHost() {
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.homeTabContent);
		mTabHost.setOnTabChangedListener(this);
		mTabHost.getTabWidget().setDividerDrawable(null);
	}

	public void initIndicator() {
		home = new TabIndicator(this);
		home.setTabIcon(R.drawable.tab_home_normal, R.drawable.tab_home_focus);
		home.setTabHint(R.string.tab_home);

		crowd = new TabIndicator(this);
		crowd.setTabIcon(R.drawable.tab_crowd_normal,
				R.drawable.tab_crowd_focus);
		crowd.setTabHint(R.string.tab_crowd);

		account = new TabIndicator(this);
		account.setTabIcon(R.drawable.tab_account_normal,
				R.drawable.tab_account_focus);
		account.setTabHint(R.string.tab_account);

		more = new TabIndicator(this);
		more.setTabIcon(R.drawable.tab_more_normal, R.drawable.tab_more_focus);
		more.setTabHint(R.string.tab_more);
	}

	public void initTab() {
		TabHost.TabSpec homeSpec = mTabHost.newTabSpec(TAB_Home);
		homeSpec.setIndicator(home);
		mTabHost.addTab(homeSpec, Fragment_Home.class, null);

		TabHost.TabSpec crowdSpec = mTabHost.newTabSpec(TAB_Crowd);
		crowdSpec.setIndicator(crowd);
		mTabHost.addTab(crowdSpec, Fragment_Stock.class, null);

		TabHost.TabSpec accountSpec = mTabHost.newTabSpec(TAB_Account);
		accountSpec.setIndicator(account);
		mTabHost.addTab(accountSpec, Fragment_Account.class, null);

		TabHost.TabSpec moreSpec = mTabHost.newTabSpec(TAB_More);
		moreSpec.setIndicator(more);
		mTabHost.addTab(moreSpec, Fragment_More.class, null);
	}

	@Override
	public void onTabChanged(String tabId) {
		setCurrentTabByTag(tabId);
	}

	public void setCurrentTabByTag(String tag) {
		home.setCurrentFocus(false);
		crowd.setCurrentFocus(false);
		account.setCurrentFocus(false);
		more.setCurrentFocus(false);

		mTabHost.setCurrentTabByTag(tag);
		if (TAB_Home.equals(tag)) {
			home.setCurrentFocus(true);
			mTabHost.setCurrentTab(0);
		} else if (TAB_Crowd.equals(tag)) {
			crowd.setCurrentFocus(true);
			mTabHost.setCurrentTab(1);
			// setTitleText(getString(R.string.crowd_title));
		} else if (TAB_Account.equals(tag)) {
			account.setCurrentFocus(true);
			mTabHost.setCurrentTab(2);
			// setTitleText(getString(R.string.tab_account));
		} else if (TAB_More.equals(tag)) {
			more.setCurrentFocus(true);
			mTabHost.setCurrentTab(3);
			// setTitleText(getString(R.string.tab_more));
		}

	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

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

	/**
	 * 双击返回键退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Model_SaveUploadPic.arrMapName = null;
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			ToastUtil.showToast(getApplicationContext(), "再按一次退出程序");
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			// Intent intent = new Intent(Intent.ACTION_MAIN);
			// intent.addCategory(Intent.CATEGORY_HOME);
			// startActivity(intent);

			// System.exit(0);

			// 退出到桌面。关闭所有的acitivity
			getApplicationContext().sendBroadcast(new Intent("finish"));
		}
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			isExit = false;
		};
	};
}
