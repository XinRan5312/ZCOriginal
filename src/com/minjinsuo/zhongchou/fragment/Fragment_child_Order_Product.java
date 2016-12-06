package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetMyOrderPageListRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyOrderPageListResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyOrderPageListResponse.ElementMyOrderList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_OrderDetail;
import com.minjinsuo.zhongchou.adapter.Adapter_MyOrder;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 我的订单——产品列表
 */
public class Fragment_child_Order_Product extends Fragment_Base implements
		OnRefreshListener2<ListView>, OnItemClickListener {
	private final static String TAG = "Fragment_child_product";

	private View mView;
	@ViewInject(R.id.lv_list)
	private PullToRefreshListView lv_list_Pro;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;

	private Adapter_MyOrder adapter_list;
	private List<ElementMyOrderList> list = new ArrayList<ElementMyOrderList>();
	/** 标志位，标志已经初始化完成 */
	private boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	private boolean mHasLoadedOnce;

	private GetMyOrderPageListResponse model;

	public static Fragment_child_Order_Product getInstance() {
		Fragment_child_Order_Product f = new Fragment_child_Order_Product();
		Bundle args = new Bundle();
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
	public void onResume() {
		super.onResume();

		if (ZCApplication.getInstance().isNeedRefresh) {
			ZCApplication.getInstance().isNeedRefresh = false;
			pageNo = "1";
			isPullDown = true;
			getMyOrderPageList();
		}
	}

	@Override
	public void initView() {

		lv_list_Pro.setMode(Mode.BOTH);
		lv_list_Pro.setOnRefreshListener(this);

		adapter_list = new Adapter_MyOrder(getActivity());
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
		getMyOrderPageList();

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(getActivity())) {
			lv_list_Pro.onRefreshComplete();
			return;
		}
		pageNo = (Integer.parseInt(pageNo) + 1) + "";
		isPullDown = false;
		getMyOrderPageList();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), Activity_OrderDetail.class);
		intent.putExtra("id", list.get(position - 1).getId());
		intent.putExtra("prjId", list.get(position - 1).getPrjId());
		intent.putExtra("flag", "product");
		startActivity(intent);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	private void getMyOrderPageList() {
		GetMyOrderPageListRequest request = new GetMyOrderPageListRequest();
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setProdId("1");// 1:代表产品，0:代表股权
		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				GetMyOrderPageListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		mHasLoadedOnce = true;
		model = (GetMyOrderPageListResponse) response;
		if (isPullDown) {
			adapter_list.deleteData();
			list.clear();
		}
		if (model != null && model.getMyOrderList() != null
				&& model.getMyOrderList().size() > 0) {
			adapter_list.setData(model.getMyOrderList());
			list.addAll(model.getMyOrderList());
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
		getMyOrderPageList();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Adapter_MyOrder.CODE_PAY) {
			String msg_result = data.getStringExtra("message");
			if (resultCode == getActivity().RESULT_OK) {
				ToastUtil.showToast(getActivity(), "支付成功");
			}
			
			isPullDown = true;
			pageNo = "1";
			getMyOrderPageList();
		}
	}
}
