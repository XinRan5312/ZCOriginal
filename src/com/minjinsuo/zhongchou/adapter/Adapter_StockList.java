package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListResponse.ElementPrjList;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 股权众筹列表数据适配器OK
 */
public class Adapter_StockList extends BaseAdapter {

	public Context context;
	private ArrayList<ElementPrjList> tenderList;
	private ImageOptions options;

	public Adapter_StockList(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<ElementPrjList>();

		options = new ImageOptions.Builder()
				// .setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
				.setRadius(DensityUtil.dip2px(0))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				.setCrop(true)
				.setIgnoreGif(false)
				// 不忽略gif图片类型
				.setCircular(false)
				// 是否圆角
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading_big)
				.setFailureDrawableId(R.drawable.icon_faild).build();
	}

	public void setData(List<ElementPrjList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public ArrayList<ElementPrjList> getData() {
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
					R.layout.item_crowd, null);

			holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			holder.supp_num = (TextView) convertView
					.findViewById(R.id.supp_num);// 已支持金额
			holder.ready_value = (TextView) convertView
					.findViewById(R.id.ready_value);// 出让股份
			holder.leftday = (TextView) convertView.findViewById(R.id.leftday);// 剩余天数
			holder.tv_progress = (TextView) convertView
					.findViewById(R.id.tv_progress);
			holder.horizontal_pb = (ProgressBar) convertView
					.findViewById(R.id.horizontal_pb);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		ElementPrjList info = tenderList.get(position);
		if (info != null) {
			x.image().bind(holder.iv_img, info.getHomePicAddress(), options);
			holder.title.setText(info.getNam());
			holder.tv_des.setText(info.getDesp());
			// 目标金额
			// holder.amount.setText(info.getAmount());
			holder.amount.setText(StringUtils.exceedW(info.getPrjAmount())+"元");
			// 已支持金额
			holder.supp_num.setText(StringUtils.exceedW(info
					.getSuppedAmt4Succ())+"元");
			// 出让股份比例
			holder.ready_value.setText(info.getPctSoldStock() + "%");// 后期添加字段
			holder.leftday.setText(info.getPrjEndSurplusDays() + "天");
			// 投资进度
			double progress = Double.parseDouble(info.getSuppedAmt4Succ())
					/ Double.parseDouble(info.getPrjAmount());

			holder.horizontal_pb.setProgress((int) (progress * 100));
			holder.tv_progress.setText(StringUtils.round(progress * 100, 0)
					+ "%");

			// 项目状态
			String status = info.getStatus();
			holder.tv_status.setText(StatusUtils.getStatusByCode(status));
			if ("10".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_green_radussmall);
			} else if ("20".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_red_radussmall);
			} else if ("30".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_green_radussmall);
			} else if ("35".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_red_radussmall);
			} else if ("40".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_green_radussmall);
			} else if ("45".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_red_radussmall);
			} else if ("50".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_red_radussmall);
			} else if ("55".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_red_radussmall);
			} else if ("60".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_red_radussmall);
			} else if ("70".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_red_radussmall);
			} else if ("80".equals(status)) {
				holder.tv_status
						.setBackgroundResource(R.drawable.shap_red_radussmall);
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
		public TextView title, tv_des, amount, tv_status, supp_num,
				ready_value, leftday, tv_progress;
		public ProgressBar horizontal_pb;
		private ImageView iv_img;
	}

}
