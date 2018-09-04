package com.thirdInterface.pay.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hurong.core.util.DateUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.action.pay.FontAction;
import com.zhiwei.credit.action.pay.FontHuiFuAction;
import com.zhiwei.credit.core.creditUtils.MD5;
import com.zhiwei.credit.core.creditUtils.StringUtils;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.pay.LoanTransferReturnBean;
import com.zhiwei.credit.model.pay.MadaiLoanInfoBean;
import com.zhiwei.credit.model.pay.MoneyMoreMore;
import com.zhiwei.credit.model.pay.ResultBean;
import com.zhiwei.credit.model.thirdInterface.Fuiou;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.thirdInterface.FuiouService;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;
import com.zhiwei.credit.util.RsaHelper;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;
/**
 * 富有实现 接口
 * @author YUAN.zc
 *
 */
public class FUThirdPayServiceImpl implements ThirdPayEngine {
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private FuiouService fuiouService;
	@Resource
	private PlBidPlanService plBidPlanService;
	public FUThirdPayServiceImpl(){
		/*Map map=AppUtil.initPropertes(this.PROSTR);
		MM_PostUrl=map.get("MM_PostUrl").toString();
		MM_PlatformMoneymoremore=map.get("MM_PlatformMoneymoremore").toString();
		MM_PrivateKey=map.get("MM_PrivateKey").toString();
		MM_PublicKey=map.get("MM_PublicKey").toString();*/
	}

