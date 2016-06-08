/*
 * Copyright (C) 2011 LINUXTEK, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * TimeZone Handler
 */

public class KTimeZoneHandler implements TypeHandler<TimeZone> {

    @Override
    public void setParameter(PreparedStatement ps, int i, 
                             TimeZone parameter, JdbcType jdbcType)
            throws SQLException {

        String s = null;
        TimeZone tz = (TimeZone) parameter;

        if (tz != null) {
            s = tz.getID();
        }

        ps.setString(i, s);
    }

    @Override
    public TimeZone getResult(ResultSet rs, String columnName)
            throws SQLException {
        String s = rs.getString(columnName);
        TimeZone tz = (TimeZone) valueOf(s);
        return (tz);
    }

    @Override
    public TimeZone getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String s = cs.getString(columnIndex);
        TimeZone tz = (TimeZone) valueOf(s);
        return (tz);
    }

    private Object valueOf(String s) {
        if (s == null) return null;
        return TimeZone.getTimeZone(s);
    }

	@Override
	public TimeZone getResult(ResultSet rs, int col) throws SQLException {
        String s = rs.getString(col);
        TimeZone tz = (TimeZone) valueOf(s);
        return (tz);
	}
}
