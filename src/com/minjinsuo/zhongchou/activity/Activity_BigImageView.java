package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.widget.ImageView;

import com.minjinsuo.zhongchou.R;

/**
 * 图片放大(注：gif图片不可放大，使用xUtils加载；其他使用imageLoader加载)
 */
public class Activity_BigImageView extends Activity_Base {

	@ViewInject(R.id.iv_pic)
	ImageView iv_pic;

	private String uri;
	private String bmPath = "";

	ImageOptions imageOptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_showbigpic);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
	}

	@Override
	protected void initData() {
		uri = getIntent().getStringExtra("url");
		// // 注：此项目暂时没有大图预览单独处理流程，采用直接加载原图法
		// if (!StringUtils.isEmpty(uri)) {
		// bmPath = uri.replace("140px", "prototype");// 下载大图片地址
		// }
	}

	@Override
	protected void initListener() {

		imageOptions = new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(3)).setIgnoreGif(false)
				.setCrop(false).setAutoRotate(true)
				.setImageScaleType(ImageView.ScaleType.MATRIX)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.icon_faild).build();

		x.image().bind(iv_pic, uri, imageOptions);

	}

	@Override
	public void onSuccess(ResponseSupport response) {

	}

	@Override
	public void failure() {

	}

	@Override
	public void onFailure(ResponseSupport response) {

	}

}
