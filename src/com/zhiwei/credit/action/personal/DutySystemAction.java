package com.zhiwei.credit.action.personal;
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

import com.zhiwei.credit.model.personal.DutySystem;
import com.zhiwei.credit.model.personal.DutySystemSections;

import com.zhiwei.credit.service.personal.DutySystemService;
/**
 * 
 * @author 
 *
 */
public class DutySystemAction extends BaseAction{
	@Resource
	private DutySystemService dutySystemService;
	private DutySystem dutySystem;
	
	private Long systemId;

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public DutySystem getDutySystem() {
		return dutySystem;
	}

	public void setDutySystem(DutySystem dutySystem) {
		this.dutySystem = dutySystem;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<DutySystem> list= dutySystemService.getAll(filter);
		
		Type type=new TypeToken<List<DutySystem>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	//用于表单的数据准备
	public String setting(){
		
		if(systemId!=null){
			dutySystem=dutySystemService.get(systemId);
		}
		StringBuffer buff = new StringBuffer("{success:true,result:[{");
		
		if(dutySystem!=null){
			String[]ids=dutySystem.getSystemSetting().split("[|]");
			String[]desc=dutySystem.getSystemDesc().split("[|]");
			//7 days a week
			if(desc!=null && desc.length==7){
				for(int i=0;i<desc.length;i++){
					buff.append("day").append(i).append(":'").append(desc[i]).append("',");
				}
			}
			if(ids!=null&&ids.length==7){
				for(int i=0;i<ids.length;i++){
					buff.append("dayId").append(i).append(":'").append(ids[i]).append("',");
				}
			}
			
			buff.deleteCharAt(buff.length()-1);
			
		}else{
			buff.append("day0:'',day1:'',day2:'',day3:'',day4:'',day5:'',day6:''")
			.append(",dayId0:'',dayId1:'',dayId2:'',dayId3:'',dayId4:'',dayId5:'',dayId6:''");
		}
		buff.append("}]}");
		setJsonString(buff.toString());
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
				dutySystemService.remove(new Long(id));
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
		DutySystem dutySystem=dutySystemService.get(systemId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(dutySystem));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	//用于选择班制的下拉列表
	public String combo(){
		
		StringBuffer sb=new StringBuffer();
		
		List<DutySystem> dutySystemList=dutySystemService.getAll();
		sb.append("[");
		for(DutySystem dutySystem:dutySystemList){
			sb.append("['").append(dutySystem.getSystemId()).append("','").append(dutySystem.getSystemName()).append("'],");
		}
		if(dutySystemList.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("]");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String data=getRequest().getParameter("data");

		Gson gson=new Gson();
		DutySystemSections[] dss=gson.fromJson(data, DutySystemSections[].class);
		
		dutySystem.setSystemSetting(dss[0].dayIdToString());
		dutySystem.setSystemDesc(dss[0].dayToString());
		
		dutySystemService.save(dutySystem);
		
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
