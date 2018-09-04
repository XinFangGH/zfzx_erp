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
import com.zhiwei.credit.dao.creditFlow.riskControl.creditInvestigation.BpBadCreditDao;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpBadCredit;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpLoneExternal;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpBadCreditDaoImpl extends BaseDaoImpl<BpBadCredit> implements BpBadCreditDao{

	public BpBadCreditDaoImpl() {
		super(BpBadCredit.class);
	}

	@Override
	public Long bpBadCreditCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer(" select count(*)  from bp_bad_credit as b where 1=1 ");
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
			hql.append(" and b.createDate >= '"+startDate+"'");
		}
		String endDate = request.getParameter("endDate");
		if(!"".equals(endDate) && null != endDate) {
			hql.append(" and b.createDate <= '"+endDate+"'");
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

	@Override
	public List<BpBadCredit> bpBadCreditList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("SELECT "
				+" b.creditId,"
				+" b.businessType,"
				+" b.cardnumber,"
				+" b.customerId,"
				+" b.customerName,"
				+" b.customerType,"
				+" b.createDate,"
				+" b.loneCompany,"
				+" b.loneMoney,"
				+" b.projectName,"
				+" b.overdueDays,"
				+" b.badType,"
				+" b.reportType,"
				+" b.creator,"
				+" d.itemValue as  badTypeValue,"
				+" b.remarks"
				+" FROM bp_bad_credit AS b "
				+" LEFT JOIN"
				+" dictionary d ON b.badType = d.dicId"
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
			hql.append(" and b.createDate >= '"+startDate+"'");
		}
		String endDate = request.getParameter("endDate");
		if(!"".equals(endDate) && null != endDate) {
			hql.append(" and b.createDate <= '"+endDate+"'");
		}
		hql.append(" order by b.createDate desc ");
	//	System.out.println("-->"+hql.toString());
		if(null!=pb){
			return this.getSession().createSQLQuery(hql.toString())
					.addScalar("creditId", Hibernate.LONG)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("cardnumber", Hibernate.STRING)
					.addScalar("customerType", Hibernate.STRING)
					.addScalar("customerName", Hibernate.STRING)
					.addScalar("customerId", Hibernate.INTEGER)
					.addScalar("createDate", Hibernate.DATE)
					.addScalar("badType", Hibernate.SHORT)
					.addScalar("badTypeValue", Hibernate.STRING)
					.addScalar("loneCompany", Hibernate.STRING)
					.addScalar("remarks", Hibernate.STRING)
					.addScalar("loneMoney", Hibernate.BIG_DECIMAL)
					.addScalar("reportType", Hibernate.SHORT)
					.addScalar("projectName", Hibernate.STRING)
					.addScalar("creator", Hibernate.STRING)
					.addScalar("overdueDays", Hibernate.LONG)		
			        .setResultTransformer(Transformers.aliasToBean(BpBadCredit.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}else{
			return this.getSession().createSQLQuery(hql.toString())
				.addScalar("creditId", Hibernate.LONG)
				.addScalar("businessType", Hibernate.STRING)
				.addScalar("cardnumber", Hibernate.STRING)
				.addScalar("customerType", Hibernate.STRING)
				.addScalar("customerName", Hibernate.STRING)
				.addScalar("customerId", Hibernate.INTEGER)
				.addScalar("createDate", Hibernate.DATE)
				.addScalar("badType", Hibernate.SHORT)
				.addScalar("badTypeValue", Hibernate.STRING)
				.addScalar("loneCompany", Hibernate.STRING)
				.addScalar("remarks", Hibernate.STRING)
				.addScalar("loneMoney", Hibernate.BIG_DECIMAL)
				.addScalar("reportType", Hibernate.SHORT)
				.addScalar("projectName", Hibernate.STRING)
				.addScalar("creator", Hibernate.STRING)
				.addScalar("overdueDays", Hibernate.LONG)		
		        .setResultTransformer(Transformers.aliasToBean(BpBadCredit.class)).list();
		}
	}

	@Override
	public BpBadCredit getByMoneyIdType(Long moneyId, String moneyType) {
		// TODO Auto-generated method stub
		String hql="from BpBadCredit b where b.moneyId=? and b.moneyType=?";
	    List<BpBadCredit> list=	this.findByHql(hql, new Object[]{moneyId,moneyType});
		if(null!=list && list.size()>0){
			BpBadCredit bpBadCredit	=list.get(0);
			return  bpBadCredit;
		}else{
			return null;
		}
		
		
		
		
	}

}