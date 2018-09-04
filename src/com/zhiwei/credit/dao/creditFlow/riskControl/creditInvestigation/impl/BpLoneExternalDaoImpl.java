package com.zhiwei.credit.dao.creditFlow.riskControl.creditInvestigation.impl;
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
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.riskControl.creditInvestigation.BpLoneExternalDao;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpLoneExternal;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpLoneExternalDaoImpl extends BaseDaoImpl<BpLoneExternal> implements BpLoneExternalDao{

	public BpLoneExternalDaoImpl() {
		super(BpLoneExternal.class);
	}

	@Override
	public List<BpLoneExternal> bpLoneExternalList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("SELECT "
				+" b.externalId,"
				+" b.businessType,"
				+" b.cardnumber,"
				+" b.customerId,"
				+" b.customerName,"
				+" b.customerType,"
				+" b.intentDate,"
				+" b.loneCompany,"
				+" b.loneMoney,"
				+" b.onLoneMoney,"
				+" b.overdueDays,"
				+" b.startDate,"
				+" b.projectStatus,"
				+" d.itemValue as  projectStatusValue,"
				+" b.remarks"
				+" FROM bp_lone_external AS b "
				+" LEFT JOIN"
				+" dictionary d ON b.projectStatus = d.dicId"
				+" where 1=1 ");
		String businessType = request.getParameter("businessType");
		if( null != businessType && !"".equals(businessType)) {
			hql.append(" and b.businessType = '"+businessType+"'");
		}
		String customerType = request.getParameter("customerType");
		if( null != customerType && !"".equals(customerType)) {
			hql.append(" and b.customerType = '"+customerType+"'");
		}

		String customerId = request.getParameter("customerId");
		if(null != customerId && !"".equals(customerId)) {
			hql.append("and b.customerId ="+Integer.valueOf(customerId));
		}
		String startDate = request.getParameter("startDate");
		if(!"".equals(startDate) && null != startDate) {
			hql.append(" and b.startDate >= '"+startDate+"'");
		}
		String endDate = request.getParameter("endDate");
		if(!"".equals(endDate) && null != endDate) {
			hql.append(" and b.startDate <= '"+endDate+"'");
		}
		hql.append(" order by b.createDate desc ");
	//	System.out.println("-->"+hql.toString());
		if(null!=pb){
			return this.getSession().createSQLQuery(hql.toString())
					.addScalar("externalId", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("customerType", Hibernate.STRING)
					.addScalar("customerName", Hibernate.STRING)
					.addScalar("customerId", Hibernate.INTEGER)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("projectStatusValue", Hibernate.STRING)
					.addScalar("loneCompany", Hibernate.STRING)
					.addScalar("remarks", Hibernate.STRING)
					.addScalar("loneMoney", Hibernate.BIG_DECIMAL)
					.addScalar("onLoneMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overdueDays", Hibernate.LONG)		
			        .setResultTransformer(Transformers.aliasToBean(BpLoneExternal.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}else{
					return this.getSession().createSQLQuery(hql.toString())
					.addScalar("externalId", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("customerType", Hibernate.STRING)
					.addScalar("customerName", Hibernate.STRING)
					.addScalar("customerId", Hibernate.INTEGER)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("intentDate", Hibernate.DATE)
					.addScalar("projectStatus", Hibernate.SHORT)
					.addScalar("projectStatusValue", Hibernate.STRING)
					.addScalar("loneCompany", Hibernate.STRING)
					.addScalar("remarks", Hibernate.STRING)
					.addScalar("loneMoney", Hibernate.BIG_DECIMAL)
					.addScalar("onLoneMoney", Hibernate.BIG_DECIMAL)
					.addScalar("overdueDays", Hibernate.LONG)		
			        .setResultTransformer(Transformers.aliasToBean(BpLoneExternal.class)).list();
		}
	}

	@Override
	public Long bpLoneExternalCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer(" select count(*)  from bp_lone_external as b where 1=1 ");
		String businessType = request.getParameter("businessType");
		if( null != businessType && !"".equals(businessType)) {
			hql.append(" and b.businessType = '"+businessType+"'");
		}
		String customerType = request.getParameter("customerType");
		if( null != customerType && !"".equals(customerType)) {
			hql.append(" and b.customerType = '"+customerType+"'");
		}

		String customerId = request.getParameter("customerId");
		if(null != customerId && !"".equals(customerId)) {
			hql.append("and b.customerId ="+Integer.valueOf(customerId));
		}
		String startDate = request.getParameter("startDate");
		if(!"".equals(startDate) && null != startDate) {
			hql.append(" and b.startDate >= '"+startDate+"'");
		}
		String endDate = request.getParameter("endDate");
		if(!"".equals(endDate) && null != endDate) {
			hql.append(" and b.startDate <= '"+endDate+"'");
		}
		hql.append(" order by b.createDate desc ");
		Long count=new Long(0);
		List list=this.getSession().createSQLQuery(hql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}

}