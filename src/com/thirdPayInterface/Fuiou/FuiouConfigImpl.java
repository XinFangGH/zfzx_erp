package com.thirdPayInterface.Fuiou;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thirdPayInterface.CommonRequestInvestRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayTypeInterface;
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

public class FuiouConfigImpl implements ThirdPayTypeInterface {
	static  BpFundIntentService bpFundIntentService = (BpFundIntentService) AppUtil.getBean("bpFundIntentService");
	static  PlBidInfoService plBidInfoService = (PlBidInfoService)AppUtil.getBean("plBidInfoService");
	static  SlActualToChargeService slActualToChargeService = (SlActualToChargeService)AppUtil.getBean("slActualToChargeService");
	/**balanceQuery
	 * 富友
	 */
	@Override
	public CommonResponse businessHandle(CommonRequst commonRequst) {
		CommonResponse commonResponse = new CommonResponse();
		if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CREATEBID)){//建立标的信息
    		//commonResponse=UMPayInterfaceUtil.createBidAccount(commonRequst);
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("操作成功");
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_UPDATEBID)){//ThirdPayConstants.BT_UPDATEBID
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			commonResponse.setResponseMsg("操作成功");
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_LOAN)){//BT_LOAN
			commonResponse = FuiouInterfaceUtil.allot(commonRequst);
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
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_LOANAUTH)){
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
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELBID)){//标的流标
			commonResponse = FuiouInterfaceUtil.preAuthorizationCancel(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_AUTOBID)){//自动投标
			commonResponse = FuiouInterfaceUtil.preAuthorization(commonRequst);
		}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYUSER)||commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BALANCEQUERY)){//用户余额查询接口
			commonResponse = FuiouInterfaceUtil.balanceQuery(commonRequst);
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
			commonRequst.setLoaner_thirdPayflagId(commonRequst.getThirdPayConfigId());             
			commonRequst.setThirdPayConfigId("");
			commonResponse = FuiouInterfaceUtil.transfers(commonRequst);
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
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 判断不同的第三方操作模式
	 */
	@Override
	public Object[] checkThirdType(String businessType,String type) {
		Object[] thirdType = new Object[2];
		if((businessType.equals(ThirdPayConstants.BT_MMLOANUSER)||businessType.equals(ThirdPayConstants.BT_LOAN))&&type==null){//理财计划注册用户起息
			//one表示 单个起息。all表示批量起息
			thirdType[0]="all";
			thirdType[1]=CommonResponse.RESPONSECODE_SUCCESS;
    	}else if(businessType.equals(ThirdPayConstants.BT_LOAN)&&type.equals("bid")&&!"".equals(type)){
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
