package com.zhiwei.credit.action.flow;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.flow.TaskProxy;
import com.zhiwei.credit.service.flow.TaskProxyService;
/**
 * 
 * @author 
 *
 */
public class TaskProxyAction extends BaseAction{
	@Resource
	private TaskProxyService taskProxyService;
	private TaskProxy taskProxy;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TaskProxy getTaskProxy() {
		return taskProxy;
	}

	public void setTaskProxy(TaskProxy taskProxy) {
		this.taskProxy = taskProxy;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		PageBean<TaskProxy> pageBean = new PageBean<TaskProxy>(start,limit,getRequest());
		taskProxyService.findList(pageBean);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pageBean.getTotalCounts()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(pageBean.getResult()));
		buff.append("}");
		jsonString = buff.toString();
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
				taskProxyService.remove(new Long(id));
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
		TaskProxy taskProxy=taskProxyService.get(id);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(taskProxy));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(taskProxy.getId()==null){
			taskProxy.setCreateDate(new Date());
			taskProxyService.save(taskProxy);
		}else{
			TaskProxy orgTaskProxy=taskProxyService.get(taskProxy.getId());
			try{
				BeanUtil.copyNotNullProperties(orgTaskProxy, taskProxy);
				orgTaskProxy.setCreateDate(new Date());
				taskProxyService.save(orgTaskProxy);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 导出Excel
	 * @return
	 */
	public void outputExcel(){
		String [] tableHeader = {"序号","被代理人","代理人","代理开始时间","代理结束时间","指定人","指定时间","状态"};
		try {
			PageBean<TaskProxy> pageBean = new PageBean<TaskProxy>(start,limit,getRequest());
			taskProxyService.findList(pageBean);
			ExcelHelper.export(pageBean.getResult(),tableHeader,"流程任务代理列表");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}