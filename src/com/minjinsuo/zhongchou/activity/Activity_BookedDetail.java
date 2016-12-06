package com.minjinsuo.zhongchou.activity;

import java.io.UnsupportedEncodingException;

import net.zkbc.p2p.fep.message.protocol.GetMyPreProdOrderByIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyPreProdOrderByIdResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelPreOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCancelPreOrderResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.BusProvider;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 预约详情页面
 * 
 * @author zp
 *
 *         2016年8月24日
 */
public class Activity_BookedDetail extends Activity_Base implements
		OnItemClickListener {
	@ViewInject(R.id.tv_expand)
	private TextView tv_expand;// 展开
	@ViewInject(R.id.iv_arrowdown)
	private ImageView iv_arrowdown;// 展开箭头
	@ViewInject(R.id.ll_expand)
	private ImageView ll_expand;// 展开布局
	@ViewInject(R.id.tv_content_expand)
	private TextView tv_content_expand;// 展开内容

	@ViewInject(R.id.tv_per_amount)
	private TextView tv_per_amount;
	@ViewInject(R.id.tv_support_num)
	private TextView tv_support_num;
	@ViewInject(R.id.tv_invest_amt)
	private TextView tv_invest_amt;
	@ViewInject(R.id.persent_stock)
	private TextView persent_stock;// 预约所占股比
	@ViewInject(R.id.persent_amt)
	private TextView persent_amt;// 预约金比
	@ViewInject(R.id.tv_yuyuejin)
	private TextView tv_yuyuejin;// 预约金
	@ViewInject(R.id.time_duration)
	private TextView time_duration;// 购买期
	@ViewInject(R.id.tv_prj_title)
	private TextView tv_prj_title;
	@ViewInject(R.id.tv_prj_content)
	private TextView tv_prj_content;
	@ViewInject(R.id.orderNo)
	private TextView orderNo;
	@ViewInject(R.id.tv_status)
	private TextView tv_status;
	@ViewInject(R.id.tv_froozeAmt)
	private TextView tv_froozeAmt;
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	private boolean isNeedExpand = true;// 控制是否展开
	private int support_num = 0;// 支持的份数

	private String orderId = "";
	private String prjId = "";
	private String orderNo_params = "";
	private String froozeAmt = "";// 冻结金额
	private static String text_cancle = "取消预约";
	private static String text_firstBuy = "优先购买";
	private static String text_investNomal = "正常购买";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_booked_detail);
		x.view().inject(this);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		setTitleText("预约详情");
		setTitleBack();

		if (getIntent() != null
				&& !StringUtils.isEmpty(getIntent().getStringExtra("id"))) {
			orderId = getIntent().getStringExtra("id");
			prjId = getIntent().getStringExtra("prjId");
			froozeAmt = getIntent().getStringExtra("froozeAmt");

			getMyPreProdOrderById();
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

	}

	@Event({ R.id.tv_expand, R.id.iv_arrowdown, R.id.ll_expand, R.id.btn_ok })
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

		case R.id.btn_ok:
			if (btn_ok.getText().toString().equals(text_cancle)) {
				new AlertDialog(Activity_BookedDetail.this).builder()
						.setMsg("取消当前预约将失去优先购买资格，请确认是否要取消预约？")
						.setPositiveButton("取消预约", new OnClickListener() {

							@Override
							public void onClick(View v) {
								submitCancelPreOrder(orderId);
							}
						}).setNegativeButton("稍后再说", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();
			} else {// 购买
				Intent intent = new Intent(Activity_BookedDetail.this,
						Activity_ThirdWeb.class);
				String url = Service_ThirdPay.PAY_ORDER
						+ "sessionId="
						+ ZCApplication.getInstance().getUserInfo()
								.getSessionId() + "&payAmt="
						+ tv_invest_amt.getText().toString().trim() + "&bdId="
						+ prjId + "&orderNo=" + orderNo_params + "&ordId="
						+ orderId + "&type=" + 0;
				intent.putExtra(Activity_ThirdWeb.URL, url);
				intent.putExtra(Activity_ThirdWeb.TITLENAME, "订单支付");
				startActivityForResult(intent, 001);
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
	 * 1、获取订单详情
	 */
	public void getMyPreProdOrderById() {
		GetMyPreProdOrderByIdRequest request = new GetMyPreProdOrderByIdRequest();
		request.setOrderId(orderId);
		// request.setOrderId("1062");// zpqa暂时写死
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetMyPreProdOrderByIdResponse.class, this);
	}

	/**
	 * 2、取消预约订单
	 * 
	 * @param orderId
	 */
	public void submitCancelPreOrder(String orderId) {
		SubmitCancelPreOrderRequest request = new SubmitCancelPreOrderRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setOrderId(orderId);
		NetWorkRequestManager.sendRequestPost(Activity_BookedDetail.this, true,
				request, SubmitCancelPreOrderResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getMyPreProdOrderById":
			GetMyPreProdOrderByIdResponse model = (GetMyPreProdOrderByIdResponse) response;
			if (model != null) {
				commpleteUI(model);
			}
			DialogUtils.dismisLoading();
			break;
		case "submitCancelPreOrder":
			DialogUtils.dismisLoading();
			new AlertDialog(Activity_BookedDetail.this).builder()
					.setMsg("该订单已取消预约")
					.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().isNeedRefresh = true;
							finish();
						}
					}).show();

			break;

		default:
			break;
		}
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		switch (response.getMessageId()) {
		case "getMyPreProdOrderById":
			break;
		case "submitCancelPreOrder":
			ToastUtil.cancelToast();
			if (!StringUtils.isEmpty(response.getMessage())) {
				new AlertDialog(Activity_BookedDetail.this).builder()
						.setMsg(response.getMessage())
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();
			}
			break;

		default:
			break;
		}
	}

	public void commpleteUI(GetMyPreProdOrderByIdResponse model) {
		tv_froozeAmt.setText(froozeAmt);// 冻结金额
		if (model == null) {
			return;
		}
		// 预约状态
		String status_yuyue = model.getDepositStatus();
		if (!StringUtils.isEmpty(model.getDepositStatus())) {
			if (status_yuyue.equals("10")) {
				btn_ok.setVisibility(View.VISIBLE);
				btn_ok.setText(text_cancle);
			} else if (status_yuyue.equals("40")) {

				if (model.getIsCanFirstBuy().equals("0")) {
					LogUtil.i("0-否,1-是，2-正常购买——不可优先购买");
					btn_ok.setVisibility(View.GONE);
				} else if (model.getIsCanFirstBuy().equals("1")) {
					btn_ok.setVisibility(View.VISIBLE);
					btn_ok.setText(text_firstBuy);
				} else if (model.getIsCanFirstBuy().equals("2")) {
					btn_ok.setVisibility(View.VISIBLE);
					btn_ok.setText(text_investNomal);
				}
			} else {
				btn_ok.setVisibility(View.GONE);
			}
			tv_status.setText(StatusUtils.getYuyueStatusByCode(status_yuyue));
			// String status_order = model.getOrderStatus();
			// tv_status.setText(StatusUtils.getOrderStatusByCode(status_order));//订单状态
		}
		tv_per_amount.setText(model.getPerSuppAmt() + "元");
		tv_support_num.setText(model.getSuppedCnt());
		Double investTotal = Double.parseDouble(model.getPerSuppAmt())
				* Double.parseDouble(model.getSuppedCnt());
		tv_invest_amt.setText(investTotal + "元");
		persent_stock.setText(model.getStockPctPerSupp() + "%");
		persent_amt.setText(model.getEarnest() + "%");
		tv_yuyuejin.setText((investTotal * Double.parseDouble(model
				.getEarnest())) + "元");

		time_duration.setText(model.getPreStartDate() + "~"
				+ model.getPreEndDate());
		orderNo.setText(model.getOrderNo());
		orderNo_params = model.getOrderNo();

		// zpqa??现在缺少 项目信息字段——现在返回的是个list，显示样式是怎么样到底
		if (model.getPrjIntroList() != null
				&& model.getPrjIntroList().size() > 0) {
			tv_prj_title.setText(model.getPrjIntroList().get(0).getTitle());
			tv_prj_content.setText(model.getPrjIntroList().get(0)
					.getFileIdMemo());
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);

		if (arg1 == RESULT_OK) {
			new AlertDialog(Activity_BookedDetail.this).builder()
					.setMsg("购买成功")
					.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().isNeedRefresh = true;
							finish();
						}
					}).show();
		}
	}
}
