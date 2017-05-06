package com.example.demo.popup;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.demo.R;

public class PopupActivity extends AppCompatActivity {

    private Button showView;

    private PopupWindow pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        showView = (Button)findViewById(R.id.show);
        showView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(showView);
            }
        });
    }

    public void showPopup(View view){
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout shareBar = (LinearLayout) inflater
                .inflate(R.layout.qrcode_share_bar,null);

        pop = new PopupWindow(shareBar, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, false);

        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.setAnimationStyle(R.style.anim_popup_bottombar);
        pop.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);

        pop.showAsDropDown(view,0,0,Gravity.BOTTOM);
        //pop.showAtLocation();
    }


}
