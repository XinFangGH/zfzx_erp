package com.zhiwei.credit.dao.coupon.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.jdbc.core.RowMapper;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.coupon.BpCouponsDao;
import com.zhiwei.credit.dao.customer.InvestPersonInfoDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;


/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCouponsDaoImpl extends BaseDaoImpl<BpCoupons> implements BpCouponsDao{

	public BpCouponsDaoImpl() {
		super(BpCoupons.class);
	}
	@Resource
	private BpCustMemberDao bpcustmemberDao;
	@Override
	public void saveList(List<BpCoupons> list) {
		 Session session =this.getSessionFactory().openSession();
		    //开始事务
	    Transaction tx = session.beginTransaction();
	    int count=0;
	    for(BpCoupons f:list){
	    	session.save(f);
	    	  if ( ++count % 500 == 0 ){
	              session.flush();
	              session.clear();
	          }
	      }
	    tx.commit();
	    session.close();
		
	}

	/**
	 * 查询出来未派发的优惠券
	 */
	@Override
	public List<BpCoupons> listForNotDistributeCoupon(
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		String hql="SELECT "+
						"bcou.couponId as couponId, "+
						"bcou.couponType as couponType, "+
						"bcou.couponNumber as couponNumber, "+
						"bcou.couponValue as couponValue, "+
						"bcou.couponStartDate as couponStartDate, "+
						"bcou.couponEndDate as couponEndDate, "+
						"bcou.couponStatus as couponStatus, "+
						"bcou.createDate as createDate, "+
						"bcou.couponResourceType as couponResourceType, "+
						"bcou.resourceId as resourceId, "+
						"IFNULL(setting.couponDescribe,activity.activityExplain) as resourceName "+
				   "FROM "+
				   		"`bp_coupons` AS bcou "+
				   "LEFT JOIN bp_coupon_setting AS setting ON ( "+
				   		"bcou.resourceId = setting.categoryId "+
				   		"AND bcou.couponResourceType = 'couponResourceType_normal' "+
				   		") "+
				   "left join bp_activity_manage as activity on( "+
				   		"bcou.resourceId = activity.activityId "+
				   		"AND bcou.couponResourceType = 'couponResourceType_active' "+
				   		") " +
				   "where " +
				   		"bcou.belongUserId is NULL and " +
				   		"bcou.couponStatus<=4 "; 
		if(request!=null){//根据request中的查询条件来设置查询限制
			String couponType=request.getParameter("couponType");//优惠券类型
			if(couponType!=null&&!"".equals(couponType)){
				hql=hql+" and (setting.couponType='"+couponType+"' )";
			}
			String couponstatus=request.getParameter("couponstatus");//优惠券状态
			if(couponstatus!=null&&!"".equals(couponstatus)){
				hql=hql+" and bcou.couponstatus="+Short.valueOf(couponstatus);
			}
			
			String resourceName=request.getParameter("resourceName");//优惠券来源描述
			if(resourceName!=null&&!"".equals(resourceName)){
				hql=hql+" and (setting.couponDescribe like '%"+resourceName+"%' or activity.activityExplain like '%"+resourceName+"%')";
			}
			
			String couponNumber=request.getParameter("couponNumber");//优惠券编号
			if(couponNumber!=null&&!"".equals(couponNumber)){
				hql=hql+" and bcou.couponNumber like '%"+couponNumber+"%'";
			}
			String couponEndDate_S=request.getParameter("couponEndDate_S");//优惠券过期日查询开始日期
			if(couponEndDate_S!=null&&!"".equals(couponEndDate_S)){
				hql=hql+" and bcou.couponEndDate >= '"+couponEndDate_S+"'";
			}
			String couponEndDate_E=request.getParameter("couponEndDate_E");//优惠券过期日查询结束日期
			if(couponEndDate_E!=null&&!"".equals(couponEndDate_E)){
				hql=hql+" and bcou.couponEndDate <= '"+couponEndDate_E+"'";
			}
		}
		hql=hql+" ORDER BY bcou.createDate desc ";
		if(start!=null&&limit!=null){
			hql=hql+"limit "+start+","+(start+limit);
			List<BpCoupons> list = this.jdbcTemplate.query(hql,new rowMapperb());
			return list;
		}else{
			List<BpCoupons> list = this.jdbcTemplate.query(hql,new rowMapperb());
			  return list;
		}
		
	}
	/**
	 * 未派发的展示实体
	 * @author Administrator
	 *
	 */
	class  rowMapperb implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			BpCoupons income = new BpCoupons();
			income.setCouponId(rs.getLong("couponId"));
			income.setCouponType(rs.getLong("couponType"));
			income.setCouponNumber(rs.getString("couponNumber"));
			income.setCouponValue(rs.getBigDecimal("couponValue"));
			income.setCouponStartDate(rs.getDate("couponStartDate"));
			income.setCouponEndDate(rs.getDate("couponEndDate"));
			income.setCouponStatus(rs.getShort("couponStatus"));
			income.setCreateDate(rs.getDate("createDate"));
			income.setResourceName(rs.getString("resourceName"));
			income.setResourceId(rs.getLong("resourceId"));
			income.setCouponResourceType(rs.getString("couponResourceType"));
			return income;
		}
		
	}
	/**
	 * 查询出来已经派发的优惠券
	 */
	@Override
	public List<BpCoupons> bouponBelongList(HttpServletRequest request,
			Integer start, Integer limit) {
			String hql="SELECT "+
				"bcou.couponId as couponId, "+
				"bcou.couponType as couponType, "+
				"bcou.couponNumber as couponNumber, "+
				" IFNULL(bb.totalMoney,0) as couponMoney, "+
				"bcou.couponValue as couponValue, "+
				"bcou.couponStartDate as couponStartDate, "+
				"bcou.couponEndDate as couponEndDate, "+
				"bcou.couponStatus as couponStatus, "+
				"bcou.belongUserId as belongUserId, "+
				"bcou.belongUserName as belongUserName, "+
				"bcou.bindOpraterDate as bindOpraterDate, "+
				"bcou.useProjectName as useProjectName, "+
				"bcou.useProjectNumber as useProjectNumber, "+
				"bcou.useProjectId as useProjectId, "+
				"bcou.useProjectType as useProjectType, "+
				"bcou.useTime as useTime, "+
				"bcou.couponResourceType as couponResourceType, "+
				"bcou.resourceId as resourceId, "+
				"bcou.createDate as createDate, "+
				"activity.activityNumber as activityNumber, "+
				"IFNULL(setting.couponDescribe,activity.activityExplain) as resourceName "+
		   "FROM "+
		   		"`bp_coupons` AS bcou "+
		   "LEFT JOIN bp_coupon_setting AS setting ON ( "+
		   		"bcou.resourceId = setting.categoryId "+
		   		"AND bcou.couponResourceType = 'couponResourceType_normal' "+
		   		") "+
		   "left join bp_activity_manage as activity on( "+
		   		"bcou.resourceId = activity.activityId "+
		   		"AND bcou.couponResourceType = 'couponResourceType_active' "+
		   		") " +
		   	" LEFT JOIN("+
		   	" SELECT"+
		   	" SUM(b.incomeMoney) as totalMoney,p.couponId"+
		   	" FROM bp_fund_intent b"+
		   	" LEFT JOIN pl_bid_info p ON p.orderNo = b.orderNo "+
		   	" WHERE"+
		   	" p.couponId IS NOT NULL"+
		   	" AND b.fundType IN ('couponInterest','principalCoupons','subjoinInterest')"+
		   	" GROUP BY p.couponId"+
		   	" )  as bb on bb.couponId=bcou.couponId "+
		   " where " +
		   		" bcou.couponNumber is NOT NULL  " ;
		   	//	"bcou.couponStatus>=4 "; 
		if(request!=null){//根据request中的查询条件来设置查询限制
			
			String categoryId = request.getParameter("categoryId");
			String couponstatus=request.getParameter("couponstatus");//优惠券状态
			if(categoryId!=null&&!"".equals(categoryId)){
				hql=hql+" and bcou.categoryId='"+categoryId+"'";
			}else{
				hql=hql+" and bcou.belongUserId is NOT NULL";
				if(couponstatus!=null&&!"".equals(couponstatus)){
					hql=hql+" and bcou.couponStatus="+Short.valueOf(couponstatus);
				}else{
					//hql=hql+" and bcou.couponStatus!=0";
				}
			}
			String couponType=request.getParameter("couponType");//优惠券类型
			if(couponType!=null&&!"".equals(couponType)){
				hql=hql+" and bcou.couponType='"+couponType+"' ";
			}
		
			
			String resourceName=request.getParameter("resourceName");//优惠券来源描述
			if(resourceName!=null&&!"".equals(resourceName)){
				hql=hql+" and (setting.couponDescribe like '%"+resourceName+"%' or activity.activityExplain like '%"+resourceName+"%')";
			}
			
			String couponNumber=request.getParameter("couponNumber");//优惠券编号
			if(couponNumber!=null&&!"".equals(couponNumber)){
				hql=hql+" and bcou.couponNumber like '%"+couponNumber+"%'";
			}
			String bindOpratorName=request.getParameter("bindOpratorName");//投资人姓名改为投资人账号了
			if(bindOpratorName!=null&&!"".equals(bindOpratorName)){
				String sql = "from BpCustMember as bp where bp.loginname like '%"+bindOpratorName+"%'";
				System.out.println(sql);
				List<BpCustMember> list = this.getSession().createQuery(sql).list();
				String loginname="(";
				for(BpCustMember bp :list){
					loginname+=bp.getId()+",";
				}
				loginname=loginname+"0)";
				hql=hql+" and  bcou.belongUserId in "+loginname;
			}
			String activityNumber=request.getParameter("activityNumber");//活动编号
			if(activityNumber!=null&&!"".equals(activityNumber)){
				hql=hql+" and activity.activityNumber like '%"+activityNumber+"%'";
			}
			String useProjectNumber=request.getParameter("useProjectNumber");//投资项目编号
			if(useProjectNumber!=null&&!"".equals(useProjectNumber)){
				hql=hql+" and bcou.useProjectNumber like '%"+useProjectNumber+"%'";
			}
			String couponEndDate=request.getParameter("couponEndDate");
			String couponEndDate2=request.getParameter("couponEndDate2");
			if(couponEndDate!=null&&!"".equals(couponEndDate)){
				hql=hql+" and bcou.couponEndDate  >= '"+couponEndDate+" 0:00:00'";
			}
			
			if(couponEndDate2!=null&&!"".equals(couponEndDate2)){
				hql=hql+" and bcou.couponEndDate  <= '"+couponEndDate2+" 23:59:59'";
			}
			String useTime=request.getParameter("useTime");
			String useTime2=request.getParameter("useTime2");
			if(useTime!=null&&!"".equals(useTime)){
				hql=hql+" and bcou.useTime  >= '"+useTime+" 0:00:00'";
			}
			if(useTime2!=null&&!"".equals(useTime2)){
				hql=hql+" and bcou.useTime  <= '"+useTime2+" 23:59:59'";
			}
			if(limit!=null&&limit==00){
				hql=hql+" and bcou.couponStatus="+Short.valueOf("10");
			}
			if(couponstatus!=null&&!"".equals(couponstatus)&&couponstatus.equals("10")){
				hql=hql+" ORDER BY bcou.useTime desc ";

			}else{
				
				hql=hql+" ORDER BY bcou.bindOpraterDate desc ";
			}
			
		}
		System.out.println("sqlql="+hql);
		if(start!=null&&limit!=null){
			hql=hql+" limit "+start+","+(start+limit);
			List<BpCoupons> list = this.jdbcTemplate.query(hql,new rowMapperbDistribute());
			return list;
		}else{
			List<BpCoupons> list = this.jdbcTemplate.query(hql,new rowMapperbDistribute());
			return list;
		}
	}
	/**
	 * 已派发的展示实体
	 * @author Administrator
	 *
	 */
	class  rowMapperbDistribute implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			BpCoupons income = new BpCoupons();
			income.setCouponId(rs.getLong("couponId"));
			income.setCouponType(rs.getLong("couponType"));
			income.setCouponNumber(rs.getString("couponNumber"));
			income.setCouponValue(rs.getBigDecimal("couponValue"));
			income.setCouponMoney(rs.getBigDecimal("couponMoney"));
			income.setCouponStartDate(rs.getDate("couponStartDate"));
			income.setCouponEndDate(rs.getDate("couponEndDate"));
			income.setCouponStatus(rs.getShort("couponStatus"));
			income.setCreateDate(rs.getDate("createDate"));
			income.setResourceName(rs.getString("resourceName"));
			income.setResourceId(rs.getLong("resourceId"));
			income.setCouponResourceType(rs.getString("couponResourceType"));
			income.setBelongUserId(rs.getLong("belongUserId"));
			income.setBelongUserName(rs.getString("belongUserName"));
			income.setBindOpraterDate(rs.getDate("bindOpraterDate"));
			income.setUseProjectType(rs.getString("useProjectType"));
			income.setUseProjectId(rs.getLong("useProjectId"));
			income.setUseProjectName(rs.getString("useProjectName"));
			income.setUseProjectNumber(rs.getString("useProjectNumber"));
			income.setUseTime(rs.getDate("useTime"));
			income.setActivityNumber(rs.getString("activityNumber"));
			return income;
		}
		
	}
	
	@Override
	public List<BpCoupons> getActivityType(String Type, Long activityId,
			String bpCustMemberId) {
		String sql = "from BpCoupons where couponResourceType=? and resourceId=? and belongUserId=?";
		return this.getSession().createQuery(sql).setParameter(0, Type).setParameter(1, activityId).setParameter(2, Long.valueOf(bpCustMemberId)).list();
	}

	@Override
	public List<BpCoupons> getCouponEndDate(String endDate) {
		String hql="from BpCoupons AS cp where" +
				" cp.couponStatus=5 AND cp.couponEndDate>='"+endDate+" 00:00:00'"+
				" AND cp.couponEndDate<='"+endDate+" 23:59:59'";
		System.out.println(hql);
		return findByHql(hql);
	}
}