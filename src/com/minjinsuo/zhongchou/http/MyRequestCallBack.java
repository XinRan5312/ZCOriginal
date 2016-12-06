package com.minjinsuo.zhongchou.http;

import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

public interface MyRequestCallBack {
	public void onSuccess(ResponseSupport response);

	public void failure();

	public void onFailure(ResponseSupport response);
}
