package com.djk_shop;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.djk_shop.view.SlideShowView;

/**
 * Created by Administrator on 2014/12/29.
 */
public class PortalActivity extends TabActivity implements CompoundButton.OnCheckedChangeListener {
    private TabHost mTabHost;
    private Intent mAIntent;
    private Intent mBIntent;
    private Intent mCIntent;
    private Intent mDIntent;
    private Intent mEIntent;

    static PortalActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.portal);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.head);

        //在intent传递参数的这种方式只能是基础数据类型，
        //如果想实现复杂的数据传递就比较麻烦了，通常需要实现 Serializable或者Parcellable接口。
        String msg = (String)getIntent().getExtras().get("msg");
        MyApplication myApplication = (MyApplication) this.getApplication();
        String username = (String) myApplication.getData().get("user_name");
        msg += ",user_name="+username;
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

        this.mAIntent = new Intent(this,IndexActivity.class);
        this.mBIntent = new Intent(this,BActivity.class);
        this.mCIntent = new Intent(this,CActivity.class);
        this.mDIntent = new Intent(this,UserCenterActivity.class);
        this.mEIntent = new Intent(this,EActivity.class);

        ((RadioButton) findViewById(R.id.radio_button0))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button1))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button2))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button3))
                .setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.radio_button4))
                .setOnCheckedChangeListener(this);

        setupIntent();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            switch (buttonView.getId()){
                case R.id.radio_button0 :
                    this.mTabHost.setCurrentTabByTag("A_TAB");
                    break;
                case R.id.radio_button1 :
                    this.mTabHost.setCurrentTabByTag("B_TAB");
                    break;
                case R.id.radio_button2 :
                    this.mTabHost.setCurrentTabByTag("C_TAB");
                    break;
                case R.id.radio_button3 :
                    this.mTabHost.setCurrentTabByTag("D_TAB");
                    break;
                case R.id.radio_button4 :
                    this.mTabHost.setCurrentTabByTag("MORE_TAB");
                    break;

            }
        }
    }

    private void setupIntent() {
        this.mTabHost = getTabHost();
        TabHost localTabHost = this.mTabHost;
        localTabHost.addTab(buildTabSpec("A_TAB",R.string.main_home,R.drawable.icon_1_n,this.mAIntent));
        localTabHost.addTab(buildTabSpec("B_TAB",R.string.main_news,R.drawable.icon_2_n,this.mBIntent));
        localTabHost.addTab(buildTabSpec("C_TAB",R.string.main_manage_date,R.drawable.icon_3_n,this.mCIntent));
        localTabHost.addTab(buildTabSpec("D_TAB",R.string.main_friends,R.drawable.icon_4_n,this.mDIntent));
        localTabHost.addTab(buildTabSpec("MORE_TAB",R.string.more,R.drawable.icon_5_n,this.mEIntent));
    }

    private TabHost.TabSpec buildTabSpec(String tag, int tab_name, int icon, Intent intent) {
        return this.mTabHost.newTabSpec(tag).setIndicator(  getString(tab_name),getResources().getDrawable(icon)  ).setContent(intent);
    }
}
