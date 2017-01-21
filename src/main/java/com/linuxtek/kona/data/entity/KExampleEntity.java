package com.linuxtek.kona.data.entity;

/**
 * Super interface of all example entity objects.
 */

public interface KExampleEntity {
	public String getOrderByClause();
	public void setOrderByClause(String orderByClause);
    
	public Integer getOffset();
    public void setOffset(Integer offset);
    
    public Integer getLimit();
    public void setLimit(Integer limit);
    
    public boolean isDistinct();
	public void setDistinct(boolean distinct);
}
