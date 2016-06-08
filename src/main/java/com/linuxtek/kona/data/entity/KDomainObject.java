/*
 * Copyright (C) 2011 LINUXTEK, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.entity;


/**
 * Base class for Entity/Domain Objects
 */
public abstract class KDomainObject implements KEntityObject {
	private static final long serialVersionUID = 1L;

	public abstract Long getId();

    @Override
    public int hashCode() {
        if (getId() != null)
            return (getId().intValue());

        return (super.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj != null && getClass() == obj.getClass()) 
        {
            final KDomainObject other = (KDomainObject) obj;
            if (getId().equals(other.getId()))
                result = true;
        }
        return (result);
    }
}
