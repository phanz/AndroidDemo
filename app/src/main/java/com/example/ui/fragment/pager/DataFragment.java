package com.example.ui.fragment.pager;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.data.Repository;
import com.example.data.local.ormlite.DatabaseRepository;
import com.example.demo.R;
import com.example.model.Contributor;
import com.example.model.Student;
import com.example.model.User;
import com.j256.ormlite.android.AndroidDatabaseConnection;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DataFragment extends BasePagerFragment {
    public static final String TAG = "DataFragment";

    private DatabaseRepository repository;
    private List<Student> studentList;

    public static DataFragment newInstance(String title) {
        DataFragment fragment = new DataFragment();
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
        View view = inflater.inflate(R.layout.fragment_ormlite, container, false);
        ButterKnife.bind(this,view);
        repository = new DatabaseRepository(getActivity());
        studentList = new ArrayList<>();
        for(int i = 0; i < 10000; i++){
            Student student = new Student();
            student.setName("yx");
            student.setDesc("yx");
            studentList.add(student);
        }
        return view;
    }

    @OnClick({R.id.create_btn,R.id.insert_btn,R.id.query_btn,R.id.delete_btn,
            R.id.gen_btn,R.id.orm_list_insert_btn,R.id.transaction_insert_btn,R.id.net_access_btn})
    public void onClick(View view){
        int id = view.getId();
        switch (id){
            case R.id.create_btn:
                User user = new User("phanz","boy");
                repository.addOrUpdateUserToDB(user);
                break;

            case R.id.insert_btn:
                break;

            case R.id.query_btn:
                List<User> userList = repository.queryUser();
                for(User u : userList){
                    Log.d(TAG,u.getName());
                }
                break;

            case R.id.delete_btn:
                break;

            case R.id.orm_list_insert_btn:
                repository.addOrUpdateRunGpsToDB(studentList);
                break;

            case R.id.transaction_insert_btn:
                transactionInsert(studentList);
                break;

            case R.id.net_access_btn:
                Repository.getInstance().getGitHubInfo(new Observer<List<Contributor>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Contributor> contributors) {
                        for (Contributor c : contributors) {
                            Log.d("TAG", "login:" + c.getLogin() + "  contributions:" + c.getContributions());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
                break;

            case R.id.content_provider_btn:
                providerExample();
                break;

            default:
                break;
        }
    }

    public void transactionInsert(List<Student> studentList){
        AndroidDatabaseConnection adc = null;
        Savepoint sp = null;
        try {
            adc = new AndroidDatabaseConnection(repository.getOrmDatabaseHelper().getWritableDatabase(), true);
            sp = adc.setSavePoint("student");
            repository.getRunGpsDao().setAutoCommit(adc, false);
            long start = System.currentTimeMillis();
            for (Student student : studentList) {
                repository.getRunGpsDao().createOrUpdate(student);
                Log.d(TAG,"单条保存时间：" + (System.currentTimeMillis() - start));
                start = System.currentTimeMillis();
            }
            start = System.currentTimeMillis();
            adc.commit(sp);
            Log.d(TAG,"事物执行时间：" + (System.currentTimeMillis() - start));
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                adc.rollback(sp);
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (IllegalStateException e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void providerExample(){
        /**
         * 对user表进行操作
         */

        // 设置URI
        Uri uri_user = Uri.parse("content://cn.scu.myprovider/user");

        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("name", "Iverson");


        // 获取ContentResolver
        ContentResolver resolver =  getActivity().getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"}, null, null, null);
        while (cursor.moveToNext()){
            System.out.println("query book:" + cursor.getInt(0) +" "+ cursor.getString(1));
            // 将表中数据全部输出
        }
        cursor.close();
        // 关闭游标

        /**
         * 对job表进行操作
         */
        // 和上述类似,只是URI需要更改,从而匹配不同的URI CODE,从而找到不同的数据资源
        Uri uri_job = Uri.parse("content://cn.scu.myprovider/job");

        // 插入表中数据
        ContentValues values2 = new ContentValues();
        values2.put("_id", 3);
        values2.put("job", "NBA Player");

        // 获取ContentResolver
        ContentResolver resolver2 =  getActivity().getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver2.insert(uri_job,values2);

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor2 = resolver2.query(uri_job, new String[]{"_id","job"}, null, null, null);
        while (cursor2.moveToNext()){
            System.out.println("query job:" + cursor2.getInt(0) +" "+ cursor2.getString(1));
            // 将表中数据全部输出
        }
        cursor2.close();
        // 关闭游标
    }
}
