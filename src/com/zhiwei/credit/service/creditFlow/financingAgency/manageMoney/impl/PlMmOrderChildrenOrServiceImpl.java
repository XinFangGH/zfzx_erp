package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTestDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTest;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrService;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;

/**
 * 
 * @author 
 *
 */
public class PlMmOrderChildrenOrServiceImpl extends BaseServiceImpl<PlMmOrderChildrenOr> implements PlMmOrderChildrenOrService{
	@SuppressWarnings("unused")
	private PlMmOrderChildrenOrDao dao;
	@Resource
	private PlMmObligatoryRightChildrenDao plMmObligatoryRightChildrenDao;
	@Resource
	private PlManageMoneyPlanBuyinfoDao plManageMoneyPlanBuyinfoDao;
	@Resource
	private PlManageMoneyPlanDao plManageMoneyPlanDao;
	@Resource
	private PlMmOrderChildrenorTestDao plMmOrderChildrenorTestDao;
	@Resource
	private BatchRunRecordService batchRunRecordService;
	public PlMmOrderChildrenOrServiceImpl(PlMmOrderChildrenOrDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public String schedulerMatching(Date seachDate,String keystr) {
		//第一步
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> map = new HashMap<String, String>();
		if(null==seachDate){
			seachDate=new Date();
		}
		
		map.put("seachDate", sd.format(seachDate));
		
		//计算最优日化利率
		map.put("currentMatchingMoney", "optimalDayRate");
	    List<PlManageMoneyPlanBuyinfo> orderlist=	plManageMoneyPlanBuyinfoDao.listbysearch(null, map);
	    for(PlManageMoneyPlanBuyinfo o: orderlist){
	    	BigDecimal optimalDayRate=calculateOptimalDayRate(o,seachDate);
	    	o.setOptimalDayRate(optimalDayRate);
			plManageMoneyPlanBuyinfoDao.save(o);
	    }
	    //自动匹配
	    //start 债权库已经进行了拆分，原来方法无法使用，此方法为最新方法，针对债权库拆分以后的项目
	    map.put("currentMatchingMoney", "currentMatchingMoney");
	    if(null !=keystr && !"".equals(keystr)){//用来分离处理 定时器全部自动匹配 和 手动全部自动匹配
	    	map.put("keystr", keystr);
	    }
	    orderlist=	plManageMoneyPlanBuyinfoDao.listbysearch(null,map);
	    
	  //跑批记录 
		AppUser appUser = ContextUtil.getCurrentUser();
		String pushUserName = "定时跑批";
		Long pushUserId = null;
		if(null != appUser && !"".equals(appUser)){
			pushUserName = appUser.getFullname();
			pushUserId = appUser.getUserId();
		}
		BatchRunRecord batchRunRecord = new BatchRunRecord();
		batchRunRecord.setRunType(BatchRunRecord.HRY_1002);
		batchRunRecord.setAppUserId(pushUserId);
		batchRunRecord.setAppUserName(pushUserName);
		batchRunRecord.setStartRunDate(new Date());
		batchRunRecord.setTotalNumber(Long.valueOf(orderlist != null ?orderlist.size() : 0));
		batchRunRecord.setHappenAbnorma("正常");
		
	    if(null !=orderlist && orderlist.size()>0){
   	    	for(PlManageMoneyPlanBuyinfo order : orderlist){
   	    		try {
   	    			map.put("childType", order.getKeystr()+"Or");
   	   	    		map.put("receiver", order.getPlManageMoneyPlan().getMoneyReceiver());
   	   	    		List<PlMmObligatoryRightChildren> orchildrenlist= plMmObligatoryRightChildrenDao.listbysearch(null, map);
   	   	    		for(PlMmObligatoryRightChildren orchildren:orchildrenlist){
   	   	    			//匹配开始
   	   		    		if(order.getCurrentMatchingMoney().compareTo(new BigDecimal(0))<=0){
   	   		    			break;
   	   		    		}
   	   		    		matchingsave(order,orchildren,seachDate,null,null);
   	   		    		//匹配结束
   	   		    		
   	   		    		//计算子债权剩余价值
   	   		    		BigDecimal surplusValueMoney=calculateSurplusValueMoney(orchildren,seachDate);
   	   					orchildren.setSurplusValueMoney(surplusValueMoney);
   	   			    	plMmObligatoryRightChildrenDao.save(orchildren);
   	   		    	}
	   	 		} catch (Exception e) {
		   	 		String ids="";
					if(null != batchRunRecord.getIds()){
						ids =batchRunRecord.getIds() + "," + order.getOrderId();
					}else{
						ids = order.getOrderId().toString();
					}
					batchRunRecord.setIds(ids);
					batchRunRecord.setHappenAbnorma("异常");
	   	 			e.printStackTrace();
	   	 		}
   	    	}
	   	}
	    //end
	    batchRunRecord.setEndRunDate(new Date());
		batchRunRecordService.save(batchRunRecord);
	    //第二步 
	    matchingreleaseall(seachDate);
		return "";
	}
	@Override
	public String matchingreleaseall(Date date){
		 SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("matchingreleaseall new Date==="+sd1.format(new Date()));
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		 List<PlMmOrderChildrenOr> omatchorclist=dao.listupdate(sd.format(date));
		 System.out.println("omatchorclist.size()==="+omatchorclist.size());
		    for(PlMmOrderChildrenOr o:omatchorclist){
		   	    System.out.println("o.getChildrenorId()"+o.getChildrenorId()+"o.getInvestPersonName()==="+o.getInvestPersonName()+"--o.getParentOrBidName()=="+o.getParentOrBidName());
		    	PlMmObligatoryRightChildren orchildren=	plMmObligatoryRightChildrenDao.get(o.getChildrenorId());
		    	PlManageMoneyPlanBuyinfo order=plManageMoneyPlanBuyinfoDao.get(o.getOrderId());
		    	PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanDao.get(o.getMmplanId());
		    	matchingrelease(date,o,order,orchildren);
		    }
			System.out.println("matchingreleaseall new Date==="+sd1.format(new Date()));
			return "";
	}
	@Override
	public String matchingrelease(Date date,PlMmOrderChildrenOr o,PlManageMoneyPlanBuyinfo order,PlMmObligatoryRightChildren	orchildren) {
		

    	
    	if(o.getMatchingEndDateType()==1){
    		if(orchildren.getIntentDate()!=null&&orchildren.getIntentDate().compareTo(date)==1){
    			orchildren.setAvailableMoney(orchildren.getAvailableMoney().add(o.getMatchingMoney()));
    			
    		}
    		
    	 
    	}else if(o.getMatchingEndDateType()==2){
    		
    		
    /*		if(plManageMoneyPlan.getIsCyclingLend()==1){
    		  order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().add(o.getMatchingMoney()).add(o.getMatchingGetMoney()));
    		}else{*/
    			if(order!=null&&order.getEndinInterestTime()!=null&&order.getEndinInterestTime().compareTo(date)==1){
    				order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().add(o.getMatchingMoney()));
    				
    			}
    			 
    	//	}
    	}else if(o.getMatchingEndDateType()==3){  
    		if(orchildren.getIntentDate()!=null&&orchildren.getIntentDate().compareTo(date)==1){
    			orchildren.setAvailableMoney(orchildren.getAvailableMoney().add(o.getMatchingMoney()));
    			
    		}
    		
/*    		if(plManageMoneyPlan.getIsCyclingLend()==1){
    		  order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().add(o.getMatchingMoney()).add(o.getMatchingGetMoney()));
    		}else{*/
    			
    		if(order!=null&&order.getEndinInterestTime()!=null&&order.getEndinInterestTime().compareTo(date)==1){
				order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().add(o.getMatchingMoney()));
				
			}
    //		}
    	}else if(o.getMatchingEndDateType()==4){ //提前赎回
    		
    		
    		if(orchildren.getIntentDate()!=null&&orchildren.getIntentDate().compareTo(date)==1){
    			orchildren.setAvailableMoney(orchildren.getAvailableMoney().add(o.getMatchingMoney()));
    			
    		}
    	}
    /*	if(order.getOptimalDayRate().compareTo(new BigDecimal(0))==0){
    		
    		order.setCurrentMatchingMoney((new BigDecimal(0)));
    	}*/
    	o.setMatchingState(1); //
    	dao.save(o);
    
