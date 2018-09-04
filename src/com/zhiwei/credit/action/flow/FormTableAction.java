package com.zhiwei.credit.action.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
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


import com.zhiwei.credit.model.flow.FormTable;
import com.zhiwei.credit.service.flow.FormTableGenService;
import com.zhiwei.credit.service.flow.FormTableService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class FormTableAction extends BaseAction{
	@Resource
	private FormTableService formTableService;
	private FormTable formTable;
	@Resource
	private FormTableGenService formTableGenService;
	
	private Long tableId;

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public FormTable getFormTable() {
		return formTable;
	}

	public void setFormTable(FormTable formTable) {
		this.formTable = formTable;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<FormTable> list= formTableService.getAll(filter);
		Type type=new TypeToken<List<FormTable>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		
		JSONSerializer serializer=new JSONSerializer();
		buff.append(serializer.serialize(list));
						

		
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
				formTableService.remove(new Long(id));
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
		FormTable formTable=formTableService.get(tableId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(formTable));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(formTable.getTableId()==null){
			formTableService.save(formTable);
		}else{
			FormTable orgFormTable=formTableService.get(formTable.getTableId());
			try{
				BeanUtil.copyNotNullProperties(orgFormTable, formTable);
				formTableService.save(orgFormTable);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
