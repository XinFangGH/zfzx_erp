package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hurong.credit.util.Common;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.util.MoneyFormat;
import com.zhiwei.credit.dao.creditFlow.common.CsBankDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemAccountDao;
import com.zhiwei.credit.dao.creditFlow.customer.common.EnterpriseBankDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrDao;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.creditAssignment.investInfoManager.Investproject;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderInfo;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderInfoService;
import com.zhiwei.credit.util.RMB;
import com.zhiwei.credit.util.xmlToWord.printOrder.POTable1;

/**
 * 
 * @author 
 *
 */
public class PlManageMoneyPlanBuyinfoServiceImpl extends BaseServiceImpl<PlManageMoneyPlanBuyinfo> implements PlManageMoneyPlanBuyinfoService{
	@SuppressWarnings("unused")
	private PlManageMoneyPlanBuyinfoDao dao;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	@Resource 
	private PlMmOrderChildrenorTestService plMmOrderChildrenorTestService;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private PlMmOrderChildrenOrDao plMmOrderChildrenOrDao;
	@Resource
	private PlMmObligatoryRightChildrenDao plMmObligatoryRightChildrenDao;
	@Resource
	private PlMmOrderChildrenOrService plMmOrderChildrenOrService;
	@Resource
	private PlMmOrderAssignInterestDao plMmOrderAssignInterestDao;
	@Resource
	private EnterpriseBankDao enterpriseBankDao;
	@Resource
	private CsBankDao csBankDao;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private ObSystemAccountDao obSystemAccountDao;
	@Resource
	private PlMmOrderInfoService plMmOrderInfoService;
	
	
	public PlManageMoneyPlanBuyinfoServiceImpl(PlManageMoneyPlanBuyinfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlManageMoneyPlanBuyinfo> listbysearch(PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.listbysearch(pb, map);
	}
	/**
	 * 投资人投资管理查询方法
	 * add by linyan
	 * 2014-5-16
	 * @param request
	 * @return
	 */
	@Override
	public List<Investproject> getPersonInvestProject(
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return dao.getPersonInvestProject(request,start,limit);
	}

	@Override
	public List<PlManageMoneyPlanBuyinfo> getListByPlanId(Long mmplanId) {
		// TODO Auto-generated method stub
		return dao.getListByPlanId( mmplanId);
	}

	@Override
	public boolean updateState(Long mmplanId,Short state) {
		String hql = "update PlManageMoneyPlanBuyinfo info set info.state = ? where info.plManageMoneyPlan.mmplanId = ?";
		dao.update(hql, new Object[]{state,mmplanId});
		return false;
	}
	
	@Override
	public String gcalculateEarlyOutOrmatching(
			PlEarlyRedemption plEarlyRedemption) {
		PlManageMoneyPlanBuyinfo order=plEarlyRedemption.getPlManageMoneyPlanBuyinfo(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		List<PlMmOrderChildrenOr> clist= plMmOrderChildrenOrDao.listbyorderid(plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getOrderId(),sdf.format(plEarlyRedemption.getEarlyDate()));
		BigDecimal money=order.getCurrentGetedMoney();
		for(PlMmOrderChildrenOr c:clist){
			c.setMatchingEndDate(plEarlyRedemption.getEarlyDate());
			int matchingLimit=DateUtil.getDaysBetweenDate(sdf.format(c.getMatchingStartDate()),sdf.format(c.getMatchingEndDate()));
			if(matchingLimit<=0){
				c.setMatchingEndDateType(1);
				PlMmObligatoryRightChildren orchildren=	plMmObligatoryRightChildrenDao.get(c.getChildrenorId());
		    	plMmOrderChildrenOrService.matchingrelease(new Date(),c,order,orchildren);
			
		    	money=money.subtract(c.getMatchingGetMoney());
				plMmOrderChildrenOrDao.remove(c);
			}else{
				c.setMatchingEndDateType(4);
				c.setMatchingLimit(matchingLimit);
				BigDecimal thismatchgetMoney=c.getMatchingMoney().multiply(c.getChildrenOrDayRate().multiply(new BigDecimal(matchingLimit))).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
				money=money.subtract(c.getMatchingGetMoney()).add(thismatchgetMoney);
				c.setMatchingGetMoney(thismatchgetMoney);
				plMmOrderChildrenOrDao.save(c);
			}
		}
		order.setCurrentGetedMoney(money);
		order.setEarlierOutDate(plEarlyRedemption.getEarlyDate());
		dao.save(order);
		
		return "";
	}

	@Override
	public List<PlManageMoneyPlanBuyinfo> getListByPlanId(Long mmplanId,
			Short status) {
		// TODO Auto-generated method stub
		return this.dao.getListByPlanId(mmplanId,status);
	}
	
	
	@Override
	public void setMap(String orderId, Map<String, Object> map) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatY = new SimpleDateFormat("yyyy");
		SimpleDateFormat formatM = new SimpleDateFormat("MM");
		SimpleDateFormat formatD = new SimpleDateFormat("dd");
		
		PlManageMoneyPlanBuyinfo order = dao.get(Long.valueOf(orderId));
		
		List<EnterpriseBank> elist = enterpriseBankDao.getBankList(Integer.valueOf(order.getInvestPersonId().toString()), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("3"));
		if (null != elist && elist.size() > 0) {
			EnterpriseBank enterpriseBank = elist.get(0);
			map.put("bankNumber", enterpriseBank.getAccountnum());
			CsBank csBank = csBankDao.get(enterpriseBank.getBankid());
			if(csBank!=null){
				map.put("bankName", csBank.getBankname());
			}
		}
		
		//起始日期和结束日期
		map.put("syyyy", formatY.format(order.getStartinInterestTime()));
		map.put("sMM", formatM.format(order.getStartinInterestTime()));
		map.put("sdd", formatD.format(order.getStartinInterestTime()));
		map.put("eyyyy", formatY.format(order.getEndinInterestTime()));
		map.put("eMM", formatM.format(order.getEndinInterestTime()));
		map.put("edd", formatD.format(order.getEndinInterestTime()));
		Date today =  new Date();
		map.put("tyyyy", formatY.format(today));
		map.put("tMM", formatM.format(today));
		map.put("tdd", formatD.format(today));
		
		//投资人名称
		map.put("name", order.getInvestPersonName());
		//产品名称
		map.put("productName", order.getMmName());
		//产品金额
		map.put("productMoney", order.getBuyMoney().toString());
		//出借编号
		map.put("number", order.getRecordNumber());
		
		
		List<PlMmOrderAssignInterest> list = plMmOrderAssignInterestDao.listByOrderIdAndFundType(orderId,"loanInterest");
		
		List<POTable1> listTable1=plMmOrderAssignInterestDao.listOrderInterest(orderId);
		BigDecimal totalxi = new BigDecimal(0);
		BigDecimal totalRinpal=new BigDecimal(0);
		BigDecimal totalquit=new BigDecimal(0);
		BigDecimal payMoney=new BigDecimal(0);
		int index=listTable1!=null?(listTable1.size()>0?listTable1.size()-1:0):0;
		for(int i=0;i<listTable1.size();i++){
			listTable1.get(i).setProductRate(new BigDecimal(listTable1.get(i).getProductRate()).setScale(2,BigDecimal.ROUND_HALF_UP)+"%");
			listTable1.get(i).setPrinpalMoney(new BigDecimal(listTable1.get(i).getPrinpalMoney()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			listTable1.get(i).setQuitMoney(new BigDecimal(listTable1.get(i).getQuitMoney()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			listTable1.get(i).setCreditorIncomeMoney(new BigDecimal(listTable1.get(i).getCreditorIncomeMoney()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
			totalxi = totalxi.add(new BigDecimal(listTable1.get(i).getCreditorIncomeMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
			totalRinpal=totalRinpal.add(new BigDecimal(listTable1.get(i).getPrinpalMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
			totalquit=totalquit.add(new BigDecimal(listTable1.get(i).getQuitMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
			System.out.println(DateUtil.getDaysBetweenDate(listTable1.get(i).getPlanDate(), DateUtil.dateToStr(new Date(), "yyyy-MM-dd")));
			if(DateUtil.getDaysBetweenDate(listTable1.get(i).getPlanDate(), DateUtil.dateToStr(new Date(), "yyyy-MM-dd"))<=0){
				if(i<index){
					index=i;
				}
			}
		}
		if(listTable1!=null&&listTable1.size()>0){
			payMoney=(new BigDecimal(listTable1.get(index).getPrinpalMoney()).setScale(2,BigDecimal.ROUND_HALF_UP)).add(new BigDecimal(listTable1.get(index).getCreditorIncomeMoney()).setScale(2,BigDecimal.ROUND_HALF_UP)).subtract(new BigDecimal(listTable1.get(index).getQuitMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
		}
		map.put("payMoney", payMoney);
		//支付金额大写
		String payMoneyBig = RMB.toBigAmt(payMoney.doubleValue());
		map.put("payMoneyBig", payMoneyBig);
		//派息列表
		map.put("table1", listTable1);
		
		//收益总额 
		map.put("totalxi", totalxi.toString());
		//本金总额 
		map.put("totalRinpal", totalRinpal.toString());
		//退出费总额 
		map.put("totalquit", totalquit.toString());
		BigDecimal totalMoney = totalxi.add(totalRinpal);
		//总金额
		map.put("totalMoney", totalMoney.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		
		String totalMoneyBig  = MoneyFormat.getInstance().hangeToBig(totalMoney);
		//总金额大写
	//	String totalMoneyBig  = RMB.toBigAmt(totalMoney.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		map.put("totalMoneyBig", totalMoneyBig);
		
		
		
		
	}
	
	
	@Override
	public boolean createOrder(PlMmOrderInfo plMmOrderInfo) {
		PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo = new PlManageMoneyPlanBuyinfo();
		
		if(plMmOrderInfo.getOrderId()!=null&&!"".equals(plMmOrderInfo.getOrderId())){
			plManageMoneyPlanBuyinfo = plManageMoneyPlanBuyinfoService.get(Long.valueOf(plMmOrderInfo.getOrderId()));
		}
		
		if(plManageMoneyPlanBuyinfo.getOrderId()==null){
			if("0".equals(plMmOrderInfo.getConfirmType())){//如果购买 确认方式 是系统自动充值扣除账户余额 则传给此账户进行充值
				
				ObSystemAccount obSystemAccount = obSystemAccountDao.getByInvrstPersonIdAndType(Long.valueOf(plMmOrderInfo.getInvestPersonId()), Short.valueOf("1"));
				ObAccountDealInfo obAccountDealInfo = new ObAccountDealInfo();
				obAccountDealInfo.setIncomMoney(plMmOrderInfo.getBuyMoney());//充值金额
				obAccountDealInfo.setPayMoney(new BigDecimal(0));
				obAccountDealInfo.setCurrentMoney(plMmOrderInfo.getBuyMoney());
				obAccountDealInfo.setTransferType("1");
				obAccountDealInfo.setDealType(Short.valueOf("2"));
				obAccountDealInfo.setCreateDate(new Date());
				obAccountDealInfo.setTransferDate(new Date());
				obAccountDealInfo.setInvestPersonName(plMmOrderInfo.getInvestPersonName());
				obAccountDealInfo.setInvestPersonId(Long.valueOf(plMmOrderInfo.getInvestPersonId()));
				obAccountDealInfo.setInvestPersonType(Short.valueOf("1"));
				obAccountDealInfo.setCompanyId(Long.valueOf(1));
				obAccountDealInfo.setRechargeLevel(Short.valueOf("3"));
				obAccountDealInfo.setDealRecordStatus(Short.valueOf("2"));
				obAccountDealInfo.setAccountId(obSystemAccount.getId());
				obAccountDealInfo.setRecordNumber(plMmOrderInfo.getRecordNumber());//理财产品订单流水号
				obAccountDealInfoService.save(obAccountDealInfo);
				
				//更新账户总额
				obSystemAccount.setTotalMoney(obSystemAccount.getTotalMoney().add(plMmOrderInfo.getBuyMoney()));
				obSystemAccountDao.save(obSystemAccount);
				
			}
			plManageMoneyPlanBuyinfo.setRunId(plMmOrderInfo.getId());
			plManageMoneyPlanBuyinfo.setRecordNumber(plMmOrderInfo.getRecordNumber());//理财产品订单流水号
			plManageMoneyPlanBuyinfo.setStartinInterestTime(plMmOrderInfo.getStartinInterestTime());
			plManageMoneyPlanBuyinfo.setEndinInterestTime(plMmOrderInfo.getEndinInterestTime());
			plManageMoneyPlanBuyinfo.setBuyMoney(plMmOrderInfo.getBuyMoney());
			plManageMoneyPlanBuyinfo.setBuyDatetime(plMmOrderInfo.getBuyDate());
			plManageMoneyPlanBuyinfo.setInvestPersonId(Long.valueOf(plMmOrderInfo.getInvestPersonId()));
			plManageMoneyPlanBuyinfo.setInvestPersonName(plMmOrderInfo.getInvestPersonName());
			plManageMoneyPlanBuyinfo.setMmName(plMmOrderInfo.getMmplanName());
			plManageMoneyPlanBuyinfo.setOrderlimit(Integer.valueOf(plMmOrderInfo.getOrderlimit()));
			plManageMoneyPlanBuyinfo.setState(Short.valueOf("2"));
			plManageMoneyPlanBuyinfo.setContractNo(plMmOrderInfo.getOrderNumber());
			
			PlManageMoneyPlan _plManageMoneyPlan = plManageMoneyPlanService.get(Long.valueOf(plMmOrderInfo.getMmplanId()));
			
			plManageMoneyPlanBuyinfo.setPlManageMoneyPlan(_plManageMoneyPlan);
			
			
			BigDecimal[] a=obSystemAccountService.sumTypeTotalMoney(plManageMoneyPlanBuyinfo.getInvestPersonId(),"1");
			if(a[0].compareTo(new BigDecimal("0"))==0){
				//setJsonString("{failure:true,moneytoobig:true,msg:'此投资人没有开设账户'}");
				//return SUCCESS;
				return false;
			}
			if(a[3].compareTo(plManageMoneyPlanBuyinfo.getBuyMoney())==-1){
				//setJsonString("{failure:true,moneytoobig:true,msg:'购买金额不能大于可用金额'}");
				//return SUCCESS;
				return false;
			}
		
			plManageMoneyPlanBuyinfo.setPersionType(PlManageMoneyPlanBuyinfo.P_TYPE1);
			PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmplanId());
			plManageMoneyPlan.setInvestedMoney(plManageMoneyPlan.getInvestedMoney().add(plManageMoneyPlanBuyinfo.getBuyMoney()));
			plManageMoneyPlanBuyinfo.setPlManageMoneyPlan(plManageMoneyPlan);
			BigDecimal dayRate=plManageMoneyPlan.getYeaRate().divide(new BigDecimal("360"),10,BigDecimal.ROUND_UP);
			plManageMoneyPlanBuyinfo.setCurrentGetedMoney(new BigDecimal("0"));
			plManageMoneyPlanBuyinfo.setPromisDayRate(dayRate);
			plManageMoneyPlanBuyinfo.setPromisMonthRate(plManageMoneyPlan.getYeaRate().divide(new BigDecimal("12"),10,BigDecimal.ROUND_UP));
			plManageMoneyPlanBuyinfo.setPromisYearRate(plManageMoneyPlan.getYeaRate());
			plManageMoneyPlanBuyinfo.setMmName(plManageMoneyPlan.getMmName());
			if(plManageMoneyPlan.getIsCyclingLend()!=null&&plManageMoneyPlan.getIsCyclingLend()==1){
				int investlimit =plManageMoneyPlan.getInvestlimit();
				BigDecimal baseMoney=plManageMoneyPlanBuyinfo.getBuyMoney();
				BigDecimal summoney=new BigDecimal(0);
				for(int i=1;i<=investlimit;i++){
					summoney=summoney.add(baseMoney.multiply(plManageMoneyPlanBuyinfo.getPromisMonthRate()).divide(new BigDecimal(100)));
					baseMoney=plManageMoneyPlanBuyinfo.getBuyMoney().add(summoney);
				 }
				
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(summoney);
			}else{
				
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(plManageMoneyPlanBuyinfo.getBuyMoney().multiply(dayRate.multiply(new BigDecimal(plManageMoneyPlanBuyinfo.getOrderlimit())).divide(new BigDecimal("100"))));
			}
		
			plManageMoneyPlanBuyinfo.setCurrentMatchingMoney(plManageMoneyPlanBuyinfo.getBuyMoney());
			plManageMoneyPlanBuyinfo.setOptimalDayRate(dayRate);
			plManageMoneyPlanBuyinfo.setKeystr("mmproduce");
			plManageMoneyPlanBuyinfo.setFirstProjectIdcount(0);
			plManageMoneyPlanBuyinfo.setFirstProjectIdstr("");
			plManageMoneyPlanBuyinfo.setIsAtuoMatch(0);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("investPersonId",plManageMoneyPlanBuyinfo.getInvestPersonId());//投资人Id（必填）
			map.put("investPersonType",ObSystemAccount.type1);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			map.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			map.put("money",plManageMoneyPlanBuyinfo.getBuyMoney());//交易金额	（必填）			 
			map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			map.put("dealType",ObAccountDealInfo.BANKDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			map.put("recordNumber",Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));//交易流水号	（必填）
			map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			String[] relt =obAccountDealInfoService.operateAcountInfo(map);
		//	String []relt = {Constants.CODE_SUCCESS};
		    if(relt[0].equals(Constants.CODE_SUCCESS)){
		    		PlManageMoneyPlanBuyinfo _plManageMoneyPlanBuyinfo = plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
		    		//绑定理财订单
		    		plMmOrderInfo.setOrderId(_plManageMoneyPlanBuyinfo.getOrderId().toString());
		    		plMmOrderInfoService.save(plMmOrderInfo);
		    		//生成投资人款项台账
				//	plMmOrderAssignInterestService.createAssignInerestlist(plManageMoneyPlanBuyinfo,plManageMoneyPlan);
					plMmOrderChildrenorTestService.matchForecast(plManageMoneyPlanBuyinfo);
		    }else{
		    	 return false;
		 	}
		     

		}else{
			PlManageMoneyPlanBuyinfo orgPlManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(plManageMoneyPlanBuyinfo.getOrderId());
			try{
				BeanUtil.copyNotNullProperties(orgPlManageMoneyPlanBuyinfo, plManageMoneyPlanBuyinfo);
				plManageMoneyPlanBuyinfoService.save(orgPlManageMoneyPlanBuyinfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		return true;
	}
	
	@Override
	public List<PlMmOrderAssignInterest> createPlMmOrderAssignInterestList(PlMmOrderInfo plMmOrderInfo) {
		
		PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo = new PlManageMoneyPlanBuyinfo();
		plManageMoneyPlanBuyinfo.setStartinInterestTime(plMmOrderInfo.getStartinInterestTime());
		plManageMoneyPlanBuyinfo.setEndinInterestTime(plMmOrderInfo.getEndinInterestTime());
		plManageMoneyPlanBuyinfo.setBuyMoney(plMmOrderInfo.getBuyMoney());
		plManageMoneyPlanBuyinfo.setBuyDatetime(plMmOrderInfo.getBuyDate());
		plManageMoneyPlanBuyinfo.setInvestPersonId(Long.valueOf(plMmOrderInfo.getInvestPersonId()));
		plManageMoneyPlanBuyinfo.setInvestPersonName(plMmOrderInfo.getInvestPersonName());
		plManageMoneyPlanBuyinfo.setMmName(plMmOrderInfo.getMmplanName());
		plManageMoneyPlanBuyinfo.setOrderlimit(Integer.valueOf(plMmOrderInfo.getOrderlimit()));
		plManageMoneyPlanBuyinfo.setContractNo(plMmOrderInfo.getOrderNumber());
		
		PlManageMoneyPlan _plManageMoneyPlan = plManageMoneyPlanService.get(Long.valueOf(plMmOrderInfo.getMmplanId()));
		
		plManageMoneyPlanBuyinfo.setPlManageMoneyPlan(_plManageMoneyPlan);
		
	//	if(plManageMoneyPlanBuyinfo.getOrderId()==null){
		if(true){
			plManageMoneyPlanBuyinfo.setPersionType(PlManageMoneyPlanBuyinfo.P_TYPE1);
			PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanService.get(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmplanId());
			plManageMoneyPlan.setInvestedMoney(plManageMoneyPlan.getInvestedMoney().add(plManageMoneyPlanBuyinfo.getBuyMoney()));
			plManageMoneyPlanBuyinfo.setPlManageMoneyPlan(plManageMoneyPlan);
			BigDecimal dayRate=plManageMoneyPlan.getYeaRate().divide(new BigDecimal("360"),10,BigDecimal.ROUND_UP);
			plManageMoneyPlanBuyinfo.setCurrentGetedMoney(new BigDecimal("0"));
			plManageMoneyPlanBuyinfo.setPromisDayRate(dayRate);
			plManageMoneyPlanBuyinfo.setPromisMonthRate(plManageMoneyPlan.getYeaRate().divide(new BigDecimal("12"),10,BigDecimal.ROUND_UP));
			plManageMoneyPlanBuyinfo.setPromisYearRate(plManageMoneyPlan.getYeaRate());
			plManageMoneyPlanBuyinfo.setMmName(plManageMoneyPlan.getMmName());
			if(plManageMoneyPlan.getIsCyclingLend()!=null&&plManageMoneyPlan.getIsCyclingLend()==1){
				int investlimit =plManageMoneyPlan.getInvestlimit();
				BigDecimal baseMoney=plManageMoneyPlanBuyinfo.getBuyMoney();
				BigDecimal summoney=new BigDecimal(0);
				for(int i=1;i<=investlimit;i++){
					summoney=summoney.add(baseMoney.multiply(plManageMoneyPlanBuyinfo.getPromisMonthRate()).divide(new BigDecimal(100)));
					baseMoney=plManageMoneyPlanBuyinfo.getBuyMoney().add(summoney);
				 }
				
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(summoney);
			}else{
				plManageMoneyPlanBuyinfo.setPromisIncomeSum(plManageMoneyPlanBuyinfo.getBuyMoney().multiply(dayRate.multiply(new BigDecimal(plManageMoneyPlanBuyinfo.getOrderlimit())).divide(new BigDecimal("100"))));
			}
		
			plManageMoneyPlanBuyinfo.setCurrentMatchingMoney(plManageMoneyPlanBuyinfo.getBuyMoney());
			plManageMoneyPlanBuyinfo.setOptimalDayRate(dayRate);
			plManageMoneyPlanBuyinfo.setKeystr("mmproduce");
			plManageMoneyPlanBuyinfo.setFirstProjectIdcount(0);
			plManageMoneyPlanBuyinfo.setFirstProjectIdstr("");
			plManageMoneyPlanBuyinfo.setIsAtuoMatch(0);

			//生成投资人款项台账
			return plMmOrderAssignInterestService.createAssignInerestlistByFlow(plManageMoneyPlanBuyinfo,plManageMoneyPlan);
		}
		return null;
		
	}
	
	@Override
	public List<PlManageMoneyPlanBuyinfo> listByMmplanId(PagingBean pb,Map<String, String> map) {
		return dao.listByMmplanId(pb, map);
	}

	@Override
	public List<PlManageMoneyPlanBuyinfo> listbyState(PagingBean pb,
			Map<String, String> map) {
		return dao.listbyState(pb,map);
	}

	@Override
	public List<PlManageMoneyPlanBuyinfo> getByDate(Map<String, String> map) {
		return dao.getByDate(map);
	}

	@Override
	public PlManageMoneyPlanBuyinfo getByDealInfoNumber(String dealInfoNumber) {
		return dao.getByDealInfoNumber(dealInfoNumber);
	}

	@Override
	public List<PlManageMoneyPlanBuyinfo> getPreAuthorizationNum(
			String preAuthorizationNum) {
		// TODO Auto-generated method stub
		return dao.getPreAuthorizationNum(preAuthorizationNum);
	}
	
	
	
	
}