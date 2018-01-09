package com.example.component.service.aidl.client;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.demo.IBookManager;
import com.example.demo.ICompute;
import com.example.demo.R;
import com.example.component.service.aidl.bean.Book;
import com.example.component.service.aidl.binder.BinderPoolImpl;

import java.util.List;

public class BinderClientActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();

    private Button mBindBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_client);
        mBindBtn = (Button)findViewById(R.id.bind_btn);

        mBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doWork();
                    }
                }).start();
            }
        });
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
