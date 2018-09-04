package com.zhiwei.credit.service.activity.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.integral.IntegralManage;
import com.zhiwei.core.integral.IntegralManageImpl;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.activity.BpActivityManageDao;
import com.zhiwei.credit.dao.coupon.BpCouponsDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.activity.BpActivityManage;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.bonusSystem.gradeSet.MemberGradeSet;
import com.zhiwei.credit.model.creditFlow.bonusSystem.record.WebBonusRecord;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;
import com.zhiwei.credit.service.activity.BpActivityManageService;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.bonusSystem.gradeSet.MemberGradeSetService;
import com.zhiwei.credit.service.creditFlow.bonusSystem.record.WebBonusRecordService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedEnvelopeService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedMemberService;
import com.zhiwei.credit.util.Common;

/**
 * 
 * @author 
 *
 */
public class BpActivityManageServiceImpl extends BaseServiceImpl<BpActivityManage> implements BpActivityManageService{
	private BpActivityManageDao dao;
	
	@Resource
	private BpCustMemberDao bpCustMemberDao;
	@Resource
	private BpCouponsDao bpCouponsDao;
	@Resource
	private BpCouponsService bpCouponsService;
	@Resource
	private IntegralManage integralManage;
	@Resource
	private BpCustRedEnvelopeService bpCustRedEnvelopeService;
	@Resource
	private BpCustRedMemberService bpCustRedMemberService;
	@Resource
	private MemberGradeSetService memberGradeSetService;
	@Resource
	private WebBonusRecordService webBonusRecordService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private BpActivityManageService bpActivityManageService;
	
