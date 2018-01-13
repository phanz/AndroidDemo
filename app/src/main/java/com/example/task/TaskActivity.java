package com.example.task;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.demo.R;
import com.example.task.alarmmanager.AlertBroadcastReceiver;
import com.example.task.alarmmanager.PollingService;
import com.example.task.alarmmanager.PollingUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import butterknife.OnClick;

public class TaskActivity extends AppCompatActivity {
    public static final String TAG = "TaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
    }

    @OnClick({ R.id.start_poll_service_btn,R.id.stop_poll_service_btn,
            R.id.start_poll_broad_cast_btn,R.id.stop_poll_broad_cast_btn,
            R.id.job_scheduler_btn,R.id.intent_service_btn,R.id.handler_btn,
            R.id.async_task_btn,R.id.thread_pool_btn })
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.start_poll_service_btn:
                PollingUtils.startPollingService(this, 5, PollingService.class, PollingService.ACTION);
                break;

            case R.id.stop_poll_service_btn:
                PollingUtils.stopPollingService(this, PollingService.class, PollingService.ACTION);
                break;

            case R.id.start_poll_broad_cast_btn:
                PollingUtils.startPollingBroadcast(this, 5, AlertBroadcastReceiver.class, AlertBroadcastReceiver.ACTION);
                break;

            case R.id.stop_poll_broad_cast_btn:
                PollingUtils.stopPollingBroadcast(this, AlertBroadcastReceiver.class, AlertBroadcastReceiver.ACTION);
                break;

            case R.id.job_scheduler_btn:
                MyJobService.startJobScheduler();
                break;

            case R.id.intent_service_btn:
                Intent serviceIntent = new Intent(this,MyIntentService.class);
                startService(serviceIntent);
                break;

            case R.id.handler_btn:
                HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"MyHandlerThread start");
                        SystemClock.sleep(2000);
                        Log.d(TAG,"MyHandlerThread end");
                    }
                });
                break;

            case R.id.async_task_btn:
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute("hello");
                break;

            case R.id.thread_pool_btn:
                ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
                singleThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG,"singleThreadPool");
                    }
                });
                break;

            default:
                break;
        }

    }

    public class MyAsyncTask extends AsyncTask<String, Integer, String> {
        public static final String TAG = "MyAsyncTask";

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: " + strings);
            publishProgress(0);
            SystemClock.sleep(2000);
            publishProgress(50);
            SystemClock.sleep(2000);
            publishProgress(100);
            Log.d(TAG, "doInBackground:  end");
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            Log.d(TAG, "onCancelled");
        }

        @Override
        protected void onCancelled() {
            Log.d(TAG, "onCancelled");
        }
    }
}
