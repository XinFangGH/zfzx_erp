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

import com.hurong.credit.util.Common;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidInfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenDao;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyType;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.activity.BpActivityManageService;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessDirProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessOrProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyTypeService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionDirProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionOrProService;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;

/**
 * 
 * @author 
 *
 */
public class PlManageMoneyPlanServiceImpl extends BaseServiceImpl<PlManageMoneyPlan> implements PlManageMoneyPlanService{
	private PlManageMoneyPlanDao dao;
	
	public PlManageMoneyPlanServiceImpl(PlManageMoneyPlanDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Resource
	private PlManageMoneyTypeService plManageMoneyTypeService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	@Resource
	private BpPersionDirProService bpPersionDirProService;
	@Resource
	private BpBusinessDirProService bpBusinessDirProService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private PlMmObligatoryRightChildrenDao plMmObligatoryRightChildrenDao;
	@Resource
	private PlBidInfoDao plBidInfoDao;
	@Resource
	private BpPersionOrProService bpPersionOrProService;
	@Resource
	private BpBusinessOrProService bpBusinessOrProService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpActivityManageService bpActivityManageService;
	@Resource
	private BpCouponsService bpCouponsService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private BatchRunRecordService batchRunRecordService;
	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	

	@SuppressWarnings("unchecked")
	@Override
	public PlManageMoneyPlan bidDynamic(PlManageMoneyPlan plManageMoneyPlan) {
		double progress;//进度
		int persionNum = 0;//人数
		BigDecimal money=new BigDecimal(0);//剩余金额
		Iterator<PlManageMoneyPlanBuyinfo> it =plManageMoneyPlan.getPlManageMoneyPlanBuyinfos().iterator();
		//投标金额合计
		BigDecimal totalMoney=new BigDecimal(0);
		while (it.hasNext()) {
			PlManageMoneyPlanBuyinfo bidInfo=it.next();
			totalMoney=totalMoney.add(bidInfo.getBuyMoney());
			persionNum=persionNum+1;
		}	
		money=plManageMoneyPlan.getSumMoney().subtract(totalMoney);
		progress=Double.valueOf(totalMoney.divide(plManageMoneyPlan.getSumMoney(),BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)).toString());
		plManageMoneyPlan.setProgress(progress);//进度
		plManageMoneyPlan.setPersionNum(persionNum);//人数
		plManageMoneyPlan.setAfterMoney(money);//剩余金额
		return plManageMoneyPlan;
		
	}

	@Override
	public long getCountBytypeIdAndNum(Long manageMoneyTypeId,
			String mmNumber) {
		String hql=" select count(*)  from PlManageMoneyPlan plan where plan.manageMoneyTypeId=? and plan.mmNumber=?";
		 List list=dao.findByHql(hql, new Object[]{manageMoneyTypeId,mmNumber});
		 long count=0;
		 if(null!=list && list.size()>0){
				count=(Long)list.get(0);
			}
		 return count;
	}
	
	@Override
	public long isCheckMmNumber(String mmNumber) {
		String hql=" select count(*)  from PlManageMoneyPlan plan where plan.mmNumber=?";
		 List list=dao.findByHql(hql, new Object[]{mmNumber});
		 long count=0;
		 if(null!=list && list.size()>0){
				count=(Long)list.get(0);
			}
		 return count;
	}

	@Override
	public List<PlManageMoneyPlan> getAllPlbuyInfo(PagingBean pb,
			Map<String, String> map) {
		return dao.getAllPlbuyInfo(pb,map);
	}
	
