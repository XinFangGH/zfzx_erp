package com.zhiwei.credit.action.creditFlow.guarantee.project;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GLSuperviseRecord;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.VGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.common.GlobalSupervisemanageService;
import com.zhiwei.credit.service.creditFlow.customer.bankRelationPerson.CustomerBankRelationPersonService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlSuperviseRecordService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.VGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.creditFlow.smallLoan.meeting.SlConferenceRecordService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.OrganizationService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class GLGuaranteeloanProjectAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService; // 企业贷款
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private GlSuperviseRecordService glSuperviseRecordService;
	@Resource
	private VGuaranteeloanProjectService vGuaranteeloanProjectService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private SlCompanyMainService companyMainService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private PersonService personService;
	@Resource
    private EnterpriseService enterpriseService;
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource 
	private ProcessFormService processFormService;
	@Resource
	private  CustomerBankRelationPersonService customerBankRelationPersonService;
	
	@Resource
	private JbpmService jbpmService;
	@Resource
	private SlConferenceRecordService slConferenceRecordService;//add by gaoqingrui  用于添加会议纪要
	@Resource
	private GlobalSupervisemanageService globalSupervisemanageService;
	
	private GLGuaranteeloanProject gLGuaranteeloanProject;
	private CustomerBankRelationPerson customerBankRelationPerson;
	private Person person;
	private GLSuperviseRecord glSuperviseRecord;
	private Enterprise enterprise;
	private short projectStatus;
	private short bmStatus;
	private Boolean isGrantedShowAllProjects;// 是否授权查看所有项目信息
	private String bankLoanData;// 银行放款数据
	private String customerRepaymentData;// 客户还款数据
	private Long projectId;
	private GlBankGuaranteemoney glBankGuaranteemoney;
	private String operationType;
	private String flag;
	private String customerRepaymentPlanData;
	private Long superviseManageId;
	private GlobalSupervisemanage globalSupervisemanage;
	
	

	public GlobalSupervisemanage getGlobalSupervisemanage() {
		return globalSupervisemanage;
	}

	public void setGlobalSupervisemanage(GlobalSupervisemanage globalSupervisemanage) {
		this.globalSupervisemanage = globalSupervisemanage;
	}

	/**
	 * 在节点保存意见与说明 公共方法
	 * 
	 * @return
	 * @author shendx
	 */
	public String saveComments() {

		String taskId = this.getRequest().getParameter("task_id");
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments
				&& !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}
		return SUCCESS;
	}

	/**
	 * 项目信息列表
	 * 
	 * @author lisl
	 */
	public String projectList() {
		String userIdsStr = "";
		PagingBean pb = new PagingBean(start, limit);
		if (!isGrantedShowAllProjects) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
		List<VGuaranteeloanProject> list = vGuaranteeloanProjectService.getProjectList(userIdsStr, projectStatus, bmStatus, pb,getRequest());
		for(VGuaranteeloanProject vp : list) {
			String appuers = "";
			if(null != vp.getAppUserIdOfA()) {
				String [] appstr = vp.getAppUserIdOfA().split(",");
				Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
				for(AppUser s : userSet){
					appuers += s.getFullname()+",";
				}
			}
			if(appuers.length()>0) {
				appuers = appuers.substring(0,appuers.length() - 1);
			}
			vp.setAppUserIdOfAValue(appuers);
			
			//获取当前所处任务的处理人
			String executor = "";//任务执行人
			Set<AppUser> appExecutor = jbpmService.getNodeHandlerUsers(vp.getDefId(), vp.getActivityName());
			for (AppUser su : appExecutor) {
				executor = executor + su.getFamilyName() + ",";
			}
			if (executor.length() > 0) {
				executor = executor.substring(0, executor.length() - 1);
			}
			vp.setExecutor(executor);
		}
		//int counts = vGuaranteeloanProjectService.getProjectList(userIdsStr,projectStatus, bmStatus, null, getRequest()).size();
		Type type = new TypeToken<List<VGuaranteeloanProject>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?0:list.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 业务经理尽职调查-保存（更新）信息 企业贷款类型
	 * 
	 * @return
	 */
	public String update() {
		StringBuffer sb = new StringBuffer("{success:true");
		try {
			String shareequity = this.getRequest().getParameter("gudongInfo");//尽职调查的股东信息只可查看,不传该参数即可;该方法别处也调用,股东信息保留,
			String slActualToChargeJsonData = this.getRequest().getParameter("slActualToChargeJsonData");
			String isDeleteAllFundIntent = this.getRequest().getParameter("isDeleteAllFundIntent");
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (shareequity!=null&&!"".equals(shareequity)) {
				String[] shareequityArr = shareequity.split("@");
				for(int i=0; i<shareequityArr.length;i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
					listES.add(enterpriseShareequity);
				}
			}
			List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			String mineIdStr = this.getRequest().getParameter("gLGuaranteeloanProject.mineId");
			//更新主体单位名称 add by gaoqingrui
			Long mineId = null;
			if(null!= mineIdStr&&!"".equals(mineIdStr)){
				mineId = Long.valueOf(mineIdStr);
				gLGuaranteeloanProject.setMineId(mineId);
				if("true".equals(AppUtil.getSystemIsGroupVersion())){//集团办   更新主体单位Id则  companyId 也更新
					gLGuaranteeloanProject.setCompanyId(mineId);
				}else{
					gLGuaranteeloanProject.setCompanyId(Long.valueOf(1));
				}
			}
			slActualToCharges = savejsoncharge(slActualToChargeJsonData,gLGuaranteeloanProject, Short.parseShort("0"));
			
			
			
			this.glGuaranteeloanProjectService.updateInfo(
					gLGuaranteeloanProject, person, enterprise, 
					listES,customerBankRelationPerson,
					slFundIntents,slActualToCharges, isDeleteAllFundIntent);
			
		 	String taskId = this.getRequest().getParameter("task_id");
		   	String comments = this.getRequest().getParameter("comments");
		   	if(taskId!=null&&!"".equals(taskId)&&comments!=null&&!"".equals(comments.trim())){
		   		this.creditProjectService.saveComments(taskId, comments);
		   	}
		   	//保存 上传会议纪要信息
		   	SlConferenceRecord slConferenceRecord = new SlConferenceRecord();
			BeanUtil.populateEntity(this.getRequest(), slConferenceRecord,"slConferenceRecord");
			if(slConferenceRecord.getConforenceId()==null){
				slConferenceRecordService.save(slConferenceRecord);
				Long conforenceId=slConferenceRecord.getConforenceId();
			}else{
				SlConferenceRecord orgSlConferenceRecord=slConferenceRecordService.get(slConferenceRecord.getConforenceId());
				BeanUtil.copyNotNullProperties(orgSlConferenceRecord, slConferenceRecord);
				slConferenceRecordService.save(orgSlConferenceRecord);
			}
		   	
			if(null!=slConferenceRecord){
		   		sb.append(",conforenceId:"+slConferenceRecord.getConforenceId());
		   	}
		   	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("金融担保常规流程尽职调查保存出错:" + e.getMessage());
		}
		//sb.append("[{\"bankId\":\"" + customerBankRelationPerson.getId()+ "\"}]");
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	
		/*
		StringBuffer sb = new StringBuffer("{success:true,result:");
		try {
			String shareequity = this.getRequest().getParameter("gudongInfo");
			String fundIntentJsonData = this.getRequest().getParameter(
					"fundIntentJsonData");
			String slActualToChargeJsonData = this.getRequest().getParameter(
					"slActualToChargeJsonData");
			String isDeleteAllFundIntent = this.getRequest().getParameter(
					"isDeleteAllFundIntent");
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (null != shareequity && !"".equals(shareequity)) {
				String[] shareequityArr = shareequity.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
								.toJava(parser.nextValue(),
										EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
			slFundIntents = savejsonintent(fundIntentJsonData,
					gLGuaranteeloanProject, Short.parseShort("0"));
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			slActualToCharges = savejsoncharge(slActualToChargeJsonData,
					gLGuaranteeloanProject, Short.parseShort("0"));
			this.glGuaranteeloanProjectService.updateInfo(
					gLGuaranteeloanProject, person, enterprise, listES,
					customerBankRelationPerson, slFundIntents,
					slActualToCharges, isDeleteAllFundIntent);

			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
			if(!"".equals(customerRepaymentPlanData) && customerRepaymentPlanData != null) {
				String[] bankLoanProgramArr = customerRepaymentPlanData.split("@");
				
				for(int i=0; i<bankLoanProgramArr.length; i++) {
					String str = bankLoanProgramArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					GlCustomerRepaymentplan glCustomerRepaymentplan = (GlCustomerRepaymentplan)JSONMapper.toJava(parser.nextValue(),GlCustomerRepaymentplan.class);
					glCustomerRepaymentplan.setProjId(projectId);
					if(null==glCustomerRepaymentplan.getRepaymentPlanId() || "".equals(glCustomerRepaymentplan.getRepaymentPlanId())){
						glCustomerRepaymentplanService.save(glCustomerRepaymentplan);
					}else{
						GlCustomerRepaymentplan orgglCustomerRepaymentplan=glCustomerRepaymentplanService.get(glCustomerRepaymentplan.getRepaymentPlanId());
						BeanUtil.copyNotNullProperties(orgglCustomerRepaymentplan, glCustomerRepaymentplan);
						glCustomerRepaymentplanService.save(orgglCustomerRepaymentplan);
					}
				}
			}
			if("0".equals(flag)) {
				List<GlBankGuaranteemoney> list=glBankGuaranteemoneyService.getbyprojId(projectId);
		          if(list.size()!=0){
		        	  GlBankGuaranteemoney orgGlBankGuaranteemoney=list.get(0);
						BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
		        	  glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);
		          }else{
		        	  glBankGuaranteemoneyService.save(glBankGuaranteemoney);
		          }
			}else if ("1".equals(flag)) {
				List<GlBankGuaranteemoney> a = new ArrayList<GlBankGuaranteemoney>();
				a = glBankGuaranteemoneyService.getbyprojId(projectId);
				if (glBankGuaranteemoney.getBankGuaranteeId() == null
						&& a.size() == 0) {
					glBankGuaranteemoney.setProjectId(projectId);
					glBankGuaranteemoney.setBusinessType("Guarantee");
					glBankGuaranteemoney.setOperationType(operationType);
					glBankGuaranteemoney.setUnfreezeMoney(glBankGuaranteemoney
							.getFreezeMoney());
					if (null != glBankGuaranteemoney.getAccountId()) {
						GlAccountBankCautionmoney glAccountBankCautionmoney = glAccountBankCautionmoneyService
								.get(glBankGuaranteemoney.getAccountId());
						glBankGuaranteemoney
								.setGlAccountBankId(glAccountBankCautionmoney
										.getParentId());
					}
					glBankGuaranteemoneyService.save(glBankGuaranteemoney);

					GlAccountRecord glAccountRecord = new GlAccountRecord(); // 冻结记录
					glAccountRecord.setCautionAccountId(glBankGuaranteemoney
							.getAccountId());
					glAccountRecord.setCapitalType(3);
					glAccountRecord.setGlAccountBankId(glBankGuaranteemoney
							.getGlAccountBankId());
					glAccountRecord.setOprateDate(glBankGuaranteemoney
							.getFreezeDate());
					glAccountRecord.setOprateMoney(glBankGuaranteemoney
							.getFreezeMoney());
					glAccountRecord.setProjectId(glBankGuaranteemoney
							.getProjectId());
					AppUser user = ContextUtil.getCurrentUser();
					glAccountRecord.setHandlePerson(user.getFullname());
					glAccountRecordService.save(glAccountRecord);

					GlAccountRecord glAccountRecord1 = new GlAccountRecord(); // 释放记录
					glAccountRecord1.setCautionAccountId(glBankGuaranteemoney
							.getAccountId());
					glAccountRecord1.setCapitalType(4);
					glAccountRecord1.setGlAccountBankId(glBankGuaranteemoney
							.getGlAccountBankId());
					glAccountRecord1.setOprateDate(glBankGuaranteemoney
							.getFreezeDate());
					glAccountRecord1.setOprateMoney(glBankGuaranteemoney
							.getFreezeMoney());
					glAccountRecord1.setProjectId(glBankGuaranteemoney
							.getProjectId());
					glAccountRecord1.setHandlePerson(user.getFullname());
					glAccountRecordService.save(glAccountRecord1);
					glBankGuaranteemoneyService.saveguaranteemoneyAccount(
							glBankGuaranteemoney.getGlAccountBankId(),
							glBankGuaranteemoney.getAccountId());

				} else {
					GlBankGuaranteemoney orgGlBankGuaranteemoney;
					if (a.size() != 0) {
						orgGlBankGuaranteemoney = glBankGuaranteemoneyService
								.getbyprojId(projectId).get(0);
					} else {
						orgGlBankGuaranteemoney = glBankGuaranteemoneyService
								.get(glBankGuaranteemoney.getBankGuaranteeId());
					}
					int same = 0;
					Long accountId = orgGlBankGuaranteemoney.getAccountId();
					Long glAccountBankId = orgGlBankGuaranteemoney
							.getGlAccountBankId();
					if (!glBankGuaranteemoney.getAccountId().equals(
							orgGlBankGuaranteemoney.getAccountId())) {// 改变了保证金账户
						same = 1;
					}

					if (null != glBankGuaranteemoney.getAccountId()) {
						GlAccountBankCautionmoney glAccountBankCautionmoney = glAccountBankCautionmoneyService
								.get(glBankGuaranteemoney.getAccountId());
						glBankGuaranteemoney
								.setGlAccountBankId(glAccountBankCautionmoney
										.getParentId());
					}
					BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney,
							glBankGuaranteemoney);
					orgGlBankGuaranteemoney
							.setUnfreezeMoney(orgGlBankGuaranteemoney
									.getFreezeMoney());
					glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);

					GlAccountRecord glAccountRecord = new GlAccountRecord();// 冻结记录
					List<GlAccountRecord> GlAccountRecords = glAccountRecordService
							.getbyprojectIdcapitalType(projectId, 3);
					if (GlAccountRecords.size() != 0) {
						glAccountRecord = GlAccountRecords.get(0);
					}
					glAccountRecord.setCautionAccountId(orgGlBankGuaranteemoney
							.getAccountId());
					glAccountRecord.setCapitalType(3);
					glAccountRecord.setGlAccountBankId(orgGlBankGuaranteemoney
							.getGlAccountBankId());
					glAccountRecord.setOprateDate(orgGlBankGuaranteemoney
							.getFreezeDate());
					glAccountRecord.setOprateMoney(orgGlBankGuaranteemoney
							.getFreezeMoney());
					glAccountRecord.setProjectId(orgGlBankGuaranteemoney
							.getProjectId());
					AppUser user = ContextUtil.getCurrentUser();
					glAccountRecord.setHandlePerson(user.getFullname());
					glAccountRecordService.save(glAccountRecord);

					GlAccountRecord glAccountRecord1 = new GlAccountRecord();// 释放记录
					List<GlAccountRecord> GlAccountRecord1s = glAccountRecordService
							.getbyprojectIdcapitalType(projectId, 4);
					if (GlAccountRecord1s.size() != 0) {
						glAccountRecord1 = GlAccountRecord1s.get(0);
					}
					glAccountRecord1.setCautionAccountId(orgGlBankGuaranteemoney
							.getAccountId());
					glAccountRecord1.setCapitalType(4);
					glAccountRecord1.setGlAccountBankId(orgGlBankGuaranteemoney
							.getGlAccountBankId());
					glAccountRecord1.setOprateDate(orgGlBankGuaranteemoney
							.getFreezeDate());
					glAccountRecord1.setOprateMoney(orgGlBankGuaranteemoney
							.getFreezeMoney());
					glAccountRecord1.setProjectId(orgGlBankGuaranteemoney
							.getProjectId());
					glAccountRecord1.setHandlePerson(user.getFullname());
					glAccountRecordService.save(glAccountRecord1);
					if (same == 1) {
						glBankGuaranteemoneyService.saveguaranteemoneyAccount(
								glAccountBankId, accountId);
					}
					glBankGuaranteemoneyService.saveguaranteemoneyAccount(
							orgGlBankGuaranteemoney.getGlAccountBankId(),
							orgGlBankGuaranteemoney.getAccountId());

				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("企业贷款 -常规流程-尽职调查更新信息-出错:" + e.getMessage());
		}
		sb.append("[{\"bankId\":\"" + customerBankRelationPerson.getId()
				+ "\"}]");
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	*/}

	public String saveprojectinfo() {
		StringBuffer sb = new StringBuffer("{success:true,result:");
		try {
			String shareequity = this.getRequest().getParameter("gudongInfo");
			String fundIntentJsonData = this.getRequest().getParameter(
					"fundIntentJsonData");
			String slActualToChargeJsonData = this.getRequest().getParameter(
					"slActualToChargeJsonData");
			String isDeleteAllFundIntent = this.getRequest().getParameter(
					"isDeleteAllFundIntent");
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (null != shareequity && !"".equals(shareequity)) {
				String[] shareequityArr = shareequity.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
								.toJava(parser.nextValue(),
										EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			String activityName = this.getRequest()
					.getParameter("activityName"); // 项目信息保存要先判断节点与签合同的先后
			String runId = this.getRequest().getParameter("runId");
			String runId1 = this.getRequest().getParameter("runId1");
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();

			boolean ischeck = creditProjectService.isBeforeTask(Long
					.valueOf(runId), activityName);
			if (ischeck == true) {
				slFundIntents = savejsonintent(fundIntentJsonData,
						gLGuaranteeloanProject, Short.parseShort("0"));
			} else {
				slFundIntents = savejsonintent(fundIntentJsonData,
						gLGuaranteeloanProject, Short.parseShort("1"));
			}
			slActualToCharges = savejsoncharge(slActualToChargeJsonData,
					gLGuaranteeloanProject, Short.parseShort("0"));
			this.glGuaranteeloanProjectService.updateInfo(
					gLGuaranteeloanProject, person, enterprise, listES,
					customerBankRelationPerson, slFundIntents,
					slActualToCharges, isDeleteAllFundIntent);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("企业贷款 -常规流程-尽职调查更新信息-出错:" + e.getMessage());
		}
		sb.append("[{\"bankId\":\"" + customerBankRelationPerson.getId()
				+ "\"}]");
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	public List<SlFundIntent> savejsonintent(String fundIntentJson,
			GLGuaranteeloanProject slSmallloanProject, Short flag) {
		
		List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
		if (null != fundIntentJson && !"".equals(fundIntentJson)) {

			String[] shareequityArr = fundIntentJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				
				try {
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
							.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(slSmallloanProject
							.getProjectId());
					SlFundIntent1.setProjectName(slSmallloanProject
							.getProjectName());
					SlFundIntent1.setProjectNumber(slSmallloanProject
							.getProjectNumber());

					if (null == SlFundIntent1.getFundIntentId()) {

						BigDecimal lin = new BigDecimal(0.00);

						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());

						}
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(flag);
						SlFundIntent1.setBusinessType("Guarantee");
						slFundIntents.add(SlFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());

						}
						SlFundIntent slFundIntent2 = slFundIntentService
								.get(SlFundIntent1.getFundIntentId());
						if (slFundIntent2.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slFundIntent2,
									SlFundIntent1);
							slFundIntent2.setBusinessType("Guarantee");
							slFundIntent2.setIsCheck(flag);
							slFundIntents.add(slFundIntent2);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return slFundIntents;
	}

	public List<SlActualToCharge> savejsoncharge(
			String slActualToChargeJsonData,
			GLGuaranteeloanProject slSmallloanProject, Short flag) {
		List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
		if (null != slActualToChargeJsonData
				&& !"".equals(slActualToChargeJsonData)) {

			String[] shareequityArr = slActualToChargeJsonData.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				String[] strArr = str.split(",");
				String typestr = "";
				if (strArr.length == 7) {
					typestr = strArr[1];
				} else {
					typestr = strArr[0];
				}
				String typeId = "";
				String typename = "";
				if (typestr.endsWith("\"") == true) {
					typename = typestr.substring(typestr.indexOf(":") + 2,
							typestr.length() - 1);
				} else {
					typeId = typestr.substring(typestr.indexOf(":") + 1,
							typestr.length());
				}
				SlPlansToCharge slPlansToCharge = new SlPlansToCharge();

				if (!typename.equals("")) {

					List<SlPlansToCharge> list = slPlansToChargeService
							.getAll();
					int k = 0;
					for (SlPlansToCharge p : list) {
						if (p.getName().equals(typename)
								&& p.getBusinessType().equals("Guarantee")) {
							k++;
						}
					}

					if (k == 0) {
						slPlansToCharge.setName(typename);
						slPlansToCharge.setIsType(1);
						slPlansToCharge.setIsValid(0);
						slPlansToCharge.setBusinessType("Guarantee");
						slPlansToChargeService.save(slPlansToCharge);
						if (strArr.length == 9) {
							str = "{";
							for (int j = 0; j <= strArr.length - 2; j++) {
								if (j != 1) {
									str = strArr[j] + ",";
								}
							}
							str = str + strArr[strArr.length - 1];

						} else {
							str = "{";
							for (int j = 1; j <= strArr.length - 2; j++) {
								str = str + strArr[j] + ",";
							}
							str = str + strArr[strArr.length - 1];
						}
					}
				} else {
					long typeid = Long.parseLong(typeId);
					slPlansToCharge = slPlansToChargeService.get(typeid);

				}

				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper
							.toJava(parser.nextValue(), SlActualToCharge.class);

					slActualToCharge.setProjectId(slSmallloanProject
							.getProjectId());
					slActualToCharge.setProjectName(slSmallloanProject
							.getProjectName());
					slActualToCharge.setProjectNumber(slSmallloanProject
							.getProjectNumber());
					slActualToCharge.setCompanyId(slSmallloanProject.getCompanyId());
					slActualToCharge.setPlanChargeId(slPlansToCharge
							.getPlansTochargeId());
					if (null == slActualToCharge.getActualChargeId()) {

						slActualToCharge.setAfterMoney(new BigDecimal(0));
						slActualToCharge.setFlatMoney(new BigDecimal(0));
						if (slActualToCharge.getIncomeMoney().equals(
								new BigDecimal(0.00))) {
							slActualToCharge.setNotMoney(slActualToCharge
									.getPayMoney());
						} else {
							slActualToCharge.setNotMoney(slActualToCharge
									.getIncomeMoney());
						}
						slActualToCharge.setBusinessType("Guarantee");
						slActualToCharge.setIsCheck(flag);
						slActualToCharges.add(slActualToCharge);
					} else {

						SlActualToCharge slActualToCharge1 = slActualToChargeService
								.get(slActualToCharge.getActualChargeId());
						if (slActualToCharge1.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slActualToCharge1,
									slActualToCharge);
							if (slActualToCharge1.getIncomeMoney().equals(
									new BigDecimal(0.00))) {
								slActualToCharge1.setNotMoney(slActualToCharge
										.getPayMoney());
							} else {
								slActualToCharge1.setNotMoney(slActualToCharge
										.getIncomeMoney());
							}
							slActualToCharge1.setPlanChargeId(slPlansToCharge
									.getPlansTochargeId());
							slActualToCharge1.setBusinessType("Guarantee");
							slActualToCharge1.setIsCheck(flag);
							slActualToCharges.add(slActualToCharge1);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		}

		return slActualToCharges;
	}

	/**
	 * 贷中监管保存数据
	 * 
	 * @return
	 */
	public String updateSupervise() {
	//	List<GlProcreditSupervision> glProcreditSupervisions = new ArrayList<GlProcreditSupervision>();
		
		String glProcreditSupervisionStr = this.getRequest().getParameter(
				"slSuperviseDatas");
		String fundIntentJsonData = this.getRequest().getParameter(
				"fundIntentJsonData");
		String slActualToChargeJsonData = this.getRequest().getParameter(
				"slActualToChargeJsonData");

		if (null != glProcreditSupervisionStr
				&& !"".equals(glProcreditSupervisionStr)) {

			String[] glProcreditSupervisionArr = glProcreditSupervisionStr
					.split("@");
			for (int i = 0; i < glProcreditSupervisionArr.length; i++) {
				String str = glProcreditSupervisionArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
//					GlProcreditSupervision glSupervision = (GlProcreditSupervision) JSONMapper
//							.toJava(parser.nextValue(),
//									GlProcreditSupervision.class);
//					glProcreditSupervisions.add(glSupervision);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// 保存款项计划
		List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
		slFundIntents = savejsonintent(fundIntentJsonData,
				gLGuaranteeloanProject, Short.parseShort("0"));

		// 保存杂项费用
		List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
		slActualToCharges = savejsoncharge(slActualToChargeJsonData,
				gLGuaranteeloanProject, Short.parseShort("0"));
		Long projectId = Long.valueOf(this.getRequest().getParameter(
				"projectId"));
		this.glGuaranteeloanProjectService.updateSupervise(projectId,
				 slFundIntents, slActualToCharges);

		String taskId = this.getRequest().getParameter("task_id");
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments
				&& !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}

		StringBuffer sb = new StringBuffer("{success:true}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	public String askfor() {
		Long projectId = Long.valueOf(this.getRequest().getParameter(
				"projectId"));
		String slActualToChargeJsonData = this.getRequest().getParameter(
				"money_plan");
		String fundIntentJsonData = this.getRequest().getParameter(
				"intent_plan");
		String categoryIds = this.getRequest().getParameter("categoryIds");
		// Short flag = Short.valueOf(this.getRequest().getParameter("flag"));

		gLGuaranteeloanProject = this.glGuaranteeloanProjectService
				.get(projectId);
		List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
		slFundIntents = savejsonintent(fundIntentJsonData,
				gLGuaranteeloanProject, Short.parseShort("0"));
		List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
		slActualToCharges = savejsoncharge(slActualToChargeJsonData,
				gLGuaranteeloanProject, Short.parseShort("0"));

		this.glGuaranteeloanProjectService.askForProject(projectId,
				slActualToCharges, slFundIntents, glSuperviseRecord,
				categoryIds);
		StringBuffer sb = new StringBuffer("{success:true}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	public String listSuperviseRecord() {
		Long projectId = Long.valueOf(this.getRequest().getParameter(
				"projectId"));
		Type type = new TypeToken<List<GLSuperviseRecord>>() {
		}.getType();
		List<GLSuperviseRecord> list = new ArrayList<GLSuperviseRecord>();
		list = glSuperviseRecordService.getListByProjectId(projectId);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(list.size()).append(",result:");
		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String deleteSuperviseRecord() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				glSuperviseRecordService.remove(new Long(id));
			}
		}
		StringBuffer sb = new StringBuffer("{success:true}");
		setJsonString(sb.toString());
		return SUCCESS;

	}

	public String list() {

		int size = 0;
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("createDate", "DESC");
		List<GLGuaranteeloanProject> list = new ArrayList<GLGuaranteeloanProject>();
		list = glGuaranteeloanProjectService.getAll(filter);
		size = glGuaranteeloanProjectService.getAll().size();
		for (GLGuaranteeloanProject l : list) {
			if (l.getProjectMoney() != null) {
				l.setProjectMoney(l.getProjectMoney().divide(
						new BigDecimal(10000)));
			}
			if (l.getEarnestmoney() != null) {
				l.setEarnestmoney(l.getEarnestmoney().divide(
						new BigDecimal(10000)));
			}
			glGuaranteeloanProjectService.evict(l);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("createDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "createDate" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();

		return SUCCESS;

	}
	
	public String getInfo(){
		String projectId = this.getRequest().getParameter("glProjectId"); //项目ID
		//String type=this.getRequest().getParameter("type");  //	 
		String taskId = this.getRequest().getParameter("glTaskId");  //	任务ID 
		
		
		
		if(projectId!=null&&!"".equals(projectId)){
			String mineName = "";
			String appUsersOfA = "";
			String appUsersOfB = "";
			try{
				GLGuaranteeloanProject project = this.glGuaranteeloanProjectService.get(Long.valueOf(projectId));
			    Map<String, Object> map = new HashMap<String, Object>();
				if(project.getMineType().equals("company_ourmain")){
					//mineName = this.companyMainService.get(project.getMineId()).getCorName();
					//getMineId 为 organization 下的 id 原来是 企业主体下的id
					if("true".equals(AppUtil.getSystemIsGroupVersion())){
						mineName = this.organizationService.get(project.getMineId()).getOrgName();
					}else{
						mineName = this.companyMainService.get(project.getMineId()).getCorName();
					}
					
				}else if (project.getMineType().equals("person_ourmain")){
					mineName = this.slPersonMainService.get(project.getMineId()).getName();
				}
				if(project.getOppositeType().equals("company_customer")){ //企业
					Enterprise enterprise1= enterpriseService.getById(project.getOppositeID().intValue());
					Person p = this.personService.getById(enterprise1.getLegalpersonid());
					if(null != enterprise1.getHangyeType()) {
						if(null!=areaDicService.getById(enterprise1.getHangyeType())){
							enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
						}
					}
		            map.put("enterprise", enterprise1);
		            map.put("person", p);
				}else if(project.getOppositeType().equals("person_customer")){ //个人
					Person p = this.personService.getById(project.getOppositeID().intValue());
					map.put("person", p);
				}
				if(mineName==null){
					mineName="";
				}
				StringBuffer textBuffer = new StringBuffer (mineName); 
				project.setMineName(textBuffer.toString());
				if(null!=project.getLoanRate() && project.getLoanRate().doubleValue()==0){
					project.setLoanRate(null);
				}
				map.put("gLGuaranteeloanProject", project);
				
				if(null!=project.getAppUserIdOfA() && !"".equals(project.getAppUserIdOfA())){
					String [] appstr=project.getAppUserIdOfA().split(",");
					Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
					for(AppUser s:userSet){
						appUsersOfA+=s.getFamilyName()+",";
					}
					appUsersOfA = appUsersOfA.substring(0, appUsersOfA.length()-1);
				}
				if(null!=project.getAppUserIdOfB() && !"".equals(project.getAppUserIdOfB())){
					String [] appstr=project.getAppUserIdOfB().split(",");
					Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
					for(AppUser s:userSet){
						appUsersOfB+=s.getFamilyName()+",";
					}
					appUsersOfB = appUsersOfB.substring(0, appUsersOfB.length()-1);
				}
				/*if(null!=project.getBankOpinion() && !"".equals(project.getBankOpinion()))
				{
					map.put("opinion", project.getBankOpinion());
				}*/
				String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());;
				String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
				String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
				map.put("appUsersOfA",appUsersOfA);
				map.put("appUsersOfB",appUsersOfB);
				map.put("mineName",mineName);
				map.put("businessType", project.getBusinessType());
				map.put("businessTypeKey",businessTypeKey);
				map.put("operationTypeKey",operationTypeKey);
				map.put("definitionTypeKey",definitionTypeKey);
				map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getDescription());
				map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
				map.put("oppositeTypeKey",this.dictionaryIndependentService.getByDicKey(project.getOppositeType()).get(0).getItemValue());
				if(null!=taskId && !"".equals(taskId)){
					ProcessForm pform = processFormService.getByTaskId(taskId);
					if(pform==null){
						pform = creditProjectService.getCommentsByTaskId(taskId);
					}
					if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
						map.put("comments", pform.getComments());
					}
				}
				CustomerBankRelationPerson bankRelationPerson = new CustomerBankRelationPerson();
				if(null!=project.getBankId() && !project.getBankId().equals("")){
					String bankName = "";
					String fenBankName = "";
					bankRelationPerson = this.customerBankRelationPersonService.getById(project.getBankId());
					List<AreaDic> list1 = customerBankRelationPersonService.findBank(bankRelationPerson.getBankid());
					bankName = list1.get(0).getText();
					if(null!=bankRelationPerson.getFenbankid() && !bankRelationPerson.getFenbankid().equals("")){  
						List<AreaDic> list2 = customerBankRelationPersonService.findBank(bankRelationPerson.getFenbankid());
						if(null!=list2 &&list2.size()>0){
							fenBankName=list2.get(0).getRemarks();
							bankName = fenBankName;
							if(bankName==""){
								fenBankName = list2.get(0).getText();
								bankName = bankName+"_"+fenBankName;
							}
						}
					}
					bankRelationPerson.setBankName(bankName);
				}
			
				map.put("customerBankRelationPerson", bankRelationPerson);
				StringBuffer buff = new StringBuffer("{success:true,data:");
				JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
				buff.append(json.serialize(map));
				buff.append("}");
				jsonString=buff.toString();
			}catch (Exception e) {
				e.printStackTrace();
				logger.error("ProjectAction:"+e.getMessage());
			}
		}
		return SUCCESS;
	}
	public String saveSupervisonInfo(){
		try{
			GlobalSupervisemanage orgGlobalSupervisemanage=globalSupervisemanageService.get(superviseManageId);
			BeanUtil.copyNotNullProperties(orgGlobalSupervisemanage, globalSupervisemanage);
			globalSupervisemanageService.save(orgGlobalSupervisemanage);
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//银行信息登记节点的保存
	public String saveBankInforegist(){
		String fundIntentJson=this.getRequest().getParameter("fundIntentJson");
	List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
	if(null != fundIntentJson && !"".equals(fundIntentJson)) {
		GLGuaranteeloanProject project=glGuaranteeloanProjectService.get(gLGuaranteeloanProject.getProjectId());
		String[] fundIntentJsonArr = fundIntentJson.split("@");
		
		for(int i=0; i<fundIntentJsonArr.length; i++) {
			String str = fundIntentJsonArr[i];
			JSONParser parser = new JSONParser(new StringReader(str));
			try{
				SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
				slFundIntent.setProjectId(project.getProjectId());
				slFundIntent.setProjectName(project.getProjectName());
				slFundIntent.setProjectNumber(project.getProjectNumber());
				slFundIntent.setCompanyId(project.getCompanyId());
				if(null==slFundIntent.getFundIntentId()){
					BigDecimal lin = new BigDecimal(0.00);

					if (slFundIntent.getIncomeMoney().compareTo(lin) == 0) {
						slFundIntent.setNotMoney(slFundIntent
								.getPayMoney());
					} else {
						slFundIntent.setNotMoney(slFundIntent
								.getIncomeMoney());

					}
					slFundIntent.setAfterMoney(new BigDecimal(0));
					slFundIntent.setAccrualMoney(new BigDecimal(0));
					slFundIntent.setFlatMoney(new BigDecimal(0));
					Short isvalid = 0;
					slFundIntent.setIsValid(isvalid);
					slFundIntent.setIsCheck(Short.parseShort("1"));
					slFundIntent.setBusinessType("Guarantee");
					slFundIntentService.save(slFundIntent);
				}else{
					BigDecimal lin = new BigDecimal(0.00);
					if (slFundIntent.getIncomeMoney().compareTo(lin) == 0) {
						slFundIntent.setNotMoney(slFundIntent
								.getPayMoney());
					} else {
						slFundIntent.setNotMoney(slFundIntent
								.getIncomeMoney());

					}
					SlFundIntent slFundIntent2 = slFundIntentService
							.get(slFundIntent.getFundIntentId());
					if (slFundIntent2.getAfterMoney().compareTo(
							new BigDecimal(0)) == 0) {
						BeanUtil.copyNotNullProperties(slFundIntent2,
								slFundIntent);
						slFundIntent2.setBusinessType("Guarantee");
						slFundIntent2.setIsCheck(Short.parseShort("1"));
						
					}
					slFundIntentService.save(slFundIntent2);
				}
				setJsonString("{success:true}");
			
			} catch(Exception e) {
				e.printStackTrace();
				logger.error("ShareequityAction:"+e.getMessage());
			}
			
		}
	}
		// 保存款项计划
	/*	List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
			slFundIntents = savejsonintent(fundIntentJson,
					gLGuaranteeloanProject, Short.parseShort("1"));*/

			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		return SUCCESS;
	}

	public GLGuaranteeloanProject getgLGuaranteeloanProject() {
		return gLGuaranteeloanProject;
	}

	public void setgLGuaranteeloanProject(
			GLGuaranteeloanProject gLGuaranteeloanProject) {
		this.gLGuaranteeloanProject = gLGuaranteeloanProject;
	}

	public CustomerBankRelationPerson getCustomerBankRelationPerson() {
		return customerBankRelationPerson;
	}

	public void setCustomerBankRelationPerson(
			CustomerBankRelationPerson customerBankRelationPerson) {
		this.customerBankRelationPerson = customerBankRelationPerson;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public GLSuperviseRecord getGlSuperviseRecord() {
		return glSuperviseRecord;
	}

	public void setGlSuperviseRecord(GLSuperviseRecord glSuperviseRecord) {
		this.glSuperviseRecord = glSuperviseRecord;
	}

	public short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public short getBmStatus() {
		return bmStatus;
	}

	public void setBmStatus(short bmStatus) {
		this.bmStatus = bmStatus;
	}

	public Boolean getIsGrantedShowAllProjects() {
		return isGrantedShowAllProjects;
	}

	public void setIsGrantedShowAllProjects(Boolean isGrantedShowAllProjects) {
		this.isGrantedShowAllProjects = isGrantedShowAllProjects;
	}

	public String getBankLoanData() {
		return bankLoanData;
	}

	public void setBankLoanData(String bankLoanData) {
		this.bankLoanData = bankLoanData;
	}

	public String getCustomerRepaymentData() {
		return customerRepaymentData;
	}

	public void setCustomerRepaymentData(String customerRepaymentData) {
		this.customerRepaymentData = customerRepaymentData;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public GlBankGuaranteemoney getGlBankGuaranteemoney() {
		return glBankGuaranteemoney;
	}

	public void setGlBankGuaranteemoney(
			GlBankGuaranteemoney glBankGuaranteemoney) {
		this.glBankGuaranteemoney = glBankGuaranteemoney;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCustomerRepaymentPlanData() {
		return customerRepaymentPlanData;
	}

	public void setCustomerRepaymentPlanData(String customerRepaymentPlanData) {
		this.customerRepaymentPlanData = customerRepaymentPlanData;
	}

	public Long getSuperviseManageId() {
		return superviseManageId;
	}

	public void setSuperviseManageId(Long superviseManageId) {
		this.superviseManageId = superviseManageId;
	}
	
}
