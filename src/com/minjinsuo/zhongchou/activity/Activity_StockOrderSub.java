package com.minjinsuo.zhongchou.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.CheckUserRightRequest;
import net.zkbc.p2p.fep.message.protocol.CheckUserRightResponse;
import net.zkbc.p2p.fep.message.protocol.GetAgreementContentResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse;
import net.zkbc.p2p.fep.message.protocol.GetStockOrderInfoByRwdIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetStockOrderInfoByRwdIdResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitPrjOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitPrjOrderResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse.ElementReceAddressList;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Dialog;
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
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 股权提交订单页面（提交流程：提交订单前进行实名认证检查、合格投资人身份检查；提交订单成功后进行支付，支付前进行是否绑卡判断）
 * 
 * @author zp
 *
 *         2016年8月23日
 */
public class Activity_StockOrderSub extends Activity_Base implements
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
	@ViewInject(R.id.tv_minus)
	private TextView tv_minus;
	@ViewInject(R.id.tv_add)
	private TextView tv_add;
	@ViewInject(R.id.tv_perAmt)
	private TextView tv_perAmt;
	@ViewInject(R.id.tv_invest_amt)
	private TextView tv_invest_amt;// 投资金额
	@ViewInject(R.id.tv_persentStock)
	private TextView tv_persentStock;// 预计所占股比
	@ViewInject(R.id.tv_usefulRedBag)
	private TextView tv_usefulRedBag;// 可使用红包
	@ViewInject(R.id.tv_prj_content)
	private TextView tv_prj_content;// 项目内容
	@ViewInject(R.id.tv_prj_title)
	private TextView tv_prj_title;// 项目标题
	@ViewInject(R.id.tv_totalPrice)
	private TextView tv_totalPrice;
	@ViewInject(R.id.recvName)
	private TextView recvName;
	@ViewInject(R.id.recvMobile)
	private TextView recvMobile;
	@ViewInject(R.id.recvAddr)
	private TextView recvAddr;
	@ViewInject(R.id.tv_fileRead)
	private TextView tv_fileRead;
	@ViewInject(R.id.rl_address)
	private RelativeLayout rl_address;// 收货地址
	@ViewInject(R.id.layout_redbag)
	private LinearLayout layout_redbag;
	@ViewInject(R.id.cb_sele)
	private CheckBox cb_sele;

	private boolean isNeedExpand = true;// 控制是否展开
	private int support_num = 1;// 支持的份数(最小为1份)
	private String id = "";// 回报id
	private String str_redBagId = "";// 选择的可用红包id串
	private double amount_red = 0;// 选择的红包总额
	private double perSupportAmt = 0;// 单笔支持金额
	private double perStockPersent = 0;// 每份所占股比

	private String seqNoAddr = "";// 收货地址序列号，提交订单时要作为参数
	private String subId = "";// 获取详情获取避免重复提交的id
	private String isUserOk = "";// 是否已经 合格投资人认证
	private List<ElementReceAddressList> list_add = new ArrayList<ElementReceAddressList>();
	private GetStockOrderInfoByRwdIdResponse model;
	private boolean isMustBeizhu;
	private String type_prj = "";// 40-公益项目，请求协议时不同

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_order_submit);
		x.view().inject(this);

		ZCApplication.getInstance().addActivity(this);// 提交订单成功后便于一块关闭此页面
		initView();
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ZCApplication.getInstance().isNeedRefresh) {
			ZCApplication.getInstance().isNeedRefresh = false;
			checkUserRight();
		}
	}

	@Override
	protected void initView() {
		setTitleText("填写订单信息");
		setTitleBack();

		if (ZCApplication.getInstance().isLogin()
				&& ZCApplication.getInstance().getUserInfo().getUserType()
						.equals("2")) {
			// 企业用户不能选择红包
			layout_redbag.setVisibility(View.GONE);
		} else {
			layout_redbag.setVisibility(View.VISIBLE);
		}

		if (getIntent() != null
				&& getIntent().getSerializableExtra("id") != null) {
			id = (String) getIntent().getSerializableExtra("id");
			type_prj = getIntent().getStringExtra(AppContants.TYPE_PRJ) == null ? ""
					: getIntent().getStringExtra(AppContants.TYPE_PRJ);

			getData(id);
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
			R.id.btn_ok, R.id.rl_address, R.id.tv_usefulRedBag,
			R.id.layout_redbag, R.id.tv_fileRead })
	private void OnClick(View view) {
		switch (view.getId()) {
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
		case R.id.tv_add:
			if (!StringUtils.isEmpty(tv_support_num.getText().toString()))
				support_num = Integer.parseInt(tv_support_num.getText()
						.toString());
			support_num += 1;
			tv_support_num.setText(support_num + "");
			tv_invest_amt.setText(support_num * perSupportAmt + "元");
			tv_totalPrice.setText(support_num * perSupportAmt + "元");// 支付总额，此时应该减去使用红包的金额
			tv_persentStock.setText(perStockPersent * support_num + "%");
			break;
		case R.id.tv_minus:
			if (!StringUtils.isEmpty(tv_support_num.getText().toString()))
				support_num = Integer.parseInt(tv_support_num.getText()
						.toString());
			if (support_num > 1)
				support_num -= 1;
			tv_support_num.setText(support_num + "");
			tv_invest_amt.setText(support_num * perSupportAmt + "元");
			tv_totalPrice.setText(support_num * perSupportAmt + "元");// 支付总额，此时应该减去使用红包的金额
			tv_persentStock.setText(perStockPersent * support_num + "%");
			break;
		case R.id.rl_address:// 选择收货地址
			Intent intent = new Intent(Activity_StockOrderSub.this,
					Activity_AddressManager.class);
			startActivityForResult(intent, 001);

			break;
		case R.id.btn_ok:
			if (ZCApplication.getInstance().isLogin()) {
				if (StringUtils.isEmpty(recvAddr.getText().toString().trim())
						|| recvAddr.getText().toString().trim().equals("无")) {
					ToastUtil.showToast(Activity_StockOrderSub.this, "请选择收货地址");
					return;
				}
				if (!cb_sele.isChecked()) {
					ToastUtil.showToast(this, "请阅读并选中协议");
					return;
				}

				checkUserRight();
			}
			break;
		case R.id.layout_redbag:
		case R.id.tv_usefulRedBag:
			intent = new Intent(Activity_StockOrderSub.this,
					Activity_EnableRedBag.class);
			intent.putExtra("investAmount", support_num * perSupportAmt + "");
			startActivityForResult(intent, 003);
			break;
		case R.id.tv_fileRead:// 查看协议
			if (!StringUtils.isEmpty(content)) {
				intent = new Intent(Activity_StockOrderSub.this,
						Activity_CommonRead.class);
				intent.putExtra(Activity_CommonRead.CONTENT, content);
				startActivity(intent);
			} else {
				ToastUtil.showToast(Activity_StockOrderSub.this, "协议为空");
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

	/*
	 * 获取所有收货地址
	 */
	public void getMyGoodsAddress(boolean isShow) {

		if (ZCApplication.getInstance().getUserInfo() == null
				|| StringUtils.isEmpty(ZCApplication.getInstance()
						.getUserInfo().getSessionId())) {
			return;
		}
		GetMyRecAddressRequest request = new GetMyRecAddressRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetMyRecAddressResponse.class, this);
	}

	/**
	 * 检测用户当前角色状态
	 */
	public void checkUserRight() {
		CheckUserRightRequest request = new CheckUserRightRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				CheckUserRightResponse.class, this);
	}

	/**
	 * 获取股权订单信息
	 * 
	 * @param rwdId
	 */
	public void getData(String rwdId) {
		GetStockOrderInfoByRwdIdRequest request = new GetStockOrderInfoByRwdIdRequest();
		request.setRwdId(rwdId);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetStockOrderInfoByRwdIdResponse.class, this);
	}

	/**
	 * 提交股权订单请求
	 */
	public void submitPrjOrder(boolean isShow) {
		if (!isAllowInvest()) {// 先判断是否大于最大投资次数限制
			return;
		}

		SubmitPrjOrderRequest request = new SubmitPrjOrderRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setSeqNoAddr(seqNoAddr);
		request.setRwdId(id);
		request.setSubId(subId);
		request.setBuyCnt(support_num + "");
		if (!StringUtils.isEmpty(str_redBagId)) {
			request.setRedMoneyId(str_redBagId);// 可用红包id
		}
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				SubmitPrjOrderResponse.class, this);
	}

	/**
	 * 下面判断投资次数限制
	 * 
	 * @return
	 */
	public boolean isAllowInvest() {
		if (!StringUtils.isEmpty(model.getLmtOneSuppCnt())) {
			Double SuppCnt = Double.parseDouble(model.getLmtOneSuppCnt());
			Double useHadInvestCnt = 0.0;
			if (model.getCustBuyedCnt() != null) {
				useHadInvestCnt = Double.parseDouble(model.getCustBuyedCnt());
			}

			if (!model.getLmtOneSuppCnt().equals("0")
					&& model.getCustBuyedCnt() != null) {
				if (support_num - useHadInvestCnt > SuppCnt) {
					ToastUtil.showToast(Activity_StockOrderSub.this,
							"已达最大支持次数（" + SuppCnt + "次）限制，不能投资该标");
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public void onSuccess(ResponseSupport response) {

		switch (response.getMessageId()) {
		case "getStockOrderInfoByRwdId":
			model = (GetStockOrderInfoByRwdIdResponse) response;
			if (model != null) {
				subId = model.getSubId();
				completeUI(model);
			}
			getMyGoodsAddress(false);
			break;
		case "getMyRecAddress":// 获取收货地址列表
			GetMyRecAddressResponse info_add = (GetMyRecAddressResponse) response;
			list_add.clear();
			list_add = info_add.getReceAddressList();
			dealAddressInUse();

			getAgreement(false);
			break;
		case "submitPrjOrder":
			SubmitPrjOrderResponse info = (SubmitPrjOrderResponse) response;
			if (info != null) {
				if (info.getResult().equals("success")) {
					String orderNo = info.getOrderNo();
					String ordId = info.getOrderId();
					String prjId = info.getPrjId();
					Intent intent = new Intent(Activity_StockOrderSub.this,
							Activity_ThirdWeb.class);
					String url = Service_ThirdPay.PAY_ORDER
							+ "sessionId="
							+ ZCApplication.getInstance().getUserInfo()
									.getSessionId() + "&payAmt=" + 200.00
							+ "&bdId=" + prjId + "&orderNo=" + orderNo
							+ "&ordId=" + ordId + "&type=" + 0;
					intent.putExtra(Activity_ThirdWeb.URL, url);
					intent.putExtra(Activity_ThirdWeb.TITLENAME, "订单支付");
					startActivityForResult(intent, 002);
				} else {
					ToastUtil.showToast(Activity_StockOrderSub.this,
							info.getMessage());
				}
			}
			DialogUtils.dismisLoading();
			break;
		case "checkUserRight":
			CheckUserRightResponse model_isRight = (CheckUserRightResponse) response;
			if (model_isRight != null
					&& !StringUtils.isEmpty(model_isRight.getIsFollower())) {
				// isFollower 是否普通投资资质认证 否-0 是-1 禁用-2 申请中-3
				// isUserLeader 是否平台领投人 否-0 是-1 禁用-2 申请中-3

				if (model_isRight.getIsFollower().equals("0")) {// 不是
					isUserOk = "0";
				} else if (model_isRight.getIsFollower().equals("1")) {// 是
					isUserOk = "1";

				} else if (model_isRight.getIsFollower().equals("2")) {// 禁用
					isUserOk = "2";
				} else {// 申请中
					isUserOk = "3";
				}

				startCheckUserIsOk();// 身份校验通过后进行提交订单
			}

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
	public void completeUI(GetStockOrderInfoByRwdIdResponse model) {
		if (!StringUtils.isEmpty(model.getPerSuppAmt()))
			perSupportAmt = Double.parseDouble(model.getPerSuppAmt());
		if (!StringUtils.isEmpty(model.getStockPctPerSupp()))
			perStockPersent = Double.parseDouble(model.getStockPctPerSupp());
		tv_persentStock.setText(model.getStockPctPerSupp() + "%");
		tv_perAmt.setText(model.getPerSuppAmt() + "元");
		tv_totalPrice.setText(model.getPerSuppAmt() + "元");
		if (model.getPrjIntroList() != null) {
			tv_prj_content.setText(model.getPrjIntroList().get(0)
					.getFileIdMemo());
			tv_prj_title.setText(model.getPrjIntroList().get(0).getTitle());
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

			if (arg0 == 002) {
				ZCApplication.getInstance().isNeedRefresh = true;
				new AlertDialog(Activity_StockOrderSub.this).builder()
						.setCancelable(false).setMsg("订单支付成功,可到我的订单列表查看")
						.setNegativeButton("返回", new OnClickListener() {

							@Override
							public void onClick(View v) {
								ZCApplication.getInstance().exit();
								finish();
							}
						}).setPositiveButton("去查看", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivity(new Intent(
										Activity_StockOrderSub.this,
										Activity_MyOrder.class));
								ZCApplication.getInstance().exit();
								finish();
							}
						}).show();
			}

			if (arg0 == 003) {
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
				tv_totalPrice
						.setText((support_num * perSupportAmt - amount_red)
								+ "元");// 支持分数*每份金额-红包抵扣
				tv_add.setEnabled(false);
				tv_minus.setEnabled(false);
			}

			if (arg0 == 004) {// 绑卡成功
				ZCApplication.getInstance().getUserInfo().setCardno("123456");
				LogUtil.i("绑卡成功，存储一个假的卡号，代表绑卡成功的标示");
			}
		}
	}

	/**
	 * // 下面进行用户身份认证（实名认证，合格投资认证，绑卡判断,余额判断——在进行支付跳转前判断）
	 */
	public void startCheckUserIsOk() {
		if (ZCApplication.getInstance().getUserInfo().getIsrealname()
				.equals("0")) {
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_StockOrderSub.this, "请先进行实名认证，再投资！去认证");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().isNeedRefresh = true;
							// 实名认证页面
							startActivity(new Intent(
									Activity_StockOrderSub.this,
									OpenChargeFirstActivity.class));
							dialog.dismiss();
						}
					});
			return;
		}
		if (isUserOk.equals("0")) {// 未认证普通投资人
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_StockOrderSub.this, "请先进行合格投资人认证，再投资！去认证");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().isNeedRefresh = true;
							// 投资人认证页面
							startActivity(new Intent(
									Activity_StockOrderSub.this,
									Activity_InvestCerciticy.class));
							dialog.dismiss();
						}
					});
			return;
		}

		if (isUserOk.equals("2")) {// 禁用
			new DialogUtils().createOneBtnDiolog(Activity_StockOrderSub.this,
					"该账户已被禁用，无法进行投资人认证");
			return;
		}
		if (isUserOk.equals("3")) {// 申请中
			new DialogUtils().createOneBtnDiolog(Activity_StockOrderSub.this,
					"普通投资人认证正在申请中，请通过认证后投资");
			return;
		}

		// 绑卡判断
		if (StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
				.getCardno())) {
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_StockOrderSub.this, "您尚未绑定银行卡，请先绑定银行卡");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							bindCard_yibao();
							dialog.dismiss();
						}
					});
			return;
		}

		// 余额判断
		String amtAvailable = ZCApplication.getInstance().getUserInfo()
				.getAvailableAmt() == null ? "0" : ZCApplication.getInstance()
				.getUserInfo().getAvailableAmt();
		String amttotalPrice = tv_totalPrice.getText().toString();
		amttotalPrice = amttotalPrice.substring(0, amttotalPrice.indexOf("元"));
		if (Double.parseDouble(amtAvailable) < Double
				.parseDouble(amttotalPrice)) {
			// 判断可用余额是否大于订单总额
			ToastUtil.showToast(Activity_StockOrderSub.this,
					"可用余额不足，请先到我的账户中充值后操作");
			return;
		}

		submitPrjOrder(false);
	}

	/**
	 * 易宝支付/汇付天下
	 */
	public void bindCard_yibao() {
		String url = Service_ThirdPay.BINDBANKCARD + "sessionId="
				+ ZCApplication.getInstance().userInfo.getSessionId();
		Intent intent2 = new Intent(getContext(), Activity_ThirdWeb.class);
		intent2.putExtra(Activity_ThirdWeb.URL, url);
		intent2.putExtra(Activity_ThirdWeb.TITLENAME, "绑定提现银行卡");
		intent2.putExtra(Activity_ThirdWeb.FLAG, "0");
		// startActivity(intent2);
		startActivityForResult(intent2, 004);
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

		recvName.setText(recv_name.trim());
		recvMobile.setText(recv_mob.trim());
		recvAddr.setText(recv_add.trim());
	}

	/**
	 * 获取协议
	 * 
	 * @param isShow
	 */
	private String content = "";

	public void getAgreement(boolean isShow) {
		String type = "4";
		if (type_prj.equals("40")) {
			type = "7";
		} else {
			type = "4";
		}
		Service_Login.getAgreementContent(this, type, isShow,
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
