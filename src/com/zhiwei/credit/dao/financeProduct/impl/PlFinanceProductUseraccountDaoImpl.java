package com.zhiwei.credit.dao.financeProduct.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;

import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;

import com.zhiwei.credit.dao.financeProduct.PlFinanceProductUseraccountDao;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUseraccount;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlFinanceProductUseraccountDaoImpl extends BaseDaoImpl<PlFinanceProductUseraccount> implements PlFinanceProductUseraccountDao{

	public PlFinanceProductUseraccountDaoImpl() {
		super(PlFinanceProductUseraccount.class);
	}

	@Override
	public List<PlFinanceProductUseraccount> getUserAccountList(
			Map<String,String> request, PagingBean pb) {
	   String fistDay=DateUtil.getFirstDate(DateUtil.addDaysToDate(new Date(), -1),"first");
	   String endDay=DateUtil.getFirstDate(DateUtil.addDaysToDate(new Date(), -1),"end");
		// TODO Auto-generated method stub
		String sql="SELECT "+
					"p.id AS id, "+
					"p.userId AS userId, "+
					"p.userName AS userName, "+
					"p.userloginName AS userloginName, "+
					"p.openDate AS openDate, "+
					"p.modifyDate AS modifyDate, "+
					"p.productId AS productId, "+
					"p.productName AS productName, "+
					"p.accountStatus AS accountStatus, "+
					"p.companyId AS companyId, "+
					"sum(case when pinfo.dealStatus>0 then pinfo.amont else 0.0 END) AS currentMoney, "+
					"sum(case when pinfo.dealtype=3 then pinfo.amont else 0.0 END ) AS totalIntestMoney, "+
					"sum(case when pinfo.dealtype=1 then pinfo.amont else 0.0 END ) AS incomeMoney, "+
					"-sum(case when pinfo.dealtype=2 then pinfo.amont else 0.0 END) AS payMoney, "+
					"sum(case when (pinfo.dealtype=3 and pinfo.dealDate BETWEEN '"+fistDay+"' and '"+endDay+"' ) then pinfo.amont else 0.0 END) AS yesterdayEarn, "+
					"sum(case when pinfo.dealStatus=2 then pinfo.amont else 0.0 END) AS intestMoney, "+
					"sum(case when pinfo.dealStatus=1 then pinfo.amont else 0.0 END) AS onwayMoney "+
					"FROM  "+
					"`pl_finance_product_useraccount` AS p "+
					"LEFT JOIN pl_finance_product_useraccountinfo AS pinfo ON (p.userId = pinfo.userId and pinfo.dealStatus>=0)  where 1=1 ";
		
		    if(request!=null){
		    	String  accountType=request.get("accountStatus");
		    	if(accountType!=null&&!"".equals(accountType)){
		    		sql=sql+" and p.accountStatus="+Short.valueOf(accountType);
		    	}
		    	String  userId=request.get("userId");
		    	if(userId!=null&&!"".equals(userId)){
		    		sql=sql+" and p.userId="+Long.valueOf(userId.toString());
		    	}
		    	
		    	String  Intentday=request.get("Intentday");
		    	if(Intentday!=null&&!"".equals(Intentday)){
		    		sql=sql+" and pinfo.dealDate<'"+Intentday.toString()+" 00:00:00'";
		    	}
		    	String openDateS=request.get("openDateS");
		    	if(openDateS!=null&&!"".equals(openDateS)){
		    		sql=sql+" and p.openDate>='"+openDateS+" 00:00:00'";
		    	}
		    	String openDateE=request.get("openDateE");
		    	if(openDateE!=null&&!"".equals(openDateE)){
		    		sql=sql+" and p.openDate<='"+openDateE+" 23:59:59'";
		    	}
		    	String userName=request.get("userName");
		    	if(userName!=null&&!"".equals(userName)){
		    		sql=sql+" and p.userName like \"%"+userName+"%\"";
		    	}
		    	String loginName=request.get("loginName");
		    	if(loginName!=null&&!"".equals(loginName)){
		    		sql=sql+" and p.userloginName like \"%"+loginName+"%\"";
		    	}
		    }
		
		     sql=sql +"  GROUP BY p.userId, p.productId";
		     System.out.println(sql);
		     List<PlFinanceProductUseraccount> list=this.getSession().createSQLQuery(sql).
		     										addScalar("id",Hibernate.LONG).
		     										addScalar("userId", Hibernate.LONG).
		     										addScalar("userName", Hibernate.STRING).
		     										addScalar("userloginName", Hibernate.STRING).
		     										addScalar("openDate", Hibernate.TIMESTAMP).
		     										addScalar("modifyDate", Hibernate.TIMESTAMP).
		     										addScalar("productId", Hibernate.LONG).
		     										addScalar("productName", Hibernate.STRING).
		     										addScalar("accountStatus", Hibernate.SHORT).
		     										addScalar("companyId", Hibernate.LONG).
		     										addScalar("currentMoney", Hibernate.BIG_DECIMAL).
		     										addScalar("totalIntestMoney", Hibernate.BIG_DECIMAL).
		     										addScalar("incomeMoney", Hibernate.BIG_DECIMAL).
		     										addScalar("payMoney", Hibernate.BIG_DECIMAL).
		     										addScalar("yesterdayEarn", Hibernate.BIG_DECIMAL).
		     										addScalar("intestMoney", Hibernate.BIG_DECIMAL).
		     										addScalar("onwayMoney", Hibernate.BIG_DECIMAL).
		     										setResultTransformer(Transformers.aliasToBean(PlFinanceProductUseraccount.class)).
		     									list();
		     if(pb!=null&&pb.getStart()!=null&&pb.getPageSize()!=null){
		    	 pb.setTotalItems(list!=null?list.size():0);
		    	 List<PlFinanceProductUseraccount> listP=this.getSession().createSQLQuery(sql).
							addScalar("id",Hibernate.LONG).
							addScalar("userId", Hibernate.LONG).
							addScalar("userName", Hibernate.STRING).
							addScalar("userloginName", Hibernate.STRING).
							addScalar("openDate", Hibernate.TIMESTAMP).
							addScalar("modifyDate", Hibernate.TIMESTAMP).
							addScalar("productId", Hibernate.LONG).
							addScalar("productName", Hibernate.STRING).
							addScalar("accountStatus", Hibernate.SHORT).
							addScalar("companyId", Hibernate.LONG).
							addScalar("currentMoney", Hibernate.BIG_DECIMAL).
							addScalar("totalIntestMoney", Hibernate.BIG_DECIMAL).
							addScalar("incomeMoney", Hibernate.BIG_DECIMAL).
							addScalar("payMoney", Hibernate.BIG_DECIMAL).
							addScalar("yesterdayEarn", Hibernate.BIG_DECIMAL).
							addScalar("intestMoney", Hibernate.BIG_DECIMAL).
							addScalar("onwayMoney", Hibernate.BIG_DECIMAL).
							setResultTransformer(Transformers.aliasToBean(PlFinanceProductUseraccount.class)).
							setFirstResult(pb.getStart()).
							setMaxResults(pb.getPageSize()).
							list();
		    	 return listP; 
		     }else{
		    	 return list; 
		     }
		
	}



}