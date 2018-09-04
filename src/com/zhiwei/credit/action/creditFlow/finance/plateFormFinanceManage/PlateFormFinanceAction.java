package com.zhiwei.credit.action.creditFlow.finance.plateFormFinanceManage;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.PlateFormFinanceDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormBidIncomeDetail;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormStatisticsDetail;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateRedFinanceDetail;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.credit.util.Common;

public class PlateFormFinanceAction extends BaseAction{
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private ObAccountDealInfoService  obAccountDealInfoService;
	@Resource
	private  YeePayService yeePayService;
	@Resource
	private PlateFormFinanceDao platFormFinanceDao;
	private ObSystemAccount obSystemAccount;
	
	private Long id;
	private ObAccountDealInfo obAccountDealInfo;
	
    /**
     * 获取系统配置信息
     */
	private Map<String,String> configMap=AppUtil.getConfigMap();
	
	/**
	 * 查询平台账户的统计帐明细
	 * @return
	 */
	public String plateFromFinanceManager(){
		String   plateFromFinanceType=this.getRequest().getParameter("accountType");
		List<ObSystemAccount> ob =obSystemAccountService.getByAccountType(plateFromFinanceType);
		if(ob!=null&&ob.size()>0){
			List listcount=obAccountDealInfoService.getOneTypePlateFormStaticsDatail(this.getRequest(),null,null);
			/**
			 * 查出来平台每个账户的自己明细
			 */
			List<PlateFormStatisticsDetail> list=obAccountDealInfoService.getOneTypePlateFormStaticsDatail(this.getRequest(),start,limit);
			Type type = new TypeToken<List<PlateFormStatisticsDetail>>() {
			}.getType();
			StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
					.append(listcount == null ? 0 : listcount.size()).append(",result:");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list, type));
			buff.append("}");
			jsonString = buff.toString();
		}else{
			Type type = new TypeToken<List<PlateFormStatisticsDetail>>() {
			}.getType();
			StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
					.append( 0 ).append(",result:");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(null, type));
			buff.append("}");
			jsonString = buff.toString();
		}
		return SUCCESS;
	}
	//在创建平台普通账户和风险保证金账户做系统
	public String getPrepareMent(){
		String   plateFromFinanceType=this.getRequest().getParameter("accountType");
		List<ObSystemAccount> ob =obSystemAccountService.getByAccountType(plateFromFinanceType);
		int code=0;//默认不能开通平台资金账户（平台普通账户，平台风险保证金账户，以及担保账户）  1表示能开平台账户
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true");
		String thirdPayName =configMap.get("thirdPayName");
		String thirdPayTypeName=configMap.get("thirdPayTypeName");                                                                                                                                                                                                          
		String platFormNumber=configMap.get("thirdPayPlateFormNumber");
		if(platFormNumber!=null&&!"".equals(platFormNumber)){
			if(plateFromFinanceType.equals(ObSystemAccount.ACCOUNTTYPE_GUARANTEE)){//担保账户允许开设多个账户
				code=1;
				sb.append(",msg:'允许开通担保账户'");
				sb.append(",thirdPayName:'");
				sb.append(thirdPayName);
				sb.append("',thirdPayTypeName:'");
				sb.append(thirdPayTypeName);
				sb.append("',platFormNumber:'");
				sb.append(platFormNumber);
				sb.append("'");
			}else if(plateFromFinanceType.equals(ObSystemAccount.ACCOUNTTYPE_PLATE)||plateFromFinanceType.equals(ObSystemAccount.ACCOUNTTYPE_RISK)){
				if(ob!=null&&ob.size()>0){
					code=0;
					sb.append(",msg:'系统中只允许存在一个平台普通账户或风险保证金账户'");
				}else{
					code=1;
					sb.append(",msg:'允许开通平台普通账户或风险保证金账户'");
					sb.append(",thirdPayName:'");
					sb.append(thirdPayName);
					sb.append("',thirdPayTypeName:'");
					sb.append(thirdPayTypeName);
					sb.append("',platFormNumber:'");
					sb.append(platFormNumber);
					sb.append("'");
				}
			}
		}else{
			sb.append(",msg:'没有第三方平台商户号，不允许开通平台普通账户,风险保证金账户或担保账户'");
		}
		sb.append(",code:");
		sb.append(code);
		sb.append("}");
		setJsonString(sb.toString());
		jsonString = sb.toString();
		return SUCCESS;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ObAccountDealInfo getObAccountDealInfo() {
		return obAccountDealInfo;
	}
	public void setObAccountDealInfo(ObAccountDealInfo obAccountDealInfo) {
		this.obAccountDealInfo = obAccountDealInfo;
	}
	/**
	 * 新增保存平台系统账户方法
	 * @return
	 */
	public String savePlateFormAccount(){
		try{
			String plateFormNumber=this.getRequest().getParameter("plateFormnumber").trim();
			String accountNumber=plateFormNumber+Common.getRandomNum(4)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
			obSystemAccount.setAccountNumber(accountNumber);
			obSystemAccount.setInvestPersonName("系统");
			ObSystemAccount obSystemAccountt=obSystemAccountService.save(obSystemAccount);
			if(obSystemAccountt!=null&&obSystemAccountt.getId()!=null){
				if(obSystemAccountt.getTotalMoney()!=null&&!obSystemAccountt.getTotalMoney().equals(new BigDecimal(0))){
					ObAccountDealInfo obAccountDealInfo =new ObAccountDealInfo();
					obAccountDealInfo.setAccountId(obSystemAccountt.getId());
					obAccountDealInfo.setIncomMoney(obSystemAccountt.getTotalMoney());
					obAccountDealInfo.setCurrentMoney(obSystemAccountt.getTotalMoney());
					obAccountDealInfo.setCreateDate(new Date());
					obAccountDealInfo.setTransferDate(new Date());
					obAccountDealInfo.setCreateId(ContextUtil.getCurrentUserId());
					obAccountDealInfo.setCompanyId(ContextUtil.getLoginCompanyId());
					obAccountDealInfo.setTransferType(ObAccountDealInfo.T_PLATEFORM_CHANGECORRECT);
					obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
					obAccountDealInfo.setRecordNumber(Common.getRandomNum(4)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));
					if(obSystemAccountt.getAccountType().equals(ObSystemAccount.ACCOUNTTYPE_PLATE)){
						obAccountDealInfo.setMsg("平台普通账户初始化调账记录");
					}else if(obSystemAccountt.getAccountType().equals(ObSystemAccount.ACCOUNTTYPE_RISK)){
						obAccountDealInfo.setMsg("平台风险保证金账户初始化调账记录");
					}else if(obSystemAccountt.getAccountType().equals(ObSystemAccount.ACCOUNTTYPE_GUARANTEE)){
						obAccountDealInfo.setMsg("保证金账户初始化调账记录");
					}
					obAccountDealInfoService.save(obAccountDealInfo);
				}
			}
			jsonString = "{success:true}";
		}catch(Exception e){
			e.printStackTrace();
			jsonString = "{success:false}";
		}
		return SUCCESS;
	}
	/**
	 * 获取账户余额
	 * @return
	 */
	public String getAccountBalanceMoney(){
		String accountType=this.getRequest().getParameter("accountType");
		List<ObSystemAccount> ob =obSystemAccountService.getByAccountType(accountType);
		String msg="";
		if(ob!=null&&ob.size()>0){
			ObSystemAccount obb=ob.get(0);
			msg="【<font style='line-height:20px'>账号：</font><font style='line-height:20px'>"+obb.getAccountNumber()+"</font>   <font style='line-height:20px'>账户余额：</font><font style='line-height:20px'>"+obb.getTotalMoney().setScale(1)+"0元</font>】";
		}else{
			msg="【<font style='line-height:20px'>账号：</font><font style='line-height:20px'></font><font style='line-height:20px'>账户余额：</font><font style='line-height:20px'>0元</font>】";
		}
		jsonString = "{success:true,msg:\""+msg+"\"}";
		return SUCCESS;
	}
	/**
	 * 平台资金账户充值取现的先决条件
	 * @return
	 */
	public String plateRechargePre(){
		String plateFromFinanceType=this.getRequest().getParameter("accountType");
		List<ObSystemAccount> ob =obSystemAccountService.getByAccountType(plateFromFinanceType);
		int code=0;//默认不能进行资金交易
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true");
		String thirdPayConfig =configMap.get("thirdPayConfig");
		int openNewPage=0;//默认不用window.open  打开新页面  1表示需要打开新页面
		if(thirdPayConfig.equals("umpayConfig")){
			openNewPage=1;
		}
		String thirdPayName =configMap.get("thirdPayName");
		String thirdPayTypeName=configMap.get("thirdPayTypeName");                                                                                                                                                                                                          
		String platFormNumber=configMap.get("thirdPayPlateFormNumber");
		if(platFormNumber!=null&&!"".equals(platFormNumber)){
			if(plateFromFinanceType.equals(ObSystemAccount.ACCOUNTTYPE_PLATE)||plateFromFinanceType.equals(ObSystemAccount.ACCOUNTTYPE_RISK)){
				if(ob!=null&&ob.size()>0){
					code=1;
					sb.append(",msg:'可以账户自己交易'");
					sb.append(",thirdPayName:'");
					sb.append(thirdPayName);
					sb.append("',thirdPayTypeName:'");
					sb.append(thirdPayTypeName);
					sb.append("',platFormNumber:'");
					sb.append(platFormNumber);
					sb.append("',accountId:");
					sb.append(ob.get(0).getId());
					sb.append(",accountName:'");
					sb.append(ob.get(0).getAccountName());
					sb.append("',accountNumber:'");
					sb.append(ob.get(0).getAccountNumber());
					sb.append("',balanceMoney:");
					sb.append(ob.get(0).getTotalMoney());
				 }else{
					code=0;
					sb.append(",msg:'没有开通平台普通资金账户，不能进行充值'");
						
				 }
			}else{
				sb.append(",msg:'只能给平台普通资金账户充值'");
			}
		}else{
			sb.append(",msg:'没有第三方平台商户号，不允许充值'");
		}
		sb.append(",openNewPage:");
		sb.append(openNewPage);
		sb.append(",code:");
		sb.append(code);
		sb.append("}");
		setJsonString(sb.toString());
		jsonString = sb.toString();
		return SUCCESS;
	
	}
	/**
	 * 获取企业户充值的list表单
	 * @return
	 */
	public String getBankList(){
		try{
			String payType=this.getRequest().getParameter("payType");
			if(payType!=null&&!"".equals(payType)){
				String thirdPayConfig=configMap.get("thirdPayConfig");
				if(thirdPayConfig.equals(Constants.YEEPAY)){
					Properties p=yeePayService.getyeePayProperties();
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
				}else if(thirdPayConfig.equals("umpayConfig")){
					
				}
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 不需要调用第三方的接口给平台账户充值方法
	 * @return
	 */
	public String normalSaveDealInfo(){
		try{
			ObSystemAccount account=obSystemAccountService.get(obAccountDealInfo.getAccountId());
			if(account!=null){
				if(obAccountDealInfo.getIncomMoney().compareTo(new BigDecimal(0))>0){
					obAccountDealInfo.setAccountId(account.getId());
					obAccountDealInfo.setInvestPersonName(account.getAccountName());
					obAccountDealInfo.setDealType(ObAccountDealInfo.BANKDEAL);
					obAccountDealInfo.setTransferType(ObAccountDealInfo.T_RECHARGE);
					obAccountDealInfo.setCreateDate(new Date());
					obAccountDealInfo.setRecordNumber(Common.getRandomNum(4)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));
					obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_1);
					obAccountDealInfo.setMsg("往平台普通资金账户插入充值记录");
					obAccountDealInfoService.save(obAccountDealInfo);
					if(obAccountDealInfo.getId()!=null){
						BigDecimal currentMoney=obSystemAccountService.changeAccountMoney(account.getId(),obAccountDealInfo.getIncomMoney(),ObAccountDealInfo.DIRECTION_INCOME);
						if(currentMoney!=null){
							obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
							obAccountDealInfo.setCurrentMoney(currentMoney);
							obAccountDealInfo.setMsg("往平台普通资金账户充值成功");
							obAccountDealInfo.setTransferDate(new Date());
							obAccountDealInfoService.merge(obAccountDealInfo);
							setJsonString("{success:true,msg:'充值成功'}");
						}else{
							obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_3);
							obAccountDealInfo.setMsg("往平台普通资金账户充值失败");
							obAccountDealInfo.setTransferDate(new Date());
							obAccountDealInfoService.merge(obAccountDealInfo);
							setJsonString("{success:true,msg:'充值失败'}");
						}
					}else{
						setJsonString("{success:true,msg:'充值失败'}");
					}
					
				}else{
					setJsonString("{success:true,msg:'充值金额应该大于0元'}");
				}
			}else{
				setJsonString("{success:true,msg:'没有找到平台普通资金账户'}");
			}
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:true,msg:'系统出错，请联系管理员'}");
		}
		return SUCCESS;
	}
	/**
	 * 不需要调用第三方的接口给平台账户取现方法
	 * @return
	 */
	public String normalWithDrawSaveDealInfo(){
		try{
			ObSystemAccount account=obSystemAccountService.get(obAccountDealInfo.getAccountId());
			if(account!=null){
				if(obAccountDealInfo.getPayMoney().compareTo(new BigDecimal(0))>0){
					obAccountDealInfo.setAccountId(account.getId());
					obAccountDealInfo.setInvestPersonName(account.getAccountName());
					obAccountDealInfo.setDealType(ObAccountDealInfo.BANKDEAL);
					obAccountDealInfo.setTransferType(ObAccountDealInfo.T_ENCHASHMENT);
					obAccountDealInfo.setCreateDate(new Date());
					obAccountDealInfo.setRecordNumber(Common.getRandomNum(4)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss"));
					obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_1);
					obAccountDealInfo.setMsg("往平台普通资金账户插入取现记录");
					obAccountDealInfoService.save(obAccountDealInfo);
					if(obAccountDealInfo.getId()!=null){
						BigDecimal currentMoney=obSystemAccountService.changeAccountMoney(account.getId(),obAccountDealInfo.getIncomMoney(),ObAccountDealInfo.DIRECTION_PAY);
						if(currentMoney!=null){
							obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_2);
							obAccountDealInfo.setCurrentMoney(currentMoney);
							obAccountDealInfo.setMsg("往平台普通资金账户取现成功");
							obAccountDealInfo.setTransferDate(new Date());
							obAccountDealInfoService.merge(obAccountDealInfo);
							setJsonString("{success:true,msg:'取现成功'}");
						}else{
							obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_3);
							obAccountDealInfo.setMsg("往平台普通资金账户取现失败");
							obAccountDealInfo.setTransferDate(new Date());
							obAccountDealInfoService.merge(obAccountDealInfo);
							setJsonString("{success:true,msg:'取现失败'}");
						}
					}else{
						setJsonString("{success:true,msg:'取现失败'}");
					}
					
				}else{
					setJsonString("{success:true,msg:'取现金额应该大于0元'}");
				}
			}else{
				setJsonString("{success:true,msg:'没有找到平台普通资金账户'}");
			}
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:true,msg:'系统出错，请联系管理员'}");
		}
		return SUCCESS;
	} 
	
	/**
	 * 获取平台随息收取台账明细
	 * @return
	 */
	public String getBidPlateFormReciveMoney (){
		List<PlateFormBidIncomeDetail> list=platFormFinanceDao.getBidPlateFormReciveMoney(this.getRequest(),start,limit);
		List<PlateFormBidIncomeDetail> listcount=platFormFinanceDao.getBidPlateFormReciveMoney(this.getRequest(),null,null);
		Type type = new TypeToken<List<PlateFormBidIncomeDetail>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 获取平台一次性收费台账明细
	 * @return
	 */
	public String getOneTimePlateFormReciveMoney (){
		String fundType="风险保证金";
		List<PlateFormBidIncomeDetail> list=platFormFinanceDao.getOneTimeReciveMoneyList(this.getRequest(),fundType,start,limit);
		List<PlateFormBidIncomeDetail> listcount=platFormFinanceDao.getOneTimeReciveMoneyList(this.getRequest(),fundType,null,null);
		Type type = new TypeToken<List<PlateFormBidIncomeDetail>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 获取平台账户非业务相关资金台账
	 * @return
	 */
	public String getAccountDealInfoByTransferType(){
		List<ObAccountDealInfo> list=platFormFinanceDao.getAccountDealInfoByTransferType(this.getRequest(),start,limit);
		List<ObAccountDealInfo> listcount=platFormFinanceDao.getAccountDealInfoByTransferType(this.getRequest(),null,null);
		Type type = new TypeToken<List<ObAccountDealInfo>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public String getPlateFormRedFianceDetail(){
		List<PlateRedFinanceDetail> list=platFormFinanceDao.getPlateFormRedFianceDetail(this.getRequest(),start,limit);
		List<PlateRedFinanceDetail> listcount=platFormFinanceDao.getPlateFormRedFianceDetail(this.getRequest(),null,null);
		Type type = new TypeToken<List<PlateRedFinanceDetail>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public void outputExcel(){
		String [] tableHeader = {"序号","借款人","招标项目名称","招标项目编号","资金类型","计划收入金额","计划收入时间","实际到账金额","实际到账时间","未到账金额"};
		try {
			List<PlateFormBidIncomeDetail> list=platFormFinanceDao.getOneTimeReciveMoneyList(this.getRequest(),"风险保证金",start,limit);
			ExcelHelper.export(list,tableHeader,"平台其他收费台账");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void outputOtherExcel(){
		String [] tableHeader = {"序号","系统交易流水号","第三方对账流水号","转入账号","转出账号","交易类型","收入金额","支出金额","创建时间","实际交易时间","交易摘要"};
		try {
			List<ObAccountDealInfo> list=platFormFinanceDao.getAccountDealInfoByTransferType(this.getRequest(),start,limit);
			ExcelHelper.export(list,tableHeader,"非业务相关台账");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void outputRedExcel(){
		String [] tableHeader = {"序号","红包系统流水编号","第三方对账流水号","会员账号","会员姓名","会员注册时间","红包名称","计划派发金额","实际派发金额","到账时间"};
		try {
			List<PlateRedFinanceDetail> list=platFormFinanceDao.getPlateFormRedFianceDetail(this.getRequest(),start,limit);
			ExcelHelper.export(list,tableHeader,"红包发放台账");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}