	@Override
	public void uPlanToRightChildren() {
		//查询出所有U计划类型,拿出专属账户
		List<PlManageMoneyType> typeList=plManageMoneyTypeService.getListbykeystr("UPlan");
		
		 //跑批记录 
		AppUser appUser = ContextUtil.getCurrentUser();
		String pushUserName = "定时跑批";
		Long pushUserId = null;
		if(null != appUser && !"".equals(appUser)){
			pushUserName = appUser.getFullname();
			pushUserId = appUser.getUserId();
		}
		BatchRunRecord batchRunRecord = new BatchRunRecord();
		batchRunRecord.setRunType(BatchRunRecord.HRY_1005);
		batchRunRecord.setAppUserId(pushUserId);
		batchRunRecord.setAppUserName(pushUserName);
		batchRunRecord.setStartRunDate(new Date());
		batchRunRecord.setHappenAbnorma("正常");
		Long total = Long.valueOf(0);
		
		if(null !=typeList && typeList.size()>0){
			for(PlManageMoneyType plManageMoneyType : typeList){
				Map<String, String> map =new HashMap<String, String>();
				map.put("userName", plManageMoneyType.getReceivablesAccount());
				map.put("state", "2");
				//查询出已放款且未生成过债权的
				List<PlBidInfo> infoList=plBidInfoService.queryUserName(map);
				if(null !=infoList && infoList.size()>0){
					total += infoList.size();
					System.out.println("U计划投资记录生成到债权库开始"+new Date()+"---记录条数=="+infoList.size());
					for(PlBidInfo plBidInfo : infoList){
						try {
							PlBidPlan plBidPlan= plBidInfo.getPlBidPlan();
							//查出投资人信息
							BpCustMember bpCustMember=bpCustMemberService.get(plBidInfo.getUserId());
							List<BpFundIntent> listintent = bpFundIntentDao.getByprincipalRepayment(plBidPlan.getBidId(),plBidInfo.getOrderNo());
							
							Long projectId=null;
							if(plBidPlan.getProType().equals("P_Dir")){
								BpPersionDirPro bpPersionDirPro=bpPersionDirProService.get(plBidPlan.getPDirProId());
								projectId=bpPersionDirPro.getProId();
							}else if(plBidPlan.getProType().equals("B_Dir")){
								BpBusinessDirPro  bpBusinessDirPro=bpBusinessDirProService.get(plBidPlan.getBdirProId());
								projectId=bpBusinessDirPro.getProId();
							}else if(plBidPlan.getProType().equals("P_Or")){
								BpPersionOrPro bpPersionOrPro=bpPersionOrProService.get(plBidPlan.getPOrProId());
								projectId=bpPersionOrPro.getProId();
							}else if(plBidPlan.getProType().equals("B_Or")){
								BpBusinessOrPro bpBusinessOrPro=bpBusinessOrProService.get(plBidPlan.getBorProId());
								projectId=bpBusinessOrPro.getProId();
							}
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							
							SlSmallloanProject slSmallloanProject= slSmallloanProjectService.get(projectId);
							
							for(BpFundIntent l:listintent){
								PlMmObligatoryRightChildren plrc=new PlMmObligatoryRightChildren();
								plrc.setPrincipalMoney(l.getIncomeMoney());
								plrc.setIntentDate(l.getIntentDate());
								plrc.setStartDate(plBidPlan.getStartIntentDate());
								int days=DateUtil.getDaysBetweenDate(sdf.format(plrc.getStartDate()), sdf.format(plrc.getIntentDate()));
								plrc.setOrlimit(days);
								plrc.setDayRate(slSmallloanProject.getDayAccrualRate());
								plrc.setAvailableMoney(l.getIncomeMoney());
								plrc.setParentOrBidId(plBidPlan.getBidId());
								plrc.setParentOrBidName(plBidPlan.getBidProName());
								plrc.setProjectId(projectId);
								plrc.setProjectName(slSmallloanProject.getProjectName());
								plrc.setChildrenorName(plrc.getParentOrBidName()+"_"+l.getPayintentPeriod());
								plrc.setSurplusValueMoney(l.getIncomeMoney().multiply(new BigDecimal(plrc.getOrlimit())).multiply(slSmallloanProject.getDayAccrualRate()).divide(new BigDecimal(100)));
								plrc.setType(Short.valueOf("3"));
								plrc.setTypeRelation(plBidInfo.getId().toString());
								plrc.setChildType("UPlanOr");
								plrc.setReceiverName(bpCustMember.getTruename());
								plrc.setReceiverP2PAccountNumber(bpCustMember.getLoginname());
								plMmObligatoryRightChildrenDao.save(plrc);
							}
							plBidInfo.setIsToObligatoryRightChildren(Short.valueOf("1"));
							plBidInfoDao.save(plBidInfo);
						} catch (Exception e) {
							String ids="";
							if(null != batchRunRecord.getIds()){
								ids =batchRunRecord.getIds() + "," + plBidInfo.getId();
							}else{
								ids = plBidInfo.getId().toString();
							}
							batchRunRecord.setIds(ids);
							batchRunRecord.setHappenAbnorma("异常");
							e.printStackTrace();
						}
						
					}
				}
			}
		}
		batchRunRecord.setTotalNumber(total);
		batchRunRecord.setEndRunDate(new Date());
		batchRunRecordService.save(batchRunRecord);
	}

