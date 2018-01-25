package com.example.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.demo.R;
import com.example.utils.RxBus;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * 代码取材与以下几篇博客：
 * https://www.cnblogs.com/liushilin/p/7058302.html
 * https://www.cnblogs.com/liushilin/p/7066074.html
 * https://www.cnblogs.com/liushilin/p/7069617.html
 * https://www.cnblogs.com/liushilin/p/7081715.html
 * https://www.cnblogs.com/liushilin/p/7110489.html
 */

public class RxJavaActivity extends AppCompatActivity {
    public static final String TAG = "RxJavaActivity";

    private PublishSubject<String> mPublishSubject;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

        baseExample();//最基本用法
        backPressureExample();//RxJava2.0中Observable已无背压功能，而添加了Flowable
        rangeExample();//生成、跳过、过滤、截取
        simpleExample();//只接收一个参数，只调用onError 或者onSuccess
        distinctExample();//去重，只发射不同的事件
        //compose ??

        /*
         Subject既是Observable也是Observer,有 PublishSubject、BehaviorSubject、ReplaySubject、AsyncSubject 四种
        * PublishSubject: 只发送注册之后的事件
        * BehaviorSubject: 创建时提供默认参数，当新订阅者到来，且当前Subject还未发送过事件，则发送默认参数，其他与PublishSubject同
        * ReplaySubject: 会发送注册之前的所有事件，注册之后的也发
        * AsyncSubject: 只发送调用onComplete后的最后一个事件(即onComplete的前一个onNext)
        * */
        mPublishSubject = PublishSubject.create();
        mDisposable = mPublishSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(RxJavaActivity.this,"PublishSubject:" + s,Toast.LENGTH_SHORT).show();
            }
        });

        RxBus.getInstance().register(String.class).subscribe(new Consumer<String>() {
            @Override
            public void accept(String integer) throws Exception {
                Toast.makeText(RxJavaActivity.this,integer,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void baseExample() {
        /**
         * subscribe(mObserver)和subscribe(mSubscriber)区别：
         * subscribe(mSubscriber)第一次onNext后自动取消订阅；在第二次请求数据时就不会执行了，
         * subscribe(mObserver)则不出现此问题
         */
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("Hello");
                e.onComplete();
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + "world!";
            }
        }).flatMap(new Function<String, ObservableSource<String>>() { //flatMap并不能保证事件的顺序，如果需要保证，需要用concatMap
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                char[] charArray = s.toCharArray();
                String[] strArray = new String[charArray.length];
                for(int i = 0; i < charArray.length; i++){
                    strArray[i] = charArray[i] + "";
                }
                return Observable.fromArray(strArray).delay(50, TimeUnit.MILLISECONDS);
            }
        }).doOnNext(new Consumer<String>() { // 在onNext之前调用,具体处于哪个线程和onNext无关
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "doOnNext: " + Thread.currentThread().getName());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    private Disposable mDisposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        //调用d.dispose()可以取消订阅
                        mDisposable = d;
                        Log.d(TAG,"onSubscribe:" + d.isDisposed());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                        if(s.equalsIgnoreCase("!")){
                            //新增的Disposable可以在内部做到切断的操作，让Observer观察者不再接收上游事件
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    public void backPressureExample(){
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("Hello I am China!");
            }
        }, BackpressureStrategy.BUFFER)
                .subscribe(new Subscriber<String>() {//这里要注意subscribe的多个重载方法
                    @Override
                    public void onSubscribe(Subscription s) {
                        //这一步是必须，我们通常可以在这里做一些初始化操作，调用request()方法表示初始化工作已经完成
                        //调用request()方法，会立即触发onNext()方法
                        //在onComplete()方法完成，才会再执行request()后边的代码
                        s.request(Long.MAX_VALUE);
                        Log.d(TAG, "onSubscribe: " + "After onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {
                        //由于Reactive-Streams的兼容性，方法onCompleted被重命名为onComplete
                        Log.d(TAG, "onComplete: " + "Flowable onComplete");
                    }
                });
    }

    public void rangeExample(){
        Flowable.range(1,10)//从5开始数10个数(5——14)
                .skip(2)
                .filter(new Predicate<Integer>() {//过滤为偶数
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer%2==0;
                    }
                })
                .take(2)//只要前2个数据
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("consumer", integer+"");
                    }
                });
    }

    public void simpleExample(){
        Single.just(new Random().nextInt())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        Log.d(TAG, "single : onSuccess : "+integer+"\n");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "single : onError : "+e.getMessage()+"\n");
                    }
                });
    }

    public void distinctExample(){
        Observable.just(1, 1, 1, 2, 2, 3, 4, 5)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "distinct : " + integer + "\n");
                    }
                });
    }

    //每次订阅都会创建一个新的Observable，并且如果没有被订阅，就不会产生新的Observable
    public void deferExample(){
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(1, 2, 3);
            }
        });


        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.e(TAG, "defer : " + integer + "\n");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "defer : onError : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "defer : onComplete\n");
            }
        });
    }

    //compose用来对Observable进行变换上的封装，封装生成的对象为ObservableTransformer，传入compose即可
    public void composeExample(){
        ObservableTransformer transformer = new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        }).compose (transformer).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "compose onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    //仅取出可观察到的最后一个值，或者是满足某些条件的最后一项
    public void lastExample(){
        Observable.just(1, 2, 3)
                .last(4)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "last : " + integer + "\n");
                    }
                });
    }

    @OnClick({ R.id.buffer_operate_btn,R.id.zip_operate_btn,R.id.concat_operate_btn,
            R.id.merge_operate_btn,R.id.reduce_operate_btn,R.id.scan_operate_btn,
            R.id.interval_btn,R.id.timer_btn, R.id.debounce_btn,R.id.window_operate_btn,
            R.id.connect_subject_btn,R.id.rx_bus_btn,R.id.reuse_subscriber_btn})
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.buffer_operate_btn:
                bufferOperate();
                break;

            case R.id.zip_operate_btn:
                zipOperate();
                break;

            case R.id.concat_operate_btn:
                concatOperate();
                break;

            case R.id.merge_operate_btn:
                mergeOperate();
                break;

            case R.id.reduce_operate_btn:
                reduceOperate();
                break;

            case R.id.scan_operate_btn:
                scanOperate();
                break;

            case R.id.interval_btn:
                intervalOperate();
                break;

            case R.id.timer_btn:
                timerOperate();
                break;

            case R.id.debounce_btn:
                debounceOperate();
                break;

            case R.id.window_operate_btn:
                windowOperate();
                break;

            case R.id.connect_subject_btn:
                mPublishSubject.onNext("Hello,connect subject");
                break;

            case R.id.rx_bus_btn:
                RxBus.getInstance().post("111");
                break;

            case R.id.reuse_subscriber_btn:
                Toast.makeText(this,"不推荐多个Observable复用同一个Observer",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void bufferOperate(){
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 2)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(@NonNull List<Integer> integers) throws Exception {
                        Log.d(TAG, "buffer size : " + integers.size() + "\n");
                        Log.d(TAG,"buffer value : ");
                        for (Integer i : integers) {
                            Log.e(TAG,i + "");
                        }
                    }
                });
    }

    /**
     * 一个序列产生A、B、C，一个序列产生1、2，组合后则为A1,B2
     */
    public void zipOperate(){
        Observable<String> stringObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("A");
                    Log.d(TAG,"String emit : A \n");
                    Log.e(TAG, "String emit : A \n");
                    e.onNext("B");
                    Log.d(TAG,"String emit : B \n");
                    Log.e(TAG, "String emit : B \n");
                    e.onNext("C");
                    Log.d(TAG,"String emit : C \n");
                    Log.e(TAG, "String emit : C \n");
                }
            }
        });

        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    Log.d(TAG,"Integer emit : 1 \n");
                    Log.e(TAG, "Integer emit : 1 \n");
                    e.onNext(2);
                    Log.d(TAG,"Integer emit : 2 \n");
                    Log.e(TAG, "Integer emit : 2 \n");
                    e.onNext(3);
                    Log.d(TAG,"Integer emit : 3 \n");
                    Log.e(TAG, "Integer emit : 3 \n");
                    e.onNext(4);
                    Log.d(TAG,"Integer emit : 4 \n");
                    Log.e(TAG, "Integer emit : 4 \n");
                    e.onNext(5);
                    Log.d(TAG,"Integer emit : 5 \n");
                    Log.e(TAG, "Integer emit : 5 \n");
                }
            }
        });

        Observable.zip(stringObservable, integerObservable, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG,"zip : accept : " + s + "\n");
                Log.e(TAG, "zip : accept : " + s + "\n");
            }
        });


    }

    /**
     * 和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送
     */
    public void mergeOperate(){
        Observable.merge(Observable.just(1, 2), Observable.just(3, 4, 5))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.d(TAG, "accept: merge :" + integer + "\n" );
                    }
                });
    }

    /**
     * reduce 操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。
     */
    public void reduceOperate(){
        Observable.just(1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: reduce : " + integer + "\n");
            }
        });
    }

    /**
     * scan 操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出。
     */
    public void scanOperate(){
        Observable.just(1, 2, 3)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.e(TAG, "accept: scan " + integer + "\n");
            }
        });
    }

    /**
     * 按照实际划分窗口，将数据发送给不同的Observable
     */
    public void windowOperate(){
        Observable.interval(1, TimeUnit.SECONDS) // 间隔一秒发一次
                .take(15) // 最多接收15个
                .window(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Observable<Long>>() {
                    @Override
                    public void accept(@NonNull Observable<Long> longObservable) throws Exception {
                        Log.e(TAG, "Sub Divide begin...\n");
                        longObservable.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Long>() {
                                    @Override
                                    public void accept(@NonNull Long aLong) throws Exception {
                                        Log.e(TAG, "Next:" + aLong + "\n");
                                    }
                                });
                    }
                });
    }

    /**
     * 合并后的序列为1,2,3,4,5,6,只有前一个 Observable 终止(onComplete) 后才会定义下一个 Observable
     */
    public void concatOperate(){
        Observable.concat(Observable.just(1,2,3), Observable.just(4,5,6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.d(TAG,"concat : "+ integer + "\n");
                    }
                });
    }

    public void intervalOperate(){
        Observable.interval(3,2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.d(TAG,"interval :" + aLong + " at " + new Date() + "\n");
                    }
                });
    }

    public void timerOperate(){
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e(TAG, "timer :" + aLong + " at " + new Date() + "\n");
                    }
                });
    }

    /**
     * 去除发送频率过快的项,比如多次点击一次有效的逻辑
     */
    public void debounceOperate(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                // send events with simulated time wait
                emitter.onNext(1); // skip
                Thread.sleep(400);
                emitter.onNext(2); // deliver
                Thread.sleep(505);
                emitter.onNext(3); // skip
                Thread.sleep(100);
                emitter.onNext(4); // deliver
                Thread.sleep(605);
                emitter.onNext(5); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG,"debounce :" + integer + "\n");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        mDisposable.dispose();
        super.onDestroy();
    }
}
