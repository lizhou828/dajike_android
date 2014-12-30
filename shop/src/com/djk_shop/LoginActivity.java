package com.djk_shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        registerTextView = (TextView)findViewById(R.id.register_text_view);
        usernameEditText = (EditText)findViewById(R.id.login_username);
        passwordEditText =  (EditText)findViewById(R.id.login_password);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button :
                getData();
                if( StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) ) {
                    UserService userService = new UserService(LoginActivity.this);
                    Boolean flag =  userService.login(username, password);
                    if(flag){
                        startActivity( new Intent(LoginActivity.this,PortalActivity.class));
                        Toast.makeText(this,"登录成功!",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.register_text_view :
                startActivity( new Intent(LoginActivity.this,RegisterActivity.class) );
                break;
            default:
                break;
        }
    }

    public void getData(){
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
    }

}
