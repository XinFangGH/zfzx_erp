package com.zhiwei.credit.action.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.UMPay.responUtil.UMPayBidAccount;
import com.thirdPayInterface.UMPay.responUtil.UMPayBidTransferCompare;
import com.thirdPayInterface.UMPay.responUtil.UMPayRecharge;
import com.thirdPayInterface.UMPay.responUtil.UMPayTransfer;
import com.thirdPayInterface.UMPay.responUtil.UMPayWithdraw;
import com.thirdPayInterface.UMPay.responUtil.withdrawAccountCompare;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObAccountDealInfoDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.thirdInterface.ThirdPayAccountDealInfo;
import com.zhiwei.credit.model.thirdInterface.YeePayReponse;
import com.zhiwei.credit.model.thirdPay.rechargeAccountCompare;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObObligationInvestInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
/**
 * 
 * @author 
 *
 */
public class ObSystemAccountAction extends BaseAction{
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private ObObligationInvestInfoService obObligationInvestInfoService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private ObAccountDealInfoService  obAccountDealInfoService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private ObAccountDealInfoDao obAccountDealInfoDao;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private PlBidPlanService plBidPlanService;
	private ObSystemAccount obSystemAccount;
	private Long id;
	
	private List list1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ObSystemAccount getObSystemAccount() {
		return obSystemAccount;
	}

	public void setObSystemAccount(ObSystemAccount obSystemAccount) {
		this.obSystemAccount = obSystemAccount;
	}

	public List getList1() {
		return list1;
	}

	public void setList1(List list1) {
		this.list1 = list1;
	}

	public String test() {
		obSystemAccountService.refreshRechargeDeal();
		return SUCCESS;
	}
	
	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ObSystemAccount> list= obSystemAccountService.getAll(filter);
		
