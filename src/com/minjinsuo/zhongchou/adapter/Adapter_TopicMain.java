package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListResponse.ElementCommonTalkList;

import org.xutils.x;
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
 * 主题帖列表
 */
public class Adapter_TopicMain extends BaseAdapter {

	public Context context;
	private ArrayList<ElementCommonTalkList> tenderList;
	private ImageOptions options;

	public Adapter_TopicMain(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<ElementCommonTalkList>();
		options = new ImageOptions.Builder()
				// .setRadius(DensityUtil.dip2px(0))
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

	public ArrayList<ElementCommonTalkList> getData() {
		return tenderList;
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
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
					R.layout.item_topic_aty, null);
			holder.title = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.id_repayCnt = (TextView) convertView
					.findViewById(R.id.id_repayCnt);
			holder.head = (CircleImageView) convertView.findViewById(R.id.head);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		ElementCommonTalkList info = tenderList.get(position);
		holder.title.setText(info.getByUserNickName());// 发起标题人姓名
		holder.tv_content.setText(info.getContent());
		holder.tv_time.setText(StringUtils.getDate(info.getCommentTime(), 2));
		holder.id_repayCnt.setText(info.getRepayCnt() + "条评论");
		x.image().bind(holder.head, info.getPortraitAddr(), options);

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
		public TextView title, tv_content, tv_time, id_repayCnt;
		public CircleImageView head;
	}
}
