package com.xx.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class StringUtil {

    private static Log log = LogFactory.getLog(StringUtil.class);

    public static String convert2UTF8(String str) {
        if (!ValidationUtil.isEmpty(str)) {
            try {
                return new String(str.getBytes("UTF8"), "UTF8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * filter the value which is 'null' string
     *
     * @param strValue
     * @return
     */
    public static String filterNullStr(String strValue) {
        if (ValidationUtil.isEmpty(strValue) || "null".equals(strValue) || "undefined".equals(strValue)
                || "::undefined::".equals(strValue)) {
            return null;
        }

        return strValue;
    }

    public static String getValueFromRequest(HttpServletRequest request, String key) {
        return filterNullStr(request.getParameter(key));
    }

    /**
     * 将字符串第一个字符变成小写
     *
     * @param str
     * @return
     */
    public static String convertFirstCharToLower(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    /**
     * 将字符串第一个字符变成大写
     *
     * @param str
     * @return
     */
    public static String convertFirstCharToUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 取得cache缓存对象的名字前缀
     *
     * @param clazz
     * @return
     */
    public static String getCacheNamePerfix(Class<?> clazz) {
        return StringUtil.convertFirstCharToLower(clazz.getSimpleName().replace("Model", ""));
    }

    /**
     * 判断是否为jsonArray字符串
     *
     * @param str
     * @return
     */
    public static boolean isJsonArrayStr(String str) {
        return !ValidationUtil.isEmpty(str) && str.startsWith("[") && str.endsWith("]");
    }

    /**
     * 判断是否为jsonObject字符串
     *
     * @param str
     * @return
     */
    public static boolean isJsonObjectStr(String str) {
        return !ValidationUtil.isEmpty(str) && str.startsWith("{") && str.endsWith("}");
    }

    /**
     * 比较两个字符串是否相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean compare(String str1, String str2) {
        if (str1 == str2) {
            return true;
        }
        if (str1 == null || str2 == null) {
            return false;
        }

        return str1.compareTo(str2) == 0;
    }

    public static String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
