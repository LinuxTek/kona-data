/*
 * Copyright (C) 2011 LINUXTEK, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.dao;

import java.util.List;

import com.linuxtek.kona.data.entity.KEntityObject;

/**
 * Marker interface to designate a MyBatis Dao object
 */
public interface KMyBatisDao<T extends KEntityObject, E> {
	 
    int deleteByPrimaryKey(Long id);
   
    int insert(T record);

    List<T> selectByExample(E example);
    
    T selectByPrimaryKey(Long id);
   
    int updateByPrimaryKey(T record);
    
    
    // for records with BLOBs
    List<T> selectByExampleWithBLOBs(E example);
    
    int updateByExampleWithBLOBs(T record, E example);
    
    int updateByPrimaryKeyWithBLOBs(T record);
}
