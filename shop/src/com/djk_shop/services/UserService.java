package com.djk_shop.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.djk_shop.dao.DBHelper;
import com.djk_shop.modules.User;

/**
 * Created by Administrator on 2014/12/26.
 */
public class UserService {

    private DBHelper dataBaseHelper;
    private SQLiteDatabase databaseRead;

    public UserService() {
    }

    public UserService(Context context) {
        this.dataBaseHelper = new DBHelper(context);
        databaseRead = dataBaseHelper.getReadableDatabase();
    }

    public  Boolean login(String username, String password) {
        String sql = "select * from user where user_name=? and password=?";
        Cursor cursor = databaseRead.rawQuery(sql, new String[]{username, password});
        if( cursor.moveToFirst() ){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return  false;
        }
    }

    //注册逻辑
    public boolean register(User user){
        String sql = "insert into user(user_name,password)values(?,?)";
        Object obj[] = {user.getUsername(),user.getPassword()};
        try{
            databaseRead.execSQL(sql,obj);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return  true;

    }

    public boolean userExists(String username){
        if(username == null || "".equals(username) || "null".equalsIgnoreCase(username)){
            return false;
        }
        String sql = "select * from user where user_name=?";
        Cursor cursor = databaseRead.rawQuery(sql, new String[]{username});
        if( cursor.moveToFirst() ){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return  false;
        }
    }
}
