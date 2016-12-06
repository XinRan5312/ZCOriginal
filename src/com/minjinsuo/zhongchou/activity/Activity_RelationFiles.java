package com.minjinsuo.zhongchou.activity;

import java.util.List;

import net.zkbc.p2p.fep.message.protocol.GetInvestFileListResponse;
import net.zkbc.p2p.fep.message.protocol.ResponseSupport;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.minjinsuo.zhongchou.R;
import com.minjinsuo.zhongchou.adapter.Adapter_RelationFiles;
import com.minjinsuo.zhongchou.model.Model_Tender;
import com.minjinsuo.zhongchou.utils.PdfUtils;

/**
 * 相关文件
 * 
 * @author zp
 *
 *         2016年6月23日
 */
public class Activity_RelationFiles extends Activity_Base implements
		OnItemClickListener {

	@ViewInject(R.id.gv_list)
	GridView gv_list;

	private Adapter_RelationFiles adapter_list;
	private List<Model_Tender> list;

	private GetInvestFileListResponse model_file;// 相关文件

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.aty_relation_files);
		x.view().inject(this);
		initView();
		initData();
	}

	@Override
	protected void initView() {
		setTitleText("相关文件");
		setTitleBack();

		if (getIntent() != null
				&& getIntent().getSerializableExtra("model") != null) {
			model_file = (GetInvestFileListResponse) getIntent().getExtras()
					.getSerializable("model");

			initList();
		}
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initListener() {

	}

	/**
	 * 列表数据
	 */
	public void initList() {
		adapter_list = new Adapter_RelationFiles(this);
		adapter_list.setData(model_file.getInvestFileList());
		gv_list.setAdapter(adapter_list);

		gv_list.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		LogUtil.i("点击了==" + position);
		Intent intent = null;
		String fileAdd = model_file.getInvestFileList().get(position)
				.getFileAddr();
		String fileType = fileAdd.substring(fileAdd.lastIndexOf(".") + 1,
				fileAdd.lastIndexOf("/"));
		if (fileType.equals("jpg") || fileType.equals("png")
				|| fileType.equals("JPG") || fileType.equals("PNG")
				|| fileType.equals("jpeg") || fileType.equals("JPEG")
				|| fileType.equals("gif") || fileType.equals("GIF")) {
			intent = new Intent(Activity_RelationFiles.this,
					Activity_BigImageView.class);
			intent.putExtra("url", model_file.getInvestFileList().get(position)
					.getFileAddr());
		} else if (fileType.equals("pdf") || fileType.equals("PDF")) {
			LogUtil.i("this is pdf~~");
			try {
				PdfUtils.showPDF(fileAdd, Activity_RelationFiles.this);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			intent = new Intent(Activity_RelationFiles.this,
					Act_CommonWeb.class);
			intent.putExtra("url", fileAdd);
			intent.putExtra("title", "文件预览");
		}

		if (intent != null) {
			startActivity(intent);
		}
	}

	@Override
	public void onSuccess(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void failure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFailure(ResponseSupport response) {
		// TODO Auto-generated method stub

	}

}
