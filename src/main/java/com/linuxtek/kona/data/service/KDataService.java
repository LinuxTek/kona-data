/*
 * Copyright (C) 2011 LINUXTEK, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.service;

import java.util.List;
import java.util.Map;

import com.linuxtek.kona.data.entity.KEntityObject;

/**
 * Basic CRUD operations.
 */
public interface KDataService<D extends KEntityObject> {

    public D add(D data);

    public D update(D data);

    public void remove(D data); 

    public void removeById(Long id);

    public D fetchById(Long id);

    public List<D> fetchByCriteria(Integer startRow,  Integer resultSize, 
    		String[] sortOrder, Map<String, Object> filterCriteria, 
            boolean distinct);

    public void validate(D data);
}
