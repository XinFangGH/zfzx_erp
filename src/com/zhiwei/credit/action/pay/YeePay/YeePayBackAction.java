package com.zhiwei.credit.action.pay.YeePay;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;

import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.action.pay.YeePay.YeePayBackAction;
import com.zhiwei.credit.service.thirdInterface.OpraterBussinessDataService;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;
import com.zhiwei.credit.model.thirdInterface.YeePay;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.thirdInterface.WebBankcardService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.util.YeePayConfig.SignUtil;






public class YeePayBackAction extends BaseAction {
	//log4j日志文件记录
	protected Log logger=LogFactory.getLog(YeePayBackAction.class);
	@Resource
	private OpraterBussinessDataService opraterBussinessDataService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	/**
	 * 验证网关接口数据返回接口
	 * @return
	 */
	public String webBackValue(){
		try{
			System.out.println("回调通知开始");
			String notify=this.getRequest().getParameter("notify");
			logger.info("回调通知开始notify==="+notify);
			String sign=this.getRequest().getParameter("sign");
			logger.info("回调通知开始sign==="+sign);
			Boolean isSign=yeePayService.verifySign(notify, sign);
			logger.info("回调通知开始sign==="+sign);
			if(isSign){
				Document doc=XmlUtil.stringToDocument(notify);
				String ret="";
				String service =doc.selectSingleNode("/notify/bizType").getText();
				if(service.equals("REGISTER")){//注册
					ret=this.doRegiest(doc);
				}else if(service.equals("RECHARGE")){//充值
					ret=this.doRecharge(doc);
				}else if(service.equals("WITHDRAW")){//取现
					ret=this.doWithDraw(doc);
				}else if(service.equals("BIND_BANK_CARD")){//绑银行卡
					ret=this.doBindBankCard(doc);
				}else if(service.equals("FREEZE")){//投标冻结
					String JZWWmethod=this.getRequest().getParameter("JZWWmethod");
					if(JZWWmethod!=null){
						ret=this.doAutoBuyMoneyPlan(doc);
					}else{
						ret=this.doAutoBuy(doc);
					}
				}else if(service.equals("REPAYMENT")){//借款人自助还款
					ret=this.doRepayment(doc);
				}else if(service.equals("AUTHORIZE_AUTO_TRANSFER")){//自动投标授权接口返回参数
					this.doAuthorizeAutoTransfer(doc);
				}else if(service.equals("AUTHORIZE_AUTO_REPAYMENT")){//自动还款授权接口返回参数
					this.doAuthorizeAutoRepayMent(doc);
				}else if(service.equals("TRANSFER_CLAIMS")){//债权交易服务器端通知
					this.doObligationDeal(doc);
				}else if(service.equals("TRANSACTION")){
					if(doc.selectSingleNode("/notify/status")==null){
						this.doObligationPublish(doc);
					}
				}
				setJsonString("SUCCESS");
			}else{
				setJsonString("FAIL");
			}
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("FAIL");
		}
		
		return SUCCESS;
	}
	/**
	 * 理财计划购买方法（业务数据处理）
	 * @param doc
	 * @return
	 */
	private String doAutoBuyMoneyPlan(Document doc) {
		System.out.println("回调调用时间"+new Date());
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String requestNo=doc.selectSingleNode("/notify/requestNo").getText();
			synchronized (requestNo) {
				if(code.equals("1")){
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId", "");
					map.put("custmerType", "0");
					map.put("requestNo",requestNo);
					map.put("dealRecordStatus","2");
					//String[]  bussinessResult=opraterBussinessDataService.bidMoneyPlan(map);
				}else{
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId", "");
					map.put("custmerType", "0");
					map.put("requestNo",requestNo);
					map.put("dealRecordStatus","3");
					String[]  bussinessResult=opraterBussinessDataService.biding(map);
				}
			}
		}else{
			ret="FAIL";
		}
		return ret;
	}
	/**
	 * 通用转账的接口（暂时用作债权交易挂牌）
	 * @param doc
	 */
	private String doObligationPublish(Document doc) {
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String orderNo=doc.selectSingleNode("/notify/requestNo").getText();
			synchronized (orderNo){
				if(code.equals("1")){
					Map<String ,String> map=new HashMap<String,String>();
					map.put("requestNo",orderNo);
					map.put("dealstatus", "2");
					//String[]  bussinessResult=opraterBussinessDataService.doObligationPublish(map);
				}else{
					Map<String ,String> map=new HashMap<String,String>();
					map.put("requestNo",orderNo);
					map.put("dealstatus", "0");
					//String[]  bussinessResult=opraterBussinessDataService.doObligationPublish(map);
				}
			}
		}else{
			
		}
		return ret;
		
	}
	/**
	 * 
	 * @param doc
	 * @return
	 */
	private String doObligationDeal(Document doc) {
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String orderNo=doc.selectSingleNode("/notify/requestNo").getText();
			synchronized (orderNo){
				if(code.equals("1")){
					Map<String ,String> map=new HashMap<String,String>();
					map.put("requestNo",orderNo);
					map.put("dealstatus", "2");
					//String[]  bussinessResult=opraterBussinessDataService.doObligationDeal(map);
				}else{
					Map<String ,String> map=new HashMap<String,String>();
					map.put("requestNo",orderNo);
					map.put("dealstatus", "0");
					//String[]  bussinessResult=opraterBussinessDataService.doObligationDeal(map);
				}
			}
		}else{
			
		}
		return ret;
		
	}
	private String doAuthorizeAutoRepayMent(Document doc) {
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String orderNo=doc.selectSingleNode("/notify/orderNo").getText();
			synchronized (orderNo){
				if(code.equals("1")){
					Map<String ,String> map=new HashMap<String,String>();
					map.put("orderNo",  orderNo.split("-")[1]);
					map.put("orderType",orderNo.split("-")[0]);
					map.put("requestNo","");
					if(orderNo.split("-")[0].equals("mmplan")){
						String []  rrett=opraterBussinessDataService.repaymentAuthorizationMoneyPlan(map);
					}else{
						String[]  bussinessResult=opraterBussinessDataService.repaymentAuthorization(map);
					}
					
				}
			}
		}else{
			
		}
		return ret;
		
	}
	/**
	 * 易宝借款人自助还款
	 * @param doc
	 * @return
	 */
	private String doRepayment(Document doc) {
		System.out.println("回调调用时间"+new Date());
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String requestNo=doc.selectSingleNode("/notify/requestNo").getText();
			synchronized (requestNo) {
				if(code.equals("1")){
					String ordId=doc.selectSingleNode("/notify/orderNo").getText();
					if(ordId.contains("-")){
						ordId=ordId.split("-")[1];
					}
					Map<String ,String> map=new HashMap<String,String>();
					map.put("orderNo", ordId);
					map.put("requestNo",requestNo);
					String[]  bussinessResult=opraterBussinessDataService.repayment(map);
					//平台派发优惠券，加息奖励
					if(bussinessResult[0]==Constants.CODE_SUCCESS){
						System.out.println("平台派发优惠券，加息奖励");
						List<BpFundIntent> list= bpFundIntentService.getByRequestNo(requestNo);
						//平台发送奖励金额
						opraterBussinessDataService.checkCouponsIntent(list.get(0).getBidPlanId().toString(),list.get(0).getPayintentPeriod().toString(),requestNo,this.getBasePath());
					}
				}
			}
		}else{
			ret="FAIL";
		}
		return ret;
	}
	
	/**
	 * 易宝支付投标
	 * @param doc
	 * @return
	 */
	private String doAutoBuy(Document doc) {
		System.out.println("回调调用时间"+new Date());
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String requestNo=doc.selectSingleNode("/notify/requestNo").getText();
			synchronized (requestNo) {
				if(code.equals("1")){
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId", "");
					map.put("custmerType", "0");
					map.put("requestNo",requestNo);
					map.put("dealRecordStatus","2");
					String[]  bussinessResult=opraterBussinessDataService.biding(map);
				}else{
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId", "");
					map.put("custmerType", "0");
					map.put("requestNo",requestNo);
					map.put("dealRecordStatus","3");
					String[]  bussinessResult=opraterBussinessDataService.biding(map);
				}
			}
		}else{
			ret="FAIL";
		}
		return ret;
	}
	/**
	 * 易宝绑卡回调方法数据
	 * @param doc
	 * @return
	 */
	private String doBindBankCard(Document doc) {
		// TODO Auto-generated method stub
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String platformUserNo=doc.selectSingleNode("/notify/platformUserNo").getText();
			String cardNo=doc.selectSingleNode("/notify/cardNo").getText();
			String cardStatus=doc.selectSingleNode("/notify/cardStatus").getText();
			String bank=doc.selectSingleNode("/notify/bank").getText();
			String requestNo=doc.selectSingleNode("/notify/requestNo").getText();
			synchronized (platformUserNo){
				if(code.equals("1")){
					Properties p=yeePayService.getyeePayProperties();
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId",platformUserNo.split("-")[1]);
					map.put("requestNo",requestNo);
					map.put("custmerType", "0");
					map.put("bankCardNo",cardNo);
					map.put("bankCode",bank);
					if(bank!=null){
						map.put("bankName",p.getProperty(bank));
					}
					if(cardStatus.equals("VERIFYING")){
						map.put("bankstatus","bindCard_status_accept");
					}else{
						map.put("bankstatus","bindCard_status_success");
					}
					String[]  bussinessResult=opraterBussinessDataService.bandCard(map);
				}else{
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId",platformUserNo.split("-")[1]);
					map.put("requestNo",requestNo);
					map.put("custmerType", "0");
					map.put("bankCardNo",cardNo);
					map.put("bankCode",bank);
					map.put("bankstatus","bindCard_status_faild");
					String[]  bussinessResult=opraterBussinessDataService.bandCard(map);
				}
			}
		}else{
			ret="FAIL";
		}
		return ret;
	
	
	}
	/**
	 * 易宝取现erp处理方法
	 * @param doc
	 * @return
	 */
	private String doWithDraw(Document doc) {
		// TODO Auto-generated method stub
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String platformUserNo=doc.selectSingleNode("/notify/platformUserNo").getText();
			String requestNo=doc.selectSingleNode("/notify/requestNo").getText();
			synchronized (requestNo) {
				if(code.equals("1")){
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId", platformUserNo.split("-")[1]);
					map.put("requestNo",requestNo);
					map.put("custmerType", "0");
					map.put("dealRecordStatus","2");
					String[]  bussinessResult=opraterBussinessDataService.withDraw(map);
				}else{
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId", platformUserNo.split("-")[1]);
					map.put("requestNo",requestNo);
					map.put("custmerType", "0");
					map.put("dealRecordStatus","3");
					String[]  bussinessResult=opraterBussinessDataService.withDraw(map);
				}
			}
		}else{
			ret="FAIL";
		}
		return ret;
	}
	/**
	 * 易宝充值回调方法
	 * @param doc
	 * @return
	 */
	private String doRecharge(Document doc) {
		// TODO Auto-generated method stub
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String platformUserNo=doc.selectSingleNode("/notify/platformUserNo").getText();
			String requestNo=doc.selectSingleNode("/notify/requestNo").getText();
			synchronized (requestNo) {
				if(code.equals("1")){
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId", platformUserNo.split("-")[1]);
					map.put("requestNo",requestNo);
					map.put("custmerType", "0");
					map.put("dealRecordStatus","2");
					String[]  bussinessResult=opraterBussinessDataService.recharge(map);
				}else{
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId", platformUserNo.split("-")[1]);
					map.put("requestNo",requestNo);
					map.put("custmerType", "0");
					map.put("dealRecordStatus","3");
					String[]  bussinessResult=opraterBussinessDataService.recharge(map);
				}
		
			}
		}
		return ret;
	
	}
	/**
	 * 易宝注册回调方法
	 * @param doc
	 * @return
	 */
	private String doRegiest(Document doc) {
		// TODO Auto-generated method stub
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String platformUserNo=doc.selectSingleNode("/notify/platformUserNo").getText();
			synchronized (platformUserNo){
				if(code.equals("1")){
					String[] num=platformUserNo.split("-");
					String customerId=num[num.length-2];
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId",customerId);
					map.put("custermemberType", "0");
					map.put("platformUserNo", platformUserNo);
					map.put("platFormUserName",platformUserNo);
					String[]  bussinessResult=opraterBussinessDataService.regedit(map);
				}
			}
		}
		return ret;
	}
	/**
	 * 处理自动投标授权情况
	 * @param doc
	 * @return
	 */
	public  String doAuthorizeAutoTransfer(Document doc) {
		String ret="SUCCESS";
		if(doc!=null&&!"".equals(doc)){
			String code =doc.selectSingleNode("/notify/code").getText();
			String platformUserNo=doc.selectSingleNode("/notify/platformUserNo").getText();
			synchronized (platformUserNo){
				if(code.equals("1")){
					Map<String ,String> map=new HashMap<String,String>();
					map.put("custermemberId",  platformUserNo.split("-")[1]);
					map.put("custmerType", "0");
					map.put("requestNo","");
					String[]  bussinessResult=opraterBussinessDataService.bidingAuthorization(map);
				}
			}
		}else{
			
		}
		return ret;
		
	}
}