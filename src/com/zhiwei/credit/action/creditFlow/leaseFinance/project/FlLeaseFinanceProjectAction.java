package com.zhiwei.credit.action.creditFlow.leaseFinance.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.project.StatsPro;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;
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
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.VLeaseFinanceProjectService;
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
/**
 * 
 * @author 
 *
 */
public class FlLeaseFinanceProjectAction extends BaseAction{
	
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private SlCompanyMainService companyMainService;
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
	private VLeaseFinanceProjectService vLeaseFinanceProjectService;
	@Resource
	private SlConferenceRecordService slConferenceRecordService;//add by gaoqingrui  用于添加会议纪要	
	@Resource
	private GlobalSupervisemanageService globalSupervisemanageService;//add by gaoqingrui  贷后监管控制	
	
	
	private FlLeaseFinanceProject flLeaseFinanceProject;
	
	private Long projectId;
	private Long superviseManageId;
	private Person person;
	private Enterprise enterprise;
	private CustomerBankRelationPerson customerBankRelationPerson;
	private Boolean isGrantedShowAllProjects;
	private String businessType;
	
	private GlobalSupervisemanage globalSupervisemanage;
	
	private short projectStatus;
	private String taskId;
	
	
	

	
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public GlobalSupervisemanage getGlobalSupervisemanage() {
		return globalSupervisemanage;
	}

