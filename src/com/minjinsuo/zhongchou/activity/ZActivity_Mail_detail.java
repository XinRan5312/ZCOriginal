package com.minjinsuo.zhongchou.activity;

import net.zkbc.p2p.fep.message.protocol.GetMyLetterInfoResponse.ElementLetterList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.utils.StringUtils;

/**
 * 收件箱、发件箱详情
 * 
 * @author zp
 *
 *         2016年5月23日
 */
public class ZActivity_Mail_detail extends Activity_Base {
	private int flag = 0;// 0:收件箱，1：发件箱
	private ElementLetterList list = new ElementLetterList();
	@ViewInject(R.id.tv_title_msg)
	private TextView tv_title_msg;
	@ViewInject(R.id.tv_sender)
	private TextView tv_sender;
	@ViewInject(R.id.tv_time)
	private TextView tv_time;
	@ViewInject(R.id.tv_receiver)
	private TextView tv_receiver;
	@ViewInject(R.id.tv_content)
	private TextView tv_content;
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.zactivity_msg_detail);
		x.view().inject(this);
		setTitleBack();
		if (getIntent() != null) {
			flag = getIntent().getIntExtra("flag", 0);
			if (flag == 0) {
				setTitleText("收件箱");
				btn_ok.setVisibility(View.VISIBLE);
			} else {
				setTitleText("发件箱");
				btn_ok.setVisibility(View.GONE);
			}

			// 从收件箱或发件箱传递来的点击对象
			list = (ElementLetterList) getIntent().getSerializableExtra("info");
			System.out.println("传递对象==" + JSON.toJSONString(list));
		}

		initView();
		initListener();
	}

	@Override
	protected void initView() {
		tv_title_msg.setText(list.getTitle());
		tv_time.setText(StringUtils.getDate(list.getLetterTime(), 2));
		tv_content.setText(list.getLetter());
		if (flag == 0) {
			tv_sender.setText(list.getRelateLoginNam());// 发件人
			tv_receiver.setText(list.getOwnerLoginNam());// 收件人
		} else {
			tv_sender.setText(list.getOwnerLoginNam());// 发件人
			tv_receiver.setText(list.getRelateLoginNam());// 收件人
		}
	}

	@Override
	protected void initListener() {
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(ZActivity_Mail_detail.this,
						Activity_MsgSend.class);
				intent.putExtra("custId", list.getRelateCustId());// 发件人id
				intent.putExtra("name", list.getRelateLoginNam());// 收件人姓名
				intent.putExtra("title", list.getTitle());// 主题
				startActivity(intent);
			}
		});
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
