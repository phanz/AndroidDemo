package com.example.ui.fragment;



import android.app.ActivityManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.demo.R;
import com.example.dynamic.LoadedResource;
import com.example.dynamic.ResourceManager;
import com.example.ui.fragment.pager.BasePagerFragment;
import com.example.ui.fragment.pager.CommonWidgetFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hanzai.peng on 2017/3/17.
 */

public class OthersFragment extends BasePagerFragment {
    private static final String TAG = "OthersFragment";

    @BindView(R.id.imageView)
    public ImageView mImage;

    public static BasePagerFragment newInstance(String title) {
        BasePagerFragment fragment = new OthersFragment();
        fragment.setTitle(title);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others,null);
        ButterKnife.bind(this,view);
        ResourceManager.init(getActivity());
        return view;
    }

    @OnClick( { R.id.load_installed_apk_res_btn,R.id.load_apk_res_btn } )
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.load_installed_apk_res_btn:
                loadInstalledBundle();
                break;

            case R.id.load_apk_res_btn:
                loadUninstalledBundle();
                break;

            default:
                break;
        }
    }

    /**
     * 加载已安装APK资源
     */
    public void loadInstalledBundle() {
        Drawable drawable = ResourceManager.installed().getDrawable("com.android.androidtech", "a");
        if (drawable != null) {
            mImage.setImageDrawable(drawable);
        }
    }

    /**
     * 加载未安装APK资源
     *
     */
    public void loadUninstalledBundle() {
        LoadedResource loadResource = ResourceManager.unInstalled().loadResource("/storage/sdcard0/AndroidTech.apk");
        Drawable drawable = ResourceManager.unInstalled().getDrawable(loadResource.packageName, "girl");
        if (drawable != null) {
            mImage.setImageDrawable(drawable);
        }
    }

}
