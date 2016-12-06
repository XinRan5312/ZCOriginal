package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.DeletMessageRequest;
import net.zkbc.p2p.fep.message.protocol.DeletMessageResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_MsgDetail;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_msg;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.SimpleSwipeListener;
import com.minjinsuo.zhongchou.view.SwipeLayout;

/**
 * 
 * 消息——站内信独用
 */
public class ListViewAdapter extends BaseSwipeAdapter {
	ArrayList<Model_msg> datas;
	// 上下文对象
	private Context mContext;

	String sessionId;

	// 构造函数
	public ListViewAdapter(Context mContext, String sessionId) {
		this.mContext = mContext;
		this.sessionId = sessionId;
	}

	public void setDatas(ArrayList<Model_msg> datas) {
		this.datas = datas;
	}

	// SwipeLayout的布局id
	@Override
	public int getSwipeLayoutResourceId(int position) {
		return R.id.swipe;
	}

	@Override
	public View generateView(final int position, ViewGroup parent) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.listview_item,
				parent, false);
		final SwipeLayout swipeLayout = (SwipeLayout) v
				.findViewById(getSwipeLayoutResourceId(position));
		swipeLayout.setEnabled(true);
		// 当隐藏的删除menu被打开的时候的回调函数
		swipeLayout.addSwipeListener(new SimpleSwipeListener() {
			@Override
			public void onOpen(SwipeLayout layout) {
			}
		});

		// 添加删除布局的点击事件
		v.findViewById(R.id.ll_menu).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 进行删除的网络请求
				startRequestDeleteMsg(datas.get(position).getId());
				datas.remove(position);
				swipeLayout.close();
				notifyDataSetChanged();
			}
		});
		return v;
	}

	protected void startRequestDeleteMsg(String msgId) {
		DeletMessageRequest request = new DeletMessageRequest();
		request.setSessionId(sessionId);
		request.setId(msgId);
		NetWorkRequestManager.sendRequestPost(mContext, true, request,
				DeletMessageResponse.class, new MyRequestCallBack() {

					@Override
					public void onSuccess(ResponseSupport response) {
						DialogUtils.dismisLoading();
						ToastUtil.showToast(mContext, "删除成功");
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

	// 对控件的填值操作独立出来了，我们可以在这个方法里面进行item的数据赋值
	@Override
	public void fillValues(int position, View convertView) {
		TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
		LinearLayout llLeftMainLayout = (LinearLayout) convertView
				.findViewById(R.id.ll_leftMain);
		if (datas != null && datas.size() > 0) {
			final Model_msg model = datas.get(position);
			llLeftMainLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 进入消息详情界面
					Intent intent = new Intent(mContext,
							Activity_MsgDetail.class);
					intent.putExtra("id", model.getId());
					intent.putExtra("content", model.getDetail());
					model.setTatus("1");
					intent.putExtra(AppContants.FROM, "innerMail");
					notifyDataSetChanged();
					mContext.startActivity(intent);
				}
			});
			tv_time.setText(model.getTime());
			tv_title.setText(model.getType());
			LinearLayout ll_icon0 = (LinearLayout) convertView
					.findViewById(R.id.lv_item_icon_0);
			LinearLayout ll_icon1 = (LinearLayout) convertView
					.findViewById(R.id.lv_item_icon_1);
			ll_icon0.setVisibility(View.INVISIBLE);
			ll_icon1.setVisibility(View.INVISIBLE);
			if (model.getTatus().equals("0")) {
				// 未读
				ll_icon0.setVisibility(View.VISIBLE);
			} else {
				ll_icon1.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public int getCount() {
		return (datas == null || datas.size() == 0) ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
