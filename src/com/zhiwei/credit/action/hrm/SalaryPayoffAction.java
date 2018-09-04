package com.zhiwei.credit.action.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.hrm.SalaryPayoff;
import com.zhiwei.credit.model.hrm.StandSalaryItem;
import com.zhiwei.credit.service.hrm.SalaryPayoffService;
import com.zhiwei.credit.service.hrm.StandSalaryItemService;

/**
 * 
 * @author 
 *
 */
public class SalaryPayoffAction extends BaseAction{
	@Resource
	private SalaryPayoffService salaryPayoffService;
	@Resource
	private StandSalaryItemService standSalaryItemService;
	private SalaryPayoff salaryPayoff;
	
	private Long recordId;

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public SalaryPayoff getSalaryPayoff() {
		return salaryPayoff;
	}

	public void setSalaryPayoff(SalaryPayoff salaryPayoff) {
		this.salaryPayoff = salaryPayoff;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SalaryPayoff> list= salaryPayoffService.getAll(filter);
		
		Type type=new TypeToken<List<SalaryPayoff>>(){}.getType();
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
				salaryPayoffService.remove(new Long(id));
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
		SalaryPayoff salaryPayoff=salaryPayoffService.get(recordId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		sb.append(gson.toJson(salaryPayoff));
		sb.append("]}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(salaryPayoff.getRecordId()==null){
			salaryPayoff.setCheckStatus(SalaryPayoff.CHECK_FLAG_NONE);
			salaryPayoff.setRegTime(new Date());
			salaryPayoff.setRegister(ContextUtil.getCurrentUser().getFullname());
		}
		BigDecimal acutalAmount = salaryPayoff.getStandAmount().add(salaryPayoff.getEncourageAmount()).subtract(salaryPayoff.getDeductAmount());
		if(salaryPayoff.getAchieveAmount().compareTo(new BigDecimal(0)) == 1){
			acutalAmount = acutalAmount.add(salaryPayoff.getAchieveAmount());
		}
		salaryPayoff.setAcutalAmount(acutalAmount);
		salaryPayoffService.save(salaryPayoff);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 审核薪酬发放
	 * @return
	 */
	public String check(){
		SalaryPayoff checkSalaryPayoff = salaryPayoffService.get(new Long(recordId));
		checkSalaryPayoff.setCheckTime(new Date());
		checkSalaryPayoff.setCheckName(ContextUtil.getCurrentUser().getFullname());
		checkSalaryPayoff.setCheckStatus(salaryPayoff.getCheckStatus());
		checkSalaryPayoff.setCheckOpinion(salaryPayoff.getCheckOpinion());
		salaryPayoffService.save(checkSalaryPayoff);
		return SUCCESS;
	}
	/**
	 * 查询个人薪酬
	 * @return
	 */
	public String personal(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<SalaryPayoff> list= salaryPayoffService.getAll(filter);
		//Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		//Type type=new TypeToken<List<SalaryPayoff>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:[");
		for(SalaryPayoff salaryDetail : list){
			buff.append("{recordId:'")
				.append(salaryDetail.getRecordId())
				.append("',fullname:'")
				.append(salaryDetail.getFullname())
				.append("',profileNo:'")
				.append(salaryDetail.getProfileNo())
				.append("',idNo:'")
				.append(salaryDetail.getIdNo())
				.append("',standAmount:'")
				.append(salaryDetail.getStandAmount())
				.append("',acutalAmount:'")
				.append(salaryDetail.getAcutalAmount())
				.append("',startTime:'")
				.append(salaryDetail.getStartTime())
				.append("',endTime:'")
				.append(salaryDetail.getEndTime())
				.append("',checkStatus:'")
				.append(salaryDetail.getCheckStatus());
			List<StandSalaryItem> items = standSalaryItemService.getAllByStandardId(salaryDetail.getStandardId());
			StringBuffer content = new StringBuffer("<table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\"><tr>");
			
			if(salaryDetail.getEncourageAmount()!= new BigDecimal(0) &&
					salaryDetail.getEncourageAmount()!= null){//奖励金额明细
				content.append("<th>")
					   .append("奖励金额</th><td>")
					   .append(salaryDetail.getEncourageAmount())
					   .append("</td>");
			}
			
			if(salaryDetail.getEncourageAmount()!= new BigDecimal(0) &&
					salaryDetail.getEncourageAmount()!= null){//扣除金额明细
				content.append("<th>")
					   .append("扣除金额</th><td>")
					   .append(salaryDetail.getDeductAmount())
					   .append("</td>");
			}
			
			if(salaryDetail.getEncourageAmount()!= new BigDecimal(0) &&
					salaryDetail.getEncourageAmount()!= null){//效绩金额明细
				content.append("<th>")
					   .append("效绩金额</th><td>")
					   .append(salaryDetail.getAchieveAmount())
					   .append("</td>");
			}
			content.append("</tr></table><table class=\"table-info\" cellpadding=\"0\" cellspacing=\"1\" width=\"98%\" align=\"center\"><tr>");
			for(StandSalaryItem item : items){
				content.append("<th>")
					   .append(item.getItemName())
					   .append("</th>");
			}
			content.append("</tr><tr>");
			for(StandSalaryItem item2 : items){
				content.append("<td>")
				   .append(item2.getAmount())
				   .append("</td>");
			}
			content.append("</tr></table>");
			buff.append("',content:'")
				.append(content.toString())
				.append("'},");
		}
		if(list.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
}
