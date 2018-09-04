package com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.RowMapper;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepaymentDao;
import com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.impl.PlateFormFinanceDaoImpl.rowMapper;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormBidIncomeDetail;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepayment;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlRiskguaranteemoneyRepaymentDaoImpl extends BaseDaoImpl<SlRiskguaranteemoneyRepayment> implements SlRiskguaranteemoneyRepaymentDao{

	public SlRiskguaranteemoneyRepaymentDaoImpl() {
		super(SlRiskguaranteemoneyRepayment.class);
	}

	@Override
	public List<SlRiskguaranteemoneyRepayment> getpunishmentRecord(
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		String sql="SELECT "+
				   " bp.id as id,"+
				   " bp.payMoney as payMoney,"+
				   " bp.payDate as payDate,"+
				   " bp.notRebackMoney as notRebackMoney,"+
				   " bp.rebackMoney as rebackMoney,"+
				   " bp.punishmentRate as punishmentRate,"+
				   " DATEDIFF(now(),bp.punishmentDate) as punishmentdays,"+
				   " bp.rebackPunishmentMoney as rebackPunishmentMoney,"+
				   " (bp.notRebackMoney*bp.punishmentRate*datediff(now(),bp.punishmentDate)/100) as notRebackPunishmentMoney,"+
				   " (bp.notRebackMoney*bp.punishmentRate*datediff(now(),bp.punishmentDate)/100+bp.rebackPunishmentMoney) as totalPunishmentMoney,"+
				   " bp.factDate as factDate, "+
				   " plan.bidId as planId, "+
				   " plan.bidProName as planName, "+
				   " plan.bidProNumber as planNumber, "+
				   " IFNULL(cp.name ,ce.enterprisename) AS borrowerName, "+
				   " IFNULL(cp.id ,ce.id) AS borrowerId, "+
				   " IFNULL(	bcm.id ,bcm1.id) AS p2pborrowerId, "+
				   " IFNULL(bcm.loginname ,bcm1.loginname) AS p2pborrowerName "+
				   " FROM "+
				   " `sl_riskguaranteemoney_repayment` AS bp "+
				   " LEFT JOIN pl_bid_plan AS plan ON (plan.bidId = bp.planId) "+
				   " LEFT JOIN sl_smallloan_project as project ON (project.projectId=bp.projectId) "+
				   " LEFT JOIN cs_person cp on (cp.id=project.oppositeID and project.oppositeType='person_customer') "+
				   " LEFT JOIN bp_cust_relation bcr on (bcr.offlineCusId=cp.id and bcr.offlineCustType='p_loan')  "+
				   " LEFT JOIN bp_cust_member bcm on (bcm.id=bcr.p2pCustId )  "+
				   " LEFT JOIN cs_enterprise ce on (ce.id=project.oppositeID and project.oppositeType='company_customer') "+
				   " LEFT JOIN  bp_cust_relation bcr1 on (bcr1.offlineCusId=ce.id and bcr1.offlineCustType='b_loan')  "+
				   " LEFT JOIN  bp_cust_member bcm1 on (bcm1.id=bcr1.p2pCustId)";
		//标的名称
		String bidPlanName=request.getParameter("bidPlanName");
		if(null !=bidPlanName&&!bidPlanName.equals("")){
					sql=sql+" and plan.bidProName  like '%/"+bidPlanName+"%'  escape '/' ";
		}
		//标的编号	
		String bidPlanNumber=request.getParameter("bidPlanNumber");
		if(null !=bidPlanNumber&&!bidPlanNumber.equals("")){
			sql=sql+" and plan.bidProNumber like '%/"+bidPlanNumber+"%'  escape '/' ";
		}
		//借款人姓名	
		String borrowerName=request.getParameter("borrowerName");
		if(null !=borrowerName&&!borrowerName.equals("")){
			sql=sql+" and (cp.name  like '%/"+borrowerName+"%'  escape '/'  or ce.enterprisename like '%/"+borrowerName+"%'  escape '/')";
		}
		
		//实际收取开始日期
		String factDatel=request.getParameter("startDate");
		if(null !=factDatel&&!factDatel.equals("")){
			 sql=sql+" and bp.payDate >= '"+factDatel+" 00:00:00'";
		}
		//实际收取截止日期
		String factDate2=request.getParameter("endDate");
		if(null !=factDate2&&!factDate2.equals("")){
			sql=sql+" and bp.payDate <= '"+factDate2+" 59:59:59'";
		}
		
		if(start!=null&&limit!=null){
			sql=sql+ " limit "+start+","+(start+limit);
		}
		List<SlRiskguaranteemoneyRepayment>	list = this.jdbcTemplate.query(sql.toString(),new rowMapper());
		return list;
	}
	
	class  rowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			SlRiskguaranteemoneyRepayment income = new SlRiskguaranteemoneyRepayment();
			income.setId(rs.getLong("id"));
			income.setPayMoney(rs.getBigDecimal("payMoney"));
			income.setPayDate(rs.getDate("payDate"));
			income.setNotRebackMoney(rs.getBigDecimal("notRebackMoney"));
			income.setRebackMoney(rs.getBigDecimal("rebackMoney"));
			income.setPunishmentRate(rs.getBigDecimal("punishmentRate"));
			income.setPunishmentdays(rs.getInt("punishmentdays"));
			income.setRebackPunishmentMoney(rs.getBigDecimal("rebackPunishmentMoney"));
			income.setNotRebackPunishmentMoney(rs.getBigDecimal("notRebackPunishmentMoney"));
			income.setTotalPunishmentMoney(rs.getBigDecimal("totalPunishmentMoney"));
			income.setFactDate(rs.getDate("factDate"));
			income.setPlanId(rs.getLong("planId"));
			income.setPlanName(rs.getString("planName"));
			income.setPlanNumber(rs.getString("planNumber"));
			income.setBorrowerId(rs.getLong("borrowerId"));
			income.setBorrowerName(rs.getString("borrowerName"));
			income.setP2pborrowerId(rs.getLong("p2pborrowerId"));
			income.setP2pborrowerName(rs.getString("p2pborrowerName"));
			return income;
		}
		
	}

}