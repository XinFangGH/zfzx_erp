package com.zhiwei.credit.service.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.StaleObjectStateException;

import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.thirdPayInterface.FuDianPay.FuDian;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObAccountDealInfoDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemAccountDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.credit.util.Processor;



/**
 * 
 * @author 
 *
 */
public class ObSystemAccountServiceImpl extends BaseServiceImpl<ObSystemAccount> implements ObSystemAccountService{
	@SuppressWarnings("unused")
	private ObSystemAccountDao dao;
	@Resource
	private ObAccountDealInfoDao obAccountDealInfoDao;
	
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private BpCustMemberDao bpCustMemberDao;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private BatchRunRecordService batchRunRecordService;
	public ObSystemAccountServiceImpl(ObSystemAccountDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<ObSystemAccount> findAccountList(String investName,String accountType,
			HttpServletRequest request, Integer start, Integer limit) {
		return dao.findAccountList(investName,accountType,request,start,limit);
	}

	@Override
	public BigDecimal getBalance(String investPersonId) {
		
		return dao.getBalance(investPersonId);
	}

	@Override
	public ObSystemAccount getByInvrstPersonId(Long investMentPersonId) {
		
		return dao.getByInvrstPersonId(investMentPersonId);
	}
    
	@Override
	public String[] saveAccount(String companyId,String investName,String investId,String cardNumber,String money,String type) {
		String[] str=new String[2];
		String ret="";
		String msg="";
		try{
			//新添加系统账户创建新账号的方法：身份证+客户类型
			Boolean isExit =dao.isExist("accountNumber", cardNumber+"-"+type);
			if(isExit){
				ret=Constants.CODE_FAILED;
				msg="当前投资人身份证已经注册了系统账户";
			}else{
				ObSystemAccount obSystemAccount = new ObSystemAccount();
				obSystemAccount.setCompanyId(Long.valueOf(companyId));
				obSystemAccount.setAccountName(investName);
				obSystemAccount.setInvestmentPersonId(Long.valueOf(investId));
				obSystemAccount.setAccountNumber(cardNumber+"-"+type);// 当前默认系统虚拟账户是投资人的身份证号码
				obSystemAccount.setTotalMoney(new BigDecimal(money));
				obSystemAccount.setInvestPersonName(investName);
				obSystemAccount.setInvestPersionType(Short.valueOf(type));// 1 线下客户
				dao.save(obSystemAccount);
				ret=Constants.CODE_SUCCESS;
				msg="系统账户成功保存！";
			}
		}catch(Exception e){
			ret=Constants.CODE_FAILED;
			msg="系统账户保存失败！";
		}
		str[0]=ret;
		str[1]=msg;
		return str;
	}

	@Override
	public ObSystemAccount getByInvrstPersonIdAndType(Long investPersionId,
			Short investPsersionType) {
		return dao.getByInvrstPersonIdAndType(investPersionId,investPsersionType);
	}

	@Override
	public BigDecimal changeAccountMoney(Long accountId, BigDecimal money,Short direction) {
		// TODO Auto-generated method stub
		try{
			ObSystemAccount account =this.dao.get(accountId);
			System.out.println("系统账户中更新标的状况");
			if(account!=null){
				if(null!=direction&& direction.compareTo(ObAccountDealInfo.DIRECTION_INCOME)==0){//收入
					account.setTotalMoney(account.getTotalMoney().add(money));
				}else if(null!=direction&& direction.compareTo(ObAccountDealInfo.DIRECTION_PAY)==0){//支出
					account.setTotalMoney(account.getTotalMoney().subtract(money));
				}
				account.setAccountStatus(Short.valueOf("0"));//1代表正在进行取现审批
				this.dao.merge(account);
				
				return account.getTotalMoney();
			}else{
				return null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public BigDecimal prefreezMoney(Long accountId, String direction) {
		
		return obAccountDealInfoDao.prefreezMoney(accountId,direction);
	}

	@Override
	public BigDecimal typeTotalMoney(Long accountId, String direction) {
		// TODO Auto-generated method stub
		return obAccountDealInfoDao.typeTotalMoney(accountId,direction);
	}

/*	@Override
	public BigDecimal[] sumTypeTotalMoney(Long accountId) {
		// TODO Auto-generated method stub
		BigDecimal[] ret=null;
		BigDecimal flag=new BigDecimal(0);//是否存在系统账户
		BigDecimal totalMoney=null;
		BigDecimal prefreezMoney =null;
		BigDecimal availbleMoney =null;
		ObSystemAccount account=dao.get(accountId);
		if(account!=null){
			flag=new BigDecimal(1);
			totalMoney=account.getTotalInvestMoney();
			prefreezMoney=obAccountDealInfoDao.prefreezMoney(account.getId(),null);
			if(prefreezMoney!=null){
				availbleMoney=account.getTotalInvestMoney().subtract(prefreezMoney);
			}
		}
		ret[0]=flag;
		ret[1]=totalMoney;
		ret[2]=prefreezMoney;
		ret[3]=availbleMoney;
		return ret;
	}*/

	@Override
	public BigDecimal[] sumTypeTotalMoney(Long investPeronId, String personType) {
		// TODO Auto-generated method stub
		BigDecimal[] ret=new BigDecimal[7] ;
		BigDecimal flag=new BigDecimal(0);//是否存在系统账户
		BigDecimal totalMoney=new BigDecimal(0);//账户总额
		BigDecimal prefreezMoney =new BigDecimal(0);//账户冻结金额
		BigDecimal availbleMoney =new BigDecimal(0);//账户可用金额
		BigDecimal totalInvestMoney=new BigDecimal(0);//账户累计投资金额
		BigDecimal totalprofitMoney=new BigDecimal(0);//账户累计进账收益
		BigDecimal totalbackPrinMoney=new BigDecimal(0);//账户累计收回本金
		ObSystemAccount account=dao.getByInvrstPersonIdAndType(investPeronId, Short.valueOf(personType));
		if(account!=null){
			flag=new BigDecimal(1);
			totalMoney=account.getTotalMoney();
			prefreezMoney=obAccountDealInfoDao.prefreezMoney(account.getId(),null);
			if(null !=prefreezMoney){
				availbleMoney=account.getTotalMoney().subtract(prefreezMoney);
			}else{
				availbleMoney=totalMoney;
			}
			totalInvestMoney =obAccountDealInfoDao.typeTotalMoney(account.getId(), ObAccountDealInfo.T_INVEST);
			totalprofitMoney =obAccountDealInfoDao.typeTotalMoney(account.getId(), ObAccountDealInfo.T_PROFIT);
			totalbackPrinMoney=obAccountDealInfoDao.typeTotalMoney(account.getId(), ObAccountDealInfo.T_PRINCIALBACK);
		}
		ret[0]=flag;
		ret[1]=totalMoney;
		ret[2]=prefreezMoney;
		ret[3]=availbleMoney;
		ret[4]=totalInvestMoney;
		ret[5]=totalprofitMoney;
		ret[6]=totalbackPrinMoney;
		return ret;
	}
	
	

	 //svn:songwj
	public BigDecimal[] sumTypeTotalMoneyP2p(Long investPeronId, String personType) {
		Long userId=null;
		/*BpCustRelation bpCustRelation = bpCustRelationService.getP2pCustById(investPeronId);
	  	   if(bpCustRelation!=null){
	  		 userId= bpCustRelation.getOfflineCusId();
	  }*/
		BigDecimal[] ret=new BigDecimal[12] ;
		BigDecimal flag=new BigDecimal(0);//是否存在系统账户
		BigDecimal totalMoney=new BigDecimal(0);//账户总额
		BigDecimal prefreezMoney =new BigDecimal(0);//账户冻结金额
		BigDecimal availbleMoney =new BigDecimal(0);//账户可用金额
		BigDecimal totalInvestMoney=new BigDecimal(0);//账户累计投资金额
		BigDecimal totalprofitMoney=new BigDecimal(0);//账户累计进账收益
		BigDecimal totalbackPrinMoney=new BigDecimal(0);//账户累计收回本金
		BigDecimal totalRecharge=new BigDecimal(0);//账户累计充值金额
		BigDecimal totalEnchashment=new BigDecimal(0);//账户累计取现金额

		BigDecimal totalLoanMoney=new BigDecimal(0);//累计融资金额
		BigDecimal totalPrincipalRepaymentMoney=new BigDecimal(0);//累计还本息
		BigDecimal totalNotPrincipalRepaymentMoney=new BigDecimal(0); //累计未还本息
		ObSystemAccount account=dao.getByInvrstPersonIdAndType(investPeronId, Short.valueOf(personType));
		if(account!=null){
			flag=new BigDecimal(1);
			totalMoney=account.getTotalMoney();
			prefreezMoney=obAccountDealInfoDao.prefreezMoney(account.getId(),null);
			if(null !=prefreezMoney){
				availbleMoney=account.getTotalMoney().subtract(prefreezMoney);
			}else{
				availbleMoney=totalMoney;
			}
			totalInvestMoney =obAccountDealInfoDao.typeTotalMoney( userId,account.getId(), ObAccountDealInfo.T_INVEST);
			totalprofitMoney =obAccountDealInfoDao.typeTotalMoney(userId,account.getId(), ObAccountDealInfo.T_PROFIT);
			totalbackPrinMoney=obAccountDealInfoDao.typeTotalMoney(userId,account.getId(), ObAccountDealInfo.T_PRINCIALBACK);
			totalRecharge=obAccountDealInfoDao.typeTotalMoney(userId,account.getId(), ObAccountDealInfo.T_RECHARGE);
			
			totalEnchashment=obAccountDealInfoDao.typeTotalMoney(userId,account.getId(), ObAccountDealInfo.T_ENCHASHMENT);
			
			 totalLoanMoney=obAccountDealInfoDao.typeTotalMoney(userId,account.getId(), ObAccountDealInfo.T_INMONEY);
			 totalPrincipalRepaymentMoney=obAccountDealInfoDao.typeTotalMoney(userId,account.getId(), ObAccountDealInfo.T_LOANPAY);
			 totalNotPrincipalRepaymentMoney=obAccountDealInfoDao.typeTotalMoney(userId,account.getId(), ObAccountDealInfo.T_N_REPAYMENT);
		}
		ret[0]=flag;
		ret[1]=totalMoney==null?new BigDecimal(0):totalMoney;
		ret[2]=prefreezMoney==null?new BigDecimal(0):prefreezMoney;
		ret[3]=availbleMoney==null?new BigDecimal(0):availbleMoney;
		ret[4]=totalInvestMoney==null?new BigDecimal(0):totalInvestMoney;
		
		if(totalprofitMoney==null){
			ret[5] = new BigDecimal(0);
		}else{
			ret[5] = totalprofitMoney;
		}
//		ret[5]=totalprofitMoney==null?new BigDecimal(0):totalprofitMoney;
//		System.out.println("totalprofitMoney-------------"+totalprofitMoney+"      "+ret[5]);
		ret[6]=totalbackPrinMoney==null?new BigDecimal(0):totalbackPrinMoney;
		ret[7]=totalRecharge==null?new BigDecimal(0):totalRecharge;
		ret[8]=totalEnchashment==null?new BigDecimal(0):totalEnchashment;
		
		ret[9]=totalLoanMoney==null?new BigDecimal(0):totalLoanMoney;
		ret[10]=totalPrincipalRepaymentMoney==null?new BigDecimal(0):totalPrincipalRepaymentMoney;
		ret[11]=totalNotPrincipalRepaymentMoney==null?new BigDecimal(0):totalNotPrincipalRepaymentMoney;
		return ret;
	}
	 
	
	
	//svn:songwj
	public BpCustMember getAccountSumMoney(BpCustMember bpCustMember) {
		if(bpCustMember!=null){
			
			BigDecimal[] ret =this.sumTypeTotalMoneyP2p(bpCustMember.getId(),ObSystemAccount.type0.toString());
			
			if(ret!=null){
				//if(ret[0].compareTo(new BigDecimal(1))==0){  验证 是否有虚拟账户
					bpCustMember.setTotalMoney(ret[1]);
					bpCustMember.setFreezeMoney(ret[2]);
					bpCustMember.setAvailableInvestMoney(ret[3]);
					bpCustMember.setTotalInvestMoney(ret[4]);
					
//					System.out.println("ret[5]swj------------------"+ret[5]);
					bpCustMember.setAllInterest(ret[5]);
					bpCustMember.setPrincipalRepayment(ret[6]);
					bpCustMember.setTotalRecharge(ret[7]);
					bpCustMember.setTotalEnchashment(ret[8]);
					
					bpCustMember.setTotalLoanMoney(ret[9]);
					bpCustMember.setTotalPrincipalRepaymentMoney(ret[10]);
					bpCustMember.setTotalNotPrincipalRepaymentMoney(ret[11]);
					
				//}
			}
		}
		return bpCustMember;
	}
    /**
     * 根据
     */
	@Override
	public List<ObSystemAccount> getByAccountType(String plateFromFinanceType) {
		// TODO Auto-generated method stub
		return this.dao.getByAccountType(plateFromFinanceType);
	}
	 /**
     * 获取系统账户基本信息和第三方支付信息新的方法
     * @param request
     * @return
     */
	@Override
	public List<ObSystemAccount> getNewSystemAccountList(
			HttpServletRequest request, Integer start,Integer limit) {
		// TODO Auto-generated method stub
		return this.dao.getNewSystemAccountList(request,start,limit);
	}
	/**
	 * 定时器，刷新系统第三方资金账户信息
	 */
   @Override
	public void refreshAllobSystemAccount() {
	   
	   System.out.println("定时器，刷新系统第三方资金账户信息");
	   List<ObAccountDealInfo> deal = obAccountDealInfoService.getAllThirdDealInfo(null);
	   //跑批记录 
		AppUser appUser = ContextUtil.getCurrentUser();
		String pushUserName = "定时跑批";
		Long pushUserId = null;
		if(null != appUser && !"".equals(appUser)){
			pushUserName = appUser.getFullname();
			pushUserId = appUser.getUserId();
		}
		BatchRunRecord batchRunRecord = new BatchRunRecord();
		batchRunRecord.setRunType(BatchRunRecord.HRY_1003);
		batchRunRecord.setAppUserId(pushUserId);
		batchRunRecord.setAppUserName(pushUserName);
		batchRunRecord.setStartRunDate(new Date());
		batchRunRecord.setHappenAbnorma("正常");
		batchRunRecord.setTotalNumber(Long.valueOf(deal != null ? deal.size() : 0));
		for(ObAccountDealInfo ob:deal){
			try {
				this.refreshThirdPayAccountCheckFile(ob.getAccountId().toString());
			} catch (Exception e) {
				String ids="";
				if(null != batchRunRecord.getIds()){
					ids =batchRunRecord.getIds() + "," + ob.getId();
				}else{
					ids = ob.getId().toString();
				}
				batchRunRecord.setIds(ids);
				batchRunRecord.setHappenAbnorma("异常");
				e.printStackTrace();
			}
		}
		batchRunRecord.setEndRunDate(new Date());
		batchRunRecordService.save(batchRunRecord);
	}
   
   
   

   /**
    * 定时任务  查询银行处理中的充值数据
    */
    @Override
	public void refreshRechargeDeal() {
    	//查询银行处理中的充值
    	List<ObAccountDealInfo> list = obAccountDealInfoDao.getListByTransFerTypeAndState("1","9","12");
    	Integer num = 0;
    	if (list != null && list.size()>0) {
    		System.out.println("调用时间："+new Date()+"银行处理中订单数:"+list.size());
			for (ObAccountDealInfo info : list) {
				CommonRequst common = new CommonRequst();
				String orderNum =ContextUtil.createRuestNumber();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				common.setRequsetNo(orderNum);//请求流水号
				common.setBussinessType(ThirdPayConstants.BT_QUERYDEAL);//业务类型
				common.setTransferName(ThirdPayConstants.TN_QUERYDEAL);//名称
				common.setQueryType(FuDian.QUERYTYPE01);
				common.setThirdPayConfigId(null);
				common.setThirdPayConfigId0(null);
				common.setOldBidRequestNo(info.getRecordNumber());
				common.setSubOrdDate(sdf.format(info.getCreateDate()));//查询日期  定长8 (订单的日期)
				CommonResponse thirdCommon = ThirdPayInterfaceUtil.thirdCommon(common);
				if (thirdCommon.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
					System.out.println("查询到流水号为:"+info.getRecordNumber()+"的记录状态为：" + thirdCommon.getState());
					//1成功、2失败、4银行处理中-->充值的状态
					if (StringUtils.isNotEmpty(thirdCommon.getState()) && "1".equals(thirdCommon.getState())) {
						//保存交易记录
						info.setTransferDate(new Date());
						info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
						//修改系统账户
						BigDecimal currentMoney = changeAccountMoney(info.getAccountId(), info.getIncomMoney(), ObAccountDealInfo.DIRECTION_INCOME);
						info.setCurrentMoney(currentMoney);
						obAccountDealInfoDao.save(info);
						num ++;
					}
				}
			}
			System.out.println("处理成功订单个数："+num);
		}
	}

/**
    * 新的方法  用来执行查询系统账户第三方账户信息以及及时刷新虚拟账户余额
    */
	@Override
	public void refreshThirdPayAccountCheckFile(String accountId) {
		// TODO Auto-generated method stub
			if(accountId!=null&&!"".equals(accountId)){
				ObSystemAccount ob=this.dao.get(Long.valueOf(accountId));
				if(ob!=null){
					if(ob.getInvestPersionType().equals(ObSystemAccount.type0)){
						BpCustMember bp=bpCustMemberDao.get(ob.getInvestmentPersonId());
						if(bp!=null){
							CommonRequst common = new CommonRequst();
							CommonResponse commonResponse = new CommonResponse();
							String orderNum =ContextUtil.createRuestNumber();
							common.setRequsetNo(orderNum);//请求流水号
							common.setThirdPayConfigId(bp.getThirdPayFlagId());//第三方账号
							common.setBussinessType(ThirdPayConstants.BT_QUERYUSER);
							common.setTransferName(ThirdPayConstants.TN_QUERYUSER);
							commonResponse=ThirdPayInterfaceUtil.thirdCommon(common);
    						if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
    							//判断用户资金异常记录信息，先查询用户系统资金记录和第三方匹配 
    							//obj[0]=账户总余额，obj[1]=冻结金额，obj[2]=可以余额,IsException异常字段，1=异常状态
								List<Object> list = this.dao.getSystemAccountMoneyList(accountId);
								if(list.size()>0){
									Object[] obj =(Object[]) list.get(0);
									if(obj!=null){
										//判断账户总余额,冻结金额，可用余额是否正常
										/*System.out.println(obj[0].toString()+"--"+commonResponse.getBalance());
										System.out.println(obj[1].toString()+"--"+commonResponse.getFreezeAmount());
										System.out.println(obj[2].toString()+"--"+commonResponse.getAvailableAmount());*/
										if(new BigDecimal(obj[0].toString()).compareTo(commonResponse.getBalance())!=0
												||new BigDecimal(obj[1].toString()).compareTo(commonResponse.getFreezeAmount())!=0
												||new BigDecimal(obj[2].toString()).compareTo(commonResponse.getAvailableAmount())!=0){
											ob.setIsException(1);
										}else{
											ob.setIsException(0);
										}
									}else{
										ob.setIsException(1);
									}
								}
    							ob.setThirdTotalMoney(commonResponse.getBalance());
								ob.setThirdAviableMoney(commonResponse.getAvailableAmount());
								ob.setThirdFreezyMoney(commonResponse.getFreezeAmount());
								ob.setCheckDate(new Date());
								try{	
									this.dao.save(ob);
								}catch(StaleObjectStateException st ){
									ObSystemAccount obnew=this.dao.get(Long.valueOf(accountId));
									obnew.setThirdTotalMoney(commonResponse.getBalance());
									obnew.setThirdAviableMoney(commonResponse.getAvailableAmount());
									obnew.setThirdFreezyMoney(commonResponse.getFreezeAmount());
									obnew.setCheckDate(new Date());
									this.dao.save(obnew);
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
	}
	/**
	 * 利用多线程来处理系统账户第三方账户信息的更新工作
	 * add by linyan
	 */
	@Override
	public void mutiplyTreadRefreshThirdPayAccount() {
		//count4EachThread:每个线程处理的list.size()
		int count4EachThread = 100;
		//需要批量处理的list数组
		List<Object> list =getNewSystemAccountList();
		Properties props=System.getProperties();
		/**
		 * 将批量处理的list数组分割成每个线程需要处理的业务量，
		 * "obSystemAccountService.mutiplyThreadSystemAccount"表示要业务处理的方法bean和method，其中method参数为List<T>
		 */
		List<Processor> processerList = Processor.generateProcessorList(list, count4EachThread,"obSystemAccountService.mutiplyThreadSystemAccount");
		java.util.concurrent.CountDownLatch countDownLatch = null;
		/**
		 * maxThreadCount:进程中同一时刻最多存在maxThreadCount个线程,多余出来的线程则排队
		 * Runtime.getRuntime().availableProcessors()  利用Runtime类获取系统的CPU个数
		 */
		int maxThreadCount=Runtime.getRuntime().availableProcessors();
		System.out.println("系统中的CPU个数："+maxThreadCount);
		// 执行完所有任务需要创建多少个线程
		int threadPageCount = (int) (processerList.size()/ ((float) maxThreadCount) + 0.5);
		try {
			for (int pageNum = 0; pageNum < threadPageCount; pageNum++) {
				int index = 0;
				int remainProcessorCount = processerList.size() - pageNum* maxThreadCount;
				int threadCount = remainProcessorCount > maxThreadCount ? maxThreadCount: remainProcessorCount;
				countDownLatch = new java.util.concurrent.CountDownLatch(threadCount);
				for (; (index < maxThreadCount)&& ((pageNum * maxThreadCount + index) < processerList.size()); index++) {
					Processor processor = processerList.get(pageNum* maxThreadCount + index);
					processor.setCountDownLatch(countDownLatch);
					processor.start();
				}
				countDownLatch.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		
	}

	private List<Object> getNewSystemAccountList() {
		// TODO Auto-generated method stub
		return this.dao.getNewSystemAccountList();
	}

	@Override
	public void mutiplyThreadSystemAccount(List<ObSystemAccount> list) {
		  try{
			  if(list!=null&&list.size()>0){
					for(ObSystemAccount temp:list){
						this.refreshThirdPayAccountCheckFile(temp.getId().toString());
						System.out.println("线程批量处理方法 accountId=="+temp.getId());
					}
				}
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	}
	
	/**
	 * 合作机构资金账户查询
	 */
	public List cooperationAccountList(Map<String, String> map){
		
		return dao.cooperationAccountList(map);
	}

	@Override
	public Long cooperationAccountListCount(Map<String, String> map) {
		return  dao.cooperationAccountListCount(map);
	}

	@Override
	public List<ObSystemAccount> findDownAccount(HttpServletRequest request,
			Integer start, Integer limit) {
		return dao.findDownAccount(request,start, limit);
	}

	@Override
	public String[] saveAccount(String investId, String type) {
		// TODO Auto-generated method stub
		String[] str=new String[2];
		String ret="";
		String msg="";
		try{
			ObSystemAccount oldAccount=dao.getByInvrstPersonIdAndType(Long.valueOf(investId), Short.valueOf(type));
			if(null!=oldAccount){
				ret=Constants.CODE_FAILED;
				msg="当前投资人身份证已经注册了系统账户";
			}else{
				ObSystemAccount obSystemAccount = new ObSystemAccount();
				obSystemAccount.setCompanyId(Long.valueOf("1"));
				obSystemAccount.setInvestmentPersonId(Long.valueOf(investId));
			//	obSystemAccount.setAccountNumber(UUID.randomUUID().toString());// 当前默认系统虚拟账户为系统生成的随机数
				obSystemAccount.setAccountNumber(UUID.randomUUID().toString().replace("-", ""));// 当前默认系统虚拟账户为系统生成的随机数
				obSystemAccount.setTotalMoney(new BigDecimal(0));
				obSystemAccount.setInvestPersionType(Short.valueOf(type));// 1 线下客户
				if(null!=oldAccount){
					ret=Constants.CODE_SUCCESS;
					msg="系统账户已经存在！";
				}else{
					dao.save(obSystemAccount);
					ret=Constants.CODE_SUCCESS;
					msg="系统账户成功保存！";
				}
				
			}
			
		}catch(Exception e){
			ret=Constants.CODE_FAILED;
			msg="系统账户保存失败！";
		}
		str[0]=ret;
		str[1]=msg;
		return str;
	}
	
	@Override
	public List<ObSystemAccount> rechargeReconciliationList(
			HttpServletRequest request, Integer start,Integer limit) {
		return this.dao.rechargeReconciliationList(request,start,limit);
	}
	
	@Override
	public List<ObSystemAccount> standardReconciliationTransfer(
			HttpServletRequest request, Integer start, Integer limit) {
		return this.dao.standardReconciliationTransferLinst(request, start, limit);
	}
	@Override
	public List<Object> getSystemAccountMoneyList(String accountId){
		return this.dao.getSystemAccountMoneyList(accountId);
	}
}