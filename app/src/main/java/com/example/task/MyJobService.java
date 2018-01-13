package com.example.task;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import com.example.DemoApp;

/**
 * Created by hanzai.peng on 2017/9/27.
 */

/**
 * 通过JobScheduler拉活，适用Android5.0及以后版本
 */
public class MyJobService extends JobService {
    public static final String TAG = "MyJobService";

    private boolean isServiceExit = false;

    private volatile static Service mKeepLiveService = null;

    public static boolean isServiceLive(){
        return mKeepLiveService != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mKeepLiveService = this;
    }

    /**
     * 启动JobScheduler拉活
     * 适用范围：用于Android 5.0以后版本进程激活，对被“强制听停止”有效
     */
    public static void startJobScheduler(){
        try{
            int jobId = 1;
            Context appContext = DemoApp.getInstance().getApplicationContext();
            JobInfo.Builder builder = new JobInfo.Builder(jobId,
                    new ComponentName(appContext,MyJobService.class));

            builder.setPeriodic(10);
            builder.setPersisted(true);
            JobScheduler jobScheduler = (JobScheduler) appContext.getSystemService(JOB_SCHEDULER_SERVICE);
            //JobScheduler.
            jobScheduler.schedule(builder.build());
        } catch (Throwable t){
            t.printStackTrace();
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG,"onStartJob");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG,"onStopJob");
        return false;
    }
}
