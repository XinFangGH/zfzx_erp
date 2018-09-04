package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.system.TypeKey;
import com.zhiwei.credit.service.system.TypeKeyService;
/**
 * 
 * @author 
 *
 */
public class TypeKeyAction extends BaseAction{
	@Resource
	private TypeKeyService typeKeyService;
	private TypeKey typeKey;
	
	private Long typekeyId;

	public Long getTypekeyId() {
		return typekeyId;
	}

	public void setTypekeyId(Long typekeyId) {
		this.typekeyId = typekeyId;
	}

	public TypeKey getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(TypeKey typeKey) {
		this.typeKey = typeKey;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<TypeKey> list= typeKeyService.getAll(filter);
		
		Type type=new TypeToken<List<TypeKey>>(){}.getType();
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
				typeKeyService.remove(new Long(id));
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
		TypeKey typeKey=typeKeyService.get(typekeyId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(typeKey));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		typeKeyService.save(typeKey);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String combo(){
		List<TypeKey> list=typeKeyService.getAll();
		StringBuffer sb=new StringBuffer("[");
		for(TypeKey typeKey:list){
			if(sb.length()>1){
				sb.append(",");
			}
			sb.append("['").append(typeKey.getTypeKey()).append("','").append(typeKey.getTypeName()).append("(").append(typeKey.getTypeKey()).append(")").append("']");
		}
		sb.append("]");
		setJsonString(sb.toString());
		return SUCCESS;
	}
}
