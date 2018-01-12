package com.example.task;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.demo.R;
import com.example.task.alarmmanager.AlertBroadcastReceiver;
import com.example.task.alarmmanager.PollingService;
import com.example.task.alarmmanager.PollingUtils;

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

                break;

            case R.id.intent_service_btn:

                break;

            case R.id.handler_btn:

                break;

            case R.id.async_task_btn:

                break;

            case R.id.thread_pool_btn:

                break;

            default:
                break;
        }

    }
}
