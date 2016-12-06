package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.model.Model_msg;

/**
 * 消息列表_消息中心和平台公告公用 消息展示列表
 */
public class Adapter_Msg extends BaseAdapter {
	ArrayList<Model_msg> datas;
	private Context mContext;

	// 构造函数
	public Adapter_Msg(Context mContext) {
		this.mContext = mContext;
	}

	public void setDatas(ArrayList<Model_msg> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return (datas == null || datas.size() == 0) ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = LayoutInflater.from(mContext).inflate(R.layout.item_msg,
				null);
		TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
		if (datas != null && datas.size() > 0) {
			final Model_msg model = datas.get(position);

			tv_time.setText(model.getTime());
			tv_title.setText(model.getType());

		}

		return convertView;
	}
}
