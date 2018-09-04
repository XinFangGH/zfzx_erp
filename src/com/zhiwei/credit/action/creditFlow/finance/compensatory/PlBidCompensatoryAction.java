package com.zhiwei.credit.action.creditFlow.finance.compensatory;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatory;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryService;
/**
 * 
 * @author 
 *
 */
public class PlBidCompensatoryAction extends BaseAction{
	@Resource
	private PlBidCompensatoryService plBidCompensatoryService;
	private PlBidCompensatory plBidCompensatory;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlBidCompensatory getPlBidCompensatory() {
		return plBidCompensatory;
	}

	public void setPlBidCompensatory(PlBidCompensatory plBidCompensatory) {
		this.plBidCompensatory = plBidCompensatory;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlBidCompensatory> list= plBidCompensatoryService.compensatoryList(pb,map);
		
		Type type = new TypeToken<List<PlBidCompensatory>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,totalCounts:")
				.append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 逾期代偿台账导出excel（未还）
	 */
	public void listOverDueToExcel(){
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlBidCompensatory> list= plBidCompensatoryService.compensatoryList(null,map);
		String[] tableHeader = {"序号", "付款人", "招标项目名称", "招标项目编号","代偿期数","代偿方" ,"代偿日期","代偿金额","代偿天数","罚息总金额","已偿还罚息总金额","已平账总金额","待偿还总金额","最近一次还款日期"};
		String[] fields = {"custmerName","bidPlanname","bidPlanNumber","payintentPeriod","compensatoryName","compensatoryDate","compensatoryMoney","compensatoryDays","punishMoney","backPunishMoney","backCompensatoryMoney","plateMoney","totalMoney","backDate"};
		try {
			ExportExcel.export(tableHeader, fields, list,"逾期代偿台账", PlBidCompensatory.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 逾期代偿台账导出excel（已还）
	 */
	public void listBackToExcel(){
		
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlBidCompensatory> list= plBidCompensatoryService.compensatoryList(null,map);
		String[] tableHeader = {"序号", "付款人", "招标项目名称", "招标项目编号","代偿期数","代偿方" ,"代偿日期","代偿金额","代偿天数","罚息总金额","已偿还罚息总金额","已平账总金额","待偿还总金额","最近一次还款日期"};
		String[] fields = {"custmerName","bidPlanname","bidPlanNumber","payintentPeriod","compensatoryName","compensatoryDate","compensatoryMoney","compensatoryDays","punishMoney","backPunishMoney","backCompensatoryMoney","plateMoney","totalMoney","backDate"};
		try {
			ExportExcel.export(tableHeader, fields, list,"以回款代偿台账", PlBidCompensatory.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				plBidCompensatoryService.remove(new Long(id));
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
		PlBidCompensatory plBidCompensatory=plBidCompensatoryService.getOneList(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plBidCompensatory));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plBidCompensatory.getId()==null){
			plBidCompensatoryService.save(plBidCompensatory);
		}else{
			PlBidCompensatory orgPlBidCompensatory=plBidCompensatoryService.get(plBidCompensatory.getId());
			try{
				BeanUtil.copyNotNullProperties(orgPlBidCompensatory, plBidCompensatory);
				plBidCompensatoryService.save(orgPlBidCompensatory);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String display(){
		PagingBean pb = new PagingBean(0, 7);
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlBidCompensatory> list= plBidCompensatoryService.compensatoryList(pb,map);
		getRequest().setAttribute("overDuePlanList", list);
		return "display";
	}
}
