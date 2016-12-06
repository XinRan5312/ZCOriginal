package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjRwdByPrjIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjRwdByPrjIdResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjRwdByPrjIdResponse.ElementPrjRwdList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_SelectTypes;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 选择支持档
 * 
 * @author zp
 *
 *         2016年6月27日
 */
public class Activity_SelectTypes extends Activity_Base implements
		OnItemClickListener {

	@ViewInject(R.id.lv_list)
	private PullToRefreshListView lv_list;

	private Adapter_SelectTypes adapter_list;
	private List<ElementPrjRwdList> list = new ArrayList<GetPrjRwdByPrjIdResponse.ElementPrjRwdList>();
	private GetPrjRwdByPrjIdResponse model_reward;// 从上个页面带过来

	private String prjId = "";
	private String flag = "";// 标示预热中还是众筹中,可能为空则表示是产品详情进入
	private boolean isFromProduct;
	private String type_prj = "";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.list_common);
		x.view().inject(this);

		ZCApplication.getInstance().addActivity(this);// 提交订单成功后便于一块关闭此页面
		initView();
		initData();
	}

	@Override
	protected void initView() {
		setTitleText("选择支持档位");
		setTitleBack();

		if (getIntent() != null) {
			type_prj = getIntent().getStringExtra(AppContants.TYPE_PRJ) == null ? ""
					: getIntent().getStringExtra(AppContants.TYPE_PRJ);

			// 从产品详情进入可以直接传递回报列表模型,从股权进入需要自己调用接口获取
			if (getIntent().getExtras().getSerializable("model") != null) {
				model_reward = (GetPrjRwdByPrjIdResponse) getIntent()
						.getExtras().getSerializable("model");
				initList(model_reward);
			}
			if (!StringUtils.isEmpty(getIntent().getStringExtra("id"))) {
				prjId = getIntent().getStringExtra("id");
				getPrjRwdByPrjId(true);
			}

			if (!StringUtils.isEmpty(getIntent().getStringExtra("flag"))) {
				flag = getIntent().getStringExtra("flag");
				LogUtil.i("来自flag==" + flag);
			}

		}
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {

	}

	public void initList(GetPrjRwdByPrjIdResponse model_reward) {
		if (model_reward != null && model_reward.getPrjRwdList() != null) {
			adapter_list = new Adapter_SelectTypes(this, flag, type_prj);
			adapter_list.setData(model_reward.getPrjRwdList());
			lv_list.setAdapter(adapter_list);
			lv_list.setMode(Mode.DISABLED);
			lv_list.setOnItemClickListener(this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 注：点击事件在adapter中

	}

	/**
	 * 获取回报列表
	 */
	public void getPrjRwdByPrjId(boolean isShow) {
		GetPrjRwdByPrjIdRequest request = new GetPrjRwdByPrjIdRequest();
		request.setPrjId(prjId);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjRwdByPrjIdResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		GetPrjRwdByPrjIdResponse model_reward = (GetPrjRwdByPrjIdResponse) response;
		if (model_reward != null && model_reward.getPrjRwdList() != null
				&& model_reward.getPrjRwdList().size() > 0) {
			initList(model_reward);
		}
		DialogUtils.dismisLoading();
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
