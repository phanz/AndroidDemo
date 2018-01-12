package com.example.system;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 主要参照这2篇文章：
 * http://blog.csdn.net/u012209506/article/details/56012744
 * http://blog.csdn.net/johnny901114/article/details/51472600
 * DownloadManager是Android推荐的用来下载大文件的方法，一般用来做fota升级时下载新的安装包
 * 不足之处有：
 * 1.发送Notification时没有声音的。也没有设置声音的方法。不过这影响不大
 * 2.每个系统的Notification界面不一样,魅族上有个暂停按钮，而小米没有。其实，点击notification,进入到下载管理界面，就有暂停按钮
 * Created by hanzai.peng on 2018/1/10.
 */

public class DownloadTask {
    public static final String TAG = "DownloadTask";

    private Context mContext;
    private DownloadManager mDownloadManager;

    public DownloadTask(Context context){
        mContext = context.getApplicationContext();
        mDownloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public long startDownload(String url){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定下载位置和文件名称
        String download = Environment.DIRECTORY_DOWNLOADS;
        request.setDestinationInExternalFilesDir(mContext, download , "dxtj.apk" );
        //指定在WIFI状态下，执行下载操作。
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置Notification的标题和描述
        request.setTitle("标题");
        request.setDescription("描述");
        //设置Notification的显示，和隐藏。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //有些机型必须设置此方法，才能在下载完成后，点击通知栏的Notification时，才能正确的打开安装界面。
        // 不然会弹出一个Toast（can not open file）.其他文件类型的MimeType
        request.setMimeType("application/vnd.android.package-archive");
        //request.addRequestHeader(String header, String value)
        long id = mDownloadManager.enqueue(request);
        //每下载的一个文件对应一个id，通过此id可以查询数据。
        return id;
    }

    //只能获取一次，数据库中的信息。我们可以使用Timer类，每隔一段时间去查询数据库
    public Cursor queryProcess(long id){
        DownloadManager.Query query = new DownloadManager.Query();
        Cursor cursor = mDownloadManager.query(query.setFilterById(id));
        return cursor;
    }


    public void parseCursor(Cursor cursor){
        if (cursor != null && cursor.moveToFirst()) {
            //下载的文件到本地的目录
            String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            //已经下载的字节数
            int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            //总需下载的字节数
            int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            //Notification 标题
            String title =cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
            //描述
            String description =cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
            //下载对应id
            long id =cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
            //下载文件名称
            String filename =cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
            //下载文件的URL链接
            String url =cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
        }
    }

    public void cancelDownload(long ... id){
        mDownloadManager.remove(id);
    }

    public static void downloadTest(Context context,String url){
        final DownloadTask downloadTask = new DownloadTask(context);
        final long id = downloadTask.startDownload(url);
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Cursor cursor = downloadTask.queryProcess(id);
                if (cursor != null && cursor.moveToFirst()) {
                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        //pb_update.setProgress(100);
                        //install(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/app-release.apk" );
                        Log.d(TAG, title + "下载完成");
                        timer.cancel();
                    }
                    int bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    int pro =  (bytesDownloaded * 100) / bytesTotal;
                    Log.d(TAG, "下载进度: " + pro);
                }
                cursor.close();
            }
        };
        timer.schedule(task, 0,1000);
    }

}
