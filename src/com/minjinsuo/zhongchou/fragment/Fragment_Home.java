package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.zkbc.p2p.fep.message.protocol.CheckUpdateRequest;
import net.zkbc.p2p.fep.message.protocol.CheckUpdateResponse;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeRequest;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeResponse;
import net.zkbc.p2p.fep.message.protocol.GetHomePageBannerListRequest;
import net.zkbc.p2p.fep.message.protocol.GetHomePageBannerListResponse;
import net.zkbc.p2p.fep.message.protocol.GetHomePageBannerListResponse.ElementHomePageBannerList;
import net.zkbc.p2p.fep.message.protocol.GetHotChoosePrjListRequest;
import net.zkbc.p2p.fep.message.protocol.GetHotChoosePrjListResponse;
import net.zkbc.p2p.fep.message.protocol.GetHotChoosePrjListResponse.ElementHotPrjList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Act_CommonWeb;
import com.minjinsuo.zhongchou.activity.Activity_ProductDetail;
import com.minjinsuo.zhongchou.activity.Activity_ProductSelect;
import com.minjinsuo.zhongchou.activity.Activity_Search;
import com.minjinsuo.zhongchou.activity.Activity_StockDetail;
import com.minjinsuo.zhongchou.adapter.Adapter_FragmentHome_Viewpager;
import com.minjinsuo.zhongchou.adapter.Adapter_ProductList;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.service.UpdateService;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.ListViewForScorllView;

/**
 * 首页tab内容 Created by zp on 2016/6/10.
 */
