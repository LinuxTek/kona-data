/*
 * Copyright (C) 2011 LinuxTek, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.entity;

import java.io.Serializable;

/**
 */

public interface KEnumObject extends Serializable {
    public Long getId();
    public String getName();
    public String getDisplayName();
    public boolean isEnabled();
}
