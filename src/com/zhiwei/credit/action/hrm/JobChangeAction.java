package com.zhiwei.credit.action.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.hrm.EmpProfile;
import com.zhiwei.credit.model.hrm.JobChange;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.hrm.EmpProfileService;
import com.zhiwei.credit.service.hrm.JobChangeService;
/**
 * 
 * @author 
 *
 */
public class JobChangeAction extends BaseAction{
	@Resource
	private JobChangeService jobChangeService;
	private JobChange jobChange;
	@Resource
	private EmpProfileService empProfileService;
	private Long changeId;

	public Long getChangeId() {
		return changeId;
	}

	public void setChangeId(Long changeId) {
		this.changeId = changeId;
	}

	public JobChange getJobChange() {
		return jobChange;
	}

	public void setJobChange(JobChange jobChange) {
		this.jobChange = jobChange;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<JobChange> list= jobChangeService.getAll(filter);
		
		Type type=new TypeToken<List<JobChange>>(){}.getType();
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
				jobChangeService.remove(new Long(id));
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
		JobChange jobChange=jobChangeService.get(changeId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		sb.append(gson.toJson(jobChange));
		sb.append("]}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		jobChange.setRegName(ContextUtil.getCurrentUser().getFullname());
		jobChange.setRegTime(new Date());
		jobChangeService.save(jobChange);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 读取更改的信息
	 * @return
	 */
	public String load(){
		String strId=getRequest().getParameter("changeId");
		if(StringUtils.isNotEmpty(strId)){
			jobChange=jobChangeService.get(new Long(strId));
		}
		return "load";
	}
	
	/**
	 * 审核职位更改的信息
	 * @return
	 */
	public String check(){
		AppUser appUser=ContextUtil.getCurrentUser();
		Short status=jobChange.getStatus();
		String changeOpinion=jobChange.getCheckOpinion();
		Long changeId=jobChange.getChangeId();
		if(changeId!=null){
			jobChange=jobChangeService.get(changeId);
			jobChange.setStatus(status);
			jobChange.setCheckOpinion(changeOpinion);
			jobChange.setCheckName(appUser.getFullname());
			jobChange.setCheckTime(new Date());
			jobChangeService.save(jobChange);
			if(status==1){
				Long profileId=jobChange.getProfileId();
				if(profileId!=null){
					EmpProfile empProfile=empProfileService.get(profileId);
					empProfile.setJobId(jobChange.getNewJobId());
					empProfile.setPosition(jobChange.getNewJobName());
					empProfile.setDepId(jobChange.getNewDepId());
					empProfile.setDepName(jobChange.getNewDepName());
					empProfile.setStandardId(jobChange.getNewStandardId());
					empProfile.setStandardMiNo(jobChange.getNewStandardNo());
					empProfile.setStandardName(jobChange.getNewStandardName());
					empProfile.setStandardMoney(jobChange.getNewTotalMoney());
					empProfileService.merge(empProfile);
				}
			}
			setJsonString("{success:true}");
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	
}
