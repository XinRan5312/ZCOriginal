package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetScoreGoodsListRequest;
import net.zkbc.p2p.fep.message.protocol.GetScoreGoodsListResponse;
import net.zkbc.p2p.fep.message.protocol.GetScoreGoodsListResponse.ElementScoreGoodsList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.os.Bundle;
import android.widget.GridView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.MyShopMallAdapter;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;

/**
 * 积分商城
 * 
 * @author Tracy
 * 
 */
public class Activity_integrateShopMall extends Activity_Base implements
		OnRefreshListener<GridView> {
	private PullToRefreshGridView ptr_refresh_invester;
	private MyShopMallAdapter mAdapter;
	private List<ElementScoreGoodsList> datas = new ArrayList<ElementScoreGoodsList>();
	private int useIntegrateCount = 0;// 我的可用积分数
	private int pageNo = 1;
	private int pageSize = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integrate_shopmall);
		setTitleBack();
		setTitleText("积分商城");
		initView();
		initListener();
	}

	@Override
	protected void initView() {
		ptr_refresh_invester = (PullToRefreshGridView) findViewById(R.id.ptr_refresh_invester);

		useIntegrateCount = getIntent().getIntExtra("useIntegrateCount", 0);
		mAdapter = new MyShopMallAdapter(this, datas, useIntegrateCount);

		GridView mGridView = ptr_refresh_invester.getRefreshableView();
		mGridView.setNumColumns(2);
		mGridView.setAdapter(mAdapter);

		ptr_refresh_invester.setMode(Mode.DISABLED);
		ptr_refresh_invester.setOnRefreshListener(this);
	}

	@Override
	protected void initListener() {
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 请求网络数据
		startRequestDataFromNet(true);
	}

	/**
	 * 请求数据
	 * 
	 * @param clear
	 *            true清空，false 不清空
	 */
	private void startRequestDataFromNet(final boolean clear) {
		GetScoreGoodsListRequest request = new GetScoreGoodsListRequest();
		request.setPageno(pageNo);
		request.setPagesize(pageSize);
		request.setSessionId(ZCApplication.getInstance().userInfo
				.getSessionId());

		NetWorkRequestManager.sendRequestPost(getContext(), true, request,
				GetScoreGoodsListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getScoreGoodsList":
			DialogUtils.dismisLoading();
			GetScoreGoodsListResponse scoreGoodsListResponse = (GetScoreGoodsListResponse) response;
			if (null != scoreGoodsListResponse
					&& null != scoreGoodsListResponse.getScoreGoodsList()
					&& scoreGoodsListResponse.getScoreGoodsList().size() != 0) {
				datas.addAll(scoreGoodsListResponse.getScoreGoodsList());
				mAdapter.notifyDataSetChanged();
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

	@Override
	public void onRefresh(PullToRefreshBase<GridView> refreshView) {
	}

	@Override
	protected void initData() {
	}

}
