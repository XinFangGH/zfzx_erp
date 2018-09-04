package com.zhiwei.credit.dao.creditFlow.smallLoan.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlEarlyRepaymentRecordDaoImpl extends BaseDaoImpl<SlEarlyRepaymentRecord> implements SlEarlyRepaymentRecordDao{

	public SlEarlyRepaymentRecordDaoImpl() {
		super(SlEarlyRepaymentRecord.class);
	}
	
	public List<SlEarlyRepaymentRecord> getByProjectId(Long projectId) {
		String hql = "from SlEarlyRepaymentRecord srr where srr.projectId =" + projectId;
		return findByHql(hql);
	}

	@Override
	public List<SlEarlyRepaymentRecord> listByProIdAndType(Long projectId,
			String businessType) {
		String hql="from SlEarlyRepaymentRecord as s where s.projectId=? and s.businessType=?";
		return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

	@Override
	public List allList(HttpServletRequest request, Integer start, Integer limit) {
		StringBuffer sb=new StringBuffer("SELECT "
				+" e.slEarlyRepaymentId,"
				+" e.earlyDate,"
				+" e.earlyProjectMoney,"
				+" e.bidPlanId,"
				+" p.bidProName,"
				+" p.bidProNumber,"
				+" p.penaltyDays,"
				+" CONCAT(bdir.proName,'') as bdirProName,"
				+" CONCAT(bor.proName,'') as borProName,"
				+" CONCAT(pdir.proName,'') as pdirProName,"
				+" CONCAT(por.proName,'') as porProName,"
				+" CONCAT(f.incomeMoney,'') as loanInterestMoney,"
				+" CONCAT(f1.incomeMoney,'') as interestPenaltyMoney,"
				+" CONCAT(f2.incomeMoney,'') as consultationMoney,"
				+" CONCAT(f3.incomeMoney,'') as serviceMoney,"
				+" p.proType,"
				+" CONCAT(bdir.proId,'') as proId1,"
				+" CONCAT(bor.proId,'') as proId2,"
				+" CONCAT(pdir.proId,'') as proId3,"
				+" CONCAT(por.proId,'') as proId4,"
				+" CONCAT(bdir.moneyPlanId,'') as bdirmoneyPlanId,"
				+" CONCAT(bor.moneyPlanId,'') as bormoneyPlanId,"
				+" CONCAT(pdir.moneyPlanId,'')as pdirmoneyPlanId,"
				+" CONCAT(por.moneyPlanId,'') as pormoneyPlanId"
				+" FROM sl_earlyrepayment_record AS e"
				+" LEFT JOIN pl_bid_plan AS p ON p.bidId = e.bidPlanId"
				+" left join bp_business_dir_pro as bdir on p.bdirProId=bdir.bdirProId"
				+" left join bp_business_or_pro as bor on p.borProId=bor.borProId"
				+" left join bp_persion_dir_pro as pdir on p.pDirProId=pdir.pdirProId"
				+" left join bp_persion_or_pro as por on p.pOrProId=por.porProId"
				+" LEFT JOIN (select sum(fi.incomeMoney) as incomeMoney,fi.slEarlyRepaymentId from bp_fund_intent as fi left join sl_earlyrepayment_record as se on se.slEarlyRepaymentId=fi.slEarlyRepaymentId where fi.fundType = 'loanInterest' and fi.slEarlyRepaymentId is not null GROUP BY fi.slEarlyRepaymentId) AS f on f.slEarlyRepaymentId=e.slEarlyRepaymentId"
				+" LEFT JOIN (select sum(fi.incomeMoney) as incomeMoney,fi.slEarlyRepaymentId from bp_fund_intent as fi left join sl_earlyrepayment_record as se on se.slEarlyRepaymentId=fi.slEarlyRepaymentId where fi.fundType = 'interestPenalty'  and fi.slEarlyRepaymentId is not null GROUP BY fi.slEarlyRepaymentId) AS f1 on f1.slEarlyRepaymentId=e.slEarlyRepaymentId"
				+" LEFT JOIN (select sum(fi.incomeMoney) as incomeMoney,fi.slEarlyRepaymentId from bp_fund_intent as fi left join sl_earlyrepayment_record as se on se.slEarlyRepaymentId=fi.slEarlyRepaymentId where fi.fundType = 'consultationMoney'  and fi.slEarlyRepaymentId is not null GROUP BY fi.slEarlyRepaymentId) AS f2 on f2.slEarlyRepaymentId=e.slEarlyRepaymentId"
				+" LEFT JOIN (select sum(fi.incomeMoney) as incomeMoney,fi.slEarlyRepaymentId from bp_fund_intent as fi left join sl_earlyrepayment_record as se on se.slEarlyRepaymentId=fi.slEarlyRepaymentId where fi.fundType = 'serviceMoney'  and fi.slEarlyRepaymentId is not null GROUP BY fi.slEarlyRepaymentId) as f3 on f3.slEarlyRepaymentId=e.slEarlyRepaymentId"
				+" where e.checkStatus=5 and e.bidPlanId is not null");
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		String proName=request.getParameter("proName");
		if(null!=proName && !proName.equals("")){
			sb.append(" and (bdir.proName like '%"+proName+"%' or bor.proName like '%"+proName+"%' or pdir.proName like '%"+proName+"%' or por.proName like '%"+proName+"%')");
		}
		String startDate=request.getParameter("startDate");
		if(null!=startDate && !startDate.equals("")){
			sb.append(" and e.earlyDate>='"+startDate+"'");
		}
		String endDate=request.getParameter("endDate");
		if(null!=endDate && !endDate.equals("")){
			sb.append(" and e.earlyDate<='"+endDate+"'");
		}
		String proType=request.getParameter("proType");
		if(null!=proType && !proType.equals("")){
			sb.append(" and  p.proType='"+proType+"'");
		}
		System.out.println("提前还款差寻的sql是"+sb.toString());
		//System.out.println(sb.toString());
		return this.getSession().createSQLQuery(sb.toString()).setFirstResult(start).setMaxResults(limit).list();
	}

	@Override
	public Long allListCount(HttpServletRequest request) {
		StringBuffer sb=new StringBuffer("SELECT "
				+" count(e.slEarlyRepaymentId)"
				+" FROM sl_earlyrepayment_record AS e"
				+" LEFT JOIN pl_bid_plan AS p ON p.bidId = e.bidPlanId"
				+" left join bp_business_dir_pro as bdir on p.bdirProId=bdir.bdirProId"
				+" left join bp_business_or_pro as bor on p.borProId=bor.borProId"
				+" left join bp_persion_dir_pro as pdir on p.pDirProId=pdir.pdirProId"
				+" left join bp_persion_or_pro as por on p.pOrProId=por.porProId"
			
				+" where e.checkStatus=1");
		String bidProName=request.getParameter("bidProName");
		if(null!=bidProName && !bidProName.equals("")){
			sb.append(" and p.bidProName like '%"+bidProName+"%'");
		}
		String bidProNumber=request.getParameter("bidProNumber");
		if(null!=bidProNumber && !bidProNumber.equals("")){
			sb.append(" and p.bidProNumber like '%"+bidProNumber+"%'");
		}
		String proName=request.getParameter("proName");
		if(null!=proName && !proName.equals("")){
			sb.append(" and (bdir.proName like '%"+proName+"%' or bor.proName like '%"+proName+"%' or pdir.proName like '%"+proName+"%' or por.proName like '%"+proName+"%')");
		}
		String startDate=request.getParameter("startDate");
		if(null!=startDate && !startDate.equals("")){
			sb.append(" and e.earlyDate>='"+startDate+"'");
		}
		String endDate=request.getParameter("endDate");
		if(null!=endDate && !endDate.equals("")){
			sb.append(" and e.earlyDate<='"+endDate+"'");
		}
		List list=this.getSession().createSQLQuery(sb.toString()).list();
		BigInteger count=new BigInteger("0");
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				count=(BigInteger) list.get(0);
			}
		}
		return count.longValue();
	}
}