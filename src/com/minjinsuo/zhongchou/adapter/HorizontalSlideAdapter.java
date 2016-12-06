package com.minjinsuo.zhongchou.adapter;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.DeletMessageRequest;
import net.zkbc.p2p.fep.message.protocol.DeletMessageResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_MsgDetail;
import com.minjinsuo.zhongchou.http.MyRequestCallBack;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_msg;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 站内信列表——侧滑删除列表适配器
 * 
 * @author zp
 *
 *         2016年10月13日
 */
public class HorizontalSlideAdapter extends BaseAdapter {

	/** 屏幕宽度 */
	private int mScreenWidth;

	/** 删除按钮事件 */
	private DeleteButtonOnclickImpl mDelOnclickImpl;
	/** HorizontalScrollView左右滑动事件 */
	private ScrollViewScrollImpl mScrollImpl;

	/** 布局参数,动态让HorizontalScrollView中的TextView宽度包裹父容器 */
	private LinearLayout.LayoutParams mParams;

	/** 记录滑动出删除按钮的itemView */
	public HorizontalScrollView mScrollView;

	/** touch事件锁定,如果已经有滑动出删除按钮的itemView,就屏蔽下一整次(down,move,up)的onTouch操作 */
	public boolean mLockOnTouch = false;
	public Context context;
	private ArrayList<Model_msg> messageList;

	public HorizontalSlideAdapter(Context context) {
		super();
		this.context = context;
		messageList = new ArrayList<Model_msg>();
		// 下面是侧滑相关设置
		Display defaultDisplay = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		defaultDisplay.getMetrics(metrics);
		mScreenWidth = metrics.widthPixels;
		mParams = new LinearLayout.LayoutParams(mScreenWidth,
				LinearLayout.LayoutParams.MATCH_PARENT);
		// 初始化删除按钮事件与item滑动事件
		mDelOnclickImpl = new DeleteButtonOnclickImpl();
		// mScrollImpl = new ScrollViewScrollImpl();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_my_msgsite_new, null);

			holder.ll_main_content = (LinearLayout) convertView
					.findViewById(R.id.ll_main_content);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.iv_left_msg = (ImageView) convertView
					.findViewById(R.id.iv_left_msg);
			holder.scrollView = (HorizontalScrollView) convertView;
			holder.deleteButton = (Button) convertView
					.findViewById(R.id.item_delete);

			holder.ll_main_content.setLayoutParams(mParams);// 动态设置宽度全屏
			holder.deleteButton.setOnClickListener(mDelOnclickImpl);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.position = position;
		final Model_msg model = messageList.get(position);
		holder.tv_time.setText(messageList.get(position).getTime());
		holder.tv_title.setText(messageList.get(position).getSender());// 没有title,暂时显示发送者
		if ("0".equals(messageList.get(position).getTatus())) {
			// 未读
			holder.iv_left_msg.setImageResource(R.drawable.new_msg);
			holder.scrollView.setBackgroundColor(context.getResources()
					.getColor(R.color.white));
		} else {
			holder.iv_left_msg.setImageResource(R.drawable.old_msg);
			holder.scrollView.setBackgroundColor(context.getResources()
					.getColor(R.color.bg));
		}
		holder.deleteButton.setTag(holder);

		holder.scrollView.scrollTo(0, 0);

		mScrollImpl = new ScrollViewScrollImpl(context, model);
		holder.scrollView.setOnTouchListener(mScrollImpl);

		return convertView;
	}

	static class ViewHolder {
		private HorizontalScrollView scrollView;
		private Button deleteButton;
		private TextView tv_time, tv_title;
		private LinearLayout ll_main_content;
		private int position;
		private ImageView iv_left_msg;

	}

	/** HorizontalScrollView的滑动事件 */
	private class ScrollViewScrollImpl implements OnTouchListener {
		/** 记录开始时的坐标 */
		private float startX = 0;
		private Context context;
		private Model_msg model;

		public ScrollViewScrollImpl(Context context, Model_msg model) {
			this.context = context;
			this.model = model;
		}

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// 如果有划出删除按钮的itemView,就让他滑回去
				if (mScrollView != null) {
					scrollView(mScrollView, HorizontalScrollView.FOCUS_LEFT);
					mScrollView = null;
					mLockOnTouch = true;
					// return true;
				}
				startX = event.getX();
				break;
			case MotionEvent.ACTION_UP:
				HorizontalScrollView view = (HorizontalScrollView) v;
				// 如果滑动了>50个像素,就显示出删除按钮
				// if (Math.abs(startX - event.getX()) > 50) {
				if (startX - event.getX() > 50) {// 打开删除布局
					startX = 0;// 因为公用一个事件处理对象,防止错乱,还原startX值
					scrollView(view, HorizontalScrollView.FOCUS_RIGHT);
					mScrollView = view;
				} else if (startX - event.getX() < -50) {// 关闭删除布局
					scrollView(view, HorizontalScrollView.FOCUS_LEFT);
				} else {// 左右滑动均小于50像素视为点击事件.避免事件冲突
					scrollView(view, HorizontalScrollView.FOCUS_LEFT);

					// 点击事件。进入详情页
					Intent intent = new Intent(context,
							Activity_MsgDetail.class);
					intent.putExtra("id", model.getId());
					intent.putExtra("content", model.getDetail());
					model.setTatus("1");
					intent.putExtra(AppContants.FROM, "innerMail");
					context.startActivity(intent);
					notifyDataSetChanged();
				}
				break;
			}
			return false;
		}
	}

	/** HorizontalScrollView左右滑动 */
	public void scrollView(final HorizontalScrollView view, final int parameter) {
		view.post(new Runnable() {
			@Override
			public void run() {
				view.pageScroll(parameter);
			}
		});
	}

	/** 删除事件 */
	private class DeleteButtonOnclickImpl implements OnClickListener {
		@Override
		public void onClick(View v) {
			final ViewHolder holder = (ViewHolder) v.getTag();
			Animation animation = AnimationUtils.loadAnimation(context,
					R.anim.anim_item_delete);
			holder.scrollView.startAnimation(animation);
			animation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// 执行删除
					startRequestDeleteMsg(messageList.get(holder.position)
							.getId());
					messageList.remove(holder.position);
					notifyDataSetChanged();
				}
			});

		}
	}

	/**
	 * 删除
	 * 
	 * @param msgId
	 */
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
}
