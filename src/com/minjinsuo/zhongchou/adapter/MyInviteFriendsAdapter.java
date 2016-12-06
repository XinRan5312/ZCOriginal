package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.model.Model_inviteFriend;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 邀请好友列表适配器
 * 
 * @author zp
 *
 *         2016年10月24日
 */
public class MyInviteFriendsAdapter extends BaseAdapter {
	private LayoutInflater mInflate;
	private ArrayList<Model_inviteFriend> datas = new ArrayList<Model_inviteFriend>();

	public MyInviteFriendsAdapter(Context context,
			ArrayList<Model_inviteFriend> datas) {
		this.datas = datas;
		this.mInflate = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = mInflate
					.inflate(R.layout.item_lv_invitefriends, null);
			mHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			mHolder.tv_itemInviteReward = (TextView) convertView
					.findViewById(R.id.tv_inviteRewardCount);
			mHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		Model_inviteFriend model_inviteFriend = datas.get(position);
		mHolder.tv_name.setText(model_inviteFriend.getName());
		if (model_inviteFriend.getWay().equals("1")) {// 加息券
			mHolder.tv_itemInviteReward.setText("加息券");
		} else if (model_inviteFriend.getWay().equals("0")) {// 代金券
			mHolder.tv_itemInviteReward.setText("代金券"
					+ StringUtils.rawIntStr2IntStr(model_inviteFriend
							.getRewardCount()) + "元");
		} else if (model_inviteFriend.getWay().equals("2")) {// 体验金
			mHolder.tv_itemInviteReward.setText("体验金"
					+ StringUtils.rawIntStr2IntStr(model_inviteFriend
							.getRewardCount()) + "元");
		} else {
			mHolder.tv_itemInviteReward.setText("无");
		}
		mHolder.tv_time.setText(StringUtils.getDate(
				model_inviteFriend.getTime(), 1));
		return convertView;
	}

	private class ViewHolder {
		private TextView tv_name;
		private TextView tv_itemInviteReward;
		private TextView tv_time;

	}
}
