package com.itheima.mobileSafe.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Savepoint;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.domain.Sms;
import com.itheima.mobileSafe.utils.SaveSmsTool;
import com.itheima.mobileSafe.utils.SmsUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UtilsActvity extends Activity {
	RelativeLayout utils_rl_set1,utils_rl_set2,utils_rl_set3,utils_rl_set4;

protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_utils);
	utils_rl_set1=(RelativeLayout) findViewById(R.id.utils_rl_set1);
	utils_rl_set2=(RelativeLayout) findViewById(R.id.utils_rl_set2);
	utils_rl_set3=(RelativeLayout) findViewById(R.id.utils_rl_set3);
	utils_rl_set4=(RelativeLayout) findViewById(R.id.utils_rl_set4);
	utils_rl_set1.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			Intent intent=new Intent(getApplicationContext(), LocationActivity.class);
			startActivity(intent);
		}
	});
	utils_rl_set2.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			startSave();
			Toast.makeText(getApplicationContext(), "保存成功", 0).show();
		}
	});
	utils_rl_set3.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
	startImport();
	Toast.makeText(getApplicationContext(), "导入成功", 0).show();
		}
	});
}

protected void startImport() {
	new Thread() {
		public void run() {
			try {
				File file = new File(getFilesDir(),"sms.xml");
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(new FileInputStream(file), "UTF-8");
				int type = parser.getEventType();
				Sms cs=new Sms();
				while(type!=XmlPullParser.END_DOCUMENT){
					SystemClock.sleep(500);
					if(type==XmlPullParser.START_TAG){
						if("address".equals(parser.getName())){
							cs.setAddress(parser.nextText());
						}else if("type".equals(parser.getName())){
							String sname = parser.nextText();
							cs.setType(sname);
						}else if("body".equals(parser.getName())){
							String sgender = parser.nextText();
							cs.setBody(sgender);
						}else if("date".equals(parser.getName())){
							cs.setDate(parser.nextText());
						}
					}
					if (type==XmlPullParser.END_TAG) {
						if ("sms".equals(parser.getName())) {
							Log.i("vivi", cs.toString());
							String address = cs.getAddress();
							String date = cs.getDate();
							String body = cs.getBody();
							String type1=cs.getType();
							Uri uri2 = Uri.parse("content://sms");
							ContentResolver resolver = getContentResolver();
							ContentValues putName=new ContentValues();
							putName.put("address", address);
							putName.put("date", date);
							putName.put("body", body);
							putName.put("type", type1);
							resolver.insert(uri2, putName);
						}
					}
					type = parser.next();
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		};
	}.start();
}
ProgressDialog pd;
protected void startSave() {
	pd=new ProgressDialog(UtilsActvity.this);
	pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	pd.show();
SaveSmsTool.saveSms(getApplicationContext(), new SaveSmsTool.SetProcees() {
	public void setprocess(int process) {
		pd.setProgress(process);
	}
	public void setmax(int max) {
		pd.setMax(max);
	}
	@Override
	public void close() {
		pd.dismiss();
		
	}
});

}
	
	
}

