package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.GetCommonTalkRepayListResponse.ElementCommonTalkRepayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 主题帖回复列表
 */
public class Adapter_TopicReply extends BaseAdapter {

	public Context context;
	private ArrayList<ElementCommonTalkRepayList> tenderList;

	public Adapter_TopicReply(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<ElementCommonTalkRepayList>();
	}

	public void setData(ArrayList<ElementCommonTalkRepayList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
	}

	public ArrayList<ElementCommonTalkRepayList> getData() {
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
					R.layout.item_topic_reply, null);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.time = (TextView) convertView.findViewById(R.id.time);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		ElementCommonTalkRepayList info = tenderList.get(position);
		if (info != null) {
			holder.name.setText(info.getByUserNickName());
			holder.content.setText(info.getContent());
			holder.time.setText(StringUtils.getDate(info.getCommentTime(), 2));
		}

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
		public TextView name, content, time;
	}

}
