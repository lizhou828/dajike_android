package com.djk_shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity implements View.OnClickListener{
    private Button loginButton;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
        loginButton.setOnClickListener(this);
    }

    private void initViews() {
        loginButton = (Button)findViewById(R.id.login_page_button);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_page_button :
                startActivity( new Intent(MyActivity.this,LoginActivity.class));
                break;
            default:break;
        }
    }
}
