package com.zhiwei.credit.service.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObAccountDealInfoDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormStatisticsDetail;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.util.Common;

/**
 * 
 * @author 
 *
 */
@WebService(targetNamespace="http://bank.creditAssignment.creditFlow.service.credit.zhiwei.com/", endpointInterface = "com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService") 
public class ObAccountDealInfoServiceImpl extends BaseServiceImpl<ObAccountDealInfo> implements ObAccountDealInfoService{
	@SuppressWarnings("unused")
	private ObAccountDealInfoDao dao;
	@Resource
	private ObSystemAccountService obSystemAccountService;
    @Resource
    private BpCustMemberService bpCustMemberService;
    @Resource
	private SlFundIntentService slFundIntentService;
    @Resource
	private BpFundIntentService bpFundIntentService;
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	/*@Resource
	private ProcessFormService processFormService;*/
	@Resource
	private CreditProjectService creditProjectService;
	public ObAccountDealInfoServiceImpl(ObAccountDealInfoDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Override
	public List<ObAccountDealInfo> getDealList(String investPersonName,
			String transferDate, String flag) {
		// TODO Auto-generated method stub

		return this.dao.getDealList(investPersonName, transferDate, flag);

	}

	@Override
	public List<ObAccountDealInfo> getRechargeDealList(String investPersonName,
			String seniorValidationRechargeStatus,
			String rechargeConfirmStatus, String flag, String rechargeLevel) {
		// TODO Auto-generated method stub
		return this.dao.getRechargeDealList(investPersonName,
				seniorValidationRechargeStatus, rechargeConfirmStatus, flag,
				rechargeLevel);
	}

	@Override
	public String getCreateNameByCreateId(Long createId) {
		return dao.getCreateNameByCreateId(createId);
	}
	/**查询投资客户账户收支明细*/
	public List<ObAccountDealInfo> getaccountListQuery(String accountId,HttpServletRequest request, Integer start, Integer limit) {
		return	this.dao.getaccountListQuery(accountId,request,start,limit);
	}
	
	@Override
	public List<ObAccountDealInfo> getWithdrawCheck(HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return dao.getWithdrawCheck(request,start,limit);
	}

	//根据投资人id  投资人债权id  以及账户交易类型
	@Override
	public ObAccountDealInfo getDealInfo(Long investMentPersonId, Long id,String flag) {
		// TODO Auto-generated method stub
		return this.dao.getDealInfo(investMentPersonId,id,flag);
	}
	//(投资人添加债权和撤销债权以及债权产品匹配投资人时用的方法)主要作用是：删除对应的一条账户交易信息以及更改账户总额
	@Override
	public void changeAccountDealInfo(ObObligationInvestInfo ob, int i) {
		// TODO Auto-generated method stub
		ObAccountDealInfo  accountDealInfo =null;
		if(ob!=null&&!"".equals(ob)){
			ObSystemAccount systemAccount =obSystemAccountService.getByInvrstPersonId(ob.getInvestMentPersonId());
			accountDealInfo =this.dao.getDealInfo(ob.getInvestMentPersonId(),ob.getId(),"4");//3表示账户支出金额用来投资
			if( i==1){//表示添加债权时间
				AppUser user =ContextUtil.getCurrentUser();
				if(accountDealInfo==null||"".equals(accountDealInfo)){
					accountDealInfo =new ObAccountDealInfo();
					accountDealInfo.setAccountId(systemAccount.getId());
					accountDealInfo.setAccountName(systemAccount.getAccountName());
					accountDealInfo.setAccountNumber(systemAccount.getAccountNumber());
					accountDealInfo.setInvestPersonId(ob.getInvestMentPersonId());
					accountDealInfo.setInvestPersonName(systemAccount.getInvestPersonName());
					accountDealInfo.setCreateDate(new Date());
					accountDealInfo.setTransferDate(ob.getInvestStartDate());
					accountDealInfo.setCreateId(user.getUserId());
					accountDealInfo.setPayMoney(ob.getInvestMoney());
					accountDealInfo.setTransferType(ObAccountDealInfo.T_INVEST);
					accountDealInfo.setInvestObligationInfoId(ob.getId());
					accountDealInfo.setCurrentMoney(systemAccount.getTotalMoney().subtract(ob.getInvestMoney()));
					this.dao.save(accountDealInfo);
					systemAccount.setTotalMoney(systemAccount.getTotalMoney().subtract(ob.getInvestMoney()));
					obSystemAccountService.merge(systemAccount);
				}else{//编辑已有的这条账户交易信息
					accountDealInfo.setTransferDate(new Date());
					accountDealInfo.setCreateId(user.getUserId());
					accountDealInfo.setPayMoney(ob.getInvestMoney());
					accountDealInfo.setCurrentMoney(systemAccount.getTotalMoney().subtract(ob.getInvestMoney()));
					this.dao.merge(accountDealInfo);
					systemAccount.setTotalMoney(systemAccount.getTotalMoney().subtract(ob.getInvestMoney()));
					obSystemAccountService.merge(systemAccount);
				}
			}else  if( i==0){//表示删除对应的平台账户的交易信息以及修改平台账户金额
				if(accountDealInfo!=null&&!"".equals(accountDealInfo))
					this.dao.remove(accountDealInfo.getId());
					systemAccount.setTotalMoney(systemAccount.getTotalMoney().add(ob.getInvestMoney()));
					obSystemAccountService.merge(systemAccount);
				}
		}
	
	}

	@Override
	public String saveRechargeDealInfo(String accountId, String money,
			String transferType, String dealType, String rechargeLevel,
			String investPersonName, String investPersonId,
			String rechargeConfirmStatus, String seniorValidationRechargeStatus) {
		String ret="";
		try{
		ObAccountDealInfo obAccountDealInfo=new ObAccountDealInfo();
		obAccountDealInfo.setAccountId(Long.valueOf(accountId));
		
		ObSystemAccount account=obSystemAccountService.get(obAccountDealInfo.getAccountId());
		obAccountDealInfo.setCompanyId(account.getCompanyId());
		
		
		if(transferType.equals("1")||transferType.equals("3")){
			obAccountDealInfo.setIncomMoney(new BigDecimal(money));
			account.setTotalMoney(account.getTotalMoney().add(new BigDecimal(money)));
		}else if(transferType.equals("2")||transferType.equals("4")){
			obAccountDealInfo.setPayMoney(new BigDecimal(money));
			account.setTotalMoney(account.getTotalMoney().subtract(new BigDecimal(money)));
		}
		//更新 账户记录
		account=obSystemAccountService.merge(account);
		
		obAccountDealInfo.setCurrentMoney(account.getTotalMoney());//跟新当前取现记录生效后账户的余额是多少
		obAccountDealInfo.setTransferType(transferType);
		obAccountDealInfo.setDealType(Short.valueOf(dealType));
	    obAccountDealInfo.setRechargeLevel(Short.valueOf(rechargeLevel));
	    obAccountDealInfo.setDealRecordStatus(Short.valueOf("2"));
		obAccountDealInfo.setCreateId(Long.valueOf(1));
		obAccountDealInfo.setCreateDate(new Date());
		obAccountDealInfo.setTransferDate(new Date());
		
		obAccountDealInfo.setInvestPersonName(investPersonName);
		obAccountDealInfo.setInvestPersonId(Long.valueOf(investPersonId));
		
		dao.save(obAccountDealInfo);
		ret="success";
		}catch(Exception e){
			ret="faild";
		}
		return ret;
	}

	/**
	 * 操作系统账户调用方法
	 * Map<String,Object> map =new HashMap<String,Object>();
	 * map.put("investPersonId",Long);//投资人Id
	 * map.put("investPersonType",Short);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
	 * map.put("transferType",Short);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
	 * map.put("money",BigDecimal);//交易金额
	 * map.put("dealDirection",short);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
	 * map.put("dealType",Short);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）
	 * map.put("recordNumber",String);//交易流水号
	 * map.put("dealStatus",short);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
	 * @return String[0]:操作代码  Constants.CODE_SUCCESS =8888账户操作成功，Constants.CODE_FAILED=0000  账户操作失败
	 *         String[1]:操作信息   操作成功 ，操作失败
	 *  add by liny 2014-5-6   
	 *  update by linyan  2014-10-10
	 */
	@Override
	public String[] operateAcountInfo(Map<String,Object> map) {
		String[] str =new String[2];
		String ret="";
		String msg="";
		try{
			System.out.println("操作账户的用户id："+map.get("investPersonId"));
			ObSystemAccount account  =obSystemAccountService.getByInvrstPersonIdAndType((Long)map.get("investPersonId"), (Short)map.get("investPersonType"));
			if(account!=null){
				//初始化交易参数开始
				ObAccountDealInfo obAccountDealInfo =new ObAccountDealInfo();
				obAccountDealInfo.setAccountId(account.getId());
				obAccountDealInfo.setInvestPersonName(account.getAccountName());
				obAccountDealInfo.setInvestPersonId(account.getInvestmentPersonId());
				obAccountDealInfo.setInvestPersonType((Short)map.get("investPersonType"));
				obAccountDealInfo.setDealType((Short)map.get("dealType"));
				obAccountDealInfo.setTransferType((String)map.get("transferType"));
				obAccountDealInfo.setCreateDate(new Date());
				obAccountDealInfo.setRecordNumber(map.get("recordNumber").toString());
				obAccountDealInfo.setDealRecordStatus((Short)map.get("dealStatus"));
				Short dealDirection=(Short)map.get("dealDirection");
				if(dealDirection.compareTo(ObAccountDealInfo.DIRECTION_INCOME)==0){
					obAccountDealInfo.setIncomMoney((BigDecimal)map.get("money"));
				}else {
					obAccountDealInfo.setPayMoney((BigDecimal)map.get("money"));
				}
				//初始化交易记录参数结束
				//根据交易类型对交易记录进行处理开始
				if(((String)map.get("transferType")).equals(ObAccountDealInfo.T_ENCHASHMENT)){
					String  startFlowmsg=creditProjectService.startEnchashmentFlow(obAccountDealInfo);
					if(null!=startFlowmsg){
						String[] s =startFlowmsg.split(",");
						if(null!=s[0]&&!"".equals(s[0])){
							String[] staskId=s[0].split(":");
							if(null!=staskId[0]&&!"".equals(staskId[0])&&!"0".equals(staskId[0])){
								obAccountDealInfo.setMsg("取现流程启动，进入审批");
								ret=Constants.CODE_SUCCESS;
								msg="操作记录：取现流程启动，进入审批阶段!";
							}else{
								obAccountDealInfo.setMsg("取现流程启动失败");
								obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_3);
								ret=Constants.CODE_FAILED;
								msg="操作记录：系统异常，请联系管理员!";
							}
							this.dao.merge(obAccountDealInfo);
						}else{
							ret=Constants.CODE_FAILED;
							msg="操作记录：系统异常，请联系管理员!";
						}
					}else{
						ret=Constants.CODE_FAILED;
						msg="操作记录：系统异常，请联系管理员!";
					}
				}else{
					this.dao.save(obAccountDealInfo);
					ret=Constants.CODE_SUCCESS;
					msg="操作记录：操作成功!";
					if(map.get("dealStatus").equals(ObAccountDealInfo.DEAL_STATUS_2)){
						map.put("DealInfoId",obAccountDealInfo.getId());
						String[] returnMsg=this.updateAcountInfo(map);
						if(null!=returnMsg){
							ret=returnMsg[0];
							msg=returnMsg[1];
						}else{
							ret=Constants.CODE_FAILED;
							msg="操作记录：系统异常，请联系管理员!";
						}
					}
						
					
				}
				
			}else{
				ret=Constants.CODE_FAILED;
				msg="操作记录：系统账户不存在!";
			}
		}catch(Exception e){
			e.printStackTrace();
			ret=Constants.CODE_FAILED;
			msg="操作记录：系统异常，请联系管理员!";
		}
		
		
		str[0]=ret;
		str[1]=msg;
		return str;
	}
	
	
	/**
	 * 将账户交易记录更新，并且跟新系统账户金额
	 * @param investPersonId 投资人Id
	 * @param transferType 交易类型  1表示充值，2表示取现,3收益，4投资，5还本
	 * @param money 交易金额
	 * @param resource 投资人来源：0线上投资人，1线下投资人
	 * @param recordNumber 交易产生编号（唯一）没有值传null
	 * @param accountInfoId  系统交易记录主键id   没有值传null
	 * @return
	 */
	
