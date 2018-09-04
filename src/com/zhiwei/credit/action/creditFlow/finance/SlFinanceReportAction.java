package com.zhiwei.credit.action.creditFlow.finance;

/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
 */
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import java.math.BigDecimal;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;

import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;

import com.zhiwei.credit.model.creditFlow.finance.ProYearRate;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.thirdRalation.SVEnterprisePersonService;


import flexjson.JSONSerializer;

/**
 * 
 * @author
 * 
 */
public class SlFinanceReportAction extends BaseAction {
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	private SlSmallloanProject slSmallloanProject;
	private SVEnterprisePersonService sVEnterprisePersonService;
	private Long projectId;
	public SlSmallloanProjectService getSlSmallloanProjectService() {
		return slSmallloanProjectService;
	}
	public void setSlSmallloanProjectService(
			SlSmallloanProjectService slSmallloanProjectService) {
		this.slSmallloanProjectService = slSmallloanProjectService;
	}
	public SlSmallloanProject getSlSmallloanProject() {
		return slSmallloanProject;
	}
	public void setSlSmallloanProject(SlSmallloanProject slSmallloanProject) {
		this.slSmallloanProject = slSmallloanProject;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String list1(){
		List<ProYearRate> list= slSmallloanProjectService.getProYearRate();
		
		Type type = new TypeToken<List<ProYearRate>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(list.size()).append(
						",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();
		return SUCCESS;
	}
	public String ratelist1(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlSmallloanProject> list= slSmallloanProjectService.getAll(filter);
		List<ProYearRate> list1= new ArrayList<ProYearRate>();
		for(SlSmallloanProject s:list){
			
			ProYearRate proYearRate=new ProYearRate();
			proYearRate.setAccrualMoney(s.getAccrualMoney());
//			proYearRate.setElseMoney(s.getElseMoney());
			proYearRate.setProjectName(s.getProjectName());
//			proYearRate.setCommisionMoney(s.getCommisionMoney());
			proYearRate.setProjectMoney(s.getProjectMoney());
	//		proYearRate.setOperationTypeName(s.getOperationType());
			proYearRate.setYearRate(s.getAnnualNetProfit());
			list1.add(proYearRate);
			
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("startDate","intentDate");
		buff.append(json.serialize(list1));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String profitlist(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlSmallloanProject> list= slSmallloanProjectService.getAll(filter);
		List<ProYearRate> list1= new ArrayList<ProYearRate>();
		for(SlSmallloanProject s:list){
			
			ProYearRate proYearRate=new ProYearRate();
			proYearRate.setAccrualMoney(s.getAccrualMoney());
//			proYearRate.setElseMoney(s.getElseMoney());
			proYearRate.setProjectName(s.getProjectName());
	//		proYearRate.setCommisionMoney(s.getCommisionMoney());
			proYearRate.setProjectMoney(s.getProjectMoney());
//			proYearRate.setOperationTypeName(s.getOperationType());
			BigDecimal accrualMoney=s.getAccrualMoney();
	//		BigDecimal commisionMoney =s.getCommisionMoney();
	//		BigDecimal elseMoney=s.getElseMoney();
	//		BigDecimal yearRate=accrualMoney.subtract(commisionMoney).subtract(elseMoney).divide(elseMoney,2);
		//	proYearRate.setYearRate(yearRate);
			list1.add(proYearRate);
			
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("startDate","intentDate");
		buff.append(json.serialize(list1));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
//	public String list2(){
//		Map<String,String> map = new HashMap<String,String>();
//		Enumeration paramEnu= getRequest().getParameterNames();
//    	while(paramEnu.hasMoreElements()){
//    		String paramName=(String)paramEnu.nextElement();
//    			String paramValue=(String)getRequest().getParameter(paramName);
//    			map.put(paramName, paramValue);
//    			System.out.print(map.get(paramName));
//    		
//    	}
//
//	//QueryFilter filter=new QueryFilter(getRequest());
//	//	List<SlSmallloanProject> list= slSmallloanProjectService.getAll(filter);
//		List<SlSmallloanProject> list= slSmallloanProjectService.getProDetail(map);
//		for(SlSmallloanProject l:list){
////			l.setProjectNumber(sVEnterprisePersonService.get(l.getOppositeID()).getName());
//		//	Set<SlFundIntent> intentlist=l.getSlFundIntents();
//			BigDecimal afterMoney=new BigDecimal(0.00);
//			BigDecimal allMoney=new BigDecimal(0.00);
//		//	if(intentlist.size()!=0){
//			//	allMoney=allMoney.add(l.getAccrualMoney().add(l.getProjectMoney()));
//		  //   afterMoney=afterMoney.add(l.getPayAccrualMoney()).add(l.getPayProjectMoney());
//		//	BigDecimal factor=allMoney.subtract(afterMoney).divide(allMoney,2);
//			
//		//	l.setPayProjectMoney(l.getPayProjectMoney().add(l.getPayAccrualMoney()));
//		//	l.setPayAccrualMoney(allMoney.subtract(afterMoney));
//		//	l.setFactor(factor);
//		
//			}
//		}
//		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
//		.append(list.size()).append(",result:");
//		JSONSerializer json = JsonUtil.getJSONSerializer("startDate","intentDate");
//		buff.append(json.serialize(list));
//		buff.append("}");
//		jsonString = buff.toString();
//		System.out.print(jsonString);
//		return SUCCESS;
//	}
}
