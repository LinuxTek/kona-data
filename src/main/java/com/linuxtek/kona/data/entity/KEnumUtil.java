/*
 * Copyright (C) 2011 LinuxTek, Inc.  All Rights Reserved.
 */
package com.linuxtek.kona.data.entity;

import java.util.LinkedHashMap;

import com.linuxtek.kona.data.entity.KEnumObject;
import com.linuxtek.kona.i18n.KTranslator;

public class KEnumUtil {
	public static <T extends Enum<?> & KEnumObject> String toString(T obj) {
		// FIXME: instantiate a KTranslator object ...
		// return (toString(KApp.getTranslator()));
		return (toString(obj, null));
	}

	public static <T extends Enum<?> & KEnumObject> String toString(T obj,
			KTranslator t) {
		String name = obj.getName();

		if (obj.getDisplayName() != null) {
			name = obj.getDisplayName();
            if (t != null) {
            	name = t.tr(obj.getDisplayName());
            } 
		}

		return (name);
	}

	public static <T extends Enum<?> & KEnumObject> T getInstance(
			Class<T> clazz, Long id) {

		if (id == null)
			return null;

		T[] values = clazz.getEnumConstants();
		for (T type : values) {
			if (type.getId().equals(id)) {
				return (type);
			}
		}
		throw new IllegalArgumentException("ERROR: " + clazz.getName()
				+ ": id not found: " + id);
	}
	
	public static <T extends Enum<?> & KEnumObject> T getInstance(
			Class<T> clazz, String name) {

		if (name == null)
			return null;

		T[] values = clazz.getEnumConstants();
		for (T type : values) {
			if (type.name().equalsIgnoreCase(name.trim())) {
				return (type);
			}
		}
		throw new IllegalArgumentException("ERROR: " + clazz.getName()
				+ ": name not found: " + name);
	}

	public static <T extends Enum<?> & KEnumObject> LinkedHashMap<Long, String> getMap(
			Class<T> clazz) {
		// FIXME: instantiate a KTranslator object ...
		// return (toString(KApp.getTranslator()));
		return (getMap(clazz, null));
	}

	public static <T extends Enum<?> & KEnumObject> LinkedHashMap<Long, String> getMap(
			Class<T> clazz, KTranslator t) {

		LinkedHashMap<Long, String> typeMap = new LinkedHashMap<Long, String>();

		T[] values = clazz.getEnumConstants();
		for (T type : values) {
			typeMap.put(type.getId(), type.toString());
		}
		return (typeMap);
	}
    
	public static <T extends Enum<?> & KEnumObject>
	LinkedHashMap<String,String> getStringMap(Class<T> clazz) {

		LinkedHashMap<String,String> typeMap =
				new LinkedHashMap<String,String>();

		T[] values = clazz.getEnumConstants();
		for (T type : values) {
			typeMap.put(type.getId().toString(), type.toString());
		}
		return (typeMap);
	}

	/*
	 * public static <T extends Enum & KEnumObject> LinkedHashMap<String,String>
	 * getStringMap(Class<T> clazz) {
	 * 
	 * LinkedHashMap<String,String> typeMap = new
	 * LinkedHashMap<String,String>();
	 * 
	 * T[] values = clazz.getEnumConstants(); for (T type : values) {
	 * typeMap.put(type.getId().toString(), type.toString()); } return
	 * (typeMap); }
	 */

	@SuppressWarnings("unchecked")
	public static <T extends Enum<?> & KEnumObject> T cast(Object o) {
		T t = (T) o;
		return (t);
	}
}
