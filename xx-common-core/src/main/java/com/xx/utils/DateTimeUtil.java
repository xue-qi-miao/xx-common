package com.xx.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/15 10:04
 */
@Slf4j
public class DateTimeUtil {

    public final static String DATE_FORMAT_MILLSECONDS = "yyMMddHHmmssSSS";

    public final static String DATE_FORMAT_SECONDS = "yyyyMMddHHmmss";

    public final static String DATE_FORMAT_MINS = "yyMMddHHmm";

    public final static String DATE_FORMAT_TIME = "HH:mm:ss";

    public final static String DATE_FORMAT_TIME_MINUTE = "HH:mm";

    public final static String DATE_FORMAT_DATE_NOT_BAR = "yyyyMMdd";

    public final static String DATE_FORMAT_SLASH = "yyyy/MM/dd";

    public final static String DATE_FORMAT_DATE = "yyyy-MM-dd";

    public final static String DATE_FORMAT_DATE_TIME_MINUTE = "yyyy-MM-dd HH:mm";

    public final static String DATE_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public final static String DATE_FORMAT_STANDARD_DATE_TIME = "yyyy/MM/dd HH:mm:ss";

    public final static String DATE_FORMAT_YYYYMMDD = "yyyy-MM-dd";

    public final static String DATE_FORMAT_YYYYMM = "yyyy-MM";

    public final static String DATE_FORMAT_YYYY = "yyyy";

    public final static String DATE_FORMAT_TIME_R = "yyyy-MM-dd HH:mm";

    public final static String DATE_FORMAT_TIME_T = "yyyy-MM-dd HH:mm:ss";

    private final static String SH_TIME_ZONE = "Asia/Shanghai";


    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间字符串
     */
    public static String nowTime() {
        return nowTimeFormat(DateTimeUtil.DATE_FORMAT_DATE_TIME);
    }

    /**
     * 获取当前时间 根据传入的格式
     *
     * @param pattern 格式化的格式
     * @return 当前时间字符串
     */
    public static String nowTimeFormat(String pattern) {
        return LocalDateTime.now(ZoneId.of(SH_TIME_ZONE)).format(ofPattern(pattern));
    }

    /**
     * 根据传入的格式格式化日期
     *
     * @param pattern 日期格式
     * @return
     */
    public static DateTimeFormatter ofPattern(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * LocalDateTime转时间格式字符串
     *
     * @param localDateTime 时间
     * @param pattern       格式化
     * @return string
     */
    public static String formatToString(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(ofPattern(pattern));
    }

    /**
     * 指定日期加 天数
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addDays(Date date, int amount) {
        Calendar tmpDate = newCalendar(date);
        tmpDate.add(Calendar.DAY_OF_MONTH, amount);
        return tmpDate.getTime();
    }

    /**
     * 指定日期加 小时
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addHours(Date date, int amount) {
        Calendar tmpDate = newCalendar(date);
        tmpDate.add(Calendar.HOUR_OF_DAY, amount);
        return tmpDate.getTime();
    }

    /**
     * 指定日期加 分钟
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount) {
        Calendar tmpDate = newCalendar(date);
        tmpDate.add(Calendar.MINUTE, amount);
        return tmpDate.getTime();
    }

    /**
     * 指定日期加 月份
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonths(Date date, int amount) {
        Calendar tmpDate = newCalendar(date);
        tmpDate.add(Calendar.MONTH, amount);
        return tmpDate.getTime();
    }

    /**
     * 指定日期加 年数
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addYears(Date date, int amount) {
        Calendar tmpDate = newCalendar(date);
        tmpDate.add(Calendar.YEAR, amount);
        return tmpDate.getTime();
    }

    /**
     * 根据传入的 date 返回 Calendar对象
     *
     * @param date
     * @return
     */
    public static Calendar newCalendar(Date date) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender;
    }

    /**
     * 转换日期格式 默认 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String convertDate2Str(Date date) {
        return convertDate2Str(date, DATE_FORMAT_DATE_TIME);
    }

    public static String convertDate2Str(Date date, String pattern) {
        if (ValidationUtil.isEmpty(date)) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(ofPattern(pattern));
    }

    /**
     * 获取以yyyy-MM-dd格式化的日期
     *
     * @param date
     * @return
     */
    public static String getDateWithoutTime(Date date) {
        if (ValidationUtil.isEmpty(date)) {
            return null;
        }

        String dateStr = convertDate2Str(date, DATE_FORMAT_DATE);
        return dateStr;
    }

