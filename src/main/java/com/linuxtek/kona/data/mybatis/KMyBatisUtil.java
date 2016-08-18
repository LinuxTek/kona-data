/*
 * Copyright (C) 2011 LINUXTEK, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.mybatis;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.linuxtek.kona.util.KClassUtil;
import com.linuxtek.kona.util.KStringUtil;


public class KMyBatisUtil {
    private static Logger logger = Logger.getLogger(KMyBatisUtil.class);

    public final static String NULL_AS_STRING = "__NULL__";
    
    public static class Criteria {
    	public Map<String,Object> filter = null;
    	
    	public Criteria() {
    		filter = new HashMap<String,Object>();
    	}
    	
    	public Criteria and(String key, Object value) {
    		filter.put(key,  value);
    		return this;
    	}
    	
    	public Map<String,Object> build() {
    		return filter;
    	}
    }
    
    public static Criteria filter() {
    	return new Criteria();
    }
    
    public static String getOrderByString(String[] sortOrder) {
        StringBuffer sb = new StringBuffer();

        for (String sortSpec : sortOrder) {
            // each sort field can have an optional ASC | DESC
            String s[] = sortSpec.split("\\s+");
            sb.append(KStringUtil.underscore(s[0]));
            if (s.length == 2) {
                sb.append(" " + s[1]);
            }
            sb.append(",");
        }

        // return final string without trailing ,
        String orderBy = KStringUtil.chop(sb.toString());
        logger.debug("orderBy: " + orderBy);
        return (orderBy);
    }
    
    public static Map<String,Object> createFilter() {
    	Map<String,Object> map = new HashMap<String,Object>();
    	return map;
    }

    public static Map<String,Object> createFilter(String key, Object value) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put(key, value);
    	return map;
    }

    /*
    public static <T,TExample, TDao> List<T> fetchByCriteria(TExample example, Integer startRow, Integer resultSize,
			String[] sortOrder, Map<String, Object> filter, boolean distinct) {
		logger.debug("AccountServiceImpl fetch(): called");
        
        //ShortUrlExample example = new ShortUrlExample();

        if (sortOrder != null) {
            example.setOrderByClause(KMyBatisUtil.getOrderByString(sortOrder));
        }

        if (startRow == null) startRow = 0;
        if (resultSize == null) resultSize = 99999999;

        example.setDistinct(distinct);

        List<T> list = null;

            KMyBatisUtil.buildExample(example.or().getClass(),
                    example.or(), filter);
            
            RowBounds rowBounds = new RowBounds(startRow, resultSize);
            list = shortUrlDao.selectByExampleWithRowbounds(example, rowBounds);

        KResultList<T> resultList = new KResultList<T>();
        resultList.setStartIndex(startRow);
        resultList.setTotalSize(list.size());
        resultList.setEndIndex(startRow + list.size());

        logger.debug("fetch(): record count: " + list.size());

        for (T entity : list) {
            resultList.add(entity);
        }

        return resultList;
	}
    */
    
    public static <T> T fetchOne(List<T> list) {
        T object = null;
        if (list == null) return null;
        if (list.size() > 1) {
            throw new IllegalStateException("Multiple results returned.");
        }

        if (list.size() == 1) {
            object = list.get(0);
        }
        return object;
    }
    
    public static Object buildExample(Class<?> clazz, Object example, 
            Map<String,Object> filter) {

        Object result = null;
        logger.debug("calling buildExample: " + clazz.getName());

        if (filter == null) {
            logger.info("Filter is null.");
            return null;
        }

        for (String key : filter.keySet()) {
            Object value = filter.get(key);

            logger.debug("buildExample: key: " + key);
            logger.debug("buildExample: value: " + value);
            
            if (value != null && value.toString().equals(NULL_AS_STRING)) {
                logger.debug("setting value to null");
                value = null;
            }
            
            // include = for completeness but should rarely be used.
            // strip leading = since that is equivalent to not including
            // any leading symbol at all.
            if (key.startsWith("=")) { 
                key = key.substring(1);
            }

            // NOTE: ORDER MATTERS since methodName will be 
            // determined by first match.
            
            String methodName = null;
            if (key.startsWith(">=")) {
                key = key.substring(2);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "GreaterThanOrEqualTo";
                logger.debug("buildExample: greaterThanEqual true  key: " + key);
            } else if (key.startsWith(">")) {
                key = key.substring(1);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "GreaterThan";
                logger.debug("buildExample: greaterThan true  key: " + key);
            } else if (key.startsWith("<=")) {
                key = key.substring(2);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "LessThanOrEqualTo";
                logger.debug("buildExample: lessThanEqual true  key: " + key);
            } else if (key.startsWith("<")) {
                key = key.substring(1);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "LessThan";
                logger.debug("buildExample: lessThan true  key: " + key);
            } else if (key.startsWith("|")) {
                key = key.substring(1);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "In";
                //FIXME: need to convert value to a List<value type>
                logger.debug("buildExample: in true  key: " + key);
            } else if (key.startsWith("%")) {
                key = key.substring(1);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "Between";
                //FIXME: need to convert value, which should be an array,
                // to values, val1 and val2
                logger.debug("buildExample: between true  key: " + key);
            } else if (key.startsWith("!|")) {
                key = key.substring(2);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "NotIn";
                logger.debug("buildExample: notIn true  key: " + key);
            } else if (key.startsWith("!%")) {
                key = key.substring(2);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "NotBetween";
                logger.debug("buildExample: notBetween true  key: " + key);
            } else if (key.startsWith("!")) {
                key = key.substring(1);
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "NotEqualTo";
                if (value == null) {
                    methodName = "and" + name + "IsNotNull";
                }
                logger.debug("buildExample: notEqual true  key: " + key);
            } else {
                // DEFAULT is a simple EqualTo match
                String name = KStringUtil.camelCase(key, true);
                methodName = "and" + name + "EqualTo";
                if (value == null) {
                	methodName = "and" + name + "IsNull";
                }
                logger.debug("buildExample: equal true  key: " + key);
            }

            logger.debug("checking for method: " + methodName);

            Method method = null;
            if (value == null) {
                method = KClassUtil.getMethod(clazz, methodName);
            } else {
                method = KClassUtil.getMethod(clazz, methodName, value);
            }

            if (method != null) {
                logger.debug("Invoking: " + clazz.getName()
                    + "." + methodName + "(" + value + ")");

                Class<?> c[] = method.getParameterTypes();

                try {
                    if (value == null) {
                        result = method.invoke(clazz.cast(example));
                    } else {
                        String s = value.toString();
                        if (value instanceof java.util.Date) {
                        //if (c[0].getName().equals("java.util.Date")) {
                            s = Long.valueOf(((Date)value).getTime()).toString();
                        } else if (value instanceof java.util.List) {
                        	List<Object> list = (List<Object>)value;
                        	s = KStringUtil.toJson(list);
                        	logger.debug("buildExample: have list: " + s);
                        }
                        logger.debug("paramterType: " + c[0].getName());
                        result = method.invoke(clazz.cast(example), 
                            KClassUtil.valueOf(c[0], s));
                    }
                } catch (Exception e) { 
                    logger.error(e.getMessage(), e); 
                }
            } else {
                logger.info("Could not find method: " 
                    + clazz.getName() + "." + methodName + "()");
            }
        }
        return result;
    }
}
