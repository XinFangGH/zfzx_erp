package com.zhiwei.credit.core.commons;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;

/**
 * 
 * 基础数据库 操作
 * 
 * @author Jiang Wanyu 	credit004
 *
 */
@SuppressWarnings("unchecked")
public interface CreditBaseDao {
	
	//清理session ,否则报错：a different object with the same identifier value was already associated with the session
	public void clearSession() throws Exception;
	/**没有参数 jiang*/
	public List queryHql(String hql) throws Exception;
	
	/**一个参数 jiang*/
	public List queryHql(String hql,Object param) throws Exception;
	
	/** 2+参数 jiang*/
	public List queryHql(String hql,Object[] params) throws Exception;
	
	/** 分页查询 --无条件      jiang */
	public List queryHql(String hql, int start, int limit) throws Exception;
	
	/** 分页查询 --一个参数条件 	jiang*/
	public List queryHql(String hql, Object param, int start, int limit) throws Exception;
	
	/** 分页查询--多参数条件【数组】 	jiang*/
	public List queryHql(String hql, Object[] params, int start, int limit) throws Exception;
	
	/**插入数据 feng*/
	public boolean saveDatas(Object object) throws Exception;
	
	/**插入数据 liujun*/
	public void saveOrUpdateAll(Collection entities) throws Exception ;
	
	/**删除数据feng*/
	public boolean deleteDatas(Object object) throws Exception;
	
	/**删除数据zhangb*/
	public void deleteAll(Collection entities) throws Exception;
	
	/** 删除数据liujun*/
	public boolean deleteDatas(Class entityClass , Serializable id) throws Exception;
	
	/**修改数据feng*/
	public boolean updateDatas(Object object) throws Exception;
	
	/**更新一部分数据【必需有主键】	jiang*/
	public boolean updatePartDatas(Object object, Serializable id) throws Exception;
	
	/**添加或更新数据*/
	public boolean saveOrUpdateDatas(Object object) throws Exception;
	
	/**根据ID获得一条记录Zhangb*/
	public Object getById(Class entityClass, Serializable id) throws Exception;
	
	/**根据ID获得一条记录并加锁Zhangb*/
	public Object getById(Class entityClass, Serializable id, LockMode lockMode) throws Exception;
	
	/**执行一条SQL不带参数Zhangb*/
	public boolean excuteSQL(String hql) throws Exception;
	
	/**执行一条SQL带1个参数Zhangb*/
	public boolean excuteSQL(String hql,Object value) throws Exception;
	
	/**执行一条SQL带多个参数Zhangb*/
	public boolean excuteSQL(String hql,Object[] values) throws Exception;
	
	//public boolean sql(String sqlString , Object[] values) throws Exception ;
	/** 查找相似实体 list liujun*/
	public List findByExample(Object o) ;
	
	public Query findByHql(String hql);
	
	public List _findById(String tid,int eid);
}
