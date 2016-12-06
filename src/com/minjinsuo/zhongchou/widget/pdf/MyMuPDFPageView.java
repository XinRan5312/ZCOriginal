package com.minjinsuo.zhongchou.widget.pdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.artifex.mupdf.MuPDFCore;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_Base;

public class MyMuPDFPageView extends ViewGroup {
	private final MuPDFCore pdfCore;
	private Activity_Base context;
	private int curPageNum;
	private Point parentSize, mSize, mPatchViewSize;
	private ImageView mEntire, mPatch;
	private Bitmap mEntireBm;
	private Rect mPatchArea;
	private AsyncTask<Void, Void, Object> mDrawEntire;
	private AsyncTask<PatchInfo, Void, PatchInfo> mDrawPatch;
	private ProgressBar busyLoadingIndicator;

	public MyMuPDFPageView(Activity_Base mContext, MuPDFCore pdfCore,
			Point parentSize) {
		super(mContext);
		this.context = mContext;
		this.pdfCore = pdfCore;
		this.parentSize = parentSize;// 容器高宽
		initReset();
	}

	public void initReset() {
		initReset(0);
	}

	public void initReset(int pagePosition) {// 初始化重置
		// setBackgroundColor(Color.YELLOW);
		setBackgroundColor(Color.WHITE);
		getCountPageSum();

		curPageNum = pagePosition;

		if (null != mDrawEntire) {
			mDrawEntire.cancel(true);
			mDrawEntire = null;
		}
		if (null == mSize) {
			mSize = parentSize;
		}
		if (null != mEntire) {
			mEntire.setImageBitmap(null);
		}
		if (null != mPatch) {
			mPatch.setImageBitmap(null);
		}
	}

	public void drawOncePage(int pdfPageNum, PointF pdfPageSize) {
		if (mDrawEntire != null) {
			mDrawEntire.cancel(true);
			mDrawEntire = null;
		}// end of if

		curPageNum = pdfPageNum;
		if (mEntire == null) {
			mEntire = new OpaqueImageView(context);
			mEntire.setScaleType(ImageView.ScaleType.FIT_CENTER);
			addView(mEntire);// 生成添加每一页的ImageView
		}// end of if
		float sourceScale = Math.min(parentSize.x / pdfPageSize.x, parentSize.y
				/ pdfPageSize.y);// 计算缩放比例
		Point newSize = new Point((int) (pdfPageSize.x * sourceScale),
				(int) (pdfPageSize.y * sourceScale));
		mSize = newSize;
		if (null == mEntireBm
				|| (null != mEntireBm && mEntireBm.getWidth() != newSize.x)
				|| (null != mEntireBm && mEntireBm.getHeight() != newSize.y)) {
			mEntireBm = Bitmap.createBitmap(mSize.x, mSize.y,
					Bitmap.Config.ARGB_8888);
		}// end of if
		mDrawEntire = new AsyncTask<Void, Void, Object>() {
			protected void onPreExecute() {
				mEntire.setImageBitmap(null);

				if (null == busyLoadingIndicator) {
					busyLoadingIndicator = new ProgressBar(context);
					busyLoadingIndicator.setIndeterminate(true);
					busyLoadingIndicator.setBackgroundResource(R.drawable.busy);
					busyLoadingIndicator.setVisibility(VISIBLE);
					addView(busyLoadingIndicator);
//					context.handler.sendEmptyMessage(1);
				}
			}

			protected Object doInBackground(Void... v) {
				drawPage(mEntireBm, mSize.x, mSize.y, 0, 0, mSize.x, mSize.y);// 绘制
				return null;
			}

			protected void onPostExecute(Object obj) {
				removeView(busyLoadingIndicator);
				busyLoadingIndicator = null;
//				context.handler.sendEmptyMessage(0);

				mEntire.setImageBitmap(mEntireBm);
				invalidate();
			}
		};
		mDrawEntire.execute();
		requestLayout();
	}

	public Bitmap getmEntireBm() {
		return mEntireBm;
	}

	protected void drawPage(Bitmap bm, int sizeX, int sizeY, int patchX,
			int patchY, int patchWidth, int patchHeight) {// 异步栈调NDK方法画内容
		pdfCore.drawPage(curPageNum, bm, sizeX, sizeY, patchX, patchY,
				patchWidth, patchHeight);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measuredHeight = measureHeight(heightMeasureSpec);
		int measuredWidth = measureWidth(widthMeasureSpec);
		setMeasuredDimension(measuredWidth, measuredHeight);

	}

