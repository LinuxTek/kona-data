/*
 * Copyright (C) 2011 LINUXTEK, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.dao;

import java.util.List;

import com.linuxtek.kona.data.entity.KEntityObject;

/**
 * Marker interface to designate a MyBatis Dao object
 */
public interface KMyBatisDaoWithBlobs<T extends KEntityObject, E> extends KMyBatisDao<T, E> {
	 
    List<T> selectByExampleWithBLOBs(E example);
    
    //int updateByExampleWithBLOBs(T record, E example);
    
    int updateByPrimaryKeyWithBLOBs(T record);
}
