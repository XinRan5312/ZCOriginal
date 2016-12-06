package com.minjinsuo.zhongchou.activity;

import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjCustBuyByPrjIdResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitApplyPrjLeaderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitApplyPrjLeaderResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_SupportPerson;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_Tender;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 支持者列表(产品和股权公用)
 * 
 * @author zp
 *
 *         2016年6月22日
 */
public class Activity_SupportPerson extends Activity_Base implements
		OnItemClickListener, OnRefreshListener2<ListView> {

	@ViewInject(R.id.lv_list)
	// PullToRefreshListView lv_list;
	private ListView lv_list;

	private Adapter_SupportPerson adapter_list;
	private List<Model_Tender> list;

	private GetPrjCustBuyByPrjIdResponse model_investor;// 从上个页面传递而来
	private int flag = 0;// 0:产品，1：股权
	private boolean isHasLingTou;// 是否有领投人
	private String prjId = "";
	private String userId = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_supportperson);
		x.view().inject(this);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		setTitleText("支持者列表");
		setTitleBack();

		if (getIntent() != null) {
			flag = getIntent().getIntExtra("flag", 0);
			prjId = StringUtils.isEmpty(getIntent().getStringExtra("prjId")) ? ""
					: getIntent().getStringExtra("prjId");
		}

		if (getIntent() != null
				&& getIntent().getSerializableExtra("model") != null) {
			model_investor = (GetPrjCustBuyByPrjIdResponse) getIntent()
					.getExtras().getSerializable("model");

			initList();
		}
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initListener() {

	}

	// 填充listView数据
	public void initList() {
		if (model_investor != null
				&& model_investor.getPrjCustBuyList() != null) {
			// 下面将领头人数据提到list的顶部
			int size = model_investor.getPrjCustBuyList().size();
			for (int i = 0; i < size; i++) {
				if (!StringUtils.isEmpty(model_investor.getPrjCustBuyList()
						.get(i).getIsPrjLeader())
						&& model_investor.getPrjCustBuyList().get(i)
								.getIsPrjLeader().equals("true")) {
					model_investor.getPrjCustBuyList().add(0,
							model_investor.getPrjCustBuyList().get(i));
					model_investor.getPrjCustBuyList().remove(i + 1);
					isHasLingTou = true;
					LogUtil.i("有领头人~~" + i);
					break;
				}
			}
		}
		adapter_list = new Adapter_SupportPerson(this, flag, isHasLingTou);
		if (model_investor.getPrjCustBuyList() != null)
			adapter_list.setData(model_investor.getPrjCustBuyList());

		if (flag == 1 && !isHasLingTou) {
			// 没有领投人，添加申请领投的header
			View view_apply = getLayoutInflater().inflate(
					R.layout.item_support_apply, null);
			LinearLayout ll_apply = (LinearLayout) view_apply
					.findViewById(R.id.ll_apply);
			Button btn_apply = (Button) view_apply.findViewById(R.id.btn_apply);
			lv_list.addHeaderView(view_apply, null, true);
			LogUtil.i("添加header 成功~~");

			ll_apply.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Activity_SupportPerson.this,
							Activity_LingTouerDetail.class);
					intent.putExtra(Activity_LingTouerDetail.ISHADLINGTOU, false);
					intent.putExtra("prjId", prjId);
					startActivity(intent);
				}
			});

			btn_apply.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (ZCApplication.getInstance().isLogin()) {
						new AlertDialog(Activity_SupportPerson.this).builder()
								.setMsg("是否申请成为该项目领投人？")
								.setPositiveButton("确定", new OnClickListener() {

									@Override
									public void onClick(View v) {
										submitApplyPrjLeader();
									}
								})
								.setNegativeButton("取消", new OnClickListener() {

									@Override
									public void onClick(View v) {

									}
								}).show();
					} else {
						new AlertDialog(Activity_SupportPerson.this)
								.builder()
								.setMsg("您尚未登录，请登录后操作")
								.setPositiveButton("确定", new OnClickListener() {

									@Override
									public void onClick(View v) {
										startActivity(new Intent(
												Activity_SupportPerson.this,
												Activity_Login.class));
									}
								})
								.setNegativeButton("取消", new OnClickListener() {

									@Override
									public void onClick(View v) {

									}
								}).show();
					}
				}
			});
		}
		lv_list.setAdapter(adapter_list);
		lv_list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		LogUtil.i("click is " + position);
		if (position == 0 && isHasLingTou) {
			Intent intent = new Intent(Activity_SupportPerson.this,
					Activity_LingTouerDetail.class);
			intent.putExtra(Activity_LingTouerDetail.ISHADLINGTOU, true);
			intent.putExtra("prjId", prjId);
			intent.putExtra("userId", model_investor.getPrjCustBuyList().get(0)
					.getCustId());
			startActivity(intent);
		}
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
	 * 申请成为项目领投人
	 */
	public void submitApplyPrjLeader() {
		SubmitApplyPrjLeaderRequest request = new SubmitApplyPrjLeaderRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, true, request,
				SubmitApplyPrjLeaderResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		LogUtil.i("申请成功~~");
		DialogUtils.dismisLoading();
		new AlertDialog(Activity_SupportPerson.this).builder().setMsg("申请已提交")
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(View v) {

					}
				}).show();

	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		switch (response.getMessageId()) {
		case "submitApplyPrjLeader":
			if (!StringUtils.isEmpty(response.getMessage())) {
				if (response.getMessage().equals("申请项目领投人，需要先是平台领投人的身份!")) {

					ToastUtil.cancelToast();// 避免网络框架中弹框重复
					new AlertDialog(Activity_SupportPerson.this).builder()
							.setMsg(response.getMessage())
							.setPositiveButton("去认证", new OnClickListener() {

								@Override
								public void onClick(View v) {
									startActivity(new Intent(
											Activity_SupportPerson.this,
											Activity_InvestCerciticy.class));
								}
							}).setNegativeButton("取消", new OnClickListener() {

								@Override
								public void onClick(View v) {

								}
							}).show();
				} else {
					ToastUtil.showToast(Activity_SupportPerson.this,
							response.getMessage());
				}
			}

			break;
		}
	}

}
