package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
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


import com.zhiwei.credit.model.customer.CsEnterpriseShareequity;
import com.zhiwei.credit.service.customer.CsEnterpriseShareequityService;
/**
 * 
 * @author 
 *
 */
public class CsEnterpriseShareequityAction extends BaseAction{
	@Resource
	private CsEnterpriseShareequityService csEnterpriseShareequityService;
	private CsEnterpriseShareequity csEnterpriseShareequity;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsEnterpriseShareequity getCsEnterpriseShareequity() {
		return csEnterpriseShareequity;
	}

	public void setCsEnterpriseShareequity(CsEnterpriseShareequity csEnterpriseShareequity) {
		this.csEnterpriseShareequity = csEnterpriseShareequity;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<CsEnterpriseShareequity> list= csEnterpriseShareequityService.getAll(filter);
		
		Type type=new TypeToken<List<CsEnterpriseShareequity>>(){}.getType();
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
				csEnterpriseShareequityService.remove(new Long(id));
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
		CsEnterpriseShareequity csEnterpriseShareequity=csEnterpriseShareequityService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csEnterpriseShareequity));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csEnterpriseShareequity.getId()==null){
			csEnterpriseShareequityService.save(csEnterpriseShareequity);
		}else{
			CsEnterpriseShareequity orgCsEnterpriseShareequity=csEnterpriseShareequityService.get(csEnterpriseShareequity.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCsEnterpriseShareequity, csEnterpriseShareequity);
				csEnterpriseShareequityService.save(orgCsEnterpriseShareequity);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
