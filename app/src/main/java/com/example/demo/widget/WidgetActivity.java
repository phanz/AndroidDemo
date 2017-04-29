package com.example.demo.widget;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demo.R;

public class WidgetActivity extends AppCompatActivity {

    private EditText mEditText;
    private TextView mImageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        mEditText = (EditText)findViewById(R.id.edit_span_input);
        mImageText = (TextView) findViewById(R.id.image_span_text);

        addEditSpan(mEditText);
        mImageText.setText(Html.fromHtml(descString(),getImageGetterInstance(),null));
    }

    public void addEditSpan(EditText editText){
        SpannableString spannableString = new SpannableString("欢迎光临Harvic的博客");

        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        spannableString.setSpan(span,0,2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Drawable d = getResources().getDrawable(R.mipmap.ic_launcher);
        d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());

        ImageSpan imgSpan = new ImageSpan(d,ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(imgSpan,5,6,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setText(spannableString);
    }

    private String descString(){
        return String.format("手表：<img src='%d'/>; 电子秤：<img src='%d'/>;手环：<img src='%d'/>;",
                R.drawable.icon_watch,
                R.drawable.icon_balance,
                R.drawable.icona_ring);
    }

    /**
     * ImageGetter用于text图文混排
     *
     * @return
     */
    public Html.ImageGetter getImageGetterInstance() {
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                int fontH = (int) (getResources().getDimension(R.dimen.textSizeMedium) * 1.5);
                int width = fontH;
                int height = fontH;

                Drawable d = getResources().getDrawable(Integer.parseInt(source));
                int intrinsicWidth = d.getIntrinsicWidth();
                int intrinsicHeight = d.getIntrinsicHeight();

                if(intrinsicWidth > intrinsicHeight){
                    width = (int) ((float) intrinsicWidth / (float) intrinsicHeight) * fontH;
                }else{
                    height = (int) ((float) intrinsicHeight / (float) intrinsicWidth) * fontH;
                }
                d.setBounds(0, 0, width, height);
                return d;
            }
        };
        return imgGetter;
    }
}
