package com.minjinsuo.zhongchou.utils;

import org.xutils.common.util.LogUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.system.ZCApplication;

public class DialogUtils {
	public static Dialog mProgressDialog;
	private static Context mContext;

	/**
	 * @Description: 显示加载提示框
	 * @param context
	 */
	public static void showLoading(Context context) {
		if (context != null) {
			mContext = context;
			showLoading(context,
					context.getResources().getString(R.string.loading));
		}
	}

	/**
	 * @Description: 关闭加载提示框
	 */
	public static void dismisLoading() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	/**
	 * @Description: 显示加载提示框
	 * @param context
	 * @param text
	 *            提示内容
	 */
	/**
	 * @param context
	 * @param text
	 */
	public static void showLoading(Context context, String text) {
		try {
			if (mProgressDialog == null) {
				mProgressDialog = buildLoadingDialog(context, text);
			}
			if (!mProgressDialog.isShowing()) {
				mProgressDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/**
		 * 加载框可以使用返回键点击取消;同时杀掉网络请求进程
		 */
		mProgressDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_DOWN) {

					dismisLoading();
					ZCApplication.getQueue().cancelAll(mContext);
					LogUtil.i("dismissLoading requestQueue is cancleAll~~");
				}
				return false;
			}
		});
	}

	/**
	 * @Description: 创建加载提示框
	 * @param context
	 * @param text
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Dialog buildLoadingDialog(Context context, String text) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		TextView loadingTextView = (TextView) layout
				.findViewById(R.id.tv_loading_dialog);
		loadingTextView.setText(text);
		Dialog loadingDialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog
		loadingDialog.setCancelable(false);// 可以用“返回键”取消
		loadingDialog.setCanceledOnTouchOutside(false);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}

	/**
	 * 底部弹出操作弹出框(上传头像)
	 * 
	 * @param context
	 * @return
	 * @author zp
	 */
	public static Dialog createSelectDiolog(Context context, String searchType) {
		final Dialog dialog = createDialog(context, R.layout.dialog_avatar,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.style.AvatarDialog,
				Gravity.BOTTOM);
		dialog.show();
		TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
		TextView tv_one = (TextView) dialog.findViewById(R.id.tv_one);
		TextView tv_two = (TextView) dialog.findViewById(R.id.tv_two);

		if (searchType.equals("camera")) {// 上传头像选择框
			tv_one.setText("相册选取");
			tv_two.setText("拍照");
		} else if (searchType.equals("borrow")) {// 借款记录点击
			tv_one.setText("查看详情");
			tv_two.setText("还款");
		} else if (searchType.equals("hetong")) {// 我的投资
			tv_one.setText("查看详情");
			tv_two.setText("查看合同");
		} else if (searchType.equals("redbag")) {// 红包列表传来
			tv_two.setVisibility(View.GONE);
			tv_one.setText("打开");
		} else if (searchType.equals("0")) {// 可转让
			tv_two.setText("转出");
		} else {
			tv_two.setText("撤回");
		}
		tvCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();

			}
		});

		return dialog;
	}

	private static Dialog createDialog(Context context, int layout, int width,
			int height, int animStyle, int gravity) {
		Dialog dialog = getAppDialog(context);
		WindowManager ma = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = ma.getDefaultDisplay();

		dialog.setContentView(layout);
		dialog.show();
		Window window = dialog.getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		if (width == 0) {
			params.width = (int) (display.getWidth() * 0.85);
		} else {
			params.width = width;
		}
		params.height = height;
		window.setAttributes(params);
		window.setGravity(gravity);
		window.setWindowAnimations(animStyle);
		dialog.setCanceledOnTouchOutside(true);

		return dialog;
	}

	private static Dialog getAppDialog(Context context) {
		return new Dialog(context, R.style.MyDialog);
	}

	/**
	 * 创建确定对话框
	 * 
	 * @param content
	 * @return
	 */
	public static Dialog createOneBtnDiolog(Context context, String content) {
		final Dialog dialog = createDialog(context, R.layout.dialog_one_btn, 0,
				LayoutParams.WRAP_CONTENT, R.style.CenterDialog, Gravity.CENTER);

		TextView contentView = (TextView) dialog.findViewById(R.id.tvContent);
		contentView.setText(content);

		dialog.findViewById(R.id.btn_confirm).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		return dialog;

	}

	/**
	 * 单按钮对话框——可设置内容显示重心
	 * 
	 * @param context
	 * @param content
	 * @param title
	 * @param gravity
	 * @return
	 */
	public static Dialog createOneBtnDiolog(Context context, String content,
			String title, int gravity) {
		final Dialog dialog = createDialog(context, R.layout.dialog_one_btn, 0,
				LayoutParams.WRAP_CONTENT, R.style.CenterDialog, Gravity.CENTER);

		TextView contentView = (TextView) dialog.findViewById(R.id.tvContent);
		TextView tvDialogTitle = (TextView) dialog
				.findViewById(R.id.tvDialogTitle);
		contentView.setText(content);
		contentView.setGravity(gravity);
		tvDialogTitle.setText(title);

		dialog.findViewById(R.id.btn_confirm).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		return dialog;

	}

	/**
	 * 创建确定取消对话框
	 * 
	 * @param content
	 * @return
	 */
	public static Dialog createTwoBtnDiolog(Context context, String content) {
		final Dialog dialog = createDialog(context, R.layout.dialog_two_btn, 0,
				LayoutParams.WRAP_CONTENT, R.style.CenterDialog, Gravity.CENTER);
		dialog.findViewById(R.id.btnLeft).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();

					}
				});

		TextView contentView = (TextView) dialog.findViewById(R.id.tvContent);
		contentView.setText(content);

		return dialog;
	}
	
	/**
	 * 创建进度条对话框
	 *
	 * @param content
	 * @return
	 */
	public static Dialog createProgressDiolog(Context context, String titleDes) {
		final Dialog dialog = createDialog(context, R.layout.dialog_progress,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT, R.style.CenterDialog,
				Gravity.CENTER);

		TextView contentView = (TextView) dialog.findViewById(R.id.tvDialogTitle);
		contentView.setText(titleDes);


		return dialog;
	}
	
}
