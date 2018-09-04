package com.zhiwei.credit.service.creditFlow.smallLoan.meeting.impl;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.smallLoan.meeting.SlConferenceRecordDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.meeting.SlConferenceRecordService;

/**
 * 
 * @author
 * 
 */
public class SlConferenceRecordServiceImpl extends
		BaseServiceImpl<SlConferenceRecord> implements
		SlConferenceRecordService {
	@SuppressWarnings("unused")
	private SlConferenceRecordDao dao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private CreditProjectService creditProjectService;
	//private SlConferenceRecord slConferenceRecord;

	private SlSmallloanProject slSmallloanProject;

	/*public SlConferenceRecord getSlConferenceRecord() {
		return slConferenceRecord;
	}

	public void setSlConferenceRecord(SlConferenceRecord slConferenceRecord) {
		this.slConferenceRecord = slConferenceRecord;
	}*/

	public SlSmallloanProject getSlSmallloanProject() {
		return slSmallloanProject;
	}

	public void setSlSmallloanProject(SlSmallloanProject slSmallloanProject) {
		this.slSmallloanProject = slSmallloanProject;
	}

	public SlConferenceRecordServiceImpl(SlConferenceRecordDao dao) {
		super(dao);
		this.dao = dao;
	}

	public Integer saveAfterFlow(FlowRunInfo flowRunInfo) {
		
		if(flowRunInfo.isBack()){
			return 1;
		}else{
			SlConferenceRecord slConferenceRecord = new SlConferenceRecord();
			String transitionName = flowRunInfo.getTransitionName();
			try {
				BeanUtil.populateEntity(flowRunInfo.getRequest(),slConferenceRecord, "slConferenceRecord");
				if (slConferenceRecord.getConforenceId() == null) {
					dao.save(slConferenceRecord);
				} else {
					// 更新
					SlConferenceRecord orgSlConferenceRecord = dao.get(slConferenceRecord.getConforenceId());
					String decisionT = "";
					String conferenceR = "";
					if("".equals(slConferenceRecord.getDecisionType())||slConferenceRecord.getDecisionType()==null){
						decisionT = orgSlConferenceRecord.getDecisionType();
					}else{
						decisionT = slConferenceRecord.getDecisionType();
					}
					if("".equals(slConferenceRecord.getConferenceResult())||slConferenceRecord.getConferenceResult()==null){
						conferenceR = orgSlConferenceRecord.getConferenceResult();
					}else{
						conferenceR = slConferenceRecord.getConferenceResult();
					}
					
					try {
						BeanUtil.copyNotNullProperties(orgSlConferenceRecord,slConferenceRecord);
								
						orgSlConferenceRecord.setDecisionType(decisionT);
						orgSlConferenceRecord.setConferenceResult(conferenceR);
						dao.save(orgSlConferenceRecord);
					} catch (Exception ex) {
						logger.error(ex.getMessage());
					}
				}
				
				Map<String,Object> vars=new HashMap<String, Object>();
				if(transitionName!=null&&!"".equals(transitionName)){
		    		ProcessForm processForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
		    		
		    		if(processForm!=null){
		    			String flowNodeKey = "slnProjectManagerSurveyed";//默认小贷的尽职调查
		    			ProcessRun processRun = processRunDao.get(processForm.getRunId());
		    			if(processRun!=null){
		    				if("guaranteeNormalFlow".equals(processRun.getProcessName())){
		    					flowNodeKey = "glnProjectManagerSurveyed";//企业经营型贷款常规流程的"业务经理尽职调查"节点
		    				}
		    			}
		    			ProUserAssign userAssign = creditProjectService.getByRunIdActivityName(processForm.getRunId(), transitionName);
		    			if(userAssign!=null&&userAssign.getTaskSequence()!=null&&!"".equals(userAssign.getTaskSequence())){
		    				if(userAssign.getTaskSequence().contains(flowNodeKey)){//打回到小贷常规"尽职调查"或企业经营型贷款常规流程的"业务经理尽职调查"节点
		    					String creatorId = "1";//默认一个值，为超级管理员。
		    					ProcessForm pform = processFormDao.getByRunIdFlowNodeKey(processForm.getRunId(), flowNodeKey);
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
				}
				vars.put("meettingResult",transitionName);
				flowRunInfo.getVariables().putAll(vars);
				
				/*
				
				if("agree".equals(slConferenceRecord.getConferenceResult())||"condition".equals(slConferenceRecord.getConferenceResult())){
					meettingResult = 1;
				}else if("back".equals(slConferenceRecord.getConferenceResult())){
					meettingResult = 2;
					flowRunInfo.setAfresh(true);//表示打回重做
				}else if("end".equals(slConferenceRecord.getConferenceResult())){
					meettingResult = 3;
				}*/
				return 1;

			} catch (Exception e) {
				
				e.printStackTrace();
				return 0;
			}
			//return 1;
		}
		
	}
	
	public SlConferenceRecord getByProjectId(Long projId, String businessType){
		return dao.getByProjectId(projId,businessType);
	}
	
	public Boolean deleteAllConferenceRecordByProjectIdAndBusinessType(Long projId, String businessType){
		SlConferenceRecord scr = null;
		scr = dao.getByProjectId(projId, businessType);
		if(scr != null){
			dao.remove(scr);
		}
		return true;
	}

}