package com.minjinsuo.zhongchou.fragment;

import java.util.HashMap;
import java.util.Map;

import net.zkbc.p2p.fep.message.protocol.ChangePortraitRequest;
import net.zkbc.p2p.fep.message.protocol.ChangePortraitResponse;
import net.zkbc.p2p.fep.message.protocol.CheckUpdateRequest;
import net.zkbc.p2p.fep.message.protocol.CheckUpdateResponse;
import net.zkbc.p2p.fep.message.protocol.GetCommNewListRequest;
import net.zkbc.p2p.fep.message.protocol.GetCommNewListResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_AddressManager;
import com.minjinsuo.zhongchou.activity.Activity_CommonRead;
import com.minjinsuo.zhongchou.activity.Activity_Main;
import com.minjinsuo.zhongchou.activity.Activity_MsgCommon;
import com.minjinsuo.zhongchou.activity.Activity_Pwd_Manage;
import com.minjinsuo.zhongchou.activity.Activity_ThirdWeb;
import com.minjinsuo.zhongchou.activity.Activity_msgCenter;
import com.minjinsuo.zhongchou.http.GetThirdAmountManager;
import com.minjinsuo.zhongchou.http.GetThirdAmountManager.GetThirdAmountManagerCallBack;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.QueryMoney;
import com.minjinsuo.zhongchou.service.LockPatternService;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.service.UpdateService;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CameraManager;
import com.minjinsuo.zhongchou.utils.CameraManager.OnHeadImageManagerFinish;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.CircleImageView;

/**
 * 更多tab内容
 */
