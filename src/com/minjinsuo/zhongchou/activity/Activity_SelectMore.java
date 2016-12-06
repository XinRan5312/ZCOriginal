package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.model.Model_SelectMore;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 可多选的list<String>
 * 
 * @author zp
 *
 *         2016年9月22日
 */
public class Activity_SelectMore extends Activity_Base implements
		OnItemClickListener {
	@ViewInject(R.id.lv_list)
	private PullToRefreshListView lv_list_Pro;

	private Adapter_Inner adapter_list;
	private List<Model_SelectMore> list_data = new ArrayList<Model_SelectMore>();

	private ArrayList<String> strList = new ArrayList<String>();// 保存选中的id
	private ArrayList<String> strContent = new ArrayList<String>();// 保存选中的文字

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.aty_select_more);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleText("认证资格");
		setTitleBack();

		setTitleRight("确定", new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 需要把adapter中选择的id数据传回上个页面（可以多选）
				Intent intent = new Intent();
				LogUtil.i("选中的id==" + JSON.toJSONString(strList));
				intent.putStringArrayListExtra("strArr_id", strList);
				setResult(RESULT_OK, intent);
				finish();

			}
		});

	}

	@Override
	protected void initData() {
		if (getIntent() != null) {
			list_data = (List<Model_SelectMore>) getIntent()
					.getSerializableExtra("list");
			LogUtil.i("size==" + list_data.size());

			if (!StringUtils.isEmpty(getIntent().getStringExtra("prefrence"))
					&& getIntent().getSerializableExtra("prefrence").equals(
							"prefrence")) {
				setTitleText("投资偏好");
			} else {
				setTitleText("认证资格");
			}
		}

		lv_list_Pro.setMode(Mode.DISABLED);

		adapter_list = new Adapter_Inner(this);
		adapter_list.setData(list_data);
		lv_list_Pro.setAdapter(adapter_list);
		lv_list_Pro.setOnItemClickListener(this);
	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onSuccess(ResponseSupport response) {
	}

	@Override
	public void failure() {
	}

	@Override
	public void onFailure(ResponseSupport response) {
	}

	private class Adapter_Inner extends BaseAdapter {

		public Context context;
		private ArrayList<Model_SelectMore> tenderList;
		private boolean history;

		public Adapter_Inner(Context context) {
			super();
			this.context = context;
			// 初始化
			tenderList = new ArrayList<Model_SelectMore>();
		}

		public void setData(List<Model_SelectMore> tenderList) {
			if (tenderList != null) {
				this.tenderList.addAll(tenderList);
			}
		}

		public ArrayList<Model_SelectMore> getData() {
			return tenderList;
		}

		public void deleteData() {
			if (this.tenderList != null && this.tenderList.size() > 0) {
				this.tenderList.clear();
			}
		}

		@Override
		public int getCount() {
			if (tenderList != null) {
				return tenderList.size();
			} else {
				return 0;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mHolder = null;
			if (convertView == null) {
				mHolder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_select_more, null, false);
				mHolder.tv_content = (TextView) convertView
						.findViewById(R.id.tv_title);
				mHolder.cb_select = (CheckBox) convertView
						.findViewById(R.id.cb_select);

				convertView.setTag(mHolder);
			} else {
				mHolder = (ViewHolder) convertView.getTag();
			}

			final Model_SelectMore info = tenderList.get(position);
			if (info != null) {
				mHolder.tv_content.setText(info.getContent());

				mHolder.cb_select
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									info.setChecked(true);
									if (!strList.contains(info.getId())) {
										strList.add(info.getId());
										strContent.add(info.getContent());
									}
								} else {
									info.setChecked(false);
									if (strList.contains(info.getId())) {
										strList.remove(info.getId());
										strContent.remove(info.getContent());
									}
								}

							}
						});

				if (info.isChecked()) {
					mHolder.cb_select.setChecked(true);
				} else {
					mHolder.cb_select.setChecked(false);
				}
			}
			return convertView;
		}

		@Override
		public Object getItem(int position) {
			return tenderList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		class ViewHolder {
			private TextView tv_content;
			private CheckBox cb_select;
		}

	}

}
