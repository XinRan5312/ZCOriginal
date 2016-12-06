package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.fragment.ZFragment_inbox;
import com.minjinsuo.zhongchou.fragment.ZFragment_outbox;
import com.minjinsuo.zhongchou.fragment.ZFragment_sitesMsg;
import com.minjinsuo.zhongchou.service.BusProvider;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.widget.PagerSlidingTabStrip;
import com.squareup.otto.Subscribe;

/**
 * 消息中心(收件箱、发件箱、站内信)
 */
public class Activity_msgCenter extends Activity_Base {
	private PagerSlidingTabStrip mTabs;
	private ViewPager mPager;
	private EquityPagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_my_attention);

		x.view().inject(this);
		initView();
		initListener();
		BusProvider.getInstance().register(this);
	}

	// 获取当前选中的tab索引用来控制是否支持侧滑关闭当前页面
	@Subscribe
	public void OnEven(String currentPosition) {
		if (!StringUtils.isEmpty(currentPosition)) {
			if (currentPosition.equals("0")) {
				setSwipeBackEnable(true);
			} else {
				setSwipeBackEnable(false);
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		BusProvider.getInstance().unregister(this);
	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void initView() {
		setTitleText("消息中心");
		setTitleBack();

		mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mPager = (ViewPager) findViewById(R.id.pager);
		mTabs.setTextColor(getResources().getColor(R.color.main_color));
		mTabs.setTextSize(CommonUtils.sp2px(this, 14));
		mTabs.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		mTabs.setIndicatorHeight(6);
		mTabs.setIndicatorColorResource(R.color.main_color);
		mTabs.setShouldExpand(true);

		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		mPager.setPageMargin(pageMargin);
		mPagerAdapter = new EquityPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mTabs.setViewPager(mPager);
	}

	private class EquityPagerAdapter extends FragmentPagerAdapter {
		String[] datas = { "私信收件箱", "私信发件箱", "站内信" };

		public EquityPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return datas[position];
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {
			case 0:
				return ZFragment_inbox.getInstance();
			case 1:
				return ZFragment_outbox.getInstance();
			case 2:
				return ZFragment_sitesMsg.getInstance();
				// return ZFragment_sitesMsg2.getInstance();
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			return datas.length;
		}

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

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}
}
