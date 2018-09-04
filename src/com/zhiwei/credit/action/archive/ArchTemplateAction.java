package com.zhiwei.credit.action.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.archive.ArchTemplate;
import com.zhiwei.credit.service.archive.ArchTemplateService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class ArchTemplateAction extends BaseAction{
	@Resource
	private ArchTemplateService archTemplateService;
	private ArchTemplate archTemplate;
	
	private Long templateId;

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public ArchTemplate getArchTemplate() {
		return archTemplate;
	}

	public void setArchTemplate(ArchTemplate archTemplate) {
		this.archTemplate = archTemplate;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<ArchTemplate> list= archTemplateService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:[");
		
		Gson gson=new Gson();
		for(ArchTemplate at:list){
			buff.append("{");
			buff.append("\"templateId\":" + at.getTemplateId() + ",");
			buff.append("\"archivesType\":" + gson.toJson(at.getArchivesType().getTypeName()) + ",");
			buff.append("\"tempName\":" + gson.toJson(at.getTempName()) + ",");
			buff.append("\"tempPath\":" + gson.toJson(at.getTempPath()));
			buff.append("},");
		}
		jsonString=buff.substring(0, buff.length()-1)+"]}";
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
				archTemplateService.remove(new Long(id));
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
		ArchTemplate archTemplate=archTemplateService.get(templateId);
		
		JSONSerializer jsonSerializer=JsonUtil.getJSONSerializer();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(jsonSerializer.serialize(archTemplate));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		archTemplateService.save(archTemplate);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
