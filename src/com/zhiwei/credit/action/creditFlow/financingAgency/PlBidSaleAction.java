package com.zhiwei.credit.action.creditFlow.financingAgency;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hurong.credit.util.Common;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidSale;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidSaleService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class PlBidSaleAction extends BaseAction{
	@Resource
	private PlBidSaleService plBidSaleService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	private PlBidSale plBidSale;
	private Long id;
	private String listtype;
	public String getListtype() {
		return listtype;
	}

	public void setListtype(String listtype) {
		this.listtype = listtype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public PlBidSale getPlBidSale() {
		return plBidSale;
	}

	public void setPlBidSale(PlBidSale plBidSale) {
		this.plBidSale = plBidSale;
	}

    public List<PlBidSale> common(Map<String, String> map,QueryFilter filter){
	    List<PlBidSale> list = new ArrayList<PlBidSale>();
	    if(listtype.equals("transferingList")){
			 list= plBidSaleService.transferingList(null,filter.getPagingBean(), map);
		}else if(listtype.equals("transferedList")){//交易成功债权查询（只要是交易成功的都查）
			 map.put("saleStatus", "4");//交易成功
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		}else if(listtype.equals("preTransferFeeStatus1")){//确认实收手续费（只查询交易成功但没有确认实收手续费的）
			 map.put("saleStatus", "4");//交易成功
			 map.put("preTransferFeeStatus", "1,7");
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		}else if(listtype.equals("preTransferFeeStatus2")){////交易费用列表查询
			 map.put("saleStatus", "4");//交易成功
			 map.put("preTransferFeeStatus", "2");
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		}else if(listtype.equals("closeedList")){
			 map.put("saleStatus", "9,10");//手动关闭
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		}else if(listtype.equals("ingTransferList")){
			 map.put("saleStatus", "3");//正在交易中的
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		}else if(listtype.equals("transfereToOrList")){
			 map.put("saleStatus", "7");//转到债权中的
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		}else if(listtype.equals("transferFianceList")){
			 map.put("saleStatus", "1,4");//挂牌成功和交易成功   
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		 }else if(listtype.equals("returnTransfered")){
			 map.put("saleStatus", "4");//交易成功   返款
			 map.put("returnStatus", "1");
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		 }else if(listtype.equals("all")){
			 map.put("saleStatus", "1,3,4,7,9,10");//查询全部
			 list= plBidSaleService.outTransferingList(null,filter.getPagingBean(), map);
		 }
	   return list;
    }
   
	public String list(){
		Map<String, String> map=new HashMap<String,String>();
		QueryFilter filter = new QueryFilter(getRequest());
		Enumeration<?> paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlBidSale> list=common(map,filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("saleStartTime", "saleCloseTime", "saleDealTime", "saleSuccessTime","intentDate");
		serializer.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
		buff.append(serializer.exclude(new String[] { "class" }).serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
     }
 
	/**
	 * 取消债权交易挂牌
	 */
	public String update(){
		PlBidSale orgPlBidSale=plBidSaleService.get(id);
		String[] ret=new String[3];
		if(orgPlBidSale!=null){
			if(orgPlBidSale.getSaleStatus().equals(Short.valueOf("1"))){
				BpCustMember mem = bpCustMemberService.get(orgPlBidSale.getOutCustID());
				String orderNo = ContextUtil.createRuestNumber();
				CommonResponse cr=new CommonResponse();
				CommonRequst cq=new CommonRequst();
				cq.setThirdPayConfigId(mem.getThirdPayFlagId());//用户支付账号
				cq.setConfimStatus(false);//false表示退回 true表示通过
				cq.setLoanNoList(orgPlBidSale.getPreAuthorizationNum());//挂牌时双乾返回的流水号
				cq.setTransferName(ThirdPayConstants.TN_CANCELDEAL);
				cq.setBussinessType(ThirdPayConstants.BT_CANCELDEAL);
				cq.setQueryRequsetNo(orgPlBidSale.getPreTransferFeeRequestNo());//挂牌时的流水号
				cq.setRequsetNo(orderNo);//请求流水号
				cq.setAmount(orgPlBidSale.getPreTransferFee());
				cq.setBidId(orgPlBidSale.getId().toString());
				cr=ThirdPayInterfaceUtil.thirdCommon(cq);
				if(cr.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					orgPlBidSale.setSaleStatus(Short.valueOf("9"));
					orgPlBidSale.setSaleCloseTime(new Date());
					plBidSaleService.save(orgPlBidSale);
					ObAccountDealInfo deal = obAccountDealInfoService.getByOrderNumber(orgPlBidSale.getPreTransferFeeRequestNo(),
							"", ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL, "0");
					if(deal!=null&&deal.getDealRecordStatus().toString().equals("7")){
						Map<String,Object> mapI=new HashMap<String,Object>();
						mapI.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
						mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						mapI.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						mapI.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
						mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						mapI.put("recordNumber",orgPlBidSale.getPreTransferFeeRequestNo());//交易流水号	（必填）
						mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_3);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						System.out.println("购买债权人系统账户交易记录dealStatus=="+mapI.get("dealStatus"));
						obAccountDealInfoService.updateAcountInfo(mapI);
					}else{
						String orderNos = ContextUtil.createRuestNumber();
						Map<String,Object> mapI=new HashMap<String,Object>();
						mapI.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
						mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						mapI.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						mapI.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
						mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						mapI.put("recordNumber",orderNos);//交易流水号	（必填）
						mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						System.out.println("购买债权人系统账户交易记录dealStatus=="+mapI.get("dealStatus"));
						obAccountDealInfoService.operateAcountInfo(mapI);
					}
					
					setJsonString("{success:true,msg:'取消债权交易成功'}");
				}else{
					setJsonString("{success:true,msg:'取消债权交易失败'}");
				}
			}else{
				setJsonString("{success:true,msg:'债权不能取消挂牌，因为债权可能正在交易'}");
			}
		}else{
			setJsonString("{success:true,msg:'取消失败，没有找到挂牌记录'}");
		}
		
		return SUCCESS;
		
	}
  
	/**
    * 获取挂牌交易的信息，确认预收费用转实收费用  并完成退款
    * @return
    */
	public String get(){
		try{
			PlBidSale orgPlBidSale=plBidSaleService.getMoreinfoById(id);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			// 将数据转成JSON格式
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append(gson.toJson(orgPlBidSale));
			sb.append("}");
			setJsonString(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println();
		return SUCCESS;
	}
	/**
	 * 将预收转为实收
	 * @return
	 */
	public String pretransferFeeChangeFactTransfer(){
		PlBidSale orgPlBidSale=plBidSaleService.get(plBidSale.getId());
		if(orgPlBidSale!=null){
			if(orgPlBidSale.getSaleStatus().equals(Short.valueOf("4"))){//交易成功后将预收服务费转为实收服务费
				if(orgPlBidSale.getPreTransferFeeStatus()!=null&&orgPlBidSale.getPreTransferFeeStatus().equals(Short.valueOf("2"))){
					setJsonString("{success:true,msg:'数据已经处理过了，不允许重复提交'}");
				}else{
					//预收已经转了实收，但是实收费用要比预收费用小，故此要退费（预收费用 - 实收费用）（此方法一般情况下处理实收后执行退费报错后使用）
					if(orgPlBidSale.getPreTransferFeeStatus()!=null&&orgPlBidSale.getPreTransferFeeStatus().equals(Short.valueOf("1"))){//预收转了实收
						if(AppUtil.getConfigMap().get("thirdPayType").toString().trim().equals("0")){//托管支付
								if(orgPlBidSale.getTransferFee()!=null&&orgPlBidSale.getPreTransferFee().subtract(orgPlBidSale.getTransferFee()).compareTo(new BigDecimal(0))>0){
									String requestNo=ContextUtil.createRuestNumber();
									BpCustMember b=bpCustMemberService.get(orgPlBidSale.getOutCustID());
									CommonRequst commonRequest = new CommonRequst();
									commonRequest.setThirdPayConfigId(b.getThirdPayFlagId());//用户第三方标识
									commonRequest.setRequsetNo(requestNo);//请求流水号
									commonRequest.setAmount(orgPlBidSale.getPreTransferFee().subtract(orgPlBidSale.getTransferFee()));//交易金额
									commonRequest.setCustMemberType("0");
									commonRequest.setBidId(orgPlBidSale.getId().toString());//标的id
									commonRequest.setPlanMoney(orgPlBidSale.getSumMoney());//标的金额
									commonRequest.setTransferName(ThirdPayConstants.TN_FALSESERVICE);
									commonRequest.setBussinessType(ThirdPayConstants.BT_FALSESERVICE);
									CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
									if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
										orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("2"));
										plBidSaleService.save(orgPlBidSale);
										Map<String,Object> RebackMoney=new HashMap<String,Object>();
										RebackMoney.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
										RebackMoney.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
										RebackMoney.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
										RebackMoney.put("money",orgPlBidSale.getPreTransferFee().subtract(orgPlBidSale.getTransferFee()));//交易金额	（必填）			 
										RebackMoney.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
										RebackMoney.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
										RebackMoney.put("recordNumber",requestNo);//交易流水号	（必填）
										RebackMoney.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
										obAccountDealInfoService.operateAcountInfo(RebackMoney);
										setJsonString("{success:true,msg:'预收成功转实收并完成退款'}");
									}else{
										setJsonString("{success:true,msg:'预收成功转实收但退款失败:"+commonResponse.getResponseMsg()+"'}");
									}
									
								}else{
									orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("2"));
									plBidSaleService.save(orgPlBidSale);
									setJsonString("{success:true,msg:'预收成功转实收无退款'}");
								}
						}else{//资金池支付
							if(orgPlBidSale.getTransferFee()!=null&&orgPlBidSale.getPreTransferFee().subtract(orgPlBidSale.getTransferFee()).compareTo(new BigDecimal(0))>0){
								String requestNo=ContextUtil.createRuestNumber();
								BpCustMember b=bpCustMemberService.get(orgPlBidSale.getInCustID());
								orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("2"));
								plBidSaleService.save(orgPlBidSale);
								Map<String,Object> RebackMoney=new HashMap<String,Object>();
								RebackMoney.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
								RebackMoney.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
								RebackMoney.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
								RebackMoney.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
								RebackMoney.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
								RebackMoney.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
								RebackMoney.put("recordNumber",requestNo);//交易流水号	（必填）
								RebackMoney.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
								obAccountDealInfoService.operateAcountInfo(RebackMoney);
								setJsonString("{success:true,msg:'预收成功转实收并完成退款'}");
							}else{
								setJsonString("{success:true,msg:'预收成功转实收无退款'}");
							}
						}
					}else{//预收没有转实收（正常业务逻辑，先预收转实收，再退费）
						if(AppUtil.getConfigMap().get("thirdPayType").toString().trim().equals("0")){//托管支付
								String requestNo=ContextUtil.createRuestNumber();
								BpCustMember b=bpCustMemberService.get(orgPlBidSale.getOutCustID());
								CommonRequst cr = new CommonRequst();
								cr.setThirdPayConfigId(b.getThirdPayFlagId());//用户第三方标识
								cr.setRequsetNo(requestNo);//请求流水号
								cr.setQueryRequsetNo(orgPlBidSale.getPreTransferFeeRequestNo());//原流水号
								cr.setConfimStatus(true);
								cr.setAmount(orgPlBidSale.getPreTransferFee());
								cr.setLoanNoList(orgPlBidSale.getPreAuthorizationNum());//审核的流水号
								cr.setTransferName(ThirdPayConstants.TN_TRUESERVICE);//业务用途
								cr.setBussinessType(ThirdPayConstants.BT_TRUESERVICE);//业务类型
								CommonResponse crp=ThirdPayInterfaceUtil.thirdCommon(cr);
								if(crp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
									orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("1"));
									plBidSaleService.save(orgPlBidSale);
									Map<String,Object> mapI=new HashMap<String,Object>();
									mapI.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
									mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
									mapI.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
									mapI.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
									mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
									mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
									mapI.put("recordNumber",orgPlBidSale.getPreTransferFeeRequestNo());//交易流水号	（必填）
									mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
									System.out.println("购买债权人系统账户交易记录dealStatus=="+mapI.get("dealStatus"));
									obAccountDealInfoService.updateAcountInfo(mapI);
									if(orgPlBidSale.getPreTransferFee().subtract(orgPlBidSale.getTransferFee()).compareTo(new BigDecimal(0))>0){
										String requestNum=ContextUtil.createRuestNumber();
										CommonRequst commonRequest = new CommonRequst();
										commonRequest.setThirdPayConfigId(b.getThirdPayFlagId());//用户第三方标识
										commonRequest.setRequsetNo(requestNum);//请求流水号
										commonRequest.setAmount(orgPlBidSale.getPreTransferFee().subtract(orgPlBidSale.getTransferFee()));//交易金额
										commonRequest.setCustMemberType("0");
										commonRequest.setBidId(orgPlBidSale.getId().toString());//标的id
										commonRequest.setPlanMoney(orgPlBidSale.getSumMoney());//标的金额
										commonRequest.setTransferName(ThirdPayConstants.TN_FALSESERVICE);
										commonRequest.setBussinessType(ThirdPayConstants.BT_FALSESERVICE);
										CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
										if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
											orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("2"));
											plBidSaleService.save(orgPlBidSale);
											Map<String,Object> RebackMoney=new HashMap<String,Object>();
											RebackMoney.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
											RebackMoney.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
											RebackMoney.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
											RebackMoney.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
											RebackMoney.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
											RebackMoney.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
											RebackMoney.put("recordNumber",requestNo);//交易流水号	（必填）
											RebackMoney.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
											obAccountDealInfoService.operateAcountInfo(RebackMoney);
											setJsonString("{success:true,msg:'预收成功转实收并完成退款'}");
										}else{
											setJsonString("{success:true,msg:'预收成功转实收但退款失败'}");
										}
										
									}else{
										orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("2"));
										plBidSaleService.save(orgPlBidSale);
										setJsonString("{success:true,msg:'预收成功转实收无退款:"+crp.getResponseMsg()+"'}");
									}
									
								} else {
									setJsonString("{success:true,msg:'预收转实收失败:"+crp.getResponseMsg()+"'}");
								}
						}else{//资金池支付
							orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("1"));
							plBidSaleService.save(orgPlBidSale);
							Map<String,Object> mapI=new HashMap<String,Object>();
							mapI.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
							mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
							mapI.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
							mapI.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
							mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
							mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
							mapI.put("recordNumber",orgPlBidSale.getPreTransferFeeRequestNo());//交易流水号	（必填）
							mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
							System.out.println("购买债权人系统账户交易记录dealStatus=="+mapI.get("dealStatus"));
							obAccountDealInfoService.updateAcountInfo(mapI);
							if(orgPlBidSale.getTransferFee()!=null&&orgPlBidSale.getPreTransferFee().subtract(orgPlBidSale.getTransferFee()).compareTo(new BigDecimal(0))>0){
								String requestNo=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
								BpCustMember b=bpCustMemberService.get(orgPlBidSale.getInCustID());
								orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("2"));
								plBidSaleService.save(orgPlBidSale);
								Map<String,Object> RebackMoney=new HashMap<String,Object>();
								RebackMoney.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
								RebackMoney.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
								RebackMoney.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
								RebackMoney.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
								RebackMoney.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
								RebackMoney.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
								RebackMoney.put("recordNumber",requestNo);//交易流水号	（必填）
								RebackMoney.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
								obAccountDealInfoService.operateAcountInfo(RebackMoney);
								setJsonString("{success:true,msg:'预收成功转实收并完成退款'}");
							}else{
								setJsonString("{success:true,msg:'预收成功转实收无退款'}");
							}
						}
					}
				}
				//已经转入债权库
			}else if(orgPlBidSale.getSaleStatus().equals(Short.valueOf("7"))){
				String  transferType=this.getRequest().getParameter("transferType");
				if(transferType!=null&&transferType.equals("agree")){//同意实收转预收
					//预收没有转实收
					if(AppUtil.getConfigMap().get("thirdPayType").toString().trim().equals("0")){//托管支付
						if(AppUtil.getConfigMap().get("thirdPayConfig").toString().trim().equals(Constants.YEEPAY)){
							Map<String,Object> map =new HashMap<String,Object>();
							map.put("basePath", this.getBasePath());
							map.put("requestNo",orgPlBidSale.getPreTransferFeeRequestNo());
							String [] rett=yeePayService.checkCommentTransfer(map);
							/*String [] rett=new String[2];
							rett[0]=Constants.CODE_SUCCESS;*/
							if (rett!=null&&rett[0].equals(Constants.CODE_SUCCESS)) {
								orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("1"));
								plBidSaleService.save(orgPlBidSale);
								Map<String,Object> mapI=new HashMap<String,Object>();
								mapI.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
								mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
								mapI.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
								mapI.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
								mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
								mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
								mapI.put("recordNumber",orgPlBidSale.getPreTransferFeeRequestNo());//交易流水号	（必填）
								mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
								System.out.println("购买债权人系统账户交易记录dealStatus=="+mapI.get("dealStatus"));
								obAccountDealInfoService.updateAcountInfo(mapI);
								setJsonString("{success:true,msg:'预收转实收成功'}");
							} else {
								setJsonString("{success:true,msg:'预收转实收失败'}");
							}
						}else{
							setJsonString("{success:true,msg:'目前不支持其他第三方支付的预收转实收并完成退款'}");
						}
					}else{//资金池支付
						orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("1"));
						plBidSaleService.save(orgPlBidSale);
						Map<String,Object> mapI=new HashMap<String,Object>();
						mapI.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
						mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						mapI.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						mapI.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
						mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						mapI.put("recordNumber",orgPlBidSale.getPreTransferFeeRequestNo());//交易流水号	（必填）
						mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						System.out.println("购买债权人系统账户交易记录dealStatus=="+mapI.get("dealStatus"));
						obAccountDealInfoService.updateAcountInfo(mapI);
						setJsonString("{success:true,msg:'预收转实收成功'}");
					}
				}else if(transferType!=null&&transferType.equals("cancel")){//取消转预收
					if(AppUtil.getConfigMap().get("thirdPayType").toString().trim().equals("0")){//托管支付
						if(AppUtil.getConfigMap().get("thirdPayConfig").toString().trim().equals(Constants.YEEPAY)){
							Map<String,Object> map =new HashMap<String,Object>();
							map.put("basePath", this.getBasePath());
							map.put("requestNo",orgPlBidSale.getPreTransferFeeRequestNo());
							map.put("checkStatus","CANCEL");
							String [] rett=yeePayService.checkCommentTransfer(map);
							if (rett!=null&&rett[0].equals(Constants.CODE_SUCCESS)) {
								orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("-1"));
								plBidSaleService.save(orgPlBidSale);
								Map<String,Object> mapI=new HashMap<String,Object>();
								mapI.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
								mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
								mapI.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
								mapI.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
								mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
								mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
								mapI.put("recordNumber",orgPlBidSale.getPreTransferFeeRequestNo());//交易流水号	（必填）
								mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
								System.out.println("购买债权人系统账户交易记录dealStatus=="+mapI.get("dealStatus"));
								obAccountDealInfoService.updateAcountInfo(mapI);
								setJsonString("{success:true,msg:'预收退回'}");
							} else {
								setJsonString("{success:true,msg:'预收转实收退回失败'}");
							}
						}else{
							setJsonString("{success:true,msg:'目前不支持其他第三方支付的预收退回'}");
						}
					}else{//资金池支付
						orgPlBidSale.setPreTransferFeeStatus(Short.valueOf("-1"));
						plBidSaleService.save(orgPlBidSale);
						Map<String,Object> mapI=new HashMap<String,Object>();
						mapI.put("investPersonId",orgPlBidSale.getOutCustID());//投资人Id（必填）
						mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						mapI.put("transferType",ObAccountDealInfo.T_PLATEFORM_OBLIGATIONDEAL);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						mapI.put("money",orgPlBidSale.getPreTransferFee());//交易金额	（必填）			 
						mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						mapI.put("recordNumber",orgPlBidSale.getPreTransferFeeRequestNo());//交易流水号	（必填）
						mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_3);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						System.out.println("购买债权人系统账户交易记录dealStatus=="+mapI.get("dealStatus"));
						obAccountDealInfoService.updateAcountInfo(mapI);
						setJsonString("{success:true,msg:'预收退回'}");
					}
				}
			}else{
				setJsonString("{success:true,msg:'确认实收费用失败，债权没有成功交易'}");
			}
			
		}else{
			setJsonString("{success:true,msg:'确认实收费用失败，没有找到债权交易记录'}");
		}
		
		return SUCCESS;
	}
	/**
	 * 债权交易返款
	 * @return
	 */
	public String confirmtransreturn(){
		String id = this.getRequest().getParameter("id");
		PlBidSale sale = plBidSaleService.get(Long.valueOf(id));
		if(sale!=null&&sale.getReturnStatus()==null){
			BpCustMember customer = bpCustMemberService.get(sale.getOutCustID());
			String orderNum =ContextUtil.createRuestNumber();//生成第三需要的流水号
			CommonRequst commonRequest=new CommonRequst();
			commonRequest.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方支付账号
			commonRequest.setRequsetNo(orderNum);//请求流水号
			commonRequest.setBidId(sale.getBidPlanID().toString());//标的Id
			commonRequest.setBussinessType(ThirdPayConstants.BT_BACKDEAL);//计划类型
			commonRequest.setTransferName(ThirdPayConstants.TN_BACKDEAL);//计划名称
			if(customer.getCustomerType() != null && customer.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
				commonRequest.setAccountType(1);
			}else{//借款人是个人
				commonRequest.setAccountType(0);
			}
			commonRequest.setAmount(sale.getSumMoney());
			CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
			if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				sale.setReturnRequestNo(orderNum);
				sale.setReturnStatus("2");
				plBidSaleService.save(sale);
				
				BpCustMember bpso=bpCustMemberService.get(sale.getOutCustID());
				Map<String,Object> mapIo=new HashMap<String,Object>();
				mapIo.put("investPersonId",bpso.getId());//投资人Id（必填）
				mapIo.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
				mapIo.put("transferType",ObAccountDealInfo.T_TRANSFER);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				mapIo.put("money",sale.getSumMoney());//交易金额	（必填）			 
				mapIo.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapIo.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapIo.put("recordNumber",sale.getNewOrderNo()+"-O");//交易流水号	（必填）
				mapIo.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
				obAccountDealInfoService.operateAcountInfo(mapIo);
				
				setJsonString("{success:true,msg:'返款成功'}");
			}else{
				setJsonString("{success:true,msg:'返款失败："+commonResponse.getResponseMsg()+"'}");
			}
		}else{
			setJsonString("{success:true,msg:'已经返款不能重复返款'}");
		}
		return SUCCESS;
	}
	
	public void outputExcel(){
		String [] tableHeader = {"序号","出让人","受让人","债权名称","出让本金","挂牌时间","结算总金额","预收服务费","实收服务费","交易成功时间"};
		try {
			Map<String, String> map=new HashMap<String,String>();
			QueryFilter filter = new QueryFilter(getRequest());
			Enumeration<?> paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<PlBidSale> list=common(map,filter);
			ExcelHelper.export(list,tableHeader,"债权交易手续费台账");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}