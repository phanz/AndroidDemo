package com.example.data.local.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by phanz on 2017/3/5.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "test.db";
    private Map<String,Dao> daoMap = new HashMap<>();
    private static DatabaseHelper instance;
    private static final int VERSION = 2;

    public static synchronized DatabaseHelper getHelper(Context context){
        if(instance == null){
            synchronized (DatabaseHelper.class){
                if(instance == null){
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    private DatabaseHelper(Context context){
        super(context,TABLE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        Log.d(TAG,"onCreate:");
        onUpgrade(database,0,VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        for(int version = oldVersion + 1; version <= newVersion; version++){
            upGradeTo(database,version);
        }
    }

    private void upGradeTo(SQLiteDatabase database, int version) {
        Log.d(TAG, "upGradeto-->" + version);
        switch (version) {
            case 1:
                upGradeTo1();
                break;

            case 2:
                upGradeTo2();
                break;

            default:
                Log.w(TAG, "upGradeTo: unknow up grade to " + version);
        }
    }

    private void upGradeTo1(){
        try {
            TableUtils.createTable(connectionSource,User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void upGradeTo2(){
        try {
            TableUtils.createTable(connectionSource,Student.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cleanTables() {
        try {
            TableUtils.dropTable(connectionSource,User.class,true);
            TableUtils.dropTable(connectionSource,Student.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if(daoMap.containsKey(className)){
            dao = daoMap.get(className);
        }
        if(dao == null){
            dao = super.getDao(clazz);
            daoMap.put(className,dao);
        }
        return dao;
    }

    @Override
    public void close(){
        super.close();
        for(String key : daoMap.keySet()){
            Dao dao = daoMap.get(key);
            dao = null;  //本人很怀疑能起作用？
        }
    }
}
