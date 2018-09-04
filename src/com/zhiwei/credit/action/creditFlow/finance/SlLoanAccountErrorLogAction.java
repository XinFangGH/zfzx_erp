package com.zhiwei.credit.action.creditFlow.finance;
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
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.creditFlow.finance.SlLoanAccountErrorLog;
import com.zhiwei.credit.service.creditFlow.finance.SlLoanAccountErrorLogService;
/**
 * 
 * @author 
 *
 */
public class SlLoanAccountErrorLogAction extends BaseAction{
	@Resource
	private SlLoanAccountErrorLogService slLoanAccountErrorLogService;
	private SlLoanAccountErrorLog slLoanAccountErrorLog;
	
	private Long accountErrorId;

	public Long getAccountErrorId() {
		return accountErrorId;
	}

	public void setAccountErrorId(Long accountErrorId) {
		this.accountErrorId = accountErrorId;
	}

	public SlLoanAccountErrorLog getSlLoanAccountErrorLog() {
		return slLoanAccountErrorLog;
	}

	public void setSlLoanAccountErrorLog(SlLoanAccountErrorLog slLoanAccountErrorLog) {
		this.slLoanAccountErrorLog = slLoanAccountErrorLog;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		List<SlLoanAccountErrorLog> list;
		String customerName=this.getRequest().getParameter("customerName");
		if(null==customerName){
			customerName="";
		}
		String projectNum=this.getRequest().getParameter("projectNum");
		if(null==projectNum){
			projectNum="";
		}
		String companyId=this.getRequest().getParameter("companyId");
		PagingBean pb=new PagingBean(start,limit);
		if((projectNum==null||projectNum.equals(""))&&(customerName==null||customerName.equals(""))){
			list= slLoanAccountErrorLogService.getAll(pb);
		}else{
	      list= slLoanAccountErrorLogService.getList(customerName, projectNum,companyId, pb);
		}
		Type type=new TypeToken<List<SlLoanAccountErrorLog>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		
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
				slLoanAccountErrorLogService.remove(new Long(id));
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
		SlLoanAccountErrorLog slLoanAccountErrorLog=slLoanAccountErrorLogService.get(accountErrorId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slLoanAccountErrorLog));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slLoanAccountErrorLog.getAccountErrorId()==null){
			slLoanAccountErrorLogService.save(slLoanAccountErrorLog);
		}else{
			SlLoanAccountErrorLog orgSlLoanAccountErrorLog=slLoanAccountErrorLogService.get(slLoanAccountErrorLog.getAccountErrorId());
			try{
				BeanUtil.copyNotNullProperties(orgSlLoanAccountErrorLog, slLoanAccountErrorLog);
				slLoanAccountErrorLogService.save(orgSlLoanAccountErrorLog);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
