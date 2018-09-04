package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.thirdPayInterface.CommonRequestInvestRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlEarlyRedemptionService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import com.zhiwei.credit.service.thirdInterface.OpraterBussinessDataService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.credit.util.Common;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PlEarlyRedemptionAction extends BaseAction{
	@Resource
	private PlEarlyRedemptionService plEarlyRedemptionService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private ProcessFormService processFormService;
	
	private CreditProjectService creditProjectService;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private OpraterBussinessDataService opraterBussinessDataService;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	
	private PlEarlyRedemption plEarlyRedemption;
	
	private Long earlyRedemptionId;
	private Long orderId;
	private Short checkStatus;
	private Short state;
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	
	public ProcessFormService getProcessFormService() {
		return processFormService;
	}

	public void setProcessFormService(ProcessFormService processFormService) {
		this.processFormService = processFormService;
	}


	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public Long getEarlyRedemptionId() {
		return earlyRedemptionId;
	}

	public void setEarlyRedemptionId(Long earlyRedemptionId) {
		this.earlyRedemptionId = earlyRedemptionId;
	}

	public PlEarlyRedemption getPlEarlyRedemption() {
		return plEarlyRedemption;
	}

	public void setPlEarlyRedemption(PlEarlyRedemption plEarlyRedemption) {
		this.plEarlyRedemption = plEarlyRedemption;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		List<PlEarlyRedemption> list= plEarlyRedemptionService.listByOrderId(orderId);
		StringBuffer buff = new StringBuffer("{success:true,result:");
		//Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
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
				plEarlyRedemptionService.remove(new Long(id));
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
		Map<String,Object> map=new HashMap<String,Object>();
		PlEarlyRedemption plEarlyRedemption=plEarlyRedemptionService.get(earlyRedemptionId);
		if(null!=plEarlyRedemption){
			map.put("plEarlyRedemption", plEarlyRedemption);
			PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getOrderId());
			map.put("plManageMoneyPlanBuyinfo", plManageMoneyPlanBuyinfo);
			PlManageMoneyPlan plManageMoneyPlan=plManageMoneyPlanBuyinfo.getPlManageMoneyPlan();
			map.put("plManageMoneyPlan", plManageMoneyPlan);
		}
		String taskId = this.getRequest().getParameter("slTaskId");
		if (null != taskId && !"".equals(taskId)) {
			ProcessForm pform = processFormService.getByTaskId(taskId);
			if (pform == null) {
				pform = creditProjectService.getCommentsByTaskId(taskId);
			}
			if (pform != null && pform.getComments() != null
					&& !"".equals(pform.getComments())) {
				map.put("comments", pform.getComments());
			}
		}
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(json.serialize(map));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	public String getInfo(){
		try{
			PlEarlyRedemption plEarlyRedemption=plEarlyRedemptionService.get(earlyRedemptionId);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("plEarlyRedemption", plEarlyRedemption);
			StringBuffer buff=new StringBuffer("{success:true,data:");
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(map));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plEarlyRedemption.getEarlyRedemptionId()==null){
			plEarlyRedemptionService.save(plEarlyRedemption);
		}else{
			PlEarlyRedemption orgPlEarlyRedemption=plEarlyRedemptionService.get(plEarlyRedemption.getEarlyRedemptionId());
			try{
				BeanUtil.copyNotNullProperties(orgPlEarlyRedemption, plEarlyRedemption);
				plEarlyRedemptionService.save(orgPlEarlyRedemption);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String startEarlyRedemptionProcess(){
		try{
			String str = plEarlyRedemptionService.startEarlyRedemptionProcess(orderId);
			jsonString="{success:true,"+str+"}";
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 提前赎回保存方法
	 */
	public String updateRedemptionInfo(){
		try{
			PlEarlyRedemption redemtion=plEarlyRedemptionService.get(plEarlyRedemption.getEarlyRedemptionId());
			BeanUtil.copyNotNullProperties(redemtion, plEarlyRedemption);
			plEarlyRedemptionService.merge(redemtion);
			PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoService.get(redemtion.getPlManageMoneyPlanBuyinfo().getOrderId());
			String fundsJson=this.getRequest().getParameter("fundsJson");
			if(null!=fundsJson && !fundsJson.equals("")){
				List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestService.listByEarlyRedemptionId(redemtion.getEarlyRedemptionId());
				for(PlMmOrderAssignInterest a:list){
					if(a.getAfterMoney().compareTo(new BigDecimal(0))==0){
						plMmOrderAssignInterestService.remove(a);
					}
				}
				String[] fundsJsonArr = fundsJson.split("@");
				
				for(int i=0; i<fundsJsonArr.length; i++) {
					String str = fundsJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					PlMmOrderAssignInterest plMmOrderAssignInterest = (PlMmOrderAssignInterest)JSONMapper.toJava(parser.nextValue(),PlMmOrderAssignInterest.class);
					plMmOrderAssignInterest.setAfterMoney(new BigDecimal(0));
					plMmOrderAssignInterest.setInvestPersonId(plManageMoneyPlanBuyinfo.getInvestPersonId());
					plMmOrderAssignInterest.setInvestPersonName(plManageMoneyPlanBuyinfo.getInvestPersonName());
					plMmOrderAssignInterest.setIsCheck(Short.valueOf("1"));
					plMmOrderAssignInterest.setIsValid(Short.valueOf("0"));
					plMmOrderAssignInterest.setKeystr("mmproduce");
					plMmOrderAssignInterest.setMmName(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmName());
					plMmOrderAssignInterest.setMmplanId(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmplanId());
					plMmOrderAssignInterestService.save(plMmOrderAssignInterest);
					
				}
			}
			//保存意见与说明
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String listbystate(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlEarlyRedemption> list= plEarlyRedemptionService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 提前退出
	 * @return
	 */
	public String updateState(){
//		Short state=Short.valueOf(this.getRequest().getParameter("state"));
		Short checkStatus=Short.valueOf(this.getRequest().getParameter("checkStatus"));
		plEarlyRedemption=plEarlyRedemptionService.get(earlyRedemptionId);
		PlManageMoneyPlanBuyinfo order=plEarlyRedemption.getPlManageMoneyPlanBuyinfo();
		plEarlyRedemption.setEarlyDate(new Date());
		//同意审核通过
		if(checkStatus.equals(Short.valueOf("1"))){
			if(!order.getState().equals(Short.valueOf("8"))){
				//结算派息，
				List<PlMmOrderAssignInterest> pailist=plMmOrderAssignInterestService.mmplancreateList(plEarlyRedemption);
			    //转账金额
				BigDecimal sumMoney=new BigDecimal("0");
				//結算的利息总额
				BigDecimal loanInterestMoney=new BigDecimal("0");
				for(PlMmOrderAssignInterest pi:pailist){
					if(pi.getFundType().equals("liquidatedDamages")){
						sumMoney=sumMoney.subtract(pi.getPayMoney());
					}else if(pi.getFundType().equals("loanInterest")){ 
						sumMoney=sumMoney.add(pi.getIncomeMoney());
						loanInterestMoney=loanInterestMoney.add(pi.getIncomeMoney());
						
					}else if(pi.getFundType().equals("principalRepayment")){ 
						sumMoney=sumMoney.add(pi.getIncomeMoney());
					}
				}
				if(sumMoney.compareTo(new BigDecimal(0))>0){
					CommonResponse cr = this.doEarlyRepayment(sumMoney,loanInterestMoney,order);
					String[] set=new String[2];
					//第三方成功
					if(CommonResponse.RESPONSECODE_SUCCESS.equals(cr.getResponsecode())){
						
						plEarlyRedemption.setLoanerRequestNo(cr.getCommonRequst().getRequsetNo());
						plEarlyRedemption.setRealTranseactionAmount(sumMoney);
						plEarlyRedemptionService.save(plEarlyRedemption);
						
						Map<String,String> map=new HashMap<String,String>();
						map.put("requsetNo",cr.getCommonRequst().getRequsetNo());
						map.put("sumMoney",sumMoney.toString());
						map.put("earlyRedemptionId",plEarlyRedemption.getEarlyRedemptionId().toString());
						set = opraterBussinessDataService.earlyEarlyRepayment(map);
						
						if(set[0].equals(CommonResponse.RESPONSECODE_SUCCESS)){
							//把之后的派息全部划掉
							plMmOrderAssignInterestService.mmplanupdateList(plEarlyRedemption);
							//与债权的匹配日最大不能超过提前支取日
							plManageMoneyPlanBuyinfoService.gcalculateEarlyOutOrmatching(plEarlyRedemption);
							
							plEarlyRedemption.setCheckStatus(Short.valueOf("1"));
							plEarlyRedemptionService.save(plEarlyRedemption);
							
							order.setState(Short.valueOf("8"));
							order.setIsAtuoMatch(1);//修改订单为托管,定时器才会扫到该条记录并释放债权。
							plManageMoneyPlanBuyinfoService.save(order);
							//判断理财计划是否使用了优惠券。把未派发的奖励台账设置为无效的
							if(order.getCouponId()!=null&&!order.equals("")){
								List<PlMmOrderAssignInterest>	assign = plMmOrderAssignInterestService.getByPlanIdA(order.getOrderId(), order.getInvestPersonId(), order.getPlManageMoneyPlan().getMmplanId(), null,null);
								for(PlMmOrderAssignInterest pl:assign){
									pl.setIsValid(Short.valueOf("1"));
									pl.setIsCheck(Short.valueOf("1"));
									plMmOrderAssignInterestService.save(pl);
								}
							}
							/*//发送短信提醒
							SmsSendUtil smsSendUtil= new SmsSendUtil();
							BpCustMember member = bpCustMemberService.get(order.getInvestPersonId());
							PlManageMoneyPlan plan = order.getPlManageMoneyPlan();
							smsSendUtil.sms_plEarlyRedemption(member.getTelphone().toString(), member.getLoginname()
									, plan.getMmName(), plan.getMmNumber(), sumMoney.toString(), "sms_redeemSuccess");*/
							
							setJsonString("{success:true,msg:'提前支取审核通过，已经支付了支取金额'}");
						}else{
							setJsonString("{success:true,msg:'提前支取审核通过，请进行返款操作'}");
						}
					
					}else{
						//TODO 处理异常情况      
						String tostring=(cr.getResponseMsg());
						setJsonString("{success:true,msg:'提前支取审核失败，原因："+tostring+"'}");
					}
				}else{
					//把之后的派息全部划掉
					plMmOrderAssignInterestService.mmplanupdateList(plEarlyRedemption);
					//与债权的匹配日最大不能超过提前支取日
					plManageMoneyPlanBuyinfoService.gcalculateEarlyOutOrmatching(plEarlyRedemption);
					
					for(PlMmOrderAssignInterest pi:pailist){
						plMmOrderAssignInterestService.save(pi);
					}
					
					plEarlyRedemption.setCheckStatus(Short.valueOf("1"));
					plEarlyRedemptionService.save(plEarlyRedemption);
					
					order.setState(Short.valueOf("8"));
					plManageMoneyPlanBuyinfoService.save(order);
					setJsonString("{success:true,msg:'提前支取审核通过，已经支付了支取金额'}");
				}
			}else{
				plEarlyRedemptionService.remove(plEarlyRedemption);
				setJsonString("{success:true,msg:'提前支取失败，已经支取过'}");
			}
		}else{
			if(!order.getState().equals(Short.valueOf("8"))){//驳回
				plEarlyRedemption.setCheckStatus(Short.valueOf("3"));
				plEarlyRedemptionService.save(plEarlyRedemption);
				order.setState(Short.valueOf("2"));
				plManageMoneyPlanBuyinfoService.save(order);
				
				/*//发送短信提醒
				SmsSendUtil smsSendUtil= new SmsSendUtil();
				BpCustMember member = bpCustMemberService.get(order.getInvestPersonId());
				PlManageMoneyPlan plan = order.getPlManageMoneyPlan();
				smsSendUtil.sms_plEarlyRedemption(member.getTelphone().toString(), member.getLoginname()
						, plan.getMmName(), plan.getMmNumber(), null, "sms_redeemFail");*/
				
				setJsonString("{success:true,msg:'提前支取审核不通过'}");
			
			}else{
				
				plEarlyRedemptionService.remove(plEarlyRedemption);
				setJsonString("{success:true,msg:'提前支取失败，已经支取过'}");
			}
			
		}
		
		return SUCCESS;
	
	}

	/**
	 * 理财计划提前退出
	 * @param sumMoney
	 * @param loanInterestMoney
	 * @param info
	 * @return
	 */
	private CommonResponse doEarlyRepayment(BigDecimal sumMoney,BigDecimal loanInterestMoney,PlManageMoneyPlanBuyinfo info) {
		// TODO Auto-generated method stub
		String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
		CommonResponse commonResponse = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try{
			if(AppUtil.getConfigMap().get("thirdPayType").toString().equals("0")){
				
				BpCustMember customer=bpCustMemberService.get(info.getInvestPersonId());
				BpCustMember moneyReciver=bpCustMemberService.getMemberUserName(info.getPlManageMoneyPlan().getMoneyReceiver());
					
				if("pt".equals(info.getPlManageMoneyPlan().getReceiverType())){
					CommonRequst commonRequest = new CommonRequst();
					commonRequest.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方标识
					commonRequest.setRequsetNo(orderNum);//请求流水号
					commonRequest.setAmount(sumMoney);//交易金额
					commonRequest.setCustMemberType("0");
					commonRequest.setBidId(info.getOrderId().toString());//标的Id
					commonRequest.setBussinessType(ThirdPayConstants.BT_MMSIGNOUTPLATF);//业务类型
					commonRequest.setTransferName(ThirdPayConstants.TN_MMSIGNOUTPLATF);//业务名称
					commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
				}else{
					List<CommonRequestInvestRecord> repayList =new ArrayList<CommonRequestInvestRecord>();
					CommonRequestInvestRecord cir = new CommonRequestInvestRecord();
					cir.setInvest_thirdPayConfigId(customer.getThirdPayFlagId());
					cir.setBidRequestNo(info.getDealInfoNumber());
					cir.setAmount(sumMoney);
					cir.setFee(new BigDecimal("0"));
					repayList.add(cir);
					
					CommonRequst commonRequest=new CommonRequst();
					commonRequest.setThirdPayConfigId(moneyReciver.getThirdPayFlagId());//用户第三方支付账号
					commonRequest.setInvest_thirdPayConfigId(customer.getThirdPayFlagId());
					commonRequest.setRequsetNo(orderNum);//请求流水号
					commonRequest.setBidId(info.getPlManageMoneyPlan().getMmplanId().toString());//标的Id
					commonRequest.setBussinessType(ThirdPayConstants.BT_MMSIGNOUTUSER);//计划类型
					commonRequest.setTransferName(ThirdPayConstants.TN_MMSIGNOUTUSER);//计划名称
					commonRequest.setRepaymemntList(repayList);
					commonRequest.setPlanMoney(sumMoney);
					commonRequest.setSubOrdId(info.getDealInfoNumber());//投标时的流水号
					commonRequest.setSubOrdDate(sdf.format(info.getBuyDatetime()));//投标日期
					commonRequest.setFundType("plearly");
					if(moneyReciver.getCustomerType()!=null&&moneyReciver.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
						commonRequest.setAccountType(1);
					}else{//借款人是个人
						commonRequest.setAccountType(0);
					}
					commonRequest.setRetInterest(loanInterestMoney.setScale(2).toString());
					commonRequest.setAmount(sumMoney);
					commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
					
					if(CommonResponse.RESPONSECODE_SUCCESS.equals(commonResponse.getResponsecode())){
						Map<String,Object> mapL=new HashMap<String,Object>();
						mapL.put("investPersonId",moneyReciver.getId());//投资人Id（必填）
						mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						mapL.put("transferType",ObAccountDealInfo.T_LOANPAY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						mapL.put("money",sumMoney);//交易金额	（必填）			 
						mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						mapL.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						mapL.put("recordNumber",commonResponse.getCommonRequst().getRequsetNo());//交易流水号	（必填）
						mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						obAccountDealInfoService.operateAcountInfo(mapL);
					}else if(CommonResponse.RESPONSECODE_FREEZE.equals(commonResponse.getResponsecode())){
						//提前退出申请成功。继续调用审核接口。
						String requestNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
						CommonRequst request = new CommonRequst();
						request.setRequsetNo(requestNum);
						request.setLoanNoList(orderNum);//审核流水号，还款时的流少号
						request.setConfimStatus(false);
						request.setBussinessType(ThirdPayConstants.BT_USEALLAUDIT);//业务类型
						request.setTransferName(ThirdPayConstants.TN_USEALLAUDIT);//名称
						CommonResponse response=ThirdPayInterfaceUtil.thirdCommon(request);
						if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
							Map<String,Object> mapL=new HashMap<String,Object>();
							mapL.put("investPersonId",moneyReciver.getId());//投资人Id（必填）
							mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
							mapL.put("transferType",ObAccountDealInfo.T_LOANPAY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
							mapL.put("money",sumMoney);//交易金额	（必填）			 
							mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
							mapL.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
							mapL.put("recordNumber",requestNum);//交易流水号	（必填）
							mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
							obAccountDealInfoService.operateAcountInfo(mapL);
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						}
					}
				}
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
				commonResponse.setResponseMsg("资金池模式可以直接派息");
			}
			return commonResponse;
		}catch(Exception e){
			e.printStackTrace();
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			commonResponse.setResponseMsg("对接第三方出错，请联系管理员");
			return commonResponse;
		}
	}

	public String gcalculateEarlyOut() {
		plEarlyRedemption=plEarlyRedemptionService.get(earlyRedemptionId);
		List<PlMmOrderAssignInterest> pailist=plMmOrderAssignInterestService.mmplancreateList(plEarlyRedemption);
	    //转账金额
		//提前支取违约金
		BigDecimal liquidatedDamagesMoney=new BigDecimal("0");
		//欠派利息
		BigDecimal loanInterestMoney=new BigDecimal("0");
		//本金
		BigDecimal principalRepaymenMoney=new BigDecimal("0");
		for(PlMmOrderAssignInterest pi:pailist){
			if(pi.getFundType().equals("liquidatedDamages")){
				liquidatedDamagesMoney=liquidatedDamagesMoney.subtract(pi.getPayMoney());
			}else if(pi.getFundType().equals("loanInterest")){ 
				loanInterestMoney=loanInterestMoney.add(pi.getIncomeMoney());
			}else if(pi.getFundType().equals("principalRepayment")){ 
				principalRepaymenMoney=principalRepaymenMoney.add(pi.getIncomeMoney());
			}
		}
		//结算金额
		BigDecimal sumMoney=loanInterestMoney.add(liquidatedDamagesMoney).add(principalRepaymenMoney);
		
		setJsonString("{success:true,liquidatedDamagesMoney:"+liquidatedDamagesMoney+",loanInterestMoney:"+loanInterestMoney
				+",principalRepaymenMoney:"+principalRepaymenMoney+",sumMoney:"+sumMoney+"}");
		
		return SUCCESS;
	}
	
	/**
	 * 提前赎回返款
	 * @return
	 */
	public String earlyBackMoney() {
		String earlyRedemptionId=this.getRequest().getParameter("earlyRedemptionId");
		String orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
		if(null != earlyRedemptionId && !"".equals(earlyRedemptionId)){
			PlEarlyRedemption plEarlyRedemption = plEarlyRedemptionService.get(Long.valueOf(earlyRedemptionId));
			PlManageMoneyPlanBuyinfo order=plEarlyRedemption.getPlManageMoneyPlanBuyinfo();
			plEarlyRedemption.setInvestRequestNo(orderNum);
			
			BpCustMember customer=bpCustMemberService.get(plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getInvestPersonId());
			//注册账户返款
			CommonRequst commonRequest=new CommonRequst();
			commonRequest.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方支付账号
			commonRequest.setRequsetNo(orderNum);//请求流水号
			commonRequest.setBidId(plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getPlManageMoneyPlan().getMmplanId().toString());//标的Id
			commonRequest.setBussinessType(ThirdPayConstants.BT_MMBACKMONEY);//计划类型
			commonRequest.setTransferName(ThirdPayConstants.TN_MMBACKMONEY);//计划名称
			if(customer.getCustomerType() != null && customer.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
				commonRequest.setAccountType(1);
			}else{//借款人是个人
				commonRequest.setAccountType(0);
			}
			commonRequest.setAmount(plEarlyRedemption.getRealTranseactionAmount());
			CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
			String[] set=new String[2];
			if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				Map<String,String> map=new HashMap<String,String>();
				map.put("requsetNo",orderNum);
				map.put("earlyRedemptionId",plEarlyRedemption.getEarlyRedemptionId().toString());
				set = opraterBussinessDataService.earlyBackMoney(map);
			}
			if(set[0].equals(CommonResponse.RESPONSECODE_SUCCESS)){
				//把之后的派息全部划掉
				plMmOrderAssignInterestService.mmplanupdateList(plEarlyRedemption);
				//与债权的匹配日最大不能超过提前支取日
				plManageMoneyPlanBuyinfoService.gcalculateEarlyOutOrmatching(plEarlyRedemption);
				
				plEarlyRedemption.setCheckStatus(Short.valueOf("1"));
				plEarlyRedemptionService.save(plEarlyRedemption);
				
				order.setState(Short.valueOf("8"));
				plManageMoneyPlanBuyinfoService.save(order);
				//判断理财计划是否使用了优惠券。把未派发的奖励台账设置为无效的
				if(order.getCouponId()!=null&&!order.equals("")){
					List<PlMmOrderAssignInterest>	assign = plMmOrderAssignInterestService.getByPlanIdA(order.getOrderId(), order.getInvestPersonId(), order.getPlManageMoneyPlan().getMmplanId(), null,null);
					for(PlMmOrderAssignInterest pl:assign){
						pl.setIsValid(Short.valueOf("1"));
						pl.setIsCheck(Short.valueOf("1"));
						plMmOrderAssignInterestService.save(pl);
					}
				}
				setJsonString("{success:true,msg:'提前支取审核通过，已经支付了支取金额'}");
			}
			
		}
		return SUCCESS;
	}
	
}
