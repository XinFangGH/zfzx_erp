package com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.PlateFormFinanceDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormBidIncomeDetail;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateRedFinanceDetail;

public class PlateFormFinanceDaoImpl extends HibernateDaoSupport implements PlateFormFinanceDao  {

	protected JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<PlateFormBidIncomeDetail> getBidPlateFormReciveMoney(
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		String sql="SELECT  "+
					     "bp.fundIntentId as fundId,"+
		                 "bp.fundType as fundType,"+
	                     "bp.incomeMoney as planIncomeMoney,"+
	                     "bp.intentDate as planReciveDate,"+
	                     "bp.afterMoney as factIncomeMoney,"+
	                     "bp.notMoney as notMoney,"+
	                     "bp.factDate as factReciveDate,"+
	                     "plan.bidId as bidPlanId,"+
	                     "plan.bidProName as bidPlanName,"+
	                     "plan.bidProNumber as bidPlanNumber,"+
	                     "IFNULL(cp.name ,ce.enterprisename) AS borrowerName,"+
	                     "IFNULL(cp.id ,ce.id) AS borrowerId,"+
	                     "IFNULL(	bcm.id ,bcm1.id) AS p2pborrowerId,"+
	                     "IFNULL(bcm.loginname ,bcm1.loginname) AS p2pborrowerName,"+
	                     "dic.itemValue as fundTypeName"+
                    " FROM "+
	                     " `bp_fund_intent` AS bp"+
                         " LEFT JOIN pl_bid_plan AS plan ON (plan.bidId = bp.bidPlanId)"+
                         " LEFT JOIN sl_smallloan_project as project ON (project.projectId=bp.projectId)"+
                         " LEFT JOIN cs_person cp on (cp.id=project.oppositeID and project.oppositeType='person_customer')"+
                         " LEFT JOIN bp_cust_relation bcr on (bcr.offlineCusId=cp.id and bcr.offlineCustType='p_loan') "+
                         " LEFT JOIN bp_cust_member bcm on (bcm.id=bcr.p2pCustId ) "+
                         " LEFT JOIN cs_enterprise ce on (ce.id=project.oppositeID and project.oppositeType='company_customer')"+
                         " LEFT JOIN  bp_cust_relation bcr1 on (bcr1.offlineCusId=ce.id and bcr1.offlineCustType='b_loan') "+
                         " LEFT JOIN  bp_cust_member bcm1 on (bcm1.id=bcr1.p2pCustId)"+
                         " LEFT JOIN  dictionary_independent AS dic ON (dic.dicKey=bp.fundType)"+
                    " WHERE "+
	                     " bp.fundType  IN ('consultationMoney','serviceMoney') and bp.isCheck=0 and bp.isValid=0 and (bp.repaySource=1 or bp.repaySource is null)";
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
			 sql=sql+" and bp.factDate >= '"+factDatel+" 00:00:00'";
		}
		//实际收取截止日期
		String factDate2=request.getParameter("endDate");
		if(null !=factDate2&&!factDate2.equals("")){
			sql=sql+" and bp.factDate <= '"+factDate2+" 59:59:59'";
		}
		sql =sql +" order by bp.fundIntentId asc ";
		if(start!=null&&limit!=null){
			sql=sql+ " limit "+start+","+(start+limit);
		}
		List<PlateFormBidIncomeDetail>	list = this.jdbcTemplate.query(sql.toString(),new rowMapper());
		return list;
		
	}

	class  rowMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PlateFormBidIncomeDetail income = new PlateFormBidIncomeDetail();
			income.setFundId(rs.getLong("fundId"));
			income.setFundType(rs.getString("fundType"));
			income.setFundTypeName(rs.getString("fundTypeName"));
			income.setBidPlanId(rs.getLong("bidPlanId"));
			income.setBidPlanName(rs.getString("bidPlanName"));
			income.setBidPlanNumber(rs.getString("bidPlanNumber"));
			income.setBorrowerId(rs.getLong("borrowerId"));
			income.setBorrowerName(rs.getString("borrowerName"));
			income.setP2pborrowerId(rs.getLong("p2pborrowerId"));
			income.setP2pborrowerName(rs.getString("p2pborrowerName"));
			income.setPlanIncomeMoney(rs.getBigDecimal("planIncomeMoney"));
			income.setPlanReciveDate(rs.getDate("planReciveDate"));
			income.setFactIncomeMoney(rs.getBigDecimal("factIncomeMoney"));
			income.setFactReciveDate(rs.getDate("factReciveDate"));
			income.setNotMoney(rs.getBigDecimal("notMoney"));
			return income;
		}
		
	}

	@Override
	public List<PlateFormBidIncomeDetail> getOneTimeReciveMoneyList(
			HttpServletRequest request, String fundType, Integer start,
			Integer limit) {
		String sql="SELECT  "+
					    " bp.actualChargeId as fundId,"+
				        " bp.planChargeId as fundType,"+
				        " bp.incomeMoney as planIncomeMoney,"+
				        " bp.intentDate as planReciveDate,"+
				        " bp.afterMoney as factIncomeMoney,"+
				        " bp.notMoney as notMoney,"+
				        " bp.factDate as factReciveDate,"+
				        " plan.bidId as bidPlanId,"+
				        " plan.bidProName as bidPlanName,"+
				        " plan.bidProNumber as bidPlanNumber,"+
				        " IFNULL(cp.name ,ce.enterprisename) AS borrowerName,"+
				        " IFNULL(cp.id ,ce.id) AS borrowerId,"+
				        " IFNULL(	bcm.id ,bcm1.id) AS p2pborrowerId,"+
				        " IFNULL(bcm.loginname ,bcm1.loginname) AS p2pborrowerName,"+
				        " slplan.`name` as fundTypeName"+
				   " FROM "+
				        " `sl_actual_to_charge` AS bp"+
				        " LEFT JOIN pl_bid_plan AS plan ON (plan.bidId = bp.bidPlanId)"+
				        " LEFT JOIN sl_smallloan_project as project ON (project.projectId=bp.projectId)"+
				        " LEFT JOIN cs_person cp on (cp.id=project.oppositeID and project.oppositeType='person_customer')"+
				        " LEFT JOIN bp_cust_relation bcr on (bcr.offlineCusId=cp.id and bcr.offlineCustType='p_loan') "+
				        " LEFT JOIN bp_cust_member bcm on (bcm.id=bcr.p2pCustId ) "+
				        " LEFT JOIN cs_enterprise ce on (ce.id=project.oppositeID and project.oppositeType='company_customer')"+
				        " LEFT JOIN  bp_cust_relation bcr1 on (bcr1.offlineCusId=ce.id and bcr1.offlineCustType='b_loan') "+
				        " LEFT JOIN  bp_cust_member bcm1 on (bcm1.id=bcr1.p2pCustId)"+
				        " LEFT JOIN sl_plans_to_charge as slplan ON (slplan.plansTochargeId=bp.planChargeId)"+
				   " WHERE "+
				        " bp.isCheck=0 and bp.bidPlanId is not NULL ";
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
			 sql=sql+" and bp.factDate >= '"+factDatel+" 00:00:00'";
		}
		//实际收取截止日期
		String factDate2=request.getParameter("endDate");
		if(null !=factDate2&&!factDate2.equals("")){
			sql=sql+" and bp.factDate <= '"+factDate2+" 59:59:59'";
		}
		
		if(start!=null&&limit!=null){
			sql=sql+ " limit "+start+","+(start+limit);
		}
		List<PlateFormBidIncomeDetail>	list = this.jdbcTemplate.query(sql.toString(),new rowMapper());
		return list;
	}

	@Override
	public List<ObAccountDealInfo> getAccountDealInfoByTransferType(HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		String sql="SELECT "+
						"ob.id as id, "+
						"account.accountName as accountName, "+
						"account.accountNumber as accountNumber,"+
						"ob.accountId as accountId, "+
						"ob.incomMoney as incomMoney, "+
						"ob.payMoney as payMoney, "+
						"ob.transferType as transferType, "+
						"ob.createDate as createDate, "+
						"ob.transferDate as transferDate, "+
						"ob.recordNumber as recordNumber, "+
						"ob.msg as msg, "+
						"ob.thirdPayRecordNumber as thirdPayRecordNumber, "+
						"ob.transferAccountName as transferAccountName, "+
						"ob.transferAccountNumber as  transferAccountNumber, "+
						"ob.createFeeAccountNumber as createFeeAccountNumber, "+
						"ob.createFeeAccountName as  createFeeAccountName, "+
						"ob.obligationInfoName as obligationInfoName, "+
						"ob.obligationNumber as obligationNumber, "+
						"setting.typeName as transferTypeName "+
				"FROM "+ 
					"ob_account_deal_info AS ob "+
					"LEFT JOIN ob_system_account AS account ON (ob.accountId = account.id) "+
					"LEFT join ob_systemaccount_setting as setting on (ob.transferType=setting.typeKey)  "+
				"WHERE " +
					"ob.dealRecordStatus=2 and account.accountType='plateFormAccount' and ob.transferType in ('"+ObAccountDealInfo.T_LOANINCOME+"','"
					+ObAccountDealInfo.T_BINDCARD_FEE+"','"+ObAccountDealInfo.T_RECHARGE_FEE+"','"
					+ObAccountDealInfo.T_RECHARGE+"','"+ObAccountDealInfo.T_ENCHASHMENT+"','"
					+ObAccountDealInfo.T_TRANSFER+"','"+ObAccountDealInfo.T_PLATEFORM_CHANGECORRECT+" ')";
		String transferType=request.getParameter("transferType");//查询交易类型 
		if(transferType!=null&&!"".equals(transferType)){
			sql=sql+" and ob.transferType='"+transferType+"'";
			/*if(transferType.equals("other")){//表示平台中其他类型的帐（包括取现手续费，绑卡手续费，充值手续费,账户调账）
				sql=sql+" and ob.transferType in ('"+ObAccountDealInfo.T_LOANINCOME+"','"
				+ObAccountDealInfo.T_BINDCARD_FEE+"','"+ObAccountDealInfo.T_RECHARGE_FEE+"','"
				+ObAccountDealInfo.T_RECHARGE+"','"+ObAccountDealInfo.T_ENCHASHMENT+"','"
				+ObAccountDealInfo.T_TRANSFER+"','"+ObAccountDealInfo.T_PLATEFORM_CHANGECORRECT+"')";
			}else{
				
			}*/
		}
		//到账开始日期
		String startDate=request.getParameter("startDate");
		//到账截止日期
		String endDate=request.getParameter("endDate");
		if(null !=startDate&&!startDate.equals("")){
			 sql=sql+" and ob.transferDate >= '"+startDate+" 00:00:00'";
		}
		
		if(null !=endDate&&!endDate.equals("")){
			sql=sql+" and ob.transferDate <= '"+endDate+" 59:59:59'";
		}
		if(start!=null&&limit!=null){
			sql=sql+ " limit "+start+","+(start+limit);
		}
		List<ObAccountDealInfo>	list = this.jdbcTemplate.query(sql.toString(),new detailRowMapper());
		return list;
	}
	//平台资金账户非业务相关的资金台账
	class  detailRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			ObAccountDealInfo income = new ObAccountDealInfo();
			income.setId(rs.getLong("id"));
			income.setAccountName(rs.getString("accountName"));
			income.setAccountNumber(rs.getString("accountNumber"));
			income.setAccountId(rs.getLong("accountId"));
			income.setIncomMoney(rs.getBigDecimal("incomMoney"));
			income.setPayMoney(rs.getBigDecimal("payMoney"));
			income.setTransferType(rs.getString("transferType"));
			income.setCreateDate(rs.getDate("createDate"));
			income.setTransferDate(rs.getDate("transferDate"));
			income.setRecordNumber(rs.getString("recordNumber"));
			income.setMsg(rs.getString("msg"));
			income.setThirdPayRecordNumber(rs.getString("thirdPayRecordNumber"));
			income.setTransferAccountName(rs.getString("transferAccountName"));
			income.setTransferAccountNumber(rs.getString("transferAccountNumber"));
			income.setCreateFeeAccountNumber(rs.getString("createFeeAccountNumber"));
			income.setCreateFeeAccountName(rs.getString("createFeeAccountName"));
			income.setObligationInfoName(rs.getString("obligationInfoName"));
			income.setObligationNumber(rs.getString("obligationNumber"));
			income.setTransferTypeName(rs.getString("transferTypeName"));
			return income;
		}
		
	}

	@Override
	public List<PlateRedFinanceDetail> getPlateFormRedFianceDetail(
			HttpServletRequest request, Integer start, Integer limit) {
		String sql="SELECT "+
						"redmember.redTopersonId as redTopersonId, "+
                        "redplan.redId as redId, "+
                        "redplan.name as redName, "+
	                    "redmember.bpCustMemberId as bpCustMemberId, "+
                        "bp.truename as truename, "+
                        "bp.loginname as loginname, "+
                        "bp.registrationDate as registrationDate,"+
                        "redmember.redMoney as redMoney, "+
                        "redmember.edredMoney as edredMoney,"+
                        "redmember.distributeTime as distributeTime, "+
                        "redmember.orderNo as orderNo, "+
                        "redmember.requestNo as requestNo "+
                   "FROM "+
	                    "`bp_cust_red_member` AS redmember "+
                        "LEFT JOIN bp_cust_red_envelope AS redplan ON ( redmember.redId = redplan.redId) "+
                        "LEFT JOIN bp_cust_member as bp on (bp.id=redmember.bpCustMemberId) where 1=1 ";
		
		
		//会员登录名	
		String loginname=request.getParameter("loginname");
		if(null !=loginname&&!loginname.equals("")){
			sql=sql+" and bp.loginname like '%/"+loginname+"%'  escape '/' ";
		}
		
		//会员真实姓名
		String truename=request.getParameter("truename");
		if(null !=truename&&!truename.equals("")){
			sql=sql+" and bp.truename like '%/"+truename+"%'  escape '/' ";
		}
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//到账开始日期
		String startDate=request.getParameter("startDate");
		//到账截止日期
		String endDate=request.getParameter("endDate");
		if(startDate==null ||endDate==null){
			startDate=sdf.format(new Date());
			endDate=sdf.format(new Date());
		}
		sql=sql+" and redmember.distributeTime >= '"+startDate+" 00:00:00' and redmember.distributeTime <= '"+endDate+" 59:59:59'";*/
		
		//到账开始日期
		String startDate=request.getParameter("startDate");
		//到账截止日期
		String endDate=request.getParameter("endDate");
		if(null !=startDate&&!startDate.equals("")){
			 sql=sql+" and redmember.distributeTime >= '"+startDate+" 00:00:00'";
		}
		
		if(null !=endDate&&!endDate.equals("")){
			sql=sql+" and redmember.distributeTime <= '"+endDate+" 59:59:59'";
		}
		if(start!=null&&limit!=null){
			sql=sql+ " limit "+start+","+(start+limit);
		}
		List<PlateRedFinanceDetail>	list = this.jdbcTemplate.query(sql.toString(),new rednoveRowMapper());
		return list;
	}
	
	class  rednoveRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PlateRedFinanceDetail income = new PlateRedFinanceDetail();
			income.setRedTopersonId(rs.getLong("redTopersonId"));
			income.setRedId(rs.getLong("redId"));
			income.setRedName(rs.getString("redName"));
			income.setBpCustMemberId(rs.getLong("bpCustMemberId"));
			income.setLoginname(rs.getString("loginname"));
			income.setTruename(rs.getString("truename"));
			income.setRegistrationDate(rs.getDate("registrationDate"));
			income.setRedMoney(rs.getBigDecimal("redMoney"));
			income.setDredMoney(rs.getBigDecimal("edredMoney"));
			income.setDistributeTime(rs.getDate("distributeTime"));
			income.setOrderNo(rs.getString("orderNo"));
			income.setRequestNo(rs.getString("requestNo"));
			return income;
		}
		
	}
}
