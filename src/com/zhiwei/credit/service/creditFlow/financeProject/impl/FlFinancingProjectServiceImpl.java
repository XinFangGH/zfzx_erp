package com.zhiwei.credit.service.creditFlow.financeProject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.financeProject.FlFinancingProjectDao;
import com.zhiwei.credit.dao.customer.InvestPersonDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.util.ProjectActionUtil;

/**
 * 
 * @author 
 *
 */
public class FlFinancingProjectServiceImpl extends BaseServiceImpl<FlFinancingProject> implements FlFinancingProjectService{
	private FlFinancingProjectDao dao;
	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	@Resource
	private EnterpriseDao enterpriseDao;
	@Resource
	private PersonDao personDao;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private InvestPersonDao  investPersonDao;
	@Resource
	private CreditProjectService creditProjectService;
	
	public FlFinancingProjectServiceImpl(FlFinancingProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public Integer updateFinancingProject(FlowRunInfo flowRunInfo){
	    try{
			String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");
			String isPreposePayAccrual=flowRunInfo.getRequest().getParameter("isPreposePayAccrualCheck");
			String degree= flowRunInfo.getRequest().getParameter("degree");
			
			String flowOver = flowRunInfo.getRequest().getParameter("flowOver");//项目结束,更改项目状态。
	    	
			List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>();
	    	if(null != gudongInfo && !"".equals(gudongInfo)) {
				 String[] shareequityArr = gudongInfo.split("@");
				 for(int i=0; i<shareequityArr.length; i++) {
						String str = shareequityArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						try{
							EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
							listES.add(enterpriseShareequity);
						
						} catch(Exception e) {
							e.printStackTrace();
							logger.error("FlFinancingProjectServiceImpl："+e.getMessage());
						}
					}
			}
	    	
	    	FlFinancingProject project=new FlFinancingProject();
	        BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "flFinancingProject");
	        
	    	if(null!=degree && !"".equals(degree)){
	    		project.setAppUserId(degree);
	    	}
	    	
	    	FlFinancingProject persistent=dao.get(project.getProjectId());
	    	
	        BeanUtils.copyProperties(project, persistent,new String[] {"projectId","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","businessType","createDate","projectStatus"});
	      	
	        Long projectId = project.getProjectId();
		
	       //款项计划
	        String slFundIentJson = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
	        slFundIntentService.savejson(slFundIentJson, projectId,persistent.getBusinessType(),Short.parseShort("0"),null);
			//费用收支
		 
			String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, projectId,"Financing",Short.parseShort("0"),null);
			
			Map<String,BigDecimal> map=slFundIntentService.saveFinancingProjectfiance(persistent.getProjectId(),"Financing");
    		persistent.setPaychargeMoney(map.get("paychargeMoney"));
    		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
    		persistent.setAccrualMoney(map.get("loanInterest"));
    		persistent.setConsultationMoney(map.get("consultationMoney"));
    		persistent.setServiceMoney(map.get("serviceMoney"));
			 //小额融资项目节点提交改变项目状态。
	        if(null!=flowOver){
	        	//更新项目状态为1:贷中
				persistent.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
	        }
	    	
	        if(isPreposePayAccrual!=null){
	    		persistent.setIsPreposePayAccrual(1);
	    	}else {
	    		persistent.setIsPreposePayAccrual(0);
	    	}
	        
	        persistent.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
	        
	        /**
	    	 * 年化净利率
	    	 */
	    	ProjectActionUtil pu= new ProjectActionUtil();
	    	pu.getFinanceMode(persistent);
			dao.merge(persistent);
			
		   	if(persistent.getOppositeType().equals("company_customer")){   //企业
		   		 Enterprise enterprise=new Enterprise();
		   	     BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
		   	     Enterprise epersistent=this.enterpriseDao.getById(enterprise.getId());
		   	     epersistent.setEnterprisename(enterprise.getEnterprisename());
			   	 epersistent.setArea(enterprise.getArea());
			     epersistent.setShortname(enterprise.getShortname());
			   	 epersistent.setHangyeType(enterprise.getHangyeType());
			   	 epersistent.setOrganizecode(enterprise.getOrganizecode());
			   	 epersistent.setCciaa(enterprise.getCciaa());
			   	 epersistent.setTelephone(enterprise.getTelephone());
			   	 epersistent.setPostcoding(enterprise.getPostcoding());
				 enterpriseDao.merge(epersistent);
		   	     
		   	     Person person=new Person();
		   	     BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
		   	     Person ppersistent=this.personDao.getById(epersistent.getLegalpersonid());
		   	     BeanUtils.copyProperties(person, ppersistent,new String[] {"id"});
		   	     this.personDao.merge(ppersistent);
    	   		 if(listES.size()>0){
    	   			 for(int i=0;i<listES.size();i++){
    	   				 EnterpriseShareequity es=listES.get(i);
    	   				 if(es.getId()==null){
    	   					 es.setEnterpriseid(epersistent.getId());
    	   					 this.enterpriseShareequityDao.save(es);
    	   				 }else {
    	   					 EnterpriseShareequity esPersistent=this.enterpriseShareequityDao.load(es.getId());
    	   					 BeanUtils.copyProperties(es, esPersistent,new String[] {"id","enterpriseid"});
    	   					 this.enterpriseShareequityDao.merge(esPersistent);
    	   				 }
    	   			 }
    	   		 }
        	}else if(persistent.getOppositeType().equals("person_customer")){
		         Person person=new Person();
		   	     BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
		   	     Person persistentPerson=this.personDao.getById(person.getId());
		     	 persistentPerson.setMarry(person.getMarry());
	     	     persistentPerson.setName(person.getName());
	     	     persistentPerson.setSex(person.getSex());
	     	     persistentPerson.setCardtype(person.getCardtype());
	     	     persistentPerson.setCardnumber(person.getCardnumber());
	     	     persistentPerson.setTelphone(person.getTelphone());
	     	     persistentPerson.setPostcode(person.getPostcode());
	     	     persistentPerson.setSelfemail(person.getSelfemail());
	     	     persistentPerson.setPostaddress(person.getPostaddress());
	     		 personDao.merge(persistentPerson);
		   	}
		   	
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("FlFinancingProjectServiceImpl更新融资项目信息出错："+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * @description 融资项目节点保存
	 * @param FlFinancingProject
	 * @param PersonInvest
	 * @param Enterprise
	 * @param List<EnterpriseShareequity>
	 * @return 
	 */
	public Integer updateInfo(FlFinancingProject flFinancingProject,InvestPerson person,EnterpriseBank enterpriseBank){
    	try{  
    		FlFinancingProject persistent=this.dao.get(flFinancingProject.getProjectId());
	  		String currentUserId = ContextUtil.getCurrentUserId().toString();
	  		if (person.getPerId() == 0) {
				person.setPerId(null);
				person.setCreater(ContextUtil.getCurrentUser().getFullname());
				person.setCreaterId(Long.valueOf(currentUserId));
				person.setCreatedate(new Date());
				person.setCompanyId(ContextUtil.getLoginCompanyId());
				investPersonDao.save(person);
			} else {
				InvestPerson persistentPerson = investPersonDao.get(person.getPerId());
				BeanUtils.copyProperties(person, persistentPerson,new String[] {"id","creater","createrId","createdate","companyId"});
				investPersonDao.merge(persistentPerson);
			}
	  		
	  		Long farenId = person.getPerId();
			if(enterpriseBank.getId() == 0) {
				InvestPerson p = investPersonDao.get(farenId);
				Long companyId = p.getCompanyId();
				enterpriseBank.setEnterpriseid(farenId.intValue());
				enterpriseBank.setCompanyId(companyId);
				
				enterpriseBankService.add(enterpriseBank);
			}else {
				EnterpriseBank persistentEnterpriseBank = enterpriseBankService.getBankList(person.getPerId().intValue(), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("1")).get(0);
				BeanUtils.copyProperties(enterpriseBank, persistentEnterpriseBank,new String[] {"id","companyId","enterpriseid"});
				
				enterpriseBankService.update(persistentEnterpriseBank);
			}
    		BeanUtils.copyProperties(flFinancingProject, persistent,new String[] {"projectId","projectStatus","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","businessType","createDate","companyId"});
    		
    		this.dao.merge(persistent);
    	}
    	catch (Exception e) {
			e.printStackTrace();
			logger.error("FlFinancingProjectServiceImpl更新融资项目信息出错："+e.getMessage());
			return 0;
		}
    	return 1;
	}
	
	/**
	 * 获取今天的项目列表
	 * @param date
	 * @return
	 */
	public List<FlFinancingProject> getTodayProjectList(Date date) {
		return dao.getTodayProjectList(date);
	}
	
	/**
	 * 融资顾问录入业务
	 * @param flowRunInfo
	 * @return
	 */
	public Integer financingConsultantEntryBusinessNextStep(FlowRunInfo flowRunInfo) {
		try{  
			FlFinancingProject flFinancingProject = new FlFinancingProject();
			InvestPerson person = new InvestPerson();
			
			String currentUserId = ContextUtil.getCurrentUserId().toString();
			String isPreposePayAccrual = flowRunInfo.getRequest().getParameter("isPreposePayAccrualCheck");
			String degree = flowRunInfo.getRequest().getParameter("degree");
			
	    	if(isPreposePayAccrual!=null){
	    		flFinancingProject.setIsPreposePayAccrual(1);
	    	}else {
	    		flFinancingProject.setIsPreposePayAccrual(0);
	    	}
			BeanUtil.populateEntity(flowRunInfo.getRequest(), flFinancingProject,"flFinancingProject");
			FlFinancingProject persistent=this.dao.get(flFinancingProject.getProjectId());
			BeanUtils.copyProperties(flFinancingProject, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate","companyId","projectStatus" });
			
			persistent.setAppUserId(degree);
			
			BeanUtil.populateEntity(flowRunInfo.getRequest(), person,"investPerson");
	  		if (person.getPerId() == 0) {
				person.setPerId(null);
				person.setCreater(ContextUtil.getCurrentUser().getFullname());
				person.setCreaterId(Long.valueOf(currentUserId));
				person.setCreatedate(new Date());
				person.setCompanyId(ContextUtil.getLoginCompanyId());
				investPersonDao.save(person);
			} else {
				InvestPerson persistentPerson = investPersonDao.get(person.getPerId());
				BeanUtils.copyProperties(person, persistentPerson,new String[] {"id","creater","createrId","createdate","companyId"});
				investPersonDao.merge(persistentPerson);
			}
	  		
			Long farenId = person.getPerId();
	  		EnterpriseBank enterpriseBank = new EnterpriseBank();
	  		BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,"enterpriseBank");
			if(enterpriseBank.getId() == 0) {
				InvestPerson p = investPersonDao.get(farenId);
				Long companyId = p.getCompanyId();
				enterpriseBank.setEnterpriseid(farenId.intValue());
				enterpriseBank.setCompanyId(companyId);
				
				enterpriseBankService.add(enterpriseBank);
			}else {
				EnterpriseBank persistentEnterpriseBank = enterpriseBankService.getBankList(person.getPerId().intValue(), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("1")).get(0);
				BeanUtils.copyProperties(enterpriseBank, persistentEnterpriseBank,new String[] {"id","companyId","enterpriseid"});
				
				enterpriseBankService.update(persistentEnterpriseBank);
			}
    		
    		this.dao.merge(persistent);
    		return 1;
    	}
    	catch (Exception e) {
			e.printStackTrace();
			logger.error("融资顾问录入业务提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 分公司主管审查
	 * @param flowRunInfo
	 * @return
	 */
	public Integer directorCheckNextStep(FlowRunInfo flowRunInfo) {
		try{ 
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm processForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						boolean isToNextTask = creditProjectService.compareTaskSequence(processForm.getRunId(),processForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
							vars.put("targetActivityName", transitionName);//打回的目标任务名称
						}
		    		}
					vars.put("inChargeCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
	    		return 1;
			}
    	}catch (Exception e) {
			e.printStackTrace();
			logger.error("主管审查提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 总经理审核
	 * @param flowRunInfo
	 * @return
	 */
	public Integer mangerApproveNextStep(FlowRunInfo flowRunInfo) {
		try{ 
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm processForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						boolean isToNextTask = creditProjectService.compareTaskSequence(processForm.getRunId(),processForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
							vars.put("targetActivityName", transitionName);//打回的目标任务名称
						}
		    		}
					vars.put("generalManagerCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
	    		return 1;
			}
    	}catch (Exception e) {
			e.printStackTrace();
			logger.error("总经理审核提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 财务确认收款
	 * @param flowRunInfo
	 * @return
	 */
	public Integer financialVerificationCollectionNextStep(FlowRunInfo flowRunInfo) {
		try{ 
			Long projectId = Long.valueOf(flowRunInfo.getRequest().getParameter("flProjectId"));
			String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
	    	String isDeleteAllFundIntent = flowRunInfo.getRequest().getParameter("isDeleteAllFundIntent");
	    	
			FlFinancingProject project = this.get(projectId);
			project.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
			BeanUtil.populateEntity(flowRunInfo.getRequest(),project, "flFinancingProject");
			this.save(project);
			
	    	List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
	    	if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
				String[] shareequityArr = fundIntentJsonData.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(project.getProjectId());
					SlFundIntent1.setProjectName(project.getProjectName());
					SlFundIntent1.setProjectNumber(project.getProjectNumber());
					if(null == SlFundIntent1.getFundIntentId()) {
						BigDecimal lin = new BigDecimal(0.00);
						if(SlFundIntent1.getIncomeMoney().compareTo(lin)==0){
				        	SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
				        }else{
				        	SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
				        }
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid=0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(Short.valueOf("0"));
						SlFundIntent1.setBusinessType(project.getBusinessType());
						SlFundIntent1.setCompanyId(project.getCompanyId());
						slFundIntents.add(SlFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if(SlFundIntent1.getIncomeMoney().compareTo(lin)==0){
				        	SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
				        }else{
				        	SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
				        }
						SlFundIntent slFundIntent2 = slFundIntentDao.get(SlFundIntent1.getFundIntentId());
						BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
						slFundIntent2.setBusinessType(project.getBusinessType());
						slFundIntent2.setIsCheck(Short.valueOf("0"));
						slFundIntents.add(slFundIntent2);
					}
				}
			}
	       	if(isDeleteAllFundIntent.equals("1")) {
	       		List<SlFundIntent> all = slFundIntentDao.getByProjectId(project.getProjectId(), project.getBusinessType());
	       		for(SlFundIntent s:all) {
	       			slFundIntentDao.remove(s.getFundIntentId());
	       		}
	       	}
	       	for(SlFundIntent s:slFundIntents) {
	       		slFundIntentDao.save(s);
	       	}
	    	return 1;
    	}catch (Exception e) {
			e.printStackTrace();
			logger.error("财务确认收款提交下一步出错："+e.getMessage());
			return 0;
		}
	}

	/**
	 * 融资资料录入（融资无审批业务）
	 */
		@Override
		public Integer financingNoApprovalBusinessDataEntry(FlowRunInfo flowRunInfo) {
			try{  
				FlFinancingProject flFinancingProject = new FlFinancingProject();
				InvestPerson person = new InvestPerson();
				
				String currentUserId = ContextUtil.getCurrentUserId().toString();
				String isPreposePayAccrual = flowRunInfo.getRequest().getParameter("isPreposePayAccrualCheck");
				String degree = flowRunInfo.getRequest().getParameter("degree");
				
				String isInterestByOneTime=flowRunInfo.getRequest().getParameter("flFinancingProject.isInterestByOneTime");
				BeanUtil.populateEntity(flowRunInfo.getRequest(), flFinancingProject,"flFinancingProject");
				flFinancingProject.setIsInterestByOneTime(Integer.valueOf(isInterestByOneTime));
				FlFinancingProject persistent=this.dao.get(flFinancingProject.getProjectId());
				BeanUtils.copyProperties(flFinancingProject, persistent, new String[] { "id",
						"operationType", "flowType", "mineType", "mineId","appUserId",
						"oppositeType", "oppositeID", "projectName",
						"projectNumber", "oppositeType", "businessType",
						"createDate","companyId","projectStatus" });
				
				persistent.setAppUserId(degree);
				
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,"investPerson");
		  		if (person.getPerId() == 0) {
					person.setPerId(null);
					person.setCreater(ContextUtil.getCurrentUser().getFullname());
					person.setCreaterId(Long.valueOf(currentUserId));
					person.setCreatedate(new Date());
					person.setCompanyId(ContextUtil.getLoginCompanyId());
					investPersonDao.save(person);
				} else {
					InvestPerson persistentPerson = investPersonDao.get(person.getPerId());
					BeanUtils.copyProperties(person, persistentPerson,new String[] {"id","creater","createrId","createdate","companyId"});
					investPersonDao.merge(persistentPerson);
				}
		  		
				Long farenId = person.getPerId();
		  		EnterpriseBank enterpriseBank = new EnterpriseBank();
		  		BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,"enterpriseBank");
				if(enterpriseBank.getId() == 0) {
					InvestPerson p = investPersonDao.get(farenId);
					Long companyId = p.getCompanyId();
					enterpriseBank.setEnterpriseid(farenId.intValue());
					enterpriseBank.setCompanyId(companyId);
					
					enterpriseBankService.add(enterpriseBank);
				}else {
					EnterpriseBank persistentEnterpriseBank = enterpriseBankService.getBankList(person.getPerId().intValue(), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("1")).get(0);
					BeanUtils.copyProperties(enterpriseBank, persistentEnterpriseBank,new String[] {"id","companyId","enterpriseid"});
					
					enterpriseBankService.update(persistentEnterpriseBank);
				}
	    		
	    		this.dao.merge(persistent);
	    		return 1;
	    	}
	    	catch (Exception e) {
				e.printStackTrace();
				logger.error("融资无审批流程资料录入提交下一步出错："+e.getMessage());
				return 0;
			}
		}
		
	/**
	 * 财务确认收款--融资无审批流程
	 */
	@Override
	public Integer financingNoApprovalBusiness(FlowRunInfo flowRunInfo) {
		try{
			
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							} else {
								  
								String taskId = flowRunInfo.getRequest().getParameter("task_id");
								String comments = flowRunInfo.getRequest().getParameter("comments");
								Long projectId = Long.valueOf(flowRunInfo.getRequest().getParameter("flProjectId"));
								String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
								String isDeleteAllFundIntent = flowRunInfo.getRequest().getParameter("isDeleteAllFundIntent");
								FlFinancingProject flFinancingProject = new FlFinancingProject();
						    	String isInterestByOneTime=flowRunInfo.getRequest().getParameter("flFinancingProject.isInterestByOneTime");
								BeanUtil.populateEntity(flowRunInfo.getRequest(), flFinancingProject,"flFinancingProject");
								flFinancingProject.setIsInterestByOneTime(Integer.valueOf(isInterestByOneTime));
								FlFinancingProject persistent=this.dao.get(projectId);
								persistent.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);//更新状态
								BeanUtils.copyProperties(flFinancingProject, persistent, new String[] { "id",
										"operationType", "flowType", "mineType", "mineId","appUserId",
										"oppositeType", "oppositeID", "projectName",
										"projectNumber", "oppositeType", "businessType",
										"createDate","companyId","projectStatus" });
								this.dao.merge(persistent);
								List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
						    	if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
									String[] shareequityArr = fundIntentJsonData.split("@");
									for (int i = 0; i < shareequityArr.length; i++) {
										String str = shareequityArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
										SlFundIntent1.setProjectId(persistent.getProjectId());
										SlFundIntent1.setProjectName(persistent.getProjectName());
										SlFundIntent1.setProjectNumber(persistent.getProjectNumber());
										if(null == SlFundIntent1.getFundIntentId()) {
											BigDecimal lin = new BigDecimal(0.00);
											if(SlFundIntent1.getIncomeMoney().compareTo(lin)==0){
									        	SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
									        }else{
									        	SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
									        }
											SlFundIntent1.setAfterMoney(new BigDecimal(0));
											SlFundIntent1.setAccrualMoney(new BigDecimal(0));
											SlFundIntent1.setFlatMoney(new BigDecimal(0));
											Short isvalid=0;
											SlFundIntent1.setIsValid(isvalid);
											SlFundIntent1.setIsCheck(Short.valueOf("0"));
											SlFundIntent1.setBusinessType(persistent.getBusinessType());
											SlFundIntent1.setCompanyId(persistent.getCompanyId());
											slFundIntents.add(SlFundIntent1);
										} else {
											BigDecimal lin = new BigDecimal(0.00);
											if(SlFundIntent1.getIncomeMoney().compareTo(lin)==0){
									        	SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
									        }else{
									        	SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
									        }
											SlFundIntent slFundIntent2 = slFundIntentDao.get(SlFundIntent1.getFundIntentId());
											BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
											slFundIntent2.setBusinessType(persistent.getBusinessType());
											slFundIntent2.setIsCheck(Short.valueOf("0"));
											slFundIntents.add(slFundIntent2);
										}
									}
								}
						       	if(isDeleteAllFundIntent.equals("1")) {
						       		List<SlFundIntent> all = slFundIntentDao.getByProjectId(persistent.getProjectId(), persistent.getBusinessType());
						       		for(SlFundIntent s:all) {
						       			if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
						       				slFundIntentDao.remove(s.getFundIntentId());
						       			}else{
						       				s.setIsCheck(Short.valueOf("0"));
						       				slFundIntentDao.save(s);
						       			}
						       		}
						       	}
						       	for(SlFundIntent s:slFundIntents) {
						       		slFundIntentDao.save(s);
						       	}
						       	if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
									this.creditProjectService.saveComments(taskId, comments);
								}
							}
							vars.put("noApprovalResult", transitionName);
							flowRunInfo.getVariables().putAll(vars);
						}
		    		}
				}
			}
			return 1;
			}catch(Exception e){
				logger.error("融资无审批流程财务确认收款提交下一步出错："+e.getMessage());
				e.printStackTrace();
				return 0;
			}
		}
}