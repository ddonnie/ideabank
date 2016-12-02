package com.dataart.fastforward.app.services.utils;

import org.apache.commons.lang3.NotImplementedException;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by logariett on 01.12.2016.
 */
public class ReflectionUtils {
    private ReflectionUtils() {
        throw new NotImplementedException("Utility classes cannot be instantiated");
    }

    public static String[] getAllNonCollectionFieldsValuesAsStrings(Object o) throws IllegalAccessException {
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        String[] fieldValues = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            if (Collection.class.isAssignableFrom(fields[i].getType()))
                continue;
            fieldValues[i] = (String) fields[i].get(o);
        }

        return fieldValues;
    }
}
