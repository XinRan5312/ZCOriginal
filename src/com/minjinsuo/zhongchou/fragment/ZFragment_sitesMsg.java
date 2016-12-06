package com.minjinsuo.zhongchou.fragment;

import java.util.ArrayList;

import net.zkbc.p2p.fep.message.protocol.GetMyInnerMailRequest;
import net.zkbc.p2p.fep.message.protocol.GetMyInnerMailResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.HorizontalSlideAdapter;
import com.minjinsuo.zhongchou.http.NetWorkRequestManager;
import com.minjinsuo.zhongchou.model.Model_msg;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;
import com.minjinsuo.zhongchou.utils.ToastUtil;

/**
 * 站内信
 */
public class ZFragment_sitesMsg extends Fragment_Base implements
		OnRefreshListener2<ListView>, OnItemClickListener {
	private View mView;
	/** 标志位，标志已经初始化完成 */
	private boolean isPrepared;
	/** 是否已被加载过一次，第二次就不再去请求数据了 */
	private boolean mHasLoadedOnce;
	private PullToRefreshListView plv_msg;
	private ArrayList<Model_msg> datas = new ArrayList<Model_msg>();
	// private Adapter_MyMessage adapter;
	private HorizontalSlideAdapter adapter;
	private int pageNo = 1;
	private static int pageSize = 10;
	private boolean refreshFlag = true;
	@ViewInject(R.id.noLog)
	private LinearLayout noLog;

	public static ZFragment_sitesMsg getInstance() {
		ZFragment_sitesMsg f = new ZFragment_sitesMsg();
		Bundle args = new Bundle();
		f.setArguments(args);
		return f;
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		if (mView == null) {
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
		plv_msg = (PullToRefreshListView) mView.findViewById(R.id.lv_msg);
		plv_msg.setMode(Mode.BOTH);
		plv_msg.setOnRefreshListener(this);
		plv_msg.setOnItemClickListener(this);

		// adapter = new Adapter_MyMessage(getActivity());
		adapter = new HorizontalSlideAdapter(getActivity());
		plv_msg.setAdapter(adapter);

		
	}

	@Override
	protected void initListener() {

	}

	public void initData() {
	}

	private void startRequestDataFromNet() {
		GetMyInnerMailRequest request = new GetMyInnerMailRequest();
		request.setSessionId(ZCApplication.getInstance().getUserInfo()
				.getSessionId());
		request.setPageNo(pageNo);
		request.setPageSize(pageSize);
		NetWorkRequestManager.sendRequestPost(getActivity(), true, request,
				GetMyInnerMailResponse.class, this);
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		GetMyInnerMailResponse model = (GetMyInnerMailResponse) response;
		mHasLoadedOnce = true;
		DialogUtils.dismisLoading();
		plv_msg.onRefreshComplete();

		if (refreshFlag) {
			datas.clear();
			adapter.deleteData();
		}

		if (model != null && model.getInnerMailList() != null
				&& model.getInnerMailList().size() > 0) {
			noLog.setVisibility(View.GONE);

			datas.clear();
			for (int i = 0; i < model.getInnerMailList().size(); i++) {

				String id = model.getInnerMailList().get(i).getId();
				String content = model.getInnerMailList().get(i).getContent();
				String isread = model.getInnerMailList().get(i).getIsread();
				String messagetype = model.getInnerMailList().get(i)
						.getMessageType();
				String sendtime = model.getInnerMailList().get(i).getSendtime();
				String title = model.getInnerMailList().get(i).getSender();

				if (!StringUtils.isEmpty(sendtime)) {
					if (sendtime.contains(".")) {
						sendtime = sendtime.substring(0, sendtime.indexOf("."));
					}
				}
				if (isread.equals("false")) {
					isread = "0";
				} else {
					isread = "1";
				}
				Model_msg model_msg = new Model_msg(id, messagetype, sendtime,
						isread, content, title);
				datas.add(model_msg);
			}// end for
			adapter.addDate(datas);
			adapter.notifyDataSetChanged();
		} else {
			if (!refreshFlag) {
				ToastUtil.showToast(getActivity(), "已加载全部");
			} else {
				noLog.setVisibility(View.VISIBLE);
			}
		}

	}

	@Override
	public void failure() {
		DialogUtils.dismisLoading();
		plv_msg.onRefreshComplete();
	}

	@Override
	public void onFailure(ResponseSupport response) {
		DialogUtils.dismisLoading();
		plv_msg.onRefreshComplete();
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible || mHasLoadedOnce) {
			return;
		}
		startRequestDataFromNet();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		pageNo = 1;
		refreshFlag = true;
		startRequestDataFromNet();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		pageNo++;
		refreshFlag = false;
		startRequestDataFromNet();
	}

}
