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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.fragment.Fragment_child_redBag;
import com.minjinsuo.zhongchou.service.BusProvider;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.widget.PagerSlidingTabStrip;
import com.squareup.otto.Subscribe;

/**
 * 我的红包
 * 
 */
public class Activity_MyRedBag extends Activity_Base {

	@ViewInject(R.id.tabs)
	private PagerSlidingTabStrip mTabs;
	@ViewInject(R.id.pager)
	private ViewPager mPager;
	@ViewInject(R.id.redbag_ruler)
	private TextView redbag_ruler;

	private AttentionPagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.aty_my_redbg);
		x.view().inject(this);
		initView();
		initData();
		initListener();
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
		setTitleText("红包");
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
		// 红包使用规则
		redbag_ruler.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String redRule = CommonUtils.getAssetsData(
						Activity_MyRedBag.this, "redBagUseRules.txt");
				if (!StringUtils.isEmpty(redRule)) {
					DialogUtils.createOneBtnDiolog(Activity_MyRedBag.this,
							redRule, "使用规则", Gravity.LEFT).show();
				}
			}
		});
	}

	private class AttentionPagerAdapter extends FragmentPagerAdapter {
		String[] datas = { "当前", "历史" };

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
				return Fragment_child_redBag.getInstance(false);
			case 1:
				return Fragment_child_redBag.getInstance(true);
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
