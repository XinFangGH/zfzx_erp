package com.thirdPayInterface.FuDianPay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rd.bds.common.property.CommonPro;
import com.rd.bds.sign.util.DecryptUtil;
import com.thirdPayInterface.CommonRequestInvestRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayTypeInterface;
import com.thirdPayInterface.Fuiou.FuiouInterfaceUtil;
import com.thirdPayInterface.Huifu.HuiFuInterfaceUtil;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;

public class FuDianConfigImpl implements ThirdPayTypeInterface {
	static  BpFundIntentService bpFundIntentService = (BpFundIntentService) AppUtil.getBean("bpFundIntentService");
	static  PlBidInfoService plBidInfoService = (PlBidInfoService)AppUtil.getBean("plBidInfoService");
	static  SlActualToChargeService slActualToChargeService = (SlActualToChargeService)AppUtil.getBean("slActualToChargeService");
	/**
	 * 富滇银行
	 */
	@Override
	
	public CommonResponse businessHandle(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CREATEBID)){//建立标的信息
			commonResponse = FuDianInterfaceUtil.createBid(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELBID)){//标的流标
			commonResponse = FuDianInterfaceUtil.preAuthorizationCancel(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_AUTOBID)){//自动投标
			commonResponse = FuDianInterfaceUtil.preAuthorization(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_LOAN)){//BT_LOAN
			commonResponse = FuDianInterfaceUtil.allot(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYUSER)||commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BALANCEQUERY)){//用户余额查询接口
			commonResponse = FuDianInterfaceUtil.balanceQuery(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYDEAL)){//查询订单状态
    		commonResponse = FuDianInterfaceUtil.queryDealInfo(commonRequst);
    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_UPDATEBID)){//ThirdPayConstants.BT_UPDATEBID
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("操作成功");
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_TRANSFER_MEMBER)){//平台给用户转账
			commonResponse = FuDianInterfaceUtil.transfers(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATEFORMRECHAGE_LOAN)){//平台收取费用
			//判断是否已经收取过平台费用   如果收过则直接返回成功
			//commonResponse = FuiouInterfaceUtil.transfers(commonRequst);
			QueryFilter filter = new QueryFilter();
			filter.addFilter("Q_bidPlanId_L_EQ", Long.valueOf(commonRequst.getBidId()));
			List<SlActualToCharge> rechageList = slActualToChargeService.getAll(filter);
			System.out.println(rechageList.size());
			if(rechageList.size()>0){
				if(rechageList.get(0).getFactDate()==null){
					//如果处理过  则返回一个apply 方法接收到该参数则不处理回调
					commonResponse = FuiouInterfaceUtil.transfers(commonRequst);
				}else{
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
				}
			}else{
				//如果处理过  则返回一个apply 方法接收到该参数则不处理回调
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
			}
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATEFORMRECHAGE)){
			commonResponse = FuiouInterfaceUtil.transfers(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_LOANAUTH)){//散标放款授权
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
			commonResponse.setResponseMsg("操作成功");
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_COUPONAWARD)){//优惠券奖励  
			commonRequst.setLoaner_thirdPayflagId(commonRequst.getThirdPayConfigId());
			commonRequst.setThirdPayConfigId("");//付款人置空默认平台付款
			commonResponse = FuiouInterfaceUtil.transfers(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_SEDRED)){//派发红包
			commonRequst.setLoaner_thirdPayflagId(commonRequst.getThirdPayConfigId());
			commonRequst.setThirdPayConfigId("");//付款人置空默认平台付款
			commonResponse = FuiouInterfaceUtil.transfers(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_TRANSFER_MEMBER)){//平台给用户转账
			commonResponse = FuiouInterfaceUtil.transfers(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYPLATF)){//平台单笔业务查询
			if(commonRequst.getQueryType()!=null&&!"".equals(commonRequst.getQueryType())){
				if("RECHARGE_RECORD".equals(commonRequst.getQueryType())){//充值记录
					commonRequst.setBussinessType("PW11");
					commonResponse = FuiouInterfaceUtil.rechargeAndWithdraw(commonRequst);
				}else if("PAYMENT_RECORD".equals(commonRequst.getQueryType())||"REPAYMENT_RECORD".equals(commonRequst.getQueryType())){//放款还款查询
					commonRequst.setBussinessType("PW03");
					commonResponse = FuiouInterfaceUtil.queryTransaction(commonRequst);
				}else{//提现记录
					commonRequst.setBussinessType("PWTX");
					commonResponse = FuiouInterfaceUtil.rechargeAndWithdraw(commonRequst);
				}
			}
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HELPPAY)){//后台还款
    		List<CommonRequestInvestRecord> list = commonRequst.getRepaymemntList();
    		CommonResponse response = new CommonResponse();
    		List<CommonResponse> list3 = new ArrayList();
    		//计数器  判断是否已经还过款  如果已经还过  则不能再还
    		Integer count =0;
    		String requestNo1 ="";
    		if(list.size()>0){
    			for(CommonRequestInvestRecord record:list){
    				//判断是否是转让后的债权
    				requestNo1 = ContextUtil.createRuestNumber();
    				QueryFilter filter1 = new QueryFilter();
    				filter1.addFilter("Q_newOrderNo_S_EQ", record.getBidRequestNo());
    				filter1.addFilter("Q_plBidPlan.bidId_L_EQ", Long.valueOf(commonRequst.getBidId()));
    				List<PlBidInfo> list2 = plBidInfoService.getAll(filter1);
    				BigDecimal accMoney = BigDecimal.ZERO;
	    				if(list2.size()>0){//如果是转让后的债权则按照标的号查找
		    				//list3 = new ArrayList();
		    				QueryFilter filter = new QueryFilter();
		    	    		filter.addFilter("Q_bidPlanId_L_EQ", Long.valueOf(commonRequst.getBidId()));
		    	    		filter.addFilter("Q_payintentPeriod_N_EQ", Integer.valueOf(commonRequst.getRemark1()));
		    	    		filter.addFilter("Q_isValid_SN_EQ", (short)0);
		    	    		List<BpFundIntent> list1 = bpFundIntentService.getAll(filter);
		    	    		if(list1.size()>0){
			    	    		for(BpFundIntent intent : list1){
			    	    			//平台费用不还给投资人
			    	    			if(intent.getFundType()!=null&&!intent.getFundType().equals("serviceMoney")&&!intent.getFundType().equals("consultationMoney")&&intent.getFactDate()==null){
			    	    				if(intent.getAccrualMoney()!=null&&intent.getAccrualMoney().compareTo(new BigDecimal(0))>0){
			    	    					accMoney = accMoney.add(intent.getAccrualMoney());
			    	    				}
			    	    				intent.setRequestNo(requestNo1);
			    	    				bpFundIntentService.merge(intent);
			    	    				count++;
			    	    			}
			    	    		}
		    	    		}
    				}else{//如果不是则按照流水号进行查找操作
	    				//list3 = new ArrayList();
	    				QueryFilter filter = new QueryFilter();
	    	    		filter.addFilter("Q_orderNo_S_EQ", record.getBidRequestNo());
	    	    		filter.addFilter("Q_payintentPeriod_N_EQ", Integer.valueOf(commonRequst.getRemark1()));
	    	    		//filter.addFilter("Q_isValid_SN_EQ", (short)1);
	    	    		List<BpFundIntent> list1 = bpFundIntentService.getAll(filter);
	    	    		if(list1.size()>0){
		    	    		for(BpFundIntent intent : list1){
		    	    			//平台费用不还给投资人
		    	    			if(intent.getFundType()!=null&&!intent.getFundType().equals("serviceMoney")&&!intent.getFundType().equals("consultationMoney")&&intent.getFactDate()==null){
		    	    				if(intent.getAccrualMoney()!=null&&intent.getAccrualMoney().compareTo(new BigDecimal(0))>0){
		    	    					accMoney = accMoney.add(intent.getAccrualMoney());
		    	    				}
		    	    				intent.setRequestNo(requestNo1);
		    	    				bpFundIntentService.merge(intent);
		    	    				count++;
		    	    			}
		    	    		}
	    	    		}
    				}
    	    		CommonResponse commonResponse1 = new CommonResponse();
    	    		CommonRequst requst = new CommonRequst();
    	    		if(count>0){
	    				requst.setThirdPayConfigId(commonRequst.getThirdPayConfigId());//出账用户支付账号
	    				requst.setLoaner_thirdPayflagId(record.getInvest_thirdPayConfigId());//入账用户支付账号
	    				requst.setContractNo("");//预授权合同号
	    				requst.setRequsetNo(requestNo1);
	    				requst.setAmount(accMoney.compareTo(new BigDecimal(0))>0?record.getAmount().subtract(record.getFee()).add(accMoney):record.getAmount().subtract(record.getFee()));
	    				commonResponse1.setRemark(record.getAmount().subtract(record.getFee()).compareTo(new BigDecimal("0"))==0?"0":record.getAmount().subtract(record.getFee()).toString());
	    				commonResponse1 = FuiouInterfaceUtil.allot(requst);
	    				commonResponse1.setRequestNo(requestNo1);
	    				list3.add(commonResponse1);
    	    		}
    			}
    			response.setResponseList(list3);
    			response.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
    			commonResponse = response;
    		}
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HELPPAY_FEE)){
			List<CommonRequestInvestRecord> list = commonRequst.getRepaymemntList();
    		CommonResponse response = new CommonResponse();
    		List<CommonResponse> list3 = new ArrayList();
    		//计数器  判断是否已经还过款  如果已经还过  则不能再还
    		Integer count =0;
    		String requestNo1 ="";
    		if(list.size()>0){
    			for(CommonRequestInvestRecord record:list){
    				//判断是否是转让后的债权
    				requestNo1 = ContextUtil.createRuestNumber();
    				QueryFilter filter1 = new QueryFilter();
    				filter1.addFilter("Q_newOrderNo_S_EQ", record.getBidRequestNo());
    				filter1.addFilter("Q_plBidPlan.bidId_L_EQ", Long.valueOf(commonRequst.getBidId()));
    				List<PlBidInfo> list2 = plBidInfoService.getAll(filter1);
	    				if(list2.size()>0){//如果是转让后的债权则按照标的号查找
		    				//list3 = new ArrayList();
		    				QueryFilter filter = new QueryFilter();
		    	    		filter.addFilter("Q_bidPlanId_L_EQ", Long.valueOf(commonRequst.getBidId()));
		    	    		filter.addFilter("Q_payintentPeriod_N_EQ", Integer.valueOf(commonRequst.getRemark1()));
		    	    		filter.addFilter("Q_isValid_SN_EQ", (short)0);
		    	    		List<BpFundIntent> list1 = bpFundIntentService.getAll(filter);
		    	    		if(list1.size()>0){
			    	    		for(BpFundIntent intent : list1){
			    	    			//平台费用不还给投资人
			    	    			if(intent.getFundType()!=null&&(intent.getFundType().equals("serviceMoney")||intent.getFundType().equals("consultationMoney"))&&intent.getFactDate()==null){
			    	    				intent.setRequestNo(requestNo1);
			    	    				bpFundIntentService.merge(intent);
			    	    				count++;
			    	    			}
			    	    		}
		    	    		}
    				}else{//如果不是则按照流水号进行查找操作
	    				//list3 = new ArrayList();
	    				QueryFilter filter = new QueryFilter();
	    	    		filter.addFilter("Q_orderNo_S_EQ", record.getBidRequestNo());
	    	    		filter.addFilter("Q_payintentPeriod_N_EQ", Integer.valueOf(commonRequst.getRemark1()));
	    	    		//filter.addFilter("Q_isValid_SN_EQ", (short)1);
	    	    		List<BpFundIntent> list1 = bpFundIntentService.getAll(filter);
	    	    		if(list1.size()>0){
		    	    		for(BpFundIntent intent : list1){
		    	    			//平台费用不还给投资人
		    	    			if(intent.getFundType()!=null&&!intent.getFundType().equals("serviceMoney")&&!intent.getFundType().equals("consultationMoney")&&intent.getFactDate()==null){
		    	    				intent.setRequestNo(requestNo1);
		    	    				bpFundIntentService.merge(intent);
		    	    				count++;
		    	    			}
		    	    		}
	    	    		}
    				}
    	    		CommonResponse commonResponse1 = new CommonResponse();
    	    		CommonRequst requst = new CommonRequst();
    	    		if(count>0){
	    				requst.setThirdPayConfigId(commonRequst.getThirdPayConfigId());//出账用户支付账号
	    				requst.setLoaner_thirdPayflagId(record.getInvest_thirdPayConfigId());//入账用户支付账号
	    				requst.setContractNo("");//预授权合同号
	    				requst.setRequsetNo(requestNo1);
	    				requst.setAmount(record.getAmount().subtract(record.getFee()));
	    				commonResponse1 = FuiouInterfaceUtil.allot(requst);
	    				commonResponse1.setRequestNo(requestNo1);
	    				list3.add(commonResponse1);
    	    		}
    			}
    			response.setResponseList(list3);
    			response.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
    			commonResponse = response;
    		}
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMAUTH)){//理财计划授权平台派息  
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("理财计划授权平台派息成功");
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMGIVEUSER)){//理财计划派息    
			commonRequst.setThirdPayConfigId(commonRequst.getThirdPayConfigId());//出账用户支付账号
			commonRequst.setLoaner_thirdPayflagId(commonRequst.getInvest_thirdPayConfigId());//入账用户支付账号
			commonResponse = FuiouInterfaceUtil.allot(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMCANCELUSER)){//理财计划流标
			commonRequst.setContractNo(commonRequst.getLoanNoList());
			commonResponse = FuiouInterfaceUtil.preAuthorizationCancel(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMSIGNOUTUSER)){//理财计划提前退出  
			//commonRequst.setThirdPayConfigId(commonRequst.getThirdPayConfigId());//出账用户支付账号
			commonRequst.setLoaner_thirdPayflagId(commonRequst.getInvest_thirdPayConfigId());//入账用户支付账号
			commonResponse = FuiouInterfaceUtil.allot(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELDEAL)){//取消挂牌
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("操作成功");
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_FALSESERVICE)){//挂牌交易服务费将预收转实收后退费
			commonRequst.setLoaner_thirdPayflagId(commonRequst.getThirdPayConfigId());             
			commonRequst.setThirdPayConfigId("");
			commonResponse = FuiouInterfaceUtil.transfers(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_TRUESERVICE)){//挂牌交易服务费将预收转为实收
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("挂牌交易服务费将预收转为实收已经成功收取");
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMLOANUSER)){//理财计划起息(注册用户)  
			String str[] = commonRequst.getLoanNoList().split(",");
			String successLoanNo="";//成功的流水号
			String errorLoanNo="";//失败的流水号
			List<CommonRequestInvestRecord> loanList = commonRequst.getLoanList();
			for(CommonRequestInvestRecord list:loanList){
				String	orderNum=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				commonRequst.setContractNo(list.getPreAuthorizationNum());
				//commonRequst.setRemark("起息");
				commonRequst.setAmount(list.getAmount());
				commonRequst.setLoaner_thirdPayflagId(list.getLoaner_thirdPayConfigId());
				commonRequst.setThirdPayConfigId(list.getInvest_thirdPayConfigId());
				commonRequst.setRequsetNo(orderNum);
				commonResponse = FuiouInterfaceUtil.allot(commonRequst);
				if(CommonResponse.RESPONSECODE_SUCCESS.equals(commonResponse.getResponsecode())){
					if(!successLoanNo.equals("")){
						successLoanNo=successLoanNo+","+"'"+list.getPreAuthorizationNum()+"'";
					}else{
						successLoanNo="'"+list.getPreAuthorizationNum()+"'";
					}
				}else{
					errorLoanNo=errorLoanNo+","+list.getPreAuthorizationNum();
				}
				commonResponse.setSuccessLoanNo(successLoanNo);
				commonResponse.setErrorLoanNo(errorLoanNo);
			}
			if(successLoanNo.equals("")&&!errorLoanNo.equals("")){
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			}
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PREPAREPAY)){//平台代偿
			Map thirdMap=HuiFuInterfaceUtil.HuifuProperty();
			String plateFormNo = thirdMap.get("thirdPay_Huifu_platNumber").toString(); 
			commonRequst.setOutCustId(plateFormNo);//该参数只能传商户
			commonRequst.setOutAcctId("MDT000001");//出账客户号
			commonRequst.setMerPriv("");//商户私有域
    		List<CommonRequestInvestRecord> list = commonRequst.getRepaymemntList();
    		CommonResponse response = new CommonResponse();
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
    				requst.setThirdPayConfigId("");//出账用户支付账号
    				requst.setLoaner_thirdPayflagId(record.getInvest_thirdPayConfigId());//入账用户支付账号 record.getInvest_thirdPayConfigId()
    				requst.setContractNo("");//预授权合同号
    				requst.setRequsetNo(requestNo1);
    				requst.setAmount(record.getAmount().subtract(record.getFee()));
    				if(record.getAmount().subtract(record.getFee()).compareTo(new BigDecimal("0"))>0){
    					commonResponse1 = FuiouInterfaceUtil.transfers(requst);
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
		}else{
    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_NOTBUSINESS);
	    	commonResponse.setResponseMsg("没有该业务类型");
    	}
		return commonResponse;
	}

	@Override
	public CommonResponse businessReturn(Map maps,HttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String reqData=maps.get("reqData").toString();
			System.out.println("返回的密文参数：" + reqData);
//			reqData = DecryptUtil.decryptByPrivateKey(reqData, CommonPro.getDemoCustomPrivateKey());
//			System.out.println("解密结果：" + reqData);
			JSONObject resObj = JSON.parseObject(reqData);
			String data = resObj.getString("data");
			String content = null;
			if (data != null) {
				content = JSON.parseObject(data).getString("content");
			} else {
				content = resObj.getString("content");
			}
			String requestNo = JSON.parseObject(content).getString("orderNo");//流水号
			commonResponse.setRequestNo(requestNo);
			
			String req = null; 
			if (data != null) {
				req = JSON.parseObject(data).getString("retCode");
			} else {
				req = resObj.getString("retCode");
			}
			String retMsg = null; 
			if (data != null) {
				retMsg = JSON.parseObject(data).getString("retMsg");
			} else {
				retMsg = resObj.getString("retMsg");
			}
			 if(req!=null&&(req.equals("0000"))){//成功标志   说明处理成功，并非结果  结果需要根据各自的状态分别判断
				//处理返回参数并封装
				commonResponse = deal(commonResponse, content);
				if (commonResponse.getActiveStatus().equals("1")) {//成功
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);//操作成功
				}else if(commonResponse.getActiveStatus().equals("2")) {//处理中
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);//操作成功
					commonResponse.setResponseMsg("申请成功，正在处理中。。。");
				}else {
					commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);//操作失败
					commonResponse.setResponseMsg("操作失败");
				}
	        }else{
	        	commonResponse.setResponseMsg(retMsg);
	        	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);//操作失败
	        }
				commonResponse.setRequestInfo(req+""+reqData);//返回数据包
		} catch (Exception e) {
			e.printStackTrace();
			 commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);//操作失败
		}
		return commonResponse;
	}

	
	/**
	 * 处理返回的参数
	 * @param commonResponse
	 * @param content
	 * @return
	 * 2017-8-29
	 * @tzw
	 */
	private CommonResponse deal(CommonResponse commonResponse,String content) {
		//业务处理结果  默认成功
		String state = "1";
		commonResponse.setActiveStatus(state);
		//如果成功一定会有这2个参数
		String accountNo = JSON.parseObject(content).getString("accountNo");//三方账号1
		String userName = JSON.parseObject(content).getString("userName");//三方账号2
		commonResponse.setThirdPayConfigId(accountNo);
		commonResponse.setThirdPayConfigId0(userName);
		
		//备注---传的是业务类型
		if (content.contains("extMark")) {
			String extMark = JSON.parseObject(content).getString("extMark");
			System.out.println("业务类型：" + extMark);
			//根据业务类型修改   企业/个人开户没有特殊值返回，所以不单独做处理，
			
			//处理绑卡.换卡数据  
			//TODO 银行bug 没有返回exmark值所以先放到外边
			putBindCard(commonResponse, content, state);
			if (extMark.equals(ThirdPayConstants.BT_BINDCARD)||extMark.equals(ThirdPayConstants.BT_REPLACECARD)) {
				putBindCard(commonResponse, content, state);
			}
			//更换手机号
			if (extMark.equals(ThirdPayConstants.BT_UPDATEPHONE)) {
				state = putChangePhone(commonResponse, content, state);
			}
			//充值
			if (extMark.equals(ThirdPayConstants.BT_RECHAGE)) {
				state = putRechage(commonResponse, content, state);
			}
			//取现
			if (extMark.equals(ThirdPayConstants.BT_WITHDRAW)) {
				state = putWithdraw(commonResponse, content, state);
			}
			//放款成功
			if (extMark.equals(ThirdPayConstants.BT_LOAN)) {
				//标的状态：0：开标、1：投标中、2：还款中、3：已还款、4：结束、5：撤标
				if (content.contains("status")) {
					String status = JSON.parseObject(content).getString("status");
					if (status.equals("2")) {
						state = "1";
					}else if(status.equals("4") || status.equals("5")) {
						state = "0";
					}else {
						state = "2";
					}
					//统一为系统状态，0不成功，1成功，2办理中
					commonResponse.setActiveStatus(state);
				}
				
				//借款管理费
				if (content.contains("loanFee")) {
					String loanFee = JSON.parseObject(content).getString("loanFee");
					commonResponse.setFee(loanFee);
				}
				//标的号
				if (content.contains("loanTxNo")) {
					String loanTxNo = JSON.parseObject(content).getString("loanTxNo");
					commonResponse.setLoanTxNo(loanTxNo);
				}
				
				//标的名称
				/*if (content.contains("loanName")) {
					String loanName = JSON.parseObject(content).getString("loanName");
					commonResponse.setloan(loanName);
				}*/
				
			}
			
		}
		//手机号
		if (content.contains("mobilePhone")) {
			String mobilePhone = JSON.parseObject(content).getString("mobilePhone");
			commonResponse.setNewMobile(mobilePhone);
		}
		//企业开户时审核状态  2 通过, 3 不通过
		if (content.contains("verifyStatus")) {
			String verifyStatus = JSON.parseObject(content).getString("verifyStatus");
			if (verifyStatus.equals("3")) {
				state = "0";
			}
			//统一为系统状态，0不成功，1成功，2办理中
			commonResponse.setActiveStatus(state);
		}
		return commonResponse;
	}

	/**
	 * 处理取现接口返回参数 
	 * @param commonResponse
	 * @param content
	 * @param state
	 * @return
	 * 2017-8-31
	 * @tzw
	 */
	private String putWithdraw(CommonResponse commonResponse, String content,
			String state) {
		//取现状态  0未处理 ;1提现成功（已扣款）；2提现失败；3银行退单成功；4提现申请成功（冻结资金）
		if (content.contains("status")) {
			String status = JSON.parseObject(content).getString("status");
			if (status.equals("2") || status.equals("3")) {
				state = "0";
			}else if(status.equals("0") || status.equals("4")) {
				state = "2";
			}
			//统一为系统状态，0不成功，1成功，2办理中
			commonResponse.setActiveStatus(state);
		}
		//取现金额
		if (content.contains("amount")) {
			String amount = JSON.parseObject(content).getString("amount");
			commonResponse.setAmount(amount);
		}
		//实际到账金额
		if (content.contains("receivedAmount")) {
			String receivedAmount = JSON.parseObject(content).getString("receivedAmount");
			commonResponse.setReceivedAmount(receivedAmount);
		}
		//手续费
		if (content.contains("fee")) {
			String fee = JSON.parseObject(content).getString("fee");
			commonResponse.setFee(fee);
		}

		//提现银行卡号
		if (content.contains("bankCardNo")) {
			String bankCardNo = JSON.parseObject(content).getString("bankCardNo");
			commonResponse.setBankCardNumber(bankCardNo);
		}
		
		//银行编码
		if (content.contains("bankCode")) {
			String bankCode = JSON.parseObject(content).getString("bankCode");
			commonResponse.setBankCode(bankCode);
		}
		
		//提现银行名称
		if (content.contains("bankName")) {
			String bankName = JSON.parseObject(content).getString("bankName");
			commonResponse.setBankName(bankName);
		}
		return state;
	}
	
	
	
	/**
	 * 处理充值返回数据
	 * @param commonResponse
	 * @param content
	 * @param state
	 * @return
	 * 2017-8-31
	 * @tzw
	 */
	private String putRechage(CommonResponse commonResponse, String content,
			String state) {
		//充值状态   1：充值成功  2：充值失败  4：银行处理中
		if (content.contains("status")) {
			String status = JSON.parseObject(content).getString("status");
			if (status.equals("2")) {
				state = "0";
			}else if(status.equals("4")) {
				state = "2";
			}
			//统一为系统状态，0不成功，1成功，2办理中
			commonResponse.setActiveStatus(state);
		}
		//充值金额
		if (content.contains("amount")) {
			String amount = JSON.parseObject(content).getString("amount");
			commonResponse.setAmount(amount);
		}
		//实际到账金额
		if (content.contains("receivedAmount")) {
			String receivedAmount = JSON.parseObject(content).getString("receivedAmount");
			commonResponse.setReceivedAmount(receivedAmount);
		}
		//手续费
		if (content.contains("fee")) {
			String fee = JSON.parseObject(content).getString("fee");
			commonResponse.setFee(fee);
		}
		return state;
	}

	/**
	 * 处理更换手机号业务数据
	 * @param commonResponse
	 * @param content
	 * @param state
	 * @return
	 * 2017-8-31
	 * @tzw
	 */
	private String putChangePhone(CommonResponse commonResponse,
			String content, String state) {
		//更换手机号状态   1代表更新手机号成功   2代表更新手机号失败\人工申请失败   3代表用户人工申请成功
		if (content.contains("status")) {
			String status = JSON.parseObject(content).getString("status");
			if (status.equals("2")) {
				state = "0";
			}
			//统一为系统状态，0不成功，1成功，2办理中
			commonResponse.setActiveStatus(state);
		}
		
		//手机号
		if (content.contains("newPhone")) {
			String newPhone = JSON.parseObject(content).getString("newPhone");
			commonResponse.setNewMobile(newPhone);
		}
		//申请协议号   预留字段，暂时不用
		/*if (content.contains("protocolNo")) {
			String protocolNo = JSON.parseObject(content).getString("protocolNo");
			//commonResponse.setNewMobile(newPhone);
		}*/
		return state;
	}

	
	/**
	 * 处理绑卡.换卡返回数据
	 * @param commonResponse
	 * @param content
	 * @param state
	 * 2017-8-31
	 * @tzw
	 */
	private void putBindCard(CommonResponse commonResponse, String content,
			String state) {
		//绑卡状态      0-绑卡中，1-绑卡成功，2-绑卡失败
		if (content.contains("bindStatus")) {
			String bindStatus = JSON.parseObject(content).getString("bindStatus");
			if (bindStatus.equals("2")) {
				state = "0";
			}else if(bindStatus.equals("0")) {
				state = "2";
			}
			//统一为系统状态，0不成功，1成功，2办理中
			commonResponse.setActiveStatus(state);
		}
		
		//更换银行卡状态     0-待审核，1-审核通过，2-审核不通过，3-鉴权不通过
		if (content.contains("status")) {
			String status = JSON.parseObject(content).getString("status");
			if (status.equals("2") || status.equals("3")) {
				state = "0";
			}else if(status.equals("0")) {
				state = "2";
			}
			//统一为系统状态，0不成功，1成功，2办理中
			commonResponse.setActiveStatus(state);
		}
		
		//绑卡银行
		if (content.contains("bank")) {
			String bank = JSON.parseObject(content).getString("bank");
			commonResponse.setBankName(bank);
		}
		
		//绑定银行卡号    绑定的银行卡号，可用于提现
		if (content.contains("bankAccountNo")) {
			String bankAccountNo = JSON.parseObject(content).getString("bankAccountNo");
			commonResponse.setBankCode(bankAccountNo);
		}
		
		//绑定银行卡号    绑定的银行卡号，可用于提现
		if (content.contains("bankAccountNo")) {
			String bankAccountNo = JSON.parseObject(content).getString("bankAccountNo");
			commonResponse.setBankCode(bankAccountNo);
		}
		//银行对应编码
		if (content.contains("bankCode")) {
			String bankCode = JSON.parseObject(content).getString("bankCode");
			commonResponse.setBankCardNumber(bankCode);
		}
		
	}

	@Override
	public Object[] checkThirdType(String businessType, String type) {
		Object[] thirdType = new Object[2];
		//富滇满标放款不用传放款用户
		if((businessType.equals(ThirdPayConstants.BT_MMLOANUSER)||businessType.equals(ThirdPayConstants.BT_LOAN))&&type==null){//理财计划注册用户起息
			//one表示 单个起息。all表示批量起息
			thirdType[0]="all";
			thirdType[1]=CommonResponse.RESPONSECODE_SUCCESS;
    	}else if(businessType.equals(ThirdPayConstants.BT_LOAN)&&type.equals("bid")&&!"".equals(type)){
//			thirdType[0]="one";
    		thirdType[0]="all";
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
