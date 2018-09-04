package com.zhiwei.credit.action.pay;

/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thirdPayInterface.CommonRecord;
import com.thirdPayInterface.CommonRequestInvestRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.thirdPayInterface.Huifu.HuiFuInterfaceUtil;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayRecordService;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemaccountSetting;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatory;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.pay.MoneyMoreMore;
import com.zhiwei.credit.model.thirdInterface.GoZhiFuVO;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;
import com.zhiwei.credit.model.thirdInterface.Record;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemaccountSettingService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryService;
import com.zhiwei.credit.service.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepaymentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.pay.IPayService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.service.thirdInterface.EasyPayService;
import com.zhiwei.credit.service.thirdInterface.GoPayService;
import com.zhiwei.credit.service.thirdInterface.OpraterBussinessDataService;
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;
import com.zhiwei.credit.service.thirdInterface.WebBankcardService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.credit.service.thirdPay.goPay.GoPayThirdPayService;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.MD5;
import com.zhiwei.credit.util.MyUserSession;
import com.zhiwei.credit.util.RsaHelper;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;

/**
 * 
 * @author
 * 
 */
public class PayAction extends BaseAction {
	@Resource
	private PlThirdInterfaceLogService plThirdInterfaceLogService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private GoPayService goPayService;
	@Resource
	private IPayService iPayService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private GoPayThirdPayService goPayThirdPayService;
	@Resource
	private EasyPayService easyPayService;
	@Resource
	private WebBankcardService webBankCardService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private SlRiskguaranteemoneyRepaymentService slRiskguaranteemoneyRepaymentService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private PlBidCompensatoryService plBidCompensatoryService;
	@Resource
	private ThirdPayRecordService thirdPayRecordService;
	@Resource
	private OpraterBussinessDataService opraterBussinessDataService;
	@Resource
	private SlEarlyRepaymentRecordService slEarlyRepaymentRecordService;
	@Resource
	private ObSystemaccountSettingService obSystemaccountSettingService;
	SmsSendUtil smsSendUtil=new SmsSendUtil();
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	MoneyMoreMore moneyMoreMore = new MoneyMoreMore();
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * public MoneyMoreMore getMoneyMoreMore() { return moneyMoreMore; } public
	 * void setMoneyMoreMore(MoneyMoreMore moneyMoreMore) { this.moneyMoreMore =
	 * moneyMoreMore; }
	 */
	// 绑定接口
	public String bind() {
		try {
			moneyMoreMore.setLoanPlatformAccount("test666");

			moneyMoreMore.setLoanMoneymoremore("13466761447");

			moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString()
					+ "loan/toloanbind.action");
			moneyMoreMore.setReturnURL(getBasePath() + "pay/fontPay.do");
			moneyMoreMore.setNotifyURL(getBasePath() + "pay/backPay.do");

			String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();

			String dataStr = moneyMoreMore.getLoanPlatformAccount()
					+ moneyMoreMore.getLoanMoneymoremore()
					+ AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString()
					+ moneyMoreMore.getReturnURL()
					+ moneyMoreMore.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));
			Map<String, String> params = new HashMap<String, String>();
			params.put("LoanPlatformAccount", moneyMoreMore
					.getLoanPlatformAccount());
			params.put("PlatformMoneymoremore",
					AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
			params.put("LoanMoneymoremore", moneyMoreMore
					.getLoanMoneymoremore());
			params.put("ReturnURL", moneyMoreMore.getReturnURL());
			params.put("NotifyURL", moneyMoreMore.getNotifyURL());
			params.put("SignInfo", moneyMoreMore.getSignInfo());

			String url = UrlUtils.generateUrl(params, moneyMoreMore
					.getSubmitURL(), Constants.CHAR_UTF);
			WebClient.SendByUrl(this.getResponse(), url, Constants.CHAR_UTF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 注册接口
	public String register() {
		moneyMoreMore.setLoanPlatformAccount("test777");
		moneyMoreMore.setRegisterType("1");
		moneyMoreMore.setMobile("138" + Common.getRandomNum(8));
		moneyMoreMore.setEmail(Common.getRandomNum(8) + "@qqq.com");
		moneyMoreMore.setRealName("aaa");
		moneyMoreMore.setIdentificationNo("130525198802132348");

		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString()
				+ "loan/toloanregister.action");
		moneyMoreMore.setReturnURL(getBasePath() + "pay/fontPay.do");
		moneyMoreMore.setNotifyURL(getBasePath() + "pay/backPay.do");

		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();

		String dataStr = moneyMoreMore.getRegisterType()
				+ moneyMoreMore.getMobile() + moneyMoreMore.getEmail()
				+ moneyMoreMore.getRealName()
				+ moneyMoreMore.getIdentificationNo()
				+ moneyMoreMore.getImage1() + moneyMoreMore.getImage2()
				+ AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString()
				+ moneyMoreMore.getReturnURL() + moneyMoreMore.getNotifyURL();
		System.out.println("加密前===" + dataStr);

		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));
		System.out.println("加密后===" + moneyMoreMore.getSignInfo());

		Map<String, String> params = new HashMap<String, String>();
		params.put("RegisterType", moneyMoreMore.getRegisterType());
		params.put("Mobile", moneyMoreMore.getMobile());
		params.put("Email", moneyMoreMore.getEmail());
		params.put("RealName", moneyMoreMore.getRealName());
		params.put("IdentificationNo", moneyMoreMore.getIdentificationNo());

		params.put("Image1", moneyMoreMore.getImage1());
		params.put("Image2", moneyMoreMore.getImage2());

		params.put("LoanPlatformAccount", moneyMoreMore
				.getLoanPlatformAccount());
		params
				.put("PlatformMoneymoremore",
						AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());

		params.put("ReturnURL", moneyMoreMore.getReturnURL());
		params.put("NotifyURL", moneyMoreMore.getNotifyURL());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		String url = "";
		try {
			url = UrlUtils.generateUrl(params, moneyMoreMore.getSubmitURL(),
					Constants.CHAR_UTF);
		} catch (Exception e) {
		}
		WebClient.SendByUrl(this.getResponse(), url, Constants.CHAR_UTF);
		return SUCCESS;
	}

	// 注册并绑定接口
	public String registerAndBind() {
		moneyMoreMore.setLoanPlatformAccount("test777");
		moneyMoreMore.setRegisterType("1");
		moneyMoreMore.setMobile("138" + Common.getRandomNum(8));
		moneyMoreMore.setEmail(Common.getRandomNum(8) + "@qqq.com");
		moneyMoreMore.setRealName("aaa");
		moneyMoreMore.setIdentificationNo("130525198802132348");

		moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString()
				+ "loan/toloanregisterbind.action");
		moneyMoreMore.setReturnURL(getBasePath() + "pay/fontPay.do");
		moneyMoreMore.setNotifyURL(getBasePath() + "pay/backPay.do");

		String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();

		String dataStr = moneyMoreMore.getRegisterType()
				+ moneyMoreMore.getMobile() + moneyMoreMore.getEmail()
				+ moneyMoreMore.getRealName()
				+ moneyMoreMore.getIdentificationNo()
				+ moneyMoreMore.getImage1() + moneyMoreMore.getImage2()
				+ moneyMoreMore.getLoanPlatformAccount()
				+ AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString()
				+ moneyMoreMore.getReturnURL() + moneyMoreMore.getNotifyURL();
		System.out.println("加密前===" + dataStr);

		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));
		System.out.println("加密后===" + moneyMoreMore.getSignInfo());

		Map<String, String> params = new HashMap<String, String>();
		params.put("RegisterType", moneyMoreMore.getRegisterType());
		params.put("Mobile", moneyMoreMore.getMobile());
		params.put("Email", moneyMoreMore.getEmail());
		params.put("RealName", moneyMoreMore.getRealName());
		params.put("IdentificationNo", moneyMoreMore.getIdentificationNo());

		params.put("Image1", moneyMoreMore.getImage1());
		params.put("Image2", moneyMoreMore.getImage2());

		params.put("LoanPlatformAccount", moneyMoreMore
				.getLoanPlatformAccount());
		params
				.put("PlatformMoneymoremore",
						AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());

		params.put("ReturnURL", moneyMoreMore.getReturnURL());
		params.put("NotifyURL", moneyMoreMore.getNotifyURL());
		params.put("SignInfo", moneyMoreMore.getSignInfo());
		String url = "";
		try {
			url = UrlUtils.generateUrl(params, moneyMoreMore.getSubmitURL(),
					Constants.CHAR_UTF);
		} catch (Exception e) {
		}
		WebClient.SendByUrl(this.getResponse(), url, Constants.CHAR_UTF);
		return SUCCESS;
	}

	/**
	 * 充值接口
	 */
	public String recharge() {
		try {
			moneyMoreMore.setOrderNo(Common.getRandomNum(10));
			moneyMoreMore.setAmount("10");

			moneyMoreMore.setRechargeMoneymoremore("m383");

			moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString()
					+ "loan/toloanrecharge.action");
			moneyMoreMore.setReturnURL(getBasePath() + "pay/fontPay.do");
			moneyMoreMore.setNotifyURL(getBasePath() + "pay/backPay.do");

			String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();

			String dataStr = moneyMoreMore.getRechargeMoneymoremore()
					+ AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString()
					+ moneyMoreMore.getOrderNo() + moneyMoreMore.getAmount()
					+ moneyMoreMore.getReturnURL()
					+ moneyMoreMore.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));

			Map<String, String> params = new HashMap<String, String>();
			params.put("RechargeMoneymoremore", moneyMoreMore
					.getRechargeMoneymoremore());
			params.put("PlatformMoneymoremore",
					AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());

			params.put("OrderNo", moneyMoreMore.getOrderNo());
			params.put("Amount", moneyMoreMore.getAmount());

			params.put("ReturnURL", moneyMoreMore.getReturnURL());
			params.put("NotifyURL", moneyMoreMore.getNotifyURL());
			params.put("SignInfo", moneyMoreMore.getSignInfo());
			String url = "";
			try {
				url = UrlUtils.generateUrl(params,
						moneyMoreMore.getSubmitURL(), Constants.CHAR_UTF);
			} catch (Exception e) {
			}
			WebClient.SendByUrl(this.getResponse(), url, Constants.CHAR_UTF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 提现接口
	 */
	public String withdraws() {
		try {
			moneyMoreMore.setOrderNo(Common.getRandomNum(10));
			moneyMoreMore.setAmount("10");
			moneyMoreMore.setFeePercent("50");
			moneyMoreMore.setCardNo("6222123412341234123");
			moneyMoreMore.setCardType("0");
			moneyMoreMore.setBankCode("10");
			moneyMoreMore.setBranchBankName("招商银行苏州支行");
			moneyMoreMore.setProvince("10");
			moneyMoreMore.setCity("1078");

			moneyMoreMore.setWithdrawMoneymoremore("p1");

			moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString()
					+ "loan/toloanwithdraws.action");
			moneyMoreMore.setReturnURL(getBasePath() + "pay/fontPay.do");
			moneyMoreMore.setNotifyURL(getBasePath() + "pay/backPay.do");

			String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();
			String publickey = AppUtil.getSysConfig().get("MM_PublicKey").toString();

			String dataStr = moneyMoreMore.getWithdrawMoneymoremore()
					+ AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString()
					+ moneyMoreMore.getOrderNo() + moneyMoreMore.getAmount()
					+ moneyMoreMore.getFeePercent() + moneyMoreMore.getCardNo()
					+ moneyMoreMore.getCardType() + moneyMoreMore.getBankCode()
					+ moneyMoreMore.getBranchBankName()
					+ moneyMoreMore.getProvince() + moneyMoreMore.getCity()
					+ moneyMoreMore.getReturnURL()
					+ moneyMoreMore.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));
			moneyMoreMore.setCardNo(rsa.encryptData(moneyMoreMore.getCardNo(),
					publickey));

			Map<String, String> params = new HashMap<String, String>();
			params.put("OrderNo", moneyMoreMore.getOrderNo());
			params.put("Amount", moneyMoreMore.getAmount());

			params.put("FeePercent", moneyMoreMore.getFeePercent());
			params.put("CardNo", moneyMoreMore.getCardNo());

			params.put("CardType", moneyMoreMore.getCardType());
			params.put("BankCode", moneyMoreMore.getBankCode());

			params.put("BranchBankName", moneyMoreMore.getBranchBankName());
			params.put("Province", moneyMoreMore.getProvince());

			params.put("City", moneyMoreMore.getCity());
			params.put("PlatformMoneymoremore",
					AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
			params.put("WithdrawMoneymoremore", moneyMoreMore
					.getWithdrawMoneymoremore());

			params.put("ReturnURL", moneyMoreMore.getReturnURL());
			params.put("NotifyURL", moneyMoreMore.getNotifyURL());
			params.put("SignInfo", moneyMoreMore.getSignInfo());
			String url = "";
			try {
				url = UrlUtils.generateUrl(params,
						moneyMoreMore.getSubmitURL(), Constants.CHAR_UTF);
			} catch (Exception e) {
			}
			WebClient.SendByUrl(this.getResponse(), url, Constants.CHAR_UTF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 获取银行列表
	 * @return
	 */
	public String getBankList(){
		try{
			String payType=this.getRequest().getParameter("payType");
			Properties p=new Properties();
			if(payType!=null&&!"".equals(payType)){
				p=ThirdPayInterfaceUtil.getBankList(payType);
			}else{
				p=null;
			}
			if(p!=null){
				StringBuffer buff = new StringBuffer("[");
				Set set= p.keySet();
				Iterator it= p.keySet().iterator();
	    		while(it.hasNext()){
	    			String key=(String)it.next();
	    			buff.append("['").append(key).append("','").append(p.getProperty(key)).append("'],");
	    		}
	    		if (set!=null&&set.size()>0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]");
				setJsonString(buff.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 查询平台余额
	 * @return
	 */
	public String getPlatformMoney(){
		CommonRequst common = new CommonRequst();
		CommonResponse commonResponse = new CommonResponse();
		String orderNum =ContextUtil.createRuestNumber();
		common.setRequsetNo(orderNum);//请求流水号
		common.setThirdPayConfigId("platform");
		common.setBussinessType(ThirdPayConstants.BT_GETPLATFORM);
		common.setTransferName(ThirdPayConstants.TN_GETPLATFORM);
		commonResponse=ThirdPayInterfaceUtil.thirdCommon(common);
		if(commonResponse!=null&&commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
			String msg="";
			msg="【<font style='line-height:20px'>账号：</font><font style='line-height:20px'>"+commonResponse.getThirdPayConfigId()+"</font>   <font style='line-height:20px'>账户可用余额：</font><font style='line-height:20px'>"+commonResponse.getBalance()+"元</font> &nbsp; 账户余额：</font><font style='line-height:20px'>"+ commonResponse.getAccountBalance()+"元</font> &nbsp; 账户冻结余额：</font><font style='line-height:20px'>"+commonResponse.getFreezeAmount()+"元 】";
			jsonString = "{success:true,msg:\""+msg+"\"}";
			return SUCCESS;
		}else{
			String msg="查询报错："+commonResponse.getResponseMsg();
			jsonString = "{success:true,msg:\""+msg+"\"}";
			return SUCCESS;
		}
	}
	/**
	 * 平台商户充值
	 * @return
	 */
	public String platformRechare(){
		CommonRequst common =new CommonRequst();
		String orderNum = ContextUtil.createRuestNumber();
		String bankCode=this.getRequest().getParameter("bankcode");
		String payType=this.getRequest().getParameter("payType");
		String rerchargeMoney=this.getRequest().getParameter("rerchargeMoney");
		//企业用户
    	common.setThirdPayConfigId("platform");//用户第三方账号
		common.setAccountType(1);//1代表企业账户 0代表个人账户
		common.setBankCode(bankCode);
		common.setRequsetNo(orderNum);//流水号
		common.setAmount(new BigDecimal(rerchargeMoney));//交易金额
		common.setTransferName(ThirdPayConstants.TN_PLATFORMPAY);//充值
		common.setBussinessType(ThirdPayConstants.BT_PLATFORMPAY);//业务类型
		CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(common);
		if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
			
		}
		return SUCCESS;
	}
	/**
	 * 查询平台账户流水
	 * @return
	 */
	public String plateFlowQuery(){
		try{
			String orderNum = ContextUtil.createRuestNumber();
			String   plateFromFinanceType=this.getRequest().getParameter("accountType");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			CommonRequst commonrequest=new CommonRequst();
			Date startDate=this.getRequest().getParameter("startDate")!=null?sdf.parse(this.getRequest().getParameter("startDate")):new Date();
			Date endDate=this.getRequest().getParameter("endDate")!=null?sdf.parse(this.getRequest().getParameter("endDate")):new Date();
			String pageNumber=this.getRequest().getParameter("pageNumber")!=null?this.getRequest().getParameter("pageNumber"):"1";
			commonrequest.setQueryStartDate(startDate);//查询开始时间
			commonrequest.setQueryEndDate(endDate);//查询结束时间
			commonrequest.setRequsetNo(orderNum);
			commonrequest.setBussinessType(ThirdPayConstants.BT_QPLATFORM);
			commonrequest.setTransferName(ThirdPayConstants.TN_QPLATFORM);
			CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonrequest);
			if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
				if(commonrequest.getRecord()!=null){
					List<CommonRecord> list=commonrequest.getRecord();
					Type type = new TypeToken<List<CommonRecord>>() {
					}.getType();
					StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
							.append(list == null ? 0 : list.size()).append(",result:");
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
					buff.append(gson.toJson(list, type));
					buff.append("}");
					jsonString = buff.toString();
				}else if(commonResponse.getRecordList()!=null){
					List<CommonRecord> list=commonResponse.getRecordList();
					for(CommonRecord record:list){
						if(record.getRequestNo()!=null&&!record.getRequestNo().equals("")){
							ObAccountDealInfo deal = obAccountDealInfoService.getByOrderNumber(record.getRequestNo(), "", "", "");
							if(deal!=null){
								if(deal.getIncomMoney().compareTo(new BigDecimal(0))>0){//平台借贷方向和用户相反
									record.setMarkType("支出");
								}else{
									record.setMarkType("收入");
								}
								QueryFilter filter = new QueryFilter(this.getRequest());
								filter.addFilter("Q_typeKey_S_EQ", deal.getTransferType());
								List<ObSystemaccountSetting> sett = obSystemaccountSettingService.getAll(filter);
								if(sett.size()>0){
									record.setBizType(sett.get(0).getTypeName());
								}
							}
						}
					}
					Type type = new TypeToken<List<CommonRecord>>() {
					}.getType();
					StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
							.append(list == null ? 0 : list.size()).append(",result:");
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
					buff.append(gson.toJson(list, type));
					buff.append("}");
					jsonString = buff.toString();
					System.out.println(jsonString);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 余额查询
	 */
	public String balanceQuery() {
		String retdata = "";
		BpCustMember mem = (BpCustMember) this.getSession().getAttribute(
				MyUserSession.MEMBEER_SESSION);
		if (mem != null) {
			BpCustMember org = bpCustMemberService.get(mem.getId());

			try {
				// 传入用户钱多多标识 即可 查询
				retdata = iPayService.balanceQueryMoneys(org
						.getMoneymoremoreId(), MoneyMoreMore.TTYPE_1);
				System.out.println(retdata);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		setJsonString(retdata);
		return SUCCESS;
	}

	/**
	 * 对账接口
	 */
	public String orderQuery() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar calendar = Calendar.getInstance();
			moneyMoreMore.setEndTime(sdf.format(calendar.getTime()));
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			moneyMoreMore.setBeginTime(sdf.format(calendar.getTime()));

			/*
			 * moneyMoreMore.setLoanNo(""); moneyMoreMore.setOrderNo("");
			 * moneyMoreMore.setBatchNo("");
			 */

			moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString()
					+ "loan/loanorderquery.action");
			String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();

			String dataStr = AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString()
					+ moneyMoreMore.getLoanNo() + moneyMoreMore.getOrderNo()
					+ moneyMoreMore.getBatchNo() + moneyMoreMore.getBeginTime()
					+ moneyMoreMore.getEndTime();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));

			Map<String, String> params = new HashMap<String, String>();
			params.put("EndTime", moneyMoreMore.getEndTime());
			params.put("BeginTime", moneyMoreMore.getBeginTime());
			params.put("PlatformMoneymoremore",
					AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
			/*
			 * params.put("LoanNo", moneyMoreMore.getLoanNo());
			 * params.put("OrderNo", moneyMoreMore.getOrderNo());
			 * params.put("BatchNo", moneyMoreMore.getBatchNo());
			 */
			params.put("SignInfo", moneyMoreMore.getSignInfo());

			String url = "";
			try {
				url = UrlUtils.generateUrl(params,
						moneyMoreMore.getSubmitURL(), Constants.CHAR_UTF);
				System.out.println(url);
			} catch (Exception e) {
			}
			WebClient.SendByUrl(this.getResponse(), url, Constants.CHAR_UTF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 资金释放接口
	 */
	public String moneyReaease() {
		try {
			MoneyMoreMore moneyMoreMore = new MoneyMoreMore();
			moneyMoreMore.setMoneymoremoreId("m449");
			moneyMoreMore.setAmount("5.00");
			moneyMoreMore.setOrderNo("8066054471");

			iPayService.moneyReaease(moneyMoreMore, this.getBasePath(), this
					.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 授权接口
	 */
	public String authorize() {
		try {

			moneyMoreMore.setMoneymoremoreId("m382");
			moneyMoreMore.setAuthorizeTypeOpen("2");
			moneyMoreMore.setAuthorizeTypeClose("1,3");

			moneyMoreMore.setSubmitURL(AppUtil.getSysConfig().get("MM_PostUrl").toString()
					+ "loan/toloanauthorize.action");
			moneyMoreMore.setReturnURL(getBasePath() + "pay/fontPay.do");
			moneyMoreMore.setNotifyURL(getBasePath() + "pay/backPay.do");

			String privatekey = AppUtil.getSysConfig().get("MM_PrivateKey").toString();

			String dataStr = moneyMoreMore.getMoneymoremoreId()
					+ AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString()
					+ moneyMoreMore.getAuthorizeTypeOpen()
					+ moneyMoreMore.getAuthorizeTypeClose()
					+ moneyMoreMore.getReturnURL()
					+ moneyMoreMore.getNotifyURL();
			// 签名
			RsaHelper rsa = RsaHelper.getInstance();
			moneyMoreMore.setSignInfo(rsa.signData(dataStr, privatekey));

			Map<String, String> params = new HashMap<String, String>();
			params.put("PlatformMoneymoremore",
					AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
			params.put("MoneymoremoreId", moneyMoreMore.getMoneymoremoreId());
			params.put("AuthorizeTypeOpen", moneyMoreMore
					.getAuthorizeTypeOpen());
			params.put("AuthorizeTypeClose", moneyMoreMore
					.getAuthorizeTypeClose());

			params.put("ReturnURL", moneyMoreMore.getReturnURL());
			params.put("NotifyURL", moneyMoreMore.getNotifyURL());
			params.put("SignInfo", moneyMoreMore.getSignInfo());

			String url = "";
			try {
				url = UrlUtils.generateUrl(params,
						moneyMoreMore.getSubmitURL(), Constants.CHAR_UTF);
				System.out.println(url);
			} catch (Exception e) {
			}
			WebClient.SendByUrl(this.getResponse(), url, Constants.CHAR_UTF);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String font() {
		/*
		 * String ResultCode = this.getRequest().getParameter("ResultCode");
		 * String LoanPlatformAccount = this.getRequest().getParameter(
		 * "LoanPlatformAccount"); String PlatformMoneymoremore =
		 * this.getRequest().getParameter( "PlatformMoneymoremore"); String
		 * MoneymoremoreId = this.getRequest().getParameter( "MoneymoremoreId");
		 * String SignInfo = this.getRequest().getParameter("SignInfo");
		 * System.out.println("返回值前台：==========返回code:" + ResultCode + "平台账号：" +
		 * LoanPlatformAccount + "平台乾多多标识:" + LoanPlatformAccount + "用户的乾多多标识:"
		 * + LoanPlatformAccount + "验证信息：" + SignInfo);
		 */
		return SUCCESS;
	}

	/**
	 * 国付宝 提现接口
	 */
	public String[] gpWithdraws(ObAccountDealInfo accountDetal) {
		String[] ret=new String[2];
		if (accountDetal.getBankId() != null && !"".equals(accountDetal.getBankId())) {
			WebBankcard card = webBankCardService.get(accountDetal.getBankId());
			if(card!=null){
				String xml = goPayService.withdraw(this.getResponse(), card
						.getBankname(), card.getCardNum(), card
						.getSubbranchbank(), card.getProvinceName(), card.getBankname(),
						card.getCityName(), accountDetal.getPayMoney().toString(), "取现",
						Constants.CHAR_GBK, "60000");
				GoZhiFuVO zhifuVO = goPayService.strToDocument(xml);

				// 组织加密明文
				String plain = "version=[" + zhifuVO.getVersion0() + "]tranCode=["
						+ zhifuVO.getTranCode0() + "]customerId=["
						+ zhifuVO.getCustomerId() + "]merOrderNum=["
						+ zhifuVO.getMerOrderNum() + "]tranAmt=["
						+ zhifuVO.getTranAmt() + "]feeAmt=[" + zhifuVO.getFeeAmt()
						+ "]totalAmount=[" + zhifuVO.getTotalAmount() + "]merURL=["
						+ zhifuVO.getMerURL() + "]recvBankAcctNum=["
						+ zhifuVO.getRecvBankAcctNum() + "]tranDateTime=["
						+ zhifuVO.getTranDateTime() + "]orderId=["
						+ zhifuVO.getOrderId() + "]respCode=["
						+ zhifuVO.getRespCode() + "]VerficationCode=["
						+ zhifuVO.getVerficationCode() + "]";
				MD5 md5 = new MD5();
				String signValue = md5.md5(plain, Constants.CHAR_GBK);
				Short status=ObAccountDealInfo.DEAL_STATUS_3;
				if (signValue.equals(zhifuVO.getSignValue())) {
					 status=ObAccountDealInfo.DEAL_STATUS_2;
					//ret=obAccountDealInfoService.updateAcountInfo(accountDetal.getInvestPersonId(), "2", zhifuVO.getTranAmt(), "0", zhifuVO.getMerOrderNum(), accountDetal.getId(), "2");
				}/*else{
					ret=obAccountDealInfoService.updateAcountInfo(accountDetal.getInvestPersonId(), "2", zhifuVO.getTranAmt(), "0", zhifuVO.getMerOrderNum(), accountDetal.getId(), "3");
				}*/
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("investPersonId",accountDetal.getInvestPersonId());//投资人Id
				map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
				map.put("transferType",ObAccountDealInfo.T_ENCHASHMENT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
				map.put("money",new BigDecimal(zhifuVO.getTranAmt()));//交易金额
				map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
				map.put("DealInfoId",null);//交易记录id，没有默认为null
				map.put("recordNumber",zhifuVO.getMerOrderNum());//交易流水号
				map.put("dealStatus",status);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
				ret=obAccountDealInfoService.updateAcountInfo(map);
				// 记录操作日志
				plThirdInterfaceLogService.saveLog(zhifuVO.getRespCode(),
						zhifuVO.getMsgExt(), plain, "国付宝取现接口", null,
						PlThirdInterfaceLog.MEMTYPE1,
						PlThirdInterfaceLog.TYPE1,
						PlThirdInterfaceLog.TYPENAME1, "", zhifuVO.getOrderId(),
						"", "");
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="没有查到绑定的银行卡信息";
			}
			
		} else {
			ret[0]=Constants.CODE_FAILED;
			ret[1]="没有选择取现银行卡";
		}
		
	
		return ret;
	}
	//ERP取现办理button  调用方法
	public String WithdrawsERP(){
		String msg = "";
		String dealInfoId =this.getRequest().getParameter("dealInfoId");
		String dealType=this.getRequest().getParameter("obAccountDealInfo.dealType");
		String[] ret=new String[2];
		if(dealInfoId!=null&&!"".equals(dealInfoId)){
			ObAccountDealInfo accountDetal = obAccountDealInfoService.get(Long.valueOf(dealInfoId));
			if(accountDetal!=null){
				accountDetal.setDealType(Short.valueOf(dealType));
				if(dealType!=null&&dealType.equals("3")){
					String thirdPayConfig=configMap.get("thirdPayConfig").toString();
					String thirdPayType=configMap.get("thirdPayType").toString();
					if(thirdPayType.equals("1")){
						if(thirdPayConfig.equals("gopayConfig")){
							ret=gpWithdraws(accountDetal);
						}else if(thirdPayConfig.equals("easypayConfig")){
							ret=easyPayService.easyPayWithdraws(accountDetal,this.getBasePath());
						}else {
							ret[0]=Constants.CODE_FAILED;
							ret[1]="尚未对接此类第三方支付的网关支付模式，请联系软件供应商";
						}
					}else{
						ret[0]=Constants.CODE_FAILED;
						ret[1]="系统错误：费网关接口不走此方法";
					}
				}else{
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("investPersonId",accountDetal.getInvestPersonId());//投资人Id
					map.put("investPersonType",accountDetal.getInvestPersonType());//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
					map.put("transferType",ObAccountDealInfo.T_ENCHASHMENT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
					map.put("money", accountDetal.getPayMoney());//交易金额
					map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
					map.put("DealInfoId",accountDetal.getId());//交易记录id，没有默认为null
					map.put("recordNumber", null==accountDetal.getRecordNumber()?"":accountDetal.getRecordNumber());//交易流水号
					map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
					ret=obAccountDealInfoService.updateAcountInfo(map);
					//ret=obAccountDealInfoService.updateAcountInfo(accountDetal.getInvestPersonId(), accountDetal.getTransferType().toString(), accountDetal.getPayMoney().toString(), accountDetal.getInvestPersonType().toString(), accountDetal.getRecordNumber(), accountDetal.getId(), "2");
				}
				if(ret!=null){
					msg=ret[1];
				}else{
					msg="系统错误，请联系管理员";
				}
			}else{
				msg="没有找到相应的取现办理记录";
			}
		}else{
			msg="取现交易记录id是必须存在的，请先选择";
		}
		System.out.println(msg);
		jsonString="{success:true,msg:'"+msg+"'}";
		return SUCCESS;
	}
	/**
	 * 第三方单笔查询接口
	 * @return
	 */
	public String query(){
		String mode =this.getRequest().getParameter("mode");
		String date =this.getRequest().getParameter("dateName");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			if(date!=null&&!date.equals("")){
				d = sdf.parse(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String orderNum=this.getRequest().getParameter("requestNo");
		String requestNo =ContextUtil.createRuestNumber();
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Type type=new TypeToken<List<Record>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if(mode!=null&&!mode.equals("")){
			CommonRequst commonRequst =new CommonRequst();
			commonRequst.setRequsetNo(requestNo);//本次请求的流水号
			commonRequst.setQueryRequsetNo(orderNum);//查询的流水号
			commonRequst.setThirdPayConfigId("");
			commonRequst.setQueryType(mode);
			commonRequst.setStartDay(d);
			commonRequst.setEndDay(d);
			if(d!=null){
				commonRequst.setTransactionTime(d);
			}
			ThirdPayRecord record1 = thirdPayRecordService.getByOrderNo(orderNum);
			/*if(record1!=null){
				if(mode!=null&&mode.equals("RECHARGE_RECORD")&&record1.getInterfaceType().equals(ThirdPayConstants.BT_RECHAGE)){//充值记录
					commonRequst.setBussinessType(ThirdPayConstants.BT_RECHARGE_QUERY);
					commonRequst.setTransferName(ThirdPayConstants.TN_RECHARGE_QUERY);
				}else if(mode!=null&&mode.equals("WITHDRAW_RECORD")&&record1.getInterfaceType().equals(ThirdPayConstants.BT_WITHDRAW)){//取现记录查询
					commonRequst.setBussinessType(ThirdPayConstants.BT_WITHDRAW_QUERY);
					commonRequst.setTransferName(ThirdPayConstants.TN_WITHDRAW_QUERY);
				}else if(mode!=null&&mode.equals("REPAYMENT_RECORD")&&(record1.getInterfaceType().equals(ThirdPayConstants.BT_REPAYMENT)||record1.getInterfaceType().equals(ThirdPayConstants.BT_BEFOREPAY)||record1.getInterfaceType().equals(ThirdPayConstants.BT_HELPPAY)||record1.getInterfaceType().equals(ThirdPayConstants.BT_MMGIVEPLATF))){//还款记录查询
					commonRequst.setBussinessType(ThirdPayConstants.BT_BIDTRANS_QUERY);
					commonRequst.setTransferName(ThirdPayConstants.TN_BIDTRANS_QUERY);
				}else if(mode!=null&&mode.equals("PAYMENT_RECORD")&&(record1.getInterfaceType().equals(ThirdPayConstants.BT_LOAN)||record1.getInterfaceType().equals(ThirdPayConstants.BT_MMLOANUSER))){//放款记录查询
					commonRequst.setBussinessType(ThirdPayConstants.BT_BIDTRANS_QUERY);
					commonRequst.setTransferName(ThirdPayConstants.TN_BIDTRANS_QUERY);
				}
			}*/
			commonRequst.setBussinessType(ThirdPayConstants.BT_QUERYPLATF);
			commonRequst.setTransferName(ThirdPayConstants.TN_QUERYPLATF);
			CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequst);
			if(commonResponse!=null&&commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
				List<CommonRecord> record=commonResponse.getRecordList();
				buff.append(record!=null?record.size():0).append(",result:");
				buff.append(gson.toJson(record, type));
			}else{
				buff.append(0).append(",result:");
				buff.append(gson.toJson(null, type));
			}
		}else{
			buff.append(0).append(",result:");
			buff.append(gson.toJson(null, type));
		}
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 平台垫付借款人逾期未还款金额
	 * 
	 */
	public String reserve(){
		int m = 0;//成功返给投资人的个数
		int n = 0;//返给投资人失败的个数
		String [] ret=new String[2];
		String cardNo=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
		String ids=this.getRequest().getParameter("ids");
		String planId=this.getRequest().getParameter("planId");
		String peridId=this.getRequest().getParameter("intentDate");
		String perid="";//期数
		String periodId = this.getRequest().getParameter("periodId");//期数
		String[] idlist=ids.split(",");
		if(planId!=null&&!"".equals(planId)&&peridId!=null&&!"".equals(peridId)&&ids!=null&&!"".equals(ids)){
				    BigDecimal repayMoney  = bpFundIntentService.getMoneyPerPeriod(planId, periodId);
				    List<BpFundIntent> list = bpFundIntentService.getByBidIdandPeriod(planId, periodId);
					int i=0;
					List<CommonRequestInvestRecord> repayList=investPersonInfoService.getRepaymentList(planId,peridId);
					String intentDate2 = "";
					if(i==0){
						if(repayList.size()>0){
							if(null!=list&&list.size()>0){
								for(BpFundIntent intent:list){
									intent.setRequestNOLoaner(cardNo);
									bpFundIntentService.merge(intent);
								}
							}
							CommonRequst common = new CommonRequst();
	    					common.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
	    					common.setTransferName(ThirdPayConstants.TN_UPDATEBID);
	    					common.setBidType(common.HRY_BID);
	    					common.setBidId(planId.toString());
	    					common.setBidIdStatus("2");//更新标的状态为还款中
	    					CommonResponse cr1=ThirdPayInterfaceUtil.thirdCommon(common);
							ThirdPayRecord record = thirdPayRecordService.getByOrderNo(cardNo);
	    					if(cr1!=null&&cr1.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
	    						CommonRequst cr = new CommonRequst();
								cr.setRequsetNo(cardNo);//流水号
								cr.setBidId(planId);//标id
								cr.setBussinessType(ThirdPayConstants.BT_PREPAREPAY);//业务类型
								cr.setTransferName(ThirdPayConstants.TN_PREPAREPAY);
								cr.setRepaymemntList(repayList);//还款list
								cr.setBidType(cr.HRY_BID);
								cr.setAmount(repayMoney);
								cr.setTransactionTime(new Date());
								cr.setRemark1(periodId);//还款期数
								CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(cr);
								if(cr!=null&&commonResponse.getResponseList()!=null){
									if(commonResponse.getResponseList().size()>0){
										for(CommonResponse response:commonResponse.getResponseList()){
											if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
												Map map = new HashMap();
												map.put("requestNo",response.getRequestNo());
												map.put("bidId",planId.toString());
												map.put("intentDate", intentDate2);
												map.put("peridId", peridId);
												map.put("orderNo", response.getRequestNo());
												map.put("planId", planId);
												map.put("cardNo", response.getRequestNo());
												if(record!=null){
													map.put("requestTime", record.getRequestTime());
												}
												opraterBussinessDataService.reserveHandle(map);
												m++;
											}else{
												n++;
											}
										}
										plBidPlanService.bidComplete(Long.valueOf(planId), this.getRequest());	
									}
								}
								if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){//第三方已经请求成功
									ret[0]=Constants.CODE_SUCCESS;
									ret[1]="还款成功";
								}else if(commonResponse.getResponseList().size()>0){
									ret[0]=Constants.CODE_SUCCESS;
									ret[1]="成功还款至投资人的个数是"+""+"失败的个数是"+"";
								}else{
									ret[0]=Constants.CODE_FAILED;
									ret[1]=commonResponse.getResponseMsg();
								}
	    					}else{
	    						ret[0]=Constants.CODE_FAILED;
								ret[1]="更改标的状态失败";
	    					}
						}else{
							ret[0]=Constants.CODE_FAILED;
							ret[1]="没有投资人还款列表，不能还款";
						}
					}
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="系统错误-还款投资人列表，标号或者期数没有值";
		}
		
		if(ret[0].equals(Constants.CODE_SUCCESS)){
			ThirdPayRecord record = thirdPayRecordService.getByOrderNo(cardNo);
			//判断第三方类型   针对于联动优势平台代偿还款的操作
			String thirdPayConfig1 = record.getThirdPayConfig().toString();
			for(String temp:idlist){
				InvestPersonInfo iofo=investPersonInfoService.getByRequestNumber(temp).get(0);
				if(iofo!=null){
					BpCustMember bp=bpCustMemberService.get(iofo.getInvestPersonId());
					if(bp!=null&&bp.getThirdPayFlagId()!=null&&!"".equals(bp.getThirdPayFlagId())){
						QueryFilter filter1=new QueryFilter(this.getRequest());
						filter1.addFilter("Q_bidPlanId_L_EQ", planId);
						filter1.addFilter("Q_intentDate_D_EQ", peridId);
						filter1.addFilter("Q_isCheck_SN_EQ", "0");
						filter1.addFilter("Q_isValid_SN_EQ", "0");
						filter1.addFilter("Q_orderNo_S_EQ", temp);
						List<BpFundIntent> b=bpFundIntentService.getAll(filter1);
						
						if(b.size()>0){
							//代偿表初始化数据
							plBidCompensatoryService.saveComPensatoryService(planId,peridId,cardNo,PlBidCompensatory.TYPE_PLATE,b.get(0).getPayintentPeriod().toString());
							for(BpFundIntent tempp:b){
								if(!tempp.getFundType().equals("principalLending")&&!tempp.getFundType().equals("serviceMoney")&&!tempp.getFundType().equals("consultationMoney")){
									BpCustMember bps=bpCustMemberService.get(tempp.getInvestPersonId());
									if(tempp.getNotMoney().compareTo(new BigDecimal(0))>0&&thirdPayConfig1!=null&&!"".equals(thirdPayConfig1)&&!thirdPayConfig1.toLowerCase().equals("umpayconfig".toLowerCase())){
										Map<String,Object> map=new HashMap<String,Object>();
										map.put("investPersonId",bps.getId());//投资人Id（必填）
										map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
										map.put("transferType",tempp.getFundType().equals("loanInterest")?ObAccountDealInfo.T_PROFIT:ObAccountDealInfo.T_PRINCIALBACK);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
										map.put("money",tempp.getAccrualMoney()!=null?tempp.getNotMoney().add(tempp.getAccrualMoney()):tempp.getNotMoney());//交易金额	（必填）			 
										map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
										map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
										map.put("recordNumber",Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));//交易流水号	（必填）
										map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
										obAccountDealInfoService.operateAcountInfo(map);
									}
									//登记代偿台账
									
									//如果是联动优势则不对账
									
									if(thirdPayConfig1!=null&&!"".equals(thirdPayConfig1)&&thirdPayConfig1.toLowerCase().equals("umpayconfig".toLowerCase())){
										
									
									}else{
										tempp.setNotMoney(new BigDecimal(0));
										tempp.setAfterMoney(tempp.getIncomeMoney());
										tempp.setFactDate(new Date());	
									}
									
									tempp.setRequestNo(cardNo);
									tempp.setLoanerRepayMentStatus(1);
									tempp.setRepaySource(BpFundIntent.REPAYSOURCE2);
									bpFundIntentService.merge(tempp);
									perid=tempp.getPayintentPeriod().toString();
									//obAccountDealInfoService.operateAcountInfo(bps.getId().toString(), tempp.getFundType().equals("loanInterest")?ObAccountDealInfo.T_PROFIT.toString():ObAccountDealInfo.T_PRINCIALBACK.toString(), tempp.getNotMoney().toString(), "3", "0", ObAccountDealInfo.ISAVAILABLE.toString(), "2", Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));
								}else{//平台代偿的时候只换本金利息和奖励金额等   但是管理费用和咨询费用不会进行代付  但是台账要对掉   而且投资人不能生成关于管理费和服务费的资金记录
									tempp.setNotMoney(new BigDecimal(0));
									tempp.setAfterMoney(tempp.getIncomeMoney());
									tempp.setFactDate(new Date());	
									tempp.setRequestNo(cardNo);
									tempp.setLoanerRepayMentStatus(1);
									tempp.setRepaySource(BpFundIntent.REPAYSOURCE2);
									bpFundIntentService.merge(tempp);
								}
							}
						
						}
					}
				}
			}
			//更改标的状态为还款中
			plBidPlanService.bidComplete(Long.valueOf(planId), this.getRequest());		
			jsonString="{success:true,msg:'平台垫付代偿还款成功'}";
		}else{
			jsonString="{success:true,msg:'平台垫付代偿还款失败,原因"+ret[1]+"'}";
		}
		return SUCCESS;
	}
	/**
	 * 对账的接口
	 * @return
	 */
	public  String  querydate(){
		String date =this.getRequest().getParameter("dateName");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d=null;
		try {
			if(date!=null){
				d = sdf.parse(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Type type=new TypeToken<List<Record>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if(d!=null&&!d.equals("")){
			CommonRequst commonrequst = new CommonRequst();
			commonrequst.setTransactionTime(d);//查询的日期
			commonrequst.setBussinessType(ThirdPayConstants.BT_QUERYACCOUNT);
			commonrequst.setTransferName(ThirdPayConstants.TN_QUERYACCOUNT);
			if(d!=null){
				commonrequst.setQueryStartDate(d);//查询开始时间
				commonrequst.setQueryEndDate(d);//查询结束时间
			}
			CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonrequst);
			if(null != commonResponse.getRecordList() && commonResponse.getRecordList().size()>0){
				List<CommonRecord>  recordList = commonResponse.getRecordList();
				buff.append(recordList!=null?recordList.size():0).append(",result:");
				buff.append(gson.toJson(recordList, type));
			}else{
				buff.append(0).append(",result:");
				buff.append(gson.toJson(null, type));
			}
		}else{
			buff.append(0).append(",result:");
			buff.append(gson.toJson(null, type));
		}
		buff.append("}");
		jsonString=buff.toString();
		System.out.println(jsonString);
		return SUCCESS;
	}
	
	public  String  UnFreeMoney(){
		String requestNo =this.getRequest().getParameter("requestNo");
		System.out.println("requestNo==="+requestNo);
		StringBuffer buff = new StringBuffer("{success:true,msg:'");
		if(requestNo!=null&&!requestNo.equals("")){
			/**(13) 解冻 (只要)	 * 2014-07-15
		     * Map<String,Object> map  第三方支付解冻需要的map参数
			 * map.get("freezeRequestNo").toString()冻结交易流水号
			 * @return
			 */
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("freezeRequestNo", requestNo);
			String[] ret=yeePayService.unfreeze(map);
			if(ret[0].equals(Constants.CODE_SUCCESS)){
				System.out.println("解冻成功");
			}
			buff.append(ret[1]);
		}
		buff.append("'}");
		jsonString=buff.toString();
		System.out.println(jsonString);
		return SUCCESS;
	}
	
	
	/**
	 * 平台帮助借款人自动还款按钮
	 * （连接第三方）
	 * @return
	 */
	public String platformHelpLoanerRepayment(){
		String [] ret=new String[2];
		String thirdPayConfig=configMap.get("thirdPayConfig").toString();
		String thirdPayType=configMap.get("thirdPayType").toString();
		String periodId = this.getRequest().getParameter("periodId");
		String planId=this.getRequest().getParameter("planId");
		String peridId=this.getRequest().getParameter("intentDate")+" 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
				if(planId!=null&&!"".equals(planId)&&peridId!=null&&!"".equals(peridId)){
					PlBidPlan plBidPlan =plBidPlanService.get(Long.valueOf(planId));
					BpCustMember mem=plBidPlanService.getLoanMember(plBidPlan);
					String cardNo=ContextUtil.createRuestNumber();//生成第三方交易流水号
					if(plBidPlan!=null){
							//只有托管模式的需要借款人授权自动还款
							if(plBidPlan.getAuthorizationStatus()!=null&&plBidPlan.getAuthorizationStatus().compareTo(Short.valueOf("1"))==0){
								if(mem!=null&&mem.getThirdPayFlagId()!=null&&!"".equals(mem.getThirdPayFlagId())){
									List<BpFundIntent> listIntent = bpFundIntentService.getByPlanIdAndPeriod(Long.valueOf(planId), Integer.valueOf(periodId), "loanInterest");
									if(listIntent != null && listIntent.size()>0 
											&& listIntent.get(0).getLoanerRepayMentStatus() != null 
											&& listIntent.get(0).getLoanerRepayMentStatus() == 1){
										ret[0]=Constants.CODE_FAILED;
										ret[1]="系统错误-已经还款成功，未进行返款，不能重复还款。";
									}else{
										ret=this.yeePayplatformHelpLoanerRepayment(planId, peridId, mem, cardNo,periodId);
									}
								}else{
									ret[0]=Constants.CODE_FAILED;
									ret[1]="系统错误-没有找到借款人p2p账号或者借款人没有开通第三方支付。";
								}
							}else{
								ret[0]=Constants.CODE_FAILED;
								ret[1]="操作错误-该标借款人没有授权进行自动还款";
							}
						if(ret[0].equals(Constants.CODE_SUCCESS)){
							//处理后台还款成功之后的业务处理
							opraterBussinessDataService.handleErpRepayment(planId, cardNo, peridId, this.getBasePath(), this.getRequest(), mem);
						}
					}else{
						ret[0]=Constants.CODE_FAILED;
						ret[1]="系统错误-没有找到需要还款标的信息";
					}
					
				}else{
					ret[0]=Constants.CODE_FAILED;
					ret[1]="系统错误-标号或者期数";
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ret[0].equals(Constants.CODE_SUCCESS)){
			jsonString="{success:true,msg:'借款人自动还款成功'}";
		}else{
			jsonString="{success:true,msg:'借款人自动还款失败,原因"+ret[1]+"'}";
		}
		return SUCCESS;
	}
	
	/**
	 * 查询平台还款日志，第三方还款记录
	 * @param mem
	 * @param planId
	 * @param intentDate
	 * @param uniqueId
	 * @return
	 */
	public boolean getThirdPayRecord(BpCustMember mem,String planId,String intentDate,String uniqueId){
		boolean thirdpay = true;
		PlBidPlan bidplan = plBidPlanService.get(Long.valueOf(planId));
		ThirdPayRecord thirdpayrecord = thirdPayRecordService.getByUniqueId(uniqueId);
		if(thirdpayrecord==null){
		}else{
			if(thirdpayrecord.getCode().equals(CommonResponse.RESPONSECODE_APPLAY)){//已提交申请
				//查询第三方操作 
				String requestNo = ContextUtil.createRuestNumber();//生成第三需要的流水号
				CommonRequst commonRequst =new CommonRequst();
				commonRequst.setLoginname(mem.getLoginname());//登录名
				commonRequst.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方账号
				commonRequst.setCustMemberId(mem.getId().toString());//用户id
				commonRequst.setRequsetNo(requestNo);//查询的交易流水号
				commonRequst.setProId(bidplan.getBidRemark());
				commonRequst.setQueryRequsetNo(thirdpayrecord.getRecordNumber());
				commonRequst.setQueryType("REPAYMENT_RECORD");//REPAYMENT_RECORD表示查询还款的交易记录
				commonRequst.setBussinessType(ThirdPayConstants.BT_QUERYPLATF);
				commonRequst.setTransferName(ThirdPayConstants.TN_QUERYPLATF);
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequst);
				if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){//第三方已经请求成功
					if(commonResponse.getRecordList().get(0).getStatus().equals("SUCCESS")){//第三方还款成功
						thirdpayrecord.setCode(CommonResponse.RESPONSECODE_SUCCESS);
						thirdpayrecord.setStatus(2);
						thirdPayRecordService.save(thirdpayrecord);
						thirdpay=false;
						//检查平台是否更新了还款记录
						//查询还款的记录
						Map<String,String> map = new HashMap<String, String>();
						map.put("bidId",planId);//标id
						map.put("intentDate",thirdpayrecord.getIntentDate().toString());//计划还款日期
						map.put("requestTime", thirdpayrecord.getRequestTime().toString());//请求接口时间
						map.put("requestNum", thirdpayrecord.getRequestNum().toString());//请求接口次数
						map.put("requestNo", commonResponse.getRequestNo());
						opraterBussinessDataService.repayment(map);
						
					}else{//还款失败
					}
				}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_NOTRECIVEPARAMETER)){//第三方未查找到记录
				}else{//交易失败
				}
			}else if(thirdpayrecord.getCode().equals(CommonResponse.RESPONSECODE_SUCCESS)){//交易成功
				thirdpay=false;
			}else if(thirdpayrecord.getCode().equals(CommonResponse.RESPONSECODE_FREEZE)){//还款申请已经提交，继续调用审核接口
				thirdpay=false;
				//调用审核接口
				String requestNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
				CommonRequst request = new CommonRequst();
				request.setRequsetNo(requestNum);
				request.setLoanNoList(thirdpayrecord.getRecordNumber());//审核流水号
				request.setConfimStatus(false);
				request.setBussinessType(ThirdPayConstants.BT_USEALLAUDIT);//业务类型
				request.setTransferName(ThirdPayConstants.TN_USEALLAUDIT);//名称
				CommonResponse response=ThirdPayInterfaceUtil.thirdCommon(request);
				if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
					Map<String,String> map = new HashMap<String, String>();
					map.put("bidId",thirdpayrecord.getBidId().toString());//标id
					map.put("intentDate",thirdpayrecord.getIntentDate().toString());//计划还款日期
					map.put("requestTime", thirdpayrecord.getRequestTime().toString());//请求接口时间
					map.put("requestNum", thirdpayrecord.getRequestNum().toString());//请求接口次数
					map.put("requestNo", thirdpayrecord.getRecordNumber());
					opraterBussinessDataService.repayment(map);
				}
			}else{//交易失败
			}
		}
		return thirdpay;
	}
	/**
	 * 自助还款接口
	 * @return
	 */
	public String[] yeePayplatformHelpLoanerRepayment(String planId,String peridId,BpCustMember mem,String cardNo,String periodId){
		String[] ret=new String[2];
		Integer i = 0;//成功返款给投资人的次数
		Integer j = 0;//返款给投资人失败的次数
		Integer o = 0;//成功还给平台费用的次数
		Integer k = 0;//还给平台费用失败的次数
		Integer countPay = 0;//请求的次数 (针对于富友的借款人生成一条资金明细的情况)
		Integer countFee = 0;//请求的次数 (针对于富友的借款人生成一条资金明细的情况)
		List<CommonRequestInvestRecord> repayList=investPersonInfoService.getRepaymentList(planId,peridId);
		BigDecimal bidTotalMoney = bpFundIntentService.getFinancialMoney(Long.valueOf(planId), "all");//标的还款总金额
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String intentDate =sdf.format(repayList.get(0).getIntentDate());
		//查询平台还款日志，第三方还款记录
		boolean thirdpay = getThirdPayRecord(mem,planId,intentDate,ThirdPayConstants.BT_HELPPAY+planId.toString()+intentDate);
		//针对于联动优势计算每一期还款金额
		BigDecimal repayMoney = bpFundIntentService.getMoneyPerPeriod(planId, periodId);
		List<BpFundIntent> list = bpFundIntentService.getByBidIdandPeriod(planId, periodId);
		String intentDate2 = "";
		if(list.size()>0){
			list.get(0).getIntentDate();
		}
		Date intentDate1 = new Date();
		if(repayList.size()>0){
			intentDate1 = repayList.get(0).getIntentDate();
			intentDate2 = sdf1.format(intentDate1);
		}
		if(null!=repayList && repayList.size()>0&&thirdpay==true){
			CommonRequst common = new CommonRequst();
			common.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
			common.setTransferName(ThirdPayConstants.TN_UPDATEBID);
			common.setBidId(planId.toString());
			common.setBidType(CommonRequst.HRY_BID);
			common.setBidIdStatus("2");//更新标的状态为还款中
			CommonResponse cr1=ThirdPayInterfaceUtil.thirdCommon(common);
			if(cr1!=null&&cr1.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
				CommonRequst commonRequest=new CommonRequst();
				commonRequest.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方支付账号
				commonRequest.setRequsetNo(cardNo);//请求流水号
				if(null!=list&&list.size()>0){
					for(BpFundIntent info:list){
						info.setRequestNOLoaner(cardNo);
						info.setRequestNo(cardNo);
						bpFundIntentService.merge(info);
					}
				}
				commonRequest.setBidId(planId);//标的Id
				commonRequest.setPlanMoney(bidTotalMoney);
				commonRequest.setIntentDate(intentDate1);//计划还款日期
				commonRequest.setBussinessType(ThirdPayConstants.BT_HELPPAY);//业务类型
				commonRequest.setTransferName(ThirdPayConstants.TN_HELPPAY);//业务名称
				commonRequest.setUniqueId(ThirdPayConstants.BT_HELPPAY+planId.toString()+intentDate);//还款唯一标识
				commonRequest.setRepaymemntList(repayList);
				commonRequest.setUniqueId(ThirdPayConstants.BT_HELPPAY+planId.toString()+intentDate);
				commonRequest.setProId(plBidPlanService.get(Long.valueOf(planId)).getBidRemark());
				commonRequest.setMerOrdDate(sdf.format(new Date()));
				commonRequest.setBidType(CommonRequst.HRY_BID);
				commonRequest.setAmount(repayMoney);
				commonRequest.setRemark1(periodId);
				if(mem.getCustomerType()!=null&&mem.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){
					commonRequest.setAccountType(1);//企业户还款
				}else{
					commonRequest.setAccountType(0);//个人还款
				}
				CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
				ThirdPayRecord record = thirdPayRecordService.getByOrderNo(cardNo);
    			if(commonResponse!=null&&commonResponse.getResponseList()!=null){
    				if(commonResponse.getResponseList().size()>0){
    					BigDecimal bigMoney = BigDecimal.ZERO;
						for(CommonResponse response1:commonResponse.getResponseList()){
							if(response1.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
								countPay++;
								Map map1 = new HashMap();
								map1.put("requestNo",response1.getRequestNo());
								map1.put("bidId",planId.toString());
								map1.put("money",response1.getRemark());
								map1.put("intentDate", intentDate2.toString());
								map1.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_2);
								if(record!=null){
									map1.put("requestTime", sdf1.format(record.getRequestTime()));
								}
								opraterBussinessDataService.repayment(map1);
								i++;
							}else{
								countPay++;
								j++;
							}
						}
						//借款人给平台转账  嵌套在给投资人返款方法中    区别其他第三方
						String cardNo1=ContextUtil.createRuestNumber();
						CommonRequst cq1=new CommonRequst();
						Date date1 = new Date();
						cq1.setRepaymemntList(repayList);//还款list
		    			cq1.setRequsetNo(cardNo1);//请求流水号
		    			cq1.setBidId(planId);
		    			cq1.setPlanMoney(bidTotalMoney);//标的还款总金额
		    			cq1.setIntentDate(intentDate1);//计划还款日期
		    			cq1.setUniqueId(ThirdPayConstants.BT_HELPPAY_FEE+planId.toString()+intentDate);//还款唯一标识
		    			cq1.setBussinessType(ThirdPayConstants.BT_HELPPAY_FEE);//业务类型
		    			cq1.setTransferName(ThirdPayConstants.TN_HELPPAY_FEE);//名称
		    			cq1.setRemark1(periodId);
		    			cq1.setThirdPayConfigId(mem.getThirdPayFlagId());//付款人的第三方登陆账号
		    			cq1.setContractNo("");//预授权合同号 若有则为授权还款   若没有则是普通转账
		    			CommonResponse cr2=ThirdPayInterfaceUtil.thirdCommon(cq1);
		    			if(cr2!=null&&cr2.getResponseList()!=null){
		    				if(cr2.getResponseList().size()>0){
								for(CommonResponse response1:cr2.getResponseList()){
									if(response1.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
										Map map1 = new HashMap();
										map1.put("requestNo",response1.getRequestNo());
										map1.put("bidId",planId.toString());
										map1.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_2);
										map1.put("intentDate", intentDate2.toString());
										if(record!=null){
											map1.put("requestTime", sdf1.format(record.getRequestTime()));
										}
										opraterBussinessDataService.repayment(map1);
										o++;
									}else{
										k++;
									}
								}
		    				}
		    			}
    				}
    			}else if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)&&commonResponse.getRequestNo()==null) {
					logger.info("自动还款请求参数："+ret[1]);
					//联动优势回调方法
					Map<String,String> map = new HashMap<String, String>();
					map.put("requestNo",cardNo);
					map.put("bidId", planId);
					map.put("periodId", peridId);
					opraterBussinessDataService.umpayRepayment(map);	
					ret[0]=Constants.CODE_SUCCESS;
					ret[1]=commonResponse.getResponseMsg();
				}else if(commonResponse.getResponseList()!=null&&commonResponse.getResponseList().size()>0){
					ret[0]=Constants.CODE_SUCCESS;
					ret[1]="成功还款给投资人的个数是"+i+"失败次数是"+j+"成功还款给平台的费用次数是"+o+"失败的次数是"+k;
				}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)&&commonResponse.getRequestNo()!=null){//针对于汇付返回成功时候数据处理
					Map map1 = new HashMap();
					map1.put("requestNo",commonResponse.getRequestNo());
					map1.put("bidId",planId.toString());
					map1.put("intentDate", intentDate2.toString());
					map1.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_2);
					if(record!=null){
						map1.put("requestTime", sdf1.format(record.getRequestTime()));
					}
					opraterBussinessDataService.repayment(map1);
					ret[0]=Constants.CODE_SUCCESS;
					ret[1]=commonResponse.getResponseMsg();
				}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_FREEZE)){
					//还款申请成功。继续调用还款审核接口。
					String requestNum=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
					CommonRequst request = new CommonRequst();
					request.setRequsetNo(requestNum);
					request.setLoanNoList(cardNo);//审核流水号，还款时的流少号
					request.setConfimStatus(false);
					request.setBussinessType(ThirdPayConstants.BT_USEALLAUDIT);//业务类型
					request.setTransferName(ThirdPayConstants.TN_USEALLAUDIT);//名称
					CommonResponse response=ThirdPayInterfaceUtil.thirdCommon(request);
					if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
						ret[0]=Constants.CODE_SUCCESS;
						ret[1]=commonResponse.getResponseMsg();
					}
				}else{
					ret[0]=Constants.CODE_FAILED;
					ret[1]=commonResponse.getResponseMsg();
				}
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="标的状态更改失败";
			}
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="已经操作了还款，或者没有投资人还款列表，请联系管理员！";
		}
		return ret;
	}
	
	
	//给投资人返款方法
	public String repayToInvestor(){
		String [] ret=new String[2];
		String planId=this.getRequest().getParameter("planId");
		String peridId=this.getRequest().getParameter("intentDate");
		String period = this.getRequest().getParameter("period");
		String  msg="";
		if(planId!=null&&!"".equals(planId)&&peridId!=null&&!"".equals(peridId)){
			PlBidPlan plBidPlan =plBidPlanService.get(Long.valueOf(planId));
			BpCustMember bpLoan=plBidPlanService.getLoanMember(plBidPlan);//获取项目的借款人信息
			String cardNo=ContextUtil.createRuestNumber();
			if(bpLoan!=null&&bpLoan.getThirdPayFlagId()!=null&&!"".equals(bpLoan.getThirdPayFlagId())){
				if(plBidPlan!=null){
					List<InvestPersonInfo> idlist=investPersonInfoService.getByBidPlanId(plBidPlan.getBidId());//投资人个数
					if(idlist!=null&&idlist.size()>0){
								int i=0;//成功返款的人数
								int j=0;//返款失败的人数
								for(InvestPersonInfo temp:idlist){
									PlBidInfo plBidInfo = plBidInfoService.getByOrderNo(temp.getOrderNo());
									BpCustMember bp=bpCustMemberService.get(plBidInfo.getNewInvestPersonId() != null ? plBidInfo.getNewInvestPersonId() : plBidInfo.getUserId());//收款p2p账号
										if(bp!=null&&bp.getThirdPayFlagId()!=null&&!"".equals(bp.getThirdPayFlagId())){
											BigDecimal  loanInterestAndprincipalRepayment=bpFundIntentService.getByPlanIdAndOtherRequest(temp.getOrderNo(),planId,peridId,"('loanInterest','principalRepayment')");
											//奖励金额  加息等
											BigDecimal  loanInterestAndprincipalRepayment1=bpFundIntentService.getByPlanIdAndOtherRequest(temp.getOrderNo(),planId,peridId,"('commoninterest','raiseinterest','subjoinInterest','principalCoupons','couponInterest','interestPenalty')");
											if(loanInterestAndprincipalRepayment!=null&&loanInterestAndprincipalRepayment.compareTo(new BigDecimal(0))>0){
												String newoderNumber=ContextUtil.createRuestNumber();//每个借款人返款生成的流水号
												//准备还款的接口调用参数
												CommonRequst commonRequst=new CommonRequst();
												commonRequst.setRequsetNo(newoderNumber);//流水号
												commonRequst.setThirdPayConfigId(bp.getThirdPayFlagId());//收款人第三方Id
												commonRequst.setBidId(plBidPlan.getBidId().toString());//标的id
												commonRequst.setBidType(commonRequst.HRY_BID);//标的类型
												commonRequst.setAmount(loanInterestAndprincipalRepayment);//返款金额
												commonRequst.setTransactionTime(new  Date());
												commonRequst.setThirdPayConfigId(bp.getThirdPayFlagId());//收款人第三方Id
												if(bp.getCustomerType()!=null&&bp.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){
													commonRequst.setAccountType(1);//判断是否为企业账户
												}else{
													commonRequst.setAccountType(0);//判断是否为个人账户
												}
												commonRequst.setBussinessType(ThirdPayConstants.BT_RETURNMONEY);
												commonRequst.setTransferName(ThirdPayConstants.TN_RETURNMONEY);
												CommonResponse response=ThirdPayInterfaceUtil.thirdCommon(commonRequst);
												//调用放款方法
												if(response!=null&&response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
													QueryFilter filter1=new QueryFilter(this.getRequest());
													filter1.addFilter("Q_bidPlanId_L_EQ", planId);
													filter1.addFilter("Q_intentDate_D_EQ", peridId);
													filter1.addFilter("Q_isCheck_SN_EQ", "0");
													filter1.addFilter("Q_isValid_SN_EQ", "0");
													filter1.addFilter("Q_orderNo_S_EQ", temp.getOrderNo());
													List<BpFundIntent> b=bpFundIntentService.getAll(filter1);
													if(b!=null){
														for(BpFundIntent tempp:b){
															if(tempp.getFundType().equals("loanInterest")||tempp.getFundType().equals("principalRepayment")){
																if(tempp.getNotMoney().compareTo(new BigDecimal(0))>0){
																	Map<String,Object> map=new HashMap<String,Object>();
																	map.put("investPersonId",bp.getId());//投资人Id（必填）
																	map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填） T_PROFIT					 
																	map.put("transferType",tempp.getFundType().equals("principalRepayment")?ObAccountDealInfo.T_PRINCIALBACK:ObAccountDealInfo.T_PROFIT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
																	map.put("money",tempp.getNotMoney().add(tempp.getAccrualMoney()));//交易金额	（必填）			 
																	map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
																	map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
																	map.put("recordNumber",newoderNumber+tempp.getFundIntentId());//交易流水号	（必填）
																	map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
																	obAccountDealInfoService.operateAcountInfo(map);
																}
																tempp.setNotMoney(new BigDecimal(0));
																tempp.setAfterMoney(tempp.getIncomeMoney());
																tempp.setFactDate(new Date());
																tempp.setRequestNo(newoderNumber);
																bpFundIntentService.merge(tempp);
																//调用发红包接口返给投资人奖励
															}else if(tempp.getFundType().equals("commoninterest")||tempp.getFundType().equals("raiseinterest")||tempp.getFundType().equals("subjoinInterest")
																	||tempp.getFundType().equals("principalCoupons")||tempp.getFundType().equals("couponInterest")||tempp.getFundType().equals("interestPenalty")){//派发奖励 
																String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
																CommonRequst commonRequest = new CommonRequst();
																commonRequest.setThirdPayConfigId(bp.getThirdPayFlagId());//用户第三方标识
																commonRequest.setRequsetNo(requestNo);//请求流水号
																if(bp.getCustomerType()!=null&&!"".equals(bp.getCustomerType())&&bp.getCustomerType()==1){
																	commonRequest.setAccountType(1);
																}
																commonRequest.setAmount(loanInterestAndprincipalRepayment1);//交易金额
																commonRequest.setBussinessType(ThirdPayConstants.BT_COUPONAWARD);//业务类型
																commonRequest.setTransferName(ThirdPayConstants.TN_COUPONAWARD);//业务名称
																CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
																if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
																	if(tempp.getNotMoney().compareTo(new BigDecimal(0))>0){
																		String typeName = "";
																		if(tempp.getFundType().equals("commoninterest")){//普通加息
																			typeName = ObAccountDealInfo.T_BIDRETURN_ADDRATE;
																		}else if(tempp.getFundType().equals("raiseinterest")){
																			typeName = ObAccountDealInfo.T_BIDRETURN_RATE31;
																		}else if(tempp.getFundType().equals("subjoinInterest")){
																			typeName = ObAccountDealInfo.T_BIDRETURN_RATE30;
																		}else if(tempp.getFundType().equals("couponInterest")){//返息
																			if(plBidPlan.getRebateType()==2){
																				typeName = ObAccountDealInfo.T_BIDRETURN_RATE27;
																			}else{
																				typeName = ObAccountDealInfo.T_BIDRETURN_RATE29;
																			}
																		}else if(tempp.getFundType().equals("principalCoupons")){//返现奖励
																			typeName = ObAccountDealInfo.T_BIDRETURN_RETURNRATIO;
																		}else if(tempp.getFundType().equals("raiseinterest")){//募集期
																			typeName = ObAccountDealInfo.T_BIDRETURN_RATE31;
																		}else if(tempp.getFundType().equals("interestPenalty")){//补偿息
																		}
																		Map<String,Object> map=new HashMap<String,Object>();
																		map.put("investPersonId",bp.getId());//投资人Id（必填）
																		map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
																		map.put("transferType",typeName);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
																		map.put("money",tempp.getNotMoney().add(tempp.getAccrualMoney()));//交易金额	（必填）			 
																		map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
																		map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
																		map.put("recordNumber",newoderNumber+tempp.getFundIntentId());//交易流水号	（必填）
																		map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
																		obAccountDealInfoService.operateAcountInfo(map);
																	}
																	tempp.setNotMoney(new BigDecimal(0));
																	tempp.setAfterMoney(tempp.getIncomeMoney());
																	tempp.setFactDate(new Date());
																	tempp.setRequestNo(newoderNumber);
																	bpFundIntentService.merge(tempp);
																}
															}
														}
													}
													//判断是否为提前还款
												    List<BpFundIntent> intent = bpFundIntentService.getByPlanIdAndPeriod(Long.valueOf(planId), Integer.valueOf(period), "principalRepayment");
													if(intent.size()>0){
														 if(intent.get(0).getSlEarlyRepaymentId()!=null){
																SlEarlyRepaymentRecord repay = slEarlyRepaymentRecordService.get(b.get(0).getSlEarlyRepaymentId());
																repay.setCheckStatus(5);
																PlBidPlan bidPlan = plBidPlanService.get(intent.get(0).getBidPlanId());
																bidPlan.setState(10);//返款给投资人的时候把标的的状态改成已完成
																plBidPlanService.merge(bidPlan);
															}
													}
													//判断该标是否还款完成
													QueryFilter filter = new QueryFilter();
													QueryFilter filter2 = new QueryFilter();
													filter.addFilter("Q_bidPlanId_L_EQ", planId);
													filter2.addFilter("Q_bidPlanId_L_EQ", planId);
													filter.addFilter("Q_factDate_NOTNULL", null);
													List<BpFundIntent> repayList= bpFundIntentService.getAll(filter);
													List<BpFundIntent> repayList1= bpFundIntentService.getAll(filter2);
													if(repayList.size()>0&&repayList1.size()>0){
														if(repayList.size()==repayList1.size()){
															PlBidPlan plan = plBidPlanService.get(Long.valueOf(planId));
															plan.setState(10);
															plBidPlanService.merge(plan);
														}
													}
													i++;
													
												}else{
													j++;
													msg=msg+bp.getTruename()+"返款失败,"+response.getResponseMsg()+";";
												}
											}else{
												j++;
												msg=msg+bp.getTruename()+"返款失败，本金和利息已经偿还过，不能返款;";
												
											}
										}else{
											j++;
											msg=msg+bp.getTruename()+"返款失败，不存在或第三方账户不存在，不能返款";
											
										}
								}
								List<BpFundIntent> list  = bpFundIntentService.getByBidIdandPeriod(planId,period);
								if(j>0){
									msg="投资人返款情况统计：共返款"+idlist.size()+"人，成功返款"+i+"人,失败返款人数"+j+"人,失败原因-"+msg;
								}else{
									msg="投资人返款情况统计：共返款"+idlist.size()+"人，成功返款"+i+"人,失败返款人数"+j+"人";
								}
					}else{
						msg="操作错误-没有找到返款投资人列表";
					}
				}else{
					msg="系统错误-没有找到需要还款标的信息";
				}
			}else{
				msg="系统错误-没有找到借款人的第三方账户信息,请检查";
			}
		}else{
			msg="系统错误-标号或者期数或者还款人p2p账号没有值";
		}
		jsonString="{success:true,msg:'"+msg+"'}";
		return SUCCESS;
	}
	
	//平台收费方法
	public String repayToIPlate(){
		String [] ret=new String[2];
		String planId=this.getRequest().getParameter("planId");
		String peridId=this.getRequest().getParameter("intentDate");
		String period = this.getRequest().getParameter("period");
		String  msg="";
		if(planId!=null&&!"".equals(planId)&&peridId!=null&&!"".equals(peridId)){
			PlBidPlan plBidPlan =plBidPlanService.get(Long.valueOf(planId));
			BpCustMember bpLoan=plBidPlanService.getLoanMember(plBidPlan);//获取项目的借款人信息
			String cardNo=ContextUtil.createRuestNumber();
			if(bpLoan!=null&&bpLoan.getThirdPayFlagId()!=null&&!"".equals(bpLoan.getThirdPayFlagId())){
				if(plBidPlan!=null){
					List<InvestPersonInfo> idlist=investPersonInfoService.getByBidPlanId(plBidPlan.getBidId());//投资人个数
					if(idlist!=null&&idlist.size()>0){
								//平台收费金额
						        BigDecimal  plateFormRepayment1 = bpFundIntentService.getPlateFeeByPlanIdandPeriod(planId, period);
								BigDecimal  plateFormRepayment=bpFundIntentService.getByPlanIdAndOtherRequest(null,planId,peridId,"('consultationMoney','serviceMoney')");
								if(plateFormRepayment1!=null&&plateFormRepayment1.compareTo(new BigDecimal(0))>0){
									String newoderNumber=ContextUtil.createRuestNumber();//每个借款人返款生成的流水号
									//准备还款的接口调用参数
									CommonRequst commonRequst=new CommonRequst();
									commonRequst.setRequsetNo(newoderNumber);//流水号
									commonRequst.setBidId(plBidPlan.getBidId().toString());//标的id
									commonRequst.setBidType(CommonRequst.HRY_BID);
									commonRequst.setAmount(plateFormRepayment1);//返款金额
									commonRequst.setTransactionTime(new  Date());
								//	commonRequst.setThirdPayConfigId(configMap.get("thirdPay_umpay_MemberID").toString());//收款人第三方Id
									commonRequst.setAccountType(2);//0个人用户  1企业用户  2平台商户
									commonRequst.setBussinessType(ThirdPayConstants.BT_PLATEFORMRECHAGE);
									commonRequst.setTransferName(ThirdPayConstants.TN_PLATEFORMRECHAGE);
									CommonResponse response=ThirdPayInterfaceUtil.thirdCommon(commonRequst);
									if(response!=null&&response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
										QueryFilter filter1=new QueryFilter(this.getRequest());
										filter1.addFilter("Q_bidPlanId_L_EQ", planId);
										filter1.addFilter("Q_intentDate_D_EQ", peridId);
										filter1.addFilter("Q_isCheck_SN_EQ", "0");
										filter1.addFilter("Q_isValid_SN_EQ", "0");
										List<BpFundIntent> b=bpFundIntentService.getAll(filter1);
										if(b!=null){
											System.out.println("返款方法====="+b.size());
											for(BpFundIntent tempp:b){
												if(tempp.getFundType().equals("consultationMoney")||tempp.getFundType().equals("serviceMoney")){
													System.out.println("返款方法1====="+tempp.getFundType());
													tempp.setNotMoney(new BigDecimal(0));
													tempp.setAfterMoney(tempp.getIncomeMoney());
													tempp.setFactDate(new Date());
													tempp.setRequestNo(newoderNumber);
													//tempp.setRepaySource(BpFundIntent.REPAYSOURCE1);
													bpFundIntentService.merge(tempp);
												}
											}
										}
										List<BpFundIntent> list  = bpFundIntentService.getByBidIdandPeriod(planId,period);
										if(list.size()>0){
											for(BpFundIntent intent: list){
												if(intent.getFundType()!=null&&!"".equals(intent.getFundType())&&(intent.getFundType().equals("principalRepayment")&&intent.getFundType().equals("loanInterest"))){
													if(intent.getFactDate()!=null&&!"".equals(intent.getFactDate())){
														for(int i = 0;i<list.size();i++){
															list.get(i).setRepaySource((short)1);
														}
													}
												}
											}
										}
										QueryFilter filter = new QueryFilter();
										QueryFilter filter2 = new QueryFilter();
										filter.addFilter("Q_bidPlanId_L_EQ", planId);
										filter2.addFilter("Q_bidPlanId_L_EQ", planId);
										filter.addFilter("Q_factDate_NOTNULL", null);
										List<BpFundIntent> repayList= bpFundIntentService.getAll(filter);
										List<BpFundIntent> repayList1= bpFundIntentService.getAll(filter2);
										if(repayList.size()>0&&repayList1.size()>0){
											if(repayList.size()==repayList1.size()){
												PlBidPlan plan = plBidPlanService.get(Long.valueOf(planId));
												plan.setState(10);
												plBidPlanService.merge(plan);
											}
										}
										msg=msg+"平台收费成功"+response.getResponseMsg()+";";
									}else{
										msg=msg+"返款失败,"+response.getResponseMsg()+";";
									}
								}else{
									msg="返款失败，平台费用已经偿还过，不能返款;";
								}
					}else{
						msg="操作错误-没有找到返款投资人列表";
					}
				}else{
					msg="系统错误-没有找到需要还款标的信息";
				}
			}else{
				msg="系统错误-没有找到借款人的第三方账户信息,请检查";
			}
		}else{
			msg="系统错误-标号或者期数或者还款人p2p账号没有值";
		}
		jsonString="{success:true,msg:'"+msg+"'}";
		return SUCCESS;
	}
	/**
	 * 线上提现申请审核
	 * @return
	 */
	public String passApplye(){
		String strId = this.getRequest().getParameter("id");
		int successI=0;//成功记录数
		int applyI=0;//还在处理中记录数
		int faildI=0;//失败记录数
		if(strId!=null&&!strId.equals("")){
			String str[] = strId.split(",");
			for(int i=0;i<str.length;i++){
				ObAccountDealInfo dealInfo = obAccountDealInfoService.get(Long.valueOf(str[i]));
				//只有提现冻结的记录才审核
				if(dealInfo!=null&&dealInfo.getDealRecordStatus().toString().equals("7")){
					//得到提现的银行卡信息
					WebBankcard bankcard = webBankCardService.get(dealInfo.getBankId());
					if(bankcard!=null){
						String orderNum = ContextUtil.createRuestNumber();
						CommonResponse commonResponse = new CommonResponse();
						CommonRequst commonRequst = new CommonRequst();
						commonRequst.setRequsetNo(orderNum);
						commonRequst.setQueryRequsetNo(dealInfo.getRecordNumber());//用户前台提现申请的流水号
						commonRequst.setAmount(dealInfo.getPayMoney());//提现金额
						commonRequst.setTrueName(bankcard.getAccountname());//银行卡真实姓名
						commonRequst.setBankCardNumber(bankcard.getCardNum());//银行卡号
						commonRequst.setBankCode(bankcard.getBankId());//银行卡编码
						commonRequst.setTransferName(ThirdPayConstants.TN_WITHDRAW);//业务用途
						commonRequst.setBussinessType(ThirdPayConstants.BT_WITHDRAW);//业务类型
						commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequst);
						dealInfo.setTransferDate(new Date());
						if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
							successI++;
							dealInfo.setMsg("交易成功");
							dealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
						}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_APPLAY)){
							applyI++;
							dealInfo.setMsg(commonResponse.getResponseMsg());
						}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)){
							faildI++;
							dealInfo.setMsg(commonResponse.getResponseMsg());
							dealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_3);
						}
						dealInfo.setThirdPayRecordNumber(commonResponse.getLoanNo());
						obAccountDealInfoService.save(dealInfo);
						//opraterBussinessDataService.withdrawPassApplye(map);
					}
				}
			}
		}
		jsonString="{success:true,msg:'第三方审核成功"+successI+"个,第三方审核中"+applyI+",第三方审核失败"+faildI+"个'}";
		return SUCCESS;
	}
	/**
	 * 线上提现申请退回
	 * @return
	 */
	public String cancelapply(){
		String strId = this.getRequest().getParameter("id");
		if(strId!=null&&!strId.equals("")){
			String str[] = strId.split(",");
			for(int i=0;i<str.length;i++){
				ObAccountDealInfo dealInfo = obAccountDealInfoService.get(Long.valueOf(str[i]));
				if(dealInfo!=null){
					dealInfo.setTransferDate(new Date());
					dealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_3);
					dealInfo.setMsg("申请已驳回");
					obAccountDealInfoService.save(dealInfo);
				}
			}
		}
		jsonString="{success:true,msg:'操作成功'}";
		return SUCCESS;
	}
	/**
	 * 查询第三方提现审核状态
	 * @return
	 */
	public String queryThirdWithdraw(){
		int successI=0;//成功记录数
		int applyI=0;//还在处理中记录数
		int faildI=0;//失败记录数
		String strId = this.getRequest().getParameter("id");
		if(strId!=null&&!strId.equals("")){
			String str[] = strId.split(",");
			for(int i=0;i<str.length;i++){
				ObAccountDealInfo dealInfo = obAccountDealInfoService.get(Long.valueOf(str[i]));
				if(dealInfo!=null){
					String orderNum = ContextUtil.createRuestNumber();
					CommonResponse commonResponse = new CommonResponse();
					CommonRequst commonRequst = new CommonRequst();
					commonRequst.setRequsetNo(orderNum);
					commonRequst.setQueryRequsetNo(dealInfo.getThirdPayRecordNumber());//用户前台提现申请的流水号
					commonRequst.setTransferName(ThirdPayConstants.TN_QUERYPLATF);//业务用途
					commonRequst.setBussinessType(ThirdPayConstants.BT_QUERYPLATF);//业务类型
					commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequst);
					dealInfo.setTransferDate(new Date());
					if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
						successI++;
						dealInfo.setMsg("交易成功");
						dealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
						//修改账户余额
						ObSystemAccount account = obSystemAccountService.get(dealInfo.getAccountId());
						account.setTotalMoney(account.getTotalMoney().subtract(dealInfo.getPayMoney()));
						obSystemAccountService.save(account);
					}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_APPLAY)){
						applyI++;
						dealInfo.setMsg(commonResponse.getResponseMsg());
					}else if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)){
						faildI++;
						dealInfo.setMsg(commonResponse.getResponseMsg());
						dealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_3);
					}
					obAccountDealInfoService.save(dealInfo);
				}
			}
		}
		jsonString="{success:true,msg:'第三方审核成功"+successI+"个,第三方审核中"+applyI+",第三方审核失败"+faildI+"个'}";
		return SUCCESS;
	}
	/**
	 * 查询标账户流水
	 * @return
	 */
	public String bidFlowQuery(){
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			CommonRequst commonrequest=new CommonRequst();
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
			Date startDate=this.getRequest().getParameter("startDate")!=null?sdf.parse(this.getRequest().getParameter("startDate")):new Date();
			Date endDate=this.getRequest().getParameter("endDate")!=null?sdf.parse(this.getRequest().getParameter("endDate")):new Date();
			Type type=new TypeToken<List<Record>>(){}.getType();
			String pageNumber=this.getRequest().getParameter("pageNumber")!=null?this.getRequest().getParameter("pageNumber"):"1";
			String bidId=this.getRequest().getParameter("bidId");
			if(bidId!=null&&!"".equals(bidId)){
				PlBidPlan  pl=plBidPlanService.get(Long.valueOf(bidId));
				if(pl!=null){
					commonrequest.setQueryStartDate(startDate);//查询开始时间
					commonrequest.setQueryEndDate(endDate);//查询结束时间
					commonrequest.setBidId(bidId);
					commonrequest.setBussinessType(ThirdPayConstants.BT_BIDFLOWQUERY);//标的流水查询
					commonrequest.setPageNumber(pageNumber);
					CommonResponse response=ThirdPayInterfaceUtil.thirdCommon(commonrequest);
					if(response!=null&&response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
						List<CommonRecord> record=response.getRecordList();
						buff.append(record!=null?record.size():0).append(",result:");
						buff.append(gson.toJson(record, type));
					}else{
						buff.append(0).append(",result:");
						buff.append(gson.toJson(null, type));
						}
					}
				buff.append("}");
				jsonString=buff.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
}
