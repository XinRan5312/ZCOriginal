package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjRwdByPrjIdResponse.ElementPrjRwdList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_Login;
import com.minjinsuo.zhongchou.activity.Activity_ProductOrderSub;
import com.minjinsuo.zhongchou.activity.OpenChargeFirstActivity;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 产品详情内嵌的list回报列表 适配器
 */
public class Adapter_ProDetailReward extends BaseAdapter {

	public Context context;
	private ArrayList<ElementPrjRwdList> tenderList;
	private String status_prj = "";// 项目状态
	private String type_prj = "";

	public Adapter_ProDetailReward(Context context, String status_prj,
			String type_prj) {
		super();
		this.context = context;
		this.status_prj = status_prj;
		this.type_prj = type_prj;
		// 初始化
		tenderList = new ArrayList<ElementPrjRwdList>();
	}

	public void setData(List<ElementPrjRwdList> list) {
		if (list != null) {
			this.tenderList.addAll(list);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
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
					R.layout.item_pro_reward, null);
			holder.title = (TextView) convertView.findViewById(R.id.tv_name);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.rl_type_wusifengxian = (LinearLayout) convertView
					.findViewById(R.id.rl_type_wusifengxian);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final TextView tv_amount = holder.amount;
		final ElementPrjRwdList info = tenderList.get(position);
		if (info != null && !StringUtils.isEmpty(info.getRwdTyp())) {
			if (info.getRwdTyp().equals("3")) {
				tv_amount.setText("无私奉献");
			} else {
				tv_amount.setText("￥" + info.getPerSuppAmt());// 支持金额
			}
		}

		// 跳转到提交订单页面
		holder.rl_type_wusifengxian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ZCApplication.getInstance().isLogin()) {
					if (ZCApplication.getInstance().getUserInfo()
							.getIsrealname() == null
							|| ZCApplication.getInstance().getUserInfo()
									.getIsrealname().equals("0")) {
						new AlertDialog(context)
								.builder()
								.setMsg("您还未实名认证，请实名认证后操作")
								.setPositiveButton("去认证",
										new OnClickListener() {

											@Override
											public void onClick(View v) {
												context.startActivity(new Intent(
														context,
														OpenChargeFirstActivity.class));
											}
										})
								.setNegativeButton("取消", new OnClickListener() {

									@Override
									public void onClick(View v) {

									}
								}).show();
						return;
					}

					if (status_prj.equals("40")) {// 筹款中
						Intent intent = new Intent(context,
								Activity_ProductOrderSub.class);
						intent.putExtra(AppContants.TYPE_PRJ, type_prj);// 主要区分是不是公益产品（协议显示不同）
						intent.putExtra("id", tenderList.get(position).getId());
						if (tv_amount.getText().toString().equals("无私奉献")) {
							intent.putExtra(AppContants.ISWSFX, true);
						} else {
							intent.putExtra(AppContants.ISWSFX, false);
						}
						context.startActivity(intent);
					} else {
						ToastUtil.showToast(context,
								"该项目" + StatusUtils.getStatusByCode(status_prj)
										+ "，不可投资");
					}
				} else {
					new AlertDialog(context).builder().setMsg("您尚未登录，请登录后操作")
							.setPositiveButton("确定", new OnClickListener() {

								@Override
								public void onClick(View v) {
									context.startActivity(new Intent(context,
											Activity_Login.class));
								}
							}).setNegativeButton("取消", new OnClickListener() {

								@Override
								public void onClick(View v) {

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
		public TextView title, amount, tv_status, tv_support_num,
				tv_day_haveleft, tv_progress;
		private LinearLayout rl_type_wusifengxian;
	}

}