	public void setGlobalSupervisemanage(GlobalSupervisemanage globalSupervisemanage) {
		this.globalSupervisemanage = globalSupervisemanage;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public Boolean getIsGrantedShowAllProjects() {
		return isGrantedShowAllProjects;
	}

	public void setIsGrantedShowAllProjects(Boolean isGrantedShowAllProjects) {
		this.isGrantedShowAllProjects = isGrantedShowAllProjects;
	}

	public Long getSuperviseManageId() {
		return superviseManageId;
	}

	public void setSuperviseManageId(Long superviseManageId) {
		this.superviseManageId = superviseManageId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public FlLeaseFinanceProject getFlLeaseFinanceProject() {
		return flLeaseFinanceProject;
	}

	public void setFlLeaseFinanceProject(FlLeaseFinanceProject flLeaseFinanceProject) {
		this.flLeaseFinanceProject = flLeaseFinanceProject;
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

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<FlLeaseFinanceProject> list= flLeaseFinanceProjectService.getAll(filter);
		
		Type type=new TypeToken<List<FlLeaseFinanceProject>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				flLeaseFinanceProjectService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		FlLeaseFinanceProject flLeaseFinanceProject=flLeaseFinanceProjectService.get(projectId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(flLeaseFinanceProject));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(flLeaseFinanceProject.getProjectId()==null){
			flLeaseFinanceProjectService.save(flLeaseFinanceProject);
		}else{
			FlLeaseFinanceProject orgFlLeaseFinanceProject=flLeaseFinanceProjectService.get(flLeaseFinanceProject.getProjectId());
			try{
				BeanUtil.copyNotNullProperties(orgFlLeaseFinanceProject, flLeaseFinanceProject);
				flLeaseFinanceProjectService.save(orgFlLeaseFinanceProject);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	//获取项目信息
	public String getInfo(){
		String projectId = this.getRequest().getParameter("flProjectId"); //项目ID
		//String type=this.getRequest().getParameter("type");  //	 
		String taskId = this.getRequest().getParameter("flTaskId");  //	任务ID 
		
		if(projectId!=null&&!"".equals(projectId)){
			String mineName = "";
			String appUsersOfA = "";
			String appUsersOfB = "";
			try{
				FlLeaseFinanceProject project = this.flLeaseFinanceProjectService.get(Long.valueOf(projectId));
			    Map<String, Object> map = new HashMap<String, Object>();
			    Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
			    if(flag==false){
			    	if(project.getMineType().equals("company_ourmain")){
						mineName = this.companyMainService.get(project.getMineId()).getCorName();
						//getMineId 为 organization 下的 id 原来是 企业主体下的id
						//mineName = this.organizationService.get(project.getMineId()).getOrgName();
					}else if (project.getMineType().equals("person_ourmain")){
						mineName = this.slPersonMainService.get(project.getMineId()).getName();
					}
			    }else{
					//if(project.getMineType().equals("company_ourmain")){
						//mineName = this.companyMainService.get(project.getMineId()).getCorName();
						//getMineId 为 organization 下的 id 原来是 企业主体下的id
						mineName = this.organizationService.get(project.getMineId()).getOrgName();
					/*}else if (project.getMineType().equals("person_ourmain")){
						mineName = this.slPersonMainService.get(project.getMineId()).getName();
					}*/
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
//				if(null!=project.getLoanRate() && project.getLoanRate().doubleValue()==0){
//					project.setLoanRate(null);
//				}
				map.put("flLeaseFinanceProject", project);
				
				if(null!=project.getAppUserId() && !"".equals(project.getAppUserId())){
					String [] appstr=project.getAppUserId().split(",");
					Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
					for(AppUser s:userSet){
						appUsersOfA+=s.getFamilyName()+",";
					}
					appUsersOfA = appUsersOfA.substring(0, appUsersOfA.length()-1);
				}
/*				if(null!=project.getAppUserIdOfB() && !"".equals(project.getAppUserIdOfB())){
					String [] appstr=project.getAppUserIdOfB().split(",");
					Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
					for(AppUser s:userSet){
						appUsersOfB+=s.getFamilyName()+",";
					}
					appUsersOfB = appUsersOfB.substring(0, appUsersOfB.length()-1);
				}*/
				/*if(null!=project.getBankOpinion() && !"".equals(project.getBankOpinion()))
				{
					map.put("opinion", project.getBankOpinion());
				}*/
				String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());;
				String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
				String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
				String leasingTypeKey = creditProjectService.getGlobalTypeValue("leasingType", project.getBusinessType(), project.getProjectId());
				map.put("appUsersOfA",appUsersOfA);
//				map.put("appUsersOfB",appUsersOfB);
				map.put("mineName",mineName);
				map.put("businessType", project.getBusinessType());
				map.put("businessTypeKey",businessTypeKey);
				map.put("operationTypeKey",operationTypeKey);
				map.put("definitionTypeKey",definitionTypeKey);
				map.put("leasingTypeKey",leasingTypeKey);
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
/*				
 * 暂无bank
 * 				if(null!=project.getBankId() && !project.getBankId().equals("")){
					String bankName = "";
					String fenBankName = "";
					bankRelationPerson = this.bankRelationPersonService.queryPerson(project.getBankId());
					List<AreaDic> list1 = bankRelationPersonService.findBank(bankRelationPerson.getBankid());
					bankName = list1.get(0).getText();
					if(null!=bankRelationPerson.getFenbankid() && !bankRelationPerson.getFenbankid().equals("")){  
						List<AreaDic> list2 = bankRelationPersonService.findBank(bankRelationPerson.getFenbankid());
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
				}*/
				if(null != superviseManageId && !"".equals(superviseManageId)){
					String smId = this.getRequest().getParameter("superviseManageId");
					if(!"".equals(smId)) {
						Long id = Long.valueOf(smId);
						GlobalSupervisemanage ss = globalSupervisemanageService.get(id);
						map.put("globalSupervisemanage", ss);
					}
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
	public List<SlActualToCharge> savejsoncharge(
			String slActualToChargeJsonData,
			FlLeaseFinanceProject flLeaseFinanceProject, Short flag) {
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
								&& p.getBusinessType().equals("LeaseFinance")) {
							k++;
						}
					}

					if (k == 0) {
						slPlansToCharge.setName(typename);
						slPlansToCharge.setIsType(1);
						slPlansToCharge.setIsValid(0);
						slPlansToCharge.setBusinessType("LeaseFinance");
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

					slActualToCharge.setProjectId(flLeaseFinanceProject
							.getProjectId());
					slActualToCharge.setProjectName(flLeaseFinanceProject
							.getProjectName());
					slActualToCharge.setProjectNumber(flLeaseFinanceProject
							.getProjectNumber());

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
						slActualToCharge.setBusinessType("LeaseFinance");
						slActualToCharge.setCompanyId(flLeaseFinanceProject.getCompanyId());
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
							slActualToCharge1.setBusinessType("LeaseFinance");
							slActualToCharge1.setCompanyId(flLeaseFinanceProject.getCompanyId());
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
	public String update() {
		StringBuffer sb = new StringBuffer("{success:true");
		try {
			String shareequity = this.getRequest().getParameter("gudongInfo");//尽职调查的股东信息只可查看,不传该参数即可;该方法别处也调用,股东信息保留,
			String slActualToChargeJsonData = this.getRequest().getParameter("slActualToChargeJson");
			String fundIntentJson=this.getRequest().getParameter("fundIntentJson");
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
			if (null != fundIntentJson && !"".equals(fundIntentJson)) {
				
				String[] slFundIentJsonArr = fundIntentJson.split("@");
				int j=1;
				for (int i = 0; i < slFundIentJsonArr.length; i++) {
					String str = slFundIentJsonArr[i];
					
					JSONParser parser = new JSONParser(new StringReader(str));
					
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
					slFundIntents.add(SlFundIntent1);
				}
			}
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			slActualToCharges=savejsoncharge(slActualToChargeJsonData,flLeaseFinanceProject,Short.valueOf("0"));
			/*String mineIdStr = this.getRequest().getParameter("gLGuaranteeloanProject.mineId");
			
			//slActualToCharges = savejsoncharge(slActualToChargeJsonData,flLeaseFinanceProject, Short.parseShort("0"));
			
			//更新主体单位名称 add by gaoqingrui
			Long mineId = null;
			if(null!= mineIdStr&&!"".equals(mineIdStr)){
				mineId = Long.valueOf(mineIdStr);
				flLeaseFinanceProject.setMineId(mineId);
			}*/
			if("true".equals(AppUtil.getSystemIsGroupVersion())){
				flLeaseFinanceProject.setCompanyId(flLeaseFinanceProject.getMineId());//集团版本，我方主体为  分公司 修改之后companyId也会改变
			}
			
			this.flLeaseFinanceProjectService.updateInfo(
					flLeaseFinanceProject, person, enterprise, 
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
			logger.error("金融租赁流程尽职调查保存出错:" + e.getMessage());
		}
//		sb.append("[{\"bankId\":\"" + customerBankRelationPerson.getId()+ "\"}]");
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
	public String getIntentDate(){
		String startDate=this.getRequest().getParameter("startDate");
		String payAccrualType=this.getRequest().getParameter("payAccrualType");
		String payintentPeriodStr=this.getRequest().getParameter("payintentPeriod");
		String dayOfEveryPeriodStr=this.getRequest().getParameter("dayOfEveryPeriod");
		Integer payintentPeriod=null;
		if(null!=payintentPeriodStr && !"".equals(payintentPeriodStr)){
			payintentPeriod=Integer.parseInt(payintentPeriodStr);
		}
		Integer dayOfEveryPeriod=null;
		if(null!=dayOfEveryPeriodStr && !"".equals(dayOfEveryPeriodStr)){
			dayOfEveryPeriod=Integer.parseInt(dayOfEveryPeriodStr);
		}
		StringBuffer buff=new StringBuffer("{success:true,intentDate:'");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		Date intentDate=StatsPro.getLeaseIntentDate(startDate, payAccrualType, payintentPeriod, dayOfEveryPeriod);
		if(null!=intentDate){
			buff.append(sd.format(intentDate));
		}
		buff.append("'}");
		jsonString=buff.toString();
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
		List<VLeaseFinanceProject> list = vLeaseFinanceProjectService.getProjectList(userIdsStr, projectStatus, pb,getRequest());
		for(VLeaseFinanceProject vp : list) {
			String appuers = "";
			if(null != vp.getAppUserId()) {
				String [] appstr = vp.getAppUserId().split(",");
				Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
				for(AppUser s : userSet){
					appuers += s.getFullname()+",";
				}
			}
			if(appuers.length()>0) {
				appuers = appuers.substring(0,appuers.length() - 1);
			}
			vp.setAppUserIdValue(appuers);
			
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
		Type type = new TypeToken<List<VLeaseFinanceProject>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?0:list.size()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public String saveSupervisonInfo() {
		GlobalSupervisemanage ss = globalSupervisemanageService.get(globalSupervisemanage
				.getSuperviseManageId());
		ss.setSuperviseManager(globalSupervisemanage.getSuperviseManager());
		ss.setSuperviseManagerName(globalSupervisemanage.getSuperviseManagerName());
		ss.setSuperviseManageTime(globalSupervisemanage.getSuperviseManageTime());
		ss.setSuperviseManageMode(globalSupervisemanage.getSuperviseManageMode());
		ss.setSuperviseManageOpinion(globalSupervisemanage
				.getSuperviseManageOpinion());
		ss.setSuperviseManageRemark(globalSupervisemanage
				.getSuperviseManageRemark());
		globalSupervisemanageService.save(ss);
		return SUCCESS;
	}
	public String cancleSuperviseRecord() {
		try {
			globalSupervisemanageService.remove(Long.valueOf(this.getRequest()
					.getParameter("superviseId")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/*
	 * 更新违约处理信息   功能模块
	 * */
	public void startBreach() {

		String projectId = this.getRequest().getParameter("projectID_");
		String listed = this.getRequest().getParameter("listed_");
		String comments = this.getRequest().getParameter("comments");// 违约说明
		FlLeaseFinanceProject project = (FlLeaseFinanceProject)this.flLeaseFinanceProjectService.getProjectById(Long.valueOf(projectId)).get(0);
		boolean flag = this.flLeaseFinanceProjectService.startBreach(project,
				listed, comments);
		StringBuffer buff = null;
		if (flag) {
			buff = new StringBuffer("{success:true,'totalCounts':");
		} else {

			buff = new StringBuffer("{success:false,'totalCounts':");
		}
		buff.append(0);
		buff.append("}");
		com.zhiwei.credit.core.creditUtils.JsonUtil.responseJsonString(buff.toString());
	}
	/*
	 * 项目结项  功能模块
	 * */
	public String loanFinished() {

		if (businessType != null && !"".equals(businessType)
				&& projectId != null && !"".equals(projectId)) {
			FlLeaseFinanceProject project = flLeaseFinanceProjectService.get(projectId);
			project.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
			flLeaseFinanceProjectService.save(project);
			jsonString = "{success:true}";
		}else{
			jsonString = "{success:false}";
		}
		return SUCCESS;
	}
	
}
