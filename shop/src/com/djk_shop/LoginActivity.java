package com.djk_shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.djk_shop.services.LoginService;
import com.djk_shop.utils.StringUtils;

/**
 * Created by Administrator on 2014/12/26.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private Button loginButton;
    private String username;
    private String password;
    private TextView usernameTextView;
    private TextView passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.login);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.login_head);

        initViews();
        loginButton.setOnClickListener(this);
    }

    private void initViews() {
        loginButton = (Button)findViewById(R.id.login_button);
        usernameTextView = (TextView)findViewById(R.id.login_username);
        username = (String) usernameTextView.getText();
        passwordTextView =  (TextView)findViewById(R.id.login_password);
        password = (String) passwordTextView.getText();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button :
                if( StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password) ) {
                    Boolean flag =  LoginService.login(username, password);
//                    if(flag){
//                        startActivity( new Intent(LoginActivity.this,PortalActivity.class));
//                    }

                }
                break;
            default:break;
        }
    }
}
