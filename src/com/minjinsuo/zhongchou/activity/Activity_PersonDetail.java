package com.minjinsuo.zhongchou.activity;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetPrjPageListForUserRequest;
import net.zkbc.p2p.fep.message.protocol.GetPrjPageListForUserResponse;
import net.zkbc.p2p.fep.message.protocol.GetPrjPageListForUserResponse.ElementUPrjList;
import net.zkbc.p2p.fep.message.protocol.GetUserPrjInfoByIdRequest;
import net.zkbc.p2p.fep.message.protocol.GetUserPrjInfoByIdResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_MyAttention;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.AppContants;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.AlertDialog;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;
import com.minjinsuo.zhongchou.view.CircleImageView;
import com.minjinsuo.zhongchou.view.ObservableScrollView;
import com.minjinsuo.zhongchou.view.ObservableScrollView.ScrollViewListener;

/**
 * 发起人详情
 * 
 * @author zp
 *
 *         2016年6月23日
 */
public class Activity_PersonDetail extends Activity_Base implements
		ScrollViewListener, OnItemClickListener {

	@ViewInject(R.id.myScrollView)
	private ObservableScrollView myScrollView;
	@ViewInject(R.id.title_bar)
	private RelativeLayout title_bar;
	@ViewInject(R.id.ll_top_container)
	private LinearLayout ll_top_container;// 顶部蓝色布局
	@ViewInject(R.id.lv_list)
	private ListView lv_list;
	@ViewInject(R.id.ll_select_area_hide)
	private LinearLayout ll_select_area_hide;// 隐藏的筛选条件
	@ViewInject(R.id.ll_select_area)
	private LinearLayout ll_select_area;// 初始显示的筛选条件
	@ViewInject(R.id.tv_project_left)
	private TextView tv_project_left;// 筛选条件
	@ViewInject(R.id.tv_project_right)
	private TextView tv_project_right;// 筛选条件
	@ViewInject(R.id.tv_project_left2)
	private TextView tv_project_left2;
	@ViewInject(R.id.tv_project_right2)
	private TextView tv_project_right2;

	@ViewInject(R.id.clv_head)
	private CircleImageView clv_head;
	@ViewInject(R.id.tv_name)
	private TextView tv_title;
	@ViewInject(R.id.tv_des)
	private TextView tv_des;
	@ViewInject(R.id.ll_btn)
	private LinearLayout ll_btn;// 私信、领头按钮
	@ViewInject(R.id.iv_lingtouren)
	private ImageView iv_lingtouren;// 是领头人时显示

	private int topHeight;// 顶部蓝色布局的高度

	private Adapter_MyAttention adapter_list;
	private List<ElementUPrjList> list = new ArrayList<GetPrjPageListForUserResponse.ElementUPrjList>();
	private String userId = "", prjId = "";// 请求参数
	private boolean isFaQi;// 是否是发起人 标示
	private String custName = "";// 发起人姓名
	private String custId = "";// 发起人id
	private int flag;// 0:发起，1：关注

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		setContentView(R.layout.aty_personfaqi_detail);
		x.view().inject(this);
		initView();
		initData();
		initListener();
	}

	@Override
	protected void initView() {
		if (getIntent() != null) {

			if (!StringUtils.isEmpty(getIntent().getStringExtra("prjId"))) {
				prjId = getIntent().getStringExtra("prjId");
			}
			if (!StringUtils.isEmpty(getIntent().getStringExtra("userId"))) {
				userId = getIntent().getStringExtra("userId");

				getUserPrjInfoById();// 获取该用户身份信息
			}

		}
		setTitleBack();
		title_bar.setBackgroundColor(Color.parseColor("#00000000"));
	}

	@Override
	protected void initData() {
		tv_project_left.setSelected(true);// 默认选中左侧条件
		tv_project_right.setSelected(false);
		tv_project_left2.setSelected(true);
		tv_project_right2.setSelected(false);

		adapter_list = new Adapter_MyAttention(this);
		lv_list.setAdapter(adapter_list);
		lv_list.setOnItemClickListener(this);
	}

	@Override
	protected void initListener() {
		// 处理顶部滑动titlebar效果
		ViewTreeObserver vto = ll_top_container.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				ll_top_container.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				topHeight = ll_top_container.getHeight();

				myScrollView.setScrollViewListener(Activity_PersonDetail.this);
			}
		});
	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		if (y <= 0) {
			// title_bar.setBackgroundColor(Color.argb((int) 0, 227, 29,
			// 26));//红色
			title_bar.setBackgroundColor(Color.argb((int) 0, 51, 48, 146));

		} else if (y > 0 && y <= topHeight - title_bar.getHeight()) {
			float scale = (float) y / topHeight;
			float alpha = (255 * scale);
			title_bar.setBackgroundColor(Color.argb((int) alpha, 51, 48, 146));
		} else {
			title_bar.setBackgroundColor(Color.argb((int) 255, 51, 48, 146));
		}

