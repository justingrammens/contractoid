package com.localtone.contractoid;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Contractoid extends Activity {
	
	private Button startButton;
	private Button clearButton;
	private Button clearListButton;
	private StopWatch stopWatch;
	private ArrayAdapter<String> timeListAdapter;
	
	final ArrayList<String> timeList = new ArrayList<String>();
    
    private static final int UPDATE_TIME = 1; 
    private TextView time = null;
    
    static NumberFormat formatter = new DecimalFormat("#0.0");
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startButton = (Button) findViewById(R.id.startstop);
        clearButton = (Button) findViewById(R.id.clear);
        clearListButton = (Button) findViewById(R.id.clearlist);
        
        stopWatch = new StopWatch();
        time = (TextView) findViewById(R.id.time);
        
        ListView list = (ListView) findViewById(android.R.id.list); // SHOW THIS IN THE PRESENTATION HOW TO MAPS TO NAMESPACE
        
        timeListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, timeList);
        list.setBackgroundColor(Color.BLACK);
        
        list.setAdapter(timeListAdapter);
        
        startButton.setOnClickListener(ocl);
        clearButton.setOnClickListener(ocl);
        clearListButton.setOnClickListener(ocl);
        
        Timer displayTimer = new Timer();
        displayTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	mHandler.sendMessage(mHandler.obtainMessage(UPDATE_TIME, stopWatch.getElapsedTimeMilli()));
            }
        }, 100, 100);
        
	}
    
    public static String timeAsString(long milliSecs) {
        String s = "";
        int hours = (int) (milliSecs % (1000 * 60 * 60 * 24)) / 3600000;
        int minutes = (int) (milliSecs % 3600000) / 60000;
        double seconds = (double) (milliSecs % 60000) / 1000;
        
        if (hours != 0) {
          s += hours + ":";
        }
        if (minutes != 0) {
          s += minutes + ":";
        }
        s += formatter.format(seconds);
        return s;
      }


	private Handler mHandler = new Handler() {
		 public void handleMessage(Message msg) {
		    switch (msg.what) {
		        case UPDATE_TIME:		        	
		        	Long l = (Long) msg.obj;
		        	time.setText(timeAsString(l.longValue()));
		        	break;
		        default:
		            super.handleMessage(msg);
		    }
		}
	};
	
	
	private OnClickListener ocl = new OnClickListener(){

		public void onClick(View v) {
			Button button = (Button) v;
			String action = button.getText().toString();
            if (action == getString(R.string.start)) {
            	stopWatch.start();
            	startButton.setText(R.string.stop);
            	clearButton.setEnabled(false);
            } else if (action == getString(R.string.stop)) {
            	stopWatch.stop();
            	startButton.setText(R.string.save);
            	clearButton.setEnabled(true);
            } else if (action == getString(R.string.save)){
            	timeList.add(String.valueOf(time.getText()));
            	timeListAdapter.notifyDataSetChanged();
            	startButton.setText(R.string.start);
            	stopWatch.reset();
            } else if (action == getString(R.string.clear)) {
            	stopWatch.reset();
            	time.setText("0");
            	startButton.setText(R.string.start);
            } else if (action == getString(R.string.clearlist)) {
            	timeList.clear();
            	timeListAdapter.notifyDataSetChanged();
            }
		}
		
	};
	
}