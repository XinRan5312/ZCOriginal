package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.fragment.Fragment_child_MyAttention;
import com.minjinsuo.zhongchou.service.BusProvider;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.widget.PagerSlidingTabStrip;
import com.squareup.otto.Subscribe;

/**
 * 我的关注
 * 
 * @author zp
 *
 *         2016年6月20日
 */
public class Activity_MyAttention extends Activity_Base {

	@ViewInject(R.id.tabs)
	private PagerSlidingTabStrip mTabs;
	@ViewInject(R.id.pager)
	private ViewPager mPager;

	private AttentionPagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.aty_my_attention);
		x.view().inject(this);

		initView();
		BusProvider.getInstance().register(this);
	}

	// 第2步：声明订阅者——用来接收数据
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
	protected void initView() {
		setTitleText(getString(R.string.my_attention_title));
		setTitleBack();

		mTabs.setTextColor(getResources().getColor(R.color.main_color));
		mTabs.setTextSize(CommonUtils.sp2px(this, 14));
		mTabs.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
		mTabs.setIndicatorHeight(6);
		mTabs.setIndicatorColorResource(R.color.main_color);
		mTabs.setShouldExpand(true);
		mTabs.setDividerColor(getResources().getColor(R.color.white));// 相当于标签之间没有间隔

		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		mPager.setPageMargin(pageMargin);
		mPagerAdapter = new AttentionPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mTabs.setViewPager(mPager);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {

	}

	private class AttentionPagerAdapter extends FragmentPagerAdapter {
		String[] datas = { "产品众筹", "股权众筹" };

		public AttentionPagerAdapter(FragmentManager fm) {
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
				// return Fragment_child_Attention_Product.getInstance();
				return Fragment_child_MyAttention.getInstance(true);
			case 1:
				// return Fragment_child_Attention_Stock.getInstance();
				return Fragment_child_MyAttention.getInstance(false);
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

}
