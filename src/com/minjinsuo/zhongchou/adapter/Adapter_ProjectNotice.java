package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjNoticeListResponse.ElementPrjNoticeList;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 股权的项目公告列表数据适配器
 */
public class Adapter_ProjectNotice extends BaseAdapter {

	public Context context;
	private ArrayList<ElementPrjNoticeList> tenderList;
	private ImageOptions options;

	public Adapter_ProjectNotice(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<ElementPrjNoticeList>();

		options = new ImageOptions.Builder()
				.setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
				.setRadius(DensityUtil.dip2px(5))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				.setCrop(true)
				.setIgnoreGif(false)
				// 不忽略gif图片类型
				.setCircular(false)
				// 是否圆角
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
	}

	public void setData(List<ElementPrjNoticeList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
	}

	public ArrayList<ElementPrjNoticeList> getData() {
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
					R.layout.item_project_dynamic, null);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_content = (TextView) convertView
					.findViewById(R.id.tv_content);// 内容
			holder.iv_top_line = (ImageView) convertView
					.findViewById(R.id.iv_top_line);
			holder.iv_mid_circle = (ImageView) convertView
					.findViewById(R.id.iv_mid_circle);
			holder.iv_bottom_line = (ImageView) convertView
					.findViewById(R.id.iv_bottom_line);
			holder.ll_container = (LinearLayout) convertView
					.findViewById(R.id.ll_container);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		ElementPrjNoticeList info = tenderList.get(position);
		if (info != null) {
			// 处理进度线样式
			if (position == 0) {
				holder.tv_time.setTextColor(context.getResources().getColor(
						R.color.green));
				holder.tv_content.setTextColor(context.getResources().getColor(
						R.color.green));
				holder.iv_top_line.setVisibility(View.INVISIBLE);
				holder.iv_bottom_line.setVisibility(View.VISIBLE);
				holder.iv_mid_circle.setImageResource(R.drawable.circle_green);
				
				if (position == tenderList.size() - 1) {
					holder.iv_top_line.setVisibility(View.INVISIBLE);
					holder.iv_bottom_line.setVisibility(View.INVISIBLE);
				}
			} else {
				holder.tv_time.setTextColor(context.getResources().getColor(
						R.color.gray_969696));
				holder.tv_content.setTextColor(context.getResources().getColor(
						R.color.gray_969696));
				holder.iv_top_line.setVisibility(View.VISIBLE);
				holder.iv_bottom_line.setVisibility(View.VISIBLE);
				holder.iv_mid_circle.setImageResource(R.drawable.circle_gray);

				if (position == tenderList.size() - 1) {
					holder.iv_top_line.setVisibility(View.VISIBLE);
					holder.iv_bottom_line.setVisibility(View.INVISIBLE);
				}
			}

			// 填充数据
			holder.tv_content.setText(info.getFileIdMemo());
			holder.tv_time
					.setText(StringUtils.getDate(info.getCreateTime(), 2));

			// 如果图片不止一张时，动态添加图片显示
			if (info.getFileAddrs() != null
					&& !StringUtils.isEmpty(info.getFileAddrs())) {
				String[] strArr = {};
				if (info.getFileAddrs().contains(",")) {// 多张图片时
					strArr = info.getFileAddrs().split(",");
					for (int i = 0; i < strArr.length; i++) {
						ImageView iv_pic = new ImageView(context);
						iv_pic.setPadding(0, DensityUtil.dip2px(10),
								DensityUtil.dip2px(10), 0);

						x.image().bind(iv_pic, strArr[i], options);
						holder.ll_container.addView(iv_pic);
					}
				} else {// 单张图片时
					ImageView iv_pic = new ImageView(context);
					iv_pic.setPadding(0, DensityUtil.dip2px(10),
							DensityUtil.dip2px(10), 0);

					x.image().bind(iv_pic, info.getFileAddrs(), options);
					holder.ll_container.addView(iv_pic);
				}

			}
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
		public TextView tv_time, tv_content;
		public ImageView iv_top_line, iv_mid_circle, iv_bottom_line,
				iv_pic_content;
		private LinearLayout ll_container;// 图片显示容器
	}

}
