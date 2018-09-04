package com.zhiwei.credit.action.task;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.task.PlanType;
import com.zhiwei.credit.model.task.WorkPlan;
import com.zhiwei.credit.service.task.PlanTypeService;
import com.zhiwei.credit.service.task.WorkPlanService;
/**
 * 
 * @author csx
 *
 */
public class PlanTypeAction extends BaseAction{
	@Resource
	private PlanTypeService planTypeService;
	private PlanType planType;
	@Resource
	private WorkPlanService workPlanService;
	
	private Long typeId;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public PlanType getPlanType() {
		return planType;
	}

	public void setPlanType(PlanType planType) {
		this.planType = planType;
	}

	public String combo(){
		
		StringBuffer sb=new StringBuffer();
		
		List<PlanType> planTypeList=planTypeService.getAll();
		sb.append("[");
		for(PlanType planType:planTypeList){
			sb.append("['").append(planType.getTypeId()).append("','").append(planType.getTypeName()).append("'],");
		}
		if(planTypeList.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlanType> list= planTypeService.getAll(filter);
		
		Type type=new TypeToken<List<PlanType>>(){}.getType();
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
				QueryFilter filter=new QueryFilter(getRequest());
				filter.addFilter("Q_planType.typeId_L_EQ",id);
				List<WorkPlan> list=workPlanService.getAll(filter);
				if(list.size()>0){
					jsonString="{success:false,message:'类型下还有计划，请移走该类型的计划任务后，再删除类型！'}";
					return SUCCESS;
				}
				planTypeService.remove(new Long(id));
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
		PlanType planType=planTypeService.get(typeId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(planType));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		planTypeService.save(planType);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
