package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetCommonTalkListResponse.ElementCommonTalkList;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkRepayListRequest;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkRepayListResponse;
import net.zkbc.p2p.fep.message.protocol.GetCommonTalkRepayListResponse.ElementCommonTalkRepayList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;
import net.zkbc.p2p.fep.message.protocol.SubmitCommonTalkRequest;
import net.zkbc.p2p.fep.message.protocol.SubmitCommonTalkResponse;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_TopicReply;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_Tender;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.CircleImageView;
import com.minjinsuo.zhongchou.widget.ClearableEditText;

/**
 * 评论主题帖回复列表
 * 
 * @author zp
 *
 */
public class Activity_TopicReply extends Activity_Base implements
		OnItemClickListener, OnRefreshListener2<ListView> {

	@ViewInject(R.id.lv_list)
	PullToRefreshListView lv_list;
	@ViewInject(R.id.et_content)
	private ClearableEditText et_content;
	@ViewInject(R.id.btn_ok)
	private Button btn_ok;
	// 下面是顶部主贴信息
	@ViewInject(R.id.civ_head)
	private CircleImageView civ_head;
	@ViewInject(R.id.tv_name)
	private TextView tv_name;
	@ViewInject(R.id.tv_contentMainTopic)
	private TextView tv_contentMainTopic;
	@ViewInject(R.id.tv_time_mainTopic)
	private TextView tv_time_mainTopic;
	@ViewInject(R.id.tv_num_replay)
	private TextView tv_num_replay;// 回复数

	private Adapter_TopicReply adapter_list;
	private List<Model_Tender> list;
	private String ctId = "";// 主题话题id
	private String prjId = "";
	private ArrayList<ElementCommonTalkRepayList> list_replay;// 数据结构重组后的回复列表
	private ElementCommonTalkList model;
	public ImageOptions imageOptions;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_topic_reply);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		setTitleText("回复详情");
		setTitleBack();

		if (getIntent() != null) {

			if (!StringUtils.isEmpty(getIntent().getStringExtra("id"))) {
				prjId = getIntent().getStringExtra("id");
			}
			if (!StringUtils.isEmpty(getIntent().getStringExtra("ctId"))) {
				ctId = getIntent().getStringExtra("ctId");
			}

			if (getIntent().getSerializableExtra("model") != null) {
				model = (ElementCommonTalkList) getIntent()
						.getSerializableExtra("model");

				completeTopMain();// 填充头部布局后获取回复列表
			}
		}
	}

	@Override
	protected void initData() {
		lv_list.setMode(Mode.DISABLED);
		adapter_list = new Adapter_TopicReply(this);

		adapter_list.setData(list_replay);
		lv_list.setAdapter(adapter_list);
	}

	@Override
	protected void initListener() {
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submitReplayTalk();
			}
		});
	}

	/**
	 * 填充回复列表数据
	 */
	public void completeList() {
		lv_list.setMode(Mode.DISABLED);
		adapter_list = new Adapter_TopicReply(this);

		adapter_list.setData(list_replay);
		lv_list.setAdapter(adapter_list);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub

	}

	public void submitReplayTalk() {
		if (!ZCApplication.getInstance().isLogin()) {
			final Dialog dialog = DialogUtils
					.createTwoBtnDiolog(this, "请登录后操作");
			dialog.findViewById(R.id.btnRight).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							startActivity(new Intent(Activity_TopicReply.this,
									Activity_Login.class));
						}
					});
			return;
		}

		if (StringUtils.isEmpty(et_content.getText().toString().trim())) {
			ToastUtil.showToast(Activity_TopicReply.this, "请输入话题内容");
			return;
		}
		SubmitCommonTalkRequest request = new SubmitCommonTalkRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setPrId(Integer.parseInt(prjId));
		// request.setPrId(1973);//zpqa
		request.setCtId(Integer.parseInt(ctId));// 话题id，如果为空，则此话题不是回复记录
		request.setContent(et_content.getText().toString().trim());
		NetWorkRequestManager.sendRequestPost(this, true, request,
				SubmitCommonTalkResponse.class, this);
	}

	/**
	 * 获取话题回复列表
	 */
	public void getCommonTalkRepayList(boolean isShow) {
		GetCommonTalkRepayListRequest request = new GetCommonTalkRepayListRequest();
		request.setPrjId(Integer.parseInt(prjId));
		// request.setPrjId(1973);// zpqa
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetCommonTalkRepayListResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "submitCommonTalk":// 提交话题
			ToastUtil.showToast(Activity_TopicReply.this, "发表话题成功");
			et_content.setText("");

			ZCApplication.getInstance().isNeedRefresh = true;
			getCommonTalkRepayList(false);
			break;
		case "getCommonTalkRepayList":// 话题回复列表
			GetCommonTalkRepayListResponse model_replay = (GetCommonTalkRepayListResponse) response;
			if (model_replay != null
					&& model_replay.getCommonTalkRepayList() != null
					&& model_replay.getCommonTalkRepayList().size() > 0) {
				// 重组数据
				dealRelapyData(model_replay, ctId);
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

	/**
	 * 处理话题回复列表数据结构（将回复列表回复id与主贴id进行遍历筛选）
	 * 
	 * @param model_replay
	 */
	public void dealRelapyData(GetCommonTalkRepayListResponse model_replay,
			String id_mainToptic) {
		ElementCommonTalkRepayList info_replay = null;
		list_replay = new ArrayList<GetCommonTalkRepayListResponse.ElementCommonTalkRepayList>();

		for (int i = 0; i < model_replay.getCommonTalkRepayList().size(); i++) {
			String replayToId = model_replay.getCommonTalkRepayList().get(i)
					.getCommentIdreplyTo();
			info_replay = model_replay.getCommonTalkRepayList().get(i);
			if (replayToId.equals(id_mainToptic)) {// 如果回复id等于主题的id则
				list_replay.add(info_replay);
			}
		}
		LogUtil.i("此回复列表 size==" + list_replay.size());

		// 刷新列表
		adapter_list.deleteData();
		adapter_list.setData(list_replay);
		adapter_list.notifyDataSetChanged();
	}

	/**
	 * 填充顶部主题帖布局，然后获取获取话题回复列表
	 */
	public void completeTopMain() {
		tv_name.setText(model.getByUserNickName());
		tv_contentMainTopic.setText(model.getContent());
		tv_time_mainTopic
				.setText(StringUtils.getDate(model.getCommentTime(), 2));
		tv_num_replay.setText(model.getRepayCnt() + "条评论");

		imageOptions = new ImageOptions.Builder()
				.setRadius(DensityUtil.dip2px(0)).setIgnoreGif(false)
				.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
				.setLoadingDrawableId(R.drawable.loading)
				.setFailureDrawableId(R.drawable.head).build();
		x.image().bind(civ_head, model.getPortraitAddr(), imageOptions);

		getCommonTalkRepayList(true);
	}
}
