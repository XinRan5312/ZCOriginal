package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.GetInviteCodeRequest;
import net.zkbc.p2p.fep.message.protocol.GetInviteCodeResponse;
import net.zkbc.p2p.fep.message.protocol.GetInviteFriendListRequest;
import net.zkbc.p2p.fep.message.protocol.GetInviteFriendListResponse;
import net.zkbc.p2p.fep.message.protocol.GetInviteFriendListResponse.ElementInviteFriendList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_inviteFriend;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.ShareUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 邀请好友
 * 
 * @author zp
 *
 *         2016年9月8日
 */
public class Activity_InviteFriends extends Activity_Base {
	private TextView tv_renshu;
	private TextView tv_jine;
	private Button bt_yaoqing;
	private RelativeLayout rl_renshu;
	private String shareCodeUrl, shareContent, sharePicUrl, shareTitle = null;
	private String personCount = null;
	private String inviteTotalRewardCount = null;
	private String errorMsg;
	private ArrayList<Model_inviteFriend> datas = new ArrayList<Model_inviteFriend>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aty_invete_friend);
		setTitleBack();
		setTitleText("邀请好友");
		initView();
		initListener();

		startGetInviteCodeRequest();
	}

	@Override
	protected void initView() {
		tv_renshu = (TextView) this.findViewById(R.id.tv_renshu);
		rl_renshu = (RelativeLayout) this.findViewById(R.id.rl_renshu);
		tv_jine = (TextView) this.findViewById(R.id.tv_jine);
		bt_yaoqing = (Button) this.findViewById(R.id.bt_yaoqing);
		// bt_yaoqing.setOnTouchListener(touchListener);
	}

	@Override
	protected void initListener() {
		rl_renshu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Activity_InviteFriends.this,
						Activity_InviteFriendList.class);
				intent.putExtra(Activity_InviteFriendList.KEY, datas);
				startActivity(intent);
			}
		});
		bt_yaoqing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!StringUtils.isEmpty(errorMsg)) {
					ToastUtil.showToast(Activity_InviteFriends.this, errorMsg);
				} else {
					showShare();
				}

			}
		});

	}

	/**
	 * 调起第三方分享视图
	 */
	private void showShare() {
		if (StringUtils.isEmpty(shareCodeUrl)) {
			ToastUtil.showToast(this, "分享url为空");
			return;
		}
		if (StringUtils.isEmpty(shareContent)) {
			ToastUtil.showToast(this, "分享内容描述为空");
			return;
		}
		ShareUtils.showShare(this, shareCodeUrl, shareContent);
	}

	private void startGetInviteCodeRequest() {
		final String sessionId = ZCApplication.getInstance().getUserInfo()
				.getSessionId();
		GetInviteCodeRequest request = new GetInviteCodeRequest();
		// request.setType(1);
		request.setSessionId(sessionId);

		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetInviteCodeResponse.class, this);
	}

	protected void startRequestFriendsList() {
		GetInviteFriendListRequest request = new GetInviteFriendListRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetInviteFriendListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getInviteCode":
			GetInviteCodeResponse model = (GetInviteCodeResponse) response;
			// 成功之后接着进行好友列表的请求
			if (model != null) {
				shareCodeUrl = model.getCode() != null ? model.getCode() : "";
				shareContent = model.getDesc() != null ? model.getDesc() : "";
				startRequestFriendsList();
			}
			break;
		case "getInviteFriendList":
			GetInviteFriendListResponse model_friend = (GetInviteFriendListResponse) response;

			// 填充数据
			if (model_friend != null) {
				personCount = model_friend.getInvitenum();
				personCount = personCount.replace("\"", "");
				inviteTotalRewardCount = model_friend.getInvitemoney();
				inviteTotalRewardCount = inviteTotalRewardCount.replace("\"",
						"");
				tv_renshu.setText(personCount + "人");
				tv_jine.setText(inviteTotalRewardCount + "元");
			}
			if (model_friend.getInviteFriendList() == null
					|| model_friend.getInviteFriendList().size() == 0) {
				return;
			}

			for (int i = 0; i < model_friend.getInviteFriendList().size(); i++) {
				ElementInviteFriendList info = model_friend
						.getInviteFriendList().get(i);
				String nickname = info.getNickname();
				String redmoneyamount = info.getRedmoneyamount();
				String inviteeregdate = info.getInviteeregdate();
				String way = info.getWay() != null ? info.getWay() : "";
				if (redmoneyamount.contains(".00")) {
					redmoneyamount = redmoneyamount.substring(0,
							redmoneyamount.indexOf("."));
				}
				Model_inviteFriend model_fri = new Model_inviteFriend(nickname,
						redmoneyamount, inviteeregdate, way);
				datas.add(model_fri);

			}

			DialogUtils.dismisLoading();
			break;

		default:
			break;
		}
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
