package com.minjinsuo.zhongchou.fragment;

import net.zkbc.p2p.fep.message.protocol.GetInviteCodeRequest;
import net.zkbc.p2p.fep.message.protocol.GetInviteCodeResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_InvestCerciticy;
import com.minjinsuo.zhongchou.activity.Activity_InvestCerciticyComFirst;
import com.minjinsuo.zhongchou.activity.Activity_Main;
import com.minjinsuo.zhongchou.activity.Activity_MyAttention;
import com.minjinsuo.zhongchou.activity.Activity_MyBooked;
import com.minjinsuo.zhongchou.activity.Activity_MyFaqi;
import com.minjinsuo.zhongchou.activity.Activity_MyOrder;
import com.minjinsuo.zhongchou.activity.Activity_MyRedBag;
import com.minjinsuo.zhongchou.activity.Activity_Register;
import com.minjinsuo.zhongchou.activity.Activity_SetGestureLock;
import com.minjinsuo.zhongchou.activity.Activity_ThirdWeb;
import com.minjinsuo.zhongchou.activity.Activity_findPassWord;
import com.minjinsuo.zhongchou.activity.Activity_integrate;
import com.minjinsuo.zhongchou.activity.OpenChargeFirstActivity;
import com.minjinsuo.zhongchou.activity.TopupAndTiXianActivity;
import com.minjinsuo.zhongchou.http.GetThirdAmountManager;
import com.minjinsuo.zhongchou.http.GetThirdAmountManager.GetThirdAmountManagerCallBack;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.QueryMoney;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.service.Service_Login;
import com.minjinsuo.zhongchou.service.Service_Login.UserCallBack;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.EncryptionAndDecryptionUtil;
import com.minjinsuo.zhongchou.utils.ShareUtils;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.widget.ClearableEditText;

/**
 * 我的账户tab页面
 */
