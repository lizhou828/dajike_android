package com.djk_shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity implements View.OnClickListener{

    private Button loginButton;

    //Listener of back button
    private DialogInterface.OnClickListener backButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int which) {
            switch (which){
                case AlertDialog.BUTTON_POSITIVE :finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE :
                    break;
                default:break;
            }
        }
    };

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //if current user pressed the back button
        if( keyCode == KeyEvent.KEYCODE_BACK ){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("系统提示:");
            alertDialog.setMessage("您确定要退出吗?");
            alertDialog.setButton("确定", backButtonListener);
            alertDialog.setButton2("取消",backButtonListener);
            alertDialog.show();
            return false;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
}
