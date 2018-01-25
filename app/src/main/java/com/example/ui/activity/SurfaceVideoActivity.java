package com.example.ui.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.R;

public class SurfaceVideoActivity extends Activity implements OnPreparedListener,Callback{

    private String videoUrl = "http://www.zcycjy.com/coursePath/%E7%BB%A7%E7%BB%AD%E6%95%99%E8%82%B2/%E6%96%B0%E8%AF%BE%E7%A8%8B%E8%A7%86%E9%A2%91/%E2%80%9C%E8%90%A5%E6%94%B9%E5%A2%9E%E2%80%9D%E6%94%BF%E7%AD%96%E8%A7%A3%E8%AF%BB%E5%8F%8A%E4%BC%81%E4%B8%9A%E7%A8%8E%E5%8A%A1%E9%A3%8E%E9%99%A9%E4%B8%8E%E7%AD%B9%E5%88%92%E6%8E%A2%E8%AE%A81.mp4";

    private SurfaceView videoSurfaceView;// 绘图容器对象，用于把视频显示在屏幕上
    private ProgressBar videoProgressBar;

    private LinearLayout videoTitleLayout;
    private Button videoBackBtn;
    private TextView videoTitleText;// 视频名称显示的view

    private LinearLayout videoBottomLayout;
    private Button videoPlayBtn;// 用于开始和暂停的按钮
    private TextView videoPlayTimeText;
    private SeekBar videoSeekBar;// 进度条控件
    private TextView videoTitleTimeText;
    private Button videoFullBtn;
    private Button videoLockBtn;

    private MediaPlayer mediaPlayer; // 播放器控件
    private int postSize; // 保存已播视频大小
    private boolean isPlaying = true; // 用于判断视频是否在播放中

    private static long lastClickTime;
    private int position = 0;

    private boolean isLocked = false;

    private static final int MSG_HIDDEN_AROUND_VIEW = 0x124;
    private static final int MSG_UPDATE_SEEK_BAR = 0x125;

