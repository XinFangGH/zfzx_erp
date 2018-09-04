package com.highchart.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import com.highchart.dao.HighchartDao;
import com.highchart.model.Highchart;
import com.highchart.model.HighchartModel;
import com.highchart.model.Series;
import com.highchart.model.SeriesData;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PageBean;

@SuppressWarnings("unchecked")
public class HighchartDaoImpl extends BaseDaoImpl<Highchart> implements HighchartDao {
	public HighchartDaoImpl() {
		super(Highchart.class);
	}

	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void findList(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String dateType = request.getParameter("dateType");
		String start_searchDate=request.getParameter("start_searchDate");
		String end_searchDate=request.getParameter("end_searchDate");
		
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" t_week.content as searchDate, ");
		sb.append(" SUM(CASE  WHEN t2.investPersonType=1 and t2.transferType=1 then t2.incomMoney else 0 END) AS moneyA, ");
		sb.append(" SUM(CASE  WHEN t2.investPersonType=1 and t2.transferType=2 then t2.payMoney else 0 END) AS moneyB, ");
		sb.append(" SUM(CASE  WHEN t2.investPersonType=0 and t2.transferType=1 then t2.incomMoney else 0 END) AS moneyC, ");
		sb.append(" SUM(CASE  WHEN t2.investPersonType=0 and t2.transferType=2 then t2.payMoney else 0 END) AS moneyD ");
		sb.append(" from ");
		sb.append(" ( ");
		//起日不为null，止日不为null 查询start_searchDate到end_searchDate
		if(dateType != null && "1".equals(dateType)){//日
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int days = DateUtil.getDaysBetweenDate(start_searchDate, end_searchDate);
				for(int i=0;i<=days;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" DAY),'%Y-%m-%d') as content");
					if(i!=days){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12天
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" DAY),'%Y-%m-%d') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as t_week ");
			
			sb.append(" LEFT JOIN ob_account_deal_info as t2 ");
			sb.append(" on t_week.content=DATE_FORMAT(transferDate,'%Y-%m-%d') and t2.dealRecordStatus=2 ");
			sb.append(" GROUP BY t_week.content ");
		}else if(dateType != null && "2".equals(dateType)){//月
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=months;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
					if(i!=months){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12月
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" MONTH),'%Y-%m') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as t_week ");
			
			sb.append(" LEFT JOIN ob_account_deal_info as t2 ");
			sb.append(" on t_week.content=DATE_FORMAT(transferDate,'%Y-%m') and t2.dealRecordStatus=2 ");
			sb.append(" GROUP BY t_week.content ");
		}else if(dateType != null && "3".equals(dateType)){//季
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				int seasons = months / 3;
				for(int i=0;i<=seasons;i++){
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER)),'季度') as content");
					if(i!=seasons){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12季
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+df.format(new Date())+"', INTERVAL -"+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+df.format(new Date())+"', INTERVAL 11 QUARTER)),'季度') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as t_week ");
			
			sb.append(" LEFT JOIN ob_account_deal_info as t2 ");
			sb.append(" on t_week.content=CONCAT(DATE_FORMAT(t2.transferDate, '%Y'),'年第',QUARTER (t2.transferDate),'季度') and t2.dealRecordStatus=2 ");
			sb.append(" GROUP BY t_week.content ");
		}else if(dateType != null && "4".equals(dateType)){//年
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int years = DateUtil.getYearSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=years;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" YEAR),'%Y') as content");
					if(i!=years){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12年
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" YEAR),'%Y') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as t_week ");
			
			sb.append(" LEFT JOIN ob_account_deal_info as t2 ");
			sb.append(" on t_week.content=DATE_FORMAT(transferDate,'%Y') and t2.dealRecordStatus=2 ");
			sb.append(" GROUP BY t_week.content ");
		}
		
		
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			addScalar("moneyB", Hibernate.BIG_DECIMAL).
			addScalar("moneyC", Hibernate.BIG_DECIMAL).
			addScalar("moneyD", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}
	
	@Override
	public void findUserTrend(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String dateType = request.getParameter("dateType");
		String start_searchDate=request.getParameter("start_searchDate");
		String end_searchDate=request.getParameter("end_searchDate");
		
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
		sb.append(" SUM(CASE  WHEN  bm.registrationDate is not NULL then 1 else 0 END) AS sumA, ");
		sb.append(" SUM(CASE  WHEN  bm.isCheckCard=1 then 1 else 0 END) AS sumB, ");
		sb.append(" SUM(CASE  WHEN bm.thirdPayFlagId is not NULL then 1 else 0 END) AS sumC ");
		sb.append(" from ");
		sb.append(" ( ");
		//起日不为null，止日不为null 查询start_searchDate到end_searchDate
		if(dateType != null && "1".equals(dateType)){//日
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int days = DateUtil.getDaysBetweenDate(start_searchDate, end_searchDate);
				for(int i=0;i<=days;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" DAY),'%Y-%m-%d') as content");
					if(i!=days){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12天
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" DAY),'%Y-%m-%d') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=DATE_FORMAT(bm.registrationDate,'%Y-%m-%d') ");
			sb.append("  GROUP BY months.content ");
		}else if(dateType != null && "2".equals(dateType)){//月
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=months;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
					if(i!=months){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12个月
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" MONTH),'%Y-%m') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=DATE_FORMAT(bm.registrationDate,'%Y-%m') ");
			sb.append("  GROUP BY months.content ");
		}else if(dateType != null && "3".equals(dateType)){//季
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				int season = months / 3;
				for(int i=0;i<=season;i++){
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER)),'季度') as content");
					if(i!=season){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12季
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+df.format(new Date())+"', INTERVAL -"+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER)),'季度') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=CONCAT(DATE_FORMAT(bm.registrationDate, '%Y'),'年第',QUARTER (bm.registrationDate),'季度') ");
			sb.append("  GROUP BY months.content ");
		}else if(dateType != null && "4".equals(dateType)){//年
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int years = DateUtil.getYearSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=years;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" YEAR),'%Y') as content");
					if(i!=years){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12年
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" YEAR),'%Y') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=DATE_FORMAT(bm.registrationDate,'%Y') ");
			sb.append("  GROUP BY months.content ");
		}
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("sumA", Hibernate.LONG).
			addScalar("sumB", Hibernate.LONG).
			addScalar("sumC", Hibernate.LONG).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findUserInvest(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String dateType = request.getParameter("dateType");
		String start_searchDate=request.getParameter("start_searchDate");
		String end_searchDate=request.getParameter("end_searchDate");
		
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
		sb.append(" IFNULL(( select COUNT(DISTINCT oi.investPersonId) from ob_account_deal_info as od where  od.id=oi.id GROUP BY od.investPersonId),0) as sumD ");
		sb.append(" from ");
		sb.append(" ( ");
		//起日不为null，止日不为null 查询start_searchDate到end_searchDate
		
		if(dateType != null && "1".equals(dateType)){//日
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int days = DateUtil.getDaysBetweenDate(start_searchDate, end_searchDate);
				for(int i=0;i<=days;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" DAY),'%Y-%m-%d') as content");
					if(i!=days){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12天
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" DAY),'%Y-%m-%d') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=DATE_FORMAT(bm.registrationDate,'%Y-%m-%d')");
			sb.append(" LEFT JOIN ob_account_deal_info as oi on oi.investPersonId=bm.id ");
			sb.append(" and oi.investPersonType=0 and transferType=4 and dealRecordStatus=2");
			sb.append(" and oi.transferDate is NOT NULL and DATE_FORMAT(oi.transferDate,'%Y-%m-%d')=months.content");
			sb.append("  GROUP BY months.content");
		}else if(dateType != null && "2".equals(dateType)){//月
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=months;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
					if(i!=months){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12月
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" MONTH),'%Y-%m') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=DATE_FORMAT(bm.registrationDate,'%Y-%m')");
			sb.append(" LEFT JOIN ob_account_deal_info as oi on oi.investPersonId=bm.id ");
			sb.append(" and oi.investPersonType=0 and transferType=4 and dealRecordStatus=2");
			sb.append(" and oi.transferDate is NOT NULL and DATE_FORMAT(oi.transferDate,'%Y-%m')=months.content");
			sb.append("  GROUP BY months.content");
		}else if(dateType != null && "3".equals(dateType)){//季
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				int season = months / 3;
				for(int i=0;i<=season;i++){
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER)),'季度') as content");
					if(i!=season){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12季
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER)),'季度') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=CONCAT(DATE_FORMAT(bm.registrationDate, '%Y'),'年第',QUARTER (bm.registrationDate),'季度')");
			sb.append(" LEFT JOIN ob_account_deal_info as oi on oi.investPersonId=bm.id ");
			sb.append(" and oi.investPersonType=0 and transferType=4 and dealRecordStatus=2");
			sb.append(" and oi.transferDate is NOT NULL and CONCAT(DATE_FORMAT(bm.registrationDate, '%Y'),'年第',QUARTER (bm.registrationDate),'季度')=months.content");
			sb.append("  GROUP BY months.content");
		}else if(dateType != null && "4".equals(dateType)){//年
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int years = DateUtil.getYearSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=years;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" YEAR),'%Y') as content");
					if(i!=years){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12年
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" YEAR),'%Y') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=DATE_FORMAT(bm.registrationDate,'%Y')");
			sb.append(" LEFT JOIN ob_account_deal_info as oi on oi.investPersonId=bm.id ");
			sb.append(" and oi.investPersonType=0 and transferType=4 and dealRecordStatus=2");
			sb.append(" and oi.transferDate is NOT NULL and DATE_FORMAT(oi.transferDate,'%Y')=months.content");
			sb.append("  GROUP BY months.content");
		}
		
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("sumD", Hibernate.LONG).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}
	
	@Override
	public void findOverdue(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String dateType = request.getParameter("dateType");
		String start_searchDate=request.getParameter("start_searchDate");
		String end_searchDate=request.getParameter("end_searchDate");
		
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
		sb.append(" SUM(CASE WHEN bi.intentDate is not NULL  THEN bi.incomeMoney ELSE 0 END) AS moneyA, ");
		sb.append(" IFNULL((SELECT COUNT(DISTINCT bi.bidPlanId)  from bp_fund_intent as bfi where bfi.fundIntentId=bi.fundIntentId),0) as sumA ");
		sb.append(" from ");
		sb.append(" ( ");
		
		//起日不为null，止日不为null 查询start_searchDate到end_searchDate
		if(dateType != null && "1".equals(dateType)){//日
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int days = DateUtil.getDaysBetweenDate(start_searchDate, end_searchDate);
				for(int i=0;i<=days;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" DAY),'%Y-%m-%d') as content");
					if(i!=days){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12天
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" DAY),'%Y-%m-%d') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_fund_intent as bi ON months.content= DATE_FORMAT(bi.intentDate,'%Y-%m-%d')");
			sb.append(" and bi.isCheck=0 and bi.isValid=0 and bi.intentDate<NOW()");
			sb.append(" and (bi.factDate>bi.intentDate or bi.factDate is NULL)");
			sb.append(" and bi.fundType in ('principalRepayment','serviceMoney','consultationMoney','loanInterest')");
			sb.append(" GROUP BY months.content");
		}else if(dateType != null && "2".equals(dateType)){//月
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=months;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
					if(i!=months){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12个月
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" MONTH),'%Y-%m') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_fund_intent as bi ON months.content= DATE_FORMAT(bi.intentDate,'%Y-%m')");
			sb.append(" and bi.isCheck=0 and bi.isValid=0 and bi.intentDate<NOW()");
			sb.append(" and (bi.factDate>bi.intentDate or bi.factDate is NULL)");
			sb.append(" and bi.fundType in ('principalRepayment','serviceMoney','consultationMoney','loanInterest')");
			sb.append(" GROUP BY months.content");
		}else if(dateType != null && "3".equals(dateType)){//季
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				int season = months / 3;
				for(int i=0;i<=season;i++){
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER)),'季度') as content");
					if(i!=season){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12季
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER)),'季度') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_fund_intent as bi ON months.content= CONCAT(DATE_FORMAT(bi.intentDate, '%Y'),'年第',QUARTER (bi.intentDate),'季度')");
			sb.append(" and bi.isCheck=0 and bi.isValid=0 and bi.intentDate<NOW()");
			sb.append(" and (bi.factDate>bi.intentDate or bi.factDate is NULL)");
			sb.append(" and bi.fundType in ('principalRepayment','serviceMoney','consultationMoney','loanInterest')");
			sb.append(" GROUP BY months.content");
		}else if(dateType != null && "4".equals(dateType)){//年
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int years = DateUtil.getYearSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=years;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" YEAR),'%Y') as content");
					if(i!=years){
						sb.append(" UNION ");
					}
				}
			//都为null
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12 年
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" YEAR),'%Y') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" LEFT JOIN bp_fund_intent as bi ON months.content= DATE_FORMAT(bi.intentDate,'%Y')");
			sb.append(" and bi.isCheck=0 and bi.isValid=0 and bi.intentDate<NOW()");
			sb.append(" and (bi.factDate>bi.intentDate or bi.factDate is NULL)");
			sb.append(" and bi.fundType in ('principalRepayment','serviceMoney','consultationMoney','loanInterest')");
			sb.append(" GROUP BY months.content");
		}
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			addScalar("sumA", Hibernate.LONG).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findSomeInfoWealth(PageBean<Highchart> pageBean) {
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
		sb.append(" SUM(CASE WHEN pb.keystr='mmproduce' AND pb.state in(2,7,8,10) THEN 1 ELSE 0 END ) AS sumA,");
		sb.append(" SUM(CASE WHEN pb.keystr='mmproduce' AND pb.state in(2,7,8,10) THEN pb.buyMoney else 0 END) AS moneyA");
		sb.append(" from ");
		sb.append(" ( ");
		int months = 1;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		sb.append(" LEFT JOIN pl_managemoneyplan_buyinfo as pb ON months.content=DATE_FORMAT(pb.startinInterestTime,'%Y-%m') ");
		sb.append(" GROUP BY months.content ");
	
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("sumA", Hibernate.LONG).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findSomeFormalPerson(PageBean<Highchart> pageBean) {
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
		sb.append(" SUM(CASE WHEN ci.investId IS NOT NULL THEN 1 ELSE 0 END) AS sumA");
		sb.append(" from ");
		sb.append(" ( ");
		int months = 1;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		sb.append(" LEFT JOIN cs_investmentperson AS ci ON months.content = DATE_FORMAT(ci.createdate,'%Y-%m')");
		sb.append(" GROUP BY months.content ");
	
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("sumA", Hibernate.LONG).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findSomeIntentionPerson(PageBean<Highchart> pageBean) {
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
		sb.append(" SUM(CASE WHEN bp.perId IS NOT NULL THEN 1 ELSE 0 END) AS sumA,");
		sb.append(" SUM(CASE WHEN bp.prosperctiveType=1 THEN 1 ELSE 0 END) AS sumB");
		sb.append(" from ");
		sb.append(" ( ");
		int months = 1;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		sb.append(" LEFT JOIN bp_cust_prosperctive bp ON months.content = DATE_FORMAT(bp.createDate,'%Y-%m')");
		sb.append(" GROUP BY months.content ");
	
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("sumA", Hibernate.LONG).
			addScalar("sumB", Hibernate.LONG).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findSomeRanking(PageBean<Highchart> pageBean) {
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" pi.customerManagerName AS nameA,");
		sb.append(" pi.departMentName AS nameB,");
		sb.append(" SUM(pb.buyMoney/10000) AS moneyA ");
		sb.append(" FROM pl_managemoneyplan_buyinfo AS pb");
		sb.append(" LEFT JOIN pl_mm_order_info AS pi ON pi.orderId=pb.orderId");
		sb.append(" WHERE pb.keystr='mmproduce' AND pb.state in(2,7,8,10)");
		sb.append(" AND DATE_FORMAT(pb.startinInterestTime,'%Y-%m')=DATE_FORMAT(NOW(),'%Y-%m')");
		sb.append(" GROUP BY pi.customerManagerNameId");
		sb.append(" ORDER BY moneyA DESC");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("nameA", Hibernate.STRING).
			addScalar("nameB", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findSomeToChampion(PageBean<Highchart> pageBean) {
		StringBuffer sb=new StringBuffer(" select");
		sb.append(" tp.searchDate,tp.customerManagerName AS nameA,tp.departMentName AS nameB,tp.moneyA");
		sb.append(" FROM( ");
		sb.append(" SELECT months.content AS searchDate,pi.customerManagerName,pi.departMentName,");
		sb.append(" SUM(CASE WHEN pb.keystr = 'mmproduce' AND pb.state IN (2, 7, 8, 10) THEN pb.buyMoney/10000 ELSE 0 END) AS moneyA");
		sb.append(" FROM(");
		
		int months = 8;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		
		sb.append(" LEFT JOIN pl_managemoneyplan_buyinfo AS pb ON months.content = DATE_FORMAT(pb.startinInterestTime,'%Y-%m')");
		sb.append(" LEFT JOIN pl_mm_order_info AS pi ON pi.orderId=pb.orderId");
		sb.append(" WHERE pi.customerManagerName IS NOT NULL");
		sb.append(" GROUP BY pi.customerManagerNameId ORDER BY moneyA DESC");
		sb.append(" ) AS tp");
		sb.append(" GROUP BY tp.searchDate ORDER BY tp.searchDate DESC");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("nameA", Hibernate.STRING).
			addScalar("nameB", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findSomeEarlyRedemption(PageBean<Highchart> pageBean) {
		StringBuffer sb = new StringBuffer("SELECT ");
		sb.append(" pb.investPersonName AS nameA,");
		sb.append(" DATE_FORMAT(pr.createDate,'%Y-%m-%d') AS searchDate,");
		sb.append(" (case pr.checkStatus when 0 then '办理中' when 1 then '已通过' ELSE '已终止' END) as nameB");
		sb.append(" from pl_early_redemption AS pr");
		sb.append(" LEFT JOIN pl_managemoneyplan_buyinfo AS pb ON pb.orderId=pr.orderId");
		sb.append(" where pr.keystr='mmproduce' ORDER BY pr.createDate desc;");
		Session session = this.getSession();
		List<Highchart> list = session.createSQLQuery(sb.toString()).
			addScalar("nameA", Hibernate.STRING).
			addScalar("searchDate",Hibernate.STRING).
			addScalar("nameB", Hibernate.STRING).
		    setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findSomeYearStatistics(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String type = request.getParameter("type");
		
		StringBuffer sb=new StringBuffer(" select");
		sb.append(" DATE_FORMAT(CONCAT(months.content,'-','01'),'%m')AS searchDate,");
		sb.append(" SUM(CASE WHEN pb.keystr = 'mmproduce' AND pb.state IN (2, 7, 8, 10) THEN pb.buyMoney/10000 ELSE 0 END) AS moneyA");
		sb.append(" FROM(");
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy");
		String data = df.format(new Date())+"-01-01";
		if(type !=null && !"".equals(type)){
			data = type+"-01-01";
		}
		int months = 11;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+data+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		
		sb.append(" LEFT JOIN pl_managemoneyplan_buyinfo AS pb ON months.content = DATE_FORMAT(pb.startinInterestTime,'%Y-%m')");
		sb.append(" GROUP BY months.content;");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findSomeMonthTypeStatistics(PageBean<Highchart> pageBean) {
		StringBuffer sb = new StringBuffer("SELECT ");
		sb.append(" pt.name AS nameA,");
		sb.append(" SUM(CASE WHEN pb.keystr = 'mmproduce' AND pb.state IN (2, 7, 8, 10) " +
				"AND pm.keystr = 'mmproduce' AND pt.keystr = 'mmproduce' THEN (pb.buyMoney / 10000) else 0 END) AS moneyA");
		sb.append(" from( ");
		int months = 0;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		sb.append(" LEFT JOIN pl_managemoneyplan_buyinfo AS pb ON months.content=DATE_FORMAT(pb.startinInterestTime,'%Y-%m')");
		sb.append(" LEFT JOIN pl_managemoney_plan AS pm ON pm.mmplanId=pb.mmplanId");
		sb.append(" LEFT JOIN pl_managemoney_type AS pt ON pt.manageMoneyTypeId=pm.manageMoneyTypeId");
		sb.append(" GROUP BY pt.manageMoneyTypeId,months.content");
		Session session = this.getSession();
		List<Highchart> list = session.createSQLQuery(sb.toString()).
			addScalar("nameA", Hibernate.STRING).
			addScalar("moneyA",Hibernate.BIG_DECIMAL).
		    setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}


	@Override
	public void findSomeIntentionPerson(PageBean<Highchart> pageBean,
			Long creatorId) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
		sb.append(" SUM(CASE WHEN bp.perId IS NOT NULL");
		if(null!=creatorId && !"".equals(creatorId)){
		sb.append(" and bp.creatorId="+creatorId);	
		}
		sb.append("  THEN 1 ELSE 0 END) AS sumA");
		sb.append(" from ");
		sb.append(" ( ");
		int months = 1;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		sb.append(" LEFT JOIN bp_cust_prosperctive bp ON months.content = DATE_FORMAT(bp.createDate,'%Y-%m')");
		sb.append(" GROUP BY months.content ");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("sumA", Hibernate.LONG).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}
	@Override
	public void getRegUser(PageBean<Highchart> pageBean) {
		StringBuffer sb = new StringBuffer(" select  months.content as searchDate,  ");
								sb.append(" SUM(CASE WHEN bm.id is not null   THEN 1 ELSE 0 END ) AS sumA,");
								sb.append(" SUM(CASE WHEN bm.registrationDate is not null and bm.isCheckCard=1 THEN 1 else 0 END) AS sumB ");
								sb.append(" from  (  select DATE_FORMAT(date_add(NOW(), INTERVAL 0 MONTH),'%Y-%m') as content ");
								sb.append(" UNION  select DATE_FORMAT(date_add(NOW(), INTERVAL -1 MONTH),'%Y-%m') as content ) as months ");
								sb.append(" LEFT JOIN bp_cust_member as bm ON months.content=DATE_FORMAT(bm.registrationDate,'%Y-%m') ");
								sb.append(" GROUP BY months.content ");
		Session session = this.getSession();
		System.out.println(sb.toString());
		List<Highchart> list = session.createSQLQuery(sb.toString())
								.addScalar("searchDate",Hibernate.STRING)
								.addScalar("sumA", Hibernate.LONG)
								.addScalar("sumB", Hibernate.LONG)
								.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void getObAccountDeal(PageBean<Highchart> pageBean) {
		StringBuffer sb = new StringBuffer("  select  months.content as searchDate,  ");
						sb.append(" IFNULL(SUM(CASE WHEN ob.transferType=1 and ob.dealRecordStatus=2 THEN ob.incomMoney else 0 END),0) AS moneyA,");
						sb.append(" IFNULL(SUM(CASE WHEN ob.transferType=2 and ob.dealRecordStatus=2 THEN ob.payMoney else 0 END),0) AS moneyB,");
						sb.append(" IFNULL(SUM(CASE WHEN ob.transferType=4 and  (dealRecordStatus=2 or dealRecordStatus=7) THEN ob.payMoney else 0 END),0) AS moneyC");
						sb.append(" from  (  select DATE_FORMAT(date_add(NOW(), INTERVAL 0 MONTH),'%Y-%m') as content ");
						sb.append(" UNION  select DATE_FORMAT(date_add(NOW(), INTERVAL -1 MONTH),'%Y-%m') as content ) as months  ");
						sb.append(" LEFT JOIN ob_account_deal_info as ob ON months.content=DATE_FORMAT(ob.createDate,'%Y-%m') ");
						sb.append(" GROUP BY months.content ");
		Session session = this.getSession();
		List<Highchart> list = session.createSQLQuery(sb.toString())
								.addScalar("searchDate",Hibernate.STRING)
								.addScalar("moneyA", Hibernate.BIG_DECIMAL)
								.addScalar("moneyB", Hibernate.BIG_DECIMAL)
								.addScalar("moneyC", Hibernate.BIG_DECIMAL)
								.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
	}

	@Override
	public void findLoanCustomer(PageBean<Highchart> pageBean, Long creatorId) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
		sb.append(" SUM(CASE WHEN ep.id IS NOT NULL ");
		if(null!=creatorId && !"".equals(creatorId)){
			sb.append(" and  ep.createrId="+creatorId);	
		}
		sb.append(" THEN 1 ELSE 0 END) AS sumA");
		sb.append(" from ");
		sb.append(" ( ");
		int months = 1;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		sb.append(" LEFT JOIN (select e.id, e.createdate,e.createrId from cs_enterprise e UNION ");
		sb.append(" select p.id,p.createdate,p.createrId from cs_person p) as ep");
		sb.append(" ON months.content = DATE_FORMAT(ep.createdate, '%Y-%m')");
		sb.append(" GROUP BY months.content ");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("sumA", Hibernate.LONG).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}
	@Override
	public void getPlearlyRedemptionNum(PageBean<Highchart> pageBean) {
		StringBuffer sb = new StringBuffer(" select SUM(CASE when pl.checkStatus=0 THEN 1 else 0 END ) as sumA,");
					 sb.append(" SUM(CASE when pl.checkStatus=0 THEN pl.earlyMoney else 0 END ) as moneyA,");			
					 sb.append(" SUM(CASE when pl.checkStatus=1 then 1 else 0 end) as sumB,");			
					 sb.append(" SUM(CASE when pl.checkStatus=1 THEN pl.earlyMoney else 0 END ) as moneyB,");			
					 sb.append(" SUM(CASE when pl.checkStatus=3 then 1 else 0 end) as sumC,");			
					 sb.append(" SUM(CASE when pl.checkStatus=3 THEN pl.earlyMoney else 0 END ) as moneyC,");			
					 sb.append(" SUM(CASE when 1=1 then 1 else 0 end) as sumD,");			
					 sb.append(" SUM(CASE when 1=1 THEN pl.earlyMoney else 0 END ) as moneyD");			
					 sb.append("  from pl_early_redemption as pl where (keystr='mmplan' or keystr='UPlan') and date_format(pl.createDate,'%Y-%m')=date_format(NOW(),'%Y-%m')");	
		Session session = this.getSession();
		List<Highchart> list = session.createSQLQuery(sb.toString())
								.addScalar("sumA",Hibernate.LONG)
								.addScalar("sumB",Hibernate.LONG)
								.addScalar("sumC",Hibernate.LONG)
								.addScalar("sumD",Hibernate.LONG)
								.addScalar("moneyA", Hibernate.BIG_DECIMAL)
								.addScalar("moneyB", Hibernate.BIG_DECIMAL)
								.addScalar("moneyC", Hibernate.BIG_DECIMAL)
								.addScalar("moneyD", Hibernate.BIG_DECIMAL)
								.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void getPlearlyRedemptionList(PageBean<Highchart> pageBean) {
		StringBuffer sb = new StringBuffer(" select plan.mmName as nameA,DATE_FORMAT(early.createDate,'%Y-%m-%d') as remarkA from pl_early_redemption as early ");
					 sb.append(" left join pl_managemoneyplan_buyinfo as plan on early.orderId=plan.orderId where early.checkStatus=0 ORDER BY early.createDate LIMIT 7");
		Session session = this.getSession();
		List<Highchart> list = session.createSQLQuery(sb.toString())
								.addScalar("nameA",Hibernate.STRING)
								.addScalar("remarkA",Hibernate.STRING)
								.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
	}
	@Override
	public void findLoanMoneyAmount(PageBean<Highchart> pageBean,
			Long creatorId, Short projectStatus) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" months.content as searchDate, ");
	//	sb.append(" SUM(IFNULL(bp.ownJointMoney,0)) AS moneyA,");
		sb.append(" SUM(CASE WHEN bp.flag=0");
		if(null!=creatorId && !"".equals(creatorId)){
			sb.append(" and bp.appUserId="+creatorId);
		}
		if(null!=projectStatus && !"".equals(projectStatus)){
			sb.append(" and bp.projectStatus="+projectStatus);	
			}
		sb.append(" THEN IFNULL(bp.ownJointMoney,0)");
		sb.append(" ELSE 0 END ) AS moneyA,");
		sb.append(" SUM(CASE WHEN bp.flag=0");
		if(null!=creatorId && !"".equals(creatorId)){
			sb.append(" and bp.appUserId="+creatorId);
		}
		if(null!=projectStatus && !"".equals(projectStatus)){
			sb.append(" and bp.projectStatus="+projectStatus);	
			}
		sb.append(" THEN 1 ELSE 0 END ) AS sumA");
		sb.append(" from ");
		sb.append(" ( ");
		int months = 1;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		sb.append(" LEFT JOIN bp_fund_project bp  ");
		sb.append(" ON months.content = DATE_FORMAT(bp.startDate, '%Y-%m')");
		sb.append(" GROUP BY months.content ");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("sumA", Hibernate.LONG).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findLoanOverdueStatistics(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		HttpServletRequest request=pageBean.getRequest();
		String type = request.getParameter("type");
		
		StringBuffer sb=new StringBuffer(" select");
		sb.append(" DATE_FORMAT(CONCAT(months.content,'-','01'),'%m')AS searchDate,");
		sb.append(" SUM(CASE WHEN  b.notMoney>0  and p.flag = 0 AND b.incomeMoney > 0  AND (b.factDate is null and b.intentDate < NOW())  and b.isValid=0 and b.isCheck=0");
		sb.append(" THEN b.incomeMoney / 10000 ELSE 0 END 	) AS moneyA,");
		sb.append(" SUM(CASE WHEN  b.aftermoney>0  and p.flag = 0 AND b.incomeMoney > 0  AND (b.factDate is not null and b.factDate<b.intentDate )  and b.isValid=0 and b.isCheck=0");
		sb.append(" THEN b.incomeMoney / 10000 ELSE 0 END 	) AS moneyC,");
		sb.append(" SUM(CASE WHEN b.afterMoney>0  and p.flag = 0 AND b.incomeMoney > 0  AND b.factDate is not NULL  and b.isValid=0 and b.isCheck=0");
		sb.append(" THEN b.afterMoney / 10000 ELSE 0 END 	) AS moneyB");
		sb.append(" FROM(");
		SimpleDateFormat df=new SimpleDateFormat("yyyy");
		String data = df.format(new Date())+"-01-01";
		if(type !=null && !"".equals(type)){
			data = type+"-01-01";
		}
		int months = 11;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+data+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		
		sb.append(" LEFT JOIN sl_fund_intent b  ON months.content = DATE_FORMAT(b.intentDate, '%Y-%m')");
		sb.append(" LEFT JOIN bp_fund_project p ON b.preceptId = p.id");
		sb.append(" GROUP BY months.content;");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			addScalar("moneyC", Hibernate.BIG_DECIMAL).
			addScalar("moneyB", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
	}

	@Override
	public void findLoanYearStatistics(PageBean<Highchart> pageBean,
			Long userId, Short projectStatus) {
		// TODO Auto-generated method stub
		HttpServletRequest request=pageBean.getRequest();
		String type = request.getParameter("type");
		
		StringBuffer sb=new StringBuffer(" select");
		sb.append(" DATE_FORMAT(CONCAT(months.content,'-','01'),'%m')AS searchDate,");
		sb.append(" SUM(CASE WHEN  b.flag=0 ");
		if(null!=userId && !"".equals(userId)){
			sb.append(" AND  b.appUserId="+userId);
		}
		if(null!=projectStatus && !"".equals(projectStatus)){
			sb.append(" AND  b.projectStatus="+projectStatus);
		}
		sb.append(" THEN b.ownJointMoney / 10000 ELSE 0 END ) AS moneyA");
		sb.append(" FROM(");
		SimpleDateFormat df=new SimpleDateFormat("yyyy");
		String data = df.format(new Date())+"-01-01";
		if(type !=null && !"".equals(type)){
			data = type+"-01-01";
		}
		int months = 11;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+data+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		
		sb.append(" LEFT JOIN bp_fund_project AS b ON months.content = DATE_FORMAT(b.startDate,'%Y-%m')");
		sb.append(" GROUP BY months.content;");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findLoneRanking(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" SUM(b.ownJointMoney / 10000) AS moneyA,");
		sb.append(" a.fullname as nameA");
		sb.append(" FROM ");
		sb.append(" bp_fund_project b");
		sb.append(" LEFT JOIN app_user a ON b.appUserId = a.userId");
		sb.append(" WHERE");
		sb.append(" b.flag = 0");
		sb.append(" AND DATE_FORMAT(b.startDate, '%Y-%m') = DATE_FORMAT(NOW(), '%Y-%m')");
		sb.append(" AND b.projectStatus = 1");
		sb.append(" GROUP BY b.appUserId");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("nameA", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
	}

	@Override
	public void findLoneRemind(PageBean<Highchart> pageBean, Long userId,
			Short projectStatus) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select");
		sb.append("  p.ownJointMoney/10000 moneyA,");
		sb.append(" DATE_FORMAT(p.intentDate,'%Y-%m-%d') AS searchDate,");
		sb.append(" getEnOrPer (p.oppositeType,p.oppositeID) AS nameA");
		sb.append(" FROM");
		sb.append("  bp_fund_project p ");
		sb.append(" WHERE");
		sb.append(" p.flag = 0");
		
	    if(null!=userId && !"".equals(userId)){
	    	sb.append(" and p.appUserId="+userId);
	    }
	    if(null!=projectStatus && !"".equals(projectStatus)){
	    	sb.append(" and p.projectStatus="+projectStatus);
	    }
		sb.append(" ORDER BY p.intentDate");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("nameA", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findOverdueMoney(PageBean<Highchart> pageBean, Long userId) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" IFNULL(SUM(b.notMoney),0) moneyA,");
		sb.append(" IFNULL(SUM(b.afterMoney),0) moneyB,");
		sb.append(" IFNULL(SUM(b.incomeMoney),0) moneyC");
		sb.append(" FROM");
		sb.append(" sl_fund_intent b");
		sb.append(" LEFT JOIN bp_fund_project p ON b.preceptId = p.id");
		sb.append(" WHERE");
		sb.append(" p.flag = 0");
		sb.append(" AND b.fundType <> 'principalLending'");
		sb.append(" AND p.intentDate < NOW()");
		sb.append(" and b.isCheck=0 and b.isValid=0");
		
		if(null!=userId && !"".equals(userId)){
			sb.append(" AND p.appUserId ="+userId);
		}
		System.out.println(sb.toString());
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			addScalar("moneyB", Hibernate.BIG_DECIMAL).
			addScalar("moneyC", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
	}

	@Override
	public void findOverdueRemind(PageBean<Highchart> pageBean, Long userId) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select");
		sb.append(" SUM(b.notMoney) / 10000 moneyA,");
		sb.append(" DATE_FORMAT(b.intentDate,'%Y-%m-%d') AS searchDate,");
		sb.append(" getEnOrPer (p.oppositeType,p.oppositeID) AS nameA");
		sb.append(" FROM");
		sb.append(" sl_fund_intent b");
		sb.append(" LEFT JOIN bp_fund_project p ON b.preceptId = p.id");
		sb.append(" WHERE");
		sb.append(" p.flag = 0");
		sb.append(" and b.notMoney>0");
		sb.append(" and b.incomeMoney>0");
		sb.append(" AND b.fundType <> 'principalLending'");
		sb.append(" AND b.intentDate < NOW()");
		sb.append(" and b.notMoney>0");
		sb.append(" AND b.isCheck=0");
		sb.append(" AND b.isValid=0");
	    if(null!=userId && !"".equals(userId)){
	    	sb.append(" and p.appUserId="+userId);
	    }
	    sb.append(" group by b.preceptId,b.intentDate");
		sb.append(" ORDER BY b.intentDate DESC");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("nameA", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findOwnRanking(PageBean<Highchart> pageBean, Long userId,
			String type) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select");
		sb.append(" (COUNT(*) + 1) as rankingA");
		sb.append(" FROM");
		sb.append(" (SELECT IFNULL(SUM(b.ownJointMoney / 10000),0) AS moneyA ");
		sb.append(" FROM bp_fund_project b");
		sb.append(" LEFT JOIN app_user a ON b.appUserId = a.userId");
		sb.append(" WHERE b.flag = 0  AND DATE_FORMAT(b.startDate, '"+type+"') = DATE_FORMAT(NOW(), '"+type+"')");
		sb.append(" AND b.projectStatus = 1 GROUP BY  b.appUserId ORDER BY moneyA DESC 	) AS mm");
		sb.append(" WHERE");
		sb.append(" mm.moneyA > (");
		sb.append(" SELECT  IFNULL(SUM(b.ownJointMoney / 10000),0) AS money");
		sb.append(" FROM bp_fund_project b");
		sb.append(" LEFT JOIN app_user a ON b.appUserId = a.userId");
		sb.append(" WHERE b.flag = 0 	AND b.appUserId = "+userId);
		sb.append(" AND DATE_FORMAT(b.startDate, '"+type+"') = DATE_FORMAT(NOW(), '"+type+"')");
		sb.append(" AND b.projectStatus = 1");
		sb.append(" )");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("rankingA", Hibernate.INTEGER).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findRiskControl(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" SUM(CASE WHEN gs.superviseManageOpinion=421  THEN IFNULL(b.ownJointMoney,0) ELSE 0 	END ) AS moneyA,");
		sb.append(" SUM(CASE WHEN gs.superviseManageOpinion=423  THEN IFNULL(b.ownJointMoney,0) ELSE 0 	END ) AS moneyB,");
		sb.append(" SUM(CASE WHEN gs.superviseManageOpinion=424  THEN IFNULL(b.ownJointMoney,0) ELSE 0 	END ) AS moneyC,");
		sb.append(" SUM(CASE WHEN gs.superviseManageOpinion=425  THEN IFNULL(b.ownJointMoney,0) ELSE 0 	END ) AS moneyD,");
		sb.append(" SUM(CASE WHEN gs.superviseManageOpinion is null  THEN IFNULL(b.ownJointMoney,0) ELSE 0 	END ) AS moneyE");
		sb.append(" FROM");
		sb.append(" bp_fund_project b");
		sb.append(" LEFT JOIN global_supervisemanage AS gs ON gs.projectId = b.id");
		sb.append(" WHERE");
		sb.append(" b.flag = 0");
		sb.append(" AND b.ownJointMoney > 0");
		sb.append(" AND b.projectStatus IN (1, 4, 6, 5)");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			addScalar("moneyB", Hibernate.BIG_DECIMAL).
			addScalar("moneyC", Hibernate.BIG_DECIMAL).
			addScalar("moneyD", Hibernate.BIG_DECIMAL).
			addScalar("moneyE", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
	}
	/**
	 * 散标违约情况统计表
	 * @param pageBean
	 */
	@Override
	public void bidExceptionMap(PageBean<HighchartModel> pageBean){
		HttpServletRequest request=pageBean.getRequest();
		String dateType = request.getParameter("type");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String firstDate=sd.format(new Date());
		String endDate=sd.format(new Date());
		Map<String,String> map=new HashMap<String,String>();
		 if(dateType!=null&&"2".equals(dateType)){
			map=DateUtil.getDate(new Date(),"month");
		}else if(dateType!=null&&"3".equals(dateType)){
			map=DateUtil.getDate(new Date(),"quarter");
		}else if(dateType!=null&&"4".equals(dateType)){
			map=DateUtil.getDate(new Date(),"year");
	    }
		 
		if(!map.isEmpty()){
			firstDate=map.get("start");
			endDate=map.get("end");
		}
		
		HighchartModel model=new HighchartModel();
		ArrayList xAxisList=new ArrayList();
		xAxisList.add("P2P");
		xAxisList.add("C2P");
		xAxisList.add("PA2P");
		xAxisList.add("CA2P");
		model.setxAxis(xAxisList);
		//series数值赋值方法
		List<Series> seriesList=new ArrayList<Series> ();
		
		//计算逾期款项(计算逾期的柱子)
		//如果是柱状图，必须保证每个注状的图例存在
		 Series series=new Series();
		 series.setName("逾期");
		 
		StringBuffer sb= new StringBuffer(" SELECT plan.proType as nameA, sum(case when plan.proType = 'P_Dir' and p.incomeMoney is not null then p.incomeMoney else 0 end ) as moneyA,");
							sb.append("  sum(case when plan.proType = 'B_Dir' and p.incomeMoney is not null then p.incomeMoney else 0 end ) as moneyB,");
							sb.append(" sum(case when plan.proType = 'P_Or' and p.incomeMoney is not null then p.incomeMoney else 0 end ) as moneyC,");
							sb.append(" sum(case when plan.proType = 'B_Or' and p.incomeMoney is not null then p.incomeMoney else 0 end ) as moneyD ");
							sb.append(" FROM bp_fund_intent AS p");
							sb.append(" LEFT JOIN pl_bid_plan AS plan ON (p.bidPlanId = plan.bidId)");
							sb.append(" WHERE p.isCheck = 0 AND p.isValid = 0 AND p.fundType != 'principalLending' ");
							sb.append(" AND DATE_FORMAT(p.intentDate,'%Y-%m-%d') >= '"+firstDate+"' AND DATE_FORMAT(p.intentDate,'%Y-%m-%d')  <= '"+endDate+"'");
							sb.append(" AND ((p.factDate IS NULL AND p.intentDate < NOW()) OR (p.factDate IS NOT NULL AND p.factDate > p.intentDate))");
							sb.append(" GROUP BY plan.proType");
		Session session = this.getSession();
		System.out.println("==========="+sb.toString());
		List<Highchart> list = session.createSQLQuery(sb.toString())
							.addScalar("nameA",Hibernate.STRING)
							.addScalar("moneyA", Hibernate.BIG_DECIMAL)
							.addScalar("moneyB", Hibernate.BIG_DECIMAL)
							.addScalar("moneyC", Hibernate.BIG_DECIMAL)
							.addScalar("moneyD", Hibernate.BIG_DECIMAL)
							.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();					
		ArrayList data=new ArrayList();
		if(list!=null&&list.size()>0){
			for(Highchart temp:list){
				data.add(temp.getMoneyA());
				data.add(temp.getMoneyB());
				data.add(temp.getMoneyC());
				data.add(temp.getMoneyD());
			}
		}else{
			data.add(0);
			data.add(0);
			data.add(0);
			data.add(0);
		}
		series.setData(data);
		seriesList.add(series);
		
		//计算代偿款项的柱子
		Series series1=new Series();
		series1.setName("代偿");
		StringBuffer sb1= new StringBuffer(" SELECT plan.proType as nameA,");
		sb1.append("  sum(case when plan.proType = 'P_Dir' then IFNULL(p.compensatoryMoney,0) else 0 end ) as moneyA, ");
		sb1.append(" sum(case when plan.proType = 'B_Dir' then IFNULL(p.compensatoryMoney,0) else 0 end ) AS moneyB, ");
		sb1.append(" sum(case when plan.proType = 'P_Or'  then IFNULL(p.compensatoryMoney,0) else 0 end ) AS moneyC, ");
		sb1.append(" sum(case when plan.proType = 'B_Or'  then IFNULL(p.compensatoryMoney,0) else 0 end ) AS moneyD ");
		sb1.append(" FROM pl_bid_compensatory AS p ");
		sb1.append(" LEFT JOIN pl_bid_plan AS plan ON (p.planId = plan.bidId )");
		sb1.append(" WHERE DATE_FORMAT(p.compensatoryDate,'%Y-%m-%d') >= '"+firstDate+"' AND DATE_FORMAT(p.compensatoryDate,'%Y-%m-%d') <= '"+endDate+"'");
		sb1.append(" GROUP BY plan.proType");
		System.out.println(sb1.toString());
		List<Highchart> list1 = session.createSQLQuery(sb1.toString())
				.addScalar("nameA",Hibernate.STRING)
				.addScalar("moneyA", Hibernate.BIG_DECIMAL)
				.addScalar("moneyB", Hibernate.BIG_DECIMAL)
				.addScalar("moneyC", Hibernate.BIG_DECIMAL)
				.addScalar("moneyD", Hibernate.BIG_DECIMAL)
				.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();					
		ArrayList data1=new ArrayList();
		if(list1!=null&&list1.size()>0){
			for(Highchart temp1:list1){
				data1.add(temp1.getMoneyA());
				data1.add(temp1.getMoneyB());
				data1.add(temp1.getMoneyC());
				data1.add(temp1.getMoneyD());
			}
			
		}else{
			data1.add(0);
			data1.add(0);
			data1.add(0);
			data1.add(0);
		}
		series1.setData(data1);
		seriesList.add(series1);
		
		
		//计算代偿款项的柱子
		Series series2=new Series();
		series2.setName("代偿未追偿");
		StringBuffer sb2= new StringBuffer(" SELECT plan.proType as nameA,");
		sb2.append("-sum(case when plan.proType = 'P_Dir' then (IFNULL(p.compensatoryMoney,0)+IFNULL(p.punishMoney,0)-IFNULL(p.backPunishMoney,0)-IFNULL(p.backCompensatoryMoney,0)-IFNULL(p.plateMoney,0)) else 0 end ) as moneyA,");
		sb2.append("-sum(case when plan.proType = 'B_Dir' then (IFNULL(p.compensatoryMoney,0)+IFNULL(p.punishMoney,0)-IFNULL(p.backPunishMoney,0)-IFNULL(p.backCompensatoryMoney,0)-IFNULL(p.plateMoney,0)) else 0 end ) as moneyB,");
		sb2.append("-sum(case when plan.proType = 'P_Or' then (IFNULL(p.compensatoryMoney,0)+IFNULL(p.punishMoney,0)-IFNULL(p.backPunishMoney,0)-IFNULL(p.backCompensatoryMoney,0)-IFNULL(p.plateMoney,0)) else 0 end ) as moneyC,");
		sb2.append("-sum(case when plan.proType = 'B_Or' then (IFNULL(p.compensatoryMoney,0)+IFNULL(p.punishMoney,0)-IFNULL(p.backPunishMoney,0)-IFNULL(p.backCompensatoryMoney,0)-IFNULL(p.plateMoney,0)) else 0 end ) as moneyD");
		sb2.append(" FROM pl_bid_compensatory AS p ");
		sb2.append(" LEFT JOIN pl_bid_plan AS plan ON (p.planId = plan.bidId )");
		sb2.append(" WHERE p.compensatoryDate <= NOW()");
		sb2.append(" GROUP BY plan.proType");
		System.out.println("sb2.toString()=="+sb2.toString());
		List<Highchart> list2 = session.createSQLQuery(sb2.toString())
				.addScalar("nameA",Hibernate.STRING)
				.addScalar("moneyA", Hibernate.BIG_DECIMAL)
				.addScalar("moneyB", Hibernate.BIG_DECIMAL)
				.addScalar("moneyC", Hibernate.BIG_DECIMAL)
				.addScalar("moneyD", Hibernate.BIG_DECIMAL)
				.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();					
		ArrayList data2=new ArrayList();
		if(list2!=null&&list2.size()>0){
			for(Highchart temp2:list2){
				data2.add(temp2.getMoneyA());
				data2.add(temp2.getMoneyB());
				data2.add(temp2.getMoneyC());
				data2.add(temp2.getMoneyD());
			}
		}else{
			data2.add(0);
			data2.add(0);
			data2.add(0);
			data2.add(0);
		}
		series2.setData(data2);
		seriesList.add(series2);
		
		//计算代偿款项的柱子
		Series series3=new Series();
		series3.setName("代偿收回");
		StringBuffer sb3= new StringBuffer(" SELECT plan.proType as nameA,");
		sb3.append("sum(case when plan.proType = 'P_Dir' then (IFNULL(p.backCompensatoryMoney,0)+IFNULL(p.backPunishMoney,0)) else 0 end) as moneyA,");
		sb3.append("sum(case when plan.proType = 'B_Dir' then (IFNULL(p.backCompensatoryMoney,0)+IFNULL(p.backPunishMoney,0)) else 0 end) as moneyB,");
		sb3.append("sum(case when plan.proType = 'P_Or' then (IFNULL(p.backCompensatoryMoney,0)+IFNULL(p.backPunishMoney,0)) else 0 end) as moneyC,");
		sb3.append("sum(case when plan.proType = 'B_Or' then (IFNULL(p.backCompensatoryMoney,0)+IFNULL(p.backPunishMoney,0)) else 0 end) as moneyD");
		sb3.append(" FROM pl_bid_compensatory_flow AS p ");
		sb3.append(" LEFT JOIN pl_bid_compensatory AS pc ON (p.compensatoryId = pc.id)");
		sb3.append(" LEFT JOIN pl_bid_plan AS plan ON (pc.planId = plan.bidId)");
		sb3.append(" where p.backStatus=2 and DATE_FORMAT(p.backDate,'%Y-%m-%d')>='"+firstDate+"' and DATE_FORMAT(p.backDate,'%Y-%m-%d')<='"+endDate+"' ");
		sb3.append(" GROUP BY plan.proType");
		System.out.println(sb3.toString());
		List<Highchart> list3 = session.createSQLQuery(sb3.toString())
				.addScalar("nameA",Hibernate.STRING)
				.addScalar("moneyA", Hibernate.BIG_DECIMAL)
				.addScalar("moneyB", Hibernate.BIG_DECIMAL)
				.addScalar("moneyC", Hibernate.BIG_DECIMAL)
				.addScalar("moneyD", Hibernate.BIG_DECIMAL)
				.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();					
		ArrayList data3=new ArrayList();
		if(list3!=null&&list3.size()>0){
			for(Highchart temp3:list3){
				data3.add(temp3.getMoneyA());
				data3.add(temp3.getMoneyB());
				data3.add(temp3.getMoneyC());
				data3.add(temp3.getMoneyD());
			}
		}else{
			data3.add(0);
			data3.add(0);
			data3.add(0);
			data3.add(0);
		}
		series3.setData(data3);
		seriesList.add(series3);
		/**
		 * 将结果集放入pageBean中
		 */
		model.setSeries(seriesList);
		pageBean.setModel(model);
		releaseSession(session);
	}
	/**
	 * 散标销售统计图
	 * @param pageBean
	 */
	@Override
	public void bidSaleStatistics(PageBean<HighchartModel> pageBean){
		HttpServletRequest request=pageBean.getRequest();
		String dateType = request.getParameter("type");
		String start_searchDate=request.getParameter("start_searchDate");
		String end_searchDate=request.getParameter("end_searchDate");
		
		StringBuffer sb=new StringBuffer("select months.content as searchDate, ");
		sb.append(" SUM(case when plan.proType='P_Dir' then IFNULL(p.userMoney,0) else 0 end ) AS moneyA,");
		sb.append(" SUM(case when plan.proType='B_Dir' then IFNULL(p.userMoney,0) else 0 end ) AS moneyB,");
		sb.append(" SUM(case when plan.proType='P_Or' then IFNULL(p.userMoney,0) else 0 end ) AS moneyC,");
		sb.append(" SUM(case when plan.proType='B_Or' then IFNULL(p.userMoney,0) else 0 end ) AS moneyD");
		sb.append(" from ");
		sb.append(" ( ");
		//起日不为null，止日不为null 查询start_searchDate到end_searchDate
		if(dateType != null && "1".equals(dateType)){//日
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int days = DateUtil.getDaysBetweenDate(start_searchDate, end_searchDate);
				for(int i=0;i<=days;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" DAY),'%Y-%m-%d') as content");
					if(i!=days){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12天
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" DAY),'%Y-%m-%d') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" left join pl_bid_info as p ON(months.content = DATE_FORMAT(p.bidtime, '%Y-%m-%d') and p.state in (1,2))");
			
		}else if( dateType==null||( dateType != null && "2".equals(dateType))){//月
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=months;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
					if(i!=months){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12月
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" MONTH),'%Y-%m') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" left join pl_bid_info as p ON(months.content = DATE_FORMAT(p.bidtime, '%Y-%m') and p.state in (1,2))");
			
		}else if(dateType != null && "3".equals(dateType)){//季
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				int seasons = months / 3;
				for(int i=0;i<=seasons;i++){
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER)),'季度') as content");
					if(i!=seasons){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12季
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER)),'季度') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" left join pl_bid_info as p ON(months.content = CONCAT(DATE_FORMAT(p.bidtime, '%Y'),'年第',QUARTER (p.bidtime),'季度') and p.state in (1,2))");
			
		}else if(dateType != null && "4".equals(dateType)){//年
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int years = DateUtil.getYearSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=years;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" YEAR),'%Y') as content");
					if(i!=years){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12年
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" YEAR),'%Y') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" left join pl_bid_info as p ON(months.content = DATE_FORMAT(p.bidtime, '%Y') and p.state in (1,2))");
			
			
		}
		sb.append(" left join pl_bid_plan as plan on(p.bidId=plan.bidId)");
		sb.append(" GROUP BY months.content ");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).//p2p
			addScalar("moneyB", Hibernate.BIG_DECIMAL).//c2p
			addScalar("moneyC", Hibernate.BIG_DECIMAL).//pa2p
			addScalar("moneyD", Hibernate.BIG_DECIMAL).//ca2p
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		releaseSession(session);
		
		HighchartModel model=new HighchartModel();
		ArrayList xAxisList=new ArrayList();
		//series数值赋值方法
		ArrayList seriesList=new ArrayList();
		//p2p
		Series series=new Series();
		series.setName("p2p");
		ArrayList data=new ArrayList();
		//c2p
		Series series1=new Series();
		series1.setName("c2p");
		ArrayList data1=new ArrayList();
		//pa2p
		Series series2=new Series();
		series2.setName("pa2p");
		ArrayList data2=new ArrayList();
		//ca2p
		Series series3=new Series();
		series3.setName("ca2p");
		ArrayList data3=new ArrayList();
		
		for(int i=0;i<list.size();i++){
			xAxisList.add(list.get(i).getSearchDate());
			
			data.add(list.get(i).getMoneyA());
			data1.add(list.get(i).getMoneyB());
			data2.add(list.get(i).getMoneyC());
			data3.add(list.get(i).getMoneyD());
			
		}
		series.setData(data);
		series1.setData(data1);
		series2.setData(data2);
		series3.setData(data3);
		
		seriesList.add(series);
		seriesList.add(series1);
		seriesList.add(series2);
		seriesList.add(series3);
		
		model.setxAxis(xAxisList);
		model.setSeries(seriesList);
		
		pageBean.setModel(model);
		
	}
	
	/**
	 * 散标放款后类型所占总放款金额比列
	 * @param pageBean
	 */
	@Override
	public void bidTypeProportion(PageBean<HighchartModel> pageBean){
		/*HttpServletRequest request=pageBean.getRequest();
		String dateType = request.getParameter("type");//查询条件
*/		
		
		StringBuffer sb=new StringBuffer("SELECT p.proKeepType as searchDate,  sum(p.bidMoney) moneyA,");
		sb.append("  (select sum(pl.bidMoney) from  pl_bid_plan AS pl WHERE pl.state IN (7, 10)) as  moneyB ");
		sb.append(" FROM pl_bid_plan AS p");
		sb.append(" WHERE p.state IN (7, 10) ");
		sb.append(" GROUP BY p.proKeepType ");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).//p2p
			addScalar("moneyB", Hibernate.BIG_DECIMAL).//c2p
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		releaseSession(session);
		
		HighchartModel model=new HighchartModel();
		ArrayList xAxisList=new ArrayList();
		//series数值赋值方法
		List<Series> seriesList=new ArrayList<Series> ();
		//p2p
		Series series=new Series();
		series.setName("散标类型占比");
		ArrayList data=new ArrayList();
		
		BigDecimal persent=new BigDecimal(100);
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				SeriesData d=new SeriesData();
				d.setName(list.get(i).getSearchDate());
				if(i!=(list.size()-1)){
					BigDecimal sigle=new BigDecimal(0);
					sigle=list.get(i).getMoneyA().divide(list.get(i).getMoneyB(), 2,BigDecimal.ROUND_HALF_UP);
					d.setY(sigle);
					persent=persent.subtract(sigle);
				}else{
					d.setY(persent);
					d.setSliced(true);
				}
				data.add(d);
			}
		}
		
		series.setData(data);
		seriesList.add(series);
		
		
		model.setSeries(seriesList);
		pageBean.setModel(model);
	}
	
    /**
     * 散标桌面(线上用户的充值取现的图表数据)
     */
	@Override
	public void onlineRechargeWithDrawChart(PageBean<HighchartModel> pageBean) {

		HttpServletRequest request=pageBean.getRequest();
		String dateType = request.getParameter("type");//查询条件
		String start_searchDate=request.getParameter("start_searchDate");
		String end_searchDate=request.getParameter("end_searchDate");
		
		StringBuffer sb=new StringBuffer("select months.content AS searchDate, ");
		sb.append(" sum(ifnull(p.incomMoney,0)) AS moneyA,");
		sb.append(" sum(ifnull(p.payMoney,0)) AS moneyB ");
		sb.append(" from ");
		sb.append(" ( ");
		//起日不为null，止日不为null 查询start_searchDate到end_searchDate
		if(dateType != null && "1".equals(dateType)){//日
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int days = DateUtil.getDaysBetweenDate(start_searchDate, end_searchDate);
				for(int i=0;i<=days;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" DAY),'%Y-%m-%d') as content");
					if(i!=days){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12天
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" DAY),'%Y-%m-%d') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" left join ob_account_deal_info as p ON(months.content = DATE_FORMAT(p.transferDate, '%Y-%m-%d') and AND p.investPersonType = 0 AND p.dealRecordStatus = 2 AND p.transferType IN (1, 2))");
			
		}else if( dateType==null||( dateType != null && "2".equals(dateType))){//月
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=months;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
					if(i!=months){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12月
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" MONTH),'%Y-%m') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" left join ob_account_deal_info as p ON(months.content = DATE_FORMAT(p.transferDate, '%Y-%m') AND p.investPersonType = 0 AND p.dealRecordStatus = 2 AND p.transferType IN (1, 2))");
			
		}else if(dateType != null && "3".equals(dateType)){//季
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int months = DateUtil.getMonthSpace(start_searchDate, end_searchDate);
				int seasons = months / 3;
				for(int i=0;i<=seasons;i++){
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+start_searchDate+"', INTERVAL -"+i+" QUARTER)),'季度') as content");
					if(i!=seasons){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12季
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER)),'季度') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" left join ob_account_deal_info as p ON(months.content = CONCAT(DATE_FORMAT(p.transferDate, '%Y'),'年第',QUARTER (p.transferDate),'季度') and p.transferType in (1,2))");
			
		}else if(dateType != null && "4".equals(dateType)){//年
			if(null != start_searchDate && !"".equals(start_searchDate) && null != end_searchDate && !"".equals(end_searchDate)){
				int years = DateUtil.getYearSpace(start_searchDate, end_searchDate);
				for(int i=0;i<=years;i++){
					sb.append(" select DATE_FORMAT(date_add('"+start_searchDate+"', INTERVAL "+i+" YEAR),'%Y') as content");
					if(i!=years){
						sb.append(" UNION ");
					}
				}
			}else{
				for(int i=0;i<=11;i++){
					//查询出前12年
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" YEAR),'%Y') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
			}
			sb.append(" ) as months ");
			sb.append(" left join ob_account_deal_info as p ON(months.content = DATE_FORMAT(p.transferDate, '%Y') AND p.investPersonType = 0 AND p.dealRecordStatus = 2 AND p.transferType IN (1, 2))");
			
			
		}
		sb.append(" GROUP BY months.content ");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).//充值
			addScalar("moneyB", Hibernate.BIG_DECIMAL).//取现
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		releaseSession(session);
		
		HighchartModel model=new HighchartModel();
		ArrayList xAxisList=new ArrayList();
		//series数值赋值方法
		List<Series> seriesList=new ArrayList<Series> ();
		//充值
		Series series=new Series();
		series.setName("充值");
		ArrayList data=new ArrayList();
		//取现
		Series series1=new Series();
		series1.setName("取现");
		ArrayList data1=new ArrayList();
		
		for(int i=0;i<list.size();i++){
			
			xAxisList.add(list.get(i).getSearchDate());
			
			data.add(list.get(i).getMoneyA());
			data1.add(list.get(i).getMoneyB());
			
		}
		xAxisList.add("");
		data.add(null);
		data1.add(null);
		
		series.setData(data);
		series1.setData(data1);
		
		seriesList.add(series);
		seriesList.add(series1);
		
		model.setxAxis(xAxisList);
		model.setSeries(seriesList);
		
		pageBean.setModel(model);
	}
	
	@Override
	public void findFinancialLoanMoney(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select ");
	    sb.append(" months.content AS searchDate,");
	    sb.append(" SUM(CASE WHEN bp.isValid = 0 AND bp.isCheck = 0 THEN IFNULL(bp.incomeMoney, 0)  ELSE 0 END ) AS moneyA,");
	    sb.append(" SUM(CASE WHEN bp.isValid = 0 AND bp.isCheck = 0 and bp.afterMoney > 0 and bp.incomeMoney>0 THEN IFNULL(bp.afterMoney, 0)  ELSE 0 END ) AS moneyB");
		sb.append(" from ");
		sb.append(" ( ");
		int months = 1;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL "+-i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		sb.append(" LEFT JOIN sl_fund_intent bp  ");
		sb.append(" ON months.content = DATE_FORMAT(bp.intentDate, '%Y-%m')");
		sb.append(" GROUP BY months.content ");
		System.out.println(sb.toString());
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			addScalar("moneyB", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findFinancialNotMoney(PageBean<Highchart> pageBean,
			String startDate, String endDate) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" IFNULL(SUM(s.notMoney),0) AS moneyA");
		sb.append(" FROM");
		sb.append(" sl_fund_intent s");
		sb.append(" WHERE");
		sb.append(" s.isValid = 0");
		sb.append(" AND s.isCheck = 0");
		sb.append(" AND s.incomeMoney > 0");
		sb.append(" AND s.notMoney > 0");
		if(null!=startDate && !"".equals(startDate)){
			sb.append(" AND s.intentDate >='"+startDate+"'");
		}
		if(null!=endDate && !"".equals(endDate)){
			sb.append(" AND s.intentDate <='"+endDate+"'");
		}
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
	}

	@Override
	public void findFundQlide(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer(" select");
		sb.append(" DATE_FORMAT(q.factDate,'%Y-%m-%d') AS searchDate,");
		sb.append(" (CASE WHEN q.isCash = 'cash' THEN '现金' ELSE '银行' END ) AS nameA,");
		sb.append(" (CASE WHEN q.incomeMoney > 0 THEN '收入' ELSE '支出' END ) AS nameB,");
		sb.append(" q.notMoney/10000 AS moneyA");
		sb.append(" FROM");
		sb.append(" sl_fund_qlide q where q.notMoney>0");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("nameA", Hibernate.STRING).
			addScalar("nameB", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void findIncomePayStatistics(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		HttpServletRequest request=pageBean.getRequest();
		String type = request.getParameter("type");
		
		StringBuffer sb=new StringBuffer(" select");
		sb.append(" DATE_FORMAT(CONCAT(months.content,'-','01'),'%m')AS searchDate,");
        sb.append(" SUM( CASE WHEN b.afterMoney> 0 AND  b.factDate is not null AND b.incomeMoney > 0 AND b.isValid = 0 ");
        sb.append(" AND b.isCheck = 0 THEN b.afterMoney / 10000 ELSE 0 END ) AS moneyA,");
        sb.append(" SUM( CASE WHEN b.afterMoney> 0 AND  b.factDate is not null AND b.payMoney > 0 AND b.isValid = 0 ");
        sb.append(" AND b.isCheck = 0 THEN b.afterMoney / 10000 ELSE 0 END ) AS moneyB");
		sb.append(" FROM(");
		SimpleDateFormat df=new SimpleDateFormat("yyyy");
		String data = df.format(new Date())+"-01-01";
		if(type !=null && !"".equals(type)){
			data = type+"-01-01";
		}
		int months = 11;
		for(int i=0;i<=months;i++){
			sb.append(" select DATE_FORMAT(date_add('"+data+"', INTERVAL "+i+" MONTH),'%Y-%m') as content");
			if(i!=months){
				sb.append(" UNION ");
			}
		}
		sb.append(" ) as months ");
		
		sb.append(" LEFT JOIN sl_fund_intent b ON months.content = DATE_FORMAT(b.factDate, '%Y-%m')");
		sb.append(" GROUP BY months.content;");
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			addScalar("moneyB", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
	}

	@Override
	public BigDecimal getPlanChildren(String date) {
		String sql = "select IFNULL(SUM(principalMoney),0) from pl_mm_obligatoryright_children " +
		 " where receiverP2PAccountNumber in(select pl.moneyReceiver from pl_managemoney_plan as pl WHERE   " +
		 " (pl.keystr='mmplan'or pl.keystr='UPlan') and pl.state!=-1 and pl.state!=3 " +
		 "and DATE_FORMAT(pl.buyStartTime,'%Y-%m')=? GROUP BY pl.moneyReceiver)";
			List list = this.getSession().createSQLQuery(sql).setParameter(0, date).list();
			BigDecimal moeny =new BigDecimal(list.get(0).toString());
			return moeny;
		}

	@Override
	public void getPlanFundAnalyze(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String dataState = request.getParameter("dataState");
		StringBuffer sb =new StringBuffer(" SELECT months.content as searchDate,IFNULL(SUM(sumMoney),0) as moneyA FROM(");
				for(int i=1;i<=12;i++){
					String num="";
					if(i<10){
						num="0"+i;
					}else{
						num=""+i;
					}
					//查询12个月
					sb.append(" SELECT DATE_FORMAT(NOW(),'%Y-"+num+"') AS content");
					if(i!=12){
						sb.append(" UNION ");
					}
				}
				sb.append(") AS months");
				sb.append(" LEFT JOIN pl_managemoney_plan AS plan ON months.content = DATE_FORMAT(plan.buyStartTime, '%Y-%m') ");
				sb.append(" and  (plan.keystr='mmplan'  or plan.keystr='UPlan') and plan.state!=-1 and plan.state!=3");
				sb.append(" GROUP BY	months.content");
		Session session = this.getSession();
		List<Highchart> list = session.createSQLQuery(sb.toString())
								.addScalar("searchDate",Hibernate.STRING)
								.addScalar("moneyA",Hibernate.BIG_DECIMAL)
								.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void getPlanManageMarket(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String dataState = request.getParameter("dataState");
		if(dataState==null||dataState.equals("")||dataState.equals("undefined")){
			dataState="month";
		}
		StringBuffer sb=new StringBuffer(" SELECT months.content AS searchDate,");
					 		   sb.append(" SUM(CASE WHEN plan.keystr='mmplan' THEN plan.sumMoney/10000 ELSE 0 END) AS moneyA,");
					 		   sb.append("  SUM(CASE WHEN plan.keystr='UPlan' THEN plan.sumMoney/10000 ELSE 0 END) AS moneyB");
							   sb.append(" from ");
							   sb.append(" ( ");
		
		if(dataState != null && "day".equals(dataState)){//日
				for(int i=0;i<=11;i++){
					//查询出前12天
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" DAY),'%Y-%m-%d') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
				sb.append(" ) as months ");
				sb.append(" LEFT JOIN pl_managemoney_plan AS plan ON months.content = DATE_FORMAT(plan.buyStartTime, '%Y-%m-%d') and  (plan.keystr='mmplan'  or plan.keystr='UPlan') and plan.state>=7");
				sb.append(" GROUP BY	months.content");
		}else if(dataState != null && "month".equals(dataState)){//月
				for(int i=0;i<=11;i++){
					//查询出前12个月
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" MONTH),'%Y-%m') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
				sb.append(" ) as months ");
				sb.append(" LEFT JOIN pl_managemoney_plan AS plan ON months.content = DATE_FORMAT(plan.buyStartTime, '%Y-%m') and  (plan.keystr='mmplan'  or plan.keystr='UPlan') and plan.state>=7");
				sb.append(" GROUP BY	months.content");

		}else if(dataState != null && "quarter".equals(dataState)){//季
				for(int i=0;i<=11;i++){
					//查询出前12季
					sb.append(" select CONCAT(DATE_FORMAT(DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER),'%Y'),'年第',QUARTER (DATE_SUB('"+df.format(new Date())+"', INTERVAL "+i+" QUARTER)),'季度') as content");
					if(i!=11){	
						sb.append(" UNION ");
					}
				}
				sb.append(" ) as months ");
				sb.append(" LEFT JOIN pl_managemoney_plan AS plan ON months.content =CONCAT(DATE_FORMAT(plan.buyStartTime, '%Y'),'年第',QUARTER (plan.buyStartTime),'季度') and  (plan.keystr='mmplan'  or plan.keystr='UPlan') and plan.state>=7");
				sb.append(" GROUP BY	months.content");
		}else if(dataState != null && "year".equals(dataState)){//年
				for(int i=0;i<=11;i++){
					//查询出前12 年
					sb.append(" select DATE_FORMAT(date_add('"+df.format(new Date())+"', INTERVAL -"+i+" YEAR),'%Y') as content");
					if(i!=11){
						sb.append(" UNION ");
					}
				}
				sb.append(" ) as months ");
				sb.append(" LEFT JOIN pl_managemoney_plan AS plan ON months.content = DATE_FORMAT(plan.buyStartTime, '%Y') and  (plan.keystr='mmplan'  or plan.keystr='UPlan') and plan.state>=7");
				sb.append(" GROUP BY	months.content");

		}
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("searchDate", Hibernate.STRING).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			addScalar("moneyB", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void getPlanManageState(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String dataState = request.getParameter("dataState");
		StringBuffer sb =new StringBuffer(" select SUM(CASE WHEN plan.state=1 and plan.preSaleTime > NOW() then 1 else 0 end) as sumA ,");
								sb.append(" SUM(CASE WHEN plan.state=1 and plan.preSaleTime > NOW() then plan.sumMoney/10000 else 0 end) as moneyA ,");
								sb.append(" SUM(CASE WHEN plan.state=1 and plan.preSaleTime < NOW() and plan.buyStartTime > NOW() then 1 else 0 end) as sumB ,");
								sb.append(" SUM(CASE WHEN plan.state=1 and plan.preSaleTime < NOW() and plan.buyStartTime > NOW() then plan.sumMoney/10000 else 0 end) as moneyB ,");
								sb.append(" SUM(CASE WHEN plan.state=1 and plan.buyStartTime > NOW() then 1 else 0 end) as sumC ,");
								sb.append(" SUM(CASE WHEN plan.state=1 and plan.buyStartTime > NOW() then plan.sumMoney/10000 else 0 end) as moneyC ,");
								sb.append(" SUM(CASE WHEN plan.state=2 then 1 else 0 end) as sumD ,");
								sb.append(" SUM(CASE WHEN plan.state=2 then plan.sumMoney/10000 else 0 end) as moneyD ,");
								sb.append(" SUM(CASE WHEN plan.state=4 then 1 else 0 end) as sumE ,");
								sb.append(" SUM(CASE WHEN plan.state=4 then plan.sumMoney/10000 else 0 end) as moneyE ,");
								sb.append(" SUM(CASE WHEN plan.state=7 then 1 else 0 end) as sumF ,");
								sb.append(" SUM(CASE WHEN plan.state=7 then plan.sumMoney/10000 else 0 end) as moneyF ,");
								sb.append(" SUM(CASE WHEN plan.state=10 then 1 else 0 end) as sumG ,");
								sb.append(" SUM(CASE WHEN plan.state=10 then plan.sumMoney/10000 else 0 end) as moneyG ,");
								sb.append(" SUM(CASE WHEN plan.state=-1 then 1 else 0 end) as sumH,");
								sb.append(" SUM(CASE WHEN plan.state=-1 then plan.sumMoney/10000 else 0 end) as moneyH");
								sb.append(" from pl_managemoney_plan as plan  where (plan.keystr='mmplan' or plan.keystr='UPlan') ");
		if(dataState!=null&&!dataState.equals("")){
			if(dataState.equals("yearweek")){
				sb.append(" and  yearweek(plan.createtime)=yearweek(NOW()) ");
			}else if(dataState.equals("month")){
				sb.append(" and MONTH(plan.createtime)=MONTH(NOW()) and year(plan.createtime)=year(now())");
			}else if(dataState.equals("quarter")){
				sb.append(" and QUARTER(plan.createtime)=QUARTER(now())");
			}else if(dataState.equals("year")){
				sb.append(" and YEAR(plan.createtime)=YEAR(NOW())");
			}
		}
		System.out.println("licai jishua="+sb.toString());
		Session session = this.getSession();
		List<Highchart> list = session.createSQLQuery(sb.toString())
								.addScalar("sumA",Hibernate.LONG)
								.addScalar("sumB",Hibernate.LONG)
								.addScalar("sumC",Hibernate.LONG)
								.addScalar("sumD",Hibernate.LONG)
								.addScalar("sumE",Hibernate.LONG)
								.addScalar("sumF",Hibernate.LONG)
								.addScalar("sumG",Hibernate.LONG)
								.addScalar("sumH",Hibernate.LONG)
								.addScalar("moneyA",Hibernate.BIG_DECIMAL)
								.addScalar("moneyB",Hibernate.BIG_DECIMAL)
								.addScalar("moneyC",Hibernate.BIG_DECIMAL)
								.addScalar("moneyD",Hibernate.BIG_DECIMAL)
								.addScalar("moneyE",Hibernate.BIG_DECIMAL)
								.addScalar("moneyF",Hibernate.BIG_DECIMAL)
								.addScalar("moneyG",Hibernate.BIG_DECIMAL)
								.addScalar("moneyH",Hibernate.BIG_DECIMAL)
								.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public void getPlanProportion(PageBean<Highchart> pageBean) {
		HttpServletRequest request=pageBean.getRequest();
		String dataState = request.getParameter("dataState");
		StringBuffer sb =new StringBuffer(" SELECT COUNT(*) as sumA,pltype.`name` as nameA,plan.manageMoneyTypeId as remarkA from pl_managemoney_plan as plan ");
								sb.append(" left JOIN pl_managemoney_type as pltype on pltype.manageMoneyTypeId=plan.manageMoneyTypeId");
								sb.append(" where (plan.keystr='mmplan' or plan.keystr='UPlan' ) and plan.state!=-1 and plan.state!=3");
		if(dataState!=null&&!dataState.equals("")){
			if(dataState.equals("yearweek")){
				sb.append(" and  YEARWEEK(plan.createtime)=YEARWEEK(NOW()) ");
			}else if(dataState.equals("month")){
				sb.append(" and MONTH(plan.createtime)=MONTH(NOW()) and year(plan.createtime)=year(now())");
			}else if(dataState.equals("quarter")){
				sb.append(" and QUARTER(plan.createtime)=QUARTER(now())");
			}else if(dataState.equals("year")){
				sb.append(" and YEAR(plan.createtime)=YEAR(NOW())");
			}
		}else{
			sb.append(" and MONTH(plan.createtime)=MONTH(NOW()) and year(plan.createtime)=year(now())");
		}
		
		sb.append(" GROUP BY plan.manageMoneyTypeId");
		Session session = this.getSession();
		List<Highchart> list = session.createSQLQuery(sb.toString())
								.addScalar("sumA",Hibernate.LONG)
								.addScalar("nameA",Hibernate.STRING)
								.addScalar("remarkA",Hibernate.STRING)
								.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
	}

	@Override
	public Long getPlanType(Long typeId) {
		String hql = "from PlManageMoneyPlan as pl";
		Session session = this.getSession();
		List list = this.getSession().createQuery(hql).list();
		return Long.valueOf(list.size());
	}

	@Override
	public void findFinancialAfterMoney(PageBean<Highchart> pageBean,
			String startDate, String endDate) {
		StringBuffer sb=new StringBuffer(" select ");
		sb.append(" sum(ifnull(s.afterMoney,0)+ifnull(s.overdueAccrual,0))  AS moneyA");
		sb.append(" FROM");
		sb.append(" `sl_fund_detail` as s");
		sb.append(" WHERE");
		sb.append(" 1 = 1");
		if(null!=startDate && !"".equals(startDate)){
			sb.append(" AND DATE_FORMAT(s.factDate,'%Y-%m-%d')>='"+startDate+"'");
		}
		if(null!=endDate && !"".equals(endDate)){
			sb.append(" AND DATE_FORMAT(s.factDate,'%Y-%m-%d')<='"+endDate+"'");
		}
		Session session=this.getSession();
		List<Highchart> list=session.createSQLQuery(sb.toString()).
			addScalar("moneyA", Hibernate.BIG_DECIMAL).
			
			setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
		pageBean.setResult(list);
		releaseSession(session);
		
		
	}
	
	/**
	 * 业绩排行（年、月、日）
	 * by shang
	 */
	@Override
	public void businessRankByMonth(PageBean<Highchart> pageBean) {
		
			String flag=pageBean.getRequest().getParameter("flag");
			Session session = this.getSession();
			String sql="";
			List<Highchart> list=new ArrayList<Highchart>();
			
			if("byMoney".equals(flag)){//按月
				//显示当月和往后5个月的数据
				int datelimit = 5;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				sql =
				"select sum(projectMoney) as moneyA,date_format(startDate,'%Y-%m') as searchDate from sl_smallloan_project where  "
				+"date_format(startDate,'%Y-%m')>=date_format(date_sub(curdate(), interval 5 month),'%Y-%m') "
				+"and date_format(startDate,'%Y-%m')<=date_format(curdate(),'%Y-%m') and projectStatus in (1,2) " 
				+"GROUP BY searchDate ";

				list = session.createSQLQuery(sql)
				.addScalar("searchDate",Hibernate.STRING)
				.addScalar("moneyA", Hibernate.BIG_DECIMAL)
				.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
				Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
				for(int i=datelimit;i>=0;i--){
					map.put(sdf.format(com.zhiwei.credit.core.creditUtils.DateUtil.getLastDate(new Date(),-i)),BigDecimal.ZERO);
				}
				for(Highchart e:list){
					map.put(e.getSearchDate(), e.getMoneyA()!=null? e.getMoneyA():BigDecimal.ZERO);
				}
				//清理所有内容放入排好序的内容
				list.clear();
				for(int i=datelimit;i>=0;i--){
					Highchart highchart = new Highchart();
					highchart.setSearchDate(sdf.format(com.zhiwei.credit.core.creditUtils.DateUtil.getLastDate(new Date(),-i)));
					highchart.setMoneyA(map.get(sdf.format(com.zhiwei.credit.core.creditUtils.DateUtil.getLastDate(new Date(),-i))));
					list.add(highchart);
				}
			}else if("bySeason".equals(flag)){//按季度
				if(com.zhiwei.credit.core.creditUtils.DateUtil.getSeason(new Date())==1){
					sql =
						"SELECT QUARTER (startDate) AS searchDate, "+
						"sum(projectMoney) AS moneyA FROM "+
						"sl_smallloan_project WHERE "+
						"QUARTER (startDate) = QUARTER (curdate()) " +
						"AND date_format(curdate(),'%Y')=DATE_FORMAT(startDate,'%Y') and projectStatus in (1,2) " +
						"GROUP BY searchDate ";
					
					list = session.createSQLQuery(sql)
					.addScalar("searchDate",Hibernate.STRING)
					.addScalar("moneyA", Hibernate.BIG_DECIMAL)
					.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
					
				}else{
					sql =
						"SELECT QUARTER (startDate) AS searchDate, "+
						"sum(projectMoney) AS moneyA FROM "+
						"sl_smallloan_project WHERE "+
						"QUARTER (startDate) <= QUARTER (curdate()) " +
						"AND date_format(curdate(),'%Y')=DATE_FORMAT(startDate,'%Y') and projectStatus in (1,2) " +
						"GROUP BY searchDate ";
					
					list = session.createSQLQuery(sql)
					.addScalar("searchDate",Hibernate.STRING)
					.addScalar("moneyA", Hibernate.BIG_DECIMAL)
					.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
					
					Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
					//检查未查询到的季度，进行数据补充并排序
					for(int i=1;i<=com.zhiwei.credit.core.creditUtils.DateUtil.getSeason(new Date());i++){
						map.put(i+"",BigDecimal.ZERO);
					}
					for(Highchart e:list){//把查询到的值赋值给map
						map.put(e.getSearchDate(), e.getMoneyA()!=null? e.getMoneyA():BigDecimal.ZERO);
					}
					//清理所有内容放入排好序的内容
					list.clear();
					for(int i=1;i<=com.zhiwei.credit.core.creditUtils.DateUtil.getSeason(new Date());i++){
						Highchart highchart = new Highchart();
						highchart.setSearchDate(i+"");
						highchart.setMoneyA(map.get(i+""));
						list.add(highchart);
					}
				}
				
			}else if("byYear".equals(flag)){//按年
				sql=
					"select  date_format(startDate,'%Y') searchDate,sum(projectMoney) moneyA " +
					"from sl_smallloan_project where " +
					"date_format(curdate(),'%Y')=date_format(startDate,'%Y') or " +
					"date_format(curdate(),'%Y')-1=date_format(startDate,'%Y') " +
					"and projectStatus in (1,2) " +
					"GROUP BY searchDate ";
				
				list = session.createSQLQuery(sql)
				.addScalar("searchDate",Hibernate.STRING)
				.addScalar("moneyA", Hibernate.BIG_DECIMAL)
				.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
			}
			
			pageBean.setResult(list);
			releaseSession(session);// 如果配置了事物则该方法不做任何操作

	}
	
	/**
	 * 业绩排行（年、月、日）
	 * by shang
	 */
	@Override
	public void myRank(PageBean<Highchart> pageBean) {
		Session session = this.getSession();
		String sql=
			"SELECT appUserName searchDate, "+
			"sum(projectMoney) moneyA "+
			"FROM sl_smallloan_project "+
			"WHERE projectStatus IN (1, 2) "+
			"GROUP BY searchDate " +
			"ORDER BY moneyA desc limit 0,3 ";
			List<Highchart> list = session.createSQLQuery(sql)
			.addScalar("searchDate",Hibernate.STRING)
			.addScalar("moneyA", Hibernate.BIG_DECIMAL)
			.setResultTransformer(Transformers.aliasToBean(Highchart.class)).list();
			pageBean.setResult(list);
			releaseSession(session);// 如果配置了事物则该方法不做任何操作
	}

	/**
	 * 业绩排行
	 */
	@Override
	public void busenessStatistics(PageBean<Highchart> pageBean) {

		String appuserId = pageBean.getRequest().getParameter("appuserId");
		if (appuserId!=null&&!"".equals(appuserId)) {
			String sql =

			"select sum(projectMoney) as result from sl_smallloan_project where projectStatus in (0,1) and appUserId=1 union ALL "
					+

					"select count(id) as result from cs_person where belongedId=1 union ALL "
					+

					"select sum(projectMoney) as result from sl_smallloan_project where date_format(startDate,'%Y-%m')=date_format(now(),'%Y-%m') AND "
					+

					" projectStatus in (1,2) and appUserId=1 UNION ALL "
					+

					"select count(id) as result from cs_person where belongedId=1 and date_format(createdate,'%Y-%m')=date_format(now(),'%Y-%m') ";

			Session session = this.getSession();
			List<Highchart> list = session.createSQLQuery(sql).list();
			pageBean.setResult(list);
			releaseSession(session);// 如果配置了事物则该方法不做任何操作
		}

	}
}
