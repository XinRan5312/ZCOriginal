package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.service.LockPatternService;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

/**
 * 引导页
 * 
 */
public class NavigationActivity extends Activity_Base implements OnClickListener {
	ViewPager vp;
	List<View> imageViews;
	int[] imageIds;
	Button btn;
	float btn_bottom;
	private int windowHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);
		windowHeight = getWindowManager().getDefaultDisplay().getHeight();
		initView();
		initListener();
	}

	@Override
	protected void initView() {
		vp = (ViewPager) findViewById(R.id.vp_splash_container);
		imageViews = new ArrayList<View>();
		imageIds = new int[] { R.drawable.splash_1, R.drawable.splash_2 };
		for (int i = 0; i < 3; i++) {
			if (i < 2) {
				ImageView iView = new ImageView(getApplicationContext());
				iView.setImageResource(imageIds[i]);
				iView.setBackgroundColor(Color.WHITE);
				imageViews.add(iView);
			} else {
				View view = View.inflate(getApplicationContext(), R.layout.splash3_view, null);
				imageViews.add(view);
				btn = (Button) view.findViewById(R.id.btn_splash3_start);
				btn.setOnClickListener(this);
			}
		}
		vp.setAdapter(new SplashAdapter());
	}

	class SplashAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		/**
		 * container 就是ViewPager object 就是当前需要被移除的View
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 把ImageView添加到ViewPager中, 并且把ImageView返回回去.
			View imageView = imageViews.get(position);
			container.addView(imageView);
			return imageView;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.zkbc.p2papp.ui.BaseActivity#initListener()
	 */
	@Override
	protected void initListener() {
		// 获取间距
		btn.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			/**
			 * 只要布局的参数一改变就会触发此方法.
			 */
			@Override
			public void onGlobalLayout() {
				// 把当前的事件给移除监听.
				btn.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				// int[] ints=new int[2];
				// btn.getLocationOnScreen(ints);

				// 获取间距
				btn_bottom = windowHeight - btn.getBottom();
				LogUtil.d("-----间距-------" + btn_bottom);
			}
		});
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				if (position == 1) {
					LinearLayout.LayoutParams params = (LayoutParams) btn.getLayoutParams();

					params.bottomMargin = (int) (btn_bottom * (positionOffset - 1));
					LogUtil.d("---------offset" + positionOffset);
					btn.setLayoutParams(params);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(this, Activity_Login.class);
		startActivity(intent);
		LockPatternService.setNotFirstStart(this);
		finish();
	}

	@Override
	protected void initData() {

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

}
