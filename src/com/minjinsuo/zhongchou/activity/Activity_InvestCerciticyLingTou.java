package com.minjinsuo.zhongchou.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetUserLeaderBookletRequest;
import net.zkbc.p2p.fep.message.protocol.GetUserLeaderBookletResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitApplyUserLeaderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitApplyUserLeaderResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Area;
import com.minjinsuo.zhongchou.model.Model_SelectMore;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.widget.ClearableEditText;

/**
 * 领头人认证（第二步）
 * 
 * @author zp
 *
 *         2016年7月5日
 */
public class Activity_InvestCerciticyLingTou extends Activity_Base {
	@ViewInject(R.id.invest_area)
	private LinearLayout invest_area;// 投资领域
	@ViewInject(R.id.ll_comType)
	private LinearLayout ll_comType;// 公司类型
	@ViewInject(R.id.ll_edu)
	private LinearLayout ll_edu;// 教育背景
	@ViewInject(R.id.ll_preference)
	private LinearLayout ll_preference;// 偏好
	@ViewInject(R.id.ll_time)
	private LinearLayout ll_time;// 从事投资时间
	@ViewInject(R.id.ll_hadPrjNum)
	private LinearLayout ll_hadPrjNum;// 已投项目
	@ViewInject(R.id.ll_prjName)
	private LinearLayout ll_prjName;// 已投项目名称
	@ViewInject(R.id.ll_nextRond)
	private LinearLayout ll_nextRond;// 项目已到下轮
	@ViewInject(R.id.ll_succExit)
	private LinearLayout ll_succExit;// 成功退出项目

	@ViewInject(R.id.hasChoiceArea)
	private TextView hasChoiceAreaTxt;
	@ViewInject(R.id.tv_comType)
	private TextView tv_comType;
	@ViewInject(R.id.et_comName)
	private ClearableEditText et_comName;
	@ViewInject(R.id.et_zhiwei)
	private ClearableEditText et_zhiwei;// 职位、头衔
	@ViewInject(R.id.tv_edu)
	private TextView tv_edu;
	@ViewInject(R.id.et_workExp)
	private ClearableEditText et_workExp;// 工作经历
	@ViewInject(R.id.tv_preference)
	private TextView tv_preference;
	@ViewInject(R.id.tv_time)
	private TextView tv_time;
	@ViewInject(R.id.tv_prjNum)
	private TextView tv_prjNum;// 已投项目数
	@ViewInject(R.id.et_hadInvestPrjName)
	private TextView et_hadInvestPrjName;// 已投项目名称
	@ViewInject(R.id.tv_nextPrj)
	private TextView tv_nextPrj;
	@ViewInject(R.id.tv_exitPrj)
	private TextView tv_exitPrj;
	@ViewInject(R.id.et_succePrjName)
	private ClearableEditText et_succePrjName;
	@ViewInject(R.id.et_succPrjIntro)
	private ClearableEditText et_succPrjIntro;
	@ViewInject(R.id.line_prjName)
	private View line_prjName;
	@ViewInject(R.id.ll_ifShow_successPrj)
	private LinearLayout ll_ifShow_successPrj;// 根据是否有成功案例，判断是否显示成功案例名称和介绍布局
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	private final int REQUEST_CHOICEAREA = 120;
	private Area hasChoiceArea;
	// 省市代码
	private String codeProv = "";
	private String codeCity = "";
	SubmitApplyUserLeaderRequest request = new SubmitApplyUserLeaderRequest();
	private GetUserLeaderBookletResponse model_select;// 选择项

