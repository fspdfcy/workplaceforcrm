package com.fspdfcy.crm.utils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/8/25
 */

public class DateUtil {

    private DateUtil() {
    }

    /**
     * 将日期格式化
     * @param pattern 格式
     * @return  返回当前日期具有格式化的字符串
     */
    public static String format(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /**
     * 将日期格式化
     * @param date  日期
     * @param pattern 格式
     * @return 具有特定格式的字符串日期
     */
    public static String format(Date date,String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 获取当前短日期格式
     * @return 当前日期的短日期格式
     */
    public static String getCurrentDate() {
        return format(Const.DATA_FORMAT_PATTERN_ALL);
    }
}

