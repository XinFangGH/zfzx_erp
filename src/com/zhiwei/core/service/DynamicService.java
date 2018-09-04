package com.zhiwei.core.service;

import java.io.Serializable;
import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.paging.PagingBean;

public interface DynamicService {
	/**
	 * 保存及更新实体
	 * @param entity
	 * @return
	 */
	public Object save(Object entity);
	/**
	 * 保存及合并实体
	 * @param entity
	 * @return
	 */
	public Object merge(Object entity);
	/**
	 * 按主键取到实体
	 * @param id
	 * @return
	 */
	public Object get(Serializable id);
	/**
	 * 按主键删除实体
	 * @param id
	 */
	public void remove(Serializable id);
	/**
	 * 删除实体
	 * @param entity
	 */
	public void remove(Object entity);
	/**
	 * 从hibernate中的session缓存移除实体
	 * @param entity
	 */
	public void evict(Object entity);
	/**
	 * 取得所有的实体
	 * @return
	 */
	public List<Object> getAll();
	/**
	 * 按分页取得所有实体
	 * @param pb
	 * @return
	 */
	public List<Object> getAll(PagingBean pb);
	/**
	 * 按过滤器显示所有实体
	 * @param filter
	 * @return
	 */
	public List<Object> getAll(QueryFilter filter);
}
