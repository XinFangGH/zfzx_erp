package com.zhiwei.core.dao.impl;
/*
 *  北京互融时代软件有限公司 OA办公自动管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import com.mysql.jdbc.ResultSetMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Connection;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.mysql.jdbc.ResultSetMetaData;

import com.hurong.credit.config.Pager;
import com.hurong.credit.config.Pager.OrderType;
import com.zhiwei.core.command.CriteriaCommand;
import com.zhiwei.core.command.FieldCommandImpl;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.command.SortCommandImpl;
import com.zhiwei.core.dao.GenericDao;
import com.zhiwei.core.web.paging.PagingBean;

@SuppressWarnings("unchecked")
abstract public class GenericDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements GenericDao<T, PK> {
	protected Log logger=LogFactory.getLog(GenericDaoImpl.class);
	
	protected JdbcTemplate jdbcTemplate;
	
	protected Class persistType;
	
	/**
	 * set the query(hql) in the app-dao.xml, then can use by
	 * getAllByQueryName(..);
	 */
	protected Map<String, String> querys = new HashMap<String, String>();
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setPersistType(Class persistType) {
		this.persistType = persistType;
	}
	
	public GenericDaoImpl(Class persistType) {
		this.persistType=persistType;
	}
	public T get(PK id) {
		return (T)getHibernateTemplate().get(persistType,id);
	}

	public T save(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}
	
	public T merge(T entity){
		getHibernateTemplate().merge(entity);
		return entity;
	}
	
	public void evict(T entity){
		getHibernateTemplate().evict(entity);
	}
	
	/**
	 * return a page record of a table.
	 * 
	 * @param queryString
	 * @param values
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public List find(final String queryString, final Object[] values, final int firstResult, final int pageSize) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query queryObject = session.createQuery(queryString);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				if (pageSize > 0) {
					queryObject.setFirstResult(firstResult).setMaxResults(pageSize).setFetchSize(pageSize);
				}
				return queryObject.list();
			}
		});
	}
	
	
	public List<T> getAll(){
		return (List<T>)getHibernateTemplate().execute(new HibernateCallback() {
		
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
					String hql="from " + persistType.getName();
					Query query=session.createQuery(hql);
					return query.list();
			}
		});
	}
	
	public List<T> getAll(final PagingBean pb){
		final String hql="from " + persistType.getName();
		int totalItems=getTotalItems(hql,null).intValue();
		pb.setTotalItems(totalItems);
		return (List<T>)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query=session.createQuery(hql);
				query.setFirstResult(pb.getFirstResult()).setFetchSize(pb.getPageSize());
				query.setMaxResults(pb.getPageSize());
				return query.list();
			}
		});
	}
	
	/**
	 * 返回queryString查询返回的记录数
	 * 
	 * @param queryString
	 * @param values
	 * @return Long
	 */
	public Long getTotalItems(String queryString,
			final Object[] values) {
		
		int orderByIndex=queryString.toUpperCase().indexOf(" ORDER BY ");
		
		if(orderByIndex!=-1){
			queryString=queryString.substring(0, orderByIndex);
		}
		
		QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(queryString, queryString, 
		java.util.Collections.EMPTY_MAP, (org.hibernate.engine.SessionFactoryImplementor)getSessionFactory());
		queryTranslator.compile(java.util.Collections.EMPTY_MAP, false);
		final String sql="select count(*) as c from (" + queryTranslator.getSQLString() + ") tmp_count_t"; 
		
		Object reVal= getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				SQLQuery query= session.createSQLQuery(sql);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return query.uniqueResult();
			}
		});
		
		//if(reVal==null) return new Long(0);
		
		return new Long(reVal.toString());
		
	}

	
	
	public List<T> findByHql(final String hql,final Object[]objs){
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query=session.createQuery(hql);
				if(objs!=null){
					for(int i=0;i<objs.length;i++){
						query.setParameter(i,objs[i]);
					}
				}
				return (List<T>)query.list();
			}
		});
	}
	
	public List<T> findByHql(final String hql,final Object[]objs,final int firstResult,final int pageSize ){
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query=session.createQuery(hql);
				query.setFirstResult(firstResult).setMaxResults(pageSize);
				if(objs!=null){
					for(int i=0;i<objs.length;i++){
						query.setParameter(i,objs[i]);
					}
				}
				return (List<T>)query.list();
			}
		});
	}
	
	public List<T> findByHql(final String hql,final Object[]objs,PagingBean pb ){
		int totalItems=getTotalItems(hql,objs).intValue();
		pb.setTotalItems(totalItems);
		return findByHql(hql,objs,pb.getFirstResult(),pb.getPageSize());
	}
	
	public List find(final String hql,final Object[] objs,PagingBean pb){
		int totalItems=getTotalItems(hql,objs).intValue();
		pb.setTotalItems(totalItems);
		return find(hql,objs,pb.getFirstResult(),pb.getPageSize());
	}
	
	
	
	public List<T> findByHql(final String hql){
		return findByHql(hql,null);
	}

	public void remove(PK id) {
		getHibernateTemplate().delete(get(id));
	}
	
	public void remove(T entity){
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 通过hql查找某个唯一的实体对象
	 * @author QGH
	 * @param queryString
	 * @param values
	 * @return
	 */
	public Object findUnique(final String hql, final Object[] values) {
		return (Object) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
	
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
				}
				return query.uniqueResult();
			}
		});
	}

	//---------------------Query Filter Start----------------------------------------------------------
	public int getCountByFilter(final QueryFilter filter) {
        Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(persistType);
                for(int i=0;i<filter.getCommands().size();i++){
                	CriteriaCommand command=filter.getCommands().get(i);
                	if (!(command instanceof SortCommandImpl)) {
                		criteria = command.execute(criteria);
					}
                }
                criteria.setProjection(Projections.rowCount());
                return criteria.uniqueResult();
            }
        });
        if(count==null) return new Integer(0);
        return count.intValue();
    }
	

	public List getAll(final QueryFilter queryFilter){
		
		if(StringUtils.isNotEmpty(queryFilter.getFilterName())){
			return getAll2(queryFilter);
		}
		
    	int totalCounts=getCountByFilter(queryFilter);
    	//设置总记录数
    	if(queryFilter.getPagingBean()!=null){
    	queryFilter.getPagingBean().setTotalItems(totalCounts);
    	}
    	List<T> resultList=(List<T>)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException,SQLException {
				  Criteria criteria = session.createCriteria(persistType);
				  //重新清除alias的命名，防止计算记录行数后名称还存在该处
				  queryFilter.getAliasSet().clear();
				  setCriteriaByQueryFilter(criteria,queryFilter);
	              return criteria.list();
			}
		});
    	
    	if(queryFilter.isExport()){
    		SimpleDateFormat tempDate = new SimpleDateFormat("yyyyMMddhhmmssSSS"); 
    		String datetime = tempDate.format(new java.util.Date());
    		queryFilter.getRequest().setAttribute("fileName", datetime);
    		queryFilter.getRequest().setAttribute("__exportList",resultList);
    	}
    	return resultList;
    }
	
	/**
	 * 按Hql查询并返回
	 * @param queryFilter
	 * @param hql
	 * @param params
	 * @return
	 */
	public List getAll2(QueryFilter queryFilter){	
		String hql=querys.get(queryFilter.getFilterName()).trim();
		
		String newHql=null;
		String condition=null;
		String groupBy=null;

		//重新设置排序 
		int orderIndex = hql.toUpperCase().indexOf(" ORDER BY ");
		int whereIndex = hql.toUpperCase().indexOf(" WHERE ");
		
		
		if (orderIndex < 0) {
			orderIndex = hql.length();
		}
		if(whereIndex<0){
			whereIndex=hql.length();
		}
		
		if(whereIndex<0){
			condition=" where 1=1 ";
		}else{
			condition=hql.substring(whereIndex+7,orderIndex);
			
			logger.debug("condition:" + condition);
			
			Pattern groupByPattern = Pattern.compile(" GROUP BY [\\w|.]+");
			Matcher m = groupByPattern.matcher(condition.toUpperCase());
			//存在Group By
			if(m.find()){
				groupBy=condition.substring(m.start(),m.end());
				condition=condition.replace(groupBy, " ");
			}
			condition=" where ("+condition+")";
		}
		
		String sortDesc="";
		
		//取得条件以及排序
		for(int i=0;i<queryFilter.getCommands().size();i++){
			CriteriaCommand command=queryFilter.getCommands().get(i);
			if(command instanceof FieldCommandImpl){
				condition+=" and " + ((FieldCommandImpl)command).getPartHql();
			}else if(command instanceof SortCommandImpl){
				if(!"".equals(sortDesc)){
					sortDesc+=",";
				}
				sortDesc+=((SortCommandImpl)command).getPartHql();
			}
		}
		
		newHql = hql.substring(0, whereIndex);

		if(queryFilter.getAliasSet().size()>0){
			//取得hql中的表的别名，为关联外表作准备
			int fromIndex=newHql.indexOf(" FROM ");
			String entityAliasName=null;
			if(fromIndex>0){
				String afterFrom=newHql.substring(fromIndex+6);
				
				String []keys=afterFrom.split("[ ]");
				if(keys.length>1){
					if(!keys[1].toUpperCase().equals("ORDER") &&!keys[1].toUpperCase().equals("JOIN")){
						entityAliasName=keys[1];
					}
				}
				//加上别名
				if(entityAliasName==null){
					entityAliasName="vo";
					newHql=newHql.replace(keys[0], keys[0]+" " + entityAliasName);
				}
			}
			
			//若存在外键，则进行组合
			String joinHql="";
			Iterator it=queryFilter.getAliasSet().iterator();
			while(it.hasNext()){
				String joinVo=(String)it.next();
				joinHql+=" join " + entityAliasName+"."+joinVo +" " + joinVo;
			}
	
			//加上外键的联接
			if(!"".equals(joinHql)){
				newHql+=joinHql;
			}
		}
		//加上条件限制
		newHql+= condition;
		
		//加上分组
		if(groupBy!=null){
			newHql+=groupBy + " ";
		}
		
		//加上排序
		if(!"".equals(sortDesc)){//带在排序在内
			newHql+=" order by " + sortDesc;
		}else{
			newHql+=hql.substring(orderIndex);
		}
		
		Object[] params=queryFilter.getParamValueList().toArray();
		
		//显示多少条记录
		int totalItems=getTotalItems(newHql,params).intValue();
		queryFilter.getPagingBean().setTotalItems(totalItems);
		if(logger.isDebugEnabled()){
			logger.debug("new hql:" + newHql);
		}
		return find(newHql, params,queryFilter.getPagingBean().getFirstResult(),queryFilter.getPagingBean().getPageSize());
	}
	
	public void flush(){
		getHibernateTemplate().flush();
	}
	
	private Criteria setCriteriaByQueryFilter(Criteria criteria,QueryFilter filter){
		for(int i=0;i<filter.getCommands().size();i++){
			criteria=filter.getCommands().get(i).execute(criteria);
		}
		if(filter.getRequest()!=null){
		if("true".equals(filter.getRequest().getParameter("isExportAll"))){
			criteria.setFirstResult(0);
			criteria.setMaxResults(filter.getPagingBean().getTotalItems());
		}else{
			criteria.setFirstResult(filter.getPagingBean().getFirstResult());
			criteria.setMaxResults(filter.getPagingBean().getPageSize());
		}
		}
		return criteria;
	}
	
	//----------------------Query Filter End-----------------------------------------------------------

	public void setQuerys(Map<String, String> querys) {
		this.querys = querys;
	}
	
	/**
	 * 执行删除或更新语句
	 * @param hql
	 * @param params
	 * @return 返回影响行数
	 */
	public Long update(final String hql,final Object... params){
		return (Long)getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query=session.createQuery(hql);
				int i=0;
				for(Object param:params){
					query.setParameter(i++, param);
				}
				Integer rows=query.executeUpdate();
				return new Long(rows);
			}
		});
	}
	
	public boolean isExist(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		T object = get(propertyName, value);
		return (object != null);
	}
	/**
	 * 
	 * @param tableName 表名称
	 * @param selectData 查询的字段 
	 * @param 参数 key 为数据库字段
	 * @return
	 */
	public List executeSqlFind(final String tableName, final String selectData, final Map<String, String> params) {
		return (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					@SuppressWarnings("deprecation")
					public List doInHibernate(Session session) {
						List list = new ArrayList<String>();
						StringBuffer buff = new StringBuffer("select  ");
						buff.append(selectData);
						buff.append(" from ");
						buff.append(tableName);
						buff.append(" where ");
						Iterator iter = params.entrySet().iterator(); 
						while (iter.hasNext()) {
							Map.Entry entry = (Map.Entry) iter.next(); 
						    Object key = entry.getKey(); 
						    Object val = entry.getValue(); 
							buff.append(key);
							buff.append("=");
							buff.append(val);
							buff.append(" and ");
						}
						buff.append(" '1==1' ");
						Connection con = session.connection();
						Statement stmt = null;
						ResultSet rs = null;
						try {
							stmt = con.createStatement();
							rs = stmt.executeQuery(buff.toString());
							list=convertList(rs);
						} catch (SQLException e) {
							e.printStackTrace();
						} finally {
							try {
								stmt.close();
								rs.close();
								con.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						return list;
					}
				});
	}
	
	public  List convertList(ResultSet rs) throws SQLException {
		List list = new ArrayList();
		ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
	    int columnCount = md.getColumnCount(); 
		while (rs.next()) { 
		for (int i = 1; i <= columnCount; i++) {
			if(rs.getObject(i)!=null&&!rs.getObject(i).equals("")){
			 list.add(rs.getObject(i));
			}
		}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		Assert.hasText(propertyName, "propertyName must not be empty");
		Assert.notNull(value, "value is required");
		String hql = "from " + persistType.getName() + " as model where model." + propertyName + " = ?";
		return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
	}

	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		if (pager == null) {
			pager = new Pager();
		}
		Integer pageNumber = pager.getPageNumber();
		Integer pageSize = pager.getPageSize();
		String property = pager.getProperty();
		String keyword = pager.getKeyword();
		String orderBy = pager.getOrderBy();
		OrderType orderType = pager.getOrderType();
		
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (StringUtils.isNotEmpty(property) && StringUtils.isNotEmpty(keyword)) {
			String propertyString = "";
			if (property.contains(".")) {
				String propertyPrefix = StringUtils.substringBefore(property, ".");
				String propertySuffix = StringUtils.substringAfter(property, ".");
				criteria.createAlias(propertyPrefix, "model");
				propertyString = "model." + propertySuffix;
			} else {
				propertyString = property;
			}
			criteria.add(Restrictions.like(propertyString, "%" + keyword + "%"));
		}
		
		Integer totalCount = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		if (StringUtils.isNotEmpty(orderBy) && orderType != null) {
			if (orderType == OrderType.asc) {
				criteria.addOrder(Order.asc(orderBy));
			} else {
				criteria.addOrder(Order.desc(orderBy));
			}
		}
		pager.setTotalCount(totalCount);
		pager.setList(criteria.list());
		return pager;
	}
}
