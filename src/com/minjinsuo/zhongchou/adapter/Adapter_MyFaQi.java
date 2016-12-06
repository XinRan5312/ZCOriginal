package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjTBPageListResponse.ElementPrjTBList;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_ProductDetail;
import com.minjinsuo.zhongchou.activity.Activity_ProjectDynamic;
import com.minjinsuo.zhongchou.activity.Activity_StockDetail;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.utils.DateUtils;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 我的发起列表数据适配器
 */
public class Adapter_MyFaQi extends BaseAdapter {

	public Context context;
	private ArrayList<ElementPrjTBList> tenderList;
	private boolean isProduct;
	private ImageOptions options;

	public Adapter_MyFaQi(Context context, boolean isProduct) {
		super();
		this.context = context;
		this.isProduct = isProduct;
		// 初始化
		tenderList = new ArrayList<ElementPrjTBList>();
		options = new ImageOptions.Builder().setRadius(DensityUtil.dip2px(0))
				.setIgnoreGif(false)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
	}

	public void setData(List<ElementPrjTBList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
	}

	public ArrayList<ElementPrjTBList> getData() {
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
					R.layout.item_my_fqi, null);

			holder.iv_type = (ImageView) convertView.findViewById(R.id.iv_type);
			holder.iv_left_icon = (ImageView) convertView
					.findViewById(R.id.iv_left_icon);
			holder.title = (TextView) convertView.findViewById(R.id.title_home);
			holder.tv_perNum = (TextView) convertView
					.findViewById(R.id.tv_perNum);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			holder.tv_support_num = (TextView) convertView
					.findViewById(R.id.tv_support_num);
			holder.tv_day_haveleft = (TextView) convertView
					.findViewById(R.id.tv_day_haveleft);
			holder.tv_progress = (TextView) convertView
					.findViewById(R.id.tv_progress);
			holder.horizontal_pb = (ProgressBar) convertView
					.findViewById(R.id.horizontal_pb);
			holder.btn_left = (Button) convertView.findViewById(R.id.btn_left);
			holder.btn_right = (Button) convertView
					.findViewById(R.id.btn_right);
			holder.type_order = (TextView) convertView
					.findViewById(R.id.type_order);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ElementPrjTBList info = tenderList.get(position);
		if (info != null) {
			x.image().bind(holder.iv_left_icon, info.getHomePicAddress(),
					options);
			holder.title.setText(info.getNam());
			holder.tv_perNum.setText("关注人数：" + info.getAttentionCount());
			// 已支持人数
			holder.tv_support_num.setText(info.getSuppedPeopleCnt() + "人");
			// 已支持金额
			holder.amount.setText(StringUtils.exceedW(info.getSuccAmount())
					+ "元");
			holder.tv_day_haveleft.setText(info.getSurplusDays() + "天");
			// 投资进度
			double progress = Double.parseDouble(info.getSuccAmount())
					/ Double.parseDouble(info.getPrjAmount());

			holder.horizontal_pb.setProgress((int) (progress * 100));
			holder.tv_progress.setText(StringUtils.round(progress * 100, 0)
					+ "%");

			// 项目状态
			String prj_status = info.getPrjBStatus();// 此字段有值则代表正式项目，否则判断显示是否该显示项目进展按钮
			if (!StringUtils.isEmpty(prj_status)) {
				holder.tv_status.setVisibility(View.VISIBLE);
				holder.tv_status.setText(StatusUtils
						.getStatusByCode(prj_status));
			} else {
				holder.tv_status.setVisibility(View.GONE);

				String status = info.getStatus();
				if (status.equals("10") || status.equals("20")
						|| status.equals("30") || status.equals("35")
						|| status.equals("40") || status.equals("45")
						|| status.equals("50")) {
					holder.btn_left.setVisibility(View.GONE);
				} else {
					holder.btn_left.setVisibility(View.VISIBLE);
				}
			}

			// if (info.getPrjTrade().equals("公益")) {
			// holder.iv_type.setImageResource(R.drawable.support_gongyi);
			// } else if (info.getPrjTrade().equals("科技")) {
			// holder.iv_type.setImageResource(R.drawable.keji);
			// } else if (info.getPrjTrade().equals("医疗")) {
			// holder.iv_type.setImageResource(R.drawable.yiliao);
			// } else {
			// holder.iv_type.setImageResource(R.drawable.other);
			// }
			holder.type_order.setText(info.getPrjTrade() == null ? "其他" : info
					.getPrjTrade());
			// 随机设置行业随机颜色(避免纯黑和纯白色)
			GradientDrawable p = (GradientDrawable) holder.type_order
					.getBackground();
			int r = (int) (Math.random() * 240 + 10);
			int g = (int) (Math.random() * 240 + 10);
			int b = (int) (Math.random() * 240 + 10);
			int color = Color.argb(111, r, g, b); // 半透明的紫色
			p.setColor(color);

			holder.btn_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// 跳转到项目进展
					Intent intent = new Intent(context,
							Activity_ProjectDynamic.class);
					intent.putExtra("id", info.getId());
					intent.putExtra(AppContants.FROM, "myFaqi");
					context.startActivity(intent);
				}
			});
			holder.btn_right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (isProduct) {
						Intent intent = new Intent(context,
								Activity_ProductDetail.class);
						intent.putExtra("prjId", info.getId());
						intent.putExtra("img", info.getHomePicAddress());
						intent.putExtra(AppContants.FROM, "myFaqi");
						context.startActivity(intent);
					} else {
						Intent intent = new Intent(context,
								Activity_StockDetail.class);
						intent.putExtra("id", info.getId());
						intent.putExtra("img", info.getHomePicAddress());
						intent.putExtra(AppContants.FROM, "myFaqi");
						context.startActivity(intent);
					}
				}
			});
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
		public TextView title, tv_perNum, amount, tv_status, tv_support_num,
				tv_day_haveleft, tv_progress, type_order;
		private ImageView iv_left_icon, iv_type;
		public ProgressBar horizontal_pb;
		private Button btn_left, btn_right;
	}

}
