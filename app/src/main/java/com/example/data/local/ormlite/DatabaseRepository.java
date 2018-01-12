package com.example.data.local.ormlite;

import android.content.Context;

import com.example.model.Student;
import com.example.model.User;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by hanzai.peng on 2017/12/27.
 */

public class DatabaseRepository {
    private Dao<User,Integer> userDao;
    private Dao<Student,Integer> runGps;
    private DatabaseHelper mOpenHelper;
    private Context mContext;

    public DatabaseRepository(Context context){
        mContext = context;
        getOrmDatabaseHelper();
    }

    public DatabaseHelper getOrmDatabaseHelper() {
        if (mOpenHelper == null) {
            mOpenHelper = DatabaseHelper.getHelper(mContext);
            mOpenHelper.getWritableDatabase();
            mOpenHelper.setWriteAheadLoggingEnabled(true);

        }
        return mOpenHelper;
    }

    public Dao<User,Integer> getUserDao(){
        try{
            if(userDao == null){
                userDao = getOrmDatabaseHelper().getDao(User.class);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return userDao;
    }

    public Dao<Student,Integer> getRunGpsDao(){
        try{
            if(runGps == null){
                runGps = getOrmDatabaseHelper().getDao(Student.class);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return runGps;
    }

    public void addOrUpdateRunGpsToDB(List<Student> studentList){
        try {
            for(Student student : studentList) {
                getRunGpsDao().create(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateUserToDB(User user){
        try {
            getUserDao().createOrUpdate(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(User user){
        try {
            getUserDao().delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user){
        try {
            getUserDao().update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> queryUser(){
        List<User> userList = null;
        try {
            userList = getUserDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
