package com.itheima.mobileSafe.utils;

import java.util.ArrayList;
import java.util.List;

import com.itheima.mobileSafe.domain.Sms;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class SmsUtils {
	public static List<Sms> getMessage(Context context) {
		ContentResolver resolver = context.getContentResolver();
		Uri uri1 = Uri.parse("content://sms");
		List<Sms> list=new ArrayList<Sms>();
		Cursor c = resolver.query(uri1, new String[]{"address,type,date,body"}, null, null, null);
		while(c.moveToNext()){
			Sms sms = new Sms();
			sms.setAddress(c.getString(0));
			sms.setType(c.getString(1));
			sms.setDate(c.getString(2));
			sms.setBody(c.getString(3));
			list.add(sms);
		}
		return list;
	}
}
