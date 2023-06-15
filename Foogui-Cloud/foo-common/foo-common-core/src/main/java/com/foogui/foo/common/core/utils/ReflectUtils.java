package com.foogui.foo.common.core.utils;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ReflectUtils extends ReflectUtil {
    /**
     * 将对象非空字段值转为map，且key为驼峰形式
     *
     * @param context 上下文
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    public static HashMap<String, Object> getNonNullFieldValues(Object context)  {
        HashMap<String, Object> nonNullValues = new HashMap<>();
        if (context==null){
            return nonNullValues;
        }

        Field[] fields = context.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(context);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
            }
            if (value != null) {
                String camelToUnderscore = StringUtils.camelToUnderscore(field.getName());
                nonNullValues.put(camelToUnderscore, value);
            }
        }
        return nonNullValues;
    }
}
