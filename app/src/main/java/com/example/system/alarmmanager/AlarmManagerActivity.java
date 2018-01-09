package com.example.system.alarmmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demo.R;

public class AlarmManagerActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mStartServicePollBtn;
    private Button mStopServicePollBtn;
    private Button mStartBroadcastPollBtn;
    private Button mStopBroadcastPollBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_manager);

        mStartServicePollBtn = (Button)findViewById(R.id.start_poll_service_btn);
        mStopServicePollBtn = (Button)findViewById(R.id.stop_poll_service_btn);
        mStartBroadcastPollBtn = (Button)findViewById(R.id.start_poll_broad_cast_btn);
        mStopBroadcastPollBtn = (Button)findViewById(R.id.stop_poll_broad_cast_btn);

        mStartServicePollBtn.setOnClickListener(this);
        mStopServicePollBtn.setOnClickListener(this);
        mStartBroadcastPollBtn.setOnClickListener(this);
        mStopBroadcastPollBtn.setOnClickListener(this);
    }

    @Override
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

            default:
                break;
        }

    }
}
