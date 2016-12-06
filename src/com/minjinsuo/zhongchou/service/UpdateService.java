package com.minjinsuo.zhongchou.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.xutils.common.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.minjinsuo.zhongchou.utils.FileUtil;
import com.minjinsuo.zhongchou.R;

/***
 * 升级服务 app版本更新服务
 */
public class UpdateService extends Service {

	public static final String Install_Apk = "Install_Apk";
	/******** download progress step *********/
	private static final int down_step_custom = 3;

	private static final int TIMEOUT = 10 * 1000;// 超时
	private static String down_url;
	private static final int DOWN_OK = 1;
	private static final int DOWN_ERROR = 0;

	private String app_name = "";

	private NotificationManager notificationManager;
	private Notification notification2;
	private PendingIntent pendingIntent2;
	private RemoteViews contentView;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * 方法描述：onStartCommand方法
	 * 
	 * @param Intent
	 *            intent, int flags, int startId
	 * @return int
	 * @see UpdateService
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			app_name = intent.getStringExtra("Key_App_Name");
			down_url = intent.getStringExtra("Key_Down_Url");
			LogUtil.i("下载的url==" + down_url);
		}

		// create file,应该在这个地方加一个返回值的判断SD卡是否准备好，文件是否创建成功，等等！
		FileUtil.createFile(app_name);

		if (FileUtil.isCreateFileSucess == true) {
			createNotification();
			createThread();
		} else {
			Toast.makeText(this, "清插入sd卡", Toast.LENGTH_SHORT).show();
			/*************** stop service ************/
			stopSelf();

		}
		return super.onStartCommand(intent, Service.START_REDELIVER_INTENT,
				startId);
	}

	/********* update UI ******/
	private final Handler handler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_OK:

				/********* 下载完成，点击安装 ***********/
				Uri uri = Uri.fromFile(FileUtil.updateFile);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(uri,
						"application/vnd.android.package-archive");
				pendingIntent2 = PendingIntent.getActivity(UpdateService.this,
						0, intent, 0);

				notification2 = new Notification.Builder(UpdateService.this)
						.setContentIntent(pendingIntent2).setAutoCancel(true)
						.setContentTitle("版本更新").setContentText("下载成功")
						.setSmallIcon(R.drawable.app_icon)
						.setWhen(System.currentTimeMillis()).build();
				notificationManager.notify(R.layout.notification_item,
						notification2);

				/***** 安装APK ******/
				installApk();

				/*** stop service *****/
				stopSelf();
				break;

			case DOWN_ERROR:
				notification2 = new Notification.Builder(UpdateService.this)
						.setContentIntent(null).setAutoCancel(true)
						.setContentTitle("版本更新").setContentText("下载失败")
						.setSmallIcon(R.drawable.app_icon)
						.setWhen(System.currentTimeMillis()).build();
				notificationManager.notify(R.layout.notification_item,
						notification2);
				/*** stop service *****/
				stopSelf();
				break;

			default:
				// stopService(updateIntent);
				/****** Stop service ******/
				// stopService(intentname)
				// stopSelf();
				break;
			}
		}
	};

	private void installApk() {
		/********* 下载完成，点击安装 ***********/
		Uri uri = Uri.fromFile(FileUtil.updateFile);
		Intent intent = new Intent(Intent.ACTION_VIEW);

		/********** 加这个属性是因为使用Context的startActivity方法的话，就需要开启一个新的task **********/
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		UpdateService.this.startActivity(intent);
	}

	/**
	 * 方法描述：createThread方法, 开线程下载
	 * 
	 * @param
	 * @return
	 * @see UpdateService
	 */
	public void createThread() {
		new DownLoadThread().start();
		LogUtil.i("下面开始更新进程~~");
	}

	private class DownLoadThread extends Thread {
		@Override
		public void run() {
			Message message = new Message();
			try {
				long downloadSize = downloadUpdateFile(down_url,
						FileUtil.updateFile.toString());
				if (downloadSize > 0) {
					// down success
					message.what = DOWN_OK;
					handler.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.what = DOWN_ERROR;
				handler.sendMessage(message);
			}
		}
	}

	/**
	 * 方法描述：createNotification方法
	 * 
	 * @param
	 * @return
	 * @see UpdateService
	 */
	@SuppressLint("NewApi")
	public void createNotification() {

		// notification = new Notification(R.drawable.app_icon,// 应用的图标
		// app_name + "正在下载", System.currentTimeMillis());

		notification2 = new Notification.Builder(this).setAutoCancel(true)
				.setContentTitle("版本更新").setContentText(app_name + "正在下载")
				.setSmallIcon(R.drawable.app_icon)
				.setWhen(System.currentTimeMillis()).build();
		notification2.flags = Notification.FLAG_ONGOING_EVENT;

		/*** 自定义 Notification 的显示 ****/
		contentView = new RemoteViews(getPackageName(),
				R.layout.notification_item);
		contentView.setTextViewText(R.id.notificationTitle, app_name + "正在下载");
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
		notification2.contentView = contentView;

		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(R.layout.notification_item, notification2);
	}

	/***
	 * down file
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public long downloadUpdateFile(String down_url, String file)
			throws Exception {

		int down_step = down_step_custom;// 提示step
		int totalSize;// 文件总大小
		int downloadCount = 0;// 已经下载好的大小
		int updateCount = 0;// 已经上传的文件大小

		InputStream inputStream;
		OutputStream outputStream;

		URL url = new URL(down_url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setConnectTimeout(TIMEOUT);
		httpURLConnection.setReadTimeout(TIMEOUT);
		// 获取下载文件的size
		totalSize = httpURLConnection.getContentLength();

		if (httpURLConnection.getResponseCode() == 404) {
			throw new Exception("fail!");
			// 这个地方应该加一个下载失败的处理，但是，因为我们在外面加了一个try---catch，已经处理了Exception,
			// 所以不用处理
		}

		inputStream = httpURLConnection.getInputStream();
		outputStream = new FileOutputStream(file, false);// 文件存在则覆盖掉

		byte buffer[] = new byte[1024];
		int readsize = 0;

		while ((readsize = inputStream.read(buffer)) != -1) {

			outputStream.write(buffer, 0, readsize);
			downloadCount += readsize;// 时时获取下载到的大小
			/*** 每次增张3% **/
			if (updateCount == 0
					|| (downloadCount * 100 / totalSize - down_step) >= updateCount) {
				updateCount += down_step;
				// 改变通知栏
				contentView.setTextViewText(R.id.notificationPercent,
						updateCount + "%");
				contentView.setProgressBar(R.id.notificationProgress, 100,
						updateCount, false);
				notification2.contentView = contentView;
				notificationManager.notify(R.layout.notification_item,
						notification2);
				LogUtil.i("下载进度==" + updateCount);
			}
		}
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		inputStream.close();
		outputStream.close();

		return downloadCount;
	}

}