package com.minjinsuo.zhongchou.service;

import org.xutils.common.util.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.igexin.sdk.PushConsts;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.SharedPreferUtils;

/**
 * 个推广播接收器(1、获取clientId；2、监听获取透传消息)
 * 
 * @author zp
 *
 *         2016年11月1日
 */
public class PushGeTuiReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();

		switch (bundle.getInt(PushConsts.CMD_ACTION)) {
		case PushConsts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
			SharedPreferUtils.setClientId(context, cid);
			ZCApplication.getInstance().setClientId(cid);
			LogUtil.d("PushGeTuiReceiver CID is " + cid);
			break;
		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			String taskid = bundle.getString("taskid");
			String messageid = bundle.getString("messageid");
			byte[] payload = bundle.getByteArray("payload");
			if (payload != null) {
				String data = new String(payload);
				LogUtil.i("接收到的透传消息==" + data);
			}
			break;
		default:
			break;
		}
	}

}
