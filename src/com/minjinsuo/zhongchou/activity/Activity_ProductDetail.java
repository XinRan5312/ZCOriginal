package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.ButtonPrjIsAttenRequest;
import net.zkbc.p2p.fep.message.protocol.ButtonPrjIsAttenResponse;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListRequest;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListResponse;
import net.zkbc.p2p.fep.message.protocol.GetInviteCodeRequest;
import net.zkbc.p2p.fep.message.protocol.GetInviteCodeResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjCustBuyByPrjIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjCustBuyByPrjIdResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoByIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoByIdResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjProgressListRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjProgressListResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjRwdByPrjIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjRwdByPrjIdResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjRwdByPrjIdResponse.ElementPrjRwdList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_ProDetailReward;
import com.minjinsuo.zhongchou.adapter.Adapter_Topic_productdetail;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ShareUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.CircleImageView;
import com.minjinsuo.zhongchou.view.ListViewForScorllView;
import com.minjinsuo.zhongchou.view.ObservableScrollView;
import com.minjinsuo.zhongchou.view.ObservableScrollView.ScrollViewListener;

/**
 * 产品详情页
 */
@SuppressLint("NewApi")
public class Activity_ProductDetail extends Activity_Base implements
		ScrollViewListener {
	@ViewInject(R.id.myScrollView)
	private ObservableScrollView myScrollView;
	@ViewInject(R.id.title_bar)
	private RelativeLayout title_bar;
	@ViewInject(R.id.ll_contains)
	LinearLayout ll_contains;// 项目公告进展
	@ViewInject(R.id.ll_contains_person)
	LinearLayout ll_contains_person;// 支持人数布局
	@ViewInject(R.id.ll_project_dynamic)
	LinearLayout ll_project_dynamic;// 项目动态
	@ViewInject(R.id.lv_topic)
	private ListViewForScorllView lv_topic;// 话题列表
	@ViewInject(R.id.ll_lingtou)
	private LinearLayout ll_lingtou;// 领投人布局
	@ViewInject(R.id.ll_supportPerson)
	private LinearLayout ll_supportPerson;// 支持者
	@ViewInject(R.id.rl_topic_goMore)
	private LinearLayout rl_topic_goMore;// 话题布局点击
	@ViewInject(R.id.iv_menu)
	private ImageView iv_menu;// 标题栏右上角收藏按钮
	@ViewInject(R.id.iv_big_bg)
	private ImageView iv_big_bg;// 顶部大图

	// 下面是顶部标的详情基本信息控件声明
	@ViewInject(R.id.tv_title_product)
	private TextView tv_title;
	@ViewInject(R.id.tv_des)
	private TextView tv_des;
	@ViewInject(R.id.tv_supportCnt)
	private TextView tv_supportCnt;
	@ViewInject(R.id.tv_suppedAmt)
	private TextView tv_suppedAmt;
	@ViewInject(R.id.tv_totalAmt)
	private TextView tv_totalAmt;
	@ViewInject(R.id.tv_dayleft)
	private TextView tv_dayleft;
	@ViewInject(R.id.tv_progress)
	private TextView tv_progress;
	@ViewInject(R.id.horizontal_pb)
	private ProgressBar horizontal_pb;

	// 下面是发起人信息控件声明
	@ViewInject(R.id.tv_invest_title)
	private TextView tv_invest_title;
	@ViewInject(R.id.tv_invest_des)
	private TextView tv_invest_des;
	@ViewInject(R.id.iv_invest_headpic)
	private CircleImageView iv_invest_headpic;

	// 下面是回报列表
	@ViewInject(R.id.lv_reward)
	private ListViewForScorllView lv_reward;
	private Adapter_ProDetailReward adapter_reward;

	// 下面是公告进展控件声明
	@ViewInject(R.id.tv_notice_num)
	private TextView tv_notice_num;

	// 下面是投资人列表控件声明
	@ViewInject(R.id.tv_item_title)
	private TextView tv_item_title;

	// 下面是话题控件声明
	@ViewInject(R.id.tv_topic_num)
	private TextView tv_topic_num;
	@ViewInject(R.id.tv_more_topic)
	private TextView tv_more_topic;

	@ViewInject(R.id.btn_invest_now)
	private Button btn_invest_now;// 立即支持

	@ViewInject(R.id.product_detail)
	private LinearLayout product_detail;

	private int imageHeight;// 顶部图片的高度
	private CircleImageView circleIv;//
	private Adapter_Topic_productdetail adapter_topic;

	private String prjIntroH5Addr = "";// 项目详情对应的h5连接（获取标的详情接口返回）
	private String attentionType = "0";// 是否关注：1-关注 0-取消关注
	private String prjId = "";// 标id，从上个页面传递而来
	private String img_bg = "";// 标id，从上个页面传递而来
	private String shareCodeUrl, shareContent, sharePicUrl, shareTitle = null;

	private GetPrjProgressListResponse model_progress;// 公告进展（动态）
	private GetPrjRwdByPrjIdResponse model_reward;// 回报列表需要展示的数据
	private GetPrjCustBuyByPrjIdResponse model_investor;// 支持人员列表
	private GetCommonTalkListResponse model_topic;// 话题列表
	private GetPrjInfoByIdResponse model_prj;// 标的详情
	private String status_prj = "";// 项目状态
	public ImageOptions imageOptions;

	@Override
	protected void onCreate(Bundle arg0) {
		// 设置状态栏透明
		CommonUtils.setStatusBarTransparent(Activity_ProductDetail.this);

		super.onCreate(arg0);
		setContentView(R.layout.aty_product_detail);
		x.view().inject(this);

		disableAutoScrollToBottom();
		initView();
		initListener();
		initData();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ZCApplication.getInstance().isLogin()
				&& ZCApplication.getInstance().isNeedRefresh) {
			ZCApplication.getInstance().isNeedRefresh = false;
			// 获取分享链接
			if (!StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
					.getIsrealname())
					&& ZCApplication.getInstance().getUserInfo()
							.getIsrealname().equals("1")) {// 获取邀请链接、邀请好友需要实名认证
				startGetInviteCodeRequest(true);
			}
		}
	}

	@Override
	protected void initView() {
		setTitleBack();
		setTitleText(getString(R.string.product_detail_title));
		title_bar.setBackgroundColor(Color.parseColor("#00000000"));

		if (ZCApplication.getInstance().isLogin()
				&& ZCApplication.getInstance().getUserInfo().getUserType()
						.equals("2")) {
			// 企业用户没有分享
		} else {
			setTitleRightDrawable2(R.drawable.share, new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (ZCApplication.getInstance().isLogin()) {
						if (ZCApplication.getInstance().getUserInfo()
								.getIsrealname() == null
								|| ZCApplication.getInstance().getUserInfo()
										.getIsrealname().equals("0")) {// 未实名认证，提示
							ToastUtil.showToast(Activity_ProductDetail.this,
									"您尚未实名认证，请到我的账户进行实名认证后操作");
						} else {
							if (!StringUtils.isEmpty(shareCodeUrl)
									&& !StringUtils.isEmpty(shareContent)) {
								ShareUtils.showShare(
										Activity_ProductDetail.this,
										shareCodeUrl, shareContent);
							} else {
								startGetInviteCodeRequest(false);
							}
						}
					} else {
						new AlertDialog(Activity_ProductDetail.this)
								.builder()
								.setMsg("您尚未登录，请登录后操作")
								.setPositiveButton("确定", new OnClickListener() {

									@Override
									public void onClick(View v) {
										startActivityForResult(new Intent(
												Activity_ProductDetail.this,
												Activity_Login.class),
												AppContants.ACT_REQUEST_CODE);
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
		}
		iv_menu.setImageResource(R.drawable.collect);
	}

	@Override
	protected void initData() {
		if (getIntent() != null) {
			if (!StringUtils.isEmpty(getIntent().getStringExtra("prjId"))) {
				prjId = getIntent().getStringExtra("prjId");
				img_bg = getIntent().getStringExtra("img");

				// 填充顶部图片
				imageOptions = new ImageOptions.Builder()
						.setRadius(DensityUtil.dip2px(0)).setIgnoreGif(false)
						.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
						.setLoadingDrawableId(R.drawable.loading_big)
						.setFailureDrawableId(R.drawable.icon_faild).build();
				x.image().bind(iv_big_bg, img_bg, imageOptions);// 先把顶部图片加载出来，避免闪烁

				getPrjInfoById(true);
			}
		}
	}

	@Override
	protected void initListener() {
		if (VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 动态设置标题栏的高度来适应沉浸式效果
			int statusBarHeight = CommonUtils.getStatusBarHeight(this);
			ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) title_bar
					.getLayoutParams();
			linearParams.height = CommonUtils.dip2px(this, 50)
					+ statusBarHeight;
			title_bar.setLayoutParams(linearParams);
		}

		myScrollView.setImageView(iv_big_bg);// 下拉放大图片显示
		// 下面是监听控制标题栏颜色变化的操作
		ViewTreeObserver vto = iv_big_bg.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				iv_big_bg.getViewTreeObserver().removeGlobalOnLayoutListener(
						this);
				imageHeight = iv_big_bg.getHeight();

				myScrollView.setScrollViewListener(Activity_ProductDetail.this);
			}
		});
	}

	/**
	 * 防止ScrollView自动滚到底部
	 */
	private void disableAutoScrollToBottom() {
		myScrollView
				.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		myScrollView.setFocusable(true);
		myScrollView.setFocusableInTouchMode(true);
		myScrollView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.requestFocusFromTouch();
				return false;
			}
		});
	}

	@SuppressLint("NewApi")
	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		if (y <= 0) {
			title_bar.setBackgroundColor(Color.argb((int) 0, 51, 48, 146));
		} else if (y > 0 && y <= imageHeight) {
			float scale = (float) y / imageHeight;
			float alpha = (255 * scale);
			title_bar.setBackgroundColor(Color.argb((int) alpha, 51, 48, 146));// 颜色渐变
		} else {
			title_bar.setBackgroundColor(Color.argb((int) 255, 51, 48, 146));
		}

	}

	@Event({ R.id.ll_project_dynamic, R.id.ll_lingtou, R.id.ll_supportPerson,
			R.id.btn_invest_now, R.id.rl_topic_goMore, R.id.product_detail,
			R.id.iv_menu, R.id.tv_more_topic })
	private void OnClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.ll_project_dynamic:// 动态
			if (model_progress != null
					&& model_progress.getPrjProgressList() != null
					&& model_progress.getPrjProgressList().size() > 0) {
				intent = new Intent(Activity_ProductDetail.this,
						Activity_ProjectDynamic.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_progress);
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				LogUtil.i("model_progress is null or list is null or list'size is 0");
			}
			break;
		case R.id.ll_lingtou:
			intent = new Intent(Activity_ProductDetail.this,
					Activity_FaQierDetail.class);
			if (model_prj != null
					&& !StringUtils.isEmpty(model_prj.getCustId())) {
				intent.putExtra("userId", model_prj.getCustId());
				intent.putExtra("prjId", model_prj.getId());
				startActivity(intent);
			}
			break;
		case R.id.ll_supportPerson:// 支持人员
			if (model_investor != null
					&& model_investor.getPrjCustBuyList() != null
					&& model_investor.getPrjCustBuyList().size() > 0) {
				intent = new Intent(Activity_ProductDetail.this,
						Activity_SupportPerson.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_investor);
				intent.putExtras(bundle);
				startActivity(intent);
			}

			break;
		case R.id.btn_invest_now:// 立即支持
			if (!ZCApplication.getInstance().isLogin()) {
				new AlertDialog(Activity_ProductDetail.this).builder()
						.setMsg("您尚未登录，请登录后操作")
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivity(new Intent(
										Activity_ProductDetail.this,
										Activity_Login.class));
							}
						}).setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

							}
						}).show();
				return;
			}

			if (ZCApplication.getInstance().getUserInfo().getIsrealname() == null
					|| ZCApplication.getInstance().getUserInfo()
							.getIsrealname().equals("0")) {
				new AlertDialog(Activity_ProductDetail.this).builder()
						.setMsg("您还未实名认证，请实名认证后操作")
						.setPositiveButton("去认证", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivity(new Intent(
										Activity_ProductDetail.this,
										OpenChargeFirstActivity.class));
							}
						}).setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();
				return;
			}
			if (model_reward != null && model_reward.getPrjRwdList() != null
					&& model_reward.getPrjRwdList().size() > 0) {
				intent = new Intent(Activity_ProductDetail.this,
						Activity_SelectTypes.class);
				intent.putExtra(AppContants.TYPE_PRJ, model_prj.getSubProdTyp());// 主要区分是不是公益产品
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_reward);
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				ToastUtil.showToast(Activity_ProductDetail.this, "没有可选择回报项目");
			}
			break;
		case R.id.tv_more_topic:
		case R.id.rl_topic_goMore:// 更多话题
			if (model_topic != null && model_topic.getCommonTalkList() != null
					&& model_topic.getCommonTalkList().size() > 0) {
				intent = new Intent(Activity_ProductDetail.this,
						Activity_TopicMain.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_topic);
				intent.putExtras(bundle);
				intent.putExtra("id", prjId);
				startActivity(intent);
			} else {
				// ToastUtil.showToast(Activity_ProductDetail.this, "暂无话题列表");
			}

			break;
		case R.id.product_detail:// 项目详情
			if (!StringUtils.isEmpty(prjIntroH5Addr)) {
				intent = new Intent(Activity_ProductDetail.this,
						Act_CommonWeb.class);
				// Activity_ThirdWeb.class);
				// TestPlayMediaInWebView.class);
				intent.putExtra("url", prjIntroH5Addr);
				intent.putExtra("title", "项目详情");
				startActivity(intent);
			}
			break;
		case R.id.iv_menu:// 收藏按钮
			if (ZCApplication.getInstance().isLogin()) {
				// 调用收藏关注接口
				buttonPrjIsAtten();
			} else {
				new AlertDialog(Activity_ProductDetail.this).builder()
						.setMsg("您尚未登录，请登录后操作")
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivity(new Intent(
										Activity_ProductDetail.this,
										Activity_Login.class));
							}
						}).setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

							}
						}).show();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 1、获取标详情接口
	 * 
	 * @param isShow
	 */
	public void getPrjInfoById(boolean isShow) {
		if (StringUtils.isEmpty(prjId)) {
			return;
		}
		GetPrjInfoByIdRequest request = new GetPrjInfoByIdRequest();
		if (ZCApplication.getInstance().isLogin()) {
			request.setSessionId(ZCApplication.getInstance().getUserInfo()
					.getSessionId());
		}
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjInfoByIdResponse.class, this);
	}

	/**
	 * 3、获取回报列表
	 */
	public void getPrjRwdByPrjId(boolean isShow) {
		GetPrjRwdByPrjIdRequest request = new GetPrjRwdByPrjIdRequest();
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjRwdByPrjIdResponse.class, this);
	}

	/**
	 * 4、获取项目公告进展(动态)
	 */
	public void getPrjProgressList(boolean isShow) {
		GetPrjProgressListRequest request = new GetPrjProgressListRequest();
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjProgressListResponse.class, this);
	}

	/**
	 * 5、获取投资人列表
	 */
	public void getPrjCustBuyByPrjId(boolean isShow) {
		GetPrjCustBuyByPrjIdRequest request = new GetPrjCustBuyByPrjIdRequest();
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjCustBuyByPrjIdResponse.class, this);
	}

	/**
	 * 6、获取话题主贴列表
	 */
	public void getCommonTalkList(boolean isShow) {
		GetCommonTalkListRequest request = new GetCommonTalkListRequest();
		request.setPrjId(Integer.parseInt(prjId));
		request.setPageNo(1);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetCommonTalkListResponse.class, this);
	}

	/**
	 * 收藏关注接口/取消关注
	 */
	public void buttonPrjIsAtten() {
		ButtonPrjIsAttenRequest request = new ButtonPrjIsAttenRequest();
		request.setIsAttentFlag(attentionType);
		request.setPrjId(prjId);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				ButtonPrjIsAttenResponse.class, this);
	}

	/**
	 * 获取分享链接
	 */
	private void startGetInviteCodeRequest(boolean isShow) {
		final String sessionId = ZCApplication.getInstance().getUserInfo()
				.getSessionId();
		GetInviteCodeRequest request = new GetInviteCodeRequest();
		// request.setType(1);
		request.setSessionId(sessionId);

		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetInviteCodeResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {

		switch (response.getMessageId()) {
		case "getInviteCode":
			GetInviteCodeResponse model = (GetInviteCodeResponse) response;
			if (model != null) {
				shareCodeUrl = model.getCode() != null ? model.getCode() : "";
				shareContent = model.getDesc() != null ? model.getDesc() : "";
			}

			DialogUtils.dismisLoading();
			break;
		case "getPrjInfoById":// 获取标的详情
			model_prj = (GetPrjInfoByIdResponse) response;
			if (model_prj != null) {
				completeInvestorUI(model_prj);
				completeTopPrjInfo(model_prj);
			}

			getPrjRwdByPrjId(false);
			break;
		case "buttonPrjIsAtten":// 关注
			DialogUtils.dismisLoading();
			if (attentionType.equals("0")) {
				attentionType = "1";// 下一次点击时即可进行收藏
				ToastUtil.showToast(Activity_ProductDetail.this, "已取消关注");
				iv_menu.setImageResource(R.drawable.collect);
			} else {
				attentionType = "0";
				ToastUtil.showToast(Activity_ProductDetail.this, "关注成功");
				iv_menu.setImageResource(R.drawable.collected);
			}
			break;
		case "getPrjRwdByPrjId":// 回报列表
			model_reward = (GetPrjRwdByPrjIdResponse) response;
			if (model_reward != null && model_reward.getPrjRwdList() != null
					&& model_reward.getPrjRwdList().size() > 0) {
				initRewardList();
			} else {
				lv_reward.setVisibility(View.GONE);
			}

			getPrjProgressList(false);
			break;
		case "getPrjProgressList":// 项目公告进展(动态)
			model_progress = (GetPrjProgressListResponse) response;
			if (model_progress != null
					&& model_progress.getPrjProgressList() != null
					&& model_progress.getPrjProgressList().size() > 0) {
				tv_notice_num.setText("（"
						+ model_progress.getPrjProgressList().size() + "）");
				// 填充项目进展横向列表
				initNoticeData(model_progress);
			}

			getPrjCustBuyByPrjId(false);
			break;
		case "getPrjCustBuyByPrjId":// 投资人列表
			model_investor = (GetPrjCustBuyByPrjIdResponse) response;
			if (model_investor != null
					&& model_investor.getPrjCustBuyList() != null) {
				tv_item_title.setText("（"
						+ model_investor.getPrjCustBuyList().size() + "）");
				initPersonSupport(model_investor);
			}
			getCommonTalkList(false);
			break;
		case "getCommonTalkList":// 话题列表
			model_topic = (GetCommonTalkListResponse) response;
			if (model_topic != null) {
				// completeInvestorUI(model_investor);
				tv_topic_num.setText("（"
						+ model_topic.getCommonTalkList().size() + "）");
				initTopic(model_topic);
			}

			if (ZCApplication.getInstance().isLogin()
					&& !StringUtils.isEmpty(ZCApplication.getInstance()
							.getUserInfo().getIsrealname())
					&& ZCApplication.getInstance().getUserInfo()
							.getIsrealname().equals("1")) {

				startGetInviteCodeRequest(false);// 获取邀请链接、邀请好友需要实名认证
			} else {
				DialogUtils.dismisLoading();
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
	 * 1、补全头部标的详情信息
	 * 
	 * @param model_prj
	 */
	public void completeTopPrjInfo(GetPrjInfoByIdResponse model_prj) {
		if (StringUtils.isEmpty(model_prj.getStatus())) {
			LogUtil.i("请检查接口返回报文");
			return;
		}
		status_prj = model_prj.getStatus();

		btn_invest_now.setText("去支持");
		if (!StringUtils.isEmpty(status_prj) && status_prj.equals("40")) {
			btn_invest_now.setBackgroundResource(R.drawable.selector_btn);
			btn_invest_now.setClickable(true);
			btn_invest_now.setEnabled(true);
		} else {
			btn_invest_now.setBackgroundResource(R.drawable.shap_btn_gray);
			btn_invest_now.setClickable(false);
			btn_invest_now.setEnabled(false);
		}

		x.image().bind(iv_big_bg, model_prj.getHomePicAddress(), imageOptions);

		tv_title.setText(model_prj.getNam());
		tv_des.setText(model_prj.getDesp());
		tv_supportCnt.setText(model_prj.getSuppedCnt() + "人");
		// tv_supportCnt.setText(model_prj.getBuyedCount());
		tv_suppedAmt.setText(StringUtils.exceedW(model_prj.getSuppedAmt4Succ())
				+ "元");
		tv_totalAmt
				.setText(StringUtils.exceedW(model_prj.getPrjAmount()) + "元");
		prjIntroH5Addr = model_prj.getPrjIntroH5Addr();// 项目详情地址
		tv_dayleft.setText(model_prj.getPrjEndSurplusDays() + "天");
		// 投资进度
		double progress = Double.parseDouble(model_prj.getSuppedAmt4Succ())
				/ Double.parseDouble(model_prj.getPrjAmount());

		horizontal_pb.setProgress((int) (progress * 100));
		tv_progress.setText(StringUtils.round((progress * 100), 0) + "%");
		// 下面判断是否收藏
		if (model_prj.getIsAttion().equals("1")) {// 已关注
			iv_menu.setImageResource(R.drawable.collected);
			attentionType = "0";// 0：取消关注。下一次点击时即可取消关注
		} else {
			attentionType = "1";
		}
	}

	/**
	 * 2、补全发起人信息（产品详情是发起人，股权详情显示领头人）
	 * 
	 * @param model_investor
	 */
	public void completeInvestorUI(GetPrjInfoByIdResponse model_prj) {
		tv_invest_title.setText(model_prj.getCustLoginNam());
		tv_invest_des.setText(model_prj.getCustIntro() == null ? "暂无简介"
				: model_prj.getCustIntro());
		if (!StringUtils.isEmpty(model_prj.getHeaderAddress())) {
			ImageOptions io_head = new ImageOptions.Builder()
					.setRadius(DensityUtil.dip2px(0)).setIgnoreGif(false)
					.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
					.setLoadingDrawableId(R.drawable.head)
					.setFailureDrawableId(R.drawable.head).build();
			x.image().bind(iv_invest_headpic, model_prj.getHeaderAddress(),
					io_head);
		}
	}

	/**
	 * 3、初始化回报列表
	 */
	public void initRewardList() {
		adapter_reward = new Adapter_ProDetailReward(this, status_prj,
				model_prj.getSubProdTyp());
		List<ElementPrjRwdList> list_rawData = new ArrayList<ElementPrjRwdList>();
		boolean isHadWSFX = false;
		if (model_reward != null && model_reward.getPrjRwdList() != null) {
			// 保证列表中只有一条无私奉献选项
			for (int i = 0; i < model_reward.getPrjRwdList().size(); i++) {
				ElementPrjRwdList model = new ElementPrjRwdList();
				if (model_reward.getPrjRwdList().get(i).getRwdTyp().equals("3")) {
					if (!isHadWSFX) {
						model.setId(model_reward.getPrjRwdList().get(i).getId());
						model.setRwdTyp(model_reward.getPrjRwdList().get(i)
								.getRwdTyp());
						model.setPerSuppAmt(model_reward.getPrjRwdList().get(i)
								.getPerSuppAmt());
						isHadWSFX = true;
						list_rawData.add(0, model);
					}
				} else {
					model.setId(model_reward.getPrjRwdList().get(i).getId());
					model.setRwdTyp(model_reward.getPrjRwdList().get(i)
							.getRwdTyp());
					model.setPerSuppAmt(model_reward.getPrjRwdList().get(i)
							.getPerSuppAmt());
					list_rawData.add(model);
				}
			}
			LogUtil.i(JSON.toJSONString(list_rawData));
			adapter_reward.setData(list_rawData);
			lv_reward.setAdapter(adapter_reward);
		}

		// 注：点击事件 在adapter中添加
	}

	/**
	 * 4、动态添加view——填充项目公告进展数据
	 */
	public void initNoticeData(final GetPrjProgressListResponse model_progress) {
		for (int i = 0; i < model_progress.getPrjProgressList().size(); i++) {
			View child_person = getLayoutInflater().inflate(
					R.layout.child_item_notice, null);
			LinearLayout ll_child_notice_container = (LinearLayout) child_person
					.findViewById(R.id.ll_child_notice_container);
			// 此处应该获取textView的id并赋值
			TextView tv_content = (TextView) child_person
					.findViewById(R.id.tv_content);
			TextView tv_time = (TextView) child_person
					.findViewById(R.id.tv_time);
			ImageView iv_line_left = (ImageView) child_person
					.findViewById(R.id.iv_line_left);
			ImageView iv_line_right = (ImageView) child_person
					.findViewById(R.id.iv_line_right);
			if (model_progress.getPrjProgressList().size() == 1) {
				iv_line_left.setVisibility(View.GONE);
				iv_line_right.setVisibility(View.GONE);
			} else {
				iv_line_left.setVisibility(View.VISIBLE);
				iv_line_right.setVisibility(View.VISIBLE);
			}

			tv_content.setText(model_progress.getPrjProgressList().get(i)
					.getTitle()
					+ "\n"
					+ (model_progress.getPrjProgressList().get(i)
							.getFileIdMemo() == null ? "" : model_progress
							.getPrjProgressList().get(i).getFileIdMemo()));

			String dayResult = CommonUtils.daysBetween(
					StringUtils.getDate(model_progress.getPrjProgressList()
							.get(i).getCreateTime(), 2),
					com.minjinsuo.zhongchou.utils.DateUtils.getCurrentTime());
			tv_time.setText(dayResult);
			ll_contains.addView(ll_child_notice_container);

			ll_child_notice_container.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Activity_ProductDetail.this,
							Activity_ProjectDynamic.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("model", model_progress);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}
	}

	/**
	 * 5、动态添加view——添加支持人员数据
	 */
	public void initPersonSupport(
			final GetPrjCustBuyByPrjIdResponse model_investor) {

		for (int i = 0; i < model_investor.getPrjCustBuyList().size(); i++) {
			View child_person = getLayoutInflater().inflate(
					R.layout.child_item_person, null);
			LinearLayout ll_child_per = (LinearLayout) child_person
					.findViewById(R.id.ll_child_person_container);
			ImageView circle_per = (ImageView) child_person
					.findViewById(R.id.circleIv);
			TextView tv_name = (TextView) child_person
					.findViewById(R.id.tv_name);
			tv_name.setText(model_investor.getPrjCustBuyList().get(i)
					.getCustLoginNam());
			// 填充顶部图片
			imageOptions = new ImageOptions.Builder()
					.setRadius(DensityUtil.dip2px(0)).setIgnoreGif(false)
					.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
					.setLoadingDrawableId(R.drawable.loading)
					.setFailureDrawableId(R.drawable.head).build();
			x.image().bind(
					circle_per,
					model_investor.getPrjCustBuyList().get(i)
							.getCustPortraitAddr(), imageOptions);
			ll_contains_person.addView(ll_child_per);
			ll_contains_person.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Activity_ProductDetail.this,
							Activity_SupportPerson.class);
					intent.putExtra("prjId", model_prj.getId());
					Bundle bundle = new Bundle();
					bundle.putSerializable("model", model_investor);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
		}
	}

	/**
	 * 6、动态添加话题列表数据
	 */
	public void initTopic(final GetCommonTalkListResponse model_topic) {
		adapter_topic = new Adapter_Topic_productdetail(this);
		adapter_topic.setData(model_topic.getCommonTalkList());

		// 发起话题视图
		View view_topic = getLayoutInflater().inflate(
				R.layout.child_item_topic, null);
		LinearLayout ll_startTopic = (LinearLayout) view_topic
				.findViewById(R.id.ll_startTopic);
		lv_topic.addFooterView(view_topic);
		lv_topic.setAdapter(adapter_topic);

		if (model_topic.getCommonTalkList().size() >= 2) {
			// 大于两个话题则 显示“更多话题”，可点击
			tv_more_topic.setVisibility(View.VISIBLE);
			rl_topic_goMore.setClickable(true);
		}
		// else {
		// tv_more_topic.setVisibility(View.INVISIBLE);
		// rl_topic_goMore.setClickable(false);
		// }

		// 跳转到查看主贴详情（回复列表）
		lv_topic.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LogUtil.i("click is " + position);
				Intent intent;
				if (model_topic.getCommonTalkList().size() >= 2) {
					if (position < 2) {// 因为此页最多显示2个话题贴子，最少显示0个
						intent = new Intent(Activity_ProductDetail.this,
								Activity_TopicReply.class);
						intent.putExtra("id", prjId);
						intent.putExtra("ctId", model_topic.getCommonTalkList()
								.get(position).getId());
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_topic
								.getCommonTalkList().get(position));// 主贴对象
						intent.putExtras(bundle);

					} else {
						intent = new Intent(Activity_ProductDetail.this,
								Activity_TopicMain.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_topic);
						intent.putExtras(bundle);
						intent.putExtra("id", prjId);
					}
				} else if (model_topic.getCommonTalkList().size() == 1) {
					if (position == 0) {
						intent = new Intent(Activity_ProductDetail.this,
								Activity_TopicReply.class);
						intent.putExtra("id", prjId);
						intent.putExtra("ctId", model_topic.getCommonTalkList()
								.get(0).getId());
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_topic
								.getCommonTalkList().get(0));// 主贴对象
						intent.putExtras(bundle);
					} else {
						intent = new Intent(Activity_ProductDetail.this,
								Activity_TopicMain.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_topic);
						intent.putExtras(bundle);
						intent.putExtra("id", prjId);
					}
				} else {
					intent = new Intent(Activity_ProductDetail.this,
							Activity_TopicMain.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("model", model_topic);
					intent.putExtras(bundle);
					intent.putExtra("id", prjId);
				}

				startActivity(intent);
			}
		});
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);

		if (arg1 == RESULT_OK) {
			ZCApplication.getInstance().isNeedRefresh = true;
		}
	}
}
