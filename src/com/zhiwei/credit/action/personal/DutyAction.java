package com.zhiwei.credit.action.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.personal.Duty;
import com.zhiwei.credit.model.personal.DutySystem;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.personal.DutyService;
import com.zhiwei.credit.service.personal.DutySystemService;
import com.zhiwei.credit.service.system.AppUserService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class DutyAction extends BaseAction{
	@Resource
	private DutyService dutyService;
	@Resource
	private DutySystemService dutySystemService;
	@Resource
	private AppUserService appUserService;
	
	private Duty duty;
	
	private Long dutyId;

	public Long getDutyId() {
		return dutyId;
	}

	public void setDutyId(Long dutyId) {
		this.dutyId = dutyId;
	}

	public Duty getDuty() {
		return duty;
	}

	public void setDuty(Duty duty) {
		this.duty = duty;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<Duty> list= dutyService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		JSONSerializer serializer=JsonUtil.getJSONSerializer("startTime","endTime");
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
				dutyService.remove(new Long(id));
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
		Duty duty=dutyService.get(dutyId);
		JSONSerializer serializer=JsonUtil.getJSONSerializer("startTime","endTime");
		
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(serializer.serialize(duty));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		
		String systemId=getRequest().getParameter("dutySystemId");

		if(StringUtils.isNotEmpty(systemId)){
			DutySystem dutySystem=dutySystemService.get(new Long(systemId));
			duty.setDutySystem(dutySystem);
		}
		//可能存在多个用户的选择
		String userId=getRequest().getParameter("duty.userId");
		
		String[] uIds=userId.split("[,]");
		
		//用于存储已经存在用户班制的用户姓名
		StringBuffer ssb=new StringBuffer("");
		boolean isExcept=false;
		if(uIds!=null){
			for(int i=0;i<uIds.length;i++){
				AppUser user=appUserService.get(new Long(uIds[i]));
				Duty uDuty=new Duty();
				try{
					//检查该用户目前这段时间内是否已经添加了班制,若添加了则需要提示
					BeanUtil.copyNotNullProperties(uDuty, duty);
					
					boolean isExist=false;
					if(uDuty.getDutyId()==null){	
						isExist=dutyService.isExistDutyForUser(user.getUserId(), uDuty.getStartTime(), uDuty.getEndTime());
					}
					if(isExist){
						isExcept=true;
						ssb.append(user.getFullname()).append(",");
					}else{
						uDuty.setAppUser(user);
						uDuty.setFullname(user.getFullname());
						dutyService.save(uDuty);
					}
					
				}catch(Exception ex){
					logger.error("error:" + ex.getMessage());
				}
			}
			
		}
		
		if(isExcept){//
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			ssb.append("在该时间(").append(sdf.format(duty.getStartTime())).append("至")
			.append(sdf.format(duty.getEndTime())).append(")内已经存在班制!");
		}
		
		setJsonString("{success:true,exception:'"+ssb.toString()+"'}");
		return SUCCESS;
	}
}
