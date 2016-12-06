package com.minjinsuo.zhongchou.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.CheckUserRightRequest;
import net.zkbc.p2p.fep.message.protocol.CheckUserRightResponse;
import net.zkbc.p2p.fep.message.protocol.GetAgreementContentResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse;
import net.zkbc.p2p.fep.message.protocol.GetPreOrderInfoByRwdIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetPreOrderInfoByRwdIdResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitPreOrderInfoRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitPreOrderInfoResponse;
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
 * 股权预约信息填写页面（提交流程：先判断提交预约前进行实名认证检查、合格投资人身份检查、是否绑卡判断、 余额是否充足提交订单成功后进行支付）
 * 
 * @author zp
 *
 *         2016年8月23日
 */
public class Activity_StockBookSub extends Activity_Base implements
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
	@ViewInject(R.id.cb_sele)
	private CheckBox cb_sele;

	@ViewInject(R.id.tv_perAmt)
	private TextView tv_perAmt;// 每份金额
	@ViewInject(R.id.tv_invest_amt)
	private TextView tv_invest_amt;// 投资金额
	@ViewInject(R.id.tv_persentStock)
	private TextView tv_persentStock;// 预计所占股比
	@ViewInject(R.id.tv_prj_content)
	private TextView tv_prj_content;// 项目内容
	@ViewInject(R.id.tv_prj_title)
	private TextView tv_prj_title;// 项目标题
	@ViewInject(R.id.time_duration)
	private TextView time_duration;// 预计优先购买期
	@ViewInject(R.id.tv_amt_yuyue)
	private TextView tv_amt_yuyue;// 预约金
	@ViewInject(R.id.tv_froozeAmt)
	private TextView tv_froozeAmt;// 冻结金额
	@ViewInject(R.id.tv_fileRead)
	private TextView tv_fileRead;// 协议
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
	private String subId = "";// 获取详情获取避免重复提交的id
	private String isUserOk = "";// 是否已经 合格投资人认证
	private String seqNoAddr = "";// 收货地址序列号，提交订单时要作为参数
	private List<ElementReceAddressList> list_add = new ArrayList<ElementReceAddressList>();
	private String type_prj = "";// 40-公益项目，请求协议时不同
	private String loan_amtTotal = "";// 传递来的标的筹资总额
	GetPreOrderInfoByRwdIdResponse model = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_book_submit);
		x.view().inject(this);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		setTitleText("填写预约信息");
		setTitleBack();

		if (getIntent() != null
				&& getIntent().getSerializableExtra("id") != null) {
			id = (String) getIntent().getSerializableExtra("id");
			type_prj = getIntent().getStringExtra(AppContants.TYPE_PRJ) == null ? ""
					: getIntent().getStringExtra(AppContants.TYPE_PRJ);
			if (!StringUtils.isEmpty(getIntent()
					.getStringExtra("loan_amtTotal"))) {
				loan_amtTotal = getIntent().getStringExtra("loan_amtTotal");
			}

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
			R.id.btn_ok, R.id.iv_arrowdown, R.id.rl_address, R.id.tv_fileRead })
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
		case R.id.tv_add:
			if (!StringUtils.isEmpty(tv_support_num.getText().toString()))
				support_num = Integer.parseInt(tv_support_num.getText()
						.toString());
			support_num += 1;
			tv_support_num.setText(support_num + "");
			tv_invest_amt.setText(support_num * perSupportAmt + "元");
			tv_amt_yuyue.setText(perSupportAmt * support_num * persentYuyue
					/ 100 + "元");
			tv_persentStock.setText(perStockPersent * support_num + "%");
			tv_froozeAmt.setText(tv_amt_yuyue.getText().toString().trim());
			break;
		case R.id.tv_minus:
			if (!StringUtils.isEmpty(tv_support_num.getText().toString()))
				support_num = Integer.parseInt(tv_support_num.getText()
						.toString());
			if (support_num > 1)
				support_num -= 1;
			tv_support_num.setText(support_num + "");
			tv_invest_amt.setText(support_num * perSupportAmt + "元");
			tv_amt_yuyue.setText(perSupportAmt * support_num * persentYuyue
					/ 100 + "元");
			tv_persentStock.setText(perStockPersent * support_num + "%");
			tv_froozeAmt.setText(tv_amt_yuyue.getText().toString().trim());
			break;
		case R.id.rl_address:// 选择收货地址
			Intent intent = new Intent(Activity_StockBookSub.this,
					Activity_AddressManager.class);
			startActivityForResult(intent, 001);

			break;
		case R.id.btn_ok:// 提交前先校验身份，再在校验绑卡，再提交（不同于订单提交）

			if (ZCApplication.getInstance().isLogin()) {
				if (StringUtils.isEmpty(recvAddr.getText().toString().trim())
						|| recvAddr.getText().toString().trim().equals("无")) {
					ToastUtil.showToast(Activity_StockBookSub.this, "请选择收货地址");
					return;
				}

				if (!cb_sele.isChecked()) {
					ToastUtil.showToast(this, "请阅读并选中协议");
					return;
				}
				new AlertDialog(Activity_StockBookSub.this)
						.builder()
						.setMsg("预约将暂时冻结金额"
								+ tv_froozeAmt.getText().toString().trim())
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {
								checkUserRight();
							}
						}).setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();
			}
			break;
		case R.id.tv_fileRead:
			if (!StringUtils.isEmpty(content)) {
				intent = new Intent(Activity_StockBookSub.this,
						Activity_CommonRead.class);
				intent.putExtra(Activity_CommonRead.CONTENT, content);
				startActivity(intent);
			} else {
				ToastUtil.showToast(Activity_StockBookSub.this, "协议为空");
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

	public void getData(String rwdId) {
		GetPreOrderInfoByRwdIdRequest request = new GetPreOrderInfoByRwdIdRequest();
		request.setRwdId(rwdId);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetPreOrderInfoByRwdIdResponse.class, this);
	}

	/**
	 * 提交预约信息订单
	 */
	public void submitPreOrderInfo(boolean isShow) {
		SubmitPreOrderInfoRequest request = new SubmitPreOrderInfoRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setBuyCnt(support_num + "");
		request.setId(id);
		request.setSeqNoAddr(seqNoAddr);// 收货地址序列号
		request.setSubId(subId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				SubmitPreOrderInfoResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {

		switch (response.getMessageId()) {
		case "getPreOrderInfoByRwdId":
			model = (GetPreOrderInfoByRwdIdResponse) response;
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
			// DialogUtils.dismisLoading();
			break;
		case "submitPreOrderInfo":
			SubmitPreOrderInfoResponse info = (SubmitPreOrderInfoResponse) response;
			if (info != null) {
				if (info.getResult().equals("success")) {
					// 预约之后需要立即进行三方支付进行冻结金额
					String orderNo = info.getOrderNo();
					String ordId = info.getOrderId();
					String prjId = info.getPrjId();
					Intent intent = new Intent(Activity_StockBookSub.this,
							Activity_ThirdWeb.class);
					String url = Service_ThirdPay.PAY_ORDER
							+ "sessionId="
							+ ZCApplication.getInstance().getUserInfo()
									.getSessionId() + "&payAmt="
							+ tv_froozeAmt.getText().toString() + "&bdId="
							+ prjId + "&orderNo=" + orderNo + "&ordId=" + ordId
							+ "&type=" + 0;
					intent.putExtra(Activity_ThirdWeb.URL, url);
					intent.putExtra(Activity_ThirdWeb.TITLENAME, "订单支付");
					startActivityForResult(intent, 002);
				} else {
					ToastUtil.showToast(Activity_StockBookSub.this,
							info.getMessage());
				}

			}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	/**
	 * 填充数据
	 * 
	 * @param model
	 */
	public void completeUI(GetPreOrderInfoByRwdIdResponse model) {
		if (!StringUtils.isEmpty(model.getPerSuppAmt()))
			perSupportAmt = Double.parseDouble(model.getPerSuppAmt());
		if (!StringUtils.isEmpty(model.getStockPctPerSupp()))
			perStockPersent = Double.parseDouble(model.getStockPctPerSupp());
		tv_persentStock.setText(model.getStockPctPerSupp() + "%");
		tv_perAmt.setText(model.getPerSuppAmt() + "元");
		tv_invest_amt.setText(model.getPerSuppAmt() + "元");

		if (model.getPrjIntroList() != null) {
			tv_prj_content.setText(model.getPrjIntroList().get(0)
					.getFileIdMemo());
			tv_prj_title.setText(model.getPrjIntroList().get(0).getTitle());
		}
		time_duration.setText(StringUtils.getDate(model.getPreStartDate(), 1)
				+ "~" + StringUtils.getDate(model.getPreEndDate(), 1));
		if (!StringUtils.isEmpty(model.getEarnest())) {
			persentYuyue = Double.parseDouble(model.getEarnest());
		}
		tv_amt_yuyue.setText((perSupportAmt * persentYuyue / 100) + "元");
		tv_froozeAmt.setText(perSupportAmt * persentYuyue / 100 + "");
	}

	/**
	 * // 下面进行用户身份认证（实名认证，合格投资认证，绑卡判断在进行支付跳转前判断）
	 */
	public void startCheckUserIsOk() {
		if (!isAllowInvest()) {// 先判断是否大于最大投资次数限制
			return;
		}

		// 1、实名认证判断
		if (ZCApplication.getInstance().getUserInfo().getIsrealname()
				.equals("0")) {
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_StockBookSub.this, "请先进行实名认证，再投资！去认证");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().isNeedRefresh = true;
							// 实名认证页面
							startActivity(new Intent(
									Activity_StockBookSub.this,
									OpenChargeFirstActivity.class));
							dialog.dismiss();
						}
					});
			return;
		}
		// 2、合格投资人校验
		if (isUserOk.equals("0")) {// 未认证普通投资人
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_StockBookSub.this, "请先进行合格投资人认证，再投资！去认证");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().isNeedRefresh = true;
							// 投资人认证页面
							startActivity(new Intent(
									Activity_StockBookSub.this,
									Activity_InvestCerciticy.class));
							dialog.dismiss();
						}
					});
			return;
		}

		if (isUserOk.equals("2")) {// 禁用
			new DialogUtils().createOneBtnDiolog(Activity_StockBookSub.this,
					"该账户已被禁用，无法进行投资人认证");
			return;
		}
		if (isUserOk.equals("3")) {// 申请中
			new DialogUtils().createOneBtnDiolog(Activity_StockBookSub.this,
					"普通投资人认证正在申请中，请通过认证后投资");
			return;
		}

		// 3、绑卡判断
		if (StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
				.getCardno())) {
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_StockBookSub.this, "您尚未绑定银行卡，请先绑定银行卡");
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

		// 4、判断可用余额是否大于冻结金额
		String amtAvailable = ZCApplication.getInstance().getUserInfo()
				.getAvailableAmt();
		String amtFrozeen = tv_froozeAmt.getText().toString()
				.substring(0, tv_froozeAmt.getText().toString().indexOf("元"));
		if (!StringUtils.isEmpty(amtAvailable)
				&& Double.parseDouble(amtAvailable) < Double
						.parseDouble(amtFrozeen)) {
			// 判断可用余额是否大于订单总额\
			ToastUtil.showToast(Activity_StockBookSub.this,
					"可用余额不足，请先到我的账户中充值后操作");
			return;
		}

		submitPreOrderInfo(false);
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
					ToastUtil.showToast(Activity_StockBookSub.this, "已达最大支持次数（"
							+ SuppCnt + "次）限制，不能投资该标");
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
		startActivityForResult(intent2, 004);
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
				new AlertDialog(Activity_StockBookSub.this).builder()
						.setMsg("您的预约申请已提交成功，\n稍后可到我的预约列表查看")
						.setCancelable(false)
						.setNegativeButton("返回", new OnClickListener() {

							@Override
							public void onClick(View v) {
								finish();
							}
						}).setPositiveButton("去查看", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivity(new Intent(
										Activity_StockBookSub.this,
										Activity_MyBooked.class));
								finish();
							}
						}).show();
			}

			if (arg0 == 004) {// 绑卡成功
				ZCApplication.getInstance().getUserInfo().setCardno("123456");
				LogUtil.i("绑卡成功，存储一个假的卡号，代表绑卡成功的标示");
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
		String type = "4";
		if (type_prj.equals(40)) {
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
