package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.CommonAdapter;
import com.minjinsuo.zhongchou.adapter.ViewHolder;
import com.minjinsuo.zhongchou.model.Area;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.db.DBhelper;

/**
 * 选择省市列表
 */
public class Act_AreaChoiceProVince extends Activity_Base {
	@ViewInject(R.id.areaListView)
	private ListView recy;
	@ViewInject(R.id.choiceCity)
	private TextView choiceCity;
	private CommonAdapter<Area> areaAdapter;
	private ArrayList<Area> areas = null;
	private final int REQUEST_CHOICEAREA = 120;// 请求选择城市的请求CODE
	private final static String RESULT_CHOICEAREA = "choiceArea";// 返回选择的城市的KEY
	// /** 选择的省份ID **/
	// private String pCode, cityName = "";

	private Area hasChoiceArea = null;
	private boolean isChoiceCity = true;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_areachoicepro);
		x.view().inject(this);

		ZCApplication.getInstance().addActivity(Act_AreaChoiceProVince.this);
		Bundle bundle = getIntent().getBundleExtra("hasChoiceArea");
		if (bundle.containsKey("hasChoiceArea")) {
			hasChoiceArea = (Area) bundle.get("hasChoiceArea");
		}
		isChoiceCity = bundle.getBoolean("choiceCity");
		initView();
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

	@Override
	protected void initView() {
		setTitleText("常驻城市");
		setTitleBack();
		// setTitleBack(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if (null != hasChoiceArea) {
		// setResult(RESULT_OK, new Intent().putExtra(
		// RESULT_CHOICEAREA, hasChoiceArea));
		// }
		// finish();
		// }
		// });
		areaAdapter = new CommonAdapter<Area>(getContext(),
				R.layout.item_areachoice) {
			@Override
			public void convert(ViewHolder helper, Area item) {
				if (null != item) {
					String name = "";
					if (isChoiceCity) {
						helper.getView(R.id.iv_arr).setVisibility(View.GONE);
						name = item.getCityName();
						if (null != hasChoiceArea
								&& item.getCityName().equals(
										hasChoiceArea.getCityName())) {
							helper.setText(R.id.area_name_choice, "已选择");
						} else {
							helper.setText(R.id.area_name_choice, "");
						}
					} else {
						helper.getView(R.id.iv_arr).setVisibility(View.VISIBLE);
						name = item.getProvinceName();
						if (null != hasChoiceArea
								&& item.getProvinceName().equals(
										hasChoiceArea.getProvinceName())) {
							helper.setText(R.id.area_name_choice, "已选择");
						} else {
							helper.setText(R.id.area_name_choice, "");
						}
					}
					helper.setText(R.id.area_name, name);
				}
			}
		};
		DBhelper dBhelper = new DBhelper(getContext());
		if (!isChoiceCity) {
			areas = dBhelper.getProvince();
		} else {
			areas = dBhelper.getCity(hasChoiceArea);
			choiceCity.setText(hasChoiceArea.getProvinceName());
		}
		areaAdapter.setdata(areas);
		recy.setAdapter(areaAdapter);
		recy.setOnItemClickListener(listener);
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == REQUEST_CHOICEAREA && arg1 == RESULT_OK && arg2 != null) {
			hasChoiceArea = (Area) arg2.getSerializableExtra(RESULT_CHOICEAREA);
			// areaAdapter.notifyDataSetChanged();
			LogUtil.i("省市结果==" + hasChoiceArea);
			if (null != hasChoiceArea) {
				setResult(RESULT_OK,
						new Intent().putExtra(RESULT_CHOICEAREA, hasChoiceArea));
			}
			finish();
		}
	}

	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (null != areas && position < areas.size()) {
				if (null == hasChoiceArea
						|| (!isChoiceCity && !areas.get(position)
								.getProvinceName()
								.equals(hasChoiceArea.getProvinceName()))
						|| (isChoiceCity && !areas.get(position).getCityName()
								.equals(hasChoiceArea.getCityName()))) {
					hasChoiceArea = areas.get(position);
					areaAdapter.notifyDataSetChanged();
				}
				if (!isChoiceCity) {
					Intent intent = new Intent(getContext(),
							Act_AreaChoiceProVince.class);
					Bundle bundle = new Bundle();
					bundle.putBoolean("choiceCity", true);
					if (null != hasChoiceArea) {
						bundle.putSerializable("hasChoiceArea", hasChoiceArea);
					}
					intent.putExtra("hasChoiceArea", bundle);
					startActivityForResult(intent, REQUEST_CHOICEAREA);
				} else if (isChoiceCity) {
					LogUtil.i("点击了 区域~~" + hasChoiceArea);
					Intent choiceAreaIntent = new Intent();
					choiceAreaIntent.putExtra(RESULT_CHOICEAREA, hasChoiceArea);
					setResult(RESULT_OK, choiceAreaIntent);
					finish();
				}
			}
		}
	};

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// if (null != hasChoiceArea) {
	// setResult(RESULT_OK,
	// new Intent().putExtra(RESULT_CHOICEAREA, hasChoiceArea));
	// }
	// }
	// return super.onKeyDown(keyCode, event);
	// }

}
