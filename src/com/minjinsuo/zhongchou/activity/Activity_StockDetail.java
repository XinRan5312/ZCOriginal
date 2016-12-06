package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ButtonPrjIsAttenRequest;
import net.zkbc.p2p.fep.message.protocol.ButtonPrjIsAttenResponse;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListRequest;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListResponse;
import net.zkbc.p2p.fep.message.protocol.GetInvestFileListRequest;
import net.zkbc.p2p.fep.message.protocol.GetInvestFileListResponse;
import net.zkbc.p2p.fep.message.protocol.GetInviteCodeRequest;
import net.zkbc.p2p.fep.message.protocol.GetInviteCodeResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjCustBuyByPrjIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjCustBuyByPrjIdResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoByIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoByIdResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjNoticeListRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjNoticeListResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjProgressListRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjProgressListResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_Topic_productdetail;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ShareUtils;
import com.minjinsuo.zhongchou.utils.StatusUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.CircleImageView;
import com.minjinsuo.zhongchou.view.ListViewForScorllView;
import com.minjinsuo.zhongchou.view.ObservableScrollView;
import com.minjinsuo.zhongchou.view.ObservableScrollView.ScrollViewListener;

/**
 * 股权详情页
 * 
 * @author zp
 *
 *         2016年6月20日
 */
