package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjRwdByPrjIdResponse.ElementPrjRwdList;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_Login;
import com.minjinsuo.zhongchou.activity.Activity_ProductOrderSub;
import com.minjinsuo.zhongchou.activity.Activity_StockBookSub;
import com.minjinsuo.zhongchou.activity.Activity_StockOrderSub;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 选择支持档位
 */
public class Adapter_SelectTypes extends BaseAdapter {

	public Context context;
	private ArrayList<ElementPrjRwdList> tenderList;
	private ImageOptions options;
	private String flag = "";// 入口标示// 预热中的股权 跳转到 预约申请页；众筹中的跳转到订单填写页
	private String type_prj = "";

	public Adapter_SelectTypes(Context context, String flag, String type_prj) {
		super();
		this.context = context;
		this.flag = flag;
		this.type_prj = type_prj;
		// 初始化
		tenderList = new ArrayList<ElementPrjRwdList>();
		options = new ImageOptions.Builder()
				.setSize(DensityUtil.dip2px(90), DensityUtil.dip2px(90))
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

	public void setData(List<ElementPrjRwdList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public ArrayList<ElementPrjRwdList> getData() {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_invest_types, null);

			holder.btn_invest = (Button) convertView
					.findViewById(R.id.btn_invest);
			holder.tv_amount = (TextView) convertView
					.findViewById(R.id.tv_amount);
			holder.tv_supportedNum = (TextView) convertView
					.findViewById(R.id.tv_supportedNum);
			holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
			holder.ll_container = (LinearLayout) convertView
					.findViewById(R.id.ll_container);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		if (flag.equals("预热中")) {
			holder.btn_invest.setText("立即预约");
		} else {
			holder.btn_invest.setText("立即支持");
		}

		final TextView tv_amount = holder.tv_amount;
		if (tenderList.get(position).getRwdTyp().equals("3")) {
			tv_amount.setText("无私奉献");
		} else {
			tv_amount.setText("￥" + tenderList.get(position).getPerSuppAmt());// 单笔支持金额
		}
		holder.tv_des.setText(tenderList.get(position).getRwdContent());// 回报内容
		holder.tv_supportedNum.setText(tenderList.get(position).getSuppedCnt());// 支持数
		// 如果图片不止一张时，动态添加图片显示
		if (tenderList.get(position).getRwdPicAddress() != null
				&& !StringUtils.isEmpty(tenderList.get(position)
						.getRwdPicAddress())) {
			String[] strArr = {};
			if (tenderList.get(position).getRwdPicAddress().contains(",")) {// 多张图片时
				strArr = tenderList.get(position).getRwdPicAddress().split(",");
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

				x.image().bind(iv_pic,
						tenderList.get(position).getRwdPicAddress(), options);
				holder.ll_container.addView(iv_pic);
			}

		}

		holder.btn_invest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ZCApplication.getInstance().isLogin()) {
					Intent intent = null;
					// 预热中的股权 跳转到 预约申请页；众筹中的跳转到订单填写页
					if (flag.equals("众筹中")) {
						intent = new Intent(context,
								Activity_StockOrderSub.class);
						intent.putExtra(AppContants.TYPE_PRJ, type_prj);
						intent.putExtra("id", tenderList.get(position).getId());
					} else if (flag.equals("预热中")) {
						intent = new Intent(context,
								Activity_StockBookSub.class);
						intent.putExtra(AppContants.TYPE_PRJ, type_prj);
						intent.putExtra("id", tenderList.get(position).getId());
					} else {// 代表产品订单提交
						intent = new Intent(context,
								Activity_ProductOrderSub.class);
						intent.putExtra(AppContants.TYPE_PRJ, type_prj);// 主要区分是不是公益产品
						intent.putExtra("id", tenderList.get(position).getId());
						if (tv_amount.getText().toString().equals("无私奉献")) {
							intent.putExtra(AppContants.ISWSFX, true);
						} else {
							intent.putExtra(AppContants.ISWSFX, false);
						}
					}
					context.startActivity(intent);
				} else {
					new AlertDialog(context).builder().setMsg("您尚未登录，请登录后操作")
							.setPositiveButton("确定", new OnClickListener() {

								@Override
								public void onClick(View v) {
									context.startActivity(new Intent(context,
											Activity_Login.class));
								}
							}).show();
				}
			}
		});

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
		public TextView tv_amount, tv_supportedNum, tv_des;
		public Button btn_invest;
		private LinearLayout ll_container;
	}

}
