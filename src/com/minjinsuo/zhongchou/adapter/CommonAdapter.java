package com.minjinsuo.zhongchou.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 万能适配器
 * 
 * @author fengqingyun
 * 
 * @param <T>
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;
	private Map<Integer, View> views;

	public CommonAdapter(Context context, int itemLayoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		this.mItemLayoutId = itemLayoutId;
		this.views = new HashMap<Integer, View>();
	}

	public void setdata(List<T> mDatas) {
		// TODO Auto-generated method stub
		this.mDatas = mDatas;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (mDatas == null)
			return 0;
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		if (mDatas == null)
			return null;
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = views.get(position);
		final ViewHolder viewHolder = getViewHolder(position, convertView, parent);
		convert(viewHolder, getItem(position));
		convertView = views.put(position, convertView);
		return viewHolder.getConvertView();

	}

	public abstract void convert(ViewHolder helper, T item);

	private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
	}

}