package com.minjinsuo.zhongchou.widget.pdf;

import android.graphics.Point;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.artifex.mupdf.MuPDFCore;
import com.minjinsuo.zhongchou.activity.Activity_Base;
import com.minjinsuo.zhongchou.utils.CommonUtils;

public class MyMuPDFPageAdapter extends BaseAdapter {
	// private final BaseActivity mContext;
	private final Activity_Base mContext;
	private final MuPDFCore mCore;
	public final SparseArray<PointF> mPageSizes = new SparseArray<PointF>();

	public MyMuPDFPageAdapter(Activity_Base c, MuPDFCore core) {
		mContext = c;
		mCore = core;
	}

	public int getCount() {
		if (mCore == null) {
			return 0;
		}
		return mCore.countPages();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		final MyMuPDFPageView pageView;
		if (convertView == null) {
			// pageView = new MyMuPDFPageView(mContext, mCore, new Point(1280,
			// 1810));
			pageView = new MyMuPDFPageView(mContext, mCore, new Point(
					CommonUtils.getWindowSize(0), CommonUtils.getWindowSize(1)));
		} else {
			pageView = (MyMuPDFPageView) convertView;
		}

		PointF pageSize = mPageSizes.get(position);// pageSize是原图大小
		if (null != pageSize) {
			pageView.drawOncePage(position, pageSize);
		} else {
			pageView.initReset(position);
			AsyncTask<Void, Void, PointF> sizingTask = new AsyncTask<Void, Void, PointF>() {
				@Override
				protected PointF doInBackground(Void... arg0) {
					return mCore.getPageSize(position);
				}

				@Override
				protected void onPostExecute(PointF result) {
					super.onPostExecute(result);
					mPageSizes.put(position, result);
					if (pageView.getCurPageNum() == position) {
						pageView.drawOncePage(position, result);
					}
				}
			};
			sizingTask.execute((Void) null);
		}
		return pageView;
	}
}