	@Override
	public void createReward() {
		System.out.println("定时器生成奖励台账开始=="+new Date());
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		//查询出处于招标中，已齐标，已过期且起息类型为（1:投标日起息，2:投标日+1起息）的理财计划
		Map<String, String> map =new HashMap<String, String>();
		map.put("state", "1,2,4");
		map.put("startinInterestCondition", "1,2");
		List<PlManageMoneyPlan> planList=plManageMoneyPlanService.getByStateAndCondition(map);
		 //跑批记录 
		AppUser appUser = ContextUtil.getCurrentUser();
		String pushUserName = "定时跑批";
		Long pushUserId = null;
		if(null != appUser && !"".equals(appUser)){
			pushUserName = appUser.getFullname();
			pushUserId = appUser.getUserId();
		}
		BatchRunRecord batchRunRecord = new BatchRunRecord();
		batchRunRecord.setRunType(BatchRunRecord.HRY_1006);
		batchRunRecord.setAppUserId(pushUserId);
		batchRunRecord.setAppUserName(pushUserName);
		batchRunRecord.setStartRunDate(new Date());
		batchRunRecord.setHappenAbnorma("正常");
		Long total = Long.valueOf(0);
		
		if(null !=planList && planList.size()>0){
			for(PlManageMoneyPlan plan : planList){
				//根据理财计划Id查询出已经起息，但是没有生成奖励，且为当天投标的
				Map<String, String> map2 =new HashMap<String, String>();
				map2.put("mmplanId", plan.getMmplanId().toString());
				map2.put("state", "2");
				map2.put("date", null);
				//map2.put("date", format.format(DateUtil.addDaysToDate(new Date(), -1)));
				map2.put("isCreateReward", "1");//没有生成过奖励
				List<PlManageMoneyPlanBuyinfo> infoList=plManageMoneyPlanBuyinfoService.getByDate(map2);
				if(null !=infoList && infoList.size()>0){
					total += infoList.size();
					for(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo : infoList){
						try {
							//判断是否有投标活动设置
							bpActivityManageService.addbpActivityManage(plManageMoneyPlanBuyinfo.getInvestPersonId(), plManageMoneyPlanBuyinfo.getBuyMoney(),plManageMoneyPlanBuyinfo.getBuyDatetime());
							//生成奖励台账
							plManageMoneyPlanBuyinfo.setStartinInterestTime(plManageMoneyPlanBuyinfo.getStartinInterestTime());
							plManageMoneyPlanBuyinfo.setEndinInterestTime(plManageMoneyPlanBuyinfo.getEndinInterestTime());
							plManageMoneyPlanBuyinfo.setIsCreateReward(2);
							plMmOrderAssignInterestService.createAssignCouponsInerestlist(plManageMoneyPlanBuyinfo,plan);
							plManageMoneyPlanBuyinfoService.save(plManageMoneyPlanBuyinfo);
						} catch (Exception e) {
							String ids="";
							if(null != batchRunRecord.getIds()){
								ids =batchRunRecord.getIds() + "," + plManageMoneyPlanBuyinfo.getOrderId();
							}else{
								ids = plManageMoneyPlanBuyinfo.getOrderId().toString();
							}
							batchRunRecord.setIds(ids);
							batchRunRecord.setHappenAbnorma("异常");
							e.printStackTrace();
						}
					}
				}
				//将已齐标和已过期的状态修改为回款中
				if(plan.getState() != 1){
					plan.setState(7);//回款中
					plManageMoneyPlanService.save(plan);
				}
			}
		}
		batchRunRecord.setTotalNumber(total);
		batchRunRecord.setEndRunDate(new Date());
		batchRunRecordService.save(batchRunRecord);
		System.out.println("定时器生成奖励台账结束=="+new Date());
	}
	
