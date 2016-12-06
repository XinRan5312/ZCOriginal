package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetMyPreOrderPageListResponse.ElementPreOrderList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelPreOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelPreOrderResponse;
import net.zkbc.p2p.fep.message.protocol.SubmitDeleteOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitDeleteOrderResponse;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_BookedDetail;
import com.minjinsuo.zhongchou.activity.Activity_ThirdWeb;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.squareup.otto.Subscribe;

/**
 * 我的预约列表数据适配器
 */
public class Adapter_MyBooked extends BaseAdapter {

	public Context context;
	private ArrayList<ElementPreOrderList> tenderList;
	private ImageOptions options;
	public static final int CODE_PAY = 001;

	public Adapter_MyBooked(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<ElementPreOrderList>();
		options = new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(3))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 很多时候设置了合适的scaleType也不需要它.
				// 加载中或错误图片的ScaleType
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
	}

	public void setData(List<ElementPreOrderList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
	}

	public ArrayList<ElementPreOrderList> getData() {
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
					R.layout.item_booked, null);

			holder.orderStatus = (TextView) convertView
					.findViewById(R.id.orderStatus);
			holder.preOrderCount = (TextView) convertView
					.findViewById(R.id.preOrderCount);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.purposeAmount = (TextView) convertView
					.findViewById(R.id.purposeAmount);
			holder.frozenAmt = (TextView) convertView
					.findViewById(R.id.frozenAmt);
			holder.time_duration = (TextView) convertView
					.findViewById(R.id.time_duration);
			holder.prjBStatus = (TextView) convertView
					.findViewById(R.id.prjBStatus);
			holder.btn_left = (TextView) convertView
					.findViewById(R.id.tv_btn_left);
			holder.btn_right = (TextView) convertView
					.findViewById(R.id.tv_supportNow);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		final ElementPreOrderList info = tenderList.get(position);
		if (info != null) {
			holder.preOrderCount.setText(info.getPreOrderCount());
			holder.title.setText(info.getPrjNam());
			holder.purposeAmount.setText(info.getPurposeAmount() + "元");
			holder.frozenAmt.setText(info.getFrostAmount() + "元");
			holder.time_duration.setText(StringUtils.getDate(
					info.getPreStartDate(), 1)
					+ "~" + StringUtils.getDate(info.getPreEndDate(), 1));
			x.image().bind(holder.iv_pic, info.getHomePicAddress(), options);
			// 项目状态
			String status = info.getPrjBStatus();
			holder.prjBStatus.setText(StatusUtils.getStatusByCode(status));
			// 预约状态??订单状态
			String status_yuyue = info.getDepositStatus();// zpqa??没有预约状态字段返回
			holder.orderStatus.setText(StatusUtils
					.getYuyueStatusByCode(status_yuyue));

			if (!StringUtils.isEmpty(status_yuyue)) {
				if (status_yuyue.equals("10")) {// 预约成功
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_right.setVisibility(View.GONE);
				} else if (status_yuyue.equals("20")
						|| status_yuyue.equals("30")
						|| status_yuyue.equals("50")) {// 预约失败、预约取消
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_right.setVisibility(View.GONE);
					holder.btn_left.setText("删除订单");
				} else if (status_yuyue.equals("40")) {// 优先购买中
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_right.setVisibility(View.VISIBLE);
					if (info.getIsCanFirstBuy().equals("1")) {
						holder.btn_right.setText("优先购买");
					} else if (info.getIsCanFirstBuy().equals("2")) {
						holder.btn_right.setText("正常购买");
					}
				} else {
					holder.btn_left.setVisibility(View.GONE);
					holder.btn_right.setVisibility(View.GONE);
				}
			} else {
				holder.btn_left.setVisibility(View.GONE);
				holder.btn_right.setVisibility(View.GONE);
			}
		}

		final String text_btn = holder.btn_left.getText().toString().trim();
		holder.btn_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (text_btn.equals("取消预约")) {
					new AlertDialog(context).builder()
							.setMsg("取消当前预约将失去优先购买资格，请确认是否要取消预约？")
							.setPositiveButton("取消预约", new OnClickListener() {

								@Override
								public void onClick(View v) {
//									submitCancelPreOrder(info.getOrderId(),
//											position);
									submitCancelOrder(info.getOrderId(),
											position);
								}
							}).setNegativeButton("稍后再说", new OnClickListener() {

								@Override
								public void onClick(View v) {

								}
							}).show();
				} else if (text_btn.equals("删除订单")) {
					new AlertDialog(context).builder().setMsg("是否删除该订单？")
							.setPositiveButton("确定", new OnClickListener() {

								@Override
								public void onClick(View v) {
									submitDeleteOrder(info.getOrderId(),
											position);
								}
							}).setNegativeButton("取消", new OnClickListener() {

								@Override
								public void onClick(View v) {
								}
							}).show();
				}
			}
		});

		holder.btn_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (info.getIsCanFirstBuy().equals("0")) {
					ToastUtil.showToast(context, "暂不可优先购买");
					return;
				}
				Intent intent = new Intent(context, Activity_ThirdWeb.class);
				String url = Service_ThirdPay.PAY_ORDER
						+ "sessionId="
						+ ZCApplication.getInstance().getUserInfo()
								.getSessionId() + "&payAmt="
						+ info.getPurposeAmount() + "&bdId=" + info.getPrjId()
						+ "&orderNo=" + info.getOrderNo() + "&ordId="
						+ info.getOrderId() + "&type=" + 0;
				intent.putExtra(Activity_ThirdWeb.URL, url);
				intent.putExtra(Activity_ThirdWeb.TITLENAME, "订单支付");
				((FragmentActivity) context).startActivityForResult(intent,
						CODE_PAY);
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
		public TextView btn_right, btn_left, title,// 标题
				prjBStatus,// 项目状态
				orderStatus,// 预约状态
				preOrderCount,// 预约人数
				frozenAmt,// 冻结金额
				time_duration,// 时间范围
				purposeAmount;// 意向投资金额
		private ImageView iv_pic;
	}

	/**
	 * 1、取消预约订单
	 * 
	 * @param orderId
	 */
