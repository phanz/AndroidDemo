package com.example.data;

import android.content.Context;

import com.example.DemoApp;
import com.example.data.local.ormlite.DatabaseRepository;
import com.example.data.local.preference.PreferenceRepository;
import com.example.model.Contributor;
import com.example.data.remote.HttpDataRepository;

import java.util.List;

import io.reactivex.Observer;

/**
 * Created by phanz on 2018/1/9.
 */

public class Repository {
    private static Repository sInstance = null;

    private PreferenceRepository mPreferenceRepository;
    private HttpDataRepository mHttpDataRepository;
    private DatabaseRepository mDatabaseRepository;

    private Repository(){
        Context context = DemoApp.getInstance().getContext();
        mDatabaseRepository = new DatabaseRepository(context);
        mPreferenceRepository = new PreferenceRepository(context,"test");
        mHttpDataRepository = new HttpDataRepository();
    }

    public static Repository getInstance() {
        if (sInstance == null) {
            synchronized (Repository.class) {
                if (sInstance == null) {
                    sInstance = new Repository();
                }
            }
        }
        return sInstance;
    }

    public void getGitHubInfo(Observer<List<Contributor>> observer){
        mHttpDataRepository.getGitHubInfo(observer);
    }
}
