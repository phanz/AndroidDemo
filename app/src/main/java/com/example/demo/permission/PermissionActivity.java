package com.example.demo.permission;

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

public class PermissionActivity extends AppCompatActivity {

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
        switch (requestCode){
            case PERMISSION_REQUEST_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                break;
        }
    }
}
