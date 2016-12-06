package com.minjinsuo.zhongchou.activity;

import java.util.Collections;

import net.zkbc.p2p.fep.message.protocol.GetPrjProgressListRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjProgressListResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_ProjectDynamic;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 项目动态进展展示；编辑项目动态 复用
 * 
 * @author zp
 *
 *         2016年6月22日
 */
public class Activity_ProjectDynamic extends Activity_Base implements
		OnItemClickListener, OnRefreshListener2<ListView> {

	@ViewInject(R.id.lv_list)
	PullToRefreshListView lv_list;
	@ViewInject(R.id.btn_subProgress)
	private Button btn_subProgress;
	@ViewInject(R.id.ll_btn_subProgress)
	private LinearLayout ll_btn_subProgress;

	private Adapter_ProjectDynamic adapter_list;
	// private List<Model_Tender> list;

	private GetPrjProgressListResponse model_progress;// 从产品详情页传递而来
	private String prjId = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_dynamic);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ZCApplication.getInstance().isNeedRefresh) {// 发布完动态可以获取时时动态
			ZCApplication.getInstance().isNeedRefresh = false;
			getPrjProgressList(true);
		}
	}

	@Override
	protected void initView() {
		setTitleText("项目动态");
		setTitleBack();

		if (getIntent() != null) {
			if (!StringUtils.isEmpty(getIntent().getStringExtra(
					AppContants.FROM))
					&& getIntent().getStringExtra(AppContants.FROM).equals(
							"myFaqi")) {
				ll_btn_subProgress.setVisibility(View.VISIBLE);
			} else {
				ll_btn_subProgress.setVisibility(View.GONE);
			}

			if (getIntent().getExtras().getSerializable("model") != null) {
				model_progress = (GetPrjProgressListResponse) getIntent()
						.getExtras().getSerializable("model");
			}
			// 我的发起——点击时传递过来id
			if (!StringUtils.isEmpty(getIntent().getStringExtra("id"))) {
				prjId = getIntent().getStringExtra("id");
				getPrjProgressList(true);
			}
		}
	}

	@Override
	protected void initData() {
		lv_list.setMode(Mode.DISABLED);
		lv_list.setOnRefreshListener(this);
		lv_list.setOnItemClickListener(this);

		adapter_list = new Adapter_ProjectDynamic(this);
		if (model_progress != null
				&& model_progress.getPrjProgressList() != null) {

			adapter_list.setData(model_progress.getPrjProgressList());
			lv_list.setAdapter(adapter_list);
		}
	}

	@Override
	protected void initListener() {
		btn_subProgress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!ZCApplication.getInstance().isLogin()) {
					new AlertDialog(Activity_ProjectDynamic.this).builder()
							.setMsg("您尚未登录，请登录后操作")
							.setPositiveButton("确定", new OnClickListener() {

								@Override
								public void onClick(View v) {
									startActivity(new Intent(
											Activity_ProjectDynamic.this,
											Activity_Login.class));
								}
							}).setNegativeButton("取消", new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub

								}
							}).show();
				} else {
					// 跳转到编辑进展页面
					Intent intent = new Intent(Activity_ProjectDynamic.this,
							Activity_DynamicEdit.class);
					intent.putExtra("id", prjId);
					startActivity(intent);
				}
			}
		});
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

	/**
	 * 获取项目公告进展(动态)
	 */
	public void getPrjProgressList(boolean isShow) {
		GetPrjProgressListRequest request = new GetPrjProgressListRequest();
		// request.setPrjId("2269");// zp暂时写死标id，否则没有进展数据
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjProgressListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		GetPrjProgressListResponse model_progress = (GetPrjProgressListResponse) response;
		if (model_progress != null
				&& model_progress.getPrjProgressList() != null) {
			adapter_list.deleteData();
			// 倒叙
			Collections.reverse(model_progress.getPrjProgressList());
			adapter_list.setData(model_progress.getPrjProgressList());
			lv_list.setAdapter(adapter_list);
			adapter_list.notifyDataSetChanged();
			DialogUtils.dismisLoading();
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

}
