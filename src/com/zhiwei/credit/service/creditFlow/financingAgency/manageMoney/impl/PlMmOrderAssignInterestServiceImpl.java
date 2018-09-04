package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hurong.core.util.DateUtil;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestDao;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyType;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.AssignInerestGenerate;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyTypeService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrService;

/**
 * 
 * @author 
 *
 */
public class PlMmOrderAssignInterestServiceImpl extends BaseServiceImpl<PlMmOrderAssignInterest> implements PlMmOrderAssignInterestService{
	@SuppressWarnings("unused")
	private PlMmOrderAssignInterestDao dao;
	@Resource
	private PlManageMoneyTypeService plManageMoneyTypeService;
	@Resource
	private PlManageMoneyPlanBuyinfoDao plManageMoneyPlanBuyinfoDao;
	@Resource
	private PlMmOrderChildrenOrService plMmOrderChildrenOrService;
	@Resource
	private BpCouponsService bpCouponsService;
	private static String riskProtection ="riskRate";
	private static String loanInterest ="loanInterest";
	private static String principalRepayment ="principalRepayment";
	public PlMmOrderAssignInterestServiceImpl(PlMmOrderAssignInterestDao dao) {
		super(dao);
		this.dao=dao;
	}
   private static BigDecimal lin=new BigDecimal(0);
   /**
    * 理财计划奖励台账
    */
   @Override
   public String createAssignCouponsInerestlist(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan) {
	   
	   AssignInerestGenerate assignInerestGenerate=new AssignInerestGenerate(orderinfo,plan);
	   BpCoupons bpCoupons=null;
	   if(orderinfo.getCouponId()!=null&&!orderinfo.equals("")){
		    bpCoupons = bpCouponsService.get(orderinfo.getCouponId());
	   }
	   List<PlMmOrderAssignInterest> 	list=assignInerestGenerate.createCouponsPlMmOrderAssignInterest(orderinfo,plan,bpCoupons);
	   for(PlMmOrderAssignInterest a:list){
		   
		   dao.save(a);
	   }
	   return "";	
   }
	@Override
	public String createAssignInerestlist(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan) {
		
	
	AssignInerestGenerate assignInerestGenerate=new AssignInerestGenerate(orderinfo,plan);
	List<PlMmOrderAssignInterest> 	list=assignInerestGenerate.createPlMmOrderAssignInterest();

	// dao.saveList(list);
	 for(PlMmOrderAssignInterest a:list){
		 dao.save(a);
	 }
	
	return "";	
		
		
		
		
		
		
		
		
		
		/*
		   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		   Date startDate1=null;
		   int startDateddate=0;
		   String startdatestr=null;
		   String nextstartdatestr=null;
		  if(null!=plan){
			int investlimit =plan.getInvestlimit();
			int periods=1;
				if(plan.getIsCyclingLend()==1){
					BigDecimal baseMoney=orderinfo.getBuyMoney();
					BigDecimal summoney=lin;
					for(int i=1;i<=investlimit;i++){
						summoney=summoney.add(baseMoney.multiply(orderinfo.getPromisMonthRate()).divide(new BigDecimal(100)));
						baseMoney=orderinfo.getBuyMoney().add(summoney);
					 }
					PlMmOrderAssignInterest ai=new PlMmOrderAssignInterest();
					ai=create(orderinfo.getOrderId(),loanInterest,plan.getKeystr(),
							1,summoney
							,DateUtil.addMonthsToDate(orderinfo.getStartinInterestTime(), investlimit),
							orderinfo.getInvestPersonId(),orderinfo.getInvestPersonName(),orderinfo.getMmName(),orderinfo.getPlManageMoneyPlan().getMmplanId(),
							lin);
					dao.save(ai);
					if(null!=plan.getRiskProtectionRate()&&plan.getRiskProtectionRate().compareTo(new BigDecimal(0))!=0){
						PlMmOrderAssignInterest ai1=new PlMmOrderAssignInterest();
						ai1=create(ai.getOrderId(),riskProtection,ai.getKeystr(),
								ai.getPeriods(),new BigDecimal(0)
								,ai.getIntentDate(),
								ai.getInvestPersonId(),ai.getInvestPersonName(),ai.getMmName(),ai.getMmplanId(),
								ai.getIncomeMoney().multiply(plan.getRiskProtectionRate()).divide(new BigDecimal(100)));
						dao.save(ai1);
					}
				}else{
					if(null!=plan.getIsOne()&&plan.getIsOne()==1){
						PlMmOrderAssignInterest ai=new PlMmOrderAssignInterest();
				
						ai=create(orderinfo.getOrderId(),loanInterest,plan.getKeystr(),
								1,orderinfo.getBuyMoney().multiply(orderinfo.getPromisMonthRate()).multiply(new BigDecimal(investlimit)).divide(new BigDecimal(100))
								,DateUtil.addMonthsToDate(orderinfo.getStartinInterestTime(), investlimit),
								orderinfo.getInvestPersonId(),orderinfo.getInvestPersonName(),orderinfo.getMmName(),orderinfo.getPlManageMoneyPlan().getMmplanId(),
								lin);
						dao.save(ai);
						if(null!=plan.getRiskProtectionRate()&&plan.getRiskProtectionRate().compareTo(new BigDecimal(0))!=0){
							PlMmOrderAssignInterest ai1=new PlMmOrderAssignInterest();
							ai1=create(ai.getOrderId(),riskProtection,ai.getKeystr(),
									ai.getPeriods(),new BigDecimal(0)
									,ai.getIntentDate(),
									ai.getInvestPersonId(),ai.getInvestPersonName(),ai.getMmName(),ai.getMmplanId(),
									ai.getIncomeMoney().multiply(plan.getRiskProtectionRate()).divide(new BigDecimal(100)));
							dao.save(ai1);
						}
					}else{
		
                       if(AppUtil.getProStr().equals("proj_daliantianchu")&&plan.getKeystr().equals("mmproduce")){
                    	String startDatemonth=""; 
						startdatestr=sd.format(orderinfo.getStartinInterestTime());
						 nextstartdatestr=sd.format(DateUtil.addMonthsToDate(orderinfo.getStartinInterestTime(),1));
						String[] arr=startdatestr.split("-");
						startDatemonth=startdatestr.split("-")[1];
						 startDateddate=Integer.valueOf(startdatestr.split("-")[2]);
						 try {
						if(startDateddate<=25){
							
							if(startdatestr.split("-")[1].equals("02")){
								String[] arr1=nextstartdatestr.split("-");
								 startDate1=sd.parse(arr1[0]+"-"+arr1[1]+"-30");
							
							}else{
							    
								startDate1=sd.parse(arr[0]+"-"+arr[1]+"-30");
								
							}
							
						}else{
							
							String[] arr1=nextstartdatestr.split("-");
							
							if(arr1[1].equals("02")){
								
								 startDate1=sd.parse(arr[0]+"-"+arr[1]+"-30");
							
							}else{
							    
								startDate1=sd.parse(arr1[0]+"-"+arr1[1]+"-30");
								
							}
							
						}
					 } catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					periods=investlimit;
					for(int i=1;i<=investlimit;i++){
						PlMmOrderAssignInterest ai=new PlMmOrderAssignInterest();
						ai.setIntentDate(DateUtil.addMonthsToDate(orderinfo.getStartinInterestTime(), i));
						if(AppUtil.getProStr().equals("proj_daliantianchu")&&plan.getKeystr().equals("mmproduce")){
							ai.setIntentDate(DateUtil.addMonthsToDate(startDate1, i-1));
							if(startDateddate<=25&&startdatestr.split("-")[1].equals("02")){
								
								ai.setIntentDate(DateUtil.addMonthsToDate(startDate1, i-2));
							}
                            if(startDateddate>=25&&nextstartdatestr.split("-")[1].equals("02")){
								
								ai.setIntentDate(DateUtil.addMonthsToDate(startDate1, i));
							}
							if(startDateddate>25&&i==investlimit){
								
								ai.setIntentDate(DateUtil.addMonthsToDate(orderinfo.getStartinInterestTime(), i));
								
							}
							
						}
					
						
						ai=create(orderinfo.getOrderId(),loanInterest,plan.getKeystr(),
								i,orderinfo.getBuyMoney().multiply(orderinfo.getPromisMonthRate()).divide(new BigDecimal(100))
								,ai.getIntentDate(),
								orderinfo.getInvestPersonId(),orderinfo.getInvestPersonName(),orderinfo.getMmName(),orderinfo.getPlManageMoneyPlan().getMmplanId()
								,lin);
						dao.save(ai);
						if(null!=plan.getRiskProtectionRate()&&plan.getRiskProtectionRate().compareTo(new BigDecimal(0))!=0){
							PlMmOrderAssignInterest ai1=new PlMmOrderAssignInterest();
							ai1=create(ai.getOrderId(),riskProtection,ai.getKeystr(),
									ai.getPeriods(),new BigDecimal(0)
									,ai.getIntentDate(),
									ai.getInvestPersonId(),ai.getInvestPersonName(),ai.getMmName(),ai.getMmplanId(),
									ai.getIncomeMoney().multiply(plan.getRiskProtectionRate()).divide(new BigDecimal(100)));
							dao.save(ai1);
						}
					 }
						
						 
					}
	//			}
					
			PlMmOrderAssignInterest ai2=new PlMmOrderAssignInterest();
			ai2=create(orderinfo.getOrderId(),principalRepayment,plan.getKeystr(),periods,orderinfo.getBuyMoney()
					,DateUtil.addMonthsToDate(orderinfo.getStartinInterestTime(), investlimit),orderinfo.getInvestPersonId()
					,orderinfo.getInvestPersonName(),orderinfo.getMmName(),orderinfo.getPlManageMoneyPlan().getMmplanId(),
					lin);
			dao.save(ai2);
		}
		

		
		return "";
	*/}
	public PlMmOrderAssignInterest create(Long orderId,String fundType,String keystr,
			int periods ,BigDecimal incomeMoney,Date intentDate,Long investPersonId
			,String investPersonName,String mmName,Long mmplanId,BigDecimal payMoney){
		
			PlMmOrderAssignInterest ai1=new PlMmOrderAssignInterest();
			ai1.setOrderId(orderId);
			ai1.setFundType(fundType);
			ai1.setKeystr(keystr);
			ai1.setPeriods(periods);
			ai1.setIncomeMoney(incomeMoney);
			ai1.setPayMoney(payMoney);
			ai1.setIntentDate(intentDate);
			ai1.setInvestPersonId(investPersonId);
			ai1.setInvestPersonName(investPersonName);
			ai1.setMmName(mmName);
			ai1.setAfterMoney(lin);
			ai1.setMmplanId(mmplanId);
			ai1.setIsValid(Short.valueOf("1"));
			ai1.setIsCheck(Short.valueOf("1"));
		return ai1;
	}
	@Override
	public PlMmOrderAssignInterest getFisrtByOrderId(Long mmplanId) {
		return this.dao.getFisrtByOrderId(mmplanId);
	}
	