//	public void submitCancelPreOrder(String orderId, final int position) {
//		SubmitCancelPreOrderRequest request = new SubmitCancelPreOrderRequest();
//		request.setSessionId(ZCApplication.getInstance().getUserInfo()
//				.getSessionId());
//		request.setOrderId(orderId);
//		NetWorkRequestManager.sendRequestPost(context, true, request,
//				SubmitCancelPreOrderResponse.class, new MyRequestCallBack() {
	public void submitCancelOrder(String orderId, final int position) {
		SubmitCancelOrderRequest request = new SubmitCancelOrderRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setOrderId(orderId);
		NetWorkRequestManager.sendRequestPost(context, true, request,
				SubmitCancelPreOrderResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						ToastUtil.showToast(context, "该订单已取消预约");
						tenderList.get(position).setDepositStatus("30");
						notifyDataSetChanged();

						DialogUtils.dismisLoading();
					}

					@Override
					public void onFailure(ResponseSupport response) {
						DialogUtils.dismisLoading();
						ToastUtil.cancelToast();
						if (!StringUtils.isEmpty(response.getMessage())) {
							new AlertDialog(context)
									.builder()
									.setMsg(response.getMessage())
									.setPositiveButton("确定",
											new OnClickListener() {

												@Override
												public void onClick(View v) {

												}
											}).show();
						}
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
					}
				});
	}

	/**
	 * 2、 删除
	 * 
	 * @param orderId
	 */
	public void submitDeleteOrder(String orderId, final int position) {
		SubmitDeleteOrderRequest request = new SubmitDeleteOrderRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setOrderId(orderId);
		NetWorkRequestManager.sendRequestPost(context, true, request,
				SubmitDeleteOrderResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						ToastUtil.showToast(context, "该订单已删除");
						tenderList.remove(position);
						notifyDataSetChanged();

						DialogUtils.dismisLoading();
					}

					@Override
					public void onFailure(ResponseSupport response) {
						DialogUtils.dismisLoading();
						ToastUtil.cancelToast();
						if (!StringUtils.isEmpty(response.getMessage())) {
							new AlertDialog(context)
									.builder()
									.setMsg(response.getMessage())
									.setPositiveButton("确定",
											new OnClickListener() {

												@Override
												public void onClick(View v) {

												}
											}).show();
						}
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
						ToastUtil.showToast(context, "删除失败");
					}
				});
	}

}
