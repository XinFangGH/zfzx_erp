package com.zhiwei.credit.action.creditFlow.creditAssignment.bank;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObShopintentStatistic;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObObligationInvestInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObShopintentStatisticService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.thirdInterface.WebBankcardService;
import com.zhiwei.credit.service.thirdPay.ThirdpayClientService;

import flexjson.JSONSerializer;

/**
 * 
 * @author
 * 
 */
public class ObAccountDealInfoAction extends BaseAction {
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private CsBankService csBankService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private ObShopintentStatisticService obShopintentStatisticService;
	private ObAccountDealInfo obAccountDealInfo;
	private List<ObAccountDealInfo> ptList;
	@Resource
	private ThirdpayClientService thirdpayClientService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private ObObligationInvestInfoService obObligationInvestInfoService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private WebBankcardService webBankCardService;
	
	
	
	private Long id;

	private CsInvestmentperson csInvestmentperson;
	
	private ObSystemAccount obSystemAccount;
	/**
	 * 标志系统账户交易明细记录类型
	 * 1表示充值，2表示取现,3收益，4投资，5还本
	 */
	private String transferType;
	/**
	 * 标志系统账户交易明细记录是否生效，或者冻结
	 */
	private Short rechargeConfirmStatus;
	/**
	 * 表示当前系统账户交易明细记录状态：1审批2成功3失败4复核5办理
	 */
	private Short dealRecordStatus;
	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	public Short getRechargeConfirmStatus() {
		return rechargeConfirmStatus;
	}

	public void setRechargeConfirmStatus(Short rechargeConfirmStatus) {
		this.rechargeConfirmStatus = rechargeConfirmStatus;
	}

	public List<ObAccountDealInfo> getPtList() {
		return ptList;
	}

