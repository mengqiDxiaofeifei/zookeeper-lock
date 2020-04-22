package com.sparksys.mall.core.utils;

import com.sparksys.mall.core.enums.DateFormatEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 中文类名: DateUtils工具类
 * 中文描述: DateUtils工具类
 *
 * @author zhouxinlei
 * @date 2019-11-20 12:10:54
 */
@Slf4j
public class DateUtils {


    public static Long formatDateToLong(String date) {
        return formatDateToLong(date, DateFormatEnum.FORMAT_DEFAULT.getPattern());
    }

    /**
     * String转long
     *
     * @param date
     * @param pattern
     * @return Long
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:24:22
     */
    public static Long formatDateToLong(String date, String pattern) {
        DateFormat defaultFormat = new SimpleDateFormat(pattern);
        try {
            return defaultFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * string转date
     *
     * @param date
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:37:52
     */
    public static Date formatDate(String date) {
        return formatDate(date, DateFormatEnum.FORMAT_DEFAULT.getPattern());
    }

    /**
     * string转date
     *
     * @param date
     * @param pattern
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:37:52
     */
    public static Date formatDate(String date, String pattern) {
        DateFormat defaultFormat = new SimpleDateFormat(pattern);
        try {
            return defaultFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * date转string
     *
     * @param date
     * @param pattern
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:38:10
     */
    public static String formatDate(Date date, String pattern) {
        DateFormat defaultFormat = new SimpleDateFormat(pattern);
        return defaultFormat.format(date);
    }

    /**
     * date转string
     *
     * @param date
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:38:10
     */
    public static String formatDate(Date date) {
        return formatDate(date, DateFormatEnum.FORMAT_DEFAULT.getPattern());
    }


    public static int[] getYearMonthDay(Date date) {
        int[] data = new int[3];
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        data[0] = year;
        data[1] = month;
        data[2] = day;
        return data;
    }

    public static int[] getYearMonthDay(String dateString) {
        return getYearMonthDay(formatDate(dateString));
    }

    /**
     * 获取指定月份的第一天
     *
     * @param yearMonthDay
     * @return java.util.Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:16:11
     */
    private static Date specifiedMonthFirstDate(int[] yearMonthDay) {
        int[] yearArray = yearMonthDay;
        int year = yearArray[0];
        int month = yearArray[1];
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        return cal.getTime();
    }

    /**
     * 获取指定月份的第一天
     *
     * @param date
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:55:19
     */
    public static String stringFirstDayOfMonth(String date) {
        Date getDate = specifiedMonthFirstDate(getYearMonthDay(date));
        return formatDate(getDate, DateFormatEnum.UDL_YMD.getPattern());
    }

    /**
     * 获取指定月份的第一天
     *
     * @param date
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:55:01
     */
    public static String stringFirstDayOfMonth(Date date) {
        return stringFirstDayOfMonth(formatDate(date));
    }

    /**
     * 获取指定月份的第一天
     *
     * @param date
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:55:01
     */
    public static Date firstDayOfMonth(Date date) {
        Date getDate = specifiedMonthFirstDate(getYearMonthDay(date));
        return getDate;
    }

    /**
     * 获获取指定月份的最后一天
     *
     * @param yearArray
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:54:44
     */
    private static Date specifiedLastMonthDate(int[] yearArray) {
        int year = yearArray[0];
        int month = yearArray[1];
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        return cal.getTime();
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param date
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:54:44
     */
    public static String stringLastDayOfMonth(String date) {
        Date getDate = specifiedLastMonthDate(getYearMonthDay(date));
        return formatDate(getDate, DateFormatEnum.UDL_YMD.getPattern());
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param date
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:54:44
     */
    public static String stringLastDayOfMonth(Date date) {
        return stringLastDayOfMonth(formatDate(date));
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param date
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:54:44
     */
    public static Date lastDayOfMonth(String date) {
        return specifiedLastMonthDate(getYearMonthDay(date));
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param date
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:54:44
     */
    public static Date lastDayOfMonth(Date date) {
        return specifiedLastMonthDate(getYearMonthDay(date));
    }

    /**
     * 获取某个时间的开始时间和结束时间
     *
     * @param days  天数-几天
     * @param clazz
     * @return Map<String, Object>
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 13:11:44
     */
    public static Map<String, Object> getDateStartEnd(Date date, Integer days, Class clazz) {
        Map<String, Object> dateMap = new HashMap<>(2);
        if (ObjectUtils.isEmpty(date)) {
            date = new Date();
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        if (ObjectUtils.isEmpty(days)) {
            days += 1;
            calendar.add(Calendar.DATE, days);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        //一天的开始时间 yyyy:MM:dd 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startDate = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDateStr = simpleDateFormat.format(startDate);
        if (clazz.isAssignableFrom(Date.class)) {
            dateMap.put("startDate", startDate);
        } else {
            dateMap.put("startDate", startDateStr);
        }
        //一天的结束时间 yyyy:MM:dd 23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endDate = calendar.getTime();
        String endDateStr = simpleDateFormat.format(endDate);
        if (clazz.isAssignableFrom(Date.class)) {
            dateMap.put("endDate", endDate);
        } else {
            dateMap.put("endDate", endDateStr);
        }
        return dateMap;
    }

    /**
     * 获取当天时间(传入格式)
     *
     * @param pattern
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:11:14
     */
    public static String getNowDate(String pattern) {
        return formatDate(new Date(), pattern);
    }

    /**
     * 获取当前时间
     *
     * @return String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:11:14
     */
    public static String getNowDate() {
        return getNowDate(DateFormatEnum.FORMAT_DEFAULT.getPattern());
    }


    /**
     * 获取昨天的日期的开始时间,格式yyyy-MM-dd 00:00:00
     *
     * @param
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:03:21
     */
    public static Date yesterdayStart() {
        Map<String, Object> dateMap = getDateStartEnd(null, -1, Date.class);
        return (Date) dateMap.get("startDate");
    }

    /**
     * 获取昨天的日期的开始时间,格式yyyy-MM-dd 00:00:00
     *
     * @param
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:03:30
     */
    public static String stringYesterdayStart() {
        Map<String, Object> dateMap = getDateStartEnd(null, -1, String.class);
        return (String) dateMap.get("startDate");
    }

    /**
     * 获取昨天的日期的开始时间,格式yyyy-MM-dd 23:59:59
     *
     * @param
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:03:30
     */
    public static String stringYesterdayEnd() {
        Map<String, Object> dateMap = getDateStartEnd(null, -1, String.class);
        return (String) dateMap.get("endDate");
    }

    /**
     * 获取昨天的日期的开始时间,格式yyyy-MM-dd 23:59:59
     *
     * @param
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:03:30
     */
    public static Date yesterdayEnd() {
        Map<String, Object> dateMap = getDateStartEnd(null, -1, Date.class);
        return (Date) dateMap.get("endDate");
    }

    public static String getTodayStart() {
        Map<String, Object> dateMap = getDateStartEnd(null, 0, String.class);
        return (String) dateMap.get("startDate");
    }

    /**
     * 获取今天的开始时间
     *
     * @param
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:19:08
     */
    public static Date getTodayDateStart() {
        Map<String, Object> dateMap = getDateStartEnd(null, 0, Date.class);
        return (Date) dateMap.get("startDate");
    }

    /**
     * 获取7天前的日期时间
     *
     * @param
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:19:34
     */
    public static Date getTodayBeforeSevenDaysDate() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(getTodayDateStart());
        calendar.add(Calendar.DATE, -6);
        return calendar.getTime();
    }

    /**
     * 获取本月的第一天的日期
     *
     * @param
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:19:44
     */
    public static Date getCurrentMonthFirstDay() {
        return firstDayOfMonth(new Date());
    }

    /**
     * 获取距离当天几个月前的日期
     *
     * @param time
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:20:47
     */
    public static Date getBeforeMonth(int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        time = -time;
        calendar.add(Calendar.MONTH, time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取距离当天几天后的日期 yyyy-MM-dd 23:59:59
     *
     * @param day
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 12:20:57
     */
    public static Date getAfterDay(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取某天的开始时间 格式"yyyy-MM-dd 00:00:00"
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:18:00
     */
    public static String stringDateStart(String dateTime) {
        Date date = formatDate(dateTime);
        Map<String, Object> dateMap = getDateStartEnd(date, null, String.class);
        return (String) dateMap.get("startDate");
    }

    /**
     * 获取某天的开始时间 格式"yyyy-MM-dd 00:00:00"
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:17:39
     */
    public static String stringDateStart(Date dateTime) {
        Map<String, Object> dateMap = getDateStartEnd(dateTime, null, String.class);
        return (String) dateMap.get("startDate");
    }

    /**
     * 获取某天的开始时间 格式"yyyy-MM-dd 00:00:00"
     *
     * @param startTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:17:29
     */
    public static Date dateStart(String startTime) {
        Date date = formatDate(startTime);
        Map<String, Object> dateMap = getDateStartEnd(date, null, Date.class);
        return (Date) dateMap.get("startDate");
    }

    /**
     * 获取某天的开始时间 格式"yyyy-MM-dd 23:59:59"
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:16:17
     */
    public static Date dateStart(Date dateTime) {
        Map<String, Object> dateMap = getDateStartEnd(dateTime, null, Date.class);
        return (Date) dateMap.get("startDate");
    }

    /**
     * 获取某天的结束时间 格式"yyyy-MM-dd 23:59:59"
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:16:02
     */
    public static String stringDateEnd(String dateTime) {
        Date date = formatDate(dateTime);
        Map<String, Object> dateMap = getDateStartEnd(date, null, String.class);
        return (String) dateMap.get("endDate");
    }

    /**
     * 获取某天的结束时间 格式"yyyy-MM-dd 23:59:59"
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:15:52
     */
    public static String stringDateEnd(Date dateTime) {
        Map<String, Object> dateMap = getDateStartEnd(dateTime, null, String.class);
        return (String) dateMap.get("endDate");
    }

    /**
     * 获取某天的结束时间 格式"yyyy-MM-dd 23:59:59"
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:15:43
     */
    public static Date dateEnd(Date dateTime) {
        Map<String, Object> dateMap = getDateStartEnd(dateTime, null, Date.class);
        return (Date) dateMap.get("endDate");
    }

    /**
     * 获取某天的结束时间 格式"yyyy-MM-dd 23:59:59"
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:15:35
     */
    public static Date dateEnd(String dateTime) {
        Date date = formatDate(dateTime);
        Map<String, Object> dateMap = getDateStartEnd(date, null, Date.class);
        return (Date) dateMap.get("endDate");
    }

    /**
     * 时间比较
     *
     * @param source
     * @param target
     * @return boolean
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:18:49
     */
    public static boolean compareTime(String source, String target) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DateFormatEnum.SLASH_FORMAT_DEFAULT.getPattern());
            long startTimeMill = format.parse(source).getTime();
            long endTimeMill = format.parse(target).getTime();
            long diff = endTimeMill - startTimeMill;
            if (diff >= 0) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("compareTime转换异常！");
            return false;
        }
    }

    /**
     * 获取某个时间7天前的时间
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:18:58
     */
    public static String beforeSevenDay(String dateTime) throws ParseException {
        return formatDate(dateTime, 6);
    }

    private static String formatDate(String dateTime, int i) {
        Date date = formatDate(dateTime, DateFormatEnum.FORMAT_DEFAULT.getPattern());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -i);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormatEnum.FORMAT_DEFAULT.getPattern());
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 获取某个时间30天前的时间
     *
     * @param dateTime
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:20:48
     */
    public static String before30Day(String dateTime) throws ParseException {
        return formatDate(dateTime, 29);
    }

    /**
     * 获取距离date，time天的日期
     *
     * @param dateStr
     * @param time
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:21:05
     */
    public static String getBeforeDate(String dateStr, int time) {
        Date date = null;
        date = formatDate(dateStr, DateFormatEnum.SLASH_YMD.getPattern());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        time = -time;
        calendar.add(Calendar.DATE, time);
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormatEnum.SLASH_YMD.getPattern());
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 计算两个时间差距多少秒
     *
     * @param startTime
     * @param endTime
     * @return long
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:21:23
     */
    public static long timeDifferenceSecond(String startTime, String endTime) {
        Date begin = formatDate(startTime, DateFormatEnum.FORMAT_DEFAULT.getPattern());
        Date end = formatDate(endTime, DateFormatEnum.FORMAT_DEFAULT.getPattern());
        long gap = (end.getTime() - begin.getTime()) / 1000;
        return gap;
    }

    /**
     * 计算两个时间差，差为几天
     *
     * @param startTime
     * @param endTime
     * @return long
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:22:42
     */
    public static long timeDiffDay(String startTime, String endTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DateFormatEnum.FORMAT_DEFAULT.getPattern());
        long startTimeMill = format.parse(startTime).getTime();
        long endTimeMill = format.parse(endTime).getTime();
        long diff = Math.abs(endTimeMill - startTimeMill);
        long diffDay = diff / (1000 * 24 * 60 * 60);
        return diffDay;
    }

    /**
     * 计算两个时间差，差为几天
     *
     * @return
     * @throws ParseException
     */
    public static long timeDiffDay(Date startTime, Date endTime) {
        long startTimeMill = startTime.getTime();
        long endTimeMill = endTime.getTime();
        long diff = Math.abs(endTimeMill - startTimeMill);
        long diffDay = diff / (1000 * 24 * 60 * 60);
        return diffDay;
    }

    /**
     * 计算两个时间差，差为几小时
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return long
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:25:30
     */
    public static long timeDiffHour(String startTime, String endTime) {
        long startTimeMill = formatDateToLong(startTime);
        long endTimeMill = formatDateToLong(endTime);
        long diff = Math.abs(endTimeMill - startTimeMill);
        long diffHour = diff / (1000 * 60 * 60);
        return diffHour;
    }

    /**
     * 当天时间加一月，返回"yyyy-MM-dd"格式
     *
     * @param
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:27:53
     */
    public static String addFewMonths(int monthNumber) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatEnum.UDL_YMD.getPattern());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthNumber);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 当天时间添加几天之后的时间, 返回"yyyy-MM-dd"格式
     *
     * @param dayNumber
     * @return Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 14:28:15
     */
    public static String addFewDays(int dayNumber) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormatEnum.UDL_YMD.getPattern());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, dayNumber);
        return simpleDateFormat.format(calendar.getTime());
    }


    /**
     * 判断日期是否在今年
     *
     * @param date
     * @return boolean
     * @author zhouxinlei
     * @date 2019/6/20
     */
    public static boolean judgeNowYear(Date date) {
        Calendar now = Calendar.getInstance();
        int a = now.get(Calendar.YEAR);
        if (a == getYearMonthDay(date)[0]) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断日期是否在本周
     *
     * @param date
     * @return boolean
     * @author zhouxinlei
     * @date 2019/6/20
     */
    public static boolean judgeNowWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(date);
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    /**
     * 获取本周第几天
     *
     * @param dayOfWeek @see #Calendar.DAY_OF_WEEK
     * @return java.util.Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:42:58
     */
    private static Date weekDate(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //一天的开始时间 yyyy:MM:dd 00:00:00
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取本周第一天
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:49:35
     */
    public static String stringFirstDateOfNowWeek() {
        Date startDate = weekDate(Calendar.MONDAY);
        return formatDate(startDate);
    }

    /**
     * 获取本周第一天
     *
     * @param
     * @return java.util.Date
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:49:12
     */
    public static Date firstDateOfNowWeek() {
        Date startDate = weekDate(Calendar.MONDAY);
        return startDate;
    }

    /**
     * 获取当前日期以及星期基础信息
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhouxinlei
     * @date 2019-11-20 15:49:03
     */
    public static String formatDateByWeek() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        String result = String.valueOf(year).concat("年");
        if (month < 10) {
            result = result.concat("0")
                    .concat(String.valueOf(month))
                    .concat("月");
        } else {
            result = result.concat(String.valueOf(month))
                    .concat("月");
        }
        if (date < 10) {
            result = result.concat("0")
                    .concat(String.valueOf(date))
                    .concat("日");
        } else {
            result = result.concat(String.valueOf(date))
                    .concat("日");
        }
        switch (week) {
            case 2: {
                result = result.concat(" 星期一");
                break;
            }
            case 3: {
                result = result.concat(" 星期二");
                break;
            }
            case 4: {
                result = result.concat(" 星期三");
                break;
            }
            case 5: {
                result = result.concat(" 星期四");
                break;
            }
            case 6: {
                result = result.concat(" 星期五");
                break;
            }
            case 7: {
                result = result.concat(" 星期六");
                break;
            }
            case 1: {
                result = result.concat(" 星期天");
                break;
            }
            default:
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.addFewDays(2));
        System.out.println(DateUtils.addFewMonths(-2));
        System.out.println(DateUtils.stringFirstDayOfMonth(new Date()));
        System.out.println(DateUtils.stringYesterdayEnd());
        System.out.println(DateUtils.formatDate("2019-06-11 12:56:00", 2));
        System.out.println(DateUtils.stringFirstDateOfNowWeek());
        System.out.println(DateUtils.formatDateByWeek());
    }
}
