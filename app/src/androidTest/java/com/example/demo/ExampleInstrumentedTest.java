package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        final Context appContext = InstrumentationRegistry.getTargetContext();

        //assertEquals("com.example.demo", appContext.getPackageName());
        String[] testStr = new String[]{"Hello","World"};
        final String s = TextUtils.join(" ",testStr);
        /*HandlerThread workThread = new HandlerThread("WorkThread");
        workThread.start();
        Handler handler = new Handler(workThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(appContext,s,Toast.LENGTH_LONG).show();
            }
        });*/
        Intent intent = new Intent(appContext,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        Log.d("TAG",s);
    }
}
