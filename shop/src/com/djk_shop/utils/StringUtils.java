package com.djk_shop.utils;

/**
 * Created by Administrator on 2014/12/26.
 */
public class StringUtils {

    public static boolean isNotBlank(String string) {
        if( string != null && !"".equals(string) && string.length() > 0 ){
            return true;
        }else{
            return false;
        }
    }

    public static boolean isBlank(String string) {
        return !isNotBlank(string);
    }
}
