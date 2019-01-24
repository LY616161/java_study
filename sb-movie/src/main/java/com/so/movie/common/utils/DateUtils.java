package com.so.movie.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DATE_FOAMAT = "yyyy-MM-dd";
    private static final String DATETIME_FOAMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date str2date(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FOAMAT);
            return simpleDateFormat.parse(str);
    }

    public static Date str2datetime(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FOAMAT);
        return simpleDateFormat.parse(str);
    }

    public static String date2str(Date date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FOAMAT);
        return simpleDateFormat.format(date);
    }

    public static String datetime2str(Date date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FOAMAT);
        return simpleDateFormat.format(date);
    }

    public static String getdate() throws ParseException {
        return date2str(new Date());
    }

    public static String getdateTime() throws ParseException {
        return datetime2str(new Date());
    }
}
