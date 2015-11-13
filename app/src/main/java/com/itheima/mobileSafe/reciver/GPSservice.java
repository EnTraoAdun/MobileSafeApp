package com.itheima.mobileSafe.reciver;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;

public class GPSservice extends Service {
SharedPreferences sp;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
MyLocation ml;
private LocationManager manager;
	@Override
	public void onCreate() {
		super.onCreate();
		sp=getSharedPreferences("config", 0);
		manager = (LocationManager) getSystemService(LOCATION_SERVICE);
		ml=new MyLocation();
		manager.requestLocationUpdates("gps", 0, 500, ml);
	}
class MyLocation implements LocationListener{

	@Override
	public void onLocationChanged(Location location) {
		double longitude = location.getLongitude();
		double latitude = location.getLatitude();
		double altitude = location.getAltitude();
		SmsManager smsManager=SmsManager.getDefault();
		String number = sp.getString("number", "15980904762");
		smsManager.sendTextMessage(number, null,  longitude+"----"+latitude+"---"+altitude, null, null);
		manager.removeUpdates(ml);
		ml=null;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}
	
	
}
}
