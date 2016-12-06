package com.minjinsuo.zhongchou.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.CheckUserRightRequest;
import net.zkbc.p2p.fep.message.protocol.CheckUserRightResponse;
import net.zkbc.p2p.fep.message.protocol.GetAgreementContentResponse;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeRequest;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse.ElementReceAddressList;
import net.zkbc.p2p.fep.message.protocol.GetProdOrderInfoByRwdIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetProdOrderInfoByRwdIdResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitPrjOrderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitPrjOrderResponse;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.Service_Login;
import com.minjinsuo.zhongchou.service.Service_Login.UserCallBack;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 提交订单页
 * 
 * @author zp
 *
 *         2016年6月27日
 */
public class Activity_ProductOrderSub extends Activity_Base {
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
	private TextView tv_perAmt;// 单笔支持金额
	@ViewInject(R.id.tv_des)
	private TextView tv_des;
	@ViewInject(R.id.tv_totalAmt)
	private TextView tv_totalAmt;
	@ViewInject(R.id.tv_feeYun)
	private TextView tv_feeYun;
	@ViewInject(R.id.recvName)
	private TextView recvName;
	@ViewInject(R.id.recvMobile)
	private TextView recvMobile;
	@ViewInject(R.id.recvAddr)
	private TextView recvAddr;
	@ViewInject(R.id.tv_fileRead)
	private TextView tv_fileRead;// 协议
	@ViewInject(R.id.et_beizhu)
	private EditText et_beizhu;
	@ViewInject(R.id.rl_address)
	private RelativeLayout rl_address;
	@ViewInject(R.id.ll_container)
	private LinearLayout ll_container;// 存放多张图片的容器
	@ViewInject(R.id.rg_container_investType)
	private RadioGroup rg_container_investType;// 存放无私奉献投资选择档的容器
	@ViewInject(R.id.cb_sele)
	private CheckBox cb_sele;

	private boolean isNeedExpand = true;// 控制是否展开
	private int support_num = 1;// 支持的份数(最小为1)

