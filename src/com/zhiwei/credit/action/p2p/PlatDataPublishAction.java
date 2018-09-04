package com.zhiwei.credit.action.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.*;
import javax.annotation.Resource;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.p2p.Operate;
import com.zhiwei.credit.model.p2p.PlatDataPublish;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.p2p.PlatDataPublishService;
/**
 * 
 * @author 
 *
 */
public class PlatDataPublishAction extends BaseAction{
	@Resource
	private PlatDataPublishService platDataPublishService;
	private PlatDataPublish platDataPublish;
	
	private Long publishId;
	private Integer typeId;
    
	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Long getPublishId() {
		return publishId;
	}

	public void setPublishId(Long publishId) {
		this.publishId = publishId;
	}

	public PlatDataPublish getPlatDataPublish() {
		return platDataPublish;
	}

	public void setPlatDataPublish(PlatDataPublish platDataPublish) {
		this.platDataPublish = platDataPublish;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlatDataPublish> list= platDataPublishService.getAll(filter);
		
		Type type=new TypeToken<List<PlatDataPublish>>(){}.getType();
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
				platDataPublishService.remove(new Long(id));
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
		PlatDataPublish platDataPublish=null;
		List<PlatDataPublish> list=platDataPublishService.getAll();
		if(list!=null){
			if(typeId!=null){
				if(list.size()>=typeId&&typeId>0){
					platDataPublish=list.get(typeId-1);
				}
			}else{
				platDataPublish=list.get(0);
			}
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(platDataPublish));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(platDataPublish.getPublishId()==null){
			AppUser user= ContextUtil.getCurrentUser();
			platDataPublish.setCreator(user.getFullname());
			platDataPublish.setCreatorId(user.getUserId());
			platDataPublish.setCreateDate(new Date());
			platDataPublishService.save(platDataPublish);
		}else{
			PlatDataPublish orgPlatDataPublish=platDataPublishService.get(platDataPublish.getPublishId());
			try{
				BeanUtil.copyNotNullProperties(orgPlatDataPublish, platDataPublish);
				platDataPublishService.save(orgPlatDataPublish);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 *平台数据详情
	 *
	 * @auther: XinFang
	 * @date: 2018/7/4 17:43
	 */
	public String show(){

		String startDate = this.getRequest().getParameter("startDate");
		String endDate = this.getRequest().getParameter("endDate");
		Gson gson = new Gson();
		Operate operate =  platDataPublishService.showAll(startDate,endDate);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(operate)).append("}");
		System.out.println(sb);
		setJsonString(sb.toString());
		return  SUCCESS;
	}
}
