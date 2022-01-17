package com.paul623.android.speakbyai.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static String getDateStringByRule(String rule){
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(rule, Locale.CHINESE);
        return simpleDateFormat.format(date);
    }
}
