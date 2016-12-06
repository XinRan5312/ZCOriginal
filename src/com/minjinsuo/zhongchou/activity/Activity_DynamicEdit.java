package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitBase64FileRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitBase64FileResponse;
import net.zkbc.p2p.fep.message.protocol.SubmitPrjProgressRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitPrjProgressResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_UpLoadPic;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_Img;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CameraManager;
import com.minjinsuo.zhongchou.utils.CameraManager.OnHeadImageManagerFinish;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.widget.FloatLabeledEditText;

/**
 * 编辑项目进展页面【发布动态接口注意事项：1、先调用上传图片接口，成功后会返回图片的一个id，需要保存下来作为调用发布动态接口的参数；2、调用发布动态接口（
 * submitPrjProgress），需要遍历保存的图片列表，参数提交时多个图片的id用“,”隔开；】
 * 
 * @author zp
 *
 *         2016年9月6日
 */
public class Activity_DynamicEdit extends Activity_Base implements
		OnItemClickListener, OnHeadImageManagerFinish {
	@ViewInject(R.id.gv_Pic)
	private GridView gv_Pic;
	@ViewInject(R.id.et_content)
	private FloatLabeledEditText et_content;
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	private Adapter_UpLoadPic adapter;
	/**
	 * 保存从相册选择图片后暂存的未上传的图片对象
	 */
	private ArrayList<Model_Img> datas_containsBitmap = new ArrayList<Model_Img>();
	/**
	 * 保存图片上传成功后的图片对象
	 */
	private ArrayList<Model_Img> datas = new ArrayList<Model_Img>();
	private CameraManager mCameraManager;
	private String prjId = "";// 项目id

	private static final String TAG_PRESUBPIC = "preSubPic";// 保存单张上传成功的图片信息列表

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_dynamic_edit);
		x.view().inject(this);

		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleText("发布项目进展");
		setTitleBack();

		if (getIntent() != null
				&& !StringUtils.isEmpty(getIntent().getStringExtra("id"))) {
			prjId = getIntent().getStringExtra("id");
			LogUtil.i(prjId);
		}
	}

	@Override
	protected void initData() {
		// 上传头像处理
		mCameraManager = new CameraManager(this);

		adapter = new Adapter_UpLoadPic(this, datas_containsBitmap);
		gv_Pic.setAdapter(adapter);
		gv_Pic.setOnItemClickListener(this);
	}

	@Override
	protected void initListener() {

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (StringUtils.isEmpty(et_content.getText().toString().trim())) {
					ToastUtil.showToast(Activity_DynamicEdit.this, "请输入内容");
					return;
				}

				// submitPrjProgress();
				if (datas_containsBitmap.size() > 0) {// 触发递归。上传图片
					subPicRequest(datas_containsBitmap.get(0).getPic_bitmap(),
							datas_containsBitmap.get(0).getFileName(), true, 0);
				} else {// 没有图片只提交文本内容
					submitPrjProgress(true);
				}
			}
		});
	}

	/**
	 * 提交动态（上传的图片要将图片名称和id单独上传）
	 */
	public void submitPrjProgress(boolean isShow) {
		if (isShow)
			DialogUtils.showLoading(this);
		SubmitPrjProgressRequest request = new SubmitPrjProgressRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setTitle("");// 进展标题？zpqa现在UI中没有输入框
		request.setFileIdMemo(et_content.getText().toString().trim());
		request.setPrjId(prjId);

		String picJsonList = SharedPreferUtils.getValue(
				Activity_DynamicEdit.this, TAG_PRESUBPIC, TAG_PRESUBPIC, "");
		ArrayList<Model_Img> subPicList = (ArrayList<Model_Img>) JSON
				.parseArray(picJsonList, Model_Img.class);
		String proFileIds = "";// 图片id串，以“,”隔开
		String proFileNames = "";// 图片名称串，以“,”隔开
		if (subPicList != null && subPicList.size() > 0) {
			for (int i = 0; i < subPicList.size(); i++) {
				if (i < subPicList.size() - 1) {
					proFileIds += (subPicList.get(i).getId() + ",");
					proFileNames += (subPicList.get(i).getFileName() + ",");
				} else {
					proFileIds += subPicList.get(i).getId();
					proFileNames += subPicList.get(i).getFileName();
				}
			}
			request.setProFileIds(proFileIds);
			request.setProFileNames(proFileNames);
		} else {
			LogUtil.i("没有上传图片~~");
		}

		NetWorkRequestManager.sendRequestPost(this, true, request,
				SubmitPrjProgressResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		SubmitPrjProgressResponse model = (SubmitPrjProgressResponse) response;
		if (model.getResult().equals("success")) {
			ToastUtil.showToast(Activity_DynamicEdit.this, "上传成功");
			ZCApplication.getInstance().isNeedRefresh = true;
			finish();
		} else if (!StringUtils.isEmpty(model.getMessage())) {
			new DialogUtils().createOneBtnDiolog(Activity_DynamicEdit.this,
					model.getMessage());
		}
	}

	@Override
	public void failure() {

	}

	@Override
	public void onFailure(ResponseSupport response) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		LogUtil.i("click is " + position);
		if (position == datas_containsBitmap.size()) {// 上传
			if (datas_containsBitmap.size() >= 3) {
				ToastUtil.showToast(Activity_DynamicEdit.this, "最多上传三张");
			} else {
				mCameraManager.showAvatarDialog();
			}
		}
		// else {
		// String bmPath = datas.get(position).getUrl();
		// LogUtil.i(bmPath);
		// if (!StringUtils.isEmpty(bmPath)) {
		// Intent intent = new Intent(Activity_DynamicEdit.this,
		// Activity_BigImageView.class);
		// intent.putExtra("url", bmPath);
		// startActivity(intent);
		// }
		//
		// }
	}

	@Override
	public void onHeadImgFinish(Bitmap bitmap, String imagePath) {
		if (imagePath.endsWith("jpg") || imagePath.endsWith("png")
				|| imagePath.endsWith("PNG") || imagePath.endsWith("JPG")
				|| imagePath.endsWith("jpeg") || imagePath.endsWith("JPEG")
				|| imagePath.endsWith("gif") || imagePath.endsWith("GIF")) {
			if (imagePath.contains("/")) {
				imagePath = imagePath.substring(imagePath.lastIndexOf("/") + 1);
			}
			/**
			 * 此处应该加入上传图片的接口，获取到图片路径后再加载到imageView中 。通常上传的图片要base64编码后上传
			 */
			// subPicRequest(bitmap, imagePath);zptip暂时屏蔽
			Model_Img img = new Model_Img();
			img.setFileName(imagePath);
			img.setId("");
			img.setUrl("");
			img.setPic_bitmap(bitmap);
			datas_containsBitmap.add(img);
			adapter.notifyDataSetChanged();
		} else {
			LogUtil.i(imagePath + "不是图片~~");
		}
		et_content.setEnabled(true);
	}

	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		mCameraManager.onActivityResult(arg0, arg2);// 触发获取返回值
	}

	/**
	 * 上传头像
	 * 
	 * @param bitmap
	 * @param imgName
	 *            :图片名称
	 * @param flag
	 *            :0-上传头像；1-上传认证资料
	 */
	public void subPicRequest(final Bitmap bitmap, final String imgName,
			boolean isSHow, final int upLoadNum) {
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
		if (isSHow)
			DialogUtils.showLoading(Activity_DynamicEdit.this);
		NetWorkRequestManager.sendRequestPost(Activity_DynamicEdit.this, true,
				request, SubmitBase64FileResponse.class,
				new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						SubmitBase64FileResponse info = (SubmitBase64FileResponse) response;

						if (info != null && info.getResult().equals("success")) {
							// 先移除，再将图片的地址和名称保存下来
							Model_Img img = new Model_Img();
							img.setFileName(imgName);
							img.setId(info.getFileId());
							img.setUrl(info.getFileLoadAddr());
							img.setPic_bitmap(null);
							datas.add(img);
							LogUtil.e("datas's size==" + datas.size());

							int count_upLoad = upLoadNum;
							count_upLoad++;
							if (count_upLoad < datas_containsBitmap.size()) {
								subPicRequest(
										datas_containsBitmap.get(count_upLoad)
												.getPic_bitmap(),
										datas_containsBitmap.get(count_upLoad)
												.getFileName(), false,
										count_upLoad);
							} else {
								SharedPreferUtils.putValue(
										Activity_DynamicEdit.this,
										TAG_PRESUBPIC, TAG_PRESUBPIC,
										JSON.toJSONString(datas));
								// 所有图片递归上传完成后请求提交动态接口
								submitPrjProgress(false);
							}
						} else {
							if (!StringUtils.isEmpty(info.getMessage())) {
								ToastUtil.showToast(Activity_DynamicEdit.this,
										info.getMessage());
							}
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

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 把保存的图片信息清除
		getSharedPreferences(TAG_PRESUBPIC, Context.MODE_PRIVATE).edit()
				.clear().commit();
		LogUtil.i("ondestroy 清除sp保存的图片");
	}

}
