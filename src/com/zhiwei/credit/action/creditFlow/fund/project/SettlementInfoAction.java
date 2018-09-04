package com.zhiwei.credit.action.creditFlow.fund.project;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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


import com.zhiwei.credit.model.creditFlow.fund.project.SettlementInfo;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementInfoService;
/**
 * 
 * @author 
 *
 */
public class SettlementInfoAction extends BaseAction{
	@Resource
	private SettlementInfoService settlementInfoService;
	private SettlementInfo settlementInfo;
	
	private Long infoId;

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public SettlementInfo getSettlementInfo() {
		return settlementInfo;
	}

	public void setSettlementInfo(SettlementInfo settlementInfo) {
		this.settlementInfo = settlementInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		List<SettlementInfo> list= settlementInfoService.listByOrgId(getRequest());
		Type type=new TypeToken<List<SettlementInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list==null?0:list.size()).append(",result:");
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
				settlementInfoService.remove(new Long(id));
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
		SettlementInfo settlementInfo=settlementInfoService.get(infoId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(settlementInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(settlementInfo.getInfoId()==null){
			settlementInfoService.save(settlementInfo);
		}else{
			SettlementInfo orgSettlementInfo=settlementInfoService.get(settlementInfo.getInfoId());
			try{
				BeanUtil.copyNotNullProperties(orgSettlementInfo, settlementInfo);
				settlementInfoService.save(orgSettlementInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
