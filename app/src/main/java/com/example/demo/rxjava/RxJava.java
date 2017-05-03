package com.example.demo.rxjava;

import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by phanz on 2017/5/2.
 */

public class RxJava {

    private Subscription mSbu;

    public void fish() {
        Fish fish1 = new Fish("青花鱼", 10, "母");
        Fish fish2 = new Fish("鲨鱼", 290, "公");
        Fish fish3 = new Fish("黑鲷", 30, "公");

        List<Fish> fishs = new ArrayList<>();

        fishs.add(fish1);
        fishs.add(fish2);
        fishs.add(fish3);


        //处理单个对象
        /*Observable.just(fish1)
                .subscribe(observer);
        */
        Observable.from(fishs)
                .filter(new Func1<Fish, Boolean>() {//过滤
                    @Override
                    public Boolean call(Fish fish) {

                        return !fish.type.contains("鲨鱼");
                    }
                })
                .map(new Func1<Fish, Fish>() {//修改属性

                    @Override
                    public Fish call(Fish fish) {
                        fish.type = "烤" + fish.type;
                        return fish;
                        //return null;
                    }
                })
                .subscribe(observer);
//                .subscribe(new Action1<Fish>() {//Action1其实是RxJava提供的一个接口,因为有参数可以实现OnNext和onError
//                    @Override
//                    public void call(Fish fish) {
//                        System.out.println(fish.toString());
//                    }
//                });
    }

    Observer observer = new Observer() {
        @Override
        public void onCompleted() {
            System.out.println("钓上来了");
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("出错了");
        }

        @Override
        public void onNext(Object o) {
            System.out.println("123");
            System.out.println("浮标有大动静,有" + o.toString() + "来了");
        }
    };

    public void birthFish() {
        Fish matherFish = new Fish("海鲫鱼", 10, "雌性");
        List<Fish> children = new ArrayList<>();
        children.add(new Fish("小海鲫鱼1", 1, "雌"));
        children.add(new Fish("小海鲫鱼2", 1, "雌"));
        children.add(new Fish("小海鲫鱼3", 1, "雄"));
        children.add(new Fish("小海鲫鱼4", 1, "雌"));
        children.add(new Fish("小海鲫鱼5", 1, "雄"));
        children.add(new Fish("小海鲫鱼6", 1, "雄"));
        children.add(new Fish("小海鲫鱼7", 1, "雄"));
        children.add(new Fish("小海鲫鱼8", 1, "雌"));

        matherFish.fishs = children;


        mSbu = Observable.just(matherFish)
                .repeat(3)//循环操作(若不填数字则无限循环)
                .subscribeOn(Schedulers.io())//在子线程执行操作
                .flatMap(new Func1<Fish, Observable<Fish>>() {//一对多转化
                    @Override
                    public Observable<Fish> call(Fish fish) {
                        List<Fish> fishs = fish.fishs;

                        System.out.println("将小鱼们拿出来");

                        return Observable.from(fishs);
                    }
                })
                .map(new Func1<Fish, Fish>() {
                    @Override
                    public Fish call(Fish fish) {
                        SystemClock.sleep(2000);
                        System.out.println(Thread.currentThread().getName() + "准备产鱼" + fish.toString());
                        return fish;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//再次将线程切换到UI线程
                .subscribe(new Action1<Fish>() {//在这里执行改变ui的操作
                    @Override
                    public void call(Fish fish) {
                        System.out.println("生了一条" + fish.toString());
                    }
                });
    }

}
