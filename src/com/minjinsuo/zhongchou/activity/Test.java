package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.service.BusProvider;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.squareup.otto.Subscribe;

/**
 * 测试类
 * 
 * 1、otto
 * 
 * @author zp
 *
 *         2016年10月17日
 */
public class Test extends Activity_Base {
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;
	@ViewInject(R.id.et_content)
	private EditText et_content;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.aty_test);

		x.view().inject(this);
		initView();
		initData();
		initListener();

		BusProvider.getInstance().register(this);// 第1步：注册
	}

	// 第2步：声明订阅者——用来接口数据
	@Subscribe
	public void OnEven(String result) {
		LogUtil.i("otto 订阅者打印数据==" + result);
	}

	@Override
	protected void initView() {
		setTitleText("测试类");
		setTitleBack();
	}

	@Override
	protected void initData() {

	}

	@Override
	protected void initListener() {

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String str = et_content.getText().toString().trim();
				if (StringUtils.isEmpty(str)) {
					ToastUtil.showToast(Test.this, "请输入编辑内容");
					return;
				}

				BusProvider.getInstance().post(str);// 第3步：发送数据
			}
		});
	}

	@Override
	public void onSuccess(ResponseSupport response) {

	}

	@Override
	public void failure() {

	}

	@Override
	public void onFailure(ResponseSupport response) {

	}

}
