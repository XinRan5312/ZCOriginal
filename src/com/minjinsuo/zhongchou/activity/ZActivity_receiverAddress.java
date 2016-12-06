package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.HashMap;

import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse.ElementReceAddressList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.UpdateUserAddressRequest;
import net.zkbc.p2p.fep.message.protocol.UpdateUserAddressResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 新增/修改 收货地址
 */
public class ZActivity_receiverAddress extends Activity_Base implements
		OnClickListener {
	@ViewInject(R.id.ll_choseCity)
	private LinearLayout ll_choseCity;
	@ViewInject(R.id.etNowAddress)
	private EditText etNowAddress;
	@ViewInject(R.id.et_receiver)
	private EditText et_receiver;
	@ViewInject(R.id.et_phoneNumber)
	private EditText et_phoneNumber;
	@ViewInject(R.id.btnNext)
	private Button btnNext;

	TableRow trEar01;
	TextView tvEar01;
	private UpdateUserAddressRequest request = new UpdateUserAddressRequest();
	private ElementReceAddressList info;// 从上个页面传递而来的原来的收货地址信息——修改收货地址

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_receiver_address);
		x.view().inject(this);

		initView();
		initListener();

		if (getIntent() != null) {// 代表修改收货地址
			info = (ElementReceAddressList) getIntent().getSerializableExtra(
					"info_address");
			if (info != null) {
				initOldData();
			}
		}
	}

	@Override
	protected void initView() {
		setTitleText("收货地址");
		setTitleBack();

		tvEar01 = (TextView) findViewById(R.id.tvEar01);
		// 居住地址省市县
		ll_choseCity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("trEar01", "Ear01");
				if (!StringUtils.isEmpty(request.getCodProv())) {
					// 如果已经设置过省市县传递到选择activity
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("provice", request.getCodProv());
					map.put("city", request.getCodCity());
					intent.putExtra("map", map);
				} else {
					if (info != null) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("provice", info.getCodProv());
						map.put("city", info.getCodCity());
						intent.putExtra("map", map);
					}
				}
				intent.setClass(ZActivity_receiverAddress.this,
						Activity_city.class);
				startActivityForResult(intent, 001);
			}
		});
	}

	@Override
	protected void initListener() {
		btnNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnNext:
			if (StringUtils.isEmpty(tvEar01.getText().toString())) {
				ToastUtil.showToast(ZActivity_receiverAddress.this, "所在地区不能为空");
				return;
			}
			if (StringUtils.isEmpty(etNowAddress.getText().toString())) {
				ToastUtil.showToast(ZActivity_receiverAddress.this, "详细地址不能为空");
				return;
			}
			if (StringUtils.isEmpty(et_receiver.getText().toString())) {
				ToastUtil.showToast(ZActivity_receiverAddress.this, "收货人不能为空");
				return;
			}
			if (StringUtils.isEmpty(et_phoneNumber.getText().toString())) {
				ToastUtil.showToast(ZActivity_receiverAddress.this, "手机号不能为空");
				return;
			}

			if (!StringUtils.isPhoneNum(et_phoneNumber.getText().toString())) {
				ToastUtil
						.showToast(ZActivity_receiverAddress.this, "请输入合法手机号码");
				return;
			}

			subAddressRequest();

			break;

		default:
			break;
		}

	}

	/**
	 * 更新/添加 收货地址
	 */
	public void subAddressRequest() {
		if (ZCApplication.getInstance().getUserInfo() == null
				|| StringUtils.isEmpty(ZCApplication.getInstance()
						.getUserInfo().getSessionId())) {
			return;
		}
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setAddr(etNowAddress.getText().toString());
		request.setRecvMobile(et_phoneNumber.getText().toString());
		request.setRecvNam(et_receiver.getText().toString());
		if (info != null) {// 修改收货地址
			request.setSeqNo(info.getSeqNo());
			request.setSubType("update");
		} else {// 新增
			request.setSubType("add");// 新增
			request.setInUseAdd("0");// 是否默认地址（是-1 否-0）
		}

		NetWorkRequestManager.sendRequestPost(this, true, request,
				UpdateUserAddressResponse.class, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 001) {
			if (!StringUtils.isEmpty(data.getStringExtra("address"))) {
				String address = data.getStringExtra("address");
				ArrayList<String> strs = data
						.getStringArrayListExtra("pcc_str_name");
				request.setCodProv(strs.get(0));
				request.setCodCity(strs.get(1));
				LogUtil.i("选择的收货地址==" + strs.get(0) + "|" + strs.get(1));
				switch (strs.size()) {
				case 2:
					// rbr.setNowcountry("");
					break;
				default:
					// rbr.setNowcountry(strs.get(2));
					break;
				}
				if (address.length() >= 10) {
					tvEar01.setText(address.substring(0, 10) + "...");
				} else {
					tvEar01.setText(address);
				}
			}
		}
		/*
		 * if (StringUtils.isNotBlank(data.getStringExtra("address"))) { String
		 * address = data.getStringExtra("address"); if (address.length() >= 10)
		 * { tvEar02.setText(address.substring(0, 10) + "..."); } else {
		 * tvEar02.setText(address); } }
		 */

		if (resultCode == AppContants.ACT_REQUEST_CODE) {
			setResult(resultCode, data);
			finish();
		}

	}

	/**
	 * 更新收货地址用
	 */
	public void initOldData() {
		tvEar01.setText(info.getCodProv() + info.getCodCity());
		etNowAddress.setText(info.getAddr());
		et_receiver.setText(info.getRecvNam());
		et_phoneNumber.setText(info.getRecvMobile());

		request.setCodProv(info.getCodProv());
		request.setCodCity(info.getCodCity());
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		DialogUtils.dismisLoading();
		UpdateUserAddressResponse model = (UpdateUserAddressResponse) response;
		if (model.getMessage().equals("操作成功")) {
			ToastUtil.showToast(ZActivity_receiverAddress.this, "操作成功");
			setResult(RESULT_OK);
			finish();
		} else {
			ToastUtil.showToast(ZActivity_receiverAddress.this,
					model.getMessage());
		}
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}
}
