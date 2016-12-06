package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetRedMoneyListRequest;
import net.zkbc.p2p.fep.message.protocol.GetRedMoneyListResponse;
import net.zkbc.p2p.fep.message.protocol.GetRedMoneyListResponse.ElementRedMoneyList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_MyRedBag;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 我的红包——红包列表（未用列表/历史列表）
 */
public class Fragment_child_redBag extends Fragment_Base implements
		OnRefreshListener2<ListView>, OnItemClickListener {

	private View mView;
	@ViewInject(R.id.lv_product)
	private PullToRefreshListView lv_list_Pro;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;
	@ViewInject(R.id.redbag_ruler)
	private TextView redbag_ruler;

	private Adapter_MyRedBag adapter_list;
	private List<ElementRedMoneyList> list = new ArrayList<ElementRedMoneyList>();
	/** 标志位，标志已经初始化完成 */
	private boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	private boolean mHasLoadedOnce;

	private GetRedMoneyListResponse model;
	private boolean history;// 是否是历史记录

	public static Fragment_child_redBag getInstance(boolean history) {
		Fragment_child_redBag fragment = new Fragment_child_redBag();
		Bundle args = new Bundle();
		args.putBoolean("classify", history);
		fragment.setArguments(args);
		return fragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_redbag_child, null);
			x.view().inject(this, mView);

			Bundle args = getArguments();
			history = args.getBoolean("classify");
			LogUtil.i("is history--" + history);

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

		adapter_list = new Adapter_MyRedBag(getActivity(), history);
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
		getRedMoneyList(history);

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(getActivity())) {
			lv_list_Pro.onRefreshComplete();
			return;
		}
		pageNo = (Integer.parseInt(pageNo) + 1) + "";
		isPullDown = false;
		getRedMoneyList(history);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		LogUtil.i("点击了" + position);// -1
	}

	@Override
	protected void initListener() {
	}

	/**
	 * 获取红包列表
	 */
	private void getRedMoneyList(boolean isHistory) {
		GetRedMoneyListRequest request = new GetRedMoneyListRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		if (isHistory) {
			request.setRedtype("50");
		} else {
			request.setRedtype("20");
		}
		request.setPageno(Integer.parseInt(pageNo));
		request.setPagesize(Integer.parseInt(pageSize));
		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				GetRedMoneyListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		mHasLoadedOnce = true;
		model = (GetRedMoneyListResponse) response;
		if (isPullDown) {
			adapter_list.deleteData();
			list.clear();
		}
		if (model != null && model.getRedMoneyList() != null
				&& model.getRedMoneyList().size() > 0) {
			list.addAll(model.getRedMoneyList());
			adapter_list.setData(model.getRedMoneyList());
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
		getRedMoneyList(history);
	}
}
