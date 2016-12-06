package com.minjinsuo.zhongchou.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetAgreementContentResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyPreProdOrderByIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyPreProdOrderByIdResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse.ElementReceAddressList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.Service_Login;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.service.Service_Login.UserCallBack;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 从我的预约列表点击“优先购”按钮跳转而来，主要功能是查看订单并可选择红包进行支付
 * 
 * @author zp
 *
 *         2016年9月27日
 */
public class Activity_My_StockBookPay extends Activity_Base implements
		OnItemClickListener {
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	@ViewInject(R.id.tv_expand)
	private TextView tv_expand;// 展开
	@ViewInject(R.id.iv_arrowdown)
	private ImageView iv_arrowdown;// 展开箭头
	@ViewInject(R.id.ll_expand)
	private ImageView ll_expand;// 展开布局
	@ViewInject(R.id.tv_content_expand)
	private TextView tv_content_expand;// 展开内容
	@ViewInject(R.id.tv_support_num)
	private TextView tv_support_num;// 支持分数
	@ViewInject(R.id.cb_protocal)
	private CheckBox cb_protocal;

	@ViewInject(R.id.tv_per_amount)
	private TextView tv_per_amount;// 每份金额
	@ViewInject(R.id.tv_invest_amt)
	private TextView tv_invest_amt;// 投资金额
	@ViewInject(R.id.persent_stock)
	private TextView persent_stock;// 预计所占股比
	@ViewInject(R.id.persent_amt)
	private TextView persent_amt;// 预约金比例
	@ViewInject(R.id.tv_prj_content)
	private TextView tv_prj_content;// 项目内容
	@ViewInject(R.id.tv_prj_title)
	private TextView tv_prj_title;// 项目标题
	@ViewInject(R.id.tv_usefulRedBag)
	private TextView tv_usefulRedBag;// 可用红包
	@ViewInject(R.id.tv_totalPrice)
	private TextView tv_totalPrice;// 需要支付金额
	@ViewInject(R.id.tv_fileRead)
	private TextView tv_fileRead;// 协议
	@ViewInject(R.id.layout_redbag)
	private LinearLayout layout_redbag;
	// 地址相关
	@ViewInject(R.id.recvName)
	private TextView recvName;
	@ViewInject(R.id.recvMobile)
	private TextView recvMobile;
	@ViewInject(R.id.recvAddr)
	private TextView recvAddr;
	@ViewInject(R.id.rl_address)
	private RelativeLayout rl_address;// 收货地址

	private boolean isNeedExpand = true;// 控制是否展开
	private int support_num = 1;// 支持的份数(最小为1份)
	private String id = "";// 回报id
	private double perSupportAmt = 0;// 单笔支持金额
	private double perStockPersent = 0;// 每份所占股比
	private double persentYuyue = 0;// 预约金比例
	private String seqNoAddr = "";// 收货地址序列号，提交订单时要作为参数

	private String str_redBagId = "";// 选择的可用红包id串
	private double amount_red = 0;// 选择的红包总额
	private List<ElementReceAddressList> list_add = new ArrayList<ElementReceAddressList>();
	public static final int CODE_PAY = 101;
	private String orderId = "";
	private String prjId = "";
	private String orderNo_params = "";
	private Double totalPay = 0.0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_book_pay);
		x.view().inject(this);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		setTitleText("订单支付");
		setTitleBack();

		if (getIntent() != null
				&& !StringUtils.isEmpty(getIntent().getStringExtra("id"))) {
			orderId = getIntent().getStringExtra("id");
			prjId = getIntent().getStringExtra("prjId");

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

	@Event({ R.id.tv_expand, R.id.tv_add, R.id.tv_minus, R.id.ll_expand,
			R.id.btn_ok, R.id.iv_arrowdown, R.id.rl_address,
			R.id.layout_redbag, R.id.tv_usefulRedBag, R.id.tv_fileRead })
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
		// case R.id.rl_address:// 选择收货地址
		// Intent intent = new Intent(Activity_My_StockBookPay.this,
		// ZActivity_addressManager.class);
		// startActivityForResult(intent, 001);
		//
		// break;
		case R.id.btn_ok:// 优先购买 支付
			if (!cb_protocal.isChecked()) {
				ToastUtil
						.showToast(Activity_My_StockBookPay.this, "请阅读并选中支付协议");
				return;
			}

			new AlertDialog(Activity_My_StockBookPay.this).builder()
					.setMsg("确定进行支付？")
					.setPositiveButton("立即支付", new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent_pay = new Intent(
									Activity_My_StockBookPay.this,
									Activity_ThirdWeb.class);
							String url = Service_ThirdPay.PAY_ORDER
									+ "sessionId="
									+ ZCApplication.getInstance().getUserInfo()
											.getSessionId() + "&payAmt="
									+ totalPay + "&bdId=" + prjId + "&orderNo="
									+ orderNo_params + "&ordId=" + orderId
									+ "&type=" + 0;
							intent_pay.putExtra(Activity_ThirdWeb.URL, url);
							intent_pay.putExtra(Activity_ThirdWeb.TITLENAME,
									"订单支付");
							startActivityForResult(intent_pay, CODE_PAY);
						}
					}).setNegativeButton("稍后再说", new OnClickListener() {

						@Override
						public void onClick(View v) {
						}
					}).show();
			break;
		case R.id.layout_redbag:
		case R.id.tv_usefulRedBag:
			Intent intent = new Intent(Activity_My_StockBookPay.this,
					Activity_EnableRedBag.class);
			intent.putExtra("investAmount", support_num * perSupportAmt + "");
			startActivityForResult(intent, 003);
			break;
		case R.id.tv_fileRead:// 查看协议
			if (!StringUtils.isEmpty(content)) {
				intent = new Intent(Activity_My_StockBookPay.this,
						Activity_CommonRead.class);
				intent.putExtra(Activity_CommonRead.CONTENT, content);
				startActivity(intent);
			} else {
				ToastUtil.showToast(Activity_My_StockBookPay.this, "协议为空");
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
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetMyPreProdOrderByIdResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {

		switch (response.getMessageId()) {
		case "getMyPreProdOrderById":
			GetMyPreProdOrderByIdResponse model = (GetMyPreProdOrderByIdResponse) response;
			if (model != null) {
				commpleteUI(model);
			}

			getAgreement(false);
			// DialogUtils.dismisLoading();
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
	}

	/**
	 * 填充数据
	 * 
	 * @param model
	 */
	public void commpleteUI(GetMyPreProdOrderByIdResponse model) {
		if (model == null) {
			return;
		}
		if (!StringUtils.isEmpty(model.getRecvNam())) {
			recvName.setText(model.getRecvNam());
			recvMobile.setText(model.getRecvMobile());
			recvAddr.setText(model.getRecvAddress());
		}
		// 预约状态
		tv_per_amount.setText(model.getPerSuppAmt() + "元");
		tv_support_num.setText(model.getSuppedCnt());
		Double investTotal = Double.parseDouble(model.getPerSuppAmt())
				* Double.parseDouble(model.getSuppedCnt());
		tv_invest_amt.setText(investTotal + "元");
		tv_totalPrice.setText(investTotal + "元");
		totalPay = investTotal;
		persent_stock.setText(model.getStockPctPerSupp() + "%");
		persent_amt.setText(model.getEarnest() + "%");
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
			if (arg0 == 001) {
				String addressAll = arg2.getStringExtra("addAll");// 地址
				String seqNo = arg2.getStringExtra("seqNo");// 地址序列号
				String mobile = arg2.getStringExtra("mobile");
				String recvNam = arg2.getStringExtra("recvName");// 收货人姓名
				LogUtil.i(recvName + "|" + addressAll);

				seqNoAddr = seqNo;
				recvName.setText(recvNam);
				recvMobile.setText(mobile);
				recvAddr.setText(addressAll);
			}

			if (arg0 == CODE_PAY) {
				LogUtil.i("预约订单--支付成功");
				new AlertDialog(Activity_My_StockBookPay.this).builder()
						.setMsg("支付成功")
						.setPositiveButton("返回", new OnClickListener() {

							@Override
							public void onClick(View v) {
								ZCApplication.getInstance().isNeedRefresh = true;
								setResult(RESULT_OK);
								finish();
							}
						}).show();
			}

			if (arg0 == 003) {// 可用红包
				ArrayList<String> strList = new ArrayList<String>();
				// 选择可用红包
				strList = arg2.getStringArrayListExtra("redBagList");
				amount_red = arg2.getDoubleExtra("amount_red", 0);

				if (strList != null && strList.size() != 0) {
					str_redBagId = strList.get(0);
					for (int i = 1; i < strList.size(); i++) {
						str_redBagId += "," + strList.get(i);
					}
				}
				LogUtil.i("支付页面  获取的返回的红包id ==" + str_redBagId + "金额="
						+ amount_red);
				tv_usefulRedBag.setText(amount_red + "元");
				tv_totalPrice.setText((totalPay - amount_red) + "元");// 支持分数*每份金额-红包抵扣
				totalPay = totalPay - amount_red;
			}
		}
	}

	/**
	 * 从收货地址中筛选出默认收货地址显示出来
	 */
	public void dealAddressInUse() {
		String recv_name = "", recv_mob = "", recv_add = "";
		if (list_add != null && list_add.size() > 0) {
			// 筛选出默认地址
			for (int i = 0; i < list_add.size(); i++) {
				if (list_add.get(i).getInUseAdd().equals("1")) {
					recv_name = list_add.get(i).getRecvNam();
					recv_mob = list_add.get(i).getRecvMobile();
					recv_add = list_add.get(i).getCodProv()
							+ list_add.get(i).getCodCity()
							+ list_add.get(i).getAddr();
					seqNoAddr = list_add.get(i).getSeqNo();
					break;
				}
				LogUtil.i("第几次循环==" + i);
			}
		}

		recvName.setText(recv_name);
		recvMobile.setText(recv_mob);
		recvAddr.setText(recv_add);
	}

	/**
	 * 获取协议
	 * 
	 * @param isShow
	 */
	private String content = "";

	public void getAgreement(boolean isShow) {
		Service_Login.getAgreementContent(this, "6", isShow,
				new UserCallBack() {

					@Override
					public void onSuccess(ResponseSupport sucResponse) {
						DialogUtils.dismisLoading();
						GetAgreementContentResponse model = (GetAgreementContentResponse) sucResponse;
						if (model.getAgreemenList() != null
								&& model.getAgreemenList().size() > 0) {
							content = model.getAgreemenList().get(0)
									.getContent();
						}

					}

					@Override
					public void onFailure(ResponseSupport failResponse) {
						DialogUtils.dismisLoading();
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
					}
				});
	}
}
