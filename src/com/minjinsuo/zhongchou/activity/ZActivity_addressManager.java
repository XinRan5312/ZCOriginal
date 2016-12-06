package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.DelUserAddressRequest;
import net.zkbc.p2p.fep.message.protocol.DelUserAddressResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyRecAddressResponse.ElementReceAddressList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SetDefaultAddressRequest;
import net.zkbc.p2p.fep.message.protocol.SetDefaultAddressResponse;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 收货地址列表
 */
public class ZActivity_addressManager extends Activity_Base implements
		OnItemClickListener {
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;
	private ListView lv_address;
	AddressAdapter adapter = new AddressAdapter();
	private List<ElementReceAddressList> list_add = new ArrayList<ElementReceAddressList>();
	private String isFrom_supConfirm = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_address_manager);
		x.view().inject(this);

		if (getIntent() != null) {
			isFrom_supConfirm = getIntent().getStringExtra("from_supConfirm");
		}

		initView();
		initListener();

		getMyGoodsAddress(true);
	}

	@Override
	protected void initView() {
		setTitleBack();
		setTitleText("地址管理");
		setTitleRightDrawable(R.drawable.icon_address_add,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivityForResult(new Intent(getContext(),
								ZActivity_receiverAddress.class), 001);
					}
				});
		lv_address = (ListView) findViewById(R.id.lv_address);
		lv_address.setAdapter(adapter);
		lv_address.setOnItemClickListener(this);
	}

	@Override
	protected void initListener() {

	}

	/*
	 * 获取所有收货地址
	 */
	public void getMyGoodsAddress(boolean isShow) {

		if (ZCApplication.getInstance().getUserInfo() == null
				|| StringUtils.isEmpty(ZCApplication.getInstance()
						.getUserInfo().getSessionId())) {
			return;
		}
		GetMyRecAddressRequest request = new GetMyRecAddressRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetMyRecAddressResponse.class, this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {// 点击事件在adapter中处理
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getMyRecAddress":// 获取收货地址列表
			GetMyRecAddressResponse info = (GetMyRecAddressResponse) response;
			if (info != null && info.getReceAddressList() != null) {
				lv_address.setVisibility(View.VISIBLE);
				noLog.setVisibility(View.GONE);
				list_add = info.getReceAddressList();
				adapter.notifyDataSetChanged();
			} else {
				lv_address.setVisibility(View.GONE);
				noLog.setVisibility(View.VISIBLE);
			}
			DialogUtils.dismisLoading();
			break;

		default:
			break;
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

	/**
	 * 列表数据适配器
	 * 
	 * @author zp
	 *
	 *         2016年10月26日
	 */
	public class AddressAdapter extends BaseAdapter {
		ViewHolder holder = null;
		int clickPosition = -1;
		boolean isClicked;

		@Override
		public int getCount() {
			if (list_add != null && !list_add.isEmpty()) {
				return list_add.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater mInflater = LayoutInflater.from(getContext());
				convertView = mInflater.inflate(R.layout.zitem_address_manage,
						null);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.tv_address = (TextView) convertView
						.findViewById(R.id.tv_address);
				holder.tv_moblie = (TextView) convertView
						.findViewById(R.id.tv_moblie);
				holder.tv_ifdefaultAddress = (TextView) convertView
						.findViewById(R.id.tv_ifdefaultAddress);
				holder.tv_change = (TextView) convertView
						.findViewById(R.id.tv_change);
				holder.tv_delete = (TextView) convertView
						.findViewById(R.id.tv_delete);
				holder.tv_setDefault = (TextView) convertView
						.findViewById(R.id.tv_setDefault);
				holder.rb_setDefault = (RadioButton) convertView
						.findViewById(R.id.rb_setDefault);
				holder.ll_itemClick = (LinearLayout) convertView
						.findViewById(R.id.ll_itemClick);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final ElementReceAddressList item_data = list_add.get(position);

			holder.tv_name.setText(item_data.getRecvNam());
			holder.tv_address.setText(item_data.getCodProv()
					+ item_data.getCodCity() + item_data.getAddr());
			holder.tv_moblie.setText(item_data.getRecvMobile());

			if (!isClicked && item_data.getInUseAdd().equals("1")) {
				holder.tv_ifdefaultAddress.setVisibility(View.VISIBLE);
				holder.rb_setDefault.setChecked(true);
			} else {
				holder.tv_ifdefaultAddress.setVisibility(View.INVISIBLE);
				holder.rb_setDefault.setChecked(false);
			}

			if (clickPosition == position) {
				holder.tv_ifdefaultAddress.setVisibility(View.VISIBLE);
				holder.rb_setDefault.setChecked(true);
			}

			// 1、设置默认地址请求
			final String id = item_data.getSeqNo();
			holder.rb_setDefault.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isClicked = true;
					startRequestSetDefault(id, position);
				}
			});
			holder.tv_setDefault.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					isClicked = true;
					startRequestSetDefault(id, position);
				}
			});

			// 2、删除地址
			holder.tv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					final Dialog dialog_del = DialogUtils.createTwoBtnDiolog(
							ZActivity_addressManager.this, "确定删除改收货地址？");
					dialog_del.findViewById(R.id.btnRight).setOnClickListener(
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									dialog_del.dismiss();
									startRequestDele(id);
								}
							});
				}
			});
			// 3、修改收货地址
			holder.tv_change.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ZActivity_addressManager.this,
							ZActivity_receiverAddress.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("info_address", item_data);
					intent.putExtras(bundle);
					startActivityForResult(intent, 002);
				}
			});

			// 4、点击item回传选择的收货地址到立即支持页面
			holder.ll_itemClick.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 如果是从立即支持点击选择收货地址跳入
					LogUtil.i("选择地址号为==" + list_add.get(position).getSeqNo());
					Intent intent = new Intent();
					intent.putExtra("seqNo", list_add.get(position).getSeqNo());
					intent.putExtra("mobile", list_add.get(position)
							.getRecvMobile());
					intent.putExtra("recvName", list_add.get(position)
							.getRecvNam());
					intent.putExtra("addAll", list_add.get(position)
							.getCodProv()
							+ list_add.get(position).getCodCity()
							+ list_add.get(position).getAddr());
					setResult(RESULT_OK, intent);
					ZActivity_addressManager.this.finish();
				}
			});
			return convertView;
		}

		class ViewHolder {
			TextView tv_name;
			TextView tv_moblie;
			TextView tv_ifdefaultAddress;// 是否默认地址
			TextView tv_address;
			RadioButton rb_setDefault;
			TextView tv_setDefault;
			TextView tv_change;
			TextView tv_delete;
			LinearLayout ll_itemClick;
		}

		/**
		 * 请求设为默认收货地址
		 */
		public void startRequestSetDefault(final String id, final int position) {
			if (ZCApplication.getInstance().getUserInfo() == null
					|| StringUtils.isEmpty(ZCApplication.getInstance()
							.getUserInfo().getSessionId())) {
				return;
			}
			SetDefaultAddressRequest request = new SetDefaultAddressRequest();
			request.setSessionId(ZCApplication.getInstance().getUserInfo()
					.getSessionId());
			request.setSeqNo(id);

			NetWorkRequestManager.sendRequestPost(
					ZActivity_addressManager.this, true, request,
					SetDefaultAddressResponse.class, new MyRequestCallBack() {

						@Override
						public void onSuccess(ResponseSupport response) {
							SetDefaultAddressResponse model = (SetDefaultAddressResponse) response;
							DialogUtils.dismisLoading();
							if (model.getResult().equals("success")) {
								clickPosition = position;
							} else {
								clickPosition = -1;
							}
							adapter.notifyDataSetChanged();
						}

						@Override
						public void onFailure(ResponseSupport response) {
							DialogUtils.dismisLoading();
						}

						@Override
						public void failure() {
							DialogUtils.dismisLoading();
						}
					});
		}

		/**
		 * 删除收货地址
		 */
		public void startRequestDele(String id) {

			if (ZCApplication.getInstance().getUserInfo() == null
					|| StringUtils.isEmpty(ZCApplication.getInstance()
							.getUserInfo().getSessionId())) {
				return;
			}
			DelUserAddressRequest request = new DelUserAddressRequest();
			request.setSessionId(ZCApplication.getInstance().getUserInfo()
					.getSessionId());
			request.setSeqNo(id);

			NetWorkRequestManager.sendRequestPost(
					ZActivity_addressManager.this, true, request,
					DelUserAddressResponse.class, new MyRequestCallBack() {

						@Override
						public void onSuccess(ResponseSupport response) {
							ToastUtil.showToast(ZActivity_addressManager.this,
									"删除成功");
							adapter = new AddressAdapter();// 重新new避免默认收货地址显示错位
							lv_address.setAdapter(adapter);
							getMyGoodsAddress(false);// 获取到最新的收货地址
						}

						@Override
						public void onFailure(ResponseSupport response) {
							DialogUtils.dismisLoading();
						}

						@Override
						public void failure() {
							DialogUtils.dismisLoading();
						}
					});

		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == RESULT_OK) {
			// 修改完地址和新增完地址后都要重新获取地址列表
			getMyGoodsAddress(true);
		}
	}

}