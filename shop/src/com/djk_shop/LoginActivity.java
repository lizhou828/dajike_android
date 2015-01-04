package com.djk_shop;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.djk_shop.services.UserService;
import com.djk_shop.utils.StringUtils;

/**
 * Created by Administrator on 2014/12/26.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private Button loginButton;
    private String username;
    private String password;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView registerTextView;
    private boolean PASSWORD = false;
    private  boolean USER_NAME = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.login);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.login_head);

        initViews();
        loginButton.setOnClickListener(this);
        registerTextView.setOnClickListener(this);
    }

    private void initViews() {
        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setClickable(false);
        loginButton.setEnabled(false);
        registerTextView = (TextView)findViewById(R.id.register_text_view);

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

                    UserService userService = new UserService(LoginActivity.this);
                    Boolean flag =  userService.login(username, password);
                    if(flag){
                        //保存用户信息到MyApplication中
                        MyApplication myApplication = (MyApplication) this.getApplication();
                        myApplication.getData().put("user_name",username);

                        Intent intent = new Intent(LoginActivity.this,PortalActivity.class);
                        intent.putExtra("msg","登录成功!");
                        startActivity(intent);
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
                break;
            default:
                break;
        }
    }



}
