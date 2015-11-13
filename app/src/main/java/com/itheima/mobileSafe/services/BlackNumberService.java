package com.itheima.mobileSafe.services;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;

import com.android.internal.telephony.ITelephony;
import com.itheima.mobileSafe.dao.BlackNumberDao;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BlackNumberService extends Service {

	public IBinder onBind(Intent intent) {
		return null;
	}

	class smsRecevice extends BroadcastReceiver {
		String number;

		public void onReceive(Context context, Intent intent) {
			Object[] object = (Object[]) intent.getExtras().get("pdus");

			for (Object object2 : object) {
				SmsMessage message = SmsMessage.createFromPdu((byte[]) object2);
				number = message.getOriginatingAddress();
			}
			String find = dao.find(number);
			if ("2".equals(find)||"3".equals(find)) {
				Log.i("BlackNumberService", "拦截");
				abortBroadcast();
			}  
		}
	}
	BlackNumberDao dao;
TelephonyManager tm;
CallstatusListener listener;
smsRecevice recevice;
	public void onCreate() {
		super.onCreate();
		dao=new BlackNumberDao(getApplicationContext());
		Log.i("vivi", "服务开启");
			tm=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
			listener=new CallstatusListener();
			tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		//动态注册
		IntentFilter filter=new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(1000);
		recevice = new smsRecevice();
		registerReceiver(recevice, filter);
	}
class CallstatusListener extends PhoneStateListener{
	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		super.onCallStateChanged(state, incomingNumber);
		switch (state) {
		//空闲状态下
		case TelephonyManager.CALL_STATE_IDLE:
			
			break;
		//响铃状态下
		case TelephonyManager.CALL_STATE_RINGING:
			String find = dao.find(incomingNumber);
			Log.i("vivi", "进来1");
			if ("1".equals(find)||"3".equals(find)) {
				Log.i("vivi", "进来2");
				endCall(incomingNumber);
			}
			break;
		//接通状态下
		case TelephonyManager.CALL_STATE_OFFHOOK:
			
			break;

		}
		
	}
	
}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//取消 电话的监听, 
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		
		// 取消注册的广播接收者 
		unregisterReceiver(recevice);
		recevice = null;
		Log.i("vivi", "服务关闭");
	}
	public void endCall(String incomingNumber) {
		try {
			//通过反射调用底层方法
			Class name = Class.forName("android.os.ServiceManager");
			Method method = name.getMethod("getService", String.class);
			IBinder ib = (IBinder) method.invoke(null, Context.TELEPHONY_SERVICE);
			ITelephony asInterface = ITelephony.Stub.asInterface(ib);
			asInterface.endCall();
			deletecalllog(incomingNumber);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	private void deletecalllog(final String incomingNumber) {
		final Uri uri=Uri.parse("content://call_log/calls");
		getContentResolver().registerContentObserver(uri, true, new ContentObserver(new Handler()) {
			public void onChange(boolean selfChange) {
				super.onChange(selfChange);
				getContentResolver().delete(uri, "number=?", new String[]{incomingNumber});
			}
		});
	}
	
}
