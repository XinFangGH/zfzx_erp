package com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProjectDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProject;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProjectService;

/**
 * 
 * @author 
 *
 */
public class SgLawsuitguaranteeProjectServiceImpl extends BaseServiceImpl<SgLawsuitguaranteeProject> implements SgLawsuitguaranteeProjectService{
	@SuppressWarnings("unused")
	private SgLawsuitguaranteeProjectDao dao;
	
	public SgLawsuitguaranteeProjectServiceImpl(SgLawsuitguaranteeProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	@Resource
	private EnterpriseDao enterpriseDao;
	@Resource
	private PersonDao personDao;
	@Resource
	private SlActualToChargeDao slActualToChargeDao;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	
	@Override
	public Integer updateInfo(SgLawsuitguaranteeProject sgLawsuitguaranteeProject,
			Person person, Enterprise enterprise,
			List<EnterpriseShareequity> listES,List<SlActualToCharge> slActualToCharges ) {
		try
		{
		
			SgLawsuitguaranteeProject persistent=this.dao.get(sgLawsuitguaranteeProject.getProjectId());
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
    		BeanUtils.copyProperties(sgLawsuitguaranteeProject, persistent,new String[] {"id","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType"});
    		
    		

    	    	
        		for(SlActualToCharge a:slActualToCharges){
        			this.slActualToChargeDao.save(a);
        			
        		}
    		this.dao.merge(persistent);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer updateProject(FlowRunInfo flowRunInfo) {
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
		    	
		    	SgLawsuitguaranteeProject project =new SgLawsuitguaranteeProject();
		    	BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "sgLawsuitguaranteeProject");
		    	SgLawsuitguaranteeProject  persistent=this.dao.get(project.getProjectId());
		    	BeanUtils.copyProperties(project, persistent,new String[] {"id","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType"});
		    	
				//费用收支
			 
				String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
				slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"BeNotFinancing",Short.parseShort("0"),null);
	    		
		    	this.dao.merge(persistent);
		       	if(persistent.getOppositeType().equals("company_customer")) //企业
	        	{   
			   		 Enterprise enterprise=new Enterprise();
			   	     BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
			   	     Enterprise epersistent=this.enterpriseDao.getById(enterprise.getId());
			   	     BeanUtils.copyProperties(enterprise, epersistent,new String[] {"id","legalpersonid"});
			   	     this.enterpriseDao.merge(epersistent);
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
		     		 //更新person信息结束
			   	     
			   	     BeanUtils.copyProperties(person, ppersistent,new String[] {"id"});
			   	     this.personDao.merge(ppersistent);
			   	     
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
			   logger.error("受理申请执行下一步  出错:"+e.getMessage());
			   return 0;
		}
	}

	@Override
	public Integer flowupdateComments(FlowRunInfo flowRunInfo) {
		 try
		 {       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }
		 	String transitionName = flowRunInfo.getTransitionName();
			if(transitionName!=null&&!"".equals(transitionName)){
				Map<String,Object> vars=new HashMap<String, Object>();
	    		ProcessForm processForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
	    		if(processForm!=null){
	    			ProUserAssign userAssign = creditProjectService.getByRunIdActivityName(processForm.getRunId(), transitionName);
	    			if(userAssign!=null&&userAssign.getTaskSequence()!=null&&!"".equals(userAssign.getTaskSequence())){
	    				if(userAssign.getTaskSequence().contains("lfAcceptAndHearACase")){//打回到"业务经理尽职调查"
	    					String creatorId = "1";//默认一个值，为超级管理员。
	    					ProcessForm pform = processFormDao.getByRunIdFlowNodeKey(processForm.getRunId(), "lfAcceptAndHearACase");
		    				if(pform!=null&&pform.getCreatorId()!=null){
		    					creatorId = pform.getCreatorId().toString();
		    					vars.put("flowAssignId",creatorId);
		    				}
	    				}
	    			}
	    		}
	    		
	    		vars.put("ministryOfLawExamineResult",transitionName);
	    		flowRunInfo.getVariables().putAll(vars);
			}
			    return 1;
		 }
		 catch (Exception e) {
			  e.printStackTrace();
			  logger.error("法务审核执行下一步  出错:"+e.getMessage());
			  return 0;
		}
}

	@Override
	public Integer flowupdatePresidentComments(FlowRunInfo flowRunInfo) {
		 try
		 {       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }
		 	String transitionName = flowRunInfo.getTransitionName();
			if(transitionName!=null&&!"".equals(transitionName)){
				Map<String,Object> vars=new HashMap<String, Object>();
	    		ProcessForm processForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
	    		if(processForm!=null){
	    			ProUserAssign userAssign = creditProjectService.getByRunIdActivityName(processForm.getRunId(), transitionName);
	    			if(userAssign!=null&&userAssign.getTaskSequence()!=null&&!"".equals(userAssign.getTaskSequence())){
	    				if(userAssign.getTaskSequence().contains("lfAcceptAndHearACase")){//打回到"业务经理尽职调查"
	    					String creatorId = "1";//默认一个值，为超级管理员。
	    					ProcessForm pform = processFormDao.getByRunIdFlowNodeKey(processForm.getRunId(), "lfAcceptAndHearACase");
		    				if(pform!=null&&pform.getCreatorId()!=null){
		    					creatorId = pform.getCreatorId().toString();
		    					vars.put("flowAssignId",creatorId);
		    				}
	    				}
	    			}
	    		}
	    		
	    		vars.put("presidentExamineResult",transitionName);
	    		flowRunInfo.getVariables().putAll(vars);
			}
			    return 1;
		 }
		 catch (Exception e) {
			  e.printStackTrace();
			  logger.error("总裁审批执行下一步  出错:"+e.getMessage());
			  return 0;
		}
	}
	public Integer flowintentcheck(FlowRunInfo flowRunInfo) {
		if(flowRunInfo.isBack()){
	    	return 1;
	    }
		String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
		Long flow_projectId = Long.valueOf(flowRunInfo.getRequest().getParameter("flow_projectId"));
		slActualToChargeService.savejson(slActualToChargeJson, flow_projectId,"BeNotFinancing",Short.parseShort("0"),null);
		
		return 1;
	}
}