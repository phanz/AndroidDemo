package com.example.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.demo.R;

public class VideoViewActivity extends Activity implements OnClickListener, OnErrorListener, OnCompletionListener {

    // 播放视频对象
    private VideoView mViewView;

    //private String path = "http://v.iask.com/v_play_ipad.php?vid=138152839";
    private String path = "http://www.zcycjy.com/coursePath/%E7%BB%A7%E7%BB%AD%E6%95%99%E8%82%B2/%E6%96%B0%E8%AF%BE%E7%A8%8B%E8%A7%86%E9%A2%91/%E2%80%9C%E8%90%A5%E6%94%B9%E5%A2%9E%E2%80%9D%E6%94%BF%E7%AD%96%E8%A7%A3%E8%AF%BB%E5%8F%8A%E4%BC%81%E4%B8%9A%E7%A8%8E%E5%8A%A1%E9%A3%8E%E9%99%A9%E4%B8%8E%E7%AD%B9%E5%88%92%E6%8E%A2%E8%AE%A81.mp4";

    // 记录暂停播放的位置
    private int currentPosition = 0;

    // 声明一个回放的记录位置
    private int backPosition = 0;
    private MediaController mMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//设置屏幕常亮
        setContentView(R.layout.activity_video_view);

        mViewView = (VideoView) findViewById(R.id.video_view);

        mMediaController = new MediaController(this);
        mViewView.setMediaController(mMediaController);
        mViewView.setVideoURI(Uri.parse(path));
        //mViewView.requestFocus();
        Button playBtn = (Button) findViewById(R.id.play_btn);
        playBtn.setOnClickListener(this);
        Button pauseBtn = (Button) findViewById(R.id.pause_btn);
        pauseBtn.setOnClickListener(this);
        Button stopBtn = (Button) findViewById(R.id.stop_btn);
        stopBtn.setOnClickListener(this);
        Button resetBtn = (Button) findViewById(R.id.reset_btn);
        resetBtn.setOnClickListener(this);
        Button surfacePlayBtn = (Button) findViewById(R.id.video_player_activity);
        surfacePlayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_btn:
                if (mViewView.isPlaying()) {

                } else {
                    mViewView.start();
                    if (currentPosition >= 0) {
                        mViewView.seekTo(currentPosition);
                        currentPosition = -1;
                    }
                }
                break;
            case R.id.pause_btn:
                if (mViewView.canPause()) {
                    mViewView.resume();
                } else {
                    mViewView.pause();
                }
                break;
            case R.id.stop_btn:
                currentPosition = mViewView.getCurrentPosition();
                mViewView.stopPlayback();
                break;
            case R.id.video_player_activity:
                startActivity(new Intent(this, SurfaceVideoActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        finish();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

}
