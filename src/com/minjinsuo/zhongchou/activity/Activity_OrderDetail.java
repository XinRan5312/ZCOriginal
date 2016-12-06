package com.minjinsuo.zhongchou.activity;

import java.io.UnsupportedEncodingException;

import net.zkbc.p2p.fep.message.protocol.ComfigRecGoodsRequest;
import net.zkbc.p2p.fep.message.protocol.ComfigRecGoodsResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyProdOrderByIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyProdOrderByIdResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyStockOrderByIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyStockOrderByIdResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelPreOrderResponse;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
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
 * 订单(产品详情)详情
 * 
 * @author zp
 *
 *         2016年6月28日
 */
public class Activity_OrderDetail extends Activity_Base implements
		OnItemClickListener {
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;
	@ViewInject(R.id.btn_cancle)
	private Button btn_cancle;
	@ViewInject(R.id.tv_expand)
	private TextView tv_expand;// 展开
	@ViewInject(R.id.iv_arrowdown)
	private ImageView iv_arrowdown;// 展开箭头
	@ViewInject(R.id.ll_expand)
	private ImageView ll_expand;// 展开布局
	@ViewInject(R.id.tv_content_expand)
	private TextView tv_content_expand;// 展开内容
	@ViewInject(R.id.btn_callHim)
	private Button btn_callHim;
	@ViewInject(R.id.btn_sendForHim)
	private Button btn_sendForHim;// 私信 发起人

	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.status_loan)
	private TextView status_loan;
	@ViewInject(R.id.totalPrice)
	private TextView totalPrice;
	@ViewInject(R.id.supportAmt)
	private TextView supportAmt;
	@ViewInject(R.id.feeYun)
	private TextView feeYun;
	@ViewInject(R.id.orderNo)
	private TextView orderNo;
	@ViewInject(R.id.status_order)
	private TextView status_order;
	@ViewInject(R.id.beizhu)
	private TextView beizhu;
	@ViewInject(R.id.content_rec)
	private TextView content_rec;
	@ViewInject(R.id.recv_name)
	private TextView recv_name;
	@ViewInject(R.id.recv_phone)
	private TextView recv_phone;
	@ViewInject(R.id.recv_add)
	private TextView recv_add;
	@ViewInject(R.id.name_faqi)
	private TextView name_faqi;// 项目发起人

	@ViewInject(R.id.iv_faqi)
	private ImageView iv_faqi;
	@ViewInject(R.id.ll_container)
	private LinearLayout ll_container;// 存放多张图片的容器
	@ViewInject(R.id.layout_recv)
	private LinearLayout layout_recv;// 回报内容布局视图（如果是股权点击进入则不显示该布局）
	@ViewInject(R.id.ll_biezhu)
	private LinearLayout ll_biezhu;// （如果是股权点击进入则不显示该布局）
	@ViewInject(R.id.ll_feeYun)
	private LinearLayout ll_feeYun;// （如果是股权点击进入则不显示该布局）

	// 下面是 股权详情时要显示的view
	@ViewInject(R.id.layout_youhuiAmt)
	private LinearLayout layout_youhuiAmt;
	@ViewInject(R.id.tv_youhuiAmt)
	private TextView tv_youhuiAmt;// 优惠金额
	@ViewInject(R.id.layout_persentStock)
	private LinearLayout layout_persentStock;
	@ViewInject(R.id.tv_persentStock)
	private TextView tv_persentStock;// 预计所占股比

	private boolean isNeedExpand = true;// 控制是否展开
	private String custMobile = "";// 要拨打的发起人联系电话

	private String orderId = "";
	private String prjId = "";
	private String custId = "";// 传递到发私信页面，该登录用户id
	private String flag = "product";// product:标示从产品订单列表点击而来，否则代表股权
	private static String text_cancle = "取消项目";
	private static String text_investNow = "立即支付";
	private String totalAmt = "";
	private String custName = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_order_detail);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleText("订单详情");
		setTitleBack();
		btn_cancle.setText(text_cancle);
		btn_ok.setText(text_investNow);

		if (getIntent() != null) {
			if (!StringUtils.isEmpty(getIntent().getStringExtra("flag"))) {

				flag = getIntent().getStringExtra("flag");// 区分产品还是股权
				if (!flag.equals("product")) {
					// 股权
					layout_recv.setVisibility(View.GONE);
					ll_feeYun.setVisibility(View.GONE);
					ll_biezhu.setVisibility(View.GONE);
					layout_youhuiAmt.setVisibility(View.VISIBLE);
					layout_persentStock.setVisibility(View.VISIBLE);
				}
			}
			if (!StringUtils.isEmpty(getIntent().getStringExtra("id"))) {
				orderId = getIntent().getStringExtra("id");
				prjId = getIntent().getStringExtra("prjId");

				if (flag.equals("product")) {
					// 获取产品订单详情
					getMyProdOrderById();
				} else {
					// 获取股权订单详情
					getMyStockOrderById();
				}
			}
		}
	}

	@Override
	protected void initData() {
		String safeTip = "";
		if (StringUtils.isEmpty(safeTip)) {
			byte b[] = CommonUtils.getData(this, "safeTip.txt");
			String str = "";
			try {
				str = new String(b, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			safeTip = str;
			tv_content_expand.setText(safeTip);
		}
	}

	@Override
	protected void initListener() {
		orderNo.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				StringUtils.copyText(Activity_OrderDetail.this, orderNo
						.getText().toString().trim());
				return false;
			}
		});
	}

	@Event({ R.id.tv_expand, R.id.iv_arrowdown, R.id.ll_expand, R.id.btn_ok,
			R.id.btn_cancle, R.id.btn_callHim, R.id.btn_sendForHim })
	private void OnClick(View view) {
		switch (view.getId()) {
		case R.id.tv_expand:
		case R.id.iv_arrowdown:
		case R.id.ll_expand:
			// 展开风险描述详情
			if (isNeedExpand) {
				isNeedExpand = false;
				tv_content_expand.setEllipsize(null); // 展开
				tv_content_expand.setSingleLine(isNeedExpand);
				tv_expand.setText("收回");

				startArrowAnim(!isNeedExpand);
			} else {
				isNeedExpand = true;
				tv_content_expand.setEllipsize(TextUtils.TruncateAt.END); // 收缩
				tv_content_expand.setLines(5);
				tv_expand.setText("展开");

				startArrowAnimInit(!isNeedExpand);
			}

			break;
		case R.id.btn_sendForHim:
			Intent intent = new Intent(Activity_OrderDetail.this,
					Activity_MsgSend.class);
			intent.putExtra("name", custName);
			intent.putExtra("custId", custId);
			startActivity(intent);
			break;
		case R.id.btn_callHim:
			if (!StringUtils.isEmpty(custMobile)) {
				CommonUtils.callPhone(Activity_OrderDetail.this, custMobile);
			} else {
				ToastUtil.showToast(Activity_OrderDetail.this, "发起人电话为空");
			}
			break;
		case R.id.btn_ok:
			if (btn_ok.getText().toString().equals(text_investNow)) {
				new AlertDialog(Activity_OrderDetail.this).builder()
						.setMsg("是否立即支付该项目？")
						.setPositiveButton("立即支付", new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										Activity_OrderDetail.this,
										Activity_ThirdWeb.class);
								String url = Service_ThirdPay.PAY_ORDER
										+ "sessionId="
										+ ZCApplication.getInstance()
												.getUserInfo().getSessionId()
										+ "&payAmt=" + totalAmt + "&bdId="
										+ prjId + "&orderNo="
										+ orderNo.getText().toString().trim()
										+ "&ordId=" + orderId + "&type=" + 0;
								intent.putExtra(Activity_ThirdWeb.URL, url);
								intent.putExtra(Activity_ThirdWeb.TITLENAME,
										"订单支付");
								startActivityForResult(intent, 001);
							}
						}).setNegativeButton("稍后再说", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();
			} else {
				// 确认收货
				new AlertDialog(Activity_OrderDetail.this).builder()
						.setMsg("是否确认收货？")
						.setPositiveButton("确认收货", new OnClickListener() {

							@Override
							public void onClick(View v) {
								comfigRecGoods(orderId);
							}
						}).setNegativeButton("稍后再说", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();

			}
			break;
		case R.id.btn_cancle:
			if (btn_cancle.getText().toString().trim().equals(text_cancle)) {
				new AlertDialog(Activity_OrderDetail.this).builder()
						.setMsg("是否取消该项目？")
						.setPositiveButton("取消项目", new OnClickListener() {

							@Override
							public void onClick(View v) {
								submitCancelOrder(orderId);
							}
						}).setNegativeButton("稍后再说", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();
			} else {
				// 投诉
				CommonUtils.callPhone(Activity_OrderDetail.this, "12345678");// zpqa??暂时缺少投诉电话
			}
			break;
		default:
			break;
		}
	}

	public void startArrowAnim(boolean flag) {
		Animation rotate = AnimationUtils.loadAnimation(this,
				R.anim.rotate_arrow);// 创建动画
		rotate.setInterpolator(new LinearInterpolator());// 设置为线性旋转
		rotate.setFillAfter(true);
		iv_arrowdown.startAnimation(rotate);
	}

	public void startArrowAnimInit(boolean flag) {
		Animation rotate = AnimationUtils.loadAnimation(this,
				R.anim.rotate_arrow_up);// 创建动画
		rotate.setInterpolator(new LinearInterpolator());// 设置为线性旋转
		rotate.setFillAfter(true);
		iv_arrowdown.startAnimation(rotate);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	/**
	 * 获取产品订单详情
	 */
	public void getMyProdOrderById() {
		GetMyProdOrderByIdRequest request = new GetMyProdOrderByIdRequest();
		request.setOrderId(orderId);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetMyProdOrderByIdResponse.class, this);
	}

	/**
	 * 获取股权订单详情
	 */
	public void getMyStockOrderById() {
		GetMyStockOrderByIdRequest request = new GetMyStockOrderByIdRequest();
		request.setOrderId(orderId);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetMyStockOrderByIdResponse.class, this);
	}

	/**
	 * 取消预约订单
	 * 
	 * @param orderId
	 */
	public void submitCancelOrder(String orderId) {
		SubmitCancelOrderRequest request = new SubmitCancelOrderRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setOrderId(orderId);
		NetWorkRequestManager.sendRequestPost(Activity_OrderDetail.this, true,
				request, SubmitCancelPreOrderResponse.class,
				new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						DialogUtils.dismisLoading();
						new AlertDialog(Activity_OrderDetail.this).builder()
								.setMsg("该订单已取消")
								.setPositiveButton("确定", new OnClickListener() {

									@Override
									public void onClick(View v) {
										ZCApplication.getInstance().isNeedRefresh = true;
										finish();
									}
								}).show();
					}

					@Override
					public void onFailure(ResponseSupport response) {
						DialogUtils.dismisLoading();
						ToastUtil.cancelToast();
						if (!StringUtils.isEmpty(response.getMessage())) {
							new AlertDialog(Activity_OrderDetail.this)
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
	 * 3、确认收货
	 * 
	 * @param orderId
	 * @param postion
	 */
	public void comfigRecGoods(String orderId) {
		ComfigRecGoodsRequest request = new ComfigRecGoodsRequest();
		request.setOrderId(orderId);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(Activity_OrderDetail.this, true,
				request, ComfigRecGoodsResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						DialogUtils.dismisLoading();

						DialogUtils.dismisLoading();
						new AlertDialog(Activity_OrderDetail.this).builder()
								.setMsg("收货成功")
								.setPositiveButton("确定", new OnClickListener() {

									@Override
									public void onClick(View v) {
										ZCApplication.getInstance().isNeedRefresh = true;
										finish();
									}
								}).show();

					}

					@Override
					public void onFailure(ResponseSupport response) {
						DialogUtils.dismisLoading();
						ToastUtil.cancelToast();
						if (!StringUtils.isEmpty(response.getMessage())) {
							new AlertDialog(Activity_OrderDetail.this)
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
						ToastUtil.showToast(Activity_OrderDetail.this, "操作失败");
					}
				});
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getMyProdOrderById":
			GetMyProdOrderByIdResponse model = (GetMyProdOrderByIdResponse) response;
			if (model != null) {
				commpleteUI(model);
			}
			break;
		case "getMyStockOrderById":
			GetMyStockOrderByIdResponse model_stock = (GetMyStockOrderByIdResponse) response;
			if (model_stock != null) {
				commpleteUI4Stock(model_stock);
			}
			break;

		default:
			break;
		}

		DialogUtils.dismisLoading();
	}

	@Override
	public void failure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	/**
	 * 填充产品详情页数据
	 * 
	 * @param model
	 */
	public void commpleteUI(GetMyProdOrderByIdResponse model) {
		if (model != null) {
			custId = model.getCustId();
			custMobile = model.getCustMobile();
			title.setText(model.getPrjNam());
			totalAmt = model.getTotalPrice();
			totalPrice.setText("￥" + model.getTotalPrice());
			supportAmt.setText("￥" + model.getRealPayAmt());
			feeYun.setText("￥" + model.getFeeFreight());
			orderNo.setText(model.getOrderNo());
			status_order.setText(StatusUtils.getOrderStatusByCode(model
					.getOrderStatus()));// 订单状态(交易状态)
			boolean isCancelOrder = false;
			if (!StringUtils.isEmpty(model.getIsCancelOrder())) {
				isCancelOrder = model.getIsCancelOrder().equals("1") ? true
						: false;
			}
			if (!StringUtils.isEmpty(model.getOrderStatus())) {
				int status_order = Integer.parseInt(model.getOrderStatus());
				initBottomBtn(status_order, isCancelOrder);
			}

			beizhu.setText(model.getCustRemark());
			recv_name.setText(model.getRecvNam());
			recv_phone.setText(model.getRecvMobile());
			recv_add.setText("收货地址：" + model.getAddr());
			content_rec.setText(model.getRwdContent());// 回报内容
			name_faqi.setText("项目发起人：" + model.getCustName());
			custName = model.getCustName();
			// 下面处理回报图片的显示
			ImageOptions options = new ImageOptions.Builder()
					.setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
					.setRadius(DensityUtil.dip2px(5)).setCrop(true)
					.setIgnoreGif(false).setCircular(false)
					.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
					.setLoadingDrawableId(R.drawable.loading)
					.setFailureDrawableId(R.drawable.icon_faild).build();
			x.image().bind(iv_faqi, model.getCustPortraitAddr(), options);// 项目发起人头像
			if (model.getRwdPicFileAddr() != null
					&& !StringUtils.isEmpty(model.getRwdPicFileAddr())) {
				String[] strArr = {};
				if (model.getRwdPicFileAddr().contains(",")) {// 多张图片时
					strArr = model.getRwdPicFileAddr().split(",");
					for (int i = 0; i < strArr.length; i++) {
						ImageView iv_pic = new ImageView(this);
						iv_pic.setPadding(0, DensityUtil.dip2px(10),
								DensityUtil.dip2px(10), 0);

						x.image().bind(iv_pic, strArr[i], options);
						ll_container.addView(iv_pic);
					}
				} else {// 单张图片时
					ImageView iv_pic = new ImageView(this);
					iv_pic.setPadding(0, DensityUtil.dip2px(10),
							DensityUtil.dip2px(10), 0);

					x.image().bind(iv_pic, model.getRwdPicFileAddr(), options);
					ll_container.addView(iv_pic);
				}
			}
			// 项目状态
			String status = model.getPrjBStatus();

			if (!StringUtils.isEmpty(status)) {
				status_loan.setVisibility(View.VISIBLE);
				if ("10".equals(status)) {
					status_loan.setText("待预热");
					status_loan
							.setBackgroundResource(R.drawable.shap_green_radussmall);
				} else if ("20".equals(status)) {
					status_loan.setText("预热中");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("30".equals(status)) {
					status_loan.setText("待筹款");
					status_loan
							.setBackgroundResource(R.drawable.shap_green_radussmall);
				} else if ("35".equals(status)) {
					status_loan.setText("预热流标");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("40".equals(status)) {
					status_loan.setText("筹款中");
					status_loan
							.setBackgroundResource(R.drawable.shap_green_radussmall);
				} else if ("45".equals(status)) {
					status_loan.setText("筹款结束");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("50".equals(status)) {
					status_loan.setText("筹款满标");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("55".equals(status)) {
					status_loan.setText("筹款流标");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("60".equals(status)) {
					status_loan.setText("项目成功");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("70".equals(status)) {
					status_loan.setText("成功结项");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("80".equals(status)) {
					status_loan.setText("失败结项");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				}

				status_loan.setText(StatusUtils.getStatusByCode(status));
			}
		}
	}

	/**
	 * 填充股权详情页数据
	 * 
	 * @param model
	 */
	public void commpleteUI4Stock(GetMyStockOrderByIdResponse model) {
		if (model != null) {
			custId = model.getCustId();
			custMobile = model.getCustMobile();
			title.setText(model.getPrjNam());
			totalAmt = model.getTotalPrice();
			totalPrice.setText("￥" + model.getTotalPrice());
			supportAmt.setText("￥" + model.getRealPayAmt());
			tv_persentStock.setText(model.getStockPct() + "%");// 预计所占股比
			Double youhuiAmt = Double.parseDouble(model.getTotalPrice())
					- Double.parseDouble(model.getRealPayAmt());
			tv_youhuiAmt.setText("￥" + youhuiAmt);// 优惠金额
			orderNo.setText(model.getOrderNo());
			status_order.setText(StatusUtils.getOrderStatusByCode(model
					.getOrderStatus()));// 订单状态
			boolean isCancelOrder = false;
			if (!StringUtils.isEmpty(model.getIsCancelOrder())) {
				isCancelOrder = model.getIsCancelOrder().equals("1") ? true
						: false;
			}
			if (!StringUtils.isEmpty(model.getOrderStatus())) {
				int status_order = Integer.parseInt(model.getOrderStatus());
				initBottomBtn(status_order, isCancelOrder);
			}
			recv_name.setText(model.getRecvNam());
			recv_phone.setText(model.getRecvMobile());
			recv_add.setText("收货地址：" + model.getRecvAddress());
			name_faqi.setText("项目发起人：" + model.getCustName());
			custName = model.getCustName();
			// 下面处理图片的显示
			ImageOptions options = new ImageOptions.Builder()
					.setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
					.setRadius(DensityUtil.dip2px(5)).setCrop(true)
					.setIgnoreGif(false).setCircular(false)
					.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
					.setLoadingDrawableId(R.drawable.loading)
					.setFailureDrawableId(R.drawable.icon_faild).build();
			x.image().bind(iv_faqi, model.getCustPortraitAddr(), options);// 项目发起人头像

			// 项目状态
			String status = model.getPrjBStatus();
			if (!StringUtils.isEmpty(status)) {
				status_loan.setVisibility(View.VISIBLE);
				if ("10".equals(status)) {
					status_loan.setText("待预热");
					status_loan
							.setBackgroundResource(R.drawable.shap_green_radussmall);
				} else if ("20".equals(status)) {
					status_loan.setText("预热中");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("30".equals(status)) {
					status_loan.setText("待筹款");
					status_loan
							.setBackgroundResource(R.drawable.shap_green_radussmall);
				} else if ("35".equals(status)) {
					status_loan.setText("预热流标");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("40".equals(status)) {
					status_loan.setText("筹款中");
					status_loan
							.setBackgroundResource(R.drawable.shap_green_radussmall);
				} else if ("45".equals(status)) {
					status_loan.setText("筹款结束");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("50".equals(status)) {
					status_loan.setText("筹款满标");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("55".equals(status)) {
					status_loan.setText("筹款流标");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("60".equals(status)) {
					status_loan.setText("项目成功");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("70".equals(status)) {
					status_loan.setText("成功结项");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				} else if ("80".equals(status)) {
					status_loan.setText("失败结项");
					status_loan
							.setBackgroundResource(R.drawable.shap_red_radussmall);
				}

			}
		}
	}

	/**
	 * 底部取消和支付按钮
	 * 
	 * @param status_order
	 */
	public void initBottomBtn(int status_order, boolean isCancelOrder) {
		/*
		 * map.put("10", "未支付"); map.put("20", "订单取消"); map.put("40", "支付成功");
		 * map.put("41", "支付失败"); map.put("50", "未发货"); map.put("70", "退款成功");
		 * map.put("80", "已发货"); map.put("90", "已收货"); map.put("110", "订单超时");
		 * map.put("120", "退款中"); map.put("140", "付款中");
		 */
		switch (status_order) {
		case 10:
			btn_cancle.setText(text_cancle);
			btn_ok.setText(text_investNow);
			btn_cancle.setVisibility(View.VISIBLE);
			btn_ok.setVisibility(View.VISIBLE);
			break;
		case 40:
			btn_cancle.setText(text_cancle);
			btn_ok.setVisibility(View.GONE);
			btn_cancle.setVisibility(View.VISIBLE);
			break;
		case 41:
			btn_ok.setText(text_investNow);
			btn_cancle.setVisibility(View.GONE);
			btn_ok.setVisibility(View.VISIBLE);
			break;
		case 80:
			btn_ok.setVisibility(View.VISIBLE);
			btn_ok.setText("确认收货");
			btn_cancle.setText("联系客服");
			btn_cancle.setVisibility(View.VISIBLE);
			btn_ok.setVisibility(View.VISIBLE);
			break;
		case 20:
		case 50:
		case 70:
		case 90:
		case 110:
		case 120:
		case 140:
			btn_cancle.setVisibility(View.GONE);
			btn_ok.setVisibility(View.GONE);
			break;
		default:
			break;
		}
		if (isCancelOrder) {
			btn_cancle.setVisibility(View.VISIBLE);
		} else {
			btn_cancle.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);

		if (arg0 == 001) {
			if (arg1 == RESULT_OK) {
				new AlertDialog(Activity_OrderDetail.this).builder()
						.setMsg("支付成功")
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {
								ZCApplication.getInstance().isNeedRefresh = true;
								finish();
							}
						}).show();
			} else {
				LogUtil.i("支付失败~~");
				ZCApplication.getInstance().isNeedRefresh = true;
				finish();
			}
		}
	}
}