public class Fragment_More extends Fragment_Base implements
		OnHeadImageManagerFinish {

	@ViewInject(R.id.ll_head)
	private LinearLayout ll_head;// 头像
	@ViewInject(R.id.ll_bindCard)
	private LinearLayout ll_bindCard;// 绑卡
	@ViewInject(R.id.ll_contactUs)
	private LinearLayout ll_contactUs;// 联系我们
	@ViewInject(R.id.ll_pwdSet)
	private LinearLayout ll_pwdSet;// 密码设置
	@ViewInject(R.id.ll_aboutUs)
	private LinearLayout ll_aboutUs;// 关于我们
	@ViewInject(R.id.ll_helpCenter)
	private LinearLayout ll_helpCenter;// 帮助中心
	@ViewInject(R.id.ll_platNotice)
	private LinearLayout ll_platNotice;// 平台公告
	@ViewInject(R.id.ll_versionCheck)
	private LinearLayout ll_versionCheck;// 检查更新
	@ViewInject(R.id.ll_add_manage)
	private LinearLayout ll_add_manage;// 地址管理
	@ViewInject(R.id.ll_noLoginUnShow)
	private LinearLayout ll_noLoginUnShow;// 登录后才显示的视图
	@ViewInject(R.id.civ_head)
	private CircleImageView civ_head;
	@ViewInject(R.id.tv_isLingTou)
	private TextView tv_isLingTou;

	@ViewInject(R.id.tv_isBankBinded)
	private TextView tv_isBankBinded;// 是否绑卡的文字标示
	@ViewInject(R.id.tv_account)
	private TextView tv_account;
	@ViewInject(R.id.tv_phone)
	private TextView tv_phone;
	@ViewInject(R.id.tv_version)
	private TextView tv_version;

	@ViewInject(R.id.btn_exit)
	private Button btn_exit;

	private View mView;
	private CameraManager mCameraManager;
	// private ZCApplication app;

	private static int REQUEST_CODE_BIND = 1;// 绑卡
	private ImageOptions imageOptions;
	private Activity_Main activity;
	// 屏幕宽度和高度
	private int width;
	private int height;
	private String versionStr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (Activity_Main) getActivity();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_more, null);
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
		if (ZCApplication.getInstance().isLogin()) {
			setTitleRightDrawable(mView, R.drawable.email,
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							startActivity(new Intent(getActivity(),
									Activity_msgCenter.class));
						}
					});

			// 是否领投人(10-项目发起人，20-项目领投人，30-跟投人，40-领投人 )
			if (!StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
					.getIdentity())) {
				if (ZCApplication.getInstance().getUserInfo().getIdentity()
						.equals("40")
						|| ZCApplication.getInstance().getUserInfo()
								.getIdentity().equals("20")) {
					tv_isLingTou.setVisibility(View.VISIBLE);
					tv_isLingTou.setText("领投人");
				} else if (ZCApplication.getInstance().getUserInfo()
						.getIdentity().equals("10")) {
					tv_isLingTou.setVisibility(View.VISIBLE);
					tv_isLingTou.setText("发起人");
				} else if (ZCApplication.getInstance().getUserInfo()
						.getIdentity().equals("20")) {
					tv_isLingTou.setText("合格投资人");
					tv_isLingTou.setVisibility(View.VISIBLE);
				}
			} else {
				tv_isLingTou.setVisibility(View.GONE);
			}
			// 初始化显示银行卡
			// tv_account.setText(StringUtils.blurNumForNM(5, 4, ZCApplication
			// .getInstance().getUserInfo().getLoginname()));
			tv_account.setText(ZCApplication.getInstance().getUserInfo()
					.getLoginname());
			if (!StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
					.getCardno())) {
				// 如果绑定过银行卡了则按钮不可点击
				ll_bindCard.setClickable(false);
				tv_isBankBinded.setText(StringUtils.blurNumForNM(4, 3,
						ZCApplication.getInstance().getUserInfo().getCardno()));
			} else {
				ll_bindCard.setClickable(true);
				tv_isBankBinded.setText("未绑定");
			}

			// 加载头像
			if (!StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
					.getPortrait())) {
				x.image()
						.bind(civ_head,
								ZCApplication.getInstance().getUserInfo()
										.getPortrait(), imageOptions);
			}

			ll_noLoginUnShow.setVisibility(View.VISIBLE);
			btn_exit.setVisibility(View.VISIBLE);
		} else {
			// 未登录，隐藏相关视图
			ll_noLoginUnShow.setVisibility(View.GONE);
			btn_exit.setVisibility(View.GONE);
			setTitleRightDrawable(mView, 0, new OnClickListener() {

				@Override
				public void onClick(View v) {
				}
			});
		}
	}

	@Override
	public void initView() {
		setTitleText(mView, getString(R.string.tab_more));
		versionStr = getVersion();
		tv_version.setText("当前版本" + versionStr);

		imageOptions = new ImageOptions.Builder()
				.setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
				// .setRadius(DensityUtil.dip2px(5))
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				.setCrop(false)
				// 很多时候设置了合适的scaleType也不需要它.
				// 加载中或错误图片的ScaleType
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.head)
				.setFailureDrawableId(R.drawable.head).build();
	}

	@Override
	public void initData() {

		// 上传头像处理
		mCameraManager = new CameraManager(getActivity(), this);
	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onHeadImgFinish(Bitmap bitmap, String imagePath) {
		/**
		 * 此处应该加入上传图片的接口，获取到图片路径后再加载到imageView中 。通常上传的图片要base64编码后上传
		 */
		// iv_head.setImageBitmap(bitmap);
		String imgName = "";
		if (imagePath.contains("/")) {
			imgName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
		}
		LogUtil.d("图片名称==" + imgName);
		subHeadImage(bitmap, imgName);
	}

	/* 上传头像 */
	public void subHeadImage(final Bitmap bitmap, String imgName) {
		if (bitmap == null) {
			LogUtil.i("ZActivity_personalMsgInfo 263row--bitmap is null");
			return;
		}
		byte[] data = mCameraManager.createImageData(bitmap);
		byte[] base64 = Base64.encode(data, Base64.DEFAULT);
		String uploadfile = new String(base64);
		ChangePortraitRequest request = new ChangePortraitRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setHeadPic(uploadfile);
		request.setName(imgName);
		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				ChangePortraitResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						ChangePortraitResponse info = (ChangePortraitResponse) response;
						if (info.getResult().equals("success")) {
							x.image().bind(civ_head, info.getMessage(),
									imageOptions);
							DialogUtils.dismisLoading();
							// 下面要把返回的头像地址保存下来，用于手势密码显示
							ZCApplication.getInstance().getUserInfo()
									.setPortrait(info.getMessage());
							LockPatternService.saveUerPhoto(getActivity(),
									info.getMessage());
						}
					}

					@Override
					public void onFailure(ResponseSupport response) {
						DialogUtils.dismisLoading();
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
					}
				});
	}

	@Event({ R.id.ll_head, R.id.ll_invest_cirtify, R.id.btn_exit,
			R.id.ll_bindCard, R.id.ll_contactUs, R.id.ll_helpCenter,
			R.id.ll_platNotice, R.id.ll_add_manage, R.id.ll_pwdSet,
			R.id.ll_versionCheck, R.id.ll_aboutUs })
	private void OnClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.ll_head:
			mCameraManager.showAvatarDialog();
			break;

		case R.id.btn_exit:

			new AlertDialog(getActivity()).builder().setMsg("是否退出当前账号？")
					.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(View v) {
							ZCApplication.getInstance().userInfo = null;
							LockPatternService.LoginOut(getActivity());
							// 退出到桌面。关闭所有的acitivity
							// getActivity().getApplicationContext()
							// .sendBroadcast(new Intent("finish"));
							ZCApplication.getInstance().exit();
							activity.setCurrentTabByTag(Activity_Main.TAB_Home);
							ToastUtil.showToast(getActivity(), "已退出");
						}
					}).setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(View v) {

						}
					}).show();
			break;
		case R.id.ll_bindCard:
			if (ZCApplication.getInstance().getUserInfo().getIsrealname()
					.equals("0")) {
				ToastUtil.showToast(getActivity(), "请先到我的账户进行实名认证");
				return;
			}
			bindCard_yibao();
			break;
		case R.id.ll_contactUs:
			// callPhone();
			if (!StringUtils.isEmpty(tv_phone.getText().toString().trim()))
				CommonUtils.callPhone(getActivity(), tv_phone.getText()
						.toString().trim());
			break;
		case R.id.ll_helpCenter:// 帮助中心
			intent = new Intent(getActivity(), Activity_MsgCommon.class);
			intent.putExtra("flag", 0);
			startActivity(intent);
			break;
		case R.id.ll_platNotice:// 平台公告
			intent = new Intent(getActivity(), Activity_MsgCommon.class);
			intent.putExtra("flag", 1);
			startActivity(intent);
			break;
		case R.id.ll_add_manage:
			intent = new Intent(getActivity(), Activity_AddressManager.class);
			startActivity(intent);
			break;
		case R.id.ll_pwdSet:
			startActivity(new Intent(getActivity(), Activity_Pwd_Manage.class));
			break;
		case R.id.ll_aboutUs:
			getDataForAboutUs();
			// byte[] b = CommonUtils.getData(getActivity(), "aboutus.txt");
			// String content = "";
			// try {
			// content = new String(b, "UTF-8");
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			break;
		case R.id.ll_versionCheck:
			doUpdate();

			break;
		default:
			break;
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
		startActivityForResult(intent2, REQUEST_CODE_BIND);
	}

	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		mCameraManager.onActivityResult(arg0, arg2);// 触发获取返回值

		if (arg1 == getActivity().RESULT_OK) {
			if (arg0 == REQUEST_CODE_BIND) {
				ToastUtil.showToast(getActivity(), "绑卡成功");
				ZCApplication.getInstance().isNeedRefresh = true;
				getBalamount();
			}
		}
	}

	/**
	 * 获取时时余额
	 */
	public void getBalamount() {
		GetThirdAmountManager.getThirdAmtRequest(getActivity(), true,
				new GetThirdAmountManagerCallBack() {

					@Override
					public void onSuccess(QueryMoney response) {
						DialogUtils.dismisLoading();

						ZCApplication.getInstance().getUserInfo()
								.setBalamount(response.getBalance());
						// 存储银行卡信息
						if (!StringUtils.isEmpty(response.getCardNo())) {

							ZCApplication.getInstance().getUserInfo()
									.setCardno(response.getCardNo());
							ZCApplication.getInstance().getUserInfo()
									.setBankid(response.getBank());

							tv_isBankBinded.setText(StringUtils.blurNumForNM(4,
									3, response.getCardNo()));
							ll_bindCard.setClickable(false);
						}

					}

					@Override
					public void onFailure() {
						DialogUtils.dismisLoading();
					}

					@Override
					public void onFailure(String errorMsg) {
						DialogUtils.dismisLoading();
						ToastUtil.showToast(getActivity(), errorMsg);
					}
				});
	}

	/**
	 * 版本更新
	 */
	private void doUpdate() {
		getDeviceHeightAndWidth();
		Map<String, String> map = new HashMap<String, String>();
		map.put("version", versionStr);
		map.put("deviceheight", height + "");
		map.put("devicewidth", width + "");
		map.put("devicetype", "android");

		CheckUpdateRequest request = new CheckUpdateRequest();
		request.setDeviceheight(height + "");
		request.setDevicewidth(width + "");
		request.setDevicetype("android");
		request.setVersion(versionStr);
		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				CheckUpdateResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		final CheckUpdateResponse model = (CheckUpdateResponse) response;
		DialogUtils.dismisLoading();
		try {
			if (model != null) {
				if (model.getPkgurl() == null) {
					CommonUtils.showToast(getActivity(), "当前版本" + versionStr
							+ "  已是最新版本");
				} else {
					new AlertDialog(getActivity()).builder().setTitle("提示")
							.setMsg("亲,有新版本了,更省流量更强大\r\n是否下载最新版本?")
							.setPositiveButton("下载", new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(getActivity(),
											UpdateService.class);
									intent.putExtra(
											"Key_App_Name",
											getActivity().getString(
													R.string.app_name));
									intent.putExtra("Key_Down_Url",
											model.getPkgurl());
									CommonUtils.showToast(getActivity(),
											"后台下载更新中…");
									getActivity().startService(intent);
								}
							}).setNegativeButton("取消", new OnClickListener() {

								@Override
								public void onClick(View v) {

								}
							}).show();
				}
			}

		} catch (Exception e) {
			CommonUtils.showToast(getActivity(),
					getString(R.string.net_exception));
		}

	}

	/**
	 * 关于我们
	 * 
	 * @param type
	 *            :AboutUs
	 */
	private void getDataForAboutUs() {
		GetCommNewListRequest request = new GetCommNewListRequest();
		request.setPageNo(Integer.parseInt(pageNo));
		request.setPageSize(Integer.parseInt(pageSize));
		request.setNewType("AboutUs");

		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				GetCommNewListResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						DialogUtils.dismisLoading();
						GetCommNewListResponse model = (GetCommNewListResponse) response;
						String content = "";
						if (model != null && model.getInfoList() != null
								&& model.getInfoList().size() > 0) {
							content = model.getInfoList().get(0).getContent();
						}
						Intent intent = new Intent(getActivity(),
								Activity_CommonRead.class);
						intent.putExtra(Activity_CommonRead.CONTENT, content);
						intent.putExtra(Activity_CommonRead.TITLE, "关于民金所");
						startActivity(intent);
					}

					@Override
					public void onFailure(ResponseSupport response) {
						DialogUtils.dismisLoading();
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
					}
				});
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

	private void getDeviceHeightAndWidth() {
		WindowManager windowManager = (WindowManager) getActivity()
				.getApplicationContext().getSystemService(
						Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();

		height = display.getHeight();
		width = display.getWidth();
	}

	private String getVersion() {
		try {
			PackageManager manager = getActivity().getApplicationContext()
					.getPackageManager();
			PackageInfo info = manager.getPackageInfo(getActivity()
					.getApplicationContext().getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			return null;
		}
	}
}
