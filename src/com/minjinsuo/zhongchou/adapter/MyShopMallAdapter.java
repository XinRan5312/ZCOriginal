package com.minjinsuo.zhongchou.adapter;

import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.CommonUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import net.zkbc.p2p.fep.message.protocol.GetScoreGoodsListResponse.ElementScoreGoodsList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.ScoreExchangeRequest;
import net.zkbc.p2p.fep.message.protocol.ScoreExchangeResponse;

/**
 * 积分商城
 * 
 * @author Tracy
 * 
 */
public class MyShopMallAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflate;
	private List<ElementScoreGoodsList> datas;
	private int useIntegrateCount;
	private ImageOptions options;// 图片配置

	public MyShopMallAdapter(Context context,
			List<ElementScoreGoodsList> datas2, int useIntegrateCount) {
		this.context = context;
		this.datas = datas2;
		this.useIntegrateCount = useIntegrateCount;
		mInflate = LayoutInflater.from(context);
		options = new ImageOptions.Builder()
		// 设置加载过程中的图片
				.setLoadingDrawableId(R.drawable.loading)
				// 设置加载失败后的图片
				.setFailureDrawableId(R.drawable.icon_faild)
				// 设置使用缓存
				.setUseMemCache(true)
				// 设置显示圆形图片
				.setCircular(false)
				// 设置支持gif
				.setIgnoreGif(false).build();
	}

	@Override
	public int getCount() {
		return datas.size();
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
		ViewHolder mHolder;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = mInflate.inflate(R.layout.item_lv_shopmall, null);
			mHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			mHolder.tv_integrateCount = (TextView) convertView
					.findViewById(R.id.tv_integrateCount);
			mHolder.iv_icon = (ImageView) convertView
					.findViewById(R.id.iv_icon);
			mHolder.btn_transfer = (Button) convertView
					.findViewById(R.id.btn_transfer);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		final ElementScoreGoodsList model = datas.get(position);
		mHolder.tv_name.setText(model.getGoodsname());
		mHolder.tv_integrateCount.setText(String.valueOf(model.getPoints()));
		x.image().bind(mHolder.iv_icon, model.getPicpath(), options);
		mHolder.btn_transfer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ZhuiHuan(position);
			}
		});

		return convertView;
	}

	/**
	 * 兑换
	 * 
	 * @param position
	 */
	public void ZhuiHuan(final int position) {
		// 进行兑换
		if (Integer.parseInt(datas.get(position).getPoints()) <= useIntegrateCount) {
			// 可以进行兑换
			new AlertDialog(context)
					.builder()
					.setMsg("您将减少" + datas.get(position).getPoints()
							+ "积分,确定兑换?").setTitle("提示")
					.setPositiveButton("确定", new OnClickListener() {

						@Override
						public void onClick(View v) {
							useIntegrateCount -= Integer.parseInt(datas.get(
									position).getPoints());
							// 发起网络请求积分兑换
							requestScoreExchange(position);
						}
					}).setNegativeButton("取消", new OnClickListener() {

						@Override
						public void onClick(View v) {
						}
					}).show();

		} else {
			// 积分不足
			CommonUtils.showToast(context, "积分不足，无法兑换");
		}
	}

	/**
	 * 兑换请求
	 * 
	 * @param position
	 */
	protected void requestScoreExchange(int position) {
		ScoreExchangeRequest request = new ScoreExchangeRequest();
		// DialogUtil.showLoading(context);
		request.setSessionId(ZCApplication.getInstance().userInfo
				.getSessionId());
		request.setId(datas.get(position).getId());
		NetWorkRequestManager.sendRequestPost(context, true, request,
				ScoreExchangeResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						CommonUtils.showToast(context,
								response.getStatusMessage());
					}

					@Override
					public void onFailure(ResponseSupport response) {
					}

					@Override
					public void failure() {
					}
				});

	}

	class ViewHolder {
		TextView tv_name, tv_integrateCount;
		ImageView iv_icon;
		Button btn_transfer;
	}
}
