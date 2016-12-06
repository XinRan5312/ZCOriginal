package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_ProductDetail;
import com.minjinsuo.zhongchou.adapter.Adapter_CrowdProduct;
import com.minjinsuo.zhongchou.model.Model_Tender;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

/**
 * 我的关注--产品众筹
 */
public class Fragment_child_product extends Fragment_Base implements OnRefreshListener2<ListView>, OnItemClickListener {
	private final static String TAG = "Fragment_child_product";

	private View mView;
	@ViewInject(R.id.lv_list)
	PullToRefreshListView lv_list;

	private Adapter_CrowdProduct adapter_list;
	private List<Model_Tender> list;

	public static Fragment_child_product getInstance() {
		Fragment_child_product f = new Fragment_child_product();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.fragment_child_common, null);
			x.view().inject(this, mView);

			initView();
			initData();
		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		return mView;
	}

	@Override
	public void initView() {

		adapter_list = new Adapter_CrowdProduct(getActivity());
		lv_list.setMode(Mode.BOTH);
		lv_list.setOnRefreshListener(this);
		lv_list.setAdapter(adapter_list);
		lv_list.setOnItemClickListener(this);

		// 添加假数据
		list = new ArrayList<Model_Tender>();
		Model_Tender model;
		for (int i = 0; i < 10; i++) {
			model = new Model_Tender();
			model.setAmount((200 + i) + "元");
			model.setTitle("测试首页数据" + i);
			list.add(model);
		}
		adapter_list.setData((ArrayList<Model_Tender>) list);
		adapter_list.notifyDataSetChanged();
	}

	@Override
	public void initData() {

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		startActivity(new Intent(getActivity(), Activity_ProductDetail.class));
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