public class Activity_StockDetail extends Activity_Base implements
		ScrollViewListener {
	@ViewInject(R.id.imageview)
	private ImageView image;
	@ViewInject(R.id.myScrollView)
	private ObservableScrollView myScrollView;
	@ViewInject(R.id.title_bar)
	private RelativeLayout title_bar;
	@ViewInject(R.id.ll_contains)
	LinearLayout ll_contains;// 项目进展
	@ViewInject(R.id.ll_contains_notice)
	LinearLayout ll_contains_notice;// 项目公告
	@ViewInject(R.id.ll_contains_person)
	LinearLayout ll_contains_person;// 支持人数布局
	@ViewInject(R.id.ll_contains_investfiles)
	LinearLayout ll_contains_investfiles;// 投资文件布局
	@ViewInject(R.id.ll_supportPerson)
	LinearLayout ll_supportPerson;// 投资人列表
	@ViewInject(R.id.ll_lingtou)
	LinearLayout ll_lingtou;// 领头人（支持人）
	@ViewInject(R.id.ll_relation_files)
	LinearLayout ll_relation_files;// 相关文件
	@ViewInject(R.id.ll_prj_detail)
	LinearLayout ll_prj_detail;// 项目详情
	@ViewInject(R.id.ll_call)
	LinearLayout ll_call;// 咨询
	@ViewInject(R.id.iv_menu)
	private ImageView iv_menu;// 标题栏右上角收藏按钮

	@ViewInject(R.id.btn_invest_now)
	Button btn_invest_now;// 立即投资

	// 下面是标的基本信息空间声明
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.des)
	private TextView des;
	@ViewInject(R.id.status)
	private TextView tv_status;
	@ViewInject(R.id.persentStock)
	private TextView persentStock;// 出让股份比例
	@ViewInject(R.id.supportedAmt)
	private TextView supportedAmt;// 已筹金额
	@ViewInject(R.id.amount)
	private TextView amount;// 目标金额
	@ViewInject(R.id.leftday)
	private TextView leftday;// 剩余天数
	@ViewInject(R.id.progress)
	private TextView tv_progress;// 进度
	@ViewInject(R.id.horizontal_pb)
	private ProgressBar horizontal_pb;

	// 下面是发起人、领头人信息
	@ViewInject(R.id.tv_rolerNam)
	private TextView tv_rolerNam;
	@ViewInject(R.id.tv_role_des)
	private TextView tv_role_des;
	@ViewInject(R.id.iv_invest_headpic)
	private CircleImageView iv_invest_headpic;

	// 下面是公告\进展控件声明
	@ViewInject(R.id.tv_notice_num)
	private TextView tv_notice_num;
	@ViewInject(R.id.tv_progress_num)
	private TextView tv_progress_num;
	@ViewInject(R.id.right_progress)
	private TextView right_progress;
	@ViewInject(R.id.right_notice)
	private TextView right_notice;
	@ViewInject(R.id.hs_progress)
	private HorizontalScrollView hs_progress;

	// 下面是投资人列表控件声明
	@ViewInject(R.id.tv_item_title)
	private TextView tv_item_title;
	@ViewInject(R.id.tv_investor_tip)
	private TextView tv_investor_tip;
	@ViewInject(R.id.right_investor_arrow)
	private TextView right_investor_arrow;
	@ViewInject(R.id.hs_scroll_investor)
	private HorizontalScrollView hs_scroll_investor;

	@ViewInject(R.id.tv_num_files)
	private TextView tv_num_files;
	@ViewInject(R.id.right_files_arrow)
	private TextView right_files_arrow;
	@ViewInject(R.id.hs_scroll)
	private HorizontalScrollView hs_scroll;

	// 下面是话题控件声明
	@ViewInject(R.id.lv_topic)
	private ListViewForScorllView lv_topic;// 话题列表
	@ViewInject(R.id.rl_topic_goMore)
	private LinearLayout rl_topic_goMore;// 话题布局点击
	@ViewInject(R.id.tv_topic_num)
	private TextView tv_topic_num;
	@ViewInject(R.id.tv_more_topic)
	private TextView tv_more_topic;

	private int imageHeight;// 顶部图片的高度
	private Adapter_Topic_productdetail adapter_topic;
	private String flag = "众筹中";
	private String ONLY_INVESTOR = "仅投资人可见";

	private String prjIntroH5Addr = "";// 项目详情对应的h5连接（获取标的详情接口返回）
	private String attentionType = "0";// 是否关注：1-关注 0-取消关注
	private String prjId = "";// 标id，从上个页面传递而来
	private String img_bg = "";// 标id，从上个页面传递而来
	private String servicePhone = "1";// 联系电话
	private boolean continueToLoad;// 是否继续加载的标示——登录后可加载全部请求,否则只请求标的详情
	private boolean isBuyed;// 该登陆用户是否已经投资过该标
	private String shareCodeUrl, shareContent, sharePicUrl, shareTitle = null;

	private GetPrjProgressListResponse model_progress;// 公告进展（动态）
	private GetPrjCustBuyByPrjIdResponse model_investor;// 支持人员列表
	private GetInvestFileListResponse model_file;// 相关文件
	private GetPrjNoticeListResponse model_notice;// 项目公告
	private GetPrjInfoByIdResponse model_prj;// 详情
	private GetCommonTalkListResponse model_topic;// 话题列表

	@Override
	protected void onCreate(Bundle arg0) {
		// 5.0+设置状态栏透明
		CommonUtils.setStatusBarTransparent(this);
		super.onCreate(arg0);
		setContentView(R.layout.aty_stock_detail);
		x.view().inject(this);

		disableAutoScrollToBottom();
		initView();
		initListener();
		initData();
	}

	@Override
	protected void initView() {
		setTitleBack();
		setTitleText(getString(R.string.crowd_detail_title));
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
							ToastUtil.showToast(Activity_StockDetail.this,
									"您尚未实名认证，请到我的账户进行实名认证后操作");
						} else {
							if (!StringUtils.isEmpty(shareCodeUrl)
									&& !StringUtils.isEmpty(shareContent)) {
								ShareUtils.showShare(Activity_StockDetail.this,
										shareCodeUrl, shareContent);
							} else {
								startGetInviteCodeRequest(false);
							}
						}
					} else {
						new AlertDialog(Activity_StockDetail.this)
								.builder()
								.setMsg("您尚未登录，请登录后操作")
								.setPositiveButton("确定", new OnClickListener() {

									@Override
									public void onClick(View v) {
										startActivityForResult(new Intent(
												Activity_StockDetail.this,
												Activity_Login.class), 001);
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
			if (getIntent().getStringExtra("id") != null) {
				prjId = getIntent().getStringExtra("id");
				img_bg = getIntent().getStringExtra("img");
				// 填充顶部图片
				imageOptions = new ImageOptions.Builder()
						.setRadius(DensityUtil.dip2px(0)).setIgnoreGif(false)
						.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
						.setLoadingDrawableId(R.drawable.loading_big)
						.setFailureDrawableId(R.drawable.icon_faild).build();
				x.image().bind(image, img_bg, imageOptions);

				if (ZCApplication.getInstance().isLogin()) {
					getPrjInfoById(true, prjId, true);
				} else {
					getPrjInfoById(true, prjId, false);
				}
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ZCApplication.getInstance().isLogin()
				&& ZCApplication.getInstance().isNeedRefresh) {
			ZCApplication.getInstance().isNeedRefresh = false;
			getPrjInfoById(true, prjId, true);
		}
	}

	@Override
	protected void initListener() {
		if (VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 动态设置状态栏的高度来适应沉浸式效果
			int statusBarHeight = CommonUtils.getStatusBarHeight(this);
			ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) title_bar
					.getLayoutParams();
			linearParams.height = CommonUtils.dip2px(this, 50)
					+ statusBarHeight;
			title_bar.setLayoutParams(linearParams);
		}

		myScrollView.setImageView(image);
		// 测量顶部图片的高度
		ViewTreeObserver vto = image.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				image.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				imageHeight = image.getHeight();

				myScrollView.setScrollViewListener(Activity_StockDetail.this);
			}
		});

	}

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

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		if (y <= 0) {
			// title_bar.setBackgroundColor(Color.argb((int) 0, 227, 29, 26));//
			title_bar.setBackgroundColor(Color.argb((int) 0, 51, 48, 146));// title_bar棰滆壊銆�

		} else if (y > 0 && y <= imageHeight) {
			float scale = (float) y / imageHeight;
			float alpha = (255 * scale);
			title_bar.setBackgroundColor(Color.argb((int) alpha, 51, 48, 146));// 棰滆壊鍙樺寲
		} else {
			title_bar.setBackgroundColor(Color.argb((int) 255, 51, 48, 146));// title_bar鏈壊
		}
	}

	@Event({ R.id.ll_project_progres, R.id.ll_project_notice, R.id.ll_lingtou,
			R.id.ll_supportPerson, R.id.ll_relation_files, R.id.btn_invest_now,
			R.id.ll_prj_detail, R.id.iv_menu, R.id.rl_topic_goMore,
			R.id.tv_more_topic, R.id.ll_call })
	private void OnClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.ll_call:
			if (!StringUtils.isEmpty(servicePhone)) {
				CommonUtils.callPhone(Activity_StockDetail.this, servicePhone);
			} else {
				ToastUtil.showToast(Activity_StockDetail.this, "客服电话为空");
			}
			break;
		case R.id.ll_prj_detail:
			if (!StringUtils.isEmpty(prjIntroH5Addr)) {
				intent = new Intent(Activity_StockDetail.this,
						Act_CommonWeb.class);
				intent.putExtra("url", prjIntroH5Addr);
				intent.putExtra("title", "项目详情");
				startActivity(intent);
			}
			break;
		case R.id.ll_project_progres:// 项目进展
			if (model_progress != null
					&& model_progress.getPrjProgressList() != null
					&& model_progress.getPrjProgressList().size() > 0) {
				intent = new Intent(Activity_StockDetail.this,
						Activity_ProjectDynamic.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_progress);
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				// ToastUtil.showToast(Activity_StockDetail.this, "暂无动态信息");
			}
			break;
		case R.id.ll_project_notice:// 项目公告
			if (right_notice.getText().toString().equals(ONLY_INVESTOR)) {
				ToastUtil.showToast(Activity_StockDetail.this, ONLY_INVESTOR);
				return;
			}
			if (model_notice != null && model_notice.getPrjNoticeList() != null
					&& model_notice.getPrjNoticeList().size() > 0) {
				intent = new Intent(Activity_StockDetail.this,
						Activity_ProjectNotice.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_notice);
				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		case R.id.ll_lingtou:
			intent = new Intent(Activity_StockDetail.this,
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
				intent = new Intent(Activity_StockDetail.this,
						Activity_SupportPerson.class);
				intent.putExtra("flag", 1);
				intent.putExtra("prjId", model_prj.getId());
				Bundle bundle_invest = new Bundle();
				bundle_invest.putSerializable("model", model_investor);
				intent.putExtras(bundle_invest);
				startActivity(intent);
			} else {
				// ToastUtil.showToast(Activity_StockDetail.this, "暂无支持人员");
			}

			break;
		case R.id.ll_relation_files:

			if (model_file != null && model_file.getInvestFileList() != null
					&& model_file.getInvestFileList().size() > 0) {
				intent = new Intent(Activity_StockDetail.this,
						Activity_RelationFiles.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_file);
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				// ToastUtil.showToast(Activity_StockDetail.this, "暂无文件");
			}
			break;
		case R.id.tv_more_topic:
		case R.id.rl_topic_goMore:// 更多话题
			if (model_topic != null && model_topic.getCommonTalkList() != null
					&& model_topic.getCommonTalkList().size() > 0) {
				intent = new Intent(Activity_StockDetail.this,
						Activity_TopicMain.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_topic);
				intent.putExtras(bundle);
				intent.putExtra("id", prjId);
				startActivity(intent);
			} else {
				// ToastUtil.showToast(Activity_StockDetail.this, "暂无话题列表");
			}

			break;
		case R.id.btn_invest_now:
			if (ZCApplication.getInstance().isLogin()) {
				if (ZCApplication.getInstance().getUserInfo().getIsrealname() == null
						|| ZCApplication.getInstance().getUserInfo()
								.getIsrealname().equals("0")) {
					new AlertDialog(Activity_StockDetail.this).builder()
							.setMsg("您还未实名认证，请实名认证后操作")
							.setPositiveButton("去认证", new OnClickListener() {

								@Override
								public void onClick(View v) {
									startActivity(new Intent(
											Activity_StockDetail.this,
											OpenChargeFirstActivity.class));
								}
							}).setNegativeButton("取消", new OnClickListener() {

								@Override
								public void onClick(View v) {

								}
							}).show();
					return;
				}

				intent = new Intent(Activity_StockDetail.this,
						Activity_SelectTypes.class);
				intent.putExtra("id", prjId);
				intent.putExtra("flag", flag);
				startActivity(intent);
			} else {
				new AlertDialog(Activity_StockDetail.this).builder()
						.setMsg("您尚未登录，请登录后操作")
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivityForResult(new Intent(
										Activity_StockDetail.this,
										Activity_Login.class), 001);
							}
						}).setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

							}
						}).show();
			}
			break;
		case R.id.iv_menu:// 收藏按钮
			if (ZCApplication.getInstance().isLogin()) {
				// 调用收藏关注接口
				buttonPrjIsAtten();
			} else {
				new AlertDialog(Activity_StockDetail.this).builder()
						.setMsg("您尚未登录，请登录后操作")
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivityForResult(new Intent(
										Activity_StockDetail.this,
										Activity_Login.class), 001);
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
	 * 1、获取标详情接口
	 * 
	 * @param isShow
	 * @param prjId
	 * @param isGoOn
	 *            :是否继续请求下面的接口
	 */
	public void getPrjInfoById(boolean isShow, String prjId, boolean isGoOn) {
		if (StringUtils.isEmpty(prjId)) {
			return;
		}

		continueToLoad = isGoOn;
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
	 * 2、获取相关文件接口
	 * 
	 * @param isShow
	 */
	public void getInvestFileList(boolean isShow) {
		GetInvestFileListRequest request = new GetInvestFileListRequest();
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetInvestFileListResponse.class, this);
	}

	/**
	 * 3、获取项目进展(动态)
	 */
	public void getPrjProgressList(boolean isShow) {
		GetPrjProgressListRequest request = new GetPrjProgressListRequest();
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjProgressListResponse.class, this);
	}

	/**
	 * 4、获取投资人列表
	 */
	public void getPrjCustBuyByPrjId(boolean isShow) {
		GetPrjCustBuyByPrjIdRequest request = new GetPrjCustBuyByPrjIdRequest();
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjCustBuyByPrjIdResponse.class, this);
	}

	/**
	 * 5、获取项目公告
	 */
	public void getPrjNoticeList(boolean isShow) {
		GetPrjNoticeListRequest request = new GetPrjNoticeListRequest();
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjNoticeListResponse.class, this);
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
				completeUI(model_prj);
				completeInvestorUI(model_prj);
			}

			if (continueToLoad) {
				getInvestFileList(false);
			} else {
				DialogUtils.dismisLoading();
			}
			break;
		case "getInvestFileList":// 投资文件
			model_file = (GetInvestFileListResponse) response;
			if (model_file != null) {
				completeFiles(model_file);
			}
			getPrjCustBuyByPrjId(false);
			break;
		case "buttonPrjIsAtten":// 关注
			DialogUtils.dismisLoading();
			if (attentionType.equals("0")) {
				attentionType = "1";// 下一次点击时即可进行收藏
				ToastUtil.showToast(Activity_StockDetail.this, "已取消关注");
				iv_menu.setImageResource(R.drawable.collect);
			} else {
				attentionType = "0";
				ToastUtil.showToast(Activity_StockDetail.this, "关注成功");
				iv_menu.setImageResource(R.drawable.collected);
			}
			break;
		case "getPrjCustBuyByPrjId":// 投资人列表
			model_investor = (GetPrjCustBuyByPrjIdResponse) response;
			initPersonSupport(model_investor);
			getPrjProgressList(false);
			break;
		case "getPrjProgressList":// 项目进展(动态)
			model_progress = (GetPrjProgressListResponse) response;
			if (model_progress != null
					&& model_progress.getPrjProgressList() != null
					&& model_progress.getPrjProgressList().size() > 0) {
				tv_progress_num.setText("（"
						+ model_progress.getPrjProgressList().size() + "）");
				// 填充项目进展横向列表
				initProgressData(model_progress);
			}

			getPrjNoticeList(false);
			break;
		case "getPrjNoticeList":
			model_notice = (GetPrjNoticeListResponse) response;

			if (model_notice != null && model_notice.getPrjNoticeList() != null
					&& model_notice.getPrjNoticeList().size() > 0) {
				tv_notice_num.setText("（"
						+ model_notice.getPrjNoticeList().size() + "）");
				// 填充项目进展横向列表
				initNoticeData(model_notice);
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
	 * 1、填充标的详情基本信息
	 */
	public void completeUI(GetPrjInfoByIdResponse info) {
		servicePhone = info.getServicePhone();
		if (StringUtils.isEmpty(info.getStatus())) {
			LogUtil.i("请检查接口是否返回标的数据");
			return;
		}
		if (info.getIsBuyed() != null && info.getIsBuyed().equals("1")) {
			isBuyed = true;
		} else {
			isBuyed = false;
		}
		x.image().bind(image, info.getHomePicAddress(), imageOptions);
		title.setText(info.getNam());
		des.setText(info.getDesp());
		if (!StringUtils.isEmpty(info.getPctSoldStock())) {
			persentStock.setText(info.getPctSoldStock() + "%");// 出让股份比例
		} else {
			LogUtil.i("出让股权比例字段 为 空~~");
		}
		supportedAmt.setText(StringUtils.exceedW(info.getSuppedAmt4Succ())
				+ "元");
		// amount.setText(info.getAmount());// 目标金额
		amount.setText(StringUtils.exceedW(info.getPrjAmount()) + "元");// 目标金额
		prjIntroH5Addr = info.getPrjIntroH5Addr();// 项目详情地址
		leftday.setText(info.getPrjEndSurplusDays() + "天");
		// 投资进度
		if (!StringUtils.isEmpty(info.getSuppedAmt4Succ())) {
			double progress = Double.parseDouble(info.getSuppedAmt4Succ())
					/ Double.parseDouble(info.getPrjAmount());

			horizontal_pb.setProgress((int) (progress * 100));
			tv_progress.setText(StringUtils.round(progress * 100, 0) + "%");
		}
		// 判断显示状态和按钮文字点击去向
		// 项目状态
		String status = info.getStatus();
		tv_status.setText(StatusUtils.getStatusByCode(status));
		tv_status.setVisibility(View.VISIBLE);
		btn_invest_now.setText("去投资");
		if ("10".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_green_radussmall);
		} else if ("20".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_red_radussmall);
			btn_invest_now.setText("去预约");
			btn_invest_now.setBackgroundResource(R.drawable.selector_btn_rect);
			btn_invest_now.setClickable(true);
			btn_invest_now.setEnabled(true);
			flag = "预热中";
		} else if ("30".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_green_radussmall);
		} else if ("35".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_red_radussmall);
		} else if ("40".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_green_radussmall);
			btn_invest_now.setText("去投资");
			btn_invest_now.setBackgroundResource(R.drawable.selector_btn_rect);
			btn_invest_now.setClickable(true);
			btn_invest_now.setEnabled(true);
			flag = "众筹中";
		} else if ("45".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_red_radussmall);
		} else if ("50".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_red_radussmall);
		} else if ("55".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_red_radussmall);
		} else if ("60".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_red_radussmall);
		} else if ("70".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_red_radussmall);
		} else if ("80".equals(status)) {
			tv_status.setBackgroundResource(R.drawable.shap_red_radussmall);
		}

		if (!continueToLoad) {
			right_files_arrow.setText("登录后可见");
			hs_scroll.setVisibility(View.GONE);
			StringUtils.setTextViewDrawableRight(getContext(),
					right_files_arrow, 0);

			tv_item_title.setText("（" + info.getBuyedCount() + "）");
			right_investor_arrow.setText("登录后可见");
			hs_scroll_investor.setVisibility(View.GONE);
			StringUtils.setTextViewDrawableRight(getContext(),
					right_investor_arrow, 0);

			tv_progress_num.setText("（" + info.getProgressCount() + "）");
			right_progress.setText("登录后可见");
			hs_progress.setVisibility(View.GONE);
			StringUtils.setTextViewDrawableRight(getContext(), right_progress,
					0);

			tv_topic_num.setText("（" + info.getTopicCount() + "）");
			tv_more_topic.setText("登录后可见");
			StringUtils
					.setTextViewDrawableRight(getContext(), tv_more_topic, 0);

			tv_notice_num.setText("（" + info.getPrjNoticeCount() + "）");
			right_notice.setText(ONLY_INVESTOR);
			StringUtils.setTextViewDrawableRight(getContext(), right_notice, 0);
		} else {

			right_files_arrow.setText("");
			hs_scroll.setVisibility(View.VISIBLE);
			StringUtils.setTextViewDrawableRight(getContext(),
					right_files_arrow, R.drawable.arrow_right);

			tv_item_title.setText("（" + info.getBuyedCount() + "）");
			right_investor_arrow.setText("");
			hs_scroll_investor.setVisibility(View.VISIBLE);
			StringUtils.setTextViewDrawableRight(getContext(),
					right_investor_arrow, R.drawable.arrow_right);

			tv_progress_num.setText("（" + info.getProgressCount() + "）");
			right_progress.setText("");
			hs_progress.setVisibility(View.VISIBLE);
			StringUtils.setTextViewDrawableRight(getContext(), right_progress,
					R.drawable.arrow_right);

			tv_topic_num.setText("（" + info.getTopicCount() + "）");
			tv_more_topic.setText("");
			StringUtils.setTextViewDrawableRight(getContext(), tv_more_topic,
					R.drawable.arrow_right);

			tv_notice_num.setText("（" + info.getPrjNoticeCount() + "）");
			right_notice.setText("");
			StringUtils.setTextViewDrawableRight(getContext(), right_notice,
					R.drawable.arrow_right);

		}
	}

	/**
	 * 1.2、补全发起人信息
	 * 
	 * @param model_investor
	 */
	public void completeInvestorUI(GetPrjInfoByIdResponse model_prj) {
		if (StringUtils.isEmpty(model_prj.getCustLoginNam())) {
			LogUtil.i("请检查是否有数据返回~~");
			return;
		}
		tv_rolerNam.setText(model_prj.getCustLoginNam());
		tv_role_des.setText(model_prj.getCustIntro() == null ? "暂无简介"
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

		// 下面判断是否收藏
		if (model_prj.getIsAttion().equals("1")) {// 已关注
			iv_menu.setImageResource(R.drawable.collected);
			attentionType = "0";// 0：取消关注。下一次点击时即可取消关注
		} else {
			attentionType = "1";
		}
	}

	/**
	 * 2、填充相关文件布局
	 */
	public void completeFiles(final GetInvestFileListResponse model_file) {
		ImageOptions imageOptions = new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(3))
				.setIgnoreGif(false)
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 很多时候设置了合适的scaleType也不需要它.
				// 加载中或错误图片的ScaleType
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
		if (model_file.getInvestFileList() != null) {
			tv_num_files.setText("（" + model_file.getInvestFileList().size()
					+ "）");
			for (int i = 0; i < model_file.getInvestFileList().size(); i++) {
				View child_person = getLayoutInflater().inflate(
						R.layout.child_item_investfiles, null);
				RelativeLayout ll_child_investfiles = (RelativeLayout) child_person
						.findViewById(R.id.ll_child_investfiles_container);
				ImageView iv_files = (ImageView) child_person
						.findViewById(R.id.iv_files);
				TextView tv_des = (TextView) child_person
						.findViewById(R.id.tv_des);

				String fileAdd = model_file.getInvestFileList().get(i)
						.getFileAddr();
				String fileType = fileAdd.substring(
						fileAdd.lastIndexOf(".") + 1, fileAdd.lastIndexOf("/"));
				LogUtil.i("文件类型==" + fileType);
				if (fileType.equals("jpg") || fileType.equals("png")
						|| fileType.equals("JPG") || fileType.equals("PNG")
						|| fileType.equals("jpeg") || fileType.equals("JPEG")
						|| fileType.equals("gif") || fileType.equals("GIF")) {
					x.image().bind(iv_files, fileAdd, imageOptions);
					tv_des.setVisibility(View.GONE);
				} else {// 文件形式
					iv_files.setImageResource(R.drawable.icon_files);
					tv_des.setText(model_file.getInvestFileList().get(i)
							.getFileNam());
					if (!StringUtils.isEmpty(model_file.getInvestFileList()
							.get(i).getFileSize())) {
						tv_des.setText(model_file.getInvestFileList().get(i)
								.getFileNam()
								+ "\n"
								+ model_file.getInvestFileList().get(i)
										.getFileSize());
					}

				}
				ll_contains_investfiles.addView(ll_child_investfiles);
				ll_child_investfiles.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Activity_StockDetail.this,
								Activity_RelationFiles.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_file);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
			}
		}
	}

	/**
	 * 3、动态添加view——添加支持人员数据
	 */
	public void initPersonSupport(
			final GetPrjCustBuyByPrjIdResponse model_investor) {
		ImageOptions mOptionApply = new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(3)).setIgnoreGif(false)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.apply)
				.setFailureDrawableId(R.drawable.apply).build();// 申请

		ImageOptions mOptionNoPic = new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(3)).setIgnoreGif(false)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.head).build();// 默认

		tv_investor_tip.setText("投资人");
		if (model_investor.getPrjCustBuyList() != null
				&& model_investor.getPrjCustBuyList().size() > 0) {
			tv_item_title.setText("（"
					+ model_investor.getPrjCustBuyList().size() + "）");
			// 遍历：将领头人调整到第一位位置
			for (int i = 0; i < model_investor.getPrjCustBuyList().size(); i++) {
				if (model_investor.getPrjCustBuyList().get(i).getIsPrjLeader()
						.equals("true")) {
					model_investor.getPrjCustBuyList().add(0,
							model_investor.getPrjCustBuyList().get(i));
					model_investor.getPrjCustBuyList().remove(i + 1);
					break;
				}
			}

			// 动态添加头像视图
			for (int i = 0; i < model_investor.getPrjCustBuyList().size(); i++) {

				View child_person = getLayoutInflater().inflate(
						R.layout.child_item_person, null);
				LinearLayout ll_child_per = (LinearLayout) child_person
						.findViewById(R.id.ll_child_person_container);
				ImageView circle_per = (ImageView) child_person
						.findViewById(R.id.circleIv);
				ImageView iv_diadema = (ImageView) child_person
						.findViewById(R.id.iv_diadema);// 皇冠图标
				TextView tv_name = (TextView) child_person
						.findViewById(R.id.tv_name);

				// 先判断列表中是否含有领头人，如果有显示带皇冠的图片，否则显示一个默认申请头像
				if (i == 0) {// 如果有领头人，则一定在第一个位置
					if (model_investor.getPrjCustBuyList().get(0)
							.getIsPrjLeader().equals("true")) {
						iv_diadema.setVisibility(View.VISIBLE);
					} else {// 没有领头人
						View child_person_def = getLayoutInflater().inflate(
								R.layout.child_item_person, null);
						LinearLayout ll_child_per_def = (LinearLayout) child_person_def
								.findViewById(R.id.ll_child_person_container);
						ImageView circle_per_def = (ImageView) child_person_def
								.findViewById(R.id.circleIv);
						ImageView iv_diadema_def = (ImageView) child_person_def
								.findViewById(R.id.iv_diadema);
						TextView tv_name_def = (TextView) child_person
								.findViewById(R.id.tv_name);
						tv_name_def.setText("点击申请");
						// circle_per.setImageResource(R.drawable.apply);
						x.image().bind(circle_per_def, "", mOptionApply);
						ll_contains_person.addView(ll_child_per_def);
					}
					tv_name.setText(model_investor.getPrjCustBuyList().get(i)
							.getCustLoginNam());
					x.image().bind(
							circle_per,
							model_investor.getPrjCustBuyList().get(i)
									.getCustPortraitAddr(), mOptionNoPic);
					ll_contains_person.addView(ll_child_per);
				} else {// position！=0
					tv_name.setText(model_investor.getPrjCustBuyList().get(i)
							.getCustLoginNam());
					x.image().bind(
							circle_per,
							model_investor.getPrjCustBuyList().get(i)
									.getCustPortraitAddr(), mOptionNoPic);
					ll_contains_person.addView(ll_child_per);
				}

			}
		} else {// 没有投资人员 申请领头默认图片按钮
			View child_person = getLayoutInflater().inflate(
					R.layout.child_item_person, null);
			LinearLayout ll_child_per = (LinearLayout) child_person
					.findViewById(R.id.ll_child_person_container);
			ImageView circle_per = (ImageView) child_person
					.findViewById(R.id.circleIv);
			ImageView iv_diadema = (ImageView) child_person
					.findViewById(R.id.iv_diadema);
			TextView tv_name = (TextView) child_person
					.findViewById(R.id.tv_name);
			tv_name.setText("点击申请");
			// circle_per.setImageResource(R.drawable.apply);
			x.image().bind(circle_per, "", mOptionApply);
			ll_contains_person.addView(ll_child_per);
		}

		ll_contains_person.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_StockDetail.this,
						Activity_SupportPerson.class);
				intent.putExtra("flag", 1);
				intent.putExtra("prjId", model_prj.getId());
				Bundle bundle = new Bundle();
				bundle.putSerializable("model", model_investor);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	/**
	 * 4、填充项目进展数据
	 */
	public void initProgressData(final GetPrjProgressListResponse model_progress) {
		for (int i = 0; i < model_progress.getPrjProgressList().size(); i++) {
			View child_person = getLayoutInflater().inflate(
					R.layout.child_item_progress, null);
			LinearLayout ll_child_progress_container = (LinearLayout) child_person
					.findViewById(R.id.ll_child_progress_container);
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
					StringUtils.getDate(model_progress.getPrjProgressList().get(i)
							.getCreateTime(), 2),
					com.minjinsuo.zhongchou.utils.DateUtils.getCurrentTime());
			tv_time.setText(dayResult);
			ll_contains.addView(ll_child_progress_container);
			ll_child_progress_container
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(
									Activity_StockDetail.this,
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
	 * 5、动态添加view——填充项目公告数据
	 */
	public void initNoticeData(final GetPrjNoticeListResponse model_notice) {
		if (!isBuyed) {// 当前登录用户是已投资（0-否,1-是 默认0）
			right_notice.setText(ONLY_INVESTOR);
			StringUtils.setTextViewDrawableRight(getContext(), right_notice, 0);
		} else {
			for (int i = 0; i < model_notice.getPrjNoticeList().size(); i++) {
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

				tv_content.setText(model_notice.getPrjNoticeList().get(i)
						.getTitle()
						+ "\n"
						+ model_notice.getPrjNoticeList().get(i)
								.getFileIdMemo());
				String dayResult = CommonUtils.daysBetween(StringUtils.getDate(
						model_notice.getPrjNoticeList().get(i).getCreateTime(),
						2), com.minjinsuo.zhongchou.utils.DateUtils
						.getCurrentTime());
				tv_time.setText(dayResult);
				ll_contains_notice.addView(ll_child_notice_container);

				ll_child_notice_container
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										Activity_StockDetail.this,
										Activity_ProjectNotice.class);
								Bundle bundle = new Bundle();
								bundle.putSerializable("model", model_notice);
								intent.putExtras(bundle);
								startActivity(intent);
							}
						});
			}
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
						intent = new Intent(Activity_StockDetail.this,
								Activity_TopicReply.class);
						intent.putExtra("id", prjId);
						intent.putExtra("ctId", model_topic.getCommonTalkList()
								.get(position).getId());
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_topic
								.getCommonTalkList().get(position));// 主贴对象
						intent.putExtras(bundle);

					} else {
						intent = new Intent(Activity_StockDetail.this,
								Activity_TopicMain.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_topic);
						intent.putExtras(bundle);
						intent.putExtra("id", prjId);
					}
				} else if (model_topic.getCommonTalkList().size() == 1) {
					if (position == 0) {
						intent = new Intent(Activity_StockDetail.this,
								Activity_TopicReply.class);
						intent.putExtra("id", prjId);
						intent.putExtra("ctId", model_topic.getCommonTalkList()
								.get(0).getId());
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_topic
								.getCommonTalkList().get(0));// 主贴对象
						intent.putExtras(bundle);
					} else {
						intent = new Intent(Activity_StockDetail.this,
								Activity_TopicMain.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("model", model_topic);
						intent.putExtras(bundle);
						intent.putExtra("id", prjId);
					}
				} else {
					intent = new Intent(Activity_StockDetail.this,
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
