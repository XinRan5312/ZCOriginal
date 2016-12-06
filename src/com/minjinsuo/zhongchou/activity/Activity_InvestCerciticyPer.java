package com.minjinsuo.zhongchou.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeRequest;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeResponse;
import net.zkbc.p2p.fep.message.protocol.GetBookletByTypeResponse.ElementBookletList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitApplyFollowerRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitApplyFollowerResponse;
import net.zkbc.p2p.fep.message.protocol.SubmitBase64FileRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitBase64FileResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
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
import com.minjinsuo.zhongchou.widget.ActionSheet;
import com.minjinsuo.zhongchou.widget.ActionSheet.ActionSheetListener;
import com.minjinsuo.zhongchou.widget.ClearableEditText;

/**
 * 普通投资人认证（第一步）
 * 
 * @author zp
 *
 *         2016年7月5日
 */
public class Activity_InvestCerciticyPer extends Activity_Base implements
		OnHeadImageManagerFinish {
	@ViewInject(R.id.ll_head)
	private LinearLayout ll_head;
	@ViewInject(R.id.invest_area)
	private LinearLayout invest_area;// 投资领域
	@ViewInject(R.id.ll_certicify_zige)
	private LinearLayout ll_certicify_zige;// 认证资格
	@ViewInject(R.id.ll_ziliao)
	private LinearLayout ll_ziliao;// 认证资料
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
	@ViewInject(R.id.btn_uploadCertify)
	private Button btn_uploadCertify;
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	private CameraManager mCameraManager;
	private SubmitApplyFollowerRequest request_subLast = new SubmitApplyFollowerRequest();
	private boolean isHadSubHeadPic;// 是否上传头像照片
	private boolean isHadSubCertifyPic;// 是否上传认证资料照片
	private static String HEADCODE = "headcode";
	private static String HEADADD = "headADD";
	private static String CERTIFYCODE = "certifycode";
	private int flag = 0;
	private List<Model_SelectMore> list_data = new ArrayList<Model_SelectMore>();
	private String strArr_id = "";// 选择的投资资格字符串数组
	private String[] actionsheetData;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_invest_certicify_per);
		x.view().inject(this);
		initView();
		initData();
		initListener();

		getBookerType();
	}

	@Override
	protected void initView() {
		setTitleText("投资人认证");
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
		if (!StringUtils.isEmpty(SharedPreferUtils.getValue(
				Activity_InvestCerciticyPer.this, "temp", HEADCODE, ""))) {
			x.image().bind(
					cir_head,
					SharedPreferUtils.getValue(
							Activity_InvestCerciticyPer.this, "temp", HEADADD,
							""), imageOptions);
			isHadSubHeadPic = true;
			request_subLast.setHeadPhotoId(SharedPreferUtils.getValue(
					Activity_InvestCerciticyPer.this, "temp", HEADCODE, ""));
		}
		if (!StringUtils.isEmpty(SharedPreferUtils.getValue(
				Activity_InvestCerciticyPer.this, "temp", CERTIFYCODE, ""))) {

			isHadSubCertifyPic = true;
			request_subLast.setInfoFileIds(SharedPreferUtils.getValue(
					Activity_InvestCerciticyPer.this, "temp", CERTIFYCODE, ""));
			btn_uploadCertify.setText("已上传");
		}

	}

	@Override
	protected void initListener() {

	}

	@Event({ R.id.ll_head, R.id.invest_area, R.id.ll_certicify_zige,
			R.id.btn_ok, R.id.ll_ziliao, R.id.btn_uploadCertify })
	private void OnClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.ll_head:// 头像
			flag = 0;
			mCameraManager.showAvatarDialog();
			// mCameraManager.showOption();
			break;
		case R.id.invest_area:// 投资领域
			intent = new Intent(Activity_InvestCerciticyPer.this,
					Avtivity_InvestArea.class);
			startActivityForResult(intent, AppContants.ACT_REQUEST_CODE);
			break;
		case R.id.ll_certicify_zige:// 认证资格
			// zpqa??下面是多选样式跳转代码，现在又改为了单选
			// intent = new Intent(Activity_InvestCerciticyPer.this,
			// Activity_SelectMore.class);
			// Bundle bundle = new Bundle();
			// bundle.putSerializable("list", (Serializable) list_data);
			// intent.putExtras(bundle);
			// startActivityForResult(intent, 002);

			// 下面这种方式为单选
			showOption("取消", sheetListener, actionsheetData);
			break;
		case R.id.btn_uploadCertify:
		case R.id.ll_ziliao:// 认证资料
			flag = 1;
			mCameraManager.showAvatarDialog();
			break;
		case R.id.btn_ok:// 提交

			if (!isHadSubHeadPic) {
				ToastUtil.showToast(Activity_InvestCerciticyPer.this, "请先上传头像");
				return;
			}
			if (!isHadSubCertifyPic) {
				ToastUtil.showToast(Activity_InvestCerciticyPer.this,
						"请先上传认证资料");
				return;
			}

			if (StringUtils.isEmpty(tv_certicify_zige.getText().toString()
					.trim())
					|| tv_certicify_zige.getText().toString().trim()
							.equals("请选择")) {
				ToastUtil
						.showToast(Activity_InvestCerciticyPer.this, "请选择投资资格");
				return;
			} 

			if (StringUtils.isEmpty(produceSelf.getText().toString().trim())) {
				ToastUtil
						.showToast(Activity_InvestCerciticyPer.this, "请填写自我介绍");
				return;
			} else {
				request_subLast.setSelfIntro(produceSelf.getText().toString()
						.trim());
			}

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
		/**
		 * 此处应该加入上传图片的接口，获取到图片路径后再加载到imageView中 。通常上传的图片要base64编码后上传
		 */
		// cir_head.setImageBitmap(bitmap);

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
				request_subLast.setPrjTrades(data);
				tv_investArea.setText("已选择");
			} else {
				tv_investArea.setText("");
			}
		}

		if (arg0 == 002 && arg1 == RESULT_OK) {

			ArrayList<String> strList = new ArrayList<String>();
			strList = arg2.getStringArrayListExtra("strArr_id");
			if (strList != null && strList.size() != 0) {
				strArr_id = strList.get(0);
				for (int i = 1; i < strList.size(); i++) {
					strArr_id += "," + strList.get(i);
				}
			}
			LogUtil.i("返回=" + strArr_id);
			request_subLast.setAuthRight(strArr_id);
			tv_certicify_zige.setText("已选择");
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
		NetWorkRequestManager.sendRequestPost(Activity_InvestCerciticyPer.this,
				true, request, SubmitBase64FileResponse.class,
				new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						SubmitBase64FileResponse info = (SubmitBase64FileResponse) response;

						if (flag == 0) {// 上传头像
							x.image().bind(cir_head, info.getFileLoadAddr(),
									imageOptions);
							isHadSubHeadPic = true;
							request_subLast.setHeadPhotoId(info.getFileId());
							// 保存下来，避免点击返回键后丢失图片id
							SharedPreferUtils.putValue(
									Activity_InvestCerciticyPer.this, "temp",
									HEADCODE, info.getFileId());
							SharedPreferUtils.putValue(
									Activity_InvestCerciticyPer.this, "temp",
									HEADADD, info.getFileLoadAddr());
						} else {
							// 上传认证资料
							isHadSubCertifyPic = true;
							request_subLast.setInfoFileIds(info.getFileId());
							btn_uploadCertify.setText("已上传");
							SharedPreferUtils.putValue(
									Activity_InvestCerciticyPer.this, "temp",
									CERTIFYCODE, info.getFileId());
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
	 * 获取认证资格接口
	 */
	public void getBookerType() {
		GetBookletByTypeRequest request = new GetBookletByTypeRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setType("authRightTyp");
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetBookletByTypeResponse.class, this);
	}

	/**
	 * 提交认证申请
	 */
	public void submitApplyFollower() {
		if (StringUtils.isEmpty(request_subLast.getPrjTrades())) {
			ToastUtil.showToast(this, "请选择投资领域");
			return;
		}
		request_subLast.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		// request_subLast.setPrjTrades("02.01");// 在onActivityResult中赋值
		NetWorkRequestManager.sendRequestPost(this, true, request_subLast,
				SubmitApplyFollowerResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		// case "getBookletByType":// 获取认证资格选择项
		// GetBookletByTypeResponse model = (GetBookletByTypeResponse) response;
		// Model_SelectMore info = null;
		// if (model != null && model.getBookletList() != null) {
		// for (int i = 0; i < model.getBookletList().size(); i++) {
		// info = new Model_SelectMore();
		// info.setId(model.getBookletList().get(i).getCode());
		// info.setContent(model.getBookletList().get(i).getDisplay());
		//
		// list_data.add(info);
		// }
		// }
		//
		// break;
		case "getBookletByType":// 获取认证资格选择项
			GetBookletByTypeResponse model = (GetBookletByTypeResponse) response;
			if (model != null && model.getBookletList() != null) {
				actionsheetData = new String[model.getBookletList().size()];

				for (int i = 0; i < model.getBookletList().size(); i++) {
					actionsheetData[i] = model.getBookletList().get(i)
							.getDisplay();
				}
			}
			break;
		case "submitApplyFollower":// 提交认证
			SubmitApplyFollowerResponse model_last = (SubmitApplyFollowerResponse) response;
			if (model_last.getResult().equals("success")) {
				// 清空保存的头像和认证资料图片id
				getSharedPreferences("temp", 0).edit().clear().commit();
				new DialogUtils()
						.createOneBtnDiolog(Activity_InvestCerciticyPer.this,
								"提交认证成功").findViewById(R.id.btn_confirm)
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								ZCApplication.getInstance().isNeedRefresh = true;
								finish();
							}
						});
			}
			break;

		default:
			break;
		}

	}

	@Override
	public void failure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	/**
	 * 选择认证资格的监听处理（单选）
	 */
	ActionSheetListener sheetListener = new ActionSheetListener() {

		@Override
		public void onOtherButtonClick(ActionSheet actionSheet, int index) {
			LogUtil.i(actionsheetData[index]);
			tv_certicify_zige.setText(actionsheetData[index]);
			request_subLast.setAuthRight(actionsheetData[index]);
		}

		@Override
		public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
			if (isCancel) {

			} else {

			}
		}
	};

}
