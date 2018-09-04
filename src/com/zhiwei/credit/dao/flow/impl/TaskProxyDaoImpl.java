package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.dao.flow.TaskProxyDao;
import com.zhiwei.credit.model.flow.TaskProxy;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class TaskProxyDaoImpl extends BaseDaoImpl<TaskProxy> implements TaskProxyDao{

	public TaskProxyDaoImpl() {
		super(TaskProxy.class);
	}

	@Override
	public void findList(PageBean<TaskProxy> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		/*--------查询总条数---------*/
		StringBuffer totalCounts = new StringBuffer ("select count(*) from (");
		StringBuffer sql=new StringBuffer("select * from (SELECT " +
				" tp.id," +
				" DATE_FORMAT(tp.startDate,'%Y-%m-%d') as startDate," +
				" DATE_FORMAT(tp.endDate,'%Y-%m-%d') as endDate," +
				" DATE_FORMAT(tp.createDate,'%Y-%m-%d') as createDate," +
				" u1.fullname as principalName," +
				" u2.fullname as agentName," +
				" u3.fullname as createName," +
				" IF(DATE_FORMAT(endDate,'%Y-%m-%d')<DATE_FORMAT(NOW(),'%Y-%m-%d'),1,0) as status" +
				" from task_proxy as tp" +
				" LEFT JOIN app_user as u1 on u1.userId=tp.principalId" +
				" LEFT JOIN app_user as u2 on u2.userId=tp.agentId" +
				" LEFT JOIN app_user as u3 on u3.userId=tp.createId ) as tp" +
				" where 1=1");
		String principalName=request.getParameter("principalName");
		String agentName=request.getParameter("agentName");
		String status=request.getParameter("status");
		if(null!=principalName && !"".equals(principalName)){
			sql.append(" and tp.principalName like '%"+principalName+"%'");
		}
		if(null!=agentName && !"".equals(agentName)){
			sql.append(" and tp.agentName like '%"+agentName+"%'");
		}
		if(null!=status && !"".equals(status)){
			if(status.equals("0")){
				sql.append(" and DATE_FORMAT(tp.endDate,'%Y-%m-%d')>=DATE_FORMAT(NOW(),'%Y-%m-%d')");
			}else{
				sql.append(" and DATE_FORMAT(tp.endDate,'%Y-%m-%d')<DATE_FORMAT(NOW(),'%Y-%m-%d')");
			}
		}
		String sort=request.getParameter("sort");
		String dir=request.getParameter("dir");
		if(null!=sort && !"".equals(sort)){
			sql.append(" order by tp."+sort+" "+dir);
		}else{
			sql.append(" order by tp.id asc");
		}
		totalCounts.append(sql).append(") as b");
		List list = this.getSession().createSQLQuery(sql.toString())
		.addScalar("id", Hibernate.LONG)
		.addScalar("principalName", Hibernate.STRING)
		.addScalar("agentName", Hibernate.STRING)
		.addScalar("createName", Hibernate.STRING)
		.addScalar("startDate", Hibernate.DATE)
		.addScalar("endDate", Hibernate.DATE)
		.addScalar("createDate", Hibernate.DATE)
		.addScalar("status", Hibernate.SHORT)
		.setResultTransformer(Transformers.aliasToBean(TaskProxy.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit()).list();
		pageBean.setResult(list);
		BigInteger total = (BigInteger) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}
}