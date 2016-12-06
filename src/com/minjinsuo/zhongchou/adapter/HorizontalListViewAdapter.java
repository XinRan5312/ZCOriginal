package com.minjinsuo.zhongchou.adapter;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.BitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalListViewAdapter extends BaseAdapter {
	private int[] mIconIDs;
	private String[] mTitles;
	private Context mContext;
	private LayoutInflater mInflater;
	Bitmap iconBitmap;
	private int selectIndex = -1;
	private boolean isShowIv;// 是否显示ImageView

	public HorizontalListViewAdapter(Context context, String[] titles, int[] ids, boolean isShowIv) {
		this.mContext = context;
		this.mIconIDs = ids;
		this.mTitles = titles;
		this.isShowIv = isShowIv;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);// LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		if (mTitles.length > 0) {
			return mTitles.length;
		} else if (mIconIDs.length > 0) {
			return mIconIDs.length;
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.horizontal_list_item, null);
			holder.mImage = (ImageView) convertView.findViewById(R.id.img_list_item);
			holder.mTitle = (TextView) convertView.findViewById(R.id.text_list_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == selectIndex) {
			convertView.setSelected(true);
		} else {
			convertView.setSelected(false);
		}

		holder.mTitle.setText(mTitles[position]);
		iconBitmap = getPropThumnail(mIconIDs[position]);
		holder.mImage.setImageBitmap(iconBitmap);

		if (!isShowIv) {
			holder.mImage.setVisibility(View.GONE);
		}

		return convertView;
	}

	private static class ViewHolder {
		private TextView mTitle;
		private ImageView mImage;
	}

	private Bitmap getPropThumnail(int id) {
		Drawable d = mContext.getResources().getDrawable(id);
		Bitmap b = BitmapUtil.drawableToBitmap(d);
		// Bitmap bb = BitmapUtil.getRoundedCornerBitmap(b, 100);
		int w = mContext.getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width);
		int h = mContext.getResources().getDimensionPixelSize(R.dimen.thumnail_default_height);

		Bitmap thumBitmap = ThumbnailUtils.extractThumbnail(b, w, h);

		return thumBitmap;
	}

	public void setSelectIndex(int i) {
		selectIndex = i;
	}
}