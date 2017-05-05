package com.example.demo.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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
        //Android默认不允许对title修改，故title样式只能在setTitle之前做处理,这里是加粗
        String title = "Dialog";
        SpannableStringBuilder titleBuilder = new SpannableStringBuilder(title);
        StyleSpan spanState = new StyleSpan(Typeface.BOLD);
        titleBuilder.setSpan(spanState,0,title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(titleBuilder)
                .setMessage("我是消息主体")
                .setView(R.layout.dialog_content)//引入圆角自定义View
                .setNegativeButton("取消",null)
                .setPositiveButton("确定",null);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        /*WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = 800;
        params.height = 1300;
        dialog.getWindow().setAttributes(params);*/
        TextView msgView = (TextView)dialog.findViewById(android.R.id.message);
        msgView.setTextColor(getResources().getColor(R.color.blue));
        //设置按钮颜色
        Button positiveBtn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveBtn.setTextColor(getResources().getColor(R.color.red));

        Button negativeBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeBtn.setTextColor(getResources().getColor(R.color.green));
        //设置窗口背景透明，否则圆角框看不出效果
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}
