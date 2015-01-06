package com.djk_shop;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/1/6.
 */
public class MobileAppInfoActivity extends Activity{
    private TextView mobileAppText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.mobile_info);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.head);

        initViews();

        String text = "手机型号："+ Build.MODEL + "\n" +
                "SDK版本："+Build.VERSION.SDK + "\n"+
                "系统版本："+ Build.VERSION.RELEASE + "\n";

        PackageInfo packageInfo = getAppInfo();
        if(packageInfo != null){
            text += "软件版本："+packageInfo.versionCode +"\n"+
                    "软件版本名称："+packageInfo.versionName+"\n"+
                    "软件包名："+packageInfo.packageName+"\n"+
                    "软件签名："+packageInfo.signatures+"\n\n\n\n";
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        text +="当前设备宽："+displayMetrics.widthPixels+"\n";
        text +="当前设备高："+displayMetrics.heightPixels+"\n\n\n\n";

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);

//        telephonyManager.getNetworkCountryIso()
//        ISO标准的国家码，即国际长途区号。
//        * 注意：仅当用户已在网络注册后有效。
//        *       在CDMA网络中结果也许不可靠。

        text += "唯一设备ID:(GSM手机的 IMEI 和 CDMA手机的 MEID)\n"+telephonyManager.getDeviceId()+"\n"+
                "手机号："+telephonyManager.getLine1Number()+"\n"+
                "国际长途区号："+telephonyManager.getNetworkCountryIso()+"\n";

                // PHONE_TYPE_NONE  无信号  PHONE_TYPE_GSM   GSM信号PHONE_TYPE_CDMA  CDMA信号
        String phoneType = "";
        if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE){
            phoneType = "无信号";
        } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM){
            phoneType = "GSM信号";
        } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA){
            phoneType = "CDMA信号";
        }else{
            phoneType = "未知信号";
        }
        text += "手机信号类型:"+phoneType;


                mobileAppText.setText(text);
    }

    private void initViews() {
        mobileAppText = (TextView)findViewById(R.id.mobile_and_app_info_text);
    }

    private PackageInfo getAppInfo(){
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo  = null;
        try {
            packageInfo= packageManager.getPackageInfo(this.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return packageInfo;
    }



}
