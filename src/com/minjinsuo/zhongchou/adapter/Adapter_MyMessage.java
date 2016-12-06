package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.DeletMessageRequest;
import net.zkbc.p2p.fep.message.protocol.DeletMessageResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_MsgDetail;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_msg;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 站内信列表专用——p2p老版UI样式（带侧滑，但会与tab活动冲突）;【改用 HorizontalSlideAdapter】
 * 
 * @author zp
 *
 */
public class Adapter_MyMessage extends BaseAdapter {

	public Context context;
	private ArrayList<Model_msg> messageList;

	public Adapter_MyMessage(Context context) {
		super();
		this.context = context;
		// 初始化
		messageList = new ArrayList<Model_msg>();
	}

	public void addDate(ArrayList<Model_msg> investList) {
		if (investList != null) {
			this.messageList.addAll(investList);
		}
	}

	public void deleteData() {
		if (messageList != null) {
			this.messageList.clear();
		}
	}

	public ArrayList<Model_msg> getData() {
		return messageList;
	}

	@Override
	public int getCount() {
		if (messageList != null) {
			return messageList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return messageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_my_msgsite, null);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.lv_item_icon_0 = (LinearLayout) convertView
					.findViewById(R.id.lv_item_icon_0);
			holder.lv_item_icon_1 = (LinearLayout) convertView
					.findViewById(R.id.lv_item_icon_1);
			holder.ll_menu = (LinearLayout) convertView
					.findViewById(R.id.ll_menu);
			holder.ll_leftMain = (LinearLayout) convertView
					.findViewById(R.id.ll_leftMain);
			holder.fl_Click = (FrameLayout) convertView
					.findViewById(R.id.fl_Click);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Model_msg model = messageList.get(position);
		holder.tv_time.setText(messageList.get(position).getTime());
		holder.tv_title.setText(messageList.get(position).getType());
		if ("0".equals(messageList.get(position).getTatus())) {
			// 未读
			holder.lv_item_icon_0.setVisibility(View.VISIBLE);
			holder.lv_item_icon_1.setVisibility(View.INVISIBLE);
		} else {
			holder.lv_item_icon_1.setVisibility(View.VISIBLE);
			holder.lv_item_icon_0.setVisibility(View.INVISIBLE);
		}
		OnClickListener mOnClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.fl_Click:
				case R.id.ll_leftMain:// 跳转详情
					Intent intent = new Intent(context,
							Activity_MsgDetail.class);
					intent.putExtra("id", model.getId());
					intent.putExtra("content", model.getDetail());
					model.setTatus("1");
					intent.putExtra(AppContants.FROM, "innerMail");
					context.startActivity(intent);
					notifyDataSetChanged();
					break;
				case R.id.ll_menu:// 删除
					startRequestDeleteMsg(messageList.get(position).getId());
					messageList.remove(position);
					notifyDataSetChanged();
					break;
				default:
					break;
				}

			}
		};
		holder.fl_Click.setOnClickListener(mOnClick);
		holder.ll_leftMain.setOnClickListener(mOnClick);
		holder.ll_menu.setOnClickListener(mOnClick);
		// 添加长按删除
		holder.ll_leftMain.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				new AlertDialog(context).builder().setMsg("是否删除该消息记录？")
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startRequestDeleteMsg(messageList.get(position)
										.getId());
								messageList.remove(position);
								notifyDataSetChanged();
							}
						}).setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();

				return false;
			}
		});
		return convertView;
	}

	public static class ViewHolder {
		public TextView tv_time, tv_title;
		public LinearLayout lv_item_icon_0, lv_item_icon_1, ll_menu,
				ll_leftMain;
		public FrameLayout fl_Click;
	}

	protected void startRequestDeleteMsg(String msgId) {
		DeletMessageRequest request = new DeletMessageRequest();
		request.setSessionId(ZCApplication.getInstance().userInfo
				.getSessionId());
		request.setId(msgId);
		NetWorkRequestManager.sendRequestPost(context, true, request,
				DeletMessageResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						DialogUtils.dismisLoading();
						ToastUtil.showToast(context, "删除成功");
						// datas.remove(position);
						// swipeLayout.close();
						// notifyDataSetChanged();
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