    /**
     * 更新进度条
     */
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == MSG_HIDDEN_AROUND_VIEW) {//开始播放3秒后隐藏头部和底部控件
                showTitleAndBottom(false);

            } else if(msg.what == MSG_UPDATE_SEEK_BAR){//更新进度条
                updateSeekBar();
                if (isPlaying) {
                    mHandler.sendEmptyMessage(MSG_UPDATE_SEEK_BAR);
                }
            }
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_surface_video);
        initView();
        preparePlayVideo();
        setListener();

    }

    private void initView() {
        videoSurfaceView = (SurfaceView) findViewById(R.id.video_surface_view);
        videoProgressBar = (ProgressBar) findViewById(R.id.video_progress_bar);

        videoTitleLayout = (LinearLayout) findViewById(R.id.video_title_layout);
        videoBackBtn = (Button) findViewById(R.id.video_back_btn);
        videoTitleText = (TextView) findViewById(R.id.video_title_text);

        videoBottomLayout = (LinearLayout) findViewById(R.id.video_bottom_layout);
        videoPlayBtn = (Button) findViewById(R.id.video_play_btn);
        videoPlayTimeText = (TextView) findViewById(R.id.video_play_time);
        videoSeekBar = (SeekBar) findViewById(R.id.video_time_seek_bar);
        videoTitleTimeText = (TextView) findViewById(R.id.video_total_time);
        videoFullBtn = (Button) findViewById(R.id.video_full_btn);

        videoLockBtn = (Button) findViewById(R.id.video_lock_btn);

        mediaPlayer = new MediaPlayer(); // 创建一个播放器对象
    }

    @SuppressWarnings("deprecation")
    private void preparePlayVideo() {
        videoProgressBar.setVisibility(View.VISIBLE);
        videoTitleText.setText("视屏名称xxx");// 视频标题
        videoPlayBtn.setEnabled(false); // 刚进来，设置其不可点击
        videoSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        videoSurfaceView.getHolder().setKeepScreenOn(true); // 保持屏幕高亮
        videoSurfaceView.getHolder().addCallback(this); // 设置监听事件
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_HIDDEN_AROUND_VIEW), 3000);//隐藏控件
    }

    private void setListener() {
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                videoSeekBar.setSecondaryProgress(percent);
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // 视频播放完成
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
                videoPlayTimeText.setText(secondToDuration(mediaPlayer.getDuration() / 1000));
                videoPlayBtn.setBackgroundResource(R.drawable.qiyi_sdk_play_btn_player);
                // 播放完成后需要自动播放下一集
                Log.e("mediaPlayer", "本集结束");
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
                Log.e("mediaPlayer", "无法播放：" + arg1 + "：" + arg2);
                return false;
            }
        });
        /**
         * 如果视频在播放，则调用mediaPlayer.pause();，停止播放视频，反之，mediaPlayer.start()
         * ，同时换按钮背景
         */
        videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    videoPlayBtn.setBackgroundResource(R.drawable.qiyi_sdk_play_btn_player);
                    mediaPlayer.pause();
                    postSize = mediaPlayer.getCurrentPosition();
                } else {
                    videoPlayBtn.setBackgroundResource(R.drawable.qiyi_sdk_play_btn_pause);
                    mediaPlayer.start();
                    if (isPlaying == false) {
                        isPlaying = true;
                        //new Thread(playingSeekBar).start();
                        mHandler.sendEmptyMessage(MSG_UPDATE_SEEK_BAR);
                    }
                }
            }
        });
        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (isFastDoubleClick()) {
                    // 计算进度条需要前进的位置数据大小
                    int value = videoSeekBar.getProgress() * mediaPlayer.getDuration() / videoSeekBar.getMax();
                    videoPlayTimeText.setText(secondToDuration(value / 1000));
                    mediaPlayer.seekTo(value);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar,int progress, boolean fromUser) {

            }
        });
        /**
         * 点击屏幕，切换控件的显示，显示则应藏，隐藏，则显示
         */
        videoSurfaceView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showTitleAndBottom(!isLocked);
                if(!isLocked){
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_HIDDEN_AROUND_VIEW), 3000);// 隐藏控件
                }
            }
        });

        videoBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        videoLockBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleScreenLock();
            }
        });

    }

    /**
     * 最好在新线程运行
     */
    public void playVideo(){
        try {
            mediaPlayer.reset(); // 回复播放器默认
            mediaPlayer.setDataSource(videoUrl); // 设置播放路径
            mediaPlayer.setDisplay(videoSurfaceView.getHolder()); // 把视频显示在SurfaceView上
            mediaPlayer.setOnPreparedListener(this); // 设置监听事件
            mediaPlayer.prepare(); // 准备播放
            mediaPlayer.seekTo(50000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        videoProgressBar.setVisibility(View.GONE); // 准备完成后，隐藏控件
        videoPlayBtn.setBackgroundResource(R.drawable.qiyi_sdk_play_btn_pause);
        if (mediaPlayer != null) {
            mediaPlayer.start(); // 开始播放视频
        } else {
            return;
        }
        if (postSize > 0) { // 说明中途停止过（activity调用过pase方法，不是用户点击停止按钮），跳到停止时候位置开始播放
            mediaPlayer.seekTo(postSize); // 跳到postSize大小位置处进行播放
        }
        mHandler.sendEmptyMessage(MSG_UPDATE_SEEK_BAR);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { // 创建完成后调用
        if (postSize > 0 && videoUrl != null) { // 说明，停止过activity调用过pase方法，跳到停止位置播放

            isPlaying = true;
            int sMax = videoSeekBar.getMax();
            int mMax = mediaPlayer.getDuration();

            videoPlayTimeText.setText(secondToDuration(mediaPlayer.getCurrentPosition() / 1000));
            videoTitleTimeText.setText(secondToDuration(mediaPlayer.getDuration() / 1000));

            videoSeekBar.setProgress(postSize * sMax / mMax);
            postSize = 0;
            videoProgressBar.setVisibility(View.GONE);
        }
        playVideo();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) { // activity调用过pase方法，保存当前播放位置
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            postSize = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
            isPlaying = false;
            videoProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                position = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
                SharedPreferences share = getSharedPreferences("video_player", 0);
                Editor editor = share.edit();
                editor.putInt("position", position);
                editor.commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayBtn.setEnabled(true);
        videoPlayBtn.setBackgroundResource(R.drawable.qiyi_sdk_play_btn_pause);
    }

    @Override
    protected void onDestroy() { // activity销毁后，释放资源
        super.onDestroy();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        System.gc();
    }

    private void toggleScreenLock() {
        isLocked = !isLocked;
        showTitleAndBottom(!isLocked);
    }

    public void showTitleAndBottom(boolean isShow){
        if(isShow){
            videoLockBtn.setBackgroundResource(R.drawable.player_landscape_screen_on_noraml);
            videoTitleLayout.setVisibility(View.VISIBLE);
            videoBottomLayout.setVisibility(View.VISIBLE);

        }else{
            videoLockBtn.setBackgroundResource(R.drawable.player_landscape_screen_off_normal);
            videoTitleLayout.setVisibility(View.GONE);
            videoBottomLayout.setVisibility(View.GONE);
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer == null) {
            isPlaying = false;
        } else if (mediaPlayer.isPlaying()) {
            isPlaying = true;
            int position = mediaPlayer.getCurrentPosition();
            int mMax = mediaPlayer.getDuration();
            int sMax = videoSeekBar.getMax();
            if (mMax > 0) {
                videoPlayTimeText.setText(secondToDuration(position / 1000));
                videoTitleTimeText.setText(secondToDuration(mMax / 1000));
                videoSeekBar.setProgress(position * sMax / mMax);
            } else {
                Toast.makeText(SurfaceVideoActivity.this, "无法播放", Toast.LENGTH_LONG).show();
            }
        }
    }

    // 防止用户频繁操作
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (3000 < timeD) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // 如果是锁定状态不允许使用系统后退键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isLocked) {
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public static String secondToDuration(int second) {
        int h = 0;
        int d = 0;
        int s = 0;
        int temp = second % 3600;
        if (second > 3600) {
            h = second / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    d = temp / 60;
                    if (temp % 60 != 0) {
                        s = temp % 60;
                    }
                } else {
                    s = temp;
                }
            }
        } else {
            d = second / 60;
            if (second % 60 != 0) {
                s = second % 60;
            }
        }

        return h + ":" + d + ":" + s;
    }

}
