package com.example.data.remote;

import com.example.model.Contributor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by phanz on 2017/7/7.
 */

public class HttpDataRepository {
    public static final String TAG = HttpDataRepository.class.getSimpleName();

    public static final String GIT_HUB_BASE_URL = "https://api.github.com";

    private Map<String,Retrofit> mRetrofitMap;
    private GitHubHttpService mGitHubHttpService;

    public HttpDataRepository(){
        mRetrofitMap = new HashMap<>();
        Retrofit testRetrofit = getRetrofit(GIT_HUB_BASE_URL);
        mGitHubHttpService = testRetrofit.create(GitHubHttpService.class);
    }

    public void getGitHubInfo(Observer<List<Contributor>> observer) {

        mGitHubHttpService.rxContributors("square", "retrofit")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    private Retrofit getRetrofit(String url){
        Retrofit retrofit = null;
        if(mRetrofitMap.containsKey(url)){
            retrofit = mRetrofitMap.get(url);
        }else{
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
            mRetrofitMap.put(url,retrofit);
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }
}
