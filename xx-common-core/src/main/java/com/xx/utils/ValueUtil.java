package com.xx.utils;

import com.xx.utils.model.FieldInfoModel;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: xueqimiao
 * @Date: 2021/12/15 9:57
 */
public class ValueUtil {

    private static char asterisk = '.';
    private static String separator = "\\.";
    public static final String JAVA_MODEL_PREFIX = "com.xx";
    private static List<String> REC_LOWER_FIELDS = new ArrayList<>();

    static {
        REC_LOWER_FIELDS.add("createBy");
        REC_LOWER_FIELDS.add("createDate");
        REC_LOWER_FIELDS.add("updateBy");
        REC_LOWER_FIELDS.add("updateDate");
        REC_LOWER_FIELDS.add("delFlag");
    }

    /**
     * 将父类所有的属性COPY到子类中。 类定义中child一定要extends father
     *
     * @param father
     * @param child
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void fatherToChild(Object father, Object child) throws Exception {
        if (!(father.getClass().isAssignableFrom(child.getClass()))) {
            throw new Exception(father + "不是" + child + "的父类，无法完成复制");
        }

        Class fatherClass = father.getClass();
        Method[] faMethods = fatherClass.getMethods();
        for (int i = 0; i < faMethods.length; i++) {
            Method faMethod = null;
            try {
                faMethod = faMethods[i];// 取出每一个属性，如deleteDate
                if (faMethod.getName().startsWith("get") && !faMethod.getReturnType().equals(Class.class)) {
                    String filedName = faMethod.getName().replaceFirst("get", "");
                    if (REC_LOWER_FIELDS.contains(filedName.toLowerCase())) {
                        continue;
                    }
                    Method setMethdod = fatherClass.getMethod("set" + filedName, faMethod.getReturnType());
                    Object obj = faMethod.invoke(father);
                    setMethdod.invoke(child, obj);
                }
            } catch (Exception e) {
                throw new Exception(father.getClass() + "复制到" + child.getClass() + "时出错，father=" + father
                        + ", child=" + child + ", method=" + faMethod);
            }
        }
    }

    /**
     * 获取models中的指定字段的值
     *
     * @param model
     * @param fieldName
     * @return
     */
    public static Object getFieldValueFromModel(Object model, String fieldName) {
        Object propertyValue = null;
        try {
            if (fieldName.indexOf(asterisk) > 0) {
                String[] propertyNames = fieldName.split(separator);
                propertyValue = model;
                for (String propertyNameTemp : propertyNames) {
                    propertyValue = PropertyUtils.getProperty(propertyValue, propertyNameTemp);
                }
            } else {
                propertyValue = PropertyUtils.getProperty(model, fieldName);
            }
        } catch (Exception e) {
            propertyValue = null;
        }
        return propertyValue;
    }

    /**
     * @param models
     * @param fieldName
     * @param filterNull 是否过滤null元素
     * @Author xueqimiao
     * @Date 2019/11/15 14:31
     * @Description 获取models中的指定字段集合
     **/
    public static List<String> getFieldValuesFromModels(Collection<?> models, String fieldName, boolean filterNull) {
        List<String> ids = new ArrayList<String>();
        if (ValidationUtil.isEmpty(models)) {
            return ids;
        }

        Iterator<?> modelIter = models.iterator();
        while (modelIter.hasNext()) {
            Object model = modelIter.next();
            Object fieldValueFromModel = getFieldValueFromModel(model, fieldName);
            ids.add((String) getFieldValueFromModel(model, fieldName));
        }

        // 过滤null元素
        if (!ValidationUtil.isEmpty(ids) && filterNull) {
            ids.removeIf(id -> ValidationUtil.isEmpty(id));
            ids = new ArrayList<String>(new TreeSet<String>(ids));
        }
        // 去除重复数据
        return ids;
    }

