package com.example.demo.ormlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.demo.R;
import com.j256.ormlite.android.AndroidDatabaseConnection;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrmLiteActivity extends AppCompatActivity {
    public static final String TAG = "OrmLiteActivity";
    private DatabaseRepository repository;
    private List<Student> studentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ormlite);
        ButterKnife.bind(this);
        repository = new DatabaseRepository(this);
        studentList = new ArrayList<>();
        for(int i = 0; i < 10000; i++){
            Student student = new Student();
            student.setName("yx");
            student.setDesc("yx");
            studentList.add(student);
        }
    }

    @OnClick({R.id.create_btn,R.id.insert_btn,R.id.query_btn,R.id.delete_btn,
        R.id.gen_btn,R.id.orm_list_insert_btn,R.id.transaction_insert_btn})
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
}
