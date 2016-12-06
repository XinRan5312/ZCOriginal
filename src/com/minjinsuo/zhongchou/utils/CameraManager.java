package com.minjinsuo.zhongchou.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.xutils.common.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.system.AppContants;

/**
 * 上传头像管理类
 * 
 * @author zp
 *
 *         2016年5月26日
 */
public class CameraManager {

	public static int Intent_Flag_From_Gallery = 110;
	public static int Intent_Flag_From_Camera = 111;
	public static int Intent_Flag_From_Crop = 112;

	private FragmentActivity mActivity;
	private Fragment mFragment;

	private String mImageFilePath;// 裁剪后路径
	private Uri mImageFileUri;// 拍照后产生图片文件的Uri
	private Dialog cameraDialog;
	private boolean needCrop;

	// 回调接口
	public interface OnHeadImageManagerFinish {
		public abstract void onHeadImgFinish(Bitmap bitmap, String imagePath);
	}

	public CameraManager(FragmentActivity activity) {
		mActivity = activity;
		needCrop = true;
	}

	public CameraManager(FragmentActivity activity, Fragment fragment) {
		this(activity);
		mFragment = fragment;
		needCrop = true;
	}

	public void setNeedCrop(boolean needCrop) {
		this.needCrop = needCrop;
	}

