package com.xx.utils;

import net.sf.json.JSONNull;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

import java.util.Date;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/15 10:03
 */
public class CommonUtil {
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";

    public static JsonConfig jsonConfig = new JsonConfig();

    static {
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
            @Override
            public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
                if (ValidationUtil.isEmpty(value)) {
                    return null;
                }
                return DateTimeUtil.convertDate2Str((Date) value);
            }

            @Override
            public Object processArrayValue(Object value, JsonConfig jsonConfig) {
                if (ValidationUtil.isEmpty(value)) {
                    return null;
                }
                return DateTimeUtil.convertDate2Str((Date) value);
            }
        });
        // 设置字符串格式
        jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
            @SuppressWarnings("rawtypes")
            @Override
            public Object getDefaultValue(Class arg0) {
                return null;
            }
        });
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object source, String name, Object value) {
                if (ValidationUtil.isEmpty(value) || value instanceof JSONNull) {
                    return true;
                }
                return false;
            }
        });
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(new String[]{"yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss.SSS"}));
    }
}
