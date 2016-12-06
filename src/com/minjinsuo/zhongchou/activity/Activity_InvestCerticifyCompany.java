package com.minjinsuo.zhongchou.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.CheckUserRightRequest;
import net.zkbc.p2p.fep.message.protocol.CheckUserRightResponse;
import net.zkbc.p2p.fep.message.protocol.GetUserLeaderBookletRequest;
import net.zkbc.p2p.fep.message.protocol.GetUserLeaderBookletResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitApplyFollowerResponse;
import net.zkbc.p2p.fep.message.protocol.SubmitBase64FileRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitBase64FileResponse;
import net.zkbc.p2p.fep.message.protocol.SubmitCompanyLeaderRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCompanyLeaderResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Area;
import com.minjinsuo.zhongchou.model.Model_SelectMore;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CameraManager;
import com.minjinsuo.zhongchou.utils.CameraManager.OnHeadImageManagerFinish;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.CircleImageView;
import com.minjinsuo.zhongchou.widget.ClearableEditText;

/**
 * 企业投资认证
 * 
 * @author zp
 *
 */
public class Activity_InvestCerticifyCompany extends Activity_Base implements
		OnHeadImageManagerFinish {
	@ViewInject(R.id.ll_head)
	private LinearLayout ll_head;
	@ViewInject(R.id.invest_area)
	private LinearLayout invest_area;// 投资领域
	@ViewInject(R.id.cir_head)
	private CircleImageView cir_head;

	@ViewInject(R.id.name)
	private TextView name;
	@ViewInject(R.id.idNo)
	private TextView idNo;
	@ViewInject(R.id.produceSelf)
	private ClearableEditText produceSelf;// 自我介绍
	@ViewInject(R.id.tv_certicify_zige)
	private TextView tv_certicify_zige;
	@ViewInject(R.id.tv_investArea)
	private TextView tv_investArea;
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	// 下面是领头认证资料布局相关控件
	@ViewInject(R.id.com_add)
	private LinearLayout com_add;// 公司所在地
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

	private CameraManager mCameraManager;
	private boolean isHadSubHeadPic;// 是否上传头像照片
	private static String HEADCODE = "headcode";
	private static String HEADADD = "headADD";
	private static String CERTIFYCODE = "certifycode";
	private int flag = 0;
	private List<Model_SelectMore> list_data = new ArrayList<Model_SelectMore>();

	// 下面是领头认证相关变量
	private final int REQUEST_CHOICEAREA = 120;
	private Area hasChoiceArea;
	// 省市代码
	private String codeProv = "";
	private String codeCity = "";
	private String code_com = "";// 公司类型表示码，需要作为参数上传
	private String code_edu = "";// 教育类型表示码，需要作为参数上传
	private String code_pre = "";// 偏好表示码，需要作为参数上传
	private String code_time = "";// 投资时间表示码，需要作为参数上传
	private GetUserLeaderBookletResponse model_select;// 选择项
	SubmitCompanyLeaderRequest request_params = new SubmitCompanyLeaderRequest();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_invest_cirtify_company);
		x.view().inject(this);
		initView();
		initData();
		initListener();

		// checkUserRight();
	}

	@Override
	protected void initView() {
		setTitleText("企业投资认证");
		setTitleBack();

		name.setText(ZCApplication.getInstance().getUserInfo().getRealname());
		if (!StringUtils.isEmpty(ZCApplication.getInstance().getUserInfo()
				.getIdcardnumber()))
			idNo.setText(StringUtils.blurNumForNM(6, 4, ZCApplication
					.getInstance().getUserInfo().getIdcardnumber()));
	}

	@Override
	protected void initData() {
		// 上传头像处理
		mCameraManager = new CameraManager(this);

		// 如果之前上传过头像、认证资料则加载
		if (!StringUtils
				.isEmpty(SharedPreferUtils.getValue(
						Activity_InvestCerticifyCompany.this, "temp_com",
						HEADCODE, ""))) {
			x.image().bind(
					cir_head,
					SharedPreferUtils.getValue(
							Activity_InvestCerticifyCompany.this, "temp_com",
							HEADADD, ""), imageOptions);
			isHadSubHeadPic = true;
			request_params.setHeadPhotoId(SharedPreferUtils.getValue(
					Activity_InvestCerticifyCompany.this, "temp_com", HEADCODE,
					""));
		}

	}

	@Override
	protected void initListener() {

	}

	@Event({ R.id.ll_head, R.id.invest_area, R.id.btn_ok, R.id.ll_ziliao,
			R.id.btn_uploadCertify, R.id.com_add, R.id.ll_comType, R.id.ll_edu,
			R.id.ll_preference, R.id.ll_time, R.id.ll_hadPrjNum,
			R.id.ll_nextRond, R.id.ll_succExit })
	private void OnClick(View view) {
		Intent intent = null;
		Bundle bundle = null;
		switch (view.getId()) {
		case R.id.ll_head:// 头像
			flag = 0;
			mCameraManager.showAvatarDialog();
			// mCameraManager.showOption();
			break;
		case R.id.invest_area:// 投资领域
			intent = new Intent(Activity_InvestCerticifyCompany.this,
					Avtivity_InvestArea.class);
			startActivityForResult(intent, AppContants.ACT_REQUEST_CODE);
			break;
		case R.id.btn_uploadCertify:
		case R.id.ll_ziliao:// 认证资料
			flag = 1;
			mCameraManager.showAvatarDialog();
			break;

		case R.id.com_add:
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

		case R.id.btn_ok:// 提交

			submitApplyFollower();// 满足条件可以提交申请
			break;
		case R.id.btn_certicify_bottom:
			break;
		default:
			break;
		}
	}

	@Override
	public void onHeadImgFinish(Bitmap bitmap, String imagePath) {
		if (imagePath.contains("/")) {
			imagePath = imagePath.substring(imagePath.lastIndexOf("/") + 1);
		}
		LogUtil.d("图片名称==" + imagePath);
		subPicRequest(bitmap, imagePath, flag);
	}

	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		mCameraManager.onActivityResult(arg0, arg2);// 触发获取返回值

		if (arg0 == AppContants.ACT_REQUEST_CODE && arg1 == RESULT_OK) {
			String data = arg2.getStringExtra("data");
			if (!StringUtils.isEmpty(data)) {
				request_params.setPrjTrades(data);
				tv_investArea.setText("已选择");
			} else {
				tv_investArea.setText("");
			}
		}

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
				request_params.setLikeTrades(code_pre);
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
					request_params.setInvestCnt("1");// 已投项目数 1有 0无
					ll_prjName.setVisibility(View.VISIBLE);
					line_prjName.setVisibility(View.VISIBLE);
				} else {
					request_params.setInvestCnt("0");// 已投项目数 1有 0无
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
					request_params.setPrjInNext("1");
				} else {
					request_params.setPrjInNext("0");
				}
			}
			if (arg0 == 007) {// 成功退出项目
				String result = arg2.getStringExtra("data");
				if (!StringUtils.isEmpty(result)) {
					tv_exitPrj.setText(result);
				}

				if (tv_exitPrj.getText().toString().equals("有")) {
					request_params.setPrjsucc("1");
					ll_ifShow_successPrj.setVisibility(View.VISIBLE);
				} else {
					request_params.setPrjsucc("0");
					ll_ifShow_successPrj.setVisibility(View.GONE);
				}
			}

		}
	}

	/**
	 * 上传头像、认证资料
	 * 
	 * @param bitmap
	 * @param imgName
	 *            :图片名称
	 * @param flag
	 *            :0-上传头像；1-上传认证资料
	 */
	public void subPicRequest(final Bitmap bitmap, String imgName,
			final int flag) {
		if (bitmap == null) {
			LogUtil.i("bitmap is null");
			return;
		}
		byte[] data = mCameraManager.createImageData(bitmap);
		byte[] base64 = Base64.encode(data, Base64.DEFAULT);
		String uploadfile = new String(base64);
		SubmitBase64FileRequest request = new SubmitBase64FileRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setFileStr(uploadfile);
		request.setFileName(imgName);
		NetWorkRequestManager.sendRequestPost(
				Activity_InvestCerticifyCompany.this, true, request,
				SubmitBase64FileResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						SubmitBase64FileResponse info = (SubmitBase64FileResponse) response;

						if (flag == 0) {// 上传头像
							x.image().bind(cir_head, info.getFileLoadAddr(),
									imageOptions);
							isHadSubHeadPic = true;
							request_params.setHeadPhotoId(info.getFileId());
							// 保存下来，避免点击返回键后丢失图片id
							SharedPreferUtils.putValue(
									Activity_InvestCerticifyCompany.this,
									"temp_com", HEADCODE, info.getFileId());
							SharedPreferUtils.putValue(
									Activity_InvestCerticifyCompany.this,
									"temp_com", HEADADD, info.getFileLoadAddr());
						}
						DialogUtils.dismisLoading();
					}

					@Override
					public void onFailure(ResponseSupport response) {
						DialogUtils.dismisLoading();
					}

					@Override
					public void failure() {
						DialogUtils.dismisLoading();
					}
				});
	}

	/**
	 * 获取公司类型、教育年限等选择项列表
	 */
	public void getUserLeaderBooklet(boolean isShow) {
		GetUserLeaderBookletRequest request = new GetUserLeaderBookletRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetUserLeaderBookletResponse.class, this);
	}

	/**
	 * 提交认证申请
	 */
	public void submitApplyFollower() {

		if (!isHadSubHeadPic) {
			ToastUtil.showToast(Activity_InvestCerticifyCompany.this, "请先上传头像");
			return;
		}
		if (StringUtils.isEmpty(request_params.getPrjTrades())) {
			ToastUtil.showToast(this, "请选择投资领域");
			return;
		}

		if (StringUtils.isEmpty(produceSelf.getText().toString().trim())) {
			ToastUtil
					.showToast(Activity_InvestCerticifyCompany.this, "请填写自我介绍");
			return;
		} else {
			request_params
					.setSelfIntro(produceSelf.getText().toString().trim());
		}

		// 二、下面校验 原来的领头人认证相关条件
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
		if (StringUtils.isEmpty(et_comName.getText().toString())) {
			ToastUtil.showToast(this, "请输入公司名称");
			return;
		}
		if (StringUtils.isEmpty(hasChoiceAreaTxt.getText().toString())
				|| hasChoiceAreaTxt.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择公司所在地");
			return;
		}
		if (StringUtils.isEmpty(tv_comType.getText().toString())
				|| tv_comType.getText().toString().trim().equals("请选择")) {
			ToastUtil.showToast(this, "请选择公司类型");
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

		request_params.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		// zpqa??下面是领头人认证相关条件
		request_params.setCodProv(codeProv);
		request_params.setCodCity(codeCity);
		request_params.setCodCompany(code_com);
		request_params.setCompanyNam(et_comName.getText().toString().trim());
		request_params.setJobTitle(et_zhiwei.getText().toString().trim());
		request_params.setEducation(code_edu);
		request_params.setWorkHis(et_workExp.getText().toString().trim());
		request_params.setLikeTrades(code_pre);
		request_params.setInvestTime(code_time);

		NetWorkRequestManager.sendRequestPost(this, true, request_params,
				SubmitCompanyLeaderResponse.class, this);
	}

	/**
	 * 检测用户当前角色状态
	 */
	public void checkUserRight() {
		CheckUserRightRequest request = new CheckUserRightRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				CheckUserRightResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getUserLeaderBooklet":// 获取选择项列表
			DialogUtils.dismisLoading();
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
		case "submitCompanyLeader":// 提交企业认证
			DialogUtils.dismisLoading();
			SubmitApplyFollowerResponse model_last = (SubmitApplyFollowerResponse) response;
			if (model_last.getResult().equals("success")) {
				// 清空保存的头像和认证资料图片id
				getSharedPreferences("temp_com", 0).edit().clear().commit();
				new DialogUtils()
						.createOneBtnDiolog(
								Activity_InvestCerticifyCompany.this,
								"提交认证成功，等待审核…").findViewById(R.id.btn_confirm)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								ZCApplication.getInstance().isNeedRefresh = true;
								finish();
							}
						});
			}
			break;
		case "checkUserRight":
			CheckUserRightResponse model = (CheckUserRightResponse) response;
			if (model.getIsFollower().equals("0")) {
				getUserLeaderBooklet(false);
				return;
			}

			DialogUtils.dismisLoading();
			String result = "";
			if (model.getIsFollower().equals("1")) {
				result = "您已是平台领投人";
			}

			if (model.getIsFollower().equals("2")) {
				result = "您已被禁止申请平台领投人";
			}
			if (model.getIsFollower().equals("3")) {
				result = "您的平台领投人申请正在审核中";
			}

			LogUtil.i(result);
			final Dialog dialog = DialogUtils.createOneBtnDiolog(
					Activity_InvestCerticifyCompany.this, result);
			dialog.setCancelable(false);
			dialog.setCanceledOnTouchOutside(false);
			dialog.findViewById(R.id.btn_confirm).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							finish();
						}
					});

			break;

		default:
			break;
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

}
