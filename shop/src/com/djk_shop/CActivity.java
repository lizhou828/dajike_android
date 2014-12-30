package com.djk_shop;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-12-2
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public class CActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        TextView textView = new TextView(this);
        textView.setText("This is C Activity");
        textView.setGravity(Gravity.CENTER);
        setContentView(textView);
    }
}
