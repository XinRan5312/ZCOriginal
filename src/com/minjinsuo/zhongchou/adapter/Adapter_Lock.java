package com.minjinsuo.zhongchou.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.minjinsuo.zhongchou.R;

/**
 * 设置手势密码view适配器
 * 
 * @author zp
 *
 *         2016年10月24日
 */
public class Adapter_Lock extends BaseAdapter {

	private char keys[];
	public Context context;

	public Adapter_Lock(Context context) {
		super();
		this.context = context;
	}

	public void setKey(String key) {
		if (key != null) {
			this.keys = key.toCharArray();
			this.notifyDataSetChanged();
		}
	}

	public void ClearDate() {
		this.keys = null;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return 9;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		// imageView.setImageResource(R.drawable.eqe);
		imageView.setImageResource(R.drawable.shape_ring_maincolor);
		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				if ((keys[i] - 48) == position) {
					// imageView.setImageResource(R.drawable.eqd);
					imageView.setImageResource(R.drawable.shape_ball_maincolor);
					continue;
				}
			}

		}
		return imageView;
	}

}