    public static List<Long> getFieldPkValuesFromModels(Collection<?> models, String fieldName, boolean filterNull) {
        List<Long> ids = new ArrayList<Long>();
        if (ValidationUtil.isEmpty(models)) {
            return ids;
        }

        Iterator<?> modelIter = models.iterator();
        while (modelIter.hasNext()) {
            Object model = modelIter.next();
            //Object fieldValueFromModel = getFieldValueFromModel(model, fieldName);
            ids.add((Long) getFieldValueFromModel(model, fieldName));
        }

        // 过滤null元素
        if (!ValidationUtil.isEmpty(ids) && filterNull) {
            ids.removeIf(id -> ValidationUtil.isEmpty(id));
            ids = new ArrayList<Long>(new TreeSet<Long>(ids));
        }
        // 去除重复数据
        return ids;
    }

    /**
     * 获取models中的指定字段集合, 默认过滤null元素
     *
     * @param
     * @return
     */
    public static List<String> getFieldValuesFromModels(Collection<?> models, String fieldName) {
        return getFieldValuesFromModels(models, fieldName, true);
    }

    public static List<Long> getFieldIdValuesFromModels(Collection<?> models) {
        return getFieldPkValuesFromModels(models, "id", true);
    }

    public static List<Long> getFieldPkValuesFromModels(Collection<?> models, String fieldName) {
        return getFieldPkValuesFromModels(models, fieldName, true);
    }

    /**
     * 为某个model集合设定指定列的值
     *
     * @param models
     * @param fieldName
     * @param value
     */
    public static void setFieldValueToModels(Collection<?> models, String fieldName, Object value) {
        if (ValidationUtil.isEmpty(models)) {
            return;
        }

        Iterator<?> modelIter = models.iterator();
        while (modelIter.hasNext()) {
            Object model = modelIter.next();
            setFieldValueToModel(model, fieldName, value);
        }
    }

    /**
     * 将models中指定的两个字段转换成Map
     *
     * @param models
     * @param keyFieldName
     * @param valueFieldName
     * @return
     */
    public static Map<Object, Object> getFieldValuesMapFromModels(Collection<?> models, String keyFieldName, String valueFieldName) {
        Map<Object, Object> resultMap = new LinkedHashMap<Object, Object>();
        if (ValidationUtil.isEmpty(models)) {
            return resultMap;
        }

        Iterator<?> modelIter = models.iterator();
        while (modelIter.hasNext()) {
            Object model = modelIter.next();
            resultMap.put(getFieldValueFromModel(model, keyFieldName), getFieldValueFromModel(model, valueFieldName));
        }
        return resultMap;
    }

    /**
     * 通过实体属性分组 一对多
     * {@code List<实体>} 转为 {@code Map<实体属性,List<实体>>}
     *
     * @param list        集合
     * @param keyFunction 作为key的lambda
     * @param <E>         集合类型
     * @param <K>         key的类型
     * @return {@code Map<实体属性,List<实体>>}
     */
    public static <E, K> Map<K, List<E>> group(List<E> list, Function<E, K> keyFunction) {
        return FunctionUtils.groupThen(list, keyFunction, Collectors.toList());
    }

    /**
     * List 转 Map
     *
     * @param list        集合
     * @param keyFunction 作为key的lambda
     * @param <E>         集合类型
     * @param <K>         key的类型
     * @return 一对一
     */
    public static <E, K> Map<K, E> listToMap(List<E> list, Function<E, K> keyFunction) {
        return FunctionUtils.nullAsEmptyStream(list).collect(LinkedHashMap::new, (m, v) -> m.put(Optional.ofNullable(v).map(keyFunction).orElse(null), v), LinkedHashMap::putAll);
    }

    public static <T> Map<Object, T> getFieldValuesMapFromModels(Collection<T> models, String keyFieldName) {
        Map<Object, T> resultMap = new LinkedHashMap<Object, T>();
        if (ValidationUtil.isEmpty(models)) {
            return resultMap;
        }

        Iterator<T> modelIter = models.iterator();
        while (modelIter.hasNext()) {
            T model = modelIter.next();
            resultMap.put(getFieldValueFromModel(model, keyFieldName), model);
        }
        return resultMap;
    }

