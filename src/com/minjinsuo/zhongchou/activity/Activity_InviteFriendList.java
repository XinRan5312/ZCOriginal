package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.MyInviteFriendsAdapter;
import com.minjinsuo.zhongchou.model.Model_inviteFriend;

public class Activity_InviteFriendList extends Activity_Base {

	public static final String KEY = "key";
	private ListView list;
	private ArrayList<Model_inviteFriend> datas = new ArrayList<Model_inviteFriend>();
	private MyInviteFriendsAdapter mAdapter;
	private LinearLayout noLog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_invite_list);
		setTitleBack();
		setTitleText("好友列表");
		datas = (ArrayList<Model_inviteFriend>) getIntent()
				.getSerializableExtra(KEY);
		list = (ListView) this.findViewById(R.id.list);
		noLog = (LinearLayout) this.findViewById(R.id.noLog);
		if (datas != null && datas.size() != 0) {
			mAdapter = new MyInviteFriendsAdapter(this, datas);
			list.setAdapter(mAdapter);
		} else {
			noLog.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void initView() {

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
