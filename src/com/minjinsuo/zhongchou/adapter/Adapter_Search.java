package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_Search;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;

/**
 * List<String>列表数据适配器
 */
public class Adapter_Search extends BaseAdapter {

	public Context context;
	private ArrayList<String> tenderList;

	public Adapter_Search(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<String>();
	}

	public void setData(List<String> list) {
		if (list != null) {
			this.tenderList.addAll(list);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
	}

	public ArrayList<String> getData() {
		return tenderList;
	}

	@Override
	public int getCount() {
		if (tenderList != null) {
			return tenderList.size();
		} else {
			return 0;
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_search_history, null);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.iv_clear = (ImageView) convertView
					.findViewById(R.id.iv_clear);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.title.setText(tenderList.get(position));
		holder.iv_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 此处从保存的列表中删除给历史记录
				tenderList.remove(position);
				String str_history = JSON.toJSONString(tenderList);
				SharedPreferUtils.putValue(context, Activity_Search.CODE_HISTORY,
						Activity_Search.CODE_HISTORY, str_history);
				notifyDataSetChanged();
			}
		});

		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return tenderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView title;
		private ImageView iv_clear;
	}

}