	public void setPtList(List<ObAccountDealInfo> ptList) {
		this.ptList = ptList;
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
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<ObAccountDealInfo> list = obAccountDealInfoService.getAll(filter);

		Type type = new TypeToken<List<ObAccountDealInfo>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}
	/**
	 * 平台交易总览
	 */
	public String getPlateDeal() {
		QueryFilter filter = new QueryFilter(getRequest());
		String transferDate_GE = this.getRequest().getParameter("transferDate_GE");
		String transferDate_LE = this.getRequest().getParameter("transferDate_LE");
		String recordNumber = this.getRequest().getParameter("recordNumber");
		String truename = this.getRequest().getParameter("truename");
		String transferType = this.getRequest().getParameter("transferType");
		String dealRecordStatus = this.getRequest().getParameter("dealRecordStatus");
		if(transferType!=null&&!transferType.equals("")){
			/*List inList=new ArrayList();
			inList.add("1");
			inList.add("2");
			inList.add("4");*/
			filter.addFilter("Q_transferType_S_GE", transferType);
		}
		if(transferDate_GE!=null&&!transferDate_GE.equals("")){
			filter.addFilter("Q_createDate_D_GE", transferDate_GE+" 00:00:00");
		}
		if(transferDate_LE!=null&&!transferDate_LE.equals("")){
			filter.addFilter("Q_createDate_D_LE", transferDate_LE+" 23:59:59");
		}
		if(truename!=null&&!truename.equals("")){
			filter.addFilter("Q_investPersonName_S_LK", truename);
		}
		if(recordNumber!=null&&!recordNumber.equals("")){
			filter.addFilter("Q_recordNumber_S_LK", recordNumber);
		}
		if(transferType!=null&&!transferType.equals("")){
			filter.addFilter("Q_transferType_S_EQ", transferType);
		}
		if(dealRecordStatus!=null&&!dealRecordStatus.equals("")){
			filter.addFilter("Q_dealRecordStatus_SN_EQ", dealRecordStatus);
		}
		filter.addSorted("transferDate", QueryFilter.ORDER_DESC);
		List<ObAccountDealInfo> list = obAccountDealInfoService.getAll(filter);
		for(ObAccountDealInfo deal :list){
			obAccountDealInfoService.evict(deal);
			if(deal.getTransferDate()==null||deal.getTransferType().equals("")){
				deal.setTransferDate(deal.getCreateDate());
			}
			BpCustMember member = bpCustMemberService.get(deal.getInvestPersonId());
			if(member!=null){
				deal.setLoginname(member.getLoginname());
			}
		}
		Type type = new TypeToken<List<ObAccountDealInfo>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
		",result:");
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 导出平台交易总览
	 * @return
	 */
	public void getExcelPlateDeal() {
		QueryFilter filter = new QueryFilter(getRequest());
		String transferDate_GE = this.getRequest().getParameter("transferDate_GE");
		String transferDate_LE = this.getRequest().getParameter("transferDate_LE");
		String recordNumber = this.getRequest().getParameter("recordNumber");
		String truename = this.getRequest().getParameter("truename");
		String transferType = this.getRequest().getParameter("transferType");
		String dealRecordStatus = this.getRequest().getParameter("dealRecordStatus");
		if(transferType!=null&&!transferType.equals("")){
			filter.addFilter("Q_transferType_S_GE", transferType);
		}
		if(transferDate_GE!=null&&!transferDate_GE.equals("")){
			filter.addFilter("Q_createDate_D_GE", transferDate_GE+" 00:00:00");
		}
		if(transferDate_LE!=null&&!transferDate_LE.equals("")){
			filter.addFilter("Q_createDate_D_LE", transferDate_LE+" 00:00:00");
		}
		if(truename!=null&&!truename.equals("")){
			filter.addFilter("Q_investPersonName_S_LK", truename);
		}
		if(recordNumber!=null&&!recordNumber.equals("")){
			filter.addFilter("Q_recordNumber_S_LK", recordNumber);
		}
		if(transferType!=null&&!transferType.equals("")){
			if(transferType.equals("recharge")){
				filter.addFilter("Q_transferType_S_EQ", "1");
			}else if(transferType.equals("withdraw")){
				filter.addFilter("Q_transferType_S_EQ", "2");
			}else{
				filter.addFilter("Q_transferType_S_EQ", transferType);
			}
		}
		if(dealRecordStatus!=null&&!dealRecordStatus.equals("")){
			filter.addFilter("Q_dealRecordStatus_SN_EQ", dealRecordStatus);
		}
		filter.getPagingBean().setPageSize(100000000);
		List<ObAccountDealInfo> list = obAccountDealInfoService.getAll(filter);
		for(ObAccountDealInfo deal :list){
			obAccountDealInfoService.evict(deal);
			if(deal.getTransferDate()==null||deal.getTransferType().equals("")){
				deal.setTransferDate(deal.getCreateDate());
			}
			BpCustMember member = bpCustMemberService.get(deal.getInvestPersonId());
			if(member!=null){
				deal.setLoginname(member.getLoginname());
			}
			if(deal.getTransferType().equals("1")){
				deal.setTransferTypeNmae("充值");
			}else if(deal.getTransferType().equals("2")){
				deal.setTransferTypeNmae("提现");
			}else if(deal.getTransferType().equals("3")){
				deal.setTransferTypeNmae("投资收益");
			}else if(deal.getTransferType().equals("4")){
				deal.setTransferTypeNmae("投资");
			}else if(deal.getTransferType().equals("5")){
				deal.setTransferTypeNmae("本金收回");
			}else if(deal.getTransferType().equals("6")){
				deal.setTransferTypeNmae("取现手续费");
			}else if(deal.getTransferType().equals("7")){
				deal.setTransferTypeNmae("借款人还本付息");
			}else if(deal.getTransferType().equals("8")){
				deal.setTransferTypeNmae("借款人借款入账");
			}else if(deal.getTransferType().equals("10")){
				deal.setTransferTypeNmae("系统红包");
			}else if(deal.getTransferType().equals("25")){
				deal.setTransferTypeNmae("投资返现");
			}else if(deal.getTransferType().equals("26")){
				deal.setTransferTypeNmae("加息返现");
			}else{
				deal.setTransferTypeNmae("其他");
			}
			if(deal.getDealRecordStatus().toString().equals("1")){
				deal.setDealRecordStatusName("失败");
			}else if(deal.getDealRecordStatus().toString().equals("2")){
				deal.setDealRecordStatusName("成功");
			}else if(deal.getDealRecordStatus().toString().equals("7")){
				deal.setDealRecordStatusName("冻结");
			}else{
				deal.setDealRecordStatusName("其他");
			}
		}
		try {
			if(transferType!=null&&!transferType.equals("")&&transferType.equals("recharge")){
				String[] tableHeader = {"序号", "交易日期", "订单编号","充值金额","登录名",
						"真实姓名","交易状态"};
				String[] fields = {"transferDate","recordNumber","incomMoney","loginname",
						"investPersonName","dealRecordStatusName"};
				ExportExcel.export(tableHeader, fields, list,"平台充值台账", ObAccountDealInfo.class);
			}else if(transferType!=null&&!transferType.equals("")&&transferType.equals("withdraw")){
				String[] tableHeader = {"序号", "交易日期", "订单编号","取现金额","登录名",
						"真实姓名","交易状态"};
				String[] fields = {"transferDate","recordNumber","payMoney","loginname",
						"investPersonName","dealRecordStatusName"};
				ExportExcel.export(tableHeader, fields, list,"平台取现台账", ObAccountDealInfo.class);
			}else{
				String[] tableHeader = {"序号", "交易日期", "订单编号", "交易类型","收入金额","支出金额" ,"登录名",
						"真实姓名","交易状态"};
				String[] fields = {"transferDate","recordNumber","transferTypeNmae","incomMoney","payMoney","loginname",
						"investPersonName","dealRecordStatusName"};
				ExportExcel.export(tableHeader, fields, list,"平台交易总览", ObAccountDealInfo.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				obAccountDealInfoService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		ObAccountDealInfo obAccountDealInfo = obAccountDealInfoService.get(id);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(obAccountDealInfo));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	//旧的方法
	public String save() {
		if (obAccountDealInfo.getId() == null) {
			AppUser user = ContextUtil.getCurrentUser();
			obAccountDealInfo.setCreateId(user.getUserId());
			obAccountDealInfo.setCreateDate(new Date());
			obAccountDealInfoService.save(obAccountDealInfo);
		} else {
			ObAccountDealInfo orgObAccountDealInfo = obAccountDealInfoService
					.get(obAccountDealInfo.getId());
			try {
				BeanUtil.copyNotNullProperties(orgObAccountDealInfo,
						obAccountDealInfo);
				obAccountDealInfoService.save(orgObAccountDealInfo);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		// 生成款项信息以及修改平台账户总额
		List<SlFundIntent> list = this.slFundIntentService
				.findAccountFundIntent(obAccountDealInfo.getAccountId(),
						obAccountDealInfo.getInvestPersonId(),
						obAccountDealInfo.getId(), 1);
		if (list != null && list.size() > 0) {// 用来移除该条平台账户交易有款项台账记录
			for (SlFundIntent temp : list) {
				this.slFundIntentService.remove(temp);
			}
		}
		ObSystemAccount account = this.obSystemAccountService.get(obAccountDealInfo.getAccountId());
		SlFundIntent sl = new SlFundIntent();
		sl.setFundType("accountEnchashment");// accountEnchashment资金类型表示为平台账户取现
		sl.setBusinessType("Assignment");
		sl.setIsCheck(Short.valueOf("0"));// 0表示可以对账
		sl.setIsValid(Short.valueOf("0"));// 0表示有效的
		sl.setIntentDate(obAccountDealInfo.getTransferDate());
		sl.setPayMoney(obAccountDealInfo.getPayMoney());// 表示取现金额
		sl.setIncomeMoney(new BigDecimal(0));
		sl.setAfterMoney(new BigDecimal(0));
		sl.setNotMoney(obAccountDealInfo.getPayMoney());
		sl.setFlatMoney(new BigDecimal(0));
		sl.setCompanyId(obAccountDealInfo.getCompanyId());
		sl.setAccountNumber(account.getAccountNumber());// 保存投资人平台账户的账号
		sl.setSystemAccountId(obAccountDealInfo.getAccountId());
		sl.setInvestPersonId(obAccountDealInfo.getInvestPersonId());
		sl.setInvestPersonName(obAccountDealInfo.getInvestPersonName());
		sl.setAccountDealInfoId(obAccountDealInfo.getId());
		this.slFundIntentService.save(sl);

		account.setTotalMoney(account.getTotalMoney().subtract(obAccountDealInfo.getPayMoney()));
		account.setAccountStatus((short) 0);// 1投资客户已经启动流程，0流程未启动
		this.obSystemAccountService.merge(account);
		obAccountDealInfo.setCurrentMoney(account.getTotalMoney());// 生成完款项将取现完成后账户的金额还剩多少保存上
		obAccountDealInfoService.merge(obAccountDealInfo);
		setJsonString("{success:true}");
		return SUCCESS;

	}
	/**
	 * 取现申请方法
	 * 需要启动取现流程
	 */
	public String saveEnchashmentApply(){
		AppUser user = ContextUtil.getCurrentUser();
		obAccountDealInfo.setRechargeLevel(Short.valueOf("2"));
		obAccountDealInfo.setInvestPersonType(Short.valueOf("1"));
		obAccountDealInfo.setAccountId(obSystemAccount.getId());
		obAccountDealInfo.setInvestPersonId(csInvestmentperson.getInvestId());
		obAccountDealInfo.setInvestPersonName(csInvestmentperson.getInvestName());
		obAccountDealInfo.setCreateId(user.getUserId());
		obAccountDealInfo.setCreateDate(new Date());
		obAccountDealInfo.setCompanyId(ContextUtil.getLoginCompanyId());
		String  msg=creditProjectService.startEnchashmentFlow(obAccountDealInfo);
		jsonString="{success:true,"+msg+"}";
		return SUCCESS;
	}
	
	public String getInfo(){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String investPersonId = this.getRequest().getParameter("investId");
			String taskId = this.getRequest().getParameter("taskId"); // 任务ID
			String obAccountDealInfoId =this.getRequest().getParameter("obAccountDealInfoId"); // 取现清单ID
			String customerType=this.getRequest().getParameter("customerType"); // 投资客户的类型
			if(obAccountDealInfoId!=null&&!"".equals(obAccountDealInfoId)){
				ObAccountDealInfo info =obAccountDealInfoService.get(Long.valueOf(obAccountDealInfoId));
				if(info!=null&&info.getCreateId()!=null){
					AppUser user =appUserService.get(info.getCreateId());
					info.setCreateName(user.getFullname());
				}
				map.put("obAccountDealInfo", info);
				if(investPersonId!=null&&!"".equals(investPersonId)){
					/* ===========查询余额信息============= */
					ObSystemAccount ob=obSystemAccountService.getByInvrstPersonIdAndType(Long.parseLong(investPersonId), Short.valueOf(customerType));
					if(ob!=null){
						BigDecimal freeze = obSystemAccountService.prefreezMoney(ob.getId(), null);
						if(freeze!=null){
							ob.setFreezeMoney(freeze);
							ob.setAvailableInvestMoney(ob.getTotalMoney().subtract(freeze));
						}else{
							ob.setFreezeMoney(new BigDecimal(0));
							ob.setAvailableInvestMoney(ob.getTotalMoney());
						}
					}
					map.put("obSystemAccount", ob);
					/* =========== 查询账户信息=========== */
					if(customerType!=null&&!"".equals(customerType)&&"0".equals(customerType)){
						BpCustMember bp=bpCustMemberService.get(Long.parseLong(investPersonId));
						map.put("bpCustMember", bp);
						if(info.getBankId()!=null){
							WebBankcard card=webBankCardService.get(info.getBankId());
							map.put("webBankcard", card);
						}else{
							map.put("webBankcard", null);
						}
						
						
					}else{
						csInvestmentperson = csInvestmentpersonService.get(Long.parseLong(investPersonId));
						map.put("csInvestmentperson", csInvestmentperson);
						List<EnterpriseBank> elist = enterpriseBankService.getBankList(Integer.valueOf(investPersonId), Short.valueOf("1"), Short.valueOf("0"), "0".equals(customerType)?Short.valueOf("4"):Short.valueOf("3"));
						EnterpriseBank enterpriseBank=null;
						if (null != elist && elist.size() > 0) {
							enterpriseBank = elist.get(0);
						}
						map.put("enterpriseBank", enterpriseBank);
					}
				}
			}
			
			if (null != taskId && !"".equals(taskId)) {
				ProcessForm pform = processFormService.getByTaskId(taskId);
				if (pform != null && pform.getComments() != null && !"".equals(pform.getComments())) {
					map.put("comments", pform.getComments());
				}else{
					map.put("comments", "");
				}
			}
			StringBuffer buff = new StringBuffer("{success:true,data:");
			JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(map));
			buff.append("}");
			jsonString=buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return SUCCESS;
	}

	// 保存充值交易信息
	public String saveDealInfo() {
		StringBuffer buff = new StringBuffer("");
		ObSystemAccount account = obSystemAccountService.get(obAccountDealInfo.getAccountId());
		CsInvestmentperson csInvestmentperson = csInvestmentpersonService.get(account.getInvestmentPersonId());
		//保存充值记录基本数据
		String degree = this.getRequest().getParameter("degree");
		String investPersonName = this.getRequest().getParameter("investpersonName");
		//obAccountDealInfo.setCreateId(Long.valueOf(degree));
		obAccountDealInfo.setCreateDate(new Date());
		obAccountDealInfo.setInvestPersonName(investPersonName);
		obAccountDealInfo.setCompanyId(account.getCompanyId());
		if(obAccountDealInfo.getRechargeLevel()==1){//天储第三方机构充值划扣方法
			String[] rechargeArr=new String[2];
			if(configMap.get("thirdPayConfig").toString().equals(Constants.FUIOU)){
			 rechargeArr = thirdpayClientService.rechargeFuiou(csInvestmentperson, obAccountDealInfo.getIncomMoney(), "充值",this.getRequest());
			}else{
			rechargeArr[0]="000000";
			rechargeArr[1]="000000";
			}
			
			if (rechargeArr != null && rechargeArr[0].equals("000000")) {
				try{
					obAccountDealInfoService.save(obAccountDealInfo);
					// 更新系统个人账户的总额
					account.setTotalMoney(account.getTotalMoney().add(obAccountDealInfo.getIncomMoney()));
					obSystemAccountService.merge(account);
					obAccountDealInfo.setCurrentMoney(account.getTotalMoney());// 跟新当前充值记录生效后账户的余额是多少
					obAccountDealInfo.setDealRecordStatus(Short.valueOf("2"));
					obAccountDealInfoService.merge(obAccountDealInfo);
					buff.append("{success:true,'msg':'充值成功！'");
				}catch (Exception e) {
					obAccountDealInfo.setDealRecordStatus(Short.valueOf("3"));
					obAccountDealInfoService.save(obAccountDealInfo);
					buff.append("{success:false,'msg':"+e.getMessage());
				}
			} else {
				buff.append("{success:false,'msg':\""+rechargeArr[1].replace("\"", "")+"\"");
	     	}
		}else if(obAccountDealInfo.getRechargeLevel()==2){//系统账户自维护方法
			obAccountDealInfo.setDealRecordStatus(Short.valueOf("1"));
			obAccountDealInfo.setInvestPersonType(Short.valueOf("1"));
			obAccountDealInfoService.save(obAccountDealInfo);
			buff.append("{success:true,'msg':'已经提交了充值申请，等待充值一次审核！'");
		}
		
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	// 系统账户交易记录查询方法
	/**
	 * 
	 */
	public String getreChargeList() {
		List<ObAccountDealInfo> list = obAccountDealInfoService.queyAccountInfoRecord(null,transferType,dealRecordStatus,rechargeConfirmStatus,this.getRequest(),start,limit);
		List<ObAccountDealInfo> listcount = obAccountDealInfoService.queyAccountInfoRecord(null,transferType,dealRecordStatus,rechargeConfirmStatus,this.getRequest(),null,null);
		if(null!=list&&list.size()!=0){
			for(int i=0;i<list.size();i++){
				ObAccountDealInfo dealInfo = list.get(i);
				if(null!=dealInfo&&(null==dealInfo.getInvestPersonName()||"".equals(dealInfo.getInvestPersonName()))){
					if(null==dealInfo.getInvestPersonType()||0==dealInfo.getInvestPersonType()){
						if(null!=dealInfo.getInvestPersonId()){
							BpCustMember member = bpCustMemberService.get(dealInfo.getInvestPersonId());
							if(null!=member){
								dealInfo.setInvestPersonName(member.getTruename());
							}
						}
					}else{
						if(null!=dealInfo.getInvestPersonId()){
							CsInvestmentperson person = csInvestmentpersonService.get(dealInfo.getInvestPersonId());
							if(null!=person){
								dealInfo.setInvestPersonName(person.getInvestName());
							}	
						}
					}
				}
				
			}
		}

		Type type = new TypeToken<List<ObAccountDealInfo>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(listcount == null ? 0 : listcount.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	// 确认充值情况 并生成款项
	public String ConfirmStatus() {
		String rechargeConfirmStatus = this.getRequest().getParameter("rechargeConfirmStatus");
		ObAccountDealInfo info = obAccountDealInfoService.get(id);
		if (rechargeConfirmStatus != null && !"".equals(rechargeConfirmStatus)&& !"null".equals(rechargeConfirmStatus)) {
				info.setDealRecordStatus(Short.valueOf("2"));
				//saveObShopIntentStatistic(info);
				// 更新系统个人账户的总额
				ObSystemAccount account = obSystemAccountService.get(info.getAccountId());
				account.setTotalMoney(account.getTotalMoney().add(info.getIncomMoney()));
				obSystemAccountService.merge(account);
				info.setCurrentMoney(account.getTotalMoney());// 跟新当前取现记录生效后账户的余额是多少
				obAccountDealInfoService.merge(info);
		}
		obAccountDealInfoService.merge(info);
		return SUCCESS;
	}
	/**
	 * 统计门店充值所得收益
	 * @param info
	 * @return
	 */
	private Boolean saveObShopIntentStatistic(ObAccountDealInfo info) {
		try {
			ObShopintentStatistic shopStatic = new ObShopintentStatistic();
			shopStatic.setShopId(info.getShopId());
			shopStatic.setShopName(info.getShopName());
			shopStatic.setDealInfoId(info.getId());
			shopStatic.setDealMoney(info.getIncomMoney());
			shopStatic.setInvestpersonId(info.getInvestPersonId());
			shopStatic.setInvestName(info.getInvestPersonName());
			shopStatic.setSystemAccountId(info.getAccountId());
			shopStatic.setSystenAccountNumber(info.getAccountNumber());
			shopStatic.setCreateDate(new Date());
			String shopRate = AppUtil.getshopRate();
			if (shopRate != null && !"".equals(shopRate)) {
				shopStatic.setShopRate(new BigDecimal(shopRate));
				shopStatic.setShopIntent(info.getIncomMoney().multiply(
						new BigDecimal(shopRate)).divide(new BigDecimal(100)));
			} else {
				shopStatic.setShopRate(new BigDecimal(0));
				shopStatic.setShopIntent(new BigDecimal(0));
			}
			AppUser appUser = ContextUtil.getCurrentUser();
			shopStatic.setCreatorId(appUser.getUserId());
			shopStatic.setCreatorName(appUser.getFullname());
			shopStatic.setCompanyId(ContextUtil.getLoginCompanyId());
			obShopintentStatisticService.save(shopStatic);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 根据系统账户Id  以及查询条件查询系统账户的收支查询
	 * 账户明细查询
	 * 
	 *  */
	public String accountListQuery() {
		String accountId = this.getRequest().getParameter("accountId");
		if (null != accountId) {
			List<ObAccountDealInfo> list = obAccountDealInfoService.getaccountListQuery(accountId,this.getRequest(),start,limit);
			List<ObAccountDealInfo> listcount =obAccountDealInfoService.getaccountListQuery(accountId,this.getRequest(),null,null);
			Type type = new TypeToken<List<ObAccountDealInfo>>() {}.getType();
			// 指定集合对象属性
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(listcount == null ? 0 : listcount.size()).append(",result:");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list, type));
			buff.append("}");
			jsonString = buff.toString();
		}
		return SUCCESS;
	}
	/**
	 * 查询线上提现申请审核
	 * @return
	 */
	public String withdrawCheck() {
			List<ObAccountDealInfo> list = obAccountDealInfoService.getWithdrawCheck(this.getRequest(),start,limit);
			List<ObAccountDealInfo> listcount =obAccountDealInfoService.getWithdrawCheck(this.getRequest(),null,null);
			Type type = new TypeToken<List<ObAccountDealInfo>>() {}.getType();
			// 指定集合对象属性
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(listcount == null ? 0 : listcount.size()).append(",result:");
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			buff.append(gson.toJson(list, type));
			buff.append("}");
			jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 充值审批
	 * @return
	 */
	public String updateApproval(){
		String approvalState = this.getRequest().getParameter("approvalState");
		String ids = this.getRequest().getParameter("ids");
		ObAccountDealInfo ob = obAccountDealInfoService.get(Long.valueOf(ids));
		if(ob!=null){
			ob.setDealRecordStatus(Short.valueOf(approvalState));
			obAccountDealInfoService.save(ob);
			//saveObShopIntentStatistic(info);
			if(approvalState!=null&&!"".equals(approvalState)&&!"null".equals(approvalState)&&"2".equals(approvalState)){
				// 更新系统个人账户的总额
				ObSystemAccount account = obSystemAccountService.get(ob.getAccountId());
				account.setTotalMoney(account.getTotalMoney().add(ob.getIncomMoney()));
				obSystemAccountService.merge(account);
				ob.setCurrentMoney(account.getTotalMoney());// 跟新当前取现记录生效后账户的余额是多少
				obAccountDealInfoService.merge(ob);
			}
		}
		
		return SUCCESS;
	}
	/***
	 * 取现审核清单查询方法 zcb
	 * 
	 * @return
	 */
	public String getListInfo() {
		String investPersonName = this.getRequest().getParameter(
				"investPersonName");
		String transferDate = this.getRequest().getParameter("transferDate");
		List<ObAccountDealInfo> list = obAccountDealInfoService.getDealList(
				investPersonName, transferDate, "2");
		List<ObAccountDealInfo> listAdd = new ArrayList<ObAccountDealInfo>();
		if (list != null && list.size() > 0) {
			for (ObAccountDealInfo temp : list) {
				if (null != temp && !"".equals(temp)) {
					List<SlFundIntent> sl = slFundIntentService
							.findAccountFundIntent(temp.getAccountId(), temp
									.getInvestPersonId(), temp.getId(), 1);
					if (sl != null && sl.size() > 0) {
						ObSystemAccount obSystemAccount = obSystemAccountService
								.get(temp.getAccountId());
						temp.setAccountName(obSystemAccount.getAccountName()); // 取现账户
						temp.setAccountNumber(obSystemAccount
								.getAccountNumber());// 取现账号
						if (null != temp.getCreateId()) {
							AppUser user = appUserService.get(temp
									.getCreateId());
							temp.setCreateName(user.getFullname());
						}
						List<EnterpriseBank> elist = enterpriseBankService
								.getBankList(Integer.valueOf(temp
										.getInvestPersonId().toString()), Short
										.valueOf("1"), Short.valueOf("0"),
										Short.valueOf("3"));
						if (null != elist && elist.size() > 0) {
							EnterpriseBank bank = elist.get(0);
							/*
							 * CsBank csBank =
							 * csBankService.get(bank.getBankid());
							 * temp.setKhname(csBank.getBankname());
							 */

						}
						listAdd.add(temp);
					}

				}
			}
		}

		Type type = new TypeToken<List<ObAccountDealInfo>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(listAdd.size() <= 0 ? 0 : listAdd.size()).append(
						",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(listAdd, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/***
	 * 取现审核清单 打印清单
	 * 
	 * @return
	 */
	public String p_beforeFundRateH() {
		List<ObAccountDealInfo> ptList_temp = null;
		String JsonData = this.getRequest().getParameter("JsonData");
		ptList_temp = printH(JsonData);
		ptList = ptList_temp;
		return "p_productProfitH";
	}

	// 打印清单方法
	private List<ObAccountDealInfo> printH(String JsonData) {
		List<ObAccountDealInfo> ptList_temp = new ArrayList<ObAccountDealInfo>();
		if (null != JsonData && !"".equals(JsonData)) {
			String[] jsonDataArr = JsonData.split("@");
			for (int i = 0; i < jsonDataArr.length; i++) {
				String str = jsonDataArr[i];
				String id = str.substring(str.lastIndexOf(":") + 1, str
						.length() - 1);
				if (null != id && !"".equals(id)) {
					ObAccountDealInfo odInfo = obAccountDealInfoService
							.get(Long.valueOf(id));
					if (null != odInfo && !"".equals(odInfo)) {
						ObSystemAccount obSystemAccount = obSystemAccountService
								.get(odInfo.getAccountId());
						List<EnterpriseBank> elist = enterpriseBankService
								.getBankList(Integer.valueOf(odInfo
										.getInvestPersonId().toString()), Short
										.valueOf("1"), Short.valueOf("0"),
										Short.valueOf("3"));
						if (null != elist && elist.size() > 0) {
							EnterpriseBank bank = elist.get(0);
							CsBank csBank = csBankService.get(bank.getBankid());
							odInfo.setKhname(csBank.getBankname());
						}
						odInfo.setAccountName(obSystemAccount.getAccountName()); // 取现账户
						odInfo.setAccountNumber(obSystemAccount
								.getAccountNumber());// 取现账号
						AppUser user = appUserService.get(odInfo.getCreateId());
						odInfo.setCreateName(user.getFullname());
						ptList_temp.add(odInfo);
					}
				}
			}
		}
		return ptList_temp;
	}
	
	/**
	 * 合作机构账户明细查询
	 * @return
	 */
	public String cooperationAccountInfoList(){

		String name = this.getRequest().getParameter("name");
		String startDate = this.getRequest().getParameter("startDate");
		String endDate = this.getRequest().getParameter("endDate");
		String transferType = this.getRequest().getParameter("transferType");
		Map<String,String> map = new HashMap<String, String>();
		map.put("start", this.start.toString());
		map.put("limit", this.limit.toString());
		map.put("name",name);
		map.put("startDate",startDate);
		map.put("endDate",endDate);
		map.put("transferType",transferType);
		
		
		List<ObAccountDealInfo> accountList = obAccountDealInfoService.cooperationAccountInfoList(map);
		Long count = obAccountDealInfoService.cooperationAccountInfoListCount(map);
		
		
		Type type = new TypeToken<List<ObSystemAccount>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(count == null ? 0 : count).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
		buff.append(gson.toJson(accountList, type));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println("jsonString=="+jsonString);
		return SUCCESS;
		
	
	}

	/**
	 *查询三方请求明细
	 *
	 * @auther: XinFang
	 * @date: 2018/7/23 14:12
	 */
	public  String ThirdPayRecoad(){
        String bpName = getRequest().getParameter("bpName");
        String startDate = getRequest().getParameter("startDate");
        String transferType = getRequest().getParameter("transferType");

		List<ThirdPayRecord> list = obAccountDealInfoService.getThirdPayRecord(bpName,startDate,transferType);

        StringBuffer buff = new StringBuffer("{success:true,totalCounts:").append(list == null ? 0 : list.size()).append(",result:");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        buff.append(gson.toJson(list));
        buff.append("}");
        jsonString = buff.toString();


        return  SUCCESS;
	}
	

	public void setCsInvestmentperson(CsInvestmentperson csInvestmentperson) {
		this.csInvestmentperson = csInvestmentperson;
	}

	public CsInvestmentperson getCsInvestmentperson() {
		return csInvestmentperson;
	}

	public void setObSystemAccount(ObSystemAccount obSystemAccount) {
		this.obSystemAccount = obSystemAccount;
	}

	public ObSystemAccount getObSystemAccount() {
		return obSystemAccount;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setDealRecordStatus(Short dealRecordStatus) {
		this.dealRecordStatus = dealRecordStatus;
	}

	public Short getDealRecordStatus() {
		return dealRecordStatus;
	}
	
}
