package com.example.demo.ormlite;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by phanz on 2017/3/5.
 */

public class UserDao {
    private Context context;
    private Dao<User,Integer> userDao;
    private DatabaseHelper helper;

    public UserDao(Context context){
        this.context = context;
        helper = DatabaseHelper.getHelper(context);
        try {
            userDao = helper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(User user){
        try {
            userDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listAllUser(){
        try {
            List<User> users = helper.getDao(User.class).queryForAll();
            for(User u : users){
                Log.d("TAG",u.getName());
            }
            Log.e("TAG", "testList: "+users.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
