package com.fspdfcy.crm.utils;

import java.util.ResourceBundle;

/**
 * 字符串工具类
 * @author fsp
 * @version v1.0
 * 创建时间: 2021/8/25
 */
public class StringUtil {
    public static String REGDATE_PROPERTIES = "com.fspdfcy.egov.resource.Message_CN";
    private static ResourceBundle bundle = ResourceBundle.getBundle(REGDATE_PROPERTIES);

    private StringUtil() {
    }

    /**
     * 读取配置文件中的配置信息
     * @param code 键
     * @return  值
     */
    public static String getTextByCode(String code) {
        return  bundle.getString(code);
    }

    public static boolean isNotEmpty(String string) {
        return string != null && string.trim().length() != 0;
    }
}