	@Override
	public void payReward() {
		System.out.println("定时器派发奖励开始=="+new Date());
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		//查询出处于招标中，已齐标，已过期,还款中且起息类型为（1:投标日起息，2:投标日+1起息）的理财计划
		Map<String, String> map =new HashMap<String, String>();
		map.put("state", "1,2,4,7");
		map.put("startinInterestCondition", "1,2");
		List<PlManageMoneyPlan> planList=plManageMoneyPlanService.getByStateAndCondition(map);
		 //跑批记录 
		AppUser appUser = ContextUtil.getCurrentUser();
		String pushUserName = "定时跑批";
		Long pushUserId = null;
		if(null != appUser && !"".equals(appUser)){
			pushUserName = appUser.getFullname();
			pushUserId = appUser.getUserId();
		}
		BatchRunRecord batchRunRecord = new BatchRunRecord();
		batchRunRecord.setRunType(BatchRunRecord.HRY_1007);
		batchRunRecord.setAppUserId(pushUserId);
		batchRunRecord.setAppUserName(pushUserName);
		batchRunRecord.setStartRunDate(new Date());
		batchRunRecord.setHappenAbnorma("正常");
		Long total = Long.valueOf(0);
		if(null !=planList && planList.size()>0){
			for(PlManageMoneyPlan plan : planList){
				//根据理财计划Id查询出已经起息，但是没有生成奖励,且为当天投标的
				Map<String, String> map2 =new HashMap<String, String>();
				map2.put("mmplanId", plan.getMmplanId().toString());
				map2.put("state", "2");
				map2.put("date", format.format(DateUtil.addDaysToDate(new Date(), -1)));
				List<PlManageMoneyPlanBuyinfo> infoList=plManageMoneyPlanBuyinfoService.getByDate(map2);
				if(null !=infoList && infoList.size()>0){
					total += infoList.size();
					for(PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo : infoList){
						try {
							checkCoupons(plManageMoneyPlanBuyinfo);
						} catch (Exception e) {
							String ids="";
							if(null != batchRunRecord.getIds()){
								ids =batchRunRecord.getIds() + "," + plManageMoneyPlanBuyinfo.getOrderId();
							}else{
								ids = plManageMoneyPlanBuyinfo.getOrderId().toString();
							}
							batchRunRecord.setIds(ids);
							batchRunRecord.setHappenAbnorma("异常");
							e.printStackTrace();
						}
					}
				}
			}
		}
		batchRunRecord.setTotalNumber(total);
		batchRunRecord.setEndRunDate(new Date());
		batchRunRecordService.save(batchRunRecord);
		System.out.println("定时器派发奖励结束=="+new Date());
	}
	
	@Override
	public List<PlManageMoneyPlan> getByStateAndCondition(Map<String, String> map) {
		return dao.getByStateAndCondition(map);
	}
	
