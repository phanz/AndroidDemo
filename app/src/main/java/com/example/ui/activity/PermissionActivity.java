package com.example.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demo.R;
import com.example.utils.PermissionHelper;

/**
 * 使用原生的Android动态权限申请和封装后的工具类申请
 */
public class PermissionActivity extends AppCompatActivity {

    PermissionHelper mHelper = new PermissionHelper(this);
    private Button mOpenPermissionBtn;
    private static final int PERMISSION_REQUEST_CAMERA =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persmission);

        mOpenPermissionBtn = (Button)findViewById(R.id.open_permission_btn);
        mOpenPermissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
    }


    /**
     * 方式一：原生的Android动态权限申请
     */
    public void openCamera(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkCameraPermission();
        }else{
            doOpenCamera();
        }
    }

    public void checkCameraPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){

                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                    Toast.makeText(this,"需要权限才能开启相机",Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CAMERA);
            }else{
                doOpenCamera();
            }
        }
    }

    public void doOpenCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.handleRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                break;
        }
    }

    /**
     * 方式二：使用封装后的工具类申请，还需要在onRequestPermissionsResult做对应的交代
     */
    public void openCameraByUtils(){


        mHelper.requestPermissions("请授予xx[相机]，[读写]权限！",
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {

                    }

                    @Override
                    public void doAfterDenied(String... permission) {

                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
}