    /**
     * 将models中指定的两个字段转换成Map
     *
     * @param models
     * @param keyFieldName
     * @return
     */
    public static Map<Object, List<Object>> getFieldValuesMapFromModels(Collection<?> models, String keyFieldName, String... valueFieldNames) {
        Map<Object, List<Object>> resultMap = new LinkedHashMap<Object, List<Object>>();
        if (ValidationUtil.isEmpty(models)) {
            return resultMap;
        }

        Iterator<?> modelIter = models.iterator();
        while (modelIter.hasNext()) {
            Object model = modelIter.next();
            List<Object> values = new ArrayList<Object>();
            for (String valueFieldName : valueFieldNames) {
                values.add(getFieldValueFromModel(model, valueFieldName));
            }
            resultMap.put(getFieldValueFromModel(model, keyFieldName), values);
        }
        return resultMap;
    }

    /**
     * 根据指定字段并按照该字段相同的值进行组合
     *
     * @param <T>
     * @param models
     * @param keyFieldName
     * @return
     */
    public static <T> Map<Object, List<T>> getFieldValuesMapFromModelsBySameKey(Collection<T> models, String keyFieldName) {
        Map<Object, List<T>> resultMap = new LinkedHashMap<Object, List<T>>();
        if (ValidationUtil.isEmpty(models)) {
            return resultMap;
        }

        Iterator<T> modelIter = models.iterator();
        while (modelIter.hasNext()) {
            T model = modelIter.next();
            Object keyValue = getFieldValueFromModel(model, keyFieldName);
            if (resultMap.containsKey(keyValue)) {
                resultMap.get(keyValue).add(model);
            } else {
                List<T> values = new ArrayList<T>();
                values.add(model);
                resultMap.put(keyValue, values);
            }
        }
        return resultMap;
    }


