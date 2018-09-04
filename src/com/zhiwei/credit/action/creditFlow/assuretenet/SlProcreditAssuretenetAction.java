package com.zhiwei.credit.action.creditFlow.assuretenet;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com/
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;
import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.assuretenet.SlProcreditAssuretenet;
import com.zhiwei.credit.service.creditFlow.assuretenet.SlProcreditAssuretenetService;
/**
 * 贷款准入原则处理
 * @author zhangyl
 *
 */
public class SlProcreditAssuretenetAction extends BaseAction{
	@Resource
	private SlProcreditAssuretenetService slProcreditAssuretenetService;
	private SlProcreditAssuretenet slProcreditAssuretenet;
	
	private Long id;
	private String projId;
	private String businessType;
    
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public SlProcreditAssuretenetService getSlProcreditAssuretenetService() {
		return slProcreditAssuretenetService;
	}

	public void setSlProcreditAssuretenetService(
			SlProcreditAssuretenetService slProcreditAssuretenetService) {
		this.slProcreditAssuretenetService = slProcreditAssuretenetService;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}

	public SlProcreditAssuretenet getSlProcreditAssuretenet() {
		return slProcreditAssuretenet;
	}

	public void setSlProcreditAssuretenet(SlProcreditAssuretenet slProcreditAssuretenet) {
		this.slProcreditAssuretenet = slProcreditAssuretenet;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		List<SlProcreditAssuretenet> list= slProcreditAssuretenetService.getByProjId(projId, businessType);
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list));
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
				slProcreditAssuretenetService.remove(new Long(id));
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
		SlProcreditAssuretenet slProcreditAssuretenet=slProcreditAssuretenetService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slProcreditAssuretenet));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slProcreditAssuretenet.getAssuretenetId()!=null){
			
			SlProcreditAssuretenet orgSlProcreditAssuretenet=slProcreditAssuretenetService.get(slProcreditAssuretenet.getAssuretenetId());
			if(null!=slProcreditAssuretenet.getBusinessmanageropinion()
					&&!"".equals(slProcreditAssuretenet.getBusinessmanageropinion())){
				orgSlProcreditAssuretenet.setBusinessmanageropinion(slProcreditAssuretenet.getBusinessmanageropinion());
			}
			if(null!=slProcreditAssuretenet.getRiskmanageropinion()
					&&!"".equals(slProcreditAssuretenet.getRiskmanageropinion())){
				orgSlProcreditAssuretenet.setRiskmanageropinion(slProcreditAssuretenet.getRiskmanageropinion());
			}
			
			slProcreditAssuretenetService.merge(orgSlProcreditAssuretenet);
			setJsonString("{success:true}");
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;		
	}
	
}
