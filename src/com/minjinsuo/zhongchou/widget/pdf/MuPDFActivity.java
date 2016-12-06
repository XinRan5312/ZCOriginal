package com.minjinsuo.zhongchou.widget.pdf;

import java.io.File;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.artifex.mupdf.MuPDFCore;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_Base;

/**
 * listView形式展示pdf文件
 * 
 * @author zp
 *
 *         2015年12月7日
 */
public class MuPDFActivity extends Activity_Base {
	private MuPDFCore core;
	private String mFileName;
	private ListView mDocListView;
	private MyMuPDFPageAdapter pdfPageAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.z_test_pdf);
		setTitleBack();
		setTitleText("查看文件");

		if (core == null) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = getContext().getFilesDir().getPath()
					+ "/zhongChouApp/contract.pdf";

			int lastSlashPos = path.lastIndexOf('/');
			mFileName = new String(lastSlashPos == -1 ? path
					: path.substring(lastSlashPos + 1));
			System.out.println("Trying to open " + path);

			try {
				core = new MuPDFCore(path);
			} catch (Exception e) {
				Log.e("exception--", e.toString());
			}
		}

		if (core == null) {
			AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setTitle("加载失败");
			alert.setButton(AlertDialog.BUTTON_POSITIVE, "Dismiss",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});
			alert.show();
			return;
		}

		createUI(savedInstanceState);
	}

	public void createUI(Bundle savedInstanceState) {
		mDocListView = (ListView) findViewById(R.id.lv_pdf);
		pdfPageAdapter = new MyMuPDFPageAdapter(this, core);
		mDocListView.setAdapter(pdfPageAdapter);
	}

	public void onDestroy() {
		if (core != null)
			core.onDestroy();
		core = null;
		super.onDestroy();
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}
}