    /**
     * 获取以yyyy-MM-dd hh:ss:mm格式化的日期
     *
     * @param date
     * @return
     */
    public static String getDateWithTime(Date date) {
        if (ValidationUtil.isEmpty(date)) {
            return null;
        }
        String dateStr = convertDate2Str(date);
        return dateStr;
    }

    /**
     * 获取当前日期，并以yyyy-MM-dd格式化
     *
     * @return
     */
    public static String getCurretntDateWithoutTime() {
        Date currentDate = getCurrentTime();
        String dateStr = convertDate2Str(currentDate, DATE_FORMAT_DATE);
        return dateStr;
    }

    /**
     * 获取时间的当天最小值，并返回格式化后的字符串
     *
     * @param date
     * @return
     */
    public static String getMinTimeStr(Date date) {
        String dateStr = convertDate2Str(date, DATE_FORMAT_DATE);
        return dateStr + " 00:00:00";
    }

    /**
     * 获取时间的当天最打值，并返回格式化后的字符串
     *
     * @param date
     * @return
     */
    public static String getMaxTimeStr(Date date) {
        String dateStr = convertDate2Str(date, DATE_FORMAT_DATE);
        return dateStr + " 23:59:59";
    }

    /**
     * 获取时间的当天最小值
     *
     * @param date
     * @return
     */
    public static Date getMinTimeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
        return cal.getTime();
    }

    /**
     * 获取时间的当天最大值
     *
     * @param date
     * @return
     */
    public static Date getMaxTimeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
        return cal.getTime();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentTime() {
        return new Date();
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        Date currentDate = getCurrentTime();
        return getDateTime(currentDate, null, null, null, 0, 0, 0);
    }

    /**
     * 获取当前年和月
     *
     * @return
     */
    public static Date getCurrentMonth() {
        Date currentDate = getCurrentTime();
        return getDateTime(currentDate, null, null, 1, 0, 0, 0);
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static Date getCurrentYear() {
        Date currentDate = getCurrentTime();
        return getDateTime(currentDate, null, 1, 1, 0, 0, 0);
    }

    /**
     * 获取当前时间，并设定时分秒
     *
     * @param date
     * @param hour
     * @param min
     * @param second
     * @return
     */
    public static Date getDateTime(Date date, Integer year, Integer month, Integer day, Integer hour, Integer min,
                                   Integer second) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(year == null ? cal.get(Calendar.YEAR) : year, month == null ? cal.get(Calendar.MONTH) : month,
                day == null ? cal.get(Calendar.DATE) : day, hour == null ? cal.get(Calendar.HOUR_OF_DAY) : hour,
                min == null ? cal.get(Calendar.MINUTE) : min, second == null ? cal.get(Calendar.SECOND) : second);

        return cal.getTime();
    }

    /**
     * 取得时间
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param second
     * @return
     */
    public static Date getDateTime(Integer year, Integer month, Integer day, Integer hour, Integer min, Integer second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year == null ? cal.get(Calendar.YEAR) : year, month == null ? cal.get(Calendar.MONTH) : month,
                day == null ? cal.get(Calendar.DATE) : day, hour == null ? cal.get(Calendar.HOUR_OF_DAY) : hour,
                min == null ? cal.get(Calendar.MINUTE) : min, second == null ? cal.get(Calendar.SECOND) : second);

        return cal.getTime();
    }

    /**
     * 智能时间转换
     *
     * @param dateStr
     * @return
     */
    public static Date parseToDate(String dateStr) {
        return parseToDateByLength(dateStr);
    }

    /**
     * 已指定格式来转换字符为Date
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date parseToDate(String dateStr, String pattern) {
        //try {
        //    //LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(ofPattern(pattern));
        //    Instant instant = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern))
        //            .atStartOfDay()
        //            .atZone(ZoneId.systemDefault())
        //            .toInstant();
        //    return Date.from(instant);
        //} catch (Exception e) {
        //    log.error("日期转换异常", e.getMessage());
        //}
        //return null;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        } catch (Exception e) {
            Instant instant = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern))
                    .atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant();
            return Date.from(instant);
        }
    }

    /**
     * 智能时间转换，根据字符串长度来转换成Date
     *
     * @param dateStr
     * @return
     */
    public static Date parseToDateByLength(String dateStr) {
        Date date = null;
        try {
            if (ValidationUtil.isEmpty(dateStr)) {
                return null;
            }
            if (dateStr.contains("T") && dateStr.endsWith("Z")) {
                // 注意是空格+UTC
                dateStr = dateStr.replace("Z", " UTC");
                Instant instant = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS Z"))
                        .atStartOfDay()
                        .atZone(ZoneId.systemDefault())
                        .toInstant();
                return Date.from(instant);
            }
            if (dateStr.length() == 8) {
                return DateTimeUtil.parseToDate(dateStr, DateTimeUtil.DATE_FORMAT_DATE_NOT_BAR);
            }

            if (dateStr.length() == 10) {
                return DateTimeUtil.parseToDate(dateStr, DateTimeUtil.DATE_FORMAT_YYYYMMDD);
            }

            if (dateStr.length() == 13) {
                return new Date(Long.parseLong(dateStr));
            }
            if (dateStr.length() == 14) {
                return DateTimeUtil.parseToDate(dateStr, DATE_FORMAT_SECONDS);
            }
            if (dateStr.length() == 16) {
                return DateTimeUtil.parseToDate(dateStr, DateTimeUtil.DATE_FORMAT_TIME_R);
            }

            if (dateStr.length() == 19) {
                return DateTimeUtil.parseToDate(dateStr, DateTimeUtil.DATE_FORMAT_TIME_T);
            }

            date = parseToDate(dateStr, DATE_FORMAT_DATE_TIME);
            if (date == null) {
                date = parseToDate(dateStr, DATE_FORMAT_DATE_TIME_MINUTE);
            }
            if (date == null) {
                date = parseToDate(dateStr, DATE_FORMAT_DATE);
            }
        } catch (Exception e) {
            log.error("时间格式转换出错！dateStr=" + dateStr);
        }

        return date;
    }

    public static String[] getStartEndTime() {
        Date today = getCurrentTime();
        return getStartEndTime(today);
    }

    public static String[] getStartEndTime(Date date) {
        String startTime = getMinTimeStr(date);
        String endTime = getMaxTimeStr(date);
        return new String[]{startTime, endTime};
    }

    public static Date[] getStartEndTime4Date(Date date) {
        String startTime = getMinTimeStr(date);
        String endTime = getMaxTimeStr(date);
        return new Date[]{DateTimeUtil.parseToDate(startTime), DateTimeUtil.parseToDate(endTime)};
    }

    /**
     * 改时间是否为当天日期
     *
     * @param date
     * @return
     */
    public static boolean isCurrentDate(Date date) {
        return isSameDate(getCurrentTime(), date);
    }

    /**
     * 判断是否为同一天
     *
     * @param one
     * @param two
     * @return
     */
    public static boolean isSameDate(Date one, Date two) {
        String oneDateStr = getDateWithoutTime(one);
        String twoDateStr = getDateWithoutTime(two);
        if (ValidationUtil.isEmpty(oneDateStr) && ValidationUtil.isEmpty(twoDateStr)) {
            return true;
        } else if (ValidationUtil.isEmpty(oneDateStr) || ValidationUtil.isEmpty(twoDateStr)) {
            return false;
        }

        return oneDateStr.equals(twoDateStr);
    }

    /**
     * 两者日期是否为同一天
     *
     * @param one
     * @param two
     * @return
     */
    public static boolean isDifferentDate(Date one, Date two) {
        return !isSameDate(one, two);
    }

    /**
     * 获取日期的某个指定位置的值，可以为年，月，日，时，分，秒，如Calendar.HOUR
     *
     * @param date
     * @param fieldType 年，月，日，时，分，秒，如Calendar.HOUR
     * @return
     */
    public static int getDateFieldValue(Date date, int fieldType) {
        if (ValidationUtil.isEmpty(date)) {
            return 0;
        }

        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender.get(fieldType);
    }

    /**
     * 设定日期的某个指定位置的值，可以为年，月，日，时，分，秒，如Calendar.HOUR
     *
     * @param date
     * @param fieldType 年，月，日，时，分，秒，如Calendar.HOUR
     * @param value     设定的值
     * @return
     */
    public static Date setDateFiledValue(Date date, int fieldType, int value) {
        if (ValidationUtil.isEmpty(date)) {
            return null;
        }

        Calendar calender = newCalendar(date);
        calender.setTime(date);
        calender.set(fieldType, value);
        return calender.getTime();
    }

    /**
     * Back to the day base on current day.
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date backToDays(Date date, int amount) {
        Calendar tmpDate = newCalendar(date);
        tmpDate.add(Calendar.DAY_OF_MONTH, amount * -1);
        return tmpDate.getTime();
    }

    /**
     * Back to the month base on current month.
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date backToMonths(Date date, int amount) {
        Calendar tmpDate = newCalendar(date);
        tmpDate.add(Calendar.MONTH, amount * -1);
        return tmpDate.getTime();
    }

    /**
     * Get pre-week monday
     *
     * @param currentDate
     * @param pos
     * @return
     */
    public static Date getPreWeekMonday(Date currentDate, int pos) {
        return getWeekDay(currentDate, -pos, Calendar.MONDAY);
    }

    /**
     * Get pre-week monday
     *
     * @param currentDate
     * @param pos
     * @return
     */
    public static Date getPreWeekFriday(Date currentDate, int pos) {
        return getWeekDay(currentDate, -pos, Calendar.FRIDAY);
    }

    private static Date getWeekDay(Date currentDate, int pos, int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int preWeek = currentWeek + pos;
        calendar.set(Calendar.WEEK_OF_YEAR, preWeek);
        int year = calendar.getWeekYear();
        calendar.setWeekDate(year, preWeek, dayOfWeek);
        return calendar.getTime();
    }

    public static Date getWeekDay(Date currentDate, int pos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        int preWeek = currentWeek + pos;
        calendar.set(Calendar.WEEK_OF_YEAR, preWeek);
        int year = calendar.getWeekYear();
        calendar.setWeekDate(year, preWeek, currentDay);
        return calendar.getTime();
    }

    public static Date getPreWeekDate(Date currentDate, int pos) {
        return getWeekDay(currentDate, -pos);
    }

    public static Date getNextWeekDate(Date currentDate, int pos) {
        return getWeekDay(currentDate, pos);
    }


    public static String format(Date date) {
        return format(date, DATE_FORMAT_DATE_TIME);
    }

    public static String format(Date date, String pattern) {
        String strDate = "";
        if (!ValidationUtil.isEmpty(date) && !ValidationUtil.isEmpty(pattern)) {
            try {
                strDate = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(ofPattern(pattern));
                //SimpleDateFormat formatter = new SimpleDateFormat(pattern, java.util.Locale.CHINA);
                //strDate = formatter.format(date);
            } catch (Exception e) {
                log.error("日期转换异常", e.getMessage());
            }
        }
        return strDate;
    }

    /**
     * 获取架构体系默认时间
     *
     * @return
     */
    public static Date getDefDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1900);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }


    /**
     * 获取上个月第一天； 时间：2016-11-19 16:53:20
     *
     * @return Date
     * @author wk
     */
    public static Date getPreMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }


    /**
     * 获取本月第一天； 时间：2016-11-19 16:53:20
     *
     * @return Date
     * @author wk
     */
    public static Date getPresentMonthStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取本月最后一天
     *
     * @return
     */
    public static Date getPresentMonthEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 上一周的星期一
     *
     * @return
     */
    public static Date getLastWeekyMonday() {

        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int monDay = 1 - dayOfWeek;
        cal.add(Calendar.DATE, monDay - 7);
        return cal.getTime();
    }

    /**
     * 上一周的星期天
     *
     * @return
     */
    public static Date getLastWeekySunday() {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        int sunDay = 7 - dayOfWeek;
        cal.add(Calendar.DATE, sunDay - 7);
        return cal.getTime();
    }

    /**
     * 获取距离当前相距月份的第一天
     *
     * @param num 相距月份
     * @return
     */
    public static Date getApartMonthFirstDay(int num) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, num);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * 获取距离当前相距月份的第一天
     *
     * @param num 相距月份
     * @return
     */
    public static Date getApartMonthLastDay(int num) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, num);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    /**
     * @param date
     * @return int
     * @Author xueqimiao
     * @Date 2019/5/9 11:08
     * @Description 获取指定时间的当月最大天数
     **/
    public static int getMaxDayByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.getActualMaximum(Calendar.DATE);
        return cal.getActualMaximum(Calendar.DATE);
    }

    /**
     * @param date
     * @return java.util.Date
     * @Author xueqimiao
     * @Date 2019/5/9 10:50
     * @Description 获取指定时间的月份的第一天
     **/
    public static Date getMonthFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }


    /**
     * @param date
     * @return java.util.Date
     * @Author xueqimiao
     * @Date 2019/5/9 10:50
     * @Description 获取指定时间的月份的第一天
     **/
    public static Date getMonthEndDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static Date getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 1);
        return c.getTime();
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static Date getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek + 7);
        return c.getTime();
    }

    /**
     * 获取上个季度的第一天
     *
     * @return
     */
    public static Date getLastQuarterFirstDay() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return startCalendar.getTime();
    }

    /**
     * 获取上个季度的第一天
     *
     * @return
     */
    public static Date getLastQuarterLastDay() {
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, ((int) endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return endCalendar.getTime();
    }

    /**
     * 获取当前季度的时间范围
     *
     * @return current quarter
     */
    public static Date getCurrentQuarterFirstDay() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return startCalendar.getTime();
    }

    /**
     * 获取当前季度的时间范围
     *
     * @return current quarter
     */
    public static Date getCurrentQuarterLastDay() {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return endCalendar.getTime();
    }

    /**
     * 获取本年的第一天
     *
     * @return
     */
    public static Date getCurrentYearFirstDay() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取本年的最后一天
     *
     * @return
     */
    public static Date getCurrentYearLastDay() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取去年的第一天
     *
     * @return
     */
    public static Date getLastYearFirstDay() {
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        ca.setTime(new Date()); // 设置时间为当前时间
        ca.add(Calendar.YEAR, -1); // 年份减1
        return getYearFirst(ca.get(Calendar.YEAR));
    }

    /**
     * 获取去年的最后一天
     *
     * @return
     */
    public static Date getLastYearLastDay() {
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        ca.setTime(new Date()); // 设置时间为当前时间
        ca.add(Calendar.YEAR, -1); // 年份减1
        return getYearLast(ca.get(Calendar.YEAR));
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
     * 获取两个时间相差天数
     *
     * @param d1
     * @param d2
     * @return
     * @author xueqimiao
     * @createTime 2018年12月12日 上午11:20:55
     */
    public static int diffDay(Date d1, Date d2) {
        long t1 = d1.getTime();
        long t2 = d2.getTime();
        long day = (t1 - t2) / (1000 * 60 * 60 * 24);
        return Math.abs((int) day);
    }

    /**
     * 获取两个时间相差天数(精确到小时)
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double diffDay2(Date d1, Date d2) {
        double t1 = d1.getTime();
        double t2 = d2.getTime();
        double day = (t1 - t2) / (1000 * 60 * 60 * 24);
        return Math.abs((double) day);
    }

    /**
     * 获取两个时间相差年份
     *
     * @param d1
     * @param d2
     * @return
     * @author hfj
     * @createTime 202-04-05
     */
    public static int diffYear(Date d1, Date d2) {
        Calendar calend1 = Calendar.getInstance();
        calend1.setTime(d1);
        Calendar calend2 = Calendar.getInstance();
        calend2.setTime(d2);
        int year = (calend1.get(Calendar.YEAR) - calend2.get(Calendar.YEAR)) * 12;
        int month = calend1.get(Calendar.MONTH) - calend2.get(Calendar.MONTH);
        month = Math.abs(year + month);
        return month / 12;
    }


    /**
     * 获取两个时间的相差月数
     *
     * @param d1
     * @param d2
     * @return
     * @author xueqimiao
     * @createTime 2018年12月12日 上午11:30:38
     */
    public static int diffMonth(Date d1, Date d2) {
        Calendar calend1 = Calendar.getInstance();
        calend1.setTime(d1);
        Calendar calend2 = Calendar.getInstance();
        calend2.setTime(d2);
        int year = (calend1.get(Calendar.YEAR) - calend2.get(Calendar.YEAR)) * 12;
        int month = calend1.get(Calendar.MONTH) - calend2.get(Calendar.MONTH);
        return Math.abs(year + month);
    }

    /**
     * 获取两个日期相差分钟
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long diffMinute(Date d1, Date d2) {
        long diffInMs = Math.abs(d1.getTime() - d2.getTime());
        System.out.println(d1.getTime() - d2.getTime());
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMs);
        return diffInMinutes;
    }


    /**
     * 比较两个时间大小
     * 返回0 代表一样大
     * 返回1 代表第一个日期大
     * 返回-1 代表第二个日期大
     *
     * @param d1
     * @param d2
     * @return d1 > d2 : 1 d1 = d2 : 0 d1 < d2 : -1
     */
    public static int compareDate(Date d1, Date d2) {
        if (d1.getTime() > d2.getTime()) {
            return 1;
        } else if (d1.getTime() < d2.getTime()) {
            return -1;
        } else {// 相等
            return 0;
        }
    }

    /**
     * 比较日期大小
     * 返回0 代表一样大
     * 返回1 代表第一个日期大
     * 返回-1 代表第二个日期大
     *
     * @param dateStr1
     * @param dateStr2
     * @return
     */
    public static int compareDate(String dateStr1, String dateStr2) {
        Date d1 = parseToDateByLength(dateStr1);
        Date d2 = parseToDateByLength(dateStr2);
        return compareDate(d1, d2);
    }

    /**
     * 目标日期是否在日期范围内
     *
     * @param date      目标日期
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static boolean isInDate(Date date, Date startDate, Date endDate) {
        if (date.getTime() >= startDate.getTime() && date.getTime() <= endDate.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否超过24小时
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static boolean compareDateTo24(Date date) {
        Date start = date;
        Date end = new Date();
        long cha = end.getTime() - start.getTime();
        if (cha < 0) {
            return false;
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result >= 24) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取日期的最大最小值
     * 比如 2023-05-20 13:14:00 返回 2023-05-20 00:00:00 和 2023-05-20 23:59:59
     *
     * @param date
     * @return
     */
    public static String[] getMinMaxTime(Date date) {
        Date startDate = getMinTimeDate(date);
        Date endDate = getMaxTimeDate(date);
        String startTime = getDateWithTime(startDate);
        String endTime = getDateWithTime(endDate);
        return new String[]{startTime, endTime};
    }

    /**
     * 查询日期区间内所有月份的第一天
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static List<Date> monthFirstDayRange(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        Date start = setMonthFirstDay(startDate);
        Date end = setMonthFirstDay(endDate);
        int compare = compareDate(start, end);
        while (compare < 1 || compare == 0) {
            dates.add(start);
            start = addMonths(start, 1);
            compare = compareDate(start, end);
        }
        return dates;
    }


    private static Date setMonthFirstDay(Date date) {
        date = setDateFiledValue(date, Calendar.DATE, 1);
        date = setDateFiledValue(date, Calendar.HOUR_OF_DAY, 0);
        date = setDateFiledValue(date, Calendar.MINUTE, 0);
        date = setDateFiledValue(date, Calendar.SECOND, 0);
        return date;
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthDay
     * @return
     */
    public static Integer getAge(Date birthDay) {
        if (ValidationUtil.isEmpty(birthDay)) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            return null;
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;//当前日期在生日之前，年龄减一
                }
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }

    /**
     * 获取今年天数
     *
     * @return
     */
    public static int getDaysOfThisYear() {
        return LocalDate.now().lengthOfYear();
    }

    /**
     * 判断一个日期是否在第二个日期之前
     *
     * @param d1 第一个日期
     * @param d2 第二个日期
     * @return
     */
    public static boolean isBefore(Date d1, Date d2) {
        return d1.getTime() < d2.getTime();
    }


    public static void main(String[] args) {
        System.out.println(compareDate(DateTimeUtil.addMinutes(getCurrentTime(), -1), getCurrentTime()));
    }
}
