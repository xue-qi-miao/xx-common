package com.xx.utils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertySetStrategy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/15 10:05
 */
public class JSONUtil {
    private static Map<Class<?>, Map<String, Class<?>>> classConvertMap = new HashMap<Class<?>, Map<String, Class<?>>>();

    /**
     * 生成json转换对应表
     *
     * @param modelClass
     * @return
     */
    private static Map<String, Class<?>> generateJSONConvertMap(Class<?> modelClass, Map<String, Class<?>> fieldConverts) {
        if (classConvertMap.containsKey(modelClass)) {
            return classConvertMap.get(modelClass);
        }

        Map<String, Class<?>> convertMap = new HashMap<String, Class<?>>();
        initJSONConvertMap(modelClass, convertMap, fieldConverts, "");
        if (ValidationUtil.isEmpty(fieldConverts)) {
            classConvertMap.put(modelClass, convertMap);
        }
        return convertMap;
    }

    /**
     * 初始化convert map， 尤其是当该class包含了List或者Map的时候需要装换
     *
     * @param modelClass
     * @param convertMap
     */
    private static void initJSONConvertMap(Class<?> modelClass, Map<String, Class<?>> convertMap,
                                           Map<String, Class<?>> fieldConverts, String parentField) {
        if (!ValidationUtil.isEmpty(parentField) && parentField.split("\\.").length >= 3) {
            return;
        }

        Field[] fields = modelClass.getDeclaredFields();
        if (ValidationUtil.isEmpty(fields)) {
            return;
        }

        for (Field field : fields) {
            if (convertMap.containsKey(field.getName())) {
                continue;
            }
            if (!ValidationUtil.isEmpty(field.getAnnotatedType())) {
                Type type = field.getAnnotatedType().getType();
                if (Class.class.isAssignableFrom(type.getClass())) {
                    Class<?> typeClass = (Class<?>) type;
                    if (typeClass.getName().startsWith("com.bosum") || Collection.class.isAssignableFrom(typeClass) || Map.class.isAssignableFrom(typeClass)) {
                        initJSONConvertMap(typeClass, convertMap, fieldConverts, parentField + field.getName() + ".");
                    }
                    continue;
                }
            }

            if (ValidationUtil.isEmpty(field.getAnnotatedType()) || ValidationUtil.isEmpty(field.getAnnotatedType().getType())) {
                continue;
            }

            Class<?> classInAnno = null;
            Type fieldType = field.getAnnotatedType().getType();
            if (!ValidationUtil.isEmpty(fieldConverts) && fieldConverts.containsKey(parentField + field.getName())) {
                classInAnno = fieldConverts.get(field.getName());
            } else if (fieldType instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) field.getAnnotatedType().getType();
                if (!ValidationUtil.isEmpty(paramType.getRawType()) && Class.class.isAssignableFrom(paramType.getRawType().getClass())) {
                    Class<?> paramTypeClass = (Class<?>) paramType.getRawType();
                    if (Map.class.isAssignableFrom(paramTypeClass)) {
                        boolean containModel = false;
                        for (Type actualType : paramType.getActualTypeArguments()) {
                            if (Class.class.isAssignableFrom(actualType.getClass()) && ((Class<?>) actualType).getName().startsWith("com.bosum")) {
                                containModel = true;
                            }
                        }
                        if (!containModel) {
                            continue;
                        }
                    }
                }

                Type[] types = paramType.getActualTypeArguments();
                if (!ValidationUtil.isEmpty(types)) {
                    Type type = types[types.length - 1];
                    if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                        type = ((ParameterizedType) type).getRawType();
                    }
                    if (Class.class.isAssignableFrom(type.getClass())) {
                        classInAnno = (Class<?>) type;
                    }
                }
            }

            if (classInAnno == null) {
                continue;
            }
            convertMap.put(field.getName(), classInAnno);
            if (classInAnno.getName().startsWith("com.zdww")) {
                initJSONConvertMap(classInAnno, convertMap, fieldConverts, parentField + field.getName() + ".");
            } else if (Collection.class.isAssignableFrom(classInAnno) || Map.class.isAssignableFrom(classInAnno)) {
                initJSONConvertMap(classInAnno, convertMap, fieldConverts, parentField + field.getName() + ".");
            }
        }
    }


    /**
     * 从JSONObject中取值失败
     *
     * @param jsonObj
     * @param paramName
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T getValueFromJsonObject(JSONObject jsonObj, String paramName, Class<T> clazz) throws Exception {
        if (!jsonObj.containsKey(paramName)) {
            return null;
        }

        String methodName = null;
        if (Integer.class.equals(clazz)) {
            methodName = "Int";
        } else {
            methodName = clazz.getSimpleName();
        }

        try {
            return (T) JSONObject.class.getDeclaredMethod("get" + methodName, String.class).invoke(jsonObj, paramName);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new Exception("从JSONObject中取值报错！", e);
        }
    }

    public static <T> T parseJSONObjToObject(JSONObject json, Class<T> classz, Map<String, Class<?>> fieldConverts) {
        if (ValidationUtil.isEmpty(json)) {
            return null;
        }

        Map<String, Class<?>> convertMap = generateJSONConvertMap(classz, fieldConverts);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setPropertySetStrategy(new PropertyStrategyWrapper(PropertySetStrategy.DEFAULT));
        jsonConfig.setRootClass(classz);
        jsonConfig.setClassMap(convertMap);
        return (T) JSONObject.toBean(json, jsonConfig);
    }
}
