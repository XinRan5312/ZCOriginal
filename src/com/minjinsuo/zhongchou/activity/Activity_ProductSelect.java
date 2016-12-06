package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeResponse;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeResponse.ElementBookletList;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListResponse.ElementPrjList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_ProductSelect;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 产品列表筛选效果(更多、公益、等)
 * 
 * @author zp
 *
 *         2016年6月22日
 */
public class Activity_ProductSelect extends Activity_Base implements
		OnItemClickListener, OnRefreshListener2<ListView> {

	@ViewInject(R.id.lv_list)
	PullToRefreshListView lv_list;
	// 筛选类型
	@ViewInject(R.id.tv_sele_type)
	TextView tv_sele_type;
	@ViewInject(R.id.tv_sele_status)
	TextView tv_sele_status;

	private Adapter_ProductSelect adapter_list;
	private ListView choice_list;
	private SimpleAdapter adapter_pop_list;
	private PopupWindow popupWindow;
	private List<Map<String, String>> maps1 = new ArrayList<>();
	private List<Map<String, String>> maps2 = new ArrayList<>();
	private boolean sel_load_showing = true;
	private boolean isPullDown = true;

	private GetBookletByTypeResponse model;
	private List<ElementBookletList> list_industry;// 从首页传递而来
	private List<String> list_status = new ArrayList<String>();

	private String title = "";
	private String type_code = "";// 行业类型请求标示码
	private String status = "";// 状态标示码
	private GetPrjInfoPageListResponse model_prj;
	private List<ElementPrjList> list = new ArrayList<GetPrjInfoPageListResponse.ElementPrjList>();;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_product_select);
		x.view().inject(this);
		initView();
		initData();
		initPopup();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleText("全部");
		setTitleBack();

		if (getIntent() != null) {
			title = getIntent().getStringExtra("title");
			type_code = getIntent().getStringExtra("type_code");
			tv_sele_type.setText(title);

			getPrjInfoPageList(true, type_code, status);

			model = (GetBookletByTypeResponse) getIntent()
					.getSerializableExtra("list_industry");
			if (model != null && model.getBookletList() != null) {
				list_industry = model.getBookletList();

				Map<String, String> map;
				map = new HashMap<String, String>();
				map.put("title", "全部");
				maps1.add(map);
				map = new HashMap<String, String>();
				map.put("title", "公益");
				maps1.add(map);
				for (int i = 0; i < list_industry.size(); i++) {
					map = new HashMap<String, String>();
					map.put("title", list_industry.get(i).getDisplay());
					maps1.add(map);
				}
			}
		}
	}

	@Override
	protected void initData() {
		lv_list.setMode(Mode.BOTH);
		lv_list.setOnRefreshListener(this);
		adapter_list = new Adapter_ProductSelect(this);
		lv_list.setAdapter(adapter_list);
		lv_list.setOnItemClickListener(this);

		setStatusData();
	}

	@Override
	protected void initListener() {
		// 筛选框选择事件
		choice_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// popup列表
				popupWindow.dismiss();
				if (sel_load_showing) {// 行业类型
					if (position == 0) {
						type_code = "";
						title = "全部";
						tv_sele_type.setText(title);
					} else if (position == 1) {
						type_code = "";
						title = "公益";
						tv_sele_type.setText(title);
					} else {
						type_code = list_industry.get(position - 2).getCode();
						title = list_industry.get(position - 2).getDisplay();
						tv_sele_type.setText(list_industry.get(position - 2)
								.getDisplay());
					}
					getPrjInfoPageList(true, type_code, status);
				} else {// 选择标的状态
					if (position == 0) {
						status = "";
						tv_sele_status.setText("全部");
					} else {
						status = list_status.get(position);
						tv_sele_status
								.setText(maps2.get(position).get("title"));
					}
					getPrjInfoPageList(true, type_code, status);
				}

			}
		});

		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				CommonUtils.setTextViewDrawableRight(
						Activity_ProductSelect.this, tv_sele_status,
						R.drawable.arrow_down);
				CommonUtils.setTextViewDrawableRight(
						Activity_ProductSelect.this, tv_sele_type,
						R.drawable.arrow_down);
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(Activity_ProductSelect.this,
				Activity_ProductDetail.class);
		intent.putExtra("prjId", list.get(position - 1).getId());
		intent.putExtra("img", list.get(position - 1).getHomePicAddress());
		startActivity(intent);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		isPullDown = true;
		pageNo = 1;
		getPrjInfoPageList(true, type_code, status);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		isPullDown = false;
		pageNo++;
		getPrjInfoPageList(true, type_code, status);
	}

	@Event({ R.id.tv_sele_type, R.id.tv_sele_status })
	private void Onclick(View view) {
		switch (view.getId()) {
		case R.id.tv_sele_type:
			sel_load_showing = true;
			showPopupWindow(view, maps1);
			CommonUtils.setTextViewDrawableRight(Activity_ProductSelect.this,
					tv_sele_type, R.drawable.arrow_up);
			break;
		case R.id.tv_sele_status:
			sel_load_showing = false;
			showPopupWindow(view, maps2);
			CommonUtils.setTextViewDrawableRight(Activity_ProductSelect.this,
					tv_sele_status, R.drawable.arrow_up);
			break;
		}
	}


	/**
	 * 获取筛选列表数据
	 */
	public void getPrjInfoPageList(boolean isShow, String type, String statu) {
		GetPrjInfoPageListRequest request = new GetPrjInfoPageListRequest();
		if (title.equals("公益")) {
			request.setProdId("2");// 产品表示 0 股权众筹 1 产品众筹 2 公益众筹
			request.setCodPrjTrade("");
		} else if (title.equals("更多")) {
			request.setProdId("1");// 不填写代表获取所有类型
			request.setCodPrjTrade("");// 传空代表全部
		} else {
			request.setCodPrjTrade(type);// 项目行业 字典信息接口中value字段值
			request.setProdId("1");// 产品表示 0 股权众筹 1 产品众筹 2 公益众筹
		}
		request.setStatus(statu);// 状态
		request.setKeywords("");
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);

		NetWorkRequestManager.sendRequestPost(Activity_ProductSelect.this,
				isShow, request, GetPrjInfoPageListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		// case "getBookletByType":// 状态
		// GetBookletByTypeResponse model = (GetBookletByTypeResponse) response;
		// if (model != null && model.getBookletList() != null
		// && model.getBookletList().size() > 0) {
		// list_status = model.getBookletList();
		// setStatusData();
		// // // 接着获取列表数据
		// // getPrjInfoPageList(false, type_code, status);
		// lv_list.onRefreshComplete();
		// DialogUtils.dismisLoading();
		// }
		//
		// break;
		case "getPrjInfoPageList":// 获取列表数据
			model_prj = (GetPrjInfoPageListResponse) response;

			if (isPullDown) {
				adapter_list.deleteData();
				list.clear();
			}
			if (model_prj != null && model_prj.getPrjList() != null
					&& model_prj.getPrjList().size() > 0) {
				list.addAll(model_prj.getPrjList());
				adapter_list.setData(model_prj.getPrjList());
			} else if (isPullDown) {
				ToastUtil.showToast(Activity_ProductSelect.this, "暂无数据");
			} else {
				ToastUtil.showToast(Activity_ProductSelect.this, "已加载全部");
			}

			adapter_list.notifyDataSetChanged();
			// if (!isHadLoadData) {
			// getStatusType(false);
			// isHadLoadData = true;
			// } else {
			lv_list.onRefreshComplete();
			DialogUtils.dismisLoading();
			// }
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

	private void initPopup() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.pop_layout, null);
		choice_list = (ListView) view.findViewById(R.id.list_pop_layout);
		LayoutParams lp;
		lp = choice_list.getLayoutParams();
		lp.width = getWindowManager().getDefaultDisplay().getWidth() / 2 - 2;
		choice_list.setLayoutParams(lp);
		// 自适配长、框设置
		popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.sp_bg_white));
		popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		popupWindow.update();
	}

	/**
	 * 填充筛选状态条件列表数据
	 */
	public void setStatusData() {
		Map<String, String> map;
		map = new HashMap<String, String>();
		map.put("title", "全部");
		maps2.add(map);
		map = new HashMap<String, String>();
		map.put("title", "预热中");
		maps2.add(map);
		map = new HashMap<String, String>();
		map.put("title", "众筹中");
		maps2.add(map);
		map = new HashMap<String, String>();
		map.put("title", "众筹成功");
		maps2.add(map);
		map = new HashMap<String, String>();
		map.put("title", "项目成功");
		maps2.add(map);

		list_status.add(0, "");
		list_status.add(1, "20");
		list_status.add(2, "40");
		list_status.add(3, "50");
		list_status.add(4, "60");
	}

	private void showPopupWindow(View asDropDownView,
			List<Map<String, String>> maps) {
		adapter_pop_list = new SimpleAdapter(this, maps,
				R.layout.pop_list_item, new String[] { "title" },
				new int[] { R.id.choice_title });
		choice_list.setAdapter(adapter_pop_list);
		popupWindow.showAsDropDown(asDropDownView, 0, 0);
	}

}
