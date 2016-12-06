package com.minjinsuo.zhongchou.fragment;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class Fragment_Base extends Fragment implements
		MyRequestCallBack {
	public String pageNo = "1";
	public static String pageSize = "10";
	public boolean isPullDown = true;
	/** 标志位，标志已经初始化完成 */
	protected boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	protected boolean mHasLoadedOnce;

	protected boolean isVisible;

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}

	}

	/**
	 * 可见
	 */
	protected void onVisible() {
		lazyLoad();
	}

	/**
	 * 不可见
	 */
	protected void onInvisible() {

	}

	/**
	 * 初始化视图
	 */
	protected abstract void initView();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 获取当前Activity的Context
	 * 
	 * @return
	 */
	public Context getContext() {
		return getActivity();
	}

	/**
	 * 初始化监听
	 */
	protected abstract void initListener();

	public void setTitleText(View view, String text) {
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
		if (tv_title != null) {
			tv_title.setText(text);
		}
	}

	public void setTitleBack(View view) {
		ImageView btn_back = (ImageView) view.findViewById(R.id.btn_back);
		if (btn_back != null) {
			if (btn_back.getVisibility() != View.VISIBLE) {
				btn_back.setVisibility(View.VISIBLE);
			}
			btn_back.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getActivity().finish();
				}
			});
		}
	}

	/**
	 * 右边按钮
	 * 
	 * @param view
	 * @param text
	 * @param onClickListener
	 */
	public void setTilteRight(View view, String text,
			OnClickListener onClickListener) {
		TextView tv_right = (TextView) view.findViewById(R.id.tv_right);
		ImageView iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
		if (tv_right != null && iv_menu != null) {
			if (tv_right.getVisibility() != View.VISIBLE) {
				tv_right.setVisibility(View.VISIBLE);
				iv_menu.setVisibility(View.GONE);

			}
			tv_right.setText(text);
			tv_right.setOnClickListener(onClickListener);
		}
	}

	/**
	 * 右边按钮带图片
	 * 
	 * @param view
	 * @param rid
	 * @param onClickListener
	 */
	public void setTitleRightDrawable(View view,int rid, OnClickListener onClickListener) {
		ImageView iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
		if (iv_menu != null) {
			if (iv_menu.getVisibility() != View.VISIBLE) {
				iv_menu.setVisibility(View.VISIBLE);
			}
			iv_menu.setImageResource(rid);
			iv_menu.setOnClickListener(onClickListener);
		}
	}
	/**
	 * 延迟加载 子类必须重写此方法
	 */
	protected abstract void lazyLoad();
}
