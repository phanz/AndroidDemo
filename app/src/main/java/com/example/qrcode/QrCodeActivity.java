package com.example.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demo.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QrCodeActivity extends AppCompatActivity {

    @BindView(R.id.generate_result_image)
    public ImageView mGenerateImage;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.capture_btn,R.id.generate_qrcode_btn,R.id.image_scan_btn})
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.capture_btn:
                intent = new Intent(this, CaptureActivity.class);
                startActivity(intent);
                break;
            case R.id.generate_qrcode_btn:
                mBitmap = QRCodeUtil.createQRCodeBitmap("https://www.baidu.com", 480, 480);
                mGenerateImage.setImageBitmap(mBitmap);
                break;

            case R.id.image_scan_btn:
                readQrcode(mBitmap);
                break;

            default:
                break;
        }
    }

    public void readQrcode(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data = new int[width * height];
        bitmap.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result re = null;
        try {
            re = reader.decode(bitmap1);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        if (re == null) {
            Toast.makeText(this,"识别图片失败",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"结果：" + re.getText(),Toast.LENGTH_SHORT).show();
        }
    }
}
