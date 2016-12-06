package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import net.zkbc.p2p.fep.message.protocol.GetInvestFileListResponse.ElementInvestFileList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 相关文件
 */
public class Adapter_RelationFiles extends BaseAdapter {

	public Context context;
	private ArrayList<ElementInvestFileList> tenderList;
	private ImageOptions imageOptions;

	public Adapter_RelationFiles(Context context) {
		super();
		this.context = context;
		// 初始化
		tenderList = new ArrayList<ElementInvestFileList>();
		imageOptions = new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(3))
				.setIgnoreGif(false)
				// 如果ImageView的大小不是定义为wrap_content, 不要crop.
				// .setCrop(true)
				// 很多时候设置了合适的scaleType也不需要它.
				// 加载中或错误图片的ScaleType
				// .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();
	}

	public void setData(List<ElementInvestFileList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public ArrayList<ElementInvestFileList> getData() {
		return tenderList;
	}

	@Override
	public int getCount() {
		if (tenderList != null) {
			return tenderList.size();
		} else {
			return 0;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			// convertView =
			// LayoutInflater.from(context).inflate(R.layout.item_relation_files,
			// null);
			convertView = LayoutInflater.from(context).inflate(
					R.layout.child_item_investfiles, null);
			holder.iv_files = (ImageView) convertView
					.findViewById(R.id.iv_files);
			holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		ElementInvestFileList model_file = tenderList.get(position);
		String fileAdd = model_file.getFileAddr();
		String fileType = fileAdd.substring(fileAdd.lastIndexOf(".") + 1,
				fileAdd.lastIndexOf("/"));
		LogUtil.i("文件类型==" + fileType);
		if (fileType.equals("jpg") || fileType.equals("png")
				|| fileType.equals("JPG") || fileType.equals("PNG")
				|| fileType.equals("jpeg") || fileType.equals("JPEG")
				|| fileType.equals("gif") || fileType.equals("GIF")) {
			x.image().bind(holder.iv_files, fileAdd, imageOptions);
			holder.tv_des.setVisibility(View.GONE);
		} else {// 文件形式
			holder.iv_files.setImageResource(R.drawable.icon_files);
			holder.tv_des.setText(model_file.getFileNam());
			if (!StringUtils.isEmpty(model_file.getFileSize())) {
				holder.tv_des.setText(model_file.getFileNam() + "\n"
						+ model_file.getFileSize());
			}

		}
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return tenderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView tv_des;
		public ImageView iv_files;
	}

}
