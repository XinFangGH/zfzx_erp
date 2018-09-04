package com.zhiwei.credit.service.creditFlow.pawn.project.impl;
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
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PlPawnProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.meeting.SlConferenceRecordDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.VPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;

/**
 * 
 * @author 
 *
 */
public class PlPawnProjectServiceImpl extends BaseServiceImpl<PlPawnProject> implements PlPawnProjectService{
	@SuppressWarnings("unused")
	private PlPawnProjectDao dao;
	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	@Resource
	private EnterpriseDao enterpriseDao;
	@Resource
	private PersonDao personDao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private SlConferenceRecordDao slConferenceRecordDao;
	@Resource
	private SlFundIntentService slFundIntentService;
	public PlPawnProjectServiceImpl(PlPawnProjectDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Integer updatePawnInfo(PlPawnProject plPawnProject, Person person,
			Enterprise enterprise, List<EnterpriseShareequity> listES,
			StringBuffer sb) {
		try{
			PlPawnProject persistent=this.dao.get(plPawnProject.getProjectId());
			Short flag1 = 0;
			if (persistent.getOppositeType().equals("company_customer")) {
				flag1 = 0;
				if (listES.size() > 0) {
					for (int i = 0; i < listES.size(); i++) {
						EnterpriseShareequity es = listES.get(i);
						if (es.getId() == null) {
							es.setEnterpriseid(enterprise.getId());
							this.enterpriseShareequityDao.save(es);
						} else {
							EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
									.load(es.getId());
							BeanUtils.copyProperties(es, esPersistent,
									new String[] { "id", "enterpriseid" });
							this.enterpriseShareequityDao.merge(esPersistent);
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
				flag1 = 1;
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
			BeanUtil.copyNotNullProperties(persistent, plPawnProject);
			this.dao .save(persistent);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Integer investigationNextStep(FlowRunInfo flowRunInfo) {
		try {
			String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");
			String degree = flowRunInfo.getRequest().getParameter("degree");
			PlPawnProject project = new PlPawnProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"plPawnProject");
			
			// 保存共同借款人信息
			
			// 保存股东信息
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (null != gudongInfo && !"".equals(gudongInfo)) {
				String[] shareequityArr = gudongInfo.split("@");
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

			

			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			PlPawnProject persistent = this.dao.get(project.getProjectId());
			BeanUtil.copyNotNullProperties(persistent, project);
			
			
			this.dao.merge(persistent);

			Short flag = 0;
			if (persistent.getOppositeType().equals("company_customer")) // 企业
			{
				flag = 0;
				Enterprise enterprise = new Enterprise();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise,
						"enterprise");
				Enterprise epersistent = this.enterpriseDao
						.getById(enterprise.getId());
				epersistent.setEnterprisename(enterprise.getEnterprisename());
				epersistent.setArea(enterprise.getArea());
				epersistent.setShortname(enterprise.getShortname());
				epersistent.setHangyeType(enterprise.getHangyeType());
				epersistent.setOrganizecode(enterprise.getOrganizecode());
				epersistent.setCciaa(enterprise.getCciaa());
				epersistent.setTelephone(enterprise.getTelephone());
				epersistent.setPostcoding(enterprise.getPostcoding());
				epersistent.setRootHangYeType(enterprise.getRootHangYeType());

				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");
				if (null != person.getId() && person.getId() != 0) {
					Person ppersistent = this.personDao
							.getById(epersistent.getLegalpersonid());
					ppersistent.setMarry(person.getMarry());
					ppersistent.setName(person.getName());
					ppersistent.setSex(person.getSex());
					ppersistent.setCardtype(person.getCardtype());
					ppersistent.setCardnumber(person.getCardnumber());
					ppersistent.setCellphone(person.getCellphone());
					ppersistent.setPostcode(person.getPostcode());
					ppersistent.setSelfemail(person.getSelfemail());
					ppersistent.setPostaddress(person.getPostaddress());
					personDao.merge(ppersistent);
				} else {
					Person p = new Person();
					p.setMarry(person.getMarry());
					p.setName(person.getName());
					p.setSex(person.getSex());
					p.setCardtype(person.getCardtype());
					p.setCardnumber(person.getCardnumber());
					p.setCellphone(person.getCellphone());
					p.setPostcode(person.getPostcode());
					p.setSelfemail(person.getSelfemail());
					p.setPostaddress(person.getPostaddress());
					p.setCompanyId(ContextUtil.getLoginCompanyId());
					personDao.save(p);
					epersistent.setLegalpersonid(p.getId());
				}
				enterpriseDao.merge(epersistent);
				if (listES.size() > 0) {
					for (int i = 0; i < listES.size(); i++) {
						EnterpriseShareequity es = listES.get(i);
						if (es.getId() == null) {
							es.setEnterpriseid(epersistent.getId());
							this.enterpriseShareequityDao.save(es);
						} else {
							EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
									.load(es.getId());
							BeanUtils.copyProperties(es, esPersistent,
									new String[] { "id", "enterpriseid" });
							this.enterpriseShareequityDao.merge(esPersistent);
						}
					}
				}
			} else if (persistent.getOppositeType().equals("person_customer")) {
				flag = 1;
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");

				// 更新person信息开始
				Person persistentPerson = this.personDao
						.getById(person.getId());
				persistentPerson.setMarry(person.getMarry());
				persistentPerson.setName(person.getName());
				persistentPerson.setSex(person.getSex());
				persistentPerson.setCardtype(person.getCardtype());
				persistentPerson.setCardnumber(person.getCardnumber());
				persistentPerson.setCellphone(person.getCellphone());
				persistentPerson.setPostcode(person.getPostcode());
				persistentPerson.setSelfemail(person.getSelfemail());
				persistentPerson.setPostaddress(person.getPostaddress());
				personDao.merge(persistentPerson);
				// 更新person信息结束
			}
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款尽调提交任务  信息出错:" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer competentExaminationNextStep(FlowRunInfo flowRunInfo) {
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
				    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "Investigation");
			    				if(processForm!=null&&processForm.getCreatorId()!=null){
			    					String creatorId = processForm.getCreatorId().toString();
			    					vars.put("flowAssignId",creatorId);
			    				}
				    		}else{
				    			
				    		}
				    	}
				    	vars.put("firstInstanceResult",transitionName);
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
	public Integer riskControlDepartmentNextStep(FlowRunInfo flowRunInfo) {
		try{
			Map<String, Object> vars = new HashMap<String, Object>();
			String sbhPartake = flowRunInfo.getRequest().getParameter("sbhPartake");
			if (sbhPartake != null && !"".equals(sbhPartake)) {
				String assignUserIds = sbhPartake;
				vars.put("flowAssignId", assignUserIds);
				flowRunInfo.getVariables().putAll(vars);
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer riskOfficerReviewNextStep(FlowRunInfo flowRunInfo) {
		try
		 {       
			    if(flowRunInfo.isBack()){
			    	return 1;
			    }else{
			    	String transitionName = flowRunInfo.getTransitionName();
			    	Map<String,Object> vars=new HashMap<String, Object>();
			      	if(transitionName!=null&&!"".equals(transitionName)){
			    		if(transitionName.contains("终止")||transitionName.contains("结束")){
			      			flowRunInfo.setStop(true);
			      			String projectId=flowRunInfo.getRequest().getParameter("plprojectId");
			      			if(null!=projectId && !"".equals(projectId)){
			      				PlPawnProject project=dao.get(Long.valueOf(projectId));
			      				project.setProjectStatus(Short.valueOf("3"));
			      				dao.save(project);
			      			}
			    		}else{
				      		
				      		ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				      		
					    	if(currentForm!=null){
					    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
					    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
					    			flowRunInfo.setAfresh(true);//表示打回重做
					    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "firstInstance");
				    				if(processForm!=null&&processForm.getCreatorId()!=null){
				    					String creatorId = processForm.getCreatorId().toString();
				    					vars.put("flowAssignId",creatorId);
				    				}
					    		}
					    	}
					    	
			    		}
			    		vars.put("riskOfficerReviewResult",transitionName);
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
	public Integer directorOfAuditRiskNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName!=null&&!"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		      			String projectId=flowRunInfo.getRequest().getParameter("plprojectId");
		      			if(null!=projectId && !"".equals(projectId)){
		      				PlPawnProject project=dao.get(Long.valueOf(projectId));
		      				project.setProjectStatus(Short.valueOf("3"));
		      				dao.save(project);
		      			}
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm!=null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}else{
								if(transitionName.equals("线上评审会决议")){
					    			String sbhPartake = flowRunInfo.getRequest().getParameter("sbhPartake");
					    			if (sbhPartake != null && !"".equals(sbhPartake)) {
					    				String assignUserIds = sbhPartake;
					    				vars.put("flowAssignId", assignUserIds);
					    			}
								}
				    		}
						}
		    		}
					vars.put("directorOfAuditRiskResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("典当-风险审主管核提交下一步出错："+e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer summaryReviewCommentsAndSummaryNextStep(FlowRunInfo flowRunInfo) {
		try
		 {       
			    if(flowRunInfo.isBack()){
			    	return 1;
			    }else{
			    	String transitionName = flowRunInfo.getTransitionName();
			    	Map<String,Object> vars=new HashMap<String, Object>();
			      	if(transitionName!=null&&!"".equals(transitionName)){
			    		if(transitionName.contains("终止")||transitionName.contains("结束")){
			      			flowRunInfo.setStop(true);
			      			String projectId=flowRunInfo.getRequest().getParameter("plprojectId");
			      			if(null!=projectId && !"".equals(projectId)){
			      				PlPawnProject project=dao.get(Long.valueOf(projectId));
			      				project.setProjectStatus(Short.valueOf("3"));
			      				dao.save(project);
			      			}
			    		}else{
				      		
				      		ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				      		
					    	if(currentForm!=null){
					    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
					    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
					    			flowRunInfo.setAfresh(true);//表示打回重做
					    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "firstInstance");
				    				if(processForm!=null&&processForm.getCreatorId()!=null){
				    					String creatorId = processForm.getCreatorId().toString();
				    					vars.put("flowAssignId",creatorId);
				    				}
					    		}else{
					    			PlPawnProject project = new PlPawnProject();
					    			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"plPawnProject");
					    			PlPawnProject persistent = this.dao.get(project.getProjectId());
					    			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
					    			BeanUtil.copyNotNullProperties(persistent, project);
					    			this.dao.merge(persistent);
					    			SlConferenceRecord slConferenceRecord = new SlConferenceRecord();
					    			BeanUtil.populateEntity(flowRunInfo.getRequest(), slConferenceRecord,"slConferenceRecord");
					    			if(slConferenceRecord.getConforenceId()==null){
					    				slConferenceRecordDao.save(slConferenceRecord);
					    				Long conforenceId=slConferenceRecord.getConforenceId();
					    			}else{
					    				SlConferenceRecord orgSlConferenceRecord=slConferenceRecordDao.get(slConferenceRecord.getConforenceId());
					    				BeanUtil.copyNotNullProperties(orgSlConferenceRecord, slConferenceRecord);
					    				slConferenceRecordDao.save(orgSlConferenceRecord);
					    			}
					    		}
					    	}
					    	
			    		}
			    		vars.put("summaryReviewIdeasResult",transitionName);
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
	public Integer summaryReviewCommentsNextStep(FlowRunInfo flowRunInfo) {
		try
		 {       
			    if(flowRunInfo.isBack()){
			    	return 1;
			    }else{
			    	String transitionName = flowRunInfo.getTransitionName();
			    	Map<String,Object> vars=new HashMap<String, Object>();
			      	if(transitionName!=null&&!"".equals(transitionName)){
			    		if(transitionName.contains("终止")||transitionName.contains("结束")){
			      			flowRunInfo.setStop(true);
			      			String projectId=flowRunInfo.getRequest().getParameter("plprojectId");
			      			if(null!=projectId && !"".equals(projectId)){
			      				PlPawnProject project=dao.get(Long.valueOf(projectId));
			      				project.setProjectStatus(Short.valueOf("3"));
			      				dao.save(project);
			      			}
			    		}else{
				      		
				      		ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				      		
					    	if(currentForm!=null){
					    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
					    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
					    			flowRunInfo.setAfresh(true);//表示打回重做
					    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "firstInstance");
				    				if(processForm!=null&&processForm.getCreatorId()!=null){
				    					String creatorId = processForm.getCreatorId().toString();
				    					vars.put("flowAssignId",creatorId);
				    				}
					    		}
					    	}
					    	
			    		}
			    		vars.put("summaryAssessmentOpinionResult",transitionName);
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
	public Integer generalManagerApprovalNextStep(FlowRunInfo flowRunInfo) {
		try
		 {       
			    if(flowRunInfo.isBack()){
			    	return 1;
			    }else{
			    	String transitionName = flowRunInfo.getTransitionName();
			    	Map<String,Object> vars=new HashMap<String, Object>();
			      	if(transitionName!=null&&!"".equals(transitionName)){
			    		if(transitionName.contains("终止")||transitionName.contains("结束")){
			      			flowRunInfo.setStop(true);
			      			String projectId=flowRunInfo.getRequest().getParameter("plprojectId");
			      			if(null!=projectId && !"".equals(projectId)){
			      				PlPawnProject project=dao.get(Long.valueOf(projectId));
			      				project.setProjectStatus(Short.valueOf("3"));
			      				dao.save(project);
			      			}
			    		}
			    		vars.put("generalManagerApprovalResult",transitionName);
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
	public Integer paymentConfirmationNextStep(FlowRunInfo flowRunInfo) {
		try{

			String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
			String projectId=flowRunInfo.getRequest().getParameter("projectId");
			PlPawnProject persistent = dao.get(Long.valueOf(projectId));
			String startDate=flowRunInfo.getRequest().getParameter("plPawnProject.startDate");
			String intentDate=flowRunInfo.getRequest().getParameter("plPawnProject.intentDate");
			List<SlFundIntent> oldList = slFundIntentService.getByProjectId(Long.valueOf(projectId), persistent.getBusinessType());
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
			persistent.setStartDate(DateUtil.parseDate(startDate,"yyyy-MM-dd"));
			persistent.setIntentDate(DateUtil.parseDate(intentDate,"yyyy-MM-dd"));
			persistent.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
			this.dao.merge(persistent);
		
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer planRecognitionNextStep(FlowRunInfo flowRunInfo) {
		try{

			String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
			String projectId=flowRunInfo.getRequest().getParameter("projectId");
			PlPawnProject persistent = dao.get(Long.valueOf(projectId));
			List<SlFundIntent> oldList = slFundIntentService.getByProjectId(Long.valueOf(projectId), persistent.getBusinessType());
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
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer fileVerificationNextStep(FlowRunInfo flowRunInfo) {
		try
		 {       
			    if(flowRunInfo.isBack()){
			    	return 1;
			    }else{
			    	String transitionName = flowRunInfo.getTransitionName();
			    	Map<String,Object> vars=new HashMap<String, Object>();
			      	if(transitionName!=null&&!"".equals(transitionName)){
			    		if(transitionName.contains("终止")||transitionName.contains("结束")){
			      			flowRunInfo.setStop(true);
			    		}else{
				      		
				      		ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				      		
					    	if(currentForm!=null){
					    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
					    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
					    			flowRunInfo.setAfresh(true);//表示打回重做
					    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "submitTheFiling");
				    				if(processForm!=null&&processForm.getCreatorId()!=null){
				    					String creatorId = processForm.getCreatorId().toString();
				    					vars.put("flowAssignId",creatorId);
				    				}
					    		}
					    	}
					    	
			    		}
			    		vars.put("fileVerificationAndConfirmationResult",transitionName);
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
	public Integer relevantFormalitiesNextStep(FlowRunInfo flowRunInfo) {
		try{
			String phnumber=flowRunInfo.getRequest().getParameter("plPawnProject.phnumber");
			String projectId=flowRunInfo.getRequest().getParameter("projectId");
			PlPawnProject project=this.dao.get(Long.valueOf(projectId));
			if(null!=phnumber && !"".equals(phnumber)){
				project.setPhnumber(phnumber);
				this.dao.save(project);
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<VPawnProject> getProjectList(String userIdsStr,
			Short[] projectStatus, PagingBean pb, HttpServletRequest request) {
		
		return dao.getProjectList(userIdsStr, projectStatus, pb, request);
	}

	@Override
	public VPawnProject getByProjectId(Long projectId) {
		return dao.getByProjectId(projectId);
	}
	
	@Override
	public List<VPawnProject> getPawnList(String projectNum,String projectName,int start,int limit,String companyId) {
		
		return dao.getPawnList(projectNum, projectName, start, limit,companyId);
	}

	@Override
	public long getPawnNum(String projectNum, String projectName,
			String companyId) {
		return dao.getLeaseFinanceNum(projectNum, projectName,companyId);
	}

}