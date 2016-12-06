package com.minjinsuo.zhongchou.adapter;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Adapter_RedBag extends BaseAdapter {
	private List<HashMap<String, String>> list_arrow;
	private Context context;
	private boolean history;
	private double money;

	public Adapter_RedBag(Context context, boolean history, double money) {
		this.context = context;
		this.history = history;
		this.money = money;
	}

	public List<HashMap<String, String>> getList_arrow() {
		return list_arrow;
	}

	public void setList_arrow(List<HashMap<String, String>> list_arrow) {
		this.list_arrow = list_arrow;
	}

	@Override
	public int getCount() {
		if (list_arrow != null) {
			return list_arrow.size();
		} else {
			return 10;
		}
	}

	@Override
	public Object getItem(int position) {
		return list_arrow.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_redbag, null, false);
			mHolder.limit_redbag = (TextView) convertView.findViewById(R.id.limit_redbag);
			mHolder.vowel_redbag = (TextView) convertView.findViewById(R.id.vowel_redbag);
			mHolder.name_redbag = (TextView) convertView.findViewById(R.id.name_redbag);
			mHolder.time_redbag = (TextView) convertView.findViewById(R.id.time_redbag);
			mHolder.norm_redbag = (TextView) convertView.findViewById(R.id.norm_redbag);
			mHolder.hongbao_bg = (LinearLayout) convertView.findViewById(R.id.hongbao_bg);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		if (history) {
			mHolder.hongbao_bg.setBackgroundResource(R.drawable.redbag_right_hui);
			mHolder.name_redbag.setTextColor(context.getResources().getColor(R.color.c_d2));
		} else {
			mHolder.hongbao_bg.setBackgroundResource(R.drawable.redbag_right);
			mHolder.name_redbag.setTextColor(context.getResources().getColor(R.color.c_fc6));
		}
		if (list_arrow != null) {
			if (list_arrow.get(position).containsKey("startdate")
					&& !StringUtils.isEmpty(list_arrow.get(position).get("startdate"))
					&& list_arrow.get(position).containsKey("enddate")
					&& !StringUtils.isEmpty(list_arrow.get(position).get("enddate"))
					&& list_arrow.get(position).containsKey("requirement")
					&& !StringUtils.isEmpty(list_arrow.get(position).get("requirement"))
					&& list_arrow.get(position).containsKey("amount")
					&& !StringUtils.isEmpty(list_arrow.get(position).get("amount"))
					&& list_arrow.get(position).containsKey("name")
					&& !StringUtils.isEmpty(list_arrow.get(position).get("name"))
					&& list_arrow.get(position).containsKey("way")
					&& !StringUtils.isEmpty(list_arrow.get(position).get("way"))) {
				String startdate = list_arrow.get(position).get("startdate");
				String enddate = list_arrow.get(position).get("enddate");
				try {
					mHolder.time_redbag
							.setText(stringToDate(startdate, "yyyy-MM-dd") + "-" + stringToDate(enddate, "yyyy-MM-dd"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String userange = list_arrow.get(position).get("userange");
				mHolder.norm_redbag.setText(userange);
				String way = list_arrow.get(position).get("way");
				String amount = list_arrow.get(position).get("amount");
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
				String name = list_arrow.get(position).get("name");
				mHolder.name_redbag.setText(name);
			} else {
				Log.e("RedBagAdapter", "list_arrow Data Error.some key does not exist");
			}
		} else {
			Log.e("RedBagAdapter", "list_arrow is NULL");
		}
		return convertView;
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
		private TextView limit_redbag, name_redbag, time_redbag, norm_redbag, vowel_redbag;
		private LinearLayout hongbao_bg;
	}

}
