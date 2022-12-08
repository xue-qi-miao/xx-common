package com.xx.utils;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONException;
import net.sf.json.util.PropertySetStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;


public class PropertyStrategyWrapper extends PropertySetStrategy {
    private Log logger = LogFactory.getLog(getClass());

    private PropertySetStrategy original;

    public PropertyStrategyWrapper(PropertySetStrategy original) {
        this.original = original;
    }

    @Override
    public void setProperty(Object bean, String key, Object value) throws JSONException {
        try {
            if (value != null && value.getClass().equals(MorphDynaBean.class)) {
                try {
                    Field field = MorphDynaBean.class.getDeclaredField("dynaValues");
                    ReflectionUtils.makeAccessible(field);
                    Object valueMap = field.get((MorphDynaBean) value);
                    original.setProperty(bean, key, valueMap);
                    return;
                } catch (Exception e) {
                    original.setProperty(bean, key, value);
                    return;
                }
            }

            original.setProperty(bean, key, value);
        } catch (JSONException e) {
            if (e.getMessage().toLowerCase()
                    .indexOf("java.lang.NoSuchMethodException: Unknown property '".toLowerCase()) >= 0) {
                //logger.warn("转换json到object出错，以兼容！", e);
            } else {
                throw e;
            }
        }
    }
}

/*
 * $Log: av-env.bat,v $
 */