/*
 * Copyright (C) 2011 LINUXTEK, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.mybatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * Locale Handler
 */

public class KLocaleHandler implements TypeHandler<Locale> {

    @Override
    public void setParameter(PreparedStatement ps, int i, 
                             Locale parameter, JdbcType jdbcType)
            throws SQLException {

        String s = null;
        Locale locale = (Locale) parameter;

        if (locale != null) {
            s = locale.toString();
        }

        ps.setString(i, s);
    }

    @Override
    public Locale getResult(ResultSet rs, String columnName)
            throws SQLException {
        String s = rs.getString(columnName);
        Locale locale = (Locale) valueOf(s);
        return (locale);
    }

    @Override
    public Locale getResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String s = cs.getString(columnIndex);
        Locale locale = (Locale) valueOf(s);
        return (locale);
    }

	@Override
	public Locale getResult(ResultSet rs, int col) throws SQLException {
        String s = rs.getString(col);
        Locale locale = (Locale) valueOf(s);
        return locale;
	}
    
    private Object valueOf(String s) {
        if (s != null) {
            String l[] = s.split("_");

            if (l == null || l.length > 3) {
                throw new IllegalArgumentException("Invalid locale: " + s);
            }

            if (l.length == 1) {
                return (new Locale(l[0]));
            } else if (l.length == 2) {
                return (new Locale(l[0], l[1]));
            } else {
                return (new Locale(l[0], l[1], l[2]));
            }             
        }
        return (s);
    }
}
