import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smartem.eval.service.common.constant.CommonConst;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * 日期相关的公共函数定义。
 *
 * @author yzy
 * @version 2018年2月9日
 */
public class DateUtil {
    private static Logger log = LogManager.getLogger(DateUtil.class);


    /**
     * 定义UTC日期转换器。SimpleDateFormat不是线程安全的。
     */
    private static ThreadLocal<SimpleDateFormat> utcDateFormatter = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat format = new SimpleDateFormat(CommonConst.TIME_FORMAT_RULE);
            format.setTimeZone(TimeZone.getTimeZone(CommonConst.UTC_TIME_ZONE));
            return format;
        }
    };

    /**
     * 定义当前系统时间转换器。使用的时区为当前系统所在时区。
     */
    private static ThreadLocal<SimpleDateFormat> localDateFormatter = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat format = new SimpleDateFormat(CommonConst.TIME_FORMAT_RULE);
            format.setTimeZone(TimeZone.getTimeZone(CommonConst.LOCAL_TIME_ZONE));
            return format;
        }
    };

    /**
     * 将UTC日期转换为字符串形式。方便日志文件记录。
     *
     * @param date 日期
     * @return yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static String toUTCDateString(Date date) {
        return utcDateFormatter.get().format(date);
    }

    /**
     * 将UTC时间戳转换为字符串格式。
     *
     * @param timestamp 时间戳
     * @return yyyy-MM-dd HH:mm:ss格式的字符串
     */
    public static String timeStamp2UTCDateStr(long timestamp) {
        return toUTCDateString(new Date(timestamp));
    }

    /**
     * 获取当前utc时间
     *
     * @return yyyy-MM-dd HH:mm:ss格式的utc时间字符串
     */
    public static String getCurrentUTC() {

        // Date本身是没有时区概念的.转换为字符串的过程中涉及时区信息。
        return toUTCDateString(new Date());
    }

    /**
     * 将日期转换为本地时间字符串。
     *
     * @param date 日期对象
     * @return 字符串
     */
    public static String toLocalDateStr(Date date) {
        return localDateFormatter.get().format(date);
    }

    /**
     * 获取系统本地时间字符串
     *
     * @return
     * @author yzy
     * @version 2018年12月8日
     */
    public static String getCurrentLocalDate() {
        return localDateFormatter.get().format(new Date());
    }

    public static String toLocalDateTimeStr(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConst.TIME_FORMAT_RULE);
        return localDateTime.format(formatter);
    }

    public static String toLocalDateStr(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConst.DATE_FORMAT_RULE);
        return localDate.format(formatter);
    }

    /**
     * 将字符串转为日期
     *
     * @param dateString
     * @return
     */
    public static LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = null;
        if (dateString.length() > 10) {
            formatter = DateTimeFormatter.ofPattern(CommonConst.TIME_FORMAT_RULE);
        } else {
            formatter = DateTimeFormatter.ofPattern(CommonConst.DATE_FORMAT_RULE);
        }
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }

    /**
     * 解析utc时间字符串，转换为Date对象
     *
     * @param utcDateStr utc时间字符串,格式为：yyyy-MM-dd HH:mm:ss
     * @return Date
     * @throws ParseException 解析异常
     */
    public static Date toUTCDate(String utcDateStr) throws ParseException {
        return utcDateFormatter.get().parse(utcDateStr);
    }

    /**
     * 解析utc时间字符串，转换为时间戳。
     *
     * @param utcDateStr utc时间字符串,格式为：yyyy-MM-dd HH:mm:ss
     * @return 时间戳
     */
    public static long toTimeStamp(String utcDateStr) {
        try {
            Date date = utcDateFormatter.get().parse(utcDateStr);
            return date.getTime();
        } catch (ParseException e) {
            log.error("Invoke toTimeStamp() fail. utcDateStr:" + utcDateStr + "|exception:" + e.getMessage());
            return 0;
        }
    }

    /**
     * 提起日期精确到天的信息。
     *
     * @param date 时间: 2018-02-03 12:13:01
     * @return 精确到天的信息。如:2018-02-03
     */
    public static String distillDate(String date) {

        date = date.trim();
        int blankPos = date.indexOf(" ");
        if (blankPos > 0) {
            return date.subSequence(0, blankPos).toString();
        }

        return date;
    }

    /**
     * 提起日期的时分秒信息
     *
     * @param date 时间: 2018-02-03 12:13:01
     * @return 时分秒信息。如：12:13:01
     */
    public static String distillTime(String date) {

        date = date.trim();
        int blankPos = date.indexOf(" ");
        int endPos = date.length();
        if (blankPos > 0) {
            return date.subSequence(blankPos, endPos).toString();
        }
        return date;

    }

    /**
     * 获取一个时间段内所有的日期(每一天的日期)
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<Date> getDatesFromTimeInterval(Date startTime, Date endTime) {
        List<Date> retList = new ArrayList<Date>();
        retList.add(startTime);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(startTime);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(endTime);
        //测试此日期是否在指定日期之后
        while (true) {
            //根据日历规则给指定字段增加时间量
            calendarStart.add(Calendar.DAY_OF_MONTH, 1);
            if (calendarStart.getTime().after(endTime)) {
                break;
            }
            retList.add(calendarStart.getTime());
        }
        return retList;

    }

    /**
     * 获取一个时间段内所有的日期（字符串）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDatesStrFromTimeInterval(Date startDate, Date endDate) {
        List<Date> dateList = getDatesFromTimeInterval(startDate, endDate);
        List<String> ret = getDateStrList(dateList);
        return ret;

    }

    /**
     * 将日期转换成字符串(不带时区)
     *
     * @param dateList
     * @return
     */
    public static List<String> getDateStrList(List<Date> dateList) {
        List<String> retList = new ArrayList<>();
        for (Date date : dateList) {
            String dateStr = toUTCDateString(date);
            retList.add(dateStr);
        }
        return retList;

    }

    /**
     * 获取一个月的月初时间(UTC不带时区)
     *
     * @param date 时间   date=null时，自动获取当月月初或月末
     * @param type type='1'获取月初日期，type=‘2’获取月末时间
     * @return 日期时间格式"YYYY-MM-DD"
     */
    public static String getMonthOfStartOrEndDate(Date date, String type) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConst.DATE_FORMAT_RULE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String monthEnd = sdf.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String monthStart = sdf.format(calendar.getTime());
        String monthDate = "";
        if ("1".equals(type))
            monthDate = monthStart;
        else if ("2".equals(type))
            monthDate = monthEnd;
        return monthDate;
    }

    /**
     * 获取一个月的月初和月末时间(带时区)
     *
     * @param timeZone 时区
     * @param date     时间 date=null时，自动取当月初或月末
     * @param type     type='1'获取月初日期，type=‘2’获取月末时间
     * @return 日期时间格式"YYYY-MM-DD"
     */
    public static String getMonthOfStartOrEndDateByTimeZone(String timeZone, Date date, String type) {
        if (date == null) {
            date = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConst.DATE_FORMAT_RULE);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String monthEnd = sdf.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        String monthStart = sdf.format(calendar.getTime());
        String monthDate = "";
        if ("1".equals(type))
            monthDate = monthStart;
        else if ("2".equals(type))
            monthDate = monthEnd;

        return monthDate;

    }

    /**
     * 将UTC时间转换成带时区的时间字符串
     *
     * @param timeZone
     * @param date
     * @return
     * @throws ParseException
     */
    public static String transformDateByTimeZone(String timeZone, String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConst.TIME_FORMAT_RULE);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        String retDate = sdf.format(toUTCDate(date));
        return retDate;
    }

    /**
     * 计算前后两个时间点的间隔时长。
     *
     * @param preTimeStr   前一个时间点
     * @param afterTimeStr 后一个时间点。
     * @return 时间间隔(毫秒)
     * @author yzy
     */
    public static long calcTimeLength(String preTimeStr, String afterTimeStr) {

        try {

            // 计算时长与时区无关,只要保证preTimeStr和afterTimeStr是同一时区下的数据。
            Date preTime = localDateFormatter.get().parse(preTimeStr);
            Date afterTime = localDateFormatter.get().parse(afterTimeStr);

            return afterTime.getTime() - preTime.getTime();
        } catch (ParseException e) {
            log.error("Parse time fail. preTimeStr:" + preTimeStr + "|afterTimeStr:" + afterTimeStr + "|exception:"
                    + e.getMessage());
            return 0;
        }
    }

    /**
     * 系统统一使用UTC时间
     *
     * @author gjj
     * @date 2018/9/18
     */
    public static Date abtainUTC() {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int mil = cal.get(Calendar.MILLISECOND);
        StringBuilder utctimebuffer = new StringBuilder();
        utctimebuffer.append(year).append("-").append(month).append("-").
                append(day).append(" ").append(hour).append(":").append(minute).append(":")
                .append(second).append(".").append(mil);
        try {
            String timeFormat = "yyyy-MM-dd HH:mm:ss.SSS";
            DateFormat format = new SimpleDateFormat(timeFormat);
            return format.parse(utctimebuffer.toString());
        } catch (ParseException e) {
            log.info("abtainUTC() error:" + e.getMessage());
            return new Date();
        }

    }

    public static Date getCurrentDate() {
        Date date;
        try {
            date = localDateFormatter.get().parse(getCurrentLocalDate());
        } catch (ParseException e) {
            date = new Date();
            log.error("parse date exception", e);
        }
        return date;
    }

    /**
     * 获取本地当前日期，时区为GMT+08:00
     *
     * @return 本地当前日期
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now(ZoneId.of(CommonConst.LOCAL_TIME_ZONE));
    }

    /**
     * 获取本地当前时间，时区为GMT+08:00
     *
     * @return 本地当前时间
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now(ZoneId.of(CommonConst.LOCAL_TIME_ZONE));
    }

    /**
     * 利用localDate的取值范围[year:1000-9999]
     * 对日期字符串进行严格的校验(带有时间的日期)
     *
     * @param dateStr 日期字符串
     * @return ture/false
     */
    public static boolean CheckDateTimeString(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConst.TIME_FORMAT_RULE);
        try {
            LocalDateTime.parse(dateStr, formatter);
        } catch (Exception e) { return false;}
        return true;
    }

    /**
     * 利用localDate的取值范围[year:1000-9999]
     * 对日期字符串进行严格的校验(无时间的日期)
     *
     * @param dateStr 日期字符串
     * @return ture/false
     */
    public static boolean CheckDateString(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConst.DATE_FORMAT_RULE);
        try {
            LocalDate.parse(dateStr, formatter);
        } catch (Exception e) { return false;}
        return true;
    }

}
