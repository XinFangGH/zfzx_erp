package com.zhiwei.credit.action.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnTicketLossRecord;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnTicketLossRecordService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
/**
 * 
 * @author 
 *
 */
public class PawnTicketLossRecordAction extends BaseAction{
	@Resource
	private PawnTicketLossRecordService pawnTicketLossRecordService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private CreditBaseDao creditBaseDao;
	private PawnTicketLossRecord pawnTicketLossRecord;
	
	private Long lossRecordId;

	public Long getLossRecordId() {
		return lossRecordId;
	}

	public void setLossRecordId(Long lossRecordId) {
		this.lossRecordId = lossRecordId;
	}

	public PawnTicketLossRecord getPawnTicketLossRecord() {
		return pawnTicketLossRecord;
	}

	public void setPawnTicketLossRecord(PawnTicketLossRecord pawnTicketLossRecord) {
		this.pawnTicketLossRecord = pawnTicketLossRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		List<PawnTicketLossRecord> list= pawnTicketLossRecordService.getListByProjectId(Long.valueOf(projectId),businessType);
		StringBuffer buff = new StringBuffer("{success:true,result:");
		
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
				pawnTicketLossRecordService.remove(new Long(id));
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
		PawnTicketLossRecord pawnTicketLossRecord=pawnTicketLossRecordService.get(lossRecordId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(pawnTicketLossRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		try{
			if(pawnTicketLossRecord.getLossRecordId()==null){
				pawnTicketLossRecord.setCreateTime(new Date());
				pawnTicketLossRecordService.save(pawnTicketLossRecord);
				PlPawnProject project=plPawnProjectService.get(pawnTicketLossRecord.getProjectId());
				project.setLockStatus(pawnTicketLossRecord.getLossRecordId());
				plPawnProjectService.save(project);
			}else{
				PawnTicketLossRecord orgPawnTicketLossRecord=pawnTicketLossRecordService.get(pawnTicketLossRecord.getLossRecordId());
				BeanUtil.copyNotNullProperties(orgPawnTicketLossRecord, pawnTicketLossRecord);
				pawnTicketLossRecordService.save(orgPawnTicketLossRecord);
			}
			String uploadIds=this.getRequest().getParameter("uploadIds");
			if(null!=uploadIds && !uploadIds.equals("")){
				String[] ids=uploadIds.split(",");
				for(String fileId:ids){
					FileForm form=(FileForm) creditBaseDao.getById(FileForm.class, Integer.valueOf(fileId));
					form.setMark("pawnTicketLossFile."+pawnTicketLossRecord.getLossRecordId()+"."+pawnTicketLossRecord.getProjectId());
					form.setRemark("pawnTicketLossFile."+pawnTicketLossRecord.getLossRecordId()+"."+pawnTicketLossRecord.getProjectId());
					fileFormService.save(form);
				}
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
