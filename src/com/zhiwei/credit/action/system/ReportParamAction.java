package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.ReportParam;
import com.zhiwei.credit.service.system.ReportParamService;
/**
 * 
 * @author 
 *
 */
public class ReportParamAction extends BaseAction{
	@Resource
	private ReportParamService reportParamService;
	private ReportParam reportParam;
	
	private Long paramId;

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public ReportParam getReportParam() {
		return reportParam;
	}

	public void setReportParam(ReportParam reportParam) {
		this.reportParam = reportParam;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		String strReportId=getRequest().getParameter("reportId");
		if(StringUtils.isNotEmpty(strReportId)){
			List<ReportParam> list= reportParamService.findByRepTemp(new Long(strReportId));
			
			Type type=new TypeToken<List<ReportParam>>(){}.getType();
			StringBuffer buff = new StringBuffer("{success:true,")
			.append("result:");
			Gson gson=new Gson();
			buff.append(gson.toJson(list, type));
			buff.append("}");
			jsonString=buff.toString();
		}
		
		
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
				reportParamService.remove(new Long(id));
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
		ReportParam reportParam=reportParamService.get(paramId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(reportParam));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		reportParamService.save(reportParam);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