	@Override
	public String[] updateAcountInfo(Map<String,Object> map) {
		// TODO Auto-generated method stub
		String[] str =new String[2];
		String ret="";
		String msg="";
		Long DealInfoId=null;
		if(map.containsKey("DealInfoId")){
			DealInfoId=(Long)map.get("DealInfoId");
		}
		ObSystemAccount account  =obSystemAccountService.getByInvrstPersonIdAndType((Long)map.get("investPersonId"), (Short)map.get("investPersonType"));
		if(account!=null){
			ObAccountDealInfo info =this.dao.getDealinfo(map.get("transferType").toString(),account.getId(),(Long)map.get("investPersonId"),(Short)map.get("investPersonType"),map.get("recordNumber").toString(),DealInfoId);
			if(info!=null){
					if((Short)map.get("dealStatus")!=null&&map.get("dealStatus").equals(ObAccountDealInfo.DEAL_STATUS_2)){
						/*if(info.getTransferDate()!=null){
							ret=Constants.CODE_SUCCESS;
							msg="操作信息：业务数据已经处理过!";
						}else{*/
							BigDecimal  currentMoney=obSystemAccountService.changeAccountMoney(account.getId(),(BigDecimal)map.get("money"), (Short)map.get("dealDirection"));
							if(currentMoney!=null){
								info.setCurrentMoney(currentMoney);
								info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
								info.setRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
								info.setTransferDate(new Date());
								this.dao.merge(info);
								ret=Constants.CODE_SUCCESS;
								msg="操作信息：操作成功!";
							}else{
								ret=Constants.CODE_FAILED;
								info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
								info.setTransferDate(new Date());
								info.setMsg("系统异常，请联系管理员!,交易标示异常");
								this.dao.merge(info);
								msg="操作信息：系统异常，请联系管理员!交易标示异常";
							}
//						}
					}else if((Short)map.get("dealStatus")!=null&&map.get("dealStatus").equals(ObAccountDealInfo.DEAL_STATUS_3)){
						ret=Constants.CODE_FAILED;
						info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_3);
						info.setTransferDate(new Date());
						info.setMsg("第三方支付出错!,交易标示作废");
						this.dao.merge(info);
						msg="操作信息：第三方支付出错!交易标示作废";
					}else if((Short)map.get("dealStatus")!=null&&map.get("dealStatus").equals(ObAccountDealInfo.DEAL_STATUS_1)){
						ret=Constants.CODE_FAILED;
						info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_1);
						info.setTransferDate(new Date());
						info.setMsg("无用操作");
						this.dao.merge(info);
						msg="操作信息：无用操作";
					}else if((Short)map.get("dealStatus")!=null&&map.get("dealStatus").equals(ObAccountDealInfo.DEAL_STATUS_4)){
						ret=Constants.CODE_FAILED;
						info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_4);
						info.setTransferDate(new Date());
						info.setMsg("取现审核");
						this.dao.merge(info);
						msg="操作信息：取现审核状态";
					}else if((Short)map.get("dealStatus")!=null&&map.get("dealStatus").equals(ObAccountDealInfo.DEAL_STATUS_5)){
						ret=Constants.CODE_FAILED;
						info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_5);
						info.setTransferDate(new Date());
						info.setMsg("取现办理");
						this.dao.merge(info);
						msg="操作信息：取现办理状态";
					}else if((Short)map.get("dealStatus")!=null&&map.get("dealStatus").equals(ObAccountDealInfo.DEAL_STATUS_7)){
						ret=Constants.CODE_FAILED;
						info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_7);
						info.setTransferDate(new Date());
						info.setMsg("第三方支付冻结金额!");
						this.dao.merge(info);
						msg="操作信息：成功冻结资金";
					}else if((Short)map.get("dealStatus")!=null&&map.get("dealStatus").equals(ObAccountDealInfo.DEAL_STATUS_8)){
						ret=Constants.CODE_FAILED;
						info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_8);
						info.setTransferDate(new Date());
						info.setMsg("第三方支付冻结金额!");
						this.dao.merge(info);
						BigDecimal  currentMoney=obSystemAccountService.changeAccountMoney(account.getId(),(BigDecimal)map.get("money"), ObAccountDealInfo.DIRECTION_INCOME);
						msg="操作信息：成功冻结资金";
					}else{
						ret=Constants.CODE_FAILED;
						info.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_6);
						info.setTransferDate(new Date());
						info.setMsg("交易状态不正常，标识为异常数据，交易状态："+map.get("dealStatus"));
						this.dao.merge(info);
						msg="操作信息：交易状态不正常，标识为异常数据，交易状态："+map.get("dealStatus");
					}
			}else{
				ret=Constants.CODE_FAILED;
				msg="操作失败：交易记录不存在!";
			}
		}else{
			ret=Constants.CODE_FAILED;
			msg="操作失败：系统账户不存在!";
		}
		str[0]=ret;
		str[1]=msg;
		return str;
	}
	
	/**
	 * 获取账户交易记录的交易方向
	 * @param transferType
	 * @return
	 */
	private Short getDealInfoDirection(String transferType) {
		Short direction=null;
		if(transferType.equals(ObAccountDealInfo.T_RECHARGE.toString())||transferType.equals(ObAccountDealInfo.T_PRINCIALBACK.toString())||transferType.equals(ObAccountDealInfo.T_PROFIT.toString())||transferType.equals(ObAccountDealInfo.T_LOANINCOME.toString())||transferType.equals(ObAccountDealInfo.T_REDENVELOPE.toString())){
			direction=1;
		}else {
			direction=2;
		}
		return direction;
	}

	/**
	 * 查询系统账户交易信息调用方法
	 * @param accountId  系统账户id
	 * @param transferType  交易类型  1表示充值，2表示取现,3收益，4投资，5还本
	 * @param dealRecordStatus  系统账户交易明细记录状态：1审批2成功3失败4复核5办理
	 * @param rechargeConfirmStatus 系统账户交易明细记录是否生效，或者冻结
	 * @param request
	 * @return
	 */
	@Override
	public List<ObAccountDealInfo> queyAccountInfoRecord(Long accountId,String transferType, Short dealRecordStatus,Short rechargeConfirmStatus, HttpServletRequest request,Integer start, Integer limit) {
		return dao.queyAccountInfoRecord(accountId,transferType,dealRecordStatus,rechargeConfirmStatus,request,start,limit);
	}

	@Override
	public String[] saveAcountInfo(ObAccountDealInfo obAccountDealInfo) {
		// TODO Auto-generated method stub
		try{
			
		}catch(Exception e){
			
		}
		return null;
	}

	@Override
	public String[] newOpreaterDealInfo(String investPersonId,String transferType, String money,String dealType,String resource,String operateType,String accountType,String recordNumber,String bankId) {
		// TODO Auto-generated method stub
		String[] str =new String[2];
		String ret="";
		String msg="";
		try{
			ObSystemAccount account  =obSystemAccountService.getByInvrstPersonIdAndType(Long.valueOf(investPersonId), Short.valueOf(resource));
			if(account!=null){
				//初始化交易参数开始
				Short direction=null;
				ObAccountDealInfo obAccountDealInfo =new ObAccountDealInfo();
				obAccountDealInfo.setAccountId(account.getId());
				obAccountDealInfo.setInvestPersonName(account.getAccountName());
				obAccountDealInfo.setInvestPersonId(account.getInvestmentPersonId());
				obAccountDealInfo.setInvestPersonType(Short.valueOf(resource));
				obAccountDealInfo.setDealType(Short.valueOf(dealType));
				obAccountDealInfo.setTransferType(transferType);
				obAccountDealInfo.setTransferDate(new Date());
				obAccountDealInfo.setCreateDate(new Date());
				obAccountDealInfo.setRecordNumber(recordNumber);
				if(transferType.equals(ObAccountDealInfo.T_RECHARGE.toString())||transferType.equals(ObAccountDealInfo.T_PRINCIALBACK.toString())||transferType.equals(ObAccountDealInfo.T_PROFIT.toString())){
					direction=1;
					obAccountDealInfo.setIncomMoney(new BigDecimal(money));
				}else {
					direction=2;
					obAccountDealInfo.setPayMoney(new BigDecimal(money));
				}
				obAccountDealInfo.setRechargeLevel(Short.valueOf(accountType));
				//初始化交易记录参数结束
				//根据交易类型对交易记录进行处理开始
				if(transferType.equals(ObAccountDealInfo.T_ENCHASHMENT.toString())){
					obAccountDealInfo.setBankId(Long.valueOf(bankId));
					String  startFlowmsg=creditProjectService.startEnchashmentFlow(obAccountDealInfo);
					if(null!=startFlowmsg){
						String[] s =startFlowmsg.split(",");
						if(null!=s[0]&&!"".equals(s[0])){
							String[] staskId=s[0].split(":");
							if(null!=staskId[0]&&!"".equals(staskId[0])&&!"0".equals(staskId[0])){
								obAccountDealInfo.setMsg("取现流程启动，进入审批");
								ret=Constants.CODE_SUCCESS;
								msg="操作记录：取现流程启动，进入审批阶段!";
							}else{
								obAccountDealInfo.setMsg("取现流程启动失败");
								obAccountDealInfo.setDealRecordStatus(Short.valueOf("3"));
								ret=Constants.CODE_FAILED;
								msg="操作记录：系统异常，请联系管理员!";
							}
							this.dao.merge(obAccountDealInfo);
						}else{
							ret=Constants.CODE_FAILED;
							msg="操作记录：系统异常，请联系管理员!";
						}
					}else{
						ret=Constants.CODE_FAILED;
						msg="操作记录：系统异常，请联系管理员!";
					}
				}else{
					obAccountDealInfo.setDealRecordStatus(Short.valueOf("1"));
					this.dao.save(obAccountDealInfo);
					ret=Constants.CODE_SUCCESS;
					msg="操作记录：操作成功!";
					if(accountType!=null&&accountType.equals("2")){/*
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("investPersonId",bpCustMember.getId());//投资人Id
						map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
						map.put("transferType",type);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
						map.put("money",new BigDecimal(tranAmt));//交易金额
						map.put("dealDirection",dealDirection);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
						map.put("DealInfoId",obAccountDealInfo.getId());//交易记录id，没有默认为null
						map.put("recordNumber",recordNumber);//交易流水号
						map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)

						//String[] returnMsg=this.updateAcountInfo(Long.valueOf(investPersonId),transferType, money,resource,recordNumber,obAccountDealInfo.getId(),"2");
						if(null!=returnMsg){
							ret=returnMsg[0];
							msg=returnMsg[1];
						}else{
							ret=Constants.CODE_FAILED;
							msg="操作记录：系统交易失败!";
						}
					*/}
				}
			}else{
				ret=Constants.CODE_FAILED;
				msg="操作记录：系统账户不存在!";
			}
		}catch(Exception e){
			e.printStackTrace();
			ret=Constants.CODE_FAILED;
			msg="操作记录：系统异常，请联系管理员!";
		}
		
		
		str[0]=ret;
		str[1]=msg;
		return str;

	}

	@Override
	public String[] updateAccountByRepayment(String OutCustId,String InCustId,String TransAmt,String OrdId,String Fee,String Remark1,String feeType) {
		String [] strArr=Remark1.split("@");
		String type="";
		BigDecimal totalMoney=null;
		String[] retArr=new String[2];
		BpCustMember outCut=null;
		if(OutCustId!=null){
		// 保存借款人交易记录
		 outCut =  bpCustMemberService.getMemberByFlagId(OutCustId);//借款人账号 减少 金额
		 
		 String orderId=OrdId+"-"+Common.genertOrder(3);
		 Map<String,Object> map=new HashMap<String,Object>();
		 map.put("investPersonId",outCut.getId());//投资人Id（必填）
		 map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
		 map.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
		 map.put("money",new BigDecimal(TransAmt));//交易金额	（必填）			 
		 map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
		 map.put("dealType",ObAccountDealInfo.T_LOANPAY);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
		 map.put("recordNumber",orderId);//交易流水号	（必填）
		 map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
		 String[] ret =operateAcountInfo(map);

		//String[]  ret = operateAcountInfo(outCut.getId().toString(), "7", TransAmt, "2", "0", "0", "2", orderId);
		//更新借款人账户信息
		}
		//生成新的订单号 给投资人用
		String newOrdId=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");//生成新的订单号
		//投资人账号 增加 金额
		BpCustMember inCut = bpCustMemberService.getMemberByFlagId(InCustId);
		if(strArr[0].equals("loanInterest")){
			type=ObAccountDealInfo.T_PROFIT.toString();
		}else if(strArr[0].equals("principalRepayment")){
			type=ObAccountDealInfo.T_PRINCIALBACK.toString();
		}
		String orderId=OrdId+"-"+Common.genertOrder(3);
		 Map<String,Object> mapin=new HashMap<String,Object>();
		 mapin.put("investPersonId",inCut.getId());//投资人Id（必填）
		 mapin.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
		 mapin.put("transferType",Short.valueOf(type));//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
		 mapin.put("money",new BigDecimal(TransAmt));//交易金额	（必填）			 
		 mapin.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
		 mapin.put("dealType",ObAccountDealInfo.T_LOANPAY);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
		 mapin.put("recordNumber",newOrdId);//交易流水号	（必填）
		 mapin.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
		 String[] ret =operateAcountInfo(mapin);
		//String[]  ret0 = operateAcountInfo(inCut.getId().toString(), type, TransAmt, "2", "0", "0", "2", newOrdId);
		// 服务费更新
		BpCustMember feeMem=null;
		if(feeType.equals("O")){
			 feeMem=outCut;
		}else if(feeType.equals("I")){
			 feeMem=inCut;
		}
		if(Fee!=null&&!Fee.equals("")&&!Fee.equals("0.00")&&feeMem!=null){
			String newOrdId0=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");//生成新的订单号
			Map<String,Object> mapin3=new HashMap<String,Object>();
			 mapin3.put("investPersonId",feeMem.getId());//投资人Id（必填）
			 mapin3.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			 mapin3.put("transferType",ObAccountDealInfo.T_LOANINCOME);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			 mapin3.put("money",new BigDecimal(Fee));//交易金额	（必填）			 
			 mapin3.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			 mapin3.put("dealType",ObAccountDealInfo.T_LOANPAY);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			 mapin3.put("recordNumber",newOrdId0);//交易流水号	（必填）
			 mapin3.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			 String[] ret3 =operateAcountInfo(mapin3);
			//String[]  ret3 = operateAcountInfo(feeMem.getId().toString(), "6", Fee, "2", "0", "0", "2", newOrdId0);
			if(feeType.equals("O")){
			 totalMoney=new BigDecimal(Fee).add(new BigDecimal(TransAmt));
			}else{
				totalMoney=new BigDecimal(TransAmt).subtract(new BigDecimal(Fee));
			}
		}else {
			 totalMoney=new BigDecimal(TransAmt);
		}
		
		//更新款项计划表
		slFundIntentService.updateFinance(strArr[1]);
		
		retArr[0]=Constants.CODE_SUCCESS;
		retArr[1]=totalMoney.toString();
		return retArr;
	}

	@Override
	public ObAccountDealInfo getByOrderNumber(String orderNo, String totalFee,String transferType,
			String type0) {
		// TODO Auto-generated method stub
		return dao.getByOrderNumber(orderNo,totalFee,transferType,type0);
	}

	@Override
	public ObAccountDealInfo getBythirdNumber(String thirdNumber) {
		// TODO Auto-generated method stub
		return dao.getBythirdNumber(thirdNumber);
	}

	/**
	 * 查出来平台每个账户的自己每种明细帐的统计数据
	 */
	@Override
	public List<PlateFormStatisticsDetail> getOneTypePlateFormStaticsDatail(HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return dao.getOneTypePlateFormStaticsDatail(request,start,limit);
	}

	@Override
	public List<ObAccountDealInfo> cooperationAccountInfoList(Map<String, String> map) {
		return dao.cooperationAccountInfoList(map);
	}

	@Override
	public Long cooperationAccountInfoListCount(Map<String, String> map) {
		return dao.cooperationAccountInfoListCount(map);
	}

	@Override
	public BigDecimal sumPersonMoney(String transferType, Long investPersonId,
			String startTime, String endTime) {
		// TODO Auto-generated method stub
		return dao.sumPersonMoney(transferType, investPersonId, startTime, endTime);
	}
	
	@Override
	public BigDecimal getAccountIdRechargeMoney(Long accoutId) {
		return dao.getAccountIdRechargeMoney(accoutId);
	}
	
	@Override
	public BigDecimal sumMoney(String transferType, Long[] investPersonIds,
			String startTime, String endTime) {
		// TODO Auto-generated method stub
		return dao.sumMoney(transferType, investPersonIds, startTime, endTime);
	}

	@Override
	public List<ObAccountDealInfo> getAllThirdDealInfo(String day) {
		 return dao.getAllThirdDealInfo(day);
	}

	@Override
	public List<ObAccountDealInfo> getListByTransFerTypeAndState(
			String transferType, String dealRecordStatus,String searchHours) {
		return dao.getListByTransFerTypeAndState(transferType,dealRecordStatus,dealRecordStatus);
	}

	@Override
	public void raiseinterestNotMoney() {
		System.out.println("募集期转账定时任务开始:"+DateUtil.formatEnDate(new Date()));
		List<BpFundIntent> list  = bpFundIntentService.getRaiseinterestNotMoney();
		BigDecimal money = BigDecimal.ZERO;
		BpFundIntent bpFund;
		if (list != null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				bpFund = list.get(i);
				BpCustMember member = bpCustMemberService.get(bpFund.getInvestPersonId());
				if (member != null) {
					if (bpFund.getNotMoney().compareTo(BigDecimal.ZERO) != 0) {
						//请求银行,调用三方转账接口
						String orderNum = ContextUtil.createRuestNumber();
						money = bpFund.getNotMoney();
						if (money.longValue() > 100){
							money = new BigDecimal(100);
						}
						CommonRequst commonRequst = new CommonRequst();
						commonRequst.setRequsetNo(orderNum);
						commonRequst.setThirdPayConfigId(member.getThirdPayFlagId());
						commonRequst.setAmount(money);
						commonRequst.setThirdPayConfigId0(member.getThirdPayFlag0());
						commonRequst.setBussinessType(ThirdPayConstants.BT_TRANSFER_MEMBER);
						commonRequst.setTransferName(ThirdPayConstants.TN_TRANSFER_MEMBER);
						CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(commonRequst);
                        if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
//						if (true) {
							//放款成功,调整数据库相应字段
							bpFund.setNotMoney(bpFund.getNotMoney().subtract(money));
							bpFund.setAfterMoney(bpFund.getAfterMoney().add(money));
							bpFund.setFactDate(new Date());
							bpFundIntentService.save(bpFund);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("investPersonId", member.getId());//投资人Id（必填）
							map.put("investPersonType", ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）
							map.put("transferType", ObAccountDealInfo.T_BIDRETURN_RATE31);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
							map.put("money", money);//交易金额	（必填）
							map.put("dealDirection", ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
							map.put("dealType", ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
							map.put("recordNumber", orderNum);//交易流水号	（必填）
							map.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
							operateAcountInfo(map);

							//每放款成功一次,扣放款账户一笔钱
							Map<String, Object> map2 = new HashMap<String, Object>();
							map2.put("investPersonId", Long.valueOf("7164"));//投资人Id（必填）
							map2.put("investPersonType", ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）
							map2.put("transferType", ObAccountDealInfo.T_TRANSFER);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
							map2.put("money", money);//交易金额	（必填）
							map2.put("dealDirection", ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
							map2.put("dealType", ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
							map2.put("recordNumber", orderNum+"_1");//交易流水号	（必填）
							map2.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
							operateAcountInfo(map2);
							System.out.println("募集期转账定时任务处理结果:成功,转账个数:"+(i+1)+"转账金额:"+money);
						} else {
							System.out.println("募集期转账定时任务处理结果:未完全成功,转账个数:"+i+",转账金额:"+money);
							break;
						}
					}else{
						System.out.println("募集期转账定时任务处理结果:失败");
					}
				}
			}

		}else{
			System.out.println("定时任务处理结果:当前没有需要转账的用户");
		}


	}

	/**
	 *查询三方请求明细
	 *
	 * @auther: XinFang
	 * @date: 2018/7/23 14:12
	 */
	@Override
	public List<ThirdPayRecord> getThirdPayRecord(String bpName, String startDate,String transferType) {
		return dao.getThirdPayRecord(bpName,  startDate,transferType);
	}


}