package com.minjinsuo.zhongchou.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.xutils.common.util.LogUtil;

import android.content.Context;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.minjinsuo.zhongchou.model.QueryMoney;
import com.minjinsuo.zhongchou.system.ZCApplication;
import com.minjinsuo.zhongchou.utils.CommonUtils;
import com.minjinsuo.zhongchou.utils.DialogUtils;
import com.minjinsuo.zhongchou.utils.StringUtils;

/*从第三方获取余额
 * @author zp
 *
 *2016年8月23日
 */
public class PayAmountTask extends AsyncTask<String, Void, String> {

	public interface PayAmountCallBack {
		public void onSuccess(QueryMoney response);

		public void onFailure(String errorMsg);

		public void onFailure();

	}

	static Context context;
	PayAmountCallBack callBack;
	boolean isShow;

	public PayAmountTask(Context context, boolean isShow,
			PayAmountCallBack payAmountCallBack) {
		this.context = context;
		this.isShow = isShow;
		this.callBack = payAmountCallBack;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (isShow) {
			DialogUtils.showLoading(context);
		}
	}

	@Override
	protected String doInBackground(String... params) {
		String amount = getAmountStart();
		return amount;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		QueryMoney qm = null;
		if (!StringUtils.isEmpty(result)) {
			LogUtil.i("result:" + result);
			qm = new QueryMoney();
			qm = JSON.parseObject(result, QueryMoney.class);
			if (qm != null) {
				LogUtil.i(qm.getDescription());
				if (qm.getDescription().equals("操作成功")) {
					callBack.onSuccess(qm);
				} else if (!StringUtils.isEmpty(qm.getErrorMsg())) {
					callBack.onFailure(qm.getErrorMsg());
				} else {
					callBack.onFailure();
				}
			}
		} else {
			callBack.onFailure();
		}
	}

	public static String getAmountStart() {
		String result = null;
		String URL_HOSTAMOUNT = CommonUtils.getValue("postUrlAmount");

		String param = null;
		String sessionId = ZCApplication.getInstance().userInfo.getSessionId();
		try {
			param = "sessionId=" + URLEncoder.encode(sessionId, "UTF-8");
			// 建立连接
			URL url = null;
			url = new URL(URL_HOSTAMOUNT);
			HttpURLConnection httpConn = null;
			httpConn = (HttpURLConnection) url.openConnection();
			// 设置参数
			httpConn.setDoOutput(true); // 需要输出
			// httpConn.setDoInput(true); // 需要输入
			httpConn.setUseCaches(false); // 不允许缓存
			httpConn.setRequestMethod("POST");
			// 设置POST方式连接
			// 设置请求属性
			httpConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpConn.setRequestProperty("Charset", "UTF-8");
			// 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
			httpConn.connect();
			// 建立输入流，向指向的URL传入参数
			DataOutputStream dos;
			dos = new DataOutputStream(httpConn.getOutputStream());

			dos.writeBytes(param);
			dos.flush();
			dos.close();

			// 获得响应状态
			int resultCode = httpConn.getResponseCode();

			if (HttpURLConnection.HTTP_OK == resultCode) {
				StringBuffer sb = new StringBuffer();
				String readLine = new String();
				BufferedReader responseReader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(),
								"UTF-8"));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				responseReader.close();
				result = sb.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty(result)) {
			// 解析数据
		}
		return result;
	}
}
