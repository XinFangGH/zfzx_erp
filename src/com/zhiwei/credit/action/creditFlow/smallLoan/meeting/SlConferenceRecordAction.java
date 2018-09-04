package com.zhiwei.credit.action.creditFlow.smallLoan.meeting;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.meeting.SlConferenceRecordService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class SlConferenceRecordAction extends BaseAction{
	@Resource
	private SlConferenceRecordService slConferenceRecordService;
	private SlConferenceRecord slConferenceRecord;
	
	@Resource
	private CreditProjectService creditProjectService;
	
	private Long conforenceId;
	private String projId;
	private String businessType;
	
	

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public Long getConforenceId() {
		return conforenceId;
	}

	public void setConforenceId(Long conforenceId) {
		this.conforenceId = conforenceId;
	}

	public SlConferenceRecord getSlConferenceRecord() {
		return slConferenceRecord;
	}

	public void setSlConferenceRecord(SlConferenceRecord slConferenceRecord) {
		this.slConferenceRecord = slConferenceRecord;
	}
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlConferenceRecord> list= slConferenceRecordService.getAll(filter);
		
		Type type=new TypeToken<List<SlConferenceRecord>>(){}.getType();
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
				slConferenceRecordService.remove(new Long(id));
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
		SlConferenceRecord slConferenceRecord=slConferenceRecordService.get(conforenceId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slConferenceRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	public String getByProjectId(){
		SlConferenceRecord slConferenceRecord=slConferenceRecordService.getByProjectId(Long.parseLong(projId),businessType);
		/*if(slConferenceRecord!=null){
			if(slConferenceRecord.getConferenceResult() != null){
				CreditDictionary cd = iDictionaryService.get(slConferenceRecord.getConferenceResult());
				if(cd != null){
					slConferenceRecord.setConferenceResultStr(cd.getValue());
				}
			}
			if(slConferenceRecord.getDecisionType() != null){
				CreditDictionary cd = iDictionaryService.get(slConferenceRecord.getDecisionType());
				if(cd != null){
					slConferenceRecord.setDecisionTypeStr(cd.getValue());
				}
			}
		}*/
		/*Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = null;
		if(slConferenceRecord ==null){
			sb = new StringBuffer("{success:true,data:[{");
			sb.append(gson.toJson(slConferenceRecord));
			sb.append("}]}");
		}else{
			sb = new StringBuffer("{success:true,data:");
			sb.append(gson.toJson(slConferenceRecord));
			sb.append("}");
		}
		
		setJsonString(sb.toString());*/
		
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("conforenceTime");
		sb.append(serializer.exclude(new String[] { "class", "SlConferenceRecord" })
				.serialize(slConferenceRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		slConferenceRecord.setProjectId(Long.parseLong(projId));
		slConferenceRecord.setBusinessType(getRequest().getParameter("businessType"));
		if(slConferenceRecord.getConforenceId()==null){
			slConferenceRecordService.save(slConferenceRecord);
			Long conforenceId=slConferenceRecord.getConforenceId();
			
		}else{
			SlConferenceRecord orgSlConferenceRecord=slConferenceRecordService.get(slConferenceRecord.getConforenceId());
			try{
				BeanUtil.copyNotNullProperties(orgSlConferenceRecord, slConferenceRecord);
				slConferenceRecordService.save(orgSlConferenceRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		String taskId=this.getRequest().getParameter("task_id");
       	String comments=this.getRequest().getParameter("comments");
       	if(null!=taskId && !"".equals(taskId) && null!=comments && !"".equals(comments.trim()))
       	{
       		this.creditProjectService.saveComments(taskId, comments);
       	}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