public class Fragment_Home extends Fragment_Base implements
		ViewPager.OnPageChangeListener,
		// PullToRefreshBase.OnRefreshListener,
		OnRefreshListener2<ScrollView>, OnItemClickListener, MyRequestCallBack {
	private final static String TAG = "Fragment_Home";
	private View mView;

	@ViewInject(R.id.viewpager)
	ViewPager viewpager;
	@ViewInject(R.id.ll_vp)
	LinearLayout ll_vp; // 点的布局
	@ViewInject(R.id.lv_list)
	private ListViewForScorllView lv_list;
	@ViewInject(R.id.ptr_refresh)
	private PullToRefreshScrollView ptr_refresh;
	@ViewInject(R.id.home_more)
	private TextView home_more;// 更多快捷按钮
	// 一下两个选项名称要根据接口返回的列表中取前俩个进行显示
	@ViewInject(R.id.tv_quick_second)
	private TextView tv_quick_second;
	@ViewInject(R.id.tv_quick_third)
	private TextView tv_quick_third;
	@ViewInject(R.id.iv_second)
	private ImageView iv_second;
	@ViewInject(R.id.iv_third)
	private ImageView iv_third;
	@ViewInject(R.id.tv_nodata)
	private TextView tv_nodata;
	@ViewInject(R.id.ll_second)
	private LinearLayout ll_second;
	@ViewInject(R.id.ll_third)
	private LinearLayout ll_third;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;

	private List<ImageView> imageViews = new ArrayList<ImageView>(); // 轮播图片集合
	// private List<HashMap<String, String>> photoList;
	private AtomicInteger atomicInteger = new AtomicInteger();
	private int count = 0;
	private ImageOptions imageOptions;
	// 首页列表数据
	// private List<Model_Tender> list;
	private Adapter_ProductList adapter;

	/**
	 * 是否已被加载过一次，第二次就不再去请求数据了
	 */
	private boolean mHasLoadedOnce;
	private GetHomePageBannerListResponse info;
	private GetHotChoosePrjListResponse homePrjInfoList;
	private List<ElementHotPrjList> list_hotList = new ArrayList<ElementHotPrjList>();
	private static int pageSize = 10;
	private int pageNo = 1;
	private boolean isPullDown = true;

	private GetBookletByTypeResponse model;// 行业类型列表，需要传递到筛选条件中
	private String type_select_01, type_select_02;// 四个筛选条件中间两个
	// 屏幕宽度和高度
	private int width;
	private int height;
	private String versionStr;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_home, null);
			x.view().inject(this, mView);
			initView();
			initData();
			getBanner(true);
		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		return mView;
	}

	@Override
	public void initView() {
		setTitleText(mView, getString(R.string.home_title));
		setTitleRightDrawable(mView, R.drawable.search, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Activity_Search.class);
				intent.putExtra(AppContants.FROM, "product");
				startActivity(intent);
			}
		});

		imageOptions = new ImageOptions.Builder()
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 加载中或错误图片的ScaleType
				.setPlaceholderScaleType(ImageView.ScaleType.CENTER)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.banner_default)
				.setFailureDrawableId(R.drawable.banner_default).build();

		ptr_refresh.setMode(PullToRefreshBase.Mode.BOTH);
		ptr_refresh.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
		ptr_refresh.getLoadingLayoutProxy(true, false).setReleaseLabel(
				"释放刷新...");
		ptr_refresh.getLoadingLayoutProxy(true, false).setRefreshingLabel(
				"正在刷新...");
		ptr_refresh.setOnRefreshListener(this);

		adapter = new Adapter_ProductList(getActivity());
		lv_list.setAdapter(adapter);
		lv_list.setOnItemClickListener(this);
	}

	@Override
	public void initData() {
		setViewPagerData();// 加载图片数据
		changePagerThread();
	}

	/**
	 * 设置viewpager数据
	 */
	private void setViewPagerData() {
		imageViews.clear();
		ll_vp.removeAllViews();

		if (info == null || info.getHomePageBannerList().size() == 0) {
			addPaterView("", R.drawable.banner_default, 0, null);// 默认显示
		} else {
			for (int i = 0; i < info.getHomePageBannerList().size(); i++) {
				addPaterView(info.getHomePageBannerList().get(i).getPicture(),
						R.drawable.banner_default, i, info
								.getHomePageBannerList().get(i));
			}
		}

		atomicInteger.set(0);
		viewpager.setAdapter(new Adapter_FragmentHome_Viewpager(imageViews));
		viewpager.setOnPageChangeListener(this);
	}

	/**
	 * 每次添加一张图片
	 *
	 * @param uri
	 *            网络图片的路径
	 * @param id
	 *            url为空时显示的默认图片。图片的id(R.drawable.×××)
	 * @param i
	 *            banner索引，用于控制小点显示
	 */
	private void addPaterView(String uri, int id, int i,
			final ElementHomePageBannerList info) {
		ImageView iv = new ImageView(getActivity());
		iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

		if (!StringUtils.isEmpty(uri)) {
			x.image().bind(iv, uri, imageOptions);
		} else if (id != 0) {
			// iv.setImageResource(id);
			x.image().bind(iv, "", imageOptions);
		}

		// 为banner图添加点击跳转事件——可能是h5超链接，也可能是通过id跳转到详情页
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!StringUtils.isEmpty(info.getProdId())
						&& !StringUtils.isEmpty(info.getPrjId())) {// 代表跳转到项目详情页
					LogUtil.i("点击要跳转的标id==" + info.getPrjId());
					if (info.getProdId().equals("0")) {// 股权
						Intent intent = new Intent(getActivity(),
								Activity_StockDetail.class);
						intent.putExtra("id", info.getPrjId());
						startActivity(intent);
					} else {// 产品
						Intent intent = new Intent(getActivity(),
								Activity_ProductDetail.class);
						intent.putExtra("prjId", info.getPrjId());
						startActivity(intent);
					}

				} else if (!StringUtils.isEmpty(info.getClickLink())
						&& info.getClickLink().startsWith("http")) {// H5展示
					Intent intent = new Intent(getActivity(),
							Act_CommonWeb.class);
					intent.putExtra("url", info.getClickLink());
					intent.putExtra("title", info.getTitile() == null ? "推荐"
							: info.getTitile());
					startActivity(intent);
				}

			}
		});

		// 将每个imageView放到list中传回adapter
		imageViews.add(iv);

		// 添加点了
		ImageView point = new ImageView(getActivity());
		point.setPadding(20, 0, 0, 0);
		// 创建了布局的参数 全部都是包裹内容
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
		// 设置每个点之间的间距
		params.rightMargin = 20;
		// 给ImageView 设置参数
		point.setLayoutParams(params);
		point.setImageResource(R.drawable.selectot_points);
		if (i == count) {
			point.setEnabled(true);
		} else {
			point.setEnabled(false);
		}
		ll_vp.addView(point);
	}

	/*
	 * 每隔固定时间切换banner
	 */
	private void changePagerThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					atomicInteger.incrementAndGet();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					viewHandler.sendEmptyMessage(atomicInteger.get());
				}
			}
		}).start();

	}

	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int index = msg.what;
			if (viewpager != null && viewpager.getChildCount() > 0)
				viewpager.setCurrentItem(index);
		}
	};

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		atomicInteger.set(position);
		int index = position % imageViews.size(); // 0-4
		// 获得了之前的点
		ImageView last_point = (ImageView) ll_vp.getChildAt(count);
		last_point.setEnabled(false);
		// 现在的点 高亮显示
		ImageView point = (ImageView) ll_vp.getChildAt(index);
		point.setEnabled(true);
		count = index;
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), Activity_ProductDetail.class);
		intent.putExtra("prjId", list_hotList.get(position).getId());
		intent.putExtra("img", list_hotList.get(position).getImgAddr());
		startActivity(intent);
	}

	@Override
	protected void initListener() {

	}

	@Event({ R.id.home_more, R.id.tv_quick_third, R.id.tv_quick_second,
			R.id.tv_gongyi, R.id.ll_third, R.id.ll_second })
	private void OnClick(View view) {
		if (model == null || model.getBookletList() == null
				|| model.getBookletList().size() <= 0) {
			LogUtil.i("没有获取到行业类别数据行业类别");
			return;
		}
		Intent intent = new Intent(getActivity(), Activity_ProductSelect.class);
		Bundle bundle = null;
		switch (view.getId()) {
		case R.id.ll_second:
		case R.id.tv_quick_second:
			intent.putExtra("type_code", type_select_01);
			intent.putExtra("title", tv_quick_second.getText().toString());
			bundle = new Bundle();
			bundle.putSerializable("list_industry", model);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.ll_third:
		case R.id.tv_quick_third:
			intent.putExtra("type_code", type_select_02);
			intent.putExtra("title", tv_quick_third.getText().toString());
			bundle = new Bundle();
			bundle.putSerializable("list_industry", model);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.tv_gongyi:
			intent.putExtra("title", ((TextView) view).getText().toString());
			bundle = new Bundle();
			bundle.putSerializable("list_industry", model);
			intent.putExtras(bundle);
			startActivity(intent);
			break;
		case R.id.home_more:
			intent.putExtra("title", ((TextView) view).getText().toString());
			bundle = new Bundle();
			bundle.putSerializable("list_industry", model);
			intent.putExtras(bundle);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	/**
	 * 获取轮播图
	 */
	public void getBanner(boolean isShow) {
		GetHomePageBannerListRequest request = new GetHomePageBannerListRequest();
		NetWorkRequestManager.sendRequestPost(getActivity(), isShow, request,
				GetHomePageBannerListResponse.class, this);
	}

	/**
	 * 获取首页列表数据
	 */
	public void getHomeListData(boolean isShow) {
		GetHotChoosePrjListRequest request = new GetHotChoosePrjListRequest();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		NetWorkRequestManager.sendRequestPost(getActivity(), isShow, request,
				GetHotChoosePrjListResponse.class, this);
	}

	/**
	 * 获取首页推荐行业类型
	 */
	public void getIndustryType(boolean isShow) {
		GetBookletByTypeRequest request = new GetBookletByTypeRequest();
		request.setType("prjTrade");
		NetWorkRequestManager.sendRequestPost(getActivity(), isShow, request,
				GetBookletByTypeResponse.class, this);
	}

	/**
	 * 版本更新
	 */
	private void doUpdate(boolean isShow) {
		getDeviceHeightAndWidth();
		versionStr = getVersion();
		CheckUpdateRequest request = new CheckUpdateRequest();
		request.setDeviceheight(height + "");
		request.setDevicewidth(width + "");
		request.setDevicetype("android");
		request.setVersion(versionStr);
		NetWorkRequestManager.sendRequestPost(getActivity(), isShow, request,
				CheckUpdateResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		ptr_refresh.onRefreshComplete();
		switch (response.getMessageId()) {
		case "getHomePageBannerList":
			info = (GetHomePageBannerListResponse) response;
			if (info != null && info.getHomePageBannerList() != null) {
				setViewPagerData();
			}
			// 获取首页列表数据
			getHomeListData(false);
			break;
		case "getHotChoosePrjList":// 获取首页列表数据成功返回
			homePrjInfoList = (GetHotChoosePrjListResponse) response;
			if (isPullDown) {
				adapter.deleteData();
				list_hotList.clear();
			}
			if (homePrjInfoList.getHotPrjList() != null
					&& homePrjInfoList.getHotPrjList().size() > 0) {
				lv_list.setVisibility(View.VISIBLE);
				noLog.setVisibility(View.GONE);
				list_hotList.addAll(homePrjInfoList.getHotPrjList());
				adapter.setData(homePrjInfoList.getHotPrjList());
				adapter.notifyDataSetChanged();
			} else if (isPullDown) {
				// ToastUtil.showToast(getActivity(), "暂无热门推荐标");
				lv_list.setVisibility(View.GONE);
				noLog.setVisibility(View.VISIBLE);
				tv_nodata.setText("暂无热门推荐产品");
			} else {
				ToastUtil.showToast(getActivity(), "已加载全部");
			}
			getIndustryType(false);
			break;
		case "getBookletByType":// 获取行业类别
			model = (GetBookletByTypeResponse) response;
			if (model != null && model.getBookletList() != null
					&& model.getBookletList().size() > 0) {
				// 显示前两个行业类别到首页推荐
				String second_show = model.getBookletList().get(0).getDisplay();
				type_select_01 = model.getBookletList().get(0).getCode();
				String third_show = model.getBookletList().get(1).getDisplay();
				type_select_02 = model.getBookletList().get(1).getCode();
				tv_quick_second.setText(second_show);
				tv_quick_third.setText(third_show);

				ImageOptions mOptionQuick = new ImageOptions.Builder()
						// 如果ImageView的大小不是定义为wrap_content, 不要crop.
						// .setCrop(true)
						.setPlaceholderScaleType(ImageView.ScaleType.CENTER)
						.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
						.setLoadingDrawableId(R.drawable.home2)
						.setFailureDrawableId(R.drawable.home2).build();
				final String pic_second = model.getBookletList().get(0)
						.getIconAddr();
				final String pic_third = model.getBookletList().get(1)
						.getIconAddr();

				x.image().bind(iv_second, pic_second, mOptionQuick);

				ImageOptions mOptionQuick3 = new ImageOptions.Builder()
						// 如果ImageView的大小不是定义为wrap_content, 不要crop.
						// .setCrop(true)
						.setPlaceholderScaleType(ImageView.ScaleType.CENTER)
						.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
						.setLoadingDrawableId(R.drawable.home3)
						.setFailureDrawableId(R.drawable.home3).build();
				x.image().bind(iv_third, pic_third, mOptionQuick3);
			}

			if (!mHasLoadedOnce) {
				doUpdate(false);
			} else {
				DialogUtils.dismisLoading();
				ptr_refresh.onRefreshComplete();
			}
			break;

		case "checkUpdate":
			DialogUtils.dismisLoading();
			ptr_refresh.onRefreshComplete();
			mHasLoadedOnce = true;
			final CheckUpdateResponse model = (CheckUpdateResponse) response;
			if (!StringUtils.isEmpty(model.getPkgurl())) {

				new com.minjinsuo.zhongchou.utils.AlertDialog(getActivity())
						.builder().setMsg("亲,有新版本了,更省流量更强大\r\n是否下载最新版本?")
						.setPositiveButton("立即更新", new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(getActivity(),
										UpdateService.class);
								intent.putExtra("Key_App_Name", getActivity()
										.getString(R.string.app_name));
								intent.putExtra("Key_Down_Url",
										model.getPkgurl());
								ToastUtil.showToast(getActivity(), "后台下载更新中…");
								getActivity().startService(intent);
							}
						}).setNegativeButton("稍后再说", new OnClickListener() {

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

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		ptr_refresh.onRefreshComplete();
		if (null != response) {
			ToastUtil.showToast(getContext(), response.getStatusMessage());
		}
		switch (response.getMessageId()) {
		case "getHomePageBannerList":// 就算banner请求失败也应该继续请求首页列表
			getHomeListData(true);
			break;
		case "getHotChoosePrjList":
			getIndustryType(true);
			break;
		}
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		ptr_refresh.onRefreshComplete();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		pageNo = 1;
		isPullDown = true;
		// 获取首页轮播图
		getBanner(true);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		pageNo++;
		isPullDown = false;
		getHomeListData(true);
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

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
