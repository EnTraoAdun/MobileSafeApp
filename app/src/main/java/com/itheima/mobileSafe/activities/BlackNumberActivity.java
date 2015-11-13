package com.itheima.mobileSafe.activities;

import java.util.List;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.dao.BlackNumberDao;
import com.itheima.mobileSafe.domain.BlackNumber;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BlackNumberActivity extends Activity {
ImageView black_empty;
LinearLayout black_ll;
ListView black_lv;
BlackNumberDao dao;
	
	List<BlackNumber> list;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_backnumber);
	black_empty=	(ImageView) findViewById(R.id.black_empty);
	black_ll=(LinearLayout) findViewById(R.id.black_ll);
	black_lv=(ListView) findViewById(R.id.black_lv);
	 dao=new BlackNumberDao(this);
	filldata();
}
public void add(View v){
	Intent intent=new Intent(this, AddBlackNumberActivity.class);
	startActivityForResult(intent, 0);
}
class Myadapter extends BaseAdapter{

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.i("vivi", list.toString());
		View v;
		ViewHodler hodler;
		if (convertView!=null) {
			v=convertView;
			 hodler = (ViewHodler) convertView.getTag();
		} else {
			v=View.inflate(getApplicationContext(), R.layout.blacknumber_item, null);
			hodler=new ViewHodler();
			hodler.black_delete=(ImageView) v.findViewById(R.id.black_delete);
			hodler.black_item_number=(TextView) v.findViewById(R.id.black_item_number);
			hodler.black_item_method=(TextView) v.findViewById(R.id.black_item_method);
			v.setTag(hodler);
		}
		final BlackNumber bn=list.get(position);
		hodler.black_item_number.setText(bn.getNumber());
		String method = bn.getMethod();
		if ("1".equals(method)) {
			hodler.black_item_method.setText("拒接电话");
		} else if("2".equals(method)) {
			hodler.black_item_method.setText("拒收短信");
		}else{
			hodler.black_item_method.setText("拒接电话和短信");
		}
		hodler.black_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean delete = dao.delete(bn.getNumber());
				if (delete) {
					list.remove(position);
					Toast.makeText(getApplicationContext(), "删除成功", 0).show();
					adapter.notifyDataSetChanged();
				}
				Toast.makeText(getApplicationContext(), "删除实拍", 0).show();
			}
		});
		return v;
	}}
 static class ViewHodler{
	TextView black_item_number;
	TextView black_item_method;
	ImageView black_delete;
}
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	 
	 if (data!=null) {
		String method = data.getStringExtra("method");
		String number = data.getStringExtra("number");
		Log.i("vivi", method);
		Log.i("vivi", number);
		BlackNumber blackNumber = new BlackNumber();
		blackNumber.setMethod(method);
		blackNumber.setNumber(number);
		Log.i("vivi", blackNumber.toString());
		Log.i("vivi", list.toString());
		list.add(blackNumber);
		adapter.notifyDataSetChanged();
	}
 };
 
Myadapter adapter;
private void load() {
	
		
		if (list!=null) {
			black_ll.setVisibility(View.GONE);
			adapter=new Myadapter();
			black_lv.setAdapter(adapter);
		} else {
			black_ll.setVisibility(View.GONE);
			black_empty.setVisibility(View.VISIBLE);
		}
	

}
public void filldata() {
	new Thread(){
	public void run() {
		 list = dao.query();
		 Log.i("vivirun", list.toString());
		runOnUiThread(new Runnable() {
			
			public void run() {
				load();
			}

		});
	};
	}.start();
}
}
