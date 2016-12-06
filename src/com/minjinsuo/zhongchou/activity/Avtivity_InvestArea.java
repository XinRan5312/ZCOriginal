package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeRequest;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeResponse;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeResponse.ElementBookletList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.SectionedBaseAdapter;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.ZModel_InvestArea;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.view.UpLoadPinnedHeaderListView;

/**
 * 体现记录，需要分组list显示
 * 
 * @author zp
 *
 *         2015年11月5日
 */
public class Avtivity_InvestArea extends Activity_Base {

	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	// 设置自定义listview
	UpLoadPinnedHeaderListView pinnedlistView;
	Adapter_InvestArea sectionedAdapter;

	/**
	 * 数组分组使用参数
	 */
	List<String> listGroup = new ArrayList<String>();// 组容器。存放所有的组，用于listView参数
	List<ZModel_InvestArea> listLast = new ArrayList<ZModel_InvestArea>();// 存放分好组的数据模型，用于显示到listview
	ZModel_InvestArea model = null;// 实体类对象

	List<com.minjinsuo.zhongchou.model.ZModel_InvestArea.ElementBookletList> list_son_collect;// 保存实体列表中内容数据
	com.minjinsuo.zhongchou.model.ZModel_InvestArea.ElementBookletList son_record_detail;// 某个组下的list容器

	private GetBookletByTypeRequest request;
	private List<ElementBookletList> list_BigTrade = new ArrayList<ElementBookletList>();
	private List<ElementBookletList> list_smallTrade = new ArrayList<ElementBookletList>();

	private List<String> strList = new ArrayList<String>();// 保存选中的行业编码

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zgetcash_record);
		setTitleBack();
		setTitleText("投资领域");

		x.view().inject(this);
		initView();
		initData();
		initListener();

		getBookerType("prjTrade", true);// prjTrade:标示获取大行业；prjTrade.:标示获取子行业
	}

	public void initView() {

		pinnedlistView = (UpLoadPinnedHeaderListView) findViewById(R.id.pinnedListView);
		/**
		 * 加载主体数据,需要传递实体类型的list和存放所有组名的list
		 */
		sectionedAdapter = new Adapter_InvestArea(this, listLast, listGroup);
		pinnedlistView.setAdapter(sectionedAdapter);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String[] strArr = new String[strList.size()];
				strList.toArray(strArr);
				String str2 = StringUtils.join(",", strArr);
				LogUtil.i(strList.size() + "要传回的行业号=" + "str2=" + str2);
				Intent intent = new Intent();
				intent.putExtra("data", str2);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

	/**
	 * 获取行业或子行业
	 */
	public void getBookerType(String type, boolean isShow) {
		request = new GetBookletByTypeRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setType(type);
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetBookletByTypeResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		GetBookletByTypeResponse model = (GetBookletByTypeResponse) response;
		if (request.getType().equals("prjTrade")) {// 获取大行业
			if (model != null && model.getBookletList() != null) {
				list_BigTrade.addAll(model.getBookletList());

				getBookerType("prjTrade.", false);
			}
		} else {// 获取子行业
			if (model != null && model.getBookletList() != null) {
				list_smallTrade.addAll(model.getBookletList());

				completeList();
			}
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

	/**
	 * 重组大小行业数据结构
	 */
	public void completeList() {
		String code_Group = "";
		String code_son = "";
		// 添加假数据
		for (int i = 0; i < list_BigTrade.size(); i++) {// 大行业
			listGroup.add(list_BigTrade.get(i).getDisplay());
			code_Group = list_BigTrade.get(i).getCode();

			model = new ZModel_InvestArea();
			model.setGroupName(list_BigTrade.get(i).getDisplay());
			list_son_collect = new ArrayList<ZModel_InvestArea.ElementBookletList>();
			for (int j = 0; j < list_smallTrade.size(); j++) {// 每组孩子
				code_son = list_smallTrade.get(j).getCode();

				if (code_Group.equals(code_son)) {
					son_record_detail = new ZModel_InvestArea.ElementBookletList();
					son_record_detail.setDisplay(list_smallTrade.get(j)
							.getDisplay());
					son_record_detail.setValue(list_smallTrade.get(j)
							.getValue());
					son_record_detail.setCode(list_smallTrade.get(j).getCode());
					list_son_collect.add(son_record_detail);
				}
			}
			model.setSonList(list_son_collect);
			listLast.add(model);
		}

		sectionedAdapter.notifyDataSetChanged();
		DialogUtils.dismisLoading();
	}

	/**
	 * 投资认证—— 选择投资领域
	 * 
	 * @author zp
	 *
	 *         2016年9月5日
	 */
	private class Adapter_InvestArea extends SectionedBaseAdapter {

		Context context;
		private List<ZModel_InvestArea> taskList;
		private List<String> title;

		public Adapter_InvestArea(Context context,
				List<ZModel_InvestArea> taskList, List<String> title) {
			this.context = context;
			this.taskList = taskList;
			this.title = title;
		}

		public void reference(int section, int position) {
			notifyDataSetChanged();
		}

		@Override
		public Object getItem(int section, int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int section, int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getSectionCount() {
			return taskList.size();
		}

		@Override
		public int getCountForSection(int section) {

			return taskList.get(section).getSonList().size();
		}

		@Override
		public View getItemView(final int section, final int position,
				View convertView, ViewGroup parent) {
			final int index = position;
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			ViewHolderChild childHoler = null;
			if (convertView == null) {
				childHoler = new ViewHolderChild();
				convertView = layoutInflater.inflate(
						R.layout.zitem_tixian_record, parent, false);
				childHoler.cName = (TextView) convertView
						.findViewById(R.id.tv_title);
				childHoler.cb_select = (CheckBox) convertView
						.findViewById(R.id.cb_select);

				convertView.setTag(childHoler);
			} else {
				childHoler = (ViewHolderChild) convertView.getTag();
			}

			childHoler.cName.setText(taskList.get(section).getSonList()
					.get(position).getDisplay());// 子item标题
			childHoler.cb_select
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								taskList.get(section).getSonList().get(index)
										.setChecked(true);
								if (!strList.contains(taskList.get(section)
										.getSonList().get(index).getValue())) {
									strList.add(taskList.get(section)
											.getSonList().get(index).getValue());
								}
							} else {
								taskList.get(section).getSonList().get(index)
										.setChecked(false);
								if (strList.contains(taskList.get(section)
										.getSonList().get(index).getValue())) {
									strList.remove(taskList.get(section)
											.getSonList().get(position)
											.getValue());
								}
							}
						}
					});

			if (taskList.get(section).getSonList().get(position).isChecked()) {
				childHoler.cb_select.setChecked(true);
			} else {
				childHoler.cb_select.setChecked(false);
			}

			return convertView;
		}

		@Override
		public View getSectionHeaderView(int section, View convertView,
				ViewGroup parent) {
			LinearLayout layout = null;
			if (convertView == null) {
				LayoutInflater inflator = (LayoutInflater) parent.getContext()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				layout = (LinearLayout) inflator.inflate(
						R.layout.zsection_headview_for_list, null);
			} else {
				layout = (LinearLayout) convertView;
			}

			((TextView) layout.findViewById(R.id.textItem)).setText(taskList
					.get(section).getGroupName());// 组 标题

			return layout;
		}

		public final class ViewHolderChild {

			private TextView cName;// 子 title
			private CheckBox cb_select;
		}

	}
}
