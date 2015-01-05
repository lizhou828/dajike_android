package com.djk_shop.services.ormlite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.djk_shop.dao.ormlite.DBHelper;
import com.djk_shop.modules.User;
import com.djk_shop.utils.StringUtils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by Administrator on 2015/1/5.
 */
public class UserService extends OrmLiteBaseActivity<DBHelper> {
    private Dao<User,Integer> userDao;

    public UserService(Context context) {

    }

    public  Boolean login(String username, String password) {
        if(StringUtils.isBlank(username)  || StringUtils.isBlank(password) ){
            return false;
        }
        String sql = "select * from user where user_name=? and password=?";
        try {
            userDao = getHelper().getUserDao();
            User user = userDao.queryBuilder().where().eq("user_name",username).and().eq("password",password).queryForFirst();
            if( user != null ){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Login failed!", e);
            return false;
        }
    }

    public boolean register(String username, String password1) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password1);
        try {
            userDao = getHelper().getUserDao();
            userDao.create(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "Register user failed!", e);
            return false;
        }
    }

    public boolean userExists(String username){
        if(username == null || "".equals(username) || "null".equalsIgnoreCase(username)){
            return false;
        }
        try {
            userDao = getHelper().getUserDao();
            User user = userDao.queryBuilder().where().eq("user_name",username).queryForFirst();
            if( user != null ){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(this.getClass().getName(), "query userExists failed!", e);
            return false;
        }
    }
}
