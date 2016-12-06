package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;

/**
 * List<String>列表数据适配器
 */
public class Adapter_Common extends BaseAdapter {

	public Context context;
	private ArrayList<String> tenderList;

	public Adapter_Common(Context context) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_common, null);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.title.setText(tenderList.get(position));

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
	}

}
