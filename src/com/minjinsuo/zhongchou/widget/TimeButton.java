package com.minjinsuo.zhongchou.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.system.ZCApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 鉴于经常用到获取验证码倒计时按钮 在网上也没有找到理想的 自己写一个
 * 
 * 
 * @author yung
 *         <P>
 *         2015年1月14日[佛祖保佑 永无BUG]
 *         <p>
 *         PS: 由于发现timer每次cancle()之后不能重新schedule方法,所以计时完毕只恐timer.
 *         每次开始计时的时候重新设置timer, 没想到好办法初次下策
 *         注意把该类的onCreate()onDestroy()和activity的onCreate()onDestroy()同步处理
 * 
 */
public class TimeButton extends Button implements OnClickListener {
	private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
	private String textafter = "秒后重新获取~";
	private String textbefore = "点击获取验证码~";
	private final String TIME = "time";
	private final String CTIME = "ctime";
	private OnClickListener mOnclickListener;
	private Timer t;
	private TimerTask tt;
	private long time;
	Map<String, Long> map = new HashMap<String, Long>();

	public TimeButton(Context context) {
		super(context);
		setOnClickListener(this);

	}

	public TimeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnClickListener(this);
	}

	@SuppressLint("HandlerLeak")
	Handler han = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			TimeButton.this.setText(time / 1000 + textafter);
			time -= 1000;
			if (time < 0) {
				TimeButton.this.setEnabled(true);
				TimeButton.this.setText(textbefore);
				setBackgroundResource(R.drawable.btn_shape_whitered);
				clearTimer();
			}
		};
	};

	private void initTimer() {
		if (ZCApplication.timeBtn_map == null)
			ZCApplication.timeBtn_map = new HashMap<String, Object>();
		time = lenght;
		t = new Timer();
		tt = new TimerTask() {

			@Override
			public void run() {
				han.sendEmptyMessage(0x01);
			}
		};
	}

	public void clearTimer() {
		if (tt != null) {
			tt.cancel();
			tt = null;
		}
		if (t != null)
			t.cancel();
		t = null;
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		if (l instanceof TimeButton) {
			super.setOnClickListener(l);
		} else
			this.mOnclickListener = l;
	}

	@Override
	public void onClick(View v) {
		if (mOnclickListener != null)
			mOnclickListener.onClick(v);
		if (mListener != null)
			mListener.onButtonTimingClick();
	}

	/**
	 * 开始倒计时
	 */
	public void start() {
		initTimer();
		this.setText(time / 1000 + textafter);
		this.setEnabled(false);
		setBackgroundResource(R.drawable.btn_huise_selector);
		t.schedule(tt, 0, 1000);
	}

	/**
	 * 取消倒计时
	 */
	public void cancel() {
		TimeButton.this.setEnabled(true);
		TimeButton.this.setText(textbefore);
		setBackgroundResource(R.drawable.btn_shape_whitered);
		clearTimer();
	}

	/**
	 * 和activity的onDestroy()方法同步
	 */
	public void onDestroy() {
		if (ZCApplication.timeBtn_map == null)
			ZCApplication.timeBtn_map = new HashMap<String, Object>();
		ZCApplication.timeBtn_map.put(TIME, time);
		ZCApplication.timeBtn_map.put(CTIME, System.currentTimeMillis());
		clearTimer();
	}

	/**
	 * 和activity的onCreate()方法同步
	 */
	public void onCreate(Bundle savedInstanceState) {
		if (ZCApplication.timeBtn_map == null)
			return;
		if (ZCApplication.timeBtn_map.size() <= 0)// 这里表示没有上次未完成的计时
			return;
		long time = System.currentTimeMillis() - Long.parseLong(String.valueOf(ZCApplication.timeBtn_map.get(CTIME)))
				- Long.parseLong(String.valueOf(ZCApplication.timeBtn_map.get(TIME)));
		ZCApplication.timeBtn_map.clear();
		if (time > 0)
			return;
		else {
			initTimer();
			this.time = Math.abs(time);
			t.schedule(tt, 0, 1000);
			this.setText(time + textafter);
			this.setEnabled(false);
		}
	}

	/** * 设置计时时候显示的文本 */
	public TimeButton setTextAfter(String text1) {
		this.textafter = text1;
		return this;
	}

	/** * 设置点击之前的文本 */
	public TimeButton setTextBefore(String text0) {
		this.textbefore = text0;
		this.setText(textbefore);
		return this;
	}

	/**
	 * 设置到计时长度
	 * 
	 * @param lenght
	 *            时间 默认毫秒
	 * @return
	 */
	public TimeButton setLenght(long lenght) {
		this.lenght = lenght;
		return this;
	}

	/**
	 * 单击事件监听器
	 */
	private onBtnTimingClickListener mListener = null;

	public void addOnBtnTimingListener(onBtnTimingClickListener listener) {
		mListener = listener;
	}

	public interface onBtnTimingClickListener {
		void onButtonTimingClick();
	}
}