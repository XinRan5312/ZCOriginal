package com.minjinsuo.zhongchou.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListRequest;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListResponse;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkRepayListRequest;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkRepayListResponse;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkRepayListResponse.ElementCommonTalkRepayList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitCommonTalkRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCommonTalkResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_TopicMain;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_Tender;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.widget.ClearableEditText;

/**
 * 评论主题帖列表
 * 
 * @author zp
 *
 */
public class Activity_TopicMain extends Activity_Base implements
		OnItemClickListener, OnRefreshListener2<ListView> {

	@ViewInject(R.id.lv_list)
	PullToRefreshListView lv_list;
	@ViewInject(R.id.et_content)
	private ClearableEditText et_content;
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	private Adapter_TopicMain adapter_list;
	private List<Model_Tender> list;
	private GetCommonTalkListResponse model_topic;
	private String prjId = "";// 标的id，从详情页点击发起话题或更多话题传递而来

	private boolean isPullDown = true;
	private int pageNo = 1;
	private static int pageSize = 1000;// zpqa???暂时进行一次性获取，因为接口分页功能不好使

	// private GetCommonTalkRepayListResponse model_replay;
	// private ArrayList<ElementCommonTalkRepayList> list_replay;// 数据结构重组后的回复列表

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_topic);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ZCApplication.getInstance().isNeedRefresh) {
			ZCApplication.getInstance().isNeedRefresh = false;
			getCommonTalkList(true);
		}
	}

	@Override
	protected void initView() {
		setTitleText("话题");
		setTitleBack();

		if (getIntent() != null) {
			if (!StringUtils.isEmpty(getIntent().getStringExtra("id"))) {
				prjId = getIntent().getStringExtra("id");
				LogUtil.i("标的id==" + prjId);
			}

			getCommonTalkList(true);
		}
	}

	@Override
	protected void initData() {
		lv_list.setMode(Mode.PULL_FROM_START);// zpqa??只开放下拉刷新功能，上拉加载接口不好使
		lv_list.setOnRefreshListener(this);
		lv_list.setOnItemClickListener(this);

		adapter_list = new Adapter_TopicMain(this);
		lv_list.setAdapter(adapter_list);
	}

	@Override
	protected void initListener() {
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				submitCommonTalk();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		String id_mainToptic = model_topic.getCommonTalkList()
				.get(position - 1).getId();
		Intent intent = new Intent(Activity_TopicMain.this,
				Activity_TopicReply.class);
		intent.putExtra("id", prjId);
		intent.putExtra("ctId",
				model_topic.getCommonTalkList().get(position - 1).getId());
		Bundle bundle = new Bundle();
		bundle.putSerializable("model",
				model_topic.getCommonTalkList().get(position - 1));// 主贴对象
		intent.putExtras(bundle);
		startActivity(intent);

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (!CommonUtils.isNetLink(Activity_TopicMain.this)) {
			lv_list.onRefreshComplete();
			return;
		}
		pageNo = 1;
		isPullDown = true;
		getCommonTalkList(true);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

		if (!CommonUtils.isNetLink(Activity_TopicMain.this)) {
			lv_list.onRefreshComplete();
			return;
		}
		pageNo++;
		isPullDown = false;
		getCommonTalkList(true);
	}

	/**
	 * 获取话题主贴列表
	 */
	public void getCommonTalkList(boolean isShow) {
		GetCommonTalkListRequest request = new GetCommonTalkListRequest();
		request.setPrjId(Integer.parseInt(prjId));
		// request.setPrjId(1973);//zpqa
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetCommonTalkListResponse.class, this);
	}

	public void submitCommonTalk() {
		if (!ZCApplication.getInstance().isLogin()) {
			final Dialog dialog = DialogUtils
					.createTwoBtnDiolog(this, "请登录后操作");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							startActivity(new Intent(Activity_TopicMain.this,
									Activity_Login.class));
						}
					});
			return;
		}

		if (StringUtils.isEmpty(et_content.getText().toString().trim())) {
			ToastUtil.showToast(Activity_TopicMain.this, "请输入话题内容");
			return;
		}
		SubmitCommonTalkRequest request = new SubmitCommonTalkRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setPrId(Integer.parseInt(prjId));
		request.setContent(et_content.getText().toString().trim());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				SubmitCommonTalkResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getCommonTalkList":// 话题列表
			model_topic = (GetCommonTalkListResponse) response;
			if (isPullDown) {
				adapter_list.deleteData();
			}
			if (model_topic != null && model_topic.getCommonTalkList() != null) {
				if (model_topic.getCommonTalkList().size() > 0) {
					adapter_list.setData(model_topic.getCommonTalkList());
					adapter_list.notifyDataSetChanged();
				} else if (!isPullDown) {
					ToastUtil.showToast(Activity_TopicMain.this, "已加载全部");
				}
			}
			DialogUtils.dismisLoading();
			lv_list.onRefreshComplete();
			break;
		case "submitCommonTalk":// 提交话题
			ToastUtil.showToast(Activity_TopicMain.this, "发表话题成功");
			et_content.setText("");
			getCommonTalkList(true);
			break;

		default:
			break;
		}

	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
	}

}
