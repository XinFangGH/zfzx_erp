package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.Constants;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.FormTableDao;
import com.zhiwei.credit.model.flow.FormTable;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;

@SuppressWarnings("unchecked")
public class FormTableDaoImpl extends BaseDaoImpl<FormTable> implements FormTableDao{

	
	public FormTableDaoImpl() {
		super(FormTable.class);
	}
	public List<FormTable> getListFromPro(String typeId,String tableName,AppUser curUser,PagingBean pb){
		
		
		List params= new ArrayList();
		String hql = " select DISTINCT formTable from " +
		" FormTable formTable ," +
		" FormDef formDef ," +
		" FormDefMapping formDefMapping," +
		" ProDefinition proDefinition " +
		
		" where 1=1" +
		" and formTable.formDef=formDef " +
		" and formDef=formDefMapping.formDef " +
		" and formDefMapping.proDefinition.defId=proDefinition.defId "+
	
		" and formTable.tableName like ?";
		tableName="%"+tableName+"%";
		params.add(tableName);
		
		Long tId=new Long(0);
		if(typeId!=null){
			tId= Long.parseLong(typeId);
		}
		if(tId.longValue()!=0){
			hql=hql+" and  proDefinition.proType.proTypeId =?";
			params.add(tId);
		}
		if(curUser.isSupperManage()){
			return findByHql(hql,params.toArray(), pb);
		}else{
			
			
			String uIds = "'%,"+curUser.getUserId()+",%'";
			String dIds = "'%,"+curUser.getDepartment().getDepId()+",%'";
			
			StringBuffer pHsql =new StringBuffer("select pd.defId from ProDefRights pr right join pr.proDefinition pd  where 1=1 ");
			pHsql.append("and (pr.userIds like "+uIds+"  or pr.depIds like "+dIds+" ");
			
			
			Set<AppRole> roles = curUser.getRoles();
			for(Iterator it = roles.iterator();it.hasNext();){
				AppRole role = (AppRole)it.next();
				String rIds = "'%,"+role.getRoleId()+",%'";
				pHsql.append("or pr.roleIds like "+rIds+" ");
				
			}
			
			pHsql.append(")");		
			List<Long> result = getHibernateTemplate().find(pHsql.toString());
			
			
			
			if(result!=null&&result.size()>0){
				String defIds = "";
				for(Long i:result){
					defIds=defIds+i+",";
				}
				defIds=defIds.substring(0,defIds.length()-1);
				
				hql=hql+" and proDefinition.defId in ("+defIds+")";
			}
			
			return findByHql(hql,params.toArray(), pb);
		}
		
		
	}
	/**
	 * 返回所有表定义及其表的字段
	 * @return
	 */
	public List<FormTable> getAllAndFields(){
		return (List<FormTable>)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				String hql="from FormTable ft ";
				Query query=session.createQuery(hql);
				List fts=query.list();
				for(int i=0;i<fts.size();i++){
					FormTable ft=(FormTable)fts.get(i);
					Hibernate.initialize(ft.getFormFields());
				}
				return fts;
			}
		});
	}
	@Override
	public List<FormTable> findByTableKey(String tableKey) {
		String hql="from FormTable ft where ft.tableKey=?";
		return findByHql(hql,new Object[]{tableKey});
	}
	
}