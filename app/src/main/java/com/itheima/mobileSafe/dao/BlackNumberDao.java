package com.itheima.mobileSafe.dao;

import java.util.ArrayList;
import java.util.List;

import com.itheima.mobileSafe.domain.BlackNumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BlackNumberDao {
BlackNumberHelper helper;

public BlackNumberDao(Context context) {
	super();
	helper=new BlackNumberHelper(context);
	
}
//增加
public boolean insert(String number,String method){
	 SQLiteDatabase db = helper.getWritableDatabase();
	 ContentValues cv=new ContentValues();
		Log.i("vivi", method+""+"helper");
		Log.i("vivi", number+""+"helper");
	 cv.put("number", number);
	 cv.put("method", method);
	 
	 long i = db.insert("blacknumber", null, cv);
	 db.close();
	if (i==-1) {
		return false;
	} else {

		return true;
	}
}
//删除
public boolean delete(String number){
	SQLiteDatabase db = helper.getWritableDatabase();
	int i = db.delete("blacknumber", "number=?", new String[]{number});
	 db.close();
	if (i!=0) {
		return true;
	}
	return false;
}
//修改
public boolean update(String number,String method){
	SQLiteDatabase db = helper.getWritableDatabase();
	ContentValues cv=new ContentValues();
	cv.put("method", method);
	 db.close();
	int isupdate = db.update("blacknumber", cv, "number=?", new String[]{number});
	if (isupdate>0) {
		return true;
	}
	return false;
}
public String find(String number){
	SQLiteDatabase db = helper.getWritableDatabase();
	Cursor query = db.query("blacknumber", new String[]{"method"}, "number=?", new String[]{number}, null, null, null);
	if (query.moveToNext()) {
		String method = query.getString(0);
	
		return method;
	}
	return null;
}
//查询所有的

public List<BlackNumber> query(){
	List<BlackNumber> list =new ArrayList<BlackNumber>();
	SQLiteDatabase db = helper.getWritableDatabase();
	Cursor query = db.query("blacknumber", new String[]{"_id,number,method"}, null, null, null, null, null);
	while (query.moveToNext()) {
		int id = query.getInt(0);
		String number = query.getString(1);
		String method = query.getString(2);
		BlackNumber blackNumber = new BlackNumber(id, number, method);
		list.add(blackNumber);
	}
	query.close();
	 db.close();
	return list;
}
			
}
