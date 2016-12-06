package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListResponse.ElementPrjList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_Search;
import com.minjinsuo.zhongchou.activity.Activity_StockDetail;
import com.minjinsuo.zhongchou.adapter.Adapter_StockList;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 众筹tab内容
 */
public class Fragment_Stock extends Fragment_Base implements
		OnRefreshListener2<ListView>, OnItemClickListener {

	private View mView;
	@ViewInject(R.id.lv_product)
	PullToRefreshListView lv_product;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;

	private Adapter_StockList adapter_list;
	private List<ElementPrjList> list = new ArrayList<GetPrjInfoPageListResponse.ElementPrjList>();;

	private GetPrjInfoPageListResponse model_prj;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_crowd, null);
			x.view().inject(this, mView);
			initView();
			initData();

			getPrjInfoPageList(true);
		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		return mView;
	}

	@Override
	public void initView() {
		setTitleText(mView, getString(R.string.crowd_title));
		setTitleRightDrawable(mView, R.drawable.search, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), Activity_Search.class);
				intent.putExtra(AppContants.FROM, "stock");
				startActivity(intent);
			}
		});

		adapter_list = new Adapter_StockList(getActivity());
		lv_product.setMode(Mode.BOTH);
		lv_product.setOnRefreshListener(this);

		lv_product.setAdapter(adapter_list);
		lv_product.setOnItemClickListener(this);
	}

	@Override
	public void initData() {

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		isPullDown = true;
		pageNo = "1";
		getPrjInfoPageList(true);

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		isPullDown = false;
		pageNo = (Integer.parseInt(pageNo) + 1) + "";
		getPrjInfoPageList(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(), Activity_StockDetail.class);
		intent.putExtra("id", list.get(position - 1).getId());
		intent.putExtra("img", list.get(position - 1).getHomePicAddress());
		startActivity(intent);
	}

	@Override
	protected void initListener() {

	}

	/**
	 * 获取股权列表数据
	 */
	public void getPrjInfoPageList(boolean isShow) {
		GetPrjInfoPageListRequest request = new GetPrjInfoPageListRequest();

		request.setProdId("0");// 股权
		request.setStatus("");// 状态,全部状态
		request.setSort("desc");
		request.setKeywords("");
		request.setPageNo(Integer.parseInt(pageNo));
		request.setPageSize(Integer.parseInt(pageSize));

		NetWorkRequestManager.sendRequestPost(getActivity(), isShow, request,
				GetPrjInfoPageListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getPrjInfoPageList":// 获取列表数据
			model_prj = (GetPrjInfoPageListResponse) response;

			if (isPullDown) {
				adapter_list.deleteData();
				list.clear();
			}
			if (model_prj != null && model_prj.getPrjList() != null
					&& model_prj.getPrjList().size() > 0) {
				list.addAll(model_prj.getPrjList());
				adapter_list.setData(model_prj.getPrjList());
				adapter_list.notifyDataSetChanged();
				noLog.setVisibility(View.GONE);
			} else if (!isPullDown) {
				ToastUtil.showToast(getActivity(), "已加载全部");
			} else {
				noLog.setVisibility(View.VISIBLE);
			}

			lv_product.onRefreshComplete();
			DialogUtils.dismisLoading();
			break;
		}
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		lv_product.onRefreshComplete();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		lv_product.onRefreshComplete();
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}
}
