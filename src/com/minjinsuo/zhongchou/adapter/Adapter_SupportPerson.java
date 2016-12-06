package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjCustBuyByPrjIdResponse.ElementPrjCustBuyList;

import org.xutils.x;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 支持人员列表(产品和股权公用)
 */
public class Adapter_SupportPerson extends BaseAdapter {

	public Context context;
	private ArrayList<ElementPrjCustBuyList> tenderList;
	private int flag = 0;// 0:产品，1：股权
	private boolean isHasLingTou;// 是否有领投人

	public Adapter_SupportPerson(Context context, int flag, boolean isHasLingTou) {
		super();
		this.context = context;
		this.flag = flag;
		this.isHasLingTou = isHasLingTou;
		// 初始化
		tenderList = new ArrayList<ElementPrjCustBuyList>();
	}

	public void setData(List<ElementPrjCustBuyList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
	}

	public ArrayList<ElementPrjCustBuyList> getData() {
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
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_support_person, null);
			holder.title = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_zhichi = (TextView) convertView.findViewById(R.id.zhichi);
			holder.amount = (TextView) convertView.findViewById(R.id.tv_amount);
			holder.tv_supportCnt = (TextView) convertView
					.findViewById(R.id.tv_supportCnt);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.iv_ifLingTouRen = (ImageView) convertView
					.findViewById(R.id.iv_ifLingTouRen);
			holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
			holder.btn_apply = (Button) convertView
					.findViewById(R.id.btn_apply);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (tenderList.get(position) != null) {

			if (flag == 1) {
				if (position == 0 && isHasLingTou) {
					holder.iv_ifLingTouRen.setVisibility(View.VISIBLE);
				} else {
					// position += 1;// 因为0这个位置已被默认申请领投按钮代替
					holder.iv_ifLingTouRen.setVisibility(View.GONE);
				}
			}
			holder.title.setText(tenderList.get(position).getCustLoginNam());
			holder.tv_time.setText(StringUtils.getDate(tenderList.get(position)
					.getBuyTime(), 2));
			// 下面计算每份金额
			Double perAmt = Double.parseDouble(tenderList.get(position)
					.getTotalPrice() == null ? "0" : tenderList.get(position)
					.getTotalPrice())
					/ Double.parseDouble(tenderList.get(position).getSuppCnt() == null ? "1"
							: tenderList.get(position).getSuppCnt());
			holder.amount.setText(perAmt + "元");
			holder.tv_supportCnt.setText("×"
					+ (tenderList.get(position).getSuppCnt() == null ? "0"
							: tenderList.get(position).getSuppCnt()));
			x.image().bind(holder.iv_head,
					tenderList.get(position).getCustPortraitAddr());

			holder.btn_apply.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					ToastUtil.showToast(context, "跳转到领头人详情页进行申请领投");
				}
			});
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
		private TextView title, amount, tv_supportCnt, tv_time, tv_zhichi;
		private ImageView iv_ifLingTouRen, iv_head;
		private Button btn_apply;
	}

}
