package com.zhiwei.credit.service.creditFlow.finance.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hurong.credit.service.user.BpCustMemberService;
import com.messageAlert.service.SmsSendService;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.BpFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.FundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementReviewerPayService;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.OrganizationService;

public class BpFundIntentServiceImpl extends BaseServiceImpl<BpFundIntent> implements BpFundIntentService {
	private BpFundIntentDao dao;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService; 
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private FundIntentService fundIntentService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private BpCouponsService bpCouponsService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private BatchRunRecordService batchRunRecordService;
	@Resource
	private SmsSendService smsSendService;
	
	@Resource
	private BpCustMemberDao bpCustMemberDao;
	
	@Resource
	private SettlementReviewerPayService settlementReviewerPayService;
	
	@Resource
	private OrganizationService organizationService;
	
	public BpFundIntentServiceImpl(BpFundIntentDao dao) {
		super(dao);
		this.dao=dao;
		
	}
	@Override
	public BigDecimal getInterest(Long preceptId, Long investPersonId) {
		return null;
	}
	@Override
	public BigDecimal getPrincipal(Long preceptId, Long investPersonId) {
		return null;
	}
	@Override
	public List<BpFundIntent> getByBidPlanId(Long bidPlanId) {
		String hql = "from BpFundIntent bf where bf.bidPlanId=?";
		return dao.findByHql(hql, new Object[]{bidPlanId});
	}
	@Override
	public List<BpFundIntent> getBpBidPlanId(Long bidPlanId, String fundType) {
		String hql = "from BpFundIntent bf where bf.bidPlanId=? and bf.fundType = ?";
		return dao.findByHql(hql, new Object[]{bidPlanId,fundType});
	}
	@Override
	public List<BpFundIntent> getListBysql(HttpServletRequest request,Integer start ,Integer limit ) {
		// TODO Auto-generated method stub
		return dao.getListBysql(request , start ,limit);
	}
	/*
	 * 修改款项状态
	 * 
	 * ***/
	@Override
	public boolean updateState(String orderNo, Short state, String fundType) {
		String hql1 = "from BpFundIntent as bf where bf.orderNo = ? and bf.fundType = ?";
		List<BpFundIntent> list = dao.findByHql(hql1, new Object[]{orderNo,fundType});
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if(list!=null&&list.size()!=0){
			for(int i=0;i<list.size();i++){
				BpFundIntent intent = list.get(i);
				if(null!=intent){
					intent.setNotMoney(new BigDecimal(0));
					if("principalLending".equals(fundType)){
						intent.setAfterMoney(intent.getPayMoney());
					}else{
						intent.setAfterMoney(intent.getIncomeMoney());
					}
					intent.setLoanDate(s.format(new Date()));
					intent.setFactDate(new Date());
					intent.setIsCheck(state);
					dao.merge(intent);
					
//					BpCustMember b = bpCustMemberDao.get(intent.getInvestPersonId());
//					Organization o = organizationService.organizationOne(b.getDepartmentRecommend());
//					QueryFilter qf = new QueryFilter();
//					qf.addFilter("Q_collectionDepartment_S_EQ", o.getOrgId().toString());
//					List<SettlementReviewerPay> listsettle = settlementReviewerPayService.getAll(qf);
//					for(int j=0;j<listsettle.size();i++){
//						listsettle.get(j).setRemark3(intent.getLoanDate());//放款时间
//						settlementReviewerPayService.merge(listsettle.get(j));
//					}
				}
				
			}
			
		}
		//String hql = "update BpFundIntent as bf set bf.notMoney = 0 ";
		return false;
	}
	@Override
	public List<BpFundIntent> getByPlanIdAndPeriod(Long bidId,Integer payintentPeriod,String fundType) {
		String hql1="";
		if(fundType!=null&&!fundType.equals("")&&fundType.equals("notCommoninterest")){
			 hql1 = "from BpFundIntent as bf where bf.bidPlanId = ? and bf.payintentPeriod = ? and bf.fundType in ('couponInterest','principalCoupons','subjoinInterest')";
			 List<BpFundIntent> list = dao.findByHql(hql1, new Object[]{bidId,payintentPeriod});
				return list;
		}else{
			 hql1 = "from BpFundIntent as bf where bf.bidPlanId = ? and bf.payintentPeriod = ? and bf.fundType=?";
			 List<BpFundIntent> list = dao.findByHql(hql1, new Object[]{bidId,payintentPeriod,fundType});
				return list;
		}
	}
	@Override
	public List<BpFundIntent> getByPlanIdA(Long bidId, Long preceptId,
			String  orderNo, String fundType) {
		// TODO Auto-generated method stub
		if(preceptId==null&&orderNo==null){
			String hql1="";
			if(fundType!=null&&!fundType.equals("")&&fundType.equals("coupons")){
				 hql1 = "from BpFundIntent as bf where bf.bidPlanId = ?  and bf.fundType in ('principalCoupons','couponInterest','subjoinInterest') and bf.factDate is null";
			}else{
				 hql1 = "from BpFundIntent as bf where bf.bidPlanId = ?  and bf.fundType in ('"+fundType+"') and bf.factDate is null";
			}
			
			List<BpFundIntent> list = dao.findByHql(hql1, new Object[]{bidId});
			return list;
		}else{
			String hql1 = "from BpFundIntent as bf where bf.bidPlanId = ? and bf.orderNo = ? and bf.fundType in ("+fundType+")";
			List<BpFundIntent> list = dao.findByHql(hql1, new Object[]{bidId,orderNo});
			return list;
		}
		
	}
	@Override
	public List<BpFundIntent> getByPlanIdAndPeriod(Long bidId,
			Integer payintentPeriod, String fundType, Long personId,String orderNum) {
		// TODO Auto-generated method stub
		return dao.getByPlanIdAndPeriod( bidId,
				 payintentPeriod,  fundType,  personId,orderNum);
	}
	
