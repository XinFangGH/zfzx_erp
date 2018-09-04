package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hurong.core.util.DateUtil;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTestDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTest;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTestService;

/**
 * 
 * @author 
 *
 */
public class PlMmOrderChildrenorTestServiceImpl extends BaseServiceImpl<PlMmOrderChildrenorTest> implements PlMmOrderChildrenorTestService{
	@SuppressWarnings("unused")
	private PlMmOrderChildrenorTestDao dao;
	@Resource
	private PlMmOrderChildrenOrService plMmOrderChildrenOrService;
	@Resource
	private PlMmObligatoryRightChildrenDao plMmObligatoryRightChildrenDao;
	@Resource
	private PlManageMoneyPlanBuyinfoDao plManageMoneyPlanBuyinfoDao;
	@Resource
	private PlManageMoneyPlanDao plManageMoneyPlanDao;
	public PlMmOrderChildrenorTestServiceImpl(PlMmOrderChildrenorTestDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public String matchForecast(PlManageMoneyPlanBuyinfo order) {
		BigDecimal currentGetedMoney=order.getCurrentGetedMoney();
		BigDecimal optimalDayRate=order.getOptimalDayRate();
		BigDecimal currentMatchingMoney=order.getCurrentMatchingMoney();
	  Date startDate= order.getStartinInterestTime();
	  Date endDate=order.getEndinInterestTime();
	  Date seachDate=startDate;
	  SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> map = new HashMap<String, String>();
		map.put("seachDate", sd.format(seachDate));
		map.put("currentMatchingMoney", "currentMatchingMoney");
	 
		List<PlMmObligatoryRightChildren> orchildrenlist= plMmObligatoryRightChildrenDao.listbysearch(null, map);
		String matchresult="common";
		firstfor:
		while(seachDate.compareTo(endDate)==-1){
	    	  matchingreleaseOneorder(seachDate,order);
	    	  for(int i=0;i<orchildrenlist.size();i++){
	    		  PlMmObligatoryRightChildren orchildren=orchildrenlist.get(i);

		    		 
		    		if(order.getCurrentMatchingMoney().compareTo(new BigDecimal(0))<=0){
		    			break;
		    			
		    		}
		    	  if(DateUtil.getDaysBetweenDate(sd.format(seachDate), sd.format(orchildren.getIntentDate()))>1){
		    		  matchresult=matchingsave(order,orchildren,seachDate);
		    		  orchildrenlist.remove(orchildren);
		    	  }
		    	  if(matchresult.equals("finish")){
			    		 break firstfor;
			    	 }
		    	  if(orchildrenlist.size()==0)break firstfor;
		    	
	    		  
	    	  }

	      
	    	
	    	 seachDate= DateUtil.addDaysToDate(seachDate,1);
		  
	  }
	 	order.setCurrentGetedMoney(currentGetedMoney);
		order.setOptimalDayRate(optimalDayRate);
		order.setCurrentMatchingMoney(currentMatchingMoney);
		
		plManageMoneyPlanBuyinfoDao.save(order);
		
		 
		 
		return "";
	
	}
public String matchingsave(PlManageMoneyPlanBuyinfo order,PlMmObligatoryRightChildren	orchildren,Date seachDate) {
		String matchresult="common";
		BigDecimal notPromisIncome=order.getPromisIncomeSum().subtract(order.getCurrentGetedMoney());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PlMmOrderChildrenorTest ordermatchorchildren=new PlMmOrderChildrenorTest();
		ordermatchorchildren.setMatchingState(0);
		ordermatchorchildren.setMmName(order.getMmName());
		ordermatchorchildren.setMmplanId(order.getPlManageMoneyPlan().getMmplanId());
		ordermatchorchildren.setParentOrBidId(orchildren.getParentOrBidId());
		ordermatchorchildren.setParentOrBidName(orchildren.getParentOrBidName());
		ordermatchorchildren.setInvestPersonId(order.getInvestPersonId());
		ordermatchorchildren.setInvestPersonName(order.getInvestPersonName());
		ordermatchorchildren.setChildrenorId(orchildren.getChildrenorId());
		ordermatchorchildren.setOrderId(order.getOrderId());
		ordermatchorchildren.setChildrenOrDayRate(orchildren.getDayRate());
		
		if(order.getCurrentMatchingMoney().compareTo(orchildren.getAvailableMoney())==1){
			
			ordermatchorchildren.setMatchingMoney(orchildren.getAvailableMoney());  
		}else{
			ordermatchorchildren.setMatchingMoney(order.getCurrentMatchingMoney());  
		}
		ordermatchorchildren.setMatchingStartDate(seachDate);  
		
		if(ordermatchorchildren.getMatchingMoney().compareTo(new BigDecimal(0))==0){
			matchresult="MatchingMoneyequalslin";
			return matchresult;
			
		}
			
		
		
		int day=DateUtil.getDaysBetweenDate(sdf.format(order.getEndinInterestTime()),sdf.format(orchildren.getIntentDate()));
		if(day>0){
			ordermatchorchildren.setMatchingEndDate(order.getEndinInterestTime());
			ordermatchorchildren.setMatchingEndDateType(1);
		}else{
			ordermatchorchildren.setMatchingEndDate(orchildren.getIntentDate());
			ordermatchorchildren.setMatchingEndDateType(2);
			
			
		}
		
		int matchingLimit=DateUtil.getDaysBetweenDate(sdf.format(ordermatchorchildren.getMatchingStartDate()),sdf.format(ordermatchorchildren.getMatchingEndDate()));
		if(matchingLimit<=0){
			return "";
			
		}
		ordermatchorchildren.setMatchingLimit(matchingLimit);
		BigDecimal thismatchgetMoney=ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate().multiply(new BigDecimal(matchingLimit))).divide(new BigDecimal(100)) ;
		ordermatchorchildren.setMatchingGetMoney(thismatchgetMoney);
		order.setCurrentGetedMoney(order.getCurrentGetedMoney().add(thismatchgetMoney));
	
		
		int  days=DateUtil.getDaysBetweenDate(sdf.format(ordermatchorchildren.getMatchingEndDate()),sdf.format(order.getEndinInterestTime()));
		if(days==0){
			order.setOptimalDayRate(new BigDecimal(0));
		}else{
			BigDecimal optimalDayRate=order.getPromisIncomeSum().subtract(order.getCurrentGetedMoney()).divide(new BigDecimal(days),10,BigDecimal.ROUND_HALF_UP);
			order.setOptimalDayRate(optimalDayRate);
			
		}
		
	
		if(order.getPromisIncomeSum().subtract(order.getCurrentGetedMoney()).compareTo(new BigDecimal(0))==-1){  
		  matchresult="finish";	
		 int  neddday=notPromisIncome.multiply(new BigDecimal(100)).divide(ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate()),100,BigDecimal.ROUND_HALF_DOWN).intValue();
		 ordermatchorchildren.setMatchingEndDate(DateUtil.addDaysToDate(ordermatchorchildren.getMatchingStartDate(), neddday));
		 ordermatchorchildren.setMatchingLimit(neddday);
		 ordermatchorchildren.setMatchingEndDateType(3);
		 
		 thismatchgetMoney=ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate().multiply(new BigDecimal(neddday))).divide(new BigDecimal(100)) ;
		 ordermatchorchildren.setMatchingGetMoney(thismatchgetMoney);
		 order.setCurrentGetedMoney(order.getCurrentGetedMoney().add(thismatchgetMoney));
		 
