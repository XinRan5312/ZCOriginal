package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;
import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetMyLetterInfoRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyLetterInfoResponse;
import net.zkbc.p2p.fep.message.protocol.GetMyLetterInfoResponse.ElementLetterList;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.activity.Activity_Mail_detail;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 
 * 私信收件箱(收)
 * 
 * @author zp
 *
 *         2016年5月23日
 */
public class ZFragment_inbox extends Fragment_Base implements
		OnRefreshListener2<ListView>, OnItemClickListener {
	private PullToRefreshListView lv_msg;
	private MsgAdapter mAdapter;
	private View mView;
	private int pageNo = 1;
	private final static int pageSize = 10;
	// false上拉加true下拉加载
	private boolean isPullDown = true;
	private List<ElementLetterList> list_msg = new ArrayList<GetMyLetterInfoResponse.ElementLetterList>();
	private GetMyLetterInfoResponse info;
	private ImageOptions imageOptions;
	/** 标志位，标志已经初始化完成 */
	private boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	private boolean mHasLoadedOnce;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;

	public static ZFragment_inbox getInstance() {
		ZFragment_inbox f = new ZFragment_inbox();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		if (mView == null) {
			Log.e("", "ZFragment_Equity");
			mView = inflater.inflate(R.layout.zfragment_msg_inbox, null);
			x.view().inject(this, mView);
			initView();
			isPrepared = true;
			lazyLoad();
		}
		ViewGroup parent = (ViewGroup) mView.getParent();
		if (parent != null) {
			parent.removeView(mView);
		}
		return mView;
	}

	@Override
	protected void initView() {

		lv_msg = (PullToRefreshListView) mView.findViewById(R.id.lv_msg);
		lv_msg.setMode(Mode.BOTH);
		lv_msg.setOnRefreshListener(this);
		lv_msg.setOnItemClickListener(this);

		mAdapter = new MsgAdapter();
		lv_msg.setAdapter(mAdapter);

	}

	public void initData() {
	}

	public void startRequestMsg() {
		if (ZCApplication.getInstance().getUserInfo() == null
				|| StringUtils.isEmpty(ZCApplication.getInstance()
						.getUserInfo().getSessionId())) {
			return;
		}

		GetMyLetterInfoRequest request = new GetMyLetterInfoRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		// request.setStatus();//10-未阅读 20-已阅读
		request.setLetterTyp("20");// 10-发件箱 20-收件箱
		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				GetMyLetterInfoResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		GetMyLetterInfoResponse info = (GetMyLetterInfoResponse) response;
		noLog.setVisibility(View.GONE);
		mHasLoadedOnce = true;
		DialogUtils.dismisLoading();
		lv_msg.onRefreshComplete();

		if (isPullDown) {
			list_msg = info.getLetterList();

			if (list_msg == null || list_msg.size() == 0) {
				noLog.setVisibility(View.VISIBLE);
				return;
			}
		} else {// 上拉加载

			if (info.getLetterList() != null) {
				list_msg.addAll(info.getLetterList());

				if (info.getLetterList().size() == 0) {
					ToastUtil.showToast(getActivity(), "已经加载全部");
					return;
				}
			} else {
				ToastUtil.showToast(getActivity(), "已经加载全部");
				return;
			}
		}

		mAdapter.notifyDataSetChanged();

	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		lv_msg.onRefreshComplete();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		lv_msg.onRefreshComplete();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		System.out.println("点击了==" + position);
		Intent intent = new Intent(getActivity(), Activity_Mail_detail.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("info", list_msg.get(position - 1));
		intent.putExtras(bundle);
		intent.putExtra("flag", 0);// 0代表收件箱
		getActivity().startActivity(intent);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		isPullDown = true;
		pageNo = 1;
		startRequestMsg();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		isPullDown = false;
		pageNo++;
		startRequestMsg();
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		}
		// 获取私信数据
		startRequestMsg();
	}

	@Override
	protected void initListener() {

	}

	private class MsgAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (list_msg != null && !list_msg.isEmpty()) {
				return list_msg.size();
			} else {
				return 0;
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater mInflater = LayoutInflater.from(getContext());
				convertView = mInflater.inflate(R.layout.zitem_msgcenter, null);
				holder.iv_head = (ImageView) convertView
						.findViewById(R.id.iv_head);
				holder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tv_user = (TextView) convertView
						.findViewById(R.id.tv_user);
				holder.tv_content = (TextView) convertView
						.findViewById(R.id.tv_content);
				holder.tv_time = (TextView) convertView
						.findViewById(R.id.tv_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			ElementLetterList list_item = list_msg.get(position);
			holder.tv_title.setText(list_item.getTitle());
			holder.tv_user.setText("用户：" + list_item.getRelateLoginNam());
			String time_format = "";
			if (list_item.getLetterTime().contains(".")) {
				time_format = list_item.getLetterTime().substring(0,
						list_item.getLetterTime().indexOf("."));
			} else {
				time_format = list_item.getLetterTime();
			}
			holder.tv_time.setText(StringUtils.getDate(time_format, 1));

			holder.tv_content.setText(list_item.getLetter());
			x.image().bind(holder.iv_head, list_item.getHeaderAddress(),
					imageOptions);

			return convertView;
		}
	}

	private class ViewHolder {
		ImageView iv_head;
		TextView tv_title;
		TextView tv_user;
		TextView tv_content;
		TextView tv_time;
	}

}
