package com.itheima.mobileSafe.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackNumberHelper extends SQLiteOpenHelper {

	public BlackNumberHelper(Context context) {
		super(context, "black.db", null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table blacknumber(_id integer primary key autoincrement,number varchar(20),method varchar(1))");
		
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
