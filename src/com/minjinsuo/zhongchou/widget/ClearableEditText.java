package com.minjinsuo.zhongchou.widget;

import org.xutils.common.util.LogUtil;

import com.minjinsuo.zhongchou.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;

/**
 * To change clear icon, set
 * <p/>
 * 
 * <pre>
 * android:drawableRight="@drawable/custom_icon"
 * </pre>
 */
public class ClearableEditText extends EditText implements OnTouchListener, OnFocusChangeListener, TextWatcher {

	public interface OnValidateListener {
		void onValidate();
	}

	public interface Listener {
		void didClearText();
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	private Drawable xD;
	private Listener listener;

	private OnValidateListener validator = null;

	public void setOnValidateListener(OnValidateListener validator) {
		this.validator = validator;
	}

	public ClearableEditText(Context context) {
		super(context);
		init();
	}

	public ClearableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Override
	public void setError(CharSequence error, Drawable icon) {
		if (error == null || !TextUtils.isEmpty(error))
			super.setError(error);

		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], icon, getCompoundDrawables()[3]);
	}

	@Override
	public void setOnTouchListener(OnTouchListener l) {
		this.l = l;
	}

	@Override
	public void setOnFocusChangeListener(OnFocusChangeListener f) {
		this.f = f;
	}

	private OnTouchListener l;
	private OnFocusChangeListener f;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (getCompoundDrawables()[2] != null) {
			boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - xD.getIntrinsicWidth());
			if (tappedX) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					setText("");
					if (listener != null) {
						listener.didClearText();
					}
				}
				return true;
			}
		}
		if (l != null) {
			return l.onTouch(v, event);
		}
		return false;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if (hasFocus) {
			boolean b = (getText() != null && getText().length() != 0);
			setClearIconVisible(b);
		} else {
			setClearIconVisible(false);
			if (validator != null)
				validator.onValidate();
		}
		if (f != null) {
			f.onFocusChange(v, hasFocus);
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (isFocused()) {
			boolean b = (s != null && s.length() != 0);
			setClearIconVisible(b);
		}

		String editableText = getText().toString();
		String editable = this.getText().toString();
		// if (!StringUtils.isEmpty(editableText)) {
		// String stringFilter = StringUtils.StringFilter(editableText);
		// if (!stringFilter.equals(editableText)) {
		// // 不满足要求输入
		// CommonUtils.showToast(ZCApplication.getInstance(), "不能输入特殊字符");
		// this.setText(stringFilter);
		// this.setSelection(stringFilter.length()); // 光标置后
		// }
		// }
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void afterTextChanged(Editable s) {
	}

	private void init() {
		xD = getCompoundDrawables()[2];
		if (xD == null) {
			xD = getResources()
					// .getDrawable(android.R.drawable.presence_offline);
					.getDrawable(R.drawable.clear);
		}

		xD.setBounds(0, 0, xD.getIntrinsicWidth(), xD.getIntrinsicHeight());
		setClearIconVisible(false);
		super.setOnTouchListener(this);
		super.setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}

	protected void setClearIconVisible(boolean visible) {
		Drawable x = visible ? xD : null;
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], x, getCompoundDrawables()[3]);
	}
}