	@Override
	public PlMmOrderAssignInterest deleteCoupons(Long mmPlanId, String fundType) {
		// TODO Auto-generated method stub
		return dao.deleteCoupons(mmPlanId, fundType);
	}
	@Override
	public List<PlMmOrderAssignInterest> listByEarlyRedemptionId(
			Long earlyRedemptionId) {
		
		return dao.listByEarlyRedemptionId(earlyRedemptionId);
	}
	
	@Override
	public List<PlMmOrderAssignInterest> getByPlanIdA(Long orderId,
			Long investPersonId, Long mmplanId, String fundType,Integer periods) {
		// TODO Auto-generated method stub
		return dao.getByPlanIdA(orderId, investPersonId, mmplanId, fundType,periods);
	}
	@Override
	public List<PlMmOrderAssignInterest> createList(
			PlEarlyRedemption plEarlyRedemption,Long orderId,Long earlyRedemptionId) {
		
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
//		PlManageMoneyPlanBuyinfo orderinfo=plEarlyRedemption.getPlManageMoneyPlanBuyinfo();
		PlManageMoneyPlanBuyinfo orderinfo=plManageMoneyPlanBuyinfoDao.get(orderId);  //上面注释的orderinfo等于null @zcb
		List<PlMmOrderAssignInterest> list=new ArrayList<PlMmOrderAssignInterest>();
		PlMmOrderAssignInterest a=null;
		List<PlMmOrderAssignInterest> list1=dao.listByEarlyDate(">='"+sd.format(plEarlyRedemption.getEarlyDate())+"'", orderinfo.getOrderId(),"('loanInterest')",plEarlyRedemption.getEarlyRedemptionId());
		if(null!=list1 && list1.size()>0){
			a=list1.get(0);
		}
		List<PlMmOrderAssignInterest> list2=dao.listByEarlyDate("<='"+sd.format(plEarlyRedemption.getEarlyDate())+"'", orderinfo.getOrderId(),"('loanInterest')",plEarlyRedemption.getEarlyRedemptionId());
		Date startDate=orderinfo.getStartinInterestTime();
		if(null!=list2 && list2.size()>0){
			PlMmOrderAssignInterest a1=list2.get(list2.size()-1);
			startDate=a1.getIntentDate();
		}
		//提前赎回本金
		PlMmOrderAssignInterest obj1=this.createObject("principalRepayment", plEarlyRedemption.getEarlyMoney(), new BigDecimal(0), a.getPeriods(), plEarlyRedemption.getEarlyDate());
		
		if(obj1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(obj1);
		}
		PlMmOrderAssignInterest obj2=this.createObject("liquidatedDamages",  new BigDecimal(0),plEarlyRedemption.getEarlyMoney().multiply(plEarlyRedemption.getLiquidatedDamagesRate().divide(new BigDecimal(100))).add(plEarlyRedemption.getEarlyMoney().multiply(new BigDecimal(plEarlyRedemption.getPenaltyDays())).multiply(orderinfo.getPlManageMoneyPlan().getYeaRate().divide(new BigDecimal(36000),10,BigDecimal.ROUND_HALF_UP))), a.getPeriods(), plEarlyRedemption.getEarlyDate());
		if(obj2.getPayMoney().compareTo(new BigDecimal(0))!=0){
			list.add(obj2);
		}
		int days=DateUtil.getDaysBetweenDate(sd.format(startDate), sd.format(plEarlyRedemption.getEarlyDate()))+1;
		PlMmOrderAssignInterest obj3=this.createObject("loanInterest", plEarlyRedemption.getEarlyMoney().multiply(new BigDecimal(days)).multiply(orderinfo.getPlManageMoneyPlan().getYeaRate().divide(new BigDecimal(36000),10,BigDecimal.ROUND_HALF_UP)), new BigDecimal(0), a.getPeriods(), plEarlyRedemption.getEarlyDate());
		if(obj3.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(obj3);
		}
		return list;
	}
	@Override
	public List<PlMmOrderAssignInterest> mmplanupdateList(
			PlEarlyRedemption plEarlyRedemption) {    
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		PlManageMoneyPlanBuyinfo orderinfo=plEarlyRedemption.getPlManageMoneyPlanBuyinfo();
		List<PlMmOrderAssignInterest> list=new ArrayList<PlMmOrderAssignInterest>();
		List<PlMmOrderAssignInterest> list3=dao.listByEarlyDate(null, orderinfo.getOrderId(),"('loanInterest','principalRepayment')",plEarlyRedemption.getEarlyRedemptionId());
		for(PlMmOrderAssignInterest pai:list3){
			if(pai.getAfterMoney().compareTo(new BigDecimal("0"))==0){
				pai.setIsValid(Short.valueOf("1"));
	        	pai.setIsCheck(Short.valueOf("1"));
	        	dao.save(pai);
			}
	        
			}
     return null;
   }
	@Override
	public List<PlMmOrderAssignInterest> mmplancreateList(
			PlEarlyRedemption plEarlyRedemption) {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		PlManageMoneyPlanBuyinfo orderinfo=plEarlyRedemption.getPlManageMoneyPlanBuyinfo();
		PlManageMoneyPlan plan=orderinfo.getPlManageMoneyPlan();
		List<PlMmOrderAssignInterest> list=new ArrayList<PlMmOrderAssignInterest>();
		
	//	PlMmOrderAssignInterest a=null;
		int periods=orderinfo.getOrderlimit();
		List<PlMmOrderAssignInterest> list1=dao.listByEarlyDate(">='"+sd.format(plEarlyRedemption.getEarlyDate())+"'", orderinfo.getOrderId(),"('loanInterest')",plEarlyRedemption.getEarlyRedemptionId());
		
		if(null!=list1 && list1.size()>0){
			periods=list1.get(0).getPeriods();
		}
       
		List<PlMmOrderAssignInterest> list2=dao.listByEarlyDate(null, orderinfo.getOrderId(),"('loanInterest')",plEarlyRedemption.getEarlyRedemptionId());
		Date startDate=orderinfo.getStartinInterestTime();
	/*	if(null!=list2 && list2.size()>0){
			PlMmOrderAssignInterest a1=list2.get(list2.size()-1);
			startDate=a1.getIntentDate();
		}*/
		BigDecimal afterloanInterestMoney=new BigDecimal("0");
		for(PlMmOrderAssignInterest p:list2 ){
			afterloanInterestMoney =afterloanInterestMoney.add(p.getAfterMoney());
	    }
		
		//查询出本金台账
		PlMmOrderAssignInterest bj = null;
		List<PlMmOrderAssignInterest> plList = dao.listByOrderIdAndFundType(orderinfo.getOrderId().toString(),"principalRepayment");
		if(plList != null && plList.size()>0){
			bj = plList.get(0);
		}
		
     //提取支取的罚息
		
		PlMmOrderAssignInterest obj2=create(orderinfo.getOrderId(),"liquidatedDamages",plan.getKeystr(),
				periods, 
				lin,
				plEarlyRedemption.getEarlyDate(),
				orderinfo.getInvestPersonId(),orderinfo.getInvestPersonName(),orderinfo.getMmName(),orderinfo.getPlManageMoneyPlan().getMmplanId()
				,bj.getIncomeMoney().subtract(bj.getAfterMoney()).multiply(plEarlyRedemption.getLiquidatedDamagesRate()).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
		obj2.setAfterMoney(obj2.getPayMoney());
		//obj2.setFactDate(new Date());
		if(obj2.getPayMoney().compareTo(new BigDecimal(0))!=0){
			list.add(obj2);
		//	dao.save(obj2);
		}
		
		
		
		
		//利息
		int days=DateUtil.getDaysBetweenDate(sd.format(startDate), sd.format(plEarlyRedemption.getEarlyDate()));
		BigDecimal toDayMoney=plEarlyRedemption.getEarlyMoney().multiply(new BigDecimal(days)).multiply(orderinfo.getPlManageMoneyPlan().getYeaRate()).divide(new BigDecimal(36000),2,BigDecimal.ROUND_HALF_UP);
		BigDecimal loanInterestMoney=toDayMoney.subtract(afterloanInterestMoney);
		PlMmOrderAssignInterest obj3=create(orderinfo.getOrderId(),loanInterest,plan.getKeystr(),
				periods, 
				loanInterestMoney
				,plEarlyRedemption.getEarlyDate(),
				orderinfo.getInvestPersonId(),orderinfo.getInvestPersonName(),orderinfo.getMmName(),orderinfo.getPlManageMoneyPlan().getMmplanId()
				,lin);
		obj3.setAfterMoney(obj3.getIncomeMoney());
		//obj3.setFactDate(new Date());
		if(obj3.getIncomeMoney().compareTo(new BigDecimal(0))>0){
			list.add(obj3);
		//	dao.save(obj3);
		}
		
		//提前赎回本金
		PlMmOrderAssignInterest obj1=create(orderinfo.getOrderId(),principalRepayment,plan.getKeystr(),periods,bj.getIncomeMoney().subtract(bj.getAfterMoney())
				,plEarlyRedemption.getEarlyDate(),orderinfo.getInvestPersonId()
				,orderinfo.getInvestPersonName(),orderinfo.getMmName(),orderinfo.getPlManageMoneyPlan().getMmplanId(),
				lin);
		obj1.setAfterMoney(obj1.getIncomeMoney());
		//obj1.setFactDate(new Date());
		if(obj1.getIncomeMoney().compareTo(new BigDecimal(0))!=0){
			list.add(obj1);
			//	dao.save(obj1);
		}
		
		return list;
	}
	@Override
	public PlMmOrderAssignInterest createObject(String fundType,
			BigDecimal incomeMoney, BigDecimal payMoney,Integer periods, Date intentDate) {
		PlMmOrderAssignInterest plMmOrderAssignInterest=new PlMmOrderAssignInterest();
		plMmOrderAssignInterest.setFundType(fundType);
		plMmOrderAssignInterest.setIncomeMoney(incomeMoney);
		plMmOrderAssignInterest.setPayMoney(payMoney);
		plMmOrderAssignInterest.setPeriods(periods);
		plMmOrderAssignInterest.setIntentDate(intentDate);
		
		
		return plMmOrderAssignInterest;
	}
	@Override
	public void schedulingCalculateAssign() {
		List<PlManageMoneyPlanBuyinfo> list=plManageMoneyPlanBuyinfoDao.getListToEnd(new Date());
		for(PlManageMoneyPlanBuyinfo orderinfo:list){
			PlManageMoneyPlan plan=orderinfo.getPlManageMoneyPlan();
			BigDecimal calMoney=new BigDecimal("0");
			
			//如果获得的收益要大于承诺的收益，那就要重新生成最后一次的收益
			if(orderinfo.getCurrentGetedMoney().compareTo(orderinfo.getPromisIncomeSum())==1){
				BigDecimal maxpromisIncomeSum=  orderinfo.getBuyMoney().multiply(plan.getMaxYearRate()).multiply(new BigDecimal(orderinfo.getOrderlimit())).divide(new BigDecimal(3000),2,BigDecimal.ROUND_HALF_UP);
				//
				if(orderinfo.getCurrentGetedMoney().compareTo(maxpromisIncomeSum)==1){
					calMoney=maxpromisIncomeSum;
				}else{
					calMoney=orderinfo.getCurrentGetedMoney();
				}
				
				PlMmOrderAssignInterest ai2=new PlMmOrderAssignInterest();
				ai2=create(orderinfo.getOrderId(),loanInterest,plan.getKeystr(),orderinfo.getOrderlimit(),
						calMoney,
						orderinfo.getEndinInterestTime(),orderinfo.getInvestPersonId(),orderinfo.getInvestPersonName(),orderinfo.getMmName(),orderinfo.getPlManageMoneyPlan().getMmplanId(),
						lin);
				dao.save(ai2);
				
			}
		
		}
		
	}
	@Override
	public List<PlMmOrderAssignInterest> getByDealCondition(Long orderId,
			Long assignInterestId) {
		// TODO Auto-generated method stub
		return dao.getByDealCondition(orderId,assignInterestId);
	}
	@Override
	public List<PlMmOrderAssignInterest> createAssignInerestlistByFlow(PlManageMoneyPlanBuyinfo orderinfo, PlManageMoneyPlan plan) {
		AssignInerestGenerate assignInerestGenerate=new AssignInerestGenerate(orderinfo,plan);
		List<PlMmOrderAssignInterest> 	list=assignInerestGenerate.createPlMmOrderAssignInterest();
		return list;
	}
	@Override
	public List<PlMmOrderAssignInterest> listByOrderId(String orderId) {
		return dao.findByHql("from PlMmOrderAssignInterest as p where p.orderId = ? ",new Object[]{Long.valueOf(orderId)} );
	}
	
	@Override
	public String createExperienceAssignInerestlist(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan) {
		AssignInerestGenerate assignInerestGenerate=new AssignInerestGenerate(orderinfo,plan);
		List<PlMmOrderAssignInterest> 	list=assignInerestGenerate.createExperiencePlMmOrderAssignInterest();
		for(PlMmOrderAssignInterest a:list){
			 dao.save(a);
		}
		return "";	
	}
	
	@Override
	public List<PlMmOrderAssignInterest> listByOrderIdAndFactDate(Long orderId) {
		return dao.findByHql("from PlMmOrderAssignInterest as p where p.orderId = ? and p.factDate is null ",new Object[]{Long.valueOf(orderId)} );
	}
	@Override
	public List<PlMmOrderAssignInterest> listByMmPlanId(Long mmplanId,
			String keystr) {
		return dao.listByMmPlanId(mmplanId,keystr);
	}
	@Override
	public List<PlMmOrderAssignInterest> getCouponsList(PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.getCouponsList(pb, map);
	}
	@Override
	public List<PlMmOrderAssignInterest> getByPlanIdAndPeriod(Long planId,
			Long payintentPeriod, String fundType) {
		// TODO Auto-generated method stub
		return dao.getByPlanIdAndPeriod(planId, payintentPeriod, fundType);
	}
	@Override
	public List<PlMmOrderAssignInterest> getRaiseBpfundIntent(Long planId) {
		// TODO Auto-generated method stub
		return dao.getRaiseBpfundIntent(planId);
	}
	@Override
	public List<PlMmOrderAssignInterest> getdistributeMoneyAssign(Long planId,
			Long payintentPeriod, String fundType) {
		// TODO Auto-generated method stub
		return dao.getdistributeMoneyAssign(planId, payintentPeriod, fundType);
	}
	@Override
	public List<PlMmOrderAssignInterest> getCouponsAssignIncome(PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getCouponsAssignIncome(pb);
	}
	
	@Override
	public String createUPlanAssignInerestlist(PlManageMoneyPlanBuyinfo orderinfo,PlManageMoneyPlan plan) {
		AssignInerestGenerate assignInerestGenerate=new AssignInerestGenerate(orderinfo,plan);
		List<PlMmOrderAssignInterest> 	list=assignInerestGenerate.createUPlanPlMmOrderAssignInterest();
		for(PlMmOrderAssignInterest a:list){
		 dao.save(a);
		}
		return "";	
	}
	@Override
	public List<PlMmOrderAssignInterest> getAllInterest(PagingBean pb,
			Map<String, String> map) {
		return dao.getAllInterest(pb,map);
	}
	@Override
	public List<PlMmOrderAssignInterest> getAllByMmproduceInterest(
			PagingBean pb, Map<String, String> map) {
		return dao.getAllByMmproduceInterest(pb,map);
	}
	@Override
	public List<PlMmOrderAssignInterest> getByRequestNo(Map<String, String> map) {
		return dao.getByRequestNo(map);
	}

}