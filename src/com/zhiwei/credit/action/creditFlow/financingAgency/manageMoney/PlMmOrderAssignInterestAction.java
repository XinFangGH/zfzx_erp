package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hurong.credit.util.Common;
import com.messageAlert.service.SmsSendService;
import com.thirdPayInterface.CommonRequestInvestRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.BpFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;
import com.zhiwei.credit.model.thirdInterface.Fuiou;
import com.zhiwei.credit.model.thirdInterface.Repayment;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import com.zhiwei.credit.service.thirdInterface.FuiouService;
import com.zhiwei.credit.service.thirdInterface.OpraterBussinessDataService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class PlMmOrderAssignInterestAction extends BaseAction{
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private FuiouService fuiouService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	public PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoSevice;
	@Resource
	public PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	public BpCouponsService bpCouponsService;
	@Resource
	public OpraterBussinessDataService opraterBussinessDataService;
	@Resource
	private SmsSendService smsSendService;
	
	SmsSendUtil smsSendUtil=new SmsSendUtil();
	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	private PlMmOrderAssignInterest plMmOrderAssignInterest;
	 private PlEarlyRedemption plEarlyRedemption;
	private Long assignInterestId;
    private Long investPersonId;
    private String keystr;
    private Long earlyRedemptionId;
    private Long orderId;
    
    
    public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public PlEarlyRedemption getPlEarlyRedemption() {
		return plEarlyRedemption;
	}

	public void setPlEarlyRedemption(PlEarlyRedemption plEarlyRedemption) {
		this.plEarlyRedemption = plEarlyRedemption;
	}

	public Long getEarlyRedemptionId() {
		return earlyRedemptionId;
	}

	public void setEarlyRedemptionId(Long earlyRedemptionId) {
		this.earlyRedemptionId = earlyRedemptionId;
	}
	
	public Long getAssignInterestId() {
		return assignInterestId;
	}

	public void setAssignInterestId(Long assignInterestId) {
		this.assignInterestId = assignInterestId;
	}

	public Long getInvestPersonId() {
		return investPersonId;
	}

	public void setInvestPersonId(Long investPersonId) {
		this.investPersonId = investPersonId;
	}

	public PlMmOrderAssignInterest getPlMmOrderAssignInterest() {
		return plMmOrderAssignInterest;
	}

	public void setPlMmOrderAssignInterest(PlMmOrderAssignInterest plMmOrderAssignInterest) {
		this.plMmOrderAssignInterest = plMmOrderAssignInterest;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		List flist = new ArrayList();
		flist.add("loanInterest");
		flist.add("riskRate");
		flist.add("liquidatedDamages");
		flist.add("principalRepayment");
		filter.addFilterIn("Q_fundType_S_IN", flist);
		List<PlMmOrderAssignInterest> list= plMmOrderAssignInterestService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
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
				plMmOrderAssignInterestService.remove(new Long(id));
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
		PlMmOrderAssignInterest plMmOrderAssignInterest=plMmOrderAssignInterestService.get(assignInterestId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plMmOrderAssignInterest));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String assignInterest(){
		PlMmOrderAssignInterest plMmOrderAssignInterest=plMmOrderAssignInterestService.get(assignInterestId);
		plMmOrderAssignInterest.setLoanerRepayMentStatus(2);
		plMmOrderAssignInterest.setFactDate(new Date());
		String	orderNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
		//ObSystemAccount obSystemAccount=obSystemAccountService.getByInvrstPersonId(investPersonId);
		if(keystr.equals("mmproduce")){
			
			if(plMmOrderAssignInterest.getIncomeMoney().compareTo(new BigDecimal(0))>0){
				plMmOrderAssignInterest.setAfterMoney(plMmOrderAssignInterest.getIncomeMoney());
			}
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("investPersonId",investPersonId);//投资人Id（必填）
			map.put("investPersonType",ObSystemAccount.type1);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）	
			if(plMmOrderAssignInterest.getIncomeMoney().compareTo(new BigDecimal(0))>0){//派利息和本金
				plMmOrderAssignInterest.setAfterMoney(plMmOrderAssignInterest.getIncomeMoney());
				map.put("transferType",plMmOrderAssignInterest.getFundType().equals("principalRepayment")?ObAccountDealInfo.T_PRINCIALBACK:ObAccountDealInfo.T_PROFIT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				map.put("money",plMmOrderAssignInterest.getIncomeMoney());//交易金额	（必填）			 
				map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			}else{//收取补偿息
				plMmOrderAssignInterest.setAfterMoney(plMmOrderAssignInterest.getPayMoney());
				map.put("transferType",ObAccountDealInfo.T_PLATEFORM_OTHERFEE);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				map.put("money",plMmOrderAssignInterest.getPayMoney());//交易金额	（必填）			 
				map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			}
			
			map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			map.put("recordNumber",orderNum);//交易流水号	（必填）
			map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			String[] ret =obAccountDealInfoService.operateAcountInfo(map);
			/*String[] relt=obAccountDealInfoService.operateAcountInfo(investPersonId.toString(),plMmOrderAssignInterest.equals("principalRepayment")?ObAccountDealInfo.T_PRINCIALBACK.toString():ObAccountDealInfo.T_PROFIT.toString(), plMmOrderAssignInterest.getIncomeMoney().toString() ,ObAccountDealInfo.BANKDEAL.toString(),
						ObSystemAccount.type1.toString(), ObAccountDealInfo.ISAVAILABLE.toString(),ObAccountDealInfo.UNREADTHIRDRECORD.toString(),orderNum);*/
			if(ret[0].equals(Constants.CODE_SUCCESS)){
				plMmOrderAssignInterestService.merge(plMmOrderAssignInterest);
				setJsonString("{success:true,msg:"+ret[1]+"}");
			}else{
				setJsonString("{success:true,msg:"+ret[1]+"}");
			}
			
		}
		return SUCCESS;
	}
	/**
	 * 解除派息锁定
	 * @return
	 */
	public String updateLockType(){
		String[] ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String temp:ids){
				PlMmOrderAssignInterest plMmOrderAssignInterest=plMmOrderAssignInterestService.get(Long.valueOf(temp));
				plMmOrderAssignInterest.setLockType(null);
				plMmOrderAssignInterestService.save(plMmOrderAssignInterest);
			}
			setJsonString("{success:true,msg:'解除成功'}");
			return SUCCESS;
		}else{
			setJsonString("{success:true,msg:'未找到派息记录'}");
			return SUCCESS;
		}
	}
	/**
	 * 理财计划派息方法
	 */
	public String assignInterestMoneyPlan(){
		try{
			String[] ids=getRequest().getParameterValues("ids");
			int assignCount=0;//成功派息记录
			String msg="";
			if(ids!=null){
				for(String temp:ids){
					String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
					PlMmOrderAssignInterest plMmOrderAssignInterest=plMmOrderAssignInterestService.get(Long.valueOf(temp));
					if(plMmOrderAssignInterest.getLockType()==null||plMmOrderAssignInterest.getLockType().equals("")){
						plMmOrderAssignInterest.setLockType("1");//锁定
						plMmOrderAssignInterest.setLoanerRequestNo(orderNum);
						plMmOrderAssignInterestService.save(plMmOrderAssignInterest);
						if(plMmOrderAssignInterest.getAfterMoney().compareTo(plMmOrderAssignInterest.getIncomeMoney())<0){
							String[] set=new String[2];
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							if(configMap.get("thirdPayType").toString().equals("0")){
								PlManageMoneyPlanBuyinfo info=plManageMoneyPlanBuyinfoSevice.get(plMmOrderAssignInterest.getOrderId());
								BpCustMember customer=bpCustMemberService.get(plMmOrderAssignInterest.getInvestPersonId());
								BpCustMember moneyReciver=bpCustMemberService.getMemberUserName(info.getPlManageMoneyPlan().getMoneyReceiver());
								if(null !=info.getPlManageMoneyPlan().getReceiverType()&&
										info.getPlManageMoneyPlan().getReceiverType().equals("pt")){//平台账户派息
									CommonRequst commonRequest = new CommonRequst();
									commonRequest.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方标识
									commonRequest.setRequsetNo(orderNum);//请求流水号
									commonRequest.setAmount(plMmOrderAssignInterest.getIncomeMoney());//交易金额
									commonRequest.setCustMemberType("0");
									commonRequest.setBidId(plMmOrderAssignInterest.getMmplanId().toString());//标的Id
									commonRequest.setBussinessType(ThirdPayConstants.BT_MMGIVEPLATF);//业务类型
									commonRequest.setTransferName(ThirdPayConstants.TN_MMGIVEPLATF);//业务名称
									CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
									if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
										Map<String,String> map=new HashMap<String,String>();
										map.put("requsetNo",orderNum);
										map.put("isCreate","1");
										set = opraterBussinessDataService.dividendAssigninterest(map);
									}else{
										set[0] = CommonResponse.RESPONSECODE_FAILD;
										set[1] = "操作记录：派息操作失败!";
									}
								}else{//注册账户派息
									List<CommonRequestInvestRecord> repayList =new ArrayList<CommonRequestInvestRecord>();
									CommonRequestInvestRecord cir = new CommonRequestInvestRecord();
									cir.setInvest_thirdPayConfigId(customer.getThirdPayFlagId());
									cir.setBidRequestNo(info.getDealInfoNumber());
									cir.setAmount(plMmOrderAssignInterest.getIncomeMoney());
									cir.setFee(new BigDecimal("0"));
									repayList.add(cir);
									CommonRequst commonRequest=new CommonRequst();
									commonRequest.setInvest_thirdPayConfigId(customer.getThirdPayFlagId());
									commonRequest.setThirdPayConfigId(moneyReciver.getThirdPayFlagId());//用户第三方支付账号
									commonRequest.setRequsetNo(orderNum);//请求流水号
									commonRequest.setBidId(plMmOrderAssignInterest.getMmplanId().toString());//标的Id
									commonRequest.setBussinessType(ThirdPayConstants.BT_MMGIVEUSER);//计划类型
									commonRequest.setTransferName(ThirdPayConstants.TN_MMGIVEUSER);//计划名称
									commonRequest.setRepaymemntList(repayList);
									commonRequest.setSubOrdId(info.getDealInfoNumber());//投标时的流水号
									commonRequest.setSubOrdDate(sdf.format(info.getBuyDatetime()));//投标日期
									commonRequest.setPlanMoney(plMmOrderAssignInterest.getIncomeMoney());
									if(moneyReciver.getCustomerType()!=null&&moneyReciver.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
										commonRequest.setAccountType(1);
									}else{//借款人是个人
										commonRequest.setAccountType(0);
									}
									commonRequest.setFundType(plMmOrderAssignInterest.getFundType());
									commonRequest.setAmount(plMmOrderAssignInterest.getIncomeMoney());
									CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
									if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {//派息成功
										Map<String,String> map=new HashMap<String,String>();
										map.put("requsetNo",orderNum);
										map.put("isCreate","2");
										set = opraterBussinessDataService.dividendAssigninterest(map);
									}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_FREEZE)){//派息申请成功，继续调用审核接口
										String requestNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
										CommonRequst request = new CommonRequst();
										request.setRequsetNo(requestNum);
										request.setLoanNoList(orderNum);//审核流水号，还款时的流少号
										request.setConfimStatus(false);
										request.setBussinessType(ThirdPayConstants.BT_USEALLAUDIT);//业务类型
										request.setTransferName(ThirdPayConstants.TN_USEALLAUDIT);//名称
										CommonResponse response=ThirdPayInterfaceUtil.thirdCommon(request);
										if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
											Map<String,String> map=new HashMap<String,String>();
											map.put("requsetNo",orderNum);
											map.put("isCreate","2");
											set = opraterBussinessDataService.dividendAssigninterest(map);
										}
									}else{
										set[0] = CommonResponse.RESPONSECODE_FAILD;
										set[1] = "操作记录：派息操作失败!";
									}
								}
								if(set[0].equals(CommonResponse.RESPONSECODE_SUCCESS)){
									//派发优惠券奖励
									checkCoupons(plMmOrderAssignInterest);
									BpCustMember bm=bpCustMemberService.get(plMmOrderAssignInterest.getInvestPersonId());
									//派息成功发送短信
									//本金
									if(plMmOrderAssignInterest.getFundType().equals("principalRepayment")){
										Map<String, String> map = new HashMap<String, String>();
										map.put("telephone", bm.getTelphone());
										map.put("${name}", bm.getTruename());
										map.put("${investPrincipal}", plMmOrderAssignInterest.getAfterMoney().toString());
										map.put("${investInterest}", new BigDecimal(0.00).toString());
										map.put("${projName}", plMmOrderAssignInterest.getMmName());
										map.put("${payintentPeriod}", plMmOrderAssignInterest.getPeriods().toString());
										map.put("key","sms_paymentRemind");
										smsSendService.smsSending(map);
										
										//smsSendUtil.sms_paymentRemind(bm.getTelphone(), bm.getTruename(),plMmOrderAssignInterest.getAfterMoney().toString(), new BigDecimal(0.00).toString(), plMmOrderAssignInterest.getMmName(),plMmOrderAssignInterest.getPeriods().toString());
									//利息与提前赎回违约金
									}else if(plMmOrderAssignInterest.getFundType().equals("loanInterest") 
											|| plMmOrderAssignInterest.getFundType().equals("liquidatedDamages")){
										Map<String, String> map = new HashMap<String, String>();
										map.put("telephone", bm.getTelphone());
										map.put("${name}", bm.getTruename());
										map.put("${investPrincipal}", new BigDecimal(0.00).toString());
										map.put("${investInterest}", plMmOrderAssignInterest.getAfterMoney().toString());
										map.put("${projName}", plMmOrderAssignInterest.getMmName());
										map.put("${payintentPeriod}", plMmOrderAssignInterest.getPeriods().toString());
										map.put("key","sms_paymentRemind");
										smsSendService.smsSending(map);
										
										//smsSendUtil.sms_paymentRemind(bm.getTelphone(), bm.getTruename(),new BigDecimal(0.00).toString(), plMmOrderAssignInterest.getAfterMoney().toString(), plMmOrderAssignInterest.getMmName(),plMmOrderAssignInterest.getPeriods().toString());
									}
									
									assignCount++;
								}else if(set[0].equals(CommonResponse.RESPONSECODE_APPLAY)){
									assignCount++;
								}
							}else{
								plMmOrderAssignInterest.setAfterMoney(plMmOrderAssignInterest.getIncomeMoney());
								plMmOrderAssignInterestService.save(plMmOrderAssignInterest);
								Map<String,Object> map=new HashMap<String,Object>();
								map.put("investPersonId",plMmOrderAssignInterest.getInvestPersonId());//投资人Id（必填）
								map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
								map.put("transferType",plMmOrderAssignInterest.equals("principalRepayment")?ObAccountDealInfo.T_PRINCIALBACK:ObAccountDealInfo.T_PROFIT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
								map.put("money",plMmOrderAssignInterest.getIncomeMoney());//交易金额	（必填）			 
								map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
								map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
								map.put("recordNumber",orderNum);//交易流水号	（必填）
								map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
								String[] ret =obAccountDealInfoService.operateAcountInfo(map);
								assignCount++;
							}
						}
						List<PlMmOrderAssignInterest> plList= plMmOrderAssignInterestService.getByDealCondition(plMmOrderAssignInterest.getOrderId(),plMmOrderAssignInterest.getInvestPersonId());
						if(plList!=null&&plList.size()>0){
							
						}else{
							PlManageMoneyPlanBuyinfo info=plManageMoneyPlanBuyinfoSevice.get(plMmOrderAssignInterest.getOrderId());
							info.setState(Short.valueOf("10"));
							plManageMoneyPlanBuyinfoSevice.save(info);
						}
					}else{
						setJsonString("{success:true,msg:'已操作过该条派息记录,请先确定是否正常派息,重新派息请先解除派息锁定'}");
						return SUCCESS;
					}
					}
					
				setJsonString("{success:true,msg:'本次需派息记录个数为:"+(ids!=null?ids.length:0)+",成功派息个数为："+assignCount+"'}");
			}else{
				setJsonString("{success:true,msg:'没有选择派息记录'}");
			}
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:true,msg:'系统报错'}");
			return SUCCESS;
		}
	}
	public String listCoupons(){
		String mmplanId = this.getRequest().getParameter("mmplanId");
		if(!mmplanId.equals("undefined")&&mmplanId!=null&&!mmplanId.equals("")){
			List<PlMmOrderAssignInterest> list = plMmOrderAssignInterestService.listByMmPlanId(Long.valueOf(mmplanId),"coupons");
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(list.size()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[] { "intentDate"});
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
		}
		return SUCCESS;
	}
	public String listByEarlyRedemptionId(){
		try{
			String flag=this.getRequest().getParameter("flag");
			List<PlMmOrderAssignInterest> list=new ArrayList<PlMmOrderAssignInterest>();
			if(flag.equals("1")){
				if(null!=earlyRedemptionId){
					list=plMmOrderAssignInterestService.listByEarlyRedemptionId(earlyRedemptionId);
				}else{
					QueryFilter filter=new QueryFilter();
					filter.addFilter("Q_orderId_L_EQ", orderId.toString());
					filter.addFilter("Q_isValid_SN_EQ", "0");
					filter.addFilter("Q_isCheck_SN_EQ", "0");
					list=plMmOrderAssignInterestService.getAll(filter);
				}
			}else if(flag.equals("0")){
				list=plMmOrderAssignInterestService.createList(plEarlyRedemption, orderId,earlyRedemptionId);
			}
			StringBuffer buff=new StringBuffer("{success:true,result:");
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}


	/**
	 * 体验标派息方法
	 */
	public String dividendExperience(){
		try{
			String mmplanId= getRequest().getParameter("mmplanId");
			int assignCount=0;//成功派息记录
			String[] set=new String[2];
			QueryFilter filter=new QueryFilter(this.getRequest());
			filter.getPagingBean().setPageSize(100000000);
			filter.addFilter("Q_mmplanId_L_EQ", mmplanId);
			filter.addFilter("Q_isValid_SN_EQ", "0");
			filter.addFilter("Q_isCheck_SN_EQ", "0");
			filter.addFilter("Q_factDate_D_EQ", null);
			List<PlMmOrderAssignInterest> orderlist= plMmOrderAssignInterestService.getAll(filter);
			if(null !=orderlist && orderlist.size()>0){
				for(PlMmOrderAssignInterest plMmOrderAssignInterest:orderlist){
					if(plMmOrderAssignInterest.getAfterMoney().compareTo(plMmOrderAssignInterest.getIncomeMoney())<0){
						String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
						BpCustMember customer=bpCustMemberService.get(plMmOrderAssignInterest.getInvestPersonId());
						String ammont=plMmOrderAssignInterest.getIncomeMoney().toString();
						if(customer.getThirdPayFlagId()!=null){//表示没有开通第三方支付
							CommonRequst commonRequest = new CommonRequst();
							commonRequest.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方标识
							commonRequest.setRequsetNo(requestNo);//请求流水号
							commonRequest.setAmount(new BigDecimal(ammont));//交易金额
							commonRequest.setCustMemberType("0");
							commonRequest.setTransferName(ThirdPayConstants.TN_SEDRED);
							commonRequest.setBussinessType(ThirdPayConstants.BT_SEDRED);
							commonRequest.setBidId(plMmOrderAssignInterest.getAssignInterestId().toString());
							if(customer.getCustomerType()!=null&&customer.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
								commonRequest.setAccountType(1);
							}else{//借款人是个人
								commonRequest.setAccountType(0);
							}
							CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
							if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
								set[0]=Constants.CODE_SUCCESS;
								set[1]=commonResponse.getResponseMsg();
							}else{
								set[0]=Constants.CODE_FAILED;
								set[1]=commonResponse.getResponseMsg();
							}
						}else{
							set[0]=Constants.CODE_FAILED;
							set[1]="红包派发失败：投资人尚未注册第三方支付账户";
						}
						
						if(set!=null&&set[0].equals(Constants.CODE_SUCCESS)){
							Map<String,Object> mapL=new HashMap<String,Object>();
							mapL.put("investPersonId",customer.getId());//投资人Id（必填）
							mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
							mapL.put("transferType",ObAccountDealInfo.T_EXPERIENCE);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
							mapL.put("money",plMmOrderAssignInterest.getIncomeMoney());//交易金额	（必填）			 
							mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
							mapL.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
							mapL.put("recordNumber",requestNo);//交易流水号（必填）
							mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
							obAccountDealInfoService.operateAcountInfo(mapL);
							assignCount++;
							
							plMmOrderAssignInterest.setAfterMoney(plMmOrderAssignInterest.getIncomeMoney());
							plMmOrderAssignInterest.setFactDate(new Date());
							plMmOrderAssignInterestService.save(plMmOrderAssignInterest);
							
							//派息成功发送短信
							Map<String, String> map = new HashMap<String, String>();
							map.put("telephone", customer.getTelphone());
							map.put("${name}", customer.getTruename());
							map.put("${investPrincipal}", new BigDecimal(0.00).toString());
							map.put("${investInterest}", plMmOrderAssignInterest.getAfterMoney().toString());
							map.put("${projName}", plMmOrderAssignInterest.getMmName());
							map.put("${payintentPeriod}", plMmOrderAssignInterest.getPeriods().toString());
							map.put("key","sms_paymentRemind");
							smsSendService.smsSending(map);
							
							//smsSendUtil.sms_paymentRemind(customer.getTelphone(), customer.getTruename(), 
							//		new BigDecimal(0.00).toString(), plMmOrderAssignInterest.getAfterMoney().toString(), plMmOrderAssignInterest.getMmName(),plMmOrderAssignInterest.getPeriods().toString());
						}
					}
					List<PlMmOrderAssignInterest> plList= plMmOrderAssignInterestService.getByDealCondition(plMmOrderAssignInterest.getOrderId(),plMmOrderAssignInterest.getInvestPersonId());
					if(plList!=null&&plList.size()>0){
						
					}else{//派息成功，修改购买记录状态
						PlManageMoneyPlanBuyinfo info=plManageMoneyPlanBuyinfoSevice.get(plMmOrderAssignInterest.getOrderId());
						info.setState(Short.valueOf("10"));
						plManageMoneyPlanBuyinfoSevice.save(info);
					}
				}
			    setJsonString("{success:true,msg:'本次需派息记录个数为:"+(orderlist!=null?orderlist.size():0)+",成功派息个数为："+assignCount+"'}");
			}else{
				setJsonString("{success:true,msg:'没有派息记录'}");
			}
			setJsonString("{success:true,msg:'"+set[1]+"'}");
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:true,msg:'系统报错'}");
			return SUCCESS;
		}
	}
	
		private String[] doReadyDistribute(BpCustMember b,BpCustRedMember bpCustRedMember) {
			String[] str=new String[2];
			String thirdPayType=configMap.get("thirdPayType").toString();
			String thirdPayConfig=configMap.get("thirdPayConfig").toString();
			ObSystemAccount account=obSystemAccountService.getByInvrstPersonIdAndType(b.getId(), ObSystemAccount.type0);
			if(null!=bpCustRedMember.getRedMoney()&&bpCustRedMember.getRedMoney().compareTo(new BigDecimal("0"))==1){
				if(account!=null){
					//订单编号（交易流水号）
					String requestNo=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
					if(thirdPayType.equals("0")){//托管账户
						if(b.getThirdPayFlag0()!=null&&b.getThirdPayFlagId()!=null){//表示没有开通第三方支付
							if(thirdPayConfig.equals(Constants.YEEPAY)){
								String ammont=bpCustRedMember.getRedMoney().toString();
								/**(18)平台划款
								 * Map<String,Object> map  第三方支付取消投标需要的map参数
								 * map.get("basePath").toString() 只当前的绝对路径
								 * map.get("platformUserNo").toString() 第三方支付账号
								 * map.get("customerId").toString();
								 * map.get("customerType").toString();
								 * map.get("requestNo").toString()交易流水号
								 * map.get("money");划款金额
								 * @return
								 */
								Map<String,Object> map=new HashMap<String,Object>();
								map.put("basePath", this.getBasePath());
								map.put("platformUserNo", b.getThirdPayFlag0());
								map.put("customerId", b.getId().toString());
								map.put("customerType", "0");
								map.put("requestNo", requestNo);
								map.put("money", ammont);
								String[]  rett=yeePayService.PLATFORM_TRANSFER(map);
								str=rett;
							}else{
								str[0]=Constants.CODE_FAILED;
								str[1]="红包派发失败：尚不支持此家第三方支付类型";
							}
						}else{
							str[0]=Constants.CODE_FAILED;
							str[1]="红包派发失败：投资人尚未注册第三方支付账户";
						}
					}else{//大账户
						str[0]=Constants.CODE_SUCCESS;
						str[1]="红包派发";
					}
					
					if(str[0].equals(Constants.CODE_SUCCESS)){
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("investPersonId",b.getId());//投资人Id（必填）
						map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						map.put("transferType",ObAccountDealInfo.T_REDENVELOPE);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						map.put("money",bpCustRedMember.getRedMoney());//交易金额	（必填）			 
						map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						map.put("recordNumber",requestNo);//交易流水号	（必填）
						map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						obAccountDealInfoService.operateAcountInfo(map);
						//String [] obDealInfo=obAccountDealInfoService.operateAcountInfo(b.toString(), ObAccountDealInfo.T_REDENVELOPE.toString(), bpCustRedMember.getRedMoney().toString(), "3", "0", "1", "2", requestNo);
					}
					

					
				}else{
					str[0]=Constants.CODE_FAILED;
					str[1]="红包派发失败：投资人尚未开通系统账户";
				}
			}else{
				str[0]=Constants.CODE_FAILED;
				str[1]="红包派发成功：派发金额为0";
			}
		return str;
	}
		
		
		/**
		 * 显示列表
		 */
		public String listAssigninterest(){
			PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlMmOrderAssignInterest> list= plMmOrderAssignInterestService.getAllInterest(pb,map);
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
			return SUCCESS;
		}
	
		/**
		 * 优惠券派发奖励
		 * @param plList
		 */
		public void checkCoupons(PlMmOrderAssignInterest orderInterest){
				PlManageMoneyPlan bidplan = plManageMoneyPlanService.get(orderInterest.getMmplanId());
				PlManageMoneyPlanBuyinfo buy = plManageMoneyPlanBuyinfoSevice.get(orderInterest.getOrderId());
				//判断此标是否可用优惠券
				if(bidplan.getCoupon()!=null&&bidplan.getCoupon().compareTo(1)==0){
					//判断返利方式是否是 随期或者到期
					if(bidplan.getRebateWay().compareTo(2)==0||bidplan.getRebateWay().compareTo(3)==0){
						List<PlMmOrderAssignInterest> bpfundInterestList=null;//利息
						List<PlMmOrderAssignInterest> bpfundPrincipalList=null;//本金
						String transferType="";//资金类型
						boolean checkWay=false;
						if(bidplan.getRebateWay().compareTo(2)==0){//随期
							checkWay=true;
						}else if(bidplan.getRebateWay().compareTo(3)==0){
							int per = Integer.valueOf(orderInterest.getPeriods())+1;
							QueryFilter filter = new QueryFilter(this.getRequest());
							filter.addFilter("Q_assignInterestId_L_EQ", orderInterest.getAssignInterestId());
							filter.addFilter("Q_periods_N_EQ", per);
							List<PlMmOrderAssignInterest> checkFund = plMmOrderAssignInterestService.getAll(filter);
							if(checkFund.size()==0){//表示当前期数是最后一期还款
								checkWay=true;
							}
						}
						//判断是否使用了优惠券，派发金额
						if(checkWay){
							//判断 返利类型
							if(bidplan.getRebateType().compareTo(1)==0){//返现 principalCoupons
								transferType=ObAccountDealInfo.T_BIDRETURN_RETURNRATIO;
								bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(orderInterest.getOrderId(), orderInterest.getInvestPersonId(), bidplan.getMmplanId(), "'principalCoupons'",orderInterest.getPeriods());
							}else if(bidplan.getRebateType().compareTo(2)==0){//返息 couponInterest
								transferType=ObAccountDealInfo.T_BIDRETURN_RATE27;
								bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(orderInterest.getOrderId(), orderInterest.getInvestPersonId(), bidplan.getMmplanId(), "'couponInterest'",orderInterest.getPeriods());
							}else if(bidplan.getRebateType().compareTo(3)==0){//返息现  principalCoupons couponInterest
								transferType=ObAccountDealInfo.T_BIDRETURN_RATE29;
								bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(orderInterest.getOrderId(), orderInterest.getInvestPersonId(), bidplan.getMmplanId(), "'couponInterest'",orderInterest.getPeriods());
								bpfundPrincipalList = plMmOrderAssignInterestService.getByPlanIdA(orderInterest.getOrderId(), orderInterest.getInvestPersonId(), bidplan.getMmplanId(), "'principalCoupons'",orderInterest.getPeriods());
							}else if(bidplan.getRebateType().compareTo(4)==0){//加息 couponInterest
								transferType=ObAccountDealInfo.T_BIDRETURN_RATE30;
								bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(orderInterest.getOrderId(), orderInterest.getInvestPersonId(), bidplan.getMmplanId(), "'subjoinInterest'",orderInterest.getPeriods());
							}
						}
						if(bpfundInterestList!=null){//返利息
							couponIntent(bpfundInterestList,buy,bidplan,transferType);
						}
						if(bpfundPrincipalList!=null){//返本金
							couponIntent(bpfundPrincipalList,buy,bidplan,ObAccountDealInfo.T_BIDRETURN_RATE28);
						}
					}
				}else{
					//判断此标是否设置普通加息
					//普通加息日化利率，判断是否是立还
					if(bidplan.getAddRate()!=null&&!bidplan.getAddRate().equals("")&&bidplan.getAddRate().compareTo(new BigDecimal(0))!=0){
						if(bidplan.getRebateWay().compareTo(2)==0){//随期
							List<PlMmOrderAssignInterest> bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(orderInterest.getOrderId(), orderInterest.getInvestPersonId(), bidplan.getMmplanId(), "'commoninterest'",orderInterest.getPeriods());
							couponIntent(bpfundInterestList,buy,bidplan,ObAccountDealInfo.T_BIDRETURN_ADDRATE);
						}else if(bidplan.getRebateWay().compareTo(3)==0){//到期
							int per = Integer.valueOf(orderInterest.getPeriods())+1;
							QueryFilter filter = new QueryFilter(this.getRequest());
							filter.addFilter("Q_assignInterestId_L_EQ", orderInterest.getAssignInterestId());
							filter.addFilter("Q_periods_N_EQ", per);
							List<PlMmOrderAssignInterest> checkFund = plMmOrderAssignInterestService.getAll(filter);
							if(checkFund.size()==0){
								List<PlMmOrderAssignInterest> bpfundInterestList = plMmOrderAssignInterestService.getByPlanIdA(orderInterest.getOrderId(), orderInterest.getInvestPersonId(), bidplan.getMmplanId(), "'commoninterest'",orderInterest.getPeriods());
								couponIntent(bpfundInterestList,buy,bidplan,ObAccountDealInfo.T_BIDRETURN_ADDRATE);
							}
						}
						
					}
				}
			
		}
		/**
		 * 派发优惠券奖励
		 * @param bp
		 * @param info
		 */
		private void couponIntent(List<PlMmOrderAssignInterest> bp,PlManageMoneyPlanBuyinfo bidInfo1,PlManageMoneyPlan bidplan,String transferType){
			for(PlMmOrderAssignInterest bpfund:bp){
				if(bpfund.getFactDate()==null||bpfund.getFactDate().equals("")){
					BpCustMember mem=bpCustMemberService.get(bpfund.getInvestPersonId());
					String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
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
					commonRequest.setBussinessType(ThirdPayConstants.BT_COUPONAWARD);//业务类型
					commonRequest.setTransferName(ThirdPayConstants.TN_COUPONAWARD);//业务名称
					
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
		/**
		 * 理财计划 平台台账派息
		 */
		public  String distributeMoney(){
			String planId = this.getRequest().getParameter("planId");
			String fundType = this.getRequest().getParameter("fundType");
			String periods = this.getRequest().getParameter("periods");
			String transferType="";
			boolean coupons=false;
			try {
			List<PlMmOrderAssignInterest> list = plMmOrderAssignInterestService.getdistributeMoneyAssign(Long.valueOf(planId), Long.valueOf(periods), fundType);
			if(list.size()>0){
				for(PlMmOrderAssignInterest bpfund:list){
					if(bpfund.getFactDate()==null||bpfund.getFactDate().equals("")){
						BpCustMember mem=null;
						mem=bpCustMemberService.get(bpfund.getInvestPersonId());
						String requestNo=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
						CommonRequst commonRequest = new CommonRequst();
						commonRequest.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方标识
						commonRequest.setRequsetNo(requestNo);//请求流水号
						commonRequest.setAmount(bpfund.getIncomeMoney());//交易金额
						commonRequest.setCustMemberId(mem.getId().toString());
						commonRequest.setCustMemberType("0");
						commonRequest.setTransferName(ThirdPayConstants.TN_COUPONAWARD);
						commonRequest.setBussinessType(ThirdPayConstants.BT_COUPONAWARD);
						CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
						if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
							if(bpfund.getFundType().equals("raiseinterest")){
								transferType=ObAccountDealInfo.T_BIDRETURN_RATE31;
							}else if(bpfund.getFundType().equals("commoninterest")){
								transferType=ObAccountDealInfo.T_BIDRETURN_ADDRATE;
							}else if(bpfund.getFundType().equals("couponInterest")){
								transferType=ObAccountDealInfo.T_BIDRETURN_RATE29;
							}else if(bpfund.getFundType().equals("principalCoupons")){
								transferType=ObAccountDealInfo.T_BIDRETURN_RATE28;
							}
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
							
							///更新款项
							bpfund.setAfterMoney(bpfund.getIncomeMoney());
							bpfund.setFactDate(new Date());
							plMmOrderAssignInterestService.save(bpfund);
						}
					}
				}
				setJsonString("{success:true,msg:'派息成功'}");
			}else{
				setJsonString("{success:true,msg:'该项目未找到尚未派息的记录,奖励已经派发，不能重复派发'}");
			}
			} catch (Exception e) {
				e.printStackTrace();
				setJsonString("{success:true,msg:'派息失败'}");
			}
			return SUCCESS;
		}
		/**
		 * 优惠券，普通加息 ，募集期 奖励 台账
		 * @return
		 */
		public String getCouponsList(){
			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Long size = Long.valueOf("0");
			Date[] date = null;
			String searchaccount;
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestService.getCouponsList(pb, map);
			System.currentTimeMillis();
			List<PlMmOrderAssignInterest> sizelist=plMmOrderAssignInterestService.getCouponsList(null, map);
			
			size=Long.valueOf(sizelist.size());
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),
					new String[] { "intentDate" });
			json.transform(new DateTransformer("yyyy-MM-dd"),
					new String[] { "factDate" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
			System.currentTimeMillis();
			return SUCCESS;
		}
		/**
		 * 优惠券，普通加息 奖励 明细台账
		 * @return
		 */
		public String getCouponsDetailList(){
			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			String fundType = this.getRequest().getParameter("fundType");
			String planId = this.getRequest().getParameter("planId");
			String payintentPeriod = this.getRequest().getParameter("payintentPeriod");
			PagingBean pb = new PagingBean(start, limit);
			Long size = Long.valueOf("0");
			List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestService.getByPlanIdAndPeriod(Long.valueOf(planId),Long.valueOf(payintentPeriod), fundType);
			size=Long.valueOf(list.size());
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),
					new String[] { "intentDate" });
			json.transform(new DateTransformer("yyyy-MM-dd"),
					new String[] { "factDate" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
			System.currentTimeMillis();
			return SUCCESS;
		}
		/**
		 * 募集期奖励明细台账
		 * @return
		 */
		public String getRaiseDetail(){
			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
			String fundType = this.getRequest().getParameter("fundType");
			String planId = this.getRequest().getParameter("planId");
			PagingBean pb = new PagingBean(start, limit);
			Long size = Long.valueOf("0");
			List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestService.getRaiseBpfundIntent(Long.valueOf(planId));
			size=Long.valueOf(list.size());
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
			.append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),
					new String[] { "intentDate" });
			json.transform(new DateTransformer("yyyy-MM-dd"),
					new String[] { "factDate" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
			System.currentTimeMillis();
			return SUCCESS;
		}
		/**
		 * 导出优惠券台账
		 */
		public void getcouponsExcel(){

			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Long size = Long.valueOf("0");
			Date[] date = null;
			String searchaccount;
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestService.getCouponsList(pb, map);
			String excelName="";
			for(PlMmOrderAssignInterest sl:list){
				String fundType = this.getRequest().getParameter("fundType");
				if(!fundType.equals("commoninterest")){
					excelName="优惠券奖励台账";
					if(sl.getRebateType()!=null&&sl.getRebateType()!=null){
						if(sl.getRebateType().equals("1")){
							sl.setRebateTypeName("返现");
						}else if(sl.getRebateType().equals("2")){
							sl.setRebateTypeName("返息");
						}else if(sl.getRebateType().equals("3")){
							sl.setRebateTypeName("返息现");
						}else if(sl.getRebateType().equals("4")){
							sl.setRebateTypeName("加息");
						}
						if(sl.getRebateWay().equals("1")){
							sl.setRebateWayName("立返");
						}else if(sl.getRebateWay().equals("2")){
							sl.setRebateWayName("随期");
						}else if(sl.getRebateWay().equals("3")){
							sl.setRebateWayName("到期");
						}
					}
				}else{
					excelName="普通加息奖励台账";
					sl.setRebateTypeName("加息");
					sl.setRebateWayName("随期");
				}
				if(sl.getFundType().equals("principalCoupons")){
					sl.setFundTypeName("本金奖励");
				}else
				{
					sl.setFundTypeName("利息奖励");
				}
			}
			String[] tableHeader = {"序号", "理财计划名称", "理财计划编号", "返利类型","返利方式","还款期数" ,"奖励金额",
					"资金类型","计划奖励日期","实际奖励日期"};
			String[] fields = {"mmName","mmNumber","rebateTypeName","rebateWayName","periods","sumMoney",
					"fundTypeName","intentDate","factDate"};
			try {
				ExportExcel.export(tableHeader, fields, list,excelName, PlMmOrderAssignInterest.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 募集期奖励台账导出
		 */
		public void excelRaiseAssign(){
			
			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			PagingBean pb = new PagingBean(start, limit);
			Map<String, String> map = new HashMap<String, String>();
			Long size = Long.valueOf("0");
			Date[] date = null;
			String searchaccount;
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestService.getCouponsList(pb, map);
			System.currentTimeMillis();
			String excelName="";
			for(PlMmOrderAssignInterest sl:list){
				
					sl.setRebateTypeName("返息");
					sl.setRebateWayName("立返");
				
				if(sl.getFundType().equals("principalCoupons")){
					sl.setFundTypeName("本金奖励");
				}else
				{
					sl.setFundTypeName("利息奖励");
				}
			}
			String[] tableHeader = {"序号", "理财计划名称", "理财计划编号", "返利类型","返利方式","募集期利率" ,"奖励金额",
					"资金类型","计划奖励日期","实际奖励日期"};
			String[] fields = {"mmName","mmNumber","rebateTypeName","rebateWayName","raiseRate","sumMoney",
					"fundTypeName","intentDate","factDate"};
			try {
				ExportExcel.export(tableHeader, fields, list,"募集期奖励台账", PlMmOrderAssignInterest.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	public String getCouponsAssignIncome(){
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String fundType = this.getRequest().getParameter("fundType");
		String planId = this.getRequest().getParameter("planId");
		String payintentPeriod = this.getRequest().getParameter("payintentPeriod");
		PagingBean pb = new PagingBean(start, limit);
		Long size = Long.valueOf("0");
		List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestService.getCouponsAssignIncome(pb);
		size=Long.valueOf(list.size());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		System.currentTimeMillis();
		return SUCCESS;
	}
	public void excelCouponsAssignIncome(){
		List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestService.getCouponsAssignIncome(null);
		String[] tableHeader = {"序号", "投资人", "用户名", "优惠券收益奖励","普通加息收益奖励","累计收益奖励" ,"未收收益"};
		String[] fields = {"investPersonName","loginName","couponsIncome","addrateIncome","sumIncome","notIncome"};
		try {
			ExportExcel.export(tableHeader, fields, list,"理财优惠券收益查询", PlMmOrderAssignInterest.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示理财产品款项列表
	 */
	public String listByMmproduceAssigninterest(){
		PagingBean pb = new PagingBean(start, limit);
		
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlMmOrderAssignInterest> list= plMmOrderAssignInterestService.getAllByMmproduceInterest(pb,map);
		List<PlMmOrderAssignInterest> listCount= plMmOrderAssignInterestService.getAllByMmproduceInterest(null,map);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(listCount !=null ? listCount.size() : 0).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 派息返款
	 */
	public String backMoney(){
		try{
			String[] ids=getRequest().getParameterValues("ids");
			int assignCount=0;//成功派息记录
			if(ids!=null){
				for(String temp:ids){
					String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
					PlMmOrderAssignInterest plMmOrderAssignInterest=plMmOrderAssignInterestService.get(Long.valueOf(temp));
					plMmOrderAssignInterest.setInvestRequestNo(orderNum);
					plMmOrderAssignInterestService.save(plMmOrderAssignInterest);
					
					if(plMmOrderAssignInterest.getAfterMoney().compareTo(plMmOrderAssignInterest.getIncomeMoney())<0 
								&& plMmOrderAssignInterest.getLoanerRepayMentStatus() == 1){
						
						String[] set=new String[2];
						BpCustMember customer=bpCustMemberService.get(plMmOrderAssignInterest.getInvestPersonId());
						//注册账户返款
						CommonRequst commonRequest=new CommonRequst();
						commonRequest.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方支付账号
						commonRequest.setRequsetNo(orderNum);//请求流水号
						commonRequest.setBidId(plMmOrderAssignInterest.getMmplanId().toString());//标的Id
						commonRequest.setBussinessType(ThirdPayConstants.BT_MMBACKMONEY);//计划类型
						commonRequest.setTransferName(ThirdPayConstants.TN_MMBACKMONEY);//计划名称
						commonRequest.setPlanMoney(plMmOrderAssignInterest.getIncomeMoney());
						if(customer.getCustomerType() != null && customer.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
							commonRequest.setAccountType(1);
						}else{//借款人是个人
							commonRequest.setAccountType(0);
						}
						commonRequest.setAmount(plMmOrderAssignInterest.getIncomeMoney());
						CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
						
						if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
							Map<String,String> map=new HashMap<String,String>();
							map.put("requsetNo",orderNum);
							set = opraterBussinessDataService.backMoneyassigninterest(map);
						}
						if(set[0].equals(CommonResponse.RESPONSECODE_SUCCESS)){
							//派发优惠券奖励
							checkCoupons(plMmOrderAssignInterest);
							
							BpCustMember bm=bpCustMemberService.get(plMmOrderAssignInterest.getInvestPersonId());
							//派息成功发送短信
							//本金
							if(plMmOrderAssignInterest.getFundType().equals("principalRepayment")){
								//派息成功发送短信
								Map<String, String> map = new HashMap<String, String>();
								map.put("telephone", bm.getTelphone());
								map.put("${name}", bm.getTruename());
								map.put("${investPrincipal}", plMmOrderAssignInterest.getAfterMoney().toString());
								map.put("${investInterest}", new BigDecimal(0.00).toString());
								map.put("${projName}", plMmOrderAssignInterest.getMmName());
								map.put("${payintentPeriod}", plMmOrderAssignInterest.getPeriods().toString());
								map.put("key","sms_paymentRemind");
								smsSendService.smsSending(map);
								
								//smsSendUtil.sms_paymentRemind(bm.getTelphone(), bm.getTruename(),plMmOrderAssignInterest.getAfterMoney().toString(), new BigDecimal(0.00).toString(), plMmOrderAssignInterest.getMmName(),plMmOrderAssignInterest.getPeriods().toString());
							//利息与提前赎回违约金
							}else if(plMmOrderAssignInterest.getFundType().equals("loanInterest") 
									|| plMmOrderAssignInterest.getFundType().equals("liquidatedDamages")){
								//派息成功发送短信
								Map<String, String> map = new HashMap<String, String>();
								map.put("telephone", bm.getTelphone());
								map.put("${name}", bm.getTruename());
								map.put("${investPrincipal}", new BigDecimal(0.00).toString());
								map.put("${investInterest}", plMmOrderAssignInterest.getAfterMoney().toString());
								map.put("${projName}", plMmOrderAssignInterest.getMmName());
								map.put("${payintentPeriod}", plMmOrderAssignInterest.getPeriods().toString());
								map.put("key","sms_paymentRemind");
								smsSendService.smsSending(map);
								
								//smsSendUtil.sms_paymentRemind(bm.getTelphone(), bm.getTruename(),new BigDecimal(0.00).toString(), plMmOrderAssignInterest.getAfterMoney().toString(), plMmOrderAssignInterest.getMmName(),plMmOrderAssignInterest.getPeriods().toString());
							}
							
							assignCount++;
						}
						//处理当还完所有台账后，自动该变状态为已完成
						List<PlMmOrderAssignInterest> plList= plMmOrderAssignInterestService.getByDealCondition(plMmOrderAssignInterest.getOrderId(),plMmOrderAssignInterest.getInvestPersonId());
						if(plList!=null&&plList.size()>0){
							
						}else{
							PlManageMoneyPlanBuyinfo info=plManageMoneyPlanBuyinfoSevice.get(plMmOrderAssignInterest.getOrderId());
							info.setState(Short.valueOf("10"));
							plManageMoneyPlanBuyinfoSevice.save(info);
						}
					}
				}
				setJsonString("{success:true,msg:'本次需返款记录个数为:"+(ids!=null?ids.length:0)+",成功返款个数为："+assignCount+"'}");
			}else{
				setJsonString("{success:true,msg:'没有选择返款记录'}");
			}
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:true,msg:'系统报错'}");
			return SUCCESS;
		}
	}
	
	/**
	 * 根据订单id查询对应的款项台账
	 * @return
	 */
	public String findListByOrderId(){
		String orderId=this.getRequest().getParameter("orderId");
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addFilter("Q_orderId_L_EQ",orderId);
		filter.addFilter("Q_isValid_SN_EQ","0");
		filter.addFilter("Q_isCheck_SN_EQ","0");
		List<PlMmOrderAssignInterest> list= plMmOrderAssignInterestService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	
	public void setKeystr(String keystr) {
		this.keystr = keystr;
	}

	public String getKeystr() {
		return keystr;
	}
}
