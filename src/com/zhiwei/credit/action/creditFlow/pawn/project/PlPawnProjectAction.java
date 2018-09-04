package com.zhiwei.credit.action.creditFlow.pawn.project;
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

import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.project.PawnFundIntentListPro;
import com.zhiwei.credit.core.project.PawnFundIntentListProtw;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.meeting.SlConferenceRecordDao;
import com.zhiwei.credit.dao.system.DictionaryDao;
import com.zhiwei.credit.dao.system.GlobalTypeDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnContinuedManagment;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnRedeemManagement;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnVastMaragement;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.VPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.common.EnterpriseShareequityService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnContinuedManagmentService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnRedeemManagementService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnVastMaragementService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.TaskSignDataService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PlPawnProjectAction extends BaseAction{
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private SlConferenceRecordDao slConferenceRecordDao;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private GlobalTypeDao globalTypeDao;
	@Resource
	private DictionaryDao dictionaryDao;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private EnterpriseShareequityService enterpriseShareequityService;
	@Resource
	private EnterpriseDao enterpriseDao;
	@Resource
	private PersonDao personDao;
	@Resource
	private PawnContinuedManagmentService pawnContinuedManagmentService;
	@Resource
	private PawnRedeemManagementService pawnRedeemManagementService;
	@Resource
	private PawnVastMaragementService pawnVastMaragementService;
	private PlPawnProject plPawnProject;
	
	private Long projectId;
	private Enterprise enterprise;
	private Person person;
	private SlConferenceRecord slConferenceRecord;
	private Boolean isGrantedShowAllProjects;
	private Short projectStatus; 
	
	
	public Boolean getIsGrantedShowAllProjects() {
		return isGrantedShowAllProjects;
	}

	public void setIsGrantedShowAllProjects(Boolean isGrantedShowAllProjects) {
		this.isGrantedShowAllProjects = isGrantedShowAllProjects;
	}

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public SlConferenceRecord getSlConferenceRecord() {
		return slConferenceRecord;
	}

	public void setSlConferenceRecord(SlConferenceRecord slConferenceRecord) {
		this.slConferenceRecord = slConferenceRecord;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public PlPawnProject getPlPawnProject() {
		return plPawnProject;
	}

	public void setPlPawnProject(PlPawnProject plPawnProject) {
		this.plPawnProject = plPawnProject;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlPawnProject> list= plPawnProjectService.getAll(filter);
		
		Type type=new TypeToken<List<PlPawnProject>>(){}.getType();
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
				plPawnProjectService.remove(new Long(id));
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
		PlPawnProject plPawnProject=plPawnProjectService.get(projectId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plPawnProject));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plPawnProject.getProjectId()==null){
			plPawnProjectService.save(plPawnProject);
		}else{
			PlPawnProject orgPlPawnProject=plPawnProjectService.get(plPawnProject.getProjectId());
			try{
				BeanUtil.copyNotNullProperties(orgPlPawnProject, plPawnProject);
				plPawnProjectService.save(orgPlPawnProject);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String getInfo(){
		String projectId=this.getRequest().getParameter("slProjectId"); //项目ID
		String taskId=this.getRequest().getParameter("slTaskId");  //	任务ID 
		String runId=this.getRequest().getParameter("runId");
		PlPawnProject project=this.plPawnProjectService.get(Long.valueOf(projectId));
		Map<String, Object> map = new HashMap<String, Object>();
		
	
			Person p = new Person();
			//if判断是企业客户 elseif是个人客户
		
			if(project.getOppositeType().equals("company_customer")){
			
				Enterprise enterprise1= enterpriseService.getById(project.getOppositeID().intValue());
				if(enterprise1.getLegalpersonid()!=null){
					try {
						p=this.personService.getById(enterprise1.getLegalpersonid());
					} catch (Exception e) {
						e.printStackTrace();
					}
					map.put("person", p);
				}
				if(null != enterprise1.getHangyeType()) {
					  try {
						if(null!=areaDicService.getById(enterprise1.getHangyeType())){ 
							  enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
						  }
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
	            map.put("enterprise", enterprise1);
	           
			}else if(project.getOppositeType().equals("person_customer")) {
				
				try {
					p=this.personService.getById(project.getOppositeID().intValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
				map.put("person", p);
				
			}
		
		
		List<ProcessRun> runList = processRunService.getByProcessNameProjectId(project.getProjectId(), project.getBusinessType(), project.getFlowType());
		if(runList!=null&&runList.size()!=0){
			List<TaskSignData> tsdList =  taskSignDataService.getByRunId(runList.get(0).getRunId());
			if(tsdList.size()>0) {
				map.put("isOnline", true);
			}else {
				map.put("isOnline", false);
			}
		}
		
		String appuers="";
		String appUserId="";
		if(null!=project.getAppUserId()){
			String [] appstr=project.getAppUserId().split(",");
			Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				appuers+=s.getFullname()+",";
				appUserId+=s.getUserId()+",";
			}
		}
		if(appuers.length()>0){
			appuers=appuers.substring(0, appuers.length()-1);
		}
		if(appUserId.length()>0){
			appUserId=appUserId.substring(0,appUserId.length()-1);
		}
		String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
		String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
		String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
		//map.put("times", times);
		map.put("plPawnProject", project); 
		map.put("businessType", project.getBusinessType());
		map.put("appuerName",appuers);
		map.put("businessTypeKey",businessTypeKey);
		map.put("operationTypeKey",operationTypeKey);
		map.put("definitionTypeKey",definitionTypeKey);
		map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getName());
		map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
		if(null!=taskId && !"".equals(taskId)){
			ProcessForm pform = processFormService.getByTaskId(taskId);
			if(pform==null){
				pform = creditProjectService.getCommentsByTaskId(taskId);
			}
			if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
				map.put("comments", pform.getComments());
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	public String update() {
		try {
  		    StringBuffer sb = new StringBuffer("{success:true");
			String shareequity = this.getRequest().getParameter("gudongInfo");
			String degree = this.getRequest().getParameter("degree");
			
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>(); // 股东信息
			if (null != shareequity && !"".equals(shareequity)) {
				String[] shareequityArr = shareequity.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
					listES.add(enterpriseShareequity);
				}
			}
			
		
			if (null != degree && !"".equals(degree)) {
				plPawnProject.setAppUserId(degree);
			}
		/*	String productName=this.getRequest().getParameter("plPawnProject.productName");
			if(null!=productName && !"".equals(productName)){
				boolean flag1 = StringUtil.isNumeric(productName);
				GlobalType globalType = globalTypeDao.getByNodeKey("productName").get(0);
				if(flag1==false){
					Dictionary dic = new Dictionary();
					dic.setItemValue(productName);
					dic.setItemName(globalType.getTypeName());
					dic.setProTypeId(globalType.getProTypeId());
					dic.setDicKey(productName);
					dic.setStatus("0");
					dictionaryDao.save(dic);
					plPawnProject.setProductName(dic.getDicId());
				}else{
					Dictionary cd = dictionaryDao.get(Long.valueOf(productName));
					if (null == cd) {
						Dictionary dic = new Dictionary();
						dic.setItemValue(productName);
						dic.setItemName(globalType.getTypeName());
						dic.setProTypeId(globalType.getProTypeId());
						dic.setDicKey(productName);
						dic.setStatus("0");
						dictionaryDao.save(dic);
						plPawnProject.setProductName(dic.getDicId());
					}
				}
			}*/
			Integer flag = this.plPawnProjectService.updatePawnInfo(plPawnProject, person, enterprise, listES,sb);
			if (flag == 0) {
				
			} else {
				String taskId = this.getRequest().getParameter("task_id");
				String comments = this.getRequest().getParameter("comments");
				if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
					this.creditProjectService.saveComments(taskId, comments);
				}
			}
			sb.append("}");
			setJsonString(sb.toString());
		} catch (Exception e) {
			logger.error("调查评估节点-保存（更新）信息出错:" + e.getMessage());
		}
		return SUCCESS;
	}
	public String updateConferenceRecord(){
		try{
			PlPawnProject persistent=this.plPawnProjectService.get(plPawnProject.getProjectId());
			BeanUtil.copyNotNullProperties(persistent, plPawnProject);
			plPawnProjectService.save(persistent);
			if(slConferenceRecord.getConforenceId()==null){
				slConferenceRecordDao.save(slConferenceRecord);
			}else{
				SlConferenceRecord orgSlConferenceRecord=slConferenceRecordDao.get(slConferenceRecord.getConforenceId());
				BeanUtil.copyNotNullProperties(orgSlConferenceRecord, slConferenceRecord);
				slConferenceRecordDao.save(orgSlConferenceRecord);
			}
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
			jsonString="{success:true,conforenceId:"+slConferenceRecord.getConforenceId()+"}";
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String updatePaymentConfirmation(){
		String fundIntentJsonData = this.getRequest().getParameter("fundIntentJson");
	
		PlPawnProject persistent = plPawnProjectService.get(Long.valueOf(projectId));
		List<SlFundIntent> oldList = slFundIntentService.getByProjectId(projectId, persistent.getBusinessType());
		for(SlFundIntent sfi : oldList) {
			if(sfi.getAfterMoney().compareTo(new BigDecimal(0))==0) {
				slFundIntentService.remove(sfi);
			}
		}
		
		if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
			String[] shareequityArr = fundIntentJsonData.split("@");
			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(persistent.getProjectId());
					SlFundIntent1.setProjectName(persistent.getProjectName());
					SlFundIntent1.setProjectNumber(persistent.getProjectNumber());

					if (null == SlFundIntent1.getFundIntentId()) {

						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(Short.valueOf("0"));
						SlFundIntent1.setBusinessType(persistent.getBusinessType());
						SlFundIntent1.setCompanyId(persistent.getCompanyId());
						slFundIntentService.save(SlFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
						if (slFundIntent2.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
							SlFundIntent1.setBusinessType(persistent.getBusinessType());
							SlFundIntent1.setCompanyId(persistent.getCompanyId());
							SlFundIntent1.setIsCheck(Short.valueOf("0"));
							slFundIntentService.merge(SlFundIntent1);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		persistent.setStartDate(plPawnProject.getStartDate());
		persistent.setIntentDate(plPawnProject.getIntentDate());
		this.plPawnProjectService.merge(persistent);
		String taskId = this.getRequest().getParameter("task_id");
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}
		return SUCCESS;
	}
	public String updatePlanRecognition(){
		String fundIntentJsonData = this.getRequest().getParameter("fundIntentJson");
	
		PlPawnProject persistent = plPawnProjectService.get(Long.valueOf(projectId));
		List<SlFundIntent> oldList = slFundIntentService.getByProjectId(projectId, persistent.getBusinessType());
		for(SlFundIntent sfi : oldList) {
			if(sfi.getAfterMoney().compareTo(new BigDecimal(0))==0) {
				slFundIntentService.remove(sfi);
			}
		}
		
		if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
			String[] shareequityArr = fundIntentJsonData.split("@");
			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(persistent.getProjectId());
					SlFundIntent1.setProjectName(persistent.getProjectName());
					SlFundIntent1.setProjectNumber(persistent.getProjectNumber());

					if (null == SlFundIntent1.getFundIntentId()) {

						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(Short.valueOf("0"));
						SlFundIntent1.setBusinessType(persistent.getBusinessType());
						SlFundIntent1.setCompanyId(persistent.getCompanyId());
						slFundIntentService.save(SlFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
						if (slFundIntent2.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
							SlFundIntent1.setBusinessType(persistent.getBusinessType());
							SlFundIntent1.setCompanyId(persistent.getCompanyId());
							SlFundIntent1.setIsCheck(Short.valueOf("0"));
							slFundIntentService.merge(SlFundIntent1);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		String taskId = this.getRequest().getParameter("task_id");
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}
		return SUCCESS;
	}
	public String updateRelevantFormalities(){
		String phnumber=this.getRequest().getParameter("plPawnProject.phnumber");
		PlPawnProject project=plPawnProjectService.get(projectId);
		if(null!=phnumber && !"".equals(phnumber)){
			project.setPhnumber(phnumber);
			plPawnProjectService.save(project);
		}
		String taskId = this.getRequest().getParameter("task_id");
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}
		return SUCCESS;
	}
	
	public String getComprehensiveCost(){
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		List<SlFundIntent> list=new ArrayList<SlFundIntent>();
			if(AppUtil.getInterest().equals("0")){
				list = PawnFundIntentListPro.getFundIntentDefaultList(
					"Pawn", "singleInterest",
					0,
					plPawnProject.getPayaccrualType(),
					new BigDecimal(0),
					plPawnProject.getProjectMoney(),
					sd.format(plPawnProject.getStartDate()),
					plPawnProject.getIntentDate()==null?null:sd.format(plPawnProject.getIntentDate()),
					plPawnProject.getMonthFeeRate().divide(new BigDecimal(30),10,BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100)),
					new BigDecimal(0),
					plPawnProject.getPayintentPeriod(),
					"2",
					null,
					plPawnProject.getDayOfEveryPeriod(),
					"no",null,1,null);
			}else{
				list = PawnFundIntentListProtw.getFundIntentDefaultList(
						"Pawn", "singleInterest",
						0,
						plPawnProject.getPayaccrualType(),
						new BigDecimal(0),
						plPawnProject.getProjectMoney(),
						sd.format(plPawnProject.getStartDate()),
						plPawnProject.getIntentDate()==null?null:sd.format(plPawnProject.getIntentDate()),
						plPawnProject.getMonthFeeRate().divide(new BigDecimal(30),10,BigDecimal.ROUND_HALF_UP).divide(BigDecimal.valueOf(100)),
						new BigDecimal(0),
						plPawnProject.getPayintentPeriod(),
						"2",
						null,
						plPawnProject.getDayOfEveryPeriod(),
						"no",null,1,null);
			}
			BigDecimal money=new BigDecimal(0);
			if(null!=list && list.size()>0){
				for(SlFundIntent s:list){
					if(s.getFundType().equals("pawnServiceMoney")){
						money=money.add(s.getIncomeMoney());
					}
				}
			}
			jsonString="{success : true,money:"+money.setScale(2,BigDecimal.ROUND_HALF_UP)+"}";
		return SUCCESS;
	}
	public String projectList(){
		String userIdsStr = "";
		PagingBean pb = new PagingBean(start, limit);
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if (!isGrantedShowAllProjects && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
		Short[] projectStatuses = null;
		switch (projectStatus) {
			case 1:
				projectStatuses = new Short[] {1,4};
				break;
			case 0:
				projectStatuses = new Short[] {0};
				break;
			case 2:
				projectStatuses = new Short[] {};
				break;
			case 3:
				projectStatuses = new Short[] {3};
				break;
			case 4:
				projectStatuses = new Short[] {4};
				break;
			case 5:
				projectStatuses = new Short[] {5};
				break;
			case 6:
				projectStatuses = new Short[] {6};
				break;
			case 7:
				projectStatuses = new Short[] {0,1,3,4,5,6};
				break;
		}
		List<VPawnProject> list = plPawnProjectService.getProjectList(
				userIdsStr, projectStatuses, pb, getRequest());
		// int counts =
		// vSmallloanProjectService.getProjectList(userIdsStr,projectStatuses,
		// null, getRequest()).size();
		List<VPawnProject> clist = plPawnProjectService.getProjectList(
				userIdsStr, projectStatuses, null, getRequest());
		int ls = clist.size();

		for (VPawnProject vp : list){
			String appuers = "";
			if (null != vp.getAppUserId()) {
				String[] appstr = vp.getAppUserId().split(",");
				Set<AppUser> userSet = this.appUserService
						.getAppUserByStr(appstr);
				for (AppUser s : userSet) {
					appuers = appuers + s.getFamilyName() + ",";
				}
			}
			if (appuers.length() > 0) {
				appuers = appuers.substring(0, appuers.length() - 1);
			}
			vp.setAppUserName(appuers);

			// 获取当前所处任务的处理人
			String executor = "";// 任务执行人
			Set<AppUser> appExecutor = jbpmService.getNodeHandlerUsers(vp
					.getDefId(), vp.getActivityName());
			for (AppUser su : appExecutor) {
				executor = executor + su.getFamilyName() + ",";
			}
			if (executor.length() > 0) {
				executor = executor.substring(0, executor.length() - 1);
			}
			vp.setExecutor(executor);
			List<PawnContinuedManagment> plist=pawnContinuedManagmentService.getListByProjectId(vp.getProjectId(), vp.getBusinessType(), null);
			if(null!=plist && plist.size()>0){
				PawnContinuedManagment con=plist.get(plist.size()-1);
				vp.setContinueCount(plist.size());
			}
			List<PawnRedeemManagement> rlist=pawnRedeemManagementService.getListByProjectId(vp.getProjectId(), vp.getBusinessType());
			if(null!=rlist && rlist.size()>0){
				PawnRedeemManagement redeem=rlist.get(rlist.size()-1);
				vp.setRedeemDate(redeem.getRedeemDate());
			}
			List<PawnVastMaragement> vlist=pawnVastMaragementService.getListByProjectId(vp.getProjectId(), vp.getBusinessType());
			if(null!=vlist && vlist.size()>0){
				PawnVastMaragement vast=vlist.get(vlist.size()-1);
				vp.setVastDate(vast.getVastDate());
			}
		}

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(ls).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String updateAll(){
		try{
			  StringBuffer sb = new StringBuffer("{success:true");
				String shareequity = this.getRequest().getParameter("gudongInfo");
				String degree = this.getRequest().getParameter("degree");
				String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
				List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>(); // 股东信息
				if (null != shareequity && !"".equals(shareequity)) {
					String[] shareequityArr = shareequity.split("@");
					for (int i = 0; i < shareequityArr.length; i++) {
						String str = shareequityArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);
					}
				}
				PlPawnProject persistent=this.plPawnProjectService.get(plPawnProject.getProjectId());
				if (persistent.getOppositeType().equals("company_customer")) {
					if (listES.size() > 0) {
						for (int i = 0; i < listES.size(); i++) {
							EnterpriseShareequity es = listES.get(i);
							if (es.getId() == null) {
								es.setEnterpriseid(enterprise.getId());
								this.enterpriseShareequityService.save(es);
							} else {
								EnterpriseShareequity esPersistent = this.enterpriseShareequityService
										.load(es.getId());
								BeanUtils.copyProperties(es, esPersistent,
										new String[] { "id", "enterpriseid" });
								this.enterpriseShareequityService.merge(esPersistent);
							}
						}
					}
					Enterprise ePersistent = this.enterpriseDao
							.getById(enterprise.getId());
					ePersistent.setEnterprisename(enterprise.getEnterprisename());
					ePersistent.setArea(enterprise.getArea());
					ePersistent.setShortname(enterprise.getShortname());
					ePersistent.setHangyeType(enterprise.getHangyeType());
					ePersistent.setOrganizecode(enterprise.getOrganizecode());
					ePersistent.setCciaa(enterprise.getCciaa());
					ePersistent.setTelephone(enterprise.getTelephone());
					ePersistent.setPostcoding(enterprise.getPostcoding());
					ePersistent.setRootHangYeType(enterprise.getRootHangYeType());
		
					// 更新法人信息
					if (null != person.getId() && person.getId() != 0) {
						Person pPersistent = this.personDao.getById(person
								.getId());
						pPersistent.setName(person.getName());
						pPersistent.setSex(person.getSex());
						pPersistent.setCardtype(person.getCardtype());
						pPersistent.setCardnumber(person.getCardnumber());
						pPersistent.setCellphone(person.getCellphone());
						pPersistent.setSelfemail(person.getSelfemail());
						pPersistent.setCompanyId(ContextUtil.getLoginCompanyId());
						this.personDao.merge(pPersistent);
					} else {
						Long currentUserId = ContextUtil.getCurrentUserId();
						Person p = new Person();
						p.setId(null);
						p.setCreater(ContextUtil.getCurrentUser().getFullname());
						p.setBelongedId(currentUserId.toString());
						p.setCreaterId(currentUserId);
						p.setCreatedate(new Date());
						p.setCompanyId(ContextUtil.getLoginCompanyId());
						p.setName(person.getName());
						p.setSex(person.getSex());
						p.setCardtype(person.getCardtype());
						p.setCardnumber(person.getCardnumber());
						p.setCellphone(person.getCellphone());
						p.setSelfemail(person.getSelfemail());
		
						this.personDao.save(p);
						ePersistent.setLegalpersonid(p.getId());
					}
					sb.append(",legalpersonid:" + ePersistent.getLegalpersonid());
					enterpriseDao.merge(ePersistent);
		
				} else if (persistent.getOppositeType().equals("person_customer")) {
					Person pPersistent = this.personDao.getById(person
							.getId());
					pPersistent.setMarry(person.getMarry());
					pPersistent.setName(person.getName());
					pPersistent.setSex(person.getSex());
					pPersistent.setCardtype(person.getCardtype());
					pPersistent.setCardnumber(person.getCardnumber());
					pPersistent.setCellphone(person.getCellphone());
					pPersistent.setPostcode(person.getPostcode());
					pPersistent.setSelfemail(person.getSelfemail());
					pPersistent.setPostaddress(person.getPostaddress());
					this.personDao.merge(pPersistent);
					
				}
				if (null != degree && !"".equals(degree)) {
					plPawnProject.setAppUserId(degree);
				}
				BeanUtil.copyNotNullProperties(persistent, plPawnProject);
				this.plPawnProjectService .save(persistent);
				if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
					String[] fundIntentJsonArr = fundIntentJsonData.split("@");
					for (int i = 0; i < fundIntentJsonArr.length; i++) {
						String str = fundIntentJsonArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						try {
							SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
							SlFundIntent1.setProjectId(persistent.getProjectId());
							SlFundIntent1.setProjectName(persistent.getProjectName());
							SlFundIntent1.setProjectNumber(persistent.getProjectNumber());
							SlFundIntent1.setBusinessType(persistent.getBusinessType());
							SlFundIntent1.setCompanyId(persistent.getCompanyId());
							BigDecimal lin = new BigDecimal(0.00);
							if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
								SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
							} else {
								SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
							}
							SlFundIntent1.setAfterMoney(new BigDecimal(0));
							SlFundIntent1.setAccrualMoney(new BigDecimal(0));
							SlFundIntent1.setFlatMoney(new BigDecimal(0));
							Short isvalid = 0;
							SlFundIntent1.setIsValid(isvalid);
							if(persistent.getProjectStatus()==0){
								SlFundIntent1.setIsCheck(Short.valueOf("1"));
							}else{
								SlFundIntent1.setIsCheck(Short.valueOf("0"));
							}
							
							if (null == SlFundIntent1.getFundIntentId()) {
								slFundIntentService.save(SlFundIntent1);
							} else {
								SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
								if (slFundIntent2.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
									BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
									slFundIntentService.merge(SlFundIntent1);
								}
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				sb.append("}");
			jsonString=sb.toString();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
