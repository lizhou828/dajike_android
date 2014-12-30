package com.djk_shop.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.djk_shop.modules.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2014/12/29.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String databaseName = "user.db";
    private static final int databaseVersion = 1;
    private Dao<User, Integer> userDao;

    public DBHelper(Context context){
        super(context, databaseName, null, databaseVersion);
    }

    /**
     * create database
     * @param database
     * @param connectionSource
     */
//    @Override
//    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
//        try {
//            TableUtils.createTable(connectionSource, User.class);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Log.e(DBHelper.class.getName(),"Unable to create databases",e);
//        }
//    }

    /**
     * update database( database version )
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
//    @Override
//    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
//        try {
//            TableUtils.dropTable(connectionSource,User.class,true);
//            onCreate(database,connectionSource);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            Log.e(DBHelper.class.getName(),"Unable to upgrade databases from "+oldVersion +" to "+newVersion,e);
//        }
//    }

//    public Dao<User,Integer> getUserDao() throws SQLException{
//        if( userDao == null ){
//            userDao = getDao(User.class);
//        }
//        return userDao;
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create Table user(" +
                "id integer primary key autoincrement," +
                "user_name varchar(32)," +
                "password varchar(32)," +
                "gender varchar(6)," +
                "id_number varchar(18)," +
                "portal_image varchar(128)," +
                "status integer(2)," +
                "married varchar(10)" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
