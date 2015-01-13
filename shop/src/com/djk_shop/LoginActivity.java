package com.djk_shop;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.djk_shop.dao.ormlite.DBHelper;
import com.djk_shop.modules.User;
import com.djk_shop.services.UserService;
import com.djk_shop.utils.StringUtils;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by Administrator on 2014/12/26.
 */
public class LoginActivity extends OrmLiteBaseActivity<DBHelper> implements View.OnClickListener{
    private Button loginButton;
    private String username;
    private String password;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView registerTextView;
    private boolean PASSWORD = false;
    private boolean USER_NAME = false;
    private Dao<User, Integer> userDao;

    static LoginActivity instance;

    private SharedPreferences sharedPreferences;
    private static final String LOGIN_INFO="LOGIN_INFO";
    private static final String USER_NAME_INFO="USER_NAME";
    private static final String PASSWORD_INFO = "PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.login);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.login_head);

        initViews();
        sharedPreferences = getSharedPreferences(LOGIN_INFO ,Activity.MODE_APPEND);
        loginButton.setOnClickListener(this);
        registerTextView.setOnClickListener(this);

        instance = this;
        if( PortalActivity.instance != null) PortalActivity.instance.finish();
    }

    private void initViews() {
        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setClickable(false);
        loginButton.setEnabled(false);
        registerTextView = (TextView)findViewById(R.id.register_text_view);


        if( sharedPreferences != null ){
            usernameEditText.setText( sharedPreferences.getString(USER_NAME_INFO,"") );
            passwordEditText.setText( sharedPreferences.getString(PASSWORD_INFO,"") );
        }

        usernameEditText = (EditText)findViewById(R.id.login_username);
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                username = usernameEditText.getText().toString().trim();
//                Toast.makeText(LoginActivity.this,"afterTextChanged!,username="+username,Toast.LENGTH_SHORT).show();
                if( StringUtils.isNotBlank( username ) ){
                    USER_NAME = true;
                    if( PASSWORD ){
                        canUseLoginButton();
                    }
                }else{
                    cannotUseLoginButton();
                    USER_NAME = false;
                }
            }
        });
        passwordEditText =  (EditText)findViewById(R.id.login_password);
        passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                password = passwordEditText.getText().toString().trim();
//                Toast.makeText(LoginActivity.this,"afterTextChanged!,password="+password,Toast.LENGTH_SHORT).show();
                if( StringUtils.isNotBlank( password ) ){
                    PASSWORD = true;
                    if( USER_NAME ){
                        canUseLoginButton();
                    }
                }else{
                    cannotUseLoginButton();
                    PASSWORD = false;
                }
            }
        });
    }

    private void canUseLoginButton() {
        loginButton.setTextColor(getResources().getColor(android.R.color.white));
        loginButton.setBackgroundColor(getResources().getColor(R.color.can_login));
        loginButton.setClickable(true);
        loginButton.setEnabled(true);
    }
    private void cannotUseLoginButton() {
        loginButton.setTextColor(getResources().getColor(android.R.color.black));
        loginButton.setBackgroundColor(getResources().getColor(R.color.cant_login));
        loginButton.setClickable(false);
        loginButton.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button :
                if( USER_NAME && PASSWORD ) {

//                    UserService userService = new UserService(LoginActivity.this);
//                    Boolean flag =  userService.login(username, password);
                    Boolean flag =  login(username, password);
                    if(flag){
                        //保存用户信息到MyApplication中
                        MyApplication myApplication = (MyApplication) this.getApplication();
                        myApplication.getData().put("user_name",username);

                        Intent intent = new Intent(LoginActivity.this,PortalActivity.class);
                        intent.putExtra("msg","登录成功!");
                        startActivity(intent);
                        finish();
                    }else{
                        cannotUseLoginButton();
                        Toast.makeText(this,"用户名或密码错误!",Toast.LENGTH_LONG).show();
                    }
                }else if( !USER_NAME ){
                    Toast.makeText(this,"用户名不能为空!",Toast.LENGTH_LONG).show();
                }else if( !PASSWORD ){
                    Toast.makeText(this,"密码不能为空!",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register_text_view :
                startActivity( new Intent(LoginActivity.this,RegisterActivity.class) );
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //save user info in sharedPreferences for auto-login next time before current activity stopped
        sharedPreferences.edit().putString(USER_NAME_INFO,username)
                .putString(PASSWORD_INFO,password).commit();
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
