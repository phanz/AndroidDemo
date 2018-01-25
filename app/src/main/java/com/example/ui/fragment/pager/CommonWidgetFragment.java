package com.example.ui.fragment.pager;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.demo.R;
import com.example.ui.activity.AnimationActivity;
import com.example.ui.activity.DrawableActivity;
import com.example.ui.activity.VideoViewActivity;
import com.example.ui.activity.ListViewActivity;
import com.example.ui.activity.ItemDecorationActivity;
import com.example.ui.activity.RecyclerViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonWidgetFragment extends BasePagerFragment {
    public static final String TAG = "CommonWidgetFragment";

    @BindView(R.id.dialog_show_btn)
    public Button mDialogBtn;

    @BindView(R.id.popup_show_btn)
    public Button mPopupBtn;

    public static CommonWidgetFragment newInstance(String title) {
        CommonWidgetFragment fragment = new CommonWidgetFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_common_widget, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick({ R.id.dialog_show_btn,R.id.popup_show_btn,R.id.animation_btn, R.id.list_view_btn,
            R.id.recycler_view_btn, R.id.item_decoration_btn,R.id.drawable_btn,R.id.video_btn})
    public void onClick(View view){
        int id = view.getId();
        Intent intent = null;
        switch (id){
            case R.id.dialog_show_btn:
                AlertDialog dialog = showDialog(getActivity());
                break;

            case R.id.popup_show_btn:
                PopupWindow popupWindow = showPopup(mPopupBtn);
                break;

            case R.id.animation_btn:
                intent = new Intent(getActivity(), AnimationActivity.class);
                startActivity(intent);
                break;

            case R.id.list_view_btn:
                intent = new Intent(getActivity(), ListViewActivity.class);
                startActivity(intent);
                break;

            case R.id.recycler_view_btn:
                intent = new Intent(getActivity(), RecyclerViewActivity.class);
                startActivity(intent);
                break;

            case R.id.item_decoration_btn:
                intent = new Intent(getActivity(), ItemDecorationActivity.class);
                startActivity(intent);
                break;

            case R.id.drawable_btn:
                intent = new Intent(getActivity(), DrawableActivity.class);
                startActivity(intent);
                break;

            case R.id.video_btn:
                intent = new Intent(getActivity(), VideoViewActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }


    public AlertDialog showDialog(Context context){
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
        dialog.show();
        return dialog;
    }

    public PopupWindow showPopup(View view){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout shareBar = (LinearLayout) inflater
                .inflate(R.layout.qrcode_share_bar,null);

        PopupWindow pop = new PopupWindow(shareBar, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, false);

        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.setAnimationStyle(R.style.anim_popup_bottombar);
        pop.getContentView().measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);

        pop.showAsDropDown(view,0,0, Gravity.BOTTOM);
        //pop.showAtLocation();
        return pop;
    }
}
