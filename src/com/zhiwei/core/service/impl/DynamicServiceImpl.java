package com.zhiwei.core.service.impl;

import java.io.Serializable;
import java.util.List;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.dao.DynamicDao;
import com.zhiwei.core.service.DynamicService;
import com.zhiwei.core.web.paging.PagingBean;

public class DynamicServiceImpl implements DynamicService{
	
	private DynamicDao dynamicDao;
	
	public DynamicServiceImpl() {
		
	}
	
	public DynamicServiceImpl(DynamicDao dao) {
		this.dynamicDao=dao;
	}
	
	@Override
	public Object save(Object entity) {
		return dynamicDao.save(entity);
	}

	@Override
	public Object merge(Object entity) {
		return dynamicDao.merge(entity);
	}

	@Override
	public Object get(Serializable id) {
		return dynamicDao.get(id);
	}

	@Override
	public void remove(Serializable id) {
		dynamicDao.remove(id);
	}

	@Override
	public void remove(Object entity) {
		dynamicDao.remove(entity);
	}

	@Override
	public void evict(Object entity) {
		dynamicDao.evict(entity);
	}

	@Override
	public List<Object> getAll() {
		return dynamicDao.getAll();
	}

	@Override
	public List<Object> getAll(PagingBean pb) {
		return dynamicDao.getAll(pb);
	}

	@Override
	public List<Object> getAll(QueryFilter filter) {
		return dynamicDao.getAll(filter);
	}

}
