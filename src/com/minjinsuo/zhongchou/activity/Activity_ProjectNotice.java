package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.GetPrjNoticeListResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjProgressListResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_ProjectNotice;

/**
 * 项目公告 不同于项目动态进展
 * 
 * @author zp
 *
 *         2016年6月22日
 */
public class Activity_ProjectNotice extends Activity_Base implements
		OnItemClickListener, OnRefreshListener2<ListView> {

	@ViewInject(R.id.lv_list)
	PullToRefreshListView lv_list;
	@ViewInject(R.id.ll_btn_subProgress)
	private LinearLayout ll_btn_subProgress;// 公告 不可编辑

	private Adapter_ProjectNotice adapter_list;
	// private List<Model_Tender> list;

	private GetPrjNoticeListResponse model_notice;// 从股权详情页传递而来

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_dynamic);
		x.view().inject(this);
		initView();
		initData();

	}

	@Override
	protected void initView() {
		setTitleText("项目公告");
		setTitleBack();

		if (getIntent() != null
				&& getIntent().getExtras().getSerializable("model") != null) {
			model_notice = (GetPrjNoticeListResponse) getIntent().getExtras()
					.getSerializable("model");
		}

		ll_btn_subProgress.setVisibility(View.GONE);
	}

	@Override
	protected void initData() {
		lv_list.setMode(Mode.DISABLED);
		lv_list.setOnRefreshListener(this);

		if (model_notice != null && model_notice.getPrjNoticeList() != null) {
			adapter_list = new Adapter_ProjectNotice(this);
			adapter_list.setData(model_notice.getPrjNoticeList());
			lv_list.setAdapter(adapter_list);
			lv_list.setOnItemClickListener(this);
		}
	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

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

}
