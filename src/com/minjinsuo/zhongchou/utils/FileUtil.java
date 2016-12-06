package com.minjinsuo.zhongchou.utils;

import java.io.File;
import java.io.IOException;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.system.ZCApplication;

/**
 * 类描述：FileUtil
 * 
 * @version
 */
public class FileUtil {

	public static File updateDir = null;
	public static File updateFile = null;
	/*********** 保存升级APK的目录 ***********/
	public static final String ZhongChouApplication = "ZCUpdateApplication";

	public static boolean isCreateFileSucess;
	private Callback.Cancelable cancelableHttp;

	/**
	 * 方法描述：app版本更新保存下载的APK文件
	 * 
	 * @param String
	 *            app_name
	 * @return
	 * @see FileUtil
	 */
	public static void createFile(String app_name) {

		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {
			isCreateFileSucess = true;

			updateDir = new File(Environment.getExternalStorageDirectory()
					+ "/" + ZhongChouApplication + "/");
			updateFile = new File(updateDir + "/" + app_name + ".apk");

			if (!updateDir.exists()) {
				updateDir.mkdirs();
			}
			if (!updateFile.exists()) {
				try {
					updateFile.createNewFile();
				} catch (IOException e) {
					isCreateFileSucess = false;
					e.printStackTrace();
				}
			}

		} else {
			isCreateFileSucess = false;
		}
	}

	public FileUtil() {
	}

	public interface fileCallBack {
		public void onSuccess();

		public void onFailure();

		public void onLoading(long total, long current);
	}

	/**
	 * xUtil3文件下载_只在PDF文件下载时使用
	 * 
	 * @param url
	 * @param path
	 * @param callBack
	 */
	public void downloadFile(final Context context, final String url,
			String path, final fileCallBack callBack) {
		final Dialog dialog_progress = getMyProgressDialog(context, "正在加载…");
		RequestParams requestParams = new RequestParams(url);
		requestParams.setSaveFilePath(path);
		requestParams.setCacheMaxAge(5 * 1000);// 缓存时间5秒
		requestParams.setCacheDirName(path);
		cancelableHttp = x.http().get(requestParams,
				new Callback.ProgressCallback<File>() {
					@Override
					public void onWaiting() {
					}

					@Override
					public void onStarted() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isDownloading) {
						if (current != total) {
							LogUtil.d("正在下载……" + (100 * current / total) + "%");
							callBack.onLoading(total, current);

							TextView tv_progress = (TextView) dialog_progress
									.findViewById(R.id.tv_progress);
							tv_progress.setText((100 * current / total) + "%");
							tv_progress.setVisibility(View.VISIBLE);
							ProgressBar proBar = (ProgressBar) dialog_progress
									.findViewById(R.id.progress);
							proBar.setProgress((int) (100 * current / total));
						} else {
							dialog_progress.dismiss();
						}
					}

					@Override
					public void onSuccess(File result) {
						callBack.onSuccess();
						dialog_progress.dismiss();
					}

					@Override
					public void onError(Throwable ex, boolean isOnCallback) {
						ex.printStackTrace();
						LogUtil.i("----------失败----------");
						callBack.onFailure();
					}

					@Override
					public void onCancelled(CancelledException cex) {
						LogUtil.e("----------http had be cancelled~~---------");
					}

					@Override
					public void onFinished() {
					}
				});
	}

	/**
	 * 创建加载进度对话框(注意关闭显示时要取消volley请求队列)
	 * 
	 * @param context
	 * @param titleDes
	 * @return
	 */
	private Dialog getMyProgressDialog(final Context context, String titleDes) {
		Dialog dialog = DialogUtils.createProgressDiolog(context, titleDes);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_DOWN) {

					if (dialog != null) {
						cancelableHttp.cancel();
						dialog.dismiss();
					}
					ZCApplication.getQueue().cancelAll(context);
					LogUtil.i("dismissLoading requestQueue is cancleAll~~");
				}
				return false;
			}
		});

		return dialog;
	}
}