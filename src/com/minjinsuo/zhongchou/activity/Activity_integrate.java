package com.minjinsuo.zhongchou.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetScoreListRequest;
import net.zkbc.p2p.fep.message.protocol.GetScoreListResponse;
import net.zkbc.p2p.fep.message.protocol.GetScoreListResponse.ElementScoreList;
import net.zkbc.p2p.fep.message.protocol.GetUserScoreRequest;
import net.zkbc.p2p.fep.message.protocol.GetUserScoreResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_integrate;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

public class Activity_integrate extends Activity_Base implements
		OnItemClickListener {
	public static final int FLAG_NOW = 0;
	public static final int FLAG_HISTORY = 1;
	private PullToRefreshListView lv_product;
	private ArrayList<Model_integrate> datas = new ArrayList<Model_integrate>();
	private MyIntegrateAdapter adapter;
	private int pageNo = 1;
	private int pageSize = 10;
	String strRuleString = "";
	// 刷新的flag，为0代表上拉，为1下拉
	private int refreshFlag;
	// 总积分
	private String totalIntegrate = "0";
	// 可用积分
	private String freezeInegrate = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_integrate);
		setTitleBack();
		setTitleText("我的积分");
		initView();
		initListener();
	}

	@Override
	protected void initView() {
		if (StringUtils.isEmpty(strRuleString)) {
			byte b[] = CommonUtils.getData(this, "intergerRule.txt");
			String str = "";
			try {
				str = new String(b, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			strRuleString = str;
		}

		lv_product = (PullToRefreshListView) findViewById(R.id.lv_product);
		lv_product.setMode(Mode.DISABLED);
		adapter = new MyIntegrateAdapter();

		ListView mListView = lv_product.getRefreshableView();
		mListView.setDivider(null);
		mListView.setDividerHeight(0);
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(this);
		mListView.setOnTouchListener(touchListener);

		mListView.setOnItemClickListener(this);
		// 请求网络数据
		startRequestDataFromNet();
	}

	private void startRequestDataFromNet() {
		String sessionId = ZCApplication.getInstance().userInfo.getSessionId();
		GetUserScoreRequest userScoreRequest = new GetUserScoreRequest();
		userScoreRequest.setSessionId(sessionId);
		NetWorkRequestManager.sendRequestPost(getContext(), true,
				userScoreRequest, GetUserScoreResponse.class, this);
	}

	private void startRequestDataFromNet2(boolean isShow) {
		GetScoreListRequest scoreListRequest = new GetScoreListRequest();
		scoreListRequest.setSessionId(ZCApplication.getInstance().userInfo
				.getSessionId());
		scoreListRequest.setPageno(pageNo);
		scoreListRequest.setPagesize(pageSize);

		NetWorkRequestManager.sendRequestPost(getContext(), isShow,
				scoreListRequest, GetScoreListResponse.class, this);
	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	class MyIntegrateAdapter extends BaseAdapter {
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			if (position == 0) {
				// 第一个条目比较特殊
				view = View.inflate(getApplicationContext(),
						R.layout.item_lv_integrate_top, null);
				TextView tv_totalAmount = (TextView) view
						.findViewById(R.id.tv_totalAmount);
				TextView tv_usingAmount = (TextView) view
						.findViewById(R.id.tv_usingAmount);
				TextView tvIntergerRule = (TextView) view
						.findViewById(R.id.tvIntergerRule);
				// 点击显示积分规则
				tvIntergerRule.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						DialogUtils.createOneBtnDiolog(getContext(),
								strRuleString, "积分规则", Gravity.LEFT).show();
						// new AlertDialog(getContext()).builder()
						// .setMsg(strRuleString).setTitle("积分规则")
						// .setPositiveButton("确定", new OnClickListener() {
						//
						// @Override
						// public void onClick(View v) {
						// }
						// }).show();
					}
				});
				tv_totalAmount.setText(totalIntegrate);
				// tv_totalAmount
				// .setText((Integer.parseInt(totalIntegrate) - Integer
				// .parseInt(freezeInegrate)) + "");
				tv_usingAmount.setText(freezeInegrate);
				ImageView iv_showMall = (ImageView) view
						.findViewById(R.id.iv_showMall);
				iv_showMall.setOnTouchListener(touchListener);
				iv_showMall.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 进入积分商城界面
						Intent intent = new Intent(Activity_integrate.this,
								Activity_integrateShopMall.class);
						intent.putExtra("useIntegrateCount", Integer
								.valueOf((null == totalIntegrate || "null"
										.equals(totalIntegrate)) ? "0"
										: totalIntegrate));
						startActivityForResult(intent, 0);
					}
				});
			} else {
				view = View.inflate(getApplicationContext(),
						R.layout.item_lv_integrate, null);
				TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
				TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
				TextView tv_amount = (TextView) view
						.findViewById(R.id.tv_amount);
				TextView tv_status = (TextView) view
						.findViewById(R.id.tv_status);
				Model_integrate model = datas.get(position - 1);
				String type = model.getType();
				if (type.length() > 10) {
					type = type.substring(0, 10) + "...";
				}
				tv_type.setText(type);
				tv_time.setText(model.getTime());
				tv_amount.setText(model.getAmount());
				tv_status.setText(model.getStatus());
			}
			return view;
		}

		@Override
		public int getCount() {
			return datas.size() + 1; // 因为第一个条目多一
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// 重新刷新界面
		datas.clear();
		startRequestDataFromNet();
		super.onActivityResult(arg0, arg1, arg2);
	}

	/**
	 * 统计相关
	 */
	OnTouchListener touchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP://
				// xHttpUtil.getInstance().postCountLog(getContext(),
				// PageMapp.pagMap.get(TAG),
				// CommonUtil.CODE.EVENTID.TOUCH_UP,
				// PageMapp.controlMap.get(v.getId()), null, null);
				break;

			default:
				break;
			}
			return false;
		}
	};

	// @Override
	// public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
	// {
	// // TODO Auto-generated method stub
	// // 下拉刷新
	// pageNo = 1;
	// refreshFlag = 1;
	// startRequestDataFromNet();
	// }
	//
	// @Override
	// public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
	// // TODO Auto-generated method stub
	// // 上拉加载更多
	// refreshFlag = 0;
	// pageNo++;
	// startRequestDataFromNet();
	// if (datas.size() % pageSize == 0) {
	// } else {
	// CommonUtils.showToast(getApplicationContext(), "已经到底啦");
	// }
	// }

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub
		switch (response.getMessageId()) {
		case "getUserScore":
			GetUserScoreResponse userScoreResponse = (GetUserScoreResponse) response;
			if (null != userScoreResponse) {
				totalIntegrate = userScoreResponse.getUsedpoints();
				freezeInegrate = userScoreResponse.getFreezepoints();
			} else {
				Log.e("GetUserScore Interface ", "usedpoints key is not exist");
			}
			startRequestDataFromNet2(false);
			break;
		case "getScoreList":
			GetScoreListResponse scoreListResponse = (GetScoreListResponse) response;
			List<ElementScoreList> scoreLists = scoreListResponse
					.getScoreList();
			if (scoreLists == null || scoreLists.size() == 0) {
				return;
			}
			if (refreshFlag == 1) {
				// 下拉刷新清除已有数据
				datas.clear();
			}
			for (ElementScoreList element : scoreLists) {
				String time = element.getTime();
				String pointsname = element.getPointsname();// type
				String status = element.getStatus();// status
				String points = element.getPoints();// amount
				Model_integrate model = new Model_integrate(pointsname, time,
						points, status);
				datas.add(model);
			}
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
	}

	@Override
	public void failure() {

	}

	@Override
	protected void initData() {

	}

	@Override
	public void onFailure(ResponseSupport response) {

	}
}
