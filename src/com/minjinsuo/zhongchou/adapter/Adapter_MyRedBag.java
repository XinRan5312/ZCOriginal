package com.minjinsuo.zhongchou.adapter;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetRedMoneyListResponse.ElementRedMoneyList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 我的红包数据适配器
 */
public class Adapter_MyRedBag extends BaseAdapter {

	public Context context;
	private ArrayList<ElementRedMoneyList> tenderList;
	private boolean history;

	public Adapter_MyRedBag(Context context, boolean history) {
		super();
		this.context = context;
		this.history = history;
		// 初始化
		tenderList = new ArrayList<ElementRedMoneyList>();
	}

	public void setData(List<ElementRedMoneyList> tenderList) {
		if (tenderList != null) {
			this.tenderList.addAll(tenderList);
		}
	}

	public ArrayList<ElementRedMoneyList> getData() {
		return tenderList;
	}

	public void deleteData() {
		if (this.tenderList != null && this.tenderList.size() > 0) {
			this.tenderList.clear();
		}
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
		ViewHolder mHolder = null;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_redbag, null, false);
			mHolder.limit_redbag = (TextView) convertView
					.findViewById(R.id.limit_redbag);
			mHolder.vowel_redbag = (TextView) convertView
					.findViewById(R.id.vowel_redbag);
			mHolder.name_redbag = (TextView) convertView
					.findViewById(R.id.name_redbag);
			mHolder.time_redbag = (TextView) convertView
					.findViewById(R.id.time_redbag);
			mHolder.norm_redbag = (TextView) convertView
					.findViewById(R.id.norm_redbag);
			mHolder.hongbao_bg = (LinearLayout) convertView
					.findViewById(R.id.hongbao_bg);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}

		if (history) {
			mHolder.hongbao_bg
					.setBackgroundResource(R.drawable.redbag_right_hui);
			mHolder.name_redbag.setTextColor(context.getResources().getColor(
					R.color.c_d2));
		} else {
			mHolder.hongbao_bg.setBackgroundResource(R.drawable.redbag_right);
			mHolder.name_redbag.setTextColor(context.getResources().getColor(
					R.color.c_fc6));
		}

		ElementRedMoneyList info = tenderList.get(position);

		if (info != null) {
			String name = info.getName();
			mHolder.name_redbag.setText(name);

			String startdate = info.getStartdate();
			String enddate = info.getEnddate();
			try {
				mHolder.time_redbag.setText(stringToDate(startdate,
						"yyyy-MM-dd")
						+ "-"
						+ stringToDate(enddate, "yyyy-MM-dd"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String userange = info.getUserange();
			mHolder.norm_redbag.setText(userange);

			String amount = info.getAmount();
			String way = info.getWay() == null ? "" : info.getWay();
			if ("加息券".equals(way)) {
				mHolder.vowel_redbag.setVisibility(View.GONE);
				mHolder.limit_redbag.setText(amount + "%");
			} else {
				if (amount.endsWith(".00")) {
					amount = amount.replace(".00", "");
				}
				mHolder.vowel_redbag.setVisibility(View.VISIBLE);
				mHolder.limit_redbag.setText(amount);
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

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	// string类型转换为date类型
	// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
	// HH时mm分ss秒，
	// strTime的时间格式必须要与formatType的时间格式相同
	public static String stringToDate(String strTime, String formatType)
			throws ParseException, java.text.ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		Date date = null;
		date = formatter.parse(strTime);
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	// date类型转换为String类型
	// formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	// data Date类型的时间
	public static String dateToString(Date data, String formatType) {
		return new SimpleDateFormat(formatType).format(data);
	}

	class ViewHolder {
		private TextView limit_redbag, name_redbag, time_redbag, norm_redbag,
				vowel_redbag;
		private LinearLayout hongbao_bg;
	}

}
