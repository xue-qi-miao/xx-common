package com.xx.utils.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName: FieldInfoModel
 * @Author: xueqimiao
 * @Date: 2021/11/19 14:14
 */
public class FieldInfoModel {

    private String fieldName;

    private Method setMethod;

    private Method getMethod;

    private Field field;

    private Class<?> fieldType;

    public FieldInfoModel(Field field, Method setMethod, Method getMethod) {
        this.field = field;
        this.setMethod = setMethod;
        this.getMethod = getMethod;
        this.fieldType = field.getType();
        this.fieldName = field.getName();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<?> fieldType) {
        this.fieldType = fieldType;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getSetMethod() {
        return setMethod;
    }

    public void setSetMethod(Method setMethod) {
        this.setMethod = setMethod;
    }

    public Method getGetMethod() {
        return getMethod;
    }

    public void setGetMethod(Method getMethod) {
        this.getMethod = getMethod;
    }
}