	@Override
	public String[] repayment(SlFundIntent slFundIntent,List<BpFundIntent> bpFundIntentList,BpCustMember outMem,BigDecimal fullMoney, String basePath, String postType,
			HttpServletResponse rep, HttpServletRequest req, String remk0,String remk1, String remk2) {
		String[] retArr=new String[2];
		//标
		PlBidPlan plBidPlan=null;
		BigDecimal totalMoney=new BigDecimal(0);
		StringBuffer buff=new StringBuffer();
		BpCustMember inmem=null;
		try{
		 for(BpFundIntent bpfund:bpFundIntentList){
			 String[] FUArr=new String[3];
			 //还款金额
			 String amtt=bpfund.getIncomeMoney().multiply(new BigDecimal(100)).setScale(0).toString();
			 //获取标
			 plBidPlan=plBidPlanService.get(bpfund.getBidPlanId());
			 //收款人
			 inmem=bpCustMemberService.get(bpfund.getInvestPersonId());
			 //债权标  平台 -投资人
			 FUArr= repaymentFU(plBidPlan,slFundIntent, bpfund, outMem.getThirdPayFlagId(), inmem.getThirdPayFlagId(), amtt);
			 
			 if(FUArr!=null&&FUArr[0].equals(Constants.CODE_SUCCESS)){
				 totalMoney=totalMoney.add(new BigDecimal(FUArr[2]));
				 
				 buff.append("为投资人 "+bpfund.getInvestPersonName()+"还款："+FUArr[1]);
				 buff.append(",");
				 //更新投资人款项台帐
				 updateBpfundIntent(bpfund, bpfund.getIncomeMoney(), new BigDecimal(0));
			 }else{
				 buff.append("为投资人 "+bpfund.getInvestPersonName()+"还款失败， 失败原因："+FUArr[1]);
				 buff.append(",");
			 }
			 // 更新借款人款项台帐
			 updateSlfundIntent(slFundIntent, totalMoney);
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

	/**
	 * 还款 调用富有接口
	 * @param temp
	 * @param outMemId
	 * @param inMemId
	 * @param amtt
	 * @param remark
	 * @return  retArr 3
	 */
	private String[] repaymentFU(PlBidPlan plBidPlan,SlFundIntent sf,BpFundIntent temp,String outMemId, String inMemId,String amt ){
		String[] retArr =new String[3];
		BigDecimal totalMoney=new BigDecimal(0);
		String	orderNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
		
		String[] ret=null;
		
		 if(plBidPlan.getProType()!=null &&(plBidPlan.getProType().equals("P_Or")||plBidPlan.getProType().equals("B_Or"))){
			  ret=fuiouService.traAcc(orderNum, outMemId,inMemId, amt, "", "平台还款到投资人");
		 }else if(plBidPlan.getProType()!=null &&(plBidPlan.getProType().equals("P_Dir")||plBidPlan.getProType().equals("B_Dir"))){//直投标 借款人 -投资人
			 ret= fuiouService.transferPersonToPerson(orderNum, outMemId, inMemId, amt, "", "借款人给投资人还本付息");
			 
		 }
		
		if(ret[0].equals(Constants.CODE_SUCCESS)){
			temp.setAfterMoney(temp.getIncomeMoney());
			totalMoney=totalMoney.add(temp.getIncomeMoney());
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("investPersonId",bpCustMemberService.getMemberByFlagId(outMemId).getId());//投资人Id（必填）
			map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			map.put("transferType",ObAccountDealInfo.T_LOANPAY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			map.put("money",temp.getIncomeMoney());//交易金额	（必填）			 
			map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			map.put("recordNumber",orderNum+"-1");//交易流水号	（必填）
			map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			String[] rett=obAccountDealInfoService.operateAcountInfo(map);
			//String[] rett=obAccountDealInfoService.operateAcountInfo(bpCustMemberService.getMemberByFlagId(outMemId).getId().toString(), ObAccountDealInfo.T_LOANPAY.toString(), temp.getIncomeMoney().toString(), "3", "0", "1", "2", orderNum+"-1");
			Map<String,Object> mapO=new HashMap<String,Object>();
			mapO.put("investPersonId",temp.getInvestPersonId());//投资人Id（必填）
			mapO.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			mapO.put("transferType",temp.getFundType().equals("loanInterest")?ObAccountDealInfo.T_PROFIT:ObAccountDealInfo.T_PRINCIALBACK);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			mapO.put("money",temp.getIncomeMoney());//交易金额	（必填）			
			mapO.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			mapO.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			mapO.put("recordNumber",orderNum+"-0");//交易流水号	（必填）
			mapO.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			String[] rettt=obAccountDealInfoService.operateAcountInfo(mapO);
			//String[] rettt=obAccountDealInfoService.operateAcountInfo(temp.getInvestPersonId().toString(), temp.getFundType().equals("loanInterest")?ObAccountDealInfo.T_PROFIT.toString():ObAccountDealInfo.T_PRINCIALBACK.toString(), temp.getIncomeMoney().toString(), "3", "0", "1", "2", orderNum+"-0");
			System.out.println(rett[1]);
			System.out.println(rettt[1]);
			retArr[0]=Constants.CODE_SUCCESS;
			retArr[1]="成功!";
			retArr[2]=totalMoney.toString();
			
		}else{
			retArr[0]=Constants.CODE_FAILED;
			retArr[1]="富有金账户转账失败-失败编码"+ret[1]+"";
			retArr[2]="0";
		}
	
		return retArr;
	}
   /**
    * 更新款项台帐
    * @param slFundIntent
    * @param totalMoney
    */
	private void updateSlfundIntent(SlFundIntent slFundIntent,BigDecimal totalMoney ){
		slFundIntent.setAfterMoney(totalMoney);
		slFundIntent.setNotMoney(slFundIntent.getNotMoney().subtract(totalMoney));
		slFundIntentService.merge(slFundIntent);
	}
	
	/**
	 * 更新投资人款项台帐
	 * @param bpfund
	 * @param afterMoney
	 * @param notMoney
	 */
	private void updateBpfundIntent(BpFundIntent bpfund, BigDecimal afterMoney,BigDecimal notMoney ){
		bpfund.setAfterMoney(bpfund.getIncomeMoney());
		 bpfund.setNotMoney(new BigDecimal(0));
		 bpfund.setFactDate(new Date());
		 bpFundIntentService.save(bpfund);
	}
	 
}
