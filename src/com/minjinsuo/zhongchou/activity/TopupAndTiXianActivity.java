package com.minjinsuo.zhongchou.activity;

import java.io.UnsupportedEncodingException;

import net.zkbc.p2p.fep.message.protocol.GetCashApplyStatusRequest;
import net.zkbc.p2p.fep.message.protocol.GetCashApplyStatusResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyAccountResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.GetThirdAmountManager;
import com.minjinsuo.zhongchou.http.GetThirdAmountManager.GetThirdAmountManagerCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.QueryMoney;
import com.minjinsuo.zhongchou.service.Service_ThirdPay;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 提现、充值公用
 * 
 */
public class TopupAndTiXianActivity extends Activity_Base implements
		OnRefreshListener<ScrollView> {
	@ViewInject(R.id.lv_list)
	private PullToRefreshScrollView lv_list;
	@ViewInject(R.id.tv_balamount)
	private TextView tv_balamount;
	@ViewInject(R.id.et_money)
	private EditText et_money;
	@ViewInject(R.id.ll_status_withdraw)
	private LinearLayout ll_status_withdraw;
	@ViewInject(R.id.tv_status)
	private TextView tv_status;
	@ViewInject(R.id.bt_tixian)
	private Button bt_tixian;

	private String money;
	private String bankstat;
	String strRuleString = null;
	private static final int REQUEST_CODE_TIXIAN = 1;
	private static final int REQUEST_CODE_TOPUP = 3;
	private static final int REQUEST_CODE_BIND = 2;
	private static final String APPLY_AMT = "apply_amt";
	// 提现充值标志，0 提现， 1充值
	int flag = 0;
	GetMyAccountResponse p2pUser = null;
	private String status_cach = "";// 体现状态 00-初始化 10-申请中 20-批准 30-否决

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tixian);
		x.view().inject(this);

		initView();
		initData();
		initListener();

		getBalamout();
	}

	@Override
	protected void initView() {
		setTitleBack();
		int flags = getIntent().getFlags();
		if (flags == 0) {
			// 提现
			setTitleText("提现");
			flag = flags;
			lv_list.setMode(Mode.PULL_DOWN_TO_REFRESH);
			lv_list.setOnRefreshListener(this);
		} else {
			setTitleText("充值");
			flag = flags;
			lv_list.setMode(Mode.DISABLED);
			lv_list.setOnRefreshListener(this);
		}

		p2pUser = ZCApplication.getInstance().getUserInfo();
		bankstat = p2pUser.getBankstat();
		if ("0".equals(bankstat)) {// 未绑定
			et_money.setInputType(InputType.TYPE_NULL);
			bt_tixian.setClickable(false);
		}
	}

	@Override
	protected void initData() {
		TextView tvFalgStr = (TextView) findViewById(R.id.tvFalgStr);
		if (flag == 0) {
			tvFalgStr.setText("提现金额");
			bt_tixian.setText("提现");
		} else {
			tvFalgStr.setText("充值金额");
			bt_tixian.setText("充值");
			et_money.setHint("");
		}

		TextView tView = (TextView) findViewById(R.id.tvStatement);

		if (StringUtils.isEmpty(strRuleString)) {
			byte b[] = CommonUtils.getData(TopupAndTiXianActivity.this,
					"payStatement.txt");
			String str = "";
			try {
				str = new String(b, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			strRuleString = str;
		}
		tView.setText(strRuleString);
	}

	@Override
	protected void initListener() {
		bt_tixian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				money = et_money.getText().toString().trim();

				if (StringUtils.isEmpty(money)) {
					Toast.makeText(getApplicationContext(), "请输入金额",
							Toast.LENGTH_SHORT).show();
					et_money.setText("");
					et_money.requestFocus();
					return;
				}

				if (money.equals(".") || !StringUtils.isMoneyVerify(money)) {
					Toast.makeText(getApplicationContext(), "输入金额格式不正确",
							Toast.LENGTH_SHORT).show();
					et_money.setText("");
					et_money.requestFocus();
					return;
				}

				double i_money = StringUtils.changeToDouble(money);// 输入的金额
				if (i_money == 0) {
					Toast.makeText(getApplicationContext(), "金额必须大于0",
							Toast.LENGTH_SHORT).show();
					et_money.setText("");
					et_money.requestFocus();
					return;
				}

				if (!StringUtils.isEmpty(ZCApplication.getInstance()
						.getUserInfo().getMaxCash())
						&& i_money > StringUtils.changeToDouble(ZCApplication
								.getInstance().getUserInfo().getMaxCash())
						&& flag == 0) {// 金额大于最大提现金额限制
					Toast.makeText(
							getApplicationContext(),
							"提现金额不得高于"
									+ ZCApplication.getInstance().getUserInfo()
											.getMaxCash() + "元",
							Toast.LENGTH_SHORT).show();
					et_money.setText("");
					et_money.requestFocus();
					return;
				}

				if (!StringUtils.isEmpty(ZCApplication.getInstance()
						.getUserInfo().getMinCash())
						&& i_money < StringUtils.changeToDouble(ZCApplication
								.getInstance().getUserInfo().getMinCash())
						&& flag == 0) {// 金额小于最小提现金额限制
					Toast.makeText(
							getApplicationContext(),
							"提现金额不得低于"
									+ ZCApplication.getInstance().getUserInfo()
											.getMinCash() + "元",
							Toast.LENGTH_SHORT).show();
					et_money.setText("");
					et_money.requestFocus();
					return;
				}

				if (!StringUtils.isEmpty(ZCApplication.getInstance()
						.getUserInfo().getUsableCashCount())
						&& Integer.parseInt(ZCApplication.getInstance()
								.getUserInfo().getUsableCashCount()) <= 0
						&& flag == 0) {
					Toast.makeText(getApplicationContext(), "您今日提现次数已用完",
							Toast.LENGTH_SHORT).show();
					et_money.setText("");
					et_money.requestFocus();
					return;
				}

				if (flag == 0) {
					if (StringUtils.isEmpty(tv_balamount.getText().toString()
							.trim())) {
						ToastUtil.showToast(TopupAndTiXianActivity.this,
								"可用余额获取失败，请重试");
						return;
					}

					if (Double.parseDouble(money) > Double
							.parseDouble(tv_balamount.getText().toString()
									.trim())) {
						ToastUtil.showToast(TopupAndTiXianActivity.this,
								"可用余额不足");
						return;
					}

					SharedPreferUtils.putValue(TopupAndTiXianActivity.this,
							APPLY_AMT, APPLY_AMT, money);
					String url = Service_ThirdPay.TIXIAN
							+ "sessionId="
							+ ZCApplication.getInstance().getUserInfo()
									.getSessionId() + "&transAmt=" + money
							+ "&maxCash=" + p2pUser.getMaxCash() + "&minCash="
							+ p2pUser.getMinCash() + "&usableCashCount="
							+ p2pUser.getUsableCashCount();
					Intent intent = new Intent(TopupAndTiXianActivity.this,
							Activity_ThirdWeb.class);
					intent.putExtra(Activity_ThirdWeb.URL, url);
					intent.putExtra(Activity_ThirdWeb.TITLENAME, "提现");
					startActivityForResult(intent, REQUEST_CODE_TIXIAN);
				} else {
					GetMyAccountResponse p2pUser = ZCApplication.getInstance()
							.getUserInfo();
					String url = "";
					// 汇付天下，托管类型
					url = Service_ThirdPay.TOPUP
							+ "sessionId="
							+ ZCApplication.getInstance().getUserInfo()
									.getSessionId() + "&transAmt=" + money;
					Intent intent = new Intent(TopupAndTiXianActivity.this,
							Activity_ThirdWeb.class);
					intent.putExtra(Activity_ThirdWeb.URL, url);
					intent.putExtra(Activity_ThirdWeb.TITLENAME, "充值");
					startActivityForResult(intent, REQUEST_CODE_TOPUP);
				}
			}
		});
	}

	/**
	 * 获取用户提现状态（第一次点击提现为申请，只有申请通过后才能进行体现）
	 */
	public void getCashApplyStatus(boolean isShow) {
		GetCashApplyStatusRequest request = new GetCashApplyStatusRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetCashApplyStatusResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
		GetCashApplyStatusResponse model = (GetCashApplyStatusResponse) response;
		if (model != null && !StringUtils.isEmpty(model.getWdAppStatus())) {
			status_cach = model.getWdAppStatus();
			if (flag == 0) {
				if (status_cach.equals("10")) {// 审核中
					ll_status_withdraw.setVisibility(View.VISIBLE);
					tv_status.setText("审核中");
					bt_tixian.setBackgroundResource(R.drawable.shap_btn_gray);
					bt_tixian.setEnabled(false);
					et_money.setEnabled(false);
					et_money.setText(SharedPreferUtils.getValue(
							TopupAndTiXianActivity.this, APPLY_AMT, APPLY_AMT,
							""));// 金额是之前提交时的金额，不可编辑
				} else if (status_cach.equals("20")) {// 已批准
					ll_status_withdraw.setVisibility(View.VISIBLE);
					tv_status.setText("已批准");
					bt_tixian.setBackgroundResource(R.drawable.selector_btn);
					bt_tixian.setEnabled(true);
					et_money.setEnabled(false);
					et_money.setText(SharedPreferUtils.getValue(
							TopupAndTiXianActivity.this, APPLY_AMT, APPLY_AMT,
							""));
				} else {// 初始状态或被否决后
					if (status_cach.equals("30")) {
						ToastUtil.showToast(TopupAndTiXianActivity.this, "上次"
								+ et_money.getText().toString().trim()
								+ "元提现申请已被否决，请重试");
					}
					ll_status_withdraw.setVisibility(View.GONE);
					bt_tixian.setBackgroundResource(R.drawable.selector_btn);
					bt_tixian.setEnabled(true);
					et_money.setEnabled(true);
					et_money.setText("");
				}

			}
		}
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		lv_list.onRefreshComplete();
	}

	/**
	 * 专门用接口来请求数据获取实时余额
	 */
	public void getBalamout() {
		GetThirdAmountManager.getThirdAmtRequest(TopupAndTiXianActivity.this,
				true, new GetThirdAmountManagerCallBack() {

					@Override
					public void onSuccess(QueryMoney response) {
						// tv_balamount.setText(response.getReq().get("balance"));
						tv_balamount.setText(response.getAvailableAmount());

						if (flag == 0) {
							getCashApplyStatus(false);
						} else {
							DialogUtils.dismisLoading();
						}
					}

					@Override
					public void onFailure() {
						DialogUtils.dismisLoading();
					}

					@Override
					public void onFailure(String errorMsg) {
						DialogUtils.dismisLoading();
						ToastUtil.showToast(TopupAndTiXianActivity.this,
								errorMsg);
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_TIXIAN
				|| requestCode == REQUEST_CODE_TOPUP) {
			if (resultCode == RESULT_OK) {
				if (status_cach.equals("20")) {// 如果从已批准状态提现成功，则恢复初始化
					ll_status_withdraw.setVisibility(View.GONE);
					bt_tixian.setBackgroundResource(R.drawable.selector_btn);
					bt_tixian.setEnabled(true);
					et_money.setEnabled(true);
					et_money.setText("");
					SharedPreferUtils.putValue(TopupAndTiXianActivity.this,
							APPLY_AMT, APPLY_AMT, "");
				}
				Toast.makeText(getApplicationContext(), "交易成功",
						Toast.LENGTH_LONG).show();
				TopupAndTiXianActivity.this.setResult(RESULT_OK);
				TopupAndTiXianActivity.this.finish();
			} else {
				// 处理审核提示后，进行刷新该页面
				getCashApplyStatus(true);
			}
		} else if (requestCode == REQUEST_CODE_BIND) {
			switch (resultCode) {
			case RESULT_OK:
				Bundle bundle = data.getExtras();
				String status = bundle.getString("status");
				if ("1".equals(status)) {// 成功
					Toast.makeText(getApplicationContext(), "绑定成功",
							Toast.LENGTH_LONG).show();
					ZCApplication.getInstance().getUserInfo().setBankstat("1");
					// rl_bind.setVisibility(View.GONE);
					et_money.setInputType(InputType.TYPE_CLASS_NUMBER);
					bt_tixian.setClickable(true);

				} else {// 失败
					Toast.makeText(getApplicationContext(), "绑定失败",
							Toast.LENGTH_LONG).show();
				}
				break;
			}
		}

	}

	@Override
	public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
		getBalamout();
	}

}