		 order.setOptimalDayRate(new BigDecimal(0));
		}
		
		
		dao.save(ordermatchorchildren);
	
	
		order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().subtract(ordermatchorchildren.getMatchingMoney()));
		plManageMoneyPlanBuyinfoDao.save(order);
		
		
		return matchresult;
		
	}
public String matchingreleaseOneorder(Date date,PlManageMoneyPlanBuyinfo order){
	SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
	 List<PlMmOrderChildrenorTest> omatchorclist=dao.listupdate(sd.format(date),order.getOrderId());
	    for(PlMmOrderChildrenorTest o:omatchorclist){
	    	
	    	PlMmObligatoryRightChildren orchildren=	plMmObligatoryRightChildrenDao.get(o.getChildrenorId());
	    	PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanDao.get(o.getMmplanId());
	    	matchingrelease(o,order,orchildren,plManageMoneyPlan);
	    }
		return "";
}
public String matchingrelease(PlMmOrderChildrenorTest o,PlManageMoneyPlanBuyinfo order,PlMmObligatoryRightChildren	orchildren,PlManageMoneyPlan plManageMoneyPlan) {
	

	
	if(o.getMatchingEndDateType()==1){
		
	 
	}else if(o.getMatchingEndDateType()==2){
		
		
	//	if(plManageMoneyPlan.getIsCyclingLend().equals(Integer.valueOf("1"))){
	//	  order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().add(o.getMatchingMoney()).add(o.getMatchingGetMoney()));
	//	}else{
			
			 order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().add(o.getMatchingMoney()));
	//	}
	}else if(o.getMatchingEndDateType()==3){  
		
		/*
		if(plManageMoneyPlan.getIsCyclingLend()==1){
		  order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().add(o.getMatchingMoney()).add(o.getMatchingGetMoney()));
		}else{
			
			 order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().add(o.getMatchingMoney()));
		}*/
	}
	
	o.setMatchingState(1); //
	dao.save(o);

	return "";
}
}