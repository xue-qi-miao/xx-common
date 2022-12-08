package com.xx.utils;


import com.xx.utils.model.FieldInfoModel;
import com.xx.utils.model.OrderModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ClassUtil
 * @Author: xueqimiao
 * @Date: 2021/11/19 14:15
 */
public class ClassUtil {
    private static Map<String, Map<String, FieldInfoModel>> clazzFieldInfoMap = new HashMap<String, Map<String, FieldInfoModel>>();
    private static Map<String, List<OrderModel>> clazzOrderModelMap = new HashMap<String, List<OrderModel>>();

    /**
     * 取得model类中的字段等信息
     *
     * @param clazz
     * @return
     */
    public static <T> Map<String, FieldInfoModel> getModelFieldInfoMap(Class<T> clazz) {
        try {
            if (ValidationUtil.isEmpty(clazz)) {
                return null;
            }

            if (clazzFieldInfoMap.containsKey(clazz.getName())) {
                return clazzFieldInfoMap.get(clazz.getName());
            }

            Map<String, FieldInfoModel> fieldInfoMap = new HashMap<String, FieldInfoModel>();
            Method[] methods = clazz.getMethods();
            if (!ValidationUtil.isEmpty(methods)) {
                for (int index = 0; index < methods.length; index++) {
                    Method method = methods[index];
                    FieldInfoModel fieldInfo = ClassUtil.getFieldInfoByMethod(clazz, method);
                    if (!ValidationUtil.isEmpty(fieldInfo)) {
                        fieldInfoMap.put(fieldInfo.getFieldName(), fieldInfo);
                    }
                }
            }
            clazzFieldInfoMap.put(clazz.getName(), fieldInfoMap);
            return fieldInfoMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 取得该方法中该字段的信息
     *
     * @param clazz
     * @param method
     * @return 3个长度的数组，(field, getMethod, SetMethod)
     */
    public static FieldInfoModel getFieldInfoByMethod(Class<?> clazz, Method method) {
        String methodName = method.getName();
        String methodPart = null;
        if (methodName.startsWith("get")) {
            methodPart = methodName.substring(3);
        } else if (methodName.startsWith("is")) {
            methodPart = methodName.substring(2);
        }

        if (methodPart == null) {
            return null;
        }

        String fieldName = StringUtil.convertFirstCharToLower(methodPart);

        String setMethodName = "set" + methodPart;
        Method setMethod = null;
        try {
            setMethod = clazz.getMethod(setMethodName, method.getReturnType());
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }

        Field field = getFieldInfo(clazz, fieldName);
        if (field == null) {
            return null;
        }
        return new FieldInfoModel(field, setMethod, method);
    }

    private static Field getFieldInfo(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException | SecurityException e) {
            // Do Nothing
        }

        if (field != null) {
            return field;
        }
        if (clazz.getSuperclass() != null) {
            return getFieldInfo(clazz.getSuperclass(), fieldName);
        }
        return null;
    }

    /**
     * 取得该类中指定的泛型类
     * @param <T>
     * @param clazz
     */
    /*public static List<Class<?>> getAllGenericityClass(Class<?> clazz)
    {
        Type superClass = clazz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType)
        {
            List<Class<?>> typeClass = new ArrayList<Class<?>>();
            Type[] types = ((ParameterizedType) superClass).getActualTypeArguments();
            for(Type type : types)
            {
                String className = type.getTypeName();
                try
                {
                    Class<?> modelClass = BeanUtil.loadClass(className);
                    typeClass.add(modelClass);
                }
                catch (ASDException e)
                {
                    e.printStackTrace();
                    continue;
                }
            }
            return typeClass;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericityClass(Class<?> clazz)
    {
        List<Class<?>> typeCLass = getAllGenericityClass(clazz);
        if(ValidationUtil.isEmpty(typeCLass))
        {
            return null;
        }
        return (Class<T>) typeCLass.get(0);
    }*/

    /**
     * 判断该类型是否为java基础类型
     *
     * @param clazz
     * @return
     */
    public static boolean isBaseType(Class<?> clazz) {
        if (clazz.equals(Integer.class) || clazz.equals(Byte.class)
                || clazz.equals(Long.class) || clazz.equals(Double.class)
                || clazz.equals(Float.class) || clazz.equals(Character.class)
                || clazz.equals(Short.class) || clazz.equals(Boolean.class)) {
            return true;
        }
        return false;
    }

    /**
     * 是否为Map类型
     *
     * @param clazz
     * @return
     */
    public static boolean isMapClass(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    /**
     * 是否为集合类型
     *
     * @param clazz
     * @return
     */
    public static boolean isCollectionClass(Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 取得集合中的泛型类型
     *
     * @param field 集合字段名称
     * @return
     */
    public static Class<?> getCollectionTypeClass(Field field) {
        if (!Collection.class.isAssignableFrom(field.getType())) {
            return null;
        }

        Type genericType = field.getGenericType();
        if (genericType == null) {
            return null;
        }

        // 如果是泛型参数的类型
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
            return genericClazz;
        }
        return null;
    }
}
