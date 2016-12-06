package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Adapter_FragmentHome_Viewpager extends PagerAdapter {

	@Override
	public int getCount() {
		return imageViews.size() <= 1 ? imageViews.size() : Integer.MAX_VALUE;
	}

	private List<ImageView> imageViews = new ArrayList<ImageView>();

	public Adapter_FragmentHome_Viewpager(List<ImageView> imageViews) {
		this.imageViews = imageViews;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		int index = position % imageViews.size();
		ImageView iv = imageViews.get(index);
		try {
			if (((ViewPager) container).getChildCount() == 4 || iv.getParent() != null) {
				((ViewPager) container).removeView(iv);
			}
			((ViewPager) container).addView(iv);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iv;
	}
}