	@Override
	public List<BpFundIntentPeriod> getRaiseBpfundIntent(Long bidId) {
		// TODO Auto-generated method stub
		return dao.getRaiseBpfundIntent(bidId);
	}
	
	@Override
	public List<SlFundIntentPeriod> getCouponsIncome(PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getCouponsIncome(pb);
	}
	@Override
	public List<BpFundIntent> listOfInverstPerson(String orderNo,
			String fundTypes) {
		// TODO Auto-generated method stub
		return dao.listOfInverstPerson(orderNo, fundTypes);
	}
	
	@Override
	public List<BpFundIntent> getdistributeMoney(Long planId,
			Long payintentPeriod, String fundType, String remak) {
		// TODO Auto-generated method stub
		return dao.getdistributeMoney(planId, payintentPeriod, fundType, remak);
	}
	/**
	 * 根据投资人的id获得投资人当期应该受到的钱是多少
	 * @param temp
	 * @param planId
	 * @param peridId
	 * @param object
	 * @return
	 */
	@Override
	public BigDecimal getByPlanIdAndOtherRequest(String temp, String planId,
			String peridId, String type) {
		// TODO Auto-generated method stub
		return dao.getByPlanIdAndOtherRequest(temp,planId,peridId,type);
	}
	@Override
	public void createPunishByTiming(){		
		List<String> fundtypelist=new ArrayList<String>();
		fundtypelist.add("principalRepayment");
		fundtypelist.add("loanInterest");
		fundtypelist.add("consultationMoney");//财务服务费
		fundtypelist.add("serviceMoney");//管理咨询费
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		//cal.add(Calendar.DATE,-5);
		List<BpFundIntent> listbyOwe=	dao.listbyOwe("SmallLoan",fundtypelist,cal.getTime());
		//跑批记录 
		AppUser appUser = ContextUtil.getCurrentUser();
		String pushUserName = "定时跑批";
		Long pushUserId = null;
		if(null != appUser && !"".equals(appUser)){
			pushUserName = appUser.getFullname();
			pushUserId = appUser.getUserId();
		}
		BatchRunRecord batchRunRecord = new BatchRunRecord();
		batchRunRecord.setRunType(BatchRunRecord.HRY_1001);
		batchRunRecord.setAppUserId(pushUserId);
		batchRunRecord.setAppUserName(pushUserName);
		batchRunRecord.setStartRunDate(new Date());
		batchRunRecord.setTotalNumber(Long.valueOf(listbyOwe.size()));
		batchRunRecord.setHappenAbnorma("正常");
		for(BpFundIntent owe:listbyOwe){
			try {
				if(owe.getBidPlanId()==6091){
					System.out.println("");
				}
				BigDecimal overduerate=new BigDecimal("0");
				SlSmallloanProject slSmallloanProject=slSmallloanProjectService.get(owe.getProjectId());
				if(owe.getFundType().equals("principalRepayment")){
//					System.out.println("本金罚息的利率是"+slSmallloanProject.getOverdueRate());
					if(null!=slSmallloanProject.getOverdueRate()&&slSmallloanProject.getOverdueRate().compareTo(new BigDecimal(0))==1){
						overduerate=slSmallloanProject.getOverdueRate();
					}
				}else if(owe.getFundType().equals("loanInterest")){//loanInterest
//					System.out.println("利息罚息的利率是"+slSmallloanProject.getOverdueRate());
					if(null!=slSmallloanProject.getOverdueRate()&&slSmallloanProject.getOverdueRate().compareTo(new BigDecimal(0))==1){
						overduerate=slSmallloanProject.getOverdueRate();
					}
				}else if(owe.getFundType().equals("consultationMoney")){
//					System.out.println("利息罚息的利率是"+slSmallloanProject.getOverdueRate());
					if(null!=slSmallloanProject.getOverdueRate()&&slSmallloanProject.getOverdueRate().compareTo(new BigDecimal(0))==1){
						overduerate=slSmallloanProject.getOverdueRate();
					}
				}else if(owe.getFundType().equals("serviceMoney")){
//					System.out.println("利息罚息的利率是"+slSmallloanProject.getOverdueRate());
					if(null!=slSmallloanProject.getOverdueRate()&&slSmallloanProject.getOverdueRate().compareTo(new BigDecimal(0))==1){
						overduerate=slSmallloanProject.getOverdueRate();
					}
				}
				Long days=DateUtil.compare_date(owe.getIntentDate(),(owe.getFactDate()==null?new Date():owe.getFactDate()));
			      Long sortday = days; 
				  BigDecimal day1 = new BigDecimal((sortday>0?days:0));
			      BigDecimal OverdueMoney = overduerate.multiply(day1);
			      BigDecimal  sortoverdueMoney = OverdueMoney.multiply(owe.getNotMoney()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
//			      System.out.println("罚息金额是"+sortoverdueMoney);
			      owe.setAccrualMoney(sortoverdueMoney);
			      owe.setPunishDays(days.intValue());
			      dao.merge(owe);
			} catch (Exception e) {
				String ids="";
				if(null != batchRunRecord.getIds()){
					ids =batchRunRecord.getIds() + "," + owe.getFundIntentId();
				}else{
					ids = owe.getFundIntentId().toString();
				}
				batchRunRecord.setIds(ids);
				batchRunRecord.setHappenAbnorma("异常");
				e.printStackTrace();
			}
			
		}
		batchRunRecord.setEndRunDate(new Date());
		batchRunRecordService.save(batchRunRecord);
	}
	@Override
	public void saveFundIntent(String fundIntentJson, PlBidPlan plan,
			BpFundProject project, Short isCheck) {
		dao.saveFundIntent(fundIntentJson, plan, project, isCheck);
		
	}
		
	@Override
	public List<BpFundIntent> getByRequestNo(String requestNo) {
		// TODO Auto-generated method stub
		return dao.getByRequestNo(requestNo);
	}
	@Override
	public List<BpFundIntent> getCouponsIntent(String planId, String peridId,String requestNo,String fundType) {
		// TODO Auto-generated method stub
		return dao.getCouponsIntent(planId, peridId,requestNo,fundType);
	}
	@Override
	public BigDecimal getPrincipalMoney(Long bidPlanId, String date,String orderNo) {
		
		return dao.getPrincipalMoney(bidPlanId, date,orderNo);
	}
	@Override
	public List<BpFundIntent> listByEarlyDate(Long bidPlanId, String orderNo,
			String earlyDate,String fundType) {
	
		return dao.listByEarlyDate(bidPlanId, orderNo, earlyDate,fundType);
	}
	@Override
	public List<BpFundIntent> listbySlEarlyRepaymentId(Long bidPlanId,
			Long slEarlyRepaymentId) {
		
		return dao.listbySlEarlyRepaymentId(bidPlanId, slEarlyRepaymentId);
	}
	@Override
	public void saveCommonFundIntent(String fundIntentJson, PlBidPlan plan,
			BpFundProject project, Short isCheck) {
		dao.saveCommonFundIntent(fundIntentJson, plan, project, isCheck);
		
	}
	@Override
	public List<BpFundIntent> listOfInvestAndEarlyId(String orderNo,
			String fundTypes, Long slEarlyRepaymentId) {
		
		return dao.listOfInvestAndEarlyId(orderNo, fundTypes, slEarlyRepaymentId);
	}
	@Override
	public List<BpFundIntent> listByOrderNoAndEarlyId(String orderNo,
			String fundTypes, Long slEarlyRepaymentId) {
		
		return dao.listByOrderNoAndEarlyId(orderNo, fundTypes, slEarlyRepaymentId);
	}
	@Override
	public List<BpFundIntent> listByOrderNo(Long bidPlanId, String orderNo) {
		
		return dao.listByOrderNo(bidPlanId, orderNo);
	}

	@Override
	public List<BpFundIntentPeriod> listByBidPlanIdAndpayintentPeriodAndorerNo(
			PagingBean pb, Map<String, String> map) {
		
		List<BpFundIntentPeriod> list=	dao.listByBidPlanIdAndpayintentPeriodAndorerNo(pb, map);
		int j=0;
		if(null!=pb){
			for(BpFundIntentPeriod  l:list){
				List<BpFundIntent>	bpfundlist=dao.getByBidPlanIdAndIntentDate(l.getPlanId(), l.getIntentDate(), l.getOrderNo());
				l.initBpFundIntentPeriod(bpfundlist);
				j++;
			}
		}
		return list;
	}
	@Override
	public List<FundIntent> getLoanPersonIntentList(String preceptId ,Long bidPlanId) {
		
		Long slEarlyRepaymentId = null;
		
		List<FundIntent> list = new ArrayList<FundIntent>();
		List<FundIntent> list1 = new ArrayList<FundIntent>();
		
		
		List<InvestPersonInfo> li = new ArrayList<InvestPersonInfo>();
		if(null!=bidPlanId){
			li = investPersonInfoService.getByBidPlanId(bidPlanId);
		}else{
			li = investPersonInfoService.getByMoneyPlanId(Long.parseLong(preceptId));
		}
		
	
		if(li!=null&&li.size()!=0){
		
			int i=1;
			for(InvestPersonInfo person:li){
				List<BpFundIntent> filist = new ArrayList<BpFundIntent>();
				
				if(null!=slEarlyRepaymentId){
					filist=listOfInvestAndEarlyId(person.getOrderNo(), "",slEarlyRepaymentId);
				}else{
					filist=listOfInverstPerson(person.getOrderNo(), "");
				}
			   if(i==1){
				   
				   list1.addAll(filist);
				   
			   }else{
				   for(int j=0;j<list1.size();j++){
					   fundIntentService.evict(list1.get(j));
					   if(list1.get(j).getIncomeMoney().compareTo(new BigDecimal("0"))==1){
						   list1.get(j).setIncomeMoney( list1.get(j).getIncomeMoney().add(filist.get(j).getIncomeMoney()));
						   list1.get(j).setNotMoney( list1.get(j).getIncomeMoney());
					   }else{
						   list1.get(j).setPayMoney( list1.get(j).getPayMoney().add(filist.get(j).getPayMoney()));
						   list1.get(j).setNotMoney( list1.get(j).getPayMoney());
					   }
					   list1.get(j).setFundTypeName(dictionaryIndependentService.getByDicKey(list1.get(j).getFundType()).get(0).getItemValue());
				   }
				   
			   }
				i++;
			}
		}
		
		return list1;
		
	}
	
	@Override
	public List bidFundList(Long bidPlanId) {
		
		return dao.bidFundList(bidPlanId);
	}
	@Override
	public void deleteFundIntent(Long bidPlanId) {
		dao.deleteFundIntent(bidPlanId);
		
	}
	@Override
	public void deleteFundType(String fundType,Long bidPlanId) {
		dao.deleteFundType(fundType,bidPlanId);
		
	}
	@Override
	public void saveFundIntent(List<BpFundIntent> list,Long bidPlanId) {
		dao.saveFundIntent(list,bidPlanId);
	}
	@Override
	public BigDecimal getPrincipalAndInterest(Long bidPlanId) {
		
		return dao.getPrincipalAndInterest(bidPlanId);
	}
	@Override
	public List bidFundListByOrderNo(Long bidPlanId, String orderNo) {
		
		return dao.bidFundListByOrderNo(bidPlanId, orderNo);
	}
	@Override
	public void calculateSubjoinInterest(PlBidPlan plBidPlan,InvestPersonInfo person) {
		
		//开始计算---优惠券利息--加息利息
		try {
			/**----------------------计算优惠券利息------------------*/
			//此标可以用优惠券
			if(plBidPlan.getCoupon()!=null&&plBidPlan.getCoupon().intValue()==1){
				//查询出对应的投标记录
				PlBidInfo plBidInfo = plBidInfoService.getByOrderNo(person.getOrderNo());
				if(plBidInfo.getCouponId()!=null&&plBidPlan.getRebateType()!=null&&plBidPlan.getRebateWay()!=null){//如果优惠券不为空
					//通过投标记录查找优惠券
					BpCoupons bpCoupons = bpCouponsService.get(plBidInfo.getCouponId());
					if(bpCoupons.getCouponType().toString().equals("3")){//加息券
						/**----------------------计算加息利息------------------*/
						//起息日期和还款日期之间的天数
						//int datePeriod = DateUtil.getDaysBetweenDate(plBidPlan.getStartIntentDate(), plBidPlan.getEndIntentDate());
						//得到日化利率  利率为加息利率,投资金额按照利息计算方式（只生成利息）
						BigDecimal subjoinInterest = bpCoupons.getCouponValue()
													.divide(new BigDecimal(100))
													.divide(new BigDecimal(360),100,BigDecimal.ROUND_UP);
						//设置加息利息
						person.setSubjoinInterest(subjoinInterest);
						person.setRebateType(plBidPlan.getRebateType());
						person.setRebateWay(plBidPlan.getRebateWay());
					}else{//普通返现券
						/**
						 * 计算优惠券奖励金额
						 * 
						 */
						//如果投标金额大于优惠券金额
						BigDecimal couponInterest = null;
						//RebateType  返利类型  1=返现 ，2=返息，3=返息现，4=加息
						//RebateWay   返利方式  1=立返 ，2=随期,3=到期
						if(plBidPlan.getRebateWay()==1||plBidPlan.getRebateWay()==3){//立返=
							if(plBidPlan.getRebateType()==1){//面值*折现比例
								couponInterest = bpCoupons.getCouponValue().multiply(plBidPlan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
							}else if(plBidPlan.getRebateType()==2){//面值*折现比例 按标的计息方式生成收息表(没有本金)进行加合利息
								couponInterest = bpCoupons.getCouponValue().multiply(plBidPlan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
							}else if(plBidPlan.getRebateType()==3){//面值*折现比例 按标的计息方式生成收息表(有本金)进行加合本息
								couponInterest = bpCoupons.getCouponValue().multiply(plBidPlan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
							}
						}else{//随期=
							if(plBidPlan.getRebateType()==1){//当期本金*【面值*折现比例】
								//得到   面值*折现比例
								couponInterest = bpCoupons.getCouponValue().multiply(plBidPlan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
							}else if(plBidPlan.getRebateType()==2){//面值*折现比例 按标的计息方式生成收息表(没有本金) 不进行加合利息
								couponInterest = bpCoupons.getCouponValue().multiply(plBidPlan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
							}else if(plBidPlan.getRebateType()==3){//面值*折现比例按标的计息方式生成收息表(有本金) 随息发放本息
								couponInterest = bpCoupons.getCouponValue().multiply(plBidPlan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
							}
						}
						
//						if(person.getInvestMoney().compareTo(bpCoupons.getCouponValue())>0){
							//优惠券金额*返现利率
							//couponInterest = bpCoupons.getCouponValue().multiply(plBidPlan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
//						}else{
//							//投标金额*返现利率
//							couponInterest = person.getInvestMoney().multiply(plBidPlan.getReturnRatio()).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
//						}
						//设置优惠券利息
						person.setCouponInterest(couponInterest);
						person.setRebateType(plBidPlan.getRebateType());
						person.setRebateWay(plBidPlan.getRebateWay());
					}
					
				}
			}else{
				//此标是否可以使用普通加息利率  投资金额按加息利率以计息方式进行计算，随利息发放
				if(plBidPlan.getAddRate()!=null&&plBidPlan.getAddRate().compareTo(new BigDecimal("0"))==1){
					BigDecimal subjoinInterest = plBidPlan.getAddRate()
					.divide(new BigDecimal(12),2)
					.divide(new BigDecimal(3000),10,BigDecimal.ROUND_UP);
					person.setRebateType(0);
					person.setRebateWay(plBidPlan.getRebateWay());
					person.setSubjoinInterest(subjoinInterest);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//结束计算
		
	}
	@Override
	public List<BpFundIntent> listInterest(Long bidPlanId) {
		String hql = " from BpFundIntent where bidPlanId = ? and fundType in ('subjoinInterest','couponInterest','principalCoupons','commoninterest','raiseinterest') and isValid=0";
		List<BpFundIntent> list = dao.findByHql(hql, new Object[]{bidPlanId});
		
		return list;
	}
	
	/**
	 *根据投资人ID统计已还款金额
	 * @param temp
	 * @param planId
	 * @param peridId
	 * @param object
	 * @return
	 */
	@Override
	public BigDecimal getAfterMoney(String temp, String planId,
			String peridId, String type) {
		// TODO Auto-generated method stub
		return dao.getAfterMoney(temp,planId,peridId,type);
	}

	@Override
	public List<BpFundIntent> getNotMoneyInfoByBidId(Long bidPlanId) {
		String hql = "from BpFundIntent bf where bf.bidPlanId=? and bf.fundType<> 'principalLending' and bf.isCheck=0 and bf.isValid=0 and bf.notMoney>0";
		return dao.findByHql(hql, new Object[]{bidPlanId});
	}

	
	@Override
	public List<BpFundIntent> getThirdBpFundIntentList(String bidId,
			Date intentDate) {
		// TODO Auto-generated method stub
		return dao.getThirdBpFundIntentList(bidId, intentDate);
	}
	@Override
	public BigDecimal getFinancialMoney(Long bidPlanId, String type) {
		// TODO Auto-generated method stub
		return dao.getFinancialMoney(bidPlanId, type);
	}
	@Override
	public BigDecimal getMoneyPerPeriod(String bidId, String period) {
		// TODO Auto-generated method stub
		return dao.getMoneyPerPeriod(bidId, period);
	}
	@Override
	public List<BpFundIntent> getByRequestNoLoaner(String requestNo) {
		// TODO Auto-generated method stub
		return dao.getByRequestNoLoaner(requestNo);
	}
	@Override
	public List<BpFundIntent> getByBidIdandPeriod(String bidId, String period) {
		// TODO Auto-generated method stub
		return dao.getByBidIdandPeriod(bidId, period);
	}
	@Override
	public BigDecimal getPlateFeeByPlanIdandPeriod(String planId, String period) {
		// TODO Auto-generated method stub
		return dao.getPlateFeeByPlanIdandPeriod(planId, period);
	}


	@Override
	public List<BpFundIntent> getOverList(String bidId) {
		// TODO Auto-generated method stub
		return dao.getOverList(bidId);
	}
	@Override
	public List<BpFundIntent> bidFundList2(long bidId) {
		return dao.bidFundList2(bidId);
	}
	@Override
	public BigDecimal getPrincipalAndInterest(Long bidPlanId, String orderNo) {
		return dao.getPrincipalAndInterest(bidPlanId,orderNo);
	}
	@Override
	public void getOverdueFundIntent() {
		//p2p借款项目
		//查询当天逾期的项目
		List<BpFundIntent> overdueList = dao.getOverdueFundIntent("0");
		//查询明天到期的项目
		List<BpFundIntent> overdueList1 = dao.getOverdueFundIntent("1");
		//查询后5天到期的项目
		List<BpFundIntent> overdueList5 = dao.getOverdueFundIntent("5");
		//发送短信提醒
		SmsSendUtil send = new SmsSendUtil();
		for(BpFundIntent fund :overdueList){
			Map<String, String> map = new HashMap<String, String>();
			map.put("telephone", fund.getOpposittelephone());
			map.put("${name}", fund.getOppositeName());
			map.put("${projName}", fund.getProjectName());
			map.put("${payintentPeriod}",  fund.getPayintentPeriod().toString());
			map.put("${money}",  fund.getNotMoney().toString());
			map.put("key", "sms_overdueProj");
//			smsSendService.smsSending(map);
			//send.sms_overdueProj(fund.getOppositeName(), fund.getOpposittelephone(), fund.getProjectName(), fund.getPayintentPeriod().toString(), fund.getNotMoney().toString());
		}
		for(BpFundIntent fund :overdueList1){
			String intentDate = fund.getIntentDate().toString();
			String str[] = intentDate.split("-");
			Map<String, String> map = new HashMap<String, String>();
			map.put("telephone", fund.getOpposittelephone());
			map.put("${name}", fund.getOppositeName());
			map.put("${projName}", fund.getProjectName());
			map.put("${payintentPeriod}",  fund.getPayintentPeriod().toString());
			map.put("${year}",  str[0]);
			map.put("${month}",  str[1]);
			map.put("${day}",  str[2]);
			map.put("${money}",  fund.getNotMoney().toString());
			map.put("key", "sms_huankuancuishou");
//			smsSendService.smsSending(map);
			//send.sms_huankuancuishou(fund.getProjectName(),fund.getPayintentPeriod().toString(),fund.getOppositeName(), fund.getOpposittelephone(), fund.getNotMoney().toString(), str[0], str[1], str[2]);
		}
		for(BpFundIntent fund :overdueList5){
			String intentDate = fund.getIntentDate().toString();
			String str[] = intentDate.split("-");
			Map<String, String> map = new HashMap<String, String>();
			map.put("telephone", fund.getOpposittelephone());
			map.put("${name}", fund.getOppositeName());
			map.put("${projName}", fund.getProjectName());
			map.put("${payintentPeriod}",  fund.getPayintentPeriod().toString());
			map.put("${year}",  str[0]);
			map.put("${month}",  str[1]);
			map.put("${day}",  str[2]);
			map.put("${money}",  fund.getNotMoney().toString());
			map.put("key", "sms_huankuancuishou");
//			smsSendService.smsSending(map);
			//send.sms_huankuancuishou(fund.getProjectName(),fund.getPayintentPeriod().toString(),fund.getOppositeName(), fund.getOpposittelephone(), fund.getNotMoney().toString(), str[0], str[1], str[2]);
		}
		//erp小贷项目
		List<SlFundIntent> slfundList = dao.getOverdueSlIntent("0");
		for(SlFundIntent sl : slfundList){
			Enterprise enterprise1=new Enterprise();
			Person person=new Person();
			String name = "";
			String telphone="";
			if(sl.getOppositeType().equals("company_customer")){
				enterprise1=this.enterpriseService.getById(sl.getOppositeID().intValue());
				telphone=enterprise1.getTelephone();
				name=enterprise1.getEnterprisename();
			}else {
				person=this.personService.getById(sl.getOppositeID().intValue());
				telphone=person.getCellphone();
				name=person.getName();
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("telephone", telphone);
			map.put("${name}", name);
			map.put("${projName}", sl.getProjectName());
			map.put("${payintentPeriod}",  sl.getPayintentPeriod().toString());
			map.put("${money}",  sl.getNotMoney().toString());
			map.put("key", "sms_overdueProj");
//			smsSendService.smsSending(map);
			//send.sms_overdueProj(name,telphone,sl.getProjectName(), sl.getProjectNumber(), sl.getNotMoney().toString());
		}
	}

	@Override
	public List<BpFundIntent> findByType(Long personId, Long bidId) {


		return dao.findByType(personId, bidId);
	}

    @Override
    public List<BpFundIntent> listCollectPeriod(Long bidPlanId) {
        String hql = " from BpFundIntent where bidPlanId = ? and fundType = 'raiseinterest'  and isValid=0";
        List<BpFundIntent> list = dao.findByHql(hql, new Object[]{bidPlanId});
        return list;
    }

	@Override
	public List<BpFundIntent> getRaiseinterestNotMoney() {
		String hql = "from BpFundIntent WHERE fundType = 'raiseinterest' and incomeMoney != afterMoney and  notMoney > 0";
		List<BpFundIntent> list = dao.findByHql(hql, new Object[]{});
		return list;
	}

}