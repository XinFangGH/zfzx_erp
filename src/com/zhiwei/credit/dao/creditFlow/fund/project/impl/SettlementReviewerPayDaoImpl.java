package com.zhiwei.credit.dao.creditFlow.fund.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.fund.project.SettlementReviewerPayDao;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SettlementReviewerPayDaoImpl extends BaseDaoImpl<SettlementReviewerPay> implements SettlementReviewerPayDao{

	public SettlementReviewerPayDaoImpl() {
		super(SettlementReviewerPay.class);
	}
	
	/**
	 * 按部门ID查询最新提成的信息
	 */
	public List<SettlementReviewerPay> platInvestCore(HttpServletRequest request){
		String sql = "select * from (SELECT"+
			" o.org_name as organName,o.org_Id as organId,s.id,s.payStartDate,s.payEndDate,s.factMoney,s.state,s.collectionDepartment " +
			" FROM settlement_reviewer_pay AS s "+
			" LEFT JOIN organization AS o on o.org_id=s.collectionDepartment" +
			" where 1=1 ";
		String departName = request.getParameter("departName");
		String type =  request.getParameter("type");
			if(departName!=null&&!"".equals(departName)){
				sql = sql + " and o.org_name like '%"+departName+"%'";
			}
			if("1".equals(type)){
				sql = sql + " and o.settlementType in (1,2)";
			}else if("2".equals(type)){
				sql = sql + " and o.settlementType in (3)";
			}
			sql = sql+" ORDER BY s.payStartDate desc";
			sql = sql + ") as a GROUP BY a.collectionDepartment";
		List<SettlementReviewerPay> list = (List<SettlementReviewerPay>) this.getSession().createSQLQuery(sql).
								  addScalar("id", Hibernate.LONG).
								  addScalar("organId", Hibernate.LONG).
								  addScalar("organName", Hibernate.STRING).
								  addScalar("payStartDate", Hibernate.DATE).
								  addScalar("payEndDate", Hibernate.DATE).
								  addScalar("factMoney", Hibernate.BIG_DECIMAL).
								  addScalar("state", Hibernate.SHORT).
								  setResultTransformer(Transformers.aliasToBean(SettlementReviewerPay.class)).list();
		return list;
	}
	
	
	public List<SettlementReviewerPay> listSettle(Long id,String startDate,String endDate){
		String sql= "SELECT ff.intentDate as intentDate,ff.loginname as loginname,ff.commissionRate as commissionRate,SUM(ff.incomeMoney) as incomeMoney,FORMAT(incomeMoney * commissionRate / 100,2) as ticheng from " +
		"(select c.loginname,DATE_FORMAT(d.intentDate,'%Y-%m-%d') as intentDate,d.incomeMoney as incomeMoney,FORMAT(c.commissionRate,2) as commissionRate,(incomeMoney*commissionRate/100) as ticheng from bp_fund_intent d," +
				"(select b.loginname,a.recommendCode,b.id,a.commissionRate from bp_cust_member b ,(select * from organization o where o.org_id = ?) a" +
				" where a.recommendCode = b.departmentRecommend) c where d.fundType = 'principalRepayment' and d.factDate is null and d.intentDate>= ? and d.intentDate <= ? " +
				"and d.investPersonId = c.id ) as ff  GROUP BY ff.intentDate";
		System.out.println(sql);
		List<SettlementReviewerPay> list = (List<SettlementReviewerPay>) this.getSession().createSQLQuery(sql).
											addScalar("loginname",Hibernate.STRING).
											addScalar("intentDate",Hibernate.STRING).
											addScalar("incomeMoney",Hibernate.STRING).
											addScalar("commissionRate",Hibernate.STRING).
											addScalar("ticheng",Hibernate.BIG_DECIMAL).
											setParameter(0, id).setParameter(1, startDate).setParameter(2, endDate).
											setResultTransformer(Transformers.aliasToBean(SettlementReviewerPay.class)).list();
		return list;
	}
	
	public List<SettlementReviewerPay> listInformation(String date){
		String sql = "select bp.truename as truename,DATE_FORMAT(b.intentDate, '%Y-%m-%d') AS intentDate,b.incomeMoney as incomeMoney," +
				"b.investpersonName as loginname,p.bidProName as bidProName FROM pl_bid_plan p,bp_cust_member bp,(select * from bp_fund_intent b " +
				"where fundType='principalRepayment' and intentDate = ? ) b " +
				"where b.bidPlanId = p.bidId and bp.id = b.investPersonId";
		System.out.println(sql);
		List<SettlementReviewerPay> list =  (List<SettlementReviewerPay>) this.getSession().createSQLQuery(sql).
											addScalar("truename",Hibernate.STRING).
											addScalar("intentDate",Hibernate.STRING).
											addScalar("incomeMoney",Hibernate.STRING).
											addScalar("loginname",Hibernate.STRING).
											addScalar("bidProName",Hibernate.STRING).
											setParameter(0, date).
											setResultTransformer(Transformers.aliasToBean(SettlementReviewerPay.class)).list();
		return list;
	}
	/**
	 * 根据部门ID查询对应的记录
	 */
	@Override
	public List<SettlementReviewerPay> getByOrgId(String orgId){
		String hql = "from SettlementReviewerPay as s where s.collectionDepartment=?";
		return findByHql(hql, new Object[]{orgId});
	}
}