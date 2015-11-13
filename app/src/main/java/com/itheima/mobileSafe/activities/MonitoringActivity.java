package com.itheima.mobileSafe.activities;

import java.util.ArrayList;
import java.util.List;

import com.itheima.mobileSafe.R;
import com.itheima.mobileSafe.domain.FluxInfo;
import com.itheima.mobileSafe.engine.FluxInfoProvider;

import android.app.Activity;
import android.graphics.Color;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MonitoringActivity extends Activity {
	TextView monitoring_tv1, monitoring_tv2,monitoring_tv3,monitoring_tv4;
	ListView monitoring_lv;
	List<FluxInfo> list;
	List<FluxInfo> user;
	List<FluxInfo> system;
	LinearLayout monitoring_ll;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monitoring);
		long totalRxBytes = TrafficStats.getTotalRxBytes();
		long totalTxBytes = TrafficStats.getTotalTxBytes();
		long mobileRxBytes = TrafficStats.getMobileRxBytes();
		long mobileTxBytes = TrafficStats.getMobileTxBytes();
		monitoring_tv1 = (TextView) findViewById(R.id.monitoring_tv1);
		monitoring_tv2 = (TextView) findViewById(R.id.monitoring_tv2);
		monitoring_tv3 = (TextView) findViewById(R.id.monitoring_tv3);
		monitoring_tv4 = (TextView) findViewById(R.id.monitoring_tv4);
		monitoring_lv=(ListView) findViewById(R.id.monitoring_lv);
		monitoring_ll=(LinearLayout) findViewById(R.id.monitoring_ll);
		monitoring_tv1.setText("接受流量:"
				+ Formatter.formatFileSize(getApplicationContext(),
						totalRxBytes));
				
		monitoring_tv2.setText("发送流量"
				+ Formatter.formatFileSize(getApplicationContext(),
						totalTxBytes));
		monitoring_tv3.setText("接受运营商流量:"
				+ Formatter.formatFileSize(getApplicationContext(),
						mobileRxBytes));
		
		monitoring_tv4.setText("发送运营商流量"
				+ Formatter.formatFileSize(getApplicationContext(),
						mobileTxBytes));
		fillData();
	}


	private void fillData() {
		new Thread() {
			public void run() {
				list = FluxInfoProvider.getFluxInfo(getApplicationContext());
				SystemClock.sleep(3000);
				user = new ArrayList<FluxInfo>();
				system = new ArrayList<FluxInfo>();
				for (FluxInfo info : list) {
					if (info.isUserApp()) {
						user.add(info);
					} else {
						system.add(info);
					}
				}
				Log.i("vivi", list.toString());
				runOnUiThread(new Runnable() {

					public void run() {
						monitoring_ll.setVisibility(View.GONE);  
						adapter = new Myadapter();
						monitoring_lv.setAdapter(adapter);
					}

				});
			};

		}.start();
		
	}
	Myadapter adapter;
	class Myadapter extends BaseAdapter{

		@Override
		public int getCount() {
			return list.size()+2;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v;
			viewHolder holder;
			FluxInfo info = null;

			if (position == 0) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("用户应用 : " + user.size() + "个");
				return tv;
			} else if (position > 0 && position <= user.size()) {
				info = user.get(position - 1);

			} else if (position == user.size() + 1) {
				TextView tv = new TextView(getApplicationContext());
				tv.setBackgroundColor(Color.GRAY);
				tv.setTextColor(Color.WHITE);
				tv.setText("系统应用 : " + system.size() + "个");
				return tv;
			} else {
				info = system.get(position - user.size() - 2);

			}
			if (convertView != null && convertView.getTag() != null) {
				v = convertView;
				holder = (viewHolder) convertView.getTag();
			} else {
				v = View.inflate(getApplicationContext(), R.layout.flux_item,
						null);
				holder = new viewHolder();
				holder.flux_image = (ImageView) v.findViewById(R.id.flux_image);
				holder.flux_name = (TextView) v.findViewById(R.id.flux_name);
				holder.flux_receiver = (TextView) v
						.findViewById(R.id.flux_receiver);
				holder.flux_sender = (TextView) v.findViewById(R.id.flux_sender);
				v.setTag(holder);
			}

			holder.flux_image.setImageDrawable(info.getIcon());
			holder.flux_name.setText(info.getAppName());
			holder.flux_receiver.setText(Formatter.formatFileSize(getApplicationContext(), info.getReceiver()));
			holder.flux_sender.setText(Formatter.formatFileSize(getApplicationContext(), info.getSender()));
			return v;
		}
		
		
	}
	static class viewHolder {
		ImageView flux_image ;
		TextView flux_name, flux_receiver, flux_sender;

	}
}
