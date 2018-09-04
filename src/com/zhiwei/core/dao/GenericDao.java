package com.zhiwei.core.dao;

import java.io.Serializable;
import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
/*
 *  北京互融时代软件有限公司 OA办公自动管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
/**
 * 
 * @author csx
 *
 */
public interface GenericDao<T,PK extends Serializable> {
	public T save(T entity);
	public T merge(T entity);
	public T get(PK id);
	public void remove(PK id);
	public void remove(T entity);
	public void evict(T entity);
	
	public List<T> getAll();
	public List<T> getAll(PagingBean pb);
	public List<T> getAll(QueryFilter filter);
	
	public List<T> findByHql( String hql, Object[]objs);
	public List<T> findByHql( String hql, Object[]objs,PagingBean pb );
	public List<T> findByHql( String hql, Object[]objs, int firstResult, int pageSize );
	
	
	/**
	 * 根据属性名判断数据是否已存在.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            值
	 * @return boolean
	 */
	public boolean isExist(String propertyName, Object value);
	
	/**
	 * 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 实体对象
	 */
	public T get(String propertyName, Object value);
	public void flush();
	
	/**
	 * 执行删除或更新语句
	 * @param hql
	 * @param params
	 * @return 返回影响行数
	 */
	public Long update(final String hql,final Object... params);
	
}
