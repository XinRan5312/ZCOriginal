package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.GetCommNewListRequest;
import net.zkbc.p2p.fep.message.protocol.GetCommNewListResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_Msg;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_msg;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 消息中心和平台公告公用 消息展示列表
 * 
 * @author zp
 *
 *         2016年9月6日
 */
public class Activity_MsgCommon extends Activity_Base implements
		OnItemClickListener, OnRefreshListener2<ListView> {
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;
	private ArrayList<Model_msg> datas = new ArrayList<Model_msg>();
	private Adapter_Msg adapter;
	// 刷新的flag，为0代表上拉，为1下拉
	private int refreshFlag;
	private PullToRefreshListView plv_msg;
	private boolean flag;// 0：代表帮助中心 1：平台公告
	private String type = "HelpCenter";// newType 所属信息类别 HelpCenter-帮助中心
										// CompanyNotice-公司公告

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_list_commont);

		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleBack();
		setTitleText("消息中心");

		if (getIntent() != null) {
			if (getIntent().getIntExtra("flag", 0) == 0) {
				setTitleText("帮助中心");
				type = "HelpCenter";
			} else {
				setTitleText("公司公告");
				type = "CompanyNotice";
			}
		}
	}

	@Override
	protected void initData() {
		plv_msg = (PullToRefreshListView) findViewById(R.id.lv_list);
		plv_msg.setOnRefreshListener(Activity_MsgCommon.this);
		plv_msg.setMode(com.handmark.pulltorefresh.library.PullToRefreshBase.Mode.BOTH);
		plv_msg.setOnItemClickListener(this);

		adapter = new Adapter_Msg(this);
		plv_msg.setAdapter(adapter);
		// 请求网络数据
		startRequestDataFromNet(type);
	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (datas != null && datas.size() > 0) {
			LogUtil.i("点击了position==" + (arg2 - 1));
			Intent intent = new Intent(this, Activity_MsgDetail.class);
			intent.putExtra("id", datas.get(arg2 - 1).getId());
			intent.putExtra("content", datas.get(arg2 - 1).getDetail());
			datas.get(arg2 - 1).setTatus("1");
			startActivity(intent);
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// 下拉刷新
		pageNo = 1;
		refreshFlag = 1;
		startRequestDataFromNet(type);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

		// 上拉加载更多
		refreshFlag = 0;
		pageNo++;
		startRequestDataFromNet(type);
	}

	private void startRequestDataFromNet(String type) {
		GetCommNewListRequest request = new GetCommNewListRequest();
		// request.setSessionId(ZCApplication.getInstance().getUserInfo()
		// .getSessionId());
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		request.setNewType(type);

		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetCommNewListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		DialogUtils.dismisLoading();
		plv_msg.onRefreshComplete();

		GetCommNewListResponse model = (GetCommNewListResponse) response;
		if (refreshFlag == 1) {
			// 下拉刷新清除已有数据
			datas.clear();
		}
		if (model == null || model.getInfoList() == null
				|| model.getInfoList().size() == 0) {
			noLog.setVisibility(View.VISIBLE);
			plv_msg.setVisibility(View.GONE);
			return;
		}
		noLog.setVisibility(View.GONE);
		plv_msg.setVisibility(View.VISIBLE);
		for (int i = 0; i < model.getInfoList().size(); i++) {
			String id = model.getInfoList().get(i).getId();
			String content = model.getInfoList().get(i).getContent();
			String sendtime = StringUtils.getDate(model.getInfoList().get(i)
					.getIssue_time(), 2);
			String title = model.getInfoList().get(i).getTitle();
			Model_msg info = new Model_msg(id, title, sendtime, "0", content,
					"");
			datas.add(info);
		}

		adapter.setDatas(datas);
		adapter.notifyDataSetChanged();

	}

	@Override
	public void failure() {

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

}