public class Fragment_Account extends Fragment_Base implements
		OnRefreshListener<ScrollView> {
	private View mView;

	@ViewInject(R.id.ll_invest_cirtify)
	private LinearLayout ll_invest_cirtify;// 投资认证
	@ViewInject(R.id.ll_myBook)
	private LinearLayout ll_myBook;// 我的预约
	@ViewInject(R.id.ll_mysupport)
	private LinearLayout ll_mysupport;// 我的支持
	@ViewInject(R.id.ll_myattention)
	private LinearLayout ll_myattention;// 我的关注
	@ViewInject(R.id.ll_myfaqi)
	private LinearLayout ll_myfaqi;// 我的发起
	@ViewInject(R.id.ll_myRedBag)
	private LinearLayout ll_myRedBag;// 我的红包
	@ViewInject(R.id.ll_myInveteFriend)
	private LinearLayout ll_myInveteFriend;// 我的邀请
	@ViewInject(R.id.btn_withdrawMoney)
	private LinearLayout btn_withdrawMoney;// 提现
	@ViewInject(R.id.btn_saveMoney)
	private LinearLayout btn_saveMoney;// 充值
	@ViewInject(R.id.layout_companyUserNoShow)
	private LinearLayout layout_companyUserNoShow;// 企业用户屏蔽该布局
	@ViewInject(R.id.ptr_refresh_invester)
	private PullToRefreshScrollView ptr_refresh_invester;
	@ViewInject(R.id.tv_amount_all)
	private TextView tv_amount_all;// 总资产
	@ViewInject(R.id.tv_avaliable)
	private TextView tv_avaliable;// 可用余额
	@ViewInject(R.id.tv_freeze)
	private TextView tv_freeze;// 冻结资金
	@ViewInject(R.id.ll_layout_login)
	private LinearLayout ll_layout_login;// 登录布局
	@ViewInject(R.id.rl_layout_account)
	private RelativeLayout rl_layout_account;// 我的账户布局
	@ViewInject(R.id.auth)
	private TableRow auth;// 实名认证提示
	@ViewInject(R.id.account_eye_check)
	private CheckBox account_eye_check;

	// 登录——控件初始化
	@ViewInject(R.id.et_username)
	private ClearableEditText et_username;
	@ViewInject(R.id.et_pass)
	private ClearableEditText et_pass;
	@ViewInject(R.id.btn_login)
	private Button btn_login;
	@ViewInject(R.id.btn_register)
	private TextView btn_register;
	@ViewInject(R.id.ivEye)
	private ImageView ivEye;
	@ViewInject(R.id.tv_forgetpwd_login)
	private TextView tv_forgetpwd;

	private RelativeLayout rl_dialog;
	private TextView dialogTitle;
	private TextView dialogMessage;
	private Button dialogBtn;
	private Button dialogBtn_cancle;

	private String shareCodeUrl, shareContent;// 分享内容和url
	private final int REQUEST_DOLOGIN = 101;
	private static final int REQUEST_GESTURE = 002;
	private ZCApplication app;
	private String amtTotal = "0", amtAvailable = "0", amtFrozeen = "0";
	private boolean state_eye;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (ZCApplication) getActivity().getApplicationContext();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// app.TAG = "tab_account";
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_account, null);
			x.view().inject(this, mView);

			initView();
			initData();
			initListener();
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

		state_eye = SharedPreferUtils.getEyeSate(getContext());

		checkAccount();
	}

	public void checkAccount() {
		LogUtil.i("checkAccount~~");
		if (ZCApplication.getInstance().isLogin()) {

			setTitleText(mView, "我的账户");
			ll_layout_login.setVisibility(View.GONE);
			rl_layout_account.setVisibility(View.VISIBLE);
			if (ZCApplication.getInstance().getUserInfo().getUserType()
					.equals("2")) {
				layout_companyUserNoShow.setVisibility(View.GONE);
			} else {
				layout_companyUserNoShow.setVisibility(View.VISIBLE);
			}

			// 判断是否实名认证
			if (ZCApplication.getInstance().getUserInfo().getIsrealname() == null
					|| ZCApplication.getInstance().getUserInfo()
							.getIsrealname().equals("0")) {
				completeAmtView("0", "0", "0");
				if (LockPatternService.getIsFirstLogin(getActivity())) {
					showRealNameDialog();
				} else {
					auth.setVisibility(View.VISIBLE);
					rl_dialog.setVisibility(View.GONE);
				}
			} else {// 已实名认证
				auth.setVisibility(View.GONE);
				rl_dialog.setVisibility(View.GONE);
				if (ZCApplication.getInstance().isNeedRefresh) {
					ZCApplication.getInstance().isNeedRefresh = false;
					getMyAccount();
				} else {
					// 此时只拿时时余额
					getBalamount(true);
				}
			}
		} else {
			setTitleText(mView, "登录");
			ll_layout_login.setVisibility(View.VISIBLE);
			rl_layout_account.setVisibility(View.GONE);
		}
	}

	@Override
	public void initView() {
		// 实名认证
		rl_dialog = (RelativeLayout) mView.findViewById(R.id.realname);
		dialogTitle = (TextView) mView.findViewById(R.id.dialogTitle);
		dialogMessage = (TextView) mView.findViewById(R.id.dialogMessage);
		dialogBtn = (Button) mView.findViewById(R.id.dialogBtn);
		dialogBtn_cancle = (Button) mView.findViewById(R.id.dialogBtn_cancle);
	}

	@Override
	public void initData() {
	}

	@Override
	protected void initListener() {
		ptr_refresh_invester.setMode(Mode.PULL_FROM_START);
		ptr_refresh_invester.setOnRefreshListener(this);

		// 控制金额明文、密文显示
		account_eye_check
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						state_eye = isChecked;
						SharedPreferUtils.setEyeState(getContext(), isChecked);
						changeTxtState(isChecked);
					}
				});
	}

	@Event({ R.id.ll_myfaqi, R.id.ll_mysupport, R.id.ll_myattention,
			R.id.ll_myRedBag, R.id.btn_saveMoney, R.id.btn_withdrawMoney,
			R.id.ll_myBook, R.id.ll_integral, R.id.btn_login,
			R.id.btn_register, R.id.tv_forgetpwd_login, R.id.ivEye,
			R.id.ll_myInveteFriend, R.id.auth, R.id.ll_invest_cirtify })
	private void OnClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.ll_invest_cirtify:// 投资认证
			if (ZCApplication.getInstance().getUserInfo().getIsrealname() == null
					|| ZCApplication.getInstance().getUserInfo()
							.getIsrealname().equals("0")) {
				// ToastUtil.showToast(getActivity(), "您还未实名认证，请实名认证后操作");
				showRedirectDialog();
				return;
			}
			if (!StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
					.getUserType())
					&& ZCApplication.getInstance().getUserInfo().getUserType()
							.equals("2")) {

				intent = new Intent(getActivity(),
						Activity_InvestCerciticyComFirst.class);
				startActivity(intent);
			} else {
				intent = new Intent(getActivity(),
						Activity_InvestCerciticy.class);
				startActivity(intent);
			}
			break;
		case R.id.ll_myBook:// 我的预约
			startActivity(new Intent(getActivity(), Activity_MyBooked.class));
			break;
		case R.id.ll_myfaqi:// 我的发起
			startActivity(new Intent(getActivity(), Activity_MyFaqi.class));
			break;
		case R.id.ll_mysupport:// 我的订单
			startActivity(new Intent(getActivity(), Activity_MyOrder.class));
			break;
		case R.id.ll_myattention:// 我的关注
			startActivity(new Intent(getActivity(), Activity_MyAttention.class));
			break;
		case R.id.ll_myRedBag:// 我的红包
			startActivity(new Intent(getActivity(), Activity_MyRedBag.class));
			break;
		case R.id.ll_integral:// 我的积分
			startActivity(new Intent(getActivity(), Activity_integrate.class));
			break;
		case R.id.btn_saveMoney:// 充值
			if (ZCApplication.getInstance().getUserInfo().getIsrealname() == null
					|| ZCApplication.getInstance().getUserInfo()
							.getIsrealname().equals("0")) {
				// ToastUtil.showToast(getActivity(), "您还未实名认证，请实名认证后操作");
				showRedirectDialog();
				return;
			}
			intent = new Intent();
			intent.setClass(getActivity(), TopupAndTiXianActivity.class);
			intent.setFlags(1);
			startActivity(intent);
			break;
		case R.id.btn_withdrawMoney:// 提现
			if (ZCApplication.getInstance().getUserInfo().getIsrealname() == null
					|| ZCApplication.getInstance().getUserInfo()
							.getIsrealname().equals("0")) {
				// ToastUtil.showToast(getActivity(), "您还未实名认证，请实名认证后操作");
				showRedirectDialog();
				return;
			}
			if (StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
					.getCardno())) {
				ToastUtil.showToast(getActivity(), "您还未绑定银行卡，请您先绑定银行卡");
				hftxBindCard_yibao();
				return;
			}
			intent = new Intent();
			intent.setClass(getActivity(), TopupAndTiXianActivity.class);
			intent.setFlags(0);
			startActivity(intent);
			break;
		case R.id.btn_login:
			String password = et_pass.getText().toString();
			if (StringUtils.isEmpty(et_username.getText().toString())) {
				ToastUtil.showToast(getActivity(), "请输入手机号");
				return;
			}
			if (StringUtils.isEmpty(password)) {
				ToastUtil.showToast(getActivity(), "请输入密码");
				return;
			}
			try {
				password = EncryptionAndDecryptionUtil.encode(password);
			} catch (Exception e) {
				LogUtil.d("password encode Error：" + e.getMessage());
				e.printStackTrace();
			}
			goLogin(et_username.getText().toString().trim(), password);
			break;
		case R.id.btn_register:
			startActivityForResult(new Intent(getActivity(),
					Activity_Register.class), CommonUtils.CODE.GOREGISTER);
			break;
		case R.id.tv_forgetpwd_login:// 忘记密码
			startActivity(new Intent(getContext(), Activity_findPassWord.class));
			break;
		case R.id.ivEye:
			if (ivEye.getTag() == null || ((Integer) ivEye.getTag()) == 0) {
				et_pass.setTransformationMethod(HideReturnsTransformationMethod
						.getInstance());
				ivEye.setImageResource(R.drawable.eye_y);
				ivEye.setTag(1);
				if (!StringUtils.isEmpty(et_pass.getText().toString())) {
					et_pass.setSelection(et_pass.getText().toString().length());// 光标置后
				} else {
					et_pass.setSelection(0);// 光标置后
				}
			} else {
				et_pass.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
				ivEye.setImageResource(R.drawable.eye_n);
				ivEye.setTag(0);
				if (!StringUtils.isEmpty(et_pass.getText().toString())) {
					et_pass.setSelection(et_pass.getText().toString().length());// 光标置后
				} else {
					et_pass.setSelection(0);// 光标置后
				}
			}
			break;
		case R.id.ll_myInveteFriend:// 邀请好友
			if (ZCApplication.getInstance().getUserInfo().getIsrealname() == null
					|| ZCApplication.getInstance().getUserInfo()
							.getIsrealname().equals("0")) {
				// ToastUtil.showToast(getActivity(), "您还未实名认证，请实名认证后操作");
				showRedirectDialog();
				return;
			}
			// startActivity(new Intent(getActivity(),
			// Activity_InviteFriends.class));
			startGetInviteCodeRequest();
			break;
		case R.id.auth:
			startActivity(new Intent(getActivity(),
					OpenChargeFirstActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {

		getMyAccount();
	}

	/**
	 * 弹出未实名认证弹框
	 */
	public void showRealNameDialog() {
		// 显示需要认证的界面
		rl_dialog.setVisibility(View.VISIBLE);
		dialogTitle.setText("提示");
		dialogMessage.setText("您还没有身份认证，请先认证您的信息");
		dialogBtn.setText("立即认证");
		dialogBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LockPatternService.setIfFirstLogin(getActivity(), false);
				// 打开相应的界面
				startActivity(new Intent(getActivity(),
						OpenChargeFirstActivity.class));
			}
		});
		dialogBtn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LockPatternService.setIfFirstLogin(getActivity(), false);
				rl_dialog.setVisibility(View.GONE);
				auth.setVisibility(View.VISIBLE);
				layout_companyUserNoShow.setVisibility(View.VISIBLE);
			}
		});

	}

	/**
	 * 获取时时余额
	 */
	public void getBalamount(boolean isShowLoading) {
		// 如果已经实名认证，从第三方获取账户信息
		GetThirdAmountManager.getThirdAmtRequest(getActivity(), isShowLoading,
				new GetThirdAmountManagerCallBack() {

					@Override
					public void onSuccess(QueryMoney response) {
						DialogUtils.dismisLoading();
						ptr_refresh_invester.onRefreshComplete();

						amtTotal = response.getBalance() == null ? 0 + ""
								: Double.parseDouble(response.getBalance())
										+ "";
						amtAvailable = response.getAvailableAmount() == null ? 0 + ""
								: Double.parseDouble(response
										.getAvailableAmount()) + "";
						amtFrozeen = response.getFreezeAmount() == null ? 0 + ""
								: Double.parseDouble(response.getFreezeAmount())
										+ "";

						ZCApplication.getInstance().getUserInfo()
								.setBalamount(response.getBalance());
						ZCApplication.getInstance().getUserInfo()
								.setAvailableAmt(amtAvailable);

						completeAmtView(amtTotal, amtAvailable, amtFrozeen);

						// 存储银行卡信息
						if (!StringUtils.isEmpty(response.getCardNo())) {

							ZCApplication.getInstance().getUserInfo()
									.setCardno(response.getCardNo());
							ZCApplication.getInstance().getUserInfo()
									.setBankid(response.getBank());
						}

					}

					@Override
					public void onFailure() {
						DialogUtils.dismisLoading();
						ptr_refresh_invester.onRefreshComplete();
						completeAmtView("0", "0", "0");
					}

					@Override
					public void onFailure(String errorMsg) {
						DialogUtils.dismisLoading();
						ptr_refresh_invester.onRefreshComplete();
						ToastUtil.showToast(getActivity(), errorMsg);
						completeAmtView("0", "0", "0");
					}
				});
	}

	public void completeAmtView(String amtTotal, String amtAvailable,
			String amtFrozeen) {
		tv_amount_all.setText(StringUtils.getTwoPoint(amtTotal));
		tv_avaliable.setText(StringUtils.getTwoPoint(amtAvailable));
		tv_freeze.setText(StringUtils.getTwoPoint(amtFrozeen));

		account_eye_check.setChecked(state_eye);
		tv_amount_all.setTag(StringUtils.getTwoPoint(amtTotal));
		tv_avaliable.setTag(StringUtils.getTwoPoint(amtAvailable));
		tv_freeze.setTag(StringUtils.getTwoPoint(amtFrozeen));

		// 一定要放在最后执行的步骤后面，防止刷新数据后金额变明文造成状态错乱
		changeTxtState(state_eye);
	}

	/**
	 * 易宝支付方式_绑定银行卡
	 */
	private void hftxBindCard_yibao() {
		String url = Service_ThirdPay.BINDBANKCARD + "sessionId="
				+ app.userInfo.getSessionId();
		Intent bindIntent = new Intent(getActivity(), Activity_ThirdWeb.class);
		bindIntent.putExtra("url", url);
		bindIntent.putExtra(Activity_ThirdWeb.TITLENAME, "绑定提现银行卡");
		startActivity(bindIntent);
	}

	/**
	 * 获取账户信息，接着获取时时余额
	 */
	public void getMyAccount() {
		Service_Login.getAccountMessage(getActivity(),
				app.userInfo.getSessionId(), true, new UserCallBack() {

					@Override
					public void onSuccess(ResponseSupport sucResponse) {

						getBalamount(false);
					}

					@Override
					public void onFailure(ResponseSupport failResponse) {
						DialogUtils.dismisLoading();
						ptr_refresh_invester.onRefreshComplete();
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
						ptr_refresh_invester.onRefreshComplete();
					}
				});
	}

	/**
	 * 登录成功后，接着获取账户信息
	 * 
	 * @param username
	 * @param password
	 */
	public void goLogin(String username, String password) {
		// 登录后获取我的账户信息
		Service_Login.goLogin(getActivity(), et_username.getText().toString(),
				password, true, true, new UserCallBack() {

					@Override
					public void onSuccess(ResponseSupport sucResponse) {
						LogUtil.i("登录成功~~");
						ll_layout_login.setVisibility(View.GONE);
						rl_layout_account.setVisibility(View.VISIBLE);

						// 登录成功如果没有手势密码设置手势,可选择跳过
						if (StringUtils.isEmpty(LockPatternService
								.getLockPattern(getActivity()))) {
							Intent intent = new Intent(getActivity(),
									Activity_SetGestureLock.class);
							intent.putExtra(Activity_SetGestureLock.FIRSTSET,
									true);
							startActivityForResult(intent, 001);
						} else {
							checkAccount();
						}
						et_pass.setText("");
						et_username.setText("");

						DialogUtils.dismisLoading();
						ptr_refresh_invester.onRefreshComplete();
					}

					@Override
					public void onFailure(ResponseSupport failResponse) {
						DialogUtils.dismisLoading();
						ptr_refresh_invester.onRefreshComplete();
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
						ptr_refresh_invester.onRefreshComplete();
					}
				});
	}

	/**
	 * 获取邀请链接
	 */
	private void startGetInviteCodeRequest() {
		final String sessionId = ZCApplication.getInstance().getUserInfo()
				.getSessionId();
		GetInviteCodeRequest request = new GetInviteCodeRequest();
		request.setSessionId(sessionId);

		NetWorkRequestManager.sendRequestPost(getActivity(), false, request,
				GetInviteCodeResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						GetInviteCodeResponse model = (GetInviteCodeResponse) response;
						if (model != null) {
							shareCodeUrl = model.getCode() != null ? model
									.getCode() : "";
							shareContent = model.getDesc() != null ? model
									.getDesc() : "";

							showShare();
						}
					}

					@Override
					public void onFailure(ResponseSupport response) {
						if (!StringUtils.isEmpty(response.getMessage())) {
							ToastUtil.showToast(getActivity(),
									response.getMessage());
						}
					}

					@Override
					public void failure() {
						LogUtil.i("获取邀请谅解失败~~");
					}
				});
	}

	@Override
	public void onSuccess(ResponseSupport response) {
	}

	@Override
	public void failure() {
	}

	@Override
	public void onFailure(ResponseSupport response) {
	}

	@Override
	protected void lazyLoad() {

	}

	/**
	 * 调起第三方分享视图
	 */
	private void showShare() {
		if (StringUtils.isEmpty(shareCodeUrl)) {
			ToastUtil.showToast(getActivity(), "分享url为空");
			return;
		}
		if (StringUtils.isEmpty(shareContent)) {
			ToastUtil.showToast(getActivity(), "分享内容描述为空");
			return;
		}
		ShareUtils.showShare(getActivity(), shareCodeUrl, shareContent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_DOLOGIN:
			if (Activity.RESULT_OK == resultCode) {
				LogUtil.d("==登陆成功");
			} else {
				Activity_Main activity_Main = (Activity_Main) getActivity();
				activity_Main.setCurrentTabByTag(activity_Main.TAB_Home);
			}
			break;
		case CommonUtils.CODE.GOREGISTER:
			if (resultCode == getActivity().RESULT_OK) {
				LogUtil.i("注册后登陆成功");
				// 登录成功如果没有手势密码设置手势
				if (StringUtils.isEmpty(LockPatternService
						.getLockPattern(getActivity()))) {
					Intent intent = new Intent(getActivity(),
							Activity_SetGestureLock.class);
					intent.putExtra(Activity_SetGestureLock.FIRSTSET, true);
					startActivityForResult(intent, 001);
				}
			}
		case 001:// 手势设置
			if (getActivity().RESULT_OK == resultCode) {
				LockPatternService.isOpenLockPattern(getActivity(), true);
				LogUtil.i("手势设置成功，开关:"
						+ LockPatternService.getIsOpenLockPatten(getActivity()));
			} else if (getActivity().RESULT_CANCELED == resultCode) {// 跳过手势
				LockPatternService.isOpenLockPattern(getActivity(), false);
			}
			break;

		default:
			break;
		}
	}

	public void showRedirectDialog() {
		new AlertDialog(getActivity()).builder().setMsg("您尚未实名认证，请实名认证后操作")
				.setPositiveButton("立即认证", new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(getActivity(),
								OpenChargeFirstActivity.class));
					}
				}).setNegativeButton("稍后再说", new OnClickListener() {

					@Override
					public void onClick(View v) {
					}
				}).show();
	}

	/**
	 * 金额密文、明文转化
	 * 
	 * @param state
	 */
	private void changeTxtState(boolean state) {

		StringUtils.hiddenOrVisible(tv_amount_all, state);
		StringUtils.hiddenOrVisible(tv_avaliable, state);
		StringUtils.hiddenOrVisible(tv_freeze, state);
	}
}