	/**
	 * 检查是否有投标活动
	 * @param bidInfo1
	 *//*
	private void addbpActivityManage(PlManageMoneyPlanBuyinfo bidInfo1){
		BpCustMember mem=null;
		//判断是否是第一次投标
    	QueryFilter fit = new QueryFilter();
    	fit.addFilter("Q_userId_L_EQ", bidInfo1.getInvestPersonId().toString());
    	fit.addFilter("Q_state_SN_EQ", "2");
    	List<PlBidInfo> infoList = plBidInfoService.getAll(fit);
    	//判断是否是第一次购买理财计划
    	QueryFilter filter = new QueryFilter();
    	filter.addFilter("Q_investPersonId_L_EQ", bidInfo1.getInvestPersonId().toString());
    	filter.addFilter("Q_state_SN_EQ", "2");
    	List<PlManageMoneyPlanBuyinfo> buyList = plManageMoneyPlanBuyinfoService.getAll(filter);
    	 //判断是否是推荐的用户
		 BpCustMember custMem=bpCustMemberService.get(bidInfo1.getInvestPersonId());
		 if(custMem.getRecommandPerson()!=null&&!custMem.getRecommandPerson().equals("")){
			 mem = bpCustMemberService.isRecommandPerson(custMem.getRecommandPerson());
		 }
		 if(infoList.size()>0||buyList.size()>0){
			 bpActivityManageService.autoDistributeEngine("3", bidInfo1.getInvestPersonId().toString(),bidInfo1.getBuyMoney());
		 }else{
			//投标
			 bpActivityManageService.autoDistributeEngine("3", bidInfo1.getInvestPersonId().toString(),bidInfo1.getBuyMoney());
			 //首投
			 bpActivityManageService.autoDistributeEngine("8", bidInfo1.getInvestPersonId().toString(),bidInfo1.getBuyMoney());
			 if(mem!=null){
				//邀请好友第一次投标
				bpActivityManageService.autoDistributeEngine("5", mem.getId().toString(),bidInfo1.getBuyMoney());
			 }
		 }
		 if(mem!=null){
			 //累计推荐投资
			 bpActivityManageService.autoDistributeEngine("9", bidInfo1.getInvestPersonId().toString(),null);
		 }
		 //累计投资
		 bpActivityManageService.autoDistributeEngine("6", bidInfo1.getInvestPersonId().toString(),null);
	}*/
	
	
	/**
	 * 优惠券派发奖励
	 * @param plList
	 */
	public void checkCoupons(PlManageMoneyPlanBuyinfo bidInfo){
			PlManageMoneyPlan bidplan = bidInfo.getPlManageMoneyPlan();
			//判断此标是否可用优惠券
			if(bidplan.getCoupon()!=null&&bidplan.getCoupon().compareTo(1)==0){
				//判断返利方式是否是 立还
				if(bidplan.getRebateWay().compareTo(1)==0){
					List<PlMmOrderAssignInterest> bpfundInterestList=null;//利息
					List<PlMmOrderAssignInterest> bpfundPrincipalList=null;//本金
					String transferType="";//资金类型
					//判断是否使用了优惠券，派发金额
					if(bidInfo.getCouponId()!=null&&!bidInfo.getCouponId().equals("")){
						//判断 返利类型
						if(bidplan.getRebateType().compareTo(1)==0){//返现 principalCoupons
							transferType=ObAccountDealInfo.T_BIDRETURN_RETURNRATIO;
							bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo.getOrderId(), bidInfo.getInvestPersonId(), bidplan.getMmplanId(), "'principalCoupons'",null);
						}else if(bidplan.getRebateType().compareTo(2)==0){//返息 couponInterest
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE27;
							bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo.getOrderId(), bidInfo.getInvestPersonId(), bidplan.getMmplanId(), "'couponInterest'",null);
						}else if(bidplan.getRebateType().compareTo(3)==0){//返息现  principalCoupons couponInterest
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE29;
							bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo.getOrderId(), bidInfo.getInvestPersonId(), bidplan.getMmplanId(), "'couponInterest'",null);
							bpfundPrincipalList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo.getOrderId(), bidInfo.getInvestPersonId(), bidplan.getMmplanId(), "'principalCoupons'",null);
						}else if(bidplan.getRebateType().compareTo(4)==0){//加息 couponInterest
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE30;
							bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo.getOrderId(), bidInfo.getInvestPersonId(), bidplan.getMmplanId(), "'subjoinInterest'",null);
						}
					}
					if(bpfundInterestList!=null){//返利息
						couponIntent(bpfundInterestList,bidInfo,bidplan,transferType);
					}
					if(bpfundPrincipalList!=null){//返本金
						couponIntent(bpfundPrincipalList,bidInfo,bidplan,ObAccountDealInfo.T_BIDRETURN_RATE28);
					}
				}
				
				//判断是否使用了优惠券，更新优惠券
				if(bidInfo.getCouponId()!=null&&!bidInfo.getCouponId().equals("")){
						BpCoupons coupon = bpCouponsService.get(bidInfo.getCouponId());
						coupon.setCouponStatus(Short.valueOf("10"));
						coupon.setUseProjectName(bidplan.getMmName());
						coupon.setUseProjectNumber(bidplan.getMmNumber());
						coupon.setUseProjectType(bidplan.getKeystr());
						coupon.setUseTime(new Date());
						bpCouponsService.save(coupon);
				}
				
			}else{
				//判断此标是否设置普通加息
				//普通加息日化利率，判断是否是立还
				if(bidplan.getAddRate()!=null&&!bidplan.getAddRate().equals("")&&bidplan.getAddRate().compareTo(new BigDecimal(0))!=0){
					if(bidplan.getRebateWay().compareTo(1)==0){
						List<PlMmOrderAssignInterest> bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo.getOrderId(), bidInfo.getInvestPersonId(), bidplan.getMmplanId(), "'commoninterest'",null);
						couponIntent(bpfundInterestList,bidInfo,bidplan,ObAccountDealInfo.T_BIDRETURN_ADDRATE);
					}
					
				}
			}
			
			//判断此标是否设置了募集期利率
			if(bidplan.getRaiseRate()!=null&&!bidplan.getRaiseRate().equals("")){
				List<PlMmOrderAssignInterest> raiseinterestList = plMmOrderAssignInterestService.getByPlanIdA(bidInfo.getOrderId(), bidInfo.getInvestPersonId(), bidplan.getMmplanId(), "'raiseinterest'",null);
				couponIntent(raiseinterestList,bidInfo,bidplan,ObAccountDealInfo.T_BIDRETURN_RATE31);
			}
	}
	
	/**
	 * 派发优惠券奖励
	 * @param bp
	 * @param info
	 */
	private void couponIntent(List<PlMmOrderAssignInterest> bp,PlManageMoneyPlanBuyinfo bidInfo1,PlManageMoneyPlan bidplan,String transferType){
		String BasePath=configMap.get("erpURL").toString();
		System.out.println(BasePath);
		for(PlMmOrderAssignInterest bpfund:bp){
			if(bpfund.getFactDate()==null||bpfund.getFactDate().equals("")){
				BpCustMember mem=bpCustMemberService.get(bpfund.getInvestPersonId());
				String requestNo=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
				CommonRequst commonRequest = new CommonRequst();
				commonRequest.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方标识
				commonRequest.setRequsetNo(requestNo);//请求流水号
				commonRequest.setAmount(bpfund.getIncomeMoney());//交易金额
				if(mem.getCustomerType()!=null&&mem.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
					commonRequest.setAccountType(1);
				}else{//借款人是个人
					commonRequest.setAccountType(0);
				}
				commonRequest.setCustMemberType("0");
				commonRequest.setBidId(bpfund.getAssignInterestId().toString());
				commonRequest.setTransferName(ThirdPayConstants.TN_COUPONAWARD);//业务类型
				commonRequest.setBussinessType(ThirdPayConstants.BT_COUPONAWARD);//业务名称
				
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
				System.out.println("返现result="+commonResponse.getResponseMsg());
				
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					//添加资金明细
					Map<String,Object> map3=new HashMap<String,Object>();
					map3.put("investPersonId",mem.getId());//投资人Id（必填）
					map3.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
					map3.put("transferType",transferType);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
					map3.put("money",bpfund.getIncomeMoney());//交易金额	（必填）			 
					map3.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
					map3.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
					map3.put("recordNumber",requestNo);//交易流水号	（必填）
					map3.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
					obAccountDealInfoService.operateAcountInfo(map3);
					
					//更新款项
					bpfund.setAfterMoney(bpfund.getIncomeMoney());
					bpfund.setFactDate(new Date());
					plMmOrderAssignInterestService.save(bpfund);
				}
			}
			
		}
	}

	@Override
	public BigDecimal findBidMoneyByAccount(String account,String keystr) {
		return dao.findBidMoneyByAccount(account,keystr);
	}
	@Override
	public BigDecimal findMatchingMoneyByAccount(String account, String keystr) {
		return dao.findMatchingMoneyByAccount(account,keystr);
	}

}