package com.thirdInterface.pay.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhiwei.core.Constants;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
/**
 * 实用于资金池模式 
 * @author YUAN.zc
 *
 */
public class ZWThirdPayServiceImpl implements ThirdPayEngine {
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private BpFundIntentService bpFundIntentService;

	public ZWThirdPayServiceImpl(){
		
	}

	@Override
	public String[] repayment(SlFundIntent slFundIntent,List<BpFundIntent> bpFundIntentList,BpCustMember outMem,BigDecimal fullMoney, String basePath, String postType,
			HttpServletResponse rep, HttpServletRequest req, String remk0,String remk1, String remk2) {
		String[] retArr=new String[2];
		//标
		StringBuffer buff=new StringBuffer();
		BpCustMember inmem=null;
		String Fee="0.00";//服务费
		try{
		 for(BpFundIntent bpfund:bpFundIntentList){
			String oweParams=remk0+"bfid:"+bpfund.getFundIntentId()+":"+bpfund.getNotMoney();
			 String[] ZWArr=new String[3];
			 //还款金额
			 String amtt=bpfund.getIncomeMoney().toString();
			 //收款人
			 inmem=bpCustMemberService.get(bpfund.getInvestPersonId());
			 List<BpFundIntent> riskRateList=bpFundIntentService.getByPlanIdAndPeriod(bpfund.getBidPlanId(), bpfund.getPayintentPeriod(), "riskRate",bpfund.getInvestPersonId(),bpfund.getOrderNo());
			if(riskRateList!=null&&riskRateList.size()>0&&bpfund.getFundType().equals("loanInterest")){
				Fee=riskRateList.get(0).getPayMoney().toString();
				//更新投资人服务费台帐
				BpFundIntent riskBpFund=riskRateList.get(0);
				riskBpFund.setAfterMoney(riskBpFund.getPayMoney());
				riskBpFund.setNotMoney(new BigDecimal(0));
				riskBpFund.setFactDate(new Date());
				bpFundIntentService.save(riskBpFund);
			}
			 // String Fee=).get(0).getIncomeMoney().toString();
			 //债权标  平台 -投资人
			 ZWArr=obAccountDealInfoService.updateAccountByRepayment(outMem.getThirdPayFlagId(), inmem.getThirdPayFlagId(), amtt, bpfund.getOrderNo(), Fee, oweParams,"I");
			 oweParams="";
			 if(ZWArr!=null&&ZWArr[0].equals(Constants.CODE_SUCCESS)){
				 
				 buff.append("为投资人 "+bpfund.getInvestPersonName()+"还款："+ZWArr[1]);
				 buff.append(",");
			 }else{
				 buff.append("为投资人 "+bpfund.getInvestPersonName()+"还款失败， 失败原因："+ZWArr[1]);
				 buff.append(",");
			 }
		 }
		 retArr[0]=Constants.CODE_SUCCESS;
		 retArr[1]=buff.toString();
		}catch (Exception e) {
			buff.append(e.getMessage());
			 retArr[0]=Constants.CODE_FAILED;
			 retArr[1]=buff.toString();
			e.printStackTrace();
		}
	  return retArr;
	}

	
	 
}
