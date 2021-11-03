package swjtu.syyymq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 自定义日期与字符串转化工具类
 */
public class DateUtil {
    // TODO:根据后续需求再进行扩展
    public static final String DEFAULT_PATTERN  = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将时间戳转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param timestamp 毫秒时间戳
     * @param pattern 自定义时间格式
     * @return 时间字符串
     */
    public static String timeStamp2String(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(new Date(timestamp));
    }
    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param timestamp 毫秒时间戳
     * @return 时间字符串
     */
    public static String timeStamp2String(long timestamp) {
        return timeStamp2String(timestamp,DEFAULT_PATTERN);
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 自定义时间格式
     * @return 毫秒时间戳
     */
    public static long string2TimeStamp(String time, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.getDefault()).parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * 将时间字符串转为时间戳
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 毫秒时间戳
     */
    public static long string2TimeStamp(String time) {
        return string2TimeStamp(time, DEFAULT_PATTERN);
    }
    /**
     * 将时间字符串转为Date类型
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, String pattern) {
        return new Date(string2TimeStamp(time, pattern));
    }
    /**
     * 将时间字符串转为Date类型
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_PATTERN);
    }
    /**
     * 将Date类型转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param date    Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }
    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_PATTERN);
    }
    /**
     * 将时间戳转为Date类型
     *
     * @param timestamp 毫秒时间戳
     * @return Date类型时间
     */
    public static Date timeStamp2Date(long timestamp) {
        return new Date(timestamp);
    }
    /**
     * Date时间类型转换为时间戳
     * @param date Date类型
     * @return 时间戳
     */
    public static Long date2timeStamp(Date date){
        return date.getTime();
    }
    /**
     * 获取当前毫秒时间戳
     * @return 毫秒时间戳
     */
    public static long getNowTimeStamp() {
        return date2timeStamp(new Date());
    }
    /**
     * 获取当前Date
     * @return Date类型时间
     */
    public static Date getNowTimeDate() {
        return new Date();
    }
    /**
     * 获取当前时间字符串
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String getNowTimeString(String pattern) {
        return timeStamp2String(getNowTimeStamp(), pattern);
    }
}
