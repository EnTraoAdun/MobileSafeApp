package com.itheima.mobileSafe.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public abstract class BaseSettingActivity extends Activity {
GestureDetector gtd;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gtd=new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				
				if (Math.abs(velocityY)<30) {
					return true;
				}
				if (Math.abs(e1.getRawY()-e2.getRawY())>50) {
					return true;
				}
				if ((e1.getRawX()-e2.getRawX()>100)) {
					//显示下一屏
					next();
				}
				if ((e1.getRawX()-e2.getRawX()<-100)) {
					//显示下一屏
					back();
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
			
		});
	}
	protected abstract void back() ;
	protected abstract void next() ;
	@Override
		public boolean onTouchEvent(MotionEvent event) {
			gtd.onTouchEvent(event);
			return super.onTouchEvent(event);
		}
}
