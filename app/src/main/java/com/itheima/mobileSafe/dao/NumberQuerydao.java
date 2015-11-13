package com.itheima.mobileSafe.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NumberQuerydao {

static final String PATH="/data/data/com.itheima.mobileSafe/files/address.db";
public static String findLocation(String number){
	SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
	String regex ="^1[34578]\\d{9}$";
	boolean matches = number.matches(regex);
	String location="";
	if (matches) {
		//是手机号码
		Cursor cursor = db.rawQuery(" select location from data2,data1 where data2.id=data1.outkey and data1.id=?", new String[]{number.substring(0, 7)});
		if(cursor.moveToNext()){
		 location = cursor.getString(0);
		}
		 cursor.close();
			db.close();
		return location;
	} else {
		switch (number.length()) {
		case 3:
			location="报警电话";
			break;
		case 4:
			location="模拟器";
			break;
		case 5:
			location="商业客服电话";
			break;
		case 7:
		case 8:
			location="本地号码";
			break;
		default:
			
			if(number.length()>10&&number.startsWith("0")){
				
				//data2表中 查询 号码的归属地信息
				// select location from data2 where area=?
				Cursor cursor;
				cursor = db.rawQuery("select location from data2 where area=?", new String[]{number.substring(1, 4)});
				if(cursor.moveToNext()){
					
					location =cursor.getString(0);
					location= location.substring(0, location.length()-2);
				}
				cursor.close();
				
				cursor = db.rawQuery("select location from data2 where area=?", new String[]{number.substring(1, 3)});
				if(cursor.moveToNext()){
					
					location =cursor.getString(0);
					location= location.substring(0, location.length()-2);
				}
				cursor.close();
			}
			break;
	}
	
}
	return location;
}
}
