package com.djk_shop.utils;

/**
 * Created by Administrator on 2014/12/26.
 */
public class StringUtils {
    public static boolean isNotBlank(String username) {
        if( username != null && !"".equals(username) && username.length() > 0 ){
            return true;
        }else{
            return false;
        }
    }

}
