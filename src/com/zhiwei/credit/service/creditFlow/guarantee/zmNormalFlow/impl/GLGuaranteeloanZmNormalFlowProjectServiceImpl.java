package com.zhiwei.credit.service.creditFlow.guarantee.zmNormalFlow.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.springframework.beans.BeanUtils;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeCommentsDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlSuperviseRecordDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountRecordDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.GLGuaranteeloanProjectDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.flow.TaskSignDataDao;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GLSuperviseRecord;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeComments;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.customer.bankRelationPerson.CustomerBankRelationPersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.zmNormalFlow.GLGuaranteeloanZmNormalFlowProjectService;
import com.zhiwei.credit.service.flow.JbpmService;

public class GLGuaranteeloanZmNormalFlowProjectServiceImpl extends
BaseServiceImpl<GLGuaranteeloanProject> implements
		GLGuaranteeloanZmNormalFlowProjectService   {

	private  GLGuaranteeloanProjectDao dao;
	@Resource
	private GlSuperviseRecordDao glSuperviseRecordDao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private TaskSignDataDao taskSignDataDao;
	@Resource
	private JbpmService jbpmService;//没有dao，直接调用service
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private GlAccountRecordDao glAccountRecordDao;
	@Resource
	private GlBankGuaranteemoneyDao glBankGuaranteemoneyDao;
	@Resource
	private GlAccountBankCautionmoneyDao glAccountBankCautionmoneyDao;
	@Resource
	private GlAccountBankDao glAccountBankDao;
	@Resource
	private GlBankGuaranteemoneyService glBankGuaranteemoneyService;
	@Resource
	private GlAccountBankCautionmoneyService glAccountBankCautionmoneyService;
	public GLGuaranteeloanZmNormalFlowProjectServiceImpl(GLGuaranteeloanProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	@Resource
	private PersonDao personDao;
	
	@Resource
	private EnterpriseDao enterpriseDao;
	
	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	@Resource
	private SlActualToChargeDao slActualToChargeDao;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	
	@Resource
	private  CustomerBankRelationPersonService customerBankRelationPersonService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private GlFlownodeCommentsDao glFlownodeCommentsDao;
	/**
	 * 担保项目 业务经理尽职调查节点  保存(更新)信息
	 */
	@Override
	public Integer updateInfo(GLGuaranteeloanProject gLGuaranteeloanProject,
			Person person, Enterprise enterprise,
			List<EnterpriseShareequity> listES,CustomerBankRelationPerson customerBankRelationPerson,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent) {
		try
		{
		  
			
			
			GLGuaranteeloanProject persistent=this.dao.get(gLGuaranteeloanProject.getProjectId());
		   	if(persistent.getOppositeType().equals("company_customer"))
        	{
    	   		 if(listES.size()>0)
    	   		 {
    	   			 
    	   			 for(int i=0;i<listES.size();i++)
    	   			 {
    	   				 EnterpriseShareequity es=listES.get(i);
    	   				 if(es.getId()==null)
    	   				 {
    	   					es.setEnterpriseid(enterprise.getId());
    	   					this.enterpriseShareequityDao.save(es);
    	   				 }
    	   				 else 
    	   				 {
    	   					 EnterpriseShareequity esPersistent=this.enterpriseShareequityDao.load(es.getId());
    	   					 BeanUtils.copyProperties(es, esPersistent,new String[] {"id","enterpriseid"});
    	   					 this.enterpriseShareequityDao.merge(esPersistent);
    	   				 }
    	   			 }
    	   		 }
    	    	 Enterprise ePersistent=this.enterpriseDao.getById(enterprise.getId());
    	    	 ePersistent.setEnterprisename(enterprise.getEnterprisename());
    	    	 ePersistent.setArea(enterprise.getArea());
    	    	 ePersistent.setShortname(enterprise.getShortname());
    	    	 ePersistent.setHangyeType(enterprise.getHangyeType());
    	    	 ePersistent.setOrganizecode(enterprise.getOrganizecode());
    	    	 ePersistent.setCciaa(enterprise.getCciaa());
    	    	 ePersistent.setTelephone(enterprise.getTelephone());
    	    	 ePersistent.setPostcoding(enterprise.getPostcoding());
    	    	 this.enterpriseDao.merge(ePersistent);
    	    	 
    	    	 
	    	   	  Person pPersistent=this.personDao.getById(ePersistent.getLegalpersonid());
	    		  pPersistent.setName(person.getName());
	    		  pPersistent.setSex(person.getSex());
	    		  pPersistent.setCardtype(person.getCardtype());
	    		  pPersistent.setCardnumber(person.getCardnumber());
	    		  pPersistent.setTelphone(person.getTelphone());
	    		  pPersistent.setSelfemail(person.getSelfemail());
	    		  this.personDao.merge(pPersistent);
        	}
        	else if(persistent.getOppositeType().equals("person_customer"))
        	{
        		  Person pPersistent=this.personDao.getById(person.getId());
        		  pPersistent.setMarry(person.getMarry());
        		  pPersistent.setName(person.getName());
        		  pPersistent.setSex(person.getSex());
        		  pPersistent.setCardtype(person.getCardtype());
        		  pPersistent.setCardnumber(person.getCardnumber());
        		  pPersistent.setTelphone(person.getTelphone());
        		  pPersistent.setPostcode(person.getPostcode());
        		  pPersistent.setSelfemail(person.getSelfemail());
        		  pPersistent.setPostaddress(person.getPostaddress());
        		  this.personDao.merge(pPersistent);
        	}
    		BeanUtils.copyProperties(gLGuaranteeloanProject, persistent,new String[] {"id","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType"});
    		int bankid=0;
    		if(null==customerBankRelationPerson.getId() || customerBankRelationPerson.getId()==0) //第一次保存
    		{    
    			 if(null!=customerBankRelationPerson.getBankid() && "".equals(customerBankRelationPerson.getBankid()))
    			 {
    				 AppUser currentUser = ContextUtil.getCurrentUser();//个人信息
    					Long currentUserId = ContextUtil.getCurrentUserId();
    					customerBankRelationPerson.setCreater(currentUser.getFullname());
    					customerBankRelationPerson.setCreaterId(currentUserId);
    					customerBankRelationPerson.setBelongedId(currentUserId.toString());
    					customerBankRelationPerson.setCreatedate(DateUtil.getNowDateTimeTs());
    				 customerBankRelationPersonService.addPerson(customerBankRelationPerson);
    			  persistent.setBankId(customerBankRelationPerson.getId());
    			 }
    		}
    		else
    		{
    			CustomerBankRelationPerson pasint=this.customerBankRelationPersonService.getById(customerBankRelationPerson.getId());
    			pasint.setFenbankid(customerBankRelationPerson.getFenbankid());
    			pasint.setBlmphone(customerBankRelationPerson.getBlmphone());
    			pasint.setBlmtelephone(customerBankRelationPerson.getBlmtelephone());
    			pasint.setEmail(customerBankRelationPerson.getEmail());
    			pasint.setDuty(customerBankRelationPerson.getDuty());
    			pasint.setName(customerBankRelationPerson.getName());
    			customerBankRelationPersonService.addPerson(pasint);
    			persistent.setBankId(customerBankRelationPerson.getId());
    			bankid=pasint.getBankid();
    		}
// 	    	GlBankGuaranteemoney glBankGuaranteemoney= new GlBankGuaranteemoney();
//			List<GlBankGuaranteemoney> listBankGuarantee = glBankGuaranteemoneyDao.getbyprojId(gLGuaranteeloanProject.getProjectId());
//			if(listBankGuarantee.size() != 0){
//				glBankGuaranteemoney=listBankGuarantee.get(0);
//			}
//			glBankGuaranteemoney.setProjectId(gLGuaranteeloanProject.getProjectId());
//			glBankGuaranteemoney.setBusinessType("Guarantee");
//			glBankGuaranteemoney.setOperationType("CompanyBusiness");
//			
// 	    	this.glBankGuaranteemoneyDao.save(glBankGuaranteemoney);
// 	       // gaomm 银行保证金
 	    	if(isDeleteAllFundIntent.equals("1")) {
    	    	List<SlFundIntent> allintent=this.slFundIntentDao.getByProjectId1(persistent.getProjectId(),"Guarantee");
    	    	for(SlFundIntent s:allintent){
    	    		slFundIntentDao.evict(s);
    	    		if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
    	    		this.slFundIntentDao.remove(s);
    	    		}
    	    	}
        		for(SlFundIntent a:slFundIntents){
        			this.slFundIntentDao.save(a);
        		}
        		for(SlActualToCharge a:slActualToCharges){
        			this.slActualToChargeDao.save(a);
        		}
    		}
 	 		Map<String,BigDecimal> map=slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");
    		persistent.setPaychargeMoney(map.get("paychargeMoney"));
    		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
    		this.dao.merge(persistent);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("企业贷项目 中铭流程 -业务经理尽职调查节点执行下一步  出错:"+e.getMessage());
			return 0;
		}
	}
    
    /**
     * 担保项目 业务经理尽职调查节点流转到 业务主管审查上报材料
     */
	@Override
	public Integer updateEnterpriceDiligenceCreditFlowProject(
			FlowRunInfo flowRunInfo) {
		if(flowRunInfo.isBack()){
			return 1;
		}
		
		try
		{
				String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");
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
								}
							}
				}
		    	
		    	GLGuaranteeloanProject project =new GLGuaranteeloanProject();
		    	CustomerBankRelationPerson customerBankRelationPerson =new CustomerBankRelationPerson();
		    	BeanUtil.populateEntity(flowRunInfo.getRequest(), customerBankRelationPerson, "customerBankRelationPerson");
		    	BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
		    	GLGuaranteeloanProject  persistent=this.dao.get(project.getProjectId());
		    	BeanUtils.copyProperties(project, persistent,new String[] {"id","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType"});
		    	int bankid=0;
		    	if(null==customerBankRelationPerson.getId() || customerBankRelationPerson.getId()==0) //第一次保存
	    		{
		    		AppUser currentUser = ContextUtil.getCurrentUser();//个人信息
		    		Long currentUserId = ContextUtil.getCurrentUserId();
		    		customerBankRelationPerson.setCreater(currentUser.getFullname());
		    		customerBankRelationPerson.setCreaterId(currentUserId);
		    		customerBankRelationPerson.setBelongedId(currentUserId.toString());
		    		customerBankRelationPerson.setCreatedate(DateUtil.getNowDateTimeTs());
		    		customerBankRelationPersonService.addPerson(customerBankRelationPerson);
	    			 persistent.setBankId(customerBankRelationPerson.getId());
	    		
	    		}
	    		else
	    		{
	    			CustomerBankRelationPerson pasint=this.customerBankRelationPersonService.getById(customerBankRelationPerson.getId());
	    			pasint.setFenbankid(customerBankRelationPerson.getFenbankid());
	    			pasint.setBlmphone(customerBankRelationPerson.getBlmphone());
	    			pasint.setBlmtelephone(customerBankRelationPerson.getBlmtelephone());
	    			pasint.setName(customerBankRelationPerson.getName());
	    			pasint.setDuty(customerBankRelationPerson.getDuty());
	    			pasint.setEmail(customerBankRelationPerson.getEmail());
	    			customerBankRelationPersonService.addPerson(pasint);
	    			persistent.setBankId(customerBankRelationPerson.getId());
	    			bankid=pasint.getBankid();
	    		}
