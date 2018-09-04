package com.zhiwei.credit.action.personal;
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

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.personal.HolidayRecord;
import com.zhiwei.credit.service.personal.HolidayRecordService;
/**
 * 
 * @author 
 *
 */
public class HolidayRecordAction extends BaseAction{
	@Resource
	private HolidayRecordService holidayRecordService;
	private HolidayRecord holidayRecord;
	
	private Long recordId;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public HolidayRecord getHolidayRecord() {
		return holidayRecord;
	}

	public void setHolidayRecord(HolidayRecord holidayRecord) {
		this.holidayRecord = holidayRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<HolidayRecord> list= holidayRecordService.getAll(filter);
		
		Type type=new TypeToken<List<HolidayRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
				holidayRecordService.remove(new Long(id));
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
		HolidayRecord holidayRecord=holidayRecordService.get(recordId);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(holidayRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(holidayRecord.getIsAll()==null){
			holidayRecord.setIsAll(HolidayRecord.IS_PERSONAL_HOLIDAY);
		}else{
			holidayRecord.setIsAll(HolidayRecord.IS_ALL_HOLIDAY);
		}
		holidayRecordService.save(holidayRecord);
		
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
