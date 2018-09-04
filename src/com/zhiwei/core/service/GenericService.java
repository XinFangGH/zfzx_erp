package com.zhiwei.core.service;
/*
 *  北京互融时代软件有限公司 OA办公自动管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.io.Serializable;
import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.paging.PagingBean;
/**
 * 
 * @author csx
 *
 * @param <T>
 * @param <PK>
 */
//@WebService
public interface GenericService<T,PK extends Serializable> {
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public T save(T entity);
	/**
	 * merge the object
	 * @param entity
	 * @return
	 */
	public T merge(T entity);
	
	/**
	 * evict the object
	 * @param entity
	 */
	public void evict(T entity);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public T get(PK id);
	
	/**
	 * 
	 * @return
	 */
	//@WebMethod(operationName="getAll")
	public List<T> getAll();
	
	/**
	 * 
	 * @param pb
	 * @return
	 */
	//@WebMethod(operationName="getAllByPb")
	public List<T> getAll(PagingBean pb);
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	//@WebMethod(operationName="getAllByFilter")
	public List<T> getAll(QueryFilter filter);
	
	//@WebMethod(operationName="remove")
	public void remove(PK id);
	
	//@WebMethod(operationName="removeByEntity")
	public void remove(T entity);
	
	/**
	 * flush the session
	 */
	public void flush();
	
	public Boolean isExist(String propertyName, Object value);

	
}