    /**
     * 为bean设置字段的值
     *
     * @param bean
     * @param propertyName
     * @param value
     * @return
     */
    public static boolean setFieldValueToModel(Object bean, String propertyName, Object value) {
        try {
            int asteriskPos = propertyName.indexOf(asterisk);
            if (asteriskPos > 0) {
                String parentPropertyName = propertyName.substring(0, asteriskPos);
                Object parentProperty = getFieldValueFromModel(bean, parentPropertyName);
                String filedName = propertyName.substring(asteriskPos + 1);
                setPropertiesValue(parentProperty, filedName, value);
                PropertyUtils.setProperty(parentProperty, filedName, value);
            } else {
                setPropertiesValue(bean, propertyName, value);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 为bean设置字段的值
     *
     * @param bean
     * @param propertyName
     * @param value
     * @param ignoreEmpty  当value为空时，不设置
     * @return
     */
    public static boolean setFieldValueToModel(Object bean, String propertyName, Object value, boolean ignoreEmpty) {
        if (ValidationUtil.isEmpty(value) && ignoreEmpty) {
            return false;
        }
        return setFieldValueToModel(bean, propertyName, value);
    }

    private static boolean setPropertiesValue(Object parentProperty, String filedName, Object value) {
        try {
            if (ValidationUtil.isEmpty(value)) {
                PropertyUtils.setProperty(parentProperty, filedName, value);
                return true;
            }
            try {
                Class<?> filedClass = PropertyUtils.getPropertyType(parentProperty, filedName);
                if (Number.class.isAssignableFrom(filedClass)) {
                    String filedClassName = filedClass.getSimpleName().toLowerCase(Locale.ENGLISH);
                    filedClassName = "integer".equals(filedClassName) ? "int" : filedClassName;
                    value = Number.class.getMethod(filedClassName + "Value").invoke((Number) value);
                }
            } catch (Throwable e) {
                // Do Nothing
            }
            PropertyUtils.setProperty(parentProperty, filedName, value);
        } catch (Exception e) {
            // Do Nothing
            return false;
        }
        return true;
    }


    /**
     * 比较两个model中不一致的基本类型字段集合
     *
     * @param <T>
     * @param model1
     * @param model2
     * @param idFieldName
     * @return
     */
    public static <T> Map<String, Object[]> compareModelSimpleDiff(T model1, T model2, String idFieldName) {
        if (ValidationUtil.isEmpty(model1) || ValidationUtil.isEmpty(model2)) {
            return null;
        }

        Map<String, Object[]> diffValuesMap = new HashMap<String, Object[]>();
        Map<String, FieldInfoModel> fieldInfoMap = ClassUtil.getModelFieldInfoMap(model1.getClass());
        Iterator<String> fieldNameIter = fieldInfoMap.keySet().iterator();
        while (fieldNameIter.hasNext()) {
            String fieldName = fieldNameIter.next();
            if (fieldName.equals(idFieldName) || fieldName.equals("createDate")
                    || fieldName.equals("recDate") || fieldName.equals("recName") || fieldName.equals("recStatus")) {
                continue;
            }

            Object value1 = ValueUtil.getFieldValueFromModel(model1, fieldName);
            Object value2 = ValueUtil.getFieldValueFromModel(model2, fieldName);
            if ((ValidationUtil.isEmpty(value1) && ValidationUtil.isEmpty(value2))
                    || (!ValidationUtil.isEmpty(value1) && value1.getClass().getName().startsWith(JAVA_MODEL_PREFIX))
                    || (!ValidationUtil.isEmpty(value2) && value2.getClass().getName().startsWith(JAVA_MODEL_PREFIX))
                    || (!ValidationUtil.isEmpty(value1) && !ValidationUtil.isEmpty(value2)
                    && String.valueOf(value1).equals(String.valueOf(value2)))) {
                continue;
            }

            diffValuesMap.put(fieldName, new Object[]{value1, value2});
        }
        return diffValuesMap;
    }

    /**
     * 将所要求的值复制到另外一个对象中
     *
     * @param source         源对象
     * @param target         目标对象
     * @param idFieldName    主键字段名称
     * @param sourceNotEmpty 仅当源对象字段不为空时
     * @param targetEmpty    仅当源对目标字段为空时
     * @param includeRec     复制包含recDate, recName, recStatus
     * @param includeCreat   复制包含createDate
     */
    public static <T> void copyFieldValue(T source, T target, String idFieldName,
                                          boolean sourceNotEmpty, boolean targetEmpty, boolean includeRec, boolean includeCreat) {
        if (ValidationUtil.isEmpty(source) || ValidationUtil.isEmpty(target)) {
            return;
        }

        Map<String, FieldInfoModel> sourceFieldInfoMap = ClassUtil.getModelFieldInfoMap(source.getClass());
        Map<String, FieldInfoModel> targetFieldInfoMap = ClassUtil.getModelFieldInfoMap(target.getClass());

        Iterator<String> targetFieldIter = targetFieldInfoMap.keySet().iterator();
        while (targetFieldIter.hasNext()) {
            String targetField = targetFieldIter.next();
            boolean isRecField = targetField.equals("recDate") || targetField.equals("recName") || targetField.equals("recStatus");
            if (!sourceFieldInfoMap.containsKey(targetField) || targetField.equals(idFieldName) || (!includeRec && isRecField)
                    || (!includeCreat && targetField.equals("createDate"))) {
                continue;
            }

            FieldInfoModel targetFieldInfo = targetFieldInfoMap.get(targetField);
            FieldInfoModel sourceFieldInfo = sourceFieldInfoMap.get(targetField);
            if (!targetFieldInfo.getFieldType().getName().equals(sourceFieldInfo.getFieldType().getName())
                    || targetFieldInfo.getFieldType().getName().equals(JAVA_MODEL_PREFIX)) {
                continue;
            }

            Object sourceValue = ValueUtil.getFieldValueFromModel(source, targetField);
            if (ValidationUtil.isEmpty(sourceValue) && sourceNotEmpty) {
                continue;
            }

            Object targetValue = ValueUtil.getFieldValueFromModel(target, targetField);
            if (!ValidationUtil.isEmpty(targetValue) && targetEmpty) {
                continue;
            }
            ValueUtil.setFieldValueToModel(target, targetField, sourceValue);
        }
    }

    /**
     * 将sources中的值全部转换成对应T的数据model
     *
     * @param sources
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T extends Serializable> List<T> copyFieldValues(List<? extends Serializable> sources, Class<T> clazz) {
        List<T> targetList = new ArrayList<T>();
        if (ValidationUtil.isEmpty(sources)) {
            return targetList;
        }

        for (Serializable source : sources) {
            T target = copyFieldValue(source, clazz);
            targetList.add(target);
        }
        return targetList;
    }

    /**
     * VO与数据Model相互转换, 字段名必须相同
     *
     * @param <T>
     * @param source
     * @param targetClazz
     */
    public static <T> T copyFieldValue(Serializable source, Class<T> targetClazz) {
        if (ValidationUtil.isEmpty(source)) {
            return null;
        }
        JSONObject json = JSONObject.fromObject(source, CommonUtil.jsonConfig);
        return JSONUtil.parseJSONObjToObject(json, targetClazz, null);
    }


    /**
     * 属性复制,limitColumns字段不复制
     *
     * @param source       源
     * @param target       目标
     * @param limitColumns 限制字段
     * @throws Exception
     * @author zhubb
     * @createTime 2018年1月22日 上午11:04:18
     */
    public static void copyFieldValueNoContainColumns(Serializable source, Serializable target, String[] limitColumns)
            throws Exception {
        copyFieldValueColumns(source, target, limitColumns, false);
    }

    /**
     * 属性复制,只复制limitColumns字段
     *
     * @param source       源
     * @param target       目标
     * @param limitColumns 限制字段
     * @throws Exception
     * @author xueqimiao
     * @createTime 2018年1月22日
     */
    public static void copyFieldValueContainColumns(Serializable source, Serializable target, String[] limitColumns)
            throws Exception {
        copyFieldValueColumns(source, target, limitColumns, true);
    }

    /**
     * @param source       源
     * @param target       目标
     * @param limitColumns 限制字段
     * @param containsFlag true：包含， false：剔除
     * @throws Exception
     * @author xueqimiao
     * @createTime 2018年1月22日
     */
    @SuppressWarnings("unchecked")
    private static void copyFieldValueColumns(Serializable source, Serializable target, String[] limitColumns,
                                              boolean containsFlag) throws Exception {
        // copy 字段为空
        if (ValidationUtil.isEmpty(limitColumns) && containsFlag) {
            return;
        }
        Class<? extends Serializable> sourceClass = source.getClass();
        List<Field> sourceFieldList = new LinkedList<>();
        while (sourceClass != null) {
            sourceFieldList.addAll(new HashSet<>(Arrays.asList(sourceClass.getDeclaredFields())));
            sourceClass = (Class<? extends Serializable>) sourceClass.getSuperclass();
        }
        if (limitColumns == null) {
            limitColumns = new String[]{};
        }
        List<String> limitColumnList = Arrays.asList(limitColumns);
        try {
            final List<String> excloudList = new ArrayList<>();
            for (int i = 0; i < sourceFieldList.size(); i++) {
                Field field = sourceFieldList.get(i);
                String fieldName = StringUtil.convertFirstCharToLower(field.getName());
                if (limitColumnList.contains(fieldName) ^ containsFlag) {
                    excloudList.add(fieldName);
                }
            }

            BeanUtils.copyProperties(source, target, excloudList.toArray(new String[]{}));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("数据copy失败，请检查后数据", e);
        }
    }

}