		Type type=new TypeToken<List<ObSystemAccount>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				obSystemAccountService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ObSystemAccount obSystemAccount=obSystemAccountService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(obSystemAccount));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	 public String saveAccount(){
		 String  companyId=this.getRequest().getParameter("companyId");
		 String investName=this.getRequest().getParameter("investName");
		 String investId=this.getRequest().getParameter("investId");
		 String cardNumber=this.getRequest().getParameter("cardNumber");
		 String money=this.getRequest().getParameter("money");
		 String type=this.getRequest().getParameter("type");
		 investName=StringUtil.stringURLDecoderByUTF8(investName);
		 String[] ret=obSystemAccountService.saveAccount(companyId, investName, investId, cardNumber, money, type);
	    if(ret!=null&&ret[0].equals(Constants.CODE_SUCCESS)){
	    	jsonString = "{code:'"+Constants.CODE_SUCCESS+"',msg:'"+ret[1]+"'}";
	    }else{
	    	jsonString = "{code:'"+Constants.CODE_FAILED+"',msg:'"+ret[1]+"'}";
	    }
	    return SUCCESS;
	 }
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(obSystemAccount.getId()==null){
			obSystemAccountService.save(obSystemAccount);
		}else{
			ObSystemAccount orgObSystemAccount=obSystemAccountService.get(obSystemAccount.getId());
			try{
				BeanUtil.copyNotNullProperties(orgObSystemAccount, obSystemAccount);
				obSystemAccountService.save(orgObSystemAccount);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	// 查询出来投资人系统虚拟账户
	public String systemAccountList() {
		String investName = this.getRequest().getParameter("investPersonName");
		String accountType= this.getRequest().getParameter("accountType");
		List<ObSystemAccount> list = obSystemAccountService.findAccountList(investName,accountType,this.getRequest(),start,limit);
		List listcount= obSystemAccountService.findAccountList(investName,accountType,this.getRequest(),null,null);
		if(list != null && list.size() > 0){
			for(ObSystemAccount temp : list){
				BigDecimal freezMoney =obSystemAccountService.prefreezMoney(temp.getId(), null);
				if(freezMoney!=null){
					temp.setFreezeMoney(freezMoney);
					temp.setAvailableInvestMoney(temp.getTotalMoney().subtract(freezMoney));
				}else{
					temp.setAvailableInvestMoney(temp.getTotalMoney());
				}
				BigDecimal investTotalMoney =obSystemAccountService.typeTotalMoney(temp.getId(), ObAccountDealInfo.T_INVEST);
				temp.setTotalInvestMoney(investTotalMoney);
				BigDecimal profitTotalMoney =obSystemAccountService.typeTotalMoney(temp.getId(), ObAccountDealInfo.T_PROFIT);
				temp.setAllInterest(profitTotalMoney);
				BigDecimal princialBackTotalMoney =obSystemAccountService.typeTotalMoney(temp.getId(), ObAccountDealInfo.T_PRINCIALBACK);
				temp.setUnPrincipalRepayment(princialBackTotalMoney);
			}
		}
		/*if (list != null && list.size() > 0) {
			for (ObSystemAccount temp : list) {
				BigDecimal total = new BigDecimal(0);//累计投资额
				BigDecimal current = new BigDecimal(0);//目前投资额
				BigDecimal freeze = new BigDecimal(0);//预冻结投资额（投资尚未生效金额）
				BigDecimal unBackMoney =new BigDecimal(0);//待回收款
				BigDecimal unPrincipalRepayment =new BigDecimal(0);//待回收本金
				BigDecimal unInterest =new BigDecimal(0);//待回收利息
				BigDecimal allInterest =new BigDecimal(0);//累计收回利息
				BigDecimal expectAllInterest =new BigDecimal(0);//预期累计收回利息
				BigDecimal personInterestRate =new BigDecimal(0);//（累计收回利息/累计投资额）为累计收益率
				//表示当前投资人购买的已经全部回款和正在回款的债权记录（用来记录到目前为止这个账户的投资金额）
				List<ObObligationInvestInfo> listobigation = obObligationInvestInfoService.getListInvestPeonId(temp.getInvestmentPersonId(), "2");
				if (listobigation != null && listobigation.size() > 0) {
					for (ObObligationInvestInfo temp1 : listobigation) {
						total = total.add(temp1.getInvestMoney());
						List<SlFundIntent>  listSlFundInterest =slFundIntentService.getTreeIdAndFundType(temp1.getObligationId(), temp1.getInvestMentPersonId(), temp1.getId(),obligationFundIntenList.InvestAccrual);
						if(listSlFundInterest!=null&&listSlFundInterest.size()>0){
							for(SlFundIntent slf :listSlFundInterest){
								allInterest=allInterest.add(slf.getIncomeMoney());
							}
						}
					}
				}
				temp.setAllInterest(allInterest);//累计回收利息
				temp.setTotalInvestMoney(total);//累计投资额
				if(total.compareTo(new BigDecimal(0))==1){
					if(allInterest.compareTo(new BigDecimal(0))==1){
						personInterestRate =allInterest.divide(total,4,BigDecimal.ROUND_UP).multiply(new BigDecimal(100));
					}
				}
				temp.setPersonInterestRate(personInterestRate);//计算投资人收益率
				//表示当前投资人正在回款的债权购买记录（用来计算当前已经投资的金额）
				List<ObObligationInvestInfo> listobigationCu = obObligationInvestInfoService
						.getListInvestPeonId(temp.getInvestmentPersonId(), "1");
				if (listobigationCu != null && listobigationCu.size() > 0) {
					for (ObObligationInvestInfo temp2 : listobigationCu) {
						current = current.add(temp2.getInvestMoney());
						//查出没有对账的款项
						List<SlFundIntent>  listSlFund =slFundIntentService.getListByTreeIdUn(temp2.getObligationId(), temp2.getInvestMentPersonId(), temp2.getId());
						if(listSlFund!=null&&listSlFund.size()>0){
							for(SlFundIntent sl :listSlFund){//用来计算当前账户的未对账的款项
								if(sl.getFundType().equals(obligationFundIntenList.InvestAccrual)||sl.getFundType().equals(obligationFundIntenList.InvestRoot)){
									unBackMoney =unBackMoney.add(sl.getIncomeMoney());
									if(sl.getFundType().equals(obligationFundIntenList.InvestAccrual)){
										unInterest =unInterest.add(sl.getIncomeMoney());
									}else if(sl.getFundType().equals(obligationFundIntenList.InvestRoot)){
										unPrincipalRepayment =unPrincipalRepayment.add(sl.getIncomeMoney());
									}
								}
							}
						}
					}
				}
				temp.setUnBackMoney(unBackMoney);//待回收款
				temp.setUnInterest(unInterest);//待回收利息
				temp.setUnPrincipalRepayment(unPrincipalRepayment);//待回收本金
				expectAllInterest =unInterest.add(allInterest);//预期累计回收利息
				temp.setExpectAllInterest(expectAllInterest);//预期累计回收利息
				temp.setCurrentInvestMoney(current);
				//表示当前投资人正在还没有对账的债权购买记录（用来计算预冻结的投资记录）
				List<ObObligationInvestInfo> listobigationFr = obObligationInvestInfoService
						.getListInvestPeonId(temp.getInvestmentPersonId(), "0");
				if (listobigationFr != null && listobigationFr.size() > 0) {
					for (ObObligationInvestInfo temp3 : listobigationFr) {
						freeze = freeze.add(temp3.getInvestMoney());
					}
				}
				temp.setFreezeMoney(freeze);
				BigDecimal  avaible =temp.getTotalMoney().subtract(freeze);
				temp.setAvailableInvestMoney(avaible);
				
			}
		}*/
		Type type = new TypeToken<List<ObSystemAccount>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	//根据投资人的id  查出来投资人的系统账户信息 以及投资人信息
	public String getInvestPersonInfo(){
		String msg ="";
		String  investPersonId =this.getRequest().getParameter("investId");
		ObSystemAccount obSystemAccount=obSystemAccountService.getByInvrstPersonId(Long.valueOf(investPersonId));
		CsInvestmentperson person =csInvestmentpersonService.get(Long.valueOf(investPersonId));
		if(person!=null){
			msg =msg+"投资人："+person.getInvestName()+"    身份证号："+person.getCardnumber();
			if(obSystemAccount!=null){
				msg =msg+"    账户金额："+obSystemAccount.getTotalMoney()+"元";
			}
			msg =msg+"    通讯地址："+person.getPostaddress();
		}
		jsonString = "{success:true,msg:'" + msg + "'}";
		return SUCCESS;
	}
	/**
	 * 线上取现申请调用方法
	 * add by linyan 
	 * Date 2014-5-16
	 * @return
	 */
	public String startEnchantedFlow(){
		String investPersonId=this.getRequest().getParameter("investPersonId");
		String resource=this.getRequest().getParameter("resource");
		if(null!=investPersonId &&!"".equals(investPersonId) &&null!=resource&&!"".equals(resource)){
			String[]ret=null;
			String transferType=this.getRequest().getParameter("transferType");
			System.out.println("transferType=="+transferType);
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("investPersonId",Long.valueOf(investPersonId));//投资人Id（必填）
			map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					
			map.put("transferType",ObAccountDealInfo.T_ENCHASHMENT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			map.put("money",new BigDecimal(this.getRequest().getParameter("money")));//交易金额	（必填）			 
			map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			map.put("recordNumber",this.getRequest().getParameter("recordNumber"));//交易流水号	（必填）
			map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_1);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			ret =obAccountDealInfoService.operateAcountInfo(map);

			/*if(transferType.contains(",")){
				String[] s=transferType.split(",");
				ret=obAccountDealInfoService.newOpreaterDealInfo(investPersonId, s[0], this.getRequest().getParameter("money"), this.getRequest().getParameter("dealType"), resource, this.getRequest().getParameter("operateType"), this.getRequest().getParameter("accountType"), this.getRequest().getParameter("recordNumber"),s[1]);
			}else{
				ret=obAccountDealInfoService.operateAcountInfo(investPersonId, this.getRequest().getParameter("transferType"), this.getRequest().getParameter("money"), this.getRequest().getParameter("dealType"), resource, this.getRequest().getParameter("operateType"), this.getRequest().getParameter("accountType"), this.getRequest().getParameter("recordNumber"));
			}*/
			 
			if(null!=ret){
				jsonString = "{code:'"+ret[0]+"',msg:'" + ret[1] + "'}";
			}else{
				jsonString = "{code:'"+Constants.CODE_FAILED+"',msg:'没有返回值'}";
			}
		}else{
			jsonString = "{code:'"+Constants.CODE_FAILED+"',msg:'投资人ID和投资人类型不能为空'}";
		}
		
		return SUCCESS;
	}
	/**
	 *线上取现客户解冻账户交易信息
	 * @return
	 */
	public String updateAcountInfo(){
		String investPersonId=this.getRequest().getParameter("investPersonId");
		String resource=this.getRequest().getParameter("resource");
		if(null!=investPersonId &&!"".equals(investPersonId) &&null!=resource&&!"".equals(resource)){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("investPersonId",Long.valueOf(investPersonId));//投资人Id
			map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
			map.put("transferType",ObAccountDealInfo.T_ENCHASHMENT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
			map.put("money",new BigDecimal(this.getRequest().getParameter("money")));//交易金额
			map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
			map.put("DealInfoId",null);//交易记录id，没有默认为null
			map.put("recordNumber",this.getRequest().getParameter("recordNumber"));//交易流水号
			map.put("dealStatus",this.getRequest().getParameter("dealRecordStatus"));//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
			String[] ret =obAccountDealInfoService.updateAcountInfo(map);

			//String[] ret=obAccountDealInfoService.updateAcountInfo(Long.valueOf(investPersonId), this.getRequest().getParameter("transferType"), this.getRequest().getParameter("money"), resource, this.getRequest().getParameter("recordNumber"), null, this.getRequest().getParameter("dealRecordStatus"));
			if(null!=ret){
				jsonString = "{code:'"+ret[0]+"',msg:'" + ret[1] + "'}";
			}else{
				jsonString = "{code:'"+Constants.CODE_FAILED+"',msg:'没有返回值'}";
			}	
		}else{
			jsonString = "{code:'"+Constants.CODE_FAILED+"',msg:'投资人ID和投资人类型不能为空'}";
		}
		return SUCCESS;
	}
	/**
	 * 获取第三方支付账户信息
	 * @return
	 */
	public String getThirdPayAccountDealInfo(){
		String accountId=this.getRequest().getParameter("accountId");
		ThirdPayAccountDealInfo thirdAccount =new ThirdPayAccountDealInfo();
		if(accountId!=null&&!"".equals(accountId)){
			if ("-1".equals(accountId)) {//商户平台账户查询   富滇银行给出的
				String fudianPlatformAccount = AppUtil.getConfigMap().get("thirdPayConfig").toString().trim();
				String fudianPlatformUsername = AppUtil.getConfigMap().get("thirdPayConfig").toString().trim();
				thirdAccount.setPlateFormUserNo(fudianPlatformAccount);
				String orderNum1 =ContextUtil.createRuestNumber();
				CommonResponse response = new CommonResponse();
				CommonRequst common1 = new CommonRequst();
				common1.setRequsetNo(orderNum1);//请求流水号
				common1.setTransactionTime(new Date());//用户查询日期
				common1.setThirdPayConfigId(fudianPlatformAccount);//第三方账号
				common1.setThirdPayConfigId0(fudianPlatformUsername);//第三方账号
				common1.setBussinessType(ThirdPayConstants.BT_BALANCEQUERY);
				common1.setTransferName(ThirdPayConstants.TN_BALANCEQUERY);
				response=ThirdPayInterfaceUtil.thirdCommon(common1);	
				if(response.getResponsecode()!=null&&response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					thirdAccount.setPlateFormUserType("平台");
					thirdAccount.setAccountAvailableMoney(response.getWithDrawbalance().toString());
					thirdAccount.setAccountMoney(response.getBalance().toString());
					thirdAccount.setAccountFreezeMoney(response.getFreezeAmount().toString());
					//1代表账户状态正常，2代表账户冻结，3代表账户挂失，4账户销户
					thirdAccount.setThirdAccountStatus("正常");
				}
			}else {
				ObSystemAccount ob=obSystemAccountService.get(Long.valueOf(accountId));
				if(ob!=null){
					if(ob.getInvestPersionType().equals(ObSystemAccount.type0)){
						BpCustMember bp=bpCustMemberService.get(ob.getInvestmentPersonId());
						if(bp!=null){
							String thirdPayConfig= AppUtil.getConfigMap().get("thirdPayConfig").toString().trim();
							String thirdPayType= AppUtil.getConfigMap().get("thirdPayType").toString().trim();
							if(thirdPayType.equals("0")){//资金托管模式
								
								if(bp.getThirdPayFlagId()!=null){
									thirdAccount.setPlateFormUserNo(bp.getThirdPayFlagId());
									String orderNum1 =ContextUtil.createRuestNumber();
									CommonResponse response = new CommonResponse();
									CommonRequst common1 = new CommonRequst();
									common1.setRequsetNo(orderNum1);//请求流水号
									common1.setTransactionTime(new Date());//用户查询日期
									common1.setThirdPayConfigId(bp.getThirdPayFlagId());//第三方账号
									common1.setThirdPayConfigId0(bp.getThirdPayFlag0());//第三方账号
									common1.setBussinessType(ThirdPayConstants.BT_BALANCEQUERY);
									common1.setTransferName(ThirdPayConstants.TN_BALANCEQUERY);
									response=ThirdPayInterfaceUtil.thirdCommon(common1);	
									if(response.getResponsecode()!=null&&response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
										if(bp.getCustomerType().equals(BpCustMember.CUSTOMER_PERSON)){
											thirdAccount.setPlateFormUserType("个人");
										}else{
											thirdAccount.setPlateFormUserType("企业");
										}
										thirdAccount.setAccountAvailableMoney(response.getWithDrawbalance().toString());
										thirdAccount.setAccountMoney(response.getBalance().toString());
										thirdAccount.setAccountFreezeMoney(response.getFreezeAmount().toString());
										//1代表账户状态正常，2代表账户冻结，3代表账户挂失，4账户销户
										String state = response.getState();
										if (StringUtils.isNotEmpty(state)) {
											if ("1".equals(state)) {
												thirdAccount.setThirdAccountStatus("正常");
											}else if("2".equals(state)) {
												thirdAccount.setThirdAccountStatus("冻结");
											}else if("3".equals(state)) {
												thirdAccount.setThirdAccountStatus("账户挂失");
											}else {
												thirdAccount.setThirdAccountStatus("账户销户");
											}
										}else {
											thirdAccount.setThirdAccountStatus("正常");
										}
									}
								}
							}else{//资金池模式
								thirdAccount.setPlateFormUserNo(ob.getAccountNumber());
								thirdAccount.setPlateFormUserType("账户正常");
								thirdAccount.setAccountMoney(ob.getTotalMoney().toString());
								BigDecimal freeMoney=obAccountDealInfoDao.prefreezMoney(ob.getId(),null);
								BigDecimal availableMoney=new BigDecimal("0");
								if(freeMoney!=null){
									availableMoney=ob.getTotalMoney().subtract(freeMoney);
								}else{
									freeMoney=new BigDecimal("0");
								}
								thirdAccount.setAccountAvailableMoney(availableMoney.toString());
								thirdAccount.setAccountFreezeMoney(freeMoney.toString());
							}
						}else{
							//TODO 针对p2p账号不存在的情况处理
							
						}
					}else if(ob.getInvestPersionType().equals(ObSystemAccount.type1)){
						
					}else{
						
					}
				}else{
					
				}
			}
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(thirdAccount));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 新的查询系统账户方法  展示第三方支付账户信息
	 * add by linyan
	 * @return
	 */
	public String newSystemAccountList(){
		List<ObSystemAccount> accountList=obSystemAccountService.getNewSystemAccountList(this.getRequest(),start,limit);
		List<ObSystemAccount> listcount=obSystemAccountService.getNewSystemAccountList(this.getRequest(),null,null);
		Type type = new TypeToken<List<ObSystemAccount>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		buff.append(gson.toJson(accountList, type));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println("jsonString=="+jsonString);
		return SUCCESS;
	}
	/**
	 * 个人桌面显示的信息
	 * @return
	 */
	public String desktopisExceptionAccountList(){
		//查询前7条记录
		List<ObSystemAccount> accountList=obSystemAccountService.getNewSystemAccountList(this.getRequest(),start,7);
		getRequest().setAttribute("accountList", accountList);
		return "isExceptionAccountList";
	}
	/**
	 * 新的方法  用来执行查询系统账户第三方账户信息以及及时刷新虚拟账户余额
	 * add by linyan
	 */
	public void refreshThirdPayAccountCheckFile(){
		String accountId=this.getRequest().getParameter("accountId");
		obSystemAccountService.refreshThirdPayAccountCheckFile(accountId);
	}
	/**
	 * 全部更新系统第三方账户信息及实时刷新虚拟账户余额
	 */
	public void refreshAllobSystemAccount(){
		//查询系统所有发生了交易的用户 day 查询时间段
		String day = this.getRequest().getParameter("day");
		List<ObAccountDealInfo> deal = obAccountDealInfoService.getAllThirdDealInfo(day);
		for(ObAccountDealInfo ob:deal){
			obSystemAccountService.refreshThirdPayAccountCheckFile(ob.getAccountId().toString());
		}
	}
	/**
	 * 新方法  全部更新系统第三方账户信息及实时刷新虚拟账户余额，线程控制
	 * add by linyan
	 */
	public void refreshAllThirdPayAccountCheckFile(){
		obSystemAccountService.mutiplyTreadRefreshThirdPayAccount();
	}
	
	/**
	 * 合作机构资金账户管理
	 * @return
	 */
	public String cooperationAccountList(){

		
		String accountName = this.getRequest().getParameter("accountName");
		String offlineCustType = this.getRequest().getParameter("offlineCustType");
		Map<String,String> map = new HashMap<String, String>();
		map.put("start", this.start.toString());
		map.put("limit", this.limit.toString());
		map.put("accountName",accountName);
		map.put("offlineCustType",offlineCustType);
		
		
		List<ObSystemAccount> accountList = obSystemAccountService.cooperationAccountList(map);
		Long count = obSystemAccountService.cooperationAccountListCount(map);
		
		
		Type type = new TypeToken<List<ObSystemAccount>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(count == null ? 0 : count).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		buff.append(gson.toJson(accountList, type));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println("jsonString=="+jsonString);
		return SUCCESS;
		
	}
	
	// 查询出来线下投资人系统虚拟账户
	public String systemAccountDownList() {
		List<ObSystemAccount> list = obSystemAccountService.findDownAccount(this.getRequest(),start,limit);
		List listcount= obSystemAccountService.findDownAccount(this.getRequest(),null,null);
		if(list != null && list.size() > 0){
			for(ObSystemAccount temp : list){
				BigDecimal freezMoney =obSystemAccountService.prefreezMoney(temp.getId(), null);
				if(freezMoney!=null){
					temp.setFreezeMoney(freezMoney);
					temp.setAvailableInvestMoney(temp.getTotalMoney().subtract(freezMoney));
				}else{
					temp.setAvailableInvestMoney(temp.getTotalMoney());
				}
				BigDecimal investTotalMoney =obSystemAccountService.typeTotalMoney(temp.getId(), ObAccountDealInfo.T_INVEST);
				temp.setTotalInvestMoney(investTotalMoney);
				BigDecimal profitTotalMoney =obSystemAccountService.typeTotalMoney(temp.getId(), ObAccountDealInfo.T_PROFIT);
				temp.setAllInterest(profitTotalMoney);
				BigDecimal princialBackTotalMoney =obSystemAccountService.typeTotalMoney(temp.getId(), ObAccountDealInfo.T_PRINCIALBACK);
				temp.setUnPrincipalRepayment(princialBackTotalMoney);
			}
		}
		Type type = new TypeToken<List<ObSystemAccount>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 充值对账
	 * add by linyan
	 * @return
	 */
	public String rechargeReconciliationList(){
		String type1 = this.getRequest().getParameter("type");
		String searchDate = this.getRequest().getParameter("searchDate");
		String accountName = this.getRequest().getParameter("accountName");
		
		ArrayList<ObSystemAccount> list=new ArrayList<ObSystemAccount>();
		//downLoadFile(type1);
		List<rechargeAccountCompare> list3 = new ArrayList<rechargeAccountCompare>();
		List<ObSystemAccount> accountList=obSystemAccountService.rechargeReconciliationList(this.getRequest(),start,limit);
		List<ObSystemAccount> listcount=obSystemAccountService.rechargeReconciliationList(this.getRequest(),null,null);
		Type type = new TypeToken<List<rechargeAccountCompare>>() {}.getType();
		List<ObAccountDealInfo> dealList1 = new ArrayList<ObAccountDealInfo>();
		if(type1!=null&&!"".equals(type1)){
			//查询系统中所有充值成功的记录
			QueryFilter filter = new QueryFilter();
			filter.addFilter("Q_transferType_S_EQ", ObAccountDealInfo.T_RECHARGE);
			filter.addFilter("Q_dealRecordStatus_SN_EQ", (short)2);
			List<ObAccountDealInfo> dealList = obAccountDealInfoService.getAll(filter);
			if(dealList.size()>0){
				for(ObAccountDealInfo info:dealList){
					if(info.getTransferDate()!=null){
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String dateString = formatter.format(info.getTransferDate());
						if(dateString.equals(searchDate)){
							dealList1.add(info);
						}
					}
				}
			}
			List<UMPayRecharge> list4 = new ArrayList<UMPayRecharge>();
			if(searchDate!=null&&!"".equals(searchDate)){
				list4 = downLoadFile(type1,DateUtil.parseDate(searchDate));
			}else{
				list4 = downLoadFile(type1,new Date());
				
			}
			
			rechargeAccountCompare compare = new rechargeAccountCompare();
			
			for(ObAccountDealInfo info:dealList1){
				compare = new rechargeAccountCompare();
				compare.setRechargeTime(info.getCreateDate().toString());
				compare.setRechargeMoney(info.getIncomMoney().toString());
				compare.setAccountBalance(info.getCurrentMoney().toString());
				compare.setP2pRequestNoLocal(info.getRecordNumber());
				compare.setP2pTransferDate(info.getTransferDate().toString());
				if(info.getAccountId()!=null&&!"".equals(info.getAccountId())){
				}
				if(list4!=null&&list4.size()>0){//循环第三方返回的信息 找到与系统对应的信息 并添加到比较实体类中
					int i = 0;
					for(int u=0;u<list4.size();u++ ){
						if(list4.get(u).getP2pRequestNo().trim().equals(info.getRecordNumber().trim())){
							i++;
							compare.setP2pRequestNo(list4.get(u).getP2pRequestNo());
							compare.setCreateDate(list4.get(u).getCreateDate());
							compare.setAccountNo(list4.get(u).getAccountNo());
							compare.setPlateFormNo(list4.get(u).getPlateFormNo());
							compare.setMoney(list4.get(u).getMoney());
							compare.setAccountNoDate(list4.get(u).getAccountNoDate());
							compare.setAccountNoTime(list4.get(u).getAccountNoTime());
							compare.setAccountOrderNo(list4.get(u).getAccountOrderNo());
							compare.setUserName(list4.get(u).getUserName());
						}
					}
				}
				list3.add(compare);
			}
			for(ObSystemAccount list1:accountList){
				BigDecimal RechargeMoney=obAccountDealInfoService.getAccountIdRechargeMoney(list1.getId());
				list1.setRechargeMoney(RechargeMoney);
				list.add(list1);
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		buff.append(gson.toJson(list3, type));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println("jsonString=="+jsonString);
		return SUCCESS;
	}
	
	/**
	 * 提现对账
	 * add by linyan
	 * @return
	 */
	public String withdrawalsReconciliationList(){
		String searchDate = this.getRequest().getParameter("searchDate");
		String accountName = this.getRequest().getParameter("accountName");
		String accountNumber = this.getRequest().getParameter("accountNumber");
		List<withdrawAccountCompare> list3 = new ArrayList<withdrawAccountCompare>();
		List<ObSystemAccount> accountList=obSystemAccountService.rechargeReconciliationList(this.getRequest(),start,limit);
		List<ObSystemAccount> listcount=obSystemAccountService.rechargeReconciliationList(this.getRequest(),null,null);
		Type type = new TypeToken<List<withdrawAccountCompare>>() {}.getType();
		List<ObAccountDealInfo> dealList1 = new ArrayList<ObAccountDealInfo>();
		
		//查询系统中所有充值成功的记录
		QueryFilter filter = new QueryFilter();
		filter.addFilter("Q_transferType_S_EQ", ObAccountDealInfo.T_ENCHASHMENT);
		filter.addFilter("Q_dealRecordStatus_SH_EQ", (short)2);
		System.out.println(accountName+"   "+accountNumber);
		if(accountName!=null&&!"".equals(accountName)){
			filter.addFilter("Q_investPersonName_S_EQ", accountName);
		}
        if(accountNumber!=null&&!"".equals(accountNumber)){
        	if(null != bpCustMemberService.getMemberUserName(accountNumber) &&
        			!"".equals(bpCustMemberService.getMemberUserName(accountNumber))){
        		filter.addFilter("Q_investPersonId_L_EQ",bpCustMemberService.getMemberUserName(accountNumber).getId());
        	}
        	
		}
		List<ObAccountDealInfo> dealList = obAccountDealInfoService.getAll(filter);
		if(dealList.size()>0){
			for(ObAccountDealInfo info:dealList){
				if(info.getTransferDate()!=null){
					  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					  String dateString = formatter.format(info.getTransferDate());
					  System.out.println(dateString);
					  if(dateString.equals(searchDate)){
						  dealList1.add(info);
					  }
				}
			}
		}
		System.out.println(dealList1.size()+"//';......");
		List<UMPayWithdraw> list4 = new ArrayList<UMPayWithdraw>();
		if(searchDate!=null&&!"".equals(searchDate)){
			list4 = downLoadFile("2",DateUtil.parseDate(searchDate));
		}else{
			list4 = downLoadFile("2",new Date());
			
		}
			
			
        for(ObAccountDealInfo info:dealList1){
        	if(info.getAccountId()!=null&&!"".equals(info.getAccountId())){
		    }
        	withdrawAccountCompare	compare = new withdrawAccountCompare();
        	System.out.println(info.getInvestPersonId()+"....."+bpCustMemberService.get(info.getInvestPersonId()).getLoginname());
		    compare.setUserName(bpCustMemberService.get(info.getInvestPersonId()).getLoginname());
			compare.setWithdrawTime(info.getCreateDate().toString());
			compare.setWithdrawMoney(info.getPayMoney().toString());
			compare.setAccountBalance(info.getCurrentMoney().toString());
			compare.setP2pRequestNoLocal(info.getRecordNumber());
			compare.setP2pTransferDate(info.getTransferDate().toString());
			
			if(list4.size()>0){//循环第三方返回的信息 找到与系统对应的信息 并添加到比较实体类中
				int i = 0;
				for(int u=0;u<list4.size();u++ ){
					System.out.println("充值返回字符串的长度"+list4.size());
					System.out.println("请求商户号是"+list4.get(u).getTransferOrderNo()+"..."+list4.get(u).getPlateOrderNo()+".."+list4.get(u).getPlateFormNo());
					System.out.println(list4.get(u).getPlateOrderNo()+":"+info.getRecordNumber());
					if(list4.get(u).getPlateOrderNo().trim().equals(info.getRecordNumber().trim())){
						System.out.println("数据"+i);
						i++;
						compare.setPlateFormNo(list4.get(u).getPlateFormNo());
						compare.setPlateOrderNo(list4.get(u).getPlateOrderNo());
						compare.setOrderDate(list4.get(u).getOrderDate());
						compare.setTransferMoney(list4.get(u).getTransferMoney());
						compare.setTransferFee(list4.get(u).getTransferFee());
						compare.setCheckAccountDate(list4.get(u).getCheckAccountDate());
						compare.setSaveAccountDate(list4.get(u).getSaveAccountDate());
					    compare.setTransferOrderNo(list4.get(u).getTransferOrderNo());
					    compare.setTransferState(list4.get(u).getTransferState());
						list3.add(compare);
					}
				}
			}
        }
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		buff.append(gson.toJson(list3, type));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println("2jsonString=="+jsonString);
		return SUCCESS;
	}
	
	//标的对账
	public String bidReconciliationList(){
		String searchDate = this.getRequest().getParameter("searchDate");
		Type type = new TypeToken<List<UMPayBidAccount>>() {}.getType();
		List<UMPayBidAccount> list4 =new ArrayList<UMPayBidAccount>();;
		if(searchDate!=null&&!"".equals(searchDate)){
			list4 = downLoadFile("3",DateUtil.parseDate(searchDate));
		}else{
			list4 = downLoadFile("3",new Date());
			
		}
		List<UMPayBidAccount> list =new ArrayList<UMPayBidAccount>();
		if(null != list4 && list4.size()>0){
			for(UMPayBidAccount bid:list4){
				String id=bid.getBidId();
				System.out.println(id+"...id");
				if(bid.getBidId()!=null){
					if(id.contains("mmplan")){
						System.out.println(".....");
						Long planId=Long.valueOf(id.split("mmplan")[1]);
						if(plManageMoneyPlanService.get(planId)!=null){
							bid.setBidName(plManageMoneyPlanService.get(planId).getMmName());
						}
						
					}
					if(id.contains("P_Or")){
						Long planId=Long.valueOf(id.split("P_Or")[1]);
						if(plBidPlanService.get(planId)!=null){
							bid.setBidName(plBidPlanService.get(planId).getBidProName());
						}
						
					}
					if(id.contains("P_Dir")){
						Long planId=Long.valueOf(id.split("P_Dir")[1]);
						if(plBidPlanService.get(planId)!=null){
							bid.setBidName(plBidPlanService.get(planId).getBidProName());
						}
						
					}
					list.add(bid);
				}
				
			}
		}
		System.out.println(list.size()+"......");
	    StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(list == null ? 0 : list.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println("2jsonString=="+jsonString);
		return SUCCESS;
	}
	
	//标的转账对账
	public String bidTransferReconciliationList(){
		String searchDate = this.getRequest().getParameter("searchDate");
		Type type = new TypeToken<List<UMPayBidTransferCompare>>() {}.getType();
		List<UMPayBidTransferCompare> list4 =new ArrayList<UMPayBidTransferCompare>();
		if(searchDate!=null&&!"".equals(searchDate)){
			list4 = downLoadFile("4",DateUtil.parseDate(searchDate));
		}else{
			list4 = downLoadFile("4",new Date());
			
		}
	    StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(list4 == null ? 0 : list4.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		buff.append(gson.toJson(list4, type));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println("2jsonString=="+jsonString);
		return SUCCESS;
	}
	
	//转账对账
	public String StandardReconciliationTransferList(){
		String searchDate = this.getRequest().getParameter("abortDate");//时间
		List<ObSystemAccount> accountList=obSystemAccountService.standardReconciliationTransfer(this.getRequest(),start,limit);
		List<ObSystemAccount> listcount=obSystemAccountService.standardReconciliationTransfer(this.getRequest(),null,null);
		Type type = new TypeToken<List<UMPayTransfer>>() {}.getType();
		List<UMPayTransfer> list4= new ArrayList<UMPayTransfer>();
		if(searchDate!=null&&!"".equals(searchDate)){
			list4 = downLoadFile("5",DateUtil.parseDate(searchDate));
		}else{
			list4 = downLoadFile("5",new Date());
		}
	    StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(list4 == null ? 0 : list4.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		buff.append(gson.toJson(list4, type));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 对账文件下载
	 * @param type
	 * @param date
	 * @return
	 */
	public List downLoadFile(String type,Date date){
		if(type!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");//小写的mm表示的是分钟  
			Object [] ret = new Object[3];
			if(type.equals("1")){//充值交易明细文件
				String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				CommonRequst cr = new CommonRequst();
				cr.setRequsetNo(orderNum);//流水号
				cr.setTransactionTime(date);//查询时间
				cr.setBussinessType(ThirdPayConstants.BT_RECHARGEFILE);//业务类型
				cr.setTransferName(ThirdPayConstants.TN_RECHARGEFILE);//业务用途
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(cr);
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					if(commonResponse.getuMPayRechargeList()!=null){
						 list1 = commonResponse.getuMPayRechargeList();
					}else{
						 list1 = commonResponse.getRecordList();
					}
					
				}
			}else if(type.equals("2")){//提现交易明细文件
				String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				CommonRequst cr = new CommonRequst();
				cr.setRequsetNo(orderNum);//流水号
				cr.setTransactionTime(date);//查询时间
				cr.setBussinessType(ThirdPayConstants.BT_WITHDRAWFILE);//业务类型
				cr.setTransferName(ThirdPayConstants.TN_WITHDRAWFILE);//业务用途
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(cr);
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					 list1 = commonResponse.getuMPayWithdrawList();
				}
			}else if(type.equals("3")){//3.	标的对账文件（全量）
				String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				CommonRequst cr = new CommonRequst();
				cr.setRequsetNo(orderNum);//流水号
				cr.setTransactionTime(date);//查询时间
				cr.setBussinessType(ThirdPayConstants.BT_BIDBALANCEFILE);//业务类型
				cr.setTransferName(ThirdPayConstants.TN_BIDBALANCEFILE);//业务用途
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(cr);
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					 list1 = commonResponse.getuMPayWithdrawList();
				}
			}else if(type.equals("4")){//4.	标的转账
				String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				CommonRequst cr = new CommonRequst();
				cr.setRequsetNo(orderNum);//流水号
				cr.setTransactionTime(date);//查询时间
				cr.setBussinessType(ThirdPayConstants.BT_BIDTRANSFERFILE);//业务类型
				cr.setTransferName(ThirdPayConstants.TN_BIDTRANSFERFILE);//业务用途
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(cr);
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					 list1 = commonResponse.getuMPayWithdrawList();
				}
			}else if(type.equals("5")){//5.	转账对账文件
				String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				CommonRequst cr = new CommonRequst();
				cr.setRequsetNo(orderNum);//流水号
				cr.setTransactionTime(date);//查询时间
				cr.setBussinessType(ThirdPayConstants.BT_TRANSFERFILE);//业务类型
				cr.setTransferName(ThirdPayConstants.TN_TRANSFERFILE);//业务用途
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(cr);
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					 list1 = commonResponse.getuMPayWithdrawList();
				}
			}
		  
		}
		return list1;
	}
	
}
