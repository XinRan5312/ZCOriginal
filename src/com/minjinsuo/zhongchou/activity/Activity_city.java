package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.HashMap;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.model.Model_proviceCityConfig;
import com.minjinsuo.zhongchou.utils.CommonUtils;

public class Activity_city extends Activity_Base {
	private Spinner province_spinner;
	private Spinner city_spinner;
	private Spinner county_spinner;
	private String provinceKey, cityKey;
	private EditText display;
	private String strProvince, strCity, strCounty;
	private Button btnConfirm;

	private ArrayAdapter<String> province_adapter;
	private ArrayAdapter<String> city_adapter;
	private ArrayAdapter<String> county_adapter;
	private boolean hasSelected = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		setTitleBack();
		setTitleText("城市选择");
		loadSpinner();

	}

	private void loadSpinner() {
		// 已经上传成功的已有的省市县
		savedDataMap = (HashMap<String, String>) getIntent()
				.getSerializableExtra("map");
		// 省市县
		if (savedDataMap != null && savedDataMap.size() > 0) {
			provice = savedDataMap.get("provice");
			city = savedDataMap.get("city");
			country = savedDataMap.get("country");
		}

		display = (EditText) findViewById(R.id.display_edit);
		province_spinner = (Spinner) findViewById(R.id.province_spinner);
		province_spinner.setPrompt("省份选择");
		province_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,
				Model_proviceCityConfig.getProvinceValue());
		province_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		province_spinner.setAdapter(province_adapter);
		province_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						provinceKey = Model_proviceCityConfig.getCityKey().get(
								position);
						strProvince = province_spinner.getSelectedItem()
								.toString();

						city_spinner = (Spinner) findViewById(R.id.city_spinner);

						city_spinner.setPrompt("城市选择");
						select(city_spinner, city_adapter,
								Model_proviceCityConfig
										.getProvinceCity(provinceKey));
						city_adapter = new ArrayAdapter<String>(
								Activity_city.this,
								android.R.layout.simple_spinner_item,
								Model_proviceCityConfig
										.getProvinceCity(provinceKey));
						/**
						 * "name" : "曾庆", "maritalstatus" : "1", "hometowncity"
						 * : "350300", "nowcity" : "210700", "nowaddress" :
						 * "Rehnquist", "hometowncounty" : "350304",
						 * "phonenumber" : "13717708741", "hometownaddress" :
						 * "wyuijhg", "qqno" : "1234567893", "sessionId" :
						 * "a536b07a-5318-4228-b3b4-4c45f2c20701", "education" :
						 * "50", "nowprovice" : "210000", "hometownprovice" :
						 * "350000", "nowcountry" : "210711" } 2015-04-10
						 * 11:25:32.796 mobip2p[1437:50041] 响应报文:
						 * {"messageId":"submitPersonInfo","statusCode":0}
						 * 2015-04-10 11:25:32.796 mobip2p[1437:50041] 提交成功
						 * 2015-04-10 11:25:32.806 mobip2p[1437:50041] 发送报文:{
						 * "sessionId" : "a536b07a-5318-4228-b3b4-4c45f2c20701"
						 * 
						 * upload = {"education":"10","hometownaddress":"ee",
						 * "hometowncity":"140100","hometowncounty":"140105",
						 * "hometownprovice"
						 * :"140000","maritalstatus":"0","name":
						 * "崔琦琦","nowaddress"
						 * :"gj","nowcity":"140100","nowcountry"
						 * :"140105","nowprovice"
						 * :"140000","phonenumber":"13717708727"
						 * ,"qqno":"133","messageId":"submitPersonInfo"}
						 * 
						 * }
						 */
						city_adapter
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						city_spinner.setAdapter(city_adapter);
						// city_spinner.setSelection(0, true);
						city_spinner
								.setOnItemSelectedListener(new OnItemSelectedListener() {

									@Override
									public void onItemSelected(
											AdapterView<?> arg0, View arg1,
											int position, long arg3) {

										strCity = city_spinner
												.getSelectedItem().toString();
										cityKey = Model_proviceCityConfig
												.getCityKey(strCity).substring(
														0, 4);
										Log.i("cityKey==", cityKey);
										county_spinner = (Spinner) findViewById(R.id.county_spinner);
										String[] cityCountry = Model_proviceCityConfig
												.getCityCountry(cityKey);

										county_adapter = new ArrayAdapter<String>(
												Activity_city.this,
												android.R.layout.simple_spinner_item,
												cityCountry);
										county_adapter
												.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
										county_spinner
												.setAdapter(county_adapter);
										if (cityCountry.length == 0) {
											// 没有数据
											solveResult(true);
											hasSelected = true;
											return;
										}
										county_spinner
												.setOnItemSelectedListener(new OnItemSelectedListener() {

													@Override
													public void onItemSelected(
															AdapterView<?> arg0,
															View arg1,
															int position,
															long arg3) {
														strCounty = county_spinner
																.getSelectedItem()
																.toString();
														String addressOut = "";
														if (strProvince
																.equals(strCity)) {
															addressOut = strCity
																	+ strCounty;
														} else {
															addressOut = strProvince
																	+ strCity
																	+ strCounty;
														}
														solveResult(false);
													}

													@Override
													public void onNothingSelected(
															AdapterView<?> arg0) {

													}

												});
										if (!hasSelected) {
											hasSelected = true;
											int countryPosition = Model_proviceCityConfig
													.getCountryPosition(city,
															country);
											if (countryPosition != -1) {
												county_spinner
														.setSelection(countryPosition - 1);
											}
										}

									}

									@Override
									public void onNothingSelected(
											AdapterView<?> arg0) {

									}

								});
						if (!hasSelected) {
							int cityPositionPosition = Model_proviceCityConfig
									.getCityPosition(provice, city);
							if (cityPositionPosition != -1) {
								city_spinner.setSelection(cityPositionPosition);
							}
						}

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});

		int provincePosition = Model_proviceCityConfig
				.getProvincePosition(provice);
		if (provincePosition != -1) {
			province_spinner.setSelection(provincePosition);
		}

	}

	String address;
	private HashMap<String, String> savedDataMap;
	private String provice;
	private String city;
	private String country;

	public void solveResult(final boolean isSpecisl) {
		address = strProvince + strCity;
		String addDidplay = strProvince + "-" + strCity;

		if (isSpecisl) {
			// 没有县的特殊情况

		} else {
			address += strCounty;
			addDidplay += "-" + strCounty;
		}

		display.setText(addDidplay);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		btnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				ArrayList<String> strs = new ArrayList<String>();
				strs.add(Model_proviceCityConfig.getProvinceKey(strProvince));
				strs.add(Model_proviceCityConfig.getCityKey(strCity));
				if (isSpecisl) {

				} else {
					strs.add(Model_proviceCityConfig.getCountryKey(strCounty,
							strProvince));
				}
				intent.putStringArrayListExtra("pcc", strs);

				// 下面是众筹使用的回调值
				ArrayList<String> str_name = new ArrayList<String>();
				str_name.add(strProvince);
				str_name.add(strCity);
				intent.putStringArrayListExtra("pcc_str_name", str_name);
				// 众筹项目 添加收货地址走此条路
				if ("Ear01".equals(getIntent().getStringExtra("trEar01"))) {

					int EAR_RESULTCODE = 001;
					intent.putExtra("address", address);
					// intent.setClass(Activity_city.this,
					// Activity_inputInfo1.class);
					setResult(EAR_RESULTCODE, intent);
				} else if ("Ear02"
						.equals(getIntent().getStringExtra("trEar02"))) {
					int EAR_RESULTCODE = 002;
					// intent.putExtra("address", address);
					// intent.setClass(Activity_city.this,
					// Activity_inputInfo1.class);
					// setResult(EAR_RESULTCODE, intent);
				} else if ("Ear03"
						.equals(getIntent().getStringExtra("trEar03"))) {
					//
				}

				finish();
			}
		});
	}

	private void select(Spinner spin, ArrayAdapter<String> adapter,
			String str[]) {
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, str);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(adapter);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}
}
