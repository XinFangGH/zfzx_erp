package com.zhiwei.credit.dao.creditFlow.financingAgency.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.jdbc.core.RowMapper;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidSaleDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidSale;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlBidSaleDaoImpl extends BaseDaoImpl<PlBidSale> implements PlBidSaleDao{

	public PlBidSaleDaoImpl() {
		super(PlBidSale.class);
	}

	
	@Override
	public List<PlBidSale> getCanTransferingList(Long userId,PagingBean pb, Map<String, String> map) {

		String saleStatus= map.get("saleStatus");
		StringBuffer sql =new StringBuffer("");
		sql.append("select f.*,plan.bidProName,sl.yearAccrualRate,sl.accrualtype,sl.intentDate,sl.startDate ");
		sql.append("from(select * from(select  pbi.id as bidInfoID ,pbi.bidId as bidPlanID ,pbi.orderNo,");
		sql.append("(select count(*) from pl_bid_sale pbs where pbs.bidInfoID=pbi.id and pbs.outCustID="+userId+" and pbs.saleStatus<2) as tansferingcount,");
		sql.append("IFNULL((select sum(sfi.notMoney) from bp_fund_intent sfi where sfi.fundType='principalRepayment' and sfi.isCheck=0 and sfi.isValid=0 and sfi.notMoney !=0 and sfi.orderNo =pbi.orderNo ),0) as saleMoney ");
		sql.append("from pl_bid_info as pbi where  pbi.state=2 and ( pbi.userId="+userId+"  or pbi.newInvestPersonId="+userId+" ) ) as a " );
		sql.append("where a.tansferingcount=0  " );//and a.saleMoney>0
	//	sql.append(" limit 0,15) as f ");
		sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize())+") as f");	
		sql.append(" left join pl_bid_plan plan on plan.bidId=f.bidPlanID ");
		sql.append(" left join bp_business_dir_pro as bdir on bdir.bdirProId =plan.bdirProId and plan.proType='B_Dir' ");
		sql.append(" left join bp_business_or_pro  as bor on bor.borProId =plan.borProId and plan.proType='B_Or' ");
		sql.append(" left join bp_persion_dir_pro  as pdir on pdir.pDirProId =plan.pDirProId and plan.proType='P_Dir' ");
		sql.append(" left join bp_persion_or_pro  as por on por.pOrProId =plan.pOrProId and plan.proType='P_Or' ");
	//	sql.append(" left join bp_fund_project as sl  on ( sl.flag=1 AND((sl.projectId=bdir.proId ) or (sl.projectId=bor.proId ) or(sl.projectId=pdir.proId ) or (sl.projectId=por.proId ))) ");
		sql.append(" left join bp_fund_project as sl  on (((sl.id=bdir.moneyPlanId ) or (sl.id=bor.moneyPlanId ) or(sl.id=pdir.moneyPlanId ) or (sl.id=por.moneyPlanId ))) ");
		
		StringBuffer sqlcount =new StringBuffer("");
		sqlcount.append("select count(*) ");
		sqlcount.append("from (select * from(select  pbi.id as bidInfoID ,pbi.bidId as bidPlanID ,pbi.orderNo,");
		sqlcount.append("(select count(*) from pl_bid_sale pbs where pbs.bidInfoID=pbi.id and pbs.outCustID="+userId+"  and pbs.saleStatus<2) as tansferingcount,");
		sqlcount.append("IFNULL((select sum(sfi.notMoney) from bp_fund_intent sfi where sfi.fundType='principalRepayment' and sfi.isCheck=0 and sfi.isValid=0 and sfi.notMoney !=0 and sfi.orderNo =pbi.orderNo ),0) as saleMoney ");
		sqlcount.append("from pl_bid_info as pbi where  pbi.state=2 and ( pbi.userId="+userId+" or pbi.newInvestPersonId="+userId+") ) as a " );
		sqlcount.append("where a.tansferingcount=0) as f " );//and a.saleMoney>0
		System.out.println("sqlcount=="+sqlcount);
		
		 Query query = getSession().createSQLQuery(sqlcount.toString());
		 List countlist=query.list();
		 BigInteger count=new BigInteger("0");
			if(null!=countlist && countlist.size()>0){
				if(null!=countlist.get(0)){
					count=(BigInteger) countlist.get(0);
				}
			}
		pb.setTotalItems(Integer.parseInt(count.toString()));
		List<PlBidSale> list = this.jdbcTemplate.query(sql.toString(),new rowMapperb1());
		  return list;

	}
	class  rowMapperb1 implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PlBidSale plBidSale = new PlBidSale();
			plBidSale.setSaleMoney(rs.getBigDecimal("saleMoney"));
			plBidSale.setBidInfoID(rs.getLong("bidInfoID"));
			plBidSale.setBidPlanID(rs.getLong("bidPlanID"));
			plBidSale.setBidProName(rs.getString("bidProName"));
			plBidSale.setYearAccrualRate(rs.getBigDecimal("yearAccrualRate"));
			plBidSale.setAccrualtype(rs.getString("accrualtype"));
			plBidSale.setIntentDate(rs.getDate("intentDate"));
			plBidSale.setStartDate(rs.getDate("startDate"));
			return plBidSale;
		}
		
	}
	@Override
	public List<PlBidSale> inTransferingList(Long userId, PagingBean pb,
			Map<String, String> map) {
		String saleStatus= map.get("saleStatus");
		
		StringBuffer sql =new StringBuffer("");
		sql.append("select  a.id as id,a.changeMoneyRate,a.changeMoneyType,a.saleStartTime,a.saleCloseTime,a.bidInfoID,a.bidPlanID,a.sumMoney,a.saleSuccessTime,a.sumMoney,a.preTransferFee,a.saleSuccessTime,");
		sql.append("pbi.orderNo,plan.bidProName,sl.intentDate,sl.yearAccrualRate,sl.accrualtype,sl.startDate,");
		sql.append("a.saleMoney ");//已买出的和关闭的出让金额不变
		sql.append(" from (select * from pl_bid_sale as pbs  where pbs.saleStatus="+saleStatus+" " );
		if(userId !=null){
			sql.append(" and pbs.inCustID="+userId+" " );
		}
		sql.append(" order by pbs.saleDealTime desc " );
		sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize())+")as a " );	
		sql.append("left join pl_bid_info pbi on pbi.id =a.bidInfoID ");
		sql.append("left join pl_bid_plan plan on a.bidPlanID=plan.bidId ");
		sql.append("left join bp_business_dir_pro as bdir on bdir.bdirProId =plan.bdirProId and plan.proType='B_Dir' ");
		sql.append("left join bp_business_or_pro  as bor on bor.borProId =plan.borProId and plan.proType='B_Or' ");
		sql.append("left join bp_persion_dir_pro  as pdir on pdir.pDirProId =plan.pDirProId and plan.proType='P_Dir' ");
		sql.append("left join bp_persion_or_pro  as por on por.pOrProId =plan.pOrProId and plan.proType='P_Or' ");
	//	sql.append("left join bp_fund_project as sl  on ( sl.flag=1 AND((sl.projectId=bdir.proId ) or (sl.projectId=bor.proId ) or(sl.projectId=pdir.proId ) or (sl.projectId=por.proId ))) ");
		sql.append("left join bp_fund_project as sl  on (((sl.id=bdir.moneyPlanId ) or (sl.id=bor.moneyPlanId ) or(sl.id=pdir.moneyPlanId ) or (sl.id=por.moneyPlanId ))) ");
		
		StringBuffer sqlcount =new StringBuffer("");
		sqlcount.append("select  count(*)");
		sqlcount.append(" from (select * from pl_bid_sale as pbs where pbs.saleStatus="+saleStatus);
		if(userId !=null){
			sqlcount.append(" and pbs.inCustID="+userId);
		}
		sqlcount.append("   )as a ");
		 Query query = getSession().createSQLQuery(sqlcount.toString());
		 List countlist=query.list();
		 
		 BigInteger count=new BigInteger("0");
			if(null!=countlist && countlist.size()>0){
				if(null!=countlist.get(0)){
					count=(BigInteger) countlist.get(0);
				}
			}
		pb.setTotalItems(Integer.parseInt(count.toString()));
		List<PlBidSale> list = this.jdbcTemplate.query(sql.toString(),new rowMapperb());
		  return list;

	}


	@Override
	public List<PlBidSale> outTransferingList(Long userId, PagingBean pb,
			Map<String, String> map) {
		String saleStatus= map.get("saleStatus");
		String outCustName= map.get("outCustName");
		String inCustName= map.get("inCustName");
		String saleSuccessTimeg= map.get("saleSuccessTimeg");
		String saleSuccessTimel= map.get("saleSuccessTimel");
		String startDate= map.get("startDate");
		String endDate= map.get("endDate");
		String preTransferFeeStatus = map.get("preTransferFeeStatus");
		StringBuffer sql =new StringBuffer("");
		sql.append("select  a.outCustName,a.inCustName,a.id as id,a.changeMoneyRate,a.changeMoneyType,a.saleStartTime,a.saleCloseTime,a.bidInfoID,a.bidPlanID,a.sumMoney,a.saleSuccessTime,a.changeMoney,a.transferFee,a.preTransferFee,a.saleSuccessTime,");
		sql.append("pbi.orderNo,plan.bidProName,sl.intentDate,sl.yearAccrualRate,sl.accrualtype,sl.startDate,");
		if(saleStatus.equals("0")){ //挂牌中出让金额是不断变化的
			sql.append("(select sum(sfi.notMoney) from bp_fund_intent sfi where sfi.fundType='principalRepayment' and sfi.isCheck=0 and sfi.isValid=0 and sfi.orderNo =pbi.orderNo) as saleMoney ");
		}else{
			sql.append("a.saleMoney ");//已买出的和关闭的出让金额不变
		}
		
		sql.append(" from (select * from pl_bid_sale as pbs" +" where pbs.saleStatus in("+saleStatus+") " );
		if(null !=outCustName&&!outCustName.equals("")){
			sql.append(	" and pbs.outCustName='"+outCustName+"' " );
		}
		if(null !=inCustName&&!inCustName.equals("")){
			sql.append(	" and pbs.inCustName='"+inCustName+"' " );
		}
		if(null !=saleSuccessTimel&&!saleSuccessTimel.equals("")){
			sql.append(	" and DATE_FORMAT(pbs.saleSuccessTime,'%y-%m-%d') >=DATE_FORMAT('"+saleSuccessTimel+"','%y-%m-%d')" );
		}
		if(null !=saleSuccessTimeg&&!saleSuccessTimeg.equals("")){
			sql.append(	" and DATE_FORMAT(pbs.saleSuccessTime,'%y-%m-%d') <=DATE_FORMAT('"+saleSuccessTimeg+"','%y-%m-%d')");
		}
		if(null !=startDate&&!startDate.equals("")){
			sql.append(	" and DATE_FORMAT(pbs.saleStartTime,'%y-%m-%d') >=DATE_FORMAT('"+startDate+"','%y-%m-%d')" );
		}
		if(null !=endDate&&!endDate.equals("")){
			sql.append(	" and DATE_FORMAT(pbs.saleStartTime,'%y-%m-%d') <=DATE_FORMAT('"+endDate+"','%y-%m-%d')");
		}
		if(map.containsKey("returnStatus")){
			sql.append(	" and pbs.returnStatus is null");
		}
		if(map.containsKey("preTransferFeeStatus")){
			sql.append(	" and pbs.preTransferFeeStatus in ("+preTransferFeeStatus+")");
		}
		
		sql.append(" order by pbs.saleDealTime,pbs.saleCloseTime desc " );
		sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize())+")as a ");	
		sql.append("left join pl_bid_info pbi on pbi.id =a.bidInfoID ");
		sql.append("left join pl_bid_plan plan on a.bidPlanID=plan.bidId ");
		sql.append("left join bp_business_dir_pro as bdir on bdir.bdirProId =plan.bdirProId and plan.proType='B_Dir' ");
		sql.append("left join bp_business_or_pro  as bor on bor.borProId =plan.borProId and plan.proType='B_Or' ");
		sql.append("left join bp_persion_dir_pro  as pdir on pdir.pDirProId =plan.pDirProId and plan.proType='P_Dir' ");
		sql.append("left join bp_persion_or_pro  as por on por.pOrProId =plan.pOrProId and plan.proType='P_Or' ");
	//	sql.append("left join bp_fund_project as sl  on ( sl.flag=1 AND((sl.projectId=bdir.proId ) or (sl.projectId=bor.proId ) or(sl.projectId=pdir.proId ) or (sl.projectId=por.proId ))) ");
		sql.append("left join bp_fund_project as sl  on (((sl.id=bdir.moneyPlanId ) or (sl.id=bor.moneyPlanId ) or(sl.id=pdir.moneyPlanId ) or (sl.id=por.moneyPlanId ))) ");
		
		
		StringBuffer sqlcount =new StringBuffer("");
		sqlcount.append("select  count(*) ");
		sqlcount.append(" from (select * from pl_bid_sale as pbs  where pbs.saleStatus in("+saleStatus+") " );
		if(null !=outCustName&&!outCustName.equals("")){
			sqlcount.append(	" and pbs.outCustName='"+outCustName+"' " );
		}
		if(null !=inCustName&&!inCustName.equals("")){
			sqlcount.append(	" and pbs.inCustName='"+inCustName+"' " );
		}
		sqlcount.append(" )as a ");
		 Query query = getSession().createSQLQuery(sqlcount.toString());
		 List countlist=query.list();
		 
		 BigInteger count=new BigInteger("0");
			if(null!=countlist && countlist.size()>0){
				if(null!=countlist.get(0)){
					count=(BigInteger) countlist.get(0);
				}
			}
		pb.setTotalItems(Integer.parseInt(count.toString()));
		List<PlBidSale> list = this.jdbcTemplate.query(sql.toString(),new rowMapperb());
		  return list;

	}
	class  rowMapperb implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PlBidSale plBidSale = new PlBidSale();
			plBidSale.setChangeMoneyRate(rs.getInt("changeMoneyRate"));
			plBidSale.setChangeMoneyType(rs.getShort("changeMoneyType"));
			plBidSale.setSaleMoney(rs.getBigDecimal("saleMoney"));
			plBidSale.setBidInfoID(rs.getLong("bidInfoID"));
			plBidSale.setBidPlanID(rs.getLong("bidPlanID"));
			plBidSale.setBidProName(rs.getString("bidProName"));
			plBidSale.setIntentDate(rs.getDate("intentDate"));
			plBidSale.setYearAccrualRate(rs.getBigDecimal("yearAccrualRate"));
			plBidSale.setSumMoney(rs.getBigDecimal("sumMoney"));
			plBidSale.setChangeMoney(rs.getBigDecimal("changeMoney"));
			plBidSale.setTransferFee(rs.getBigDecimal("transferFee"));
			plBidSale.setPreTransferFee(rs.getBigDecimal("preTransferFee"));
			plBidSale.setAccrualtype(rs.getString("accrualtype"));
			Calendar calendar=Calendar.getInstance();
			plBidSale.setSaleSuccessTime(rs.getTimestamp("saleSuccessTime"));
			plBidSale.setSaleCloseTime(rs.getTimestamp("saleCloseTime"));
			plBidSale.setSaleStartTime(rs.getTimestamp("saleStartTime"));
			plBidSale.setStartDate(rs.getDate("startDate"));
			plBidSale.setId(rs.getLong("id"));
			plBidSale.setOutCustName(rs.getString("outCustName"));
			plBidSale.setInCustName(rs.getString("inCustName"));
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 System.out.println("saleSuccessTime=="+plBidSale.getSaleSuccessTime());
			if(plBidSale.getSaleSuccessTime()!=null){
				
				try {
					plBidSale.setSaleSuccessTime(sdf.parse(sdf.format(plBidSale.getSaleSuccessTime())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(null!=plBidSale.getSaleCloseTime()){
				try {
					Date a=sdf.parse(plBidSale.getSaleCloseTime().toString());
					plBidSale.setSaleCloseTime(a);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(null!=plBidSale.getSaleStartTime()){
				try {
					Date a=sdf.parse(plBidSale.getSaleStartTime().toString());
					plBidSale.setSaleStartTime(a);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}if(null!=plBidSale.getSaleSuccessTime()){
				try {
					Date a=sdf.parse(plBidSale.getSaleSuccessTime().toString());
					plBidSale.setSaleSuccessTime(a);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			/*plBidSale.setSaleSuccessTime(rs.getString("saleSuccessTime")==null?null:sdf.parse(rs.getString("saleSuccessTime")));
			plBidSale.setSaleCloseTime(rs.getDate(columnLabel, cal)("saleCloseTime")==null?null:sdf.parse(rs.getString("saleCloseTime")));
			plBidSale.setSaleStartTime(rs.getString("saleStartTime")==null?null:sdf.parse(rs.getString("saleStartTime")));*/
			return plBidSale;
		}
		
	}
	@Override
	public List<PlBidSale> transferingList(Long userId, PagingBean pb,
			Map<String, String> map) {
	

		String outCustName= map.get("outCustName");
		String inCustName= map.get("inCustName");
		StringBuffer sql =new StringBuffer("");
		sql.append("select  a.outCustName,a.inCustName, a.id as id,a.changeMoneyRate,a.changeMoneyType,a.saleStartTime,a.saleCloseTime,a.bidInfoID,a.bidPlanID,a.sumMoney,a.saleSuccessTime,a.changeMoney,a.transferFee,a.preTransferFee,a.saleSuccessTime,");
		sql.append("pbi.orderNo,plan.bidProName,sl.intentDate,sl.yearAccrualRate,sl.accrualtype,sl.startDate,");
	//   sql.append("IFNULL((select sum(sfi.notMoney) from bp_fund_intent sfi where sfi.fundType='principalRepayment' and sfi.isCheck=0 and sfi.isValid=0 and sfi.orderNo =pbi.orderNo),0) as saleMoney ");
	   sql.append("(pbi.userMoney-IFNULL((select sum(sfi1.afterMoney) from bp_fund_intent sfi1 where  sfi1.orderNo =pbi.orderNo and sfi1.fundType='principalRepayment' and sfi1.isCheck=0 and sfi1.isValid=0   ),0)) as saleMoney  ");
		sql.append(" from (select * from pl_bid_sale as pbs" +" where pbs.saleStatus=1 " );
		if(null !=outCustName&&!outCustName.equals("")){
			sql.append(	" and pbs.outCustName='"+outCustName+"' " );
		}
		if(null !=inCustName&&!inCustName.equals("")){
			sql.append(	" and pbs.inCustName='"+inCustName+"' " );
		}
		sql.append("order by pbs.saleDealTime,pbs.saleCloseTime desc " );
		sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize())+")as a ");	
		sql.append("left join pl_bid_info pbi on pbi.id =a.bidInfoID ");
		sql.append("left join pl_bid_plan plan on a.bidPlanID=plan.bidId ");
		sql.append("left join bp_business_dir_pro as bdir on bdir.bdirProId =plan.bdirProId and plan.proType='B_Dir' ");
		sql.append("left join bp_business_or_pro  as bor on bor.borProId =plan.borProId and plan.proType='B_Or' ");
		sql.append("left join bp_persion_dir_pro  as pdir on pdir.pDirProId =plan.pDirProId and plan.proType='P_Dir' ");
		sql.append("left join bp_persion_or_pro  as por on por.pOrProId =plan.pOrProId and plan.proType='P_Or' ");
	//	sql.append("left join bp_fund_project as sl  on ( sl.flag=1 AND((sl.projectId=bdir.proId ) or (sl.projectId=bor.proId ) or(sl.projectId=pdir.proId ) or (sl.projectId=por.proId ))) ");
		sql.append("left join bp_fund_project as sl  on (((sl.id=bdir.moneyPlanId ) or (sl.id=bor.moneyPlanId ) or(sl.id=pdir.moneyPlanId ) or (sl.id=por.moneyPlanId ))) ");
		System.out.println("================"+sql);
		
		
		StringBuffer sqlcount =new StringBuffer("");
		sqlcount.append("select  count(*) ");
		sqlcount.append(" from (select * from pl_bid_sale as pbs  where pbs.saleStatus=1 " );
		
		if(null !=outCustName&&!outCustName.equals("")){
			sqlcount.append(	" and pbs.outCustName='"+outCustName+"' " );
		}
		if(null !=inCustName&&!inCustName.equals("")){
			sqlcount.append(	" and pbs.inCustName='"+inCustName+"' " );
		}
		sqlcount.append(" )as a ");
		 Query query = getSession().createSQLQuery(sqlcount.toString());
		 List countlist=query.list();
		 System.out.println("sqlcount==="+sqlcount);
		 BigInteger count=new BigInteger("0");
			if(null!=countlist && countlist.size()>0){
				if(null!=countlist.get(0)){
					count=(BigInteger) countlist.get(0);
				}
			}
		pb.setTotalItems(Integer.parseInt(count.toString()));
		List<PlBidSale> list = this.jdbcTemplate.query(sql.toString(),new rowMapperb());
		  return list;

	

	
	}


	@Override
	public List<PlBidSale> getCanToObligatoryRightChildren(
			Map<String, String> map) {
		Short saleStatus=Short.valueOf(map.get("saleStatus"));
		String outCustName= map.get("outCustName");
		String hql="from PlBidSale as m where m.saleStatus=? and m.outCustName=?";
		return this.findByHql(hql, new Object[]{saleStatus,outCustName});
		
	}


	@Override
	public PlBidSale getMoreinfoById(Long id) {
		StringBuffer sql =new StringBuffer("");
		sql.append("select  a.outCustName,a.inCustName,a.id as id,a.changeMoneyRate,a.changeMoneyType,a.saleStartTime,a.saleCloseTime,a.bidInfoID,a.bidPlanID,a.sumMoney,a.saleSuccessTime,a.changeMoney,");
		sql.append("pbi.orderNo,plan.bidProName,sl.intentDate,sl.yearAccrualRate,sl.accrualtype,sl.startDate,");
		sql.append("a.saleMoney,a.preTransferFee as preTransferFee,IFNULL(a.transferFee,0) as transferFee,a.preTransferFeeStatus as preTransferFeeStatus, ");//已买出的和关闭的出让金额不变
		sql.append("IFNULL((a.preTransferFee-a.transferFee),0) as reBackFee ");//已买出的和关闭的出让金额不变
		sql.append(" from  pl_bid_sale as a ");
		sql.append("left join pl_bid_info pbi on pbi.id =a.bidInfoID ");
		sql.append("left join pl_bid_plan plan on a.bidPlanID=plan.bidId ");
		sql.append("left join bp_business_dir_pro as bdir on bdir.bdirProId =plan.bdirProId and plan.proType='B_Dir' ");
		sql.append("left join bp_business_or_pro  as bor on bor.borProId =plan.borProId and plan.proType='B_Or' ");
		sql.append("left join bp_persion_dir_pro  as pdir on pdir.pDirProId =plan.pDirProId and plan.proType='P_Dir' ");
		sql.append("left join bp_persion_or_pro  as por on por.pOrProId =plan.pOrProId and plan.proType='P_Or' ");
	//	sql.append("left join bp_fund_project as sl  on ( sl.flag=1 AND((sl.projectId=bdir.proId ) or (sl.projectId=bor.proId ) or(sl.projectId=pdir.proId ) or (sl.projectId=por.proId ))) ");
		sql.append("left join bp_fund_project as sl  on (((sl.id=bdir.moneyPlanId ) or (sl.id=bor.moneyPlanId ) or(sl.id=pdir.moneyPlanId ) or (sl.id=por.moneyPlanId ))) ");
		sql.append(" where a.saleStatus in(4,7)  and a.id="+id );
		sql.append(" order by a.saleDealTime,a.saleCloseTime desc " );
		System.out.println(sql);
		
	   List<PlBidSale>  list = this.jdbcTemplate.query(sql.toString(),new oneQueryinfoRaw());
		return (list!=null&&list.size()>0)?list.get(0):null;
	}
	
	class  oneQueryinfoRaw implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PlBidSale plBidSale = new PlBidSale();
			plBidSale.setChangeMoneyRate(rs.getInt("changeMoneyRate"));
			plBidSale.setChangeMoneyType(rs.getShort("changeMoneyType"));
			plBidSale.setSaleMoney(rs.getBigDecimal("saleMoney"));
			plBidSale.setBidInfoID(rs.getLong("bidInfoID"));
			plBidSale.setBidPlanID(rs.getLong("bidPlanID"));
			plBidSale.setBidProName(rs.getString("bidProName"));
			plBidSale.setIntentDate(rs.getDate("intentDate"));
			plBidSale.setYearAccrualRate(rs.getBigDecimal("yearAccrualRate"));
			plBidSale.setSumMoney(rs.getBigDecimal("sumMoney"));
			plBidSale.setChangeMoney(rs.getBigDecimal("changeMoney"));
			plBidSale.setTransferFee(rs.getBigDecimal("transferFee"));
			plBidSale.setAccrualtype(rs.getString("accrualtype"));
			plBidSale.setSaleSuccessTime(rs.getDate("saleSuccessTime"));
			plBidSale.setSaleCloseTime(rs.getDate("saleCloseTime"));
			plBidSale.setSaleStartTime(rs.getDate("saleStartTime"));
			plBidSale.setStartDate(rs.getDate("startDate"));
			plBidSale.setId(rs.getLong("id"));
			plBidSale.setOutCustName(rs.getString("outCustName"));
			plBidSale.setInCustName(rs.getString("inCustName"));
			plBidSale.setTransferFee(rs.getBigDecimal("transferFee"));
			plBidSale.setPreTransferFee(rs.getBigDecimal("preTransferFee"));
			plBidSale.setReBackFee(rs.getBigDecimal("reBackFee"));
			plBidSale.setPreTransferFeeStatus(rs.getShort("preTransferFeeStatus"));
			return plBidSale;
		}
		
	}
}
