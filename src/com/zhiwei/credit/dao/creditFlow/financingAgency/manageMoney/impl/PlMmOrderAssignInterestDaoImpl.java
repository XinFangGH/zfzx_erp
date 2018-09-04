package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.BpFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.AssignInerestGenerate;
import com.zhiwei.credit.util.xmlToWord.printOrder.POTable1;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlMmOrderAssignInterestDaoImpl extends BaseDaoImpl<PlMmOrderAssignInterest> implements PlMmOrderAssignInterestDao{

	public PlMmOrderAssignInterestDaoImpl() {
		super(PlMmOrderAssignInterest.class);
	}

	@Override
	public PlMmOrderAssignInterest getFisrtByOrderId(Long orderId) {
		String hql = "from PlMmOrderAssignInterest where orderId =? order by intentDate ASC limit 1";
		Object objs[] = {orderId};
		List list =  this.findByHql(hql,objs);
		if(list==null||list.size()==0)return null;
		return (PlMmOrderAssignInterest)list.get(0);
	}

	@Override
	public PlMmOrderAssignInterest deleteCoupons(Long mmPlanId, String fundType) {
			final String sql="delete from pl_mm_order_assigninterest  where mmplanId ="+mmPlanId+" and fundType in('couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest')";
			this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException,
				SQLException {
					session.createSQLQuery(sql).executeUpdate();
					return null;
				}
			});
			return null;
	}

	@Override
	public List<PlMmOrderAssignInterest> listByEarlyRedemptionId(
			Long earlyRedemptionId) {
		String hql="from PlMmOrderAssignInterest as m where m.earlyRedemptionId=?";
		return this.findByHql(hql, new Object[]{earlyRedemptionId});
	}

	@Override
	public List<PlMmOrderAssignInterest> getByPlanIdA(Long orderId,
			Long investPersonId, Long mmplanId, String fundType,Integer periods) {
		if(fundType!=null&&!fundType.equals("")&&periods==null){
			String hql = "from PlMmOrderAssignInterest as bf where bf.orderId = ? and bf.investPersonId=? and bf.mmplanId=? and bf.fundType in ("+fundType+") and bf.factDate is null";
			return this.findByHql(hql, new Object[]{orderId,investPersonId,mmplanId});
		}else if(fundType!=null&&!fundType.equals("")&&periods !=null && !"".equals(periods)){
			String hql = "from PlMmOrderAssignInterest as bf where bf.orderId = ? and bf.investPersonId=? and bf.mmplanId=? and bf.fundType in ("+fundType+") and bf.factDate is null and bf.periods=?";
			return this.findByHql(hql, new Object[]{orderId,investPersonId,mmplanId,periods});
		}else{
			String hql = "from PlMmOrderAssignInterest as bf where bf.orderId = ? and bf.investPersonId=? and bf.mmplanId=? " +
					" and bf.fundType in('couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest') and bf.factDate is null";
			return this.findByHql(hql, new Object[]{orderId,investPersonId,mmplanId});
		}
		
	}

	@Override
	public List<PlMmOrderAssignInterest> listByEarlyDate(String earlyDate,
			Long orderId,String fundType,Long earlyRedemptionId) {
		if(null==earlyDate){
			String hql="from PlMmOrderAssignInterest as m where m.orderId=? and m.fundType in"+fundType+" and (m.earlyRedemptionId is null or m.earlyRedemptionId!=?) order by m.intentDate asc";
			return this.findByHql(hql, new Object[]{orderId,earlyRedemptionId});
			
		}else{
			
			String hql="from PlMmOrderAssignInterest as m where m.orderId=? and m.intentDate"+earlyDate+" and m.fundType in"+fundType+" and (m.earlyRedemptionId is null or m.earlyRedemptionId!=?) order by m.intentDate asc";
			return this.findByHql(hql, new Object[]{orderId,earlyRedemptionId});
		}
		
	}

	@Override
	public List<PlMmOrderAssignInterest> getByDealCondition(Long orderId,Long assignInterestId) {
		String hql="from PlMmOrderAssignInterest as m where m.orderId=? and m.investPersonId=? and m.incomeMoney>m.afterMoney  and m.isValid=0 and m.isCheck=0 ";
		return this.getSession().createQuery(hql).setParameter(0, orderId).setParameter(1, assignInterestId).list();
	}

	@Override
	public String saveList(List<PlMmOrderAssignInterest> list) {
		 //开始事务
		 try {  
			   Transaction tx = this.getSession().beginTransaction();
			   int count=0;
			   for(PlMmOrderAssignInterest f:list){
				   this.getSession().save(f);
			   	  if ( ++count % 100 == 0 )
			         {
			   		this.getSession().flush();
			   		this.getSession().clear();
			         }
			     }
			     
			   tx.commit();
			   
		 } catch (Exception e) {  
	         e.printStackTrace(); // 打印错误信息  
	         this.getSession().getTransaction().rollback(); // 出错将回滚事物  
	     } finally {  
	    	 this.getSession().close(); // 关闭Session  
	     }  
		
	     return "";
	}
	/* public void saveMedicines(List<Medicine> ms) {  
    Session session = null;  
    if (ms != null && ms.size() > 0) {  
        try {  
            session = HibernateUtil.getSession(); // 获取Session  
            session.beginTransaction(); // 开启事物  
            Medicine medicine = null; // 创建药品对象  
            // 循环获取药品对象  
            for (int i = 0; i < ms.size(); i++) {  
                medicine = (Medicine) ms.get(i); // 获取药品  
                session.save(medicine); // 保存药品对象  
                // 批插入的对象立即写入数据库并释放内存  
                if (i % 10 == 0) {  
                    session.flush();  
                    session.clear();  
                }  
            }  
            session.getTransaction().commit(); // 提交事物  
        } catch (Exception e) {  
            e.printStackTrace(); // 打印错误信息  
            session.getTransaction().rollback(); // 出错将回滚事物  
        } finally {  
            HibernateUtil.closeSession(session); // 关闭Session  
        }  
    }  */

	@Override
	public List<PlMmOrderAssignInterest> listByOrderIdAndFundType(String orderId, String fundType) {
		
		String hql = "from PlMmOrderAssignInterest where orderId =? and fundType = ? ";
		return this.findByHql(hql,new Object[]{Long.valueOf(orderId),fundType} );
				
	}

	@Override
	public List<PlMmOrderAssignInterest> listByMmPlanId(Long mmplanId,
			String keystr) {
		if(keystr!=null&&keystr.equals("coupons")){//优惠券查询台账
			String hql="from PlMmOrderAssignInterest where mmplanId=? and fundType in ('couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest')";
			return this.findByHql(hql,new Object[]{mmplanId});
		}/*else if(keystr!=null&&keystr.equals("all")){//理财计划款项台账
			String hql="from PlMmOrderAssignInterest where  fundType not in ('couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest')";
			return this.findByHql(hql);
		}*/else{//体验标台账
			String hql="from PlMmOrderAssignInterest where mmplanId=? and keystr=? and factDate is null";
			return this.findByHql(hql,new Object[]{mmplanId,keystr});
		}
	}

	@Override
	public List<PlMmOrderAssignInterest> getCouponsList(PagingBean pb,
			Map<String, String> map) {
		String fundType = map.get("fundType");
		if(fundType.equals("notCommoninterest")){
			fundType="('couponInterest','principalCoupons','subjoinInterest')";
		}else if(fundType.equals("raiseinterest")){
			fundType="('raiseinterest')";
		}else{
			fundType="('commoninterest')";
		}
		StringBuffer sql =new StringBuffer("SELECT	pbp.mmName as mmName,  pbp.mmNumber as mmNumber,  pbp.rebateType as rebateType," +
				"  pbp.rebateWay as rebateWay,  intentperiod.periods as periods,  pbp.mmplanId as mmplanId," +
				"  intentperiod.fundType as fundType,  intentperiod.sumMoney as sumMoney," +
				"  intentperiod.intentDate as intentDate,  intentperiod.factDate as factDate,pbp.raiseRate as raiseRate,intentperiod.sumAfterMoney as sumAfterMoney FROM	" +
				"  (SELECT	bp.*,SUM(bp.incomeMoney) as sumMoney,SUM(bp.afterMoney) as sumAfterMoney FROM	pl_mm_order_assigninterest bp	WHERE	bp.isValid = 0" +
				"	AND bp.isCheck = 0	AND bp.fundType IN "+fundType+" 	GROUP BY	bp.mmplanId,	bp.intentDate	) AS intentperiod" +
				"  LEFT JOIN pl_managemoney_plan pbp ON intentperiod.mmplanId = pbp.mmplanId WHERE	1 = 1");
	if(fundType.equals("('commoninterest')")){
		sql.append(" and pbp.addRate>0 ");
	}else if(fundType.equals("('raiseinterest')")){
		
	}else{
		sql.append(" and pbp.coupon=1 ");
	}	
	String bidName=map.get("bidName");
	if(null !=bidName&&!bidName.equals("")){
		sql.append(" and pbp.mmName like '%"+bidName+"%' ");
	}
	String bidProNumber=map.get("bidProNumber");
	if(null !=bidProNumber&&!bidProNumber.equals("")){
		sql.append(" and pbp.mmNumber  like '%"+bidProNumber+"%' ");
	}
	String intentDateg=map.get("intentDateg");
	if(null !=intentDateg&&!intentDateg.equals("")){
		sql.append(" and intentperiod.intentDate <= '").append(intentDateg).append("'");
	}
	String intentDatel=map.get("intentDatel");
	if(null !=intentDatel&&!intentDatel.equals("")){
		sql.append(" and intentperiod.intentDate >= '").append(intentDatel).append("'");
	}
	String factDateg=map.get("factDateg");
	if(null !=factDateg&&!factDateg.equals("")){
		sql.append(" and intentperiod.factDate <= '").append(factDateg).append(" 59:59:59'");
	}
	String factDatel=map.get("factDatel");
	if(null !=factDatel&&!factDatel.equals("")){
		sql.append(" and intentperiod.factDate >= '").append(factDatel).append(" 00:00:00'");
	}
	String rebateType=map.get("rebateType");
	if(null !=rebateType&&!rebateType.equals("")){
		sql.append(" and pbp.rebateType = ").append(rebateType);
	}
	String rebateWay=map.get("rebateWay");
	if(null !=rebateWay&&!rebateWay.equals("")){
		sql.append(" and pbp.rebateWay = ").append(rebateWay);
	}
	//System.out.println(sql);
	if(null==pb){
		List<PlMmOrderAssignInterest>	list = this.jdbcTemplate.query(sql.toString(),new rowMapperCoupons());
		  return list;
	}else{
		sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize()));
		List<PlMmOrderAssignInterest> list = this.jdbcTemplate.query(sql.toString(),new rowMapperCoupons());
		  return list;
	}
	}
	@Override
	public List<PlMmOrderAssignInterest> getAllInterest(PagingBean pb,
			Map<String, String> map) {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer hql=new StringBuffer("from PlMmOrderAssignInterest as po where po.isValid=0 and po.isCheck=0");
		String keystr=map.get("Q_keystr_S_EQ");
		String investPersonName=map.get("Q_investPersonName_S_LK");
		String mmName=map.get("Q_mmName_S_LK");
		String intentDate_GE=map.get("Q_intentDate_D_GE");
		String intentDate_LE=map.get("Q_intentDate_D_LE");
		String periods=map.get("Q_periods_N_EQ");
		String buttonType=map.get("buttonType");
		if(buttonType.equals("6")){//查询逾期未派息台账
			hql.append(" and po.intentDate < '"+sf.format(new Date())+"' and po.factDate is NULL");
		}
		if(buttonType.equals("7")){//查询需要返款的台账
			hql.append(" and po.loanerRepayMentStatus=1");
		}
		if(null !=keystr && !"".equals(keystr)){
			if(keystr.equals("mmplan")){
				hql.append(" and po.keystr in('"+keystr+"') and fundType in('loanInterest','principalRepayment','riskRate','liquidatedDamages')");
			}else{
				hql.append(" and po.keystr='"+keystr+"' and fundType not in('couponInterest','principalCoupons','subjoinInterest','commoninterest','raiseinterest')");
			}
		}
		if(null !=investPersonName && !"".equals(investPersonName)){
			hql.append(" and po.investPersonName like '%"+investPersonName+"%'");
		}
		if(null !=mmName && !"".equals(mmName)){
			hql.append(" and po.mmName like '%"+mmName+"%'");
		}
		if(null !=intentDate_GE && !"".equals(intentDate_GE)){
			hql.append(" and po.intentDate>='"+intentDate_GE+"'");
		}
		if(null !=intentDate_LE && !"".equals(intentDate_LE)){
			hql.append(" and po.intentDate<='"+intentDate_LE+"'");
		}
		if(null !=periods && !"".equals(periods)){
			hql.append(" and po.periods='"+periods+"'");
		}
		//派息状态
		String loanerRepayMentStatus = map.get("loanerRepayMentStatus");
		if(null != loanerRepayMentStatus && !"".equals(loanerRepayMentStatus)){
			if(loanerRepayMentStatus.equals("3")){
				hql.append(" and po.loanerRepayMentStatus is null");
			}else{
				hql.append(" and po.loanerRepayMentStatus = "+loanerRepayMentStatus);
			}
		}
		System.out.println("hql="+hql);
		if(null==pb){
			return findByHql(hql.toString());
		}else{
			return findByHql(hql.toString(),null,pb);
		}
	}
	
	class  rowMapperCoupons implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PlMmOrderAssignInterest assign = new PlMmOrderAssignInterest();
			assign.setMmName(rs.getString("mmName"));
			assign.setMmNumber(rs.getString("mmNumber"));
			assign.setPeriods(rs.getInt("periods"));
			assign.setMmplanId(rs.getLong("mmplanId"));
			assign.setIntentDate(rs.getDate("intentDate"));
			assign.setFactDate(rs.getDate("factDate"));
			assign.setRebateType(rs.getString("rebateType"));
			assign.setRebateWay(rs.getString("rebateWay"));
			assign.setFundType(rs.getString("fundType"));
			assign.setSumMoney(rs.getString("sumMoney"));
			assign.setRaiseRate(rs.getString("raiseRate"));
			assign.setSumAfterMoney(rs.getString("sumAfterMoney"));
			return assign;
		}
		
	}
	@Override
	public List<PlMmOrderAssignInterest> getByPlanIdAndPeriod(Long planId,
			Long payintentPeriod, String fundType) {
		String hql1="";
		if(fundType!=null&&!fundType.equals("")&&fundType.equals("notCommoninterest")){
			 hql1 = "from PlMmOrderAssignInterest as bf where bf.mmplanId = ? and bf.periods = ? and bf.fundType in ('couponInterest','principalCoupons','subjoinInterest')";
			 List<PlMmOrderAssignInterest> list = this.findByHql(hql1, new Object[]{planId,payintentPeriod.intValue()});
				return list;
		}else{
			 hql1 = "from PlMmOrderAssignInterest as bf where bf.mmplanId = ? and bf.periods = ? and bf.fundType=? and bf.isValid = 0 AND bf.isCheck = 0";
			 List<PlMmOrderAssignInterest> list = this.findByHql(hql1, new Object[]{planId,payintentPeriod.intValue(),fundType});
				return list;
		}
	}

	@Override
	public List<PlMmOrderAssignInterest> getdistributeMoneyAssign(Long planId,
			Long payintentPeriod, String fundType) {
		if(fundType!=null&&!fundType.equals("")&&fundType.equals("coupons")){
			String hql1 = "from PlMmOrderAssignInterest as bf where bf.mmplanId = ? and bf.periods = ? and bf.fundType in ('couponInterest','principalCoupons','subjoinInterest') and bf.isValid = 0 AND bf.isCheck = 0 and bf.factDate is  null";
			 List<PlMmOrderAssignInterest> list = this.findByHql(hql1, new Object[]{planId,payintentPeriod.intValue()});
				return list;
		}else{
			String hql1 = "from PlMmOrderAssignInterest as bf where bf.mmplanId = ? and bf.periods = ? and bf.fundType=? and bf.isValid = 0 AND bf.isCheck = 0 and bf.factDate is  null";
			 List<PlMmOrderAssignInterest> list = this.findByHql(hql1, new Object[]{planId,payintentPeriod.intValue(),fundType});
				return list;
		}
		
	}

	@Override
	public List<PlMmOrderAssignInterest> getRaiseBpfundIntent(Long planId) {
		StringBuffer sql =new StringBuffer("SELECT	bp.investpersonName AS investpersonName,	bp.incomeMoney AS incomeMoney," +
				"	bp.intentDate AS intentDate,	bp.factDate AS factDate,	 bp.fundType AS fundType," +
				" 	pl.buyDatetime AS buyDatetime,	pbp.startinInterestTime AS startinInterestTime," +
				"	 TO_DAYS(pbp.startinInterestTime) - TO_DAYS(pl.buyDatetime) AS days FROM	pl_mm_order_assigninterest bp " +
				"  LEFT JOIN pl_managemoney_plan pbp ON bp.mmplanId = pbp.mmplanId " +
				"  LEFT JOIN pl_managemoneyplan_buyinfo AS pl ON pl.orderId = bp.orderId WHERE	bp.isValid = 0 AND bp.isCheck = 0 " +
				"  AND bp.fundType IN ('raiseinterest') and pbp.mmplanId="+planId+"");
			List<PlMmOrderAssignInterest> list = this.jdbcTemplate.query(sql.toString(),new rowMapperRaiseDetail());
			return list;
	}
	class  rowMapperRaiseDetail implements RowMapper {
		
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PlMmOrderAssignInterest income = new PlMmOrderAssignInterest();
			income.setIntentDate(rs.getDate("intentDate"));
			income.setFactDate(rs.getDate("factDate"));
			income.setFundType(rs.getString("fundType"));
			income.setIncomeMoney(rs.getBigDecimal("incomeMoney"));
			income.setDays(rs.getString("days"));
			income.setStartinInterestTime(rs.getString("startinInterestTime"));
			income.setBuyDatetime(rs.getString("buyDatetime"));
			income.setInvestPersonName(rs.getString("investpersonName"));
			return income;
		}
		
	}
	@Override
	public List<PlMmOrderAssignInterest> getCouponsAssignIncome(PagingBean pb) {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer sql =new StringBuffer("SELECT	member.truename as truename,p.investpersonName as investpersonName, " +
				"sum(CASE WHEN p.fundType = 'principalCoupons' THEN	p.afterMoney ELSE 0 END ) +  " +
				"sum(CASE WHEN p.fundType = 'couponInterest' THEN	p.afterMoney ELSE 0 END ) +  " +
				"sum(CASE WHEN p.fundType = 'subjoinInterest' THEN	p.afterMoney ELSE 0 END ) AS 'couponsIncome',  " +
				"sum(CASE WHEN p.fundType = 'commoninterest' THEN	p.afterMoney ELSE 0 END ) AS 'addrateIncome',  " +
				"sum(CASE WHEN p.fundType = 'principalCoupons' THEN	p.afterMoney ELSE 0 END ) +  " +
				"sum(CASE WHEN p.fundType = 'couponInterest' THEN	p.afterMoney ELSE 0 END ) +  " +
				"sum(CASE WHEN p.fundType = 'subjoinInterest' THEN	p.afterMoney ELSE 0 END ) +  " +
				"sum(CASE WHEN p.fundType = 'commoninterest' THEN	p.afterMoney ELSE 0 END) AS 'sumIncome', " +
				"sum(CASE WHEN p.fundType = 'principalCoupons' THEN	p.incomeMoney ELSE 0 END ) +  " +
				"sum(CASE WHEN p.fundType = 'couponInterest' THEN	p.incomeMoney ELSE 0 END ) +  " +
				"sum(CASE WHEN p.fundType = 'subjoinInterest' THEN	p.incomeMoney ELSE 0 END ) +  " +
				"sum(CASE WHEN p.fundType = 'commoninterest' THEN	p.incomeMoney ELSE 0 END )-" +
				"sum(CASE WHEN p.fundType = 'principalCoupons' THEN	p.afterMoney ELSE 0 END ) -" +
				"sum(CASE WHEN p.fundType = 'couponInterest' THEN	p.afterMoney ELSE 0 END ) -  " +
				"sum(CASE WHEN p.fundType = 'subjoinInterest' THEN	p.afterMoney ELSE 0 END ) -  " +
				"sum(CASE WHEN p.fundType = 'commoninterest' THEN	p.afterMoney ELSE 0 END) AS 'notIncome'  " +
				"FROM `pl_mm_order_assigninterest` AS p LEFT JOIN bp_cust_member as member on " +
				"p.investPersonId=member.id  WHERE p.isCheck = 0 AND p.isValid = 0  " +
				"AND p.fundType in('couponInterest','principalCoupons',	'subjoinInterest','commoninterest') ");
		String Q_loginname = request.getParameter("Q_loginname");
		String Q_truename = request.getParameter("Q_truename");
		if(Q_loginname!=null&&!Q_loginname.equals("")){
			sql.append(" and p.investpersonName like '%"+Q_loginname+"%'");
		}
		if(Q_truename!=null&&!Q_truename.equals("")){
			sql.append(" and member.truename like '%"+Q_truename+"%'");
		}
		sql.append(" GROUP BY	p.investPersonId");
		if(null==pb){
			List<PlMmOrderAssignInterest> list = this.jdbcTemplate.query(sql.toString(),new rowMapperCouponsIncome());
			return list;
		}else{
			sql.append(" limit "+pb.getStart()+","+(pb.getStart()+pb.getPageSize()));
			List<PlMmOrderAssignInterest> list = this.jdbcTemplate.query(sql.toString(),new rowMapperCouponsIncome());
			return list;
		}
		
	}
	class  rowMapperCouponsIncome implements RowMapper {
		
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			PlMmOrderAssignInterest income = new PlMmOrderAssignInterest();
			income.setInvestPersonName(rs.getString("trueName"));
			income.setLoginName(rs.getString("investpersonName"));
			income.setCouponsIncome(rs.getString("couponsIncome"));
			income.setAddrateIncome(rs.getString("addrateIncome"));
			income.setSumIncome(rs.getString("sumIncome"));
			income.setNotIncome(rs.getString("notIncome"));
			return income;
		}
		
	}
	@Override
	public List<PlMmOrderAssignInterest> getAllByMmproduceInterest(
			PagingBean pb, Map<String, String> map) {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT * from pl_mm_order_assigninterest AS pmoa "+
         "LEFT JOIN pl_mm_order_info AS pmoi ON pmoi.orderId=pmoa.orderId " +
         //loanInterest：利息，riskRate：风险保证金，liquidatedDamages：提前赎回违约金  principalRepayment：本金
         "where pmoa.keystr='mmproduce' and pmoa.isCheck=0 and pmoa.isValid=0  and pmoa.fundType in ('loanInterest','riskRate','liquidatedDamages','principalRepayment') ");
		
		String userIds= map.get("userId");
		String shopId=map.get("shopId");
		//按上下级分离
		if(null!=userIds && !"".equals(userIds)){
			sb.append(" and  fn_check_repeat(pmoi.customerManagerNameId,'"+userIds+"') = 1 ");
		}
		//按门店分离数据
		if(null !=shopId && !"".equals(shopId)){
			sb.append(" and pmoi.departId="+shopId);
		}
		String investPersonName=map.get("Q_investPersonName_S_LK");
		if(null !=investPersonName && !"".equals(investPersonName)){
			sb.append(" and pmoa.investPersonName like '%"+investPersonName+"%'");
		}
		String mmName=map.get("Q_mmName_S_LK");
		if(null !=mmName && !"".equals(mmName)){
			sb.append(" and pmoa.mmName like '%"+mmName+"%'");
		}
		String intentDateGE=map.get("Q_intentDate_D_GE");
		if(null !=intentDateGE && !"".equals(intentDateGE)){
			sb.append(" and pmoa.intentDate >= '"+intentDateGE+"'");
		}
		String intentDateLE=map.get("Q_intentDate_D_LE");
		if(null !=intentDateLE && !"".equals(intentDateLE)){
			sb.append(" and pmoa.intentDate <= '"+intentDateLE+"'");
		}
		String periods=map.get("Q_periods_N_EQ");
		if(null !=periods && !"".equals(periods)){
			sb.append(" and pmoa.periods = "+periods);
		}
		
		List list=null;
		if(null == pb){
			list=this.getSession().createSQLQuery(sb.toString())
			.addEntity(PlMmOrderAssignInterest.class)
			.list();
		}else{
			list=this.getSession().createSQLQuery(sb.toString())
			.addEntity(PlMmOrderAssignInterest.class)
			.setFirstResult(pb.getStart())
			.setMaxResults(pb.getPageSize())
			.list();
		}
		
		return list;
	}

	@Override
	public List<PlMmOrderAssignInterest> getByRequestNo(Map<String, String> map) {
		StringBuffer hql = new StringBuffer("from PlMmOrderAssignInterest as pi where 1=1");
		String investRequestNo = map.containsKey("investRequestNo") ? map.get("investRequestNo") : "";
		if(!"".equals(investRequestNo) && null != investRequestNo){
			hql.append(" and pi.investRequestNo='"+investRequestNo+"'");
		}
		String loanerRequestNo = map.containsKey("loanerRequestNo") ? map.get("loanerRequestNo") : "";
		if(!"".equals(loanerRequestNo) && null != loanerRequestNo){
			hql.append(" and pi.loanerRequestNo='"+loanerRequestNo+"'");
		}
		return this.findByHql(hql.toString());
	}

	@Override
	public List<POTable1> listOrderInterest(String orderId) {
		// TODO Auto-generated method stub
		if(orderId!=null&&!"".equals(orderId)){
			String sql =" SELECT "+
			" p.orderId as orderId , "+
			" p.periods as periods, "+
			" p.mmName as productName, "+
			" plan.promisYearRate as productRate, "+
			" p.intentDate as planDate, "+
			" p.intentDate as planDate, "+
			" SUM(case when p.fundType='loanInterest'  then p.incomeMoney else 0 end ) as creditorIncomeMoney, "+
			" SUM(case when p.fundType='principalRepayment'  then p.incomeMoney else 0 end ) as prinpalMoney, "+
			" SUM(case when p.fundType='liquidatedDamages'  then p.payMoney else 0 end ) as quitMoney "+
			" FROM "+
			" pl_mm_order_assigninterest AS p "+
			" LEFT JOIN pl_managemoneyplan_buyinfo AS plan ON (p.orderId = plan.orderId) "+
			" WHERE "+
			" p.keystr='mmproduce' and p.isCheck=0 and p.isValid=0  " +
			" and p.orderId="+Long.valueOf(orderId)+
			" GROUP BY p.intentDate ORDER BY p.intentDate ASC ";
			return this.getSession().createSQLQuery(sql).
				   addScalar("orderId", Hibernate.STRING).
				   addScalar("periods", Hibernate.STRING).
				   addScalar("productName", Hibernate.STRING).
				   addScalar("productRate", Hibernate.STRING).
				   addScalar("planDate", Hibernate.STRING).
				   addScalar("creditorIncomeMoney", Hibernate.STRING).
				   addScalar("prinpalMoney", Hibernate.STRING).
				   addScalar("quitMoney", Hibernate.STRING).
				   setResultTransformer(Transformers.aliasToBean(POTable1.class)) .list();
		}else{
			return null;
		}
		
		
	}
	
}