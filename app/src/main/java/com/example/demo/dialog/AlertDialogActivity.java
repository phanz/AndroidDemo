package com.example.demo.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.demo.R;

public class AlertDialogActivity extends AppCompatActivity {

    private Button mDialogBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        mDialogBtn = (Button)findViewById(R.id.dialog_show_btn);
        mDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = crateDialog(AlertDialogActivity.this);
                dialog.show();
            }
        });
    }

    public AlertDialog crateDialog(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                //.setTitle("Dialog")
                .setView(R.layout.dialog_content);
                //.setNegativeButton("取消",null)
                //.setPositiveButton("确定",null);
                //.setMessage("This is a Dialog");

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        dialog.show();

        /*WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 800;
        params.height = 1300;
        dialog.getWindow().setAttributes(params);
        Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveBtn.setTextColor(getResources().getColor(R.color.colorAccent));*/
        return dialog;
    }
}
