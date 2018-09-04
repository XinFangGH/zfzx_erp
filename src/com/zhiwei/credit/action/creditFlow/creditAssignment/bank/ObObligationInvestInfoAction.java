package com.zhiwei.credit.action.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.ObObligationProject;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.VObligationInvestInfo;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObObligationInvestInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.project.ObObligationProjectService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.project.VObligationInvestInfoService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.system.AppUserService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class ObObligationInvestInfoAction extends BaseAction{
	@Resource
	private ObObligationInvestInfoService obObligationInvestInfoService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private ObObligationProjectService obObligationProjectService;
	@Resource
	private VObligationInvestInfoService vObligationInvestInfoService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	private ObObligationInvestInfo obObligationInvestInfo;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ObObligationInvestInfo getObObligationInvestInfo() {
		return obObligationInvestInfo;
	}

	public void setObObligationInvestInfo(ObObligationInvestInfo obObligationInvestInfo) {
		this.obObligationInvestInfo = obObligationInvestInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ObObligationInvestInfo> list= obObligationInvestInfoService.getAll(filter);
		
		Type type=new TypeToken<List<ObObligationInvestInfo>>(){}.getType();
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
				obObligationInvestInfoService.remove(new Long(id));
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
		ObObligationInvestInfo obObligationInvestInfo=obObligationInvestInfoService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(obObligationInvestInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(obObligationInvestInfo.getId()==null){
			obObligationInvestInfoService.save(obObligationInvestInfo);
		}else{
			ObObligationInvestInfo orgObObligationInvestInfo=obObligationInvestInfoService.get(obObligationInvestInfo.getId());
			try{
				BeanUtil.copyNotNullProperties(orgObObligationInvestInfo, obObligationInvestInfo);
				obObligationInvestInfoService.save(orgObObligationInvestInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	//根据id查出来已经保存的信息
	public String listInvest(){
		String obligationId =this.getRequest().getParameter("obligationId");
		List<ObObligationInvestInfo> list= obObligationInvestInfoService.getInfoByobObligationProjectId(Long.valueOf(obligationId), "1");
		if(list!=null&&list.size()>0){
			for(ObObligationInvestInfo temp:list){
				CsInvestmentperson  person =csInvestmentpersonService.get(temp.getInvestMentPersonId());
				temp.setInvestPersonName(person.getInvestName());
				temp.setAviliableMoney(new BigDecimal(0));
			}
		}
		Type type=new TypeToken<List<ObObligationInvestInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	//保存投资人的购买债权的list
	public String saveinvestMentList(){
		try{
			BigDecimal compar =new BigDecimal(0);
			String  msg="";
			String investmentData =this.getRequest().getParameter("investMentList");
			String obligationId =this.getRequest().getParameter("obligationId");
			AppUser user =ContextUtil.getCurrentUser();
			ObObligationProject obObligationProject =null;
			ObObligationInvestInfo  systemInvest =null;//默认查出当前债权产品的系统公司债权记录（）
			if(obligationId!=null&&!"".equals(obligationId)){
				obObligationProject =obObligationProjectService.get(Long.valueOf(obligationId));
				List<ObObligationInvestInfo>  list =obObligationInvestInfoService.getInfoByobObligationProjectId(Long.valueOf(obligationId),"0");
				if(list!=null&&list.size()>0){
					systemInvest =list.get(0);
				}
				if(investmentData!=null&&!"".equals(investmentData)){
					//准备保存投资人信息
					String[] investmentInfoArr = investmentData.split("@");
					for (int i = 0; i < investmentInfoArr.length; i++){
						String str = investmentInfoArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						ObObligationInvestInfo bo = (ObObligationInvestInfo) JSONMapper.toJava(parser.nextValue(), ObObligationInvestInfo.class);
						if (bo.getId() == null) {
							bo.setObligationAccrual(obObligationProject.getAccrual());
							bo.setCompanyId(obObligationProject.getCompanyId());
							bo.setObligationName(obObligationProject.getObligationName());
							bo.setCreatorId(user.getUserId());
							bo.setInvestObligationStatus(Short.valueOf("0"));
							bo.setSystemInvest(Short.valueOf("1"));
							bo.setObligationId(Long.valueOf(obligationId));
							bo.setFundIntentStatus(Short.valueOf("0"));
							obObligationInvestInfoService.save(bo);
						} else {
							ObObligationInvestInfo info =obObligationInvestInfoService.get(bo.getId());
							BeanUtil.copyNotNullProperties(info, bo);
							obObligationInvestInfoService.merge(info);
						}
					}
				
				}
				List<ObObligationInvestInfo>  listinvests =obObligationInvestInfoService.getInfoByobObligationProjectId(Long.valueOf(obligationId),"1");
				if(listinvests.size()>0&&listinvests!=null){
					BigDecimal totalInvest =new BigDecimal(0);
					Long Quotient =0l;
					BigDecimal rate =new BigDecimal(0);//默认公司债权还剩的比例
					for(ObObligationInvestInfo temp :listinvests){
						totalInvest=totalInvest.add(temp.getInvestMoney());
						Quotient=Quotient+temp.getInvestQuotient();
					}
					if(totalInvest.compareTo(compar)==0){
						
					}else if(totalInvest.compareTo(obObligationProject.getProjectMoney())==0||totalInvest.compareTo(obObligationProject.getProjectMoney())==1){
						systemInvest.setInvestMoney(new BigDecimal(0));
						systemInvest.setInvestQuotient(Long.valueOf("0"));
						systemInvest.setInvestRate(new BigDecimal(0));
						systemInvest.setInvestObligationStatus(Short.valueOf("2"));
						obObligationInvestInfoService.merge(systemInvest);
						obObligationProject.setObligationStatus(Short.valueOf("1"));//债权产品匹配成功后改变债权产品状态
						obObligationProjectService.merge(obObligationProject);
					}else if(totalInvest.compareTo(obObligationProject.getProjectMoney())==-1){//债权产品没有匹配完成
						BigDecimal avaible =obObligationProject.getProjectMoney().subtract(totalInvest);
						systemInvest.setInvestMoney(avaible);
						systemInvest.setInvestQuotient(Long.valueOf(obObligationProject.getTotalQuotient().toString())-Quotient);
						systemInvest.setInvestRate(avaible.divide(obObligationProject.getProjectMoney(),4,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)));
						obObligationInvestInfoService.merge(systemInvest);
					}
				}
				msg="保存成功!";
			}else{
				msg="系统出错，请联系管理员。【债权表的主键Id丢失】";
			}
			StringBuffer sb = new StringBuffer("{success:true,msg:");
			sb.append(msg);
			sb.append("}");
			setJsonString(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("保存债权匹配投资人出错"+e.getMessage());
			String msg ="系统出错，请联系管理员。";
			StringBuffer sb = new StringBuffer("{success:false,msg:");
			sb.append(msg);
			sb.append("}");
			setJsonString(sb.toString());
		}
		return SUCCESS;
	}
	//根据投资人的id查出来债权项目
	public String listInvestPersonByPersonId(){
		String obligationState =this.getRequest().getParameter("obligationState");//用老判断是查出全部的结果集  还是没有默认的金帆客户
		String investPersonId =this.getRequest().getParameter("investId");
		List<VObligationInvestInfo>  list=null;
		int size =0;
		if(investPersonId!=null&&!"".equals(investPersonId)&&!"null".equals(investPersonId)){
			list=vObligationInvestInfoService.getlistInvestPersonByPersonId(investPersonId,obligationState,start,limit);
		}
		if(list!=null&&list.size()>0){
			size =list.size();
		}
		Type type=new TypeToken<List<VObligationInvestInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list,type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
		
	}
	//查询视图 查出来除系统默认外的债权投资人(债权到期情况使用方法)
	public String listInvestPerson(){
		String obligationState =this.getRequest().getParameter("obligationState");//用老判断是查出全部的结果集  还是没有默认的金帆客户
		String obligationName =this.getRequest().getParameter("obligationName");
		String investName =this.getRequest().getParameter("investName");
		String investStartDate =this.getRequest().getParameter("investStartDate");
		String investEndDate =this.getRequest().getParameter("investEndDate");
		List<VObligationInvestInfo>  list =vObligationInvestInfoService.getInvestList(obligationState,obligationName,investName,investStartDate,investEndDate,start,limit);
		Type type=new TypeToken<List<VObligationInvestInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()>0?list.size():"0").append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list,type));
		buff.append("}");
		jsonString=buff.toString();
		return  SUCCESS;
	}
	//查询视图 查出来所有的债权投资人(债权库查询使用方法)
	public String listAllInvest(){
		String obligationState =this.getRequest().getParameter("obligationState");//用老判断是查出全部的结果集  还是没有默认的金帆客户
		String obligationName =this.getRequest().getParameter("obligationName");
		String investName =this.getRequest().getParameter("investName");
		String payintentPeriod =this.getRequest().getParameter("payintentPeriod");
		String investEndDate =this.getRequest().getParameter("investEndDate");
		List<VObligationInvestInfo>  list =vObligationInvestInfoService.getAllList(obligationState,obligationName,investName,payintentPeriod,investEndDate,start,limit);
		Type type=new TypeToken<List<VObligationInvestInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()>0?list.size():"0").append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list,type));
		buff.append("}");
		jsonString=buff.toString();
		return  SUCCESS;
	}
	//vm中查询业务受理时的债权信息
	public String getInfo(){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			if(null!=id){
				ObObligationInvestInfo investInfo=this.obObligationInvestInfoService.get(id);
				if(null!=investInfo){
					if(null!=investInfo.getCreatorId()){
						AppUser user=appUserService.get(investInfo.getCreatorId());
						if(null!=user){
							map.put("creatorName", user.getFullname());
						}
					}
					map.put("obObligationInvestInfo", investInfo);
				}
				ObObligationProject project=obObligationProjectService.get(investInfo.getObligationId());
				List<ObObligationInvestInfo>  listInfo=obObligationInvestInfoService.getInfoByobObligationProjectId(project.getId(),"0");
				if(!listInfo.isEmpty()){
					ObObligationInvestInfo oi=listInfo.get(0);
					if(null!=oi){	
						project.setUnmappingMoney(oi.getInvestMoney());//设置未匹配金额
						BigDecimal unmappingMoney=project.getProjectMoney().subtract(oi.getInvestMoney());
						project.setMappingMoney(unmappingMoney);//设置已匹配金额
						project.setUnmappingQuotient(oi.getInvestQuotient());//设置未匹配份额
					}
				}
				map.put("obObligationProject", project);
			}
			StringBuffer buff = new StringBuffer("{success:true,data:");
			JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(map));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		/*String projectId=this.getRequest().getParameter("taskId"); //项目ID
		String taskId=this.getRequest().getParameter("task_id"); //流程任务Id
		ObObligationInvestInfo project=this.obObligationInvestInfoService.get(Long.valueOf(projectId));
		Map<String, Object> map = new HashMap<String, Object>();
		String mineName="";
		String financeServiceMineName="";
		String managementConsultingMineName="";
		
		
		if(null!=project.getInvestMentPersonId() && !"".equals(project.getInvestMentPersonId() )){
			if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
				if(project.getManagementConsultingMineType().equals("company_ourmain")){
					managementConsultingMineName=this.companyMainService.get(project.getManagementConsultingMineId()).getCorName();
				}else{	
					managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
				}
			}
		}
		
		try{
			if(project.getMineType().equals("company_ourmain")){
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
				mineName=this.companyMainService.get(project.getMineId()).getCorName();
			}else if (project.getMineType().equals("person_ourmain")){
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
				mineName=this.slPersonMainService.get(project.getMineId()).getName();
			}
			Person p = new Person();
			//if判断是企业客户 elseif是个人客户
			Short sub=0;
			if(project.getOppositeType().equals("company_customer")){
				sub =0;
				Enterprise enterprise1= enterpriseServ.getEnterpriseById(project.getOppositeID().intValue());
				if(enterprise1.getLegalpersonid()!=null){
					p=this.personManagerService.queryPersonEntity(enterprise1.getLegalpersonid());
					map.put("person", p);
				}
				if(null != enterprise1.getHangyeType()) {
					  if(null!=areaDicServ.getById(enterprise1.getHangyeType())){ 
						  enterprise1.setHangyeName(areaDicServ.getById(enterprise1.getHangyeType()).getText());
					  }
				}
	            map.put("enterprise", enterprise1);
	           
			}else if(project.getOppositeType().equals("person_customer")) {
				sub = 1;
				p=this.personManagerService.queryPersonEntity(project.getOppositeID().intValue());
				map.put("person", p);
				if(null!=p){
					if(null!=p.getId()){
						if(null!=p.getMarry() && p.getMarry()==317){
		        			Spouse spouse=spouseService.getByPersonId(p.getId());
		        			map.put("spouse", spouse);
		        		}
					}
				}
			}
		 if(null!=project.getOppositeID()){
			 List<EnterpriseBank> list=enterpriseBankServ.getBankList(project.getOppositeID().intValue(), sub, Short.valueOf("0"));
	         if(null!=list && list.size()>0){
        		EnterpriseBank bank=list.get(0);
        		map.put("enterpriseBank", bank);
        	}
		 }
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction:"+e.getMessage());
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
		if(null!=project.getAppUserId()){
			String [] appstr=project.getAppUserId().split(",");
			Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				appuers+=s.getFullname()+",";
			}
		}
		if(appuers.length()>0){
			appuers=appuers.substring(0, appuers.length()-1);
		}
		StringBuffer textBuffer = new StringBuffer (mineName); 
		project.setMineName(textBuffer.toString());
		project.setAppUsers(appuers);
		project.setAppUserName(appuers);
		String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");  //	 
		if(null!=slSuperviseRecordId &&  !"".equals(slSuperviseRecordId)){
			SlSuperviseRecord slSuperviseRecord=this.slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
			map.put("slSuperviseRecord", slSuperviseRecord);
		}
		List<SlFundIntent> list=slFundIntentService.getByProjectId3(project.getProjectId(), project.getBusinessType(), "principalLending");
		if(null!=list && list.size()>0){
			SlFundIntent f=list.get(0);
			map.put("startDate", f.getIntentDate());
		}
		//Long times = processFormService.getActvityExeTimes(runId, "slnExaminationArrangement");//是否经过审贷会
		String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
		String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
		String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
		//map.put("times", times);
		map.put("slSmallloanProject", project); 
		map.put("mineName", mineName);
		map.put("financeServiceMineName", financeServiceMineName); 
		map.put("managementConsultingMineName", managementConsultingMineName); 
		map.put("businessType", project.getBusinessType());
		map.put("isAheadPay", project.getIsAheadPay());
		map.put("businessTypeKey",businessTypeKey);
		map.put("operationTypeKey",operationTypeKey);
		map.put("definitionTypeKey",definitionTypeKey);
		map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getName());
		map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
		try {
			if(null!=project.getLoanLimit() && !project.getLoanLimit().equals("")){
				DictionaryIndependent dic=this.dictionaryIndependentService.getByDicKey(project.getLoanLimit()).get(0);
				if(null!=dic){
					map.put("smallLoanTypeKey", dic.getItemValue());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null!=taskId && !"".equals(taskId)){
			ProcessForm pform = processFormService.getByTaskId(taskId);
			if(pform==null){
				pform = creditProjectService.getCommentsByTaskId(taskId);
			}
			if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
				map.put("comments", pform.getComments());
			}
		}
		
		//查询债权信息
		ObObligationProject op=obObligationProjectService.getInfo(projectId);
		if(null!=op){
			map.put("obObligationProject", op);
		}
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();*/
		return SUCCESS;
	}
	//给投资人添加债权时保存债权信息
	public String saveInvestObligation(){
		String msg="";
		try{
			String obligationId =this.getRequest().getParameter("obligationId");
			ObObligationInvestInfo ob=new ObObligationInvestInfo();
			String degree =this.getRequest().getParameter("degree");
			obObligationInvestInfo.setFundIntentStatus(Short.valueOf("0"));
			ObObligationProject obObligationProject =obObligationProjectService.get(Long.valueOf(obligationId));
			obObligationInvestInfo.setCompanyId(obObligationProject.getCompanyId());
			if(degree!=null&&!"".equals(degree)){
				AppUser appuser =appUserService.get(Long.valueOf(degree));
				obObligationInvestInfo.setCreatorId(Long.valueOf(degree));
			}
			if(obObligationInvestInfo.getId()!=null&&!"".equals(obObligationInvestInfo.getId())){
				ObObligationInvestInfo  persient=obObligationInvestInfoService.get(obObligationInvestInfo.getId());
				BeanUtil.copyNotNullProperties(persient, obObligationInvestInfo);
				obObligationInvestInfoService.merge(persient);
				ob=persient;
			}else{
				obObligationInvestInfoService.save(obObligationInvestInfo);
				ob=obObligationInvestInfo;
				
			}
			//生成款项信息
			//拿到生成并修改过的款项信息
			String slFundIntentList =this.getRequest().getParameter("slFundIntentList");
			if (null != slFundIntentList && !"".equals(slFundIntentList)) {
				String[] shareequityArr = slFundIntentList.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setObligationInfoId(obObligationInvestInfo.getId());
					SlFundIntent1.setInvestPersonId(obObligationInvestInfo.getInvestMentPersonId());
					SlFundIntent1.setObligationId(Long.valueOf(obligationId));
					SlFundIntent1.setCompanyId(obObligationInvestInfo.getCompanyId());
					if (null == SlFundIntent1.getFundIntentId()) {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						Short isvalid = 0;
						SlFundIntent1.setAfterMoney(lin);
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(Short.valueOf("0"));
						slFundIntentService.save(SlFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
						if(slFundIntent2 != null) {
							if (slFundIntent2.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
								BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
								SlFundIntent1.setIsCheck(Short.valueOf("0"));
								slFundIntentService.merge(SlFundIntent1);
							}
						}
					}
				}
			}
			//更新系统默认的债权信息（即更新金帆的债权信息和债权产品的状态）
			obObligationInvestInfoService.changeStatus(obligationId,1);
			//对应的一条账户交易信息以及更改账户总额(如果当时录入的债权时间是当天或者是之前的投资信息，则需要变更平台账户金额)
			Date currentDate =new Date();
			if(ob.getInvestStartDate().compareTo(currentDate) <0 ||ob.getInvestStartDate().compareTo(currentDate)==0){
				obObligationInvestInfoService.checkSlFundQulid(ob);
			}
			//obAccountDealInfoService.changeAccountDealInfo(ob,1);
			msg="操作信息：保存成功!";
		}catch(Exception e){
			e.printStackTrace();
			msg="操作信息：保存失败，请联系管理员!";
		}
		jsonString = "{success:true,msg:'" + msg + "'}";
		return SUCCESS;
	}
	//根据债权表的id   删除债权信息，并且删除债权信息的款项信息，以及修改系统默认的
	public String delInvestObligation(){
		//String obligationState =this.getRequest().getParameter("obligationState");//用老判断是查出全部的结果集  还是没有默认的金帆客户
		String  investObligationid=this.getRequest().getParameter("investObligationid");
		String investMentPersonId =this.getRequest().getParameter("investMentPersonId");
		String obligationId=this.getRequest().getParameter("obligationId");
		ObObligationInvestInfo ob=obObligationInvestInfoService.get(Long.valueOf(investObligationid));
		String msg ="债权已经支付了收益，不能删除债权信息！";
		//List<SlFundIntent> list =slFundIntentService;
		if(ob!=null&&!"".equals(ob)){
			if(ob.getFundIntentStatus()==0){
				obObligationInvestInfoService.remove(Long.valueOf(investObligationid));
				//删除款项信息
				List<SlFundIntent> list =slFundIntentService.getListByTreeId(Long.valueOf(obligationId),Long.valueOf(investMentPersonId),Long.valueOf(investObligationid));
				if(list!=null&&list.size()>0){
					for(SlFundIntent temp:list){
						slFundIntentService.remove(temp);
					}
				}
				//更新系统默认的债权信息（即更新金帆的债权信息和债权产品的状态）
				obObligationInvestInfoService.changeStatus(obligationId,0);
				//删除对应的一条账户交易信息以及更改账户总额
				obAccountDealInfoService.changeAccountDealInfo(ob,0);
				msg="已经撤销了这一条债权信息！";
			}else if(ob.getFundIntentStatus()==1){
				msg="已经回款中的债权不能撤销！";
			}else if(ob.getFundIntentStatus()==2){
				msg="已经结束的债权不能撤销！";
			}
			
		}
		jsonString = "{success:true,msg:'" + msg + "'}";
		return SUCCESS;
	}
}
