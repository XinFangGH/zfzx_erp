package com.thirdPayInterface.Huifu;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.thirdPayInterface.CommonRequestInvestRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayTypeInterface;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayRecordService;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
public class HuifuConfigImpl implements ThirdPayTypeInterface {
	static  BpFundIntentService bpFundIntentService = (BpFundIntentService) AppUtil.getBean("bpFundIntentService");
	static  PlBidInfoService plBidInfoService = (PlBidInfoService)AppUtil.getBean("plBidInfoService");
	/**
	 * 汇付天下
	 */
	@Override
	public CommonResponse businessHandle(CommonRequst commonRequst) {
		CommonResponse commonResponse=new CommonResponse();
	    try{
	    	if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PREGISTER)){//个人开通第三方
	    		commonResponse=HuiFuInterfaceUtil.register(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BID)){//投标接口
	    		commonResponse=HuiFuInterfaceUtil.biding(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_RECHAGE)){//充值  
	    		commonResponse=HuiFuInterfaceUtil.rechargeMoney(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATFORMPAY)){//平台商户充值  
	    		commonResponse=HuiFuInterfaceUtil.rechargeMoney(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_WITHDRAW)){//取现
	    		commonResponse=HuiFuInterfaceUtil.withdrawsMoney(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CREATEBID)){
	    		if(commonRequst.getBidType()!=null&&commonRequst.getBidType().equals(CommonRequst.HRY_PLANBUY)){//理财计划
	    			commonRequst.setProId(commonRequst.getBidType()+commonRequst.getBidId());//项目ID
	    			commonRequst.setRetType("99");//还款方式
	    			commonRequst.setBidProdType("99");//标的产品类型
	    			commonRequst.setBorrTotAmt(commonRequst.getBidMoney().toString());//标的总金额
	    			commonRequst.setBidProdType("99");//标的产品类型
	    			commonRequst.setBorrPurpose("理财计划");
	    			commonRequst.setBorrCertType("00");
	    		}else{//散标
	    			commonRequst.setGuarAmt("");//担保金额
	    			commonRequst.setGuarCompId("");//担保公司id
	    			commonRequst.setMerPriv("");//用户私有域
	    			commonRequst.setReqExt("");
	    			commonRequst.setBorrPhone("");
	    			commonRequst.setBorrWork("");
	    			commonRequst.setBorrWorkYear("");
	    			commonRequst.setBorrIncome("");
	    			commonRequst.setBorrEdu("");
	    			commonRequst.setBorrMarriage("");
	    			commonRequst.setBorrAddr("");
	    			commonRequst.setBorrEmail("");
	    			commonRequst.setGuarantType("");		
	    			commonRequst.setRiskCtlType("");
	    			commonRequst.setRecommer("");
	    			commonRequst.setLimitBidSum("");
	    			commonRequst.setLimitMaxBidSum("");
	    			commonRequst.setLimitMinBidAmt("");
	    			commonRequst.setLimitMinBidSum("");
	    			commonRequst.setOverdueProba("");
	    			commonRequst.setRetType("99");//还款类型
	    			commonRequst.setBidProdType("99");//标的产品类型
	    			commonRequst.setBidPayforState("");
	    			commonRequst.setProId(CommonRequst.HRY_BID+commonRequst.getBidId());//项目ID
	    			commonRequst.setBidType("99");
	    		}
	    		commonResponse=HuiFuInterfaceUtil.createBid(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_UPDATEBID)){//更新标的状态
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    		commonResponse.setResponseMsg("处理成功");
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_LOAN)){//散标放款
				Map thirdMap=HuiFuInterfaceUtil.HuifuProperty();
				String plateFormNo = thirdMap.get("thirdPay_Huifu_platNumber").toString(); 
				if(commonRequst.getFee()!=null&&commonRequst.getFee().compareTo(new BigDecimal(0))>0){
					commonRequst.setDivDetails("[{\"DivCustId\":\""+plateFormNo+"\",\"DivAcctId\":\""+"MDT000001"+"\",\"DivAmt\":\""+commonRequst.getFee()+"\"}]");
					commonRequst.setFeeObjFlag("I");//根据费用是否为0进行判断  (I:向入款账户收费 O:向出款账户收费)默认向借款人收取
				}else{
					commonRequst.setDivDetails("");
					commonRequst.setFeeObjFlag("");//根据费用是否为0进行判断  (I:向入款账户收费 O:向出款账户收费)默认向借款人收取
				}	    		
				commonRequst.setReqExt("{\"LoansVocherAmt\":\""+""+"\",\"ProId\":\""+CommonRequst.HRY_BID+commonRequst.getBidId()+"\"}");
				commonRequst.setMerPriv("");//商户私有域`
				commonRequst.setIsDefault("N");//是否默认Y--默认添加资金池:这部分资金需要商户调用商户代取现接口，帮助用户做后台取现动作N--不默认不添加资金池:这部分资金用户可以自己取现
				commonRequst.setIsUnFreeze("Y");//是否解冻
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    		commonRequst.setOrderDate(sdf.format(new Date()));
	    		commonResponse=HuiFuInterfaceUtil.bidLoan(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMLOANUSER)){//理财计划起息(收款账户为用户)
	    		String unFreezeNo = ContextUtil.createRuestNumber();
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    		commonRequst.setOrderDate(sdf.format(new Date()));
	    		Map thirdMap=HuiFuInterfaceUtil.HuifuProperty();
				String plateFormNo = thirdMap.get("thirdPay_Huifu_platNumber").toString(); 
				if(commonRequst.getFee()!=null&&commonRequst.getFee().compareTo(new BigDecimal(0))>0){
					commonRequst.setDivDetails("[{\"DivCustId\":\""+plateFormNo+"\",\"DivAcctId\":\""+"MDT000001"+"\",\"DivAmt\":\""+commonRequst.getFee()+"\"}]");
					commonRequst.setFeeObjFlag("I");//根据费用是否为0进行判断  (I:向入款账户收费 O:向出款账户收费)默认向借款人收取
				}else{
					commonRequst.setDivDetails("");
					commonRequst.setFeeObjFlag("");//根据费用是否为0进行判断  (I:向入款账户收费 O:向出款账户收费)默认向借款人收取
				}
				commonRequst.setReqExt("{\"LoansVocherAmt\":\""+""+"\",\"ProId\":\""+commonRequst.getBidType()+commonRequst.getBidId()+"\"}");//用户扩展域
				commonRequst.setIsDefault("N");//是否默认Y--默认添加资金池:这部分资金需要商户调用商户代取现接口，帮助用户做后台取现动作N--不默认不添加资金池:这部分资金用户可以自己取现
				commonRequst.setIsUnFreeze("Y");//是否解冻
				commonRequst.setUnFreezeOrdId(unFreezeNo);//解冻订单号
	    		commonResponse=HuiFuInterfaceUtil.bidLoanPlmanage(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATEFORMRECHAGE_LOAN)){//
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    		commonResponse.setResponseMsg("处理成功");
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELBID)){//取消投标
	    		commonResponse=HuiFuInterfaceUtil.unFreeze(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMCANCELUSER)){//理财计划取消购买(收款账户为用户)
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    		commonRequst.setIsUnFreeze("Y");
	    		commonRequst.setOrdId(commonRequst.getQueryRequsetNo());
	    		commonRequst.setTrxId(commonRequst.getLoanNoList());
	    		commonRequst.setOrderDate(sdf.format(new Date()));
	    		commonResponse=HuiFuInterfaceUtil.unFreezePlmanage(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_UNFREEZE)){//资金解冻（流标）
	    		commonResponse=HuiFuInterfaceUtil.bidFailed(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BALANCEQUERY)){//余额查询 
	    		commonResponse=HuiFuInterfaceUtil.queryBalanceBg(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BATCHREPAYMENT)){//后台批量还款
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HELPPAY)){//平台帮助借款人还款
	    		Map thirdMap=HuiFuInterfaceUtil.HuifuProperty();
				String plateFormNo = thirdMap.get("thirdPay_Huifu_platNumber").toString(); 
	    		List<CommonRequestInvestRecord> list =  commonRequst.getRepaymemntList();//获取到还款列表
	    		List<CommonResponse> list1 = new ArrayList();
	    		Map map = new HashMap();
	    		String periodId = commonRequst.getRemark1();//还款期数
	    		String bidId = commonRequst.getBidId();//标的号	
	    		//查出这一期所有的台账（本金利息加上平台费用）
	    		String orderNo = ContextUtil.createRuestNumber();
	    		BigDecimal accMoney = BigDecimal.ZERO;//罚息的金额
	    		List<BpFundIntent> list_fund = bpFundIntentService.getByBidIdandPeriod(bidId, periodId);
	    		if(list_fund.size()>0){//重新给对应的款项赋值
	    			for(BpFundIntent intent:list_fund){
	    				if(intent.getAccrualMoney()!=null&&intent.getAccrualMoney().compareTo(new BigDecimal(0))>0){
	    					accMoney = accMoney.add(intent.getAccrualMoney());//计算罚息的总金额
	    				}
	    				intent.setRequestNo(orderNo);
	    				bpFundIntentService.merge(intent);
	    			}
	    		}
	    		String repay_orderNo ="";//还款订单号
	    		List div_list = new ArrayList();
	    		DivDetails div_details = new DivDetails();
	    		Map mapI = new HashMap();
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    		String batch_id = ContextUtil.createRuestNumber();
	    		String requestNo = "";//
	    		List list_details = new ArrayList();
	    		for(CommonRequestInvestRecord record : list){
	    			Map mapII = new HashMap();
	    			repay_orderNo = ContextUtil.createRuestNumber();
	    			mapI.put("DivCustId", plateFormNo);
	    			mapI.put("DivAcctId", "MDT000001");
	    			mapI.put("DivAmt", record.getFee().setScale(2).toString());
	    			JSONArray json2 = JSONArray.fromObject(mapI);
	    			mapII.put("InCustId", record.getInvest_thirdPayConfigId());
	    			mapII.put("InAcctId", "");
	    			mapII.put("OrdId", repay_orderNo);
	    			mapII.put("SubOrdId", record.getBidRequestNo());
	    			mapII.put("DzBorrCustId", "");
	    			mapII.put("TransAmt", record.getAmount().subtract(record.getFee()).add(accMoney).setScale(2).toString());
	    			if(record.getFee().compareTo(new BigDecimal("0"))>0){
	    				mapII.put("Fee", record.getFee().setScale(2).toString());
	    				mapII.put("DivDetails", json2);
	    				mapII.put("FeeObjFlag", "O");
	    			}
	    			list_details.add(mapII);
	    		}
	    		JSONArray json = JSONArray.fromObject(list_details);
	    		map.put("InDetails", json);
	    		JSONObject json1 = JSONObject.fromObject(map);
	    		commonRequst.setOutCustId(commonRequst.getThirdPayConfigId());//出款人的第三方账户号
	    		commonRequst.setOutAcctId("");
	    		commonRequst.setReqExt("");
	    		commonRequst.setMerPriv("");
	    		commonRequst.setBatchId(batch_id);//还款批次号
	    		commonRequst.setMerOrdDate(sdf.format(new Date()));
	    		commonRequst.setInDetails(json1.toString());
	    		commonResponse.setResponseList(list1);
	    		commonResponse=HuiFuInterfaceUtil.batchRepay(commonRequst);
	    		if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
 	    			commonResponse.setRequestNo(orderNo);
	    		}
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMGIVEUSER)
	    			||commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMSIGNOUTUSER)){//理财计划派息(收款账户为用户)，提前贖回 
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    		commonRequst.setProId(CommonRequst.HRY_PLANBUY+commonRequst.getBidId());
	    		commonRequst.setOrderDate(sdf.format(new Date()));
	    		commonResponse=HuiFuInterfaceUtil.repayment(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HELPPAY_FEE)){//平台收费
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
	    		commonResponse.setResponseMsg("操作成功");
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PREPAREPAY)){//平台准备金代偿还款
	    		//平台代偿
	    		List<CommonRequestInvestRecord> list = commonRequst.getRepaymemntList();
	    		CommonResponse response = new CommonResponse();
	    		Properties props = new Properties();
				Map thirdMap=HuiFuInterfaceUtil.HuifuProperty();
				String plateFormNo = thirdMap.get("thirdPay_Huifu_platNumber").toString(); 
	    		List<CommonResponse> list3 = new ArrayList();
	    		if(list.size()>0){
	    			for(CommonRequestInvestRecord record:list){
	    				list3 = new ArrayList();
	    				String requestNo1 = ContextUtil.createRuestNumber();
	    				QueryFilter filter = new QueryFilter();
	    	    		filter.addFilter("Q_bidPlanId_L_EQ", Long.valueOf(commonRequst.getBidId()));
	    	    		filter.addFilter("Q_payintentPeriod_N_EQ", Integer.valueOf(commonRequst.getRemark1()));
	    	    		filter.addFilter("Q_isValid_SN_EQ",(short)0);
	    	    		List<BpFundIntent> list1 = bpFundIntentService.getAll(filter);
	    	    		for(BpFundIntent intent : list1){
	    	    			//平台代偿不需要将管理费用还给自己
	    	    				intent.setRequestNo(requestNo1);
	    	    				bpFundIntentService.merge(intent);
	    	    		}
	    			    CommonResponse commonResponse1 = new CommonResponse();
	    				CommonRequst requst = new CommonRequst();
	    				requst.setInCustId(record.getInvest_thirdPayConfigId());//入账用户支付账号
	    				requst.setInAcctId("");//入账子账号
	    				requst.setOutCustId(plateFormNo);//出账客户号
	    				requst.setOutAcctId("MDT000001");//出账子账号
	    				requst.setRequsetNo(requestNo1);
	    				requst.setAmount(record.getAmount().subtract(record.getFee()));
	    				if(record.getAmount().subtract(record.getFee()).compareTo(new BigDecimal(0))>0){
	    					commonResponse1 = HuiFuInterfaceUtil.transfer1(requst);
	    				}else{
	    					commonResponse1.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    				}
	    				commonResponse1.setRequestNo(requestNo1);
	    				list3.add(commonResponse1);
	    			}
	    			response.setResponseList(list3);
	    			response.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
	    			commonResponse = response;
	    		}
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYPLATF)){//平台会员交易查询(充值、取现、还款、放款、转账)
	    		if(commonRequst.getQueryType().equals("RECHARGE_RECORD")){
	    			commonResponse=HuiFuInterfaceUtil.queryTransDetail(commonRequst);
	    		}else{
	    			commonResponse=HuiFuInterfaceUtil.queryTransStat(commonRequst);
	    		}
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYUSER)){ //余额查询  
	    		commonResponse=HuiFuInterfaceUtil.queryBalanceBg(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QPLATFORM)){ //平台商户流水查询 
	    		commonResponse=HuiFuInterfaceUtil.trfReconciliation(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_GETPLATFORM)){ //平台商户余额查询 
	    		commonResponse=HuiFuInterfaceUtil.queryBalanceBg(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_SEDRED)){//发红包
	    		commonResponse=HuiFuInterfaceUtil.transfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_COUPONAWARD)){//优惠券派发奖励
	    		commonResponse=HuiFuInterfaceUtil.transfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_AUTOBID)){//散标自动投标
	    		commonResponse=HuiFuInterfaceUtil.autobid(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMAUTH)){//理财计划授权平台派息
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    		commonResponse.setResponseMsg("操作成功");
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELDEAL)){//取消挂牌
	    		commonRequst.setBidType(CommonRequst.HRY_SALE);
	    		commonResponse=HuiFuInterfaceUtil.cancelDeal(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_FALSESERVICE)){//挂牌交易服务费将预收转实收后退费
	    		commonRequst.setBidType(CommonRequst.HRY_SALE);
	    		commonResponse=HuiFuInterfaceUtil.cancelDeal(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_TRUESERVICE)){//挂牌交易服务费将预收转为实收
	    		commonRequst.setBidType(CommonRequst.HRY_SALE);
	    		commonResponse=HuiFuInterfaceUtil.cancelDeal(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_GETPLATFORM)){//商户自身的余额查询  
	    		commonResponse=HuiFuInterfaceUtil.queryPlateForm(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_RECHARGEFILE)){//充值对账  
	    		commonResponse=HuiFuInterfaceUtil.saveReconciliation(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_WITHDRAWFILE)){//提现对账   
	    		commonResponse=HuiFuInterfaceUtil.cashReconciliation(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDTRANSFERFILE)){//放还款对账   ,标的转账
	    		commonResponse=HuiFuInterfaceUtil.reconciliation(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDBALANCEFILE)){//标的审核状态查询   ，标的对账
//	    		commonResponse=HuiFuInterfaceUtil.queryBidInfo(commonRequst);
//	    		commonResponse=HuiFuInterfaceUtil.addBidAttachInfo(commonRequst);
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_NOTBUSINESS);  
		    	commonResponse.setResponseMsg("暂不支持该业务");
	    	}else {
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_NOTBUSINESS);  
		    	commonResponse.setResponseMsg("没有该业务类型");
	    	}
	    	
	    	
	    }catch(Exception e){
	    	e.printStackTrace();
	    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	commonResponse.setResponseMsg("系统报错");
	    }
		 
		return commonResponse;
	}
	
	/**
	 * 解析双乾回调
	 */
	@Override
	public CommonResponse businessReturn(Map map,HttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		//注： 如果是单一业务可以用 cmdId消息类型判断业务类型、
		//    如果是一个消息类型处理多个业务 和 请求的流水号一起判断业务类型
		
		//得到消息类型判断系统业务
		String cmdId = "";
		if(map.containsKey("CmdId")){
			cmdId = map.get("CmdId").toString();
		}
		//得到请求的流水号
		String requestNo = "";
		if(map.containsKey("MerPriv")){
			requestNo = map.get("MerPriv").toString();
		}
		//如果流水号不为空就可判断业务类型
		ThirdPayRecord thirdPayRecord=null;
		if(!requestNo.equals("")){
			ThirdPayRecordService thirdPayRecordService = (ThirdPayRecordService) AppUtil.getBean("thirdPayRecordService");
			thirdPayRecord = thirdPayRecordService.getByOrderNo(requestNo);
		}
		if(!cmdId.equals("")){
			if(cmdId.equals("UserRegister")){//注册
				String merData = 	map.get("CmdId").toString().trim()+
									map.get("RespCode").toString().trim()+
									map.get("MerCustId").toString().trim()+
									map.get("UsrId").toString().trim()+
									map.get("UsrCustId").toString().trim()+
									map.get("BgRetUrl").toString().trim()+
									map.get("TrxId").toString().trim()+
									map.get("RetUrl").toString().trim()+
									map.get("MerPriv").toString().trim();
				boolean flag=HuiFuInterfaceUtil.verifyChkValue(merData,map.get("ChkValue").toString().trim());
				if(flag){
					if(map.get("RespCode").toString().equals("000")){
						commonResponse.setThirdPayConfigId(map.get("UsrCustId").toString());
	    				commonResponse.setThirdPayConfigId0(map.get("UsrCustId").toString());
	    				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_ISNOTPASSSIGN);
				}
				commonResponse.setRequestNo(requestNo);
				commonResponse.setRequestInfo(map.toString());
				commonResponse.setResponseMsg(map.get("RespDesc").toString());
			}else if(cmdId.equals("NetSave")){//充值
				String merData = 	map.get("CmdId").toString().trim()+
									map.get("RespCode").toString().trim()+
									map.get("MerCustId").toString().trim()+
									map.get("UsrCustId").toString().trim()+
									map.get("OrdId").toString().trim()+
									map.get("OrdDate").toString().trim()+
									map.get("TransAmt").toString().trim()+
									(map.containsKey("TrxId")?map.get("TrxId").toString().trim():"")+
									map.get("RetUrl").toString().trim()+
									map.get("BgRetUrl").toString().trim()+
									map.get("MerPriv").toString().trim();
				boolean flag=HuiFuInterfaceUtil.verifyChkValue(merData,map.get("ChkValue").toString());
				if(flag){
					if(map.get("RespCode").toString().equals("000")){
						commonResponse.setThirdPayConfigId(map.get("UsrCustId").toString());
						commonResponse.setThirdPayConfigId0(map.get("UsrCustId").toString());
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_ISNOTPASSSIGN);
				}
				commonResponse.setRequestNo(requestNo);
				commonResponse.setRequestInfo(map.toString());
				commonResponse.setResponseMsg(map.get("RespDesc").toString());
			}else if(cmdId.equals("Cash")){//取现
				String merData = 	map.get("CmdId").toString().trim()+
									map.get("RespCode").toString().trim()+
									map.get("MerCustId").toString().trim()+
									map.get("OrdId").toString().trim()+
									map.get("UsrCustId").toString().trim()+
									map.get("TransAmt").toString().trim()+
									map.get("OpenAcctId").toString().trim()+
									map.get("OpenBankId").toString().trim()+
									map.get("RetUrl").toString().trim()+
									map.get("BgRetUrl").toString().trim()+
									map.get("MerPriv").toString().trim();
				boolean flag=HuiFuInterfaceUtil.verifyChkValue(merData,map.get("ChkValue").toString());
				if(flag){
					if(map.get("RespCode").toString().equals("000")){
						commonResponse.setThirdPayConfigId(map.get("UsrCustId").toString());
						commonResponse.setThirdPayConfigId0(map.get("UsrCustId").toString());
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					}
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_ISNOTPASSSIGN);
				}
					commonResponse.setRequestNo(requestNo);
					commonResponse.setRequestInfo(map.toString());
					commonResponse.setResponseMsg(map.get("RespDesc").toString());
				}else if(cmdId.equals("InitiativeTender")){
					String merData =  map.get("CmdId").toString().trim()
									+ map.get("RespCode").toString().trim()
									+ map.get("MerCustId").toString().trim()
									+ map.get("OrdId").toString().trim()
									+ map.get("OrdDate").toString().trim()
									+ map.get("TransAmt").toString().trim()
									+ map.get("UsrCustId").toString().trim()
									+ map.get("TrxId").toString().trim()
									+ map.get("RetUrl").toString().trim()
									+ map.get("BgRetUrl").toString().trim()
									+ map.get("MerPriv").toString().trim();
					boolean flag=HuiFuInterfaceUtil.verifyChkValue(merData,map.get("ChkValue").toString());
					if(flag){
						if(map.get("RespCode").toString().equals("000")){
							commonResponse.setThirdPayConfigId(map.get("UsrCustId").toString());
							commonResponse.setThirdPayConfigId0(map.get("UsrCustId").toString());
							commonResponse.setRequestNo(map.get("OrdId").toString());
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_ISNOTPASSSIGN);
					}
						commonResponse.setRequestNo(requestNo);
						commonResponse.setRequestInfo(map.toString());
						commonResponse.setResponseMsg(map.get("RespDesc").toString());
				}else if(cmdId.equals("AddBidInfo")){
					String merData =  map.get("CmdId").toString().trim()
					+ map.get("RespCode").toString().trim()
					+ map.get("MerCustId").toString().trim()
					+ map.get("OrdId").toString().trim()
					+ map.get("OrdDate").toString().trim()
					+ map.get("TransAmt").toString().trim()
					+ map.get("UsrCustId").toString().trim()
					+ map.get("TrxId").toString().trim()
					+ map.get("RetUrl").toString().trim()
					+ map.get("BgRetUrl").toString().trim()
					+ map.get("MerPriv").toString().trim();
					boolean flag=HuiFuInterfaceUtil.verifyChkValue(merData,map.get("ChkValue").toString());
					if(flag){
						if(map.get("RespCode").toString().equals("000")){
							commonResponse.setThirdPayConfigId(map.get("UsrCustId").toString());
							commonResponse.setThirdPayConfigId0(map.get("UsrCustId").toString());
							commonResponse.setRequestNo(map.get("OrdId").toString());
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
						}else{
							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
						}
					}else{
						commonResponse.setResponsecode(CommonResponse.RESPONSECODE_ISNOTPASSSIGN);
					}
						commonResponse.setRequestNo(requestNo);
						commonResponse.setRequestInfo(map.toString());
						commonResponse.setResponseMsg(map.get("RespDesc").toString());
					}				
		}
		return commonResponse;
	}

	/**
	 * 判断不同的第三方操作模式
	 */
	@Override
	public Object[] checkThirdType(String businessType,String type) {
		Object[] thirdType = new Object[2];
		if(businessType.equals(ThirdPayConstants.BT_MMLOANUSER)||businessType.equals(ThirdPayConstants.BT_LOAN)){//理财计划注册用户起息
			//one表示 单个起息。all表示批量起息
			thirdType[0]="one";
			thirdType[1]=CommonResponse.RESPONSECODE_SUCCESS;
    	}else if(businessType.equals(ThirdPayConstants.BT_MMAUTH)){//理财计划授权平台派息
			//1表示 打开页面。0表示不打开页面
			thirdType[0]="0";
			thirdType[1]=CommonResponse.RESPONSECODE_SUCCESS;
    	}else{
    		thirdType[0]="error";
    		thirdType[1]=CommonResponse.RESPONSECODE_NOTBUSINESS;
    	}
		return thirdType;
	}
}