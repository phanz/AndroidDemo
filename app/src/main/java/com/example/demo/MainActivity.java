package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.demo.aidl.client.BinderClientActivity;
import com.example.demo.customview.CustomViewActivity;
import com.example.demo.expandablelistview.ExpandableListViewActivity;
import com.example.demo.fragment.FragmentActivity;
import com.example.demo.gesture.SimpleGestureActivity;
import com.example.demo.recyclerview.activity.ItemDecorationActivity;
import com.example.demo.recyclerview.activity.RecyclerViewActivity;
import com.example.demo.widget.WidgetActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mAidlBtn;
    private Button mRecyclerBtn;
    private Button mItemDecoration;
    private Button mFragmentBtn;
    private Button mGestureBtn;
    private Button mCustomViewBtn;
    private Button mExpandableListViewBtn;
    private Button mWidgetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAidlBtn = (Button)findViewById(R.id.aidl_btn);
        mAidlBtn.setOnClickListener(this);

        mRecyclerBtn = (Button)findViewById(R.id.recycler_view_btn);
        mRecyclerBtn.setOnClickListener(this);

        mItemDecoration = (Button)findViewById(R.id.item_decoration_btn);
        mItemDecoration.setOnClickListener(this);

        mFragmentBtn = (Button)findViewById(R.id.fragment_btn);
        mFragmentBtn.setOnClickListener(this);

        mGestureBtn = (Button)findViewById(R.id.gesture_btn);
        mGestureBtn.setOnClickListener(this);

        mCustomViewBtn = (Button)findViewById(R.id.custom_view_btn);
        mCustomViewBtn.setOnClickListener(this);

        mExpandableListViewBtn = (Button)findViewById(R.id.expandable_list_view_btn);
        mExpandableListViewBtn.setOnClickListener(this);

        mWidgetBtn = (Button)findViewById(R.id.widget_btn);
        mWidgetBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.aidl_btn:
                Intent aidlIntent = new Intent(MainActivity.this, BinderClientActivity.class);
                startActivity(aidlIntent);
                break;

            case R.id.recycler_view_btn:
                Intent recyclerViewIntent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(recyclerViewIntent);
                break;

            case R.id.item_decoration_btn:
                Intent itemDecorationIntent = new Intent(MainActivity.this, ItemDecorationActivity.class);
                startActivity(itemDecorationIntent);
                break;

            case R.id.fragment_btn:
                Intent fragmentIntent = new Intent(MainActivity.this, FragmentActivity.class);
                startActivity(fragmentIntent);
                break;

            case R.id.gesture_btn:
                Intent gestureIntent = new Intent(MainActivity.this, SimpleGestureActivity.class);
                startActivity(gestureIntent);
                break;

            case R.id.custom_view_btn:
                Intent customViewIntent = new Intent(MainActivity.this, CustomViewActivity.class);
                startActivity(customViewIntent);
                break;

            case R.id.expandable_list_view_btn:
                Intent expandableListViewIntent = new Intent(MainActivity.this, ExpandableListViewActivity.class);
                startActivity(expandableListViewIntent);
                break;

            case R.id.widget_btn:
                Intent widgetIntent = new Intent(MainActivity.this, WidgetActivity.class);
                startActivity(widgetIntent);
                break;
            default:
                break;
        }
    }

    public AlertDialog crateDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Dialog")
                .setMessage("This is a Dialog");

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        dialog.show();

        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 800;
        params.height = 1300;
        dialog.getWindow().setAttributes(params);
        return dialog;
    }

    public void shareMsg(String activityTitle, String msgTitle, String msgText,String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }

    public void shareSingleImage(String imagePath) {
        //String imagePath = Environment.getExternalStorageDirectory() + File.separator + "test.jpg";
        File file = new File(imagePath);
        long len = file.length();
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
