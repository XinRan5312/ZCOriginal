package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjTBPageListRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjTBPageListResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjTBPageListResponse.ElementPrjTBList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_ProductDetail;
import com.minjinsuo.zhongchou.activity.Activity_StockDetail;
import com.minjinsuo.zhongchou.adapter.Adapter_MyFaQi;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 我的发起——产品列表/股权列表
 */
public class Fragment_child_Faqi extends Fragment_Base implements
		OnRefreshListener2<ListView>, OnItemClickListener {

	private View mView;
	@ViewInject(R.id.lv_list)
	private PullToRefreshListView lv_list_Pro;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;

	private Adapter_MyFaQi adapter_list;
	private List<ElementPrjTBList> list = new ArrayList<ElementPrjTBList>();
	/** 标志位，标志已经初始化完成 */
	private boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	private boolean mHasLoadedOnce;

	// private GetPrjPageListForUserResponse model;
	private GetPrjTBPageListResponse model;
	private static String isProduct = "isProduct";
	private boolean isFromProduct;

	public static Fragment_child_Faqi getInstance(boolean flag) {
		Fragment_child_Faqi f = new Fragment_child_Faqi();
		Bundle args = new Bundle();
		args.putBoolean(isProduct, flag);
		f.setArguments(args);
		return f;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_child_support, null);
			x.view().inject(this, mView);

			Bundle args = getArguments();
			isFromProduct = args.getBoolean(isProduct);
			LogUtil.i("是否是产品==" + isFromProduct);

			initView();
			initData();
			isPrepared = true;
			lazyLoad();

		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		return mView;
	}

	@Override
	public void initView() {

		lv_list_Pro.setMode(Mode.BOTH);
		lv_list_Pro.setOnRefreshListener(this);

		adapter_list = new Adapter_MyFaQi(getActivity(), isFromProduct);
		lv_list_Pro.setAdapter(adapter_list);
		lv_list_Pro.setOnItemClickListener(this);
	}

	@Override
	public void initData() {

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(getActivity())) {
			lv_list_Pro.onRefreshComplete();
			return;
		}
		pageNo = "1";
		isPullDown = true;
		getList();

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(getActivity())) {
			lv_list_Pro.onRefreshComplete();
			return;
		}
		pageNo = (Integer.parseInt(pageNo) + 1) + "";
		isPullDown = false;
		getList();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		if (isFromProduct) {
			Intent intent = new Intent(getActivity(),
					Activity_ProductDetail.class);
			intent.putExtra("prjId", list.get(position - 1).getId());
			intent.putExtra("img", list.get(position - 1).getHomePicAddress());
			startActivity(intent);
		} else {
			Intent intent = new Intent(getActivity(),
					Activity_StockDetail.class);
			intent.putExtra("id", list.get(position - 1).getId());
			intent.putExtra("img", list.get(position - 1).getHomePicAddress());
			startActivity(intent);
		}
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	private void getList() {
		GetPrjTBPageListRequest request = new GetPrjTBPageListRequest();
		request.setPageNo(Integer.parseInt(pageNo));
		request.setPageSize(Integer.parseInt(pageSize));
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		if (isFromProduct) {
			request.setProdId(1);// 1:代表产品，0:代表股权
		} else {
			request.setProdId(0);
		}
		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				GetPrjTBPageListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		mHasLoadedOnce = true;
		model = (GetPrjTBPageListResponse) response;
		if (isPullDown) {
			adapter_list.deleteData();
			list.clear();
		}
		if (model != null && model.getPrjTBList() != null
				&& model.getPrjTBList().size() > 0) {
			adapter_list.setData(model.getPrjTBList());
			list.addAll(model.getPrjTBList());
			adapter_list.notifyDataSetChanged();
			noLog.setVisibility(View.GONE);
		} else if (isPullDown) {
			ToastUtil.showToast(getActivity(), "暂无数据");
			noLog.setVisibility(View.VISIBLE);
		} else {
			ToastUtil.showToast(getActivity(), "已加载全部数据");
		}
		DialogUtils.dismisLoading();
		lv_list_Pro.onRefreshComplete();
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		lv_list_Pro.onRefreshComplete();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		lv_list_Pro.onRefreshComplete();
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		}
		getList();
	}
}