	private int measureHeight(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		int result = 1810;

		if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}
		return result;
	}

	private int measureWidth(int measureSpec) {
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);
		int result = 1280;
		if (specMode == MeasureSpec.AT_MOST) {
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		}
		return result;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		int w = right - left;
		int h = bottom - top;

		if (null != mEntire) {
			mEntire.layout(0, 0, w, h);
		}
		if (null != mPatchViewSize) {
			if (mPatchViewSize.x != w || mPatchViewSize.y != h) {
				mPatchViewSize = null;
				mPatchArea = null;
				if (mPatch != null)
					mPatch.setImageBitmap(null);
			} else {
				mPatch.layout(mPatchArea.left, mPatchArea.top,
						mPatchArea.right, mPatchArea.bottom);
			}
		}
		if (null != busyLoadingIndicator) {
			int bw = 100, bh = 100;
			busyLoadingIndicator.layout((w - bw) / 2, (h - bh) / 2,
					(w + bw) / 2, (h + bh) / 2);
		}
	}

	public void addHq() {// 高清
		Rect viewArea = new Rect(getLeft(), getTop(), getRight(), getBottom());
		if (viewArea.width() != mSize.x || viewArea.height() != mSize.y) {
			Point patchViewSize = new Point(viewArea.width(), viewArea.height());
			Rect patchArea = new Rect(0, 0, parentSize.x, parentSize.y);

			if (!patchArea.intersect(viewArea))
				return;

			patchArea.offset(-viewArea.left, -viewArea.top);
			if (patchArea.equals(mPatchArea)
					&& patchViewSize.equals(mPatchViewSize))
				return;

			if (mDrawPatch != null) {
				mDrawPatch.cancel(true);
				mDrawPatch = null;
			}

			if (mPatch == null) {
				mPatch = new OpaqueImageView(context);
				mPatch.setScaleType(ImageView.ScaleType.FIT_CENTER);
				addView(mPatch);
			}

			Bitmap bm = Bitmap.createBitmap(patchArea.width(),
					patchArea.height(), Bitmap.Config.ARGB_8888);

			mDrawPatch = new AsyncTask<PatchInfo, Void, PatchInfo>() {
				protected PatchInfo doInBackground(PatchInfo... v) {
					drawPage(v[0].bm, v[0].patchViewSize.x,
							v[0].patchViewSize.y, v[0].patchArea.left,
							v[0].patchArea.top, v[0].patchArea.width(),
							v[0].patchArea.height());
					return v[0];
				}

				protected void onPostExecute(PatchInfo v) {
					mPatchViewSize = v.patchViewSize;
					mPatchArea = v.patchArea;
					mPatch.setImageBitmap(v.bm);
					// requestLayout();
					mPatch.layout(mPatchArea.left, mPatchArea.top,
							mPatchArea.right, mPatchArea.bottom);
					invalidate();
				}
			};
			mDrawPatch.execute(new PatchInfo(bm, patchViewSize, patchArea));
		}
	}

	public void removeHq() {// 移除高清
		if (mDrawPatch != null) {
			mDrawPatch.cancel(true);
			mDrawPatch = null;
		}

		mPatchViewSize = null;
		mPatchArea = null;
		if (mPatch != null)
			mPatch.setImageBitmap(null);
	}

	public int getCurPageNum() {
		return curPageNum;
	}

	public int getCountPageSum() {
		if (null != pdfCore) {
			return pdfCore.countPages();
		}
		return 0;
	}

	@Override
	public boolean isOpaque() {
		return true;
	}
}

class PatchInfo {
	public Bitmap bm;
	public Point patchViewSize;
	public Rect patchArea;

	public PatchInfo(Bitmap aBm, Point aPatchViewSize, Rect aPatchArea) {
		bm = aBm;
		patchViewSize = aPatchViewSize;
		patchArea = aPatchArea;
	}
}

class OpaqueImageView extends ImageView {
	public OpaqueImageView(Context context) {
		super(context);
	}

	@Override
	public boolean isOpaque() {
		return true;
	}
}