//	     	       // gaomm 银行保证金
//	     	    	GlBankGuaranteemoney glBankGuaranteemoney= new GlBankGuaranteemoney();
//	    			List<GlBankGuaranteemoney> listBankGuarantee = glBankGuaranteemoneyService.getbyprojId(project.getProjectId());
//	    			if(listBankGuarantee.size() != 0){
//	    				glBankGuaranteemoney=listBankGuarantee.get(0);
//	    			}
//	    			glBankGuaranteemoney.setProjectId(project.getProjectId());
//	    			glBankGuaranteemoney.setBusinessType("Guarantee");
//	    			glBankGuaranteemoney.setOperationType("CompanyBusiness");
//	     	    	this.glBankGuaranteemoneyService.save(glBankGuaranteemoney);
//	     	       // gaomm 银行保证金
		    	//款项计划
		        String slFundIentJson = flowRunInfo.getRequest().getParameter("fundIntentJsonData1");
		        slFundIntentService.savejson(slFundIentJson, persistent.getProjectId(),"Guarantee",Short.parseShort("1"),null);
				//费用收支
			 
				String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
				slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"Guarantee",Short.parseShort("0"),null);
				
				Map<String,BigDecimal> map=slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");
	    		persistent.setPaychargeMoney(map.get("paychargeMoney"));
	    		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
				
				
				
		    	this.dao.merge(persistent);
		       	if(persistent.getOppositeType().equals("company_customer")) //企业
	        	{   
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
			   	     this.enterpriseDao.merge(epersistent);
			   	     
			   	     
			   	     //更新person信息
			   	     Person person=new Person();
			   	     BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			   	     Person ppersistent=this.personDao.getById(epersistent.getLegalpersonid());
			   	     ppersistent.setMarry(person.getMarry());
			   	     ppersistent.setName(person.getName());
			   	     ppersistent.setSex(person.getSex());
			   	     ppersistent.setCardtype(person.getCardtype());
			   	     ppersistent.setCardnumber(person.getCardnumber());
			   	     ppersistent.setTelphone(person.getTelphone());
			   	     ppersistent.setPostcode(person.getPostcode());
			   	     ppersistent.setSelfemail(person.getSelfemail());
			   	     ppersistent.setPostaddress(person.getPostaddress());
		     		 personDao.merge(ppersistent);
		     		 
			   	     
	    	   		 if(listES.size()>0)
	    	   		 {
	    	   			 
	    	   			 for(int i=0;i<listES.size();i++)
	    	   			 {
	    	   				 EnterpriseShareequity es=listES.get(i);
	    	   				 if(es.getId()==null)
	    	   				 {
	    	   					 es.setEnterpriseid(epersistent.getId());
	    	   					 this.enterpriseShareequityDao.save(es);
	    	   				 }
	    	   				 else 
	    	   				 {
	    	   					 EnterpriseShareequity esPersistent=this.enterpriseShareequityDao.load(es.getId());
	    	   					 BeanUtils.copyProperties(es, esPersistent,new String[] {"id","enterpriseid"});
	    	   					 this.enterpriseShareequityDao.merge(esPersistent);
	    	   				 }
	    	   			 }
	    	   		 }
	        	}
			   	else if(persistent.getOppositeType().equals("person_customer"))
			   	{
			         Person person=new Person();
			   	     BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			   	     
			   	     //更新person信息开始
			   	     Person persistentPerson=this.personDao.getById(person.getId());
		     	     persistentPerson.setMarry(person.getMarry());
		     	     persistentPerson.setName(person.getName());
		     	     persistentPerson.setSex(person.getSex());
		     	     persistentPerson.setCardtype(person.getCardtype());
		     	     persistentPerson.setCardnumber(person.getCardnumber());
		     	     persistentPerson.setTelphone(person.getTelphone());
		     	     persistentPerson.setPostcode(person.getPostcode());
		     	     persistentPerson.setSelfemail(person.getSelfemail());
		     	     persistentPerson.setAddress(person.getAddress());
		     		 personDao.merge(persistentPerson);
		     		 //更新person信息结束
			   	}
		    	return 1;
		}
		catch (Exception e) {
			   e.printStackTrace();
			   logger.error("担保项目-中铭流程   项目尽调材料上报 执行下一步   出错:"+e.getMessage());
			   return 0;
		}
	}

	@Override
	public Integer updateEnterpriceReportMaterial(FlowRunInfo flowRunInfo) {
		 try
		 {       
			    if(flowRunInfo.isBack()){
			    	return 1;
			    }else{
			    	String transitionName = flowRunInfo.getTransitionName();
			    	
			      	if(transitionName!=null&&!"".equals(transitionName)){
			      		Map<String,Object> vars=new HashMap<String, Object>();
			      		ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
			      		
				    	if(currentForm!=null){
				    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
				    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
				    			flowRunInfo.setAfresh(true);//表示打回重做
				    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "mnDueDiligence");
			    				if(processForm!=null&&processForm.getCreatorId()!=null){
			    					String creatorId = processForm.getCreatorId().toString();
			    					vars.put("flowAssignId",creatorId);
			    				}
				    		}else{
				    			
				    		}
				    	}
				    	vars.put("riskManagerCheckResult",transitionName);
						flowRunInfo.getVariables().putAll(vars);
			    	}
			    	return 1;
			    }
		 }
		 catch (Exception e) {
			  e.printStackTrace();
			  return 0;
		}
	}
	
	@Override
	public Integer approveAssureMaterial(FlowRunInfo flowRunInfo){
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String approveResult = flowRunInfo.getRequest().getParameter("approveResult");
		    	Map<String,Object> vars=new HashMap<String, Object>();
		    	Integer shenpiResult = 0;
		    	if(approveResult!=null&&!"".equals(approveResult)){
		    		if("2".equals(approveResult)){
		    			flowRunInfo.setAfresh(true);//表示打回重做
		    		}
		    		shenpiResult = Integer.valueOf(approveResult);
		    	}
		    	vars.put("verifyWarrantMaterial",shenpiResult);
				flowRunInfo.getVariables().putAll(vars);
		    	return 1;
		    }
		 }catch (Exception e) {
			  e.printStackTrace();
			  return 0;
		}
	}

	@Override
	public Integer updateEnterpriceRiskManagerAudit(FlowRunInfo flowRunInfo) {
	
		try {
			
			if(flowRunInfo.isBack()){
				return 1;
			}
			String meetting = flowRunInfo.getRequest().getParameter("meetting");
			String temp = flowRunInfo.getRequest().getParameter("isFlowFlag");
			GLGuaranteeloanProject project=new GLGuaranteeloanProject();
	        BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
			boolean isFlowFlag=true;
			if(null==temp)
			{
				isFlowFlag=false;
			}
			Map<String,Object> vars=new HashMap<String, Object>();
			Integer mt=0;
			if(null!=meetting && !"".equals(meetting))
			{
				mt=Integer.valueOf(meetting);
			}
			vars.put("meetting",mt);
			vars.put("isFlowFlag",isFlowFlag);
			vars.put("creditMoney",project.getProjectMoney().doubleValue());
			flowRunInfo.getVariables().putAll(vars);

			return 1;
			     
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 项目归档提交
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateProjectStatus(FlowRunInfo flowRunInfo){
		if(flowRunInfo.isBack()){
			return 1;
		}else{
			try{
				String flowOver = flowRunInfo.getRequest().getParameter("flowOver");
				if(null!=flowOver){
					String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
					if(null!=projectId){
						GLGuaranteeloanProject gl = dao.get(new Long(projectId));
						if(null!=gl){
							gl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
							gl.setEndDate(new Date());
							dao.merge(gl);
							return 1;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public void updateSupervise(Long projectId,
			   List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges) {
		
//		       for(GlProcreditSupervision gs:glProcreditSupervisions){
//			   gs.setProjectId(projectId);
//			   this.glProcreditSupervisionDao.save(gs);
//		      }
		       
		       for(SlActualToCharge slActualToCharge:slActualToCharges){
					this.slActualToChargeDao.save(slActualToCharge);
				}
				for(SlFundIntent a:slFundIntents){
					slFundIntentDao.save(a);
				}
	}

	@Override
	public boolean askForProject(Long projectId,
			List<SlActualToCharge> slActualToCharges,
			List<SlFundIntent> slFundIntents,
			GLSuperviseRecord glSuperviseRecord, String categoryIds) {
		glSuperviseRecord.setProjectId(projectId);
		glSuperviseRecordDao.save(glSuperviseRecord);   
		for(SlActualToCharge slActualToCharge:slActualToCharges){
			this.slActualToChargeDao.save(slActualToCharge);
		}
		for(SlFundIntent a:slFundIntents){
			slFundIntentDao.save(a);
			
		}
		//保存的同时更新合同分类表ProcreditContractCategory add by chencc start...
		if(!"".equals(categoryIds)){
			String [] idArray = categoryIds.split(",");
			if(idArray.length >0){
				for(int i=0;i<idArray.length;i++ ){
					if(!"".equals(idArray[i])&& idArray[i]!=null && StringUtil.isNumeric(idArray[i])){
						try {
							Object [] obj = {glSuperviseRecord.getId(),Integer.parseInt(idArray[i])};
							creditBaseDao.excuteSQL("update ProcreditContract set isApply=1,clauseId =? where id =?",obj);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		//保存的同时更新合同分类表ProcreditContractCategory add by chencc end...
		return true;
	}
    //绿色通道审保会决议
	@Override
	public Integer greenConferenceResult(FlowRunInfo flowRunInfo) {
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				//String shenpi = flowRunInfo.getRequest().getParameter("shenpi");
				String transitionName = flowRunInfo.getTransitionName();
				String zmRunId = flowRunInfo.getRequest().getParameter("zmRunId");
		    	String creatorId = "1";//默认一个值，为超级管理员。
		    	if(transitionName!=null&&!"".equals(transitionName)){
		    		
		    		ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
			    	if(currentForm!=null){
			    		Map<String,Object> vars=new HashMap<String, Object>();
			    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
			    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
			    			flowRunInfo.setAfresh(true);//表示打回重做
			    			
			    			String flowNodeKey = "zmnCheckMaterial";//默认一个值
							
							ProUserAssign userAssign = creditProjectService.getByRunIdActivityName(currentForm.getRunId(), transitionName);
							if(userAssign!=null&&userAssign.getTaskSequence()!=null&&!"".equals(userAssign.getTaskSequence())){
								if(userAssign.getTaskSequence().contains("zmnCheckMaterial")){//打回至业务主管审核上报材料
									flowNodeKey = "zmnCheckMaterial";
								}else if(userAssign.getTaskSequence().contains("zmnBefSyntheticalAnalyse")){//打回至保前综合分析
									flowNodeKey = "zmnBefSyntheticalAnalyse";
								}
							}
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(new Long(zmRunId), flowNodeKey);
		    				if(processForm!=null&&processForm.getCreatorId()!=null){
		    					creatorId = processForm.getCreatorId().toString();
		    					vars.put("flowAssignId",creatorId);
		    				}
			    		}
			    		vars.put("riskDirectorResult",transitionName);
			    		flowRunInfo.getVariables().putAll(vars);
			    	}
		    	}
		    	return 1;
		    }
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("风险主管审核提交任务报错："+e.getMessage());
			return 0;
		}
		
	}

	@Override
	public Integer businessDirectorAudit(FlowRunInfo flowRunInfo) {
		 try
		 {       
			    if(flowRunInfo.isBack()){
			    	return 1;
			    }else{
			    	
			    	Integer  shenpiResult=1;
			    	String shenpi = flowRunInfo.getRequest().getParameter("shenpi");
			      	if(shenpi!=null&&!"".equals(shenpi)){
			    		if("2".equals(shenpi)){
			    			flowRunInfo.setAfresh(true);//表示打回重做
			    		}
			    	}
			      	shenpiResult = Integer.valueOf(shenpi);
			    	Map<String,Object> vars=new HashMap<String, Object>();
			    	vars.put("businessDirectorResult",shenpiResult);
					flowRunInfo.getVariables().putAll(vars);
			    	return 1;
			    }
		 }
		 catch (Exception e) {
			  e.printStackTrace();
			  return 0;
		}
	}


	@Override
	public Integer updatechargeaffirm(
			GLGuaranteeloanProject gLGuaranteeloanProject,
			List<SlFundIntent> slFundIntents,
			List<SlActualToCharge> slActualToCharges) {
		
		
		GLGuaranteeloanProject persistent=this.dao.get(gLGuaranteeloanProject.getProjectId());
		BeanUtils.copyProperties(gLGuaranteeloanProject, persistent,new String[] {"id","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType","bankId"});
		
		
		List<SlFundIntent> allintent=this.slFundIntentDao.getByProjectId1(persistent.getProjectId(),"Guarantee");
    	for(SlFundIntent s:allintent){
    		slFundIntentDao.evict(s);
    		if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
    		this.slFundIntentDao.remove(s);
    		}
    	}
		for(SlFundIntent a:slFundIntents){
			this.slFundIntentDao.save(a);
		}
		for(SlActualToCharge a:slActualToCharges){
			this.slActualToChargeDao.save(a);
		}
		Map<String,BigDecimal> map=slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");
		persistent.setPaychargeMoney(map.get("paychargeMoney"));
		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
		this.dao.merge(persistent);
		return 1;
	}
	@Override
	public Integer chargeaffirm(FlowRunInfo flowRunInfo) {
		if(flowRunInfo.isBack()){
			return 1;
		}else{
		    	try {
		    		GLGuaranteeloanProject project =new GLGuaranteeloanProject();
		    		BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
			    	GLGuaranteeloanProject  persistent=this.dao.get(project.getProjectId());
			    	BeanUtils.copyProperties(project, persistent,new String[] {"id","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType","bankId"});
			    	
			    	//款项计划
			        String slFundIentJson = flowRunInfo.getRequest().getParameter("fundIntentJsonData1");
			        slFundIntentService.savejson(slFundIentJson, persistent.getProjectId(),"Guarantee",Short.parseShort("1"),null);
					//费用收支
				 
					String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
					slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"Guarantee",Short.parseShort("0"),null);
					
					Map<String,BigDecimal> map=slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");
		    		persistent.setPaychargeMoney(map.get("paychargeMoney"));
		    		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
		    		this.dao.merge(persistent);
			    	
					return 1;
		    	   
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("企业贷项目 中铭流程 -费用确认执行下一步  出错:"+e.getMessage());
					return 0;
				} 
		
	}
	}

	
	/**
	 * 审保会集体决议
	 * @param flowRunInfo
	 * @return
	 */
	public Integer examinationArrangement(FlowRunInfo flowRunInfo){
		try{
			String signVoteType = flowRunInfo.getRequest().getParameter("signVoteType1");//投票结果：同意(1)、决绝(2)、弃权(0)。
			
			String userId = ContextUtil.getCurrentUserId().toString();
			String userFullName = ContextUtil.getCurrentUser().getFullname();
			
			Map<String,Object> vars=new HashMap<String, Object>();
			
			String taskId = flowRunInfo.getTaskId();
			if(taskId!=null&&!"".equals(taskId)){
				Task parentTask = jbpmService.getParentTask(taskId.toString());
				ProcessForm processForm = processFormDao.getByTaskId(taskId);
				if(processForm!=null){
					boolean saveTaskSignDataOk = this.saveTaskSignData(processForm,userId,userFullName,parentTask.getId(),signVoteType);
					boolean saveFlowNodeOk = this.saveFlowNodeComments(processForm, flowRunInfo);
					if(saveTaskSignDataOk&&saveFlowNodeOk){
						vars.put("microNormalSDH","false");
						flowRunInfo.getVariables().putAll(vars);
						
						List<Transition> trans=jbpmService.getTransitionsByTaskId(flowRunInfo.getTaskId());
						if(trans!=null&&trans.size()!=0){
							String transitionName = trans.get(0).getName();
							flowRunInfo.setTransitionName(transitionName);
						}
					}
				}
			}
			return 1;
		}catch(Exception e){
			logger.error("审保会集体决议："+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	private boolean saveTaskSignData(ProcessForm processForm,String userId,String userFullName,String parentTaskId,String signVoteType){
		try{
			
			TaskSignData taskSignData = new TaskSignData();
			taskSignData.setVoteId(new Long(userId));
			taskSignData.setVoteName(userFullName);
			taskSignData.setVoteTime(new Date());
			taskSignData.setTaskId(parentTaskId);
			taskSignData.setRunId(new Long(processForm.getRunId()));
			taskSignData.setIsAgree(Short.valueOf(signVoteType));
			taskSignData.setFromTaskId(processForm.getFromTaskId());
			taskSignData.setFormId(processForm.getFormId());
			taskSignDataDao.save(taskSignData);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("审保会集体决议提交保存会签信息出错："+e.getMessage());
			return false;
		}
	}
	
	private boolean saveFlowNodeComments(ProcessForm processForm,FlowRunInfo flowRunInfo){
		try{
			
			String premiumRateComments = flowRunInfo.getRequest().getParameter("glFlownodeComments.premiumRateComments");
			String mortgageComments = flowRunInfo.getRequest().getParameter("glFlownodeComments.mortgageComments");
			String assureTimeLimitComments = flowRunInfo.getRequest().getParameter("glFlownodeComments.assureTimeLimitComments");
			String assureTotalMoneyComments = flowRunInfo.getRequest().getParameter("glFlownodeComments.assureTotalMoneyComments");
			
			GlFlownodeComments gfFlow = glFlownodeCommentsDao.getByFormId(processForm.getFormId());
			if(gfFlow!=null){
				gfFlow.setPremiumRateComments(premiumRateComments);
				gfFlow.setMortgageComments(mortgageComments);
				gfFlow.setAssureTimeLimitComments(assureTimeLimitComments);
				gfFlow.setAssureTotalMoneyComments(assureTotalMoneyComments);
				
				glFlownodeCommentsDao.merge(gfFlow);
			}else{
				GlFlownodeComments flowNode = new GlFlownodeComments();
				flowNode.setFormId(processForm.getFormId());
				flowNode.setRunId(processForm.getRunId());
				if(premiumRateComments!=null&&!"".equals(premiumRateComments)){
					flowNode.setPremiumRateComments(premiumRateComments);
				}
				if(mortgageComments!=null&&!"".equals(mortgageComments)){
					flowNode.setMortgageComments(mortgageComments);
				}
				if(assureTimeLimitComments!=null&&!"".equals(assureTimeLimitComments)){
					flowNode.setAssureTimeLimitComments(assureTimeLimitComments);
				}
				if(assureTotalMoneyComments!=null&&!"".equals(assureTotalMoneyComments)){
					flowNode.setAssureTotalMoneyComments(assureTotalMoneyComments);
				}
				glFlownodeCommentsDao.save(flowNode);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("审保会集体决议保存意见与说明出错："+e.getMessage());
			return false;
		}
	}

	@Override
	public Integer presidentAudit(FlowRunInfo flowRunInfo) {
		try{
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }
		    else{
		    	String transitionName = flowRunInfo.getTransitionName();
		    	String zmRunId = flowRunInfo.getRequest().getParameter("zmRunId");
				String businessType = flowRunInfo.getRequest().getParameter("businessType_flow");
				Long projectId = Long.parseLong(flowRunInfo.getRequest().getParameter("projectId_flow"));
		    	if(transitionName!=null&&!"".equals(transitionName)){
		    		Map<String,Object> vars=new HashMap<String, Object>();
		    		ProcessForm processForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
		    		if(processForm!=null){
		    			ProUserAssign userAssign = creditProjectService.getByRunIdActivityName(processForm.getRunId(), transitionName);
		    			if(userAssign!=null&&userAssign.getTaskSequence()!=null&&!"".equals(userAssign.getTaskSequence())){
		    				if(userAssign.getTaskSequence().contains("zmnMakeContract")){//提交至合同制作
		    					List<SlFundIntent> list1=slFundIntentDao.getByProjectId1(projectId, businessType);
								List<SlActualToCharge>list2=slActualToChargeDao.listbyproject(projectId, businessType);
								Short a=0;
								for(SlFundIntent l:list1){
									l.setIsCheck(a);
									slFundIntentDao.save(l);
								}
								for(SlActualToCharge l:list2){
									l.setIsCheck(a);	
									slActualToChargeService.save(l);
								}
		    				}else if(userAssign.getTaskSequence().contains("zmnCheckMaterial")){//打回至业务主管审核上报材料
		    					String creatorId = "1";//默认一个值，为超级管理员。
		    					ProcessForm pform = processFormDao.getByRunIdFlowNodeKey(new Long(zmRunId), "zmnCheckMaterial");
			    				if(pform!=null&&pform.getCreatorId()!=null){
			    					creatorId = pform.getCreatorId().toString();
			    					vars.put("flowAssignId",creatorId);
			    				}
		    				}
		    			}
		    			
		    			boolean isToNextTask = creditProjectService.compareTaskSequence(processForm.getRunId(), processForm.getActivityName(), transitionName);
			    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
			    			flowRunInfo.setAfresh(true);//表示打回重做
			    		}
		    		}
		    		vars.put("presidentResult",transitionName);
		    		flowRunInfo.getVariables().putAll(vars);
		    	}
		    	
		    	//String shenpi = flowRunInfo.getRequest().getParameter("shenpi");
				/*String zmRunId = flowRunInfo.getRequest().getParameter("zmRunId");
				String businessType = flowRunInfo.getRequest().getParameter("businessType_flow");
				Long projectId = Long.parseLong(flowRunInfo.getRequest().getParameter("projectId_flow"));
				if(shenpi!=null&&!"".equals(shenpi)){
					if("1".equals(shenpi)){
						List<SlFundIntent> list1=slFundIntentDao.getByProjectId1(projectId, businessType);
						List<SlActualToCharge>list2=slActualToChargeDao.listbyproject(projectId, businessType);
						Short a=0;
						for(SlFundIntent l:list1){
							l.setIsCheck(a);
							slFundIntentDao.save(l);
						}
						for(SlActualToCharge l:list2){
							l.setIsCheck(a);	
							slActualToChargeService.save(l);
						}
					}
				}
				
		    	Integer shenpiResult = 1;
		    	String creatorId = "1";//默认一个值，为超级管理员。
		     	if(shenpi!=null&&!"".equals(shenpi)){
		    		if("2".equals(shenpi)){
		    			flowRunInfo.setAfresh(true);//表示打回重做
		    			
		    			if(zmRunId!=null&&!"".equals(zmRunId)){
		    				//ProcessForm processForm = processFormDao.getByRunIdTaskName(new Long(zmRunId), "业务主管审核上报材料");
		    				ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(new Long(zmRunId), "zmnCheckMaterial");
		    				if(processForm!=null&&processForm.getCreatorId()!=null){
		    					creatorId = processForm.getCreatorId().toString();
		    				}
		    			}
		    		}
		    	}
		    	shenpiResult = Integer.valueOf(shenpi);
		    	Map<String,Object> vars=new HashMap<String, Object>();
		    	vars.put("presidentResult",shenpiResult);
		    	if("2".equals(shenpi)){
		    		vars.put("flowAssignId",creatorId);
		    	}
				flowRunInfo.getVariables().putAll(vars);*/
		    }
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 风险主管调配任务提交
	 * @param flowRunInfo
	 * @return
	 */
	public Integer assignRiskManager(FlowRunInfo flowRunInfo){
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				String flowAssignId = flowRunInfo.getRequest().getParameter("assignId");
				//String riskManagerOpinion = flowRunInfo.getRequest().getParameter("riskManagerOpinion");
				//String riskManagerTask = flowRunInfo.getRequest().getParameter("riskManagerTask");
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
			    	if(currentForm!=null){
			    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
			    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
			    			flowRunInfo.setAfresh(true);//表示打回重做
			    			if(flowAssignId==null||"".equals(flowAssignId)){
			    				ProcessForm pf = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "zmnCheckMaterial");
			    				if(pf!=null&&pf.getCreatorId()!=null){
			    					flowAssignId = pf.getCreatorId().toString();
			    				}
			    			}/*else{
			    				flowAssignId = "1";
			    			}*/
			    		}
			    	}
					/*if("1".equals(riskManagerOpinion)&&riskManagerTask!=null&&!"".equals(riskManagerTask)){
						vars.put("flowAssignId",riskManagerTask);
					}else if("2".equals(riskManagerOpinion)){
						flowRunInfo.setAfresh(true);//表示打回重做
					}*/
			    	vars.put("flowAssignId",flowAssignId);
					vars.put("allocateTaskResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
			}
			return 1;
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("风险主管调配任务提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 保前综合分析提交(指定风险主管审核处理人：为“风险主管调配任务”的节点处理人。(风险主管角色下的人员))
	 * @param flowRunInfo
	 * @return
	 */
	public Integer beforeSynthesizeAnalyse(FlowRunInfo flowRunInfo){
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String zmRunId = flowRunInfo.getRequest().getParameter("zmRunId");
				if(zmRunId!=null&&!"".equals(zmRunId)){
					String creatorId = "";
					//ProcessForm processForm = processFormDao.getByRunIdTaskName(new Long(zmRunId), "风险主管调配任务");
					ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(new Long(zmRunId), "zmnAllocateTask");
					if(processForm!=null&&processForm.getCreatorId()!=null){
						creatorId = processForm.getCreatorId().toString();
					}
					Map<String,Object> vars=new HashMap<String, Object>();
			    	vars.put("flowAssignId",creatorId);
					flowRunInfo.getVariables().putAll(vars);
				}
			}
			return 1;
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("保前综合分析提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 审保会调配任务
	 * @param flowRunInfo
	 * @param flowAssignId flowAssignId 指定执行人的ID或ID列表，格式如： 领导审批:财务审核:...|1,2:3,4:...),也只可为1,2,3(当下一步仅有一任务时）
	 * @return
	 */
	public Integer assignSbhPartake(FlowRunInfo flowRunInfo){
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String sbhPartake = flowRunInfo.getRequest().getParameter("sbhPartake");
				String shbExternalAdviser = flowRunInfo.getRequest().getParameter("shbExternalAdviser");
				if(sbhPartake!=null&&!"".equals(sbhPartake)){
					Map<String,Object> vars=new HashMap<String, Object>();
					String assignUserIds = sbhPartake;
					if(shbExternalAdviser!=null&&!"".equals(shbExternalAdviser)){
						assignUserIds += ","+shbExternalAdviser;
					}
					//String assignUserIds = "审保会集体决议:外部顾问:|"+sbhPartake+":"+shbExternalAdviser+":";
					vars.put("flowAssignId",assignUserIds);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("设定审保委员提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 违约处理提交更新项目状态
	 * @param flowRunInfo
	 * @return
	 */
	public Integer breakAContractHandle (FlowRunInfo flowRunInfo){
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String zmProjectId = flowRunInfo.getRequest().getParameter("zmProjectId");
				if(zmProjectId!=null&&!"".equals(zmProjectId)){
					GLGuaranteeloanProject glProject = dao.get(new Long(zmProjectId));
					if(glProject!=null){
						glProject.setBmStatus(Constants.PROJECT_STATUS_COMPLETE);
						dao.merge(glProject);
					}
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("违约处理提交下一步出错："+e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer financechargeaffirmFlow(FlowRunInfo flowRunInfo) {
	
		if(flowRunInfo.isBack()){
			return 1;
		}else{
			 try{
		 String projectId=flowRunInfo.getRequest().getParameter("projectId1");
		List<GlBankGuaranteemoney> a=new ArrayList<GlBankGuaranteemoney>();
		a=glBankGuaranteemoneyService.getbyprojId(Long.valueOf(projectId));
		 GlBankGuaranteemoney glBankGuaranteemoney=new GlBankGuaranteemoney();
		 BeanUtil.populateEntity(flowRunInfo.getRequest(), glBankGuaranteemoney, "glBankGuaranteemoney");
		 //表示向银行缴纳保证金
		 if(glBankGuaranteemoney.getBankGuaranteeId()==null && a.size()==0){  //以防其实以保存，但是页面没从新加载，id还是空的，
    			glBankGuaranteemoney.setProjectId(Long.valueOf(projectId));
    			glBankGuaranteemoney.setBusinessType("Guarantee");
    			glBankGuaranteemoney.setOperationType("CompanyBusiness");
    			glBankGuaranteemoney.setIsRelease(Short.parseShort("0"));
    			if(null !=glBankGuaranteemoney.getAccountId()){
		    			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
					   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
		     	    	glBankGuaranteemoneyDao.save(glBankGuaranteemoney);
	     	    	
	     	    	    GlAccountRecord glAccountRecord=new GlAccountRecord();
						glAccountRecord.setCautionAccountId(glBankGuaranteemoney.getAccountId());
						glAccountRecord.setCapitalType(3);
						glAccountRecord.setGlAccountBankId(glBankGuaranteemoney.getGlAccountBankId());
						glAccountRecord.setOprateDate(glBankGuaranteemoney.getFreezeDate());
						glAccountRecord.setOprateMoney(glBankGuaranteemoney.getFreezeMoney());
						glAccountRecord.setProjectId(glBankGuaranteemoney.getProjectId());
						AppUser user=ContextUtil.getCurrentUser();
						glAccountRecord.setHandlePerson(user.getFullname());
						glAccountRecordDao.save(glAccountRecord);
 	    	           saveguaranteemoneyAccount(glBankGuaranteemoney.getGlAccountBankId(),glBankGuaranteemoney.getAccountId());
    			}else{
    				glBankGuaranteemoneyDao.save(glBankGuaranteemoney);
    			}
    }else{	  
    	 GlBankGuaranteemoney orgGlBankGuaranteemoney;
    	 if(a.size() !=0){
	    	 orgGlBankGuaranteemoney=glBankGuaranteemoneyDao.getbyprojId(Long.parseLong(projectId)).get(0);
	     }else{ 	 
		     orgGlBankGuaranteemoney=glBankGuaranteemoneyDao.get(glBankGuaranteemoney.getBankGuaranteeId());
	     }
    	 int same=0;
    	 if(null != glBankGuaranteemoney.getAccountId()){
					
    		    Long accountId=orgGlBankGuaranteemoney.getAccountId();
					Long glAccountBankId=orgGlBankGuaranteemoney.getGlAccountBankId();
					if(!glBankGuaranteemoney.getAccountId().equals(orgGlBankGuaranteemoney.getAccountId())){//改变了保证金账户
						same=1;
					}
				  GlAccountBankCautionmoney glAccountBankCautionmoney = glAccountBankCautionmoneyDao.get(glBankGuaranteemoney.getAccountId());
				   BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
				   orgGlBankGuaranteemoney.setIsRelease(Short.parseShort("0"));
				   orgGlBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
				   glBankGuaranteemoneyDao.save(orgGlBankGuaranteemoney);
				 
				   GlAccountRecord glAccountRecord=new GlAccountRecord();
					 List<GlAccountRecord> GlAccountRecords= glAccountRecordDao.getbyprojectIdcapitalType(Long.parseLong(projectId),3);
					 if(GlAccountRecords.size()!=0){
						 glAccountRecord=GlAccountRecords.get(0);
					 }
					glAccountRecord.setCautionAccountId(orgGlBankGuaranteemoney.getAccountId());
					glAccountRecord.setCapitalType(3);
					glAccountRecord.setGlAccountBankId(orgGlBankGuaranteemoney.getGlAccountBankId());
					glAccountRecord.setOprateDate(orgGlBankGuaranteemoney.getFreezeDate());
					glAccountRecord.setOprateMoney(orgGlBankGuaranteemoney.getFreezeMoney());
					glAccountRecord.setProjectId(orgGlBankGuaranteemoney.getProjectId());
					AppUser user=ContextUtil.getCurrentUser();
					glAccountRecord.setHandlePerson(user.getFullname());
					glAccountRecordDao.save(glAccountRecord);
					if(same==1){
						if(accountId !=null){
							saveguaranteemoneyAccount(glAccountBankId,accountId);
						}
			 	    }
				   saveguaranteemoneyAccount(orgGlBankGuaranteemoney.getGlAccountBankId(),orgGlBankGuaranteemoney.getAccountId());
			}else{
				
				  BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
				  glBankGuaranteemoneyDao.save(orgGlBankGuaranteemoney);
				
			}
    	 }
		 
		 
		    String slActualToChargeData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
			slActualToChargeService.savejson(slActualToChargeData, Long.parseLong(projectId), "Guarantee", Short.parseShort("0"),null);
			 return 1;
    } catch(Exception e) {
		 logger.error("GLGuaranteeloanZmNormalFlowProjectServiceImpl:"+e.getMessage());


			e.printStackTrace();
		    return -1;
		}
	
	 
}
	
	}
	public void saveguaranteemoneyAccount(Long glAccountBankId,Long cautionAccountId){
		GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyDao.get(cautionAccountId);
		List<GlAccountRecord> listRecord =glAccountRecordDao.getallbycautionAccountId(cautionAccountId,0,99999999);
		BigDecimal incomemoney=new BigDecimal(0);
		BigDecimal paymoney =new BigDecimal(0);
		BigDecimal frozenMoney=new BigDecimal(0);
		BigDecimal unFrozenMoney =new BigDecimal(0);
		for(GlAccountRecord l:listRecord){
			if(l.getCapitalType()==1){ //存入
				incomemoney=incomemoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==2){ //支出
				paymoney=paymoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==3){ //冻结
				frozenMoney=frozenMoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==4){ //解冻
				unFrozenMoney=unFrozenMoney.add(l.getOprateMoney());
			}
		}
		BigDecimal sum =glAccountBankCautionmoney.getRawauthorizationMoney().add(incomemoney).subtract(paymoney);
		glAccountBankCautionmoney.setAuthorizationMoney(sum);
		BigDecimal sumsurplusMoney=glAccountBankCautionmoney.getRawsurplusMoney().add(incomemoney).subtract(paymoney).add(unFrozenMoney).subtract(frozenMoney);
		glAccountBankCautionmoney.setSurplusMoney(sumsurplusMoney);
		glAccountBankCautionmoneyDao.save(glAccountBankCautionmoney);
		
		GlAccountBank glAccountBank=glAccountBankDao.get(glAccountBankId);
		List<GlAccountBankCautionmoney> list=glAccountBankCautionmoneyDao.getallbybankId(glAccountBankId);
		BigDecimal authorizationMoney=new BigDecimal(0);
		BigDecimal surplusMoney =new BigDecimal(0);
		for(GlAccountBankCautionmoney l:list){
			authorizationMoney=authorizationMoney.add(l.getAuthorizationMoney());
			surplusMoney=surplusMoney.add(l.getSurplusMoney());
		}
		glAccountBank.setAuthorizationMoney(authorizationMoney);
		glAccountBank.setSurplusMoney(surplusMoney);
		glAccountBankDao.save(glAccountBank);
	}
}
