package com.sjzd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");

    public static String getNowTimeString() {
       return simpleDateFormat.format(new Date());
    }
}
