package com.zhiwei.credit.dao.creditFlow.financingAgency.persion.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.BpPersionOrProDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpBadCredit;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpPersionOrProDaoImpl extends BaseDaoImpl<BpPersionOrPro> implements BpPersionOrProDao{

	public BpPersionOrProDaoImpl() {
		super(BpPersionOrPro.class);
	}

	@Override
	public BpPersionOrPro getByBpFundProjectId(Long moneyPlanId) {
		String hql = "from BpPersionOrPro as bop where bop.moneyPlanId = ?";
		List list = new ArrayList();
		list = super.findByHql(hql, new Object[]{moneyPlanId});
		if(list!=null&&list.size()!=0){
			return (BpPersionOrPro) list.get(0);
		}
		return null;
	}

	@Override
	public List<BpPersionOrPro> bpPersionOrProList(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("SELECT "
				+" b.porProId,"
				+" b.proId,"
				+" b.proName,"
				+" b.proNumber,"
				+" b.yearInterestRate,"
				+" b.loanStarTime,"
				+" b.loanEndTime,"
				+" b.businessType,"
				+" b.moneyPlanId,"
				+" b.bidMoney,"
				+" s.id as superviseRecordId,"
				+" s.startDate,"
				+" s.endDate,"
				+" s.continuationMoney"
				+" FROM bp_persion_or_pro AS b "
				+" LEFT JOIN"
				+" sl_supervise_record s ON b.moneyPlanId = s.projectId"
				+" where s.checkStatus = 1 and (s.isInRights is null or s.isInRights=0 ) ");
		String orginalType=request.getParameter("orginalType");
		if(null != orginalType && !"".equals(orginalType)){
			hql.append(" and b.orginalType = "+Short.valueOf(orginalType));
		}
		String persionName = request.getParameter("persionName");
		if( null != persionName && !"".equals(persionName)) {
			hql.append(" and b.persionName = '%"+persionName+"%'");
		}
		String proName = request.getParameter("proName");
		if( null != proName && !"".equals(proName)) {
			hql.append(" and b.proName like '%"+proName+"%'");
		}
		String proNumber = request.getParameter("proNumber");
		if( null != proNumber && !"".equals(proNumber)) {
			hql.append(" and b.proNumber = '%"+proNumber+"%'");
		}
	
	
		hql.append(" order by b.createTime desc ");
	//	System.out.println("-->"+hql.toString());
		if(null!=pb){
			return this.getSession().createSQLQuery(hql.toString())
					.addScalar("porProId", Hibernate.LONG)
					.addScalar("proId", Hibernate.LONG)
					.addScalar("moneyPlanId", Hibernate.LONG)
					.addScalar("proName", Hibernate.STRING)
					.addScalar("proNumber", Hibernate.STRING)
					.addScalar("yearInterestRate", Hibernate.BIG_DECIMAL)
					.addScalar("loanStarTime", Hibernate.DATE)
					.addScalar("loanEndTime", Hibernate.DATE)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("bidMoney", Hibernate.BIG_DECIMAL)
					.addScalar("superviseRecordId", Hibernate.LONG)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("endDate", Hibernate.DATE)
					.addScalar("continuationMoney", Hibernate.BIG_DECIMAL)
			        .setResultTransformer(Transformers.aliasToBean(BpPersionOrPro.class)).setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}
		else{
			return this.getSession().createSQLQuery(hql.toString())
				.addScalar("porProId", Hibernate.LONG)
					.addScalar("proId", Hibernate.LONG)
					.addScalar("moneyPlanId", Hibernate.LONG)
					.addScalar("proName", Hibernate.STRING)
					.addScalar("proNumber", Hibernate.STRING)
					.addScalar("yearInterestRate", Hibernate.BIG_DECIMAL)
					.addScalar("loanStarTime", Hibernate.DATE)
					.addScalar("loanEndTime", Hibernate.DATE)
					.addScalar("businessType", Hibernate.STRING)
					.addScalar("bidMoney", Hibernate.BIG_DECIMAL)
					.addScalar("superviseRecordId", Hibernate.LONG)
					.addScalar("startDate", Hibernate.DATE)
					.addScalar("endDate", Hibernate.DATE)
					.addScalar("continuationMoney", Hibernate.BIG_DECIMAL)
					.setResultTransformer(Transformers.aliasToBean(BpPersionOrPro.class)).list();
			
		}
	}

	@Override
	public Long bpPersionOrProCount(HttpServletRequest request) {
		// TODO Auto-generated method stub
		StringBuffer hql=new StringBuffer("select count(*) from(  SELECT "
				+" b.porProId,"
				+" b.proId,"
				+" b.proName,"
				+" b.proNumber,"
				+" b.yearInterestRate,"
				+" s.continuationMoney"
				+" FROM bp_persion_or_pro AS b "
				+" LEFT JOIN"
				+" sl_supervise_record s ON b.moneyPlanId = s.projectId"
				+" where s.checkStatus = 1 and (s.isInRights is null or s.isInRights=0 ) ");
		String orginalType=request.getParameter("orginalType");
		if(null != orginalType && !"".equals(orginalType)){
			hql.append(" and b.orginalType = "+Short.valueOf(orginalType));
		}
		String persionName = request.getParameter("persionName");
		if( null != persionName && !"".equals(persionName)) {
			hql.append(" and b.persionName = '%"+persionName+"%'");
		}
		String proName = request.getParameter("proName");
		if( null != proName && !"".equals(proName)) {
			hql.append(" and b.proName like '%"+proName+"%'");
		}
		String proNumber = request.getParameter("proNumber");
		if( null != proNumber && !"".equals(proNumber)) {
			hql.append(" and b.proNumber = '%"+proNumber+"%'");
		}
	
	
		hql.append(" order by b.createTime desc ) as gg");
	//	System.out.println("-->"+hql.toString());
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