	private String code_com = "";// 公司类型表示码，需要作为参数上传
	private String code_edu = "";// 教育类型表示码，需要作为参数上传
	private String code_pre = "";// 偏好表示码，需要作为参数上传
	private String code_time = "";// 投资时间表示码，需要作为参数上传
	private List<Model_SelectMore> list_data = new ArrayList<Model_SelectMore>();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_certicify_lingtouren);
		x.view().inject(this);
		initView();
		initData();
		initListener();

		getUserLeaderBooklet();
	}

	@Override
	protected void initView() {
		setTitleText("领头人认证");
		setTitleBack();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	@Event({ R.id.invest_area, R.id.ll_comType, R.id.ll_edu,
			R.id.ll_preference, R.id.ll_time, R.id.ll_hadPrjNum,
			R.id.ll_nextRond, R.id.ll_succExit, R.id.btn_ok })
	private void OnClick(View view) {
		Intent intent = null;
		Bundle bundle = null;
		switch (view.getId()) {
		case R.id.invest_area:
			intent = new Intent(getContext(), Act_AreaChoiceProVince.class);
			bundle = new Bundle();
			bundle.putBoolean("choiceCity", false);
			if (null != hasChoiceArea) {
				bundle.putSerializable("hasChoiceArea", hasChoiceArea);
			}
			intent.putExtra("hasChoiceArea", bundle);
			startActivityForResult(intent, REQUEST_CHOICEAREA);
			break;
		case R.id.ll_comType:
			intent = new Intent(getContext(), Activity_SelectListCommon.class);
			intent.putExtra("flag", getResources()
					.getString(R.string.title_com));
			bundle = new Bundle();
			bundle.putSerializable("model", model_select);
			intent.putExtras(bundle);
			startActivityForResult(intent, 001);
			break;
		case R.id.ll_edu:
			intent = new Intent(getContext(), Activity_SelectListCommon.class);
			intent.putExtra("flag", getResources()
					.getString(R.string.title_edu));
			bundle = new Bundle();
			bundle.putSerializable("model", model_select);
			intent.putExtras(bundle);
			startActivityForResult(intent, 002);
			break;
		case R.id.ll_preference:// 偏好设置
			// intent = new Intent(getContext(),
			// Activity_SelectListCommon.class);
			// intent.putExtra("flag",
			// getResources().getString(R.string.title_preference));
			// bundle = new Bundle();
			// bundle.putSerializable("model", model_select);
			// intent.putExtras(bundle);
			// startActivityForResult(intent, 003);

			intent = new Intent(getContext(), Activity_SelectMore.class);
			Bundle bundle_pre = new Bundle();
			bundle_pre.putSerializable("list", (Serializable) list_data);
			intent.putExtras(bundle_pre);
			intent.putExtra(AppContants.FROM, "prefrence");
			startActivityForResult(intent, 003);
			break;
		case R.id.ll_time:
			intent = new Intent(getContext(), Activity_SelectListCommon.class);
			intent.putExtra("flag",
					getResources().getString(R.string.title_time));
			bundle = new Bundle();
			bundle.putSerializable("model", model_select);
			intent.putExtras(bundle);
			startActivityForResult(intent, 004);
			break;
		case R.id.ll_hadPrjNum:
			intent = new Intent(getContext(), Activity_SelectListCommon.class);
			intent.putExtra("flag",
					getResources().getString(R.string.title_hadInvestPrjNum));
			startActivityForResult(intent, 005);
			break;
		case R.id.ll_nextRond:
			intent = new Intent(getContext(), Activity_SelectListCommon.class);
			intent.putExtra("flag",
					getResources().getString(R.string.title_nextPrj));
			startActivityForResult(intent, 006);
			break;
		case R.id.ll_succExit:
			intent = new Intent(getContext(), Activity_SelectListCommon.class);
			intent.putExtra("flag",
					getResources().getString(R.string.title_succExitPrj));
			startActivityForResult(intent, 007);
			break;
		case R.id.btn_ok:
			submitApplyUserLeader();
			break;
		default:
			break;
		}
	}

	/**
	 * 提交领头人认证
	 */
	public void submitApplyUserLeader() {
		if (StringUtils.isEmpty(hasChoiceAreaTxt.getText().toString())
				|| hasChoiceAreaTxt.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择常住城市");
			return;
		}
		if (StringUtils.isEmpty(tv_comType.getText().toString())
				|| tv_comType.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择公司类型");
			return;
		}
		if (StringUtils.isEmpty(et_comName.getText().toString())) {
			ToastUtil.showToast(this, "请输入公司名称");
			return;
		}
		if (StringUtils.isEmpty(et_zhiwei.getText().toString())) {
			ToastUtil.showToast(this, "请输入职位/头衔");
			return;
		}
		if (StringUtils.isEmpty(tv_edu.getText().toString())
				|| tv_edu.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择教育背景");
			return;
		}
		if (StringUtils.isEmpty(et_workExp.getText().toString())) {
			ToastUtil.showToast(this, "请输入工作经历");
			return;
		}
		if (StringUtils.isEmpty(tv_preference.getText().toString())
				|| tv_preference.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择阶段偏好");
			return;
		}
		if (StringUtils.isEmpty(tv_time.getText().toString())
				|| tv_time.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择从事投资时间");
			return;
		}
		if (StringUtils.isEmpty(tv_prjNum.getText().toString())
				|| tv_prjNum.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择已投项目数");
			return;
		}
		if (tv_prjNum.getText().toString().trim().equals("有")
				&& StringUtils
						.isEmpty(et_hadInvestPrjName.getText().toString())) {
			ToastUtil.showToast(this, "请输入已投项目名称");
			return;
		}
		if (StringUtils.isEmpty(tv_nextPrj.getText().toString())
				|| tv_nextPrj.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择是否有项目已到下轮");
			return;
		}
		if (StringUtils.isEmpty(tv_exitPrj.getText().toString())
				|| tv_exitPrj.getText().toString().equals("请选择")) {
			ToastUtil.showToast(this, "请选择是否有成功退出项目");
			return;
		}
		// 当有成功案例的时候才显示成功案例名称和介绍输入框
		if (tv_exitPrj.getText().toString().equals("有")
				&& StringUtils.isEmpty(et_succePrjName.getText().toString())) {
			ToastUtil.showToast(this, "请输入成功案例名称");
			return;
		}
		if (tv_exitPrj.getText().toString().equals("有")
				&& StringUtils.isEmpty(et_succPrjIntro.getText().toString())) {
			ToastUtil.showToast(this, "请输入成功案例介绍");
			return;
		}

		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setCodProv(codeProv);
		request.setCodCity(codeCity);
		request.setCodCompany(code_com);
		request.setCompanyNam(et_comName.getText().toString().trim());
		request.setJobTitle(et_zhiwei.getText().toString().trim());
		request.setEducation(code_edu);
		request.setWorkHis(et_workExp.getText().toString().trim());
		request.setLikeTrades(code_pre);
		request.setInvestTime(code_time);

		NetWorkRequestManager.sendRequestPost(this, true, request,
				SubmitApplyUserLeaderResponse.class, this);
	}

	/**
	 * 获取公司类型、教育年限等选择项列表
	 */
	public void getUserLeaderBooklet() {
		GetUserLeaderBookletRequest request = new GetUserLeaderBookletRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetUserLeaderBookletResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getUserLeaderBooklet":// 获取选择项列表
			model_select = (GetUserLeaderBookletResponse) response;
			Model_SelectMore info = null;
			if (model_select != null
					&& model_select.getStockPhaseList() != null) {
				for (int i = 0; i < model_select.getStockPhaseList().size(); i++) {
					info = new Model_SelectMore();
					info.setId(model_select.getStockPhaseList().get(i)
							.getScode());
					info.setContent(model_select.getStockPhaseList().get(i)
							.getSdisplay());

					list_data.add(info);
				}
			}
			break;
		case "submitApplyUserLeader":// 提交认证
			SubmitApplyUserLeaderResponse model = (SubmitApplyUserLeaderResponse) response;
			if (model.getResult().equals("success")) {
				ToastUtil.showToast(Activity_InvestCerciticyLingTou.this,
						"提价成功，待审核");
				ZCApplication.getInstance().isNeedRefresh = true;
				finish();
			} else {
				if (!StringUtils.isEmpty(model.getMessage())) {
					new DialogUtils().createOneBtnDiolog(
							Activity_InvestCerciticyLingTou.this,
							model.getMessage());
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void failure() {

	}

	@Override
	public void onFailure(ResponseSupport response) {

	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (RESULT_OK == arg1) {
			if (REQUEST_CHOICEAREA == arg0) {
				// 选择省市地址
				hasChoiceArea = (Area) arg2.getSerializableExtra("choiceArea");
				String proName = hasChoiceArea.getProvinceName();
				String cityName = hasChoiceArea.getCityName();
				hasChoiceAreaTxt.setText((TextUtils.isEmpty(proName) ? ""
						: proName)
						+ (TextUtils.isEmpty(cityName) ? "" : cityName));
				codeProv = hasChoiceArea.getPcode();
				codeCity = hasChoiceArea.getCode();
				LogUtil.i("选择的城市==" + hasChoiceArea.toString());
			}

			if (arg0 == 001) {
				// 选择公司类型
				String result = arg2.getStringExtra("data");
				code_com = arg2.getStringExtra("code");
				if (!StringUtils.isEmpty(result)) {
					tv_comType.setText(result);
				}
			}
			if (arg0 == 002) {
				// 选择教育背景
				String result = arg2.getStringExtra("data");
				code_edu = arg2.getStringExtra("code");
				if (!StringUtils.isEmpty(result)) {
					tv_edu.setText(result);
				}
			}
			// if (arg0 == 003) {
			// // 选择 阶段偏好
			// String result = arg2.getStringExtra("data");
			// code_pre = arg2.getStringExtra("code");
			// if (!StringUtils.isEmpty(result)) {
			// tv_preference.setText(result);
			// }
			// }
			if (arg0 == 003) {
				ArrayList<String> strList = new ArrayList<String>();
				strList = arg2.getStringArrayListExtra("strArr_id");
				if (strList != null && strList.size() != 0) {
					code_pre = strList.get(0);
					for (int i = 1; i < strList.size(); i++) {
						code_pre += "," + strList.get(i);
					}
				}
				LogUtil.i("返回=" + code_pre);
				request.setLikeTrades(code_pre);
				tv_preference.setText("已选择");
			}

			if (arg0 == 004) {
				// 选择 从事时间
				String result = arg2.getStringExtra("data");
				code_time = arg2.getStringExtra("code");
				if (!StringUtils.isEmpty(result)) {
					tv_time.setText(result);
				}
			}
			if (arg0 == 005) {
				// 选择 是否有完成项目
				String result = arg2.getStringExtra("data");
				if (!StringUtils.isEmpty(result)) {
					tv_prjNum.setText(result);
				}

				if (tv_prjNum.getText().toString().trim().equals("有")) {
					request.setInvestCnt("1");// 已投项目数 1有 0无
					ll_prjName.setVisibility(View.VISIBLE);
					line_prjName.setVisibility(View.VISIBLE);
				} else {
					request.setInvestCnt("0");// 已投项目数 1有 0无
					ll_prjName.setVisibility(View.GONE);
					line_prjName.setVisibility(View.GONE);
				}
			}
			if (arg0 == 006) {
				// 选择 下轮
				String result = arg2.getStringExtra("data");
				if (!StringUtils.isEmpty(result)) {
					tv_nextPrj.setText(result);
				}

				if (tv_nextPrj.getText().toString().trim().equals("有")) {
					request.setPrjInNext("1");
				} else {
					request.setPrjInNext("0");
				}
			}
			if (arg0 == 007) {// 成功退出项目
				String result = arg2.getStringExtra("data");
				if (!StringUtils.isEmpty(result)) {
					tv_exitPrj.setText(result);
				}

				if (tv_exitPrj.getText().toString().equals("有")) {
					request.setPrjsucc("1");
					ll_ifShow_successPrj.setVisibility(View.VISIBLE);
				} else {
					request.setPrjsucc("0");
					ll_ifShow_successPrj.setVisibility(View.GONE);
				}
			}

		}
	}
}