	@SuppressWarnings("resource")
	public void onActivityResult(int requestCode, Intent intent) {
		/* 从相册返回的数据 */
		if (requestCode == CameraManager.Intent_Flag_From_Gallery) {
			if (intent != null) {
				mImageFileUri = intent.getData();
				if (needCrop) {
					startCropPicture();
				} else {
					try {
						Bitmap bitmap = BitmapFactory.decodeStream(mActivity
								.getContentResolver().openInputStream(
										mImageFileUri));
						callBack(bitmap, "");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		/* 拍照返回的数据 */
		else if (requestCode == CameraManager.Intent_Flag_From_Camera) {
			if (intent != null) {
				System.out.println("intent 有值--");
				Bitmap mBitmap = (Bitmap) intent.getExtras().get("data");
				mImageFileUri = intent.getData();
				if (mImageFileUri == null) {

					mImageFileUri = Uri.parse(MediaStore.Images.Media
							.insertImage(mActivity.getContentResolver(),
									mBitmap, null, null));
				}
				if (needCrop) {
					startCropPicture();
				} else {
					callBack(mBitmap, "");
				}
			} else {
				System.out.println("intent 无值--");
			}
		}
		/* 保存裁剪后的图片到系统中，用于上传图片 */
		else if (requestCode == CameraManager.Intent_Flag_From_Crop) {
			// get the returned data
			if (intent == null || intent.getExtras() == null) {
				return;
			}
			Bundle extras = intent.getExtras();
			// get the cropped bitmap
			if (extras != null) {
				Bitmap thePic = extras.getParcelable("data");

				/* Write to file */
				FileOutputStream out;
				try {
					out = new FileOutputStream(getImageFilePath());
					if (thePic == null) {
						return;
					}
					thePic.compress(Bitmap.CompressFormat.JPEG, 100, out);
					out.close();
					out = null;

					String file = mImageFilePath;
					LogUtil.i("==>>path" + file);
					final Bitmap bitmap = BitmapFactory.decodeFile(file);
					callBack(bitmap, getImageFilePath());

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * fragment和activity
	 * 
	 * @param bitmap
	 */
	private void callBack(Bitmap bitmap, String imagePath) {
		if (mFragment != null) {
			OnHeadImageManagerFinish headImageManagerFinish = (OnHeadImageManagerFinish) mFragment;
			headImageManagerFinish.onHeadImgFinish(bitmap, imagePath);
		} else {
			OnHeadImageManagerFinish headImageManagerFinish = (OnHeadImageManagerFinish) mActivity;
			headImageManagerFinish.onHeadImgFinish(bitmap, imagePath);
		}
	}

	public Dialog showAvatarDialog() {
		if (cameraDialog == null) {
			cameraDialog = DialogUtils.createSelectDiolog(mActivity, "camera");
		} else if (!cameraDialog.isShowing()) {
			cameraDialog.show();
		}
		cameraDialog.findViewById(R.id.tv_two).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {// 拍照
						AppContants.isNotShowGesPwd = true;
						startCameraCapture();
						cameraDialog.dismiss();
					}
				});
		cameraDialog.findViewById(R.id.tv_one).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {// 相册
						AppContants.isNotShowGesPwd = true;
						startGalleryIntent();
						cameraDialog.dismiss();
					}
				});
		return cameraDialog;
	}

	/* 打开相册选择照片 */
	private void startGalleryIntent() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			showNoSDDialog();
			return;
		}
		Intent galleryIntent = new Intent();
		galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
		galleryIntent.setType("image/*");
		if (mFragment != null) {
			mFragment.startActivityForResult(galleryIntent,
					CameraManager.Intent_Flag_From_Gallery);
		} else {
			System.out.println("相册--");
			mActivity.startActivityForResult(galleryIntent,
					CameraManager.Intent_Flag_From_Gallery);
		}

	}

	/* 拍照图片 */
	@SuppressLint("SimpleDateFormat")
	private void startCameraCapture() {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			showNoSDDialog();
			return;
		}
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (mFragment != null) {
			mFragment.startActivityForResult(takePictureIntent,
					CameraManager.Intent_Flag_From_Camera);
		} else {
			System.out.println("拍照--");
			mActivity.startActivityForResult(takePictureIntent,
					CameraManager.Intent_Flag_From_Camera);
		}

	}

	/* 裁剪图片 */
	private void startCropPicture() {
		System.out.println("剪裁---");
		Intent cropIntent = new Intent("com.android.camera.action.CROP");

		// indicate image type and Uri
		cropIntent.setDataAndType(mImageFileUri, "image/*");
		// set crop properties
		cropIntent.putExtra("crop", "true");
		// indicate aspect of desired crop
		cropIntent.putExtra("aspectX", 1);
		cropIntent.putExtra("aspectY", 1);
		// indicate output X and Y
		cropIntent.putExtra("outputX", 300);
		cropIntent.putExtra("outputY", 300);
		cropIntent.putExtra("scale", true);
		// retrieve data on return
		cropIntent.putExtra("return-data", true);
		cropIntent.putExtra("noFaceDetection", true);// 取消系统默认人脸识别，否则会按照1：1来设置裁剪比

		if (mFragment != null) {
			mFragment.startActivityForResult(cropIntent,
					CameraManager.Intent_Flag_From_Crop);
		} else {
			mActivity.startActivityForResult(cropIntent,
					CameraManager.Intent_Flag_From_Crop);
		}

	}

	private String getImageFilePath() {
		// if (mImageFilePath == null) {
		String imageFileName = "IMG_zhongchou" + System.currentTimeMillis();

		try {
			File mImageFile = File.createTempFile(imageFileName, ".jpg",
					mActivity.getExternalCacheDir());
			mImageFilePath = mImageFile.getAbsolutePath();
			System.out.println("getImageFilePath====" + mImageFilePath);

		} catch (IOException e) {
			e.printStackTrace();
		}
		// }
		return mImageFilePath;
	}

	private void showNoSDDialog() {
		String content = "存储卡不可用，请检查您是否插好外部存储卡";
		final Dialog dialog = DialogUtils
				.createSelectDiolog(mActivity, content);
		dialog.findViewById(R.id.tv_one).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}
				});
		dialog.findViewById(R.id.tv_two).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}
				});
	}

	/**
	 * 压缩裁剪后的图片
	 * 
	 * @param bitmap
	 * @return
	 */
	public byte[] createImageData(Bitmap bitmap) {
		byte[] result = null;
		if (bitmap != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				bitmap.compress(CompressFormat.JPEG, 60, out);
				result = out.toByteArray();
				out.close();
			} catch (Exception e) {

			}
		}
		return result;
	}

}
