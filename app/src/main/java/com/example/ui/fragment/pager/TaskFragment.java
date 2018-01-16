package com.example.ui.fragment.pager;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demo.R;
import com.example.task.MyIntentService;
import com.example.task.MyJobService;
import com.example.task.alarmmanager.AlertBroadcastReceiver;
import com.example.task.alarmmanager.PollingService;
import com.example.task.alarmmanager.PollingUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskFragment extends BasePagerFragment {
    public static final String TAG = "CommonWidgetFragment";

    public static TaskFragment newInstance(String title) {
        TaskFragment fragment = new TaskFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({ R.id.start_poll_service_btn,R.id.stop_poll_service_btn,
            R.id.start_poll_broad_cast_btn,R.id.stop_poll_broad_cast_btn,
            R.id.job_scheduler_btn,R.id.intent_service_btn,R.id.handler_btn,
            R.id.async_task_btn,R.id.thread_pool_btn })
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.start_poll_service_btn:
                PollingUtils.startPollingService(getActivity(), 5, PollingService.class, PollingService.ACTION);
                break;

            case R.id.stop_poll_service_btn:
                PollingUtils.stopPollingService(getActivity(), PollingService.class, PollingService.ACTION);
                break;

            case R.id.start_poll_broad_cast_btn:
                PollingUtils.startPollingBroadcast(getActivity(), 5, AlertBroadcastReceiver.class, AlertBroadcastReceiver.ACTION);
                break;

            case R.id.stop_poll_broad_cast_btn:
                PollingUtils.stopPollingBroadcast(getActivity(), AlertBroadcastReceiver.class, AlertBroadcastReceiver.ACTION);
                break;

            case R.id.job_scheduler_btn:
                MyJobService.startJobScheduler();
                break;

            case R.id.intent_service_btn:
                Intent serviceIntent = new Intent(getActivity(),MyIntentService.class);
                getActivity().startService(serviceIntent);
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

    public static class MyAsyncTask extends AsyncTask<String, Integer, String> {
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