		return "";
	}
	@Override
	public String matchingsave(PlManageMoneyPlanBuyinfo order,PlMmObligatoryRightChildren	orchildren,Date seachDate,BigDecimal matchingmoney,AppUser appUser) {
		BigDecimal notPromisIncome=order.getPromisIncomeSum().subtract(order.getCurrentGetedMoney());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PlMmOrderChildrenOr ordermatchorchildren=new PlMmOrderChildrenOr();
		if(null !=appUser){
			
			ordermatchorchildren.setMatchPersonId(appUser.getUserId());
			ordermatchorchildren.setMatchPersonName(appUser.getFullname());
		}
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
		if(null==matchingmoney){
			if(order.getCurrentMatchingMoney().compareTo(orchildren.getAvailableMoney())==1){
				
				ordermatchorchildren.setMatchingMoney(orchildren.getAvailableMoney());  
			}else{
				ordermatchorchildren.setMatchingMoney(order.getCurrentMatchingMoney());  
			}
		}else{
			//防止没有页面刷新，匹配超额，出现可匹配金额为负数
			if(matchingmoney.compareTo(order.getCurrentMatchingMoney())==-1 || matchingmoney.compareTo(order.getCurrentMatchingMoney())==0){
				ordermatchorchildren.setMatchingMoney(matchingmoney);
			}else{
				return "";
			}
		}
		
		if(ordermatchorchildren.getMatchingMoney().compareTo(new BigDecimal(0))==0){
			return "";
			
		}
		ordermatchorchildren.setMatchingStartDate(seachDate);  
		
		 if(order.getStartinInterestTime().compareTo(seachDate)==1 || orchildren.getStartDate().compareTo(seachDate)==1){
		    	return "";
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
			return "matchingdayisunused";
			
		}
		ordermatchorchildren.setMatchingLimit(matchingLimit);
		BigDecimal thismatchgetMoney=ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate().multiply(new BigDecimal(matchingLimit))).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP) ;
		ordermatchorchildren.setMatchingGetMoney(thismatchgetMoney);
		order.setCurrentGetedMoney(order.getCurrentGetedMoney().add(thismatchgetMoney));
	
		order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().subtract(ordermatchorchildren.getMatchingMoney()));