//		// 控制筛选布局显示
//		if (y >= (topHeight - title_bar.getHeight())) {
//			ll_select_area_hide.setVisibility(View.VISIBLE);
//			ll_select_area.setVisibility(View.INVISIBLE);
//			ll_select_area_hide.setFocusable(true);
//			ll_select_area_hide.setFocusableInTouchMode(true);
//
//		} else if (y <= (topHeight - ll_select_area_hide.getHeight())) {
//			ll_select_area_hide.setVisibility(View.GONE);
//			ll_select_area.setVisibility(View.VISIBLE);
//		}
	}

	@Event({ R.id.tv_project_left, R.id.tv_project_right, R.id.ll_btn })
	private void OnClick(View view) {
		Intent intent = null;
		switch (view.getId()) {
		case R.id.tv_project_left2:
		case R.id.tv_project_left:

			tv_project_left.setSelected(true);
			tv_project_right.setSelected(false);
			tv_project_left2.setSelected(true);
			tv_project_right2.setSelected(false);

			isPullDown = true;
			getPrjPageListForUser(0, true);
			break;
		case R.id.tv_project_right2:
		case R.id.tv_project_right:
			tv_project_left.setSelected(false);
			tv_project_right.setSelected(true);
			tv_project_left2.setSelected(false);
			tv_project_right2.setSelected(true);

			isPullDown = true;
			getPrjPageListForUser(1, true);
			break;
		case R.id.ll_btn:
			if (ZCApplication.getInstance().isLogin()) {
				if (isFaQi) {
					intent = new Intent(Activity_PersonDetail.this,
							Activity_MsgSend.class);
					intent.putExtra("name", custName);
					intent.putExtra("custId", custId);
					startActivity(intent);
				} else {
					startActivity(new Intent(Activity_PersonDetail.this,
							Activity_InvestCerciticyLingTou.class));
				}
			} else {
				new AlertDialog(Activity_PersonDetail.this).builder()
						.setMsg("您尚未登录，请登录后操作")
						.setPositiveButton("确定", new OnClickListener() {

							@Override
							public void onClick(View v) {
								startActivity(new Intent(
										Activity_PersonDetail.this,
										Activity_Login.class));
							}
						}).setNegativeButton("取消", new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						}).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = null;
		if (list != null && list.size() > 0) {
			LogUtil.e("产品类型==" + list.get(position).getProdId());
			if (list.get(position).getProdId().equals("0")) {// 股权
				intent = new Intent(Activity_PersonDetail.this,
						Activity_StockDetail.class);
				intent.putExtra("id", list.get(position).getId());
				intent.putExtra("img", list.get(position).getHomePicAddress());
			} else {// 产品
				intent = new Intent(Activity_PersonDetail.this,
						Activity_ProductDetail.class);
				intent.putExtra("prjId", list.get(position).getId());
				intent.putExtra("img", list.get(position).getHomePicAddress());
			}
			startActivity(intent);
		}
	}

	public void getUserPrjInfoById() {
		GetUserPrjInfoByIdRequest request = new GetUserPrjInfoByIdRequest();
		request.setPrjId(prjId);
		request.setUserId(userId);
		NetWorkRequestManager.sendRequestPost(this, true, request,
				GetUserPrjInfoByIdResponse.class, this);
	}

	/**
	 * 获取项目列表
	 * 
	 * @param flag_0
	 *            :发起，1：关注
	 */
	public void getPrjPageListForUser(int flag, boolean isShow) {
		GetPrjPageListForUserRequest request = new GetPrjPageListForUserRequest();
//		request.setSessionId(ZCApplication.getInstance().getUserInfo()
//				.getSessionId());
		request.setCustId(custId);
		// request.setProdId("1");// 0 股权众筹\n 1 产品众筹
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		if (flag == 0) {
			request.setSearchtype("00");// 00-发起 10-跟投 20-关注 30-领投 40-预约
		} else {
			request.setSearchtype("20");
		}
		NetWorkRequestManager.sendRequestPost(this, isShow, request,
				GetPrjPageListForUserResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		switch (response.getMessageId()) {
		case "getUserPrjInfoById":

			GetUserPrjInfoByIdResponse model_user = (GetUserPrjInfoByIdResponse) response;
			if (model_user != null) {
				completeUI(model_user);
			}

			getPrjPageListForUser(0, false);
			break;
		case "getPrjPageListForUser":
			// 获取产品列表成功
			GetPrjPageListForUserResponse model = (GetPrjPageListForUserResponse) response;
			if (isPullDown) {
				adapter_list.deleteData();
				list.clear();
			}
			if (model != null && model.getUPrjList() != null
					&& model.getUPrjList().size() > 0) {
				adapter_list.setData(model.getUPrjList());
				list.addAll(model.getUPrjList());
				adapter_list.notifyDataSetChanged();
			} else if (isPullDown) {
				ToastUtil.showToast(Activity_PersonDetail.this, "暂无数据");
			} else {
				ToastUtil.showToast(Activity_PersonDetail.this, "已加载全部数据");
			}
			adapter_list.notifyDataSetChanged();
			DialogUtils.dismisLoading();
			break;

		default:
			break;
		}
	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
	}

	public void completeUI(GetUserPrjInfoByIdResponse model) {
		custId = model.getId();
		custName = model.getLoginNam();
		tv_title.setText(custName);
		tv_des.setText(model.getSelfIntro());
		x.image().bind(clv_head, model.getPortraitAddr(), imageOptions);
		if (model.getIdentity().equals("10")) {// 10-项目发起人 20--项目领投人（股权)
			setTitleText("发起人详情");
			iv_lingtouren.setImageResource(R.drawable.product_detail_iconfaqi);
			iv_lingtouren.setVisibility(View.VISIBLE);
			isFaQi = true;
		} else {
			setTitleText("领投人详情");
			iv_lingtouren.setImageResource(R.drawable.lingtouren);
			iv_lingtouren.setVisibility(View.VISIBLE);
			isFaQi = false;
		}
	}
}
