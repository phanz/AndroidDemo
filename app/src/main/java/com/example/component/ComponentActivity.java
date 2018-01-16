package com.example.component;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.component.activity.LifeCycleActivity;
import com.example.component.service.LifeCycleService;
import com.example.component.service.aidl.bean.Book;
import com.example.component.service.aidl.binder.BinderPoolImpl;
import com.example.component.service.aidl.client.BinderClient;
import com.example.component.service.aidl.service.BinderPoolService;
import com.example.demo.IBookManager;
import com.example.demo.ICompute;
import com.example.demo.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComponentActivity extends AppCompatActivity {
    public static final String TAG = "ComponentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);
        ButterKnife.bind(this);
        startService(new Intent(this, BinderPoolService.class));
    }

    @OnClick({R.id.activity_cycle_btn,R.id.service_bind_btn,R.id.service_cycle_btn})
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.activity_cycle_btn:
                Intent intent = new Intent(this,LifeCycleActivity.class);
                startActivity(intent);
                break;

            case R.id.service_bind_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doWork();
                    }
                }).start();
                break;

            case R.id.service_cycle_btn:
                startService(new Intent(this, LifeCycleService.class));
                break;

            default:
                break;
        }
    }

    private void doWork(){
        BinderClient binderClient = BinderClient.getInstance(this);
        IBinder binder = binderClient.queryBinder(BinderPoolImpl.TYPE_COMPUTE);
        ICompute computeBinder = ICompute.Stub.asInterface(binder);
        binder = binderClient.queryBinder(BinderPoolImpl.TYPE_BOOK_MANAGER);
        IBookManager bookManagerBinder = IBookManager.Stub.asInterface(binder);

        try {
            int result = computeBinder.add(3,5);
            Log.d(TAG,"compute result:" + result);
            Book book = new Book("Android开发艺术探索",50);
            bookManagerBinder.addBook(book);
            List<Book> books = bookManagerBinder.getBooks();
            for(Book b : books){
                Log.d(TAG,b.toString());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