	private String id = "";// 回报id
	private double perSupportAmt = 0;// 单笔支持金额
	private double feeYun = 0;// 运费
	private String seqNoAddr = "";// 收货地址序列号，提交订单时要作为参数
	private String subId = "";// 获取详情获取避免重复提交的id
	private String isUserOk = "";// 是否已经 合格投资人认证
	private String content = "";
	private List<ElementReceAddressList> list_add = new ArrayList<ElementReceAddressList>();
	private boolean isMustBeizhu;
	private boolean isWuSiFengXian;// 是否是无私奉献
	private GetProdOrderInfoByRwdIdResponse model;
	private String type_prj = "";// 40-公益项目，请求协议时不同

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_order_confirm);
		x.view().inject(this);

		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {

		setTitleText("确认订单");
		setTitleBack();

		if (getIntent() != null
				&& getIntent().getSerializableExtra("id") != null) {
			id = (String) getIntent().getSerializableExtra("id");
			type_prj = getIntent().getStringExtra(AppContants.TYPE_PRJ) == null ? ""
					: getIntent().getStringExtra(AppContants.TYPE_PRJ);
			isWuSiFengXian = getIntent().getBooleanExtra(AppContants.ISWSFX,
					false);
			LogUtil.i("是否是无私奉献产品--" + isWuSiFengXian);

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
			R.id.iv_arrowdown, R.id.rl_address, R.id.btn_invest_now,
			R.id.tv_fileRead })
	private void OnClick(View view) {
		switch (view.getId()) {
		case R.id.iv_arrowdown:
		case R.id.tv_expand:
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
			tv_totalAmt.setText(perSupportAmt * support_num + feeYun + "元");
			break;
		case R.id.tv_minus:
			if (!StringUtils.isEmpty(tv_support_num.getText().toString()))
				support_num = Integer.parseInt(tv_support_num.getText()
						.toString());
			if (support_num > 1)
				support_num -= 1;
			tv_support_num.setText(support_num + "");
			tv_totalAmt.setText(perSupportAmt * support_num + feeYun + "元");
			break;
		case R.id.rl_address:// 选择收货地址
			Intent intent = new Intent(Activity_ProductOrderSub.this,
					Activity_AddressManager.class);
			startActivityForResult(intent, 001);

			break;
		case R.id.btn_invest_now:
			if (ZCApplication.getInstance().isLogin()) {
				if (StringUtils.isEmpty(seqNoAddr)
						|| recvAddr.getText().toString().trim().equals("无")) {
					ToastUtil.showToast(Activity_ProductOrderSub.this,
							"请选择收货地址");
					return;
				}
				// 下面判断是否必填备注
				if (isMustBeizhu
						&& StringUtils.isEmpty(et_beizhu.getText().toString()
								.trim())) {
					ToastUtil.showToast(Activity_ProductOrderSub.this,
							"请填写备注信息");
					return;
				}
				if (!cb_sele.isChecked()) {
					ToastUtil.showToast(Activity_ProductOrderSub.this,
							"请阅读并选中协议");
					return;
				}

				if (!isAllowInvest()) {
					return;
				}

				checkUserRight();
			}
			break;
		case R.id.tv_fileRead:// 查看协议
			if (!StringUtils.isEmpty(content)) {
				intent = new Intent(Activity_ProductOrderSub.this,
						Activity_CommonRead.class);
				intent.putExtra(Activity_CommonRead.CONTENT, content);
				startActivity(intent);
			} else {
				ToastUtil.showToast(Activity_ProductOrderSub.this, "协议为空");
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
	 * 获取选择支持回报项信息
	 * 
	 * @param rwaId
	 */
	public void getData(String rwaId) {
		GetProdOrderInfoByRwdIdRequest request = new GetProdOrderInfoByRwdIdRequest();
		request.setRwdId(rwaId);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetProdOrderInfoByRwdIdResponse.class, this);
	}

	/**
	 * 提交订单请求
	 */
	public void submitPrjOrder(boolean isShow) {
		SubmitPrjOrderRequest request = new SubmitPrjOrderRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setSeqNoAddr(seqNoAddr);
		request.setRwdId(id);
		request.setSubId(subId);
		request.setBuyCnt(support_num + "");
		request.setRemark(et_beizhu.getText().toString().trim());// 备注信息
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				SubmitPrjOrderResponse.class, this);
	}

	/**
	 * 下面判断投资次数限制
	 * 
	 * @return
	 */
	public boolean isAllowInvest() {
		if (model.getLmtOneSuppCnt() != null) {
			Double SuppCnt = Double.parseDouble(model.getLmtOneSuppCnt());
			Double useHadInvestCnt = 0.0;
			if (model.getCustBuyedCnt() != null) {
				useHadInvestCnt = Double.parseDouble(model.getCustBuyedCnt());
			}

			if (!model.getLmtOneSuppCnt().equals("0")
					&& model.getCustBuyedCnt() != null) {
				if (support_num - useHadInvestCnt > SuppCnt) {
					ToastUtil.showToast(Activity_ProductOrderSub.this,
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

	/**
	 * 获取协议
	 * 
	 * @param isShow
	 */
	public void getAgreement(boolean isShow) {
		String type = "3";
		if (type_prj.equals(40)) {
			type = "7";
		} else {
			type = "3";
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

	public void getBookletType(boolean isShow) {
		GetBookletByTypeRequest request = new GetBookletByTypeRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setType("selfPayTyp");
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetBookletByTypeResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {

		switch (response.getMessageId()) {
		case "getProdOrderInfoByRwdId":
			model = (GetProdOrderInfoByRwdIdResponse) response;
			if (model != null) {
				subId = model.getSubId();
				if (model.getIsFillMemo() != null) {
					if (model.getIsFillMemo().equals("0")) {// 非必填
						isMustBeizhu = false;
					} else {
						isMustBeizhu = true;
					}
				}
				completeUI(model);
			}

			if (isWuSiFengXian) {
				getBookletType(false);
			} else {
				getMyGoodsAddress(false);
			}
			break;
		case "getBookletByType":// （只有无私奉献时请求）无私奉献——获取投资档位
			GetBookletByTypeResponse model_type = (GetBookletByTypeResponse) response;
			if (model_type != null) {
				completeTypeUI(model_type);
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
					Intent intent = new Intent(Activity_ProductOrderSub.this,
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
					if (!StringUtils.isEmpty(info.getMessage())) {
						new DialogUtils().createOneBtnDiolog(
								Activity_ProductOrderSub.this,
								info.getMessage());
					}
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
	public void completeUI(GetProdOrderInfoByRwdIdResponse model) {
		tv_des.setText(model.getRwdContent());
		tv_perAmt.setText(model.getPerSuppAmt() + "元");
		if (!StringUtils.isEmpty(model.getFeeFreight())) {
			if (Double.parseDouble(model.getFeeFreight()) == 0) {
				tv_feeYun.setText("免运费");
				feeYun = 0;
			} else {
				tv_feeYun.setText(model.getFeeFreight() + "元");
				feeYun = Double.parseDouble(model.getFeeFreight());
			}
		}
		if (!StringUtils.isEmpty(model.getPerSuppAmt()))
			perSupportAmt = Double.parseDouble(model.getPerSuppAmt());

		tv_totalAmt.setText((perSupportAmt + feeYun) + "元");
		// 下面处理回报图片的显示
		ImageOptions options = new ImageOptions.Builder()
				.setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
				.setRadius(DensityUtil.dip2px(5))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				.setCrop(true)
				.setIgnoreGif(false)
				// 不忽略gif图片类型
				.setCircular(false)
				// 是否圆角
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
		if (model.getRwdPicAddress() != null
				&& !StringUtils.isEmpty(model.getRwdPicAddress())) {
			String[] strArr = {};
			if (model.getRwdPicAddress().contains(",")) {// 多张图片时
				strArr = model.getRwdPicAddress().split(",");
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

				x.image().bind(iv_pic, model.getRwdPicAddress(), options);
				ll_container.addView(iv_pic);
			}

		}
	}

	/**
	 * 填充数据——无私奉献 支持选择档
	 */
	public void completeTypeUI(GetBookletByTypeResponse model_type) {
		if (model_type.getBookletList() != null) {
			RadioGroup.LayoutParams params = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.setMargins(CommonUtils.dip2px(this, 8), 0, 0, 0);
			for (int i = 0; i < model_type.getBookletList().size(); i++) {
				RadioButton rb = new RadioButton(this);
				rb.setButtonDrawable(android.R.color.transparent);
				rb.setBackgroundResource(R.drawable.selector_red_bg);
				rb.setTextColor(R.drawable.selector_red_text);// ??不起作用
				rb.setGravity(Gravity.CENTER);
				rb.setPadding(CommonUtils.dip2px(this, 6),
						CommonUtils.dip2px(this, 3),
						CommonUtils.dip2px(this, 6),
						CommonUtils.dip2px(this, 3));
				rb.setText("￥" + model_type.getBookletList().get(i).getValue());
				rg_container_investType.addView(rb, params);
				rg_container_investType.setVisibility(View.VISIBLE);
				tv_perAmt.setVisibility(View.GONE);
			}

			// 默认选中第一个
			for (int i = 0; i < rg_container_investType.getChildCount(); i++) {
				if (i != 0) {
					((RadioButton) rg_container_investType.getChildAt(i))
							.setChecked(false);
				} else {
					((RadioButton) rg_container_investType.getChildAt(i))
							.setChecked(true);
					String result = ((RadioButton) rg_container_investType
							.getChildAt(i)).getText().toString();
					perSupportAmt = Double.parseDouble(result.substring(result
							.indexOf("￥") + 1));
					tv_totalAmt.setText(perSupportAmt + feeYun + "元");
				}
			}

			rg_container_investType
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							RadioButton rb_sel = (RadioButton) findViewById(rg_container_investType
									.getCheckedRadioButtonId());
							String result = rb_sel.getText().toString();
							perSupportAmt = Double.parseDouble(result
									.substring(result.indexOf("￥") + 1));
							tv_totalAmt.setText(perSupportAmt * support_num
									+ feeYun + "元");
						}
					});

		} else {
			rg_container_investType.setVisibility(View.GONE);
			tv_perAmt.setVisibility(View.VISIBLE);
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

				new AlertDialog(Activity_ProductOrderSub.this).builder()
						.setCancelable(false).setMsg("订单支付成功,可到我的订单列表查看")
						.setNegativeButton("返回", new OnClickListener() {

							@Override
							public void onClick(View v) {
								finish();
							}
						}).setPositiveButton("去查看", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivity(new Intent(
										Activity_ProductOrderSub.this,
										Activity_MyOrder.class));
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
	 * // 下面进行用户身份认证（实名认证，合格投资认证，绑卡判断在进行支付跳转前判断）
	 */
	public void startCheckUserIsOk() {
		if (ZCApplication.getInstance().getUserInfo().getIsrealname()
				.equals("0")) {
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_ProductOrderSub.this, "请先进行实名认证，再投资！去认证");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().isNeedRefresh = true;
							// 实名认证页面
							startActivity(new Intent(
									Activity_ProductOrderSub.this,
									OpenChargeFirstActivity.class));
							dialog.dismiss();
						}
					});
			return;
		}
		if (isUserOk.equals("0")) {// 未认证普通投资人
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_ProductOrderSub.this, "请先进行合格投资人认证，再投资！去认证");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().isNeedRefresh = true;
							// 投资人认证页面
							startActivity(new Intent(
									Activity_ProductOrderSub.this,
									Activity_InvestCerciticy.class));
							dialog.dismiss();
						}
					});
			return;
		}

		if (isUserOk.equals("2")) {// 禁用
			new DialogUtils().createOneBtnDiolog(Activity_ProductOrderSub.this,
					"该账户已被禁用，无法进行投资人认证");
			return;
		}
		if (isUserOk.equals("3")) {// 申请中
			new DialogUtils().createOneBtnDiolog(Activity_ProductOrderSub.this,
					"普通投资人认证正在申请中，请通过认证后投资");
			return;
		}

		// 2、绑卡判断
		if (StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
				.getCardno())) {
			final Dialog dialog = new DialogUtils().createTwoBtnDiolog(
					Activity_ProductOrderSub.this, "您尚未绑定银行卡，请先绑定银行卡");
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

		// 3、余额判断
		String amtAvailable = ZCApplication.getInstance().getUserInfo()
				.getAvailableAmt();
		String amttotalPrice = tv_totalAmt.getText().toString();
		amttotalPrice = amttotalPrice.substring(0, amttotalPrice.indexOf("元"));
		if (!StringUtils.isEmpty(amtAvailable)
				&& Double.parseDouble(amtAvailable) < Double
						.parseDouble(amttotalPrice)) {
			// 判断可用余额是否大于订单总额\
			ToastUtil.showToast(Activity_ProductOrderSub.this,
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

		recvName.setText(recv_name);
		recvMobile.setText(recv_mob);
		recvAddr.setText(recv_add);
	}
}
