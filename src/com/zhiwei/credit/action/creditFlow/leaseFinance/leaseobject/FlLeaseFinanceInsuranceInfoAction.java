package com.zhiwei.credit.action.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
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


import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfo;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfoService;
/**
 * 
 * @author 
 *
 */
public class FlLeaseFinanceInsuranceInfoAction extends BaseAction{
	@Resource
	private FlLeaseFinanceInsuranceInfoService flLeaseFinanceInsuranceInfoService;
	private FlLeaseFinanceInsuranceInfo flLeaseFinanceInsuranceInfo;
	
	private Long insuranceId;
	private Long projectId;
	
	

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getInsuranceId() {
		return insuranceId;
	}

	public void setInsuranceId(Long insuranceId) {
		this.insuranceId = insuranceId;
	}

	public FlLeaseFinanceInsuranceInfo getFlLeaseFinanceInsuranceInfo() {
		return flLeaseFinanceInsuranceInfo;
	}

	public void setFlLeaseFinanceInsuranceInfo(FlLeaseFinanceInsuranceInfo flLeaseFinanceInsuranceInfo) {
		this.flLeaseFinanceInsuranceInfo = flLeaseFinanceInsuranceInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
//		QueryFilter filter=new QueryFilter(getRequest());
		List<FlLeaseFinanceInsuranceInfo> list= flLeaseFinanceInsuranceInfoService.getListByLeaseObjectId(projectId);
		
		Type type=new TypeToken<List<FlLeaseFinanceInsuranceInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
				flLeaseFinanceInsuranceInfoService.remove(new Long(id));
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
		FlLeaseFinanceInsuranceInfo flLeaseFinanceInsuranceInfo=flLeaseFinanceInsuranceInfoService.get(insuranceId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(flLeaseFinanceInsuranceInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(flLeaseFinanceInsuranceInfo.getInsuranceId()==null){
			flLeaseFinanceInsuranceInfoService.save(flLeaseFinanceInsuranceInfo);
		}else{
			FlLeaseFinanceInsuranceInfo orgFlLeaseFinanceInsuranceInfo=flLeaseFinanceInsuranceInfoService.get(flLeaseFinanceInsuranceInfo.getInsuranceId());
			try{
				BeanUtil.copyNotNullProperties(orgFlLeaseFinanceInsuranceInfo, flLeaseFinanceInsuranceInfo);
				flLeaseFinanceInsuranceInfoService.save(orgFlLeaseFinanceInsuranceInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
