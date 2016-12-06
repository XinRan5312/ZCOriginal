package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.model.Model_Img;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 编辑项目动态——上传图片显示的九宫格
 * 
 * 2016年6月7日
 */
public class Adapter_UpLoadPic extends BaseAdapter {
	private Context context;
	private ArrayList<Model_Img> datas;
	private LayoutInflater mInflater;
	private ImageOptions imageOptions;

	public Adapter_UpLoadPic(Context context, ArrayList<Model_Img> datas) {
		this.context = context;
		this.datas = datas;
		mInflater = LayoutInflater.from(context);

		imageOptions = new ImageOptions.Builder()
				.setSize(DensityUtil.dip2px(70), DensityUtil.dip2px(70))
				.setRadius(DensityUtil.dip2px(0)).setCrop(true)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
	}

	@Override
	public int getCount() {
		if (datas.size() >= 3) {
			return 3;
		}
		return datas.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.item_gridview_pic, null);
		ImageView image = (ImageView) convertView
				.findViewById(R.id.MainActivityImage);
		ImageView item_deleteIcon = (ImageView) convertView
				.findViewById(R.id.item_deleteIcon);
		System.out.println("position:" + position + "datas:" + datas.size());
		if (position == datas.size()) {
			image.setImageResource(R.drawable.add_pic);
			item_deleteIcon.setVisibility(View.GONE);
		} else {
			if (!StringUtils.isEmpty(datas.get(position).getUrl())) {
				x.image().bind(image, datas.get(position).getUrl(),
						imageOptions);
			} else {
				image.setImageBitmap(datas.get(position).getPic_bitmap());
			}
			item_deleteIcon.setVisibility(View.VISIBLE);
		}

		final int positionDel = position;
		item_deleteIcon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				datas.remove(positionDel);
				notifyDataSetChanged();
			}
		});

		convertView.setPadding(10, 10, 10, 10);
		return convertView;
	}
}
