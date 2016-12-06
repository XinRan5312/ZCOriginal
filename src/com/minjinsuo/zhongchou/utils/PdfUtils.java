package com.minjinsuo.zhongchou.utils;

import java.io.File;

import org.xutils.common.util.LogUtil;

import android.content.Context;
import android.content.Intent;

import com.minjinsuo.zhongchou.utils.FileUtil.fileCallBack;
import com.minjinsuo.zhongchou.widget.pdf.MuPDFActivity;

/**
 * PDF处理类——先下载后展示
 * 
 * @author zp
 *
 *         2016年9月24日
 */
public class PdfUtils {
	private static final String NAME_PDF = "name_pdf";

	/**
	 * 下载操作及存储合同
	 * 
	 * @author zp
	 *
	 *         2015年12月7日
	 */
	// private static String urlpath =
	// "http://www.adobe.com/content/dam/Adobe/en/devnet/acrobat/pdfs/pdf_open_parameters.pdf";

	// 从服务器下载PDF并且跳转到MUPDF的ACTIVITY
	public static void showPDF(String urlpath, final Context context)
			throws Exception {
		String path = createDir("contract.pdf", context);
		// DialogUtils.showLoading(context);
		// 下载pdf文件（确保服务器防火墙允许）
		new FileUtil().downloadFile(context, urlpath, path, new fileCallBack() {

			@Override
			public void onSuccess() {
				// DialogUtils.dismisLoading();
				String pathDir = context.getFilesDir().getPath()
						+ "/zhongChouApp/contract.pdf";
				File file = new File(pathDir);
				if ((int) getDirSize(file) == 0) {
					ToastUtil.showToast(context, "无有效文件");
					return;
				}
				Intent intent = new Intent(context, MuPDFActivity.class);
				intent.setAction(Intent.ACTION_VIEW);
				context.startActivity(intent);
			}

			@Override
			public void onLoading(long total, long current) {
				if (!StringUtils.isEmpty(current + "")) {
					LogUtil.i("已下载==" + current);
				}
			}

			@Override
			public void onFailure() {
				DialogUtils.dismisLoading();
				LogUtil.i("下载失败");
			}
		});
	}

	private static String createDir(String filename, Context context) {
		String path = context.getFilesDir().getPath() + "/zhongChouApp";
		File path1 = new File(path);
		if (!path1.exists())
			// 若不存在，创建目录，可以在应用启动的时候创建
			path1.mkdirs();
		path = path + "/" + filename;
		return path;
	}

	/**
	 * 删除某个文件夹下的所有文件夹和文件
	 * 
	 * @param delpath
	 * @return
	 */
	public void deleteMyFile(String delpath, Context context) {
		try {
			File file = new File(delpath);
			if (!file.isDirectory()) {
				System.out.println("1");
				file.delete();
			} else if (file.isDirectory()) {
				System.out.println("2");
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					File delfile = fileList[i];
					if (!delfile.isDirectory()) {
						System.out.println("相对路径=" + delfile.getPath());
						System.out.println("绝对路径=" + delfile.getAbsolutePath());
						System.out.println("文件全名=" + delfile.getName());
						delfile.delete();
						System.out.println("删除文件成功");
					} else if (delfile.isDirectory()) {
						context.deleteFile(fileList[i].getPath());
					}
				}
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double getDirSize(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else {// 如果是文件则直接返回其大小
				double size = (double) file.length() / 1024;
				return size;
			}
		} else {
			System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;
		}
	}
}
