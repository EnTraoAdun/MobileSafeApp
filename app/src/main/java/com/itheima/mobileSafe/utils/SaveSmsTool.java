package com.itheima.mobileSafe.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;

import com.itheima.mobileSafe.domain.Sms;

public class SaveSmsTool {
	public interface SetProcees{
		void setmax(int max);
		void setprocess(int process);
		void close();
	}
	public static void saveSms(final Context context,final SetProcees callback){
		new Thread(){
			public void run() {
				try {
					
					List<Sms> list = SmsUtils.getMessage(context.getApplicationContext());
					Log.i("vivi", "list.size="+list.size());
					callback.setmax(list.size());
					Log.i("vivi", list.toString());
					File file = new File(context.getFilesDir(), "sms.xml");
					OutputStream out = new FileOutputStream(file);
					XmlSerializer serializer = Xml.newSerializer();
					serializer.setOutput(out, "UTF-8");
					serializer.startDocument("UTF-8", true);
					int count = 0;
					for (Sms contacts : list) {
						String address = contacts.getAddress();
						String type = contacts.getType();
						String date = contacts.getDate();
						String body = contacts.getBody();
						Log.i("vivi", "body="+body+"");
						Log.i("vivi", "address="+address+"");
						serializer.startTag(null, "sms");
						serializer.startTag(null, "address");
						serializer.text(address);
						serializer.endTag(null, "address");
						serializer.startTag(null, "type");
						serializer.text(type);
						serializer.endTag(null, "type");
						serializer.startTag(null, "date");
						serializer.text(date);
						serializer.endTag(null, "date");
						serializer.startTag(null, "body");
						serializer.text(body);
						serializer.endTag(null, "body");
						serializer.endTag(null, "sms");
						count++;
						callback.setprocess(count);
						Log.i("vivi", "count="+count+"");
					}
					serializer.endDocument();
					out.close();
					callback.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		}.start();
		
		
	}

}
