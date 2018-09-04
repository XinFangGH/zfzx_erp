package com.zhiwei.credit.service.creditFlow.common.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.zhiwei.core.model.BaseProject;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.common.GlobalSupervisemanageDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.service.creditFlow.common.GlobalSupervisemanageService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.flow.JbpmService;

/**
 * 
 * @author 
 *
 */
public class GlobalSupervisemanageServiceImpl extends BaseServiceImpl<GlobalSupervisemanage> implements GlobalSupervisemanageService{
	@SuppressWarnings("unused")
	private GlobalSupervisemanageDao dao;
	private Scheduler scheduler;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private GLGuaranteeloanProjectService gLGuaranteeloanProjectService;
	
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private BpFundProjectDao bpFundProjectDao;
	public GlobalSupervisemanageServiceImpl(GlobalSupervisemanageDao dao) {
		super(dao);
		this.dao=dao;
	}
	/**
	 * 重新设置Trigger的执行时间
	 */
	public void init() {
		try{
			CronTriggerBean trigger = (CronTriggerBean) scheduler.getTrigger("globalSupervisemanageTrigger",Scheduler.DEFAULT_GROUP);  
	        String originConExpression = trigger.getCronExpression();
	        String pushTime = "0 0/2 * * * ?";
	        trigger.setCronExpression(pushTime);  
            scheduler.rescheduleJob("globalSupervisemanageTrigger", Scheduler.DEFAULT_GROUP, trigger); 
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void startSuperviseFlow(GlobalSupervisemanage globalSupervisemanage){
		ContextUtil.setUserId(Long.valueOf(globalSupervisemanage.getDesignSuperviseManagers()));
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		BaseProject project = null;
		String superviseFlowKey = "";
		if(null!=globalSupervisemanage.getBusinessType()&&"LeaseFinance".equals(globalSupervisemanage.getBusinessType())){
			project = flLeaseFinanceProjectService.get(Long.valueOf(globalSupervisemanage.getProjectId()));
			superviseFlowKey = "LeaseFinanceSuperviseFlow";
		}else if(null!=globalSupervisemanage.getBusinessType()&&"Guarantee".equals(globalSupervisemanage.getBusinessType())){
			project = gLGuaranteeloanProjectService.get(Long.valueOf(globalSupervisemanage.getProjectId()));
			superviseFlowKey = "GuaranteeSuperviseFlow";
		}else if(null!=globalSupervisemanage.getBusinessType()&&"SmallLoan".equals(globalSupervisemanage.getBusinessType())){
			BpFundProject pro = bpFundProjectDao.get(Long.valueOf(globalSupervisemanage.getProjectId()));
			superviseFlowKey = "smallLoanSuperviseFlow";
			if(pro!=null){
				Long companyId = new Long(0);
				String isGroupVersion=AppUtil.getSystemIsGroupVersion();
				if(isGroupVersion!=null&&Boolean.valueOf(isGroupVersion)){
					companyId = pro.getCompanyId();
				}
				ProDefinition pdf = proDefinitionDao.getByCompanyIdProcessName(companyId, superviseFlowKey);
				if(pdf!=null){
					String customerName = "";
					Map<String,Object> newVars = new HashMap<String, Object>();
					ProcessRun run = processRunDao.getByBusinessTypeProjectId(pro.getProjectId(), pro.getBusinessType());
					if(run!=null){
						customerName = run.getCustomerName();
					}
					newVars.put("flowAssignId",globalSupervisemanage.getDesignSuperviseManagers());
					newVars.put("projectId",pro.getProjectId());
					newVars.put("oppositeType",pro.getOppositeType());
					newVars.put("businessType",pro.getBusinessType());
					newVars.put("fundProjectId",pro.getId());
					newVars.put("customerName",customerName); 
					newVars.put("projectNumber",pro.getProjectNumber()); 
					newVars.put("superviseManageId", globalSupervisemanage.getSuperviseManageId());
					newFlowRunInfo.getVariables().putAll(newVars);
					newFlowRunInfo.setBusMap(newVars);
					newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
					newFlowRunInfo.setFlowSubject(pro.getProjectName()+"-"+pro.getProjectNumber()+"-贷后监管");
					jbpmService.doStartProcess(newFlowRunInfo);
					globalSupervisemanage.setIsProduceTask(true);
					dao.merge(globalSupervisemanage);
				}
			}
		}
		if(project!=null){
			Long companyId = new Long(0);
			String isGroupVersion=AppUtil.getSystemIsGroupVersion();
			if(isGroupVersion!=null&&Boolean.valueOf(isGroupVersion)){
				companyId = project.getCompanyId();
			}
			ProDefinition pdf = proDefinitionDao.getByCompanyIdProcessName(companyId, superviseFlowKey);
			if(pdf!=null){
				String customerName = "";
				Map<String,Object> newVars = new HashMap<String, Object>();
				ProcessRun run = processRunDao.getByBusinessTypeProjectId(project.getProjectId(), project.getBusinessType());
				if(run!=null){
					customerName = run.getCustomerName();
				}
				newVars.put("flowAssignId",globalSupervisemanage.getDesignSuperviseManagers());
				newVars.put("projectId",project.getProjectId());
				newVars.put("oppositeType",project.getOppositeType());
				newVars.put("businessType",project.getBusinessType());
				newVars.put("customerName",customerName); 
				newVars.put("projectNumber",project.getProjectNumber()); 
				newVars.put("superviseManageId", globalSupervisemanage.getSuperviseManageId());
				newFlowRunInfo.getVariables().putAll(newVars);
				newFlowRunInfo.setBusMap(newVars);
				newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
				newFlowRunInfo.setFlowSubject(project.getProjectName()+"-"+project.getProjectNumber());
				jbpmService.doStartProcess(newFlowRunInfo);
				globalSupervisemanage.setIsProduceTask(true);
				dao.merge(globalSupervisemanage);
			}
		}
	}
	/**
	 * 当前时间大于或等于计划监管时间的时候启动一个监管流程
	 */
	public boolean supervisemanagePlanPush() {
		Date currentDate = new Date();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		List<GlobalSupervisemanage> list = dao.listByDesignSuperviseManageTime(sd.format(currentDate));
		for (GlobalSupervisemanage globalSupervisemanage : list) {
			startSuperviseFlow(globalSupervisemanage);
		}
		return true;
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	@Override
	public List<GlobalSupervisemanage> getListByProjectId(Long projectId,
			String businessType) {
		
		return dao.getListByProjectId(projectId, businessType);
	}
}