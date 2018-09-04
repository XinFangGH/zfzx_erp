package com.zhiwei.credit.action.hrm;
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


import com.zhiwei.credit.model.hrm.StandSalaryItem;
import com.zhiwei.credit.service.hrm.StandSalaryItemService;
/**
 * 
 * @author 
 *
 */
public class StandSalaryItemAction extends BaseAction{
	@Resource
	private StandSalaryItemService standSalaryItemService;
	private StandSalaryItem standSalaryItem;
	
	private Long itemId;

	private Long standardId;
	
	
	public Long getStandardId() {
		return standardId;
	}

	public void setStandardId(Long standardId) {
		this.standardId = standardId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public StandSalaryItem getStandSalaryItem() {
		return standSalaryItem;
	}

	public void setStandSalaryItem(StandSalaryItem standSalaryItem) {
		this.standSalaryItem = standSalaryItem;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<StandSalaryItem> list= null;
		if(standardId != null){
			list = standSalaryItemService.getAllByStandardId(standardId);
		}
		Type type=new TypeToken<List<StandSalaryItem>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		
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
				standSalaryItemService.remove(new Long(id));
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
		StandSalaryItem standSalaryItem=standSalaryItemService.get(itemId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(standSalaryItem));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		standSalaryItemService.save(standSalaryItem);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
