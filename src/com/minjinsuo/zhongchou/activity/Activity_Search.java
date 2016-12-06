package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjInfoPageListResponse.ElementPrjList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_ProductSelect;
import com.minjinsuo.zhongchou.adapter.Adapter_Search;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.widget.ClearableEditText;

/**
 * 搜索页面
 * 
 * @author zp
 *
 *         2016年9月7日
 */
public class Activity_Search extends Activity_Base implements
		OnItemClickListener, TextWatcher, OnRefreshListener2<ListView> {

	@ViewInject(R.id.lv_list_history)
	private ListView lv_list_history;
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;
	@ViewInject(R.id.et_content)
	private ClearableEditText et_content;
	@ViewInject(R.id.btn_back)
	private ImageView btn_back;
	@ViewInject(R.id.lv_list)
	PullToRefreshListView lv_list;
	@ViewInject(R.id.tip)
	private TextView tip;

	private Adapter_Search adapter;
	List<String> list_history = new ArrayList<String>();

	private Adapter_ProductSelect adapter_list;// 搜索结果产品、股权列表
	private List<ElementPrjList> list = new ArrayList<GetPrjInfoPageListResponse.ElementPrjList>();;
	public static final String CODE_HISTORY = "code_history";
	private String keyWord = "";
	private boolean isProduct;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.aty_search);
		x.view().inject(this);
		initView();
		initData();
		initListener();

	}

	@Override
	protected void initView() {
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		if (getIntent() != null
				&& getIntent().getStringExtra(AppContants.FROM) != null) {
			if (getIntent().getStringExtra(AppContants.FROM).equals("product")) {
				isProduct = true;
			} else {
				isProduct = false;
			}
		}
	}

	@Override
	protected void initData() {
		adapter = new Adapter_Search(this);
		lv_list_history.setAdapter(adapter);
		lv_list_history.setOnItemClickListener(this);
		// 初始化搜索历史列表
		initListHistory();

		// 结果列表（产品、股权）
		adapter_list = new Adapter_ProductSelect(this);
		lv_list.setAdapter(adapter_list);

		lv_list.setMode(Mode.BOTH);
		lv_list.setOnRefreshListener(this);
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				LogUtil.i("结果列表==" + position);// 1
				Intent intent = null;
				position = position - 1;
				if (isProduct) {
					intent = new Intent(Activity_Search.this,
							Activity_ProductDetail.class);
					intent.putExtra("prjId", list.get(position).getId());
					intent.putExtra("img", list.get(position)
							.getHomePicAddress());
					startActivity(intent);
				} else {
					intent = new Intent(Activity_Search.this,
							Activity_StockDetail.class);
					intent.putExtra("id", list.get(position).getId());
					intent.putExtra("img", list.get(position)
							.getHomePicAddress());
					startActivity(intent);
				}
			}
		});
	}

	@Override
	protected void initListener() {
		et_content.addTextChangedListener(this);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (btn_ok.getText().toString().equals("取消")) {
					finish();
				} else {
					// 进行搜索，并插入搜索历史
					keyWord = et_content.getText().toString().trim();
					if (!StringUtils.isEmpty(keyWord)) {
						tip.setVisibility(View.GONE);
						lv_list_history.setVisibility(View.GONE);
						lv_list.setVisibility(View.VISIBLE);

						isPullDown = true;
						pageNo = 1;
						getPrjInfoPageList(keyWord);
					}
				}
			}
		});
	}

	/**
	 * 初始化搜索历史列表
	 */
	public void initListHistory() {
		adapter.deleteData();
		// 获取保存的搜索历史记录
		String json_list_history = SharedPreferUtils.getValue(this,
				CODE_HISTORY, CODE_HISTORY, "");
		if (!StringUtils.isEmpty(json_list_history)) {
			tip.setVisibility(View.VISIBLE);
			lv_list_history.setVisibility(View.VISIBLE);
			lv_list.setVisibility(View.GONE);

			list_history = JSON.parseArray(json_list_history, String.class);
			adapter.setData(list_history);
			adapter.notifyDataSetChanged();
		} else {
			lv_list_history.setVisibility(View.GONE);
			tip.setVisibility(View.GONE);
		}

	}

	/**
	 * 根据关键词进行搜索
	 */
	public void getPrjInfoPageList(String keyWord) {
		GetPrjInfoPageListRequest request = new GetPrjInfoPageListRequest();
		request.setKeywords(keyWord);// 搜索关键词
		if (isProduct) {
			request.setProdId("1");// 不填写代表获取所有类型（0：股权；1：产品）
		} else {
			request.setProdId("0");
		}
		request.setCodPrjTrade("");// 传空代表全部
		request.setStatus("");// 状态
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);

		NetWorkRequestManager.sendRequestPost(Activity_Search.this, true,
				request, GetPrjInfoPageListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		GetPrjInfoPageListResponse model_prj = (GetPrjInfoPageListResponse) response;

		if (isPullDown) {
			adapter_list.deleteData();
			list.clear();
		}
		if (model_prj != null && model_prj.getPrjList() != null
				&& model_prj.getPrjList().size() > 0) {
			list.addAll(model_prj.getPrjList());
			adapter_list.setData(model_prj.getPrjList());
		} else if (isPullDown) {
			ToastUtil.showToast(Activity_Search.this, "暂无数据");
		} else {
			ToastUtil.showToast(Activity_Search.this, "已加载全部");
		}
		adapter_list.notifyDataSetChanged();

		// 搜索成功后添加到搜索历史
		insertSearchHistory(et_content.getText().toString().trim());

		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		LogUtil.i("click is " + position);
		et_content.setText(list_history.get(position));
		// 进行搜索，并插入搜索历史
		keyWord = et_content.getText().toString().trim();
		if (!StringUtils.isEmpty(keyWord)) {
			tip.setVisibility(View.GONE);
			lv_list_history.setVisibility(View.GONE);
			lv_list.setVisibility(View.VISIBLE);

			isPullDown = true;
			pageNo = 1;
			getPrjInfoPageList(keyWord);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		if (et_content.getText().toString().trim().length() <= 0) {
			btn_ok.setText("取消");

			initListHistory();
		} else {
			btn_ok.setText("确定");
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

		isPullDown = true;
		pageNo = 1;
		getPrjInfoPageList(keyWord);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		isPullDown = false;
		pageNo++;
		getPrjInfoPageList(keyWord);
	}

	/**
	 * 插入搜索历史
	 */
	public void insertSearchHistory(String keyword) {
		list_history.add(0, keyword);
		for (int i = 1; i < list_history.size(); i++) {
			if (list_history.get(i).equals(keyword)) {
				list_history.remove(i);
			}
		}

		String str_history = JSON.toJSONString(list_history);
		SharedPreferUtils.putValue(Activity_Search.this, CODE_HISTORY,
				CODE_HISTORY, str_history);
	}
}
