package com.minjinsuo.zhongchou.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.minjinsuo.zhongchou.R;

/**
 * 专门处理分享传参设置
 * <p/>
 * Created by zp on 2016/8/7.
 */
public class ShareUtils {
	public static void showShare(Context context, String shareCodeUrl,
			String shareContent) {
		ShareSDK.initSDK(context);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
		oks.setTitle("分享");
		// titleUrl是标题的网络链接，QQ和QQ空间等使用
		oks.setTitleUrl(shareCodeUrl);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(shareContent + shareCodeUrl);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		String pathString = context.getFilesDir().getPath() + "/zhongchou.jpg";
		File file1 = new File(pathString);
		if (!file1.exists() || file1.length() <= 0) {
			File file = new File(context.getFilesDir().getPath(),
					"zhongchou.jpg");
			Bitmap bm = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.app_icon);
			try {
				bm.compress(Bitmap.CompressFormat.JPEG, 100,
						new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		oks.setSilent(true);
		oks.setImagePath(context.getFilesDir().getPath() + "/zhongchou.jpg");// 确保SDcard下面存在此张图片

		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(shareCodeUrl);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("赞");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(context.getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(shareCodeUrl);

		// 启动分享GUI
		oks.show(context);
	}
}
