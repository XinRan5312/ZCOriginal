package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.ComfigRecGoodsRequest;
import net.zkbc.p2p.fep.message.protocol.ComfigRecGoodsResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyOrderPageListRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyOrderPageListResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyOrderPageListResponse.ElementMyOrderList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelPreOrderResponse;
import net.zkbc.p2p.fep.message.protocol.SubmitDeleteOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitDeleteOrderResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_OrderDetail;
import com.minjinsuo.zhongchou.activity.Activity_ThirdWeb;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 我的订单——产品列表/股权列表
 */
public class Fragment_child_MyOrder extends Fragment_Base implements
		OnRefreshListener2<ListView>, OnItemClickListener {

	private View mView;
	@ViewInject(R.id.lv_list)
	private PullToRefreshListView lv_list_Pro;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;

	private Adapter_MyOrder adapter_list;
	private List<ElementMyOrderList> list = new ArrayList<ElementMyOrderList>();
	/** 标志位，标志已经初始化完成 */
	private boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	private boolean mHasLoadedOnce;

	private GetMyOrderPageListResponse model;
	private boolean isProduct;

	public static Fragment_child_MyOrder getInstance(boolean from) {
		Fragment_child_MyOrder f = new Fragment_child_MyOrder();
		Bundle args = new Bundle();
		args.putBoolean(AppContants.FROM, from);
		f.setArguments(args);
		return f;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_child_support, null);
			x.view().inject(this, mView);

			Bundle args = getArguments();
			isProduct = args.getBoolean(AppContants.FROM);
			LogUtil.i("is isProduct--" + isProduct);

			initView();
			initData();
			isPrepared = true;
			lazyLoad();

		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		return mView;
	}

	@Override
	public void onResume() {
		super.onResume();

		if (ZCApplication.getInstance().isNeedRefresh) {
			ZCApplication.getInstance().isNeedRefresh = false;
			pageNo = "1";
			isPullDown = true;
			getMyOrderPageList();
		}
	}

	@Override
	public void initView() {

		lv_list_Pro.setMode(Mode.BOTH);
		lv_list_Pro.setOnRefreshListener(this);

		adapter_list = new Adapter_MyOrder(getActivity());
		lv_list_Pro.setAdapter(adapter_list);
		lv_list_Pro.setOnItemClickListener(this);
	}

	@Override
	public void initData() {

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(getActivity())) {
			lv_list_Pro.onRefreshComplete();
			return;
		}
		pageNo = "1";
		isPullDown = true;
		getMyOrderPageList();

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(getActivity())) {
			lv_list_Pro.onRefreshComplete();
			return;
		}
		pageNo = (Integer.parseInt(pageNo) + 1) + "";
		isPullDown = false;
		getMyOrderPageList();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), Activity_OrderDetail.class);
		intent.putExtra("id", list.get(position - 1).getId());
		intent.putExtra("prjId", list.get(position - 1).getPrjId());
		if (isProduct) {
			intent.putExtra("flag", "product");
		} else {
			intent.putExtra("flag", "stock");
		}
		startActivity(intent);
	}

	@Override
	protected void initListener() {

	}

	private void getMyOrderPageList() {
		GetMyOrderPageListRequest request = new GetMyOrderPageListRequest();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		if (isProduct) {
			request.setProdId("1");// 1:代表产品，0:代表股权
		} else {
			request.setProdId("0");
		}
		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				GetMyOrderPageListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		mHasLoadedOnce = true;
		model = (GetMyOrderPageListResponse) response;
		if (isPullDown) {
			adapter_list.deleteData();
			list.clear();
		}
		if (model != null && model.getMyOrderList() != null
				&& model.getMyOrderList().size() > 0) {
			adapter_list.setData(model.getMyOrderList());
			list.addAll(model.getMyOrderList());
			noLog.setVisibility(View.GONE);
		} else if (isPullDown) {
			ToastUtil.showToast(getActivity(), "暂无数据");
			noLog.setVisibility(View.VISIBLE);
		} else {
			ToastUtil.showToast(getActivity(), "已加载全部数据");
		}
		adapter_list.notifyDataSetChanged();
		DialogUtils.dismisLoading();
		lv_list_Pro.onRefreshComplete();
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		lv_list_Pro.onRefreshComplete();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		lv_list_Pro.onRefreshComplete();
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		}
		getMyOrderPageList();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Adapter_MyOrder.CODE_PAY) {
			String msg_result = data.getStringExtra("message");
			if (resultCode == getActivity().RESULT_OK) {
				ToastUtil.showToast(getActivity(), "支付成功");
			}

			isPullDown = true;
			pageNo = "1";
			getMyOrderPageList();
		}
	}

	/**
	 * 我的订单数据适配器——因支付和取消项目、删除项目后要对列表做一系列处理，所以使用内部类
	 */
	private class Adapter_MyOrder extends BaseAdapter {

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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_supported, null);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.amount = (TextView) convertView
						.findViewById(R.id.amount);
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
				holder.iv_pic = (ImageView) convertView
						.findViewById(R.id.iv_pic);
				holder.iv_type = (ImageView) convertView
						.findViewById(R.id.iv_type);
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

				holder.iv_type.setVisibility(View.GONE);
				holder.type_order.setVisibility(View.VISIBLE);
				holder.type_order.setText(info.getPrjTrade() == null ? "其他"
						: info.getPrjTrade());
				// 随机设置行业随机颜色
				GradientDrawable p = (GradientDrawable) holder.type_order
						.getBackground();
				int r = (int) (Math.random() * 240 + 10);
				int g = (int) (Math.random() * 240 + 10);
				int b = (int) (Math.random() * 240 + 10);
				int color = Color.argb(111, r, g, b); // 半透明的紫色
				p.setColor(color);

				// 项目状态
				String status_prj = info.getPrjBStatus();
				holder.prjStatus.setText(StatusUtils
						.getStatusByCode(status_prj));

				// 订单状态
				String status = info.getStatus();
				if (!StringUtils.isEmpty(status)) {
					holder.tv_order_status.setText(StatusUtils
							.getOrderStatusByCode(status));
				}
				int statusCode = Integer.parseInt(status);
				/*
				 * map.put("10", "未支付"); map.put("20", "订单取消"); map.put("40",
				 * "支付成功"); map.put("41", "支付失败"); map.put("50", "未发货");
				 * map.put("70", "退款成功"); map.put("80", "已发货"); map.put("90",
				 * "已收货"); map.put("110", "订单超时"); map.put("120", "退款中");
				 * map.put("140", "付款中");
				 */
				switch (statusCode) {
				case 10:
					holder.tv_order_status.setText("交易状态：" + "未支付");
					holder.btn_left.setText("取消项目");
					holder.btn_right.setText("立即支付");
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_right.setVisibility(View.VISIBLE);
					if (!StringUtils.isEmpty(info.getIsCancelOrder())) {
						if (info.getIsCancelOrder().equals("1")) {
							holder.btn_left.setVisibility(View.VISIBLE);
						} else {
							holder.btn_left.setVisibility(View.GONE);
						}
					}
					break;
				case 20:
					holder.tv_order_status.setText("交易状态：" + "订单取消");
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_left.setText("删除订单");
					holder.btn_right.setVisibility(View.GONE);
					break;
				case 40:
					holder.tv_order_status.setText("交易状态：" + "支付成功");
					holder.btn_left.setText("取消订单");
					holder.btn_right.setVisibility(View.GONE);
					holder.btn_left.setVisibility(View.VISIBLE);
					if (!StringUtils.isEmpty(info.getIsCancelOrder())) {
						if (info.getIsCancelOrder().equals("1")) {
							holder.btn_left.setVisibility(View.VISIBLE);
						} else {
							holder.btn_left.setVisibility(View.GONE);
						}
					}
					break;
				case 41:
					holder.tv_order_status.setText("交易状态：" + "支付失败");
					holder.btn_left.setText("删除订单");
					holder.btn_right.setText("立即支付");
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_right.setVisibility(View.VISIBLE);
					if (!StringUtils.isEmpty(info.getIsCancelOrder())) {
						if (info.getIsCancelOrder().equals("1")) {
							holder.btn_left.setVisibility(View.VISIBLE);
						} else {
							holder.btn_left.setVisibility(View.GONE);
						}
					}
					break;

				case 50:
					holder.tv_order_status.setText("交易状态：" + "未发货");
					holder.btn_left.setVisibility(View.GONE);
					holder.btn_right.setVisibility(View.GONE);
					break;
				case 70:
					holder.tv_order_status.setText("交易状态：" + "退款成功");
					holder.btn_right.setVisibility(View.GONE);
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_left.setText("删除订单");
					break;
				case 80:
					holder.tv_order_status.setText("交易状态：" + "已发货");
					holder.btn_right.setVisibility(View.VISIBLE);
					holder.btn_right.setText("确认收货");
					holder.btn_left.setText("联系客服");
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_right.setVisibility(View.VISIBLE);
					break;
				case 90:
					holder.tv_order_status.setText("交易状态：" + "已收货");
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_right.setVisibility(View.GONE);
					holder.btn_left.setText("删除订单");
					break;
				case 110:
					holder.tv_order_status.setText("交易状态：" + "订单超时");
					holder.btn_left.setVisibility(View.VISIBLE);
					holder.btn_right.setVisibility(View.GONE);
					holder.btn_left.setText("删除订单");
					break;
				case 120:
					holder.tv_order_status.setText("交易状态：" + "退款中");
					holder.btn_right.setVisibility(View.GONE);
					holder.btn_left.setVisibility(View.GONE);
					break;
				case 140:
					holder.tv_order_status.setText("交易状态：" + "付款中");
					holder.btn_right.setVisibility(View.GONE);
					holder.btn_left.setVisibility(View.GONE);
					break;

				default:
					break;
				}

				final String text_left = holder.btn_left.getText().toString()
						.trim();
				holder.btn_left.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (text_left.contains("取消")) {

							new AlertDialog(context)
									.builder()
									.setMsg("是否取消该订单？")
									.setPositiveButton("确认取消",
											new OnClickListener() {

												@Override
												public void onClick(View v) {
													submitCancelOrder(
															info.getId(),
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
													submitDeleteOrder(
															info.getId(),
															position);
												}
											})
									.setNegativeButton("稍后再说",
											new OnClickListener() {

												@Override
												public void onClick(View v) {

												}
											}).show();

						} else if (text_left.equals("联系客服")) {
							CommonUtils.callPhone(
									context,
									info.getServicePhone() == null ? "1" : info
											.getServicePhone());// zpqa？暂无客服电话
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
													comfigRecGoods(
															info.getId(),
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
									+ info.getOrderNo() + "&ordId="
									+ info.getId() + "&type=" + 0;
							intent.putExtra(Activity_ThirdWeb.URL, url);
							intent.putExtra(Activity_ThirdWeb.TITLENAME, "订单支付");
							((FragmentActivity) context)
									.startActivityForResult(intent, CODE_PAY);
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

		private class ViewHolder {
			public TextView title, amount, tv_orderNo, tv_order_status,
					prjStatus, btn_right, btn_left, type_order;
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
					SubmitCancelPreOrderResponse.class,
					new MyRequestCallBack() {

						@Override
						public void onSuccess(ResponseSupport response) {
							ToastUtil.showToast(context, "该订单已取消");
							DialogUtils.dismisLoading();

							pageNo = "1";
							isPullDown = true;
							getMyOrderPageList();
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
							// tenderList.get(position).setStatus("90");// 已收货
							// notifyDataSetChanged();

							DialogUtils.dismisLoading();
							pageNo = "1";
							isPullDown = true;
							getMyOrderPageList();
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

}
