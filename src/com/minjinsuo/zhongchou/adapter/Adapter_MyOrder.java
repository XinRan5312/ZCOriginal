package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.ComfigRecGoodsRequest;
import net.zkbc.p2p.fep.message.protocol.ComfigRecGoodsResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelPreOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelPreOrderResponse;
import net.zkbc.p2p.fep.message.protocol.SubmitDeleteOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitDeleteOrderResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyOrderPageListResponse.ElementMyOrderList;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_ThirdWeb;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 我的订单数据适配器
 */
public class Adapter_MyOrder extends BaseAdapter {

	public Context context;
	private ArrayList<ElementMyOrderList> tenderList;
	private ImageOptions options;
	public static final int CODE_PAY = 001;

	public Adapter_MyOrder(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<ElementMyOrderList>();
		options = new ImageOptions.Builder()
				// .setRadius(DensityUtil.dip2px(0))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 很多时候设置了合适的scaleType也不需要它.
				// 加载中或错误图片的ScaleType
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
	}

	public void setData(List<ElementMyOrderList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public ArrayList<ElementMyOrderList> getData() {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_supported, null);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.amount = (TextView) convertView.findViewById(R.id.amount);
			holder.tv_orderNo = (TextView) convertView
					.findViewById(R.id.tv_orderNo);
			holder.tv_order_status = (TextView) convertView
					.findViewById(R.id.tv_order_status);
			holder.prjStatus = (TextView) convertView
					.findViewById(R.id.prjStatus);
			holder.btn_left = (TextView) convertView
					.findViewById(R.id.btn_left);
			holder.btn_right = (TextView) convertView
					.findViewById(R.id.btn_right);
			holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
			holder.iv_type = (ImageView) convertView.findViewById(R.id.iv_type);
			holder.type_order = (TextView) convertView
					.findViewById(R.id.type_order);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final ElementMyOrderList info = tenderList.get(position);
		if (info != null) {
			holder.title.setText(info.getCommodityNam());
			holder.amount.setText("￥" + info.getTotalPrice());
			holder.tv_orderNo.setText("订单编号：" + info.getOrderNo());
			x.image().bind(holder.iv_pic, info.getFileaddr(), options);
			if (info.getPrjTrade().equals("公益")) {
				holder.iv_type.setImageResource(R.drawable.support_gongyi);
				holder.iv_type.setVisibility(View.VISIBLE);
				holder.type_order.setVisibility(View.GONE);
			} else if (info.getPrjTrade().equals("科技")) {
				holder.iv_type.setImageResource(R.drawable.keji);
				holder.type_order.setVisibility(View.GONE);
				holder.iv_type.setVisibility(View.VISIBLE);
			} else if (info.getPrjTrade().equals("医疗")) {
				holder.iv_type.setImageResource(R.drawable.yiliao);
				holder.type_order.setVisibility(View.GONE);
				holder.iv_type.setVisibility(View.VISIBLE);
			} else {
				holder.iv_type.setVisibility(View.GONE);
				holder.type_order.setVisibility(View.VISIBLE);
				holder.type_order.setText(info.getPrjTrade() == null ? "未知"
						: info.getPrjTrade());
			}
			// 项目状态
			String status_prj = info.getPrjbstaus();// prjbstaus 后期新添加的字段
			holder.prjStatus.setText(StatusUtils.getStatusByCode(status_prj));

			String status = info.getStatus();
			if (!StringUtils.isEmpty(status)) {
				holder.tv_order_status.setText(StatusUtils
						.getOrderStatusByCode(status));
			}
			int statusCode = Integer.parseInt(status);
			/*
			 * 10 未支付(立即支付/取消) 110 已失效(删除按钮) 40 支付成功(取消按钮) 20 订单已取消(删除按钮) 80
			 * 已发货(投诉/确认收货) 90 已收货(删除按钮) 50 交易已完成(删除按钮) 100 退款中(无按钮显示) 70
			 * 退款成功(删除按钮)
			 */
			switch (statusCode) {
			case 10:
				holder.tv_order_status.setText("交易状态：" + "未支付");
				holder.btn_left.setText("取消项目");
				holder.btn_right.setText("立即支付");
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_right.setVisibility(View.VISIBLE);
				break;
			case 20:
				holder.tv_order_status.setText("交易状态：" + "已取消");
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_left.setText("删除订单");
				holder.btn_right.setVisibility(View.GONE);
				break;
			case 30:
				holder.tv_order_status.setText("交易状态：" + "定金已付");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setText("取消订单");
				holder.btn_left.setVisibility(View.VISIBLE);
				break;
			case 35:
				holder.tv_order_status.setText("交易状态：" + "预约作废");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setText("删除订单");
				holder.btn_left.setVisibility(View.VISIBLE);
				break;
			case 36:
				holder.tv_order_status.setText("交易状态：" + "满标作废");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setText("删除订单");
				holder.btn_left.setVisibility(View.VISIBLE);
				break;
			case 40:
				holder.tv_order_status.setText("交易状态：" + "已支付");
				holder.btn_left.setText("取消订单");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.VISIBLE);
				break;
			case 41:
				holder.tv_order_status.setText("交易状态：" + "支付异常");
				holder.btn_left.setText("取消订单");
				holder.btn_right.setText("立即支付");
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_right.setVisibility(View.VISIBLE);
				break;
			case 45:
				holder.tv_order_status.setText("交易状态：" + "未支付");
				holder.btn_left.setText("取消订单");
				holder.btn_right.setText("立即支付");
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_right.setVisibility(View.VISIBLE);
				break;
			case 50:
				holder.tv_order_status.setText("交易状态：" + "已成功");
				holder.btn_left.setText("删除订单");
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_right.setVisibility(View.GONE);
				break;
			case 60:
				holder.tv_order_status.setText("交易状态：" + "已流标");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setText("取消订单");
				holder.btn_left.setVisibility(View.VISIBLE);
				break;
			case 65:
				holder.tv_order_status.setText("交易状态：" + "超额退款中");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.GONE);
				break;
			case 70:
				holder.tv_order_status.setText("交易状态：" + "已退款");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_left.setText("删除订单");
				break;
			case 75:
				holder.tv_order_status.setText("交易状态：" + "已删除");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.GONE);
				break;
			case 80:
				holder.tv_order_status.setText("交易状态：" + "已发货");
				holder.btn_right.setVisibility(View.VISIBLE);
				holder.btn_right.setText("确认收货");
				holder.btn_left.setText("投诉");
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_right.setVisibility(View.VISIBLE);
				break;
			case 85:
				holder.tv_order_status.setText("交易状态：" + "未中奖");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_left.setText("取消订单");
				break;
			case 90:
				holder.tv_order_status.setText("交易状态：" + "已收货");
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setText("删除订单");
				break;
			case 100:
				holder.tv_order_status.setText("交易状态：" + "申请退款中");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.GONE);
				break;
			case 110:
				holder.tv_order_status.setText("交易状态：" + "已失效");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_left.setText("删除订单");
				break;
			case 120:
				holder.tv_order_status.setText("交易状态：" + "退款处理中");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.GONE);
				break;
			case 130:
				holder.tv_order_status.setText("交易状态：" + "定金已退");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.GONE);
				break;
			case 140:
				holder.tv_order_status.setText("交易状态：" + "付款处理中");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.GONE);
				break;
			case 150:
				holder.tv_order_status.setText("交易状态：" + "中奖");
				holder.btn_right.setVisibility(View.GONE);
				holder.btn_left.setVisibility(View.VISIBLE);
				holder.btn_left.setText("删除订单");
				break;

			default:
				break;
			}

			final String text_left = holder.btn_left.getText().toString()
					.trim();
			holder.btn_left.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (text_left.equals("取消订单")) {

						new AlertDialog(context)
								.builder()
								.setMsg("是否取消该订单？")
								.setPositiveButton("确认取消",
										new OnClickListener() {

											@Override
											public void onClick(View v) {
												submitCancelOrder(info.getId(),
														position);
											}
										})
								.setNegativeButton("稍后再说",
										new OnClickListener() {

											@Override
											public void onClick(View v) {

											}
										}).show();

					} else if (text_left.equals("删除订单")) {
						new AlertDialog(context)
								.builder()
								.setMsg("是否删除该订单？")
								.setPositiveButton("确认删除",
										new OnClickListener() {

											@Override
											public void onClick(View v) {
												submitDeleteOrder(info.getId(),
														position);
											}
										})
								.setNegativeButton("稍后再说",
										new OnClickListener() {

											@Override
											public void onClick(View v) {

											}
										}).show();

					} else if (text_left.equals("投诉")) {
						CommonUtils.callPhone(context, "12345678");
					}
				}
			});

			final String text_right = holder.btn_right.getText().toString()
					.trim();
			holder.btn_right.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (text_right.equals("确认收货")) {
						new AlertDialog(context)
								.builder()
								.setMsg("是否确认收货？")
								.setPositiveButton("确认收货",
										new OnClickListener() {

											@Override
											public void onClick(View v) {
												comfigRecGoods(info.getId(),
														position);
											}
										})
								.setNegativeButton("稍后再说",
										new OnClickListener() {

											@Override
											public void onClick(View v) {

											}
										}).show();

					} else if (text_right.equals("立即支付")) {
						Intent intent = new Intent(context,
								Activity_ThirdWeb.class);
						String url = Service_ThirdPay.PAY_ORDER
								+ "sessionId="
								+ ZCApplication.getInstance().getUserInfo()
										.getSessionId() + "&payAmt="
								+ info.getTotalPrice() + "&bdId="
								+ info.getPrjId() + "&orderNo="
								+ info.getOrderNo() + "&ordId=" + info.getId()
								+ "&type=" + 0;
						intent.putExtra(Activity_ThirdWeb.URL, url);
						intent.putExtra(Activity_ThirdWeb.TITLENAME, "订单支付");
						((FragmentActivity) context).startActivityForResult(
								intent, CODE_PAY);
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
		public TextView title, amount, tv_orderNo, tv_order_status, prjStatus,
				btn_right, btn_left, type_order;
		private ImageView iv_pic, iv_type;
	}

	/**
	 * 1、取消订单
	 * 
	 * @param orderId
	 */
	public void submitCancelOrder(String orderId, final int position) {
		SubmitCancelOrderRequest request = new SubmitCancelOrderRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setOrderId(orderId);
		NetWorkRequestManager.sendRequestPost(context, true, request,
				SubmitCancelPreOrderResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						ToastUtil.showToast(context, "该订单已取消");
						tenderList.get(position).setStatus("20");
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

	/**
	 * 3、确认收货
	 * 
	 * @param orderId
	 * @param postion
	 */
	public void comfigRecGoods(String orderId, final int position) {
		ComfigRecGoodsRequest request = new ComfigRecGoodsRequest();
		request.setOrderId(orderId);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(context, true, request,
				ComfigRecGoodsResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						ToastUtil.showToast(context, "操作成功");
						tenderList.get(position).setStatus("90");// 已收货
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
						ToastUtil.showToast(context, "操作失败");
					}
				});
	}
}
