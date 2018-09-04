package com.zhiwei.credit.action.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.hrm.StandSalary;
import com.zhiwei.credit.model.hrm.StandSalaryItem;
import com.zhiwei.credit.service.hrm.StandSalaryItemService;
import com.zhiwei.credit.service.hrm.StandSalaryService;
/**
 * 
 * @author 
 *
 */
public class StandSalaryAction extends BaseAction{
	
	private static short STATUS_DRAFT = (short)0;//草稿
	private static short STATUS_PASS = (short)1;//审核通过
	private static short STATUS_NOT_PASS = (short)2;//审核未通过
	
	@Resource
	private StandSalaryService standSalaryService;
	@Resource
	private StandSalaryItemService standSalaryItemService;
	private StandSalary standSalary;
	private String data;
	private Long standardId;

	private String deleteItemIds;
	
	
	
	public String getDeleteItemIds() {
		return deleteItemIds;
	}

	public void setDeleteItemIds(String deleteItemIds) {
		this.deleteItemIds = deleteItemIds;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Long getStandardId() {
		return standardId;
	}

	public void setStandardId(Long standardId) {
		this.standardId = standardId;
	}

	public StandSalary getStandSalary() {
		return standSalary;
	}

	public void setStandSalary(StandSalary standSalary) {
		this.standSalary = standSalary;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<StandSalary> list= standSalaryService.getAll(filter);
		
		Type type=new TypeToken<List<StandSalary>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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
				standSalaryService.remove(new Long(id));
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
		StandSalary standSalary=standSalaryService.get(standardId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(standSalary));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String getform(){
		StandSalary standSalary=standSalaryService.get(standardId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(standSalary));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	/**
	 * 添加及保存操作
	 */
	public String save(){

		//验证
		boolean pass = false;
		StringBuffer buff = new StringBuffer("{");
		if(standSalary.getStandardId() == null){
			if(standSalaryService.checkStandNo(standSalary.getStandardNo())){
				pass = true;
			}else{
				buff.append("msg:'标准编号已存在,请重新输入.',");
			}
		}else{
			pass = true;
		}
		
		if(pass){
			if(standSalary.getStandardId()!= null){
				//修改旧纪录
				standSalary.setModifyName(ContextUtil.getCurrentUser().getFullname());
				standSalary.setModifyTime(new Date());
			}else{
				standSalary.setSetdownTime(new Date());
				standSalary.setFramer(ContextUtil.getCurrentUser().getFullname());
			}
				standSalary.setStatus(STATUS_DRAFT);
				if(StringUtils.isNotEmpty(deleteItemIds)){
					String[] ids = deleteItemIds.split(",");
					for(String id : ids){
						if(StringUtils.isNotEmpty(id)){
							standSalaryItemService.remove(new Long(id));
						}
					}
				}
				standSalaryService.save(standSalary);
				if(StringUtils.isNotEmpty(data)){
					Gson gson=new Gson();
					StandSalaryItem[] standSalaryItems=gson.fromJson(data, StandSalaryItem[].class);
					for(StandSalaryItem standSalaryItem:standSalaryItems){
						if(standSalaryItem.getItemId()==-1){//若为-1，则代表尚未持久化
							standSalaryItem.setItemId(null);
						}
						standSalaryItem.setStandardId(standSalary.getStandardId());
						standSalaryItemService.save(standSalaryItem);
					}
				}
				buff.append("success:true}");
		}else{
			buff.append("failure:true}");
		}
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	public String check(){
		String status = getRequest().getParameter("status");
		StandSalary checkStandard = standSalaryService.get(standSalary.getStandardId());
		checkStandard.setCheckName(ContextUtil.getCurrentUser().getFullname());
		checkStandard.setCheckTime(new Date());
		checkStandard.setCheckOpinion(standSalary.getCheckOpinion());
		if(StringUtils.isNotEmpty(status) && Short.valueOf(status)==STATUS_PASS){
			checkStandard.setStatus(STATUS_PASS);
		}else{
			checkStandard.setStatus(STATUS_NOT_PASS);
		}
		standSalaryService.save(checkStandard);
		
		return SUCCESS;
	}
	
	/**
	 * 系统按时间生成标准编号给用户
	 * @return
	 */
	public String number(){
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss-SSSS");
		String standardNo = date.format(new Date());
		setJsonString("{success:true,standardNo:'SN"+standardNo+"'}");
		return SUCCESS;
	}
	
	/**
	 * 下拉选择工资标准
	 */
	public String combo(){
		List<StandSalary> list= standSalaryService.findByPassCheck();
		Type type=new TypeToken<List<StandSalary>>(){}.getType();
		StringBuffer buff = new StringBuffer();
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		jsonString=buff.toString();
		return SUCCESS;
	}
}

