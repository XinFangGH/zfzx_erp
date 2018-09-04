package com.zhiwei.credit.action.creditFlow.pawn.project;
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

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.pawn.project.PawnTicketReissueRecord;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnTicketReissueRecordService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
/**
 * 
 * @author 
 *
 */
public class PawnTicketReissueRecordAction extends BaseAction{
	@Resource
	private PawnTicketReissueRecordService pawnTicketReissueRecordService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	private PawnTicketReissueRecord pawnTicketReissueRecord;
	
	private Long reissueRecordId;

	public Long getReissueRecordId() {
		return reissueRecordId;
	}

	public void setReissueRecordId(Long reissueRecordId) {
		this.reissueRecordId = reissueRecordId;
	}

	public PawnTicketReissueRecord getPawnTicketReissueRecord() {
		return pawnTicketReissueRecord;
	}

	public void setPawnTicketReissueRecord(PawnTicketReissueRecord pawnTicketReissueRecord) {
		this.pawnTicketReissueRecord = pawnTicketReissueRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		List<PawnTicketReissueRecord> list=pawnTicketReissueRecordService.getListByProjectId(Long.valueOf(projectId), businessType);
		StringBuffer buff=new StringBuffer("{success:true,result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
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
				pawnTicketReissueRecordService.remove(new Long(id));
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
		PawnTicketReissueRecord pawnTicketReissueRecord=pawnTicketReissueRecordService.get(reissueRecordId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(pawnTicketReissueRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		try{
			PlPawnProject project=plPawnProjectService.get(pawnTicketReissueRecord.getProjectId());
			if(pawnTicketReissueRecord.getReissueRecordId()==null){
				pawnTicketReissueRecord.setLossRecordId(project.getLockStatus());
				pawnTicketReissueRecordService.save(pawnTicketReissueRecord);
				project.setLockStatus(Long.valueOf(0));
				plPawnProjectService.save(project);
			}else{
				PawnTicketReissueRecord orgPawnTicketReissueRecord=pawnTicketReissueRecordService.get(pawnTicketReissueRecord.getReissueRecordId());
				
					BeanUtil.copyNotNullProperties(orgPawnTicketReissueRecord, pawnTicketReissueRecord);
					pawnTicketReissueRecordService.save(orgPawnTicketReissueRecord);
				
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