	private static Map configMap = AppUtil.getConfigMap();
	public BpActivityManageServiceImpl(BpActivityManageDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public void findActivityNumber(String flag) {
		StringBuffer sb = new StringBuffer("{success:true,data:");
		String str_Date = DateUtil.getNowDateTime("yyyyMMdd");
		StringBuffer num = new StringBuffer();
		int number = 0;
		if("0".equals(flag)){
			num.append("JF").append(str_Date);
		}else if("1".equals(flag)){
			num.append("HB").append(str_Date);
		}else if("2".equals(flag)){
			num.append("YH").append(str_Date);
		}else if("3".equals(flag)){
			num.append("JFDHYH").append(str_Date);
		}
		List<BpActivityManage> list=dao.findActivityNumber(flag);
		if(null!=list && list.size()>0){
			number=list.size();
			//注释原因--数值从0开始数的，不用再加1  liusl
			//number+=1;
			if(list.size()<10){
				num.append("000").append(number);
			}else if(list.size()>=10&&list.size()<100){
				num.append("00").append(number);
			}else if(list.size()>=100){
				num.append("0").append(number);
			}
		}else{
			num.append("000").append(number);
		}
		sb.append("'").append(num).append("'}");
		JsonUtil.responseJsonString(sb.toString());
	}

	@Override
	public boolean findExistCrossDate(BpActivityManage bpActivityManage) {
		List<BpActivityManage> list=dao.findExistCrossDate(bpActivityManage);
		if(null!=list && list.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean closeActivity(String[] ids) {
		try{
			for (String id : ids) {
				BpActivityManage oldManage=dao.get(Long.valueOf(id));
				if(oldManage.getStatus()==0){
					oldManage.setStatus(1);
					dao.merge(oldManage);
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("删除活动失败:" + e.getMessage());
			return false;
		}
	}

	@Override
	public void findList(PageBean<BpActivityManage> pageBean) {
		dao.findList(pageBean);
	}
	
	@Override
	public List<BpActivityManage> listActivity(String activityType) {
		return dao.listActivity(Long.valueOf(activityType),null);
	}
	

	/**
	 * 自动分发活动奖励引擎
	 * 活动中心，所有活动查询
	 * --自动派发积分，
	 *   自动派发红包
	 *   自动派发优惠券
     * @param bpCustMemberId   --p2p账户ID
     * @param money   --金额--用于投标，充值时进行条件判断
     * @param activityType   --活动操作类型
     * 		  		["注册", "1"],
					["邀请", "2"],
					["投标", "3"],
					["充值", "4"],
					["邀请好友第一次投标", "5"]
					["累计投资", "6"],
					["累计充值", "7"],
					["首投", "8"],
					["累计推荐投资", "9"]			
     * 
     * 
     * 
     * @author LIUSL
	 */
	@Override
	public void autoDistributeEngine(String activityType, String memberId,BigDecimal money,Date buyDate) {
		 
		/**
		 * 此方法被调用的地方有   注册，邀请，投标，充值，邀请好友第一次投标
		 * 每次传一个操作点类型过来，进行三个活动查询，分别进行循环，依次派发对应奖励
		 */
		try {
			//标识--是否加过活动积分
			boolean indexFlag = false;
			
			//查出同一个操作点的全部活动
			List<BpActivityManage> list = dao.listActivity(Long.valueOf(activityType),buyDate);
			if(list!=null&&list.size()>0){
				for(int i = 0 ; i < list.size() ; i++ ){
					String bpCustMemberId = memberId;
					//拿到具体的活动实例
					BpActivityManage bpActivityManage = list.get(i);
					//查询一个活动中累计交易的金额6，7，9
					if(activityType.equals("6")||activityType.equals("7")){
						money=sumMoney(activityType,bpCustMemberId,bpActivityManage);
					}else if(activityType.equals("9")){
						money=sumMoney(activityType,bpCustMemberId,bpActivityManage);
						//查找推荐人
						 BpCustMember custMem=bpCustMemberService.get(Long.valueOf(bpCustMemberId));
						 BpCustMember mem = bpCustMemberService.isRecommandPerson(custMem.getRecommandPerson());
						 if(mem!=null){
							 bpCustMemberId=mem.getId().toString();
						 }
					}
					//判断活动的类型，区分是积分活动，红包活动，优惠券活动
					if(bpActivityManage.getFlag()!=null&&bpActivityManage.getFlag()==0){//积分
						
						/*-------------------------------自动增加积分---------------------------------------------*/
						//如果积分活动是注册则直接增加积分
						if("1".equals(activityType)){
							/*******************/
							//更改标识--标识为活动积分增加过
							indexFlag= true;
							/*******************/
							
							integralManage.addSocreBpActivityManage(Long.valueOf(bpCustMemberId), bpActivityManage.getParValue().longValue(), bpActivityManage);
						}else{//否则根据条件奖励积分
							/*******************/
							//更改标识--标识为活动积分增加过
							indexFlag= true;
							/*******************/
							//判断每一个梯度只能奖励一次，只有累计的判断6，7，9
							boolean send = checkSend(bpActivityManage,bpCustMemberId,activityType,"0"); 
							//校验积分活动和红包活动是否带有条件约束
							boolean flag = checkCriteria(bpActivityManage, bpCustMemberId, money);
							if(flag&&send){
								
								//如果bpActivityManage.getReferenceUnit()不等于3----发一次奖励
								if(bpActivityManage.getReferenceUnit().compareTo(Long.valueOf(3))!=0){
									integralManage.addSocreBpActivityManage(Long.valueOf(bpCustMemberId), bpActivityManage.getParValue().longValue(), bpActivityManage);
								}else{//否则发以金额成倍奖励   --以投标金额翻倍
									//倍数
									BigDecimal multiple = money.divide(new BigDecimal(bpActivityManage.getMoney()),0,RoundingMode.DOWN);
									//成倍奖励  --带for就有多条，不带for就一条
									for(int x = 0 ; x < multiple.intValue() ; x ++){
										integralManage.addSocreBpActivityManage(Long.valueOf(bpCustMemberId), bpActivityManage.getParValue().longValue(), bpActivityManage);
									} 
									
								}
							}
						}
						
						
					}else if(bpActivityManage.getFlag()!=null&&bpActivityManage.getFlag()==1){//红包
						/*-------------------------------自动派发红包---------------------------------------------*/
						//如果红包活动是注册则直接增加积分
						if("1".equals(activityType)){
							
							bpCustRedMemberService.saveByBpActivityManage(Long.valueOf(bpCustMemberId), bpActivityManage.getParValue(), bpActivityManage);
						}else{//否则根据条件奖励积分
							//判断每一个梯度只能奖励一次，只有累计的判断6，7，9
							boolean send = checkSend(bpActivityManage,bpCustMemberId,activityType,"1"); 
							//校验积分活动和红包活动是否带有条件约束
							boolean flag = checkCriteria(bpActivityManage, bpCustMemberId, money);
							if(flag&&send){
								//如果bpActivityManage.getReferenceUnit()不等于3----发一次奖励
								if(bpActivityManage.getReferenceUnit().compareTo(Long.valueOf(3))!=0){
									bpCustRedMemberService.saveByBpActivityManage(Long.valueOf(bpCustMemberId), bpActivityManage.getParValue(), bpActivityManage);
								}else{//否则发以金额成倍奖励
									//倍数
									BigDecimal multiple = money.divide(new BigDecimal(bpActivityManage.getMoney()),0,RoundingMode.DOWN);
									//成倍奖励   --带for就有多条，不带for就一条
									for(int x = 0 ; x < multiple.intValue() ; x ++){
										bpCustRedMemberService.saveByBpActivityManage(Long.valueOf(bpCustMemberId), bpActivityManage.getParValue().multiply(multiple), bpActivityManage);
									} 
								}
							}
						}	
					}else if(bpActivityManage.getFlag()!=null&&bpActivityManage.getFlag()==2){//优惠券
						/*-------------------------------自动派发优惠券---------------------------------------------*/
						
						//如果活动是注册则直接增加
						if("1".equals(activityType)){
							bpCouponsService.saveBpCoupons(bpCustMemberId, bpActivityManage);
						}else{//否则根据条件奖励积分
							//判断每一个梯度只能奖励一次，只有累计的判断6，7，9
							boolean send = checkSend(bpActivityManage,bpCustMemberId,activityType,"2"); 
							//校验优惠券是否带有条件约束
							boolean flag = checkCriteria(bpActivityManage, bpCustMemberId, money);
							if(flag&&send){
								//如果bpActivityManage.getReferenceUnit()不等于3----发一次奖励
								if(bpActivityManage.getReferenceUnit().compareTo(Long.valueOf(3))!=0){
									bpCouponsService.saveBpCoupons(bpCustMemberId, bpActivityManage);
								}else{//否则发以金额成倍奖励
									//倍数
									BigDecimal multiple = money.divide(new BigDecimal(bpActivityManage.getMoney()),0,RoundingMode.DOWN);
									//成倍奖励   --带for就有多条，不带for就一条
									for(int x = 0 ; x < multiple.intValue() ; x ++){
										bpCouponsService.saveBpCoupons(bpCustMemberId, bpActivityManage);
									} 
								}
								
							}
						}
						
					}
					
				}
			}
			
			
			//加入新的逻辑-----如果活动积分增加了积分-----则普通积分规则不能加分
			//如果活动积分没有增加过，则查询普通积分规则进行增加
			if(!indexFlag){
				try {
					IntegralManage integralManage = new IntegralManageImpl();
					String operationKey = "";
					/**
					1	["注册", "register"],
					2	["邀请", "invite"],
					3	["投标", "tender"],
					4	["充值", "recharge"],
					5	["邀请第一次投标", "inviteOnce"]
					 */
					if("1".equals(activityType)){
						operationKey = "register";
					}else if("2".equals(activityType)){
						operationKey = "invite";
					}else if("3".equals(activityType)){
						operationKey = "tender";
					}else if("4".equals(activityType)){
						operationKey = "recharge";
					}else if("5".equals(activityType)){
						operationKey = "inviteOnce";
					}
					//查询普通积分规则进行加分
					integralManage.addScordByFlagKey(Long.valueOf(memberId), operationKey);
				} catch (Exception e) {
					logger.error("--------------------普通积分奖励报错------------------------");
					e.printStackTrace();
				}
			}
			//------------------------------------------
			
			
			
		} catch (Exception e) {
			logger.error("活动分发方法报错----------");
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 校验积分活动和红包活动是否带有条件约束
	 * @param bpActivityManage   活动对象
	 * @param bpCustMemberId p2p账户ID
	 * @param money  金额
	 * @return  true条件通过，条件不通过false
	 */
	private boolean checkCriteria(BpActivityManage bpActivityManage, String bpCustMemberId,BigDecimal money){
		try {
			if(bpActivityManage.getReferenceUnit()!=null){
				//小于等于条件
				if(bpActivityManage.getReferenceUnit().compareTo(Long.valueOf(1))==0){
					//会员等级设定不为空，并且会员等级设置不为无
					if(bpActivityManage.getLevelId()!=null&&bpActivityManage.getLevelId().compareTo(Long.valueOf(0))!=0){
						BpCustMember bpCustMember = bpCustMemberDao.get(Long.valueOf(bpCustMemberId));
						//当前用户会员等级
						MemberGradeSet memberGradeSet = memberGradeSetService.findByUserScore(bpCustMember.getScore());
						//配置的会员等级
						MemberGradeSet confMemberGradeSet = memberGradeSetService.get(bpActivityManage.getLevelId());
						//当前用户会员等级大于配置的会员等级
						if(Long.valueOf(memberGradeSet.getLevelName()).compareTo(Long.valueOf(confMemberGradeSet.getLevelName()))>0){
							//则不满足条件返回false
							return false;
						}
					}
					//判断金额--如果当前金额大于条件所设定的金额，则返回false
					if(money.compareTo(new BigDecimal(bpActivityManage.getMoney()))>0){
						return false;
					}
					return true;
				//大于等于条件
				}else if(bpActivityManage.getReferenceUnit().compareTo(Long.valueOf(2))==0){
					//会员等级设定不为空，并且会员等级设置不为无
					if(bpActivityManage.getLevelId()!=null&&bpActivityManage.getLevelId().compareTo(Long.valueOf(0))!=0){
						BpCustMember bpCustMember = bpCustMemberDao.get(Long.valueOf(bpCustMemberId));
						//当前用户会员等级
						MemberGradeSet memberGradeSet = memberGradeSetService.findByUserScore(bpCustMember.getScore());
						//配置的会员等级
						MemberGradeSet confMemberGradeSet = memberGradeSetService.get(bpActivityManage.getLevelId());
						//当前用户会员等级小于配置的会员等级
						if(Long.valueOf(memberGradeSet.getLevelName()).compareTo(Long.valueOf(confMemberGradeSet.getLevelName()))<0){
							//则不满足条件返回false
							return false;
						}
					}
					//判断金额--如果当前金额小于条件所设定的金额，则返回false
					if(money.compareTo(new BigDecimal(bpActivityManage.getMoney()))<0){
						return false;
					}
					return true;
				//满足条件金额成倍奖励---默认为两个条件都大于等于
				}else if(bpActivityManage.getReferenceUnit().compareTo(Long.valueOf(3))==0){
					//会员等级设定不为空，并且会员等级设置不为无
					if(bpActivityManage.getLevelId()!=null&&bpActivityManage.getLevelId().compareTo(Long.valueOf(0))!=0){
						BpCustMember bpCustMember = bpCustMemberDao.get(Long.valueOf(bpCustMemberId));
						//当前用户会员等级
						MemberGradeSet memberGradeSet = memberGradeSetService.findByUserScore(bpCustMember.getScore());
						//配置的会员等级
						MemberGradeSet confMemberGradeSet = memberGradeSetService.get(bpActivityManage.getLevelId());
						//当前用户会员等级小于配置的会员等级
						if(Long.valueOf(memberGradeSet.getLevelName()).compareTo(Long.valueOf(confMemberGradeSet.getLevelName()))<0){
							//则不满足条件返回false
							return false;
						}
					}
					//判断金额--如果当前金额小于条件所设定的金额，则返回false
					if(money.compareTo(new BigDecimal(bpActivityManage.getMoney()))<0){
						return false;
					}
					return true;
				}else{//没有条件
					return true;
				}
			}else{//没有条件
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 查询设置的活动梯度是否已经派发
	 * ["累计投资", "6"],
		["累计充值", "7"],
		["首投", "8"],
		["累计推荐投资", "9"]	
	 * @param activityNumber
	 * @param bpCustMemberId
	 * @return
	 */
	private boolean checkSend(BpActivityManage bpActivityManage,String bpCustMemberId,String activityType,String flag){
		if(activityType.equals("6")||activityType.equals("7")||activityType.equals("9")){
			if(flag.equals("0")){//判断积分
				List<WebBonusRecord> list = webBonusRecordService.getActivityNumber(bpActivityManage.getActivityNumber(), bpCustMemberId, null);
				if(list.size()>=1){
					return false;
				}else{
					return true;
				}
			}else if(flag.equals("1")){//判断红包
				List<BpCustRedMember> list = bpCustRedMemberService.getActivityNumber(bpActivityManage.getActivityNumber(), bpCustMemberId, null);
				if(list.size()>=1){
					return false;
				}else{
					return true;
				}
			}else if(flag.equals("2")){//判断优惠券
				List<BpCoupons> list = bpCouponsService.getActivityType(BpCoupons.COUPONRESOURCE_ACTIVE,bpActivityManage.getActivityId(),bpCustMemberId);
				if(list.size()>=1){
					return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return true;
		}
		
	
	}
	/**
	 * 查询一个活动范围内的累计交易金额
	 * @param activityType
	 * @param bpCustMemberId
	 * @param bpActivityManage
	 * @return
	 */
	 private BigDecimal sumMoney(String activityType,String bpCustMemberId,BpActivityManage bpActivityManage)
	 {
		    BigDecimal sumMoney = null;
			if(activityType.equals("6")){//累计投资
				sumMoney = obAccountDealInfoService.sumPersonMoney("4", Long.valueOf(bpCustMemberId), bpActivityManage.getActivityStartDate().toString(), bpActivityManage.getActivityEndDate().toString());
			}else if(activityType.equals("7")){//累计充值
				sumMoney = obAccountDealInfoService.sumPersonMoney("1", Long.valueOf(bpCustMemberId), bpActivityManage.getActivityStartDate().toString(), bpActivityManage.getActivityEndDate().toString());
			}else if(activityType.equals("9")){//累计推荐投资（查询投资人的推荐人，根据推荐人查询出他名下所有的被推荐人，查询出所有被推荐人的投资金额之和）
				//查找推荐人
				BpCustMember custMem=bpCustMemberService.get(Long.valueOf(bpCustMemberId));
				BpCustMember mem = bpCustMemberService.isRecommandPerson(custMem.getRecommandPerson());
				//查询推荐人名下所有的被推荐人
				List<BpCustMember> memberList = bpCustMemberService.getBpCustMemberByrecommandPerson(mem.getPlainpassword());
				if(null != memberList && memberList.size()>0){
					Long[] investPersonIds = new Long[memberList.size()];
					for(int i = 0 ; i < memberList.size() ; i++ ){
						investPersonIds[i] = memberList.get(i).getId();
					}
					sumMoney = obAccountDealInfoService.sumMoney("4", investPersonIds, bpActivityManage.getActivityStartDate().toString(), bpActivityManage.getActivityEndDate().toString());
				}else{
					sumMoney = new BigDecimal("0");
				}
			}
			return sumMoney;
	 }
	 
	 
	 
	@Override
	public void addbpActivityManage(Long investPersonId,BigDecimal buyMoney,Date buyDate) {
		BpCustMember mem=null;
		//判断是否是第一次投标
    	QueryFilter fit = new QueryFilter();
    	fit.addFilter("Q_userId_L_EQ", investPersonId);
    	fit.addFilter("Q_state_SN_EQ", "2");
    	List<PlBidInfo> infoList = plBidInfoService.getAll(fit);
    	//判断是否是第一次购买理财计划
    	QueryFilter filter = new QueryFilter();
    	filter.addFilter("Q_investPersonId_L_EQ", investPersonId);
    	filter.addFilter("Q_state_SN_EQ", "2");
    	List<PlManageMoneyPlanBuyinfo> buyList = plManageMoneyPlanBuyinfoService.getAll(filter);
    	 //判断是否是推荐的用户
		 BpCustMember custMem=bpCustMemberService.get(investPersonId);
		 if(custMem.getRecommandPerson()!=null&&!custMem.getRecommandPerson().equals("")){
			 mem = bpCustMemberService.isRecommandPerson(custMem.getRecommandPerson());
		 }
		 //判断投标记录大于1 就表示不是首投
		 if(infoList.size()>1||buyList.size()>1){
			 bpActivityManageService.autoDistributeEngine("3", investPersonId.toString(),buyMoney,buyDate);
		 }else{
			//投标
			 bpActivityManageService.autoDistributeEngine("3", investPersonId.toString(),buyMoney,buyDate);
			 //首投
			 bpActivityManageService.autoDistributeEngine("8", investPersonId.toString(),buyMoney,buyDate);
			 if(mem!=null){
				//邀请好友第一次投标
				bpActivityManageService.autoDistributeEngine("5", mem.getId().toString(),buyMoney,buyDate);
			 }
		 }
		 if(mem!=null){
			 //累计推荐投资
			 bpActivityManageService.autoDistributeEngine("9", investPersonId.toString(),null,buyDate);
		 }
		 //累计投资
		 bpActivityManageService.autoDistributeEngine("6", investPersonId.toString(),null,buyDate);
	}

	/**
	 * 判断此标是否可用优惠券
	 * @param bidplan
	 * @param bidInfo1
	 * @param orderNo
	 */
	public void checkCoupons(PlBidPlan bidplan,PlBidInfo bidInfo1,String orderNo){
		//判断此标是否可用优惠券
		if(bidplan.getCoupon()!=null&&bidplan.getCoupon().compareTo(1)==0){
			//判断返利方式是否是 立还
			if(bidplan.getRebateWay().compareTo(1)==0){
				List<BpFundIntent> bpfundInterestList=null;//利息
				List<BpFundIntent> bpfundPrincipalList=null;//本金
				String transferType="";//资金类型
				//判断是否使用了优惠券，派发金额
				if(bidInfo1.getCouponId()!=null&&!bidInfo1.getCouponId().equals("")){
					//判断 返利类型
					if(bidplan.getRebateType().compareTo(1)==0){//返现 principalCoupons
						transferType=ObAccountDealInfo.T_BIDRETURN_RETURNRATIO;
						bpfundInterestList = bpFundIntentService.getByPlanIdA(bidInfo1.getBidId(), null, orderNo, "'principalCoupons'");
					}else if(bidplan.getRebateType().compareTo(2)==0){//返息 couponInterest
						transferType=ObAccountDealInfo.T_BIDRETURN_RATE27;
						bpfundInterestList = bpFundIntentService.getByPlanIdA(bidInfo1.getBidId(), null, orderNo, "'couponInterest'");
					}else if(bidplan.getRebateType().compareTo(3)==0){//返息现  principalCoupons couponInterest
						transferType=ObAccountDealInfo.T_BIDRETURN_RATE29;
						bpfundInterestList = bpFundIntentService.getByPlanIdA(bidInfo1.getBidId(), null, orderNo, "'couponInterest'");
						bpfundPrincipalList = bpFundIntentService.getByPlanIdA(bidInfo1.getBidId(), null, orderNo, "'principalCoupons'");
					}else if(bidplan.getRebateType().compareTo(4)==0){//加息 couponInterest
						transferType=ObAccountDealInfo.T_BIDRETURN_RATE30;
						bpfundInterestList = bpFundIntentService.getByPlanIdA(bidInfo1.getBidId(), null, orderNo, "'subjoinInterest'");
					}
				}
				if(bpfundInterestList!=null){//返利息
					couponIntent(bpfundInterestList,bidInfo1,bidplan,transferType);
				}
				if(bpfundPrincipalList!=null){//返本金
					couponIntent(bpfundPrincipalList,bidInfo1,bidplan,ObAccountDealInfo.T_BIDRETURN_RATE28);
				}
			}
			
			//更新优惠券为已使用
			if(bidInfo1.getCouponId()!=null&&!bidInfo1.getCouponId().equals("")){
				BpCoupons coupon = bpCouponsService.get(bidInfo1.getCouponId());
				coupon.setCouponStatus(Short.valueOf("10"));
				coupon.setUseProjectName(bidplan.getBidProName());
				coupon.setUseProjectNumber(bidplan.getBidProNumber());
				coupon.setUseProjectType(bidplan.getProType());
				coupon.setUseTime(new Date());
				bpCouponsService.save(coupon);
			}
		}else{
			//判断是否此标设置了普通加息
			if(bidplan.getAddRate()!=null&&!bidplan.getAddRate().equals("")){
				//判断是否设置的是立还
				if(bidplan.getRebateWay().compareTo(1)==0){
					List<BpFundIntent> commoninterest = bpFundIntentService.getByPlanIdA(bidplan.getBidId(), null,orderNo,"commoninterest");
					couponIntent(commoninterest,bidInfo1,bidplan,ObAccountDealInfo.T_BIDRETURN_ADDRATE);
				}
			}
		}
		//判断此标是否设置了募集期利率
		if(bidplan.getRaiseRate()!=null&&!bidplan.getRaiseRate().equals("")){
			List<BpFundIntent> raiseinterestList = bpFundIntentService.getByPlanIdA(bidInfo1.getBidId(), null, orderNo, "'raiseinterest'");
			couponIntent(raiseinterestList,bidInfo1,bidplan,ObAccountDealInfo.T_BIDRETURN_RATE31);
		}
	}
	/**
	 * 派发优惠券奖励
	 * @param bp
	 * @param info
	 */
	public void couponIntent(List<BpFundIntent> bp,PlBidInfo bidInfo1,PlBidPlan bidplan,String transferType){
		String loanerPayConfigId = "";
		if(bidplan.getReceiverP2PAccountNumber()!=null){
			loanerPayConfigId = bidplan.getReceiverP2PAccountNumber();
		}
		for(BpFundIntent bpfund:bp){
			if(bpfund.getFactDate()==null||bpfund.getFactDate().equals("")){
				BpCustMember mem=bpCustMemberService.get(bpfund.getInvestPersonId());
				String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				CommonRequst commonRequest = new CommonRequst();
				commonRequest.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方标识
				commonRequest.setRequsetNo(requestNo);//请求流水号
				commonRequest.setAmount(bpfund.getNotMoney());//交易金额
				commonRequest.setBidId(bpfund.getFundIntentId().toString());
				if(mem.getCustomerType()!=null&&mem.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
					commonRequest.setAccountType(1);
				}else{//借款人是个人
					commonRequest.setAccountType(0);
				}
				commonRequest.setLoaner_thirdPayflagId(mem.getThirdPayFlagId());//收款人第三方登陆账号
				commonRequest.setContractNo("");
				commonRequest.setBussinessType(ThirdPayConstants.BT_COUPONAWARD);//业务类型
				commonRequest.setTransferName(ThirdPayConstants.TN_COUPONAWARD);//业务名称
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
				
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					//添加资金明细
					Map<String,Object> map3=new HashMap<String,Object>();
					map3.put("investPersonId",mem.getId());//投资人Id（必填）
					map3.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
					map3.put("transferType",transferType);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
					map3.put("money",bpfund.getNotMoney());//交易金额	（必填）			 
					map3.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
					map3.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
					map3.put("recordNumber",requestNo);//交易流水号	（必填）
					map3.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
					obAccountDealInfoService.operateAcountInfo(map3);
					
					//更新款项
					bpfund.setNotMoney(new BigDecimal(0));
					bpfund.setAfterMoney(bpfund.getIncomeMoney());
					bpfund.setFactDate(new Date());
					bpfund.setRequestNo(requestNo);
					bpFundIntentService.save(bpfund);
				}
			}
			
		}
	}
	@Override
	public List<BpActivityManage> findBySendTypeAndState(Long sendType, Integer flag,Integer valueOf) {
		String hql = " from BpActivityManage where sendType = ? and flag = ? and status = ? ";
		return dao.findByHql(hql, new Object[]{sendType,flag,valueOf});
	}

	


}