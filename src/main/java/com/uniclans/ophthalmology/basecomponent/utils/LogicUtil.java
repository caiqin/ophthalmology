package com.uniclans.ophthalmology.basecomponent.utils;


import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: LogicUtil
 * @Description: 简单逻辑判断
 */
public class LogicUtil {

    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Collection collection) {
        if (null == collection || 0 == collection.size()) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNotNullAndEmpty(Collection collection) {
        return !isNullOrEmpty(collection);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Map map) {
        if (null == map || 0 == map.size()) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNotNullAndEmpty(Map map) {
        return !isNullOrEmpty(map);
    }

    public static boolean isNullOrEmpty(Object[] objects) {
        if (null == objects || 0 == objects.length) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNullAndEmpty(Object[] objects) {
        return !isNullOrEmpty(objects);
    }

    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotNull(Object Object) {
        return !isNull(Object);
    }

    public static boolean isNullOrEmpty(String subject) {
        if (null == subject || "".equals(subject)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断字符串是否  不为空并且不为null
     * @param subject
     * @return
     */
    public static boolean isNotNullAndEmpty(String subject) {
        return !isNullOrEmpty(subject);
    }

}
