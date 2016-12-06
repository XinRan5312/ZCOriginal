package com.minjinsuo.zhongchou.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetRedMoneyListResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SearchInvestRedMoneyListRequest;
import net.zkbc.p2p.fep.message.protocol.SearchInvestRedMoneyListResponse;
import net.zkbc.p2p.fep.message.protocol.SearchInvestRedMoneyListResponse.ElementInvestRedMoneyList;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;

/**
 * 可用红包
 * 
 * @author zp
 *
 *         2016年9月14日
 */
public class Activity_EnableRedBag extends Activity_Base implements
		OnItemClickListener {
	@ViewInject(R.id.lv_product)
	private PullToRefreshListView lv_list_Pro;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;
	// @ViewInject(R.id.redbag_ruler)
	// private TextView redbag_ruler;
	@ViewInject(R.id.title_bar)
	private RelativeLayout title_bar;

	private Adapter_MyRedBag adapter_list;
	private List<ElementInvestRedMoneyList> list = new ArrayList<ElementInvestRedMoneyList>();
	private GetRedMoneyListResponse model;

	private String investAmount = "";
	private ArrayList<String> strList = new ArrayList<String>();// 保存选中的红包id
	private ArrayList<String> strAmountList = new ArrayList<String>();// 保存选中的红包金额
	private double amount_red = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.fragment_redbag_child);
		x.view().inject(this);
		initView();
		initData();
		initListener();

		getRedBagEnable();
	}

	@Override
	protected void initView() {
		title_bar.setVisibility(View.VISIBLE);
		setTitleText("可用红包");
		setTitleBack();
		// redbag_ruler.setVisibility(View.GONE);

		if (getIntent() != null) {
			investAmount = getIntent().getStringExtra("investAmount");
			LogUtil.i("传递来的投资金额==" + investAmount);
		}

		setTitleRight("确定", new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 先判断选择红包总额是否大于投资金额
				if (startJudge()) {

					// 需要把adapter中选择的红包id数据传回上个页面（可以多选）
					Intent intent = new Intent();
					LogUtil.i("选中的红包id==" + JSON.toJSONString(strList));
					intent.putStringArrayListExtra("redBagList", strList);
					intent.putExtra("amount_red", amount_red);
					setResult(RESULT_OK, intent);
					finish();
				} else {
					finish();
				}

			}
		});

	}

	/**
	 * 判断选中的红包金额是否大于投资金额
	 * 
	 * @return
	 */
	private boolean startJudge() {
		amount_red = 0;
		if (strAmountList == null) {
			return false;
		}
		for (int i = 0; i < strAmountList.size(); i++) {
			amount_red += Double.parseDouble(strAmountList.get(i));
		}

		LogUtil.i("红包总额==" + amount_red + "");
		if (amount_red > Double.parseDouble(investAmount)) {
			DialogUtils.createOneBtnDiolog(this, "红包总额不能大于投资金额");
			return false;
		}
		return true;
	}

	@Override
	protected void initData() {
		lv_list_Pro.setMode(Mode.DISABLED);

		adapter_list = new Adapter_MyRedBag(this);
		lv_list_Pro.setAdapter(adapter_list);
		lv_list_Pro.setOnItemClickListener(this);
	}

	@Override
	protected void initListener() {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	/**
	 * 获取可用红包
	 */
	public void getRedBagEnable() {
		SearchInvestRedMoneyListRequest request = new SearchInvestRedMoneyListRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setAmount(investAmount);
		NetWorkRequestManager.sendRequestPost(this, true, request,
				SearchInvestRedMoneyListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		SearchInvestRedMoneyListResponse model = (SearchInvestRedMoneyListResponse) response;
		if (model != null && model.getInvestRedMoneyList() != null) {
			lv_list_Pro.setVisibility(View.VISIBLE);
			noLog.setVisibility(View.GONE);

			adapter_list.deleteData();
			adapter_list.setData(model.getInvestRedMoneyList());
			adapter_list.notifyDataSetChanged();
		} else {
			lv_list_Pro.setVisibility(View.GONE);
			noLog.setVisibility(View.VISIBLE);
		}

		DialogUtils.dismisLoading();
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
	}

	/**
	 * 可用红包列表适配器
	 * 
	 * @author zp
	 *
	 *         2016年10月26日
	 */
	private class Adapter_MyRedBag extends BaseAdapter {

		public Context context;
		private ArrayList<ElementInvestRedMoneyList> tenderList;
		private boolean history;

		public Adapter_MyRedBag(Context context) {
			super();
			this.context = context;
			// 初始化
			tenderList = new ArrayList<ElementInvestRedMoneyList>();
		}

		public void setData(List<ElementInvestRedMoneyList> tenderList) {
			if (tenderList != null) {
				this.tenderList.addAll(tenderList);
			}
		}

		public ArrayList<ElementInvestRedMoneyList> getData() {
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
				mHolder.cb_select = (CheckBox) convertView
						.findViewById(R.id.cb_select);
				mHolder.ll_main = (LinearLayout) convertView
						.findViewById(R.id.ll_main);

				convertView.setTag(mHolder);
			} else {
				mHolder = (ViewHolder) convertView.getTag();
			}

			mHolder.cb_select.setVisibility(View.VISIBLE);
			mHolder.hongbao_bg.setBackgroundResource(R.drawable.redbag_right);
			mHolder.name_redbag.setTextColor(context.getResources().getColor(
					R.color.c_fc6));

			final ElementInvestRedMoneyList info = tenderList.get(position);

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
				String userange = info.getUserange();// 使用范围
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

				mHolder.cb_select
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									info.setChecked(true);
									if (!strList.contains(info.getId())) {
										strList.add(info.getId());
										strAmountList.add(info.getAmount());
									}
								} else {
									info.setChecked(false);
									if (strList.contains(info.getId())) {
										strList.remove(info.getId());
										strAmountList.remove(info.getAmount());
									}
								}

								LogUtil.i("选中的红包金额共=="
										+ JSON.toJSONString(strAmountList));
							}
						});

				if (info.isChecked()) {
					mHolder.cb_select.setChecked(true);
				} else {
					mHolder.cb_select.setChecked(false);
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


		// string类型转换为date类型
		// strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd
		// HH:mm:ss//yyyy年MM月dd日
		// HH时mm分ss秒，
		// strTime的时间格式必须要与formatType的时间格式相同
		public String stringToDate(String strTime, String formatType)
				throws ParseException, java.text.ParseException {
			SimpleDateFormat formatter = new SimpleDateFormat(formatType);
			Date date = null;
			date = formatter.parse(strTime);
			return new SimpleDateFormat("yyyy-MM-dd").format(date);
		}


		class ViewHolder {
			private TextView limit_redbag, name_redbag, time_redbag,
					norm_redbag, vowel_redbag;
			private LinearLayout hongbao_bg, ll_main;
			private CheckBox cb_select;
		}

	}

}
