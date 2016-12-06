package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.model.Model_Tender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 首页列表、我的关注等数据适配器
 */
public class Adapter_CrowdProduct extends BaseAdapter {

	public Context context;
	private ArrayList<Model_Tender> tenderList;

	public Adapter_CrowdProduct(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<Model_Tender>();
	}

	public void setData(ArrayList<Model_Tender> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
	}

	public ArrayList<Model_Tender> getData() {
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
					R.layout.item_home, null);
			holder.title = (TextView) convertView.findViewById(R.id.title_home);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.title.setText(tenderList.get(position).getTitle());
		holder.amount.setText(tenderList.get(position).getAmount());

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
		public TextView title, amount;
		public ProgressBar horizontal_pb;
	}

}
