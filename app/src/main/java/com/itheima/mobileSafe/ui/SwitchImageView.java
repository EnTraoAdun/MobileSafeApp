package com.itheima.mobileSafe.ui;

import com.itheima.mobileSafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SwitchImageView extends ImageView {

	public SwitchImageView(Context context) {
		super(context);
	}

	public SwitchImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SwitchImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	private boolean status=false;
	public boolean getStatus(){
		return status;
	}
	public void setStatus(boolean status){
		this.status=status;
		if (status) {
			setImageResource(R.drawable.on);
		} else {
			setImageResource(R.drawable.off);

		}
	}
	public void changStatus(){
		setStatus(!status);
	}
}
