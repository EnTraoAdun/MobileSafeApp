package com.itheima.mobileSafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarquueTextView extends TextView{

	public MarquueTextView(Context context) {
		super(context);
	}
	public MarquueTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	public MarquueTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
