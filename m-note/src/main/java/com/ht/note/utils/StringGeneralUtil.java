package com.ht.note.utils;

import org.springframework.util.StringUtils;

public class StringGeneralUtil {

    public static boolean checkNotNull(String checkStr){
        if (StringUtils.isEmpty(checkStr) || "null".equals(checkStr) || "undefined".equals(checkStr)){
            return false;
        }else {
            return true;
        }
    }
}
