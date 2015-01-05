package com.djk_shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.djk_shop.dao.ormlite.DBHelper;
import com.djk_shop.modules.User;

import com.djk_shop.services.ormlite.UserService;
import com.djk_shop.utils.StringUtils;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;


import java.sql.SQLException;


/**
 * Created by Administrator on 2014/12/29.
 */
public class RegisterActivity extends OrmLiteBaseActivity<DBHelper> implements View.OnClickListener {
    private UserService userService ;
    private TextView loginTextView;
    private TextView haveAccountTextView;
    private TextView passwordTextView1;
    private TextView passwordTextView2;
    private TextView usernameTextView;
    private String username;
    private String password1;
    private String password2;
    private Dao<User, Integer> userDao;
    private Button registerButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.register);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.register_head);

        initView();

//        userService = new UserService(this);
        loginTextView.setOnClickListener(this);
        haveAccountTextView.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }
    private void initView() {
        loginTextView= (TextView)findViewById(R.id.login_text_view);
        haveAccountTextView= (TextView)findViewById(R.id.have_account);
        registerButton = (Button)findViewById(R.id.register_button);

        usernameTextView = (TextView) findViewById(R.id.register_username);
        passwordTextView1 = (TextView)findViewById(R.id.register_password_1);
        passwordTextView2 = (TextView)findViewById(R.id.register_password_2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_text_view :
                startActivity( new Intent(RegisterActivity.this,LoginActivity.class) );
                break;
            case R.id.have_account :
                startActivity( new Intent(RegisterActivity.this,LoginActivity.class) );
                break;
            case R.id.register_button:
                getData();
                boolean flag = checkData();
                if( !flag ) break;

//                Boolean registerSuccess = userService.register(username,password1);
                Boolean registerSuccess = register(username,password1);

                if(registerSuccess){
                    //保存用户信息到MyApplication中
                    MyApplication myApplication = (MyApplication) this.getApplication();
                    myApplication.getData().put("user_name",username);

                    Intent intent =new Intent(RegisterActivity.this,PortalActivity.class);
                    intent.putExtra("msg","注册成功!");
                    startActivity( intent );
                }else{
                    Toast.makeText(this,"注册失败!",Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }

    private boolean checkData()  {
        if( StringUtils.isBlank(username)  || StringUtils.isBlank(password1) || StringUtils.isBlank(password2) ){
            Toast.makeText(this,"用户名或密码不能为空!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if( !password1.equals(password2) ){
            Toast.makeText(this,"两次输入的密码不一致!",Toast.LENGTH_SHORT).show();
            return false;
        }
//        Boolean userExist = userService.userExists(username);
        Boolean userExist = userExists(username);
        if( userExist ){
            Toast.makeText(this,"用户已存在!",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }

    }



    private void getData() {
        username = usernameTextView.getText().toString().trim();
        password1 = passwordTextView1.getText().toString().trim();
        password2 = passwordTextView2.getText().toString().trim();

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
}
