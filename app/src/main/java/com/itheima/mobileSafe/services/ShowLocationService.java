package com.itheima.mobileSafe.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.dao.NumberQuerydao;
import com.itheima.mobileSafe.services.BlackNumberService.smsRecevice;

public class ShowLocationService extends Service {
	TelephonyManager tm;
	CallstatusListener listener;
	SharedPreferences sp;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	newCall recevice;

	public void onCreate() {
		super.onCreate();
		sp = getSharedPreferences("status", 0);
		// 当电话打过来时的监听器
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new CallstatusListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		// 当打出去电话的广播接受者
		IntentFilter filter = new IntentFilter(
				"android.intent.action.NEW_OUTGOING_CALL");
		filter.setPriority(1000);
		recevice = new newCall();
		registerReceiver(recevice, filter);
	}

	public void onDestroy() {
		unregisterReceiver(recevice);
		recevice = null;
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
	};

	String Location;

	// 拨打电话,获取拨打的号码
	class newCall extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String data = getResultData();
			Location = NumberQuerydao.findLocation(data);
			showLocation(Location);
			String ip = sp.getString("ip", "17951");
			if (!data.startsWith("0")) {
				setResultData(ip+data);
			}
		}

	}

	class CallstatusListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				hideview();
				break;
			// 响铃状态下
			case TelephonyManager.CALL_STATE_RINGING:
				Location = NumberQuerydao.findLocation(incomingNumber);
				if (Location != null) {
					showLocation(Location);
				}

				break;
			// 接通状态下
			case TelephonyManager.CALL_STATE_OFFHOOK:

				break;

			}

		}

	}

	View v;
	WindowManager wm;
	LayoutParams params;
	int[] styles_background = { R.drawable.call_locate_blue,
			R.drawable.call_locate_gray, R.drawable.call_locate_green,
			R.drawable.call_locate_orange };

	public void showLocation(String location) {
		v = View.inflate(getApplicationContext(), R.layout.my_toast, null);
		TextView tv_location = (TextView) v.findViewById(R.id.tv_location);
		int which = sp.getInt("which", 0);
		tv_location.setBackgroundResource(styles_background[which]);
		tv_location.setText(location);
		v.setOnTouchListener(new OnTouchListener() {
			float startx, starty;

			@Override
			// 实现拖动
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				// 按下
				case MotionEvent.ACTION_DOWN:
					startx = event.getRawX();
					starty = event.getRawY();
					break;
				// 移动
				case MotionEvent.ACTION_MOVE:

					float nowX = event.getRawX();
					float nowY = event.getRawY();
					float dx = nowX - startx;
					float dy = nowY - starty;
					params.x += dx;
					params.y += dy;
					// params.x=(int) event.getRawX();
					// params.y=(int) event.getRawY()-16;
					wm.updateViewLayout(v, params);
					startx = nowX;
					starty = nowY;
					break;
				// 抬起
				case MotionEvent.ACTION_UP:
					break;

				}
				return false;
			}
		});
		params = new WindowManager.LayoutParams();
		// 设置初始位置
		params.gravity = Gravity.TOP + Gravity.LEFT;
		params.x = 60;
		params.y = 60;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		params.format = PixelFormat.TRANSLUCENT;
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		wm.addView(v, params);
	}

	public void hideview() {
		if (v != null) {
			wm.removeView(v);
		}
	}
}
