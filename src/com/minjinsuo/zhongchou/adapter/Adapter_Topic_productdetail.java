package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListResponse.ElementCommonTalkList;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.view.CircleImageView;

/**
 * 话题列表数据适配器
 */
public class Adapter_Topic_productdetail extends BaseAdapter {

	public Context context;
	private ArrayList<ElementCommonTalkList> tenderList;
	private ImageOptions options;

	public Adapter_Topic_productdetail(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<ElementCommonTalkList>();
		new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(0))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 很多时候设置了合适的scaleType也不需要它.
				// 加载中或错误图片的ScaleType
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.head).build();
	}

	public void setData(List<ElementCommonTalkList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
	}

	public ArrayList<ElementCommonTalkList> getData() {
		return tenderList;
	}

	@Override
	public int getCount() {
		if (tenderList != null) {
			if (tenderList.size() < 3) {
				return tenderList.size();
			} else {
				return 2;
			}
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
					R.layout.item_topic, null);
			holder.investor = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			holder.tv_replay = (TextView) convertView
					.findViewById(R.id.tv_replay);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.iv_head = (CircleImageView) convertView
					.findViewById(R.id.iv_head);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.investor.setText(tenderList.get(position).getByUserNickName());
		holder.tv_content.setText(tenderList.get(position).getContent());
		holder.tv_replay
				.setText(tenderList.get(position).getRepayCnt() + "条回复");
		String time = StringUtils.getDate(tenderList.get(position)
				.getCommentTime(), 2);
		holder.tv_time.setText("|" + time);
		if (!StringUtils.isEmpty(tenderList.get(position).getPortraitAddr())) {
			x.image().bind(holder.iv_head,
					tenderList.get(position).getPortraitAddr(), options);
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
		public TextView investor, tv_content, tv_time, tv_replay;
		private CircleImageView iv_head;
	}

}
