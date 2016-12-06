package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetMyPreOrderPageListRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyPreOrderPageListResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyPreOrderPageListResponse.ElementPreOrderList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelOrderResponse;
import net.zkbc.p2p.fep.message.protocol.SubmitDeleteOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitDeleteOrderResponse;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 我的——我的预约
 * 
 * @author zp
 *
 *         2016年08月24日
 */
public class Activity_MyBooked extends Activity_Base implements
		OnItemClickListener, OnRefreshListener2<ListView> {
	@ViewInject(R.id.lv_list)
	private PullToRefreshListView lv_list;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;

	private Adapter_MyBooked adapter_list;
	private List<ElementPreOrderList> list = new ArrayList<GetMyPreOrderPageListResponse.ElementPreOrderList>();
	private GetMyPreOrderPageListResponse model;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.list_common);
		x.view().inject(this);
		initView();
		initData();

		getMyPreOrderPageList();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ZCApplication.getInstance().isNeedRefresh) {
			ZCApplication.getInstance().isNeedRefresh = false;
			isPullDown = true;
			pageNo = 1;
			getMyPreOrderPageList();
		}
	}

	@Override
	protected void initView() {
		setTitleText("我的预约");
		setTitleBack();
	}

	@Override
	protected void initData() {

		adapter_list = new Adapter_MyBooked(this);
		lv_list.setAdapter(adapter_list);

		lv_list.setMode(Mode.BOTH);
		lv_list.setOnRefreshListener(this);
		lv_list.setOnItemClickListener(this);
	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(Activity_MyBooked.this,
				Activity_My_BookedDetail.class);
		intent.putExtra("id", list.get(position - 1).getOrderId());
		intent.putExtra("prjId", list.get(position - 1).getPrjId());
		intent.putExtra("froozeAmt", list.get(position - 1).getFrostAmount());

		startActivity(intent);
	}

	public void getMyPreOrderPageList() {
		GetMyPreOrderPageListRequest request = new GetMyPreOrderPageListRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetMyPreOrderPageListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		model = (GetMyPreOrderPageListResponse) response;
		if (isPullDown) {
			adapter_list.deleteData();
			list.clear();
		}
		if (model != null && model.getPreOrderList() != null
				&& model.getPreOrderList().size() > 0) {
			adapter_list.setData(model.getPreOrderList());
			list.addAll(model.getPreOrderList());
			adapter_list.notifyDataSetChanged();
			noLog.setVisibility(View.GONE);
		} else if (isPullDown) {
			ToastUtil.showToast(Activity_MyBooked.this, "暂无数据");
			noLog.setVisibility(View.VISIBLE);
		} else {
			ToastUtil.showToast(Activity_MyBooked.this, "已加载全部");
		}
		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(Activity_MyBooked.this)) {
			lv_list.onRefreshComplete();
			return;
		}
		isPullDown = true;
		pageNo = 1;
		getMyPreOrderPageList();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(Activity_MyBooked.this)) {
			lv_list.onRefreshComplete();
			return;
		}
		isPullDown = false;
		pageNo++;
		getMyPreOrderPageList();
	}

	/**
	 * 因支付和取消项目、删除项目后订单状态有不确定改变（如支付异常、退款异常等状态），所以使用内部类便于操作
	 * 
	 * @author zp
	 *
	 *         2016年9月19日
	 */
	private class Adapter_MyBooked extends BaseAdapter {

		public Context context;
		private ArrayList<ElementPreOrderList> tenderList;
		private ImageOptions options;

		public Adapter_MyBooked(Context context) {
			super();
			this.context = context;
			// 初始化
			tenderList = new ArrayList<ElementPreOrderList>();
			options = new ImageOptions.Builder()
					.setRadius(DensityUtil.dip2px(3))
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
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
				holder.iv_pic = (ImageView) convertView
						.findViewById(R.id.iv_pic);

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
				x.image()
						.bind(holder.iv_pic, info.getHomePicAddress(), options);
				// 项目状态
				String status = info.getPrjBStatus();
				holder.prjBStatus.setText(StatusUtils.getStatusByCode(status));
				// 预约状态??订单状态
				String status_yuyue = info.getDepositStatus();
				holder.orderStatus.setText(StatusUtils
						.getYuyueStatusByCode(status_yuyue));

				if (!StringUtils.isEmpty(status_yuyue)) {
					if (status_yuyue.equals("10")) {// 预约成功
						holder.btn_left.setVisibility(View.VISIBLE);
						holder.btn_right.setVisibility(View.GONE);
						holder.btn_left.setText("取消预约");
					} else if (status_yuyue.equals("20")
							|| status_yuyue.equals("30")
							|| status_yuyue.equals("50")) {// 预约失败、预约取消
						holder.btn_left.setVisibility(View.VISIBLE);
						holder.btn_right.setVisibility(View.GONE);
						holder.btn_left.setText("删除订单");
					} else if (status_yuyue.equals("40")) {// 优先购买中
						holder.btn_left.setVisibility(View.VISIBLE);
						holder.btn_left.setText("取消预约");
						if (status.equals("40")) {// 只有满足优先购买中并且标的状态是众筹中时才显示优先购买或正常购买按钮
							if (info.getIsCanFirstBuy().equals("1")) {
								holder.btn_right.setText("优先购买");
								holder.btn_right.setVisibility(View.VISIBLE);
							} else if (info.getIsCanFirstBuy().equals("2")) {
								holder.btn_right.setText("正常购买");
								holder.btn_right.setVisibility(View.VISIBLE);
							} else {
								holder.btn_right.setVisibility(View.GONE);
							}
						} else {
							holder.btn_right.setVisibility(View.GONE);
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
						new AlertDialog(context)
								.builder()
								.setMsg("取消当前预约将失去优先购买资格，请确认是否要取消预约？")
								.setPositiveButton("立即取消",
										new OnClickListener() {

											@Override
											public void onClick(View v) {
												// submitCancelPreOrder(info.getOrderId(),
												// position);
												submitCancelOrder(
														info.getOrderId(),
														position);
											}
										})
								.setNegativeButton("稍后再说",
										new OnClickListener() {

											@Override
											public void onClick(View v) {

											}
										}).show();
					} else if (text_btn.equals("删除订单")) {
						new AlertDialog(context)
								.builder()
								.setMsg("是否删除该订单？")
								.setPositiveButton("确定", new OnClickListener() {

									@Override
									public void onClick(View v) {
										submitDeleteOrder(info.getOrderId(),
												position);
									}
								})
								.setNegativeButton("取消", new OnClickListener() {

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

					Intent intent = new Intent(context,
							Activity_My_StockBookPay.class);
					intent.putExtra("id", info.getOrderId());
					intent.putExtra("prjId", info.getPrjId());
					context.startActivity(intent);
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

		private class ViewHolder {
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
		public void submitCancelOrder(String orderId, final int position) {
			SubmitCancelOrderRequest request = new SubmitCancelOrderRequest();
			request.setSessionId(ZCApplication.getInstance().getUserInfo()
					.getSessionId());
			request.setOrderId(orderId);
			NetWorkRequestManager.sendRequestPost(context, true, request,
					SubmitCancelOrderResponse.class, new MyRequestCallBack() {

						@Override
						public void onSuccess(ResponseSupport response) {
							ToastUtil.showToast(context, "该订单已取消预约");
							// tenderList.get(position).setDepositStatus("30");
							// notifyDataSetChanged();
							DialogUtils.dismisLoading();
							getMyPreOrderPageList();
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
}