//		dao.save(ordermatchorchildren);
		BigDecimal optimalDayRate=calculateOptimalDayRate(order,seachDate);
		order.setOptimalDayRate(optimalDayRate);
		
	/*
		if(order.getPromisIncomeSum().subtract(order.getCurrentGetedMoney()).compareTo(new BigDecimal(0))==-1){  
			
	
	//	 order.setCurrentMatchingMoney(new BigDecimal(0));
		 
		 if(null==appUser){
				int  neddday=notPromisIncome.multiply(new BigDecimal(100)).divide(ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate()),100,BigDecimal.ROUND_HALF_DOWN).intValue();
				 ordermatchorchildren.setMatchingEndDate(DateUtil.addDaysToDate(ordermatchorchildren.getMatchingStartDate(), neddday));
				 ordermatchorchildren.setMatchingLimit(neddday);
				 ordermatchorchildren.setMatchingEndDateType(3);

				  matchingLimit=DateUtil.getDaysBetweenDate(sdf.format(ordermatchorchildren.getMatchingStartDate()),sdf.format(ordermatchorchildren.getMatchingEndDate()));
					if(matchingLimit<=0){
						return "matchingdayisunused";
						
					}
				 
				 thismatchgetMoney=ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate().multiply(new BigDecimal(neddday))).divide(new BigDecimal(100)) ;
				 ordermatchorchildren.setMatchingGetMoney(thismatchgetMoney);
				 order.setCurrentGetedMoney(order.getPromisIncomeSum().subtract(notPromisIncome).add(thismatchgetMoney));
				 
				 order.setOptimalDayRate(new BigDecimal(0));
			 
		  }else{
			  
			//  order.setOptimalDayRate(new BigDecimal(0));
			  
		  }
		}*/
		
	//	dao.save(ordermatchorchildren);
		mergematching(ordermatchorchildren);
	    
		if(order.getFirstProjectIdstr().indexOf(","+orchildren.getParentOrBidId().toString())==-1){
			order.setFirstProjectIdstr(","+orchildren.getParentOrBidId()+order.getFirstProjectIdstr());
			order.setFirstProjectIdcount(order.getFirstProjectIdcount()+1);
		}
		plManageMoneyPlanBuyinfoDao.save(order);
		

		BigDecimal surplusValueMoney=calculateSurplusValueMoney(orchildren,seachDate);
		orchildren.setSurplusValueMoney(surplusValueMoney);
		orchildren.setAvailableMoney(orchildren.getAvailableMoney().subtract(ordermatchorchildren.getMatchingMoney()));
		plMmObligatoryRightChildrenDao.save(orchildren);
		
		return "";
		
	}
	
	public String mergematching(PlMmOrderChildrenOr o){
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");	
	     List<PlMmOrderChildrenOr>	s=dao.listbysame(sd.format(o.getMatchingStartDate()),sd.format(o.getMatchingEndDate()),o.getOrderId(),o.getChildrenorId());
		 if(null !=s&&s.size()!=0){
			 PlMmOrderChildrenOr a=s.get(0);
			 a.setMatchingMoney(a.getMatchingMoney().add(o.getMatchingMoney()));
			 a.setMatchingGetMoney(a.getMatchingGetMoney().add(o.getMatchingGetMoney()));
			 dao.save(a);
		 }else{
			 dao.save(o); 
			 
		 }
		return "";
		
	}
	public String firstmatchingsave(PlManageMoneyPlanBuyinfo order,PlMmObligatoryRightChildren	orchildren,Date seachDate) {
		BigDecimal ordermatchingmoney=order.getBuyMoney().divide(new BigDecimal(PlManageMoneyPlanBuyinfo.pic_count),100,BigDecimal.ROUND_DOWN);
		BigDecimal notPromisIncome=order.getPromisIncomeSum().subtract(order.getCurrentGetedMoney());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PlMmOrderChildrenOr ordermatchorchildren=new PlMmOrderChildrenOr();
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
		
		if(ordermatchingmoney.compareTo(orchildren.getAvailableMoney())==1){
			
			ordermatchorchildren.setMatchingMoney(orchildren.getAvailableMoney());  
		}else{
			ordermatchorchildren.setMatchingMoney(ordermatchingmoney);  
		}
		
		if(ordermatchorchildren.getMatchingMoney().compareTo(new BigDecimal(0))==0){
			return "";
			
		}
		 
		
	    if(order.getStartinInterestTime().compareTo(seachDate)==1 || orchildren.getStartDate().compareTo(seachDate)==1){
	    	return "";
	    }
			
	    ordermatchorchildren.setMatchingStartDate(seachDate); 
		
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
			return "matchingdayisunused";
			
		}
	System.out.println("==="+matchingLimit+"");
		ordermatchorchildren.setMatchingLimit(matchingLimit);
		BigDecimal thismatchgetMoney=ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate().multiply(new BigDecimal(matchingLimit))).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
		ordermatchorchildren.setMatchingGetMoney(thismatchgetMoney);
		order.setCurrentGetedMoney(order.getCurrentGetedMoney().add(thismatchgetMoney));
		
		order.setCurrentMatchingMoney(order.getCurrentMatchingMoney().subtract(ordermatchorchildren.getMatchingMoney()));
		dao.save(ordermatchorchildren);
		BigDecimal optimalDayRate=calculateOptimalDayRate(order,seachDate);
		
		order.setOptimalDayRate(optimalDayRate);
	/*	if(order.getPromisIncomeSum().subtract(order.getCurrentGetedMoney()).compareTo(new BigDecimal(0))==-1){  
			int  neddday=notPromisIncome.multiply(new BigDecimal(100)).divide(ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate()),100,BigDecimal.ROUND_HALF_DOWN).intValue();
			 ordermatchorchildren.setMatchingEndDate(DateUtil.addDaysToDate(ordermatchorchildren.getMatchingStartDate(), neddday));
			 ordermatchorchildren.setMatchingLimit(neddday);
			 ordermatchorchildren.setMatchingEndDateType(3);
			 
			  matchingLimit=DateUtil.getDaysBetweenDate(sdf.format(ordermatchorchildren.getMatchingStartDate()),sdf.format(ordermatchorchildren.getMatchingEndDate()));
				if(matchingLimit<=0){
					return "matchingdayisunused";
					
				}
	
				System.out.println("-----"+matchingLimit+"");
			 thismatchgetMoney=ordermatchorchildren.getMatchingMoney().multiply(ordermatchorchildren.getChildrenOrDayRate().multiply(new BigDecimal(neddday))).divide(new BigDecimal(100)) ;
			 ordermatchorchildren.setMatchingGetMoney(thismatchgetMoney);
			 order.setCurrentGetedMoney(order.getPromisIncomeSum().subtract(notPromisIncome).add(thismatchgetMoney));
			 
			 order.setOptimalDayRate(new BigDecimal(0));
		//	 order.setCurrentMatchingMoney(new BigDecimal(0));
		}
		*/
		
		dao.save(ordermatchorchildren);
		PlMmOrderChildrenorTest ordertext=new PlMmOrderChildrenorTest();
		BeanUtils.copyProperties(ordermatchorchildren,ordertext);
		ordertext.setMatchId(null);
		plMmOrderChildrenorTestDao.save(ordertext);
		
		
		if(order.getFirstProjectIdstr().indexOf(","+orchildren.getParentOrBidId().toString())==-1){
			order.setFirstProjectIdstr(","+orchildren.getParentOrBidId()+order.getFirstProjectIdstr());
			order.setFirstProjectIdcount(order.getFirstProjectIdcount()+1);
		}
		
		plManageMoneyPlanBuyinfoDao.save(order);
		
		
		BigDecimal surplusValueMoney=calculateSurplusValueMoney(orchildren,seachDate);
		orchildren.setSurplusValueMoney(surplusValueMoney);
		orchildren.setAvailableMoney(orchildren.getAvailableMoney().subtract(ordermatchorchildren.getMatchingMoney()));
		plMmObligatoryRightChildrenDao.save(orchildren);
		
		return "";
		
	}
	@Override
	public List<PlMmOrderChildrenOr> listbysearch(PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.listbysearch(pb, map);
	}

	@Override
	public String everyschedulerMatching() {
		Date seachDate=new Date();
		schedulerMatching(DateUtil.addDaysToDate(new Date(), -1),null);
		return "";
	}

	/**
	 * 计算债权剩余价值
	 * @param orchildren
	 * @param seachDate
	 * @return
	 */

	public BigDecimal calculateSurplusValueMoney(PlMmObligatoryRightChildren	orchildren,Date seachDate) {
		
		BigDecimal sum=new BigDecimal(0);
		BigDecimal usedsum=new BigDecimal(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<PlMmOrderChildrenOr> list=dao.listbyChildrenorId(orchildren.getChildrenorId());
		int  days=1;
		for(PlMmOrderChildrenOr l:list){
			 days=DateUtil.getDaysBetweenDate(sdf.format(l.getMatchingEndDate()),sdf.format(orchildren.getIntentDate()));
			sum=sum.add(new BigDecimal(days).multiply(l.getMatchingMoney()));
		}
	    days=DateUtil.getDaysBetweenDate(sdf.format(seachDate),sdf.format(orchildren.getIntentDate()));
		sum=sum.add(orchildren.getAvailableMoney().multiply(new BigDecimal(days)));
		

		return sum.multiply(orchildren.getDayRate()).divide(new BigDecimal(100));
	}
	
	/**
	 * 计算最优日化利率
	 * @param order
	 * @param seachDate
	 * @return
	 */
	public BigDecimal calculateOptimalDayRate(PlManageMoneyPlanBuyinfo order,Date seachDate) {
		
		BigDecimal sum=new BigDecimal(0);
		BigDecimal usedsum=new BigDecimal(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<PlMmOrderChildrenOr> list=dao.listbyorderid(order.getOrderId());
		int  days=1;
		for(PlMmOrderChildrenOr l:list){
			 days=DateUtil.getDaysBetweenDate(sdf.format(l.getMatchingEndDate()),sdf.format(order.getEndinInterestTime()));
			sum=sum.add(new BigDecimal(days).multiply(l.getMatchingMoney()));
		}
	    days=DateUtil.getDaysBetweenDate(sdf.format(seachDate),sdf.format(order.getEndinInterestTime()));
		sum=sum.add(order.getCurrentMatchingMoney().multiply(new BigDecimal(days)));
		BigDecimal optimalDayRate=new BigDecimal(0);
		if(sum.compareTo(new BigDecimal(0)) !=0){
			optimalDayRate=order.getPromisIncomeSum().subtract(order.getCurrentGetedMoney()).divide(sum,10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
	    }
		
		//如果当前已实现收益已经大于预期收益，最优日化利率则为0，不能成为负数
		if(optimalDayRate.compareTo(new BigDecimal(0)) ==-1){
			optimalDayRate=new BigDecimal(0);
		}
		return optimalDayRate;
	}
	@Override
	public String halfAutomatching(Date seachDate,String[]ids,String childType) {
		//第 一次匹配即收益为o的订单做第一次匹配要做特殊处理，即要分五分去匹配
	 	SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Map<String, String> map = new HashMap<String, String>();
		if(null==seachDate){
			seachDate=new Date();
		}
		map.put("seachDate", sd.format(seachDate));
		map.put("childType", childType);
		if(null!=ids){
			for(String id:ids){
				PlManageMoneyPlanBuyinfo order =plManageMoneyPlanBuyinfoDao.get(new Long(id));
				map.put("receiver",order.getPlManageMoneyPlan().getMoneyReceiver());
				List<PlMmObligatoryRightChildren> orchildrenlist= plMmObligatoryRightChildrenDao.listbysearch(null, map);
				if(null!=order.getEarlierOutDate() && !"".equals(order.getEarlierOutDate())){
					//防止页面未刷新，导致已提前赎回记录匹配
				}else{
					for(PlMmObligatoryRightChildren orchildren:orchildrenlist){
						if(order.getFirstProjectIdcount()==PlManageMoneyPlanBuyinfo.pic_count){//||order.getOptimalDayRate().equals(new BigDecimal(0))){
							break;
						}
						if(order.getFirstProjectIdstr().indexOf(","+orchildren.getParentOrBidId().toString())==-1){
							firstmatchingsave(order,orchildren,seachDate);
						}			    	
					}
				}
			}
		}
		return "";
	}

	@Override
	public List<PlMmOrderChildrenOr> listbyIds(String ids) {
		// TODO Auto-generated method stub
		return dao.listbyIds(ids);
	}
	
	@Override
	public String updateChildrenor(PlMmObligatoryRightChildren plMmObligatoryRightChildren) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<PlMmOrderChildrenOr> plcList = dao.listbyChildrenorId(plMmObligatoryRightChildren.getChildrenorId());
		if(null !=plcList && plcList.size()>0){
			for(PlMmOrderChildrenOr plOrderOr : plcList){
				//当匹配截止日大于修改后的债权到期日时，需要修改对应数据，反之，则不用修改
				if(plOrderOr.getMatchingEndDate().compareTo(plMmObligatoryRightChildren.getIntentDate())>0){
					//重新计算出的匹配天数
					int matchingLimit=DateUtil.getDaysBetweenDate(sdf.format(plOrderOr.getMatchingStartDate())
							,sdf.format(plMmObligatoryRightChildren.getIntentDate()));
					//重新计算出的收益
					BigDecimal thismatchgetMoney=plOrderOr.getMatchingMoney().multiply(plOrderOr.getChildrenOrDayRate()
							.multiply(new BigDecimal(matchingLimit))).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					//匹配结束日
					plOrderOr.setMatchingEndDate(plMmObligatoryRightChildren.getIntentDate());
					//匹配匹配期限
					plOrderOr.setMatchingLimit(matchingLimit);
					PlManageMoneyPlanBuyinfo planBuyinfo = plManageMoneyPlanBuyinfoDao.get(plOrderOr.getOrderId());
					BigDecimal money=planBuyinfo.getCurrentGetedMoney();
					money=money.subtract(plOrderOr.getMatchingGetMoney()).add(thismatchgetMoney);
					if(null !=planBuyinfo && "".equals(planBuyinfo)){
						planBuyinfo.setCurrentGetedMoney(money);
						plManageMoneyPlanBuyinfoDao.save(planBuyinfo);
					}
					//此匹配获得的收益
					plOrderOr.setMatchingGetMoney(thismatchgetMoney);
					dao.save(plOrderOr);
				}
			}
		}
		return "";
	}
}