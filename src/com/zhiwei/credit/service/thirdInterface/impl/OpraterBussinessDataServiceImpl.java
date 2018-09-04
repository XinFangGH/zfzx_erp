package com.zhiwei.credit.service.thirdInterface.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.messageAlert.service.SmsSendService;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayRecordService;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.auto.PlBidAuto;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatory;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;
import com.zhiwei.credit.service.activity.BpActivityManageService;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.auto.PlBidAutoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlEarlyRedemptionService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import com.zhiwei.credit.service.thirdInterface.OpraterBussinessDataService;
import com.zhiwei.credit.service.thirdInterface.WebBankcardService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.credit.util.Common;

public class OpraterBussinessDataServiceImpl implements OpraterBussinessDataService {
	//log4j日志文件记录
	protected Log logger=LogFactory.getLog(OpraterBussinessDataServiceImpl.class);
	
	@Resource
	public BpCustMemberService bpCustMemberService;
	@Resource
	public ObSystemAccountService obSystemAccountService;
	@Resource
	public WebBankcardService webBankcardService;
	@Resource
	public ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	public PlBidInfoService plBidInfoService;
	@Resource
	public InvestPersonInfoService investPersonInfoService;
	@Resource
	public PlBidPlanService plBidPlanService;
	@Resource
	public BpFundIntentService bpFundIntentService;
	@Resource
	public PlBidAutoService plBidAutoService;
	@Resource
	public YeePayService yeePayService;
	@Resource 
	private BpCouponsService bpCouponsService;
	@Resource 
	private BpActivityManageService bpActivityManageService;
	@Resource
	public BpCustMemberDao bpCustMemberDao;
	@Resource
	private PlManageMoneyPlanDao plManageMoneyPlanDao;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private ThirdPayRecordService thirdPayRecordService;
	@Resource
	private PlEarlyRedemptionService plEarlyRedemptionService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private SmsSendService smsSendService;
	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	@Resource
	private PlBidCompensatoryService plBidCompensatoryService;
	SmsSendUtil smsSendUtil= new SmsSendUtil();
	/**
	 * 开通第三方支付接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 *  map.get("custermemberId")   开通第三方支付的用户id  有的第三方无法获取注册用户在我们平台的id  则会传空值
	 *  map.get("custermemberType") 开通第三方支付的用户类型
	 *  map.get("platformUserNo");  第三方支付的账号
	 *  map.get("platFormUserName")  第三方支付的昵称
	 * @return
	 */
	@Override
	public String[] regedit(Map<String, String> map) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		String custmemberId=null;
		if(!"".equals(map.get("custermemberId"))){
			custmemberId=map.get("custermemberId");
		}else{
			
		}
		if(custmemberId!=null){
			String platformUserNo=map.get("platformUserNo");
			String platFormUserName=map.get("platFormUserName");
			BpCustMember bpCustMember=bpCustMemberService.get(Long.valueOf(custmemberId));
			//用来检查bpCustMember有没有进行实名认证过
			if(bpCustMember!=null&&(bpCustMember.getThirdPayConfig()!=null||!"".equals(bpCustMember.getThirdPayConfig()))&&(bpCustMember.getThirdPayFlagId()!=null||!"".equals(bpCustMember.getThirdPayFlagId()))){
				bpCustMember.setThirdPayFlag0(platFormUserName);
				bpCustMember.setThirdPayFlagId(platformUserNo);
				bpCustMember.setThirdPayConfig(configMap.get("thirdPayConfig").toString());
				if(configMap.get("system_authentication_type").toString().equals(Constants.SYSTEM_AUTHENTICATION_THIRDPAY)){
					if(bpCustMember.getCustomerType()!=null&&bpCustMember.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//企业客户
						bpCustMember.setThirdPayStatus(BpCustMember.THIRDPAY_DEACCTIVED);//未激活
					}else{
						if(bpCustMember.getCardcode()!=null){
							String year=bpCustMember.getCardcode().substring(6, 10);
							String month=bpCustMember.getCardcode().substring(10,12);
							String date=bpCustMember.getCardcode().substring(12,14);
							String strDate=year+"-"+month+"-"+date;
							Date birthday=java.sql.Date.valueOf(strDate);
							bpCustMember.setBirthday(birthday);
						}
						bpCustMember.setThirdPayStatus(BpCustMember.THIRDPAY_ACCTIVED);//已激活
						bpCustMember.setIsCheckCard("1");
					}
					
					String[] a =obSystemAccountService.saveAccount("1", bpCustMember.getTruename(), bpCustMember.getId().toString(), bpCustMember.getCardcode(), "0", "0");
					logger.info("调用生成虚拟账户方法结果：a[0]="+a[0]+"; a[1]："+a[1]);
				}
				bpCustMemberService.merge(bpCustMember);
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]="开通第三方支付账户业务数据处理成功";
				logger.info("bpCustMember.id="+bpCustMember.getId()+";bpCustMember.Cardcode="+bpCustMember.getCardcode()+";bpCustMember.thirdPayConfig()="+bpCustMember.getThirdPayConfig()+";bpCustMember.getthirdPayFlagId="+bpCustMember.getThirdPayFlagId());
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="开通第三方支付账户业务数据处理失败:数据不存在或者已经处理过了";
				logger.info("失败原因：bpCustMember="+bpCustMember+"; 描述："+ret[1]);	
			}
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="业务数据处理失败";
			logger.info("custmemberId 为空");
		}
		return ret;
	}
	
	/**
	 * 第三方支付绑卡接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 *  map.get("custermemberId")   绑卡的第三方支付的用户id
	 *  map.get("requestNo");  交易请求流水号
	 *  map.get("bankCardNo") 卡号
	 *  map.get("bankCode")    开户行编码
	 *  map.get("bankstatus");  绑卡状态
	 * @return
	 */
	@Override
	public String[] bandCard(Map<String, String> map) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		String custmemberId=map.get("custermemberId");
		String requestNo=map.get("requestNo");
		WebBankcard card=null;
		if(!"".equals(requestNo)){
			card=webBankcardService.getByRequestNo(requestNo);
		}else {
			if(!"".equals(custmemberId)){
				List<WebBankcard> list=webBankcardService.getBycusterId(Long.valueOf(custmemberId));
				if(list!=null&&list.size()>0){
					card=list.get(0);
				}
			}
		}
		if(card!=null){
			card.setThirdConfig(configMap.get("thirdPayConfig").toString());
			card.setCardNum(map.get("bankCardNo"));
			card.setBankId(map.get("bankCode"));
			card.setBankname(map.get("bankCode"));
			if("".equals(map.get("bankstatus"))){
				card.setBindCardStatus(WebBankcard.BINDCARD_STATUS_SUCCESS);
			}else{
				card.setBindCardStatus(map.get("bankstatus"));
			}
			
			webBankcardService.merge(card);
			ret[0]=Constants.CODE_SUCCESS;
			ret[1]="绑定银行卡业务数据处理成功";
			logger.info("WebBankcard.id="+card.getCardId()+";WebBankcard.cardNum="+card.getCardNum()+";WebBankcard.BankId="+card.getBankId()+";WebBankcard.BindCardStatus="+card.getBindCardStatus());
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="绑定银行卡业务数据处理失败:数据不存在或者已经处理过了";
			logger.info("失败原因：card="+card+"; 描述："+ret[1]);
		}
		return ret;
	
	}

	/**
	 * 第三方支付充值接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 *  map.get("custermemberId")   充值的第三方支付的用户id
	 *  map.get("requestNo");  交易请求流水号
	 *  map.get("custmerType");  用户类型
	 *  map.get("dealRecordStatus"); 交易状态
	 * @return
	 */
	@Override
	public String[] recharge(Map<String, String> map) {
		String[] ret=new String[2];
		String custmemberId=map.get("custermemberId");
		String requestNo=map.get("requestNo");
		ObAccountDealInfo dealinfo=null;
		if(!"".equals(requestNo)){
			dealinfo=obAccountDealInfoService.getByOrderNumber(requestNo, "", ObAccountDealInfo.T_RECHARGE.toString(),  map.get("custmerType"));
		}
		if(dealinfo!=null&&dealinfo.getDealRecordStatus().compareTo(ObAccountDealInfo.DEAL_STATUS_1)==0){
			Map<String,Object> mapo=new HashMap<String,Object>();
			 mapo.put("investPersonId",dealinfo.getInvestPersonId());//投资人Id
			 mapo.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
			 mapo.put("transferType",ObAccountDealInfo.T_RECHARGE);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
			 mapo.put("money",dealinfo.getIncomMoney());//交易金额
			 mapo.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
			 mapo.put("DealInfoId",dealinfo.getId());//交易记录id，没有默认为null
			 mapo.put("recordNumber",requestNo);//交易流水号
			 mapo.put("dealStatus",Short.valueOf(map.get("dealRecordStatus")));//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
			 ret=obAccountDealInfoService.updateAcountInfo(mapo);
			//ret=obAccountDealInfoService.updateAcountInfo(dealinfo.getInvestPersonId(), ObAccountDealInfo.T_RECHARGE.toString(), dealinfo.getIncomMoney().toString(), ObSystemAccount.type0.toString(), requestNo, null, map.get("dealRecordStatus"));
			logger.info("ret[0]"+ret[0]+";ret[1]="+ret[1]+";ObAccountDealInfo.id="+dealinfo.getId()+";ObAccountDealInfo.dealRecordStatus="+dealinfo.getDealRecordStatus());
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="充值业务数据处理失败:数据不存在或者已经处理过了";
			logger.info("失败原因：ObAccountDealInfo="+dealinfo+"; 描述："+ret[1]);
		}
		return ret;
	}
	
	/**
	 * 第三方支付取现接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 *  map.get("custermemberId")   充值的第三方支付的用户id
	 *  map.get("requestNo");  交易请求流水号
	 *  map.get("custmerType");  用户类型
	 *  map.get("dealRecordStatus"); 交易状态
	 * @return
	 */
	@Override
	public String[] withDraw(Map<String, String> map) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		String requestNo="";
		if(map.containsKey("requestNo")){
			 requestNo=map.get("requestNo");
		}
		String loanNo="";
		if(map.containsKey("loanNo")){
			loanNo=map.get("loanNo");
		}
		ObAccountDealInfo dealinfo=null;
		if(!"".equals(requestNo)){
			dealinfo=obAccountDealInfoService.getByOrderNumber(requestNo, "", ObAccountDealInfo.T_ENCHASHMENT.toString(),  map.get("custmerType"));
		}
		if(!"".equals(loanNo)){
			dealinfo=obAccountDealInfoService.getBythirdNumber(loanNo);
		}
		if(dealinfo!=null&&!dealinfo.getDealRecordStatus().toString().equals("2")){
			Map<String,Object> mapo=new HashMap<String,Object>();
			 mapo.put("investPersonId",dealinfo.getInvestPersonId());//投资人Id
			 mapo.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
			 mapo.put("transferType",ObAccountDealInfo.T_ENCHASHMENT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
			 mapo.put("money",dealinfo.getPayMoney());//交易金额
			 mapo.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
			 mapo.put("DealInfoId",dealinfo.getId());//交易记录id，没有默认为null
			 mapo.put("recordNumber",requestNo);//交易流水号
			 mapo.put("dealStatus",Short.valueOf(map.get("dealRecordStatus")));//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
			 ret=obAccountDealInfoService.updateAcountInfo(mapo);
			/**
			 * 手续费收取类型
			 */
			String poundage=configMap.get("poundage").toString().trim();
			String poundageNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
			if(Integer.parseInt(poundage)>0){//客户自行支付手续费
				Map<String,Object> mapOF=new HashMap<String,Object>();
				mapOF.put("investPersonId",dealinfo.getInvestPersonId());//投资人Id（必填）
				mapOF.put("investPersonType",dealinfo.getInvestPersonType());//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
				mapOF.put("transferType",ObAccountDealInfo.T_LOANINCOME);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				mapOF.put("money",new BigDecimal(poundage));//交易金额	（必填）			
				mapOF.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapOF.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapOF.put("recordNumber",poundageNum);//交易流水号	（必填）
				mapOF.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
				String[] poundageRet =obAccountDealInfoService.operateAcountInfo(mapOF);
				logger.info("poundageRet[0]"+poundageRet[0]+";poundageRet[1]="+poundageRet[1]+";poundageNum="+poundageNum+";关联取现记录：ObAccountDealInfo.id="+dealinfo.getId());
			}else{
				
			}
			logger.info("ret[0]"+ret[0]+";ret[1]="+ret[1]+";ObAccountDealInfo.id="+dealinfo.getId()+";ObAccountDealInfo.dealRecordStatus="+dealinfo.getDealRecordStatus());
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="取现业务业务数据处理失败:数据不存在或者已经处理过了";
			logger.info("失败原因：ObAccountDealInfo="+dealinfo+"; 描述："+ret[1]);
		}
		return ret;
	}
	/**
	 * 第三方取现审核
	 */
	@Override
	public String[] withdrawPassApplye(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 第三方支付投标（冻结账户金额）接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 *  map.get("custermemberId")   第三方支付的用户id
	 *  map.get("requestNo");  交易请求流水号
	 *  map.get("custmerType");  用户类型
	 *  map.get("dealRecordStatus");交易状态
	 * @return
	 */
	@Override
	public String[] biding(Map<String, String> map) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		String custmemberId=map.get("custermemberId");
		String requestNo=map.get("requestNo");
		PlBidInfo info=null;
		if(!"".equals(requestNo)){
			info=plBidInfoService.getByOrderNo(requestNo);
		}
		if(info!=null&&info.getState().compareTo(Short.valueOf("0"))==0){
			if(map.get("dealRecordStatus").equals("2")){
				ObAccountDealInfo dealinfo=obAccountDealInfoService.getByOrderNumber(requestNo, "", ObAccountDealInfo.T_INVEST.toString(),  map.get("custmerType"));
				if(dealinfo!=null){
					ret[0]=Constants.CODE_FAILED;
					ret[1]="投标业务数据处理失败:已经处理过了";
					logger.info("失败原因：ObAccountDealInfo="+dealinfo+"; 描述："+ret[1]);
				}else{
					BpCustMember bpCustMember=bpCustMemberService.get(info.getUserId());
					List<InvestPersonInfo> investPersonInfo =investPersonInfoService.getByRequestNumber(requestNo);
					if(investPersonInfo==null||investPersonInfo.size()==0){
						//plBidPlanService.updateStatByMoney(info.getBidId(), info.getUserMoney());
						info.setState(PlBidInfo.TYPE1);
						info.setBidtime(new Date());
						System.out.println("=======修改状态");
						plBidInfoService.save(info);
						//plBidInfoService.saveToERP(info.getId().toString(),requestNo);//投资人列表到erp 直接保存
						logger.info("保存投标记录情况：PlBidInfo="+info+"; PlBidInfo.id："+info.getId()+";PlBidInfo.status="+info.getState());
					}
					Map<String,Object> mapOF=new HashMap<String,Object>();
					mapOF.put("investPersonId",dealinfo.getInvestPersonId());//投资人Id（必填）
					mapOF.put("investPersonType",dealinfo.getInvestPersonType());//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
					mapOF.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
					mapOF.put("money",info.getUserMoney());//交易金额	（必填）			
					mapOF.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
					mapOF.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
					mapOF.put("recordNumber",requestNo);//交易流水号	（必填）
					mapOF.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_7);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
					String[] rett =obAccountDealInfoService.operateAcountInfo(mapOF);
					//String[] rett =obAccountDealInfoService.operateAcountInfo(info.getUserId().toString(), ObAccountDealInfo.T_INVEST.toString(), info.getUserMoney().toString(), "3", map.get("custmerType"), ObAccountDealInfo.UNFREEZY.toString(), ObAccountDealInfo.DEAL_STATUS_1.toString(), requestNo);
					logger.info("投标保存系统账户交易记录：rett[0]="+rett[0]+"; rett[1]："+rett[1]+";requestNo="+requestNo);
					ret[0]=Constants.CODE_SUCCESS;
					ret[1]="投标业务数据处理成功";
					logger.info("PlBidInfo.id="+info.getId()+";PlBidInfo.status="+info.getState()+";PlBidInfo.orderNo="+info.getOrderNo());
				}
			}else{
				info.setState(PlBidInfo.TYPE4);
				info.setBidtime(new Date());
				System.out.println("=======修改状态");
				plBidInfoService.save(info);
				logger.info("PlBidInfo.id="+info.getId()+";PlBidInfo.status="+info.getState()+";PlBidInfo.orderNo="+info.getOrderNo());
			}
			
			
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="取现业务业务数据处理失败:数据不存在或者已经处理过了";
			logger.info("失败原因：PlBidInfo="+info+"; 描述："+ret[1]);
		}
		return ret;
	}

	/**
	 * 第三方支付取消投标（解冻冻结金额）接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	@Override
	public String[] cancelbiding(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 第三方支付自动投标授权接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * Map<String ,String> map=new HashMap<String,String>();
	 * map.gett("thirdPayConfigId");
	 * @return
	 */
	@Override
	public String[] bidingAuthorization(Map<String, String> map) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		PlBidAuto  bidAuto=null;
		BpCustMember bp=bpCustMemberService.getMemberByFlagId(map.get("thirdPayConfigId").toString());
		if(bp!=null){
			bidAuto= plBidAutoService.getPlBidAuto(Long.valueOf(map.get("custermemberId").toString()));
			if(bidAuto!=null){
				bidAuto.setIsOpen(1);
				bidAuto.setOrderTime(new Date());
				plBidAutoService.save(bidAuto);
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]="自动投标授权数据处理完成";
				logger.info("处理数据记录：bidAutoId="+bidAuto.getId()+"; 描述："+ret[1]+";授权自动投标人的第三方用户号："+map.get("thirdPayConfigId").toString());
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="没有找到需要自动授权的人";
				logger.info("处理数据记录：bidAutoId="+bidAuto.getId()+"; 描述："+ret[1]+";授权自动投标人的第三方用户号："+map.get("thirdPayConfigId").toString());
			}
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="没有找到自动投标授权记录";
			logger.info("处理数据记录：bidAutoId="+bidAuto+"; 描述："+ret[1]+";授权自动投标人的第三方用户号："+map.get("thirdPayConfigId").toString());
		}
		return ret;
	}
	
	/**
	 * 第三方支付投标放款接口（转账）接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	@Override
	public String[] loan(Map<String, String> maps) {
		 String[] LoanNoArr=maps.get("LoanNoList").toString().split(",");
		 PlBidInfo bidInfo = null;
		 BpCustMember LoanMember = null;
		 PlBidPlan bidplan = null;
		 BigDecimal totalMoney = new BigDecimal(0);//借款总金额
		 for(int i=0;i<LoanNoArr.length;i++){
			 bidInfo = plBidInfoService.getByPreAuthorizationNum(LoanNoArr[i]);//投资信息
			 bidplan = plBidPlanService.get(bidInfo.getBidId());//标的信息
			 totalMoney=totalMoney.add(bidInfo.getUserMoney());//放款总额
			 //BpCustMember OutCust = bpCustMemberService.get(bidInfo.getUserId());// 查询投资人
			 LoanMember=bpCustMemberService.getMemberUserName(bidplan.getReceiverP2PAccountNumber());//借款人
		    	
		    	//判断是否有投标活动设置
			 	bpActivityManageService.addbpActivityManage(bidInfo.getUserId(),bidInfo.getUserMoney(),bidInfo.getBidtime());
				//投资人更新资金明细 
				Map<String,Object> mapUP=new HashMap<String,Object>();
				mapUP.put("investPersonId",bidInfo.getUserId());//投资人Id
				mapUP.put("investPersonType",Short.valueOf("0"));//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				mapUP.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				mapUP.put("money",bidInfo.getUserMoney());//交易金额
				mapUP.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				mapUP.put("DealInfoId",null);//交易记录id，没有默认为null
				mapUP.put("recordNumber",bidInfo.getOrderNo());//交易流水号
				mapUP.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
				String[] rett =obAccountDealInfoService.updateAcountInfo(mapUP);
				bidInfo.setState(Short.valueOf("2"));
				plBidInfoService.flush();
				plBidInfoService.save(bidInfo);
				
				//判断此标是否可使用优惠券
				bpActivityManageService.checkCoupons(bidplan,bidInfo,bidInfo.getOrderNo());
		 }
			//借款人借款入账
		 	Map<String,Object> map=new HashMap<String,Object>();
	    	map.put("investPersonId",LoanMember.getId());//投资人Id（必填）
	    	map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
	    	map.put("transferType",ObAccountDealInfo.T_INMONEY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
	    	map.put("money",totalMoney);//交易金额	（必填）			 
	    	map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
	    	map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
	    	map.put("recordNumber",maps.get("requestNo").toString());//交易流水号	（必填）
	    	map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
	    	String[] relt =obAccountDealInfoService.operateAcountInfo(map);
	    	
		bidplan.setState(7);
		plBidPlanService.flush();
		plBidPlanService.save(bidplan);
		return null;
	}
	
/*	*//**
	 * 第三方支付投标放款接口（转账）接收到回调函数，处理业务数据方法
	 * 做为保留方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 *//*
	@Override
	public String[] loan(Map<String, String> maps) {
		String[] LoanNoArr=maps.get("LoanNoList").toString().split(",");
		PlBidInfo bidInfo = null;
		BpCustMember LoanMember = null;
		PlBidPlan bidplan = null;
		BigDecimal totalMoney = new BigDecimal(0);//借款总金额
		for(int i=0;i<LoanNoArr.length;i++){
			bidInfo = plBidInfoService.getByPreAuthorizationNum(LoanNoArr[i]);//投资信息
			bidplan = plBidPlanService.get(bidInfo.getBidId());//标的信息
			totalMoney=totalMoney.add(bidInfo.getUserMoney());//放款总额
			//BpCustMember OutCust = bpCustMemberService.get(bidInfo.getUserId());// 查询投资人
			LoanMember=bpCustMemberService.getMemberUserName(bidplan.getReceiverP2PAccountNumber());//借款人
			
			//判断是否有投标活动设置
			bpActivityManageService.addbpActivityManage(bidInfo.getUserId(),bidInfo.getUserMoney(),bidInfo.getBidtime());
			//投资人更新资金明细 
			Map<String,Object> mapUP=new HashMap<String,Object>();
			mapUP.put("investPersonId",bidInfo.getUserId());//投资人Id
			mapUP.put("investPersonType",Short.valueOf("0"));//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
			mapUP.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
			mapUP.put("money",bidInfo.getUserMoney());//交易金额
			mapUP.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
			mapUP.put("DealInfoId",null);//交易记录id，没有默认为null
			mapUP.put("recordNumber",bidInfo.getOrderNo());//交易流水号
			mapUP.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
			String[] rett =obAccountDealInfoService.updateAcountInfo(mapUP);
			bidInfo.setState(Short.valueOf("2"));
			plBidInfoService.flush();
			plBidInfoService.save(bidInfo);
			
			//判断此标是否可使用优惠券
			bpActivityManageService.checkCoupons(bidplan,bidInfo,bidInfo.getOrderNo());
		}
		//借款人借款入账
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("investPersonId",LoanMember.getId());//投资人Id（必填）
		map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
		map.put("transferType",ObAccountDealInfo.T_INMONEY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
		map.put("money",totalMoney);//交易金额	（必填）			 
		map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
		map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
		map.put("recordNumber",maps.get("requestNo").toString());//交易流水号	（必填）
		map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
		String[] relt =obAccountDealInfoService.operateAcountInfo(map);
		
		bidplan.setState(7);
		plBidPlanService.flush();
		plBidPlanService.save(bidplan);
		return null;
	}
	
*/	/**
	 * 流标处理
	 */
	@Override
	@SuppressWarnings("unchecked")
	public String[] loanFailed(Map<String, String> maps) {
		String loanOrderNo = maps.get("LoanNoList").toString();
		PlBidPlan plbidplan  = plBidPlanService.getplanByLoanOrderNo(loanOrderNo);
		Set<PlBidInfo> info = plbidplan.getPlBidInfos();
		Iterator<PlBidInfo> iterator = info.iterator();
		while (iterator.hasNext()) {
			PlBidInfo temp = iterator.next();
			if (temp != null&& temp.getState() != null&& (temp.getState().equals(Short.valueOf("1")) || temp.getState().equals(Short.valueOf("2")))) {
				//判断是否使用了优惠券
				if(temp.getCouponId()!=null&&!temp.getCouponId().equals("")){
					BpCoupons coupon = bpCouponsService.get(temp.getCouponId());
					coupon.setCouponMoney(new BigDecimal(0));
					coupon.setCouponStatus(Short.valueOf("5"));
					bpCouponsService.save(coupon);
				}
				temp.setState(Short.valueOf("3"));
				temp.setCouponId(null);
				plBidInfoService.save(temp);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("investPersonId", temp.getUserId());// 投资人Id
				map.put("investPersonType", ObSystemAccount.type0);// 投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				map.put("transferType", ObAccountDealInfo.T_INVEST);// 交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				map.put("money", temp.getUserMoney());// 交易金额
				map.put("dealDirection", ObAccountDealInfo.DIRECTION_PAY);// 交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				map.put("DealInfoId", null);// 交易记录id，没有默认为null
				map.put("recordNumber", temp.getOrderNo());// 交易流水号
				map.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_3);// 资金交易状态：1等待支付，2支付成功，3
				String[] ret = obAccountDealInfoService	.updateAcountInfo(map);
				try {
					BpCustMember customer = bpCustMemberService.get(temp.getUserId());
					PlBidPlan plan = plBidPlanService.get(temp.getBidId());
					//流标成功发送短信
					//流标成功发送短信
					Map<String, String> mapSms = new HashMap<String, String>();
					mapSms.put("telephone", customer.getTelphone());
					mapSms.put("${name}", customer.getLoginname());
					mapSms.put("${code}", temp.getUserMoney().toString());
					mapSms.put("${projName}",plan.getBidProName());
					mapSms.put("key", "sms_flowStandard");
					smsSendService.smsSending(mapSms);
					//smsSendUtil.sms_flowStandard(customer.getTelphone(),customer.getTruename(),temp.getUserMoney().toString(), plan.getBidProName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		plbidplan.setState(PlBidPlan.STATE3);
		plBidPlanService.merge(plbidplan);
	
		return null;
	}
	
	/*
	*//**
	 * 流标处理   做为保留方法
	 *//*
	@Override
	public String[] loanFailed(Map<String, String> maps) {
		 String[] LoanNoArr=maps.get("LoanNoList").toString().split(",");
		 for(int i=0;i<LoanNoArr.length;i++){
			PlBidInfo temp = plBidInfoService.getByPreAuthorizationNum(LoanNoArr[i]);//投资信息
			PlBidPlan bidplan = plBidPlanService.get(temp.getBidId());//标的信息
			if (temp != null&& temp.getState() != null&& (temp.getState().equals(Short.valueOf("1")) || temp.getState().equals(Short.valueOf("2")))) {
				//判断是否使用了优惠券
				if(temp.getCouponId()!=null&&!temp.getCouponId().equals("")){
					BpCoupons coupon = bpCouponsService.get(temp.getCouponId());
					coupon.setCouponMoney(new BigDecimal(0));
					coupon.setCouponStatus(Short.valueOf("5"));
					bpCouponsService.save(coupon);
				}
				temp.setState(Short.valueOf("3"));
				temp.setCouponId(null);
				plBidInfoService.save(temp);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("investPersonId", temp.getUserId());// 投资人Id
				map.put("investPersonType", ObSystemAccount.type0);// 投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				map.put("transferType", ObAccountDealInfo.T_INVEST);// 交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				map.put("money", temp.getUserMoney());// 交易金额
				map.put("dealDirection", ObAccountDealInfo.DIRECTION_PAY);// 交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				map.put("DealInfoId", null);// 交易记录id，没有默认为null
				map.put("recordNumber", temp.getOrderNo());// 交易流水号
				map.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_3);// 资金交易状态：1等待支付，2支付成功，3
				String[] ret = obAccountDealInfoService	.updateAcountInfo(map);
				try {
					BpCustMember customer = bpCustMemberService.get(temp.getUserId());
					PlBidPlan plan = plBidPlanService.get(temp.getBidId());
					//流标成功发送短信
					//流标成功发送短信
					Map<String, String> mapSms = new HashMap<String, String>();
					mapSms.put("telephone", customer.getTelphone());
					mapSms.put("${name}", customer.getLoginname());
					mapSms.put("${code}", temp.getUserMoney().toString());
					mapSms.put("${projName}",plan.getBidProName());
					mapSms.put("key", "sms_flowStandard");
					smsSendService.smsSending(mapSms);
					//smsSendUtil.sms_flowStandard(customer.getTelphone(),customer.getTruename(),temp.getUserMoney().toString(), plan.getBidProName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				bidplan.setState(PlBidPlan.STATE3);
				plBidPlanService.save(bidplan);
			}
		}

	
		return null;
	}*/

	/**
	 * 第三方支付自动还款授权接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * @return
	 */
	@Override
	public String[] repaymentAuthorization(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 第三方支付客户自助还款接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * map.get("orderNo");标的编号
	 * map.get("orderType");标的类型
	 * map.fet("requestNo");还款交易的流水号
	 * @return
	 */
	@Override
	public String[] repayment(Map<String, String> map) {
		String[] ret=new String[2];
		String requestNo=map.get("requestNo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String bidId="";
		BigDecimal accMoney  = map.get("accMoney")!=null?new BigDecimal(map.get("accMoney").toString()):BigDecimal.ZERO;
		//查询还款的记录
		Date intentDate;
		try {
			intentDate = sdf.parse(map.get("intentDate").toString());
			List<BpFundIntent> list= bpFundIntentService.getThirdBpFundIntentList(map.get("bidId").toString(),intentDate);
			if(list!=null){
				String orderNoStr="";
				for(BpFundIntent temp:list){
					bidId = temp.getBidPlanId().toString();
					BpCustMember LoanMember=plBidPlanService.getLoanMember(plBidPlanService.get(temp.getBidPlanId()));
					if(temp.getRequestNo()!=null&&temp.getRequestNo().equals(requestNo)){
						
						if(temp.getNotMoney().compareTo(new BigDecimal(0))>0&&temp.getFactDate()==null){
							Map<String,Object> mapL=new HashMap<String,Object>();
							mapL.put("investPersonId",LoanMember.getId());//投资人Id（必填）
							mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
							mapL.put("transferType",ObAccountDealInfo.T_LOANPAY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
							mapL.put("money",accMoney.compareTo(new BigDecimal(0))>0?temp.getNotMoney().add(accMoney):temp.getNotMoney());//交易金额	（必填）			 
							mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
							mapL.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
							mapL.put("recordNumber",Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));//交易流水号	（必填）
							mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
							obAccountDealInfoService.operateAcountInfo(mapL);
							if(temp.getFundType().equals("loanInterest")||temp.getFundType().equals("principalRepayment")){
								BpCustMember bps= null;
								List<PlBidInfo> info = plBidInfoService.getByBidId(temp.getBidPlanId());
								if(info.size()>0){
									if(info.get(0).getNewInvestPersonId()!=null){
										bps = 	bpCustMemberService.get(info.get(0).getNewInvestPersonId());
									}else{
										bps = bpCustMemberService.get(temp.getInvestPersonId());
									}
								}else{
									bps = bpCustMemberService.get(temp.getInvestPersonId());
								}
								Map<String,Object> mapI=new HashMap<String,Object>();
								mapI.put("investPersonId",bps.getId());//投资人Id（必填）
								mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
								mapI.put("transferType",temp.getFundType().equals("loanInterest")?ObAccountDealInfo.T_PROFIT:ObAccountDealInfo.T_PRINCIALBACK);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
								mapI.put("money",accMoney.compareTo(new BigDecimal(0))>0?temp.getNotMoney().add(accMoney):temp.getNotMoney());//交易金额	（必填）			 
								mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
								mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
								mapI.put("recordNumber",Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));//交易流水号	（必填）
								mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
								obAccountDealInfoService.operateAcountInfo(mapI);
							}
							temp.setNotMoney(new BigDecimal(0));
							temp.setAfterMoney(temp.getIncomeMoney());
							temp.setFactDate(new Date());
							temp.setRequestNo(requestNo);
							temp.setRequestDate(sdf.parse(map.get("requestTime").toString()));
							temp.setRequestCount(temp.getRequestCount()!=null?temp.getRequestCount().intValue()+1:1);
							temp.setRepaySource(BpFundIntent.REPAYSOURCE1);
							bpFundIntentService.merge(temp);
							logger.info("款项业务已成功处理：requestNo="+requestNo+";款项主键Id"+temp.getFundIntentId()+"; 款型类型："+temp.getFundType()+";交易订单号"+temp.getOrderNo());
							orderNoStr=temp.getFundIntentId()+":"+temp.getFundType()+":"+temp.getOrderNo()+";";
						}else{
							logger.info("款项业务已经被处理过：requestNo="+requestNo+";款项主键Id"+temp.getFundIntentId()+"; 款型类型："+temp.getFundType()+";交易订单号"+temp.getOrderNo());
						}
					}
			}
				//判断是否是最后一期还款。把标改成已完成状态
				/*List<BpFundIntent> bpList = bpFundIntentService.getOverList(bidId);
				if(bpList.size()==0){
					PlBidPlan plan = plBidPlanService.get(Long.valueOf(bidId));
					plan.setState(10);
					plBidPlanService.save(plan);
				}*/
				
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]="还款业务数据处理完成";
				logger.info("处理数据记录：requestNo="+requestNo+"; 描述："+ret[1]+";本次处理数据集合(FundIntentId:fundtype:orderNo)："+orderNoStr);
				
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="还款业务数据处理失败:数据不存在";
				logger.info("失败原因：requestNo="+requestNo+"; 描述："+ret[1]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 第三方支付解除银行卡接收到回调函数，处理业务数据方法
	 * @param map  依据不同的第三方传回的数据标识进行归纳map中的数值
	 * map.get("orderNo");标的编号
	 * map.get("orderType");标的类型
	 * map.fet("requestNo");还款交易的流水号
	 * @return
	 */
	@Override
	public String[] cancelBindBank(Map<String, String> map) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		String custmemberId=map.get("custermemberId");
		String requestNo=map.get("requestNo");
		WebBankcard card=null;
		if(!"".equals(requestNo)){
			card=webBankcardService.getByRequestNo(requestNo);
		}else {
			if(!"".equals(custmemberId)){
				List<WebBankcard> list=webBankcardService.getBycusterId(Long.valueOf(custmemberId));
				if(list!=null&&list.size()>0){
					card=list.get(0);
				}
			}
		}
		if(card!=null){
			card.setBindCardStatus(WebBankcard.BINDCARD_STATUS_CANCEL);
			webBankcardService.merge(card);
			ret[0]=Constants.CODE_SUCCESS;
			ret[1]="取消绑定银行卡业务数据处理成功";
			logger.info("WebBankcard.id="+card.getCardId()+";WebBankcard.cardNum="+card.getCardNum()+";WebBankcard.BankId="+card.getBankId()+";WebBankcard.BindCardStatus="+card.getBindCardStatus());
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="取消绑定银行卡业务数据处理失败:数据不存在或者已经处理过了";
			logger.info("失败原因：card="+card+"; 描述："+ret[1]);
		}
		return ret;
	
	
	}
	/**
	 * 第三方支付中理财计划的授权平台还款方法
	 * @param map
	 * @return
	 */
	@Override
	public String[] repaymentAuthorizationMoneyPlan(Map<String, String> map) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		String requestNo =map.get("requestNo").toString(); 
		PlManageMoneyPlan plan=null;
		if("".endsWith(requestNo)){
			String planId=map.get("orderNo").toString(); 
			plan=plManageMoneyPlanDao.get(Long.valueOf(planId));
			requestNo=plan.getRequestNo();
		}else{
			plan=plManageMoneyPlanDao.getByRequestNo(requestNo);
		}
		synchronized(requestNo){
			if(plan!=null){
				plan.setAuthorityStatus(Short.valueOf("1"));
				plManageMoneyPlanDao.save(plan);
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]="已经完成了自动还款授权";
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="自动还款授权失败：没有找到需要自动授权的标";
			}
		}
		return ret;
	}

	/**
	 * 平台发送奖励金额
	 * @param planId
	 * @param peridId
	 */
	public void checkCouponsIntent(String planId,String peridId,String requestNo,String basePath){
		PlBidPlan bidplan = plBidPlanService.get(Long.valueOf(planId));
		//判断此标是否可用优惠券
		if(bidplan.getCoupon()!=null&&bidplan.getCoupon().compareTo(1)==0){
			//判断返利方式是否是 随期或者到期
			if(bidplan.getRebateWay().compareTo(2)==0||bidplan.getRebateWay().compareTo(3)==0){
				List<BpFundIntent> bpfundInterestList=null;//利息
				List<BpFundIntent> bpfundPrincipalList=null;//本金
				String transferType="";//资金类型
				boolean checkWay=false;
				if(bidplan.getRebateWay().compareTo(2)==0){
					checkWay=true;
				}else if(bidplan.getRebateWay().compareTo(3)==0){
					int per = Integer.valueOf(peridId)+1;
					List<BpFundIntent> checkFund = bpFundIntentService.getCouponsIntent(planId, String.valueOf(per),requestNo,null);
					if(checkFund.size()==0){//表示当前期数是最后一期还款
						checkWay=true;
					}
				}
				if(checkWay){
					//判断 返利类型
					if(bidplan.getRebateType().compareTo(1)==0){//返现 principalCoupons
						transferType=ObAccountDealInfo.T_BIDRETURN_RETURNRATIO;
						bpfundInterestList = bpFundIntentService.getCouponsIntent(planId, peridId,requestNo,"principalCoupons");
					}else if(bidplan.getRebateType().compareTo(2)==0){//返息 couponInterest
						transferType=ObAccountDealInfo.T_BIDRETURN_RATE27;
						bpfundInterestList = bpFundIntentService.getCouponsIntent(planId, peridId,requestNo,"couponInterest");
					}else if(bidplan.getRebateType().compareTo(3)==0){//返息现  principalCoupons couponInterest
						transferType=ObAccountDealInfo.T_BIDRETURN_RATE29;
						bpfundInterestList = bpFundIntentService.getCouponsIntent(planId, peridId,requestNo,"couponInterest");
						bpfundPrincipalList = bpFundIntentService.getCouponsIntent(planId, peridId,requestNo,"principalCoupons");
					}else if(bidplan.getRebateType().compareTo(4)==0){//加息 couponInterest
						transferType=ObAccountDealInfo.T_BIDRETURN_RATE30;
						bpfundInterestList = bpFundIntentService.getCouponsIntent(planId, peridId,requestNo,"subjoinInterest");
					}
					if(bpfundInterestList!=null){//返利息
						couponIntent(bpfundInterestList,bidplan,transferType,basePath);
					}
					if(bpfundPrincipalList!=null){//返本金
						couponIntent(bpfundPrincipalList,bidplan,ObAccountDealInfo.T_BIDRETURN_RATE28,basePath);
					}
				}
			}
		}else{
			//判断是否此标设置了普通加息
			if(bidplan.getAddRate()!=null&&!bidplan.getAddRate().equals("")){
				//判断是否设置的是随期或到期
				if(bidplan.getRebateWay().compareTo(2)==0||bidplan.getRebateWay().compareTo(3)==0){
					boolean checkWay=false;
					if(bidplan.getRebateWay().compareTo(2)==0){
						checkWay=true;
					}else if(bidplan.getRebateWay().compareTo(3)==0){
						int per = Integer.valueOf(peridId)+1;
						List<BpFundIntent> checkFund = bpFundIntentService.getCouponsIntent(planId, String.valueOf(per),requestNo,null);
						if(checkFund.size()==0){//表示当前期数是最后一期还款
							checkWay=true;
						}
					}
					if(checkWay){
						List<BpFundIntent> subjoinInterest = bpFundIntentService.getCouponsIntent(planId, peridId,requestNo,"commoninterest");
						couponIntent(subjoinInterest,bidplan,ObAccountDealInfo.T_BIDRETURN_ADDRATE,basePath);
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
	public void couponIntent(List<BpFundIntent> bp,PlBidPlan bidplan,String transferType,String basePath){
		for(BpFundIntent bpfund:bp){
			if(bpfund.getFactDate()==null||bpfund.getFactDate().equals("")){
				BpCustMember mem=null;
				PlBidInfo bidInfo1 = plBidInfoService.getByOrderNo(bpfund.getOrderNo());
				//判断是否发生了债权交易,奖励发给新的债权人 
				if(bidInfo1.getNewInvestPersonId()!=null&&!bidInfo1.getNewInvestPersonId().equals("")){
					mem=bpCustMemberService.get(bidInfo1.getNewInvestPersonId());
				}else{
					mem=bpCustMemberService.get(bpfund.getInvestPersonId());
				}
				if(mem!=null){
					String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
					CommonRequst commonRequest = new CommonRequst();
					commonRequest.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方标识
					commonRequest.setRequsetNo(requestNo);//请求流水号
					commonRequest.setAmount(bpfund.getNotMoney());//交易金额
					commonRequest.setCustMemberId(mem.getId().toString());
					commonRequest.setCustMemberType("0");
					commonRequest.setBidId(bpfund.getFundIntentId().toString());
					commonRequest.setTransferName(ThirdPayConstants.TN_COUPONAWARD);
					commonRequest.setBussinessType(ThirdPayConstants.BT_COUPONAWARD);
					CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
					System.out.println("返现result="+commonResponse.getResponseMsg());
					
					if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
						//添加资金明细
						Map<String,Object> map3=new HashMap<String,Object>();
						map3.put("investPersonId",mem.getId());//投资人Id（必填）
						map3.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						map3.put("transferType",transferType);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						map3.put("money",bpfund.getNotMoney());//交易金额	（必填）			 
						map3.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						map3.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						map3.put("recordNumber",requestNo);//交易流水号	（必填）
						map3.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						obAccountDealInfoService.operateAcountInfo(map3);
						
						//更新款项
						bpfund.setNotMoney(new BigDecimal(0));
						bpfund.setAfterMoney(bpfund.getIncomeMoney());
						bpfund.setFactDate(new Date());
						bpFundIntentService.save(bpfund);
					}
				}
			}
			
		}
	}
	
	/**
	 * 第三方支付中理财计划的授权平台还款方法(双乾)
	 * @param map
	 * @return
	 */
	@Override
	public String[] moneyMoneyAuthorizationMoneyPlan(Map<String, String> map) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		String requestNo =map.get("requestNo").toString(); 
		PlManageMoneyPlan plan=null;
		if("".endsWith(requestNo)){
			String planId=map.get("orderNo").toString(); 
			plan=plManageMoneyPlanDao.get(Long.valueOf(planId));
			requestNo=plan.getRequestNo();
		}else{
			plan=plManageMoneyPlanDao.getByRequestNo(requestNo);
		}
		synchronized(requestNo){
			if(plan!=null){
				plan.setAuthorityStatus(Short.valueOf("1"));
				plManageMoneyPlanDao.save(plan);
				BpCustMember bm = bpCustMemberService.getMemberUserName(plan.getMoneyReceiver());
				if(null != bm && !"".equals(bm)){
					bm.setRefund("1");
					bm.setSecondAudit("1");
					bpCustMemberService.save(bm);
					/*PlBidAuto bidAuto= plBidAutoService.getPlBidAuto(bm.getId());
					if(null != bidAuto && !"".equals(bidAuto)){
						bidAuto.setIsOpen(1);
						bidAuto.setOrderTime(new Date());
					}else{
						bidAuto=plBidAutoService.initPlBidAuto(bm);
						bidAuto.setIsOpen(1);
						bidAuto.setOrderTime(new Date());
					}
					plBidAutoService.save(bidAuto);*/
				}
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]="已经完成了自动还款授权";
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="自动还款授权失败：没有找到需要自动授权的标";
			}
		}
		return ret;
	}
	
	/**
	 *联动优势授权接口回调处理
	 */
	@Override
	public String[] umpayLoanAuthorize(Map<String, String> map) {
		String requestNo =map.get("requestNo").toString(); 
		PlManageMoneyPlan plan=null;
		if(!"".endsWith(requestNo)){
			plan=plManageMoneyPlanDao.getByRequestNo(requestNo);
		}
		String open = map.get("open").toString();
		String[] ret=new String[2];
		synchronized(requestNo){
			if(open != null && !open.equals("") && plan!=null){
				plan.setAuthorityStatus(Short.valueOf("1"));
				plManageMoneyPlanDao.save(plan);
				BpCustMember bm = bpCustMemberService.getMemberUserName(plan.getMoneyReceiver());
				if(null != bm && !"".equals(bm)){
					bm.setRefund("1");
					bpCustMemberService.merge(bm);
				/*	PlBidAuto bidAuto= plBidAutoService.getPlBidAuto(bm.getId());
					if(null != bidAuto && !"".equals(bidAuto)){
						bidAuto.setIsOpen(1);
						bidAuto.setOrderTime(new Date());
					}else{
						bidAuto=plBidAutoService.initPlBidAuto(bm);
						bidAuto.setIsOpen(1);
						bidAuto.setOrderTime(new Date());
					}
					plBidAutoService.save(bidAuto);*/
				}
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]="已经完成了自动还款授权";
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="自动还款授权失败：没有找到需要自动授权的标";
			}
		}
		return ret;
	}

	@Override
	public String[] bidMoneyPlan(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] chageMobile(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] closeBidingAuthorization(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] doCompensatory(String str1, Short str2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] doFianceProductBuy(String str1, Short str2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] doObligationDeal(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] doObligationPublish(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] rAuthorization(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] dividendAssigninterest(Map<String, String> map) {
		String[] set=new String[2];
		String requsetNo = map.get("requsetNo");
		String isCreate = map.get("isCreate");
		
		Map<String,String> plmap=new HashMap<String,String>();
		plmap.put("loanerRequestNo",requsetNo);
		List<PlMmOrderAssignInterest> assignInterestList =plMmOrderAssignInterestService.getByRequestNo(plmap);
		
		PlMmOrderAssignInterest  plMmOrderAssignInterest = assignInterestList.get(0);
		//台账所对应的理财计划
		PlManageMoneyPlan plManageMoneyPlan = plManageMoneyPlanService.get(plMmOrderAssignInterest.getMmplanId());
		//出款人信息
		BpCustMember bpCustMemberL = bpCustMemberService.getMemberUserName(plManageMoneyPlan.getMoneyReceiver());
		//收款人信息
		BpCustMember bpCustMemberI = bpCustMemberService.get(plMmOrderAssignInterest.getInvestPersonId());
		
		if(!isCreate.equals("1")){//不等于1的时候生成
			//出款人资金流水
			Map<String,Object> mapL=new HashMap<String,Object>();
			mapL.put("investPersonId",bpCustMemberL.getId());//投资人Id（必填）
			mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			mapL.put("transferType",ObAccountDealInfo.T_LOANPAY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			mapL.put("money",plMmOrderAssignInterest.getIncomeMoney());//交易金额	（必填）			 
			mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			mapL.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			mapL.put("recordNumber",requsetNo+"_L");//交易流水号	（必填）
			mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			obAccountDealInfoService.operateAcountInfo(mapL);
		}	
		ThirdPayRecord thirdPayRecord = thirdPayRecordService.getByOrderNo(requsetNo);
		if(null != thirdPayRecord && !"".equals(thirdPayRecord) && thirdPayRecord.getThirdPayConfig().equals("umpayConfig")){
			plMmOrderAssignInterest.setLoanerRepayMentStatus(1);
			set[0] = CommonResponse.RESPONSECODE_APPLAY;
			set[1] = "操作记录：联动还款操作成功!";
		}else{
			//收款人资金流水
			Map<String,Object> mapI=new HashMap<String,Object>();
			mapI.put("investPersonId",bpCustMemberI.getId());//投资人Id（必填）
			mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			mapI.put("transferType",plMmOrderAssignInterest.getFundType().equals("principalRepayment")?ObAccountDealInfo.T_PRINCIALBACK:ObAccountDealInfo.T_PROFIT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			mapI.put("money",plMmOrderAssignInterest.getIncomeMoney());//交易金额	（必填）			 
			mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			mapI.put("recordNumber",requsetNo+"_I");//交易流水号	（必填）
			mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			String[] ret =obAccountDealInfoService.operateAcountInfo(mapI);
			
			plMmOrderAssignInterest.setLoanerRepayMentStatus(2);
			plMmOrderAssignInterest.setAfterMoney(plMmOrderAssignInterest.getIncomeMoney());
			plMmOrderAssignInterest.setFactDate(new Date());
			
			set[0] = CommonResponse.RESPONSECODE_SUCCESS;
			set[1] = "操作记录：派息操作成功!";
		}
		plMmOrderAssignInterestService.save(plMmOrderAssignInterest);
		
		
		return set;
	}
	
	
	@Override
	public String[] backMoneyassigninterest(Map<String, String> map) {
		String[] set=new String[2];
		String requsetNo = map.get("requsetNo");
		
		Map<String,String> plmap=new HashMap<String,String>();
		plmap.put("investRequestNo",requsetNo);
		List<PlMmOrderAssignInterest> assignInterestList =plMmOrderAssignInterestService.getByRequestNo(plmap);
		PlMmOrderAssignInterest  plMmOrderAssignInterest = assignInterestList.get(0);
		
		//收款人信息
		BpCustMember bpCustMemberI = bpCustMemberService.get(plMmOrderAssignInterest.getInvestPersonId());
		
		ThirdPayRecord thirdPayRecord = thirdPayRecordService.getByOrderNo(requsetNo);
		if(null != thirdPayRecord && !"".equals(thirdPayRecord) && thirdPayRecord.getThirdPayConfig().equals("umpayConfig")){
			//收款人资金流水
			Map<String,Object> mapI=new HashMap<String,Object>();
			mapI.put("investPersonId",bpCustMemberI.getId());//投资人Id（必填）
			mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			mapI.put("transferType",plMmOrderAssignInterest.equals("principalRepayment")?ObAccountDealInfo.T_PRINCIALBACK:ObAccountDealInfo.T_PROFIT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			mapI.put("money",plMmOrderAssignInterest.getIncomeMoney());//交易金额	（必填）			 
			mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
			mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			mapI.put("recordNumber",requsetNo+"_I");//交易流水号	（必填）
			mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			String[] ret =obAccountDealInfoService.operateAcountInfo(mapI);
			
			plMmOrderAssignInterest.setLoanerRepayMentStatus(2);
			plMmOrderAssignInterest.setAfterMoney(plMmOrderAssignInterest.getIncomeMoney());
			plMmOrderAssignInterest.setFactDate(new Date());
			
			set[0] = CommonResponse.RESPONSECODE_SUCCESS;
			set[1] = "操作记录：派息操作成功!";
		}
		plMmOrderAssignInterestService.save(plMmOrderAssignInterest);
		return set;
	}

	@Override
	public String[] earlyEarlyRepayment(Map<String, String> map) {
		String[] set=new String[2];
		String sumMoney = map.get("sumMoney");
		String requsetNo = map.get("requsetNo");
		String earlyRedemptionId = map.get("earlyRedemptionId");
		PlEarlyRedemption plEarlyRedemption = plEarlyRedemptionService.get(Long.valueOf(earlyRedemptionId));
		
		List<PlMmOrderAssignInterest> pailist=plMmOrderAssignInterestService.mmplancreateList(plEarlyRedemption);
		
		ThirdPayRecord thirdPayRecord = thirdPayRecordService.getByOrderNo(requsetNo);
		if(null != thirdPayRecord && !"".equals(thirdPayRecord) && thirdPayRecord.getThirdPayConfig().equals("umpayConfig")){
			//保存新的款项台账
			for(PlMmOrderAssignInterest pi:pailist){
				pi.setLoanerRepayMentStatus(1);
				pi.setLoanerRequestNo(requsetNo);
				pi.setEarlyRedemptionId(plEarlyRedemption.getEarlyRedemptionId());
				plMmOrderAssignInterestService.save(pi);
			}
			plEarlyRedemption.setLoanerRepayMentStatus(1);
			set[0] = CommonResponse.RESPONSECODE_APPLAY;
			set[1] = "操作记录：提前赎回操作申请成功!";
		}else{
			
			//保存新的款项台账
			for(PlMmOrderAssignInterest pi:pailist){
				
				pi.setLoanerRepayMentStatus(2);
				pi.setLoanerRequestNo(requsetNo);
				pi.setInvestRequestNo(requsetNo);
				pi.setEarlyRedemptionId(plEarlyRedemption.getEarlyRedemptionId());
				pi.setFactDate(new Date());
				pi.setIsCheck(Short.valueOf("0"));
				pi.setIsValid(Short.valueOf("0"));
				plMmOrderAssignInterestService.save(pi);
			}
			
			if(new BigDecimal(sumMoney).compareTo(new BigDecimal("0")) > 0){
				Map<String,Object> mapI=new HashMap<String,Object>();
				mapI.put("investPersonId",plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getInvestPersonId());//投资人Id（必填）
				mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
				mapI.put("transferType",ObAccountDealInfo.T_PRINCIALBACK);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				mapI.put("money",new BigDecimal(sumMoney));//交易金额	（必填）			 
				mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("recordNumber",requsetNo+"TQSHBJ");//交易流水号	（必填）
				mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
				String[] ret =obAccountDealInfoService.operateAcountInfo(mapI);
			}
			/*if(loanInterestMoney.compareTo(new BigDecimal("0")) > 0){
				Map<String,Object> mapI=new HashMap<String,Object>();
				mapI.put("investPersonId",plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getInvestPersonId());//投资人Id（必填）
				mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
				mapI.put("transferType",ObAccountDealInfo.T_PROFIT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				mapI.put("money",loanInterestMoney);//交易金额	（必填）			 
				mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("recordNumber",requsetNo+"TQSHLX");//交易流水号	（必填）
				mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
				String[] ret =obAccountDealInfoService.operateAcountInfo(mapI);
			}
			if(liquidatedDamagesMoney.compareTo(new BigDecimal("0")) > 0){
				Map<String,Object> mapI=new HashMap<String,Object>();
				mapI.put("investPersonId",plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getInvestPersonId());//投资人Id（必填）
				mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
				mapI.put("transferType",ObAccountDealInfo.T_BREACH);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				mapI.put("money",liquidatedDamagesMoney);//交易金额	（必填）			 
				mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("recordNumber",requsetNo+"TQSHWYJ");//交易流水号	（必填）
				mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
				String[] ret =obAccountDealInfoService.operateAcountInfo(mapI);
			}*/
			
			plEarlyRedemption.setLoanerRepayMentStatus(2);
			set[0] = CommonResponse.RESPONSECODE_SUCCESS;
			set[1] = "操作记录：提前赎回操作成功!";
		}
		plEarlyRedemptionService.save(plEarlyRedemption);
		return set;
	}
	
	
	@Override
	public String[] earlyBackMoney(Map<String, String> map) {
		String[] set=new String[2];
		String requsetNo = map.get("requsetNo");
		String earlyRedemptionId = map.get("earlyRedemptionId");
		PlEarlyRedemption plEarlyRedemption = plEarlyRedemptionService.get(Long.valueOf(earlyRedemptionId));
		
		Map<String,String> plmap=new HashMap<String,String>();
		plmap.put("loanerRequestNo",plEarlyRedemption.getLoanerRequestNo());
		List<PlMmOrderAssignInterest> assignInterestList =plMmOrderAssignInterestService.getByRequestNo(plmap);
		
		ThirdPayRecord thirdPayRecord = thirdPayRecordService.getByOrderNo(requsetNo);
		if(null != thirdPayRecord && !"".equals(thirdPayRecord) && thirdPayRecord.getThirdPayConfig().equals("umpayConfig")){
			BigDecimal principalRepaymentMoney = new BigDecimal("0");
			BigDecimal loanInterestMoney = new BigDecimal("0");
			BigDecimal liquidatedDamagesMoney = new BigDecimal("0");
			//保存新的款项台账
			for(PlMmOrderAssignInterest pi:assignInterestList){
				if(pi.getFundType().equals("principalRepayment")){
					principalRepaymentMoney = principalRepaymentMoney.add(pi.getIncomeMoney());
				}else if(pi.getFundType().equals("loanInterest")){
					loanInterestMoney = loanInterestMoney.add(pi.getIncomeMoney());
				}else if(pi.getFundType().equals("liquidatedDamages")){
					liquidatedDamagesMoney = liquidatedDamagesMoney.add(pi.getPayMoney());
				}
				pi.setInvestRequestNo(requsetNo);
				pi.setEarlyRedemptionId(plEarlyRedemption.getEarlyRedemptionId());
				pi.setFactDate(new Date());
				pi.setIsCheck(Short.valueOf("0"));
				pi.setIsValid(Short.valueOf("0"));
				plMmOrderAssignInterestService.save(pi);
			}
			
			if(principalRepaymentMoney.compareTo(new BigDecimal("0")) > 0){
				Map<String,Object> mapI=new HashMap<String,Object>();
				mapI.put("investPersonId",plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getInvestPersonId());//投资人Id（必填）
				mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
				mapI.put("transferType",ObAccountDealInfo.T_PRINCIALBACK);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				mapI.put("money",principalRepaymentMoney);//交易金额	（必填）			 
				mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("recordNumber",requsetNo+"TQSHBJ");//交易流水号	（必填）
				mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
				String[] ret =obAccountDealInfoService.operateAcountInfo(mapI);
			}
			if(loanInterestMoney.compareTo(new BigDecimal("0")) > 0){
				Map<String,Object> mapI=new HashMap<String,Object>();
				mapI.put("investPersonId",plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getInvestPersonId());//投资人Id（必填）
				mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
				mapI.put("transferType",ObAccountDealInfo.T_PROFIT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				mapI.put("money",loanInterestMoney);//交易金额	（必填）			 
				mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("recordNumber",requsetNo+"TQSHLX");//交易流水号	（必填）
				mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
				String[] ret =obAccountDealInfoService.operateAcountInfo(mapI);
			}
			if(liquidatedDamagesMoney.compareTo(new BigDecimal("0")) > 0){
				Map<String,Object> mapI=new HashMap<String,Object>();
				mapI.put("investPersonId",plEarlyRedemption.getPlManageMoneyPlanBuyinfo().getInvestPersonId());//投资人Id（必填）
				mapI.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
				mapI.put("transferType",ObAccountDealInfo.T_BREACH);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
				mapI.put("money",liquidatedDamagesMoney);//交易金额	（必填）			 
				mapI.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
				mapI.put("recordNumber",requsetNo+"TQSHWYJ");//交易流水号	（必填）
				mapI.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
				String[] ret =obAccountDealInfoService.operateAcountInfo(mapI);
			}
			
			plEarlyRedemption.setLoanerRepayMentStatus(2);
			set[0] = CommonResponse.RESPONSECODE_SUCCESS;
			set[1] = "操作记录：提前赎回操作成功!";
		}
		plEarlyRedemptionService.save(plEarlyRedemption);
		return set;
	}

	@Override
	public String[] umpayRepayment(Map<String, String> map) {

		String[] ret=new String[2];
		String requestNo=map.get("requestNo");
		String bidId = map.get("bidId");
		String period = map.get("periodId");
		if(requestNo!=null&&!"".equals(requestNo)){
			String thirdPayConfig = thirdPayRecordService.getByOrderNo(requestNo).getThirdPayConfig();
			String bussinessType =  thirdPayRecordService.getByOrderNo(requestNo).getInterfaceType();
			if(thirdPayConfig!=null&&!"".equals(thirdPayConfig)){
				if(thirdPayConfig.toLowerCase().equals("umpayConfig".toLowerCase())){
					synchronized(requestNo){
						List<BpFundIntent> list=bpFundIntentService.getByRequestNoLoaner(requestNo);
						BigDecimal totalMoney=new BigDecimal(0);
						if(list!=null&&list.size()>0){
							String orderNoStr="";
							BpCustMember LoanMember=plBidInfoService.getLoanMember(plBidPlanService.get(list.get(0).getBidPlanId()));
							for(BpFundIntent temp:list){
								synchronized(temp.getNotMoney()){
									if(temp.getNotMoney().compareTo(new BigDecimal(0))>0){
										totalMoney=totalMoney.add(temp.getNotMoney());
										temp.setLoanerRepayMentStatus(1);
										temp.setIsCheck(Short.valueOf("0"));
										temp.setRepaySource(BpFundIntent.REPAYSOURCE1);
										temp.setReturnDate(new Date());
										bpFundIntentService.merge(temp);
										logger.info("款项业务已成功处理：requestNo="+requestNo+";款项主键Id"+temp.getFundIntentId()+"; 款型类型："+temp.getFundType()+";交易订单号"+temp.getOrderNo());
										orderNoStr=temp.getFundIntentId()+":"+temp.getFundType()+":"+temp.getOrderNo()+";";
									}else{
										temp.setLoanerRepayMentStatus(1);
										temp.setIsCheck(Short.valueOf("0"));
										temp.setRepaySource(BpFundIntent.REPAYSOURCE1);
										bpFundIntentService.merge(temp);
										logger.info("款项业务已经被处理过：requestNo="+requestNo+";款项主键Id"+temp.getFundIntentId()+"; 款型类型："+temp.getFundType()+";交易订单号"+temp.getOrderNo());
									}
								}
							}
							if(totalMoney.compareTo(new BigDecimal(0))>0){//借款人生成款项
								ObAccountDealInfo dealinfo=obAccountDealInfoService.getByOrderNumber(requestNo, "", ObAccountDealInfo.T_LOANPAY.toString(),  "0");
								if(dealinfo!=null){
									ret[0]=Constants.CODE_FAILED;
									ret[1]="还款业务数据处理完成-重复处理,无需生成款项";
									logger.info("处理数据记录：requestNo="+requestNo+"; 描述："+ret[1]+";本次处理数据集合(FundIntentId:fundtype:orderNo)："+orderNoStr);
								}else{
									Map<String,Object> mapL=new HashMap<String,Object>();
									mapL.put("investPersonId",LoanMember.getId());//投资人Id（必填）
									mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
									mapL.put("transferType",(bussinessType!=null&&bussinessType.equals("hry50006"))?ObAccountDealInfo.T_PLBID_COMPENSATORY:ObAccountDealInfo.T_LOANPAY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
									mapL.put("money",totalMoney);//交易金额	（必填）			 
									mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
									mapL.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
									mapL.put("recordNumber",requestNo);//交易流水号	（必填）
									mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
									obAccountDealInfoService.operateAcountInfo(mapL);
									ret[0]=Constants.CODE_SUCCESS;
									ret[1]="还款业务数据处理完成";
									logger.info("处理数据记录：requestNo="+requestNo+"; 描述："+ret[1]+";本次处理数据集合(FundIntentId:fundtype:orderNo)："+orderNoStr);
								}
							}
							
							//计算还款金额并发送短信
						}else{
							ret[0]=Constants.CODE_FAILED;
							ret[1]="还款业务数据处理失败:数据不存在";
							logger.info("失败原因：requestNo="+requestNo+"; 描述："+ret[1]);
						}
					}
				}else{//对于非联动优势的第三方处理直接返回成功
					ret[0]=Constants.CODE_SUCCESS;
					ret[1]="还款业务数据处理完成";
				}
			}
		}
		return ret;
	}

	@Override
	public void handleErpRepayment(String planId,String cardNo,String period,String path,HttpServletRequest request,BpCustMember mem) {
		PlBidPlan plBidPlan =plBidPlanService.get(Long.valueOf(planId));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BpFundIntent> fundlist;
		String payintentPeriod = "";
		try {
			if(mem.getThirdPayConfig()!=null&&!"".equals(mem.getThirdPayConfig())&&!mem.getThirdPayConfig().toLowerCase().equals("umpayConfig".toLowerCase())){
				fundlist = bpFundIntentService.getThirdBpFundIntentList(planId,sdf.parse(period));
				if(fundlist!=null&&fundlist.size()>0){
					BigDecimal sumMoney = new BigDecimal(0);
					for(BpFundIntent bptemp:fundlist){
						if(payintentPeriod.equals("")){
							payintentPeriod = bptemp.getPayintentPeriod().toString();
						}
						if(!bptemp.getFundType().equals("principalLending")){
							 //计算借款人还款总金额
							  sumMoney=sumMoney.add(bptemp.getNotMoney());
							  if(bptemp.getNotMoney().compareTo(new BigDecimal(0))>0&&bptemp.getFactDate()==null){
								  if(bptemp.getFundType().equals("loanInterest")||bptemp.getFundType().equals("principalRepayment")){
									  PlBidInfo plbidinfo=plBidInfoService.getByOrderNo(bptemp.getOrderNo());
									  Long custmemId=bptemp.getInvestPersonId();
									  String bidOrderNo=bptemp.getOrderNo();
									  if(plbidinfo!=null&&plbidinfo.getNewInvestPersonId()!=null){
										  custmemId=plbidinfo.getNewInvestPersonId();
										  bidOrderNo=plbidinfo.getNewOrderNo();
									  }
									  BpCustMember bps=bpCustMemberService.get(custmemId);
									  Map<String,Object> map=new HashMap<String,Object>();
									  map.put("investPersonId",bps.getId());//投资人Id（必填）
									  map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
									  map.put("transferType",bptemp.getFundType().equals("loanInterest")?ObAccountDealInfo.T_PROFIT:ObAccountDealInfo.T_PRINCIALBACK);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
									  map.put("money",bptemp.getNotMoney());//交易金额	（必填）			 
									  map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
									  map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
									  map.put("recordNumber",cardNo+"-"+bptemp.getFundIntentId()+"I");//交易流水号	（必填）
									  map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
									  obAccountDealInfoService.operateAcountInfo(map);
								  }
								  ThirdPayRecord record = thirdPayRecordService.getByOrderNo(cardNo);
								  if(record!=null&&record.getRecordNumber()!=null){
									  bptemp.setNotMoney(new BigDecimal(0));
									  bptemp.setAfterMoney(bptemp.getIncomeMoney());
									  bptemp.setFactDate(new Date());						}
								  bptemp.setRequestNo(cardNo);
								  bptemp.setRequestDate(new Date());
								  bptemp.setRequestCount(bptemp.getRequestCount()!=null?bptemp.getRequestCount().intValue()+1:1);
								  bptemp.setRepaySource(BpFundIntent.REPAYSOURCE1);
								  bpFundIntentService.save(bptemp);
							  }
							  }
					}
					plBidPlanService.bidComplete(Long.valueOf(planId), request);	
					//生成借款人资金明细
					if(sumMoney.compareTo(new BigDecimal(0))>0){
						Map<String,Object> mapL=new HashMap<String,Object>();
						mapL.put("investPersonId",mem.getId());//投资人Id（必填）
						mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						mapL.put("transferType",ObAccountDealInfo.T_LOANPAY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						mapL.put("money",sumMoney);//交易金额	（必填）			 
						mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						mapL.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						mapL.put("recordNumber",cardNo+"-"+fundlist.get(0).getFundIntentId()+"L");//交易流水号	（必填）
						mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						obAccountDealInfoService.operateAcountInfo(mapL);
					}
				}
				//还款成功发送短信
				List<InvestPersonInfo> list=investPersonInfoService.getByBidPlanId(Long.valueOf(planId));
				if(list!=null&&list.size()>0){
					for(InvestPersonInfo temp:list){
						BpCustMember bp = bpCustMemberService.get(temp.getInvestPersonId());
						//统计利息和补偿息
						BigDecimal  lxAndBcx=bpFundIntentService.getAfterMoney(temp.getOrderNo(),planId,period,"('loanInterest','interestPenalty')");
						if(null ==lxAndBcx){
							lxAndBcx=new BigDecimal(0.00);
						}
						//统计本金
						BigDecimal  bj=bpFundIntentService.getAfterMoney(temp.getOrderNo(),planId,period,"('principalRepayment')");
						if(null ==bj){
							bj=new BigDecimal(0.00);
						}
						Map<String, String> map = new HashMap<String, String>();
						map.put("telephone", bp.getTelphone());
						map.put("${name}", bp.getTruename());
						map.put("${investPrincipal}", bj.toString());
						map.put("${investInterest}", lxAndBcx.toString());
						map.put("${projName}", plBidPlan.getBidProName());
						map.put("${payintentPeriod}", payintentPeriod);
						map.put("key","sms_paymentRemind");
						smsSendService.smsSending(map);
						
						//smsSendUtil.sms_paymentRemind(bp.getTelphone(), bp.getTruename(),bj.toString(), lxAndBcx.toString(), plBidPlan.getBidProName(),payintentPeriod);
					}
				}
					//平台派发优惠券，加息奖励
					System.out.println("平台派发优惠券，加息奖励");
					List<BpFundIntent> couponslist= bpFundIntentService.getByRequestNo(cardNo);
					if(couponslist.size()>0){
						//平台发送奖励金额
						this.checkCouponsIntent(couponslist.get(0).getBidPlanId().toString(),couponslist.get(0).getPayintentPeriod().toString(),cardNo,path);
					}
			}else{
				}
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

	/**
	 * 放款后处理业务数据
	 */
	public void handleErpLoan(PlBidPlan bidplan,BpCustMember LoanMember,String orderNum,BigDecimal totalMoney,
			List<InvestPersonInfo>investPersionList,BigDecimal infoMoney,String tranferType,String thirdConfigType) {
		
		if(thirdConfigType==null||thirdConfigType.equals("")){//如果没传类型的话则默认先生成投资人的资金明细  再生成借款人的资金明细
			Map<String,Object> map=new HashMap<String,Object>();
	    	map.put("investPersonId",LoanMember.getId());//投资人Id（必填）
	    	map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
	    	if(!tranferType.equals("")&&("1").equals(tranferType)){
	    		map.put("money",totalMoney);//交易金额	（必填）	
	    		map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）	
	    		map.put("transferType",ObAccountDealInfo.T_PLATEFORM_ONETIMEFEE);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
	    	}else{
	    		map.put("money",infoMoney);//交易金额	（必填）	
	    		map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
	    		map.put("transferType",ObAccountDealInfo.T_INMONEY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
	    	}
	    	map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
	    	map.put("recordNumber",orderNum);//交易流水号	（必填）
	    	map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
	    	String[] relt =obAccountDealInfoService.operateAcountInfo(map);
		}
    	if(!tranferType.equals("")&&("1").equals(tranferType)){
    		
    	}else{
    		for(InvestPersonInfo temp2:investPersionList){
    			PlBidInfo bidInfo1 = plBidInfoService.getByOrderNo(temp2.getOrderNo());
                BpCustMember bp = bpCustMemberService.get(temp2.getInvestPersonId());
                List<BpFundIntent> bpFundIntentList = bpFundIntentService.findByType(temp2.getInvestPersonId(), bidplan.getBidId());
                if (bpFundIntentList != null && bpFundIntentList.size()>0){
//                smsSendUtil.sms_loanSend(bp.getTelphone(),bp.getTruename(),bidplan.getBidProNumber(),bidplan.getBidProName(),"raiseinterest");
                }else {
//                smsSendUtil.sms_loanSend(bp.getTelphone(),bp.getTruename(),bidplan.getBidProNumber(),bidplan.getBidProName(),"loan");
                }
                Map<String,Object> mapUP=new HashMap<String,Object>();
    			mapUP.put("investPersonId",temp2.getInvestPersonId());//投资人Id
    			mapUP.put("investPersonType",temp2.getPersionType());//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
    			mapUP.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
    			mapUP.put("money",temp2.getInvestMoney());//交易金额
    			mapUP.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
    			mapUP.put("DealInfoId",null);//交易记录id，没有默认为null
    			mapUP.put("recordNumber",temp2.getOrderNo());//交易流水号
    			mapUP.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
    			
    			//此处注掉是因为投标的时候已经减掉一次钱了
//    			String[] rett =obAccountDealInfoService.updateAcountInfo(mapUP);
    			bidInfo1.setState(Short.valueOf("2"));
    			plBidInfoService.save(bidInfo1);
    			//判断是否有投标活动设置，此段代码只能放在业务处理完以后进行
    			bpActivityManageService.addbpActivityManage(bidInfo1.getUserId(),bidInfo1.getUserMoney(),bidInfo1.getBidtime());
    			//判断此标是否可使用优惠券
    			bpActivityManageService.checkCoupons(bidplan,bidInfo1,temp2.getOrderNo());
    		}
    	}
	//	bidplan.setState(5);
		plBidPlanService.save(bidplan);
		//调用平台收取费用的接口
}
	/**
	 * 放款后处理业务数据
	 */
	public void handleErpLoanApply(PlBidPlan bidplan,BpCustMember LoanMember,String orderNum,BigDecimal totalMoney,
			List<InvestPersonInfo>investPersionList,BigDecimal infoMoney,String tranferType,String thirdConfigType) {
		
		if(thirdConfigType==null||thirdConfigType.equals("")){//如果没传类型的话则默认先生成投资人的资金明细  再生成借款人的资金明细
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("investPersonId",LoanMember.getId());//投资人Id（必填）
			map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
			if(!tranferType.equals("")&&("1").equals(tranferType)){
				map.put("money",totalMoney);//交易金额	（必填）	
				map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）	
				map.put("transferType",ObAccountDealInfo.T_PLATEFORM_ONETIMEFEE);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			}else{
				map.put("money",infoMoney);//交易金额	（必填）	
				map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
				map.put("transferType",ObAccountDealInfo.T_INMONEY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
			}
			map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
			map.put("recordNumber",orderNum);//交易流水号	（必填）
			map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_1);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
			String[] relt =obAccountDealInfoService.operateAcountInfo(map);
		}
		if(!tranferType.equals("")&&("1").equals(tranferType)){
			
		}else{
			for(InvestPersonInfo temp2:investPersionList){
				PlBidInfo bidInfo1 = plBidInfoService.getByOrderNo(temp2.getOrderNo());
				Map<String,Object> mapUP=new HashMap<String,Object>();
				mapUP.put("investPersonId",temp2.getInvestPersonId());//投资人Id
				mapUP.put("investPersonType",temp2.getPersionType());//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				mapUP.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				mapUP.put("money",temp2.getInvestMoney());//交易金额
				mapUP.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				mapUP.put("DealInfoId",null);//交易记录id，没有默认为null
				mapUP.put("recordNumber",temp2.getOrderNo());//交易流水号
				mapUP.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_1);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
				String[] rett =obAccountDealInfoService.updateAcountInfo(mapUP);
//				bidInfo1.setState(Short.valueOf("2"));
				plBidInfoService.save(bidInfo1);
				//判断是否有投标活动设置，此段代码只能放在业务处理完以后进行
				bpActivityManageService.addbpActivityManage(bidInfo1.getUserId(),bidInfo1.getUserMoney(),bidInfo1.getBidtime());
				//判断此标是否可使用优惠券
				bpActivityManageService.checkCoupons(bidplan,bidInfo1,temp2.getOrderNo());
			}
		}
		//	bidplan.setState(5);
		plBidPlanService.save(bidplan);
		//调用平台收取费用的接口
	}

	/**
	 * 注册账户起息方法
	 */
	@Override
	public String[] plmmBussiness(List<PlManageMoneyPlanBuyinfo> plist,PlManageMoneyPlan orgPlManageMoneyPlan,BpCustMember moneyReciver,CommonResponse cResponse,String loanType,BigDecimal loanMoney) {
		for(PlManageMoneyPlanBuyinfo plinfo : plist){
			plinfo.setState((short)2);
			if(plinfo.getKeystr().equals("UPlan")){//U计划默认为托管模式
				plinfo.setIsAtuoMatch(1);//默认托管模式，可进行自动债权匹配
			}else{
				plinfo.setIsAtuoMatch(0);//默认为非托管模式，不可进行自动债权匹配
			}
			//增加投资人流水
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("investPersonId",plinfo.getInvestPersonId());//投资人Id
			map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
			map.put("transferType",ObAccountDealInfo.T_INVEST);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
			map.put("money", plinfo.getBuyMoney());//交易金额
			map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
			map.put("DealInfoId",null);//交易记录id，没有默认为null
			map.put("recordNumber",plinfo.getDealInfoNumber());//交易流水号
			map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
			String[] ret =obAccountDealInfoService.updateAcountInfo(map);
			
			if(CommonResponse.RESPONSECODE_SUCCESS.equals(cResponse.getResponsecode())){
				//更新加入费用资金流水记录
				Map<String,Object> mapUPlan=new HashMap<String,Object>();
				mapUPlan.put("investPersonId",plinfo.getInvestPersonId());//投资人Id
				mapUPlan.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				mapUPlan.put("transferType",ObAccountDealInfo.T_JOIN);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				mapUPlan.put("money", plinfo.getJoinMoney());//加入费用
				mapUPlan.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				mapUPlan.put("DealInfoId",null);//交易记录id，没有默认为null
				mapUPlan.put("recordNumber",plinfo.getDealInfoNumber()+"TJ");//交易流水号
				mapUPlan.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
				obAccountDealInfoService.updateAcountInfo(mapUPlan);
			}
			plinfo.setContractNo(DateUtil.dateToStr(new Date(), "yyyyMMdd")+"_"+plinfo.getOrderId());
			plManageMoneyPlanBuyinfoService.save(plinfo);
			
			plMmOrderAssignInterestService.createUPlanAssignInerestlist(plinfo,orgPlManageMoneyPlan);
			//判断是否有投标活动设置
			bpActivityManageService.addbpActivityManage(plinfo.getInvestPersonId(), plinfo.getBuyMoney(),plinfo.getBuyDatetime());
			//派发奖励
			plManageMoneyPlanService.checkCoupons(plinfo);
		}
		//增加收款专户资金流水
		if(loanType.equals("finish")&&loanMoney.compareTo(new BigDecimal(0))>0){
				String orderNum =ContextUtil.createRuestNumber();
				Map<String,Object> mapL=new HashMap<String,Object>();
				mapL.put("investPersonId",moneyReciver.getId());//投资人Id
				mapL.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				mapL.put("transferType",ObAccountDealInfo.T_INMONEY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				mapL.put("money", loanMoney);//交易金额
				mapL.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				mapL.put("DealInfoId",null);//交易记录id，没有默认为null
				mapL.put("recordNumber",orderNum+"-L");//交易流水号
				mapL.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
				String[] retL =obAccountDealInfoService.operateAcountInfo(mapL);
		}
		return null;
	}

	@Override
	public void handleErpFee(String bidId,String projectId,HttpServletRequest request) {
			QueryFilter filter1 = new QueryFilter(request);
	    	filter1.addFilter("Q_bidPlanId_L_EQ", bidId);
	    	filter1.addFilter("Q_projectId_L_EQ", projectId);
	    	filter1.addFilter("Q_businessType_S_EQ","SmallLoan");
	    	List<SlActualToCharge> list = slActualToChargeService.getAll(filter1);
	    	if(list!=null&&list.size()>0){
	    		for(SlActualToCharge temp :list){
	    			temp.setAfterMoney(temp.getIncomeMoney().compareTo(new BigDecimal(0))==0?temp.getPayMoney():temp.getIncomeMoney());
	    			temp.setNotMoney(new BigDecimal(0));
	    			temp.setFactDate(new Date());
	    			slActualToChargeService.merge(temp);
	    		}
	    	}
	}
	@Override
	public void handleErpFeeApply(String bidId,String projectId,HttpServletRequest request) {
		QueryFilter filter1 = new QueryFilter(request);
		filter1.addFilter("Q_bidPlanId_L_EQ", bidId);
		filter1.addFilter("Q_projectId_L_EQ", projectId);
		filter1.addFilter("Q_businessType_S_EQ","SmallLoan");
		List<SlActualToCharge> list = slActualToChargeService.getAll(filter1);
		if(list!=null&&list.size()>0){
			for(SlActualToCharge temp :list){
				temp.setAfterMoney(temp.getIncomeMoney().compareTo(new BigDecimal(0))==0?temp.getPayMoney():temp.getIncomeMoney());
				temp.setNotMoney(new BigDecimal(0));
				temp.setFactDate(new Date());
				slActualToChargeService.merge(temp);
			}
		}
	}

	@Override
	public String[] reserveHandle(Map<String, String> map) {
		String[] ret = new String[2];
		String planId = map.get("planId").toString();
		String peridId = map.get("peridId").toString();
		String orderNo = map.get("orderNo").toString();
		String cardNo = map.get("cardNo").toString();
		String perid = "";
		QueryFilter filter1=new QueryFilter();
		filter1.addFilter("Q_bidPlanId_L_EQ", planId);
		filter1.addFilter("Q_intentDate_D_EQ", peridId);
		filter1.addFilter("Q_isCheck_SN_EQ", "0");
		filter1.addFilter("Q_isValid_SN_EQ", "0");
		filter1.addFilter("Q_orderNo_S_EQ", orderNo);
		List<BpFundIntent> b=bpFundIntentService.getAll(filter1);
		if(b!=null){
			for(BpFundIntent tempp:b){
				if(!tempp.getFundType().equals("principalLending")&&!tempp.getFundType().equals("couponInterest")&&!tempp.getFundType().equals("serviceMoney")&&!tempp.getFundType().equals("consultationMoney")){
					BpCustMember bps=bpCustMemberService.get(tempp.getInvestPersonId());
					if(tempp.getNotMoney().compareTo(new BigDecimal(0))>0){
						Map<String,Object> map1=new HashMap<String,Object>();
						map1.put("investPersonId",bps.getId());//投资人Id（必填）
						map1.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						map1.put("transferType",tempp.getFundType().equals("loanInterest")?ObAccountDealInfo.T_PROFIT:ObAccountDealInfo.T_PRINCIALBACK);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						map1.put("money",tempp.getNotMoney());//交易金额	（必填）			 
						map1.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						map1.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						map1.put("recordNumber",Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));//交易流水号	（必填）
						map1.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						obAccountDealInfoService.operateAcountInfo(map1);
					}
					//登记代偿台账
					plBidCompensatoryService.saveComPensatoryService(planId,peridId,cardNo,PlBidCompensatory.TYPE_PLATE,perid);
					//如果是联动优势则不对账
					tempp.setNotMoney(new BigDecimal(0));
					tempp.setAfterMoney(tempp.getIncomeMoney());
					tempp.setFactDate(new Date());	
					tempp.setRequestNo(cardNo);
					tempp.setLoanerRepayMentStatus(1);
					tempp.setRepaySource(BpFundIntent.REPAYSOURCE2);
					bpFundIntentService.merge(tempp);
					perid=tempp.getPayintentPeriod().toString();
					//obAccountDealInfoService.operateAcountInfo(bps.getId().toString(), tempp.getFundType().equals("loanInterest")?ObAccountDealInfo.T_PROFIT.toString():ObAccountDealInfo.T_PRINCIALBACK.toString(), tempp.getNotMoney().toString(), "3", "0", ObAccountDealInfo.ISAVAILABLE.toString(), "2", Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));
				}
			}
			ret[0]=Constants.CODE_SUCCESS;
			ret[1]="还款业务数据处理完成";

		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="还款业务数据处理失败";
		}
		return   ret;
}

}


