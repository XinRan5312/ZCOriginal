package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetUserLeaderBookletResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_StringListCommon;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 选择列表——领投人认证二级选择页
 * 
 * @author zp
 *
 *         2016年09月05日
 */
public class Activity_SelectListCommon extends Activity_Base implements
		OnItemClickListener {
	@ViewInject(R.id.lv_list)
	private PullToRefreshListView lv_list;

	private Adapter_StringListCommon adapter_list;
	private List<String> list = new ArrayList<String>();

	// private String[] arr_Com = { "合伙", "私企", "国企", "集体" };// 公司类型
	private String[] arr_Edu = { "小学", "初中", "高中", "大专", "本科", "研究生", "博士",
			"其他" };// 教育背景
	private String[] arr_preference = { "种子期", "天使期", "Pre-A轮", "A轮", "B轮",
			"C轮", "D轮", "并购" };
	private String[] arr_time = { "一年", "二年", "三年", "四年", "五年", "大于五年", "无" };
	private String[] arr_prjNum = { "有", "无" };

	private String flag;
	private GetUserLeaderBookletResponse model_select;// 选择项

	List<String> list_com = new ArrayList<String>();// 公司类型名称
	List<String> list_com_code = new ArrayList<String>();// 公司类型编码

	List<String> list_edu = new ArrayList<String>();
	List<String> list_edu_code = new ArrayList<String>();

	List<String> list_pre = new ArrayList<String>();// 偏好
	List<String> list_pre_code = new ArrayList<String>();

	List<String> list_time = new ArrayList<String>();// 投资时间
	List<String> list_time_code = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_list_provice);
		x.view().inject(this);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		if (getIntent() != null
				&& !StringUtils.isEmpty(getIntent().getStringExtra("flag"))) {
			setTitleText(getIntent().getStringExtra("flag"));

			flag = getIntent().getStringExtra("flag");
			model_select = (GetUserLeaderBookletResponse) getIntent()
					.getSerializableExtra("model");
			if (flag.equals(getResources().getString(R.string.title_com))) {
				initListCom();
			}
			if (flag.equals(getResources().getString(R.string.title_edu))) {
				initListEdu();
			}
			if (flag.equals(getResources().getString(R.string.title_preference))) {
				initListPre();
			}

			if (flag.equals(getResources().getString(R.string.title_time))) {
				initListTime();
			}
			if (flag.equals(getResources().getString(
					R.string.title_hadInvestPrjNum))) {
				initListPrjNum();
			}
			if (flag.equals(getResources().getString(R.string.title_nextPrj))) {
				initListPrjNum();
			}
			if (flag.equals(getResources()
					.getString(R.string.title_succExitPrj))) {
				initListPrjNum();
			}

		}
		setTitleBack();
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {

	}

	/**
	 * 1、选择公司类型
	 */
	public void initListCom() {
		if (model_select != null && model_select.getCompanyTypList() != null) {
			for (int i = 0; i < model_select.getCompanyTypList().size(); i++) {
				list_com.add(model_select.getCompanyTypList().get(i)
						.getCdisplay());
				list_com_code.add(model_select.getCompanyTypList().get(i)
						.getCcode());
			}

			adapter_list = new Adapter_StringListCommon(this);
			// adapter_list.setData(Arrays.asList(arr_Com));
			adapter_list.setData(list_com);
			lv_list.setAdapter(adapter_list);

			lv_list.setMode(Mode.DISABLED);
			lv_list.setOnItemClickListener(this);
		} else {
			LogUtil.i("model_select or list is null");
		}
	}

	/**
	 * 2、选择教育
	 */
	public void initListEdu() {
		if (model_select != null && model_select.getEducationList() != null) {
			for (int i = 0; i < model_select.getEducationList().size(); i++) {
				list_edu.add(model_select.getEducationList().get(i)
						.getEdisplay());
				list_edu_code.add(model_select.getEducationList().get(i)
						.getEcode());
			}
			adapter_list = new Adapter_StringListCommon(this);
			adapter_list.setData(list_edu);
			lv_list.setAdapter(adapter_list);
		}
		lv_list.setMode(Mode.DISABLED);
		lv_list.setOnItemClickListener(this);
	}

	/**
	 * 3、选择偏好
	 */
	public void initListPre() {
		if (model_select != null && model_select.getStockPhaseList() != null) {
			for (int i = 0; i < model_select.getStockPhaseList().size(); i++) {
				list_pre.add(model_select.getStockPhaseList().get(i)
						.getSdisplay());
				list_pre_code.add(model_select.getStockPhaseList().get(i)
						.getScode());
			}
			adapter_list = new Adapter_StringListCommon(this);
			adapter_list.setData(list_pre);
			lv_list.setAdapter(adapter_list);

			lv_list.setMode(Mode.DISABLED);
			lv_list.setOnItemClickListener(this);
		}
	}

	/**
	 * 4、选择时间
	 */
	public void initListTime() {
		if (model_select != null && model_select.getInvestTimeList() != null) {
			for (int i = 0; i < model_select.getInvestTimeList().size(); i++) {
				list_time.add(model_select.getInvestTimeList().get(i)
						.getDisplay());
				list_time_code.add(model_select.getInvestTimeList().get(i)
						.getCode());
			}
			adapter_list = new Adapter_StringListCommon(this);
			adapter_list.setData(list_time);
			lv_list.setAdapter(adapter_list);

			lv_list.setMode(Mode.DISABLED);
			lv_list.setOnItemClickListener(this);
		}
	}

	/**
	 * 5、选择完成项目数
	 */
	public void initListPrjNum() {
		adapter_list = new Adapter_StringListCommon(this);
		adapter_list.setData(Arrays.asList(arr_prjNum));
		lv_list.setAdapter(adapter_list);

		lv_list.setMode(Mode.DISABLED);
		lv_list.setOnItemClickListener(this);
	}

	/**
	 * 6、选择下一轮
	 */
	public void initListNextRond() {
		adapter_list = new Adapter_StringListCommon(this);
		adapter_list.setData(Arrays.asList(arr_prjNum));
		lv_list.setAdapter(adapter_list);

		lv_list.setMode(Mode.DISABLED);
		lv_list.setOnItemClickListener(this);
	}

	/**
	 * 7、选择成功退出
	 */
	public void initListSucceExit() {
		adapter_list = new Adapter_StringListCommon(this);
		adapter_list.setData(Arrays.asList(arr_prjNum));
		lv_list.setAdapter(adapter_list);

		lv_list.setMode(Mode.DISABLED);
		lv_list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		if (flag.equals(getResources().getString(R.string.title_com))) {
			intent.putExtra("data", list_com.get(position - 1));
			intent.putExtra("code", list_com_code.get(position - 1));
		}
		if (flag.equals(getResources().getString(R.string.title_edu))) {
			intent.putExtra("data", list_edu.get(position - 1));
			intent.putExtra("code", list_edu_code.get(position - 1));
		}
		if (flag.equals(getResources().getString(R.string.title_preference))) {
			intent.putExtra("data", list_pre.get(position - 1));
			intent.putExtra("code", list_pre_code.get(position - 1));
		}
		if (flag.equals(getResources().getString(R.string.title_time))) {
			intent.putExtra("data", list_time.get(position - 1));
			intent.putExtra("code", list_time_code.get(position - 1));
		}
		if (flag.equals(getResources()
				.getString(R.string.title_hadInvestPrjNum))) {
			intent.putExtra("data", arr_prjNum[position - 1]);
		}
		if (flag.equals(getResources().getString(R.string.title_nextPrj))) {
			intent.putExtra("data", arr_prjNum[position - 1]);
		}
		if (flag.equals(getResources().getString(R.string.title_succExitPrj))) {
			intent.putExtra("data", arr_prjNum[position - 1]);
		}

		setResult(RESULT_OK, intent);
		finish();
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

}
